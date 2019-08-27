package eform.index;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.Common;
import util.Database;
import util.Md5;

public class INDEX {
	
	private String txtusr;
	private String txtuserword;
	private String state = "init";

	
	
	public String getTxtusr() {
		return Common.checkGet(txtusr);
	}
	public void setTxtusr(String txtusr) {
		this.txtusr = Common.checkSet(txtusr);
	}
	public String getTxtuserword() {
		return Common.checkGet(txtuserword);
	}
	public void setTxtuserword(String txtuserword) {
		this.txtuserword = Common.checkSet(txtuserword);
	}
	public String getState() {
		return Common.checkGet(state);
	}
	public void setState(String state) {
		this.state = Common.checkSet(state);
	}
	
	
	private String getMd5(String passwd) {
		String md5 = "";
		try {
			md5 = Md5.getMD5(getTxtuserword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}
	
	
	public ArrayList<Map<String,String>> selectEtecuser() {
		String sql = 
				"select a.user_id,a.User_name,substring(a.unit,1,1)as city_no ,a.unit\n" + 
				",ISNULL(a.isStop,'N')as isStop,ISNULL(a.isManager,'N')as isManager\n" + 
				",(CASE WHEN \n" + 
				"            DATEADD(mm,3,\n" + 
				"                         ISNULL((select top 1 max(s.update_time) \n" + 
				"				from etecuser_log s where s.user_id=a.user_id),0)\n" + 
				"           )\n" + 
				"           <GETDATE()\n" + 
				"  THEN 'Y' ELSE 'N' END )as isOverdue\n" + 
				"from etecuser a where a.user_id='" + getTxtusr() + "' and a.password='" + getMd5(getTxtuserword()) + "'\n" + 
				"order by user_id";
		
		System.out.println("[INDEX.selectEtecuser]\n" + sql);
		
		Database db = new Database();
		ResultSet rs = null;
		ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			rs = db.querySQL(sql);
			while(rs.next()) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("user_id", rs.getString("user_id"));
				map.put("user_name", rs.getString("user_name"));
				map.put("city_no", rs.getString("city_no"));
				map.put("unit", rs.getString("unit"));
				map.put("unitid", rs.getString("unit"));//部份舊程式仍採用unitid
				map.put("isStop", rs.getString("isStop"));
				map.put("isManager", rs.getString("isManager"));
				map.put("isOverdue", rs.getString("isOverdue"));
				
				
				list.add(map);
				setState("loginSuccess");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		
		return list;
	}
	
}
