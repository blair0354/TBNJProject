package eform.eform3_2;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Common;
import util.Database;
import util.Md5;

public class EFORM3_2 {
	//登入密碼
	String txtpassword;
	public String getTxtpassword() {return Common.get(txtpassword);}
	public void setTxtpassword(String txtpassword) {this.txtpassword = Common.get(txtpassword);}
	//確認密碼
	String txtpassword1;
	public String getTxtpassword1() {return Common.get(txtpassword1);}
	public void setTxtpassword1(String txtpassword1) {this.txtpassword1 = Common.get(txtpassword1);}
	//使用者email
	String txtuser_mail;
	public String getTxtuser_mail() {return Common.get(txtuser_mail);}
	public void setTxtuser_mail(String txtuser_mail) {this.txtuser_mail = Common.get(txtuser_mail);}
	//state
	String state;
	public String getState() {return Common.get(state);}
	public void setState(String state) {this.state = Common.get(state);}
	//user_id
	String user_id;
	public String getUser_id() {return Common.get(user_id);}
	public void setUser_id(String user_id) {this.user_id = Common.get(user_id);}
	
	
	
	
	public String doUpdate() {
		
		String msg = "";
		
		//將密碼轉換成MD5
		String pwdMD5 = "";
		try {
			pwdMD5 = Md5.getMD5(getTxtpassword());
			
			//System.out.println(pwdMD5);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		//1.查詢是否為最近3筆使用過密碼
		int cnt = checkNewPwd(getUser_id(),pwdMD5);
		//System.out.println(cnt);
		
		if(cnt > 0){
			msg = "不能使用最近三次的歷史密碼！";
		}
		else {
			//2.update etecuser
			if(updateEtecuser(pwdMD5, getTxtuser_mail(), getSystemTime(), getUser_id()) > 0) {
				//3.insert etecuser_log
				if(insertEtecuser_log(getUser_id(), pwdMD5, getSystemTime()) > 0) {
					String min_id = "";
					int id_cnt = 0;
					ArrayList<Map<String, String>> list = selectEtecuser_log(getUser_id());
					min_id = list.get(0).get("MIN_ID");
					id_cnt = Integer.parseInt(list.get(0).get("ID_CNT"));
					
					//4.id_cnt > 3  刪除最小號
					if(id_cnt > 3) {
						
						if(deleteEtecuser_log(getUser_id(), min_id) > 0) {
							msg = "更新成功!";
						}else {
							System.out.println("delete etecuser_log 刪除 0 筆資料");
						}
						
					}else {
						msg = "更新成功!";
					}
					
					
				}else {
					System.out.println("insert etecuser_log 寫入 0 筆資料");
				}
				
			}else {
				System.out.println("update etecuser 0 筆資料");
			}
			
			
		}
		
		
		setState("doUpdateSuccess");
		System.out.println("== " + msg + "==");
		return msg;
	}
	
	
	/**
	 * 查詢 欲修改的密碼是否存在 ETECUSER_LOG 中(近3筆)
	 * @param user_id
	 * @param pwdMD5
	 * @return
	 */
	private int checkNewPwd(String user_id, String pwdMD5) {
		Database database = new Database();
		ResultSet rs = null;
		String sqlStr = 
				"SELECT COUNT(*) CNT"
				+ " FROM ETECUSER_LOG"
					+ " WHERE 1 = 1"
					+ " AND USER_ID = " + Common.sqlChar(user_id)
					+ " AND PASSWORD = " + Common.sqlChar(pwdMD5);
		
		System.out.println("checkNewPwd:");
		System.out.println(sqlStr);
		int cnt = 0;
		
		try {
			rs = database.querySQL(sqlStr);
			
			while(rs.next()) {
				cnt = Integer.parseInt(rs.getString("CNT"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			database.closeAll();
		}
		
		return cnt;
	}
	
	
	/**
	 * 取得系統時間
	 * @return EX: 2018/3/14 上午 10:33:32
	 */
	public String getSystemDate() {
		Date date = new Date();
		DateFormat mediumFormat  = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
		
		return mediumFormat.format(date);
	}
	
	/**
	 * 取得系統時間 至微毫秒
	 * @return EX: 2018-03-20 14:14:56:26.704
	 */
	private String getSystemTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
		
		return sdf.format(date);
		
	}
	
	
	/**
	 * Query ETECUSER 
	 * @param uid (使用者)
	 * @return 
	 */
	public List<Map<String,String>> queryEtecuser(String uid) {
		
		Database database = new Database();
		ResultSet rs = null;
		String sqlStr = "SELECT * FROM ETECUSER WHERE 1 = 1 AND USER_ID = " + Common.sqlChar(uid);
		
		System.out.println("queryEtecuser:");
		System.out.println(sqlStr);
		
		List<Map<String, String>> row = new ArrayList<Map<String,String>>();
		try {
			rs = database.querySQL(sqlStr);
			//取得欄位數
			int columnCount = rs.getMetaData().getColumnCount();
			Map<String,String> column = null;
			while(rs.next()) {
				column = new HashMap<String,String>();
				for (int i = 1; i <= columnCount; i++) {
					column.put(rs.getMetaData().getColumnName(i), rs.getString(i));
				}
				row.add(column);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			database.closeAll();
		}
		
		return row;
	}
	
	
	/**
	 * update 使用者 password email
	 * @param passowrd
	 * @param user_mail
	 * @param user_login_time
	 * @param user_id
	 * @return
	 */
	private int updateEtecuser(String passowrd, String user_mail, String user_login_time, String user_id) {
		
		String sql = 
				"UPDATE ETECUSER"
				+ " SET PASSWORD = '" + passowrd + "',"
				+ " USER_MAIL='" + user_mail + "',"
				+ " USER_LOGIN_TIME = '" + user_login_time + "'"
					+ " WHERE USER_ID='" + user_id + "'";
		System.out.println("updateEtecuser:");
		System.out.println(sql);
		
		Database database = new Database();
		
		int rs = 0;
		
		try {
			
			rs = database.getConnection().createStatement().executeUpdate(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			database.closeAll();
		}
		
		return rs;
	}
	
	/**
	 * insert 使用者 修改後資料至 electuser_log
	 * @param user_id
	 * @param password
	 * @param update_time
	 * @return
	 */
	private int insertEtecuser_log(String user_id,String password, String update_time) {
		Database database = new Database();
		int rs = 0;
		String sql = 
				"INSERT INTO ETECUSER_LOG "
						+ "(USER_ID, PASSWORD, UPDATE_TIME) VALUES "
						+ "(" + Common.sqlChar(user_id) 
						+ "," + Common.sqlChar(password) 
						+ "," + Common.sqlChar(update_time)+ ")";
		
		System.out.println("insertEtecuser_log:");
		System.out.println(sql);
		
		try {
			rs = database.getConnection().createStatement().executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			database.closeAll();
		}
		
		return rs;
		
	}
	
	/**
	 * 查詢 etecuser_log 最小號 & 筆數 
	 * @param user_id
	 * @return
	 */
	private ArrayList<Map<String,String>> selectEtecuser_log(String user_id) {
		Database database = new Database();
		ResultSet rs = null;
		ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String sql = 
				"SELECT MIN(ID) AS MIN_ID, COUNT(*) AS ID_CNT"
						+ " FROM ETECUSER_LOG"
							+ " WHERE USER_ID=" + Common.sqlChar(user_id);
		
		System.out.println("selectEtecuser_log:");
		System.out.println(sql);
		
		try {
			rs = database.querySQL(sql);
			
			while(rs.next()) {
				Map<String,String> map = new HashMap<String,String>();
				
				map.put("MIN_ID", rs.getString("MIN_ID"));
				map.put("ID_CNT", rs.getString("ID_CNT"));
				
				list.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			database.closeAll();
		}
		
		return list;
		
	}
	
	
	private int deleteEtecuser_log(String user_id, String min_id) {
		Database database  = new Database();
		int rs = 0;
		String sql = 
				"DELETE FROM ETECUSER_LOG"
						+ " WHERE USER_ID=" + Common.sqlChar(user_id)
						+ " AND ID= " + Common.sqlChar(min_id);
		System.out.println("deleteEtecuser_log:");
		System.out.println(sql);
		
		try {
			rs = database.getConnection().createStatement().executeUpdate(sql);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			database.closeAll();
		}
		
		return rs;
	}
}
