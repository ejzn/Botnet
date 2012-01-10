package common;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;

import dataAccess.ReportAccess;

public class AlertCom implements Serializable{
	public final static String FLD_LOGGERIDENTITY = "logger_id_frn"; //MAY BE ISSUES WITH THIS VARIABLE
	public final static String FLD_TIMESTAMP = "alert_time";
	public final static String FLD_SOURCEIP = "alert_source_ip";
	public final static String FLD_SOURCEPORT = "alert_source_port";
	public final static String FLD_SOURCEMAC = "alert_source_mac";
	public final static String FLD_SOURCEIPPRENAT = "alert_source_ip_pre_nat";
	public final static String FLD_SOURCEIPPOSTNAT = "alert_source_ip_post_nat";
	public final static String FLD_SOURCEPORTPRENAT = "alert_source_port_pre_nat";
	public final static String FLD_SOURCEPORTPOSTNAT = "alert_source_port_post_nat";
	public final static String FLD_DESTINATIONIP = "alert_destination_ip";
	public final static String FLD_DESTINATIONPORT = "alert_destination_port";
	public final static String FLD_DESTINATIONMAC = "alert_destination_mac";
	public final static String FLD_DESTINATIONIPPRENAT = "alert_destination_ip_pre_nat";
	public final static String FLD_DESTINATIONIPPOSTNAT = "alert_destination_ip_post_nat";
	public final static String FLD_DESTINATIONPORTPRENAT = "alert_destination_port_pre_nat";
	public final static String FLD_DESTINATIONPORTPOSTNAT = "alert_destination_port_post_nat";
	public final static String FLD_EVENTNAME = "alert_event_name";
	public final static String FLD_EVENTCATEGORY = "alert_event_category";
	public final static String FLD_REFERENCETAG = "reference_tag";
	public final static String FLD_REFERENCESYSTEM = "reference_system";
	public final static String FLD_DESCRIPTION = "alert_description";
	public final static String FLD_PRIORITY = "alert_priority";
	public final static String FLD_DEVICETYPE = "alert_deviceType"; // MAY BE ISSUES WITH THIS VARIABLE
	//public final static String FLD_USERNAME = "userName";
	public final static String FLD_DOMAINNAME = "alert_domain_name";
	public final static String FLD_GROUPNAME = "alert_group_name";
	public final static String FLD_NETBIOSNAME = "alert_net_bios_name";
	
	private ArrayList errorList;
	private DataTable  dataTableObj;
	
	public AlertCom(){
		
		errorList = new ArrayList();
		String header[] = {
			FLD_LOGGERIDENTITY,
/**0**/		FLD_TIMESTAMP,
/**1**/		FLD_SOURCEIP, 
/**2**/		FLD_SOURCEPORT,  
/**3**/		FLD_SOURCEMAC, 
/**4**/		FLD_SOURCEIPPRENAT, 
/**5**/		FLD_SOURCEIPPOSTNAT, 
/**6**/		FLD_SOURCEPORTPRENAT, 
/**7**/		FLD_SOURCEPORTPOSTNAT, 
/**8**/		FLD_DESTINATIONIP, 
/**9**/		FLD_DESTINATIONPORT, 
/**10**/		FLD_DESTINATIONMAC, 
/**11**/		FLD_DESTINATIONIPPRENAT, 
/**12**/		FLD_DESTINATIONIPPOSTNAT, 
/**13**/		FLD_DESTINATIONPORTPRENAT, 
/**14**/		FLD_DESTINATIONPORTPOSTNAT, 
/**15**/		FLD_EVENTNAME, 
/**16**/		FLD_EVENTCATEGORY, 
/**17**/		FLD_REFERENCETAG, 
/**18**/		FLD_REFERENCESYSTEM, 
/**19**/		FLD_DESCRIPTION, 
/**20**/		FLD_PRIORITY, 
/**21**/		FLD_DEVICETYPE, 
/**22**/	//	FLD_USERNAME, 
/**23**/		FLD_DOMAINNAME, 
/**24**/		FLD_GROUPNAME, 
/**25**/		FLD_NETBIOSNAME};
		dataTableObj = new DataTable(0,header);
				
	}
	
