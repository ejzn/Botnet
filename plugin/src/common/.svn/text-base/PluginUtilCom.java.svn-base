package common;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

public class PluginUtilCom {

	public static String intToIp(long i) {
        return ((i >> 24 ) & 0xFF) + "." +
               ((i >> 16 ) & 0xFF) + "." +
               ((i >>  8 ) & 0xFF) + "." +
               ( i        & 0xFF);
    } 

public static Long ipToInt(String addr) {
        String[] addrArray = addr.split("\\.");

        long num = 0;
        for (int i=0;i<addrArray.length;i++) {
            int power = 3-i;

            num += ((Integer.parseInt(addrArray[i])%256 * Math.pow(256,power)));
        }
        return num;
    }
	

public static String ConvertTimeZoneToUTC(String Format , String timeval) {
	timeval = timeval.substring(0,Format.length());
	
	DateTime begindt = DateTimeFormat.forPattern(Format).parseDateTime(timeval);
	//DateTime begindt = new DateTime();
	//begindt = begindt.withMillis(timeval);
	begindt = begindt.withZone(DateTimeZone.UTC);
//	String beginTimeStr = begindt.toString(ISODateTimeFormat.basicDateTimeNoMillis()).replace("T", "").replace("Z", "");
	
//			 beginTimeStr = beginTimeStr.substring(0,14 );//.substring(0,beginTimeStr.indexOf("-") );
//	return Long.parseLong(beginTimeStr);
	return String.valueOf(begindt.getMillis());
}

public static Long ConvertTimeZoneToLocal(String timevalMillis) {
	//DateTime begindt = DateTimeFormat.forPattern("yyyyMMddhhmmss").parseDateTime(timeval);
	DateTime begindt = new DateTime(DateTimeZone.UTC);
	begindt = begindt.withMillis(Long.valueOf(timevalMillis));
	begindt = begindt.withZone(DateTimeZone.getDefault());
//	String beginTimeStr = begindt.toString(ISODateTimeFormat.basicDateTimeNoMillis()).replace("T", "").replace("Z", "");
	
//			 beginTimeStr = beginTimeStr.substring(0,14 );//.substring(0,beginTimeStr.indexOf("-") );
//	return Long.parseLong(beginTimeStr);
	return begindt.getMillis();
}

}
