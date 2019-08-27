package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.regex.Pattern;

import net.sf.jasperreports.engine.export.oasis.CellStyle;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.util.CellRangeAddress;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/*  
    sheet1.getPrintSetup().setLandscape(landscape); landscape=>(直false)橫向true
	sheet1.getPrintSetup().setScale((short)scale);  scale=>縮放比例 
	sheet1.getPrintSetup().setFitHeight((short)fitHeight); fitHeight=>頁高
	sheet1.getPrintSetup().setFitWidth(((short)fitWidth)); fitWidth=>頁寬
	sheet1.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); HSSFPrintSetup.paperSize=>頁面大小
    font.setFontName("標楷體");             字體    
	font.setFontHeightInPoints(fontSize);  字型size
	style.setFont(font);     font=>HSSFFont font
	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  align =>HSSFCellStyle.align
	HSSFHeader hd = getHSSFSheet().getHeader();
	hd.setCenter(HSSFHeader.font("標楷體", "") + 
			     HSSFHeader.fontSize((short) 14) + 
			     "台東縣" + getAddr(getQ_ask_area()) + getAddr(getQ_ask_addr3())+
		         getQ_ym().substring(0,3) + "年度低收入戶核定名冊 (" + getQ_ym().substring(0,3) +
		         "年" + getQ_ym().substring(3) + "月生效)");
 */

public class POI_EXCEL extends QueryBean{
	final double MARGIN_TTRIBUTE = 0.3937007874015748d;
	int report_row_total;
	int report_title_row_total;
	int row_value;
	int cell_value;
	int begin_paste_rowNum;
	int sheet_num ; 
	int page_count ; 
	int page_total;
	final int rowMax = 65535;
	final int cellMax = 255;
	
	public int getReport_row_total(){ return this.report_row_total; }
	public void setReport_row_total(int s){ this.report_row_total=s; }
	public int getReport_title_row_total(){ return this.report_title_row_total; }
	public void setReport_title_row_total(int s){ this.report_title_row_total=s; }
	public int getRow_value(){ return this.row_value; }
	public void setRow_value(int s){ this.row_value=s; }
	public int getCell_value(){ return this.cell_value; }
	public void setCell_value(int s){ this.cell_value=s; }
	public int getBegin_paste_rowNum(){ return this.begin_paste_rowNum; }
	public void setBegin_paste_rowNum(int s){ this.begin_paste_rowNum=s; }
	public int getSheet_num(){ return this.sheet_num; }
	public void setSheet_num(int s){ this.sheet_num=s; }
	public int getPage_count(){ return this.page_count; }
	public void setPage_count(int s){ this.page_count=s; }
	public int getPage_total(){ return this.page_total; }
	public void setPage_total(int s){ this.page_total=s; }
	
	HSSFWorkbook wb;
	HSSFSheet sheet1;
	public void setHSSFWorkbook(HSSFWorkbook wb){this.wb = wb;}
	public HSSFWorkbook getFHSSFWorkbook(){return this.wb;}
	public void setHSSFSheet(HSSFSheet sheet){this.sheet1 = sheet;}
	public HSSFSheet getHSSFSheet(){return this.sheet1;}
	
	boolean sheetFlag;
	public void setSheetFlag(boolean wb){this.sheetFlag = sheetFlag;}
	public boolean getSheetFlag(){return this.sheetFlag;}
	
