package util.dtree;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.Database;
import util.SuperBean;

public class TreeEntry extends SuperBean {
	private String icon;
	private String iconOpen;
	private String id;
	private String name;
	private String opened;
	private String pid;
	private String target;
	private String title;
	private String url;
	private String btnRead;
	private String btnWrite;
	private String sorted;
	private String includeChild;
	private String pname;
	private String hasChild;
	
	public String getHasChild() { return checkGet(hasChild); }
	public void setHasChild(String s) { hasChild = checkSet(s); } 
	public String getPname() { return checkGet(pname); }
	public void setPname(String s) { pname = checkSet(s); }
	public String getIcon() { return checkGet(icon); }
	public void setIcon(String s) { icon = checkSet(s); }
	public String getIconOpen() { return checkGet(iconOpen); }
	public void setIconOpen(String s) { iconOpen = checkSet(s);}
	public String getId() {return checkGet(id);}
	public void setId(String s) { id = checkSet(s);}
	public String getName() { return checkGet(name); }
	public void setName(String s) {name = checkSet(s);}
	public String getOpened() {return checkGet(opened);}
	public void setOpened(String s) {opened = checkSet(s);}
	public String getPid() {return checkGet(pid);}
	public void setPid(String s) {pid = checkSet(s);}
	public String getTarget() {return checkGet(target);}
	public void setTarget(String s) {target = checkSet(s);}
	public String getTitle() {return checkGet(title);}
	public void setTitle(String s) {title = checkSet(s);}
	public String getUrl() {return checkGet(url);}
	public void setUrl(String s) {url = checkSet(s);}
	
	public void setBtnRead(String s) {btnRead = checkSet(s);}	
	public String getBtnRead() {return checkGet(btnRead);}	
	
	public void setBtnWrite(String s) {btnWrite = checkSet(s);}	
	public String getBtnWrite() {return checkGet(btnWrite);}
	
	public void setSorted(String s) {sorted = checkSet(s);}	
	public String getSorted() {return checkGet(sorted);}		
	
	public void setIncludeChild(String s) {includeChild = checkSet(s);}	
	public String getIncludeChild() {return checkGet(includeChild);}		
	
	protected String[][] getInsertCheckSQL(){	
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkResult from sysap_dtree where id= " + id ;
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
		
		return checkSQLArray;
	}

