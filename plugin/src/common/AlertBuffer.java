package common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import servicebus.AlertPluginFramework;


public class AlertBuffer  {

	ArrayList<AlertComRow> buff ;
	

	
	AlertComRowComparator comparator ;
	
	// TreeSet buff1 ;
	
	private Long LeastTimeStamp , GreatestTimeStamp ;
	

	public AlertBuffer()
	{
		//buff1 = new TreeSet(new AlertComRowComparator());
		
		comparator = new AlertComRowComparator();
		buff = new ArrayList<AlertComRow>();
		setLeastTimeStamp((long) -1) ;
		setGreatestTimeStamp((long) -1) ;
	}
	
	public void resetTimeStamps() {
		 LeastTimeStamp = (long)-1 ;
		    GreatestTimeStamp = LeastTimeStamp ;
		    
	}
	
	public void setLeastTimeStamp(Long leastTimeStamp) {
		LeastTimeStamp = leastTimeStamp;
	}

	public Long getLeastTimeStamp() {
		return LeastTimeStamp;
		
//		if (this.size()>0)
//		{ return Long.valueOf(buff.get(0).getValueAt(AlertCom.FLD_TIMESTAMP).toString());
//		  	}
//		return (long)-1;
	}

	public void setGreatestTimeStamp(Long greatestTimeStamp) {
		GreatestTimeStamp = greatestTimeStamp;
	}

	public Long getGreatestTimeStamp() {
		return GreatestTimeStamp;
//		if (this.size()>0)
//		{ return Long.valueOf(buff.get(this.size()-1).getValueAt(AlertCom.FLD_TIMESTAMP).toString());
//		  	}
//		return (long)-1;
	}
	
	public synchronized int size()
	{
		
		return buff.size();
		
	}
	
	public synchronized AlertComRow popAndRemove()
	{
		
		
		
		AlertComRow tmp = null;
		try{
			
			if (this.size()>0)
			{ 
				
					tmp = buff.get(0);
					  buff.remove(0);
				
			}
		
		}catch(Exception e)
		{ System.out.println(e.getMessage()); }
		return tmp;
	
	}
	
	
	public synchronized AlertComRow popButDontRemove()
	{
		AlertComRow tmp = null;
		try{
			if (this.size()>0)
				 tmp = buff.get(0);
		
		}catch(Exception e)
		{ System.out.println(e.getMessage()); }
		return tmp;
	}
	
	public synchronized void pushAlert(AlertComRow alertComRowObj)
	{
		try{
			
			if (GreatestTimeStamp == -1) 
			{
				LeastTimeStamp =Long.valueOf( alertComRowObj.getValueAt( AlertCom.FLD_TIMESTAMP).toString());
				GreatestTimeStamp = Long.valueOf( alertComRowObj.getValueAt( AlertCom.FLD_TIMESTAMP).toString());
			}
	
			buff.add(alertComRowObj);
		//	Collections.sort(buff,comparator);
			
			if (LeastTimeStamp > Long.valueOf( alertComRowObj.getValueAt( AlertCom.FLD_TIMESTAMP).toString()))
				LeastTimeStamp =Long.valueOf( alertComRowObj.getValueAt( AlertCom.FLD_TIMESTAMP).toString());
			  
			  if (GreatestTimeStamp < Long.valueOf( alertComRowObj.getValueAt( AlertCom.FLD_TIMESTAMP).toString()))
				  GreatestTimeStamp = Long.valueOf( alertComRowObj.getValueAt( AlertCom.FLD_TIMESTAMP).toString());
			  
		
		}catch(Exception e)
		{ System.out.println(e.getMessage()); }
		
	}
	
	public synchronized AlertCom toAlertComObj()
	{
		AlertCom tmp = new AlertCom();
		try{
			int size = this.size();
			if (size > AlertPluginFramework.THRESHOLD_FOR_CLIENT_TO_SEND)
				size = AlertPluginFramework.THRESHOLD_FOR_CLIENT_TO_SEND;
			  //  tempRow =AlertQueue.toArray();
			
				for  (int i = 0 ; i < size ; i ++)
				{
					tmp.appendRow(buff.get(i).getRow().toArray());//popbutdontremove
				}
		
		}catch(Exception e)
		{ System.out.println(e.getMessage()); }
		
		return tmp;
	}
	
	public synchronized AlertComRow[] toArray()
	{
		AlertComRow[] tmp = null;
		try{
			int size = this.size();
			if (size ==0) return null;
			tmp = new AlertComRow[size];
			  //  tempRow =AlertQueue.toArray();
			
				for  (int i = 0 ; i < size ; i ++)
				{
					tmp[i]=buff.get(i);
				}
		
		}catch(Exception e)
		{ System.out.println(e.getMessage()); }
		
		return tmp;
	}
	
	public synchronized AlertComRow getValueAt(int index) //zero based
	{
		AlertComRow tmp = null;
		try{
			if (this.size()>=index)
				 tmp = buff.get(index);
		
		}catch(Exception e)
		{ System.out.println(e.getMessage()); }
		return tmp;
		
		
	}
	
	public void removeAlerts(int numberOfAlerts) //zero based
	{
		
		try{
			if (numberOfAlerts > this.size()) 
				numberOfAlerts = this.size();
			
			for (int i = 0 ; i< numberOfAlerts ; i++)
				popAndRemove();
		   
		
			
			recalculate_TimeStamps();
			 
			
		}catch(Exception e)
		{ System.out.println(e.getMessage()); }
		
		
		
	}
	
	private synchronized void recalculate_TimeStamps()
	{
		
		for (int i = 0 ; i< this.size() ; i++)
		{	if (LeastTimeStamp > Long.valueOf( buff.get(i).getValueAt( AlertCom.FLD_TIMESTAMP).toString()))
				LeastTimeStamp =Long.valueOf( buff.get(i).getValueAt( AlertCom.FLD_TIMESTAMP).toString());
			
		    if (GreatestTimeStamp < Long.valueOf( buff.get(i).getValueAt( AlertCom.FLD_TIMESTAMP).toString()))
			  GreatestTimeStamp =Long.valueOf( buff.get(i).getValueAt( AlertCom.FLD_TIMESTAMP).toString());

		}
		
	}
	
	public synchronized void clear() //zero based
	{
		
		try{
			buff.clear();
			resetTimeStamps();			
		}catch(Exception e)
		{ System.out.println(e.getMessage()); }
		
		
		
	}
	
}

class AlertComRowComparator implements Comparator
{
    public int compare(Object emp1, Object emp2) 
    {
    	AlertComRow e1 = (AlertComRow)emp1;
    	AlertComRow e2 = (AlertComRow)emp2;
        int ret = 1;

        if(Long.valueOf(e1.getValueAt(AlertCom.FLD_TIMESTAMP).toString()) == Long.valueOf(e2.getValueAt(AlertCom.FLD_TIMESTAMP).toString())) ret = 0;
        if (Long.valueOf(e1.getValueAt(AlertCom.FLD_TIMESTAMP).toString()) < Long.valueOf(e2.getValueAt(AlertCom.FLD_TIMESTAMP).toString())) ret = -1;

        return ret;
    }
}

