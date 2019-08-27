/*
*<br>程式目的：登入及記錄使用者session資訊
*<br>程式代號：User
*<br>撰寫日期：93/12/01
*<br>程式作者：LG-T
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/
package util;

import java.sql.ResultSet;
import sys.ap.SYSAP003F_Permission;

public class User {

	//bena field , get and set method---------------------------------
	String userID;
	String userName;
	String password;
	String organID;
	String organName;
	String unitID;
	String unitName;
	String groupID;
	String organ_mark;
	
	String isOrganManager;
	String isAdminManager;	
	String isManager;
	String msg="I am new one~~";
	
	SYSAP003F_Permission permission[];

	public String getUserID(){ return Common.get(userID); }
	public void setUserID(String s){ userID=Common.set(s); }

	public String getUserName(){ return Common.get(userName); }
	public void setUserName(String s){ userName=Common.set(s); }	
	
	public String getPassword(){ return Common.get(password); }
	public void setPassword(String s){ password=Common.set(s); }

	public String getOrganID(){ return Common.get(organID); }
	public void setOrganID(String s){ organID=Common.set(s); }
	
	public String getOrganName(){ return Common.get(organName); }
	public void setOrganName(String s){ organName=Common.set(s); }

	public String getUnitID(){ return Common.get(unitID); }
	public void setUnitID(String s){ unitID=Common.set(s); }
	
	public String getUnitName(){ return Common.get(unitName); }
	public void setUnitName(String s){ unitName=Common.set(s); }	
	
	public String getIsOrganManager(){ return Common.get(isOrganManager); }
	public void setIsOrganManager(String s){ isOrganManager=Common.set(s); }
	
	public String getIsAdminManager(){ return Common.get(isAdminManager); }
	public void setIsAdminManager(String s){ isAdminManager=Common.set(s); }
	
	public String getGroupID(){ return Common.get(groupID); }
	public void setGroupID(String s){ groupID=Common.set(s); }

	public String getOrgan_mark(){ return Common.get(organ_mark); }
	public void setOrgan_mark(String s){ organ_mark=Common.set(s); }
	
	public String getIsManager(){ return Common.get(isManager); }
	public void setIsManager(String s){ isManager=Common.set(s); }	
	
	public SYSAP003F_Permission[] getPermission(){ return permission;}
	public void setPermission(SYSAP003F_Permission p[]){ permission=p;}
	
	
	
  	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		//this.msg = msg;
	}
	/**
  	 * <br>
  	 * <br>目的：檢查使用者ID及密碼
  	 * <br>參數：無
  	 * <br>傳回：傳回本物件
  	*/
	public User checkUser() throws Exception{
		Database db = new Database();
		User obj = this;
		try {
			String sql=" select a.empID, a.empPWD, a.empName, a.groupID, a.organID, a.unitID, a.isOrganManager, a.isAdminManager, a.isManager, a.organ_mark "+
					" from SYSAP_Emp a" +
				    " where a.empID = " + Common.sqlChar(userID) +
					" and a.empPWD = " + Common.sqlChar(Common.getMD5PassWord(password)) +
					" and a.isStop <> 'Y' " ;
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){		
				sql="select * from SYSCA_Organ where organID='"+rs.getString("organID")+"'";
				ResultSet org_rs=db.querySQL(sql);
				obj.setUserID(rs.getString("empID"));
				obj.setPassword(rs.getString("empPWD"));
				obj.setUserName(rs.getString("empName"));
				obj.setOrganID(rs.getString("organID"));
				if(org_rs.next()){
					obj.setOrganName(org_rs.getString("organSName"));
				}else{
					obj.setOrganName("");
				}
				obj.setUnitID(rs.getString("unitID"));
				obj.setUnitName(rs.getString("unitID"));
				obj.setGroupID(rs.getString("groupID"));
				//obj.setIsOrganManager(rs.getString("isOrganManager"));
				obj.setIsAdminManager(rs.getString("isAdminManager"));
				//obj.setIsManager(rs.getString("isManage"));
				obj.setOrgan_mark(rs.getString("organ_mark"));
				//obj.setPermission(getPermission(rs.getString("groupID")));
			}else{
				obj.setUserID("");
				obj.setPassword("");
				obj.setUserName("");
				obj.setOrganID("");
				obj.setOrganName("");
				obj.setUnitID("");
				obj.setUnitName("");
				obj.setGroupID("");
				obj.setIsOrganManager("");
				obj.setIsAdminManager("");
				obj.setIsManager("");
				obj.setOrgan_mark("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}			
		return obj;
	}
	
	
  	/**
  	 * <br>
  	 * <br>目的：檢查使用者ID及密碼
  	 * <br>參數：無
  	 * <br>傳回：傳回本物件
  	*/
	public static User getVerifiedUser(String userID, String password) throws Exception{
		Database db = new Database();
		try {
			String sql=" select a.empID, a.empPWD, a.empName, a.groupID, a.organID, a.unitID, a.isOrganManager, a.isAdminManager, a.isManager, a.organ_mark "+
					" from SYSAP_Emp a" +
				    " where a.empID = " + Common.sqlChar(userID) +
					" and a.empPWD = " + Common.sqlChar(Common.getMD5PassWord(password)) +
					" and (a.isStop is null or a.isStop <> 'Y') ";
			//System.err.println(sql);
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){	
				sql="select * from sysca_organ where organID='"+rs.getString("organID")+"'";
				ResultSet org_rs=db.querySQL(sql);
				User obj = new User();				
				obj.setUserID(rs.getString("empID"));
				obj.setPassword(rs.getString("empPWD"));
				obj.setUserName(rs.getString("empName"));
				obj.setOrganID(rs.getString("organID"));
				if(org_rs.next()){
					obj.setOrganName(org_rs.getString("organSName"));
				}else{
					obj.setOrganName("");
			    }
				obj.setUnitID(rs.getString("unitID"));
				obj.setUnitName(rs.getString("unitID"));
				obj.setGroupID(rs.getString("groupID"));
				obj.setIsOrganManager(rs.getString("isOrganManager"));
				obj.setIsAdminManager(rs.getString("isAdminManager"));
				obj.setIsManager(rs.getString("isManager"));
				obj.setOrgan_mark(rs.getString("organ_mark"));
				return obj;

			}						
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		} finally {
			db.closeAll();
		}			
		return null;
	}	
	
  	/**
  	 * <br>
  	 * <br>目的：變更密碼
  	 * <br>參數：無
  	 * <br>傳回：無
  	*/
	public void updatePWD(){
		Database db = new Database();
		try {
			String sql = " update SYSAP_Emp set empPWD = " + Common.sqlChar(Common.getMD5PassWord(password)) +
							" where empID = " + Common.sqlChar(userID) ;
			String[] sqlArray = {new String(sql)};	
			db.excuteSQL(sqlArray);	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}		
	}	
	
	
  	/**
  	 * <br>
  	 * <br>目的：依groupID取得權限
  	 * <br>參數：groupID
  	 * <br>傳回：permission[]
  	*/
	public SYSAP003F_Permission[] getPermission(String groupID){
		sys.ap.SYSAP003F_AUTH auth = new sys.ap.SYSAP003F_AUTH();
		return auth.getPermission(groupID);
	}
	
	public boolean checkProgPermission(User u,String progID){
		SYSAP003F_Permission p[]=u.getPermission();
		for (int i=0; i<p.length; i++){
			if (progID.equals(p[i].getProgramID())){
				return true;
			}
		}
		return false;
	}	

}


