package util.dtree;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class TreeView extends TreeEntry {
	StringBuffer sbTree = new StringBuffer(1024).append("");
	Database db = new Database();	
	
	public String getSbTree() { return Common.get(sbTree.toString()); }		
	
	public void dTree(String parentID) {		
		String sSQL = " select id, pid, name, url, title, target, icon, iconOpen, opened, btnRead, btnWrite from sysap_dtree where  pid = '" + parentID  + "' ";
		try {
			ResultSet rs = db.querySQL(sSQL);
			String[] colNames = db.getColumnNames(rs);
			String id = "";
			while (rs.next()) {
				sbTree.append("d.add(");
				//int j = 0;
				for(int j=0;j<colNames.length;j++){
					if (j==0) {
						id = rs.getString(colNames[j]);
						sbTree.append("'");
						sbTree.append(id);
						//sbTree.append(Common.get(rs.getString(colNames[j])));
						sbTree.append("'");						
					} else {
						sbTree.append(",'");
						sbTree.append(Common.get(rs.getString(colNames[j])));
						sbTree.append("'");						
					}
				}
				sbTree.append(");\n");
				dTree(id);
			}
			rs.getStatement().close();
			//rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}		
	}
	
	public void dTree(String parentID, boolean isManage) {		
		String sSQL = " select id, pid, name, url, title, target, icon, iconOpen, opened from sysap_dtree where  pid = '" + parentID  + "' ";
		try {
			ResultSet rs = db.querySQL(sSQL);
			String[] colNames = db.getColumnNames(rs);
			String id = "";
			String pid = "";
			while (rs.next()) {
				sbTree.append("d.add(");
				//mytree.add(1, 0, 'My node', 'node.html', 'node title', 'mainframe', 'img/musicfolder.gif');
				if (isManage) {
					id = rs.getString("id");
					pid = rs.getString("pid");
					sbTree.append("'").append(id).append("',");	
					sbTree.append("'").append(pid).append("',");
					sbTree.append("'").append(Common.get(rs.getString("name"))).append("',");					
					sbTree.append("'").append("dTreeForm.jsp?id=").append(id).append("&pid=").append(pid).append("');\n");
					
				} else {
					for(int j=0;j<colNames.length;j++){						
						if (j==0) {
							id = rs.getString(colNames[j]);
							sbTree.append("'");
							sbTree.append(id);
							//sbTree.append(Common.get(rs.getString(colNames[j])));
							sbTree.append("'");						
						} else {
							sbTree.append(",'");
							sbTree.append(Common.get(rs.getString(colNames[j])));
							sbTree.append("'");						
						}						
					}
					sbTree.append(");\n");					
				}
				dTree(id, isManage);
			}
			rs.getStatement().close();
			//rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}		
	}	
		
	public void connClose() {
		if (db!=null) db.closeAll();
	}	
	
	public StringBuffer buildTree(String parentID, boolean isManage, StringBuffer sb) {		
		String sSQL = " select id, pid, name, url, title, target, icon, iconOpen, opened from sysap_dtree where pid = '" + parentID  + "' ";
		//Database db = new Database();
		try {
			ResultSet rs = db.querySQL(sSQL);
			String[] colNames = db.getColumnNames(rs);
			String id = "";
			String pid = "";
			while (rs.next()) {
				sb.append("d.add(");
				//mytree.add(1, 0, 'My node', 'node.html', 'node title', 'mainframe', 'img/musicfolder.gif');
				if (isManage) {
					id = rs.getString("id");
					pid = rs.getString("pid");
					sb.append("'").append(id).append("',");	
					sb.append("'").append(pid).append("',");
					sb.append("'").append(Common.get(rs.getString("name"))).append("',");					
					sb.append("'").append("dTreeForm.jsp?id=").append(id).append("&pid=").append(pid).append("');\n");
					
				} else {
					for(int j=0;j<colNames.length;j++){						
						if (j==0) {
							id = rs.getString(colNames[j]);
							sb.append("'");
							sb.append(id);
							//sb.append(Common.get(rs.getString(colNames[j])));
							sb.append("'");						
						} else {
							sb.append(",'");
							sb.append(Common.get(rs.getString(colNames[j])));
							sb.append("'");						
						}						
					}
					sb.append(");\n");					
				}
				sb = buildTree(id, isManage, sb);				
			}
			rs.getStatement().close();
			//rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//db.closeAll();
		}		
		return sb;
	}	
	
	public ArrayList buildTree(String parentID, ArrayList list) {		
		String sSQL = " select id, pid, name, url, title, target, icon, iconOpen, opened, btnRead, btnWrite, sorted from sysap_dtree where pid = '" + parentID  + "' ";
		//Database db = new Database();
		try {
			ResultSet rs = db.querySQL(sSQL);
			while (rs.next()) {
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
				list.add(entity);
				list = buildTree(entity.getId(), list);				
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
	
	public ArrayList getSQL(String parentID, ArrayList list) {		
		String sSQL = " select id, pid from sysap_dtree where pid = '" + parentID  + "' ";
		//Database db = new Database();
		try {
			ResultSet rs = db.querySQL(sSQL);
			String id = "";
			while (rs.next()) {
				id = rs.getString("id");
				list.add("delete from sysap_dtree where id='"+id+"'");
				list = getSQL(id, list);				
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
	
	public String buildDTree() {
		StringBuffer sb = new StringBuffer(1024).append("");
		java.util.Iterator it = getTreeEntry().iterator();
		while (it.hasNext()) {
			TreeEntry e = (TreeEntry) it.next();
			sb.append("d.add(");			
			sb.append("'").append(e.getId()).append("',");	
			sb.append("'").append(e.getPid()).append("',");
			sb.append("'").append(e.getName()).append("',");					
			sb.append("'").append("dTreeForm.jsp?sid=").append(e.getId()).append("&fid=").append(e.getPid()).append("');\n");			
			//sb.append("'").append("dTreeForm.jsp?id=").append(e.getId()).append("&pid=").append(e.getPid()).append("');\n");
			//mytree.add(1, 0, 'My node', 'node.html', 'node title', 'mainframe', 'img/musicfolder.gif');
			/**
			sb.append("d.add(").append(e.getId()).append(",").append(e.getPid()).append(
				",'").append(e.getName()).append("','").append(e.getUrl()).append("','").append(
				e.getTitle()).append("','").append(e.getTarget()).append("');\n");
			**/
		}
		return sb.toString();
	}
	
	public String buildTreeMenu() {
		StringBuffer sb = new StringBuffer(1024).append("");
		java.util.Iterator it = getTreeEntry().iterator();
		while (it.hasNext()) {
			TreeEntry e = (TreeEntry) it.next();
			sb.append("d.add('").append(e.getId()).append("','").append(e.getPid()).append(
				"','").append(e.getName()).append("','").append(e.getUrl()).append("','").append(
				e.getTitle()).append("','").append(e.getTarget()).append("');\n");			
		}
		return sb.toString();
	}	
	
	public String buildManageTree() {
		StringBuffer sb = new StringBuffer(1024).append("");
		java.util.Iterator it = getTreeEntry().iterator();
		while (it.hasNext()) {
			TreeEntry e = (TreeEntry) it.next();
			sb.append("d.add(");			
			sb.append("'").append(e.getId()).append("',");	
			sb.append("'").append(e.getPid()).append("',");
			sb.append("'").append(e.getName()).append("',");					
			sb.append("'").append("dTreeForm.jsp?sid=").append(e.getId()).append("&fid=").append(e.getPid()).append("');\n");
		}
		return sb.toString();
	}	
	
	public String buildDTreeSortedField() {
		StringBuffer sb = new StringBuffer(1024).append("");
		java.util.Iterator it = getTreeEntry().iterator();
		while (it.hasNext()) {
			TreeEntry e = (TreeEntry) it.next();
			sb.append("d.add(");			
			sb.append("'").append(e.getId()).append("',");	
			sb.append("'").append(e.getPid()).append("',");
			sb.append("'").append(e.getName()).append("<input type=text size=3 maxlength=9 class=field_RO name=node_").append(e.getId()).append(" value=").append(e.getSorted()).append(">").append("',");					
			sb.append("'").append("dTreeForm.jsp?sid=").append(e.getId()).append("&fid=").append(e.getPid()).append("');\n");
		}
		return sb.toString();
	}
	
	public String buildAuthorizeTree() {
		StringBuffer sb = new StringBuffer(1024).append("");
		java.util.Iterator it = getTreeEntry().iterator();
		while (it.hasNext()) {
			//id, pid, name, url, title, target, icon, iconOpen, open, obj			
			TreeEntry e = (TreeEntry) it.next();
			sb.append("d.add(");			
			sb.append("'").append(e.getId()).append("',");	
			sb.append("'").append(e.getPid()).append("',");
			sb.append("'<input type=checkbox id=").append(e.getId()).append(" name=auth class=checkbox onclick=d.checkBoxClick(this) value=").append(e.getId()).append(">").append(e.getName()).append("',");					
			sb.append("'").append("dTreeForm.jsp?sid=").append(e.getId()).append("&fid=").append(e.getPid()).append("');\n");
		}
		return sb.toString();
	}
	
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

		java.util.Iterator it = getTreeEntry().iterator();		
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
}
