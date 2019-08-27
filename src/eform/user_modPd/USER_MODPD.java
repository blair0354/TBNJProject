package eform.user_modPd;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import util.Common;
import util.Database;
import util.Md5;

public class USER_MODPD {
	
	private String txtAccNum;
	private String txtAccMIL;
	private String oldAccPwd;
	private String txtAccPwd;
	private String state = "init";
	
	
	public String getTxtAccNum() {
		return Common.get(txtAccNum);
	}


	public void setTxtAccNum(String txtAccNum) {
		this.txtAccNum = Common.checkGet(txtAccNum);
	}


	public String getTxtAccMIL() {
		return Common.checkGet(txtAccMIL);
	}


	public void setTxtAccMIL(String txtAccMIL) {
		this.txtAccMIL = Common.checkGet(txtAccMIL);
	}


	public String getOldAccPwd() {
		return Common.checkGet(oldAccPwd);
	}


	public void setOldAccPwd(String oldAccPwd) {
		this.oldAccPwd = Common.checkGet(oldAccPwd);
	}


	public String getTxtAccPwd() {
		return Common.checkGet(txtAccPwd);
	}


	public void setTxtAccPwd(String txtAccPwd) {
		this.txtAccPwd = Common.checkGet(txtAccPwd);
	}
	
	public String getState() {
		return Common.checkGet(state);
	}


	public void setState(String state) {
		this.state = Common.checkGet(state);
	}

	
	
	
	
	
	
	

	private String getMd5(String password) {
		
		try {
			password = Md5.getMD5(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return password;
	}
	
	
	public void selectEtecuser(String user_id,String password) {
		String sql = 
				"select a.user_id,a.user_mail,a.password from etecuser a\n" + 
				"	where a.user_id='" + user_id + "'\n" + 
				"	and a.password='" + getMd5(password)+ "'"; 
		
		System.out.println("[USER_MODPD.selectEtecuser]\n"+sql);
		
		Database db = new Database();
		ResultSet rs = null;
		
		try {
			rs = db.querySQL(sql);
			while(rs.next()) {
				setTxtAccNum(rs.getString("user_id"));
				setTxtAccMIL(rs.getString("user_mail"));
				setOldAccPwd(password);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
	}
	
	
	
	public String doUpdate (String user_id,String password,String newPassword,String email) {
		
		String result = "";
		
		//先檢查是否停用
		String isStop = queryIsStop(user_id, password);
		
		if("Y".equals(isStop)) {
			return "此帳號已被停用！";
		}
		
		if(queryIsPass(user_id, password)){
			return "帳號及登入舊密碼錯誤！";
		}
		
		//檢查是否是近期3次密碼
		int cnt = queryIsUsed(user_id, newPassword);
		if(cnt > 0) {
			return "不能使用最近三次的歷史密碼！";
		}
		
		
		//更新密碼
		if(updateEtecuser(getMd5(newPassword), email, getSystemTime(), user_id) > 0) {
			
			//寫入 Etecuser_log
			if(insertEtecuser_log(user_id, getMd5(newPassword), getSystemTime()) > 0) {
				
				String min_id = "";
				int id_cnt = 0;
				ArrayList<Map<String, String>> list = selectEtecuser_log(user_id);
				min_id = list.get(0).get("MIN_ID");
				id_cnt = Integer.parseInt(list.get(0).get("ID_CNT"));
				
				//4.id_cnt > 3  刪除最小號
				if(id_cnt > 3) {
					
					if(deleteEtecuser_log(user_id, min_id) > 0) {
						result = "更新成功!";
					}else {
						System.out.println("delete etecuser_log 刪除 0 筆資料");
					}
					
				}else {
					result = "更新成功!";
				}
				
			}else {
				System.out.println("insert etecuser_log 寫入 0 筆資料");
			}
		}else {
			System.out.println("update etecuser 0 筆資料");
		}
		
		
		
		return result;
	}
	
	
	/**
	 * 檢查是否停用
	 * @param user_id
	 * @param password
	 * @return
	 */
	private String queryIsStop(String user_id,String password) {
		String sql = 
				"select ISNULL(a.isStop,'N')as isStop from etecuser a \n" + 
				"	where a.user_id='" + user_id + "'\n" + 
				"	and a.password='" +  getMd5(password) + "'"; 
		System.out.println("[USER_MODPD.queryIsStop]\n" + sql);
		
		Database db = new Database();
		ResultSet rs = null;
		
		String result = "";
		
		try {
			
			rs = db.querySQL(sql);
			
			while(rs.next()) {
				result=  rs.getString("isStop");
			}
			rs.getStatement().close();
			rs.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		
		return result;
		
	}
	
	/**
	 * 檢查帳號及舊密碼是否正確
	 * @param user_id
	 * @param password
	 * @return
	 */
	private boolean queryIsPass(String user_id,String password) {
		boolean retBol=true;
		String sql = 
				"select user_id from etecuser a \n" + 
				"	where a.user_id='" + user_id + "'\n" + 
				"	and a.password='" +  getMd5(password) + "'"; 
		System.out.println("[USER_MODPD.queryIsStop]\n" + sql);
		
		Database db = new Database();
		ResultSet rs = null;
		
		try {
			
			rs = db.querySQL(sql);
			
			while(rs.next()) {
				retBol= false;
			}
			rs.getStatement().close();
			rs.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		
		return retBol;
		
	}
	
	
	private int queryIsUsed(String user_id,String newPassword) {
		int result = 0;
		
		String sql = 
				"select count(*) as cnt from etecuser_log\n" + 
						"  where user_id='" + user_id + "'\n" + 
						"  and password='" + getMd5(newPassword) + "'";
		System.out.println("[USER_MODPD.queryIsUsed]\n" + sql);
		
		Database db = new Database();
		ResultSet rs = null;
		
		try {
			
			rs = db.querySQL(sql);
			
			while(rs.next()) {
				result = Integer.parseInt(rs.getString("cnt"));
			}
			rs.getStatement().close();
			rs.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		
		
		return result;
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
