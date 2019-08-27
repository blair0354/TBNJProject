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

import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.*;

public class EFORM503 extends POI_EXCEL {
	String doProcessResult;
	public String getDoProcessResult() {return checkGet(doProcessResult);}
	public void setDoProcessResult(String s) {doProcessResult = checkSet(s);}
	
	String lastProcessState;
	public String getLastProcessState() {return checkGet(lastProcessState);}
	public void setLastProcessState(String s) {lastProcessState = checkSet(s);}
	
	String lastProcessStateName;
	public String getLastProcessStateName() {return checkGet(lastProcessStateName);}
	public void setLastProcessStateName(String s) {lastProcessStateName = checkSet(s);}
	
	String lastProcessTime;
	public String getLastProcessTime() {return checkGet(lastProcessTime);}
	public void setLastProcessTime(String s) {lastProcessTime = checkSet(s);}
	
	String lastProcessDate;
    public String getLastProcessDate() {return checkGet(lastProcessDate);}
    public void setLastProcessDate(String s) {lastProcessDate = checkSet(s);}
	
	//取得上次產製紀錄
	public void getLastProcess(){
		Database db = new Database();
		ResultSet rs = null;
		try {
			String sql = " SELECT e.PROCESS_STATE, r.KCNT PROCESS_STATE_NAME, e.PROCESS_DATE_S, e.PROCESS_TIME_S "
					+ " FROM EFORM5_LOG e, "
					+ " (SELECT KCDE_2, KCNT FROM RKEYN WHERE KCDE_1 = 'ES') r "
					+ " WHERE 1=1 "
					+ " AND e.PROCESS_STATE = r.KCDE_2 "
					+ " AND e.RPT_ID = 'EFORM502' ORDER BY ID DESC ";
			rs = db.querySQL(sql);
			if(rs.next()){  //只取最新一筆
				setLastProcessTime(Common.MaskCDate(rs.getString("PROCESS_DATE_S")) + " "
						+ Common.formatHHMMSS(rs.getString("PROCESS_TIME_S")));
				setLastProcessDate(rs.getString("PROCESS_DATE_S"));
				setLastProcessState(Common.get(rs.getString("PROCESS_STATE")));
				setLastProcessStateName(Common.get(rs.getString("PROCESS_STATE_NAME")));
			}else{
				setLastProcessTime("尚無產製紀錄");
				setLastProcessState("");
				setLastProcessStateName("");
			}
		}catch (Exception e) {
	  		e.printStackTrace();
	  	}finally {
	  		try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
	  		db.closeAll();
	  	}
	}
	
	//取得總歸戶土地權屬人資料
	public ArrayList<Object []> getEFORM5_1_data(){
		ODatabase db = new ODatabase();
		ResultSet rs = null;
		ArrayList<Object []> data = new ArrayList<Object []>();
		try {
			String sql = " SELECT e0.CTY, e0.KCNT, e1.RANGE, NVL(e1.CNT, 0) CNT_1, NVL(e2.CNT, 0) CNT_2 "
					+ " FROM "
					+ " (SELECT CTY, KCNT, ORDERBY FROM EFORM5_0) e0, "
					+ " (SELECT CTY, RANGE, CNT FROM EFORM5_1 "
					+ " WHERE RPT_FLAG = '1' AND SEX = '1' AND RANGE = ' ') e1, "
					+ " (SELECT CTY, RANGE, CNT FROM EFORM5_1 "
					+ " WHERE RPT_FLAG = '1' AND SEX = '2' AND RANGE = ' ') e2 "
					+ " WHERE 1=1 "
					+ " AND e0.CTY = e1.CTY(+) "
					+ " AND e0.CTY = e2.CTY(+) "
					+ " ORDER BY e0.ORDERBY ";
			rs = db.querySQL(sql);
			System.out.println("土地權屬人資料sql=" + sql);
			while(rs.next()){
				Object rowData[] = new Object[2];
				rowData[0] = Float.parseFloat(Common.get(rs.getString("CNT_1")));
				rowData[1] = Float.parseFloat(Common.get(rs.getString("CNT_2")));
				data.add(rowData);
			}
		}catch (Exception e) {
	  		e.printStackTrace();
	  	}finally {
	  		try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
	  		db.closeAll();
	  	}
		return data;
	}
	
