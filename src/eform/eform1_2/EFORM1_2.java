package eform.eform1_2;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Common;
import util.Database;
import util.ODatabase;

public class EFORM1_2 {
	
	private String unit;
	private List queryData;
	
	
	
	public List getQueryData() {
		return queryData;
	}


	public void setQueryData(List queryData) {
		this.queryData = queryData;
	}


	public String getUnit() {
		return Common.get(unit);
	}


	public void setUnit(String unit) {
		this.unit = Common.get(unit);
	}


	public String getOption(String sql, String selectedOne) {
        String rtnStr = "";
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
                rtnStr = rtnStr + ">" + name + "</option>\n";
            }
        } catch (Exception ex) {
            rtnStr = "<option value=''>查詢錯誤</option>";
            ex.printStackTrace();
        } finally {
 			db.closeAll();
        }        
        
        return rtnStr;
    }
	
	
	public List<Map<String,String>> doQuery(String txtcity_no, String txtunit, String txtqry_no, String txtqry_name) {
		String sql = 
				"select a.lidn as abc,a.*,b.kcnt \n"
			  + "  from rlnid a,rkeyn b \n"
			  + "    where 1=1 \n"
			  + "    and b.kcde_1(+)='09' \n"
			  + "    and b.kcde_2(+)=a.lcde \n"
			  + "    and b.cty(+)=a.cty \n"
			  + "    and b.unit(+)=a.unit \n";
		
		if(!"".equals(txtqry_no))
			sql += "    and a.lidn='" + txtqry_no +"' \n";
		if(!"".equals(txtqry_name))
			sql += "    and a.lnam='" + txtqry_name +"' \n";
		if(!"".equals(txtcity_no))
			sql += "    and a.cty='" + txtcity_no +"' \n";
		if(!"".equals(txtunit))
			sql += "    and a.unit='" + txtunit +"' \n";
		
		sql += "    order by a.lidn";
		
		
		
		System.out.println(sql);
		
		ODatabase db = new ODatabase();
		ResultSet rs = null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			rs = db.querySQL(sql);
			
			
			while(rs.next()) {
				
				
				Map<String,String> map = new HashMap<String, String>();
				map.put("lidn",Common.isoToBig5(rs.getString("lidn")));
				map.put("kcnt",Common.isoToBig5(rs.getString("kcnt")));
				map.put("lnam",Common.isoToBig5(rs.getString("lnam")));
				map.put("lbir_2",Common.isoToBig5(rs.getString("lbir_2")));
				map.put("ladr",Common.isoToBig5(rs.getString("ladr")));
				
				list.add(map);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		
		return list;
	}
	
	public String getSystemDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String result = "";
		int da = Integer.parseInt(sdf.format(date)) - 19110000;
		result += da;
		return result;
		
	}
	
	public String getSystemTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		
		return sdf.format(date);
		
	}
	
	public void insertBs_log(
			String QRY_DATE_START,
			String QRY_TIME_START,
			String USERID,
			String UNITID,
			String IP,
			String CON,
			String QRY,
			String QRY_MSG,
			String RCV_YR,
			String RCV_WORD,
			String RCV_NO,
			String SNO,
			String SNAME,
			String SNO1,
			String SNAME1,
			String QRY_PURPOSE,
			String QRY_OPER,
			String QRY_PURPOSE01,
			String QRY_PURPOSE02,
			String QRY_PURPOSE03,
			String QRY_PURPOSE03A) {
		
		String[] sql = {"insert into bs_log "
				+ "(QRY_DATE_START,QRY_TIME_START,USERID,UNITID,IP,CON,QRY,QRY_MSG,RCV_YR,RCV_WORD,RCV_NO,SNO,SNAME,SNO1,SNAME1,QRY_PURPOSE,QRY_OPER,QRY_PURPOSE01,QRY_PURPOSE02,QRY_PURPOSE03,QRY_PURPOSE03A) "
				+ "values "
				+ "(" 
				+ Common.sqlChar(QRY_DATE_START) + ","
				+ Common.sqlChar(QRY_TIME_START) + ","
				+ Common.sqlChar(USERID) + ","
				+ Common.sqlChar(UNITID) + ","
				+ Common.sqlChar(IP) + ","
				+ Common.sqlChar(CON) + ","
				+ Common.sqlChar(QRY) + ","
				+ Common.sqlChar(QRY_MSG) + ","
				+ Common.sqlChar(RCV_YR) + ","
				+ Common.sqlChar(RCV_WORD) + ","
				+ Common.sqlChar(RCV_NO) + ","
				+ Common.sqlChar(SNO) + ","
				+ Common.sqlChar(SNAME) + ","
				+ Common.sqlChar(SNO1) + ","
				+ Common.sqlChar(SNAME1) + ","
				+ Common.sqlChar(QRY_PURPOSE) + ","
				+ Common.sqlChar(QRY_OPER) + ","
				+ Common.sqlChar(QRY_PURPOSE01) + ","
				+ Common.sqlChar(QRY_PURPOSE02) + ","
				+ Common.sqlChar(QRY_PURPOSE03) + ","
				+ Common.sqlChar(QRY_PURPOSE03A)
				+")"};
		
		System.out.println(sql[0]);
		
		Database db = new Database();
		try {
			db.excuteSQL(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		
		
	}
	
	public void updateBs_log(String QRY_DATE_END,String QRY_TIME_END,String QRY_MSG,String LIDN,String LNAME,String QRY_DATE_START,String QRY_TIME_START,String IP) {
		
		String[] sql = {
				"update bs_log set " + "\n"
					+ "QRY_DATE_END=" + Common.sqlChar(QRY_DATE_END) + "," + "\n"
					+ "QRY_TIME_END=" + Common.sqlChar(QRY_TIME_END) + "," + "\n"
					+ "QRY_MSG=" + Common.sqlChar(QRY_MSG) + "," + "\n"
					+ "LIDN=" + Common.sqlChar(LIDN) + "," + "\n"
					+ "LNAME=" + Common.sqlChar(LNAME) + "\n"
						+ " where 1=1" + "\n"
						+ " and QRY_DATE_START=" + Common.sqlChar(QRY_DATE_START) + "\n"
						+ " and QRY_TIME_START=" + Common.sqlChar(QRY_TIME_START) + "\n"
						+ " and IP=" + Common.sqlChar(IP)
		};
		
		System.out.println(sql[0]);
		
		Database db = new Database();
		
		try {
			db.excuteSQL(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
	}
}
