/*
*<br>程式目的：基本代碼管理
*<br>程式代號：Sys101f
*<br>撰寫日期：93/12/01
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/
package sys.ca;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSCA001F extends SuperBean{

	//bena field , get and set method---------------------------------
	String codeKindID;
	String codeKindName;
	String codeID;
	String codeName;
	String codeCon1;
	String codeCon2;
	String codeCon3;
	String memo;
	String q_codeKindID;
	String oldCodeKindID;
	
	public String getCodeKindID(){ return checkGet(codeKindID); }
	public void setCodeKindID(String s){ codeKindID=checkSet(s); }
	public String getOldCodeKindID(){ return checkGet(oldCodeKindID); }
	public void setOldCodeKindID(String s){ oldCodeKindID=checkSet(s); }	
	public String getCodeKindName(){ return checkGet(codeKindName); }
	public void setCodeKindName(String s){ codeKindName=checkSet(s); }		
	public String getCodeID(){ return checkGet(codeID); }
	public void setCodeID(String s){ codeID=checkSet(s); }
	public String getCodeName(){ return checkGet(codeName); }
	public void setCodeName(String s){ codeName=checkSet(s); }
	public String getCodeCon1(){ return checkGet(codeCon1); }
	public void setCodeCon1(String s){ codeCon1=checkSet(s); }
	public String getCodeCon2(){ return checkGet(codeCon2); }
	public void setCodeCon2(String s){ codeCon2=checkSet(s); }
	public String getCodeCon3(){ return checkGet(codeCon3); }
	public void setCodeCon3(String s){ codeCon3=checkSet(s); }	

	public String getMemo(){ return checkGet(memo); }
	public void setMemo(String s){ memo=checkSet(s); }

	public String getQ_codeKindID(){ return checkGet(q_codeKindID); }
	public void setQ_codeKindID(String s){ q_codeKindID=checkSet(s); }

	//***************************************************************
	//執行新增, 修改, 刪除前之邏輯檢查, 須傳入字串二維陣列
 	//[0][0]:檢查之SQL語法, 查詢欄位只允許一個, 需加上as checkResult
  	//[0][1]:六種條件式("EOF","=","!=",">",">=","<","<=")
  	//[0][2]:條件值(需為數字字串,如條件式為EOF則不須輸入條件值)
  	//[0][3]:錯誤訊息
	//***************************************************************
	
	//傳回執行insert前之檢查sql
	protected String[][] getInsertCheckSQL(){
		String[][] checkSQLArray = new String[1][4];
		checkSQLArray[0][0]=" select count(*) as checkResult from SYSCA_Code " +
					" where codeKindID = " + Common.sqlChar(codeKindID) +
					" and codeID = " + Common.sqlChar(codeID);
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
		
		return checkSQLArray;
	}
	
  	//傳回insert sql　
  	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0]=" insert into SYSCA_Code(" +
				" codeKindID,"+
				" codeID,"+
				" codeName,"+
				" codeCon1,"+
				" codeCon2,"+
				" codeCon3,"+				
				" memo,"+	
				" editID,"+
				" editDate,"+
				" editTime"+				
			" )Values(" +
				Common.sqlChar(codeKindID) + "," +
				Common.sqlChar(codeID) + "," +
				Common.sqlChar(codeName) + "," +
				Common.sqlChar(codeCon1) + "," +
				Common.sqlChar(codeCon2) + "," +
				Common.sqlChar(codeCon3) + "," +				
				Common.sqlChar(memo) + "," +
				Common.sqlChar(getEditID()) + "," +
				Common.sqlChar(getEditDate()) + "," +
				Common.sqlChar(getEditTime()) + ")" ;
		return execSQLArray;
	}
	
  	// 傳回update sql
	protected String[] getUpdateSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0]=" update SYSCA_Code set" +
				" codeName = " + Common.sqlChar(codeName) + "," +
				" codeCon1 = " + Common.sqlChar(codeCon1) + "," +
				" codeCon2 = " + Common.sqlChar(codeCon2) + "," +
				" codeCon3 = " + Common.sqlChar(codeCon3) + "," +				
				" memo = " + Common.sqlChar(memo) + "," +
				" editID = " + Common.sqlChar(getEditID()) + "," +
				" editDate = " + Common.sqlChar(getEditDate()) + "," +
				" editTime = " + Common.sqlChar(getEditTime()) +  
			" where codeKindID = " + Common.sqlChar(codeKindID) +
				" and codeID = " + Common.sqlChar(codeID);		
		return execSQLArray;
	}
	
	//傳回delete sql
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0]=" delete SYSCA_Code " +
			" where codeKindID = " + Common.sqlChar(codeKindID) +
			" and codeID = " + Common.sqlChar(codeID);
		return execSQLArray;
	}
	
  	/**
  	 * <br>
  	 * <br>目的：依主鍵查詢單一資料　
  	 * <br>參數：無
  	 * <br>傳回：傳回本物件
  	*/
	public SYSCA001F  queryOne() throws Exception{
		Database db = new Database();
		SYSCA001F obj = this;
		try {
			String sql=" select codeKindID, codeID, codeName, codeCon1, codeCon2, codeCon3, memo, editID, editDate, editTime  "+
							" from SYSCA_Code  " +
						" where codeKindID = " + Common.sqlChar(codeKindID) +
							" and codeID = " + Common.sqlChar(codeID) ;
			ResultSet rs = db.querySQL(sql);
			if (rs.next()){												
				obj.setCodeName(rs.getString("codeName"));
				obj.setCodeID(rs.getString("codeID"));
				obj.setCodeName(rs.getString("codeName"));
				obj.setCodeCon1(rs.getString("codeCon1"));
				obj.setCodeCon2(rs.getString("codeCon2"));
				obj.setCodeCon3(rs.getString("codeCon3"));				
				obj.setMemo(rs.getString("memo"));
				obj.setEditID(rs.getString("editID"));
				obj.setEditDate(rs.getString("editDate"));
				obj.setEditTime(rs.getString("editTime"));				
			}			
			setStateQueryOneSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return obj;
	}

  	
  	/**
  	 * <br>
  	 * <br>目的：依查詢欄位查詢多筆資料　
  	 * <br>參數：無
  	 * <br>傳回：傳回Vector
  	*/
	public ArrayList queryAll() throws Exception{		
		Database db = new Database();
		ArrayList objList=new ArrayList();
		try {
			String sql=" select a.codeKindID, a.codeID, a.codeName, a.codeCon1, a.codeCon2, a.codeCon3, a.memo, a.editID, a.editDate, a.editTime, b.codeKindName "+
							" from SYSCA_Code a, SYSCA_CodeKind b " +
						" where a.codeKindID = " + Common.sqlChar(q_codeKindID) +
							" and a.codeKindID = b.codeKindID ";
			ResultSet rs = db.querySQL(sql+" order by a.codeID ");
			while (rs.next()){			
				String rowArray[]=new String[10];
				rowArray[0]=rs.getString("codeKindID");	
				rowArray[1]=rs.getString("codeKindName");
				rowArray[2]=rs.getString("codeID");
				rowArray[3]=rs.getString("codeName");
				rowArray[4]=Common.get(rs.getString("codeCon1"));
				rowArray[5]=Common.get(rs.getString("codeCon2"));
				rowArray[6]=Common.get(rs.getString("codeCon3"));
				rowArray[7]=Common.get(rs.getString("memo"));
				rowArray[8]=Common.get(rs.getString("editID"));
				rowArray[9]=Common.get(rs.getString("editDate"));
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

