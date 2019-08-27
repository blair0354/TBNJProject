package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    
    public static String getMD5(String str) throws NoSuchAlgorithmException {
    	 // 生成一個MD5加密計算摘要
    	 MessageDigest md = MessageDigest.getInstance("MD5");
    	 // 計算md5函數
    	 md.update(str.getBytes());
    	 // digest()最後確定返回md5 hash值，返回值為8為字符串。因為md5 hash值是16位的hex值，實際上就是8位的字符
    	 // BigInteger函數則將8位的字符串轉換成16位hex值，用字符串來表示；得到字符串形式的hash值
    	 return new BigInteger(1, md.digest()).toString(16);
    	 
    	}

}
