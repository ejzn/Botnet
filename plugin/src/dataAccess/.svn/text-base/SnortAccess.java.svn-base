package dataAccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import org.joda.time.DateTime;

import servicebus.AlertPluginFramework;

import common.PluginUtilCom;
import common.SnortEventCom;
import common.SnortConfig;
import configuration.LoggerConfiguration;

public class SnortAccess {
	
	Connection conn ;
	
	private void initializeConnection(){
		
		
		try {
			
			SnortConfig configObj = new SnortConfig();
		
		String databaseUrl = configObj.dataBaseURL ;
		
		Class.forName (configObj.dataBaseDriver).newInstance ();				
		 conn = DriverManager.getConnection (databaseUrl, configObj.dataBaseUsername, configObj.dataBasePassword);
		
		}catch (SQLException e) { e.getCause();	}
		catch (InstantiationException e) {}			
		catch (IllegalAccessException e) {} 
		catch (ClassNotFoundException e) {}	
		
	}
	
	public SnortAccess(){
		initializeConnection();
	}
	
	protected void finalize() throws Throwable
	{
		 if (conn != null) 
		        try {conn.close();} catch (SQLException e) {}
	  super.finalize();
	} 
	
	public SnortEventCom getSnortEvent(long beginTimeStamp, long endTimeStamp){
		
		SnortEventCom eventComObj = new SnortEventCom() ;
		
		
		try {
			
			if (conn == null ) 
			{
			eventComObj.setError("There is an error while connecting to Database");
			return eventComObj;
			}
			
			String query = "" ;
            query += "  SELECT ";// CONVERT (REPLACE( REPLACE( REPLACE(event.timestamp,'-',''),' ','') , ':','') , UNSIGNED)  TimeVal , event.timestamp , ";
            query += "  event.timestamp , sensor.hostname, sensor.interface, sensor.filter,";
            query += "  encoding.encoding_text,";
            query += "   detail.detail_text ,";
            query += "  signature.sig_name, signature.sig_priority, sig_rev ,";
            query += "  sig_class.sig_class_name,";
            //-- reference.ref_tag,
            //-- reference_system.ref_system_name,
            query += "  data.data_payload,";
          //  query += "   opt.opt_proto, opt.opt_code, opt.opt_len, opt.opt_data,";
            query += "  tcphdr.tcp_sport, tcphdr.tcp_dport, tcphdr.tcp_seq, tcphdr.tcp_ack, tcphdr.tcp_off, tcphdr.tcp_res, tcphdr.tcp_flags, tcphdr.tcp_win, tcphdr.tcp_csum, tcphdr.tcp_urp,";
            query += "  icmphdr.icmp_type, icmphdr.icmp_code, icmphdr.icmp_csum, icmphdr.icmp_id, icmphdr.icmp_seq,";
            query += "  iphdr.ip_src, iphdr.ip_dst, iphdr.ip_ver, iphdr.ip_hlen, iphdr.ip_tos, iphdr.ip_len, iphdr.ip_id, iphdr.ip_flags, iphdr.ip_off, iphdr.ip_ttl, iphdr.ip_proto, iphdr.ip_csum,";
            query += "  udphdr.udp_sport, udphdr.udp_dport, udphdr.udp_len, udphdr.udp_csum, sensor.hostname \"dummy\" ";
            
            query += "  FROM";
            query += "   event LEFT OUTER JOIN  sensor ON event.sid = sensor.sid ";
            query += "  LEFT OUTER JOIN encoding ON encoding.encoding_type = sensor.encoding";
            query += "  LEFT OUTER JOIN detail ON detail.detail_type = sensor.detail ";
            query += "  LEFT OUTER JOIN signature ON  signature.sig_id = event.signature ";
            query += "  LEFT OUTER JOIN sig_class ON  sig_class.sig_class_id = signature.sig_class_id"; 
          // -- left outer JOIN sig_reference ON sig_reference.sig_id = signature.sig_id
          // -- INNER JOIN reference ON reference.ref_id = sig_reference.ref_id 
           //-- INNER JOIN reference_system ON reference_system.ref_system_id = reference.ref_system_id 
            query += "   LEFT OUTER JOIN data ON (data.cid = event.cid AND data.sid = event.sid)";
            query += "   LEFT OUTER JOIN udphdr ON (udphdr.cid = event.cid AND udphdr.sid = event.sid )";
            query += "   LEFT OUTER JOIN tcphdr ON (tcphdr.cid = event.cid AND tcphdr.sid = event.sid) ";
            query += "   LEFT OUTER JOIN icmphdr ON (icmphdr.cid = event.cid AND icmphdr.sid = event.sid )";
           // query += "   LEFT OUTER JOIN opt ON (opt.cid = event.cid AND opt.sid = event.sid)";
            query += "   LEFT OUTER JOIN iphdr ON (iphdr.cid = event.cid AND iphdr.sid = event.sid)";
            
            query += "   WHERE   (REPLACE( REPLACE( REPLACE(event.timestamp,'-',''),' ','') , ':','')  >= '" ;
            query += String.valueOf(beginTimeStamp ) + "')";;
            query +="  and (REPLACE( REPLACE( REPLACE(event.timestamp,'-',''),' ','') , ':','')  <= '";
            query += String.valueOf(endTimeStamp ) + "')";
            
            query += "    ORDER BY event.timestamp ASC limit 0,"+ AlertPluginFramework.THRESHOLD_FOR_CLIENT_TO_SEND;

				

			
			
			
			Statement stmt = conn.createStatement();
			
			ResultSet result1 =	stmt.executeQuery(query);
			
			
//			CallableStatement stat1 = conn.prepareCall("{call sp_getSnortAlerts(?,?)}");
//			stat1.setLong(1, beginTimeStamp);
//			stat1.setLong(2, endTimeStamp);
//			ResultSet result1 = stat1.executeQuery();
			
			eventComObj.fillDataTable(result1);
			
			
			
			String Logger_id = (new LoggerConfiguration()).LoggerIdentity;
			for(int i = 0; i < eventComObj.getRowCount(); i++){
				
				eventComObj.setValueAt(Logger_id,i,SnortEventCom.fld_logger_id);
			//	String currT = String.valueOf( eventComObj.getDataTable().getValueAt(i, 0));
			//	String t = PluginUtilCom.ConvertTimeZoneToUTC(currT);
				eventComObj.getDataTable().setValueAt(PluginUtilCom.ConvertTimeZoneToUTC("yyyy-MM-dd HH:mm:ss", String.valueOf( eventComObj.getValueAt(i, 1))),i,SnortEventCom.fld_timeStamp);
			//	Long curr2t = (new DateTime()).withMillis(PluginUtilCom.ConvertTimeZoneToLocal(t)).getMillis();
			}
			
			
			
			//eventComObj.setLoggerId( (new LoggerConfiguration()).LoggerIdentity);
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}	
		 finally {
		      if (conn != null) 
		        try {conn.close();} catch (SQLException e) {}
		      }
		
		return eventComObj;
	}

}
