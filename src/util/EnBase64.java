/*
*<br>程式目的：以Base64進行加解密
*<br>程式代號：EnBase64
*<br>撰寫日期：107/05/02
*<br>程式作者：blair
*<br>
*/
package util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.codec.binary.Base64;

public class EnBase64 {
	
	private EnBase64() {
		 // avoid instantiation...
	}
    
    /**
	 * <br>
	 * <br>目的：將字串進行Base64加密
	 * <br>參數：字串
	 * <br>傳回：傳回字串
	*/
    public static String encodeBASE64(String x) throws Exception{
	    final Base64 base64 = new Base64();
	    final String text = util.Common.get(x);
	    final byte[] textByte = text.getBytes("UTF-8");
	    //編碼
	    final String encodedText = base64.encodeToString(textByte);
	    return encodedText;
    }
    
    /**
	 * <br>
	 * <br>目的：將已進行base64加密的文字解密
	 * <br>參數：字串
	 * <br>傳回：傳回字串
	*/
    public static String decodeBASE64(String x) throws Exception{
	    final Base64 base64 = new Base64();
	    //解碼
	    final String encodedText=(new String(base64.decode(util.Common.get(x)), "UTF-8"));
	    return encodedText;
    }
}