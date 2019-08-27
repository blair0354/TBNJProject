
/*
*<br>程式目的：機關管理作業
*<br>程式代號：sysca002f
*<br>程式日期：0940603
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ca;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSCA002F extends QueryBean {


String organID;
String organAName;
String organSName;
String organManager;
String isManager;
String organTel;
String organFax;
String organEmail;
String memo;
String title;

String q_organID;
String q_organAName;

public String getOrganID(){ return checkGet(organID); }
public void setOrganID(String s){ organID=checkSet(s); }
public String getOrganAName(){ return checkGet(organAName); }
public void setOrganAName(String s){ organAName=checkSet(s); }
public String getOrganSName(){ return checkGet(organSName); }
public void setOrganSName(String s){ organSName=checkSet(s); }
public String getIsManager(){ return checkGet(isManager); }
public void setIsManager(String s){ isManager=checkSet(s); }
public String getOrganManager(){ return checkGet(organManager); }
public void setOrganManager(String s){ organManager=checkSet(s); }
public String getOrganTel(){ return checkGet(organTel); }
public void setOrganTel(String s){ organTel=checkSet(s); }
public String getOrganFax(){ return checkGet(organFax); }
public void setOrganFax(String s){ organFax=checkSet(s); }
public String getOrganEmail(){ return checkGet(organEmail); }
public void setOrganEmail(String s){ organEmail=checkSet(s); }
public String getMemo(){ return checkGet(memo); }
public void setMemo(String s){ memo=checkSet(s); }
public String getTitle(){ return checkGet(title); }
public void setTitle(String s){ title=checkSet(s); }

public String getQ_organID(){ return checkGet(q_organID); }
public void setQ_organID(String s){ q_organID=checkSet(s); }
public String getQ_organAName(){ return checkGet(q_organAName); }
public void setQ_organAName(String s){ q_organAName=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSCA_Organ where 1=1 " + 
		" and organID = " + Common.sqlChar(organID) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" insert into SYSCA_Organ(" +
			" organID,"+
			" organAName,"+
			" organSName,"+
			" isManager,"+
			" organManager,"+
			" organTel,"+
			" organFax,"+
			" organEmail,"+
			" memo,"+
			" title,"+
			" editID,"+
			" editDate,"+
			" editTime"+			
		" )Values(" +
			Common.sqlChar(organID) + "," +
			Common.sqlChar(organAName) + "," +
			Common.sqlChar(organSName) + "," +
			Common.sqlChar(isManager) + "," +
			Common.sqlChar(organManager) + "," +
			Common.sqlChar(organTel) + "," +
			Common.sqlChar(organFax) + "," +
			Common.sqlChar(organEmail) + "," +
			Common.sqlChar(memo) + "," +
			Common.sqlChar(title) + "," +
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update SYSCA_Organ set " +
			" organAName = " + Common.sqlChar(organAName) + "," +
			" organSName = " + Common.sqlChar(organSName) + "," +
			" isManager = " + Common.sqlChar(isManager) + "," +
			" organManager = " + Common.sqlChar(organManager) + "," +
			" organTel = " + Common.sqlChar(organTel) + "," +
			" organFax = " + Common.sqlChar(organFax) + "," +
			" organEmail = " + Common.sqlChar(organEmail) + "," +
			" memo = " + Common.sqlChar(memo) + "," +
			" title = " + Common.sqlChar(title) + "," +
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) +   
		" where 1=1 " + 
			" and organID = " + Common.sqlChar(organID) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete SYSCA_Organ where 1=1 " +
			" and organID = " + Common.sqlChar(organID) +
			"";
	return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/
public SYSCA002F  queryOne() throws Exception{
	Database db = new Database();
	SYSCA002F obj = this;
	try {		
		setStateQueryOneSuccess();
		obj = getOrgan(db, organID);
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return obj;
}

public SYSCA002F getOrgan(util.Database db, String orgID) throws Exception{
	SYSCA002F obj = this;
	try {
		String sql=" select organID, organAName, organSName, isManager, organTel, organFax, organEmail, memo, organManager, title, editID, editDate, editTime  "+
			" from SYSCA_Organ where organID = " + Common.sqlChar(orgID);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setOrganID(rs.getString("organID"));
			obj.setOrganAName(rs.getString("organAName"));
			obj.setOrganSName(rs.getString("organSName"));
			obj.setIsManager(rs.getString("isManager"));
			obj.setOrganManager(rs.getString("organManager"));
			obj.setOrganTel(rs.getString("organTel"));
			obj.setOrganFax(rs.getString("organFax"));
			obj.setOrganEmail(rs.getString("organEmail"));
			obj.setMemo(rs.getString("memo"));
			obj.setTitle(rs.getString("title"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));			
		}
		rs.getStatement().close();
		rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return obj;
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
	try {
		String sql=" select a.organID, a.organSName, a.isManager, a.organManager "+
			" from SYSCA_Organ a where 1=1 "; 
			if (!"".equals(getQ_organID()))
				sql+=" and a.organID like '" + getQ_organID() +"%' " ;
			if (!"".equals(getQ_organAName()))
				sql+=" and a.organAName like '%" + getQ_organAName()+"%' " ;
		ResultSet rs = db.querySQL(sql + " order by organID", true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			
				String rowArray[]=new String[2];
				rowArray[0]=rs.getString("organID"); 
				rowArray[1]=rs.getString("organSName"); 
				//rowArray[2]=rs.getString("isManager"); 
				//rowArray[3]=rs.getString("organManager"); 
				objList.add(rowArray);	
				count++;
			} while (rs.next());
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
