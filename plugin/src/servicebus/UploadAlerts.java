package servicebus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.TimerTask;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAP12Constants;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.OperationClient;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.wsdl.WSDLConstants;

import common.AlertCom;
import common.SnortEventCom;

import configuration.LoggerConfiguration;
import dataAccess.SnortAccess;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

public class UploadAlerts extends TimerTask {

	private static  EndpointReference targetEPR ;
	private static  String TimeStampFromServer = "" ;
	private static  String Duplicate = "duplicate" ;

   
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		feedAlerts();
		//SendAlerts();
	}

	private void feedAlerts()
	{
		try {
			//System.out.println("I am getting the timestamp");	
		Long LastTimeStamp = SWAClient.SenderObj.getLastTimeStamp();
		if (LastTimeStamp< 0)
		 {
			// System.out.println("Server is either down or busy ... Mission Cancelled");
			 return;
		 }
		 
		
		String LOGGERIDENTITY = "alert_loggeridentity";
		String TIMESTAMP = "0";
		String SOURCEIP = "0";
		String SOURCEPORT = "0";
		String SOURCEMAC = "alert_source_mac";
		String SOURCEIPPRENAT = "0";
		String SOURCEIPPOSTNAT = "0";
		String SOURCEPORTPRENAT = "0";
		String SOURCEPORTPOSTNAT = "0";
		String DESTINATIONIP = "0";
		String DESTINATIONPORT = "0";
		String DESTINATIONMAC = "alert_destination_mac";
		String DESTINATIONIPPRENAT = "0";
		String DESTINATIONIPPOSTNAT = "0";
		String DESTINATIONPORTPRENAT = "0";
		String DESTINATIONPORTPOSTNAT = "0";
		String EVENTNAME = "alert_event_name";
		String EVENTCATEGORY = "alert_event_category";
		String REFERENCETAG = "reference_tag";
		String REFERENCESYSTEM = "reference_system";
		String DESCRIPTION = "alert_description";
		String PRIORITY = "0";
		String DEVICETYPE = "alert_deviceType";
		String DOMAINNAME = "alert_domain_name";
		String GROUPNAME = "alert_group_name";
		String NETBIOSNAME = "alert_net_bios_name";
		
		 
		 DateTime dt = new DateTime();
		
		

		 String endTimeStr = dt.toString(ISODateTimeFormat.basicDateTimeNoMillis()).replace("T", "").replace("Z", "");
		 endTimeStr = endTimeStr.substring(0,14 );
		 Long endTimeLocal =  Long.parseLong(endTimeStr);
		 String beginTimeStr ;
		
		 
		DateTime begindt = new DateTime(DateTimeZone.UTC);
		begindt = begindt.withMillis(LastTimeStamp);
		begindt = begindt.withZone(DateTimeZone.getDefault());
		beginTimeStr = begindt.toString(ISODateTimeFormat.basicDateTimeNoMillis()).replace("T", "").replace("Z", "");
	
        beginTimeStr = beginTimeStr.substring(0,14 );
		
		 
		 Long beginTimeLocal =  Long.parseLong(beginTimeStr);
		
				 
 		 SnortEventCom attachContent = (new SnortAccess()).getSnortEvent(beginTimeLocal + 1 , endTimeLocal);
        
 		 if (attachContent.getRowCount() > 0)
 		 {
 			//System.out.println("I am pushing "+ attachContent.getRowCount()+" alerts");
 			int i;
 			for ( i=0 ; i < attachContent.getRowCount();i++)
 			{
 				
 				
 				LOGGERIDENTITY = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_logger_id ));
 	 				 				
 				TIMESTAMP = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_timeStamp ));
 				SOURCEIP = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_ip_src ));
 				
 				if(! String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_tcp_dport )).toLowerCase().equals("null")){
 					
 					SOURCEPORT = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_tcp_sport ));
 					DESTINATIONPORT = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_tcp_dport ));
 					EVENTNAME = "TCP";
 				}
 				else if(! String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_icmp_type )).toLowerCase().equals("null")){
 					
 					
 					SOURCEPORT = "0";
 					DESTINATIONPORT = "0";
 					EVENTNAME = "ICMP";
 					
 				}
 				else if(! String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_udp_dport )).toLowerCase().equals("null")){
 					
 					SOURCEPORT = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_udp_sport ));
 					DESTINATIONPORT = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_udp_dport ));
 					EVENTNAME = "UDP";
 					}
 				

 				
 			//	SOURCEPORT = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_tcp_sport ));
 				SOURCEMAC = "N/A";
 				if (SOURCEIP.toLowerCase().equals("null"))	SOURCEIP = "0";
 				if (SOURCEPORT.toLowerCase().equals("null"))	SOURCEPORT = "0";
 				
 				SOURCEIPPRENAT = SOURCEIP ;
 				SOURCEIPPOSTNAT = SOURCEIP;
 				SOURCEPORTPRENAT = SOURCEPORT;
 				SOURCEPORTPOSTNAT =SOURCEPORT;
 				
 				DESTINATIONIP = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_ip_dst ));
 				//DESTINATIONPORT = String.valueOf(attachContent.getValueAt(i, AlertCom.FLD_DESTINATIONPORT ));
 				DESTINATIONMAC = "N/A";
 				if (DESTINATIONIP.toLowerCase().equals("null"))	DESTINATIONIP = "0";
 				if (DESTINATIONPORT.toLowerCase().equals("null"))	DESTINATIONPORT = "0";
 				DESTINATIONIPPRENAT = DESTINATIONIP;
 				DESTINATIONIPPOSTNAT = DESTINATIONIP;
 				DESTINATIONPORTPRENAT = DESTINATIONPORT;
 				DESTINATIONPORTPOSTNAT = DESTINATIONPORT;
 				//EVENTNAME = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_sig_name ));
 				EVENTCATEGORY = "N/A";
 				REFERENCETAG = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_sig_name));
 				REFERENCESYSTEM = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_sig_class_name ));
 				DESCRIPTION = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_detail_text ));
 				PRIORITY = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_sig_priority ));
 				DEVICETYPE = "Snort";
 				DOMAINNAME = String.valueOf(attachContent.getValueAt(i, SnortEventCom.fld_hostname ));
 				GROUPNAME = "N/A";
 				
 				NETBIOSNAME = "N/A";
 				
 				if (PRIORITY.toLowerCase().equals("null")) 
 					PRIORITY = "0";
 				  
 				
 				SWAClient.SenderObj.Feed_Alert(LOGGERIDENTITY,TIMESTAMP, SOURCEIP, SOURCEPORT, SOURCEMAC, SOURCEIPPRENAT,SOURCEIPPOSTNAT,SOURCEPORTPRENAT,SOURCEPORTPOSTNAT,DESTINATIONIP,DESTINATIONPORT,DESTINATIONMAC,DESTINATIONIPPRENAT,DESTINATIONIPPOSTNAT,DESTINATIONPORTPRENAT,DESTINATIONPORTPOSTNAT,EVENTNAME,EVENTCATEGORY,REFERENCETAG,REFERENCESYSTEM,DESCRIPTION,PRIORITY,DEVICETYPE,DOMAINNAME,GROUPNAME,NETBIOSNAME);
 			}
 			
 			//System.out.println("alrets were pushed");
 			//SaveTimeStamp(String.valueOf(dt.getMillis()));
 		//	System.out.println("");
 		//	System.out.println("feeded Alerts  "+ String.valueOf(i));
 		 } 
 		 
 	
		}
		catch (Exception e) {  
			System.out.print(e.toString());
		} 
	
	}
	
	
	private void SendAlerts()
	{
		try {
		 LoggerConfiguration configObj = new LoggerConfiguration();
		 
		 targetEPR = new EndpointReference(configObj.TargetEPR);
		
		 DateTimeZone zoneUTC = DateTimeZone.UTC;
		 
		 DateTime dt = new DateTime();
		
		 DateTime dtUTC = dt.withZone(DateTimeZone.UTC);

		 String endTimeStr = dt.toString(ISODateTimeFormat.basicDateTimeNoMillis()).replace("T", "").replace("Z", "");
	//	 if (endTimeStr.indexOf("-") > 0)
			 endTimeStr = endTimeStr.substring(0,14 );//(0,endTimeStr.indexOf("-") );
		 Long endTimeLocal =  Long.parseLong(endTimeStr);
		 String beginTimeStr ;
		
		 
		DateTime begindt = new DateTime(DateTimeZone.UTC);
		begindt = begindt.withMillis(Long.parseLong(configObj.LastTimeStamp));
		begindt = begindt.withZone(DateTimeZone.getDefault());
		beginTimeStr = begindt.toString(ISODateTimeFormat.basicDateTimeNoMillis()).replace("T", "").replace("Z", "");
		//	 if (beginTimeStr.indexOf("-") > 0)
				 beginTimeStr = beginTimeStr.substring(0,14 );//.substring(0,beginTimeStr.indexOf("-") );
		
		 
		 Long beginTimeLocal =  Long.parseLong(beginTimeStr);

		 Long endTimeUTC = dtUTC.getMillis();
		 DateTime test = new DateTime(DateTimeZone.UTC);
		 test = test.withMillis(endTimeUTC);
		 
		 Long beginTimeUTC ;
		
		 dtUTC = dtUTC.withMillis(Long.parseLong(configObj.LastTimeStamp));//  DateTimeFormat.forPattern("yyyyMMddhhmmss").parseDateTime(String.valueOf(configObj.LastTimeStamp));
		 beginTimeUTC= dtUTC.getMillis();
		 
 		 SnortEventCom attachContent = (new SnortAccess()).getSnortEvent(beginTimeLocal, endTimeLocal);
        
 		 if (attachContent.getRowCount() > 0)
 		 {
 			
 			System.out.print("Sending " + String.valueOf(attachContent.getRowCount() + " Alert(s) ... "));
 		    Boolean result =   TransferAlerts(attachContent , configObj.LoggerIdentity ,String.valueOf(beginTimeUTC) ,String.valueOf(endTimeUTC)) ;
 		
 		if (result) 
 			if (! TimeStampFromServer.equals(""))
 			{
 				 				 
 				SaveTimeStamp(TimeStampFromServer);
 				TimeStampFromServer = "";
 				
 			}
 			else
 			{ 	SaveTimeStamp(String.valueOf(endTimeUTC));}
 		
 		 }	
 		else 
 		{	System.out.println("Sending 0 Alert(s) ... Mission Cancelled");}
 		 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	
		
	}
	

	private void SaveTimeStamp(String TimeStamp)
	{
		
		Boolean result = (new LoggerConfiguration()).SaveTimeStamp(TimeStamp);
		
	}
	
	public static Boolean TransferAlerts(SnortEventCom SnortEventComobj, String LoggerIdentity ,String BeginTimeStamp, String EndTimeStamp)
			throws Exception {

		 try {
			 
		 
		Options options = new Options();
		options.setTo(targetEPR);
		options.setProperty(Constants.Configuration.ENABLE_SWA,	Constants.VALUE_TRUE);
		options.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		// Increase the time out when sending large attachments
		options.setTimeOutInMilliSeconds(1000000);
		options.setTo(targetEPR);
		options.setAction("urn:getAlerts");
		options.setProperty(HTTPConstants.CHUNKED,false); 

	
		ServiceClient sender = new ServiceClient();
		sender.setOptions(options);
		OperationClient mepClient = sender.createClient(ServiceClient.ANON_OUT_IN_OP);

		MessageContext mc = new MessageContext();
		
		
		DataHandler dataHandler =  makeDH(SnortEventComobj);
	    
		 
		String attachmentID = mc.addAttachment(dataHandler);

		SOAPFactory fac = OMAbstractFactory.getSOAP12Factory();
		SOAPEnvelope env = fac.getDefaultEnvelope();
		OMNamespace omNs = fac.createOMNamespace("http://servicebus", "swa");
		OMElement uploadAlerts = fac.createOMElement("Alerts", omNs);
		
		OMElement LoggerIdentityEle = fac.createOMElement("LoggerIdentity", omNs);
		LoggerIdentityEle.setText(LoggerIdentity);
		
		OMElement BeginTimeStampEle = fac.createOMElement("BeginTimeStamp", omNs);
		BeginTimeStampEle.setText(BeginTimeStamp);
		
		OMElement EndTimeStampEle = fac.createOMElement("EndTimeStamp", omNs);
		EndTimeStampEle.setText(EndTimeStamp);
		
		
		OMElement idEle = fac.createOMElement("attchmentID", omNs);
		idEle.setText(attachmentID);
		
		uploadAlerts.addChild(LoggerIdentityEle);
		uploadAlerts.addChild(BeginTimeStampEle);
		uploadAlerts.addChild(EndTimeStampEle);
		uploadAlerts.addChild(idEle);
		env.getBody().addChild(uploadAlerts);
		
		mc.setEnvelope(env);

		
		mepClient.addMessageContext(mc);
		mepClient.execute(true);
		MessageContext response = mepClient.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
	  	SOAPBody body = response.getEnvelope().getBody();
	  	OMElement element = body.getFirstElement().getFirstChildWithName(
	  	new QName("http://servicebus","return"));
		System.out.println(element.getText());
		
		if ( element.getText().toLowerCase().equals("success"))
			return true ;
		else if( element.getText().toLowerCase().indexOf(Duplicate)>-1)
			{ TimeStampFromServer = element.getText().toLowerCase().substring(element.getText().toLowerCase().indexOf(Duplicate)+Duplicate.length()+1);
		     return true ;}
		else 
			return false ;
				
		
		 } catch(Exception e) {
	            System.out.println(e.getMessage());
	            return false ;
	        }
		 
	}
	
	public static byte[] toByteArray (Object obj)
	{
	  byte[] bytes = null;
	  ByteArrayOutputStream bos = new ByteArrayOutputStream();
	  try {
	    ObjectOutputStream oos = new ObjectOutputStream(bos); 
	    oos.writeObject(obj);
	    oos.flush(); 
	    oos.close(); 
	    bos.close();
	    bytes = bos.toByteArray ();
	  }
	  catch (IOException ex) {
	    //TODO: Handle the exception
	  }
	  return bytes;
	}
	
	public static DataHandler makeDH (Object obj)
	{
		byte[] array = toByteArray(obj);
		ByteArrayDataSource ds = new ByteArrayDataSource();
		ds.setContentType("multipart/form-data");
		ds.setBytes(array);
		DataHandler dh = new DataHandler(ds);
		return dh ;
		
	}
	
	
}
