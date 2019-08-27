/*
 *<br>程式目的：性別統計表縣市資料維護作業
 *<br>程式代號：
 *<br>撰寫日期： 
 *<br>程式作者：TzuYi.Yang
 *<br>--------------------------------------------------------
 *<br>修改作者　　修改日期　　　修改目的
 *<br>--------------------------------------------------------
 */

package lca.ap;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EFORM501 extends EFORM5_SuperBean {
	
	String doProcessResult;
	public String getDoProcessResult() {return checkGet(doProcessResult);}
	public void setDoProcessResult(String s) {doProcessResult = checkSet(s);}
	
	String q_CTY;
	public String getQ_CTY() {return checkGet(q_CTY);}
	public void setQ_CTY(String s) {q_CTY = checkSet(s);}
	
	String q_KCNT;
	public String getQ_KCNT() {return checkGet(q_KCNT);}
	public void setQ_KCNT(String s) {q_KCNT = checkSet(s);}
	
	String q_CTY_YN;
	public String getQ_CTY_YN() {return checkGet(q_CTY_YN);}
	public void setQ_CTY_YN(String s) {q_CTY_YN = checkSet(s);}
	
	String CTY;
	public String getCTY() {return checkGet(CTY);}
	public void setCTY(String s) {CTY = checkSet(s);}
	
	String KCNT;
	public String getKCNT() {return checkGet(KCNT);}
	public void setKCNT(String s) {KCNT = checkSet(s);}
	
	String MERGE_CTY;
	public String getMERGE_CTY() {return checkGet(MERGE_CTY);}
	public void setMERGE_CTY(String s) {MERGE_CTY = checkSet(s);}
	
	String CTY_USER;
	public String getCTY_USER() {return checkGet(CTY_USER);}
	public void setCTY_USER(String s) {CTY_USER = checkSet(s);}
	
	String MERGE_CTY_USER;
	public String getMERGE_CTY_USER() {return checkGet(MERGE_CTY_USER);}
	public void setMERGE_CTY_USER(String s) {MERGE_CTY_USER = checkSet(s);}
	
	String ORDERBY;
	public String getORDERBY() {return checkGet(ORDERBY);}
	public void setORDERBY(String s) {ORDERBY = checkSet(s);}
	
	String CTY_YN;
	public String getCTY_YN() {return checkGet(CTY_YN);}
	public void setCTY_YN(String s) {CTY_YN = checkSet(s);}
	
	//傳回執行insert前之檢查sql
	protected String[][] getInsertCheckSQL(){
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0] = " SELECT CTY FROM EFORM5_0 WHERE CTY = " + Common.sqlChar(CTY);
		checkSQLArray[0][1] = ">";
		checkSQLArray[0][2] = "0";
		checkSQLArray[0][3] = "該筆縣市代碼已存在，請重新輸入！";
		return checkSQLArray;
	}
	
	//傳回insert sql
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		execSQLArray[0] = " INSERT INTO EFORM5_0 ( "
				+ " CTY, "
				+ " KCNT, "
				+ " MERGE_CTY, "
				+ " CTY_USER, "
				+ " MERGE_CTY_USER, "
				+ " ORDERBY, "
				+ " CTY_YN "
				+ " ) VALUES ( "
				+ Common.sqlChar(CTY) + ", "
				+ Common.sqlChar(Common.big5ToIso(KCNT)) + ", "
				+ Common.sqlChar(MERGE_CTY) + ", "
				+ Common.sqlChar(CTY_USER) + ", "
				+ Common.sqlChar(MERGE_CTY_USER) + ", "
				+ Common.sqlChar(ORDERBY) + ", "
				+ Common.sqlChar(CTY_YN)
				+ " ) ";
		return execSQLArray;
	}
	
	//傳回update sql
	protected String[] getUpdateSQL(){
		String[] execSQLArray = new String[1];
		
		execSQLArray[0]=" UPDATE EFORM5_0 SET "
				+ " KCNT = " + Common.sqlChar(Common.big5ToIso(KCNT)) + ", "
				+ " MERGE_CTY = " + Common.sqlChar(MERGE_CTY) + ", "
				+ " CTY_USER = " + Common.sqlChar(CTY_USER) + ", "
				+ " MERGE_CTY_USER = " + Common.sqlChar(MERGE_CTY_USER) + ", "
				+ " ORDERBY = " + Common.sqlChar(ORDERBY) + ", "
				+ " CTY_YN = " + Common.sqlChar(CTY_YN)
				+ " WHERE 1=1 "
				+ " AND CTY = " + Common.sqlChar(CTY);
		return execSQLArray;
	}
    
	//傳回delete sql
	protected String[] getDeleteSQL(){
		String[] execSQLArray = new String[1];
		
		execSQLArray[0]=" DELETE FROM EFORM5_0 WHERE 1=1 "
				+ " AND CTY = " + Common.sqlChar(CTY);
		return execSQLArray;
	}
	
	/**
	 * <br>
	 * <br>目的：依主鍵查詢單一資料
	 * <br>參數：無
	 * <br>傳回：傳回本物件
	*/

	public EFORM501  queryOne() throws Exception{
		ODatabase db = new ODatabase();
		EFORM501 obj = this;
		ResultSet rs = null;
		try {
			String sql = " SELECT * FROM EFORM5_0 WHERE 1=1 "
					+ " AND CTY = " + Common.sqlChar(CTY);

			rs = db.querySQL(sql);
			if (rs.next()){
				obj.setCTY(rs.getString("CTY"));
				obj.setKCNT(Common.isoToBig5(rs.getString("KCNT")));
				obj.setMERGE_CTY(rs.getString("MERGE_CTY"));
				obj.setCTY_USER(rs.getString("CTY_USER"));
				obj.setMERGE_CTY_USER(rs.getString("MERGE_CTY_USER"));
				obj.setORDERBY(rs.getString("ORDERBY"));
				obj.setCTY_YN(rs.getString("CTY_YN"));
			}
			setStateQueryOneSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMsg("查詢失敗");
		} finally {
			try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
			db.closeAll();
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
		ODatabase db = new ODatabase();
		ArrayList objList = new ArrayList();
		ResultSet rs = null;
		int counter=0;
		
		try {
			String sql = " SELECT CTY, KCNT, CTY_USER, ORDERBY, CTY_YN FROM EFORM5_0 WHERE 1=1 ";
			
			if (!"".equals(getQ_CTY())){
				sql += " and CTY = " + Common.sqlChar(getQ_CTY());
			}
			
			if (!"".equals(getQ_KCNT())){
				sql += " and KCNT like '%" + Common.big5ToIso(getQ_KCNT()) + "%' ";
			}
			
			if (!"".equals(getQ_CTY_YN())){
				sql += " and CTY_YN = " + Common.sqlChar(getQ_CTY_YN());
			}
			sql +=  " ORDER BY ORDERBY ";
			//System.out.println(sql);

			rs = db.querySQL(sql);
			while (rs.next()){
				counter++;
				String rowArray[] = new String[5];
				rowArray[0] = Common.get(rs.getString("CTY"));
				rowArray[1] = Common.isoToBig5(rs.getString("KCNT"));
				rowArray[2] = Common.get(rs.getString("CTY_USER"));
				rowArray[3] = Common.get(rs.getString("ORDERBY"));
				if(Common.get(rs.getString("CTY_YN")).equals("Y")){
					rowArray[4] = "是";
				}else if(Common.get(rs.getString("CTY_YN")).equals("N")){
					rowArray[4] = "否";
				}
				else{ rowArray[4] = ""; }
				
				objList.add(rowArray);
				
				if (counter==getListLimit()){
					setErrorMsg(getListLimitError());
					break;
				}
			}
			setStateQueryAllSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMsg("查詢失敗");
		} finally {
			db.closeAll();
		}
		return objList;
	}
}