	/**
	 * 
	 * @param columnValues An array of the values in each column to append as a row to the object.
	 */
	
	public void appendRow(Object columnValues[])
	{
		dataTableObj.appendRow(columnValues);
		
	}
	
	/**
	 * 
	 * @return The total number of columns.
	 */
	
	public int getColumnCount() {
	    return dataTableObj.getColumnCount();
	  }
	
	/**
	 * 
	 * @return The total number of rows.
	 */

	  public int getRowCount() {
	    return dataTableObj.getRowCount();
	  }

	  /**
	   * 
	   * @return A list containing all rows in the Object.
	   */
	  
	public List<List> getRows(){
		List<List> tempList = new ArrayList<List>();
		for(int i = 0;i < getRowCount(); i++){
			tempList.add(getRow(i));
		}
		return tempList;
	}
	
	/**
	 * 
	 * @param row The index of the row to retrieve.
	 * @return The row that was requested.
	 */
	
		public List  getRow(int row) {
			  

			return dataTableObj.getRow(row);
			  }
		/**
		 * 
		 * @param row The index of the row to retrieve.
		 * @return The row that was requested.
		 */
		
			public AlertComRow  getRowAsAlertRowCom(int row) {
				  
				AlertComRow tmp = new AlertComRow();

				tmp.SetValues(
						 String.valueOf( getValueAt(row,  AlertCom.FLD_LOGGERIDENTITY)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_TIMESTAMP)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_SOURCEIP)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_SOURCEPORT)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_SOURCEMAC)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_SOURCEIPPRENAT)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_SOURCEIPPOSTNAT)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_SOURCEPORTPRENAT)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_SOURCEPORTPOSTNAT)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_DESTINATIONIP)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_DESTINATIONPORT)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_DESTINATIONMAC)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_DESTINATIONIPPRENAT)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_DESTINATIONIPPOSTNAT)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_DESTINATIONPORTPRENAT)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_DESTINATIONPORTPOSTNAT)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_EVENTNAME)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_EVENTCATEGORY)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_REFERENCETAG)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_REFERENCESYSTEM)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_DESCRIPTION)), 
						 String.valueOf( getValueAt(row, AlertCom.FLD_PRIORITY)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_DEVICETYPE)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_DOMAINNAME)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_GROUPNAME)),
						 String.valueOf( getValueAt(row, AlertCom.FLD_NETBIOSNAME)));
				
				return tmp;
				  }
			
			/**
			 * 
			 * @param filter filter to build json from
			 * @return json formated filter
			 */
			public String getJson(String filter){
				
				int count=0;// = (new ReportAccess()).getAlertCount(filter);
				
				String JSON = "{\"totalCount\":"+count+", \"entries\":[";
				
				for (int j = 0; j < this.getRowCount(); j++){
					JSON = JSON + this.getRowAsJson(j)+", ";
				}
				JSON = JSON.substring(0,JSON.length()-2);
				JSON = JSON + "]}";
				
				return JSON;
			}
			
			
			/**
			 * Converts the time from Java timestamp format to human readable format.
			 * @param string the time in Java timestamp format.
			 * @return a string showing time in human readable format ("dd/MM/yyyy HH:mm:ss.SS"). 
			 */
			public String longToDate(String string_in)
			{
				long string = Long.parseLong(string_in);
				Date date = new Date(string);
				String strDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date).toString();
				return strDate;
			}
			
			/**
			 * 
			 * @param IPin Ip to be converted to string from long format
			 * @return ip in string format
			 */
			
			static public String longToString(String IPin) {
				
				long IP = Long.parseLong(IPin);
				
				long oct1;
				long oct2;
				long oct3;
				long oct4;
				oct1 = integerDivision(IP, 16777216);
				if (oct1 !=0)
					IP = IP % (oct1*16777216);
				oct2 = integerDivision(IP, 65536);
				if (oct2 !=0)
					IP = IP % (oct2*65536);
				oct3 = integerDivision(IP, 256);
				if (oct3 !=0)
					IP = IP % (oct3*256);
				oct4 = IP;
				
				String str = Long.toString(oct1)+"."+Long.toString(oct2)+"."+Long.toString(oct3)+"."+Long.toString(oct4);
				return str;
			}	
			
			static private long integerDivision(long a, long b) {
				
				long i;
				for (i=1; ; i++) {
					if (a < (b*i)) {
						i--;
						break;
					}
				}
				return i;
			}
			
			/**
			 * 
			 * @param row row index to be returned in json format. table is processed row by row
			 * @return json string 
			 */
			public String  getRowAsJson(int row) {
				  
				AlertComRow tmp = new AlertComRow();
				
				String json = "{\"logger_id_frn\":\""+ String.valueOf(getValueAt(row, AlertCom.FLD_LOGGERIDENTITY))+"\"," +
						"\"alert_time\":\""+ longToDate(String.valueOf(getValueAt(row, AlertCom.FLD_TIMESTAMP)))+"\", " +
						"\"alert_source_ip\":\""+ longToString(String.valueOf(getValueAt(row, AlertCom.FLD_SOURCEIP)))+"\", " +
						"\"alert_source_port\":\""+ String.valueOf(getValueAt(row, AlertCom.FLD_SOURCEPORT))+"\", " +
						"\"alert_source_mac\":\""+ String.valueOf(getValueAt(row, AlertCom.FLD_SOURCEMAC))+"\", " +
						"\"alert_source_ip_pre_nat\":\""+longToString(String.valueOf(getValueAt(row, AlertCom.FLD_SOURCEIPPRENAT)))+"\", " +
						"\"alert_source_ip_post_nat\":\""+longToString(String.valueOf( getValueAt(row, AlertCom.FLD_SOURCEIPPOSTNAT)))+"\", " +
						"\"alert_source_port_pre_nat\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_SOURCEPORTPRENAT))+"\", " +
						"\"alert_source_port_post_nat\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_SOURCEPORTPOSTNAT))+"\", " +
						"\"alert_destination_ip\":\""+longToString(String.valueOf( getValueAt(row, AlertCom.FLD_DESTINATIONIP)))+"\", " +
						"\"alert_destination_port\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_DESTINATIONPORT))+"\", " +
						"\"alert_destination_mac\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_DESTINATIONMAC))+"\", " +
						"\"alert_destination_ip_pre_nat\":\""+longToString(String.valueOf( getValueAt(row, AlertCom.FLD_DESTINATIONIPPRENAT)))+"\", " +
						"\"alert_destination_ip_post_nat\":\""+longToString(String.valueOf( getValueAt(row, AlertCom.FLD_DESTINATIONIPPOSTNAT)))+"\", " +
						"\"alert_destination_port_pre_nat\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_DESTINATIONPORTPRENAT))+"\", " +
						"\"alert_destination_port_post_nat\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_DESTINATIONPORTPOSTNAT))+"\", " +
						"\"alert_event_name\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_EVENTNAME))+"\", " +
						"\"alert_event_category\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_EVENTCATEGORY))+"\", " +
						"\"alert_reference_tag\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_REFERENCETAG))+"\", " +
						"\"alert_reference_system\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_REFERENCESYSTEM))+"\", " +
						"\"alert_description\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_DESCRIPTION))+"\", " +
						"\"alert_priority\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_PRIORITY))+"\", " +
						"\"alert_device_type\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_DEVICETYPE))+"\", " +
						"\"alert_domain_name\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_DOMAINNAME))+"\", " +
						"\"alert_group_name\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_GROUPNAME))+"\", " +
						"\"alert_net_bios_name\":\""+String.valueOf( getValueAt(row, AlertCom.FLD_NETBIOSNAME))+"\"}";
					
				return json;
				  }

		/**
		 * 
		 * @param rs The resultset to input into the object.
		 */