	//取得單一所有權人
	public ArrayList<Object []> getEFORM5_2_data(){
		ODatabase db = new ODatabase();
		ResultSet rs = null;
		ArrayList<Object []> data = new ArrayList<Object []>();
		try {
			String sql = " SELECT e0.CTY, NVL(e1.CNT, 0) CNT1, NVL(e2.CNT, 0) CNT2, "
					+ " NVL(e1.CNT1, 0) CNT1_1, NVL(e2.CNT1, 0) CNT1_2, "
					+ " NVL(e1.T_AREA, 0) T_AREA_1, NVL(e2.T_AREA, 0) T_AREA_2, "
					+ " NVL(e1.T_MONEY, 0) T_MONEY_1, NVL(e2.T_MONEY, 0) T_MONEY_2 "
					+ " FROM "
					+ " (SELECT CTY, ORDERBY FROM EFORM5_0) e0, "
					+ " (SELECT CTY, CNT, CNT1, T_AREA, T_MONEY FROM EFORM5_2 "
					+ " WHERE RPT_FLAG = '1' AND SEX = '1') e1, "
					+ " (SELECT CTY, CNT, CNT1, T_AREA, T_MONEY FROM EFORM5_2 "
					+ " WHERE RPT_FLAG = '1' AND SEX = '2') e2 "
					+ " WHERE 1=1 "
					+ " AND e0.CTY = e1.CTY(+) "
					+ " AND e0.CTY = e2.CTY(+) "
					+ " ORDER BY e0.ORDERBY ";
			rs = db.querySQL(sql);
			System.out.println("單一所有權人sql=" + sql);
			while(rs.next()){
				Object rowData[] = new Object[8];
				rowData[0] = Float.parseFloat(Common.get(rs.getString("CNT1")));
				rowData[1] = Float.parseFloat(Common.get(rs.getString("CNT2")));
				rowData[2] = Float.parseFloat(Arith.round(rs.getString("CNT1_1"), 4));  //小數4位
				rowData[3] = Float.parseFloat(Arith.round(rs.getString("CNT1_2"), 4));  //小數4位
				rowData[4] = Float.parseFloat(Arith.divide(rs.getString("T_AREA_1"), "10000", 4));  //面積除以10000至小數4位
				rowData[5] = Float.parseFloat(Arith.divide(rs.getString("T_AREA_2"), "10000", 4));  //面積除以10000至小數4位
				rowData[6] = Float.parseFloat(Arith.divide(rs.getString("T_MONEY_1"), "1000", 4));  //價值除以1000至小數4位
				rowData[7] = Float.parseFloat(Arith.divide(rs.getString("T_MONEY_2"), "1000", 4));  //價值除以1000至小數4位
				data.add(rowData);
			}
		}catch (Exception e) {
	  		e.printStackTrace();
	  	}finally {
	  		try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
	  		db.closeAll();
	  	}
		return data;
	}
	
