package sys.ap;

import java.sql.ResultSet;

import util.Database;
import util.Common;
import util.dtree.TreeEntry;
import sys.ap.SYSAP003F_Permission;

public class SYSAP003F_AUTH extends SYSAP002F {	
	String[] auth;
	String[] authed;
	String empID;	
	String optype;
	String hideAssigned;	
	String authoritytype;	
	SYSAP003F_Permission[] permission;
	
	public String getHideAssigned() {return hideAssigned;}
	public void setHideAssigned(String s) {hideAssigned = s; }	
	public void setAuth(String[] s) {auth = s;}
	public String[] getAuth() {return auth;}
	
	public void setAuthed(String[] s) {authed = s;}
	public String[] getAuthed() {return authed;}	
	
	public void setEmpID(String s) { empID = checkSet(s); }
	public String getEmpID() { return checkGet(empID); }
	
	public String getOptype() {return optype;}
	public void setOptype(String s) {optype = s;}	
	public String getAuthoritytype() {return checkGet(authoritytype);}
	public void setAuthoritytype(String s) {authoritytype = checkSet(s);}	
	
	public SYSAP003F_Permission[] getPermission(){ return permission;}
	public void setPermission(SYSAP003F_Permission p[]){ permission=p;}	
	
	public String buildCheckBoxTree(String treeName, String jsFunctionName, boolean bUrl, boolean bRootCheckBox) {		
		StringBuffer sb = new StringBuffer(1024).append("");
		if (Common.get(treeName).equals("")) treeName = "功能選單";
		sb.append("d.add(");			
		sb.append("'0','-1','");		
		if (bRootCheckBox) {
			sb.append("<input type=checkbox id=0 name=auth class=checkbox onclick=");
			sb.append(jsFunctionName).append("(this,\"").append(treeName).append("\") value=0>");			
		}		
		sb.append(treeName).append("'");
		if (bUrl) sb.append(",'").append("dTreeForm.jsp?sid=1&fid=-1'");
		sb.append(");\n");

		util.dtree.TreeEntry dt = new util.dtree.TreeEntry();
		java.util.Iterator it = dt.getTreeEntry().iterator();		
		while (it.hasNext()) {
			//id, pid, name, url, title, target, icon, iconOpen, open, obj			
			TreeEntry e = (TreeEntry) it.next();
			sb.append("d.add(");			
			sb.append("'").append(e.getId()).append("',");	
			sb.append("'").append(e.getPid()).append("',");
			sb.append("'<input type=checkbox id=").append(e.getId()).append(" name=auth class=checkbox onclick=").append(jsFunctionName).append("(this,\"").append(e.getName()).append("\") value=").append(e.getId()).append(">").append(e.getName()).append("'");
			if (bUrl) sb.append(",'").append("dTreeForm.jsp?sid=").append(e.getId()).append("&fid=").append(e.getPid()).append("'");
			sb.append(");\n");
		}
		return sb.toString();
	}	
	
	
  	/**
  	 * <br>
  	 * <br>目的：組出某個群組所擁有的樹 + 權限CSS顏色
  	 * <br>參數：treeID, treeName, jsFunctionName, checkboxName, checkboxIdPrefix, groupID, bIncludeAll, bUrl, bRootCheckBox
  	 * <br>傳回：組合後的javascript = String
  	 * <br>舉例:bildCheckBox("d","功能選單","auth","","checkBoxClick","test",true,false,false)
  	*/
	public String buildCheckBoxTree(String treeID, String treeName, String checkboxName, String checkboxPrefix, String jsFunctionName, String groupID, boolean bIncludeAll, boolean bUrl, boolean bRootCheckBox) {		
		SYSAP003F_Permission[] p = getPermission(groupID);

		StringBuffer sb = new StringBuffer(1024).append("");
		if (Common.get(treeName).equals("")) treeName = "功能選單";
		sb.append(treeID).append(".add(");
		sb.append("'0','-1','");
		if (bRootCheckBox) {
			sb.append("<input type=checkbox id=0 name=auth class=checkbox onclick=");
			sb.append(jsFunctionName).append("(this,\"").append(treeName).append("\") value=0>");			
		}		
		sb.append(treeName).append("'");
		if (bUrl) sb.append(",'").append("dTreeForm.jsp?sid=1&fid=-1'");
		sb.append(");\n");
		
		util.dtree.TreeEntry dt = new util.dtree.TreeEntry();
		java.util.Iterator it;		
		if (bIncludeAll) it = dt.getTreeEntry().iterator();
		else it = dt.getTreeEntry(groupID).iterator();
		
		while (it.hasNext()) {
			//id, pid, name, url, title, target, icon, iconOpen, open, obj			
			TreeEntry e = (TreeEntry) it.next();
			sb.append(treeID).append(".add(");		
			sb.append("'").append(e.getId()).append("',");	
			sb.append("'").append(e.getPid()).append("',");
			sb.append("'<input type=checkbox id=").append(checkboxPrefix).append(e.getId()).append(" name=").append(checkboxName).append(" class=checkbox onclick=").append(jsFunctionName).append("(this,\"").append(e.getName()).append("\") value=").append(e.getId()).append(">");
			
			boolean isAuthorize = false;
			String cssClass = "";
			for(int i=0;i<p.length;i++){
				if(p[i].getProgramID().equals(e.getId())) {
					isAuthorize = true;
					cssClass = p[i].getAuthority();
					break;
				}				
			}
			if (isAuthorize) sb.append("<span class=").append(cssClass).append(">").append(e.getName()).append("</span>'");
			else sb.append(e.getName()).append("'");
			
			if (bUrl) sb.append(",'").append("dTreeForm.jsp?sid=").append(e.getId()).append("&fid=").append(e.getPid()).append("'");
			sb.append(");\n");
		}
		return sb.toString();
	}	
	
	
  	/**
  	 * <br>
  	 * <br>目的：組出某個群組所擁有的權限樹
  	 * <br>參數：treeID, treeName, groupID, isAdmin
  	 * <br>傳回：組合後的javascript = String
  	 * <br>舉例:buildAuthorizeMenu("d","功能選單","test", "N")
  	*/
	public String buildAuthorizeMenu(String treeID, String treeName, String groupID, String isAdmin) {
		StringBuffer sb = new StringBuffer(1024).append("");
		if (Common.get(treeName).equals("")) treeName = "功能選單";
		sb.append(treeID).append(".add(");
		sb.append("'0','-1','").append(treeName).append("');\n");
		
		util.dtree.TreeEntry dt = new util.dtree.TreeEntry();
		java.util.Iterator it;

		if (isAdmin!=null && isAdmin.equals("Y")) it = dt.getTreeEntry().iterator();
		else it = dt.getTreeEntry(groupID).iterator();
		
		while (it.hasNext()) {
			//id, pid, name, url, title, target, icon, iconOpen, open, obj	
			TreeEntry e = (TreeEntry) it.next();
			sb.append(treeID).append(".add(");
			sb.append("'").append(e.getId()).append("',");	
			sb.append("'").append(e.getPid()).append("','");
			sb.append(e.getName()).append("','");
			if (e.getUrl().indexOf('?')!=-1) {
				sb.append(e.getUrl()).append("&progID=").append(e.getId());
			} else {
				sb.append(e.getUrl()).append("?progID=").append(e.getId());
			}
			sb.append("','").append(e.getTitle()).append("','").append(e.getTarget()).append("');\n");
		}
		return sb.toString();
	}		
		
