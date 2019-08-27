/*
 *<br>程式目的：土地所有權人性別統計
 *<br>程式代號：
 *<br>撰寫日期： 
 *<br>程式作者：TzuYi.Yang
 *<br>--------------------------------------------------------
 *<br>修改作者　　修改日期　　　修改目的
 *<br>--------------------------------------------------------
 */

package lca.ap;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.*;

public class EFORM502 extends EFORM5_SuperBean {
	
	String doProcessResult;
	public String getDoProcessResult() {return checkGet(doProcessResult);}
	public void setDoProcessResult(String s) {doProcessResult = checkSet(s);}
	
	String q_USER_ID;
	public String getQ_USER_ID() {return checkGet(q_USER_ID);}
	public void setQ_USER_ID(String s) {q_USER_ID = checkSet(s);}
	
	String q_PROCESS_STATE;
	public String getQ_PROCESS_STATE() {return checkGet(q_PROCESS_STATE);}
	public void setQ_PROCESS_STATE(String s) {q_PROCESS_STATE = checkSet(s);}
	
	String q_PROCESS_DATE_S;
	public String getQ_PROCESS_DATE_S() {return checkGet(q_PROCESS_DATE_S);}
	public void setQ_PROCESS_DATE_S(String s) {q_PROCESS_DATE_S = checkSet(s);}
	
	String q_PROCESS_DATE_E;
	public String getQ_PROCESS_DATE_E() {return checkGet(q_PROCESS_DATE_E);}
	public void setQ_PROCESS_DATE_E(String s) {q_PROCESS_DATE_E = checkSet(s);}
	
	String lastProcessState;
	public String getLastProcessState() {return checkGet(lastProcessState);}
	public void setLastProcessState(String s) {lastProcessState = checkSet(s);}
	
	String lastProcessStateName;
	public String getLastProcessStateName() {return checkGet(lastProcessStateName);}
	public void setLastProcessStateName(String s) {lastProcessStateName = checkSet(s);}
	
	String lastProcessTime;
	public String getLastProcessTime() {return checkGet(lastProcessTime);}
	public void setLastProcessTime(String s) {lastProcessTime = checkSet(s);}
	
	String lastProcessDetail;
	public String getLastProcessDetail() {return checkGet(lastProcessDetail);}
	public void setLastProcessDetail(String s) {lastProcessDetail = checkSet(s);}
	