	public void init(String workareaXlsURL,int reportTotalRow, int titleRow, int rowValue, int cellValue, int beginPasteRow){
		try {
			POIFSFileSystem pfs = new POIFSFileSystem(new FileInputStream(workareaXlsURL));
			this.wb = new HSSFWorkbook(pfs);
			this.sheet1 = this.wb.getSheetAt(0);
			
			setReport_row_total(reportTotalRow);
			setReport_title_row_total(titleRow);
			setRow_value(rowValue);
			setCell_value(cellValue);
			setBegin_paste_rowNum(beginPasteRow);
			setSheet_num(1);
			setPage_count(1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	ArrayList mergedRegions = new ArrayList();
	public ArrayList getMergedRegions(){return this.mergedRegions;}
	public void setMergedRegions(ArrayList obj){this.mergedRegions = obj;}
	public void saveMergedRegions(){
		for(int i=0 ; i<sheet1.getNumMergedRegions() ; i++){
			int[] ranges = new int[4];
			if(sheet1.getMergedRegion(i)!=null){
				ranges[0] = sheet1.getMergedRegion(i).getFirstRow();
				ranges[1] = sheet1.getMergedRegion(i).getLastRow();
				ranges[2] = sheet1.getMergedRegion(i).getFirstColumn();
				ranges[3] = sheet1.getMergedRegion(i).getLastColumn();
				this.mergedRegions.add(ranges);
			}
		}
	}
	
	/**
	*<br>目的: 設定excel上下左右邊界
	*<br>參數: top=>上 ; bottom=>下 ; left=>左 ; right=>右
	*/
	public void setMargins(double top, double bottom, double left, double right){
		this.sheet1.setMargin(HSSFSheet.TopMargin, top * MARGIN_TTRIBUTE);      
		this.sheet1.setMargin(HSSFSheet.BottomMargin, bottom * MARGIN_TTRIBUTE);
		this.sheet1.setMargin(HSSFSheet.LeftMargin, left * MARGIN_TTRIBUTE);
		this.sheet1.setMargin(HSSFSheet.RightMargin, right * MARGIN_TTRIBUTE);
	}
	
	/**
	*<br>目的: 將資料填入Excel,以字串填入
	*<br>參數: cellValue=>要填入的內容 ; rowNum=>要在第幾行填入 ; cellNum => 要在第幾格欄位填入
	*/
	public void fillCellValue(Object cellValue, int rowNum, int cellNum){	
		try{
			HSSFRow row = sheet1.getRow(rowNum);
			HSSFCell cell = row.getCell(cellNum);
			
			if(row.getCell(cellNum) == null){
				row.createCell(cellNum);
				cell = row.getCell(cellNum);
			}

			
			if (HSSFCell.CELL_TYPE_FORMULA == cell.getCellType()) {
				cell.setCellFormula(cell.getCellFormula());
			}
			
			if (cellValue instanceof String) {
				cell.setCellValue((String)cellValue);
			} else if (cellValue instanceof Integer) {
				cell.setCellValue((Integer)cellValue);
			} else if (cellValue instanceof Boolean) {
				cell.setCellValue( (Boolean)cellValue);
			} else if (cellValue instanceof Double) {
				cell.setCellValue((Double)cellValue);
			} else if (cellValue instanceof Float) {
				cell.setCellValue((Float)cellValue);
			}
		}catch(NullPointerException e){
			HSSFRow row = sheet1.createRow(rowNum);
			HSSFCell cell = row.createCell(cellNum);
			if (HSSFCell.CELL_TYPE_FORMULA == cell.getCellType()) {
				cell.setCellFormula(cell.getCellFormula());
			}
			if (cellValue instanceof String) {
				cell.setCellValue((String)cellValue);
			} else if (cellValue instanceof Integer) {
				cell.setCellValue((Integer)cellValue);
			} else if (cellValue instanceof Boolean) {
				cell.setCellValue( (Boolean)cellValue);
			} else if (cellValue instanceof Double) {
				cell.setCellValue((Double)cellValue);
			} else if (cellValue instanceof Float) {
				cell.setCellValue((Float)cellValue);
			}
		}
	}
	
	/**
	*<br>目的: 將資料填入Excel,以字串填入
	*<br>參數: cellValue=>要填入的內容 ; rowNum=>要在第幾行填入 ; cellNum => 要在第幾格欄位填入
	*/
	public void fillCellValue(Object cellValue, int rowNum, int cellNum, HSSFCellStyle style){	
		try{
			HSSFRow row = sheet1.getRow(rowNum);
			HSSFCell cell = row.getCell(cellNum);
			
			if(row.getCell(cellNum) == null){
				row.createCell(cellNum);
				cell = row.getCell(cellNum);
			}

			
			if (HSSFCell.CELL_TYPE_FORMULA == cell.getCellType()) {
				cell.setCellFormula(cell.getCellFormula());
			}
			cell.setCellStyle(style);
			if (cellValue instanceof String) {
				cell.setCellValue((String)cellValue);
			} else if (cellValue instanceof Integer) {
				cell.setCellValue((Integer)cellValue);
			} else if (cellValue instanceof Boolean) {
				cell.setCellValue( (Boolean)cellValue);
			} else if (cellValue instanceof Double) {
				cell.setCellValue((Double)cellValue);
			} else if (cellValue instanceof Float) {
				cell.setCellValue((Float)cellValue);
			}
		}catch(NullPointerException e){
			HSSFRow row = sheet1.createRow(rowNum);
			HSSFCell cell = row.createCell(cellNum);
			if (HSSFCell.CELL_TYPE_FORMULA == cell.getCellType()) {
				cell.setCellFormula(cell.getCellFormula());
			}
			if (cellValue instanceof String) {
				cell.setCellValue((String)cellValue);
			} else if (cellValue instanceof Integer) {
				cell.setCellValue((Integer)cellValue);
			} else if (cellValue instanceof Boolean) {
				cell.setCellValue( (Boolean)cellValue);
			} else if (cellValue instanceof Double) {
				cell.setCellValue((Double)cellValue);
			} else if (cellValue instanceof Float) {
				cell.setCellValue((Float)cellValue);
			}
		}
	}

	/**
	*<br>目的: 將資料填入Excel,以字串陣列填入
	*<br>參數: cellValues=>要填入的內容 ; rowNum=>要在第幾行填入 ; cellNumBigin => 要填入的欄位起始位置
	*/
	public void fillCellValue(Object[] cellValues, int rowNum, int cellNumBigin){
		try{
			HSSFRow row = sheet1.getRow(rowNum);
			
			for(int i=cellNumBigin ; i<cellValues.length ; i++){
				HSSFCell cell = row.getCell(i);
				if (HSSFCell.CELL_TYPE_FORMULA == cell.getCellType()) {
					cell.setCellFormula(cell.getCellFormula());
				}
				if (cellValues[i] instanceof String) {
					cell.setCellValue((String)cellValues[i]);
				} else if (cellValues[i] instanceof Integer) {
					cell.setCellValue((Integer)cellValues[i]);
				} else if (cellValues[i] instanceof Boolean) {
					cell.setCellValue( (Boolean)cellValues[i]);
				} else if (cellValues[i] instanceof Double) {
					cell.setCellValue((Double)cellValues[i]);
				} else if (cellValues[i] instanceof Float) {
					cell.setCellValue((Float)cellValues[i]);
				}
			}
		}catch(NullPointerException e){
			HSSFRow row = sheet1.createRow(rowNum);
			for(int i=cellNumBigin ; i<cellValues.length ; i++){ 
				HSSFCell cell = row.createCell(i);
				if (HSSFCell.CELL_TYPE_FORMULA == cell.getCellType()) {
					cell.setCellFormula(cell.getCellFormula());
				}
				if (cellValues[i] instanceof String) {
					cell.setCellValue((String)cellValues[i]);
				} else if (cellValues[i] instanceof Integer) {
					cell.setCellValue((Integer)cellValues[i]);
				} else if (cellValues[i] instanceof Boolean) {
					cell.setCellValue( (Boolean)cellValues[i]);
				} else if (cellValues[i] instanceof Double) {
					cell.setCellValue((Double)cellValues[i]);
				} else if (cellValues[i] instanceof Float) {
					cell.setCellValue((Float)cellValues[i]);
				}  
			}
		}
	}
	
	/**
	*<br>目的: 清除單一欄位
	*<br>參數: rowNum=>行數 ; destination=>欄位數
	*/
	public void clearCell(int rowNum,int cellNum){
		try{
		    HSSFRow row = sheet1.getRow(rowNum);
		    HSSFCell cell = row.getCell(cellNum);
		    cell.setCellValue("　");
		}catch(NullPointerException e){}
	}
	
	/**
	*<br>目的: 清除多個欄位
	*<br>參數: rowFrom=>行數(始) ; rowTo=>行數(末) ; cellFrom=>欄位數(始) ; cellTo=>欄位數(末)
	*/
	public void clearCells(int rowFrom, int rowTo, int cellFrom, int cellTo){
		if(rowTo > rowMax){
			rowTo = rowMax;
		}
		if(cellTo > cellMax){
			cellTo = cellMax;
		}
		for(int i=rowFrom ; i<=rowTo ; i++){
//			try{
			HSSFRow row = sheet1.getRow(i);
			if(null == row){
				continue;
			}
			for(int j=cellFrom ; j<=cellTo ; j++){
				HSSFCell cell = row.getCell(j);
				if(null == cell){
					continue;
				}
				cell.setCellValue("");
			}
//		}catch(NullPointerException e){
//            continue;
//	    }
		}	
	}
	
	/**
	*<br>目的: 創造新分頁
	*<br>參數: rowTotal=>要創造的分頁總行數 ; cellTotal=>要創造的分頁總欄數 ; beginPasteRow=>新分頁開始行數)
	*/
	public void createPage(int rowTotal,int cellTotal,int beginPasteRow){
		
		//合併儲存格
		CellRangeAddress cra;
        for(int m=0 ; m<getMergedRegions().size() ; m++){
        	int[] ranges = (int[])getMergedRegions().get(m);
        	cra = new CellRangeAddress(ranges[0]+beginPasteRow-1, ranges[1]+beginPasteRow-1, ranges[2], ranges[3]);
      		sheet1.addMergedRegion(cra);
      	}

		for(int k=0 ; k<rowTotal ; k++){
			HSSFRow oldRow = sheet1.getRow(k);
            HSSFRow newRow = sheet1.createRow(beginPasteRow-1);
	        newRow.setHeight(oldRow.getHeight());
	        
			for(int j=0 ; j<cellTotal ; j++){			
			    try{   	
			    	HSSFCell oldCell = oldRow.getCell(j);
  		    	    HSSFCell newCell = newRow.createCell(j);
  		    	    
  		    	    newCell.setCellStyle(oldCell.getCellStyle());
  		    	    
			    	if(oldCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			            newCell.setCellValue(oldCell.getStringCellValue());
			        }else if(oldCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			            newCell.setCellValue(oldCell.getNumericCellValue());
			        } 			    
			    	
			    }catch(NullPointerException e){
                    continue;
			    }
			}
	        beginPasteRow++;
	    }
	}
	
	/**
	*<br>目的: 創造新分頁
	*<br>參數: copyBeginRow=>要複製的起始行 ; copyEndRow=>要複製的結束行 ;
	*          copyBeginCell=>要複製的起始欄 ; copyEndCell=>要複製的結束欄 ;
	*          pasteRow=>要貼上的行 ; pasteCell=>要貼上的欄 ;
	
	*          cellRangeAddress=>合併儲存格,例:int[][] ranges = {{0,0,1,1},{2,5,4,4}}或是null;
	*          ranges[0][0]:合併起始行,ranges[0][1]:合併結束行,ranges[0][2]:合併起始欄,ranges[0][3]:合併結束欄
	*          
	*          行與欄及合併儲存格的行欄的起始位置一律由0開始
	*/
	public void createTable(int copyBeginRow, int copyEndRow,int copyBeginCell,int copyEndCell,int pasteRow,int pasteCell,int[][] cellRangeAddress){
		
		//合併儲存格
		CellRangeAddress cra;
		if(cellRangeAddress != null){
			for(int m=0 ; m<cellRangeAddress.length ; m++){
				cra = new CellRangeAddress(cellRangeAddress[m][0],cellRangeAddress[m][1],cellRangeAddress[m][2],cellRangeAddress[m][3]);
				sheet1.addMergedRegion(cra);
			}
		}
		
		for(int k=0 ; k<copyEndRow-copyBeginRow+1 ; k++){
		    HSSFRow oldRow = sheet1.getRow(copyBeginRow+k);
            HSSFRow newRow = sheet1.createRow(pasteRow+k);
            newRow.setHeight(oldRow.getHeight());
        
		    for(int j=0 ; j<copyEndCell-copyBeginCell+1 ; j++){
		        try{   	
		    	    HSSFCell oldCell = oldRow.getCell(copyBeginCell+j);
		    	    HSSFCell newCell = newRow.createCell(pasteCell+j);
		    	    
		    	    newCell.setCellStyle(oldCell.getCellStyle());
		    	    
		    	    if(oldCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
		                newCell.setCellValue(oldCell.getStringCellValue());
		            }else if(oldCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
		                newCell.setCellValue(oldCell.getNumericCellValue());
		            } 			    
		    	    clearCell(pasteRow+k,pasteCell+j);
		        }catch(NullPointerException e){
                    continue;
		        }
		    }
		}
	}
	
	/**
	*<br>目的: 新增Sheet
	*<br>參數: sheetName=>新Sheet名稱 ; sheetNum=>sheet個數 ; totalRow=>要複製的Sheet總行數 ; totalCell=>要複製的Sheet總欄數
	*repeatingRC=>重複標題列範圍設定,0為起始,int[] repeatingRC = {beginCell,endCell,beginRow,endRow}
	*/
	public void addNewSheet(String sheetName, int sheetNum, int totalRow, int totalCell,int[] repeatingRC){
		sheet1 = this.wb.createSheet(sheetName + sheetNum);
		HSSFSheet oldSheet = this.wb.getSheet(sheetName + (sheetNum-1));
		
		
		this.wb.setRepeatingRowsAndColumns(sheetNum-1,repeatingRC[0],repeatingRC[1],repeatingRC[2],repeatingRC[3]);
		
		HSSFHeader oldhead = oldSheet.getHeader();
		HSSFHeader newhead = this.sheet1.getHeader();
		newhead.setCenter(oldhead.getCenter());
		newhead.setLeft(oldhead.getLeft());
		newhead.setRight(oldhead.getRight());
		
		for(int m=0 ; m<totalCell ; m++){
		    sheet1.setColumnWidth(m, oldSheet.getColumnWidth(m));
		    sheet1.getPrintSetup().setLandscape(oldSheet.getPrintSetup().getLandscape());   //設定方向
		    sheet1.getPrintSetup().setPaperSize(oldSheet.getPrintSetup().getPaperSize());   //設定頁面大小
		    sheet1.getPrintSetup().setFitHeight(oldSheet.getPrintSetup().getFitHeight());   //設定頁高
			sheet1.getPrintSetup().setFitWidth(oldSheet.getPrintSetup().getFitWidth());     //設定頁寬
			sheet1.getPrintSetup().setScale(oldSheet.getPrintSetup().getScale()); 
			sheet1.setMargin(HSSFSheet.TopMargin, oldSheet.getMargin(HSSFSheet.TopMargin));
			sheet1.setMargin(HSSFSheet.BottomMargin, oldSheet.getMargin(HSSFSheet.BottomMargin));
			sheet1.setMargin(HSSFSheet.LeftMargin, oldSheet.getMargin(HSSFSheet.LeftMargin));
			sheet1.setMargin(HSSFSheet.RightMargin, oldSheet.getMargin(HSSFSheet.RightMargin));
		}
		
		//合併儲存格
		CellRangeAddress cra;
        for(int m=0 ; m<getMergedRegions().size() ; m++){
        	int[] ranges = (int[])getMergedRegions().get(m);
        	cra = new CellRangeAddress(ranges[0], ranges[1], ranges[2], ranges[3]);
      		sheet1.addMergedRegion(cra);
      	}
        
        for(int k=0 ; k<totalRow ; k++){

        	HSSFRow oldRow = null;
        	HSSFRow newRow = null;
			try{
	        	oldRow = oldSheet.getRow(k);
	        	newRow = sheet1.createRow(k);
	        	newRow.setHeight(oldRow.getHeight());
			}catch(NullPointerException e){
			    continue;
			}
        	
        	for(int j=0 ; j<totalCell ; j++){
        		try{
        		    HSSFCell oldCell = oldRow.getCell(j);
        		    HSSFCell newCell = newRow.createCell(j);
        		    
        		    newCell.setCellStyle(oldCell.getCellStyle()); 
        		    
        		    if(oldCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			            newCell.setCellValue(oldCell.getStringCellValue());
			        }else if(oldCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			            newCell.setCellValue(oldCell.getNumericCellValue());
			        }	
        		}catch(NullPointerException e){
                    continue;
    		    }
        	}
        }
	}
	
	/**
	*<br>目的: 新增Sheet
	*<br>參數: sheetName=>新Sheet名稱 ; sheetNum=>sheet個數 ; totalRow=>要複製的Sheet總行數 ; totalCell=>要複製的Sheet總欄數
	*/
	public void addNewSheet(String sheetName, int sheetNum, int totalRow, int totalCell){
		sheet1 = this.wb.createSheet(sheetName + sheetNum);
		HSSFSheet oldSheet = this.wb.getSheet(sheetName + (sheetNum-1));

		HSSFHeader oldhead = oldSheet.getHeader();
		HSSFHeader newhead = this.sheet1.getHeader();
		newhead.setCenter(oldhead.getCenter());
		newhead.setLeft(oldhead.getLeft());
		newhead.setRight(oldhead.getRight());
		
		for(int m=0 ; m<totalCell ; m++){
		    sheet1.setColumnWidth(m, oldSheet.getColumnWidth(m));
		    sheet1.getPrintSetup().setLandscape(oldSheet.getPrintSetup().getLandscape());   //設定方向
		    sheet1.getPrintSetup().setPaperSize(oldSheet.getPrintSetup().getPaperSize());   //設定頁面大小
		    sheet1.getPrintSetup().setFitHeight(oldSheet.getPrintSetup().getFitHeight());   //設定頁高
			sheet1.getPrintSetup().setFitWidth(oldSheet.getPrintSetup().getFitWidth());     //設定頁寬
			sheet1.getPrintSetup().setScale(oldSheet.getPrintSetup().getScale()); 
			sheet1.setMargin(HSSFSheet.TopMargin, oldSheet.getMargin(HSSFSheet.TopMargin));
			sheet1.setMargin(HSSFSheet.BottomMargin, oldSheet.getMargin(HSSFSheet.BottomMargin));
			sheet1.setMargin(HSSFSheet.LeftMargin, oldSheet.getMargin(HSSFSheet.LeftMargin));
			sheet1.setMargin(HSSFSheet.RightMargin, oldSheet.getMargin(HSSFSheet.RightMargin));
		}
		
		//合併儲存格
		CellRangeAddress cra;
        for(int m=0 ; m<getMergedRegions().size() ; m++){
        	int[] ranges = (int[])getMergedRegions().get(m);
        	cra = new CellRangeAddress(ranges[0], ranges[1], ranges[2], ranges[3]);
      		sheet1.addMergedRegion(cra);
      	}
        
        for(int k=0 ; k<totalRow ; k++){

        	HSSFRow oldRow = null;
        	HSSFRow newRow = null;
			try{
	        	oldRow = oldSheet.getRow(k);
	        	newRow = sheet1.createRow(k);
	        	newRow.setHeight(oldRow.getHeight());
			}catch(NullPointerException e){
			    continue;
			}
        	
        	for(int j=0 ; j<totalCell ; j++){
        		try{
        		    HSSFCell oldCell = oldRow.getCell(j);
        		    HSSFCell newCell = newRow.createCell(j);
        		    
        		    newCell.setCellStyle(oldCell.getCellStyle()); 
        		    
        		    if(oldCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			            newCell.setCellValue(oldCell.getStringCellValue());
			        }else if(oldCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			            newCell.setCellValue(oldCell.getNumericCellValue());
			        }	
        		}catch(NullPointerException e){
                    continue;
    		    }
        	}
        }
	}
	
	/**
	*<br>目的: 檔案複製
	*<br>參數: source=>來源檔案 ; destination=>目的檔案
	*<br>     
	*copyFile("D:\\workspace5.0\\hcss\\report\\template\\AAABB010R.xls","D:\\workspace5.0\\hcss\\report\\WORKAREA\\AAABB010R.xls");
	*/
	public void copyFile(String source, String destination){
		try {
			FileChannel srcChannel = new FileInputStream(source).getChannel();
			FileChannel dstChannel = new FileOutputStream(destination).getChannel();
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
			srcChannel.close();
			dstChannel.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	   
	}
	
	/**
	*<br>目的: 判斷是否需要新增sheet
	*<br>參數:
	*/
	public boolean isAddNewSheet(int reportTotalRow, int reportTotalCell,int titleRow, int rowValue, int cellValue, int beginPasteRow){
		int m = 65536/reportTotalRow;
		if(rowValue >= m*reportTotalRow-1){
            page_count = 1;       
            sheet_num++;
            addNewSheet("Sheet", sheet_num, reportTotalRow, reportTotalCell, null);
            clearCells(rowValue, rowValue+(reportTotalRow-titleRow), cellValue, reportTotalCell-1);
            return true;
    	}else{
    		return false;
    	}
	}
	
	/**
	*<br>目的: 判斷是否需要新增sheet
	*<br>參數:
	*/
	public boolean isAddNewSheet(int reportTotalRow, int reportTotalCell,int titleRow, int rowValue, int cellValue, int beginPasteRow, int spaceRow){
		int m = 65536/(reportTotalRow+spaceRow);
		if((rowValue + titleRow + spaceRow) >= m*(reportTotalRow+spaceRow)-spaceRow){
            page_count = 1;
            sheet_num++;
            addNewSheet("Sheet", sheet_num, reportTotalRow, reportTotalCell, null);
            clearCells(rowValue, rowValue+(reportTotalRow-titleRow), cellValue, reportTotalCell-1);
            return true;
    	}else{
    		return false;
    	}
	}
	
	/**
	*<br>目的: excel轉pdf或jtml
	*<br>參數: excelURL=>excel url ; fileURL=>pdf或html url
	*/
	public void xlsTOpdfORhtml(String excelURL, String fileURL){
		File inputFile = new File(excelURL);
        File outputFile = new File(fileURL);
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
        try {      	
		    connection.connect();
		    DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
	        converter.convert(inputFile, outputFile);
	        connection.disconnect();
	    }catch(Exception e) {
            e.printStackTrace();
        } finally {
            try{ 
            	if(connection != null){
            		connection.disconnect(); connection = null;
            	}
            }catch(Exception e){
            	
            }
        }
	}
	
	/**
	 * 自動計算函數公式
	 * @param rowNum
	 * @param cellNum
	 */
	public void autoFormula(int rowNum, int cellNum){
		HSSFRow row = sheet1.getRow(rowNum);
		HSSFCell cell = row.getCell(cellNum);
		if (HSSFCell.CELL_TYPE_FORMULA == cell.getCellType()) {
			cell.setCellFormula(cell.getCellFormula());
		}
	}
	
	/**
	 * 劃線
	 * @param rowNum
	 * @param cellNum 
	 */
	public void createLine(int rowFrom, int cellFrom, int rowTo, int cellTo){
		HSSFRow oldRow = sheet1.getRow(rowFrom);
		HSSFCell oldCell = oldRow.getCell(cellFrom);
		HSSFRow newRow = sheet1.getRow(rowTo);
		HSSFCell newCell = newRow.getCell(cellTo);
		for(int i=0;i<cellFrom;i++){
			oldCell = oldRow.getCell(i);
			newCell = newRow.getCell(i);
			newCell.setCellType(oldCell.getCellType());
			newCell.setCellStyle(oldCell.getCellStyle());
		}
//		HSSFCellStyle cellStyle = cell.getCellStyle();
//		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
//		cell = row.getCell(cellNum);
//		cellStyle = cell.getCellStyle();
//		cellStyle.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
//		for(int i=0;i<=cellNum;i++){
//			cell = row.getCell(i);
//			cellStyle = cell.getCellStyle();
//			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
//		}
		
		
	}
	
	/**
	 * 自動調整列高
	 * @param rowS 起始列
	 * @param rowE 結束列
	 * @param cellS 起始欄
	 * @param cellE 結束欄
	 */
	public void autoHeight(int rowS,int rowE,int cellS,int cellE){
		String cellStr = "";
		int cellWidth=0; 
		float rowHeight=0;
		float newHeight = 0;
		float maxHeight = 0;
		int charCount = 0; 
		for(int r = rowS; r <= rowE; r++){
			rowHeight = sheet1.getRow(r).getHeightInPoints();
			maxHeight = 0;
			for(int c = cellS; c <= cellE; c++){
				charCount = 0;
				cellStr = sheet1.getRow(r).getCell(c).getStringCellValue();
				cellWidth = sheet1.getColumnWidth(sheet1.getRow(r).getCell(c).getColumnIndex())/256;
				//newHeight = getNewHeight(cellStr,rowHeight,cellWidth);
				
				for (int i = 0; i < cellStr.length(); i++) {  
					int cc = getStrChar(cellStr.substring(i, i + 1));
					charCount = charCount + cc;  
				}
				if (charCount > cellWidth){ 
					newHeight = ((int) (charCount / cellWidth) + 1) * rowHeight;//計算列高
				}else{
					newHeight = rowHeight;
				}
				
				if(newHeight > maxHeight)
					maxHeight=newHeight;
				
			}
			sheet1.getRow(r).setHeightInPoints(maxHeight);
		}
		
	}
	
	/**
	 * 計算字元
	 * @param str
	 * @return
	 */
	public static int getStrChar(String str) {  
        
        if(str==" "){  
            return 1;  
        }  
        // 判斷英文數字
        if (Pattern.compile("^[A-Za-z0-9]+$").matcher(str).matches()) {  
            return 1;  
        }  
        // 判斷全型符號
        if (Pattern.compile("[\u4e00-\u9fa5]+$").matcher(str).matches()) {  
            return 2;  
        }  
        //判斷中文
        if (Pattern.compile("[^x00-xff]").matcher(str).matches()) {  
            return 2;  
        }  
        return 1;  
  
    }  
	
}
