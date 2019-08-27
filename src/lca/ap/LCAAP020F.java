/*
*<br>程式目的：lcaap010f
*<br>程式代號：lcaap010f
*<br>程式日期：0970512
*<br>程式作者：Richard Li
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package lca.ap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import util.*;

import java.util.Hashtable;

import javax.swing.table.DefaultTableModel;

public class LCAAP020F extends QueryBean{

String qry_seq;
String qry_date_start;
String qry_date_end;
String qry_userid;
String qry_flag;
String qry_status;
String userID;



public String getUserID() {
	return checkGet(userID);
}

public void setUserID(String userID) {
	this.userID = checkSet(userID);
}

public String getQry_date_start() {
	return checkGet(qry_date_start);
}

public void setQry_date_start(String qry_date_start) {
	this.qry_date_start = checkSet(qry_date_start);
}

public String getQry_date_end() {
	return checkGet(qry_date_end);
}

public void setQry_date_end(String qry_date_end) {
	this.qry_date_end = checkSet(qry_date_end);
}

public String getQry_flag() {
	return checkGet(qry_flag);
}

public void setQry_flag(String qry_flag) {
	this.qry_flag = checkSet(qry_flag);
}

public String getQry_seq() {
	return checkGet(qry_seq);
}

public void setQry_seq(String qry_seq) {
	this.qry_seq = checkSet(qry_seq);
}

public String getQry_status() {
	return checkGet(qry_status);
}

public void setQry_status(String qry_status) {
	this.qry_status = checkSet(qry_status);
}

public String getQry_userid() {
	return checkGet(qry_userid);
}

public void setQry_userid(String qry_userid) {
	this.qry_userid = checkSet(qry_userid);
}
/**
 * <br>
 * <br>目的：依查詢欄位查詢多筆資料
 * <br>參數：無
 * <br>傳回：傳回ArrayList
*/

public ArrayList queryAll() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	Hashtable chartHash=new Hashtable();
	chartHash.put("A","權利人歸戶清冊");
	chartHash.put("B","土地所有權人歸戶清冊");
	chartHash.put("C","建物所有權人歸戶清冊");
	chartHash.put("D","土地他項權利人歸戶清冊");
	chartHash.put("E","建物他項權利人歸戶清冊");
	chartHash.put("F","土地所有權人管理者歸戶清冊");
	chartHash.put("G","建物所有權人管理者歸戶清冊");
	chartHash.put("H","土地他項權利人管理者歸戶清冊");
	chartHash.put("I","建物他項權利人管理者歸戶清冊");
	chartHash.put("J","勞保局土地歸戶清冊");
	chartHash.put("K","勞保局建物歸戶清冊");
	chartHash.put("L","批次歸戶");
	chartHash.put("M","國軍退輔會土地歸戶清冊");
    chartHash.put("N","國軍退輔會建物歸戶清冊");
	Hashtable status_hash=new Hashtable();
	status_hash.put("1", "產製中");
	status_hash.put("2", "產製完成可下載");
	status_hash.put("3", "已下載");
	status_hash.put("4", "無符合條件資料");
	status_hash.put("5", "中斷服務請重新產製");
	status_hash.put("6", "產製失敗");
	Hashtable fileStyle=new Hashtable();
	fileStyle.put("1", "EXCEL");
	fileStyle.put("2", "PDF");
	fileStyle.put("3", "TXT");
		
	try {
		String sql="select * from download_log where 1=1";
		if(!this.getQry_seq().equals("")){
			sql+=" and dqry_seq="+Common.sqlChar(qry_seq);
		}
		if(!this.getQry_date_start().equals("")){
			sql+=" and dqry_date_start>="+Common.sqlChar(qry_date_start);
		}
		if(!this.getQry_date_end().equals("")){
			sql+=" and dqry_date_start<="+Common.sqlChar(qry_date_end);
		}
		if(!this.getQry_userid().equals("")){
			sql+=" and UPPER(dqry_userid)="+Common.sqlChar(qry_userid.toUpperCase(Locale.ENGLISH));
		}
		if(!this.getQry_flag().equals("")){
			sql+=" and dqry_flag="+Common.sqlChar(qry_flag);
		}
		if(!this.getQry_status().equals("")){
			sql+=" and dqry_status="+Common.sqlChar(qry_status);
		}
		sql+=" order by substring(dqry_seq,2,13) desc";
		ResultSet rs = db.querySQL(sql,true);
		while(rs.next()){
			String rowArray[]=new String[12];
			rowArray[0]=Common.get(rs.getString("dqry_seq"));
			rowArray[1]=Common.get(rs.getString("dqry_file_name"));
			rowArray[2]=Common.get(rs.getString("dqry_seq"));
			rowArray[3]=Common.get(rs.getString("dqry_date_start"));
			rowArray[4]=Common.get(rs.getString("dqry_unitid"));
			rowArray[5]=Common.get(rs.getString("dqry_userid"));
			rowArray[6]=(String)chartHash.get(Common.get(rs.getString("dqry_flag")));
			rowArray[7]=(String)status_hash.get(Common.get(rs.getString("dqry_status")));
			rowArray[8]=Common.get(rs.getString("dqry_date_end"));
			rowArray[9]=Common.get(rs.getString("dqry_time_end"));
			rowArray[10]=(String)fileStyle.get(Common.get(rs.getString("dqry_file_style")));
			rowArray[11]=Common.get(rs.getString("download_date"));
			objList.add(rowArray);
		}	
		setStateQueryAllSuccess();
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}





}


