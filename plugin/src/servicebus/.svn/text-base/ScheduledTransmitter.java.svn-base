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
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.OperationClient;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.wsdl.WSDLConstants;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import common.AlertCom;
import dataAccess.AlertAccess;

public class ScheduledTransmitter extends TimerTask  {


	private static EndpointReference WebServiceEndPoint  ;
	private static String LoggerID = "" ;
	public  static boolean still_sending = false ;
	public  static boolean server_was_busy = false ;
	

	private static AlertWrapper AReflectOfAlertComObj;
	
	
	public ScheduledTransmitter(AlertWrapper AlertWrapperObj , String WSEndPoint ,String Logger_ID )
	{
		if (!WSEndPoint.equals(""))
		{
			AReflectOfAlertComObj =  AlertWrapperObj;	
			WebServiceEndPoint = new EndpointReference(WSEndPoint);
				LoggerID = Logger_ID;
		}
		
	}


	@Override
	public void run() {
		if (WebServiceEndPoint ==null) 
			{ 
			System.out.println("Web Service EndPoint is not Supplied.");
			return ; 
			}
		if (AlertPluginFramework.UsingDataBase)
			Transmit_UsingDataBase();
		else 
			Transmit();
	}
	

	
	
	
	public synchronized static void Transmit ()
	{
		 try {
			 
			// AlertPluginFramework.totalalertcountSent_temp +=NumberOfAlerts;
				System.out.println("total alerts = "+ AlertPluginFramework.totalalertcount_temp  );
			
				
			 if (still_sending)
			 {
				 System.out.println("Still sending previous package ... Mission Cancelled");
				 return ;
			 }
			 
			 
			 
		int NumberOfAlerts ; 		
		AlertCom AlertComObj;
		
		AlertComObj = 	AReflectOfAlertComObj.GetLatestAlerts();
		
		NumberOfAlerts = AlertComObj.getRowCount();

		
		if (NumberOfAlerts==0)
		{ System.out.println("Sending 0 Alert(s) ... Mission Cancelled");
		   return;
		}
		
	
		
		String hndshake = AlertPluginFramework.handShake();
		
		if ( hndshake.equals(AlertPluginFramework.CONST_WAIT))
		{
			System.out.print("Sending " + String.valueOf(NumberOfAlerts) + " Alert(s) ... ");
			System.out.println("Server is busy ... Mission Cancelled");
			server_was_busy = true;
		  return;
		}
		
//		if (  hndshake.equals(AlertPluginFramework.CONST_LOGGER_ERRORFUL))
//		{
//			System.out.print("Sending " + String.valueOf(NumberOfAlerts) + " Alert(s) ... ");
//			System.out.println("Error has occured in server while storing alert... Mission Cancelled");
//			AReflectOfAlertComObj.RemoveAlertsAll(); 
//			 AlertPluginFramework.setLastTimeStamp(Long.valueOf(-1));
//		  return;
//		}
		
		if (  hndshake.equals(AlertPluginFramework.CONST_SERVERISDOWN))
		{
			System.out.print("Sending " + String.valueOf(NumberOfAlerts) + " Alert(s) ... ");
			System.out.println("Server is down... Mission Cancelled");
		//	AReflectOfAlertComObj.RemoveAlertsAll();  //its not needed next time server starts up, these alerts will be removed because of not getting timestamp error 
		//	AlertPluginFramework.setLastTimeStamp(Long.valueOf(-1));
		  return;
		}
		
		
		still_sending = true ;
		server_was_busy = false;
		

		 Long endTimeUTC; 
		 Long beginTimeUTC;
		 
		 Long Times[] = GetBegin_EndTime(AlertComObj);
		 beginTimeUTC =  Times[0];
		 endTimeUTC = Times[1];
		 
			 
				Options options = new Options();
			
				options.setProperty(Constants.Configuration.ENABLE_SWA,	Constants.VALUE_TRUE);
				options.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
				// Increase the time out when sending large attachments
				options.setTimeOutInMilliSeconds(20000);
				options.setTo(WebServiceEndPoint);
				options.setAction("urn:getGeneralAlerts");
				options.setProperty(HTTPConstants.CHUNKED,false); 

			
				ServiceClient sender = new ServiceClient();
				sender.setOptions(options);
				OperationClient mepClient = sender.createClient(ServiceClient.ANON_OUT_IN_OP);

				MessageContext mc = new MessageContext();
				
				
				DataHandler dataHandler =  makeDH(AlertComObj);
			    
				 
				String attachmentID = mc.addAttachment(dataHandler);

				SOAPFactory fac = OMAbstractFactory.getSOAP12Factory();
				SOAPEnvelope env = fac.getDefaultEnvelope();
				OMNamespace omNs = fac.createOMNamespace("http://servicebus", "swa");
				OMElement uploadAlerts = fac.createOMElement("Alerts", omNs);
				
				OMElement LoggerIdentityEle = fac.createOMElement("LoggerIdentity", omNs);
				LoggerIdentityEle.setText(LoggerID);
				
				OMElement BeginTimeStampEle = fac.createOMElement("BeginTimeStamp", omNs);
				BeginTimeStampEle.setText(String.valueOf(beginTimeUTC));
				
				OMElement EndTimeStampEle = fac.createOMElement("EndTimeStamp", omNs);
				EndTimeStampEle.setText(String.valueOf(endTimeUTC));
				
				
				OMElement idEle = fac.createOMElement("attchmentID", omNs);
				idEle.setText(attachmentID);
				
				uploadAlerts.addChild(LoggerIdentityEle);
				uploadAlerts.addChild(BeginTimeStampEle);
				uploadAlerts.addChild(EndTimeStampEle);
				uploadAlerts.addChild(idEle);
				env.getBody().addChild(uploadAlerts);
				
				mc.setEnvelope(env);

				
				mepClient.addMessageContext(mc);
				
				System.out.print("Sending " + String.valueOf(NumberOfAlerts) + " Alert(s) ... ");
			
				
				
				
				mepClient.execute(true);
				
				
				MessageContext response = mepClient.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			  	SOAPBody body = response.getEnvelope().getBody();
			  	OMElement element = body.getFirstElement().getFirstChildWithName(
			  	new QName("http://servicebus","return"));
				System.out.println(element.getText());
				
				if ( element.getText().toLowerCase().equals(AlertPluginFramework.CONST_SUCCESS.toLowerCase()))
					{ 
					AReflectOfAlertComObj.RemoveAlerts(NumberOfAlerts);
					still_sending = false ;
					return ;
					}
				else if( element.getText().toLowerCase().indexOf(AlertPluginFramework.CONST_DUPLICATE.toLowerCase())>-1)
					{
					AReflectOfAlertComObj.dont_Accept_Alerts_With_Timestamp_Less_Than_This(Long.valueOf( element.getText().toLowerCase().substring(element.getText().toLowerCase().indexOf(AlertPluginFramework.CONST_DUPLICATE.toLowerCase())+AlertPluginFramework.CONST_DUPLICATE.length()+1)));
					AReflectOfAlertComObj.RemoveAlertsAll(); 
					AlertPluginFramework.setLastTimeStamp(Long.valueOf( element.getText().toLowerCase().substring(element.getText().toLowerCase().indexOf(AlertPluginFramework.CONST_DUPLICATE.toLowerCase())+AlertPluginFramework.CONST_DUPLICATE.length()+1)));
					still_sending = false ;
					return  ;}
				else if (element.getText().toLowerCase().indexOf(AlertPluginFramework.CONST_ERROR_GET_TIME_AGAIN.toLowerCase())>-1)
					{AReflectOfAlertComObj.dont_Accept_Alerts_With_Timestamp_Less_Than_This(Long.valueOf( element.getText().toLowerCase().substring(element.getText().toLowerCase().indexOf(AlertPluginFramework.CONST_ERROR_GET_TIME_AGAIN.toLowerCase())+AlertPluginFramework.CONST_ERROR_GET_TIME_AGAIN.length()+1)));
					AReflectOfAlertComObj.RemoveAlertsAll(); 
					AlertPluginFramework.setLastTimeStamp(Long.valueOf( element.getText().toLowerCase().substring(element.getText().toLowerCase().indexOf(AlertPluginFramework.CONST_ERROR_GET_TIME_AGAIN.toLowerCase())+AlertPluginFramework.CONST_ERROR_GET_TIME_AGAIN.length()+1)));
					still_sending = false ;
					return  ;}
						
				
				 } catch (Exception e) {
						// TODO Auto-generated catch block
					//	e.printStackTrace();
					 System.out.println(AlertPluginFramework.CONST_ERROR_SENDING_ALERTS);
						still_sending = false ;
					}
				 
				 still_sending = false ;
	}
	
