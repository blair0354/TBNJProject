package eform.eform3_3;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import util.Common;
import util.Database;
import util.Datetime;

public class EFORM3_3 {
	private String unit;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	
	
	//取得 sql server 下拉選單
	public String getCityOption(String unit) {
		
		Database db = new Database();

		
		String rtnStr = "";
        if("00".equals(unit))
        	rtnStr ="<option value=''>全部</option>";
        
        
        String sql = 
        		"select kcde_2, kcde_2+'-'+kcnt as kcnt from rkeyn "
	        		+ "where  1 = 1 "
	        		+ "and kcde_1='45' "
	        		+ "and left(kcde_2,2)<>'/*' "
	        		+ "and left(kcde_2,2)<>'20' "
	        		+ "and left(kcde_2,2)<> '- ' ";
		if(!"00".equals(unit))
			sql += "and left(kcde_2,1) = '" + unit.substring(0,1) + "' ";
			
		sql += "order by krmk";
        
        
        try {
        	ResultSet rs = db.querySQL(sql);
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                
                rtnStr = rtnStr + "<option value='" + id + "' ";
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
	
	
	public String getOffice(String unit) {
		
		Database db = new Database();

		
		String rtnStr = "";
        if("0".equals(unit.substring(1)))
        	rtnStr ="<option value=''>全部</option>";
        
        
        String sql = 
        		"select left(krmk,2) unit,left(krmk,2)+'-'+kcnt as kcnt from rkeyn "
        		+ "where 1 = 1 "
        		+ "and kcde_1='55' "
        		+ "and left(kcde_2,2)='01' "
        		+ "and left(kcde_2,2)<>'/*' "
        		+ "and left(kcde_2,2)<>'- ' \n";
        if(!"".equals("txtcity_no"))
        	//sql += "and ";
        if(!"00".equals(unit) && "0".equals(unit.substring(1))) // unit = 縣市端 (A0,B0....) 
        	sql += "and left(krmk,1) = '" + unit.substring(0, 1) +"'\n";
        if(!"00".equals(unit) && !"0".equals(unit.substring(1)))// unit = 地所端 第二碼不為0	
	        sql += "and left(krmk,2) = '" + unit + "'\n";		
		sql += "order by krmk";
        
		
        System.out.println(sql);
        try {
        	ResultSet rs = db.querySQL(sql);
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                
                rtnStr = rtnStr + "<option value='" + id + "' ";
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
	
	/**
	 * 取得 鄉鎮 下拉選單
	 * @param sql
	 * @param selectedOne
	 * @return
	 */
	public String getOption(String sql) {
        
        String rtnStr = "<option value=''>全部</option>";
        
        Database db = new Database();
        try {
        	ResultSet rs = db.querySQL(sql);
            while (rs.next()) {
                String id = Common.isoToBig5(rs.getString(1));
                String name = rs.getString(2);
                
                rtnStr = rtnStr + "<option value='" + id + "' ";
                rtnStr = rtnStr + ">" + name + "</option>\n";
            }
            
            System.out.println(rtnStr);
            
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
	
	public String getToday() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String result = "";
		int da = Integer.parseInt(sdf.format(date)) - 19110000;
		result += da;
		result = result.substring(0,3) + "/" + result.substring(3,5) + "/" + result.substring(5);
		return result;
		
	}
	
	public String getSystemTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		return sdf.format(date);
		
	}
	
	public String getCreateTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd a hh:mm:ss");
		
		return sdf.format(date);
	}
	
	
	public List<Map<String,String>> doQuery(String txtqry_date_start, String txtqry_date_end,String txtqry_msg,String txtcity_no, String txtunit) throws Exception{
		
		String sql = 
				"select a.UNITID,a.USERID,a.IP,"
				+ "a.CON,a.QRY,a.QRY_MSG,"
				+ "a.QRY_DATE_START,a.QRY_TIME_START,a.QRY_DATE_END,"
				+ "a.QRY_TIME_END,"
				+ "(select top 1 s.user_name from etecuser s where s.user_id=a.USERID)as UserName,"
				+ "((select top 1 s.KCNT from rkeyn s where s.kcde_1='45' and s.KCDE_2=substring(a.UNITID,1,1))+(select top 1 s.KCNT from rkeyn s where s.kcde_1='55' and s.KRMK=a.UNITID)) as UnitName "
				+ "from bs_log a \n"
				+ "where 1=1 \n"
				+ "and a.qry_date_start between '" + txtqry_date_start.replace("/", "") +"' and '" + txtqry_date_end.replace("/", "") + "'";
		
		//查詢狀態不為全部才加入條件
		if(!"全部".equals(txtqry_msg)) 
			sql += "and a.qry_Msg = '" + txtqry_msg + "' \n";
		
		//查詢縣市不為全部才加入條件
		if(!"".equals(txtcity_no)) {
			if("B".equals(txtcity_no))
				sql += "and left(a.unitID,1) in ('L','B')\n";
			else if("D".equals(txtcity_no))
				sql += "and left(a.unitID,1) in ('R','D')\n";
			else if("E".equals(txtcity_no))
				sql += "and left(a.unitID,1) in ('E','S')\n";
			else
				sql += "and left(a.unitID,1) = '" + txtcity_no + "'\n";
		}
		
		//查詢事務所不為全部才加入條件
		if(!"".equals(txtunit)) {
			if("BD".equals(txtunit))
				sql += "and a.unitID in ('LA','BD')";
			else if("BE".equals(txtunit))
				sql += "and a.unitID in ('LB','BE')";
			else if("BF".equals(txtunit))
				sql += "and a.unitID in ('LC','BF')";
			else if("BH".equals(txtunit))
				sql += "and a.unitID in ('LF','BH')";
			else if("BG".equals(txtunit))
				sql += "and a.unitID in ('LE','BG')";
			else if("BI".equals(txtunit))
				sql += "and a.unitID in ('LG','BI')";
			else if("BJ".equals(txtunit))
				sql += "and a.unitID in ('LH','BJ')";
			else if("DD".equals(txtunit))
				sql += "and a.unitID in ('RA','DD')";
			else if("DE".equals(txtunit))
				sql += "and a.unitID in ('RB','DE')";
			else if("DF".equals(txtunit))
				sql += "and a.unitID in ('RC','DF')";
			else if("DG".equals(txtunit))
				sql += "and a.unitID in ('RD','DG')";
			else if("DH".equals(txtunit))
				sql += "and a.unitID in ('RE','DH')";
			else if("DI".equals(txtunit))
				sql += "and a.unitID in ('RF','DI')";
			else if("DJ".equals(txtunit))
				sql += "and a.unitID in ('RG','DJ')";
			else if("DK".equals(txtunit))
				sql += "and a.unitID in ('RH','DK')";
			else if("EF".equals(txtunit))
				sql += "and a.unitID in ('SA','EF')";
			else if("EG".equals(txtunit))
				sql += "and a.unitID in ('SB','EG')";
			else if("EH".equals(txtunit))
				sql += "and a.unitID in ('SC','EH')";
			else if("EI".equals(txtunit))
				sql += "and a.unitID in ('SD','EI')";
			else if("EJ".equals(txtunit))
				sql += "and a.unitID in ('SE','EJ')";
			else if("EK".equals(txtunit))
				sql += "and a.unitID in ('SF','EK')";
			else if("EL".equals(txtunit))
				sql += "and a.unitID in ('SG','EL')";
			else
				sql += "and a.unitID = '" + txtunit + "'";
		}
		
		System.out.println(sql);
		
		Database db = new Database();
		ResultSet rs = null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map = null;
		try {
			rs = db.querySQL(sql);
			ResultSetMetaData md = rs.getMetaData();
			int colCount = md.getColumnCount();
			
			while(rs.next()) {
				
				map = new HashMap<String,String>();
				for(int i = 1; i <= colCount; i++) {
					map.put(md.getColumnName(i) , rs.getString(i));
//					System.out.println(i + " > "+ md.getColumnName(i) + ":" + rs.getString(i));
				}
				list.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		
		return list;
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
			String LIDN,
			String LNAME
			) {
		
		String[] sql = {
				"insert into bs_log "
				+ "(QRY_DATE_START, QRY_TIME_START, USERID, UNITID, IP, CON, QRY, QRY_MSG"
				+ ", RCV_YR, RCV_WORD, RCV_NO, SNO, SNAME, SNO1, SNAME1, QRY_PURPOSE, LIDN, LNAME, QRY_OPER"
				+ ") "
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
				+ Common.sqlChar(QRY_OPER)
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
	
	
	public void doPrintExcel(List<?> list,HttpServletRequest request, HttpServletResponse response,String txtqry_date_start, String txtqry_date_end) throws Exception{
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
		int[] w = {6000,4000,4500,6000,10000,7000,4500}; 
		for(int i = 0; i < w.length; i++) {
			sheet.setColumnWidth(i, w[i]);
		}
		
		HSSFPrintSetup ps = sheet.getPrintSetup();	// 列印設定
		sheet.setZoom(100, 100);
//		ps.setPaperSize(PrintSetup.A4_PAPERSIZE); //A4
//		ps.setPaperSize((short) 8); //A3
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
		sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 6));
		row = sheet.createRow(r++);
		row.setHeightInPoints((short)20);//列 高度
		cell = row.createCell(c);
		cell.setCellValue("每日查詢紀錄清冊");
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
		sheet.addMergedRegion(new CellRangeAddress(r, r, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(r, r, 4, 5));
		row = sheet.createRow(r++);
		row.setHeightInPoints((short)16);//列 高度
		cell = row.createCell(0);
		cell.setCellValue("起訖查詢日期:" + txtqry_date_start + "~" + txtqry_date_end);
		cell.setCellStyle(cellStyle);//套用至儲存格
		cell = row.createCell(4);
		cell.setCellValue("製表日期:" + getCreateTime());
		cell.setCellStyle(cellStyle);//套用至儲存格
		
		
		//row 2
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
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//儲存格填滿
		cellStyle.setFillForegroundColor(HSSFColor.TURQUOISE.index);//儲存格顏色
		
		//合併儲存格
		row = sheet.createRow(r++);
		row.setHeightInPoints((short)16);//列 高度
		//cell 0~6
		String[] col = {"查詢單位","使用者","使用者IP","查詢資料","查詢起訖時間","查詢條件","查詢結果"};
		for(int i = 0; i < col.length; i++) {
			
			cell = row.createCell(i);
			cell.setCellValue(col[i]);
			cell.setCellStyle(cellStyle);//套用至儲存格
		}
		
		
		
		//detial
		
		font = wb.createFont();
		font.setFontName("標楷體");//設定字體
		font.setFontHeight((short)(10*20));//設定字體大小
		cellStyle =wb.createCellStyle();
		cellStyle.setFont(font);//套用至欄位樣式
		cellStyle.setWrapText(false);//斷行
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平對齊
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直對齊
		
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		int dataRow = list.size();
		for(int i = 0; i < dataRow; i++) {
			Map<String,String> map = (Map)list.get(i);
			row = sheet.createRow(r++);
			row.setHeightInPoints((short)16);//列 高度
			
			
			int dataCell = 0;
			cell = row.createCell(dataCell++);
			cell.setCellValue(map.get("UnitName"));
			cell.setCellStyle(cellStyle);//套用至儲存格

			cell = row.createCell(dataCell++);
			cell.setCellValue(map.get("UserName"));
			cell.setCellStyle(cellStyle);//套用至儲存格
			
			cell = row.createCell(dataCell++);
			cell.setCellValue(map.get("IP"));
			cell.setCellStyle(cellStyle);//套用至儲存格
			
			cell = row.createCell(dataCell++);
			cell.setCellValue(map.get("CON"));
			cell.setCellStyle(cellStyle);//套用至儲存格
			
			cell = row.createCell(dataCell++);
			String dataS = map.get("QRY_DATE_START");
			dataS = dataS.substring(0, 3) +"/"+ dataS.substring(3, 5) +"/"+ dataS.substring(5);
			String dataE = map.get("QRY_DATE_END");
			if(dataE == null || dataE.length() < 7)
				dataE = "//";
			else
				dataE = dataE.substring(0, 3) +"/"+ dataE.substring(3, 5) +"/"+ dataE	.substring(5);
			cell.setCellValue(dataS +" "+ map.get("QRY_TIME_START") + " ~ " + dataE + " " + Common.get(map.get("QRY_TIME_END")));
			cell.setCellStyle(cellStyle);//套用至儲存格
			
			//將統編2~6位替換成*
			String qry = map.get("QRY");
			String pattern = "^.*[統一編號=][*A-z\\d]{8,10}.*$";
//			System.out.println(qry + " " + qry.matches(pattern));
			if(qry.matches(pattern)) {
//				System.out.println(qry.indexOf("統一編號="));
				int replceIndex = qry.indexOf("統一編號=") + 6;
				StringBuffer sb = new StringBuffer(qry);
				sb = sb.replace(replceIndex, replceIndex + 5, "*****");
//				System.out.println(">> " + sb);
				qry = sb.toString();
			}
			cell = row.createCell(dataCell++);
			cell.setCellValue(qry);
			cell.setCellStyle(cellStyle);//套用至儲存格
			
			
			cell = row.createCell(dataCell++);
			cell.setCellValue(map.get("QRY_MSG"));
			cell.setCellStyle(cellStyle);//套用至儲存格
		}
		
		// 匯出EXCEL
				OutputStream os = null;
				try {
					//產生 Word/Excel的下載MessageBox
					//下載的檔名用setHeader
					
					response.setHeader("Content-Disposition", "attachment;filename=EFORM3_3_EXCEL_"+Datetime.getYYYMMDD()+"_"+Datetime.getHHMMSS()+".xls");
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


