package eform.eform3_1;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Common;
import util.Database;
import util.Md5;

public class EFORM3_1 {

	private String unit;
	private String txtcity_no;

	public String getTxtcity_no() {
		return Common.checkGet(txtcity_no);
	}

	public void setTxtcity_no(String txtcity_no) {
		this.txtcity_no =  Common.checkSet(txtcity_no);
	}

	public String getUnit() {
		return Common.checkGet(unit);
	}

	public void setUnit(String unit) {
		this.unit = Common.checkSet(unit);
	}
	
	public String getOption(String sql, String selectedOne) {
        String rtnStr = "";
//        System.out.println(sql);
        Database db = new Database();
        try {
        	ResultSet rs = db.querySQL(sql);
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                
                rtnStr = rtnStr + "<option value='" + id + "' ";
                if (selectedOne!= null && selectedOne.equals(id)) {
                    rtnStr = rtnStr + " selected ";
                }
                rtnStr = rtnStr + ">" + id +"-"+name + "</option>\n";
            }
        } catch (Exception ex) {
            rtnStr = "<option value=''>查詢錯誤</option>";
            ex.printStackTrace();
        } finally {
 			db.closeAll();
        }        
        
        return rtnStr;
    }
	
	
	public List<Map<String,String>> doQuery(String unit,String txtcity_no){
		txtcity_no = Common.get(txtcity_no);
		String sql ="select user_id,user_name,(select s.kcnt from rkeyn s where s.kcde_1='55' and s.kcde_2='01' and s.krmk=unit)as unitc "
				+ ",(CASE ISNULL(isManager,'') WHEN 'Y' THEN '是' ELSE '否' END)as isManagerYN  "
				+ ",(CASE ISNULL(isStop,'') WHEN 'Y' THEN '是' ELSE '否' END)as isStopYN "
				+ "from etecuser "
				+ "where 1 = 1 ";
		if(!"00".equals(unit) && "0".equals(unit.substring(1))) {
			sql += " and unit like '" + unit.substring(0,1) +"%'";
		}else if(!"00".equals(unit) && !"0".equals(unit.substring(1))) {
			sql += " and unit = '" + unit +"'";
		}
		sql += " and substring(unit,1,1) like '" + txtcity_no + "%'";
		sql += " order by unit,user_id";
//		System.out.println(sql);
		
		Database db = new Database();
		List<Map<String,String>> list = new ArrayList();
		try {
			ResultSet rs = db.querySQL(sql);
			
			ResultSetMetaData md = rs.getMetaData();
			int columnCount =  md.getColumnCount();
			
			while(rs.next()) {
				Map<String,String> map = new HashMap<String, String>();
				for(int i = 0; i < columnCount; i++) {
					map.put(md.getColumnName(i + 1).toString(), Common.checkGet(rs.getString(i + 1)));
				}
				list.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		
		return list;
	}
	int startPage = 1;	//起始頁
	int endPage = 1;	//末頁
	int totalSize = 0;	//總筆數
	int totalPage = 1;	//總頁數
	int page = 1;		//當前頁
	int showCount = 10; //每頁筆數
	
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getDetail(List<Map<String,String>> list) {
		StringBuilder sb = new StringBuilder();
		
		totalSize = list.size();
		totalPage = totalSize%showCount == 0 ? totalSize/showCount:(totalSize/showCount) + 1;
		if(getPage() > getTotalPage()) setPage(getTotalPage());
		if(getPage() < 0) setPage(1);
		
		
		
		for(int i = (getPage() - 1) * showCount; i < (getPage()*showCount > list.size() ? list.size():getPage()*showCount) ; i++) {
			sb.append("<tr onmouseover='this.style.background=\"eeeeee\"' onmouseout='this.style.background=\"\"'>");
				//序號
				sb.append("<td>");
				sb.append(i + 1);
				sb.append("</td>");
				
				//detail
				Map<String,String> m = list.get(i);
				String[] s = {"user_id","user_name","unitc","isManagerYN","isStopYN"};
				for(int j = 0; j <m.size(); j++) {
					sb.append("<td>");
					sb.append(m.get(s[j]));
					sb.append("</td>");
				}
				
				//修改
				sb.append("<td align='center'>");
				sb.append("<a href='user_update.jsp?txtuser_id=" + util.IDEncode.EncodePassword(m.get(s[0])) + "'>");
					sb.append("<img width='20' height='20' src='images/edit.bmp' border='0'>");
				sb.append("</a>");
				sb.append("</td>");
				//刪除
				sb.append("<td align='center'>");
				sb.append("<a href='javascript:if(confirm(\"是否刪除？\"))window.location=\"user_delete_control.jsp?txtuser_id=" + util.IDEncode.EncodePassword(m.get(s[0])) + "\"'>");
				sb.append("<img width='20' height='20' src='images/delete.bmp' border='0'>");
				sb.append("</a>");
				sb.append("</td>");
				
			sb.append("</tr>");
		}
		
		return sb.toString();
	}
	
	public String showPage() {
		StringBuilder sb = new StringBuilder();
		
		if(getPage() != 1) {
			sb.append("<a href='eform3_1.jsp?page=1&Txtcity_no="+getTxtcity_no()+"'><font color='#0000ff' style='font-size: 14'>[最上頁]</font></a>");
			sb.append("<a href='eform3_1.jsp?page=" + (getPage()-1) + "&Txtcity_no="+getTxtcity_no()+ "'><font color='#0000ff' style='font-size: 14'>[上一頁]</font></a>");
		}
		
		if(getPage() != getTotalPage()) {
			sb.append("<a href='eform3_1.jsp?page=" + (getPage()+1) + "&Txtcity_no="+getTxtcity_no()+ "'><font color='#0000ff' style='font-size: 14'>[下一頁]</font></a>");
			sb.append("<a href='eform3_1.jsp?page=" + getTotalPage() +"&Txtcity_no=" +getTxtcity_no()+ "'><font color='#0000ff' style='font-size: 14'>[最末頁]</font></a>");
		}
		
		sb.append("<font color='#0000ff'>輸入頁次:</font>");
		sb.append("<input type='text' size='1' name='page'><font color='#0000ff'>頁次:</font>");
		sb.append("<font color='red'>" + getPage() + "/" + getTotalPage() + "</font>");
				
		return sb.toString();
	}
	
	public String createDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		return sdf.format(date);		
		
	}
	
	/**
	 * 取得系統時間 至微毫秒
	 * @return EX: 2018-03-20 14:14:56:26.704
	 */
	public String getSystemTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
		
		return sdf.format(date);
		
	}
	
	
	public int queryUserId(String txtuser_id) {
		Database db = new Database();
		
		String sql = "select count(*) as cnt from etecuser where user_id='" + txtuser_id + "'";
		int cnt = 0;
		try {
			ResultSet rs = db.querySQL(sql);
			while(rs.next()) {
				cnt = Integer.parseInt(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		return cnt;
	}
	
	public void doInsert(
			String txtuser_id,
			String txtuser_name,
			String txtpassword,
			String txtuser_mail,
			String txtunit,
			String isStop,
			String isManager,
			String update_user,
			String update_time
			) {
		Database db = new Database();
		
		String[] sql = new String[1];
		try {
			sql[0] = "insert into etecuser(user_id,user_name,password,user_mail,unit,isStop,isManager,update_user,update_time) "
					+ "values(" 
					+ Common.sqlChar(txtuser_id)+ "," 
					+ Common.sqlChar(txtuser_name)+ "," 
					+ Common.sqlChar(Md5.getMD5(txtpassword))+ "," 
					+ Common.sqlChar(txtuser_mail)+ "," 
					+ Common.sqlChar(txtunit)+ "," 
					+ Common.sqlChar(isStop)+ "," 
					+ Common.sqlChar(isManager)+ "," 
					+ Common.sqlChar(update_user)+ "," 
					+ Common.sqlChar(update_time)
					+")";
			db.excuteSQL(sql);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		
	}
	
	public void doInsertLog(String txtuser_id,String txtpassword) {
		Database db = new Database();
		
		String[] sql = new String[1];
		try {
			sql[0] = "insert into etecuser_log (user_id,password) values("
					+ Common.sqlChar(txtuser_id) 
					+"," + Common.sqlChar(Md5.getMD5(txtpassword))
					+ ")";
			db.excuteSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
	}
	
	public List<Map<String,String>>queryUser(String txtuser_id){
		Database db = new Database();
		String sql = "select a.user_id,a.user_name,a.user_mail,substring(a.unit,1,1)as cty_no,a.unit,a.user_login_time,a.isStop,a.isManager,a.update_user,a.update_time,(select s.user_name from etecuser s where s.user_id=a.update_user)as update_userc from etecuser a where user_id=" + Common.sqlChar(Common.checkSet(txtuser_id));
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			ResultSet rs = db.querySQL(sql);
			ResultSetMetaData md = rs.getMetaData();
			int columnCount =  md.getColumnCount();
			
			while(rs.next()) {
				Map<String,String> map = new HashMap<String, String>();
				for(int i = 0; i < columnCount; i++) {
					map.put(Common.checkGet(md.getColumnName(i + 1).toString()), Common.checkGet(rs.getString(i + 1)));
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		
		return list;
	}
	
	public String getYN(String YN) {
		String s = "";
		s += "<option value=N " + ("".equals(YN) || "N".equals(YN)?"selected":"") +">否</option>";
		s += "<option value=Y " + ("Y".equals(YN)?"selected":"") +">是</option>";
		return s;
	}
	
	public int queryPwd(String txtuser_id,String txtpassword) {
		Database db = new Database();
		int cnt = 0;
		try {
			String sql = 
					"select count(*)as cnt from etecuser_log "
					+ "where user_id=" + Common.sqlChar(txtuser_id)
					+ "and password=" + Common.sqlChar(Md5.getMD5(txtpassword));
			ResultSet rs = db.querySQL(sql);
			
			while(rs.next()) {
				cnt = Integer.parseInt(rs.getString(1));
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		return cnt;
		
	}
	
	public void updateLog(
			String txtuser_name,
			String txtpassword,
			String txtuser_mail,
			String txtunit,
			String isStop,
			String isManager,
			String update_user,
			String update_time,
			String txtuser_id
			) {
		Database db = new Database();
		
		try {
		String[] sql = new String[1];
				sql[0] ="update etecuser set "
				+ "user_name=" + Common.sqlChar(txtuser_name) + ",";
		
				if(!"".equals(txtpassword))
				sql[0] += "password=" + Common.sqlChar(Md5.getMD5(txtpassword)) + ",";
				
				sql[0] += "user_mail=" + Common.sqlChar(txtuser_mail) + ","
						+ "unit=" + Common.sqlChar(txtunit) + ","
						+ "isStop=" + Common.sqlChar(isStop) + ","
						+ "isManager=" + Common.sqlChar(isManager) + ","
						+ "update_user=" + Common.sqlChar(update_user) + ","
						+ "update_time=" + Common.sqlChar(update_time)
						+ " where user_id=" + Common.sqlChar(txtuser_id);
			db.excuteSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
	}
	
	public int queryLogCnt(String txtuser_id) {
		Database db = new Database();
		String sql = "select * from etecuser_log where user_id=" + Common.sqlChar(txtuser_id) + "order by update_time desc";
		int cnt = 0;
		try {
			ResultSet rs = db.querySQL(sql);
			while(rs.next()) {
				cnt = rs.getRow();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		return cnt;
	}
	
	public void delLogCnt(String txtuser_id) {
		Database db = new Database();
		String[] sql = new String[1]; 
		 sql[0] = "delete from etecuser_log where user_id=" + Common.sqlChar(txtuser_id) + " and id = (select min(id) from etecuser_log where user_id="+Common.sqlChar(txtuser_id)+")";
		
		try {
			db.excuteSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		
	}
	
	public void doDelete(String txtuser_id) {
		Database db = new Database();
		String[] sql = new String[2];
		sql[0] = "delete from etecuser where user_id=" + Common.sqlChar(txtuser_id);
		sql[1] = "delete from etecuser_log where user_id=" + Common.sqlChar(txtuser_id);
		
		try {
			db.excuteSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
	}
	
	public void doDelLog(String txtuser_id) {
		Database db = new Database();
		String[] sql = new String[1];
		sql[0] = "delete from etecuser_log where user_id=" + Common.sqlChar(txtuser_id);
		
		try {
			db.excuteSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
	}
	
	public void resetPassword(
			String txtuser_name,
			String txtpassword,
			String txtuser_mail,
			String txtunit,
			String isStop,
			String isManager,
			String update_user,
			String update_time,
			String user_login_time,
			String txtuser_id
			) {
Database db = new Database();
		
		try {
		String[] sql = new String[1];
				sql[0] ="update etecuser set "
				+ "user_name=" + Common.sqlChar(txtuser_name) + ","
				+ "password=" + Common.sqlChar(Md5.getMD5(txtpassword)) + ","
				+ "user_mail=" + Common.sqlChar(txtuser_mail) + ","
				+ "unit=" + Common.sqlChar(txtunit) + ","
				+ "isStop=" + Common.sqlChar(isStop) + ","
				+ "isManager=" + Common.sqlChar(isManager) + ","
				+ "update_user=" + Common.sqlChar(update_user) + ","
				+ "update_time=" + Common.sqlChar(update_time) + ","
				+ "user_login_time=" + Common.sqlChar(user_login_time) 
				+ " where user_id=" + Common.sqlChar(txtuser_id);
			db.excuteSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		
	}
}
