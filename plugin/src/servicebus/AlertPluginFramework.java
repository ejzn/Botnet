package servicebus;

import java.util.Timer;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAP12Constants;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.OperationClient;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.wsdl.WSDLConstants;



import common.AlertComRow;
import configuration.LoggerConfiguration;
import dataAccess.AlertAccess;

public class AlertPluginFramework {

	public static boolean UsingDataBase = false ;
	public static boolean HadContactedWithServerAtLeastOnce = false ;
	public static String timeStampColNameInDB = "col1";
	private AlertWrapper AlertWrapperObj ;
	private static Long LastTimeStamp = Long.valueOf("-1");
	static String WSEndPoint;
	static String LoggerID ;
	Timer timer ;
	public static  String CONST_SEND = "send" ;
	public static  String CONST_WAIT = "wait" ;
	public static  String CONST_LOGGER_ERRORFUL = "Logger is Errorful" ;
	public static  String CONST_SERVERISDOWN = "server is down" ;
	public static  String CONST_ERROR_GETTING_TIMESTAMP = "Cannot get last timestamp from the server" ;
	public static  String CONST_ERROR_SENDING_ALERTS = "Alert sending  failed" ;
	
	public static  String CONST_DUPLICATE = "duplicate" ;
	public static  String CONST_SUCCESS = "success" ;
	public static  String CONST_ERROR_GET_TIME_AGAIN = "You must get the last timestamp from server again" ;
	
	public static int THRESHOLD_FOR_CLIENT_TO_SEND =5000;  
	
	// these two varaibles are just for testing the number of alerts we get and the number we send. you can delete 
	// them if you don't need them.
	public static int totalalertcount_temp= 0 ;
	public static int totalalertcountSent_temp= 0 ;

	
	public AlertPluginFramework(String WSEndPoint , String LoggerID , Long TimerIntervalMillis)
	{
		//alertAccessObj= new AlertAccess();
		this.WSEndPoint = WSEndPoint;
		AlertWrapperObj = new AlertWrapper();
		this.LoggerID = LoggerID;
	    timer = new Timer();
	    timer.schedule( new ScheduledTransmitter(AlertWrapperObj,WSEndPoint,LoggerID),TimerIntervalMillis, TimerIntervalMillis);
	   
	}
	
	public AlertPluginFramework(Long TimerIntervalMillis)
	{
		 LoggerConfiguration configObj = new LoggerConfiguration();
		WSEndPoint = configObj.TargetEPR;
		AlertWrapperObj = new AlertWrapper();
		LoggerID = configObj.LoggerIdentity;
		
		if(configObj.DataBaseActive.toLowerCase().equals("yes"))
			{UsingDataBase = true;
			 timeStampColNameInDB="col2";
			
			}
		else 
			UsingDataBase =false ;
		
	    timer = new Timer();
	    timer.schedule( new ScheduledTransmitter(AlertWrapperObj,WSEndPoint,LoggerID),TimerIntervalMillis, TimerIntervalMillis);
	    
	}
	
