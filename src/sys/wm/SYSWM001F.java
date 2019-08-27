/*
*<br>程式目的：網站訊息管理作業
*<br>程式代號：syswm001f
*<br>程式日期：0941019
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.wm;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.*;

public class SYSWM001F extends QueryBean{

String isQuery;
String newsCat;
String newsID;
String newsSubject;
String newsContent;
String newsDateS;
String newsDateE;
String isHTML;
String itemPicture1;
String itemPicture2;
String itemPicture3;
String itemPicture4;
String itemPicture5;
String itemPicture6;
String itemPicture7;
String itemPicture8;
String itemPicture9;
String itemPicture10;

String q_newsCat;
String q_newsID;
String q_newsIDS;
String q_newsIDE;
String q_newsSubject;
String q_newsContent;
String q_newsDateS;
String q_newsDateE;
String q_newsDate;

public String getIsQuery(){ return checkGet(isQuery); }
public void setIsQuery(String s){ isQuery=checkSet(s); }
public String getNewsCat(){ return checkGet(newsCat); }
public void setNewsCat(String s){ newsCat=checkSet(s); }
public String getNewsID(){ return checkGet(newsID); }
public void setNewsID(String s){ newsID=checkSet(s); }
public String getNewsSubject(){ return checkGet(newsSubject); }
public void setNewsSubject(String s){ newsSubject=checkSet(s); }
public String getNewsContent(){ return checkGet(newsContent); }
public void setNewsContent(String s){ newsContent=checkSet(s); }
public String getNewsDateS(){ return checkGet(newsDateS); }
public void setNewsDateS(String s){ newsDateS=checkSet(s); }
public String getNewsDateE(){ return checkGet(newsDateE); }
public void setNewsDateE(String s){ newsDateE=checkSet(s); }
public String getIsHTML(){ if ("Y".equals(checkGet(isHTML))) return "checked"; else return ""; }
public void setIsHTML(String s){ isHTML=checkSet(s); }
public String getItemPicture1(){ return checkGet(itemPicture1); }
public void setItemPicture1(String s){ itemPicture1=checkSet(s); }
public String getItemPicture2(){ return checkGet(itemPicture2); }
public void setItemPicture2(String s){ itemPicture2=checkSet(s); }
public String getItemPicture3(){ return checkGet(itemPicture3); }
public void setItemPicture3(String s){ itemPicture3=checkSet(s); }
public String getItemPicture4(){ return checkGet(itemPicture4); }
public void setItemPicture4(String s){ itemPicture4=checkSet(s); }
public String getItemPicture5(){ return checkGet(itemPicture5); }
public void setItemPicture5(String s){ itemPicture5=checkSet(s); }

public String getItemPicture6(){ return checkGet(itemPicture6); }
public void setItemPicture6(String s){ itemPicture6=checkSet(s); }
public String getItemPicture7(){ return checkGet(itemPicture7); }
public void setItemPicture7(String s){ itemPicture7=checkSet(s); }
public String getItemPicture8(){ return checkGet(itemPicture8); }
public void setItemPicture8(String s){ itemPicture8=checkSet(s); }
public String getItemPicture9(){ return checkGet(itemPicture9); }
public void setItemPicture9(String s){ itemPicture9=checkSet(s); }
public String getItemPicture10(){ return checkGet(itemPicture10); }
public void setItemPicture10(String s){ itemPicture10=checkSet(s); }

String filestoreLocation;
public String getFilestoreLocation(){ return checkGet(filestoreLocation); }
public void setFilestoreLocation(String s){ filestoreLocation=checkSet(s); }

public String getQ_newsCat(){ return checkGet(q_newsCat); }
public void setQ_newsCat(String s){ q_newsCat=checkSet(s); }
public String getQ_newsIDS(){ return checkGet(q_newsIDS); }
public void setQ_newsIDS(String s){ q_newsIDS=checkSet(s); }
public String getQ_newsIDE(){ return checkGet(q_newsIDE); }
public void setQ_newsIDE(String s){ q_newsIDE=checkSet(s); }
public String getQ_newsSubject(){ return checkGet(q_newsSubject); }
public void setQ_newsSubject(String s){ q_newsSubject=checkSet(s); }
public String getQ_newsContent(){ return checkGet(q_newsContent); }
public void setQ_newsContent(String s){ q_newsContent=checkSet(s); }
public String getQ_newsDateS(){ return checkGet(q_newsDateS); }
public void setQ_newsDateS(String s){ q_newsDateS=checkSet(s); }
public String getQ_newsDateE(){ return checkGet(q_newsDateE); }
public void setQ_newsDateE(String s){ q_newsDateE=checkSet(s); }
public String getQ_newsDate(){ return checkGet(q_newsDate); }
public void setQ_newsDate(String s){ q_newsDate=checkSet(s); }

String itemPictureHTML;
public void setItemPictureHTML(String s) { itemPictureHTML = s; }
public String getItemPictureHTML(){
	if (itemPictureHTML==null) return "";
	else return itemPictureHTML.trim();
}

final public String[] arrFileNames = new String[]{"itemPicture1","itemPicture2","itemPicture3","itemPicture4","itemPicture5","itemPicture6","itemPicture7","itemPicture8","itemPicture9","itemPicture10"};

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
	//取得訊息編號
	Database db = new Database();
	ResultSet rs;	
	String sql="select max(substring(newsID,8,3))+1 as newsID from SYSWM_NEWS " +
		" where substring(newsID,1,7) = " + Common.sqlChar(Datetime.getYYYMMDD()) + 
		"";		
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if (rs.getString("newsID")==null)
				setNewsID(Datetime.getYYYMMDD()+"001");
			else
				setNewsID(Datetime.getYYYMMDD()+Common.formatFrontZero(rs.getString("newsID"), 3));
		} else {
			setNewsID(Datetime.getYYYMMDD()+"001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
	String[][] checkSQLArray = new String[1][4];
 	checkSQLArray[0][0]=" select count(*) as checkResult from SYSWM_NEWS where 1=1 " + 
		" and newsID = " + Common.sqlChar(newsID) + 
		"";
	checkSQLArray[0][1]=">";
	checkSQLArray[0][2]="0";
	checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
	return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	String[] execSQLArray = new String[1];	
	execSQLArray[0]=" insert into SYSWM_NEWS(" +
			" newsCat,"+
			" newsID,"+
			" newsSubject,"+
			" newsContent,"+
			" newsDateS,"+
			" newsDateE,"+
			" isHTML,"+
			" itemPicture1,"+
			" itemPicture2,"+
			" itemPicture3,"+
			" itemPicture4,"+
			" itemPicture5,"+		
			" itemPicture6,"+
			" itemPicture7,"+
			" itemPicture8,"+
			" itemPicture9,"+
			" itemPicture10,"+					
			" editID,"+
			" editDate,"+
			" editTime"+
		" )Values(" +
			Common.sqlChar(newsCat) + "," +
			Common.sqlChar(newsID) + "," +
			Common.sqlChar(newsSubject) + "," +
			Common.sqlChar(newsContent) + "," +
			Common.sqlChar(newsDateS) + "," +
			Common.sqlChar(newsDateE) + "," +
			Common.sqlChar(isHTML) + "," +
			Common.sqlChar(itemPicture1) + "," +
			Common.sqlChar(itemPicture2) + "," +
			Common.sqlChar(itemPicture3) + "," +
			Common.sqlChar(itemPicture4) + "," +
			Common.sqlChar(itemPicture5) + "," +			
			Common.sqlChar(itemPicture6) + "," +
			Common.sqlChar(itemPicture7) + "," +
			Common.sqlChar(itemPicture8) + "," +
			Common.sqlChar(itemPicture9) + "," +
			Common.sqlChar(itemPicture10) + "," +			
			Common.sqlChar(getEditID()) + "," +
			Common.sqlChar(getEditDate()) + "," +
			Common.sqlChar(getEditTime()) + ")" ;
	return execSQLArray;
}



//傳回update sql
protected String[] getUpdateSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update SYSWM_NEWS set " +
			" newsCat = " + Common.sqlChar(newsCat) + "," +
			" newsSubject = " + Common.sqlChar(newsSubject) + "," +
			" newsContent = " + Common.sqlChar(newsContent) + "," +
			" newsDateS = " + Common.sqlChar(newsDateS) + "," +
			" newsDateE = " + Common.sqlChar(newsDateE) + "," +
			" isHTML = " + Common.sqlChar(isHTML) + "," +
			" itemPicture1 = " + Common.sqlChar(itemPicture1) + "," +
			" itemPicture2 = " + Common.sqlChar(itemPicture2) + "," +
			" itemPicture3 = " + Common.sqlChar(itemPicture3) + "," +
			" itemPicture4 = " + Common.sqlChar(itemPicture4) + "," +
			" itemPicture5 = " + Common.sqlChar(itemPicture5) + "," +
			" itemPicture6 = " + Common.sqlChar(itemPicture6) + "," +
			" itemPicture7 = " + Common.sqlChar(itemPicture7) + "," +
			" itemPicture8 = " + Common.sqlChar(itemPicture8) + "," +
			" itemPicture9 = " + Common.sqlChar(itemPicture9) + "," +
			" itemPicture10 = " + Common.sqlChar(itemPicture10) + "," +						
			" editID = " + Common.sqlChar(getEditID()) + "," +
			" editDate = " + Common.sqlChar(getEditDate()) + "," +
			" editTime = " + Common.sqlChar(getEditTime()) + 
		" where 1=1 " + 
			" and newsID = " + Common.sqlChar(newsID) +
			"";
	return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
	Database db = new Database();
	try {
		ResultSet rs = db.querySQL(" select itemPicture1, itemPicture2, itemPicture3, itemPicture4, itemPicture5, itemPicture6, itemPicture7, itemPicture8, itemPicture9, itemPicture10 from SYSWM_NEWS where newsID = " + Common.sqlChar(newsID));
		if (rs.next()) {
			for (int i=0; i<arrFileNames.length; i++) {
				doDeleteFile(rs.getString(arrFileNames[i]));
			}			
		}
		rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
		
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" delete SYSWM_NEWS where newsID = " + Common.sqlChar(newsID);
	/**
	//刪除檔案
	String[] arrFileName=null;	
	if (!"".equals(Common.get(itemPicture1))) {
		arrFileName=itemPicture1.split(":;:");
		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
	}
	if (!"".equals(Common.get(itemPicture2))) {
		arrFileName=itemPicture2.split(":;:");
		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
	}	
	if (!"".equals(Common.get(itemPicture3))) {
		arrFileName=itemPicture3.split(":;:");
		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
	}	
	if (!"".equals(Common.get(itemPicture4))) {
		arrFileName=itemPicture4.split(":;:");
		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
	}	
	if (!"".equals(Common.get(itemPicture5))) {
		arrFileName=itemPicture5.split(":;:");
		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
	}	
	**/
	return execSQLArray;
}

