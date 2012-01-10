package dataAccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.security.auth.login.Configuration;

import servicebus.AlertPluginFramework;

import com.mysql.jdbc.ResultSetMetaData;

import common.AlertCom;
import common.AlertComRow;
import common.SnortConfig;
import common.SnortEventCom;
import configuration.LoggerConfiguration;

/**
 * 
 * @author Andrew Merrithew
 *
 */
public class AlertAccess {
	
	Connection conn ;
	public static Long not_less_than_this_Time = Long.valueOf("0") ;
	private String tblName = "tblbuffer";
	
	private void initializeConnection(){
		
		
		try {
			
			LoggerConfiguration configObj = new LoggerConfiguration();
		
		String databaseUrl = configObj.dataBaseURL ;
		 
		Class.forName (configObj.dataBaseDriver).newInstance ();				
		 conn = DriverManager.getConnection (databaseUrl, configObj.dataBaseUsername, configObj.dataBasePassword);
		
		}catch (SQLException e) { e.getCause();	}
		catch (InstantiationException e) {}			
		catch (IllegalAccessException e) {} 
		catch (ClassNotFoundException e) {}	
		
	}
	
	public AlertAccess(){
		initializeConnection();
		
	}
	
	
	protected void finalize() throws Throwable
	{
		 if (conn != null) 
		        try {conn.close();} catch (SQLException e) {}
	  super.finalize();
	} 

	  private static final SimpleDateFormat monthDayYearformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timestampToMonthDayYear(Timestamp timestamp) {
		    if (timestamp == null) {
		      return null;
		    } else {
		      return monthDayYearformatter.format((java.util.Date) timestamp);
		    }
		  }
	
	public static void dont_Accept_Alerts_With_Timestamp_Less_Than_This(Long timestamp)
	{
		not_less_than_this_Time = timestamp;
	}

