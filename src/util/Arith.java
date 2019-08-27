/*
*<br>程式目的：商用數字計算
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：TzuYi.Yang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/
package util;

import java.math.BigDecimal;

public class Arith {
	/**
	 * 提供精確的加法運算。
	 * @param v1  被加數
	 * @param v2  加數
	 * @return  兩個參數的和
	 */
	public static String add(String v1, String v2) {
		if(v1 == null || v1.equals("")){ v1 = "0"; }
		if(v2 == null || v2.equals("")){ v2 = "0"; }
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).toString();
	}

	/**
	 * 提供精確的減法運算。
	 * @param v1  被減數
	 * @param v2  減數
	 * @return  兩個參數的差
	 */
	public static String subtract(String v1, String v2) {
		if(v1 == null || v1.equals("")){ v1 = "0"; }
		if(v2 == null || v2.equals("")){ v2 = "0"; }
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2).toString();
	}

	/**
	 * 提供精確的乘法運算。
	 * @param v1  被乘數
	 * @param v2  乘數
	 * @return  兩個參數的積
	 */
	public static String multiply(String v1, String v2) {
		if(v1 == null || v1.equals("")){ v1 = "0"; }
		if(v2 == null || v2.equals("")){ v2 = "0"; }
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).toString();
	}

	/**
	 * 提供（相對）精確的除法運算。當發生除不盡的情況時，由scale參數指
	 * 定精度，以後的數字四捨五入。
	 * @param v1  被除數
	 * @param v2  除數
	 * @param scale  表示表示需要精確到小數點以後幾位。
	 * @return  兩個參數的商
	 */
	public static String divide(String v1, String v2, int scale) {
		if(v1 == null || v1.equals("")){ v1 = "0"; }
		if(v2 == null || v2.equals("")){ v2 = "0"; }
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 提供精確的小數位四捨五入處理。
	 * @param v  需要四捨五入的數字
	 * @param scale  小數點後保留幾位
	 * @return  四捨五入後的結果
	 */
	public static String round(String v, int scale) {
		if(v == null || v.equals("")){ v = "0"; }
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();
	}
}