package common;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SnortConfig {

	public String dataBaseURL;
	public String dataBaseDriver;
	public String dataBaseUsername;
	public String dataBasePassword;
	public static String configurationFileName = "snortConfig.xml";
	
	    public SnortConfig(){
	    
	    	try {

	            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	            Document doc = docBuilder.parse (new File(configurationFileName));


	            doc.getDocumentElement ().normalize ();



	            NodeList dataBase = doc.getElementsByTagName("database");

	            for(int s=0; s<dataBase.getLength() ; s++){


	                Node firstdataBase = dataBase.item(s);
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
	
}