	/**
	 * 
	 * @param inputComObj
	 * @return
	 */


	
	public AlertCom alertInsert(AlertComRow alertComRowObj )//String p_timeStamp, int p_logger_id, String p_hostname, String p_interface, String p_filter, String p_encoding_text, String p_detail_text, String p_sig_name, long p_sig_priority, long p_sig_rev, String p_sig_class_name, String p_data_payload, long p_opt_proto, long p_opt_code, long p_opt_len, String p_opt_data, long p_tcp_sport, long p_tcp_dport, long p_tcp_seq, long p_tcp_ack, long p_tcp_off, long p_tcp_res, long p_tcp_flags, long p_tcp_win, long p_tcp_csum, long p_tcp_urp, long p_icmp_type, long p_icmp_code, long p_icmp_csum, long p_icmp_seq, long p_ip_src, long p_ip_dst, long p_ip_ver, long p_ip_hlen, long p_ip_tos, long p_ip_len, long p_ip_id, long p_ip_flags, long p_ip_off, long p_ip_ttl, long p_ip_proto, long p_ip_csum, long p_udp_sport, long p_udp_dport, long p_udp_len, long p_udp_csum)
	{
		AlertCom eventComObj = new AlertCom();

		int i = 0 ;
		try {
			
			if (Long.valueOf(alertComRowObj.getValueAt(AlertCom.FLD_TIMESTAMP).toString()) <= not_less_than_this_Time)
				return eventComObj;
			
			 if (AlertPluginFramework.CurrentLastTimeStamp() < Long.valueOf( alertComRowObj.getValueAt( AlertCom.FLD_TIMESTAMP).toString()))
				 AlertPluginFramework.setLastTimeStamp(Long.valueOf( alertComRowObj.getValueAt( AlertCom.FLD_TIMESTAMP).toString()));
			
			
			if (conn == null ) 
			{
			eventComObj.setError("There is an error while connecting to Database");
			return eventComObj;
			}
			
		//	CallableStatement stat = conn.prepareCall("{call sp_InsertAlertFromPlugin(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			String query = "INSERT INTO "+tblName+" ( " ; 
							
			
			for( i = 0; i < alertComRowObj.getColumnCount(); i++){
				
				query+= "col"+(i+1)+" ,";
				}
			query = query.substring(0, query.length()-1) + ") Values ( ";
			
			for( i = 0; i < alertComRowObj.getColumnCount(); i++){
				
				query+= "'"+ alertComRowObj.getValueAt(i).toString() +"'" + " ,";
					
			
			}
			
			query = query.substring(0, query.length()-1);
			query += ")";
	       Statement stmt = conn.createStatement();
			
		    stmt.executeUpdate (query);
			
			
			
			
		}catch (Exception e) {
		//e.printStackTrace();
		System.out.println("Error in inserting alerts" + e.getMessage());
		eventComObj.setError(String.valueOf(i-1) + ":" + e.getMessage());
		
		}	
		 finally {
		      if (conn != null) 
		        try {conn.close();} catch (SQLException e) {}
		      }
		return eventComObj;
	}

	
	/**
	 * 
	 * @param beginTimeStamp
	 * @param endTimeStamp
	 * @return
	 */
	public AlertCom getAlerts(){
		
		AlertCom alertComObj = new AlertCom() ;
		try {
			
		
			
		   String query = "select  " ; 
	       for( int i = 0; i < alertComObj.getColumnCount(); i++){
				
				query+= "col"+(i+1)+" ,";
					}
	       query = query.substring(0, query.length()-1);
	       query+= "from "+tblName+" order by id asc limit 0,"+ AlertPluginFramework.THRESHOLD_FOR_CLIENT_TO_SEND ;
	       
			
			
	       Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			alertComObj.fillDataTable_Blindly(result);
			//eventComObj.setLoggerId("1");
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}	
		 finally {
			   if (conn != null) 
			        try {conn.close();} catch (SQLException e) {}
			      }
		return alertComObj;
	}
	
public void DeleteAlerts(int count){
		
		AlertCom alertComObj = new AlertCom() ;
		try {
			
		int id = 0 ;
			
		   String query = "select id from "+tblName+" order by id asc limit "+(count-1)+",1";
		   Statement stmt = conn.createStatement();
		   ResultSet result = stmt.executeQuery(query);
	       
	     	
			if  (result.next())  
				id = Integer.valueOf(String.valueOf(result.getObject(1)));
			
			 
		   query = "delete from "+tblName+" where id <= "+id  ;
	      
		   stmt.executeUpdate(query);
			
		
			//eventComObj.setLoggerId("1");
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}	
		 finally {
			   if (conn != null) 
			        try {conn.close();} catch (SQLException e) {}
			      }
		
	}

public  void DeleteAlertsAll(){
	
	
	try {
		
	
		
	   String query = "delete from "+tblName+""  ;
      
       
		
       Statement stmt = conn.createStatement();
	   stmt.executeUpdate(query);
		
	
		//eventComObj.setLoggerId("1");
		
		
	}catch (SQLException e) {
		e.printStackTrace();
	}	
	 finally {
		   if (conn != null) 
		        try {conn.close();} catch (SQLException e) {}
		      }
	
}

public  long  getLastTimeStamp(String timeStampColNameInDB){
	
	
	long returnValue = Long.valueOf(-1);
	try {
		
	
		
	   String query = "select "+timeStampColNameInDB+" from "+tblName+" order by "+timeStampColNameInDB+" desc limit 0,1"  ;
      
       
		
       Statement stmt = conn.createStatement();
       ResultSet result = stmt.executeQuery(query);
       
     	
		if  (result.next())  
			returnValue = Long.valueOf(String.valueOf(result.getObject(1)));
		
		
				
		
	}catch (SQLException e) {
		e.printStackTrace();
	}	
	 finally {
		   if (conn != null) 
		        try {conn.close();} catch (SQLException e) {}
		      }
	 return returnValue;	
	
}

}