	//取得上次產製紀錄
	public void getLastProcess(){
		Database db = new Database();
		ODatabase odb = new ODatabase();
		ResultSet rs = null;
		try {
			String dataSql = " SELECT e.PROCESS_STATE, e.PROCESS_STATE_NOTE, r.KCNT PROCESS_STATE_NAME, e.PROCESS_DATE_S, e.PROCESS_TIME_S "
					+ " FROM EFORM5_LOG e, "
					+ " (SELECT KCDE_2, KCNT FROM RKEYN WHERE KCDE_1 = 'ES') r "
					+ " WHERE 1=1 "
					+ " AND e.PROCESS_STATE = r.KCDE_2 "
					+ " AND e.RPT_ID = 'EFORM502' ORDER BY ID DESC ";
			rs = db.querySQL(dataSql);
			String PROCESS_STATE_NOTE = "";
			if(rs.next()){  //只取最新一筆
				PROCESS_STATE_NOTE = Common.MaskCDate(rs.getString("PROCESS_STATE_NOTE"));
				setLastProcessTime(Common.MaskCDate(rs.getString("PROCESS_DATE_S")) + " "
						+ Common.formatHHMMSS(rs.getString("PROCESS_TIME_S")));
				setLastProcessState(Common.get(rs.getString("PROCESS_STATE")));
				setLastProcessStateName(Common.get(rs.getString("PROCESS_STATE_NAME")));
				rs.close();
			}else{
				setLastProcessTime("尚無產製紀錄");
				setLastProcessState("");
				setLastProcessStateName("");
			}
			
			if(getLastProcessState().equals("1")){  //若狀態為「寫入所有權人明細資料中」
				String cntSql = "SELECT COUNT(*) FROM EFORM5_2_D ";
				String cntStr = "所有權人明細";
				rs = odb.querySQL(cntSql);
				if(rs.next()){
					setLastProcessDetail(cntStr + ":" + PROCESS_STATE_NOTE + " " + rs.getString(1) + "筆");
				}
				rs.close();
			}else if(getLastProcessState().equals("3")){  //若狀態為「寫入所有權人歸戶統計結果中」
				String cntSql = " SELECT a.cnt5_1, b.cnt5_2, c.cnt5_3, d.cnt5_4 FROM "
						+ " (SELECT COUNT(*) cnt5_1 FROM EFORM5_1 WHERE RPT_FLAG = '1') a, "
						+ " (SELECT COUNT(*) cnt5_2 FROM EFORM5_2 WHERE RPT_FLAG = '1') b, "
						+ " (SELECT COUNT(*) cnt5_3 FROM EFORM5_3 WHERE RPT_FLAG = '1') c, "
						+ " (SELECT COUNT(*) cnt5_4 FROM EFORM5_4 WHERE RPT_FLAG = '1') d ";
				rs = odb.querySQL(cntSql);
				if(rs.next()){
					String cnt5_1 = Common.get(rs.getString("cnt5_1"));
					String cnt5_2 = Common.get(rs.getString("cnt5_2"));
					String cnt5_3 = Common.get(rs.getString("cnt5_3"));
					String cnt5_4 = Common.get(rs.getString("cnt5_4"));
					if(!cnt5_4.equals("") && !cnt5_4.equals("0")){
						setLastProcessDetail("單一所有權人統計:" + cnt5_4 + "筆");
					}else if(!cnt5_3.equals("") && !cnt5_3.equals("0")){
						setLastProcessDetail("分別共有統計:" + cnt5_3 + "筆");
					}else if(!cnt5_2.equals("") && !cnt5_2.equals("0")){
						setLastProcessDetail("公同共有統計:" + cnt5_2 + "筆");
					}else if(!cnt5_1.equals("") && !cnt5_1.equals("0")){
						setLastProcessDetail("總歸戶統計:" + cnt5_1 + "筆");
					}
				}
				rs.close();
			}else{
				setLastProcessDetail("");
			}
		}catch (Exception e) {
	  		e.printStackTrace();
	  	}finally {
	  		try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
	  		db.closeAll();
	  		odb.closeAll();
	  	}
	}

	//執行storedProcedure產製報表所需資料
	public void doProcess() {
		ODatabase db = null;
		CallableStatement cs = null;
		String id = EFORM5_Common.insertLogRecord("EFORM502", getUserID());  //寫入產製開始log並取得id
		try {
			//呼叫SP_INJECT_TEST01(storedProcedure)，將各縣市資料倒入TEST01
			/*
			EFORM5_Common.updateLogState(id, "1");  //寫入產製狀態log
	  		cs = db.getConnection().prepareCall("{call SP_INJECT_TEST01()}");
	  		cs.execute();
	  		*/
			//為了在各縣市產製時能立即知道產製狀況，已將SP_INJECT_TEST01(storedProcedure)直接寫成程式執行SQL
	  		doSP_INJECT_TEST01_1(id);
	  		doSP_INJECT_TEST01_2(id);
			
	  		//呼叫SP_INJECT_TEST01_RESULT(storedProcedure)，將各縣市TEST01資料倒入TEST01_RESULT01、TEST01_RESULT03與TEST01_RESULT05
	  		System.out.println("### EFORM502 1 [" + new java.util.Date()  + "]----id:" + id);
	  		EFORM5_Common.updateLogState(id, "2");  //寫入產製狀態log
	  		System.out.println("### EFORM502 2 [" + new java.util.Date()  + "]----id:" + id);
	  		db = new ODatabase();
	  		cs = db.getConnection().prepareCall("{call SP_INJECT_TEST01_RESULT()}");
	  		System.out.println("### EFORM502 3 [" + new java.util.Date()  + "]----SP_INJECT_TEST01_RESULT --- 1 begin");
	  		cs.execute();
	  		System.out.println("### EFORM502 4 [" + new java.util.Date()  + "]----SP_INJECT_TEST01_RESULT --- 2 finish");
	  		
	  		try{
                cs.close();
            }
            catch(Exception e){
                System.out.println("### EFORM502 4.5 [" + new java.util.Date()  + "]----SP_INJECT_TEST02_RESULT --- cs closing ");
                e.printStackTrace();
            }
	  		db.closeAll();
            System.out.println("### EFORM502 5 [" + new java.util.Date()  + "]----SP_INJECT_TEST02_RESULT --- cs close ");
            
	  		//呼叫SP_INJECT_RBLOW_TEMP01_RESULT(storedProcedure)，將各縣市土地所有權人歸戶資料倒入RBLOW_TEMP_RESULT
            System.out.println("### EFORM502 6 [" + new java.util.Date()  + "]----id:" + id);
	  		EFORM5_Common.updateLogState(id, "3");  //寫入產製狀態log
	  		System.out.println("### EFORM502 6 [" + new java.util.Date()  + "]----id:" + id);
	  		db = new ODatabase();
	  		cs = db.getConnection().prepareCall("{call SP_INJECT_RBLOW_TEMP01_RESULT()}");
	  		System.out.println("### EFORM502 8 [" + new java.util.Date()  + "]----SP_INJECT_RBLOW_TEMP01_RESULT --- 1 begin ");
	  		cs.execute();
	  		System.out.println("### EFORM502 9 [" + new java.util.Date()  + "]----SP_INJECT_RBLOW_TEMP01_RESULT --- 2 finish ");
	  		try{
                cs.close();
            }
            catch(Exception e){
                System.out.println("### EFORM502 9.5 [" + new java.util.Date()  + "]----SP_INJECT_RBLOW_TEMP02_RESULT --- cs closing ");
                e.printStackTrace();
            }
	  		db.closeAll();
            System.out.println("### EFORM502 10 [" + new java.util.Date()  + "]----id:" + id);
	  		EFORM5_Common.updateLogState(id, "0");  //寫入產製狀態log
	  		setDoProcessResult("執行成功!!");
		}catch (Exception e) { 
			EFORM5_Common.updateLogState(id, "4");  //寫入產製失敗log
			setDoProcessResult("執行失敗!!");
	  		e.printStackTrace();
	  	}finally {
	  		try { if (cs!=null){ cs.close(); cs=null;} } catch (Exception e) { e.printStackTrace(); }
	  		if(db != null){
                db.closeAll();
            }
	  	}
	}
	