	//取得分別共有
	public ArrayList<Object []> getEFORM5_3_data(){
		ODatabase db = new ODatabase();
		ResultSet rs = null;
		ArrayList<Object []> data = new ArrayList<Object []>();
		try {
			String sql = " SELECT e0.CTY, NVL(e1.CNT, 0) CNT1, NVL(e2.CNT, 0) CNT2, "
					+ " NVL(e1.CNT1, 0) CNT1_1, NVL(e2.CNT1, 0) CNT1_2, "
					+ " NVL(e1.T_AREA, 0) T_AREA_1, NVL(e2.T_AREA, 0) T_AREA_2, "
					+ " NVL(e1.T_MONEY, 0) T_MONEY_1, NVL(e2.T_MONEY, 0) T_MONEY_2 "
					+ " FROM "
					+ " (SELECT CTY, ORDERBY FROM EFORM5_0) e0, "
					+ " (SELECT CTY, CNT, CNT1, T_AREA, T_MONEY FROM EFORM5_3 "
					+ " WHERE RPT_FLAG = '1' AND SEX = '1') e1, "
					+ " (SELECT CTY, CNT, CNT1, T_AREA, T_MONEY FROM EFORM5_3 "
					+ " WHERE RPT_FLAG = '1' AND SEX = '2') e2 "
					+ " WHERE 1=1 "
					+ " AND e0.CTY = e1.CTY(+) "
					+ " AND e0.CTY = e2.CTY(+) "
					+ " ORDER BY e0.ORDERBY ";
			rs = db.querySQL(sql);
			System.out.println("分別共有sql=" + sql);
			while(rs.next()){
				Object rowData[] = new Object[8];
				rowData[0] = Float.parseFloat(Common.get(rs.getString("CNT1")));
				rowData[1] = Float.parseFloat(Common.get(rs.getString("CNT2")));
				rowData[2] = Float.parseFloat(Arith.round(rs.getString("CNT1_1"), 4));  //小數4位
				rowData[3] = Float.parseFloat(Arith.round(rs.getString("CNT1_2"), 4));  //小數4位
				rowData[4] = Float.parseFloat(Arith.divide(rs.getString("T_AREA_1"), "10000", 4));  //面積除以10000至小數4位
				rowData[5] = Float.parseFloat(Arith.divide(rs.getString("T_AREA_2"), "10000", 4));  //面積除以10000至小數4位
				rowData[6] = Float.parseFloat(Arith.divide(rs.getString("T_MONEY_1"), "1000", 4));  //價值除以1000至小數4位
				rowData[7] = Float.parseFloat(Arith.divide(rs.getString("T_MONEY_2"), "1000", 4));  //價值除以1000至小數4位
				data.add(rowData);
			}
		}catch (Exception e) {
	  		e.printStackTrace();
	  	}finally {
	  		try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
	  		db.closeAll();
	  	}
		return data;
	}
	
	//取得公同共有
	public ArrayList<Object []> getEFORM5_4_data(){
		ODatabase db = new ODatabase();
		ResultSet rs = null;
		ArrayList<Object []> data = new ArrayList<Object []>();
		try {
			String sql = " SELECT e0.CTY, NVL(e1.CNT, 0) CNT1, NVL(e2.CNT, 0) CNT2, "
					+ " NVL(e1.CNT1, 0) CNT1_1, NVL(e2.CNT1, 0) CNT1_2, "
					+ " NVL(e1.T_AREA, 0) T_AREA_1, NVL(e2.T_AREA, 0) T_AREA_2, "
					+ " NVL(e1.T_MONEY, 0) T_MONEY_1, NVL(e2.T_MONEY, 0) T_MONEY_2 "
					+ " FROM "
					+ " (SELECT CTY, ORDERBY FROM EFORM5_0) e0, "
					+ " (SELECT CTY, CNT, CNT1, T_AREA, T_MONEY FROM EFORM5_4 "
					+ " WHERE RPT_FLAG = '1' AND SEX = '1') e1, "
					+ " (SELECT CTY, CNT, CNT1, T_AREA, T_MONEY FROM EFORM5_4 "
					+ " WHERE RPT_FLAG = '1' AND SEX = '2') e2 "
					+ " WHERE 1=1 "
					+ " AND e0.CTY = e1.CTY(+) "
					+ " AND e0.CTY = e2.CTY(+) "
					+ " ORDER BY e0.ORDERBY ";
			rs = db.querySQL(sql);
			System.out.println("公同共有sql=" + sql);
			while(rs.next()){
				Object rowData[] = new Object[5];
				rowData[0] = Float.parseFloat(Common.get(rs.getString("CNT1")));
				rowData[1] = Float.parseFloat(Common.get(rs.getString("CNT2")));
				rowData[2] = Float.parseFloat(Arith.round(rs.getString("CNT1_1"), 4))
						+ Float.parseFloat(Arith.round(rs.getString("CNT1_2"), 4));  //小數4位
				rowData[3] = Float.parseFloat(Arith.divide(rs.getString("T_AREA_1"), "10000", 4))
						+ Float.parseFloat(Arith.divide(rs.getString("T_AREA_2"), "10000", 4));  //面積除以10000至小數4位
				rowData[4] = Float.parseFloat(Arith.divide(rs.getString("T_MONEY_1"), "1000", 4))
						+ Float.parseFloat(Arith.divide(rs.getString("T_MONEY_2"), "1000", 4));  //價值除以1000至小數4位
				data.add(rowData);
                System.out.println("result:" + rowData[0] + ", " + rowData[1]
                                   + ", " + rowData[2] + ", " + rowData[3]
                                   + ", " + rowData[4]);
			}
		}catch (Exception e) {
	  		e.printStackTrace();
	  	}finally {
	  		try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
	  		db.closeAll();
	  	}
		return data;
	}
	

