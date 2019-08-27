/*
*<br>程式目的：資料庫共用函數
*<br>程式代號：Database
*<br>撰寫日期：93/12/01
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者: wells　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/
package util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Datetime {
	
	private Datetime() {
		 // avoid instantiation...
	}

	/**
	 * <br>
	 * <br>目的：取得系統日期
	 * <br>參數：無
	 * <br>傳回：傳回字串YYYMMDD
	*/
    public static String getYYYMMDD() {
        StringBuffer sb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR) - 1911;
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);        
        if (y<=99){ sb.append("0"); }
        sb.append(Integer.toString(y));
        if (m<=9){ sb.append("0"); }
        sb.append(Integer.toString(m));
        if (d<=9){ sb.append("0"); }
        sb.append(Integer.toString(d));
        return sb.toString();
    }
    
    public static String getYYYMMDD(Date s) {
        StringBuffer sb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        cal.setTime(s);
        int y = cal.get(Calendar.YEAR) - 1911;
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);        
        if (y<=99){ sb.append("0"); }
        sb.append(Integer.toString(y));
        if (m<=9){ sb.append("0"); }
        sb.append(Integer.toString(m));
        if (d<=9){ sb.append("0"); }
        sb.append(Integer.toString(d));
        return sb.toString();
    }    
    
    /**
	 * <br>
	 * <br>目的：取得前一年
	 * <br>參數：無
	 * <br>傳回：傳回字串YYY
	*/
    public static String getBYYY() {
        StringBuffer sb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR) - 1912;
        if (y<=99){ sb.append("0"); }
        sb.append(Integer.toString(y));
        return sb.toString();
    }
    
	/**
	 * <br>
	 * <br>目的：取得系統年月
	 * <br>參數：無
	 * <br>傳回：傳回字串YYYMM
	*/
	public static String getYYYMM(){
		return (getYYYMMDD().substring(0,5));
	}
	
	/**
	 * <br>
	 * <br>目的：取得系統年
	 * <br>參數：無
	 * <br>傳回：傳回字串YYY
	*/
	public static String getYYY(){
		return (getYYYMMDD().substring(0,3));
	}
	
	/**
	 * <br>
	 * <br>目的：取得系統月
	 * <br>參數：無
	 * <br>傳回：傳回字串MM
	*/
	public static String getMM(){
		return (getYYYMMDD().substring(3,5));
	}	
	/**
	 * <br>
	 * <br>目的：取得系統日
	 * <br>參數：無
	 * <br>傳回：傳回字串DD
	*/
	public static String getDD(){
		return (getYYYMMDD().substring(5));
	}	
	
	/**
	 * <br>
	 * <br>目的：取得系統時間
	 * <br>參數：無
	 * <br>傳回：傳回字串HHMMSS
	*/
    public static String getHHMMSS() {
        Calendar calendar;
        String hh, mi, ss;
        calendar = Calendar.getInstance();
        hh = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) ;
        mi = String.valueOf(calendar.get(Calendar.MINUTE)) ;
        ss = String.valueOf(calendar.get(Calendar.SECOND)) ;
        if( hh.length() == 1) 
            hh = '0' + hh;
        if( mi.length() == 1) 
            mi = '0' + mi;
        if( ss.length() == 1) 
            ss = '0' + ss;   
        return hh + mi + ss;    
    }
    
	/**
	 * <br>
	 * <br>目的：取得系統時間
	 * <br>參數：無
	 * <br>傳回：傳回字串HH:MM:SS
	*/
    public static String getFHHMMSS() {
        Calendar calendar;
        String hh, mi, ss;
        calendar = Calendar.getInstance();
        hh = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) ;
        mi = String.valueOf(calendar.get(Calendar.MINUTE)) ;
        ss = String.valueOf(calendar.get(Calendar.SECOND)) ;
        if( hh.length() == 1) 
            hh = '0' + hh;
        if( mi.length() == 1) 
            mi = '0' + mi;
        if( ss.length() == 1) 
            ss = '0' + ss;   
        return hh +":"+ mi+":" + ss;    
    }
  	

	/**
	 * <br>
	 * <br>目的：取得系統時間
	 * <br>參數：無
	 * <br>傳回：傳回字串HHMM
	*/
    public static String getHHMM() {
		return (getHHMMSS().substring(0,4));       
    }

    
	//  *********************************************
	//  函數功能：比較兩個日期
	//  參　　數：sdate,edate為起訖日期, sType可以是d(Day),m(Month),y(Year)
    //  範　　例：getDateDiff("m","0940910","0941210") 會傳回3
	//  傳 回 值：假如正確是傳回一字串值;假如錯誤則傳回空字串
	//  *********************************************    
    public static String getDateDiff(String sType, String sdate, String edate) {
    	String rStr="";
    	long lStr;
     	if( Common.get(sdate).length()==7 && Common.get(edate).length()==7 && Common.get(sType).length()>0){     		
    		GregorianCalendar g1 = new GregorianCalendar();
    		GregorianCalendar g2 = new GregorianCalendar();
	    	Date dates = null, datee=null;
	    	long one_day=1000*60*60*24;
	    	g1.set(Integer.parseInt(sdate.substring(0,3))+1911,Integer.parseInt(sdate.substring(3,5))-1,Integer.parseInt(sdate.substring(5)));
	    	g2.set(Integer.parseInt(edate.substring(0,3))+1911,Integer.parseInt(edate.substring(3,5))-1,Integer.parseInt(edate.substring(5)));
	    	dates = g1.getTime();
	    	datee = g2.getTime();
        	if (Common.get(sType).equals("y")) {
        		lStr = ((Integer.parseInt(edate.substring(0,3))-Integer.parseInt(sdate.substring(0,3))));        		        		
        	} else if (Common.get(sType).equals("m")) {
    			int sMonth = g1.get(GregorianCalendar.MONTH) + (g1.get(GregorianCalendar.YEAR) * 12);
    			int eMonth = g2.get(GregorianCalendar.MONTH) + (g2.get(GregorianCalendar.YEAR) * 12);		
    			lStr = eMonth-sMonth;        		
        	} else {
    			lStr = (datee.getTime()-dates.getTime())/one_day; 
        	}
        	rStr = ""+lStr;
     	}
     	return rStr;
    }    

	//  *********************************************
	//  函數功能：某一日期加上一定期間的日或月或年
	//  參　　數：sType可以是d(Day),m(Month),y(Year); sNum數值; sdate為日期
	//  傳 回 值：傳回加上特定期間之後的日期
	//  *********************************************    
    public static String getDateAdd(String sType, int sNum, String sdate) {
    	String rStr="";
     	if( Common.get(sdate).length()==7){     		
    		GregorianCalendar g1 = new GregorianCalendar();
        	if (Common.get(sType).equals("y")) {
        		g1.set(Integer.parseInt(sdate.substring(0,3))+1911+sNum,Integer.parseInt(sdate.substring(3,5))-1,Integer.parseInt(sdate.substring(5)));        		
        	} else if (Common.get(sType).equals("m")) {
        		g1.set(Integer.parseInt(sdate.substring(0,3))+1911,Integer.parseInt(sdate.substring(3,5))-1+sNum,Integer.parseInt(sdate.substring(5)));
        	} else {
        		g1.set(Integer.parseInt(sdate.substring(0,3))+1911,Integer.parseInt(sdate.substring(3,5))-1,Integer.parseInt(sdate.substring(5))+sNum);
        	}	    	
            StringBuffer sb = new StringBuffer();
            int y = g1.get(GregorianCalendar.YEAR) - 1911;            
            int m = g1.get(GregorianCalendar.MONTH) + 1;
            int d = g1.get(GregorianCalendar.DATE);        
            if (y<=99){ sb.append("0"); }
            sb.append(Integer.toString(y));
            if (m<=9){ sb.append("0"); }
            sb.append(Integer.toString(m));
            if (d<=9){ sb.append("0"); }
            sb.append(Integer.toString(d));
            rStr = sb.toString();
     	}
     	return rStr;
    }    	

	
	/**
	 * <br>
	 * <br>目的：取得二個日期之間的月份數
	 * <br>參數：alterMonth:第一個日期, dealMonth:第二個日期
	 * <br>傳回：傳回數字-1表長度不夠, -2表第一個日期大於第二個日期
	*/	
    public static int BetweenTwoMonth(String alterMonth , String dealMonth){
        int length = 0;
        if (dealMonth.length() < 5 || alterMonth.length() < 5){
            length = -1;
        }else{
            int dealInt = Integer.parseInt(dealMonth);
            int alterInt = Integer.parseInt(alterMonth);
            if (dealInt < alterInt){
                length = -2;	
            }else{
                int dealYearInt  = Integer.parseInt(dealMonth.substring(0, 3));
                int dealMonthInt = Integer.parseInt(dealMonth.substring(3, 5));
                int alterYearInt = Integer.parseInt(alterMonth.substring(0, 3));
                int alterMonthInt= Integer.parseInt(alterMonth.substring(3, 5));
                length = (dealYearInt - alterYearInt) * 12 + (dealMonthInt - alterMonthInt);
            }
        }
        return length;
    }
	
    /**
     * 
     * @param format
     * @return
     */
	public static String getCurrentDate(String format)
	{
		if ("".equals(format)) format="yyyyMMdd";
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
		java.util.Date currentDate = new java.util.Date();
		return formatter.format(currentDate);
	}
	

	/**
	 * 算出year/month年月當月天數	 
	 * @param year
	 * @param month
	 * @return long
	 */
	public static long getDays(String year,String month)
	{
		boolean valid=false;
		int days=31;
		 
		Calendar calendar = Calendar.getInstance();			
		while(valid!=true)
		{
			int yy=Integer.parseInt(year)+1911;
			int mm=Integer.parseInt(month);
			calendar.set(yy,mm-1,days);
			int y=calendar.get(Calendar.YEAR);
			int m=calendar.get(Calendar.MONTH)+1;
			int d=calendar.get(Calendar.DATE);
			if ((yy!=y) || (mm!=m) || (days!=d))
			{
				valid=false;
				days--;
			}
			else
			{
				valid=true;
			}
		}
		return days;
	}
	
  	/**
  	 * <br>
  	 * <br>目的：2個日期的相差天數　
  	 * <br>參數：String sdate 起日期,String edate 迄日期
  	 * <br>傳回：傳回long
  	*/
    public static long DateSubtraction(String sdate,String edate) {
		GregorianCalendar g1 = new GregorianCalendar();
		GregorianCalendar g2 = new GregorianCalendar();
		g1.set(Integer.parseInt(sdate.substring(0,3))+1911,Integer.parseInt(sdate.substring(3,5))-1,Integer.parseInt(sdate.substring(5)));        
		g2.set(Integer.parseInt(edate.substring(0,3))+1911,Integer.parseInt(edate.substring(3,5))-1,Integer.parseInt(edate.substring(5)));
		Date d1 = g1.getTime();
		Date d2 = g2.getTime();
		long daterange = d2.getTime() - d1.getTime();
        long time = 1000*3600*24; //A day in milliseconds        
        return (daterange/time);
    }
}
