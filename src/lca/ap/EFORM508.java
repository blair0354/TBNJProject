/*
 *<br>程式目的：原住民地區行政區設定作業
 *<br>程式代號：
 *<br>撰寫日期： 
 *<br>程式作者：Sya
 *<br>--------------------------------------------------------
 *<br>修改作者　　修改日期　　　修改目的
 *<br>--------------------------------------------------------
 */

package lca.ap;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
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
import util.Datetime;
import util.ODatabase;
import util.POI_EXCEL;


public class EFORM508 extends POI_EXCEL {
	
	private String[] outputTable2=new String[16];	//畫面勾選回傳值
	private String sd;
	private String ed;
	private String t0;
	private String t4;
	private String msg;
	
	private HSSFCellStyle titleStyle = null;		// 黑色, 置中
	private HSSFCellStyle conditionStyle = null;	//黑色, 置左
    private HSSFCellStyle style1 = null;			// 上下左右細線, 黑色, 置中
    
    private int widthDatum = 256;//寬度基準
    private String br = "\n";	//斷行符號
	
	public String[] getOutputTable2() {return outputTable2;}
	public void setOutputTable2(String[] outputTable2) {this.outputTable2 = outputTable2;}
	
	
	public String getSd() {return checkGet(sd);}
	public void setSd(String sd) {this.sd = checkGet(sd);}
	public String getEd() {return checkGet(ed);}
	public void setEd(String ed) {this.ed = checkGet(ed);}
	
	public String getT0() {return checkGet(t0);}
	public void setT0(String t0) {this.t0 = checkGet(t0);}
	public String getT4() {return checkGet(t4);}
	public void setT4(String t4) {this.t4 = checkGet(t4);}
	
	public String getMsg() {return checkGet(msg);}
	public void setMsg(String msg) {this.msg = checkGet(msg);}
	
	
	public void dateTimeModel(){
		String ed = util.Datetime.getYYYMMDD();
		String sd = Integer.toString((Integer.valueOf(ed)-1));
		setSd(sd);
		setEd(ed);
	}
	
	//查詢
	public ArrayList<String[]> getResultModel(){
		ArrayList<String[]> reStr = new ArrayList<String[]>();
		ArrayList objList=new ArrayList();
		ODatabase db = new ODatabase();
		Hashtable lazy = new Hashtable();
		ResultSet rs = null;
		
		lazy.put("01","產製中");
		lazy.put("02","產製完成");
		lazy.put("03","產製失敗");
		lazy.put("04","產製完成已下載");
		lazy.put("05","無符合資料");
		
		try {
			System.out.println("SQL1 : " + sqlDoSelect(sd, ed));
			rs = db.querySQL(sqlDoSelect(sd, ed));
			while(rs.next()){
				String[] tmp = new String[7];
				
				tmp[0] = rs.getString(1);
				if("01".equals(Common.get(rs.getString(9))) || "03".equals(Common.get(rs.getString(9))) || "05".equals(Common.get(rs.getString(9))) ){
					tmp[0] = "";
				}
				
				tmp[1] = Common.isoToBig5(rs.getString(2));
				
				if(rs.getString(3) == null || rs.getString(4) == null){
					tmp[2] = "　";
				}else{
					tmp[2] = Common.formatYYYMMDD(rs.getString(3), 4) + " " + Common.formatHHMMSS(rs.getString(4));
				}
				
				
				if(rs.getString(5) == null || rs.getString(6) == null){
					tmp[3] = "　";
				}else{
					tmp[3] = Common.formatYYYMMDD(rs.getString(5), 4) + " " + Common.formatHHMMSS(rs.getString(6));
				}
				
				tmp[4] = (String) lazy.get(rs.getString(9));
				
				if(rs.getString(7) == null || rs.getString(8) == null){
					tmp[5] = "　";
				}else{
					tmp[5] = Common.formatYYYMMDD(rs.getString(7), 4) + " " + Common.formatHHMMSS(rs.getString(8));
				}
				
				tmp[6] = rs.getString(9);
			reStr.add(tmp);
			objList.add(tmp);
			}
		}catch (Exception e) {
			e.printStackTrace();
			setErrorMsg("Error!:"+ e.toString().replaceAll("\"", "").replaceAll("'", ""));
	  	}finally {
	  		try{
	  			if (rs!=null){
	  				rs.close(); 
	  				rs=null;
	  			}
	  		}catch (Exception e){
	  			e.printStackTrace(); 
	  		}
	  		db.closeAll();
	  	}
		return objList;
	}
	
