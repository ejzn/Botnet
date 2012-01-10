package configuration;



	import java.io.File;
import java.io.FileOutputStream;

	import javax.xml.parsers.DocumentBuilder;
	import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

	import org.w3c.dom.Document;
	import org.w3c.dom.Element;
	import org.w3c.dom.Node;
	import org.w3c.dom.NodeList;
	import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

	public class LoggerConfiguration {

		public String period;
		public String LoggerIdentity ;
		public String LastTimeStamp;
		public String TargetEPR;
		public static String configurationFileName = "LoggerConfig.xml";
		
		
		public String dataBaseURL;
		public String dataBaseDriver;
		public String dataBaseUsername;
		public String dataBasePassword;
		public String DataBaseActive = "no";
	
		
		    public LoggerConfiguration( ){
		    
		    	try {
		    	

		            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		            Document doc = docBuilder.parse (new File(configurationFileName));


		            doc.getDocumentElement ().normalize ();



		            NodeList dataBase = doc.getElementsByTagName("Logger");

		            for(int s=0; s<dataBase.getLength() ; s++){


		                Node firstdataBase = dataBase.item(s);
		                if(firstdataBase.getNodeType() == Node.ELEMENT_NODE){


		                    Element firstdataBaseElement = (Element)firstdataBase;

		                    //-------
		                    NodeList periodList = firstdataBaseElement.getElementsByTagName("PeriodBasedOnMilliSeconds");
		                    Element periodElement = (Element)periodList.item(0);

		                    NodeList longperiodlist = periodElement.getChildNodes();
		                    
		                    period = ((Node)longperiodlist.item(0)).getNodeValue();
		                    //-------
		                    NodeList LoggerIDList = firstdataBaseElement.getElementsByTagName("LoggerIdentity");
		                    Element LoggerIDElement = (Element)LoggerIDList.item(0);

		                    NodeList longLoggerIDlist = LoggerIDElement.getChildNodes();
		                    
		                    LoggerIdentity = ((Node)longLoggerIDlist.item(0)).getNodeValue();
		                    
		                    //-------
		                    NodeList LastTimeStampList = firstdataBaseElement.getElementsByTagName("LastTimeStamp");
		                    Element LastTimeStampElement = (Element)LastTimeStampList.item(0);

		                    NodeList longLastTimeStamplist = LastTimeStampElement.getChildNodes();
		                    
		                    LastTimeStamp = ((Node)longLastTimeStamplist.item(0)).getNodeValue();
		                   
		                    //-------
		                    NodeList TargetEPRList = firstdataBaseElement.getElementsByTagName("TargetEPR");
		                    Element TargetEPRElement = (Element)TargetEPRList.item(0);

		                    NodeList longTargetEPRlist = TargetEPRElement.getChildNodes();
		                    
		                    TargetEPR = ((Node)longTargetEPRlist.item(0)).getNodeValue();
		                    
		                }    
		                    

		    	            dataBase = doc.getElementsByTagName("Buffer");

		    	            for( s=0; s<dataBase.getLength() ; s++)
		    	            {


		    	            	 firstdataBase = dataBase.item(s);
		    	                if(firstdataBase.getNodeType() == Node.ELEMENT_NODE){


		    	                	Element firstdataBaseElement = (Element)firstdataBase;

		    	                    //-------
		    	                    NodeList urlList = firstdataBaseElement.getElementsByTagName("url");
		    	                    Element urlElement = (Element)urlList.item(0);

		    	                    NodeList texturllist = urlElement.getChildNodes();
		    	                    
		    	                    dataBaseURL = ((Node)texturllist.item(0)).getNodeValue();

		    	                    //-------
		    	                    NodeList driverList = firstdataBaseElement.getElementsByTagName("driver");
		    	                    Element driverElement = (Element)driverList.item(0);

		    	                    NodeList textdriverlist = driverElement.getChildNodes();
		    	                    
		    	                    dataBaseDriver = ((Node)textdriverlist.item(0)).getNodeValue();

		    	                    //----
		    	                    NodeList usernameList = firstdataBaseElement.getElementsByTagName("username");
		    	                    Element usernameElement = (Element)usernameList.item(0);

		    	                    NodeList textusernamelist = usernameElement.getChildNodes();
		    	                    
		    	                    dataBaseUsername = ((Node)textusernamelist.item(0)).getNodeValue();

		    	                    //------
		    	                    
		    	                    NodeList passwordList = firstdataBaseElement.getElementsByTagName("password");
		    	                    Element passwordElement = (Element)passwordList.item(0);

		    	                    NodeList textpasswordlist = passwordElement.getChildNodes();
		    	                    
		    	                    dataBasePassword = ((Node)textpasswordlist.item(0)).getNodeValue();
		    	                    //------
		    	                    
		    	                    NodeList DataBaseActiveList = firstdataBaseElement.getElementsByTagName("databaseactive");
		    	                    Element DataBaseActiveElement = (Element)DataBaseActiveList.item(0);

		    	                    NodeList textDataBaseActivelist = DataBaseActiveElement.getChildNodes();
		    	                    
		    	                    DataBaseActive = ((Node)textDataBaseActivelist.item(0)).getNodeValue();


		    	                }

				                    
				                    
		                    

		                }


		            }


		        }catch (SAXParseException err) {


		        }catch (SAXException e) {
		        Exception x = e.getException ();
		        ((x == null) ? e : x).printStackTrace ();

		        }catch (Throwable t) {
		        t.printStackTrace ();
		        }
		    }
		
		    public Boolean SaveTimeStamp(String TimeStamp)
		    {
		    	try {
		    	    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		            Document doc = docBuilder.parse (new File(configurationFileName));


		            doc.getDocumentElement ().normalize ();



		            NodeList dataBase = doc.getElementsByTagName("Logger");

		            for(int s=0; s<dataBase.getLength() ; s++){


		                Node firstdataBase = dataBase.item(s);
		                if(firstdataBase.getNodeType() == Node.ELEMENT_NODE){


		                    Element firstdataBaseElement = (Element)firstdataBase;

		                  
		                    //-------
		                    NodeList LastTimeStampList = firstdataBaseElement.getElementsByTagName("LastTimeStamp");
		                    Element LastTimeStampElement = (Element)LastTimeStampList.item(0);

		                    NodeList longLastTimeStamplist = LastTimeStampElement.getChildNodes();
		                    
		                    ((Node)longLastTimeStamplist.item(0)).setNodeValue(TimeStamp);
		                 
		                	}
		                }

		                TransformerFactory tFactory =
		                TransformerFactory.newInstance();
		                Transformer transformer = tFactory.newTransformer();

		              FileOutputStream FOS = new FileOutputStream(new File(configurationFileName));
		             
		              DOMSource source = new DOMSource(doc);
		              StreamResult result = new StreamResult(FOS);
		              transformer.transform(source, result); 
	           FOS.flush();
	           FOS.close();
		              
	        }catch (SAXParseException err) {


	        }catch (SAXException e) {
	        Exception x = e.getException ();
	        ((x == null) ? e : x).printStackTrace ();

	        }catch (Throwable t) {
	        t.printStackTrace ();
	        }
	        
	        
		    	return true ;
		    	
		    }
	

	
}