public void fillDataTable(ResultSet rs) {
		
	try { 
		
		dataTableObj.fillDataTable(rs);

		} catch (Exception e) {  
			System.out.print(e.toString());
		} 
	
		
	}
public void fillDataTable_Blindly(ResultSet rs) {
	
	try { 
		
		dataTableObj.fillDataTable_Blindly(rs);

		} catch (Exception e) {  
			System.out.print(e.toString());
		} 
	
		
	}
/**
 * 
 * @return The datatable within the object.
 */
	public DataTable getDataTable() {
		return dataTableObj;
	}
	/**
	 * 
	 * @param error The error to add to the error list.
	 */
	public void setError( String error){
		
		errorList.add(error);
		
	}
	/**
	 * 
	 * @param errorIndex The index of the error to return.
	 * @return The error that was requested.
	 */
	public String getError( int errorIndex){
		
		return (String) errorList.get(errorIndex);
		
	}
	/**
	 * 
	 * @return The total number of errors associated with the object.
	 */
	public int errorCount(){
		return errorList.size();
	}
	/**
	 * 
	 * @param column the index of the column to retrieve the name from.
	 * @return The name of the column requested.
	 */
	public String getColumnName(int column) {
	    return dataTableObj.getColumnName(column);
	  }
	/**
	 * 
	 * @param row The index of the row to retrieve the value from.
	 * @param column The index of the column to retrieve the value from.
	 * @return The value requested.
	 */
	  public Object getValueAt(int row, int column) {
		  return dataTableObj.getValueAt(row, column);
	  }
	  /**
	   * 
	   * @param row The index of the row to retrieve the value from.
	   * @param column The name of the column to retrieve the value from.
	   * @return The value requested.
	   */
	  public Object getValueAt(int row, String column) {
		  return dataTableObj.getValueAt(row, column);
		  }
		 
	  /**
	   * 
	   * @param value The object to input into the datatable.
	   * @param row The index of the row to insert into.
	   * @param column The index of the column to insert into.
	   */
	  public void setValueAt(Object value, int row, int column) {
		  dataTableObj.setValueAt( value,  row,  column);
	  }
	  /**
	   * 
	   * @param ColName The name of the column to retrieve the index from.
	   * @return The index of the column requested.
	   */
	  private int GetIndexByName(String ColName)
	  {
		 return dataTableObj.GetIndexByName(ColName);
		  
	  }
	  /**
	   * 
	   * @param value The object to input into the datatable.
	   * @param row The index of the row to insert into.
	   * @param column The index of the column to insert into.
	   */
	  public void setValueAt(Object value, int row, String column) {
		  dataTableObj.setValueAt( value,  row,  column);
		  }
	  public void sort_Ascending_BasedOn(String ColName) {
		 dataTableObj.sort_Ascending_BasedOn(ColName);
		  
		  }
	  
	  public void ConvertToHumanReadableValues()
	  {
		  for(int row =0 ; row< getRowCount(); row++)
		  {
			  
			  setValueAt(longToDate(getValueAt(row, AlertCom.FLD_TIMESTAMP).toString()), row, AlertCom.FLD_TIMESTAMP);
			  setValueAt(longToString(getValueAt(row, AlertCom.FLD_SOURCEIP).toString()), row, AlertCom.FLD_SOURCEIP);
			  setValueAt(longToString(getValueAt(row, AlertCom.FLD_SOURCEIPPRENAT).toString()), row, AlertCom.FLD_SOURCEIPPRENAT);
			  setValueAt(longToString(getValueAt(row, AlertCom.FLD_SOURCEIPPOSTNAT).toString()), row, AlertCom.FLD_SOURCEIPPOSTNAT);
			  
			  setValueAt(longToString(getValueAt(row, AlertCom.FLD_DESTINATIONIP).toString()), row, AlertCom.FLD_DESTINATIONIP);
			  setValueAt(longToString(getValueAt(row, AlertCom.FLD_DESTINATIONIPPRENAT).toString()), row, AlertCom.FLD_DESTINATIONIPPRENAT);
			  setValueAt(longToString(getValueAt(row, AlertCom.FLD_DESTINATIONIPPOSTNAT).toString()), row, AlertCom.FLD_DESTINATIONIPPOSTNAT);
			  
			 
		  }
		  
	  }
	  
}