	public void updateAuth() {		
		if (optype!=null && optype.length()>0) {
			Database db = new Database();
			String sql = "";
			try {				
				db.setAutoCommit(false);				
				if("add".equals(optype) && auth!=null && auth.length>0){
					int i = 0;				
					for (i=0; i<auth.length; i++) {
						sql = " delete from sysap_authority where progID=" + Common.sqlChar(auth[i]) + " and groupID=" + Common.sqlChar(groupID);
						db.exeSQL(sql);
						sql = " insert into sysap_authority(groupID,progID,auth)values(" +
							Common.sqlChar(groupID) + "," +
							Common.sqlChar(auth[i]) + "," +
							Common.sqlChar(authoritytype) + ")";							
						db.exeSQL(sql);
					}
					this.setErrorMsg("新增/修改權限成功");
				}else if("remove".equals(optype) && authed!=null && authed.length>0){
					int i = 0;				
					for (i=0; i<authed.length; i++) {
						sql = " delete from sysap_authority where progID=" + Common.sqlChar(authed[i]) + " and groupID=" + Common.sqlChar(groupID);
						db.exeSQL(sql);
					}					
					this.setErrorMsg("刪除權限成功");
				}
				db.doCommit();
			} catch (Exception e) {
				try {
					db.doRollback();
				} catch (Exception e1) {
					e1.printStackTrace();
				}				
				e.printStackTrace();
			} finally {
				db.closeAll();
			}
		}
	}
	
