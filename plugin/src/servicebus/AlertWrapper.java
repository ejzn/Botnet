package servicebus;

import common.AlertBuffer;
import common.AlertCom;
import common.AlertComRow;

import java.util.LinkedList;
import java.util.Queue;
public class AlertWrapper {


	private AlertBuffer BufferObj ; 

	private Long not_less_than_this_Time = Long.valueOf("0") ; 
	
public AlertWrapper()
{

	BufferObj = new AlertBuffer();
	setLeastTimeStamp((long) -1) ;
	setGreatestTimeStamp((long) -1) ;
}
	
	public void Feed_Alert(	AlertComRow alertComRowObj)
	{
		
		if (Long.valueOf(alertComRowObj.getValueAt(AlertCom.FLD_TIMESTAMP).toString()) <= not_less_than_this_Time)
			return;
		
		
		
	
		try {
			
			
			

			BufferObj.pushAlert(alertComRowObj);
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			
		}
	
		
	}
	
	public void dont_Accept_Alerts_With_Timestamp_Less_Than_This(Long timestamp)
	{
		not_less_than_this_Time = timestamp;
	}
	
	
	public AlertCom GetLatestAlerts()
	{
		return BufferObj.toAlertComObj();
			

	}
	
	public void RemoveAlerts(int AlertNumber)
	{	
		try{
			BufferObj.removeAlerts(AlertNumber);

		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		
		
	}
		
	}
	
	public void RemoveAlertsAll()
	{	
		try{
			
			BufferObj.clear();
			
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		
		
	}
		
	}

	public void resetTimeStamps() {
		BufferObj.resetTimeStamps();
		    
	}
	
	public void setLeastTimeStamp(Long leastTimeStamp) {
		BufferObj.setLeastTimeStamp(leastTimeStamp) ;
	}

	public Long getLeastTimeStamp() {
		return BufferObj.getLeastTimeStamp();
	}

	public void setGreatestTimeStamp(Long greatestTimeStamp) {
		BufferObj.setGreatestTimeStamp(greatestTimeStamp);
	}

	public Long getGreatestTimeStamp() {
		return BufferObj.getGreatestTimeStamp();
	}
	public int getAlertCount() {
		return BufferObj.size();
	}
}
