package eform.eform4_1;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.util.CellRangeAddress;

import util.Common;
import util.Database;
import util.Datetime;
import util.ODatabase;

public class EFORM4_1 {
	private String unit;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	
	/**
	 * 取得 鄉鎮 下拉選單
	 * @param sql
	 * @param selectedOne
	 * @return
	 */
	public String getOption(String sql, String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
        ODatabase db = new ODatabase();
        try {
        	ResultSet rs = db.querySQL(sql);
            while (rs.next()) {
                String id = Common.isoToBig5(rs.getString(1));
                String name = Common.isoToBig5(rs.getString(2));
                
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
	
	/**
	 * 查詢資料
	 * @param txtcity_no
	 * @param txtunit
	 */
	public Map<String,List<Map<String,String>>> doQuery(String txtcity_no, String txtunit) throws Exception{
		//查詢的土地類型
		final String[] type = {
					"IS_CITY",	//都市
					"NOT_CITY",	//非都市
					"IS_FARM",	//農地
					"NOT_FARM"	//非農地
					};
		
	
		//單種土地類型查詢後的資料
		List<Map<String,String>> rsList;//放入 裝著資料的rsMap
		Map<String,String> rsMap;//放入 兩種查詢到的資料 TOT1 與 AA10
		
		//將查詢後的4種土地資料 依Key分別存放
		Map<String,List<Map<String,String>>> kideMap = new HashMap<String,List<Map<String,String>>>();
		
		
		//組出 所有查詢SQL
		//放入土地類型  都、非都、農、非農
		for(int j = 0; j < type.length; j ++) {
			rsList = new ArrayList<Map<String,String>>();
			
			//SQL
			List<String> isCity = getSelectString(getWhereString(type[j], txtcity_no, txtunit), txtcity_no);
			for(int i = 0; i < isCity.size(); i++) {
				//打印sql
				System.out.println(type[j] + " : " + (i + 1) + " >\n" + isCity.get(i) + "");
				
				ODatabase db = new ODatabase();
				ResultSet rs = null;
				
				try {
					
					rs = db.querySQL(isCity.get(i));
					
					
					while(rs.next()) {
						rsMap= new HashMap<String,String>();
						
						rsMap.put("TOT1", rs.getString("TOT1"));
						rsMap.put("AA10", rs.getString("AA10"));
						
						System.out.print(type[j] + ": ");
						System.out.print("TOT1 > " + rs.getString("TOT1") + "\t");
						System.out.print("AA10 > " + rs.getString("AA10") + "\n");
						System.out.println();
						
						rsList.add(rsMap);
						
					}
					
				}finally {
					db.closeAll();
				}
				
				
			}
			
		kideMap.put(type[j], rsList);
		}
		
		
		
		
		return kideMap;
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
			String LIDN,
			String LNAME,
			String QRY_OPER,
			String QRY_PURPOSE01,
			String QRY_PURPOSE02,
			String QRY_PURPOSE03,
			String QRY_PURPOSE03A) {
		
		String[] sql = {
				"insert into bs_log "
				+ "(QRY_DATE_START, QRY_TIME_START, USERID, UNITID, IP, CON, QRY, QRY_MSG, RCV_YR, RCV_WORD, RCV_NO, SNO, SNAME, SNO1, SNAME1, QRY_PURPOSE, LIDN, LNAME, QRY_OPER, QRY_PURPOSE01, QRY_PURPOSE02, QRY_PURPOSE03, QRY_PURPOSE03A) "
				+ "values "
				+ "("
				+ Common.sqlChar(QRY_DATE_START) +","
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
				+ Common.sqlChar(LIDN) + "," 
				+ Common.sqlChar(LNAME) + "," 
				+ Common.sqlChar(QRY_OPER) + "," 
				+ Common.sqlChar(QRY_PURPOSE01) + "," 
				+ Common.sqlChar(QRY_PURPOSE02) + "," 
				+ Common.sqlChar(QRY_PURPOSE03) + "," 
				+ Common.sqlChar(QRY_PURPOSE03A)
				+ ")"};
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
	
	/**
	 * 組合 各區間sql
	 * @param min
	 * @param max
	 * @return
	 */
	private static String getArea(String min, String max) {
		String strMin = " and (a.aa10*b.bb15_3/b.bb15_2) >= " + min +"00";
		String strMax = " and (a.aa10*b.bb15_3/b.bb15_2) < " + max + "00";
		
		String result = "";
		
		if(min != null) result += strMin;
		if(max != null) result += strMax;
		
		return result;
		
	}
	
	
	/**
	 * 組合 selectSql
	 * @param whereSql
	 * @param txtcity_no
	 * @return
	 */
	private static List<String> getSelectString(String whereSql,String txtcity_no) {
		String selectSql = "select count(*) tot1,sum(a.aa10*b.bb15_3/b.bb15_2) aa10 from L1?00H03.ralid a,L1?00H03.rblow b,L1?00H03.rlnid c " + whereSql +" ";
		selectSql = selectSql.replace("?", txtcity_no);
		
		String[][] area = {
			{null,"1"},
			{"1","3"},
			{"3","5"},
			{"5","10"},
			{"10","20"},
			{"20","35"},
			{"35","50"},
			{"50","70"},
			{"70","100"},
			{"100","150"},
			{"150","200"},
			{"200","300"},
			{"300","450"},
			{"450",null},
		};
		
		List<String> sql = new ArrayList<String>();
		
		for(int i = 0; i < area.length; i++) {
			sql.add(selectSql + getArea(area[i][0], area[i][1]));
		}
		
		return sql;
	}
	
	/**
	 * 組合 where 條件
	 * @param kind 
	 * @param txtcity_no
	 * @param txtunit
	 * @return
	 */
	private static String getWhereString(String kind,String txtcity_no, String txtunit) {
		String whereSql = 
				"where a.aa48=b.ba48 and a.aa49=b.ba49 " + 
				"#doChange" + 
				"and b.BB15_2<>0 " + 
				"and a.cty='" + txtcity_no + "' " + 
				"and a.aa46='" + txtunit + "'";
		
		if("IS_CITY".equals(kind)) {//都市用地
			
			whereSql = whereSql.replace("#doChange", "and b.bb09=c.lidn and c.lcde in ('3','4','5','6') and substr(a.aa11,1,1) <> 'A' ");
			
		}else if("NOT_CITY".equals(kind)) {//非都市用地
			
			whereSql = whereSql.replace("#doChange", "and b.bb09=c.lidn and c.lcde in ('3','4','5','6') and substr(a.aa11,1,1) = 'A' ");
			
		}else if("IS_FARM".equals(kind)) {
			
			whereSql = whereSql.replace("#doChange", "and b.bb09=c.lidn and c.lcde in ('3','4','5','6') and a.aa11 in ('AA','AB','BF') ");
			
		}else if("NOT_FARM".equals(kind)) {
			
			whereSql = whereSql.replace("#doChange", "and b.bb09=c.lidn and c.lcde in ('3','4','5','6') and a.aa11 not in ('AA','AB','BF') ");
			
		}
		
		return whereSql;
	}
	
	
	private String getCreateTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd a hh:mm:ss");
		
		return sdf.format(date);
	}
	
	public void doPrintExcel(Map<?, ?> data,HttpServletRequest request, HttpServletResponse response,String txtcity_name, String txtunit_name) throws Exception{
		System.out.println("產製 Excel Start...");
		
		//建立Excel
		HSSFWorkbook wb = new HSSFWorkbook();	// 新EXCEL
		HSSFSheet sheet = wb.createSheet();		// 新sheet
		HSSFRow row = null;						//列
		HSSFCell cell = null;					//欄
		
		//起始列
		short r = 0;
		//起始欄
		int c = 0;
		//設置欄位寬度
		for(int i = 0; i < 17; i++) {
			
			sheet.setColumnWidth(i, 2900);
		}
		
		
		HSSFPrintSetup ps = sheet.getPrintSetup();	// 列印設定
		sheet.setZoom(87, 100);
//		ps.setPaperSize(PrintSetup.A4_PAPERSIZE); //A4
		ps.setPaperSize((short) 8); //A3
		ps.setLandscape(true);	// 列印方向=>true 橫向 / false 直向
		
		//字型
		HSSFFont font = wb.createFont();
		//格式設定
		HSSFCellStyle cellStyle = wb.createCellStyle();
		
		//row 0
		font.setFontName("標楷體");//設定字體
		font.setFontHeight((short)(14*20));//設定字體大小
		cellStyle.setFont(font);//套用至欄位樣式
		cellStyle.setWrapText(true);//斷行
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平對齊
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直對齊

		
		//合併儲存格
		sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 16));
		row = sheet.createRow(r++);
		row.setHeightInPoints((short)25);//列 高度
		cell = row.createCell(c);
		cell.setCellValue(txtcity_name + txtunit_name + "公有土地歸戶分類統計表");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		//row 1
		font = wb.createFont();
		font.setFontName("標楷體");//設定字體
		font.setFontHeight((short)(10*20));//設定字體大小
		cellStyle =wb.createCellStyle();
		cellStyle.setFont(font);//套用至欄位樣式
		cellStyle.setWrapText(true);//斷行
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平對齊
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直對齊
		

		
		//合併儲存格
		sheet.addMergedRegion(new CellRangeAddress(r, r, 10, 11));
		sheet.addMergedRegion(new CellRangeAddress(r, r, 13, 16));
		row = sheet.createRow(r++);
		row.setHeightInPoints((short)16);//列 高度
		cell = row.createCell(10);
		cell.setCellValue("單位:戶/公畝");
		cell.setCellStyle(cellStyle);//套用至儲存格
		cell = row.createCell(13);
		cell.setCellValue("列印日期:" + getCreateTime());
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		
		//row 2
		font = wb.createFont();
		font.setFontName("標楷體");//設定字體
		font.setFontHeight((short)(10*20));//設定字體大小
		cellStyle =wb.createCellStyle();
		cellStyle.setFont(font);//套用至欄位樣式
		cellStyle.setWrapText(true);//斷行
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//水平對齊
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直對齊
		
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		//合併儲存格
		sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(r, r + 1, 2, 2));
		row = sheet.createRow(r++);
		row.setHeightInPoints((short)23);//列 高度
		//cell 0
		cell = row.createCell(0);
		cell.setCellValue("分組面積");
		cell.setCellStyle(cellStyle);//套用至儲存格
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		font = wb.createFont();
		font.setFontName("標楷體");//設定字體
		font.setFontHeight((short)(10*20));//設定字體大小
		cellStyle =wb.createCellStyle();
		cellStyle.setFont(font);//套用至欄位樣式
		cellStyle.setWrapText(true);//斷行
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平對齊
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直對齊
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		cell = row.createCell(2);
		cell.setCellValue("總計");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		c = 3;
		cell = row.createCell(c++);
		cell.setCellValue("1公畝");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		cell = row.createCell(c++);
		cell.setCellValue("1-3");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		cell = row.createCell(c++);
		cell.setCellValue("3-5");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		cell = row.createCell(c++);
		cell.setCellValue("5-10");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		cell = row.createCell(c++);
		cell.setCellValue("10-20");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		cell = row.createCell(c++);
		cell.setCellValue("20-35");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		cell = row.createCell(c++);
		cell.setCellValue("35-50");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		cell = row.createCell(c++);
		cell.setCellValue("50-70");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		cell = row.createCell(c++);
		cell.setCellValue("70-100");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		cell = row.createCell(c++);
		cell.setCellValue("100-150");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		cell = row.createCell(c++);
		cell.setCellValue("150-200");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		cell = row.createCell(c++);
		cell.setCellValue("200-300");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		cell = row.createCell(c++);
		cell.setCellValue("300-450");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		cell = row.createCell(c++);
		cell.setCellValue("450公畝");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		//row 3
		font = wb.createFont();
		font.setFontName("標楷體");//設定字體
		font.setFontHeight((short)(10*20));//設定字體大小
		cellStyle =wb.createCellStyle();
		cellStyle.setFont(font);//套用至欄位樣式
		cellStyle.setWrapText(true);//斷行
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平對齊
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直對齊
		
//		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		//合併儲存格
		sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 1));
		row = sheet.createRow(r++);
		row.setHeightInPoints((short)23);//列 高度
		//cell 0
		cell = row.createCell(0);
		cell.setCellValue("分類");
		cell.setCellStyle(cellStyle);//套用至儲存格
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);//套用至儲存格
		cell = row.createCell(2);//這邊是總計下半部
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		
		font = wb.createFont();
		font.setFontName("標楷體");//設定字體
		font.setFontHeight((short)(10*20));//設定字體大小
		cellStyle =wb.createCellStyle();
		cellStyle.setFont(font);//套用至欄位樣式
		cellStyle.setWrapText(true);//斷行
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平對齊
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直對齊
//		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		c = 3;
		cell = row.createCell(c);
		cell.setCellValue("以下");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		for(int i = 0; i <= 11; i++) {
			cell = row.createCell( ++c );
			cell.setCellValue("公畝");
			cell.setCellStyle(cellStyle);//套用至儲存格
		}
		
		cell = row.createCell( ++c );
		cell.setCellValue("以上");
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		//row 4~13 cell 0
		font = wb.createFont();
		font.setFontName("標楷體");//設定字體
		font.setFontHeight((short)(10*20));//設定字體大小
		cellStyle =wb.createCellStyle();
		cellStyle.setFont(font);//套用至欄位樣式
		cellStyle.setWrapText(true);//斷行
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平對齊
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直對齊
		
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		//合併儲存格
		c = 0;
		String[] kide = {"總計","都市土地","非都市土地","農地","非農地"};
		for(int i = 0; i < kide.length; i++,r+=2) {
			sheet.addMergedRegion(new CellRangeAddress(r, r+1, 0, 0));
			row = sheet.createRow(r);
			row.setHeightInPoints((short)23);//列 高度
			
			cell = row.createCell(c);
			cell.setCellValue(kide[i]);
			cell.setCellStyle(cellStyle);
			
			
			row = sheet.createRow(r+1);
			row.setHeightInPoints((short)23);//列 高度
			
			cell = row.createCell(c);
			cell.setCellStyle(cellStyle);
			
		}
		
		//row 4~13 cell 1
		font = wb.createFont();
		font.setFontName("標楷體");//設定字體
		font.setFontHeight((short)(10*20));//設定字體大小
		cellStyle =wb.createCellStyle();
		cellStyle.setFont(font);//套用至欄位樣式
		cellStyle.setWrapText(true);//斷行
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平對齊
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直對齊
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		c = 1;
		r = 4;
		for(int i = 0; i < 5; i++,r+=2) {
			row = sheet.getRow(r);		//在 上一步已經create 過了 這邊直接用get取得
			row.setHeightInPoints((short)23);//列 高度
			
			cell = row.createCell(c);
			cell.setCellValue("戶數");
			cell.setCellStyle(cellStyle);
			
			row = sheet.getRow(r+1);	//在 上一步已經create 過了 這邊直接用get取得
			row.setHeightInPoints((short)23);//列 高度
			
			cell = row.createCell(c);
			cell.setCellValue("面積");
			cell.setCellStyle(cellStyle);
		}
		
		//開始塞資料 不包括總計那些欄位
		font = wb.createFont();
		font.setFontName("標楷體");//設定字體
		font.setFontHeight((short)(10*20));//設定字體大小
		cellStyle =wb.createCellStyle();
		cellStyle.setFont(font);//套用至欄位樣式
		cellStyle.setWrapText(true);//斷行
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//水平對齊
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直對齊
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		r = 6;
		
		
		String[] rsKide = {"IS_CITY","NOT_CITY","IS_FARM","NOT_FARM"};		
		for(int i = 0; i <rsKide.length; i++,r+=2) {
			System.out.println(rsKide[i] + ":");
			
			c = 3;
			short  tmp = r;
			
			List<?> list = (List<?>) data.get(rsKide[i]);
			for(int j = 0; j < list.size(); j++,c++) {
				Map<?,?> rsMap = (Map<?,?>)list.get(j);
				int 	tot1 = Integer.parseInt((String)rsMap.get("TOT1"));
				double	aa10 = rsMap.get("AA10") == null?0:Double.parseDouble((String)rsMap.get("AA10")) / 100;
//				double	aa10 = Double.parseDouble((String)rsMap.get("AA10"));
				System.out.print(j + ": ");
				System.out.print(" TOT1:" + tot1);
				System.out.print(" AA10:" + aa10 + "\n");
				
				row = sheet.getRow(tmp);
				cell = row.createCell(c);
				cell.setCellValue(tot1);
				cell.setCellStyle(cellStyle);
				
				row = sheet.getRow(tmp + 1);
				cell = row.createCell(c);
				cell.setCellValue(aa10);
				cell.setCellStyle(cellStyle);
				
			}
			
		}
		
		//用函數計算需要加總的cell
		
		//行的總計
		for(int i = 2; i < 17 ; i++) {
			short n = 4;//row
			row = sheet.getRow(n);
			cell = row.createCell(i);
			String c_s = cell_to_ASCII(i);
			String statr_r = String.valueOf((n + 1 + 2));
			String end_r = String.valueOf((n + 1 + 2 + 2));
			cell.setCellFormula(c_s + statr_r + "+" + c_s + end_r);
			cell.setCellStyle(cellStyle);
			
			n = 5;
			row = sheet.getRow(n);
			cell = row.createCell(i);
			c_s = cell_to_ASCII(i);
			statr_r = String.valueOf((n + 1 + 2));
			end_r = String.valueOf((n + 1 + 2 + 2));
			cell.setCellFormula(c_s + statr_r + "+" + c_s + end_r);
			cell.setCellStyle(cellStyle);
		}
		
		//列的總計
		for(int i = 6 ; i <= 13; i++) {
			row = sheet.getRow(i);
			cell = row.createCell(2);
			
			String start = cell_to_ASCII(3);
			String end = cell_to_ASCII(16);
			cell.setCellFormula("SUM(" + start+(i+1) + ":" + end+(i+1) + ")");
			cell.setCellStyle(cellStyle);
		}
		
		
		//尾部
		
		row = sheet.createRow(14);
		row.setHeightInPoints((short)20);//列 高度
		for(int i = 0; i < 17; i++)
			cell = row.createCell(i);
		
		sheet.addMergedRegion(new CellRangeAddress(14, 14, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(14, 14, 2, 16));
		
		font = wb.createFont();
		font.setFontName("標楷體");//設定字體
		font.setFontHeight((short)(10*20));//設定字體大小
		cellStyle =wb.createCellStyle();
		cellStyle.setFont(font);//套用至欄位樣式
		cellStyle.setWrapText(true);//斷行
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平對齊
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直對齊
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		cell = row.getCell(0);
		cell.setCellValue("備註");
		cell.setCellStyle(cellStyle);
		cell = row.getCell(1);
		cell.setCellStyle(cellStyle);
		
		cell = row.getCell(2);
		cell.setCellValue("面積如恰為１公畝，則統計屬「１－３」之級距，以下均同。");
		cell.setCellStyle(cellStyle);
		
		font = wb.createFont();
		font.setFontName("標楷體");//設定字體
		font.setFontHeight((short)(10*20));//設定字體大小
		cellStyle =wb.createCellStyle();
		cellStyle.setFont(font);//套用至欄位樣式
		cellStyle.setWrapText(true);//斷行
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平對齊
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直對齊
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		for(int i =2; i < 17; i++) {
			cell = row.getCell(i);
			cell.setCellStyle(cellStyle);
		}
		
		//簽名
		sheet.addMergedRegion(new CellRangeAddress(15, 15, 9, 10));
		sheet.addMergedRegion(new CellRangeAddress(16, 16, 9, 10));
		
		font = wb.createFont();
		font.setFontName("標楷體");//設定字體
		font.setFontHeight((short)(10*20));//設定字體大小
		cellStyle =wb.createCellStyle();
		cellStyle.setFont(font);//套用至欄位樣式
		cellStyle.setWrapText(true);//斷行
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平對齊
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直對齊
		
		row = sheet.createRow(15);
		row.setHeightInPoints((short)18);//列 高度
		cell = row.createCell(0);
		cell.setCellValue("製表");
		cell.setCellStyle(cellStyle);
		
		cell = row.createCell(9);
		cell.setCellValue("主辦業務人員");
		cell.setCellStyle(cellStyle);
		
		cell = row.createCell(14);
		cell.setCellValue("機關長官");
		cell.setCellStyle(cellStyle);
		
		row = sheet.createRow(16);
		row.setHeightInPoints((short)20);//列 高度
		cell = row.createCell(9);
		cell.setCellValue("主辦主計人員");
		cell.setCellStyle(cellStyle);
		
		
		// 匯出EXCEL
				OutputStream os = null;
				try {
					//產生 Word/Excel的下載MessageBox
					//下載的檔名用setHeader
					
					response.setHeader("Content-Disposition", "attachment;filename=EFORM4_1_EXCEL_"+Datetime.getYYYMMDD()+"_"+Datetime.getHHMMSS()+".xls");
					//要產生 Word/Excel文件主要設定ContentType為 application/vnd.ms-excel  或 "application/msword"
					response.setContentType("application/vnd.ms-excel");
					os = response.getOutputStream();
					wb.write(os);//匯出的地方
					os.flush();//將緩衝區資料送出
				}  catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (os != null) {
						try {
							os.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				System.out.println("產製 Excel End...");	
	}
	
	/**
	 * 將cell number 轉換成對應英文
	 * @param cell
	 * @return
	 */
	private static String cell_to_ASCII(int cell) {
		
		char asc2 = (char)(cell + 65);
		
		return String.valueOf(asc2);
	}
	
	
public void updateBs_log(String QRY_DATE_END,String QRY_TIME_END,String QRY_MSG,String QRY_DATE_START,String QRY_TIME_START,String IP) {
		
		String[] sql = {
				"update bs_log set " + "\n"
					+ "QRY_DATE_END=" + Common.sqlChar(QRY_DATE_END) + "," + "\n"
					+ "QRY_TIME_END=" + Common.sqlChar(QRY_TIME_END) + "," + "\n"
					+ "QRY_MSG=" + Common.sqlChar(QRY_MSG)  + "\n"
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