public void deleteAll() {
	Database db = new Database();
	String strSQL = "";
	try {
		strSQL = " select itemPicture1, itemPicture2, itemPicture3, itemPicture4, itemPicture5, itemPicture6, itemPicture7, itemPicture8, itemPicture9, itemPicture10 from SYSWM_NEWS where 1=1 ";
		if (!"".equals(getQ_newsCat()))
			strSQL += " and newsCat = " + Common.sqlChar(getQ_newsCat()) ;
		if (!"".equals(getQ_newsIDS()) && !"".equals(getQ_newsIDE()))				
			strSQL += " and newsID between " + Common.sqlChar(getQ_newsIDS()) + " and " + Common.sqlChar(getQ_newsIDE());
		else if (!"".equals(getQ_newsIDS()))
			strSQL += " and newsID = " + Common.sqlChar(getQ_newsIDS());
		if (!"".equals(getQ_newsSubject()))
			strSQL += " and newsSubject like '%" + getQ_newsSubject() + "%'" ;
		if (!"".equals(getQ_newsContent()))
			strSQL += " and newsContent like '%" + Common.sqlChar(getQ_newsContent()) + "%'";
		if (!"".equals(getQ_newsDateS()) && !"".equals(getQ_newsDateE()))
			strSQL += " and newsDateS >= " + Common.sqlChar(getQ_newsDateS()) + " and newsDateE<=" + Common.sqlChar(getQ_newsDateE());
		else if (!"".equals(getQ_newsDateS()))
			strSQL += " and newsDateS = " + Common.sqlChar(getQ_newsDateS());
		else if (!"".equals(getQ_newsDateE()))
			strSQL += " and newDateE=" + Common.sqlChar(getQ_newsDateE());		
		ResultSet rs = db.querySQL(strSQL);
						
		while (rs.next()) {
			for (int i=0; i<arrFileNames.length; i++) {
				doDeleteFile(rs.getString(arrFileNames[i]));
			}			
			//刪除檔案
			/**
			String[] arrFileName=null;	
			if (!"".equals(Common.get(rs.getString("itemPicture1")))) {
				arrFileName=rs.getString("itemPicture1").split(":;:");
				if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
			}
			if (!"".equals(Common.get(rs.getString("itemPicture2")))) {
				arrFileName=rs.getString("itemPicture2").split(":;:");
				if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
			}	
			if (!"".equals(Common.get(rs.getString("itemPicture3")))) {
				arrFileName=rs.getString("itemPicture3").split(":;:");
				if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
			}	
			if (!"".equals(Common.get(rs.getString("itemPicture4")))) {
				arrFileName=rs.getString("itemPicture4").split(":;:");
				if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
			}	
			if (!"".equals(Common.get(rs.getString("itemPicture5")))) {
				arrFileName=rs.getString("itemPicture5").split(":;:");
				if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
			}
			**/
		}
		rs.close();
		
		strSQL += " delete SYSWM_NEWS where 1=1 ";
		if (!"".equals(getQ_newsCat()))
			strSQL += " and newsCat = " + Common.sqlChar(getQ_newsCat()) ;
		if (!"".equals(getQ_newsIDS()) && !"".equals(getQ_newsIDE()))				
			strSQL += " and newsID between " + Common.sqlChar(getQ_newsIDS()) + " and " + Common.sqlChar(getQ_newsIDE());
		else if (!"".equals(getQ_newsIDS()))
			strSQL += " and newsID = " + Common.sqlChar(getQ_newsIDS());
		if (!"".equals(getQ_newsSubject()))
			strSQL += " and newsSubject like '%" + getQ_newsSubject() + "%'" ;
		if (!"".equals(getQ_newsContent()))
			strSQL += " and newsContent like '%" + Common.sqlChar(getQ_newsContent()) + "%'";
		if (!"".equals(getQ_newsDateS()) && !"".equals(getQ_newsDateE()))
			strSQL += " and newsDateS >= " + Common.sqlChar(getQ_newsDateS()) + " and newsDateE<=" + Common.sqlChar(getQ_newsDateE());
		else if (!"".equals(getQ_newsDateS()))
			strSQL += " and newsDateS = " + Common.sqlChar(getQ_newsDateS());
		else if (!"".equals(getQ_newsDateE()))
			strSQL += " and newDateE=" + Common.sqlChar(getQ_newsDateE());		
		db.exeSQL(strSQL);
		setStateDeleteSuccess();
		setErrorMsg("全部刪除完成");
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSWM001F  queryOne() throws Exception{
	Database db = new Database();
	SYSWM001F obj = this;
	try {
		String sql=" select a.newsCat, a.newsID, a.newsSubject, a.newsContent, a.newsDateS, a.newsDateE, a.itemPicture1, a.itemPicture2, a.itemPicture3, a.itemPicture4, a.itemPicture5, a.itemPicture6, a.itemPicture7, a.itemPicture8, a.itemPicture9, a.itemPicture10, a.isHTML, a.editID, a.editDate, a.editTime  "+
			" from SYSWM_NEWS a where a.newsID = " + Common.sqlChar(newsID);		
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setNewsCat(rs.getString("newsCat"));
			obj.setNewsID(rs.getString("newsID"));
			obj.setNewsSubject(rs.getString("newsSubject"));
			obj.setNewsContent(rs.getString("newsContent"));
			obj.setNewsDateS(rs.getString("newsDateS"));
			obj.setNewsDateE(rs.getString("newsDateE"));	
			/**
			StringBuffer sbHTML = new StringBuffer(500).append("");			
			for (int i=0; i<arrFileNames.length; i++) {
				String[] arrFileName;
				String attFile = Common.get(rs.getString(arrFileNames[i]));
				arrFileName=attFile.split(":;:");
				if (arrFileName.length>1) {
					sbHTML.append("<tr>\n");
					sbHTML.append("<td bgcolor='E2F0CF' class='td_form'>附件").append((i+1)).append("：</td>");
					sbHTML.append("<td bgcolor='F6FCE9' class='td_form_white'  style='text-align:left'>");
					sbHTML.append("<a href=\"downloadFileSimple?fileID=").append(attFile).append("\">");
					sbHTML.append(arrFileName[1]);
					sbHTML.append("</a></td>\n");
					sbHTML.append("</tr>\n");
				}
			}
			obj.setItemPictureHTML(sbHTML.toString());
			**/			
			obj.setItemPicture1(rs.getString("itemPicture1"));
			obj.setItemPicture2(rs.getString("itemPicture2"));
			obj.setItemPicture3(rs.getString("itemPicture3"));
			obj.setItemPicture4(rs.getString("itemPicture4"));
			obj.setItemPicture5(rs.getString("itemPicture5"));			
			obj.setItemPicture6(rs.getString("itemPicture6"));
			obj.setItemPicture7(rs.getString("itemPicture7"));
			obj.setItemPicture8(rs.getString("itemPicture8"));
			obj.setItemPicture9(rs.getString("itemPicture9"));
			obj.setItemPicture10(rs.getString("itemPicture10"));
				
			obj.setIsHTML(rs.getString("isHTML"));			
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
			obj.setEditTime(rs.getString("editTime"));			
			genAttFileHTML(rs);			
			
		}
		setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return obj;
}

private void genAttFileHTML(ResultSet rs) {
	StringBuffer sbHTML = new StringBuffer(500).append("");			
	for (int i=0; i<arrFileNames.length; i++) {
		String[] arrFileName;
		String attFile;
		try {
			attFile = Common.get(rs.getString(arrFileNames[i]));
			arrFileName=attFile.split(":;:");
			if (arrFileName.length>1) {
				sbHTML.append("<tr>\n");
				sbHTML.append("<td bgcolor='E2F0CF' class='td_form'>附件").append((i+1)).append("：</td>");
				sbHTML.append("<td bgcolor='F6FCE9' class='td_form_white'  style='text-align:left'>");
				sbHTML.append("<a class=\"text_link_b\" href=\"downloadFileSimple?fileID=").append(attFile).append("\">");
				sbHTML.append(arrFileName[1]);
				sbHTML.append("</a></td>\n");
				sbHTML.append("</tr>\n");
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	setItemPictureHTML(sbHTML.toString());	
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
		String sql=" select a.newsID, a.newsSubject, a.newsDateS, a.newsDateE, a.editID "+
			" from SYSWM_NEWS a where 1=1 "; 
			if ("Y".equals(getIsQuery())) {
				if (!"".equals(getQ_newsDate())) 
					sql+=" and a.newsDateS <= " + Common.sqlChar(getQ_newsDate()) + " and a.newsDateE>=" + Common.sqlChar(getQ_newsDate());
				else sql+=" and a.newsDateS <= " + Common.sqlChar(Datetime.getYYYMMDD()) + " and a.newsDateE>=" + Common.sqlChar(Datetime.getYYYMMDD());
				if (!"".equals(getQ_newsCat()))
					sql+=" and a.newsCat = " + Common.sqlChar(getQ_newsCat()) ;	
				//改文件下載查詢無效960803
				if (!"".equals(getQ_newsSubject()))
					sql+=" and a.newsSubject like '%" + getQ_newsSubject() + "%'" ;
				if (!"".equals(getQ_newsContent()))
					sql+=" and a.newsContent like '%" + getQ_newsContent() + "%'";
				//改文件下載查詢無效960803
			} else {
				if (!"".equals(getQ_newsCat()))
					sql+=" and a.newsCat = " + Common.sqlChar(getQ_newsCat()) ;
				if (!"".equals(getQ_newsIDS()) && !"".equals(getQ_newsIDE()))				
					sql+=" and a.newsID between " + Common.sqlChar(getQ_newsIDS()) + " and " + Common.sqlChar(getQ_newsIDE());
				else if (!"".equals(getQ_newsIDS()))
					sql+=" and a.newsID = " + Common.sqlChar(getQ_newsIDS());
				if (!"".equals(getQ_newsSubject()))
					sql+=" and a.newsSubject like '%" + getQ_newsSubject() + "%'" ;
				if (!"".equals(getQ_newsContent()))
					sql+=" and a.newsContent like '%" + getQ_newsContent() + "%'";
				else if (!"".equals(getQ_newsDateS()))
					sql+=" and a.newsDateS >= " + Common.sqlChar(getQ_newsDateS());
				else if (!"".equals(getQ_newsDateE()))
					sql+=" and a.newDateE <= " + Common.sqlChar(getQ_newsDateE());
			}				
		ResultSet rs = db.querySQL(sql + " order by newsID desc", true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
				String rowArray[]=new String[5];
				rowArray[0]=rs.getString("newsID"); 
				rowArray[1]=rs.getString("newsSubject"); 
				rowArray[2]=rs.getString("newsDateS"); 
				rowArray[3]=rs.getString("newsDateE"); 
				rowArray[4]=rs.getString("editID"); 
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


private boolean doDeleteFile(String fileName) {
	String[] arrFileName=null;	
	if (!"".equals(Common.get(fileName))) {
		arrFileName=Common.get(fileName).split(":;:");
		if (arrFileName.length>1) {
			Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));
			return true;
		}
		
	}	
	return false;
}

}