	//參數:templateURL樣本路徑, workareaURL產製路徑, reportName報表名稱
	public String doProcess(String templateURL, String workareaURL, String reportName) {
		String radm = Common.formatFrontZero(Integer.toString((int) (Math.random() * 100000 + 1)), 5);
		String templateExcel = templateURL + "\\" + reportName + ".xls";
		
		String vid = Common.getVMID();//亂數產生一個資料夾
		String workareaExcelfileName = reportName + "_"
						+ Datetime.getYYYMMDD() + "_"
						+ Datetime.getHHMMSS() + "_" + radm + ".xls";
		String workareaExcel = workareaURL + "\\"+vid+ "\\" + workareaExcelfileName;
		java.io.File outputFile=new File(workareaURL + "\\"+vid);
		outputFile.mkdirs();
		
		// 將 excel 從 template 複製到 WORKAREA
		copyFile(templateExcel, workareaExcel);

		// ******************************自行修改區***********************************
		int reportTotalRow = 139; // 報表表格總行數
		int reportTotalCell = 12; // 報表表格總欄數
		int titleRow = 4; // 表頭行數
		int rowValue = 4; // 行數,資料起始行
		int cellValue = 1; // 格數,資料起始欄
		int beginPasteRow = 38; // 新分頁的起始行數
		// *************************************************************************

		// 初始化
		init(workareaExcel, reportTotalRow, titleRow, rowValue, cellValue, beginPasteRow);

		// 儲存合併儲存格的值
		saveMergedRegions();
		try {
			// ************************************填入表頭資料***************************************
			/*
			fillCellValue("表頭報表名稱", 3, 0);
			fillCellValue(report_title, 4, 3);
			fillCellValue("總　計", 12, 0);
			fillCellValue(Integer.parseInt(month_c[1])+"月", 17, 0);
			fillCellValue(Integer.parseInt(month_c[2])+"月", 22, 0);
			fillCellValue(Integer.parseInt(month_c[3])+"月", 27, 0);
			*/
			// *************************************************************************************
			
			//報表日期
		    getLastProcess();
		    fillCellValue(Common.formatYYYMMDD(getLastProcessDate(), 3), 1, 0);
            fillCellValue(Common.formatYYYMMDD(getLastProcessDate(), 3), 36, 0);
            fillCellValue(Common.formatYYYMMDD(getLastProcessDate(), 3), 69, 0);
            fillCellValue(Common.formatYYYMMDD(getLastProcessDate(), 3), 104, 0);
			
			int row_S, col_S;  //資料起始列、欄
			
			//取得總歸戶土地權屬人資料
			ArrayList<Object []> EFORM5_1_data = getEFORM5_1_data();
			row_S = 4;
			col_S = 1;
			for(int i = 0; i < EFORM5_1_data.size(); i++){
				for(int j = 0; j < EFORM5_1_data.get(i).length; j++){
					fillCellValue(EFORM5_1_data.get(i)[j], row_S+i, col_S+j);
				}
			}
			//自動計算公式(總歸戶)
			for(int i = 5; i < 29; i++){
				for(int j = 3; j < 12; j++){
					autoFormula(i, j);
				}
			}
			
			
			
			//取得單一所有權人
			ArrayList<Object []> EFORM5_2_data = getEFORM5_2_data();
			row_S = 39;
			col_S = 1;
			for(int i = 0; i < EFORM5_2_data.size(); i++){
				for(int j = 0; j < EFORM5_2_data.get(i).length; j++){
					fillCellValue(EFORM5_2_data.get(i)[j], row_S+i, col_S+j);
				}
			}
			//自動計算公式(單一所有權人)
			for(int j = 3; j < 9; j++){ autoFormula(46, j); }  //台灣省
			for(int j = 3; j < 9; j++){ autoFormula(61, j); }  //福建省
			
			
			
			//取得分別共有
			ArrayList<Object []> EFORM5_3_data = getEFORM5_3_data();
			row_S = 72;
			col_S = 1;
			for(int i = 0; i < EFORM5_3_data.size(); i++){
				for(int j = 0; j < EFORM5_3_data.get(i).length; j++){
					fillCellValue(EFORM5_3_data.get(i)[j], row_S+i, col_S+j);
				}
			}
			//自動計算公式(分別共有)
			for(int j = 3; j < 9; j++){ autoFormula(79, j); }  //台灣省
			for(int j = 3; j < 9; j++){ autoFormula(94, j); }  //福建省
			
			
			
			//取得公同共有(有合併儲存格因此必須控制j)
			ArrayList<Object []> EFORM5_4_data = getEFORM5_4_data();
			row_S = 107;
			col_S = 1;
			for(int i = 0; i < EFORM5_4_data.size(); i++){
				int j = 0;
				fillCellValue(EFORM5_4_data.get(i)[0], row_S+i, col_S+j);
				j += 1;
				fillCellValue(EFORM5_4_data.get(i)[1], row_S+i, col_S+j);
				j += 1;
				fillCellValue(EFORM5_4_data.get(i)[2], row_S+i, col_S+j);
				j += 2;
				fillCellValue(EFORM5_4_data.get(i)[3], row_S+i, col_S+j);
				j += 2;
				fillCellValue(EFORM5_4_data.get(i)[4], row_S+i, col_S+j);
			}
			//自動計算公式(公同共有)
			autoFormula(114, 3);  //台灣省
			autoFormula(114, 5);  //台灣省
			autoFormula(114, 7);  //台灣省
			autoFormula(129, 3);  //福建省
			autoFormula(129, 5);  //福建省
			autoFormula(129, 7);  //福建省
			
			
			
			//自動計算公式(單一所有權人第一列)
			for(int i = 3; i < 9; i++){ autoFormula(39, i); }
			//自動計算公式(分別共有第一列)
			for(int i = 3; i < 9; i++){ autoFormula(72, i); }
			//自動計算公式(公同共有第一列)
			autoFormula(107, 3);
			autoFormula(107, 5);
			autoFormula(107, 7);
			//自動計算公式(總歸戶第一列)
			for(int i = 3; i < 12; i++){ autoFormula(4, i); }
			
			
			
			FileOutputStream fout = new FileOutputStream(workareaExcel);
			getFHSSFWorkbook().write(fout);
			fout.flush();
			fout.close();
			
			setState("doProcessSuccess");
			workareaExcel=vid+ ":;:" + workareaExcelfileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workareaExcel;
	}
}