	public void Feed_Alert(	
			String   LOGGERIDENTITY,
			String   TIMESTAMP,
			  String   SOURCEIP,
			  String   SOURCEPORT,
			  String   SOURCEMAC,
			  String   SOURCEIPPRENAT,
			  String   SOURCEIPPOSTNAT,
			  String   SOURCEPORTPRENAT,
			  String   SOURCEPORTPOSTNAT,
			  String   DESTINATIONIP,
			  String   DESTINATIONPORT,
			  String   DESTINATIONMAC,
			  String   DESTINATIONIPPRENAT,
			  String   DESTINATIONIPPOSTNAT,
			  String   DESTINATIONPORTPRENAT,
			  String   DESTINATIONPORTPOSTNAT,
			  String   EVENTNAME,
			  String   EVENTCATEGORY,
			  String   REFERENCETAG,
			  String   REFERENCESYSTEM,
			  String   DESCRIPTION,
			  String   PRIORITY,
			  String   DEVICETYPE,
			  String   DOMAINNAME,
			  String   GROUPNAME,
			  String   NETBIOSNAME )
	{
		totalalertcount_temp++;
		AlertComRow alertComRowObj = new AlertComRow();
		
		alertComRowObj.SetValues(LOGGERIDENTITY, TIMESTAMP, SOURCEIP, SOURCEPORT, SOURCEMAC, SOURCEIPPRENAT, SOURCEIPPOSTNAT, SOURCEPORTPRENAT, SOURCEPORTPOSTNAT, DESTINATIONIP, DESTINATIONPORT, DESTINATIONMAC, DESTINATIONIPPRENAT, DESTINATIONIPPOSTNAT, DESTINATIONPORTPRENAT, DESTINATIONPORTPOSTNAT, EVENTNAME, EVENTCATEGORY, REFERENCETAG, REFERENCESYSTEM, DESCRIPTION, PRIORITY, DEVICETYPE, DOMAINNAME, GROUPNAME, NETBIOSNAME);
		
		if (UsingDataBase)
		(new AlertAccess()).alertInsert(alertComRowObj);
		else 
		{
			AlertWrapperObj.Feed_Alert(alertComRowObj);
			if (AlertWrapperObj.getAlertCount()> THRESHOLD_FOR_CLIENT_TO_SEND)
				if (! ScheduledTransmitter.still_sending)
					if (!ScheduledTransmitter.server_was_busy)
						ScheduledTransmitter.Transmit();
		}
	}

	
	public static synchronized void setLastTimeStamp(Long lastTimeStamp) {
		LastTimeStamp = lastTimeStamp;
	}
	
	
	public static synchronized Long CurrentLastTimeStamp() {
	return LastTimeStamp;
	}
	
	
	public synchronized Long getLastTimeStamp() {
		if (! UsingDataBase)
		{	long gTimeStamp = AlertWrapperObj.getGreatestTimeStamp();
			if ( gTimeStamp== -1 )
				{	System.out.print("getting time from server: ");
					GetMyLastTimeStampFromServer();
					AlertWrapperObj.setGreatestTimeStamp(LastTimeStamp);
					AlertWrapperObj.setLeastTimeStamp(LastTimeStamp);
					System.out.println(LastTimeStamp.toString()+"   ");
				}
			else 
				LastTimeStamp = gTimeStamp;
		}
		else 
		{   long gTimeStamp = (new AlertAccess()).getLastTimeStamp(timeStampColNameInDB);
				
					if (gTimeStamp == -1 )
					 {if (LastTimeStamp<=0)
						{	System.out.print("getting time from server: ");
							GetMyLastTimeStampFromServer();
							//AlertWrapperObj.setGreatestTimeStamp(LastTimeStamp);
							//AlertWrapperObj.setLeastTimeStamp(LastTimeStamp);
							System.out.println(LastTimeStamp.toString()+"   ");
						}
					  
						 
					 }
					else 
						LastTimeStamp = gTimeStamp;
			
			
		}
		
		return LastTimeStamp  ;
	}
	
	
	public synchronized void GetMyLastTimeStampFromServer()
	{
		try {
		
			EndpointReference WebServiceEndPoint = new EndpointReference(WSEndPoint);
			Options options = new Options();
		options.setTo(WebServiceEndPoint);
		options.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		options.setTimeOutInMilliSeconds(20000);
		options.setAction("urn:getLastTimeStamp");
		options.setProperty(HTTPConstants.CHUNKED,false); 

	
		ServiceClient sender = new ServiceClient();
		sender.setOptions(options);
		OperationClient mepClient = sender.createClient(ServiceClient.ANON_OUT_IN_OP);

		MessageContext mc = new MessageContext();
		
		SOAPFactory fac = OMAbstractFactory.getSOAP12Factory();
		SOAPEnvelope env = fac.getDefaultEnvelope();
		OMNamespace omNs = fac.createOMNamespace("http://servicebus", "swa");
		OMElement uploadAlerts = fac.createOMElement("Alerts", omNs);
		
		OMElement LoggerIdentityEle = fac.createOMElement("LoggerIdentity", omNs);
		LoggerIdentityEle.setText(LoggerID);
	
		
		uploadAlerts.addChild(LoggerIdentityEle);
		
		env.getBody().addChild(uploadAlerts);
			
			mc.setEnvelope(env);
			
		mepClient.addMessageContext(mc);
		mepClient.execute(true);
		MessageContext response = mepClient.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
	  	SOAPBody body = response.getEnvelope().getBody();
	  	OMElement element = body.getFirstElement().getFirstChildWithName(
	  	new QName("http://servicebus","return"));
	  	LastTimeStamp = Long.valueOf(element.getText());
	  	HadContactedWithServerAtLeastOnce = true ;
	  	
		} catch (AxisFault e) {
				//e.printStackTrace();
				//System.out.println();
				System.out.println(CONST_ERROR_GETTING_TIMESTAMP);
		}
	}
	
	public static synchronized String handShake()
	{
		try {
		
			EndpointReference WebServiceEndPoint = new EndpointReference(WSEndPoint);
			Options options = new Options();
		options.setTo(WebServiceEndPoint);
		options.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		options.setTimeOutInMilliSeconds(20000);
		options.setAction("urn:handShake");
		options.setProperty(HTTPConstants.CHUNKED,false); 

	
		ServiceClient sender = new ServiceClient();
		sender.setOptions(options);
		OperationClient mepClient = sender.createClient(ServiceClient.ANON_OUT_IN_OP);

		MessageContext mc = new MessageContext();
		
		SOAPFactory fac = OMAbstractFactory.getSOAP12Factory();
		SOAPEnvelope env = fac.getDefaultEnvelope();
		OMNamespace omNs = fac.createOMNamespace("http://servicebus", "swa");
		OMElement uploadAlerts = fac.createOMElement("Alerts", omNs);
		
		OMElement LoggerIdentityEle = fac.createOMElement("LoggerIdentity", omNs);
		LoggerIdentityEle.setText(LoggerID);
	
		
		uploadAlerts.addChild(LoggerIdentityEle);
		
		env.getBody().addChild(uploadAlerts);
			
			mc.setEnvelope(env);
			
		mepClient.addMessageContext(mc);
		mepClient.execute(true);
		MessageContext response = mepClient.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
	  	SOAPBody body = response.getEnvelope().getBody();
	  	OMElement element = body.getFirstElement().getFirstChildWithName(
	  	new QName("http://servicebus","return"));
	  	String result = element.getText();
	  	if (result.toLowerCase().equals(CONST_SEND))
	  		return CONST_SEND;//send
	  	else 	if (result.toLowerCase().equals(CONST_LOGGER_ERRORFUL))
	  		return CONST_LOGGER_ERRORFUL; //loggerErrorful
	  	else if  (result.toLowerCase().equals(CONST_WAIT)) 
	  		  return CONST_WAIT ; //wait
			
		} catch (AxisFault e) {
				//e.printStackTrace();
			
		}
		
		return CONST_SERVERISDOWN; //server down
	}
}
