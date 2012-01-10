package common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;



public class SnortEventCom implements Serializable{

	public final static String fld_timeFormat = "timeFormat";
	 public final static String fld_timeStamp = "timestamp";
	 public final static String fld_hostname = "hostname";
	 public final static String fld_interface = "interface";
	 public final static String fld_filter = "filter";
	 public final static String fld_encoding_text = "encoding_text";
	 public final static String fld_detail_text = "detail_text";
	 public final static String fld_sig_name = "sig_name";
	public final static String fld_sig_priority = "sig_priority";
	public final static String fld_sig_rev = "sig_rev";
	public final static String fld_sig_class_name = "sig_class_name";
	public final static String fld_data_payload = "data_payload";
//	public final static String fld_opt_proto = "opt_proto";
//	public final static String fld_opt_code = "opt_code";
//	public final static String fld_opt_len = "opt_len";
//	public final static String fld_opt_data = "opt_data";
	
	public final static String fld_tcp_sport = "tcp_sport";
	public final static String fld_tcp_dport = "tcp_dport";
	public final static String fld_tcp_seq = "tcp_seq";
	public final static String fld_tcp_ack = "tcp_ack";
	public final static String fld_tcp_off = "tcp_off";
	public final static String fld_tcp_res = "tcp_res";
	public final static String fld_tcp_flags = "tcp_flags";
	public final static String fld_tcp_win = "tcp_win";
	public final static String fld_tcp_csum = "tcp_csum";
	public final static String fld_tcp_urp = "tcp_urp";
	
	public final static String fld_icmp_type = "icmp_type";
	public final static String fld_icmp_code = "icmp_code";
	public final static String fld_icmp_csum = "icmp_csum";	
	public final static String fld_icmp_id = "icmp_id";	
	public final static String fld_icmp_seq = "icmp_seq";	
	
	public final static String fld_ip_src = "ip_src";
	public final static String fld_ip_dst = "ip_dst";	
	public final static String fld_ip_ver = "ip_ver";
	public final static String fld_ip_hlen = "ip_hlen";
	public final static String fld_ip_tos = "ip_tos";
	public final static String fld_ip_len = "ip_len";
	public final static String fld_ip_id = "ip_id";
	public final static String fld_ip_flags = "ip_flags";
	public final static String fld_ip_off = "ip_off";
	public final static String fld_ip_ttl = "ip_ttl";
	public final static String fld_ip_proto = "ip_proto";
	public final static String fld_ip_csum = "ip_csum";
	
	public final static String fld_udp_sport = "udp_sport";
	public final static String fld_udp_dport = "udp_dport";
	public final static String fld_udp_len = "udp_len";
	public final static String fld_udp_csum = "udp_csum";
   public  final  static String fld_logger_id = "logger_id";
	
	
	private ArrayList errorList;
	private DataTable  dataTableObj;
	
	public SnortEventCom(){
		
		errorList = new ArrayList();
		String header[] = {
/**0**/		fld_timeFormat,
/**1**/		fld_timeStamp, 
/**2**/		fld_hostname,  
/**3**/		fld_interface, 
/**4**/		fld_filter, 
/**5**/		fld_encoding_text, 
/**6**/		fld_detail_text, 
/**7**/		fld_sig_name, 
/**8**/		fld_sig_priority, 
/**9**/		fld_sig_rev, 
/**10**/		fld_sig_class_name, 
/**11**/		fld_data_payload, 
/**12**/		//fld_opt_proto, 
/**13**/		//fld_opt_code, 
/**14**/		//fld_opt_len, 
/**15**/		//fld_opt_data, 
/**16**/		fld_tcp_sport, 
/**17**/		fld_tcp_dport, 
/**18**/		fld_tcp_seq, 
/**19**/		fld_tcp_ack, 
/**20**/		fld_tcp_off, 
/**21**/		fld_tcp_res, 
/**22**/		fld_tcp_flags, 
/**23**/		fld_tcp_win, 
/**24**/		fld_tcp_csum, 
/**25**/		fld_tcp_urp, 
/**26**/		fld_icmp_type, 
/**27**/		fld_icmp_code, 
/**28**/		fld_icmp_csum, 
/**29**/		fld_icmp_id, 
/**30**/		fld_icmp_seq, 
/**31**/		fld_ip_src, 
/**32**/		fld_ip_dst, 
/**33**/		fld_ip_ver, 
/**34**/		fld_ip_hlen, 
/**35**/		fld_ip_tos, 
/**36**/		fld_ip_len, 
/**37**/		fld_ip_id, 
/**38**/		fld_ip_flags, 
/**39**/		fld_ip_off, 
/**40**/		fld_ip_ttl, 
/**41**/		fld_ip_proto, 
/**42**/		fld_ip_csum, 
/**43**/		fld_udp_sport, 
/**44**/		fld_udp_dport, 
/**45**/		fld_udp_len, 
/**46**/		fld_udp_csum,
/**47**/		fld_logger_id};
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
		 * @param rs The resultset to input into the object.
		 */

public void fillDataTable(ResultSet rs) {
		
	try { 
		
		dataTableObj.fillDataTable(rs);

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

}