  	/**
  	 * <br>
  	 * <br>目的：依groupID取得權限
  	 * <br>參數：groupID
  	 * <br>傳回：permission[]
  	*/
	public SYSAP003F_Permission[] getPermission(String groupID){
		Database db = new Database();
		ResultSet rs ;
		String sql;
		int num=0;
		int counter=0;
		SYSAP003F_Permission permission[];
		try {
			sql = " select count(*) as num from SYSAP_Authority " + 
					" where groupID = " + Common.sqlChar(groupID) ;
			rs = db.querySQL(sql);
			if (rs.next()){
				num=rs.getInt("num");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}			
		permission = new SYSAP003F_Permission[num];
		try {	
			sql = " select groupID,progID,auth,url from SYSAP_Authority " + 
					" where groupID = " + Common.sqlChar(groupID);
			rs = db.querySQL(sql);
			while (rs.next()){
				permission[counter]=(new SYSAP003F_Permission(rs.getString("progID"),rs.getString("auth")));
				counter++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}		
		return permission;
	}			
	
  	/**
  	 * <br>
  	 * <br>目的：依groupID取得權限
  	 * <br>參數：groupID, emp_id, interact
  	 * <br>傳回：permission[]
  	*/
	public SYSAP003F_Permission[] getPermission(String groupID, String emp_id, boolean interact){
		Database db = new Database();
		ResultSet rs ;
		String sql;
		int num=0;
		int counter=0;
		SYSAP003F_Permission permission[];
		try {
			sql = " select count(*) as num from SYSAP_Authority " + 
					" where groupID " + Common.sqlChar(groupID) ;
			rs = db.querySQL(sql);
			if (rs.next()){
				num=rs.getInt("num");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}			
		permission = new SYSAP003F_Permission[num];
		try {	
			sql = " select groupID, progID, empID, auth, url from SYSAP_Authority " + 
					" where groupID = " + Common.sqlChar(groupID) ;
			rs = db.querySQL(sql);
			while (rs.next()){
				permission[counter]=(new SYSAP003F_Permission(rs.getString("progID"),rs.getString("auth")));
				counter++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}		
		return permission;
	}	
	
	public void updateParentNode() {
		Database db = new Database();
		try {
			db.setAutoCommit(false);
			ResultSet rs = db.querySQL("select groupid from sysap_group group by groupid");
			while (rs.next()) {
				String sql = " insert into sysap_authority(groupid,progId,auth) select '" + rs.getString("groupid") + "',id,'Q' from sysap_treenode where programid is null";
				db.exeSQL(sql);
			}	
			db.doCommit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	
	}
	
	public String updateGroupParentNode() {
		Database db = new Database();
		try {
			String sql = "";
			db.setAutoCommit(false);
			ResultSet rs;
			
			util.dtree.TreeEntry dt = new util.dtree.TreeEntry();
			
			rs = db.querySQL("select id from sysap_treenode where programid is null");
			while (rs.next()) {
				sql = " delete from sysap_authority where progid = " + Common.sqlChar(rs.getString("id"));
				db.exeSQL(sql);				
			}
			rs.close();
			
			rs = db.querySQL("select groupid from sysap_authority group by groupid");			
			while (rs.next()) {
				ResultSet rc = db.querySQL("select progid, auth from sysap_authority where groupID=" + Common.sqlChar(rs.getString("groupID")));
				while (rc.next()) {
					java.util.ArrayList ls = new java.util.ArrayList();
					ls = dt.getParentNode(rc.getString("progid"), ls, db);
					java.util.Iterator it = ls.iterator();
					while (it.hasNext()) {
						String pid = (String) it.next();
						sql = "delete from sysap_authority where progid = " + Common.sqlChar(pid) + " and groupID=" + Common.sqlChar(rs.getString("groupid"));
						db.exeSQL(sql);
						
						sql = " insert into sysap_authority(groupid,progId,auth)values('" + rs.getString("groupid") + "'," + Common.sqlChar(pid) + ",'" + rc.getString("auth") + "')";
						db.exeSQL(sql);						
					}					
				}
				rc.getStatement().close();
				rc.close();
			}	
			db.doCommit();
			return "update success!";
		} catch (Exception e) {
			e.printStackTrace();
			try {
				db.doRollback();
			} catch (Exception e1) {}
		} finally {
			db.closeAll();
		}
		return "";
	}
	
	public void test() {
		util.dtree.TreeEntry dt = new util.dtree.TreeEntry();	
		Database db = new Database();
		try {
			java.util.ArrayList ls = new java.util.ArrayList();			
			ls = dt.getParentNode("66", ls, db);
			java.util.Iterator it = ls.iterator();
			while (it.hasNext()) {
				System.out.println((String)it.next());						
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}
	
}
