package util;

import java.util.Calendar;
import java.util.regex.*;

/**
 * 
 * @version 1.0, Jul 7, 2006
 * @author clive.chang
 * @since TAJK Project
 */

public class Validate {
	
	public static String checkDate(String obj, String cName) {
		if (Common.get(obj).equals(""))
			return "";

		if ((obj != null) && (obj.trim().length() == 7)) {
			obj = obj.trim();
			try {
				if (Double.isNaN(Double.parseDouble(obj)))
					return "資料" + obj + "型態不符！";
				;
			} catch (NumberFormatException e) {
				return "資料" + obj + "型態不符！";
			}
			int y = Integer.parseInt(obj.substring(0, 3));
			int m = Integer.parseInt(obj.substring(3, 5));
			int d = Integer.parseInt(obj.substring(5));

			if (y < 0 || y > 999) {
				return cName + "年份格式不符!請輸入正確的民國日期(YYYMMDD)\n";
			}

			if (m < 0 || m > 12) {
				return cName + "月份格式不符!請輸入正確的民國日期(YYYMMDD)\n";
			}

			if (d < 0 || d > 31) {
				return cName + "日期格式不符!請輸入正確的民國日期(YYYMMDD)\n";
			} else {
				Calendar cal = Calendar.getInstance();
				cal.set(y + 1911, m - 1, d);
				if ((cal.get(Calendar.MONTH) != (m - 1))
						|| (cal.get(Calendar.DATE) != d)) {
					return cName + "日期格式不符!請輸入正確的民國日期(YYYMMDD)\n";
				}
			}

		} else {
			return cName + "日期格式不符!請輸入正確的民國日期(YYYMMDD)\n";
		}
		return "";
	}

