/*
*<br>程式目的：所有的bean都需繼承此類別
*<br>程式代號：SuperBean
*<br>撰寫日期：93/12/01
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/
package util;

import java.sql.ResultSet;

public class SuperBean {
	
	int listLimit=500;
	public int getListLimit(){ return listLimit; }
	public void setListLimit(int i){ listLimit=i; }
	public String getListLimitError() { return "查詢資料超過"+getListLimit()+"筆, 請縮小查詢範圍！"; }

	String state="init";
	public String getState(){ return checkGet(state); }
	public void setState(String s){ state=checkSet(s); }
	
	String errorMsg;
	public String getErrorMsg(){ return checkGet(errorMsg); }
	public void setErrorMsg(String s){ errorMsg=checkSet(s); }	

	String queryAllFlag="false";
	public String getQueryAllFlag(){ return checkGet(queryAllFlag); }
	public void setQueryAllFlag(String s){ queryAllFlag=checkSet(s); }	
	
	String userID;
	public String getUserID(){ return checkGet(userID); }
	public void setUserID(String s){ userID=checkSet(s); }
	
	String unitID;
	public String getUnitID() {
		return checkGet(unitID);
	}
	public void setUnitID(String unitID) {
		this.unitID = checkSet(unitID);
	}	
	
	
	String editID;
	public String getEditID(){ return checkGet(editID); }
	public void setEditID(String s){ editID=checkSet(s); }
	
	String editDate;
	public String getEditDate(){ return checkGet(editDate); }
	public void setEditDate(String s){ editDate=checkSet(s); }
	
	String editTime;
	public String getEditTime(){ return checkGet(editTime); }
	public void setEditTime(String s){ editTime=checkSet(s); }	
	
	String nextInsert;
	public String getNextInsert(){ return checkGet(nextInsert); }
	public void setNextInsert(String s){ nextInsert=checkSet(s); }		
	
	//檢查資料隱碼之旗標
	String sqlInjectionFlag="false";

	
	
	//傳回Insert前之檢查SQL及message　
	protected String[][] getInsertCheckSQL(){String [][] rtnArray={{"","","",""}}; return rtnArray;}	
	//傳回Insert sql
	protected String[] getInsertSQL(){String [] rtnArray={""}; return rtnArray; }
	//傳回Update前之檢查SQL及message　
	protected String[][] getUpdateCheckSQL(){String [][] rtnArray={{"","","",""}}; return rtnArray;}	
	//傳回Update sql
	protected String[] getUpdateSQL(){String [] rtnArray={""}; return rtnArray; }
	//傳回Delete前之檢查SQL及message　
	protected String[][] getDeleteCheckSQL(){String [][] rtnArray={{"","","",""}}; return rtnArray;}	
	//傳回Delete sql
	protected String[] getDeleteSQL(){String [] rtnArray={""}; return rtnArray; }

	
	/*回傳各種狀態值*/
	public void setStateInsertSuccess()  {		
		setState("insertSuccess");
		if (!"".equals(getNextInsert())) {
			setState("insert");	
		}		
	}
	public void setStateInsertError()    { setState("insertError"); }
	public void setStateUpdateSuccess()  { setState("updateSuccess"); }
	public void setStateUpdateError()    { setState("updateError"); }
	public void setStateDeleteSuccess()  { setState("deleteSuccess"); }
	public void setStateDeleteError()    { setState("deleteError"); }
	public void setStateQueryOneSuccess(){ 
		setState("queryOneSuccess");
		//setState("edit");
	}
	public void setStateQueryAllSuccess(){ 
		if ("queryAll".equals(getState())){
			setState("queryAllSuccess");			
		}
	}
	
  	/**
  	 * <br>
   	 * <br>目的：撰寫JavaBean chect get方法時所需套用的函數
   	 * <br>參數：資料字串
     * <br>傳回：檢查資料為null,是則傳回空字串
  	*/
	public String checkGet(String s){
		if(s==null){
			return "";
		}else{
			s = s.trim();	
			s = s.replaceAll("<", "&lt;");
			s = s.replaceAll(">","&gt;");
			//s = org.apache.commons.lang.StringEscapeUtils.escapeHtml(s);
			return s;
		}
	}
  	/**
  	 * <br>
  	 * <br>目的：撰寫JavaBean check set方法時所需套用的函數
  	 * <br>參數：資料字串
  	 * <br>傳回：將資料前後空白去除
  	*/
	public String checkSet(String s){
		if(s==null){
			return s;
		}else{
			//檢查資料隱碼
			if ("false".equals(sqlInjectionFlag)){
				if ((s.indexOf("'")>=0)||(s.indexOf("\"")>=0))	{
					sqlInjectionFlag="true";
				}
			}	
			return s.trim().replaceAll("'", "''");
		}
	}	
	
	/**
  	 * <br>
  	 * <br>目的：執行新增, 修改, 刪除前之邏輯檢查
  	 * <br>參數：傳入字串二維陣列, 允許多筆邏輯檢查
  	 * <br>　　　[0][0]:檢查之SQL語法, 查詢欄位只允許一個, 需加上as checkResult
  	 * <br>　　　[0][1]:七種條件式("EOF","=","!=",">",">=","<","<=")
  	 * <br>　　　[0][2]:條件值(需為數字字串,如條件式為EOF則不須輸入條件值)
  	 * <br>　　　[0][3]:錯誤訊息
  	 * <br>傳回：成功傳回true, 失敗傳回false
  	*/
	public boolean beforeExecCheck(String [][] checkSQLArray){
		boolean rtnBoolean=true;
		int checkResult=0;
		int condition=0;
		Database db = new Database();
		ResultSet rs;	
		
		if ("true".equals(sqlInjectionFlag)){
			setErrorMsg("所有欄位均不允許輸入[單引號]及[雙引號]，請重新檢查！");
			if ("insert".equals(getState()) || "insertError".equals(getState())) {
				setStateInsertError();
			} else { 
				setStateUpdateError();  
			}
			return false;
		}
		try {		

			for(int i=0; i<checkSQLArray.length; i++){
				if (!"".equals(checkSQLArray[i][0].toString())){
					rs = db.querySQL(checkSQLArray[i][0]);
					if (rs.next()){
						try{
							checkResult=Integer.parseInt(rs.getString("checkResult"));
							condition=Integer.parseInt(checkSQLArray[i][2]);
						} catch (Exception e) {
							continue;
						}		
						if ("=".equals(checkSQLArray[i][1])){
							if (checkResult==condition)
								rtnBoolean=false;
						}else if ("!=".equals(checkSQLArray[i][1])){
							if (checkResult!=condition)
								rtnBoolean=false;						
						}else if (">".equals(checkSQLArray[i][1])){
							if (checkResult>condition)
								rtnBoolean=false;						
						}else if (">=".equals(checkSQLArray[i][1])){
							if (checkResult>=condition)
								rtnBoolean=false;						
						}else if ("<".equals(checkSQLArray[i][1])){
							if (checkResult<condition)
								rtnBoolean=false;						
						}else if ("<=".equals(checkSQLArray[i][1])){
							if (checkResult<=condition)
								rtnBoolean=false;						
						}						
					}else{
						if ("EOF".equals(checkSQLArray[i][1]))
							rtnBoolean=false;
					}
					if (!rtnBoolean){
						setErrorMsg(checkSQLArray[i][3]);
						break;
					}
				}
			}		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}		
		return rtnBoolean;
	}
	
	
	
  	/**
  	 * <br>
  	 * <br>目的：新增儲存
  	 * <br>參數：無
  	 * <br>傳回：無
  	*/	
	public void insert(){
		Database db = new Database();
		
		try {
			if (!beforeExecCheck(getInsertCheckSQL())){
				setStateInsertError();
			}else{
				setEditID(getUserID());
				setEditDate(Datetime.getYYYMMDD());
				setEditTime(Datetime.getHHMMSS());				
				db.excuteSQL(getInsertSQL());		
				setStateInsertSuccess();
				setErrorMsg("新增完成");				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}			
	}

  	/**
  	 * <br>
  	 * <br>目的：修改儲存
  	 * <br>參數：無
  	 * <br>傳回：無
  	*/	
	public void update(){
		Database db = new Database();
		try {
			if (!beforeExecCheck(getUpdateCheckSQL())){
				setStateUpdateError();
			}else{
				setEditID(getUserID());
				setEditDate(Datetime.getYYYMMDD());
				setEditTime(Datetime.getHHMMSS());					
				db.excuteSQL(getUpdateSQL());		
				setStateUpdateSuccess();
				setErrorMsg("修改完成");			
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}			
	}	

  	/**
  	 * <br>
  	 * <br>目的：刪除
  	 * <br>參數：無
  	 * <br>傳回：無
  	*/	
	public void delete() {
		Database db = new Database();
		try {
			if (!beforeExecCheck(getDeleteCheckSQL())){
				setStateDeleteError();
			}else{
				setEditID(getUserID());
				setEditDate(Datetime.getYYYMMDD());
				setEditTime(Datetime.getHHMMSS());					
				db.excuteSQL(getDeleteSQL());		
				setStateDeleteSuccess();
				setErrorMsg("刪除完成");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}			
	}
	
	
}
