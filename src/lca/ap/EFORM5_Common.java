package lca.ap;

import java.sql.ResultSet;

import util.Common;
import util.Database;
import util.Datetime;
import util.ODatabase;

public class EFORM5_Common {
	
	/**
	 * <br>
	 * <br>目的：將產製紀錄寫入Efrom5_log
	 * <br>參數：RPT_ID:產製報表ID, USER_ID:產製人員ID
	 * <br>傳回：此次新增的ID(流水號)
	*/
	static public String insertLogRecord(String RPT_ID, String USER_ID) {
		Database db = new Database();
		ResultSet rs = null;
		String id = null;
		try {
			String sel_sql = " SELECT IsNull(MAX(ID+1), 1) ID FROM EFORM5_LOG ";
			rs = db.querySQL(sel_sql);
			if(rs.next()){ id = rs.getString(1); }
			String sql[] = {" INSERT INTO EFORM5_LOG (ID, RPT_ID, USER_ID, PROCESS_STATE, PROCESS_DATE_S, PROCESS_TIME_S "
					+ " ) VALUES ( '" + id + "' "
					+ ", '" + RPT_ID + "' "
					+ ", '" + USER_ID + "' "
					+ ", '1' "
					+ ", '" + Datetime.getYYYMMDD() + "' "
					+ ", '" + Datetime.getHHMMSS() + "' "
					+ " ) "};
			db.excuteSQL(sql);
		}catch (Exception e) {
	  		e.printStackTrace();
	  	}finally {
	  		db.closeAll();
	  	}
		return id;
	}
	
	/**
	 * <br>
	 * <br>目的：更新Efrom5_log的產製狀態
	 * <br>參數：ID:流水號, PROCESS_STATE:產製狀態
	 * <br>		(0:產製完成, 1:產製TEST01中, 2:產製TEST01_RESULT中, 3:產製RBLOW_TEMP_RESULT中, 4:產製失敗)
	 * <br>傳回：無
	*/
	static public void updateLogState(String ID, String PROCESS_STATE) {
		Database db = new Database();
		try {
			String sql[] = {" UPDATE EFORM5_LOG SET PROCESS_STATE = '" + PROCESS_STATE + "' "
					+ ", PROCESS_DATE_E = '" + Datetime.getYYYMMDD() + "' "
					+ ", PROCESS_TIME_E = '" + Datetime.getHHMMSS() + "' "
					+ " WHERE ID = '" + ID + "' "};
			db.excuteSQL(sql);
		}catch (Exception e) {
	  		e.printStackTrace();
	  	}finally {
	  		db.closeAll();
	  	}
	}
	
	/**
	 * <br>
	 * <br>目的：更新Efrom5_log的產製狀態
	 * <br>參數：ID:流水號, PROCESS_STATE:產製狀態, PROCESS_STATE_NOTE:產製註記
	 * <br>		(0:產製完成, 1:產製TEST01中, 2:產製TEST01_RESULT中, 3:產製RBLOW_TEMP_RESULT中, 4:產製失敗)
	 * <br>傳回：無
	*/
	static public void updateLogStateNote(String ID, String PROCESS_STATE, String PROCESS_STATE_NOTE) {
		Database db = new Database();
		try {
			String sql[] = {" UPDATE EFORM5_LOG SET PROCESS_STATE = '" + PROCESS_STATE + "' "
					+ ", PROCESS_STATE_NOTE = '" + PROCESS_STATE_NOTE + "' "
					+ ", PROCESS_DATE_E = '" + Datetime.getYYYMMDD() + "' "
					+ ", PROCESS_TIME_E = '" + Datetime.getHHMMSS() + "' "
					+ " WHERE ID = '" + ID + "' "};
			db.excuteSQL(sql);
		}catch (Exception e) {
	  		e.printStackTrace();
	  	}finally {
	  		db.closeAll();
	  	}
	}
	
	/**
	 * <br>
	 * <br>目的：取得資料測試(第一筆資料的第一個欄位結果)
	 * <br>參數：sql:SQL
	 * <br>傳回：單欄查詢結果
	*/
	static public String selectData(String sql) {
		ODatabase db = new ODatabase();
		ResultSet rs = null;
		String rtn = "";
		try {
			rs = db.querySQL(sql);
			if(rs.next()){
				rtn = Common.get(rs.getString(1));
			}
		}catch (Exception e) {
	  		e.printStackTrace();
	  	}finally {
			try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
	  		db.closeAll();
	  	}
		return rtn;
	}
}
