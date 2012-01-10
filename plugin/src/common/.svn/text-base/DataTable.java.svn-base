package common;

import java.awt.Point;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;
 /**
  * The class DataTable is meant to provide the other classes in the common layer with a consistent structure.
  * 
  * @author Andrew Merrithew
  *
  */
public class DataTable extends AbstractTableModel{

  private HashMap lookup;

  private  int rows;

 // private final int columns;

  private final String headers[];
  
  /**
   * 
   * @param rows The number of rows to create.
   * @param columnHeaders The names of the columns to create.
   */

  public DataTable(int rows, String columnHeaders[]) {
	  
    if ((rows < 0) || (columnHeaders == null)) {
      throw new IllegalArgumentException(
          "Invalid row count/columnHeaders");
    }
    this.rows = rows;
  //  this.columns = columnHeaders.length;
    headers = columnHeaders;
    lookup = new HashMap();
  }
  
  public int getColumnCount() {
    return headers.length;
  }

  public int getRowCount() {
    return rows;
  }

  public String getColumnName(int column) {
    return headers[column];
  }

  public Object getValueAt(int row, int column) {
	  if ((rows < 0) || (headers.length < 0)) {
	      throw new IllegalArgumentException("Invalid row/column setting");
	    }
	  return lookup.get(new Point(row, column));
  }
  
  public Object getValueAt(int row, String column) {
	  if ((rows < 0) || (headers.length < 0)) {
	      throw new IllegalArgumentException("Invalid row/column setting");
	    }
	  int colNumber  = GetIndexByName(column) ; ;
	    
	  if ((row < rows) && (colNumber >= 0)) {
	    return lookup.get(new Point(row, colNumber));
	  }
	  return null ;
	  }
	 
  public void setValueAt(Object value, int row, int column) {
    if ((rows < 0) || (headers.length < 0)) {
      throw new IllegalArgumentException("Invalid row/column setting");
    }
    if ((row < rows) && (column < headers.length)) {
      lookup.put(new Point(row, column), value);
    }
  }
  /**
   * 
   * @param ColName The column name to search on.
   * @return The index of the column requested.
   */
  public int GetIndexByName(String ColName)
  {
	  for (int i = 0 ; i < headers.length ; i++) 
		{
			if (  getColumnName(i).toLowerCase().equals( ColName.toLowerCase()) )
			{
				return i ;
				
			}
		}
	  
	  return -1;
	  
  }
  /**
   * 
   * @param value The value to set.
   * @param row The index of the row to set.
   * @param column The name of the column to set.
   */
  public void setValueAt(Object value, int row, String column) {
	    if ((rows < 0) || (headers.length < 0)) {
	      throw new IllegalArgumentException("Invalid row/column setting");
	    }
	    
	    int colNumber  =  GetIndexByName(column) ;
	   	   	    
	    if ((row < rows) && (colNumber >= 0)) {
	      lookup.put(new Point(row, colNumber), value);
	    }
	  }
    /**
     * 
     * @param columnValues The array of values to append as a row.
     */
  public void appendRow(Object columnValues[]) {
	   
	  if (columnValues.length <= 0 ){
	      throw new IllegalArgumentException("Invalid column Values");
	    }
		  
	  for (int i = 0 ; i < columnValues.length ; i++) 
		{
	      lookup.put(new Point(rows, i), columnValues[i]);
		}
	  rows ++  ;
	  }
  /**
   * 
   * @param row The index of the row to return.
   * @return The row requested.
   */
  public List  getRow(int row) {

	  
	  List tempRow = new ArrayList<Object>();
	  for (int i = 0 ; i < headers.length ; i++) 
		{
	       tempRow.add( lookup.get(new Point(row, i)));
		}
	    return tempRow;
	  }
/**
 * 
 * @param rs The resultset to fill the datatable with.
 */
  public void fillDataTable(ResultSet rs) {
		
		try { 
			
			ResultSetMetaData rsMetaData = rs.getMetaData();
			
			int colcount = rsMetaData.getColumnCount(); 
			
			Object tempRow[] = new Object[getColumnCount()];
		///	int k  = 0 ;
			while (rs.next()) { 
			
				 for (int i = 0 ; i < colcount; i++) 
					{
					
				     SetColumnValueInRow(tempRow,rs.getObject(i+1) , rsMetaData.getColumnName(i+1)) ;
			
					}
				// System.out.println("Append row " + String.valueOf(k));
				// k++;
				 appendRow(tempRow);
				 tempRow = new Object[getColumnCount()];
				 
					 
			} 
			} catch (Exception e) {  
				System.out.print(e.toString());
			} 
		
			
		}
  
  /**
   * 
   * @param rs The resultset to fill the datatable with.
   */
    public void fillDataTable_Blindly(ResultSet rs) {
  		
  		try { 
  			
  			ResultSetMetaData rsMetaData = rs.getMetaData();
  			
  			int colcount = rsMetaData.getColumnCount(); 
  			
  			Object tempRow[] = new Object[getColumnCount()];
  		///	int k  = 0 ;
  			while (rs.next()) { 
  			
  				 for (int i = 0 ; i < colcount; i++) 
  					{
  					
  					tempRow[i] = rs.getObject(i+1);
  					// SetColumnValueInRow(tempRow,rs.getObject(i+1) , rsMetaData.getColumnName(i+1)) ;
  			
  					}
  				// System.out.println("Append row " + String.valueOf(k));
  				// k++;
  				 appendRow(tempRow);
  				 tempRow = new Object[getColumnCount()];
  				 
  					 
  			} 
  			} catch (Exception e) {  
  				System.out.print(e.toString());
  			} 
  		
  			
  		}
/**
 * 
 * @param Row The row to change.
 * @param Val The value to set.
 * @param ColName The name of the column to set.
 */
  private void SetColumnValueInRow(Object[] Row , Object Val ,String ColName  )
  {
	  int colIndex =  GetIndexByName(ColName) ;
	  if (colIndex < 0) return ;
	  Row[colIndex] = Val ;
	  
  }
  
  public void sort_Ascending_BasedOn( String ColName) {
	  if ((rows < 0) || (headers.length < 0)) {
	      throw new IllegalArgumentException("Invalid row/column setting");
	    }
	    
	    int colNumber  =  GetIndexByName(ColName) ;
	   	if (colNumber == -1 ) 
	   		throw new IllegalArgumentException("Invalid row/column setting");   	    
	   
	 //Insertion Sort
	   	
	   	for (int i =0; i < getRowCount();i++)
	   	{
	   	   int j = i;
	   	 Long B = Long.valueOf(String.valueOf(lookup.get(new Point(i, colNumber))));
	   	 
	   	 while ((j > 0) && (Long.valueOf(String.valueOf(lookup.get(new Point(j-1, colNumber)))) > B))
	   	     {
	   		    lookup.put(new Point(j, colNumber),lookup.get(new Point(j-1, colNumber)));
		        j--;
		      }
	   	lookup.put(new Point(j, colNumber), B);
	   		
	   	}
	  }
  
//  public static void insertion_sort(int array[], int n){
//	    for (int i = 1; i < n; i++){
//	      int j = i;
//	      int B = array[i];
//	      while ((j > 0) && (array[j-1] > B)){
//	        array[j] = array[j-1];
//	        j--;
//	      }
//	      array[j] = B;
//	    }
//	  }
  
}