	public String sqlDoSelect(String sd, String ed) {
		String sql = "select AMKID,useID,useDate,useTime,susDate,susTime,dlDate,dlTime,AMFLAG"
				+ " from ABCNT01M where 1=1"
				+ " and USEDATE >='" + sd + "'"
				+ " and USEDATE <='" + ed + "'";
		return sql;
	}

		public String SelectSus(){
			String sus = "";
			ODatabase db = new ODatabase();
			ResultSet rs = null;
			try {
				String sql = "select * from ABCNT01M where AMKID= " + Common.sqlChar(getT0());
				rs = db.querySQL(sql);
				if(rs.next())
					sus = Common.formatYYYMMDD(rs.getString(3), 4) + " " + Common.formatHHMMSS(rs.getString(4));
			}catch (Exception e) {
				e.printStackTrace();
				setErrorMsg("Error!:"+ e.toString().replaceAll("\"", "").replaceAll("'", ""));
		  	}finally {
		  		try{
		  			if (rs!=null){rs.close(); rs=null;}
		  		}catch(Exception e){
		  			e.printStackTrace(); 
		  		}
		  		db.closeAll();
		  	}
			return sus;
		}
		
		public String SelectDl(){
			String dl = "";
			ODatabase db = new ODatabase();
			ResultSet rs = null;
			try {
				String sql = "select * from ABCNT01M where AMKID= " + Common.sqlChar(getT0());
				rs = db.querySQL(sql);
				if(rs.next())
					dl = Common.formatYYYMMDD(rs.getString(8), 4) + " " + Common.formatHHMMSS(rs.getString(9));
				System.out.println("DL " + dl);
			}catch (Exception e) {
				e.printStackTrace();
				setErrorMsg("Error!:"+ e.toString().replaceAll("\"", "").replaceAll("'", ""));
		  	}finally {
		  		try{
		  			if (rs!=null){rs.close(); rs=null;}
		  		}catch(Exception e){
		  			e.printStackTrace(); 
		  		}
		  		db.closeAll();
		  	}
			return dl;
		}
	