	protected String[] getInsertSQL(){
		
		Database db = new Database();
		try {
			id = db.getLookupField("select max(id)+1 from sysap_dtree");
			if ("".equals(id)) id = "1";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		
		String[] execSQLArray = new String[1];
		//select id, pid, name, url, title, target, icon, iconOpen, opened, btnRead, btnWrite
		execSQLArray[0]=" insert into sysap_dtree(" +
           " id,"+
           " pid,"+
           " name,"+
           " url,"+
           " title,"+
           " target,"+
           " icon,"+
           " iconOpen,"+
           " opened,"+
           " btnRead,"+
           " btnWrite,"+
           " sorted"+
       " )Values(" +
           Common.sqlChar(id) + "," +
           Common.sqlChar(pid) + "," +
           Common.sqlChar(name) + "," +
           Common.sqlChar(url) + "," +
           Common.sqlChar(title) + "," +
           Common.sqlChar(target) + "," +
           Common.sqlChar(icon) + "," +
           Common.sqlChar(iconOpen) + "," +
           Common.sqlChar(opened) + "," +
           Common.sqlChar(btnRead) + "," +
           Common.sqlChar(btnWrite) + "," +
           Common.sqlInt(sorted) + ")" ;
		return execSQLArray;
	}	

	protected String[] getUpdateSQL(){
		String[] execSQLArray = new String[1];
		
	    execSQLArray[0]=" update sysap_dtree set " +
	        " id = " + Common.sqlChar(id) + "," +
	        " name = " + Common.sqlChar(name) + "," +
	        " pid = " + Common.sqlChar(pid) + "," +
	        " url = " + Common.sqlChar(url) + "," +
	        " title = " + Common.sqlChar(title) + "," +
	        " target = " + Common.sqlChar(target) + "," +
	        " icon = " + Common.sqlChar(icon) + "," +
	        " iconOpen = " + Common.sqlChar(iconOpen) + "," +
	        " opened = " + Common.sqlChar(opened) + "," +
	        " btnRead = " + Common.sqlChar(btnRead) + "," +
	        " btnWrite = " + Common.sqlChar(btnWrite) + "," +
	        " sorted = " + Common.sqlInt(sorted) +	        
	    " where id = '" + id + "'";		
		return execSQLArray;
	}		
	
	protected String[] getDeleteSQL(){
		String[] execSQLArray = null;
		Database db = new Database();
		try {
			ArrayList list = new ArrayList();
			list = getSQL(id, list, db);
			execSQLArray = new String[list.size()+1];
			execSQLArray[0] = "delete from sysap_dtree where id = '" + id + "'";			
			java.util.Iterator it = list.iterator();
			int i = 1;
			while (it.hasNext()) {
				execSQLArray[i] = (String)it.next();
				i = i + 1;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return execSQLArray;
	}
	
	public ArrayList getSQL(String parentID, ArrayList list, util.Database db) {		
		String sSQL = " select id, pid from sysap_dtree where pid = '" + parentID  + "' ";
		try {
			ResultSet rs = db.querySQL(sSQL);
			String id = "";
			while (rs.next()) {
				id = rs.getString("id");
				list.add("delete from sysap_dtree where id='"+id+"'");
				list = getSQL(id, list, db);
			}
			rs.getStatement().close();
			//rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//db.closeAll();
		}
		return list;
	}
	
	public ArrayList getParentNode(String nId, ArrayList list, util.Database db) {
		if (nId.equals("0") || nId.equals("-1")) {
			//list.add(nId);
			return list;
		} else {
			String sSQL = " select id, pid from sysap_dtree where id = '" + nId  + "' ";
			try {
				ResultSet rs = db.querySQL(sSQL);
				String pid = "";
				while (rs.next()) {
					pid = rs.getString("pid");
					list.add(pid);
					list = getParentNode(pid, list, db);
				}
				rs.getStatement().close();
				//rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//db.closeAll();
			}
			return list;			
		}
	}	
	
	public TreeEntry queryOne() {
		Database db = new Database();
		TreeEntry obj= this;
		try{
			String sql = " select id, pid, name, url, title, target, icon, iconOpen, opened, btnRead, btnWrite, sorted from sysap_dtree where  id = '" + id  + "' ";
			ResultSet rs = db.querySQL(sql);			
			if(rs.next()){
				obj.setId(rs.getString("id"));
				obj.setPid(rs.getString("pid"));
				obj.setName(rs.getString("name"));
				
				sql = " select name from sysap_dtree where id = '" + obj.getPid() + "' ";
				obj.setPname(util.View.getLookupField(sql));
				
				obj.setUrl(rs.getString("url"));
				obj.setTitle(rs.getString("title"));
				obj.setTarget(rs.getString("target"));
				obj.setIcon(rs.getString("icon"));
				obj.setIconOpen(rs.getString("iconOpen"));
				obj.setOpened(rs.getString("opened"));
				obj.setBtnRead(rs.getString("btnRead"));
				obj.setBtnWrite(rs.getString("btnWrite"));	
				obj.setSorted(rs.getString("sorted"));
				//obj.setHasChild(setCS(rs.getString("id")));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
	    return obj;
	}
	
	public String getNodeName(String id) {
		id = Common.get(id);
		if (id.equals("0")) return "功能選單";
		else {
			Database db = new Database();
			try{
				String sql = " select name from sysap_dtree where  id = '" + id  + "' ";
				ResultSet rs = db.querySQL(sql);			
				if(rs.next()){
					return Common.get(rs.getString("name"));
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.closeAll();
			}
		}
		return "";
	}
	
	public ArrayList getTreeEntry() {
		ArrayList list=new ArrayList();
		String sql="select * from sysap_dtree order by sorted, pid, id";
		Database db = new Database();		  
		try{
			ResultSet rs = db.querySQL(sql);
			while(rs.next()){
				TreeEntry entity=new TreeEntry();
				entity.setId(rs.getString("id"));
				entity.setPid(rs.getString("pid"));
				entity.setName(rs.getString("name"));
				entity.setUrl(rs.getString("url"));
				entity.setTitle(rs.getString("title"));
				entity.setTarget(rs.getString("target"));
				entity.setIcon(rs.getString("icon"));
				entity.setIconOpen(rs.getString("iconOpen"));
				entity.setOpened(rs.getString("opened"));
				entity.setBtnRead(rs.getString("btnRead"));
				entity.setBtnWrite(rs.getString("btnWrite"));
				entity.setSorted(rs.getString("sorted"));
				//entity.setHasChild(setCS(rs.getString("id")));
				list.add(entity);
			}
		    return list;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		return null;
	}	
	
	public ArrayList getTreeEntry(String groupID) {
		ArrayList list=new ArrayList();
		String sql="select a.* from sysap_dtree a, sysap_authority b where b.groupID=" + Common.sqlChar(groupID) + " and a.id=b.progID order by a.sorted, a.pid, a.id";
		Database db = new Database();		  
		try{
			ResultSet rs = db.querySQL(sql);
			while(rs.next()){
				TreeEntry entity=new TreeEntry();
				entity.setId(rs.getString("id"));
				entity.setPid(rs.getString("pid"));
				entity.setName(rs.getString("name"));
				entity.setUrl(rs.getString("url"));
				entity.setTitle(rs.getString("title"));
				entity.setTarget(rs.getString("target"));
				entity.setIcon(rs.getString("icon"));
				entity.setIconOpen(rs.getString("iconOpen"));
				entity.setOpened(rs.getString("opened"));
				entity.setBtnRead(rs.getString("btnRead"));
				entity.setBtnWrite(rs.getString("btnWrite"));
				entity.setSorted(rs.getString("sorted"));
				//entity.setHasChild(setCS(rs.getString("id")));
				list.add(entity);
			}
		    return list;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		return null;
	}		
	
	public String setCS(String s) {
		Database db = new Database();
		try {
			ResultSet rs = db.querySQL("select count(*) as num from sysap_dtree where pid="+Common.sqlChar(s));
			if (rs.next() && rs.getInt("num")>0) return "Y";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return "N";
	}

}
