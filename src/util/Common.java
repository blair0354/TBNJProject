/*
*<br>程式目的：常用函數
*<br>程式代號：Common
*<br>撰寫日期：93/12/01
*<br>程式作者：KangDa Info
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/

package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.PrivilegedAction;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringEscapeUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Common {
	
	private Common() {
		 // avoid instantiation...
	}
	
  	/**
  	 * <br>
   	 * <br>目的：撰寫JavaBean get方法時所需套用的函數
   	 * <br>參數：資料字串
     * <br>傳回：檢查資料為null,是則傳回空字串
  	*/
	static public String get(String s){
		if(s==null){
			return "";
		}else{
			return s;
		}
	}
  	/**
  	 * <br>
  	 * <br>目的：撰寫JavaBean set方法時所需套用的函數
  	 * <br>參數：資料字串
  	 * <br>傳回：將資料前後空白去除
  	*/
	static public String set(String s){
		if(s==null){
			return "";
		}else{
			return s.trim();
		}
	}
	
	static public String checkSet(String s){
		if(s==null){
			return "";
		} else {
			return org.apache.commons.lang.StringEscapeUtils.escapeSql(s);
		}
	}	
	
	static public String checkGet(String s){
		if(s==null){
			return "";
		}else{
			s = s.trim();	
			//s = s.replaceAll("<", "&lt;");
			//s = s.replaceAll(">","&gt;");
			//s = org.apache.commons.lang.StringEscapeUtils.escapeHtml(s);
			//return s;
			return org.apache.commons.lang.StringEscapeUtils.escapeHtml(s);
		}
	}	
	
    static public String escapeJavaScript(String s) {
    	if (s!=null && !"".equals(s)) {
        	String[] arr = new String[]{"'","\"","\b","\n","\t","\f","\r","<>","<",">"};
        	for (int i=0; i<arr.length; i++) {
        		s = s.replaceAll(arr[i], "");
        	}
        	return StringEscapeUtils.escapeJavaScript(s);    		
    	}
    	return "";
    }      
    	
	
    static public String escapeReturnChar(String s) {
    	if (s!=null && !"".equals(s)) {
        	String[] arr = new String[]{"'","\"","\b","\n","\t","\f","\r","<>","<",">","　"," "};
        	for (int i=0; i<arr.length; i++) {
        		s = s.replaceAll(arr[i], "");
        	}
        	return s;    		
    	}
    	return "";
    }    	
	
    /**
     * <br>
     * <br>目的：組合sql語法時字串需加單引號的函數 
     * <br>參數：資料字串 
     * <br>傳回：加上單引號傳回資料字串 
     */
    static public String sqlChar(String s) {
        if (s == null || s .equals("")) { return "''"; }
        return "'" + s.trim() + "'";
    }
    
    /**
     * <br>
     * <br>目的：組合MS SQL Server語法時字串需加單引號的函數 
     * <br>參數：資料字串 
     * <br>傳回：加上單引號傳回資料字串 
     */
    static public String sql2Char(String s) {
        if (s == null || s .equals("")) { return "null"; }
        return "'" + s.trim() + "'";
    }
    
    /**
     * 很多時候我們需要自動產生資料的key值,而用vmid是個不錯的選擇..
     * @return modifyVMID
     * @see java.rmi.dgc.VMID()
     */
    public static String getVMID() {
		return new java.rmi.dgc.VMID().toString().replaceAll(":","_");
    }

    /**
     * <br>
     * <br>目的：組合sql語法時字串需加單引號的函數 
     * <br>參數：資料字串 
     * <br>傳回：加上單引號傳回資料字串 
     */
    static public String sqlInt(String s) {
        if (s == null) { return "'0'"; }
        return "'" + s.trim() + "'";
    }
    
  	/**
  	 * <br> 
  	 * <br>目的：將字串BIG5轉為ISO1
  	 * <br>參數：資料字串
  	 * <br>傳回：傳回ISO1字串
  	*/
    public static String big5ToIso(String s){
     	if (s==null){ s=""; }
    	try {
    		s=new String(s.getBytes("big5"),"ISO-8859-1");
    	} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }   
	    return s; 
    }
    
    /**
  	 * <br> 
  	 * <br>目的：將字串BIG5轉為ISO1
  	 * <br>參數：資料字串
  	 * <br>傳回：傳回ISO1字串
  	*/
    public static String Ms950ToIso(String s){
     	if (s==null){ s=""; }
    	try {
    		s=new String(s.getBytes("MS950"),"ISO-8859-1");
    	} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }   
	    return s; 
    }
    
  	/**
  	 * <br> 
  	 * <br>目的：將字串ISO1轉為BIG5
  	 * <br>參數：資料字串
  	 * <br>傳回：傳回BIG5字串
  	*/
    public static String isoToBig5(String s) {
     	if (s==null){ s=""; }
    	try {
			s=new String(s.getBytes("ISO-8859-1"),"big5");
    	} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }   
	    return s;        
    }
    
	/**
  	 * <br> 
  	 * <br>目的：將字串ISO1轉為MS950
  	 * <br>參數：資料字串
  	 * <br>傳回：傳回BIG5字串
  	*/
    public static String isoToMS950(String s) {
     	if (s==null){ s=""; }
    	try {
			s=new String(s.getBytes("ISO-8859-1"),"MS950");
    	} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }   
	    return s;        
    }
    
    /**
  	 * <br> 
  	 * <br>目的：將字串ISO1轉為UTF-8
  	 * <br>參數：資料字串
  	 * <br>傳回：傳回UTF-8字串
  	*/
    public static String isoToutf8(String s) {
     	if (s==null){ s=""; }
    	try {
			s=new String(s.getBytes("ISO-8859-1"),"big5");
    	} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }   
	    return s;        
    }
    
    /**
     * Turn a bytearray into a printable form, representing
     * each byte in hex.
     *
     * @param data the bytearray to stringize
     * @return a hex-encoded printable representation of <code>data</code>
     */
    public static String toHexString(byte[] data) {
        StringBuffer sb = new StringBuffer(data.length * 2);
        for (int i = 0; i < data.length; ++i)
        {
            sb.append(Integer.toHexString((data[i] >> 4) & 15));
            sb.append(Integer.toHexString(data[i] & 15));
        }
        return sb.toString();
    }
    
    /**
     * Encode a string as UTF-8.
     *
     * @param str the string to encode
     * @return the UTF-8 representation of <code>str</code>
     */
    public static byte[] encodeUTF8(String str) {
        // It turns out that under 1.4.2, at least, calling getBytes() is
        // faster than rolling our own converter (it uses direct buffers and, I suspect,
        // a native helper -- and the cost of the encoding lookup is mitigated by a
        // ThreadLocal that caches the last lookup). So we just do things the simple way here.
        try
        {
            return str.getBytes("UTF-8");
        }
        catch (java.io.UnsupportedEncodingException e)
        {
            // Javadoc says that UTF-8 *must* be supported by all JVMs, so we don't try to be clever here.
            throw new RuntimeException("Unexpected exception: UTF-8 charset not supported: " + e);
        }
    }    
    
    
	/**
	*<br>目的：將字串前面補零傳回字串
	*<br>參數：(1)資料字串 (2)所需長度
	*<br>傳回：傳回字串
	*<br>範例：formatFrontZero("123",5)，傳回"00123"
	*/
	static public String formatFrontZero(String s,int len){
		String format="";
		int sLen=s.length();
		for(int i=0; i<(len-sLen) ; i++){
			format += "0";
		}
		format += s;
		return format;
	}
	
	/**
	*<br>目的：將字串後面補零傳回字串
	*<br>參數：(1)資料字串 (2)所需長度
	*<br>傳回：傳回字串
	*<br>範例：formatRearZero("123",5)，傳回"12300"
	*/
	static public String formatRearZero(String s,int len){
		String format="";
		int sLen=s.length();
		for(int i=0; i<(len-sLen) ; i++){
			format += "0";
		}
		format = s + format;
		return format;
	}	
	
	/**
	*<br>目的：將字串前面補自己想要加入的字元傳回字串
	*<br>參數：(1)資料字串 (2)所需長度 (3)欲加入的字元
	*<br>傳回：傳回字串
	*<br>範例：formatRearZero("123",5, "A")，傳回"AA123"
	*/
	static public String formatFrontString(String s,int len, char ch){
		String format="";
		int sLen=s.length();
		for(int i=0; i<(len-sLen) ; i++){
			format += ch;
		}
		format += s;
		return format;
	}	

	/**
	*<br>目的：將字串後面補自己想要加入的字元傳回字串
	*<br>參數：(1)資料字串 (2)所需長度 (3)欲加入的字元
	*<br>傳回：傳回字串
	*<br>範例：formatRearZero("123",5, "A")，傳回"123AA"
	*/	
	static public String formatRearString(String s,int len, char ch){
		String format="";
		int sLen=s.length();
		for(int i=0; i<(len-sLen) ; i++){
			format += ch;
		}
		format = s + format;
		return format;
	}	
	
	/**
	 * 將HHMMSS時間轉成HH:MM:SS
	 * @param HHMMSS
	 * @return HH:MM:SS
	 */
	static public String formatHHMMSS(String s) {
		if (s!=null && s.length()==6) {
			return s.substring(0,2)+":"+s.substring(2,4)+":"+s.substring(4);
		}
		return get(s);
	}
	
	/**
	 * 將YYYMMDD日期轉化成比較易讀的格式
	 * 
	 * @param YYYMMDD
	 * @param format = 1, 2, 3, 4
	 * @return YYY.MM.DD, YYY年MM月DD日, 民國YYY年MM月DD日, YYY/MM/DD, YYY-MM-DD
	 */
	static public String formatYYYMMDD(String s, int format) {
		if (s!=null && s.length()==7) {
			try {
				switch (format) {
				case 1: return s.substring(0,3)+"."+s.substring(3,5)+"."+s.substring(5);
				case 2: return s.substring(0,3)+"年"+s.substring(3,5)+"月"+s.substring(5)+"日";
				case 3: return "民國"+s.substring(0,3)+"年"+s.substring(3,5)+"月"+s.substring(5)+"日";
				case 4: return s.substring(0,3)+"/"+s.substring(3,5)+"/"+s.substring(5);
				case 5: return s.substring(0,3)+"-"+s.substring(3,5)+"-"+s.substring(5);
				}
				return s.substring(0,3)+"."+s.substring(3,5)+"."+s.substring(5);				
			}catch(Exception e) {
				return s;
			}
		} else if (s!=null && s.length()==5) {
			try {
				switch (format) {
				case 1: return s.substring(0,3)+"."+s.substring(3,5);
				case 2: return s.substring(0,3)+"年"+s.substring(3,5)+"月";
				case 3: return "民國"+s.substring(0,3)+"年"+s.substring(3,5);
				case 4: return s.substring(0,3)+"/"+s.substring(3,5);
				case 5: return s.substring(0,3)+"-"+s.substring(3,5);
				}
				return s.substring(0,3)+"."+s.substring(3,5);				
			}catch(Exception e) {
				return s;
			}			
		}
		return get(s);
	}
	
	/**
	 * 將YYYMMDD日期轉化成YYY.MM.DD
	 * 
	 * @param YYYMMDD
	 * @return YYY.MM.DD
	 */	
	static public String formatYYYMMDD(String s) {
		return formatYYYMMDD(s,99);
	}
	
    /**
     * <br>
     * <br>目的：組合html option語法函數 
     * <br>參數：(1)sql字串 (2)被選的value <br>
     * <br>傳回：加上html option element 
     */
    static public String getOption(String sql, String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
        Database db = new Database();
        try {
        	ResultSet rs = db.querySQL(sql);
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                
                rtnStr = rtnStr + "<option value='" + id + "' ";
                if (selectedOne!= null && selectedOne.equals(id)) {
                    rtnStr = rtnStr + " selected ";
                }
                rtnStr = rtnStr + ">" + name + "</option>";
            }
        } catch (Exception ex) {
            rtnStr = "<option value=''>查詢錯誤</option>";
            ex.printStackTrace();
        } finally {
 			db.closeAll();
        }        
        return rtnStr;
    }
  
  	/**
  	 * <br> 
  	 * <br>目的：將路徑檔名改為jasper可以解讀的格式, 例:"d:\test\test.jasper", 傳回"d:\\test\\test.jasper"
  	 * <br>參數：資料字串
  	 * <br>傳回：傳回轉換後字串
  	*/
    public static String getJasperPath(String s) {
    	StringBuffer sReturn = new StringBuffer();
    	int start=0;
    	for(int i=0; i<s.length(); i++){
    		if ("\\".equals(s.substring(i,i+1))){
    			sReturn.append(s.substring(start,i) +"\\");
    			start=i;
    		}
    	}
    	sReturn.append(s.substring(start,s.length()));
    	return sReturn.toString();     
    }    
    
    static public String FormatStr(String sString, String sHTML) {
      	String sStr;
      	if (sHTML.equalsIgnoreCase("Y") || sHTML.equalsIgnoreCase("true") || sHTML.equalsIgnoreCase("1")) {
    		sStr = sString.replaceAll("<%", "&lt;%");		
    	} else {
    		sStr = sString.replaceAll("<", "&lt;");
    		sStr = sStr.replaceAll(">", "&gt;");
    		//sStr = sStr.replaceAll("\n", "<br>");
    		sStr = sStr.replaceAll("\r", "<br>");
    	}
    	return sStr;
    }    
        
    
    
    /**
  	 * <br> 
  	 * <br>目的：將user所鍵入之password經MD5加密
  	 * <br>參數：已加密字串
  	 * <br>傳回：傳回轉換後字串
  	*/
    public static String getMD5PassWord(String inPassWord){
    	try{
	      MessageDigest md = MessageDigest.getInstance("MD5");
	      md.update(inPassWord.getBytes());
	      MessageDigest theClone = (MessageDigest)md.clone();
	      byte[] digest = theClone.digest();
	    	    
	      final StringBuffer buffer = new StringBuffer();
	      for (int i = 0; i < digest.length; ++i){
	    	final byte b = digest[i]; 
	    	final int value = (b & 0x7F) + (b < 0 ? 128 : 0);
	    	buffer.append(value < 16 ? "0" : "");
	    	buffer.append(Integer.toHexString(value));
	   	  }
	      return buffer.toString();
    	}catch(Exception ex){
    		ex.printStackTrace();
    		return "";
    	}
    }
    
    /**
     * 將文字陣列轉成普通文字
     * 例如:String[]{"1","2"} 變成 1,2
     *  
     * @param strArray
     * @return
     */
    public static String arrayToString(String[] strArray) {
    	return arrayToString(strArray, ",");
    }
    
    /**
     * 將文字陣列轉成普通文字
     * 例如:String[] x = new String[]{"1","2"}
     * arrayToString(x,",") 會得到 1,2
     * 
     * @param a
     * @param separator
     * @return
     */
    public static String arrayToString(String[] a, String separator) {
        StringBuffer result = new StringBuffer().append("");
        if (a.length > 0) {
            result.append(a[0]);
            for (int i=1; i<a.length; i++) {
                result.append(separator);
                result.append(a[i]);
            }
        }
        return result.toString();
    }    

    public static boolean RemoveDirectory(File dir) {    	
        if (dir.isDirectory()) {
        	// if directory is root directory then throw exception
        	if(dir.getParentFile()==null) throw new IllegalArgumentException("Cann't remove root directory");
        	
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = RemoveDirectory(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }    
        // The directory is now empty so delete it
        return dir.delete();
    }
    
	public static boolean CreateDir(String strDirName) {
		// Create a directory; all ancestor directories must exist
		//boolean success = (new File(strDirName)).mkdir();
		return (new File(strDirName)).mkdirs();
		
		// Create a directory; all non-existent ancestor directories are
		// automatically created
		//success = (new File("directoryName")).mkdirs();
	}    
	
	
	public static boolean MakeDir(File dirToMake) throws SecurityException { 
		final File tempDirToMake = dirToMake;
		Object doPrivileged = AccessController.doPrivileged( new PrivilegedAction() { 
					public Object run() { 
							return new Boolean(tempDirToMake.mkdirs()); 
						} 
					}
				);
		Boolean result = (Boolean) doPrivileged;
		return result.booleanValue();		
	}
    
    //面積格式
    public static String areaFormat(String money){
        String formatString = new String();
        DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0.00");
        if(money!=null && !money.equals("")){
            try{
                formatString = df.format(Double.parseDouble(money));
            }catch (NumberFormatException e) {
                formatString =money;
         }
        }else{
            formatString =money;
        }
        return formatString;
    }  

    //價值格式
    public static String valueFormat(String money){
        String formatString = new String();
        DecimalFormat df = new DecimalFormat("###,###,###,###,###,###");
        if(money!=null && !money.equals("")){
            try{
                formatString = df.format(Double.parseDouble(money));
            }catch (NumberFormatException e) {
                formatString =money;
         }
        }else{
            formatString =money;
        }
        return formatString; 
    }
    
    //取消面積格式
    public static String nAreaFormat(String money){
    	String nValueFormatString=new String();
    	if((money!=null)&&(!money.equals(""))){
    		try{
    			
    			String[] tmp_arr=StrSplit(money,".",2);
    			nValueFormatString=StrSplit(tmp_arr[0],",");
    			//System.out.println(tmp_arr.length);
    			if((tmp_arr.length==2)&&(tmp_arr[1]!=null)){
    				nValueFormatString=nValueFormatString+"."+tmp_arr[1];
    			}
    		}catch(Exception e){
    			nValueFormatString=money;
    		}
    	}
    	return nValueFormatString;
    }
  
    
    //取消價值格式
    public static String nValueFormat(String money){
    	String nValueFormatString=new String();
    	if((money!=null)&&(!money.equals(""))){
    		try{
    			nValueFormatString=StrSplit(money,",");
    		}catch(Exception e){
    			nValueFormatString=money;
    		}
    	}
    	return nValueFormatString;
    }
    
    
    public static String StrSplit(String Record,String delimiter)   {   
        StringTokenizer st=new StringTokenizer(Record,delimiter);   
    	String RecordArray=new String();   
    	while(st.hasMoreTokens()){   
    	  try{   
    		  RecordArray+=st.nextToken();   
    	  }catch(Exception e){   
    		  e.printStackTrace();   
    	  }   
    	    
    	}   
    	return RecordArray;   
    }   
    
    public static String[] StrSplit(String Record,String delimiter,int arr_size){
    	StringTokenizer st=new StringTokenizer(Record,delimiter);   
     	String[] RecordArray=new String[arr_size];
     	int i=0;
     	while(st.hasMoreTokens()){   
     	  try{   
     		  RecordArray[i]=st.nextToken();
     		  i++;
     	  }catch(Exception e){   
     		  e.printStackTrace();   
     	  }   
     	    
     	}   
     	return RecordArray;  
    }

    
    /**
     * 數字金額轉國字金額:可以的話請直接使用numToZh(double val)
     * @param BigDecimal
     */
	public static String numToZh(BigDecimal bigdMoneyNumber)
	{
		return numToZh(bigdMoneyNumber.doubleValue());
	}
	
    /**
     * 數字金額轉國字金額:可以的話請直接使用numToZh(double val)
     * @param String
     */
	public static String numToZh(String value){
		return numToZh(Double.parseDouble(value));
	}

  /**
   * 數字金額轉國字金額
   * @param val double 金額
   * @return String
   */
	public static String numToZh(double val) {
		String HanDigiStr[] = new String[] {"零", "壹", "貳", "參", "肆", "伍", "陸", "柒", "捌", "玖"};
	    String HanDiviStr[] = new String[] {
	        "", "拾", "佰", "仟", "萬", "拾", "佰", "仟", "億",
	        "拾", "佰", "仟", "萬", "拾", "佰", "仟", "億",
	        "拾", "佰", "仟", "萬", "拾", "佰", "仟"};
	    String SignStr = "";
	    String TailStr = "";
	    long fraction, integer;
	    int jiao, fen;
	    String RMBStr = "", NumStr = "";
	    boolean lastzero = false;
	    boolean hasvalue = false; // 億、萬進位元前有數值標記
	    int len, n;
	    //數字金額判斷
	    if (val < 0) {
	      val = -val;
	      SignStr = "負";
	    }
	    if (val > 99999999999999.999 || val < -99999999999999.999) {
	      return "數值位數過大!";
	    }
	    // 四捨五入到分
	    long temp = Math.round(val * 100);
	    integer = temp / 100;
	    fraction = temp % 100;
	    jiao = (int) fraction / 10;
	    fen = (int) fraction % 10;
	    
	    if (jiao == 0 && fen == 0) {
	      TailStr = "整";
	    } else {
	    	TailStr = HanDigiStr[jiao];
	    	if (jiao != 0) {
	    		TailStr += "角";
	    	}
	    	if (integer == 0 && jiao == 0) { // 零元後不寫零幾分
	    		TailStr = "";
	    	}
	    	if (fen != 0) {
	    		TailStr += HanDigiStr[fen] + "分";
	    	}
	    }
	    
	    //判斷小數點前字串
	    NumStr = String.valueOf(integer);
	    len = NumStr.length();
	    if (len > 15) {
	      return "數值過大!";
	    }
	    for (int i = len - 1; i >= 0; i--) {
	      if (NumStr.charAt(len - i - 1) == ' ') {
	        continue;
	      }
	      n = NumStr.charAt(len - i - 1) - '0';
	      if (n < 0 || n > 9) {
	        return "輸入含非數位字元!";
	      }
	      if (n != 0) {
	        if (lastzero) {
	          RMBStr += HanDigiStr[0]; // 若干零後若跟非零值，只顯示一個零
	          // 除了億萬前的零不帶到後面
	          //if( !( n==1 && (i%4)==1 && (lastzero || i==len-1) ) )    // 如十進位前有零也不發壹音用此行
	        }
	        if (! (n == 1 && (i % 4) == 1 && i == len - 1)) { // 十進位處於第一位不發壹音
	          RMBStr += HanDigiStr[n];
	        }
	        RMBStr += HanDiviStr[i]; // 非零值後加進位，個位為空
	        hasvalue = true; // 置萬進位元前有值標記

	      }
	      else {
	        if ( (i % 8) == 0 || ( (i % 8) == 4 && hasvalue)) { // 億萬之間必須有非零值方顯示萬
	          RMBStr += HanDiviStr[i]; // “億”或“萬”
	        }
	      }
	      if (i % 8 == 0) {
	        hasvalue = false; // 萬進位元前有值標記逢億重定
	      }
	      lastzero = (n == 0) && (i % 4 != 0);
	    }
	    if (RMBStr.length() == 0) {
	      RMBStr = HanDigiStr[0]; // 輸入空字元或"0"，返回"零"
	    }
	    return SignStr + RMBStr + "元" + TailStr;
	}	
	
	
	//將日期加/
    public static String MaskDate(String sDate) {
    	String sMaskDate = "";
    	if ((sDate==null) || ("".equals(sDate))) {
    		return "";
    	}
    	else {
    		int iSLen = sDate.length();
    		if (iSLen <= 3) {
    			sMaskDate = sDate;
    		}
    		if ((iSLen > 3) && (iSLen <= 5)) {
    			sMaskDate = sDate.substring(0,3) + "/" +sDate.substring(3,iSLen);
    		}
    		if (iSLen > 5) {
    			sMaskDate = sDate.substring(0,3) + "/" +sDate.substring(3,5) + "/" + sDate.substring(5,iSLen);
    		}
    		return sMaskDate;
    	}
    }	
    
	//將日期加年月日
    public static String MaskCDate(String sDate) {
    	String sMaskDate = "";
    	if ((sDate==null) || ("".equals(sDate))) {
    		return "";
    	}
    	else {
    		int iSLen = sDate.length();
    		if (iSLen <= 3) {
    			sMaskDate = sDate;
    		}
    		if ((iSLen > 3) && (iSLen <= 5)) {
    			//sMaskDate = sDate.substring(0,3) + "年" +sDate.substring(3,iSLen);
    			if(!"0".equals(sDate.substring(0,1)))sMaskDate=sDate.substring(0,3)+"年";
        		else sMaskDate=sDate.substring(1,3)+"年";
        		if(!"0".equals(sDate.substring(3,4)))sMaskDate+=sDate.substring(3,iSLen)+"月";
        		else sMaskDate+=sDate.substring(4,iSLen)+"月";
    		}
    		if (iSLen > 5) {
    			//sMaskDate = sDate.substring(0,3) + "年" +sDate.substring(3,5) + "月" + sDate.substring(5,iSLen) + "日";
    			if(!"0".equals(sDate.substring(0,1)))sMaskDate=sDate.substring(0,3)+"年";
        		else sMaskDate=sDate.substring(1,3)+"年";
        		if(!"0".equals(sDate.substring(3,4)))sMaskDate+=sDate.substring(3,5)+"月";
        		else sMaskDate+=sDate.substring(4,5)+"月";
        		if(!"0".equals(sDate.substring(5,6)))sMaskDate+=sDate.substring(5,iSLen)+"日";
        		else sMaskDate+=sDate.substring(6,iSLen)+"日";
    		}
    		return sMaskDate;
    	}
    }    
    
    public static String getRemoteData(String urlString) { 
		StringBuffer document = new StringBuffer(); 
		try { 
		  java.net.URL url = new java.net.URL(urlString); 
		  java.net.URLConnection conn = url.openConnection(); 
		  BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
		  String line = null; 
		  while ((line = reader.readLine()) != null) 
		    document.append(line + "\n"); 
		  reader.close(); 
		} catch (java.net.MalformedURLException e) { 
		  System.out.println("Unable to connect to URL: " + urlString); 
		} catch (IOException e) { 
		  System.out.println("IOException when connecting to URL: " + urlString); 
		}
		return document.toString(); 
   }
    
   public static Vector ReadCsv(String csvFile){
	   Vector rowdata=new Vector();
	   try {
           BufferedReader br = new BufferedReader (
                   new FileReader (csvFile)
           );
           String line = br.readLine(); // 跳過第一行，字段名
           while ((line = br.readLine()) != null) {
                   StringTokenizer st = new StringTokenizer (line, ",");
                   Vector data=new Vector();
                   while (st.hasMoreTokens()) {
                	     data.addElement(st.nextToken());
                   }
                   rowdata.addElement(data);
                   
           }                
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
          
       }
       return rowdata;
   }
   
   public static String getQueryList(String filestoreLocation ,String filename){
	    String[] arrFileName;
		arrFileName = filename.split(":;:");
		String reStr="";
		try{
			DatabaseCvs cvs_db=new DatabaseCvs(filestoreLocation+File.separator+arrFileName[0]);
			ResultSet rs= cvs_db.querySQL("select * from "+arrFileName[1].substring(0,(arrFileName[1].length()-4)));
			while(rs.next()){
				reStr+=Common.sqlChar(rs.getString(1))+",";
			}
			rs.close();
			reStr=reStr.substring(0,reStr.length()-1);
		}catch (Exception e){
			System.out.println(e.toString());
		}
		//System.out.println(reStr);
		return reStr;
		
   }
   
   //因getQueryList此方法將文字檔資料打包好後，使用SQL的IN去多筆查詢資料時，
   //會有IN()裡面的值不可超過1000筆的限制。
	public static String getQueryListNoLimit(String filestoreLocation, String filename){
	   String[] arrFileName;
	   arrFileName = filename.split(":;:");
	   
	   int count = 999;  //每1000筆重新串IN
	   String reStr = " IN ( ";
	   try{
		   DatabaseCvs cvs_db=new DatabaseCvs(filestoreLocation+File.separator+arrFileName[0]);
		   ResultSet rs= cvs_db.querySQL("select * from "+arrFileName[1].substring(0,(arrFileName[1].length()-4)));
		   while(rs.next()){
			   reStr += Common.sqlChar(rs.getString(1)) + ",";
			   count--;
			   if(count == 0){  //每1000筆重新串IN
				   reStr = reStr.substring(0,reStr.length()-1);  //去除最後的逗點
				   reStr += " ) or IN ( ";
				   count = 999;
			   }
		   }
		   rs.close();
		   reStr = reStr.substring(0,reStr.length()-1);
		   reStr += " ) ";
	   }catch (Exception e){
		   System.out.println(e.toString());
	   }
	   return reStr;
	}
   
   /**
	 * 取得文字檔內容
	 * @param index 欲取得的欄位數量(文字檔中以逗號區隔)
	 * @return ArrayList
	 */
   public static ArrayList<String []> getFileArray(String filestoreLocation, String filename, int index){
	    String[] arrFileName = filename.split(":;:");
		ArrayList<String []> reList = new ArrayList<String []>();
		String temp[] = null;
		try{
			DatabaseCvs cvs_db=new DatabaseCvs(filestoreLocation+File.separator+arrFileName[0]);
			ResultSet rs= cvs_db.querySQL("select * from "+arrFileName[1].substring(0,(arrFileName[1].length()-4)));
			while(rs.next()){
				temp = new String[index];
				for(int i = 0; i < index; i++){
					temp[i] = Common.get(rs.getString(i+1));
				}
				reList.add(temp);
			}
			rs.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return reList;
	}
   
   public static void insertBS_LOG(String qry_date_start,String qry_time_start,String userid,String unitid,String uip,
		   String con,String qrystr,String rcv_yr,String rcv_word,String rcv_no,String sno,String sname,
		   String sno1,String sname1,String qry_oper,String lidn,String lname,String qry_purpose01,String qry_purpose02,
		   String qry_purpose03,String qry_purpose03a,String qry_seq, String qry_usertype){
	   Database sdb=new Database();
	    try{
			String[] Sqlstr=new String[1];
			Sqlstr[0]="insert into BS_LOG(qry_date_start,qry_time_start,userid,unitid,ip,con,qry,qry_msg,rcv_yr,rcv_word,rcv_no,";
			Sqlstr[0]+="sno,sname,sno1,sname1,qry_oper,lidn,lname,qry_purpose01,qry_purpose02,qry_purpose03,qry_purpose03a,qry_seq,qry_usertype)values(";
			Sqlstr[0]+=Common.sqlChar(qry_date_start)+","+Common.sqlChar(qry_time_start)+","+Common.sqlChar(userid)+","+Common.sqlChar(unitid)+",";
			Sqlstr[0]+=Common.sqlChar(uip)+","+Common.sqlChar(con)+","+Common.sqlChar(qrystr)+",'尚未完成查詢',"+Common.sqlChar(rcv_yr)+",";
		    Sqlstr[0]+=Common.sqlChar(rcv_word)+","+Common.sqlChar(rcv_no)+","+Common.sqlChar(sno)+","+Common.sqlChar(sname)+","+Common.sqlChar(sno1)+",";
		    Sqlstr[0]+=Common.sqlChar(sname1)+","+Common.sqlChar(qry_oper)+","+Common.sqlChar(lidn)+","+Common.sqlChar(lname)+","+Common.sqlChar(qry_purpose01)+",";
		    Sqlstr[0]+=Common.sqlChar(qry_purpose02)+","+Common.sqlChar(qry_purpose03)+","+Common.sqlChar(qry_purpose03a)+","+Common.sqlChar(qry_seq)+","+Common.sqlChar(qry_usertype)+")";
	        sdb.excuteSQL(Sqlstr);
	    }catch (Exception e){
			System.out.println(e.toString());
		}finally {
			sdb.closeAll();
		}
   }
   
   public static void updateBS_LOG(String qry_date_end,String qry_time_end,String qry_msg,String qry_seq){
	   Database sdb=new Database();
	   try{
			String[] Sqlstr=new String[1];
			Sqlstr[0]="update BS_LOG set qry_date_end="+Common.sqlChar(qry_date_end)+",";
			Sqlstr[0]+="qry_time_end="+Common.sqlChar(qry_time_end)+",";
			Sqlstr[0]+="qry_msg="+Common.sqlChar(qry_msg)+" where qry_seq="+Common.sqlChar(qry_seq);
			sdb.excuteSQL(Sqlstr);
		}catch (Exception e){
			System.out.println(e.toString());
		}finally {
			sdb.closeAll();
		}
   }
   
   
   public static void insertDL_LOG(String dqry_seq,String dqry_flag,String userid,String unitid,String dqry,String dqry_file_style,
		   							String dqry_date_start,String dqry_time_start){
	   Database sdb=new Database();
	    try{
			String[] Sqlstr=new String[1];
			Sqlstr[0]="insert into DOWNLOAD_LOG(dqry_seq,dqry_flag,dqry_userid,dqry_unitid,dqry,dqry_status,dqry_file_style,dqry_date_start,dqry_time_start";
			Sqlstr[0]+=")values(";
			Sqlstr[0]+=Common.sqlChar(dqry_seq)+","+Common.sqlChar(dqry_flag)+","+Common.sqlChar(userid)+","+Common.sqlChar(unitid)+",";
			Sqlstr[0]+=Common.sqlChar(dqry)+",'1',"+Common.sqlChar(dqry_file_style)+","+Common.sqlChar(dqry_date_start)+","+Common.sqlChar(dqry_time_start)+")";
			sdb.excuteSQL(Sqlstr);
	    }catch (Exception e){
			System.out.println(e.toString());
		}finally {
			sdb.closeAll();
		}
   }
   
   public static void updateDL_LOG(String dqry_status,String filename,String dqry_date_end,String dqry_time_end,String dqry_seq){
	   Database sdb=new Database();
	   try{
			String[] Sqlstr=new String[1];
			Sqlstr[0]="update DOWNLOAD_LOG set ";
			Sqlstr[0]+="dqry_file_name="+Common.sqlChar(filename)+",";
			Sqlstr[0]+="dqry_status="+Common.sqlChar(dqry_status)+",";
			Sqlstr[0]+="dqry_date_end="+Common.sqlChar(dqry_date_end)+",";
			Sqlstr[0]+="dqry_time_end="+Common.sqlChar(dqry_time_end)+" where dqry_seq="+Common.sqlChar(dqry_seq);
			System.out.println("[update DOWNLOAD_LOG]###1="+Sqlstr[0]);
			sdb.excuteSQL(Sqlstr);
	    }catch (Exception e){
			System.out.println(e.toString());
		}finally {
			sdb.closeAll();
		}
   }
   
   public static void updateDL_LOG(String dqry_status,String dqry_date_end,String dqry_time_end,String dqry_seq){
	   Database sdb=new Database();
	   try{
			String[] Sqlstr=new String[1];
			Sqlstr[0]="update DOWNLOAD_LOG set ";
			Sqlstr[0]+="dqry_status="+Common.sqlChar(dqry_status)+",";
			Sqlstr[0]+="dqry_date_end="+Common.sqlChar(dqry_date_end)+",";
			Sqlstr[0]+="dqry_time_end="+Common.sqlChar(dqry_time_end)+" where dqry_seq="+Common.sqlChar(dqry_seq);
			System.out.println("[update DOWNLOAD_LOG]###2="+Sqlstr[0]);
			sdb.excuteSQL(Sqlstr);
	    }catch (Exception e){
			System.out.println(e.toString());
		}finally {
			sdb.closeAll();
		}
   }
   
   public static void updateDLLOGByDL(String dqry_seq){
	   Database sdb=new Database();
	   try{
			String[] Sqlstr=new String[1];
			Sqlstr[0]="update DOWNLOAD_LOG set ";
			Sqlstr[0]+="dqry_status='3',";
			Sqlstr[0]+="download_date="+Common.sqlChar(Datetime.getYYYMMDD())+",";
			Sqlstr[0]+="download_time="+Common.sqlChar(Datetime.getFHHMMSS())+" where dqry_seq="+Common.sqlChar(dqry_seq);
			System.out.println("[update DOWNLOAD_LOG]###3="+Sqlstr[0]);
			sdb.excuteSQL(Sqlstr);
	    }catch (Exception e){
			System.out.println(e.toString());
		}finally {
			sdb.closeAll();
		}
   }
   
   /** 
    * 檔/檔夾壓縮成RAR格式 
    * rarName 壓縮後的壓縮檔案名(不包含尾碼) 
    * fileName 需要壓縮的檔案名(必須包含路徑) 
    * destDir 壓縮後的壓縮檔存放路徑
    * password 解壓密碼
    */
   public static String RARFile(String rarName, String fileName, String destDir,String password) {
   	String rarCmd ="C:\\WinRAR\\WinRAR.exe a -hp" + password + " -ibck -r -k " + destDir + rarName	+ ".rar " + destDir;
   	System.out.println("rarCmd"+rarCmd);
   	System.out.println("rarName"+rarName);
   	System.out.println("fileName"+fileName);
   	System.out.println("destDir"+destDir);
   	Process p = null;
   	int exitVal = 0;
   	try {
   		Runtime rt = Runtime.getRuntime();
   		System.out.println("KD:"+password);
   		System.out.println(rarCmd);
   		p = rt.exec(rarCmd);
   		exitVal = p.waitFor();
   		if (exitVal == 0)
   		p.destroy();//結束程式佔用
   	} catch (Exception e) {
   		System.out.println(e.getMessage());
   	} finally {
   		if (exitVal == 0)
   		p.destroy();//結束程式佔用
   	}
   	return rarName;
   }
   
   
   /** 
    * 檔/檔夾壓縮成ZIP格式 
    * rarName 壓縮後的壓縮檔案名(不包含尾碼) 
    * fileName 需要壓縮的檔案名(必須包含路徑) 
    * destDir 壓縮後的壓縮檔存放路徑
    * password 解壓密碼
    */
   public static String ZIPFile(String rarName, String fileName, String destDir,String password) {
	   try{
		   ZipFile zipFile=new ZipFile(destDir+rarName+".zip");//壓縮檔放置路徑
		   String folderToAdd=destDir;//要壓縮的資料夾
		   
		   ZipParameters parameters=new ZipParameters();//設定壓縮檔的參數
		   
		   //COMP_DEFLATE=壓縮,COMP_STORE=只打包不壓縮,COMP_AES_ENC=使用AES加密壓縮
		   parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		   
		   //壓縮等級
		   parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		   
		   //使用串流方式載入檔案
		   //parameters.setSourceExternalStream(true);
		   
		   //將壓縮檔加密
		   parameters.setEncryptFiles(true);
		   
		   //定義壓縮檔加密方式
		   parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
		   
		   //設定密碼
		   parameters.setPassword(password);
		   
		   zipFile.addFolder(folderToAdd, parameters);
	   }catch(Exception e){
		   e.printStackTrace();
		   System.out.println(e.getMessage());
	   }finally{
		   
	   }
	   return rarName;
   }
   
    public static void logEFORM3_5_LOG(String loginUserID, String userIP,
                                       String datetime, String dbUser,
                                       String tablename, String create_sql,
                                       String exception) {
        Database sdb=new Database();        
        try{
            String sql =
                    "   INSERT INTO EFORM3_5_LOG "
                    + "   (USER_ID, USER_IP, DO_DATETIME, DO_TYPE, "
                    + "    DO_ORAUSER, RECRE_IDX_TAB, RECRE_IDX_SQL, RECRE_EXCEPTION) "
                    + " VALUES "
                    + "   ('" + loginUserID + "', "
                    + "    '" + userIP + "', "
                    + "    '" + datetime + "', "
                    + "    'RECRE', "
                    + "    '" + dbUser + "', "
                    + "    '" + tablename + "', "
                    + "    '" + create_sql + "', "
                    + "    '" + exception + "') ";
            sdb.excuteSQL(new String[]{sql});
        }
        catch(Exception e){
            e.printStackTrace();
        }
       
   }
    
    /**
     * <br>
     * <br>目的：取得帳號是否為管理者(isManager為Y)
     * <br>參數：(1)sql字串 (2)被選的value <br>
     * <br>傳回：加上html option element 
     */
    static public String getUserIsManager(String pUserId,String pUnit) {
        String ret="";
        Database db = new Database();
        try {
        	String sql="select ISNULL(isManager,'N')as isManager from etecuser "
        			+ "where ISNULL(isStop,'N')<>'Y' "
        			+ "and user_id= "+Common.sqlChar(Common.checkSet(pUserId))
        			+ "and unit="+Common.sqlChar(Common.checkSet(pUnit))
        			+"";
        	ResultSet rs = db.querySQL(sql);
            if (rs.next()) {
            	ret = Common.get(rs.getString("isManager"));
            }
            rs.getStatement().close();
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
 			db.closeAll();
        }        
        return ret;
    }

}