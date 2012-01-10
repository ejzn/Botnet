package common;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class AlertComRow {

	
	
	
	private ArrayList errorList;
	private DataTable  dataTableObj;
	
	public AlertComRow(){
		
		errorList = new ArrayList();
		String header[] = {
				AlertCom.FLD_LOGGERIDENTITY,
				/**0**/		AlertCom.FLD_TIMESTAMP,
				/**1**/		AlertCom.FLD_SOURCEIP, 
				/**2**/		AlertCom.FLD_SOURCEPORT,  
				/**3**/		AlertCom.FLD_SOURCEMAC, 
				/**4**/		AlertCom.FLD_SOURCEIPPRENAT, 
				/**5**/		AlertCom.FLD_SOURCEIPPOSTNAT, 
				/**6**/		AlertCom.FLD_SOURCEPORTPRENAT, 
				/**7**/		AlertCom.FLD_SOURCEPORTPOSTNAT, 
				/**8**/		AlertCom.FLD_DESTINATIONIP, 
				/**9**/		AlertCom.FLD_DESTINATIONPORT, 
				/**10**/		AlertCom.FLD_DESTINATIONMAC, 
				/**11**/		AlertCom.FLD_DESTINATIONIPPRENAT, 
				/**12**/		AlertCom.FLD_DESTINATIONIPPOSTNAT, 
				/**13**/		AlertCom.FLD_DESTINATIONPORTPRENAT, 
				/**14**/		AlertCom.FLD_DESTINATIONPORTPOSTNAT, 
				/**15**/		AlertCom.FLD_EVENTNAME, 
				/**16**/		AlertCom.FLD_EVENTCATEGORY, 
				/**17**/		AlertCom.FLD_REFERENCETAG, 
				/**18**/		AlertCom.FLD_REFERENCESYSTEM, 
				/**19**/		AlertCom.FLD_DESCRIPTION, 
				/**20**/		AlertCom.FLD_PRIORITY, 
				/**21**/		AlertCom.FLD_DEVICETYPE, 
				/**22**/	//	AlertCom.FLD_USERNAME, 
				/**23**/		AlertCom.FLD_DOMAINNAME, 
				/**24**/		AlertCom.FLD_GROUPNAME, 
				/**25**/		AlertCom.FLD_NETBIOSNAME};
		dataTableObj = new DataTable(0,header);
				
	}
	
	/**
	 * 
	 * @param columnValues An array of the values in each column to append as a row to the object.
	 */
	
	public void SetValues(
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
		Object columnValues[] = new Object[getColumnCount()];
		 
		SetColumnValueInRow(columnValues,LOGGERIDENTITY , 	AlertCom.FLD_LOGGERIDENTITY);
		  
		SetColumnValueInRow(columnValues,TIMESTAMP , 	AlertCom.FLD_TIMESTAMP);

				  SetColumnValueInRow(columnValues,   SOURCEIP,	AlertCom.FLD_SOURCEIP);  
				  SetColumnValueInRow(columnValues,   SOURCEPORT,	AlertCom.FLD_SOURCEPORT);
				  SetColumnValueInRow(columnValues,   SOURCEMAC,	AlertCom.FLD_SOURCEMAC); 
				  SetColumnValueInRow(columnValues,   SOURCEIPPRENAT,	AlertCom.FLD_SOURCEIPPRENAT); 
				  SetColumnValueInRow(columnValues,   SOURCEIPPOSTNAT,		AlertCom.FLD_SOURCEIPPOSTNAT); 
				  SetColumnValueInRow(columnValues,   SOURCEPORTPRENAT,	AlertCom.FLD_SOURCEPORTPRENAT); 
				  SetColumnValueInRow(columnValues,   SOURCEPORTPOSTNAT,	AlertCom.FLD_SOURCEPORTPOSTNAT); 
				  SetColumnValueInRow(columnValues,   DESTINATIONIP,	AlertCom.FLD_DESTINATIONIP); 
				  SetColumnValueInRow(columnValues,   DESTINATIONPORT,	AlertCom.FLD_DESTINATIONPORT);
				  SetColumnValueInRow(columnValues,   DESTINATIONMAC,	AlertCom.FLD_DESTINATIONMAC); 
				  SetColumnValueInRow(columnValues,   DESTINATIONIPPRENAT,	AlertCom.FLD_DESTINATIONIPPRENAT); 
				  SetColumnValueInRow(columnValues,   DESTINATIONIPPOSTNAT,	AlertCom.FLD_DESTINATIONIPPOSTNAT); 
				  SetColumnValueInRow(columnValues,   DESTINATIONPORTPRENAT,	AlertCom.FLD_DESTINATIONPORTPRENAT); 
				  SetColumnValueInRow(columnValues,   DESTINATIONPORTPOSTNAT,	AlertCom.FLD_DESTINATIONPORTPOSTNAT); 
				
				  SetColumnValueInRow(columnValues,   EVENTNAME,	AlertCom.FLD_EVENTNAME); 
				  SetColumnValueInRow(columnValues,   EVENTCATEGORY,	AlertCom.FLD_EVENTCATEGORY); 
				  SetColumnValueInRow(columnValues,   REFERENCETAG,	AlertCom.FLD_REFERENCETAG); 
				  SetColumnValueInRow(columnValues,   REFERENCESYSTEM,	AlertCom.FLD_REFERENCESYSTEM); 
				  SetColumnValueInRow(columnValues,   DESCRIPTION,	AlertCom.FLD_DESCRIPTION); 
				  SetColumnValueInRow(columnValues,   PRIORITY,	AlertCom.FLD_PRIORITY); 
				  SetColumnValueInRow(columnValues,   DEVICETYPE,	AlertCom.FLD_DEVICETYPE); 
				  SetColumnValueInRow(columnValues,   DOMAINNAME,	AlertCom.FLD_DOMAINNAME); 
				  SetColumnValueInRow(columnValues,   GROUPNAME,	AlertCom.FLD_GROUPNAME); 
				
				  SetColumnValueInRow(columnValues,   NETBIOSNAME,	AlertCom.FLD_NETBIOSNAME); 
				  
		
		dataTableObj.appendRow(columnValues);
		
	}
	
	 private void SetColumnValueInRow(Object[] Row , Object Val ,String ColName  )
	  {
		  int colIndex =  GetIndexByName(ColName) ;
		  if (colIndex < 0) return ;
		  Row[colIndex] = Val ;
		  
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
	 * @return The row that was requested.
	 */
	
		public List  getRow() {
			  

			return dataTableObj.getRow(0);
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
	 * @param column The index of the column to retrieve the value from.
	 * @return The value requested.
	 */
	  public Object getValueAt( int column) {
		  return dataTableObj.getValueAt(0, column);
	  }
	  /**
	   * 
	   * @param column The name of the column to retrieve the value from.
	   * @return The value requested.
	   */
	  public Object getValueAt( String column) {
		  return dataTableObj.getValueAt(0, column);
		  }
		 
	  /**
	   * 
	   * @param value The object to input into the datatable.
	   * @param column The index of the column to insert into.
	   */
	  public void setValueAt(Object value,  int column) {
		  dataTableObj.setValueAt( value,  0,  column);
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
	   * @param column The index of the column to insert into.
	   */
	  public void setValueAt(Object value, String column) {
		  dataTableObj.setValueAt( value,  0,  column);
		  }
}