	//將原SP_INJECT_TEST01(storedProcedure)改為從此處執行SQL指令
	//不需作合併的縣市
	public void doSP_INJECT_TEST01_1(String id) throws Exception{
		ODatabase db = new ODatabase();
		ResultSet rs = null;
		ResultSet rs2 = null;
		String CTY_USER, KCNT;
		try {
			//清空舊資料
System.out.println("doSP_INJECT_TEST01_1 del=" + " DELETE FROM ETEC.EFORM5_2_D ");
			db.excuteSQL(new String[]{" DELETE FROM ETEC.EFORM5_2_D "});
			
			rs = db.querySQL(" SELECT CTY_USER, KCNT FROM ETEC.EFORM5_0 WHERE MERGE_CTY_USER IS NULL AND CTY_YN = 'Y' ");
			while(rs.next()){
				CTY_USER = Common.get(rs.getString("CTY_USER"));
				KCNT = Common.isoToBig5(rs.getString("KCNT"));
				String sql[] = {" INSERT INTO ETEC.EFORM5_2_D "
						+ " SELECT A.CTY CTY, "
						+ " SUBSTR(B.BB09,2,1) SEX, "
						+ " B.BB09, "
						+ " SUM(NVL(B.BB15_3,1) / NVL(B.BB15_2,1)) COUNTS, "
						+ " BB15_1 RANGE, "
						+ " SUM(NVL(A.AA10,0) * NVL(B.BB15_3,1) / NVL(B.BB15_2,1))  AREA, "
						+ " SUM(NVL(A.AA16,0) * NVL(A.AA10,0) * NVL(B.BB15_3,1) / NVL(B.BB15_2,1)) LAND_MONEY "
						+ " FROM " + CTY_USER + ".RALID A, " + CTY_USER + ".RBLOW B, " + CTY_USER + ".RLNID C "
						+ " WHERE A.UNIT = B.UNIT "
						+ " AND A.CTY = B.CTY "
						+ " AND B.CTY = C.CTY "
						+ " AND A.AA48 = B.BA48 "
						+ " AND A.AA49 = B.BA49 "
						+ " AND B.UNIT = C.UNIT "
						+ " AND LENGTH(B.BB09) = 10 "
						+ " AND (SUBSTR(B.BB09,2,1) IN ('1') OR SUBSTR(B.BB09,2,1) IN ('2')) "
						+ " AND B.BB09 = c.LIDN "
						+ " AND C.LCDE = '1' "
						+ " AND B.BB15_2 <> 0 "
						+ " GROUP BY A.CTY,SUBSTR(B.BB09,2,1),BB09,BB15_1,BB15_2,BB15_3 "};
				
System.out.println("doSP_INJECT_TEST01_1 sql=" + sql[0]);
				db.excuteSQL(sql);
				
				EFORM5_Common.updateLogStateNote(id, "1", KCNT);  //寫入產製狀態log
			}
		}finally {
			try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs2!=null){ rs2.close(); rs2=null;} } catch (Exception e) { e.printStackTrace(); }
	  		db.closeAll();
	  	}
	}
	
	//將原SP_INJECT_TEST01(storedProcedure)改為從此處執行SQL指令
	//需作合併的縣市
	public void doSP_INJECT_TEST01_2(String id) throws Exception{
		ODatabase db = new ODatabase();
		ResultSet rs = null;
		ResultSet rs_tmp = null;
		String CTY, CTY_USER, KCNT, MERGE_CTY, MERGE_CTY_USER;
		try {
			//清空舊資料
			db.excuteSQL(new String[]{" DELETE FROM ETEC.EFORM5_2_D_TEMP "});
			
			rs = db.querySQL(" SELECT CTY, CTY_USER, KCNT, MERGE_CTY, MERGE_CTY_USER FROM ETEC.EFORM5_0 WHERE MERGE_CTY_USER IS NOT NULL AND CTY_YN = 'Y' ");
			while(rs.next()){
				CTY = Common.get(rs.getString("CTY"));
				CTY_USER = Common.get(rs.getString("CTY_USER"));
				KCNT = Common.isoToBig5(rs.getString("KCNT"));
				MERGE_CTY = Common.get(rs.getString("MERGE_CTY"));
				MERGE_CTY_USER = Common.get(rs.getString("MERGE_CTY_USER"));
				String sql_1[] = {" INSERT INTO ETEC.EFORM5_2_D_TEMP "
						+ " SELECT A.CTY CTY, "
						+ " SUBSTR(B.BB09,2,1) SEX, "
						+ " B.BB09, "
						+ " SUM(NVL(B.BB15_3,1) / NVL(B.BB15_2,1)) COUNTS, "
						+ " BB15_1 RANGE, "
						+ " SUM(NVL(A.AA10,0) * NVL(B.BB15_3,1) / NVL(B.BB15_2,1)) AREA, "
						+ " SUM(NVL(A.AA16,0) * NVL(A.AA10,0) * NVL(B.BB15_3,1) / NVL(B.BB15_2,1)) LAND_MONEY "
						+ " FROM " + CTY_USER + ".RALID A, " + CTY_USER + ".RBLOW B, " + CTY_USER + ".RLNID C "
						+ " WHERE A.UNIT = B.UNIT "
						+ " AND A.CTY = B.CTY "
						+ " AND B.CTY = C.CTY "
						+ " AND A.AA48 = B.BA48 "
						+ " AND A.AA49 = B.BA49 "
						+ " AND B.UNIT = C.UNIT "
						+ " AND LENGTH(B.BB09) = 10 "
						+ " AND (SUBSTR(B.BB09,2,1) IN ('1') OR SUBSTR(B.BB09,2,1) IN ('2')) "
						+ " AND B.BB09 = c.LIDN "
						+ " AND C.LCDE = '1' "
						+ " AND B.BB15_2 <> 0 "
						+ " GROUP BY A.CTY,SUBSTR(B.BB09,2,1),BB09,BB15_1,BB15_2,BB15_3 "};
				
System.out.println("doSP_INJECT_TEST01_2 sql_1=" + sql_1[0]);
				db.excuteSQL(sql_1);
				
				String sql_2[] = {" INSERT INTO ETEC.EFORM5_2_D_TEMP "
						+ " SELECT A.CTY CTY, "
						+ " SUBSTR(B.BB09,2,1) SEX, "
						+ " B.BB09, "
						+ " SUM(NVL(B.BB15_3,1) / NVL(B.BB15_2,1)) COUNTS, "
						+ " BB15_1 RANGE, "
						+ " SUM(NVL(A.AA10,0) * NVL(B.BB15_3,1) / NVL(B.BB15_2,1))  AREA, "
						+ " SUM(NVL(A.AA16,0) * NVL(A.AA10,0) * NVL(B.BB15_3,1) / NVL(B.BB15_2,1)) LAND_MONEY "
						+ " FROM " + MERGE_CTY_USER + ".RALID A, " + MERGE_CTY_USER + ".RBLOW B, " + MERGE_CTY_USER + ".RLNID C "
						+ " WHERE A.UNIT = B.UNIT "
						+ " AND A.CTY = B.CTY "
						+ " AND B.CTY = C.CTY "
						+ " AND A.AA48 = B.BA48 "
						+ " AND A.AA49 = B.BA49 "
						+ " AND B.UNIT = C.UNIT "
						+ " AND LENGTH(B.BB09) = 10 "
						+ " AND (SUBSTR(B.BB09,2,1) IN ('1') OR SUBSTR(B.BB09,2,1) IN ('2')) "
						+ " AND B.BB09 = c.LIDN "
						+ " AND C.LCDE = '1' "
						+ " AND B.BB15_2 <> 0 "
						+ " GROUP BY A.CTY,SUBSTR(B.BB09,2,1),BB09,BB15_1,BB15_2,BB15_3 "};
				
System.out.println("doSP_INJECT_TEST01_2 sql_2=" + sql_2[0]);
				db.excuteSQL(sql_2);
				
				String sql_3[] = {" INSERT INTO ETEC.EFORM5_2_D (BB09, CTY, SEX, COUNTS, RANGE, AREA, LAND_MONEY) "
						+ " SELECT DISTINCT(BB09), '" + CTY + "' AS CTY, SEX, SUM(COUNTS) COUNTS, RANGE, SUM(AREA) AREA, SUM(LAND_MONEY) LAND_MONEY "
						+ " FROM ETEC.EFORM5_2_D_TEMP "
						+ " WHERE CTY IN ('" + CTY + "','" + MERGE_CTY + "') "
						+ " GROUP BY BB09, SEX, RANGE "};
				
System.out.println("doSP_INJECT_TEST01_2 sql_3=" + sql_3[0]);
				db.excuteSQL(sql_3);
				
				EFORM5_Common.updateLogStateNote(id, "1", KCNT);  //寫入產製狀態log
			}
		}finally {
			try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs_tmp!=null){ rs_tmp.close(); rs_tmp=null;} } catch (Exception e) { e.printStackTrace(); }
	  		db.closeAll();
	  	}
	}
	
	public ArrayList doQuery(){
		Database db = new Database();
		ResultSet rs = null;
		ArrayList objList = new ArrayList();
		try {
			String sql = " SELECT e.*, r.KCNT PROCESS_STATE_NAME FROM "
					+ " EFORM5_LOG e, "
					+ " (SELECT KCDE_2, KCNT FROM RKEYN WHERE KCDE_1 = 'ES') r "
					+ " WHERE e.PROCESS_STATE = r.KCDE_2 "
					+ " AND e.RPT_ID = 'EFORM502' ";
			
			if(!getQ_USER_ID().equals("")){
				sql += " and USER_ID = " + Common.sqlChar(q_USER_ID);
			}
			if(!getQ_PROCESS_STATE().equals("")){
				sql += " and PROCESS_STATE = " + Common.sqlChar(q_PROCESS_STATE);
			}
			if(!getQ_PROCESS_DATE_S().equals("")){
				sql += " and PROCESS_DATE_S >= " + Common.sqlChar(q_PROCESS_DATE_S);
			}
			if(!getQ_PROCESS_DATE_E().equals("")){
				sql += " and PROCESS_DATE_E <= " + Common.sqlChar(q_PROCESS_DATE_E);
			}
			
			sql += " ORDER BY ID DESC ";
			//System.out.println(sql);
			rs = db.querySQL(sql);
			while(rs.next()){
				String rowArray[] = new String[7];
				rowArray[0] = Common.get(rs.getString("ID"));
				rowArray[1] = Common.get(rs.getString("USER_ID"));
				rowArray[2] = Common.get(rs.getString("PROCESS_STATE_NAME"));
				rowArray[3] = Common.MaskDate(rs.getString("PROCESS_DATE_S"));
				rowArray[4] = Common.formatHHMMSS(rs.getString("PROCESS_TIME_S"));
				rowArray[5] = Common.MaskDate(rs.getString("PROCESS_DATE_E"));
				rowArray[6] = Common.formatHHMMSS(rs.getString("PROCESS_TIME_E"));
				
				objList.add(rowArray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
			db.closeAll();
		}
		return objList;
	}
}