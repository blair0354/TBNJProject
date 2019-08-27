package eform.eform2_5;

import java.io.FileOutputStream;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.util.Region;

import util.Common;
import util.Database;
import util.Datetime;
import util.ODatabase;

public class EFORM2_5 {
	
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
	
	private String getCreateTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd a hh:mm:ss");
		
		return sdf.format(date);
	}
	
	public void doPrintExcel(List list,HttpServletRequest request,HttpServletResponse response) {
		// 產製EXCEL
		HSSFWorkbook wb = new HSSFWorkbook();	// 新EXCEL
		HSSFSheet sheet = wb.createSheet();	// 新SHEET
		sheet.setPrintGridlines(true);	// 是否列印格線
		sheet.setColumnWidth((short)0,(short)3000); 
		sheet.setColumnWidth((short)1,(short)4000);
		sheet.setColumnWidth((short)2,(short)4500);
		sheet.setColumnWidth((short)3,(short)10500);
		HSSFPrintSetup ps = sheet.getPrintSetup();	// 列印設定
		ps.setPaperSize(PrintSetup.A4_PAPERSIZE);//A4
		ps.setLandscape(false);	// 列印方向=>false 直向
		
		HSSFCellStyle cs = wb.createCellStyle();	// 儲存格格式
		HSSFFont f = wb.createFont();	// 字型
		f.setFontName("新細明體");	// 字型設定
		f.setFontHeight((short) 280);	// 字型大小(200/20=10)
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗體
		f.setUnderline(Font.U_SINGLE);
		cs.setFont(f);
		cs.setWrapText(true);
		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);	// 儲存格對期
		cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFRow r = null;
		HSSFCell c = null;
		
		short row = 0;
		sheet.addMergedRegion(new Region(row, (short)0, row, (short)3));//合併儲存格
		r = sheet.createRow((short) row++);
		r.setHeightInPoints((short) 25);
		c = r.createCell((short) 0);
		c.setCellValue("權利人姓名統一編號清冊");
		c.setCellStyle(cs);
		
		
		
		
		HSSFCellStyle cs1 = wb.createCellStyle();
		HSSFFont f1 = wb.createFont();
		f1.setFontName("新細明體");	// 字型設定
		f1.setFontHeight((short) 240);	// 字型大小(200/20=10)
		cs1.setFont(f1);
		cs1.setWrapText(true);
		cs1.setAlignment(HSSFCellStyle.ALIGN_CENTER);	// 儲存格對期
		cs1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		sheet.addMergedRegion(new Region(row, (short)0, row, (short)3));//合併儲存格
		r = sheet.createRow(row++);
		r.setHeightInPoints((short)20);
		c = r.createCell((short) 0);
		c.setCellValue("製表日期:" + getCreateTime());
		c.setCellStyle(cs1);
		
		HSSFCellStyle cs2 = wb.createCellStyle();
		HSSFFont f2 = wb.createFont();
		cs2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cs2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cs2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cs2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		f2.setFontName("新細明體");	// 字型設定
		f2.setFontHeight((short) 240);	// 字型大小(200/20=10)
		cs2.setFont(f2);
		cs2.setWrapText(true);
		cs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);	// 儲存格對期
		cs2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cs2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cs2.setFillForegroundColor(HSSFColor.TURQUOISE.index);
		
		//標題
		short titleCell = 0;
		r = sheet.createRow(row++);
		c = r.createCell(titleCell++);
		c.setCellValue("統一編號");
		c.setCellStyle(cs2);
		
		c = r.createCell(titleCell++);
		c.setCellValue("姓名");
		c.setCellStyle(cs2);
		
		c = r.createCell(titleCell++);
		c.setCellValue("出生日期");
		c.setCellStyle(cs2);
		
		c = r.createCell(titleCell++);
		c.setCellValue("住址");
		c.setCellStyle(cs2);
		
		
		HSSFCellStyle cs3 = wb.createCellStyle();
		HSSFFont f3 = wb.createFont();
		cs3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cs3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cs3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cs3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		f3.setFontName("新細明體");	// 字型設定
		f3.setFontHeight((short) 240);	// 字型大小(200/20=10)
		cs3.setFont(f3);
		cs3.setWrapText(true);
		cs3.setAlignment(HSSFCellStyle.ALIGN_LEFT);	// 儲存格對期
		cs3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cs3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cs3.setFillForegroundColor(HSSFColor.WHITE.index);
		
		//detil
		for(int i = 0; i < list.size(); i++) {
			Map map = (Map)list.get(i);
			short detilCell = 0;
			
			r = sheet.createRow(row++);
			c = r.createCell(detilCell++);
			c.setCellValue(map.get("lidn").toString());
			c.setCellStyle(cs3);
			
			
			c = r.createCell(detilCell++);
			c.setCellValue(map.get("lnam").toString());
			c.setCellStyle(cs3);
			
			c = r.createCell(detilCell++);
			c.setCellValue(map.get("lbir_2").toString().length() == 7 ? map.get("lbir_2").toString().substring(0,3)+" 年"+map.get("lbir_2").toString().substring(3,5)+" 月" + map.get("lbir_2").toString().substring(5)+" 日":"");
			c.setCellStyle(cs3);
			
			c = r.createCell(detilCell++);
			c.setCellValue(map.get("ladr").toString());
			c.setCellStyle(cs3);
		}
		
		
		
		
		
		// 匯出EXCEL
		OutputStream os = null;
		try {
			//產生 Word/Excel的下載MessageBox
			//下載的檔名用setHeader
			
			response.setHeader("Content-Disposition", "attachment;filename=EFORM2_5_EXCEL_"+Datetime.getYYYMMDD()+"_"+Datetime.getHHMMSS()+".xls");
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
		
	}
	
	public void insertBs_log(
			List list,
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
		
		String[] sql = new String[list.size()];
		
		for(int i =0; i < list.size(); i++) {
			Map map= (Map)list.get(i);
			sql[i] = 
					"insert into bs_log "
							+ "(QRY_DATE_START,QRY_TIME_START,USERID,UNITID,IP,CON,QRY,QRY_MSG,RCV_YR,RCV_WORD,RCV_NO,SNO,SNAME,SNO1,SNAME1,QRY_PURPOSE,QRY_OPER,LIDN,LNAME,QRY_PURPOSE01,QRY_PURPOSE02,QRY_PURPOSE03,QRY_PURPOSE03A) "
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
							+ Common.sqlChar(map.get("lidn").toString()) + ","
							+ Common.sqlChar(map.get("lnam").toString()) + ","
							+ Common.sqlChar(QRY_PURPOSE01) + ","
							+ Common.sqlChar(QRY_PURPOSE02) + ","
							+ Common.sqlChar(QRY_PURPOSE03) + ","
							+ Common.sqlChar(QRY_PURPOSE03A)
							+")";
			
			System.out.println(sql[i]);
		}
		
		if(list.size() == 0) {
			sql = new String[1];
			sql[0] = "insert into bs_log "
					+ "(QRY_DATE_START,QRY_TIME_START,USERID,UNITID,IP,CON,QRY,QRY_MSG,RCV_YR,RCV_WORD,RCV_NO,SNO,SNAME,SNO1,SNAME1,QRY_PURPOSE,QRY_OPER,LIDN,LNAME,QRY_PURPOSE01,QRY_PURPOSE02,QRY_PURPOSE03,QRY_PURPOSE03A) "
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
					+ Common.sqlChar("") + ","
					+ Common.sqlChar("") + ","
					+ Common.sqlChar(QRY_PURPOSE01) + ","
					+ Common.sqlChar(QRY_PURPOSE02) + ","
					+ Common.sqlChar(QRY_PURPOSE03) + ","
					+ Common.sqlChar(QRY_PURPOSE03A)
					+")";
			System.out.println(sql[0]);
		}
		
		
		
		Database db = new Database();
		try {
			db.excuteSQL(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.closeAll();
		}
		
		
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