	public synchronized static void Transmit_UsingDataBase ()
	{
		 try {
			 
			// AlertPluginFramework.totalalertcountSent_temp +=NumberOfAlerts;
				System.out.println("total alerts = "+ AlertPluginFramework.totalalertcount_temp  );
			
				
			 if (still_sending)
			 {
				 System.out.println("Still sending previous package ... Mission Cancelled");
				 return ;
			 }
			 
			 
			 
		int NumberOfAlerts ; 		
		AlertCom AlertComObj;
		
	    AlertComObj = 	(new AlertAccess()).getAlerts();
	  	 
		  
		
		NumberOfAlerts = AlertComObj.getRowCount();

		
		if (NumberOfAlerts==0)
		{ System.out.println("Sending 0 Alert(s) ... Mission Cancelled");
		   return;
		}
		
	
		
		String hndshake = AlertPluginFramework.handShake();
		
		if ( hndshake.equals(AlertPluginFramework.CONST_WAIT))
		{
			System.out.print("Sending " + String.valueOf(NumberOfAlerts) + " Alert(s) ... ");
			System.out.println("Server is busy ... Mission Cancelled");
			server_was_busy = true;
		  return;
		}
		
//		if (  hndshake.equals(AlertPluginFramework.CONST_LOGGER_ERRORFUL))
//		{
//			System.out.print("Sending " + String.valueOf(NumberOfAlerts) + " Alert(s) ... ");
//			System.out.println("Error has occured in server while storing alert... Mission Cancelled");
//			AReflectOfAlertComObj.RemoveAlertsAll(); 
//			 AlertPluginFramework.setLastTimeStamp(Long.valueOf(-1));
//		  return;
//		}
		
		if (  hndshake.equals(AlertPluginFramework.CONST_SERVERISDOWN))
		{
			System.out.print("Sending " + String.valueOf(NumberOfAlerts) + " Alert(s) ... ");
			System.out.println("Server is down... Mission Cancelled");
		//	AReflectOfAlertComObj.RemoveAlertsAll();  //its not needed next time server starts up, these alerts will be removed because of not getting timestamp error 
		//	AlertPluginFramework.setLastTimeStamp(Long.valueOf(-1));
		  return;
		}
		
		
		still_sending = true ;
		server_was_busy = false;
		

		 Long endTimeUTC; 
		 Long beginTimeUTC;
		 
		 Long Times[] = GetBegin_EndTime(AlertComObj);
		 beginTimeUTC =  Times[0];
		 endTimeUTC = Times[1];
		 
			 
				Options options = new Options();
			
				options.setProperty(Constants.Configuration.ENABLE_SWA,	Constants.VALUE_TRUE);
				options.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
				// Increase the time out when sending large attachments
				options.setTimeOutInMilliSeconds(20000);
				options.setTo(WebServiceEndPoint);
				options.setAction("urn:getGeneralAlerts");
				options.setProperty(HTTPConstants.CHUNKED,false); 

			
				ServiceClient sender = new ServiceClient();
				sender.setOptions(options);
				OperationClient mepClient = sender.createClient(ServiceClient.ANON_OUT_IN_OP);

				MessageContext mc = new MessageContext();
				
				
				DataHandler dataHandler =  makeDH(AlertComObj);
			    
				 
				String attachmentID = mc.addAttachment(dataHandler);

				SOAPFactory fac = OMAbstractFactory.getSOAP12Factory();
				SOAPEnvelope env = fac.getDefaultEnvelope();
				OMNamespace omNs = fac.createOMNamespace("http://servicebus", "swa");
				OMElement uploadAlerts = fac.createOMElement("Alerts", omNs);
				
				OMElement LoggerIdentityEle = fac.createOMElement("LoggerIdentity", omNs);
				LoggerIdentityEle.setText(LoggerID);
				
				OMElement BeginTimeStampEle = fac.createOMElement("BeginTimeStamp", omNs);
				BeginTimeStampEle.setText(String.valueOf(beginTimeUTC));
				
				OMElement EndTimeStampEle = fac.createOMElement("EndTimeStamp", omNs);
				EndTimeStampEle.setText(String.valueOf(endTimeUTC));
				
				
				OMElement idEle = fac.createOMElement("attchmentID", omNs);
				idEle.setText(attachmentID);
				
				uploadAlerts.addChild(LoggerIdentityEle);
				uploadAlerts.addChild(BeginTimeStampEle);
				uploadAlerts.addChild(EndTimeStampEle);
				uploadAlerts.addChild(idEle);
				env.getBody().addChild(uploadAlerts);
				
				mc.setEnvelope(env);

				
				mepClient.addMessageContext(mc);
				
				System.out.print("Sending " + String.valueOf(NumberOfAlerts) + " Alert(s) ... ");
			
				
				
				
				mepClient.execute(true);
				
				
				MessageContext response = mepClient.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			  	SOAPBody body = response.getEnvelope().getBody();
			  	OMElement element = body.getFirstElement().getFirstChildWithName(
			  	new QName("http://servicebus","return"));
				System.out.println(element.getText());
				
				if ( element.getText().toLowerCase().equals(AlertPluginFramework.CONST_SUCCESS.toLowerCase()))
					{ 
					(new AlertAccess()).DeleteAlerts(NumberOfAlerts);
					still_sending = false ;
					return ;
					}
				else if( element.getText().toLowerCase().indexOf(AlertPluginFramework.CONST_DUPLICATE.toLowerCase())>-1)
					{
					
					AlertAccess.dont_Accept_Alerts_With_Timestamp_Less_Than_This(Long.valueOf( element.getText().toLowerCase().substring(element.getText().toLowerCase().indexOf(AlertPluginFramework.CONST_DUPLICATE.toLowerCase())+AlertPluginFramework.CONST_DUPLICATE.length()+1)));
					(new AlertAccess()).DeleteAlertsAll(); 
					AlertPluginFramework.setLastTimeStamp(Long.valueOf( element.getText().toLowerCase().substring(element.getText().toLowerCase().indexOf(AlertPluginFramework.CONST_DUPLICATE.toLowerCase())+AlertPluginFramework.CONST_DUPLICATE.length()+1)));
					still_sending = false ;
					return  ;}
				else if (element.getText().toLowerCase().indexOf(AlertPluginFramework.CONST_ERROR_GET_TIME_AGAIN.toLowerCase())>-1)
					{AlertAccess.dont_Accept_Alerts_With_Timestamp_Less_Than_This(Long.valueOf( element.getText().toLowerCase().substring(element.getText().toLowerCase().indexOf(AlertPluginFramework.CONST_ERROR_GET_TIME_AGAIN.toLowerCase())+AlertPluginFramework.CONST_ERROR_GET_TIME_AGAIN.length()+1)));
					(new AlertAccess()).DeleteAlertsAll(); 
					AlertPluginFramework.setLastTimeStamp(Long.valueOf( element.getText().toLowerCase().substring(element.getText().toLowerCase().indexOf(AlertPluginFramework.CONST_ERROR_GET_TIME_AGAIN.toLowerCase())+AlertPluginFramework.CONST_ERROR_GET_TIME_AGAIN.length()+1)));
					still_sending = false ;
					return  ;}
						
				
				 } catch (Exception e) {
						// TODO Auto-generated catch block
					//	e.printStackTrace();
					 System.out.println(AlertPluginFramework.CONST_ERROR_SENDING_ALERTS);
						still_sending = false ;
					}
				 
				 still_sending = false ;
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
	  catch (Exception ex) {
	    //TODO: Handle the exception
		  ex.printStackTrace();
	  }
	  return bytes;
	}
	
	public static Long[] GetBegin_EndTime (AlertCom AlertComObj) throws IOException
	{
		 Long Times[] = new Long[2];
		 Times[0] = (long) 0;
		 Times[1] = (long) 0;
		 
		 if (AlertComObj.getRowCount()>0)
		 {
			 Times[0] = Long.valueOf( AlertComObj.getValueAt(0, AlertCom.FLD_TIMESTAMP).toString());
		
		 }
		 		
		 else 
			 return Times;
		 
		  for (int i=0; i<AlertComObj.getRowCount(); i++)
		  {
			  if (Times[0] > Long.valueOf( AlertComObj.getValueAt(i, AlertCom.FLD_TIMESTAMP).toString()))
				  Times[0] = Long.valueOf( AlertComObj.getValueAt(i, AlertCom.FLD_TIMESTAMP).toString());
			  
			  if (Times[1] < Long.valueOf( AlertComObj.getValueAt(i, AlertCom.FLD_TIMESTAMP).toString()))
				  Times[1] = Long.valueOf( AlertComObj.getValueAt(i, AlertCom.FLD_TIMESTAMP).toString());
					  
			  
		  }
		  
		  return Times;
		  
	}
	
}
