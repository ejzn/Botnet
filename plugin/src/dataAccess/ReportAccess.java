package dataAccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import common.AlertCom;

/**
 * 
 * @author Andrew Merrithew
 *
 */

public class ReportAccess {
	
	static Connection conn ;
	
	
	
	public ReportAccess(){
	
	}
	
	protected void finalize() throws Throwable
	{
	  conn.close();
	  super.finalize();
	} 
	/**
	 * 
	 * @return
	 */
	
	public String[] getTopIPArray()
	{		
		boolean check = false;
			CallableStatement stat;
			try {
				stat = conn.prepareCall("{call sp_GetDistinctIPsFromHost()}");
			
			ResultSet firstResult = stat.executeQuery();
			

			
			firstResult.last();
			int c = firstResult.getRow();
			
			
			if(firstResult.getRow() == 1){
				String[] ipArray = new String[1];
				ipArray[0] = "no ip";
				String[] rankArray = new String[1];
				ipArray[0] = "0";
							
				for(int j = 0; j < rankArray.length; j++){
					ipArray[(ipArray.length / 2) + j] = rankArray[j];
				}
				
			return ipArray;
				
			}
			else	{
			String[] ipArray = new String[2 * (firstResult.getRow())];
			String[] rankArray = new String[firstResult.getRow()];
			
			firstResult.first();
			for(int i = 0; ! firstResult.isAfterLast(); i++){
				


			ipArray[i] = firstResult.getString(1);
			
			stat = conn.prepareCall("{call sp_GetHostCountByIP(?)}");
			String query = new String(ipArray[i]);
			stat.setString(1,query);
			
			ResultSet secondResult = stat.executeQuery();
			
				secondResult.first();
			
			rankArray[i] = Integer.toString(secondResult.getInt(1));
			
			firstResult.next();
			}
			
			for(int j = 0; j < rankArray.length; j++){
				ipArray[(ipArray.length / 2) + j] = rankArray[j];
			}
			
			return ipArray;
			
			}

			} catch (SQLException e) {
				e.printStackTrace();
			}	
		return null;
		
	}


	/**
	 * 
	 * @param dir 
	 * @param limit 
	 * @param start 
	 * @param sort 
	 * @return
	 */
	public static int getAlertCount(){
		int retval = 0;		
		AlertCom AlertComObj = new AlertCom() ;
		try {
			
			Statement s = conn.createStatement();
			System.out.println("Counting Alerts...");
			ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM tbl_alert2;");
			r.next();
			int count = r.getInt("rowcount") ;
			r.close() ;
			System.out.println("Alerts: " + count);
			
			retval = count;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}	
		return retval;
	}	
	
	/**
	 * 
	 * @param dir 
	 * @param limit 
	 * @param start 
	 * @param sort 
	 * @return
	 */
	public AlertCom getSnortEventAlertCom(String sort, String start, String limit, String dir){
		
		System.out.println("PAGING2[Sort by:"+sort+", Start:"+start+", Limit:"+limit+", Dir:"+dir+"]");
		
		AlertCom AlertComObj = new AlertCom() ;
		try {
			
			Statement stat = conn.createStatement();
			String query = "SELECT tbl_alert2.id, tbl_alert2.alert_device_type, tbl_alert2.alert_time," +
					"tbl_alert2.alert_source_ip, tbl_alert2.alert_source_mac,tbl_alert2.alert_source_ip_pre_nat," +
					"tbl_alert2.alert_source_ip_post_nat, tbl_alert2.alert_source_port, tbl_alert2.alert_source_port_pre_nat," +
					"tbl_alert2.alert_source_port_post_nat, tbl_alert2.alert_destination_ip, tbl_alert2.alert_destination_mac," +
					"tbl_alert2.alert_destination_ip_pre_nat, tbl_alert2.alert_destination_ip_post_nat, tbl_alert2.alert_destination_port," +
					"tbl_alert2.alert_destination_port_pre_nat, tbl_alert2.alert_destination_port_post_nat, tbl_alert2.alert_description," +
					"tbl_alert2.alert_event_name, tbl_alert2.alert_event_category, tbl_alert2.alert_priority, tbl_alert2.alert_domain_name," +
					"tbl_alert2.alert_group_name, tbl_alert2.alert_net_bios_name, tbl_alert2.alert_flow_id_frn, " +
					"tbl_reference.reference_tag, tbl_reference.reference_system, tbl_reference.reference_meaning, tbl_reference.reference_url" +
					" FROM " +
					"tbl_alert2 left outer join tbl_reference on tbl_alert2.reference_id_frn = tbl_reference.reference_id" +
					" order by "+ sort +" "+ dir +
					" LIMIT " + start +","+ limit +
					";";
			
			System.out.println("Query:");
			System.out.println(query);
			ResultSet result = stat.executeQuery(query);
			
			AlertComObj.fillDataTable(result);
		
			
		}catch (SQLException e) {
			e.printStackTrace();
		}	
		return AlertComObj;
	}
/**
 * 
 * @return
 */
	public String[] getTopProtocolArray()
	{		
		boolean check = false;
			CallableStatement stat;
			try {
				stat = conn.prepareCall("{call sp_GetDistinctProtocolFromService()}");
			
			ResultSet firstResult = stat.executeQuery();
			
			firstResult.last();
			String[] ipArray = new String[2 * (firstResult.getRow())];
			String[] rankArray = new String[firstResult.getRow()];
			
			firstResult.first();
			for(int i = 0; ! firstResult.isAfterLast(); i++){
				
				
				
			ipArray[i] = firstResult.getString(1);
			
			stat = conn.prepareCall("{call sp_GetServiceCountByProtocol(?)}");
			String query = new String(ipArray[i]);
			stat.setString(1,query);
			
			ResultSet secondResult = stat.executeQuery();
			
				secondResult.first();
			
			rankArray[i] = Integer.toString(secondResult.getInt(1));
			
			firstResult.next();
			
			}
			
			for(int j = 0; j < rankArray.length; j++){
				ipArray[(ipArray.length / 2) + j] = rankArray[j];
			}
			
			return ipArray;
			
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		return null;
		
	}
	/**
	 * 
	 * @return
	 */
	public String[] getGeoArray()
	{		
		boolean check = false;
			CallableStatement stat;
			try {
				stat = conn.prepareCall("{call sp_GetGeoFromHost()}");
			
			ResultSet firstResult = stat.executeQuery();
			
			firstResult.last();
			Integer[] codeArray = new Integer[firstResult.getRow()];
			String[] geoArray = new String[firstResult.getRow()];
			
			firstResult.first();

			for(int i = 0; ! firstResult.isAfterLast(); i++){
				
				
				
				codeArray[i] = firstResult.getInt(1);
			
			stat = conn.prepareCall("{call sp_GetGeographyByGeoID(?)}");
			int query = codeArray[i];
			stat.setInt(1,query);
			
			ResultSet secondResult = stat.executeQuery();
			
				secondResult.first();
			
			geoArray[i] = (secondResult.getString(1));
			
			firstResult.next();
			
			}
			
			return geoArray; 
			
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		return null;
		
	}

}