	public static boolean checkDate(String obj) {
		if ((obj != null) && (obj.trim().length() == 7)) {
			obj = obj.trim();
			try {
				if (Double.isNaN(Double.parseDouble(obj)))
					return false;
			} catch (NumberFormatException e) {
				return false;
			}
			int y = Integer.parseInt(obj.substring(0, 3));
			int m = Integer.parseInt(obj.substring(3, 5));
			int d = Integer.parseInt(obj.substring(5));

			if (y < 0 || y > 999)
				return false;
			if (m < 0 || m > 12)
				return false;

			if (d < 0 || d > 31) {
				return false;
			} else {
				Calendar cal = Calendar.getInstance();

				// Java's Month Start with 0, therefor --> m-1
				cal.set(y + 1911, m - 1, d);
				if ((cal.get(Calendar.MONTH) != (m - 1))
						|| (cal.get(Calendar.DATE) != d)) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	/*
	 * 函數功能：檢查資料年月是否正確(YYYMM)
	 */
	public static boolean checkYYYMM(String s) {
		try {
			if (Double.isNaN(Double.parseDouble(s)))
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
		if (s.length() != 5)
			return false;
		int year = Integer.parseInt(s.substring(0, 3));
		int month = Integer.parseInt(s.substring(3, 5));
		if ((year + 1911) > 3000 || (year + 1911) < 1900)
			return false;
		else if (month > 12 || month < 1) {
			return false;
		}
		return true;
	}

	/*
	 * 函數功能：檢查資料是否整數
	 */
	public static boolean checkInt(String s) {
		if (s.equals(""))
			return true;
		try {
			if (Double.isNaN(Double.parseDouble(s)))
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
		if (s.indexOf(".") > 0) {
			return false;
		}
		return true;
	}

	/*
	 * 函數功能：檢查資料是否為浮點數 參 數：s物件本身; I整數幾位; F小數點幾位
	 */
	public static boolean checkFloat(String s, int I, int F) {
		if (s.equals(""))
			return true;
		try {
			if (Double.isNaN(Double.parseDouble(s)))
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
		if (s.indexOf(".") == -1) {
			if (s.length() > I)
				return false;
		} else {
			if (F == 0)
				return false;
			if (s.indexOf(".") > I)
				return false;
			if (s.substring(s.indexOf(".") + 1, s.length()).length() > F)
				return false;
		}
		return true;
	}

	public static boolean checkYN(String s) {
		if ("Y".equals(s) || "N".equals(s))
			return true;
		else
			return false;
	}

	public static boolean checkAlphaInt(String s) {
		s = Common.get(s);
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if ((ch < 'A' || ch > 'Z') && (ch < 'a' || ch > 'z')
					&& (ch < '0' || ch > '9'))
				return false;
		}
		return true;
	}

	private static final Pattern ASCII_PATTERN = Pattern.compile("[\\x00-\\x7F]*");	
	public static boolean isPureASCII(String msg) {
		boolean result = false;
		if (ASCII_PATTERN.matcher(msg).matches()) {
			result = true;
		}
		return result;
	}

	/**
	 * IP Address 檢查程式
	 * 
	 * @since 2006/07/19
	 */
	public static boolean isValidIPAddr(String msg) {
		boolean result = true;
		String[] tmp = msg.split(".");
		if (tmp.length <= 4) {
			for (int i = 0; i < tmp.length; i++) {
				if (Integer.parseInt(tmp[i]) > 255) {
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * 手機門號檢查程式
	 * 
	 * @since 2006/07/19
	 */
	public static final Pattern MSISDN_PATTERN = Pattern.compile("[+-]?\\d{10,12}");

	public static boolean isValidMSISDN(String msisdn) {
		boolean result = false;
		if (MSISDN_PATTERN.matcher(msisdn).matches()) {
			result = true;
		}
		return result;
	}

	public static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+\\.*\\w+@(\\w+\\.){1,5}[a-zA-Z]{2,3}$");

	/**
	 * Email 格式檢查程式
	 * 
	 * @since 2006/07/19
	 */
	public static boolean isValidEmail(String email) {
		boolean result = false;
		if (EMAIL_PATTERN.matcher(email).matches()) {
			result = true;
		}
		return result;
	}

	public static final Pattern TWPID_PATTERN = Pattern.compile("[ABCDEFGHJKLMNPQRSTUVXYWZIO][12]\\d{8}");

	/**
	 * 身分證字號檢查程式，身分證字號規則： 字母(ABCDEFGHJKLMNPQRSTUVXYWZIO)對應一組數(10~35)，
	 * 令其十位數為X1，個位數為X2；( 如Ａ：X1=1 , X2=0 )；D表示2~9數字 Y = X1 + 9*X2 + 8*D1 + 7*D2 +
	 * 6*D3 + 5*D4 + 4*D5 + 3*D6 + 2*D7+ 1*D8 + D9 如Y能被10整除，則表示該身分證號碼為正確，否則為錯誤。
	 * 臺北市(A)、臺中市(B)、基隆市(C)、臺南市(D)、高雄市(E)、臺北縣(F)、
	 * 宜蘭縣(G)、桃園縣(H)、嘉義市(I)、新竹縣(J)、苗栗縣(K)、臺中縣(L)、
	 * 南投縣(M)、彰化縣(N)、新竹市(O)、雲林縣(P)、嘉義縣(Q)、臺南縣(R)、
	 * 高雄縣(S)、屏東縣(T)、花蓮縣(U)、臺東縣(V)、金門縣(W)、澎湖縣(X)、 陽明山(Y)、連江縣(Z)
	 * 
	 * @since 2006/07/19
	 */
	public static boolean isValidTWPID(String twpid) {
		boolean result = false;
		String pattern = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
		if (TWPID_PATTERN.matcher(twpid.toUpperCase()).matches()) {
			int code = pattern.indexOf(twpid.toUpperCase().charAt(0)) + 10;
			int sum = 0;
			sum = (int) (code / 10) + 9 * (code % 10) + 8
					* (twpid.charAt(1) - '0') + 7 * (twpid.charAt(2) - '0') + 6
					* (twpid.charAt(3) - '0') + 5 * (twpid.charAt(4) - '0') + 4
					* (twpid.charAt(5) - '0') + 3 * (twpid.charAt(6) - '0') + 2
					* (twpid.charAt(7) - '0') + 1 * (twpid.charAt(8) - '0')
					+ (twpid.charAt(9) - '0');
			if ((sum % 10) == 0) {
				result = true;
			}
		}
		return result;
	}

	public static final Pattern TWBID_PATTERN = Pattern.compile("^[0-9]{8}$");

	/**
	 * 營利事業統一編號檢查程式 可至 http://www.etax.nat.gov.tw/ 查詢營業登記資料
	 * 
	 * @since 2006/07/19
	 */
	public static boolean isValidTWBID(String twbid) {
		boolean result = false;
		String weight = "12121241";
		boolean type2 = false; // 第七個數是否為七
		if (TWBID_PATTERN.matcher(twbid).matches()) {
			int tmp = 0, sum = 0;
			for (int i = 0; i < 8; i++) {
				tmp = (twbid.charAt(i) - '0') * (weight.charAt(i) - '0');
				sum += (int) (tmp / 10) + (tmp % 10); // 取出十位數和個位數相加
				if (i == 6 && twbid.charAt(i) == '7') {
					type2 = true;
				}
			}
			if (type2) {
				if ((sum % 10) == 0 || ((sum + 1) % 10) == 0) { // 如果第七位數為7
					result = true;
				}
			} else {
				if ((sum % 10) == 0) {
					result = true;
				}
			}
		}
		return result;
	}

	public static boolean isValidQueryString(String sqlStr) {
		if (sqlStr == null || sqlStr.length() <= 0 || sqlStr.indexOf("@") >= 0
				|| sqlStr.indexOf("'") >= 0 || sqlStr.indexOf("_") >= 0
				|| sqlStr.indexOf("\"") >= 0 || sqlStr.indexOf("%") >= 0
				|| sqlStr.indexOf("=") >= 0) {
			// "關鍵字不可包含 ( @ ' % \ _ = ) 等字元\n";
			return false;
		}
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(checkDate("0961231"));
		System.out.println(checkInt("0961231"));
		System.out.println(checkFloat("0961231.x", 7, 2));
		System.out.println(checkAlphaInt("0961231ABC"));
	}

}