		public String sqlMapPOI() {
			String str = "";
			for (String tTable : outputTable2) {
				if(tTable.equals("ADCNT01")){
					str += " ADCNT01,";
				}else if(tTable.equals("ADCNT02")){
					str += " ADCNT02,";
				}else if(tTable.equals("ADCNT03")){
					str += " ADCNT03,";
				}else if(tTable.equals("ADCNT04")){
					str += " ADCNT04,";
				}else if(tTable.equals("ADCNT11")){
					str += " ADCNT11,";
				}else if(tTable.equals("ADCNT12")){
					str += " ADCNT12,";
				}else if(tTable.equals("ADCNT13")){
					str += " ADCNT13,";
				}else if(tTable.equals("ADCNT14")){
					str += " ADCNT14,";
				}else if(tTable.equals("ADCNT15")){
					str += " ADCNT15,";
				}else if(tTable.equals("ADCNT16")){
					str += " ADCNT16,";
				}else if(tTable.equals("ADCNT21")){
					str += " ADCNT21,";
				}else if(tTable.equals("ADCNT22")){
					str += " ADCNT22,";
				}else if(tTable.equals("ADCNT23")){
					str += " ADCNT23,";
				}else if(tTable.equals("ADCNT24")){
					str += " ADCNT24,";
				}else if(tTable.equals("ADCNT25")){
					str += " ADCNT25,";
				}else if(tTable.equals("ADCNT26")){
					str += " ADCNT26,";
				}
			}
			str = str.substring(0,str.length()-1);
			String sql = "select AD45C, AD46C," + str + " from ABCNT01D where AMKID= " + Common.sqlChar(getT0());
			return sql;
		}
		
		
		/**
	     * <br> 
	  	 * <br>目的：列印EXCEL
	  	 * <br>參數：(1)request (2)response
	  	 * <br>傳回：
	     * */
	    public void printExcel(HttpServletRequest request, HttpServletResponse response) {
			try {
//	            if(getT4().equals("")||getT4().equals("01")||getT4().equals("03")){
//						setMsg("狀態不允許產製報表");
//						System.out.println(getT4() + "狀態不允許產製報表");
//				}else if(getT4().equals("02")||getT4().equals("04")){
				 String dLdate = util.Datetime.getYYYMMDD();
 				String dLtime = util.Datetime.getHHMMSS();
 				getDLSave(dLdate,dLtime);
 				
	                Map map = getData();
	                if (!"".equals(getErrorMsg()) || map == null) {
	                    return;
	                } else {
	                    //繪製EXCEL
	                    HSSFWorkbook wb = new HSSFWorkbook();	//創建EXCEL檔
	                    initStyle(wb);
	                    
	                    //活頁簿定位
	                    //int sheetNo = 0;
	                    
	                   
	                    //列印統計表
	                    printDetail(wb, map);
	                    //printDetail(wb, map, sheetNo++);

	                    //開啟EXCEL
	                    OutputStream os = null;
	                    try{
	                        response.setHeader("Content-Disposition",
	                                "attachment;filename="
	                                + "eform508_" + Datetime.getYYYMMDD() + "_" + Datetime.getHHMMSS() + ".xls");
	                        response.setContentType("application/vnd.ms-excel");
	                        os = response.getOutputStream();
	                        wb.write(os);
	                        //os.flush();
	                    }
	                    catch(FileNotFoundException e){
	                        e.printStackTrace();
	                    }
	                    catch(java.io.IOException e){
	                        e.printStackTrace();
	                    }
	                    finally{
	                        if(os != null){ os.close(); }
	                    }
	                }		
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    
		//下載時先修改狀態為產製完成已下載
		public void getDLSave(String dLdate, String dLTime){
			ODatabase db = new ODatabase();
			try{
		        String sql = "update ABCNT01M set AMFLAG='04' ,dlDate="+Common.sqlChar(dLdate)+",dlTime="+Common.sqlChar(dLTime)+" where AMKID="+Common.sqlChar(t0);
		        db.exeSQL(sql);
					
			}catch (Exception e) {
				e.printStackTrace();
				setErrorMsg("Error!:"+ e.toString().replaceAll("\"", "").replaceAll("'", ""));
		  	}finally {
		  		db.closeAll();
		  	}	
		}
			
		
		/**
	     * <br>
	  	 * <br>目的：取得報表所需資料
	  	 * <br>參數：無
	  	 * <br>傳回：Map
	     * */
	    public Map getData() {
	    	Map<String, Object> map = new HashMap();
	    	ODatabase db = new ODatabase();
			try {

				map.put("TITLE", "原住民族地區土地概況統計表" );
				
	            map.put("DATE", "產製日期：" + SelectSus() + br + "下載日期：" + SelectDl());
	            //map.put("DL", "下載日期：" + SelectDl());
				
				db.querySQL(sqlMapPOI());
				
				ArrayList<Map> arr = this.getDataArr(sqlMapPOI());
				
				map.put("DATA", arr);
				
				
			} catch (Exception e) {
				e.printStackTrace();
				setErrorMsg("Error!:" + e.toString().replaceAll("\"", "").replaceAll("'", ""));
			} finally {
				db.closeAll();
			}
	    	return map;
	    }

	    public ArrayList getDataArr(String sql) {
			ODatabase db = new ODatabase();
			ArrayList<Map> arr = new ArrayList();
			if (sql != null && !"".equals(sql)) {
				ResultSet rs = null;
				try {
					
	                rs = db.querySQL(sql);
					while (rs.next()){
						//將SELECT的資料逐一丟入
						ResultSetMetaData rsmd = rs.getMetaData();
						LinkedHashMap<String, String> map = new LinkedHashMap();
						for (int i = 1 ; i < rsmd.getColumnCount() + 1 ; i++){
							map.put(Common.get(rsmd.getColumnName(i)), Common.get(rs.getString(i)));
						}
						arr.add(map);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
						db.closeAll();
				}
			}
			return arr;
	    }
	    
	    /** 
	     * <br>
	  	 * <br>目的：設定POI欄位格式
	  	 * <br>參數：無
	  	 * <br>傳回：無
	     * */
	    private void initStyle(HSSFWorkbook wb) {
	        
	    	HSSFFont fontTitle = wb.createFont();
	    	fontTitle.setFontHeightInPoints((short) 20);
	    	fontTitle.setFontName("標楷體");
	    	
	        titleStyle = wb.createCellStyle();
	        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        titleStyle.setBottomBorderColor(HSSFColor.BLACK.index);
	        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        titleStyle.setLeftBorderColor(HSSFColor.BLACK.index);
	        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        titleStyle.setRightBorderColor(HSSFColor.BLACK.index);
	        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        titleStyle.setTopBorderColor(HSSFColor.BLACK.index);
	        titleStyle.setFont(fontTitle);
	        titleStyle.setWrapText(true);
	        
	        HSSFFont font = wb.createFont();
	    	font.setFontHeightInPoints((short) 10);
	    	font.setFontName("標楷體");
	        
	        conditionStyle = wb.createCellStyle();
	        conditionStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	        conditionStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        conditionStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        conditionStyle.setBottomBorderColor(HSSFColor.BLACK.index);
	        conditionStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        conditionStyle.setLeftBorderColor(HSSFColor.BLACK.index);
	        conditionStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        conditionStyle.setRightBorderColor(HSSFColor.BLACK.index);
	        conditionStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        conditionStyle.setTopBorderColor(HSSFColor.BLACK.index);
	        conditionStyle.setFont(font);
	        conditionStyle.setWrapText(true);

	        style1 = wb.createCellStyle();
	        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        style1.setBottomBorderColor(HSSFColor.BLACK.index);
	        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        style1.setLeftBorderColor(HSSFColor.BLACK.index);
	        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        style1.setRightBorderColor(HSSFColor.BLACK.index);
	        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        style1.setTopBorderColor(HSSFColor.BLACK.index);
	        style1.setFont(font);
	        style1.setWrapText(true);
	    }
	    
	    /**
	     * <br>
	  	 * <br>目的：列印明細表
	  	 * <br>參數：(1)HSSFWorkbook (2)資料存放MAP (3)所在Sheet的位置
	  	 * <br>傳回：無
	     * */
	    public void printDetail(HSSFWorkbook wb, Map map) {

	    	HSSFSheet sheet = wb.createSheet();//創建活頁簿
	    	sheet.setZoom(120, 100);//縮放比例，前分子，後分母
	    	HSSFPrintSetup ps = sheet.getPrintSetup();//列印設定
	    	ps.setLandscape(true);//列印方向=>true:橫向
	    	//wb.setSheetName(sheetNo, getKindC(getKind()) + "案件滿意度調查統計表");//活頁簿名稱
	    	
	    	String[] strTitle = new String[(getOutputTable2().length)+2];
            String[] strToInt = new String[(getOutputTable2().length)+2];
            int ii = 2;
            int cnt = 2;
            
            strTitle[0] = "縣市,";
            strToInt[0] = "8,";
            
            strTitle[1] = "鄉鎮市區,";
            strToInt[1] = "10,";
            
            for (String tTable : outputTable2) {
            	cnt = ii;
            	if(tTable.equals("ADCNT01")){
					strTitle[ii] = "標示部"+br+"面積,";
                    strToInt[ii] = "12,";
				}else if(tTable.equals("ADCNT02")){
                    strTitle[ii] = "土地筆數,";
                    strToInt[ii] = "12,";
				}else if(tTable.equals("ADCNT03")){
                    strTitle[ii] = "原住民"+br+"所有權人數,";
                    strToInt[ii] = "12,";
				}else if(tTable.equals("ADCNT04")){
                    strTitle[ii] = "非原住民"+br+"所有權人數,";
                    strToInt[ii] = "12,";
				}else if(tTable.equals("ADCNT11")){
                    strTitle[ii] = "原住民保留地"+br+"標示部面積,";
                    strToInt[ii] = "13,";
				}else if(tTable.equals("ADCNT12")){
                    strTitle[ii] = "原住民保留地"+br+"土地筆數,";
                    strToInt[ii] = "13,";
				}else if(tTable.equals("ADCNT13")){
                    strTitle[ii] = "原住民保留地"+br+"原住民所有權人數,";
                    strToInt[ii] = "17,";
				}else if(tTable.equals("ADCNT14")){
                    strTitle[ii] = "原住民保留地"+br+"非原住民所有權人數,";
                    strToInt[ii] = "19,";
				}else if(tTable.equals("ADCNT15")){
                    strTitle[ii] = "原住民保留地"+br+"原住民持有面積,";
                    strToInt[ii] = "16,";
				}else if(tTable.equals("ADCNT16")){
                    strTitle[ii] = "原住民保留地"+br+"非原住民持有面積,";
                    strToInt[ii] = "17,";
				}else if(tTable.equals("ADCNT21")){
                    strTitle[ii] = "非原住民保留地"+br+"標示部面積,";
                    strToInt[ii] = "15,";
				}else if(tTable.equals("ADCNT22")){
                    strTitle[ii] = "非原住民保留地"+br+"土地筆數,";
                    strToInt[ii] = "15,";
				}else if(tTable.equals("ADCNT23")){
                    strTitle[ii] = "非原住民保留地"+br+"原住民所有權人數,";
                    strToInt[ii] = "17,";
				}else if(tTable.equals("ADCNT24")){
                    strTitle[ii] = "非原住民保留地"+br+"非原住民所有權人數,";
                    strToInt[ii] = "19,";
				}else if(tTable.equals("ADCNT25")){
                    strTitle[ii] = "非原住民保留地"+br+"原住民持有面積,";
                    strToInt[ii] = "15,";
				}else if(tTable.equals("ADCNT26")){
                    strTitle[ii] = "非原住民保留地"+br+"非原住民持有面積,";
                    strToInt[ii] = "17,";
				}
				//strTitle[i]=strTitle[i].substring(0,(strTitle[i].length()-1));
				ii++;
			}
            
            if(cnt != ii){
            	strTitle[cnt] = strTitle[cnt].substring(0,(strTitle[cnt].length()-1));
            	strToInt[cnt] = strToInt[cnt].substring(0,(strToInt[cnt].length()-1));
            }
            
	    	//表頭
            String[] firstRow = strTitle;	//小於六 需要 塞空白格
            
	    	//表頭寬度
	    	String[] colWidth = strToInt;	//小於六 需要 補足6格
	    	
	    	int beginRow = 0;//起使列

	    	//***第一行***
	    	HSSFRow r = sheet.createRow(beginRow);
	    	//抬頭
	    	if(firstRow.length < 5){
	    		crossSheet(sheet, beginRow, beginRow, 0, 5);
		    	writeDetail(sheet, r, gett(map.get("TITLE")), beginRow, 0, titleStyle);
			    	for(int x=1;x<6;x++){
			    		writeDetail(sheet, r,"", beginRow, x, titleStyle);
			    	}
	    	}else{
		    	crossSheet(sheet, beginRow, beginRow, 0, firstRow.length-1);
		    	writeDetail(sheet, r, gett(map.get("TITLE")), beginRow, 0, titleStyle);
			    	for(int x=1;x<firstRow.length;x++){
			    		writeDetail(sheet, r,"", beginRow, x, titleStyle);
			    	}
	    	}
	    	r.setHeightInPoints((short) 40);
	    	beginRow++;

	    	//***第二行***
	    	r = sheet.createRow(beginRow);
	    	//日期
	    	if(firstRow.length < 5){
	    		crossSheet(sheet, beginRow, beginRow, 0, 5);
		    	writeDetail(sheet, r, gett(map.get("DATE")), beginRow, 0, conditionStyle);
			    	for(int x=1;x<firstRow.length;x++){
			    		writeDetail(sheet, r,"", beginRow, x, conditionStyle);
			    	}
	    	}else{
		    	crossSheet(sheet, beginRow, beginRow, 0, firstRow.length-1);
		    	writeDetail(sheet, r, gett(map.get("DATE")), beginRow, 0, conditionStyle);
		    	for(int x=1;x<firstRow.length;x++){
		    		writeDetail(sheet, r,"", beginRow, x, conditionStyle);
		    	}
	    	}
	    	r.setHeightInPoints((short) 40);
	    	beginRow++;
	    	
	    	//***第三行***
	    	//表頭
	    	r = sheet.createRow(beginRow);
	
	    	makeFirstRow(sheet, r, firstRow, colWidth, style1);	
	    	if(firstRow.length == 3){	//只勾一個
		    	writeDetail(sheet, r, "", beginRow, firstRow.length, style1);
		    	writeDetail(sheet, r, "", beginRow, firstRow.length+1, style1);
		    	writeDetail(sheet, r, "", beginRow, firstRow.length+2, style1);
	    	}else if(firstRow.length == 4){	//只勾兩個
		    	writeDetail(sheet, r, "", beginRow, firstRow.length, style1);
		    	writeDetail(sheet, r, "", beginRow, firstRow.length+1, style1);
	    	}
	    	
	    	beginRow++;
	    	
	    	
	    	int a02 = 0; int a03 = 0; int a04 = 0;
			int a12 = 0; int a13 = 0; int a14 = 0;
			int a22 = 0; int a23 = 0; int a24 = 0;
			
			double a01; 
			double a11; double a15; double a16;
			double a21; double a25; double a26;
			
			int a02_c = 0; int a03_c = 0; int a04_c = 0;
			int a12_c = 0; int a13_c = 0; int a14_c = 0;
			int a22_c = 0; int a23_c = 0; int a24_c = 0;
			
			double a01_c = 0; 
			double a11_c = 0; double a15_c = 0; double a16_c = 0;
			double a21_c = 0; double a25_c = 0; double a26_c = 0;
			
	    	//**第四行***
	    	//資料處理
	    	if (map.containsKey("DATA")) {
	    		ArrayList<Map> arr = (ArrayList)map.get("DATA");
	    		if (arr != null && arr.size() > 0) {
	    			for (int i = 0 ; i < arr.size() ; i++) {
	    				Map dataMap = arr.get(i);
	    				
	    				r = sheet.createRow(beginRow);
	    				
	    				String AD45C = Common.isoToBig5(gett(dataMap.get("AD45C")));
	    				String AD46C = Common.isoToBig5(gett(dataMap.get("AD46C")));
	    				writeDetail(sheet, r, AD45C, beginRow, 0, style1);
	    				writeDetail(sheet, r, AD46C, beginRow, 1, style1);
	    				
	    				int col = 2;
	    				
	    				for (String tTable : outputTable2) {
	    					if(tTable.equals("ADCNT01")){
	    						a01 = Double.parseDouble(gett(dataMap.get("ADCNT01")));
	    						a01_c += a01;
	    						writeDetail(sheet, r, Common.areaFormat(String.valueOf(a01)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT02")){
	    						a02 = Integer.valueOf(gett(dataMap.get("ADCNT02")));
	    						a02_c += a02;
	    						writeDetail(sheet, r, Common.valueFormat(String.valueOf(a02)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT03")){
	    						a03 = Integer.valueOf(gett(dataMap.get("ADCNT03")));
	    						a03_c += a03;
	    						writeDetail(sheet, r, Common.valueFormat(String.valueOf(a03)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT04")){
	    						a04 = Integer.valueOf(gett(dataMap.get("ADCNT04")));
	    						a04_c += a04;
	    						writeDetail(sheet, r, Common.valueFormat(String.valueOf(a04)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT11")){
	    						a11 = Double.parseDouble(gett(dataMap.get("ADCNT11")));
	    						a11_c += a11;
	    						writeDetail(sheet, r, Common.areaFormat(String.valueOf(a11)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT12")){
	    						a12 = Integer.valueOf(gett(dataMap.get("ADCNT12")));
	    						a12_c += a12;
	    						writeDetail(sheet, r, Common.valueFormat(String.valueOf(a12)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT13")){
	    						a13 = Integer.valueOf(gett(dataMap.get("ADCNT13")));
	    						a13_c += a13;
	    						writeDetail(sheet, r, Common.valueFormat(String.valueOf(a13)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT14")){
	    						a14 = Integer.valueOf(gett(dataMap.get("ADCNT14")));
	    						a14_c += a14;
	    						writeDetail(sheet, r, Common.valueFormat(String.valueOf(a14)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT15")){
	    						a15 = Double.parseDouble(gett(dataMap.get("ADCNT15")));
	    						a15_c += a15;
	    						writeDetail(sheet, r, Common.areaFormat(String.valueOf(a15)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT16")){
	    						a16 = Double.parseDouble(gett(dataMap.get("ADCNT16")));
	    						a16_c += a16;
	    						writeDetail(sheet, r, Common.areaFormat(String.valueOf(a16)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT21")){
	    						a21 = Double.parseDouble(gett(dataMap.get("ADCNT21")));
	    						a21_c += a21;
	    						writeDetail(sheet, r, Common.areaFormat(String.valueOf(a21)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT22")){
	    						a22 = Integer.valueOf(gett(dataMap.get("ADCNT22")));
	    						a22_c += a22;
	    						writeDetail(sheet, r, Common.valueFormat(String.valueOf(a22)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT23")){
	    						a23 = Integer.valueOf(gett(dataMap.get("ADCNT23")));
	    						a23_c += a23;
	    						writeDetail(sheet, r, Common.valueFormat(String.valueOf(a23)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT24")){
	    						a24 = Integer.valueOf(gett(dataMap.get("ADCNT24")));
	    						a24_c += a24;
	    						writeDetail(sheet, r, Common.valueFormat(String.valueOf(a24)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT25")){
	    						a25 = Double.parseDouble(gett(dataMap.get("ADCNT25")));
	    						a25_c += a25;
	    						writeDetail(sheet, r, Common.areaFormat(String.valueOf(a25)), beginRow, col, style1);
	    					}else if(tTable.equals("ADCNT26")){
	    						a26 = Double.parseDouble(gett(dataMap.get("ADCNT26")));
	    						a26_c += a26;
	    						writeDetail(sheet, r, Common.areaFormat(String.valueOf(a26)), beginRow, col, style1);
	    					}
	    					col++;
	    				}
	    				if(firstRow.length == 3){	//只勾一個
	    			    	writeDetail(sheet, r, "", beginRow, firstRow.length, style1);
	    			    	writeDetail(sheet, r, "", beginRow, firstRow.length+1, style1);
	    			    	writeDetail(sheet, r, "", beginRow, firstRow.length+2, style1);
	    		    	}else if(firstRow.length == 4){	//只勾兩個
	    			    	writeDetail(sheet, r, "", beginRow, firstRow.length, style1);
	    			    	writeDetail(sheet, r, "", beginRow, firstRow.length+1, style1);
	    		    	}
	    				beginRow++;
	    			}
	    		} else {
	    			r = sheet.createRow(beginRow);
	    			crossSheet(sheet, beginRow, beginRow, 0, firstRow.length-1);
	    	    	writeDetail(sheet, r, "《查無符合條件資料》", beginRow, 0, titleStyle);
	    	    	beginRow++;
	    		}
	    	}
	    	
	    	//***第五行***
	    	r = sheet.createRow(beginRow);
	    	//合計
	    	crossSheet(sheet, beginRow, beginRow, 0, 1);
	    	writeDetail(sheet, r, "合計", beginRow, 0, style1);
	    	writeDetail(sheet, r, "", beginRow, 1, style1);
	    	
	    	int col = 2;
	    	
	    	for (String tTable : outputTable2) {
				if(tTable.equals("ADCNT01")){
					writeDetail(sheet, r, Common.areaFormat(String.valueOf(a01_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT02")){
					writeDetail(sheet, r, Common.valueFormat(String.valueOf(a02_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT03")){
					writeDetail(sheet, r, Common.valueFormat(String.valueOf(a03_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT04")){
					writeDetail(sheet, r, Common.valueFormat(String.valueOf(a04_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT11")){
					writeDetail(sheet, r, Common.areaFormat(String.valueOf(a11_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT12")){
					writeDetail(sheet, r, Common.valueFormat(String.valueOf(a12_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT13")){
					writeDetail(sheet, r, Common.valueFormat(String.valueOf(a13_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT14")){
					writeDetail(sheet, r, Common.valueFormat(String.valueOf(a14_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT15")){
					writeDetail(sheet, r, Common.areaFormat(String.valueOf(a15_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT16")){
					writeDetail(sheet, r, Common.areaFormat(String.valueOf(a16_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT21")){
					writeDetail(sheet, r, Common.areaFormat(String.valueOf(a21_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT22")){
					writeDetail(sheet, r, Common.valueFormat(String.valueOf(a22_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT23")){
					writeDetail(sheet, r, Common.valueFormat(String.valueOf(a23_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT24")){
					writeDetail(sheet, r, Common.valueFormat(String.valueOf(a24_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT25")){
					writeDetail(sheet, r, Common.areaFormat(String.valueOf(a25_c)), beginRow, col, style1);
				}else if(tTable.equals("ADCNT26")){
					writeDetail(sheet, r, Common.areaFormat(String.valueOf(a26_c)), beginRow, col, style1);
				}
				col++;
			}
	    	if(firstRow.length == 3){	//只勾一個
		    	writeDetail(sheet, r, "", beginRow, firstRow.length, style1);
		    	writeDetail(sheet, r, "", beginRow, firstRow.length+1, style1);
		    	writeDetail(sheet, r, "", beginRow, firstRow.length+2, style1);
	    	}else if(firstRow.length == 4){	//只勾兩個
		    	writeDetail(sheet, r, "", beginRow, firstRow.length, style1);
		    	writeDetail(sheet, r, "", beginRow, firstRow.length+1, style1);
	    	}
	    	beginRow++;
	    	

	    }
	    
	    /**
	     * <br> 
	  	 * <br>目的：設定報表跨欄
	  	 * <br>參數：(1)HSSFSheet (2)起使列 (3)迄止列 (4)起使欄  (5)迄止欄
	  	 * <br>傳回：
	     * */
	    public void crossSheet(HSSFSheet sheet, int strR, int endR, int strC, int endC) {
	    	if (sheet !=null) {
	    		sheet.addMergedRegion(new CellRangeAddress(strR, endR, strC, endC));
	    	}
	    }
	    
	    /**
	     * <br>
	  	 * <br>目的：POI產製報表內容-單一欄位、不套表
	  	 * <br>參數：(1)HSSFSheet (2)產製內容  (3)列 (4)欄
	  	 * <br>傳回：
	     * */
	    public void writeDetail(HSSFSheet sheet, HSSFRow r, String s, int row, int cell, HSSFCellStyle cs){
	    	HSSFCell c = null;
			c = r.createCell( cell );
//			c.setEncoding(HSSFCell.ENCODING_UTF_16);
			c.setCellValue(s);
			c.setCellStyle(cs);
	    }
	    
	    /**
	     * <br>
	  	 * <br>目的：POI產製首列
	  	 * <br>參數：(1)HSSFSheet (2)列名 (3)列寬度 (4)字型格式 (5)起始列
	  	 * <br>傳回：
	     * */
	    public void makeFirstRow(HSSFSheet sheet, HSSFRow r, String[] title, String[] colWidth, HSSFCellStyle cs){
	    	HSSFCell c = null;
	    	int x;
	    	for (int i = 0 ; i < title.length ; i++){
	    		if (colWidth.length-1 >= i){
	    			x = Integer.parseInt(colWidth[i].replaceAll(",",""));
	    			sheet.setColumnWidth(i, (short)(x * widthDatum));
	    		}
	    		c = r.createCell( i );
//	    		c.setEncoding(HSSFCell.ENCODING_UTF_16);
	    		c.setCellValue(Common.get(title[i]).replaceAll(",",""));
	    		c.setCellStyle(cs);
	    	}
	    	
	    }
	    
	    static public String gett(Object s){
			if(s==null) return "";
			else return s.toString().trim();		
		}
	    
}