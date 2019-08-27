/*
*<br>程式目的：顯示Html view之函數
*<br>程式代號：View
*<br>撰寫日期：94/05/10
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/

package util;

import java.sql.ResultSet;
import util.Database;
import java.util.*;


public class View {
	
	private View() {
		//avoid instantiation...
	}
	
    /**
     * <br>
     * <br>目的：組合html option語法函數 
     * <br>參數：(1)sql字串 (2)被選的value 
     * <br>傳回：加上html option element 
     */
    static public String getOption(String sql, String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
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
    
    /**
     * <br>
     * <br>目的：組合html option語法函數 
     * <br>參數：(1)sql字串 (2)被選的value (3)欲限定的字數,超過的皆用...
     * <br>傳回：加上html option element 
     */
    static public String getOption(String sql, String selectedOne, int wordcount) {
        String rtnStr = "<option value=''>請選擇</option>";
        Database db = new Database();
        try {
        	ResultSet rs = db.querySQL(sql);
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                if(name.length()>wordcount){
                	name=name.substring(0,wordcount)+"...";
                }
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
    
    /**
     * <br>
     * <br>目的：組合html option語法函數 
     * <br>參數：(1)sql字串 (2)被選的value 
     * <br>傳回：加上html option element 
     */
    static public String getSingleOption(String sql, String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
        Database db = new Database();
        try {
        	ResultSet rs = db.querySQL(sql);
            while (rs.next()) {
                String id = rs.getString(1);
                
                rtnStr = rtnStr + "<option value='" + id + "' ";
                if (selectedOne!= null && selectedOne.equals(id)) {
                    rtnStr = rtnStr + " selected ";
                }
                rtnStr = rtnStr + ">" + id + "</option>\n";
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
     * <br>
     * <br>目的：組合Yes or No option語法函數 
     * <br>參數：被選的value 
     * <br>傳回：加上html option element 
     */
    static public String getYNOption(String selectedOne) {
    	StringBuffer rtnStr = new StringBuffer();
    	rtnStr.append("<option value=''>請選擇</option>");
    	if ("Y".equals(selectedOne)){
    		rtnStr.append("<option value='Y' selected>是</option>");
    		rtnStr.append("<option value='N'>否</option>");
    	}else if ("N".equals(selectedOne)){
    		rtnStr.append("<option value='Y'>是</option>");
    		rtnStr.append("<option value='N' selected>否</option>");
    	}else{
    		rtnStr.append("<option value='Y'>是</option>");
    		rtnStr.append("<option value='N'>否</option>");   		
    	}
        return rtnStr.toString();
    }
  
    
    /**
     * <br>
     * <br>目的：組合ownership(權屬)option語法函數 
     * <br>參數：被選的value 
     * <br>傳回：加上html option element 
     */
    static public String getOnwerOption(String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
    	//String rtnStr = "";
        Database db = new Database();
        try {
        	ResultSet rs = db.querySQL("select z.codeid, z.codeName from sysca_code z where 1=1 and z.codeKindID='owa' order by z.codeid ");
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
    
    /**
     * <br>
     * <br>目的：組合月份01~12 option語法函數 
     * <br>參數：被選的value 
     * <br>傳回：加上html option element 
     */    
    static public String getMonthOption(String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
    	for (int i=1; i<13; i++) {
            rtnStr = rtnStr + "<option value='" + Common.formatFrontZero(""+i,2) + "' ";
            if (selectedOne!= null && selectedOne.equals(Common.formatFrontZero(Integer.toString(i),2))) {
                rtnStr = rtnStr + " selected ";
            }
            rtnStr = rtnStr + ">" + Common.formatFrontZero(""+i,2) + "</option>\n";    		
    	}
        return rtnStr;
    }     
    
    /**
     * <br>
     * <br>目的：組合1~100百分比option語法函數 
     * <br>參數：被選的value 
     * <br>傳回：加上html option element 
     */    
    static public String getPercentageOption(String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
    	for (int i=1; i<101; i++) {
            rtnStr = rtnStr + "<option value='" + i + "' ";
            if (selectedOne!= null && selectedOne.equals(""+i)) {
                rtnStr = rtnStr + " selected ";
            }
            rtnStr = rtnStr + ">" + i + "%</option>\n";    		
    	}
        return rtnStr;
    }    
    
    /**
     * <br>
     * <br>目的：組合小時0~23 option語法函數 
     * <br>參數：被選的value 
     * <br>傳回：加上html option element 
     */    
    static public String getHourOption(String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
    	for (int i=0; i<24; i++) {
            rtnStr = rtnStr + "<option value='" + i + "' ";
            if (selectedOne!= null && selectedOne.equals(Integer.toString(i))) {
                rtnStr = rtnStr + " selected ";
            }
            rtnStr = rtnStr + ">" + i + "</option>\n";    		
    	}
        return rtnStr;
    }    
    
    /**
     * <br>
     * <br>目的：組合分鐘0~59 option語法函數 
     * <br>參數：被選的value 
     * <br>傳回：加上html option element 
     */    
    static public String getMinuteOption(String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
    	for (int i=0; i<60; i++) {
            rtnStr = rtnStr + "<option value='" + i + "' ";
            if (selectedOne!= null && selectedOne.equals(Integer.toString(i))) {
                rtnStr = rtnStr + " selected ";
            }
            rtnStr = rtnStr + ">" + i + "</option>\n";    		
    	}
        return rtnStr;
    }        
    
    
    /**
     * <br>
     * <br>目的：組合textOption option語法函數，分隔符號';'，第一個表value，第2個表option 
     * <br>參數：被選的value 
     * <br>傳回：加上html option element 
     */
    static public String getTextOption(String textOption , String selectedOne) {
    	StringBuffer rtnStr = new StringBuffer();
    	rtnStr.append("<option value=''>請選擇</option>");
    	String[] stestOption = textOption.split(";");
    	for(int i=0 ; i<stestOption.length;i++){
    	    if(stestOption[i].equals(selectedOne))
    	        rtnStr.append("<option value='"+stestOption[i]+"' selected>"+stestOption[i+1]+"</option>");
    	    else
    	        rtnStr.append("<option value='"+stestOption[i]+"' >"+stestOption[i+1]+"</option>");
    	    i++;
    	}
        return rtnStr.toString();
    } 
    
    /**
     * <br>
     * <br>目的：組合popOrgan語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回機關代碼的欄位名稱 (3)inputValue 機關代碼的值 (4)chineseDesc機關代碼的中文說明 (5)isLimit是否顯示全部機關名稱
     * <br>傳回：加上html option element 
     */
    static public String getPopOrgan(String className, String inputName, String inputValue, String chineseDesc) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	else if ("field_RO".equals(className)) ROStr="";
    	rtnStr.append("<input class=\""+ className +"\" type=\"hidden\" name=\""+inputName+"\" size=\"10\" maxlength=\"10\" value=\""+inputValue+"\">\n");		 
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");    	
    	if(!"".equals(ROStr))
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popOrgan('"+inputName+"','"+inputName+"Name','N')\" value=\"...\" title=\"機關輔助視窗\">\n");
    	return rtnStr.toString();
    } 
    
    /**
     * <br>
     * <br>目的：組合popOrgan語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回機關代碼的欄位名稱 (3)inputValue 機關代碼的值 (4)chineseDesc機關代碼的中文說明 (5)isLimit是否顯示全部機關名稱
     * <br>傳回：加上html option element 
     */
    static public String getPopOrgan(String className, String inputName, String inputValue, String chineseDesc,String isLimit) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	else if ("field_RO".equals(className)) ROStr="";
    	rtnStr.append("<input class=\""+ className +"\" type=\"hidden\" name=\""+inputName+"\" size=\"10\" maxlength=\"10\" value=\""+inputValue+"\">\n");		 
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");    	
    	if(!"".equals(ROStr))
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popOrgan('"+inputName+"','"+inputName+"Name','"+isLimit+"')\" value=\"...\" title=\"機關輔助視窗\">\n");
    	return rtnStr.toString();
    }    
     
    /**
     * <br>
     * <br>目的：組合PopProperty語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回財產編號的欄位名稱 (3)inputValue 財產編號的值 (4)chineseDesc財產編號的中文說明 (5)preWord財產編號的前置詞
     * <br>傳回：加上html option element 
     */
    static public String getPopProperty(String className, String inputName, String inputValue, String chineseDesc, String preWord) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	else if (className.indexOf("RO")>0) {
    		ROStr="";
    		rtnStr.append("[<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"10\" maxlength=\"11\" value=\""+inputValue+"\">");
    		rtnStr.append("<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");    		
    	} else {
    		rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"10\" maxlength=\"11\" value=\""+inputValue+"\" onBlur=\"getProperty('"+inputName+"','"+inputName+"Name','"+preWord+"');\">\n");
        	if(!"".equals(ROStr)) rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popProperty('"+inputName+"','"+inputName+"Name','"+ preWord +"')\" value=\"...\" title=\"財產編號輔助視窗\">\n");    	
        	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");    		
    	}
        return rtnStr.toString();
    } 
    
    
    /**
     * <br>
     * <br>目的：組合popStore語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回廠商編號的欄位名稱 (3)inputValue 廠商編號的值 (4)chineseDesc廠商編號的中文說明
     * <br>傳回：加上html option element 
     */
    static public String getPopStoreNo(String className, String inputName, String inputValue, String chineseDesc) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"10\" maxlength=\"10\" value=\""+inputValue+"\">\n");		 
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popStore('"+inputName+"','"+inputName+"Name')\" value=\"...\" title=\"廠商輔助視窗\">\n");
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");
        return rtnStr.toString();
    }     

    
    /**
     * <br>
     * <br>目的：組合popDocNo語法函數
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回文號編號的欄位名稱 (3)inputValue 文號編號的值 (4)chineseDesc文號編號的中文說明
     * <br>傳回：加上html option element 
     */
    static public String getPopDocNo(String className, String inputName, String inputValue, String chineseDesc) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"5\" maxlength=\"5\" value=\""+inputValue+"\">\n");		 
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popDocNo('"+inputName+"','"+inputName+"Name')\" value=\"...\" title=\"文號輔助視窗\">\n");
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");
        return rtnStr.toString();
    }  
    
    /**
     * <br>
     * <br>目的：組合popKeepUnit語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回保管單位編號的欄位名稱 (3)inputValue 保管單位編號的值 (4)chineseDesc保管單位編號的中文說明
     * <br>傳回：加上html option element 
     */
    static public String getPopKeepUnit(String className, String inputName, String inputValue, String chineseDesc) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"5\" maxlength=\"5\" value=\""+inputValue+"\">\n");		 
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popKeepUnit('"+inputName+"','"+inputName+"Name')\" value=\"...\" title=\"保管單位輔助視窗\">\n");
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");
        return rtnStr.toString();
    }      
    /**
     * <br>
     * <br>目的：組合popCalndar語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回日期的欄位名稱 (3)inputValue 日期的值
     * <br>傳回：加上html option element 
     */
    static public String getPopCalndar(String className, String inputName, String inputValue) {
    	StringBuffer rtnStr = new StringBuffer();
    	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"7\" maxlength=\"7\" value=\""+inputValue+"\">\n");		 
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popCalndar('"+inputName+"')\" value=\"...\" title=\"萬年曆輔助視窗\">\n");
        return rtnStr.toString();
    }   
    
    /**
     * <br>
     * <br>目的：組合popCalndar語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回日期的欄位名稱 (3)inputValue 日期的值 (4)欲使用的javascript function
     * <br>傳回：加上html option element 
     */
    static public String getPopCalndar(String className, String inputName, String inputValue, String js) {
    	StringBuffer rtnStr = new StringBuffer();
    	rtnStr.append("<input "+js+" class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"7\" maxlength=\"7\" value=\""+inputValue+"\">\n");		 
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popCalndar('"+inputName+"')\" value=\"...\" title=\"萬年曆輔助視窗\">\n");
        return rtnStr.toString();
    }       


    /**
     * <br>
     * <br>目的：組合popUpload語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:檔案上傳欄位名稱 (3)inputValue 檔案名稱
     * <br>傳回：一個檔案上傳欄位, 上傳及下載檔案按鈕各一個
     * <br>2005/10/22
     */
    static public String getPopUpload(String className, String inputName, String inputValue) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	else if ("field_RO".equals(className)) ROStr="";		 
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"\" size=\"20\" maxlength=\"300\" value=\""+inputValue+"\">]\n");    	
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"openUploadWindow('"+inputName+"','"+inputValue+"');\" value=\"上傳檔案\">\n");
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"Download\" onclick=\"openDownloadWindow(form1."+inputName+".value);\" value=\"下載檔案\">\n");    	
    	return rtnStr.toString();
    }   
    /**
     * <br>
     * <br>目的：組合popUpload語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:檔案上傳欄位名稱 (3)inputValue 檔案名稱
     * <br>傳回：一個檔案上傳欄位, 上傳檔案按鈕各一個
     * <br>2005/10/22
     */
    static public String getNPopUpload(String className, String inputName, String inputValue) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	else if ("field_RO".equals(className)) ROStr="";		 
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"\" size=\"20\" maxlength=\"300\" value=\""+inputValue+"\">]\n");    	
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"openUploadWindow('"+inputName+"','"+inputValue+"');\" value=\"上傳檔案\">\n");
    	return rtnStr.toString();
    }   
    
  	/**
  	 * <br> 
  	 * <br>目的：組合查詢列表之html
  	 * <br>參數：(1)主鍵之index (2)顯示之index (3)列表集合 (4)是否有查詢旗標
  	 * <br>傳回：傳回列表之html字串
  	*/
    public static String getQuerylist(boolean primaryArray[], boolean displayArray[], 
    		ArrayList objList, String queryAllFlag) {
    	int i;
    	int counter=0;
    	boolean trFlag = false;
    	StringBuffer rtnStr = new StringBuffer();    
    	StringBuffer sbQueryOne = new StringBuffer("");
    	    	
		if ("true".equals(queryAllFlag) && objList.size()==0){
			//rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
			rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100'>&nbsp;查無資料！</td></tr>");
			//rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100'>&nbsp;<input type=\"hidden\" name=\"initQueryListFlag\" value=\"false\"></td></tr>");
		}else{
			String rowArray[]=new String[primaryArray.length];
			java.util.Iterator it=objList.iterator();
			while(it.hasNext()){			
				rowArray= (String[])it.next();
				counter++;
				//顯示TR
				rtnStr.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne(");
//				if (counter==1) {
//					sbQueryOne.append("<script type='text/javascript'>if (form1.state.value=='queryAllSuccess') { try { queryOne(");
//				}				
				for(i=0;i<primaryArray.length;i++){
					if (primaryArray[i]){
						if(trFlag){
							rtnStr.append(",'").append(rowArray[i]).append("'");
//							if (counter==1) {
//								sbQueryOne.append(",'").append(rowArray[i]).append("'");
//							}
						}else{
							rtnStr.append("'").append(rowArray[i]).append("'");
//							if (counter==1) {
//								sbQueryOne.append("'").append(rowArray[i]).append("'");
//							}	
							trFlag = true;
						}						
					}
				}
//				if (counter==1) sbQueryOne.append("); } catch(e) {  }\n\n}</script>");
				rtnStr.append(")\" >\n");
	
				//顯示TD
				rtnStr.append(" <td class='listTD' >").append(counter).append(".</td>\n");
				for(i=0;i<displayArray.length;i++){
					if (displayArray[i]){
						rtnStr.append(" <td class='listTD' >").append(Common.get(rowArray[i])).append("</td>\n");
					}
				}				
				rtnStr.append("</tr>\n");
				trFlag = false;
			}
		}				
		return rtnStr.append(sbQueryOne).toString();     
    }    
    /**
  	 * <br> 
  	 * <br>目的：組合查詢列表之html
  	 * <br>參數：(1)主鍵之index (2)顯示之index (3)列表集合 (4)是否有查詢旗標
  	 * <br>傳回：傳回列表之html字串
  	*/
    public static String getQuerylist(boolean primaryArray[], boolean displayArray[], 
    		ArrayList objList, String queryAllFlag ,String choice) {
    	int i;
    	int counter=0;
    	boolean trFlag = false;
    	StringBuffer rtnStr = new StringBuffer();    
    	StringBuffer sbQueryOne = new StringBuffer("");
    	    	
		if ("true".equals(queryAllFlag) && objList.size()==0){
			//rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
			rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100'>&nbsp;查無資料！</td></tr>");
			//rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100'>&nbsp;<input type=\"hidden\" name=\"initQueryListFlag\" value=\"false\"></td></tr>");
		}else{
			String rowArray[]=new String[primaryArray.length];
			java.util.Iterator it=objList.iterator();
			while(it.hasNext()){			
				rowArray= (String[])it.next();
				counter++;
				//顯示TR
				rtnStr.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" ");
//				if (counter==1) {
//					sbQueryOne.append("<script type='text/javascript'>if (form1.state.value=='queryAllSuccess') { try { queryOne(");
//				}				
				for(i=0;i<primaryArray.length;i++){
					if (primaryArray[i]){
						if(trFlag){
							rtnStr.append(",'").append(rowArray[i]).append("'");
//							if (counter==1) {
//								sbQueryOne.append(",'").append(rowArray[i]).append("'");
//							}
						}else{
							rtnStr.append("'").append(rowArray[i]).append("'");
//							if (counter==1) {
//								sbQueryOne.append("'").append(rowArray[i]).append("'");
//							}	
							trFlag = true;
						}						
					}
				}
//				if (counter==1) sbQueryOne.append("); } catch(e) {  }\n\n}</script>");
				rtnStr.append("\" >\n");
	
				//顯示TD
				rtnStr.append(" <td class='listTD' >").append(counter).append(".</td>\n");
				for(i=0;i<displayArray.length;i++){
					if (displayArray[i]){
						rtnStr.append(" <td class='listTD' nowrap>").append(Common.get(rowArray[i])).append("</td>\n");
					}
				}				
				rtnStr.append("</tr>\n");
				trFlag = false;
			}
		}				
		return rtnStr.append(sbQueryOne).toString();     
    } 
    
  	/**
  	 * <br> 
  	 * <br>目的：組合查詢列表之html
  	 * <br>參數：(1)主鍵之index (2)顯示之index (3)列表集合對齊方式 (4)列表集合 (5)是否有查詢旗標
  	 * <br>傳回：傳回列表之html字串
  	*/    
    public static String getQuerylist(boolean primaryArray[], boolean displayArray[], String[] arrAlign,
    		ArrayList objList, String queryAllFlag) {
    	int i, counter=0;
    	boolean trFlag = false;
    	StringBuffer rtnStr = new StringBuffer(1000);
    	    	
		if ("true".equals(queryAllFlag) && objList.size()==0){
			rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100'>&nbsp;查無資料！</td></tr>");
		}else{
			String rowArray[]=new String[primaryArray.length];
			java.util.Iterator it=objList.iterator();
			while(it.hasNext()) {			
				rowArray= (String[])it.next();
				counter++;
				//顯示TR
				rtnStr.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne(");		
				for(i=0;i<primaryArray.length;i++){
					if (primaryArray[i]) {
						if (trFlag) {
							rtnStr.append(",'").append(rowArray[i]).append("'");
						} else {
							rtnStr.append("'").append(rowArray[i]).append("'");
							trFlag = true; 
						}						
					}
				}
				rtnStr.append(")\" >\n");
	
				//顯示TD
				rtnStr.append(" <td class='listTD' >").append(counter).append(".</td>\n");
				for(i=0;i<displayArray.length;i++){
					if (displayArray[i]) rtnStr.append(" <td class='listTD' style=\"text-align:").append(arrAlign[i]).append("\">").append(Common.get(rowArray[i])).append("</td>\n");					
				}				
				rtnStr.append("</tr>\n");
				trFlag = false;
			}
		}	
		return rtnStr.toString();     
    }     
    
    /**
  	 * <br> 
  	 * <br>目的：組合查詢列表之html
  	 * <br>參數：(1)主鍵之index (2)顯示之index (3)列表集合對齊方式 (4)列表集合 (5)是否有查詢旗標
  	 * <br>傳回：傳回列表之html字串
  	*/    
    public static String getQuerylist(boolean primaryArray[], boolean displayArray[], String[] arrAlign,
    		ArrayList objList, String queryAllFlag, int charchoice) {
    	int i, counter=0;
    	boolean trFlag = false;
    	StringBuffer rtnStr = new StringBuffer(1000);
    	    	
		if ("true".equals(queryAllFlag) && objList.size()==0){
			rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100'>&nbsp;查無資料！</td></tr>");
		}else{
			String rowArray[]=new String[primaryArray.length];
			java.util.Iterator it=objList.iterator();
			while(it.hasNext()) {			
				rowArray= (String[])it.next();
				counter++;
				//顯示TR
				rtnStr.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne(");		
				for(i=0;i<primaryArray.length;i++){
					if (primaryArray[i]) {
						if (trFlag) {
							rtnStr.append(",'").append(rowArray[i]).append("'");
						} else {
							rtnStr.append("'").append(rowArray[i]).append("'");
							trFlag = true; 
						}						
					}
				}
				rtnStr.append(")\" >\n");
	
				//顯示TD
				rtnStr.append(" <td class='listTD' nowrap>").append(counter).append(".</td>\n");
				for(i=0;i<displayArray.length;i++){
					if (displayArray[i]) rtnStr.append(" <td class='listTD' nowrap style=\"text-align:").append(arrAlign[i]).append("\">").append(Common.get(rowArray[i])).append("</td>\n");					
				}				
				rtnStr.append("</tr>\n");
				trFlag = false;
			}
		}	
		return rtnStr.toString();     
    }     
    
    
    
    /**
     * 取得page用的 query list,移除了一般query list NO 欄位 
     * @param primaryArray
     * @param displayArray
     * @param objList
     * @param queryAllFlag
     * @return
     */
    public static String getPageQuerylist(boolean primaryArray[], boolean displayArray[], 
            ArrayList objList, String queryAllFlag) {
        int i;
        int counter=0;
        boolean trFlag = false;
        StringBuffer rtnStr = new StringBuffer(); 
    	StringBuffer sbQueryOne = new StringBuffer("");        
        
        if ("true".equals(queryAllFlag) && objList.size()==0){
            //rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
            rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100'>&nbsp;</td></tr>");
        }else{
            String rowArray[]=new String[primaryArray.length];
            java.util.Iterator it=objList.iterator();
            while(it.hasNext()){            
                rowArray= (String[])it.next();
                counter++;
                //顯示TR
                rtnStr.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne(");
				if (counter==1) {
					sbQueryOne.append("<script type='text/javascript'>if (form1.state.value=='queryAllSuccess') { try { queryOne(");
				}				                
                for(i=0;i<primaryArray.length;i++){
                    if (primaryArray[i]){
                        if(trFlag){
                            rtnStr.append(",'"+rowArray[i]+"'");
							if (counter==1) {
								sbQueryOne.append(",'"+rowArray[i]+"'");
							}                            
                        }else{
                            rtnStr.append("'"+rowArray[i]+"'");
							if (counter==1) {
								sbQueryOne.append("'"+rowArray[i]+"'");
							}                            
                            trFlag = true;
                        }
                        
                    }
                }
                rtnStr.append(")\" >\n");
				if (counter==1) sbQueryOne.append("); } catch(e) {  }\n\n}</script>");                
                
                //顯示TD
                for(i=0;i<displayArray.length;i++){
                    if (displayArray[i]){
                        rtnStr.append(" <td class='listTD' >"+Common.get(rowArray[i])+"</td>\n");
                    }
                }               
                rtnStr.append("</tr>\n");
                trFlag = false;
            }
        }       
		return rtnStr.append(sbQueryOne).toString();     
    }            
    
    /**
     * 組出有checkbox的query List
     *
     * @param primaryArray
     * @param displayArray
     * @param arrAlign
     * @param objList
     * @param queryAllFlag
     * @param checkboxName
     * @return
     */
    public static String getCheckboxQuerylist(boolean primaryArray[], boolean displayArray[], String[] arrAlign,
            ArrayList objList, String queryAllFlag, String checkboxName) {
    	return getCheckboxQuerylist(primaryArray, displayArray, arrAlign, objList, queryAllFlag, checkboxName, null, null, null);
    }
    public static String getCheckboxQuerylist(boolean primaryArray[], boolean displayArray[], String[] arrAlign,
    		ArrayList objList, String queryAllFlag, String checkboxName, boolean linkArray[], String target) {
    	return getCheckboxQuerylist(primaryArray, displayArray, arrAlign, objList, queryAllFlag, checkboxName, linkArray, target,null);
    }    
    public static String getCheckboxQuerylist(boolean primaryArray[], boolean displayArray[], String[] arrAlign,
    		ArrayList objList, String queryAllFlag, String checkboxName, boolean linkArray[], String target, String className) {
    	int i, counter=0;
    	boolean trFlag = false, targetFlag = false;
    	//boolean even = true;
    	StringBuffer sb = new StringBuffer();
    	if (objList!=null && objList.size()>0) {
			String rowArray[]=new String[primaryArray.length];
			java.util.Iterator it=objList.iterator();
			while(it.hasNext()) {
				rowArray= (String[])it.next();
				counter++;
//				String classTR="listTROdd", classTD = "listTDOdd";
//				if (even) {
//					classTR = "listTREven";
//					classTD = "listTDEven";
//				}
				//顯示TR
				//sb.append(" <tr class='highLight' onClick=\"queryOne(");
				sb.append(" <tr class='highLight' >");
				StringBuffer v = new StringBuffer().append("");
				for(i=0;i<primaryArray.length;i++){
					if (primaryArray[i]) {
						if (trFlag) {
							v.append(",'").append(rowArray[i]).append("'");
						} else {
							v.append("'").append(rowArray[i]).append("'");
							trFlag = true;
						}
					}
				}
				//if (target!=null && !"".equals(Common.get(target))) v.append(",'").append(target).append("'");
				//sb.append(v).append(")\" >\n");


				//顯示TD
				if (className!=null && !"".equals(Common.get(className))) {
					sb.append(" <td class='listTD' >").append("<input type='checkbox' class='").append(className).append("' id=\"").append(checkboxName).append("\" name=\"").append(checkboxName).append("\" value=\"").append(v.toString().replaceAll("'", "")).append("\"></td>\n");
				} else {
					sb.append(" <td class='listTD' >").append("<input type='checkbox' id=\"").append(checkboxName).append("\" name=\"").append(checkboxName).append("\" value=\"").append(v.toString().replaceAll("'", "")).append("\"></td>\n");
				}				
				targetFlag = false;
				for(i=0;i<displayArray.length;i++){
					if (displayArray[i]) {
						if (targetFlag==false && target!=null && !"".equals(Common.get(target))) {
							v.append(",'").append(target).append("'");
							targetFlag = true;
						}

						if (arrAlign!=null && arrAlign.length>0) {
							sb.append(" <td nowrap class='listTD' style=\"text-align:").append(arrAlign[i]).append("\">"); //.append(Common.get(rowArray[i])).append("</td>\n");
						} else {
							sb.append(" <td nowrap class='listTD' >");
						}
						if (linkArray!=null && linkArray[i]) {
							sb.append("<a href='#' class='sLink2' onClick=\"queryOne(").append(v).append(")\"");
							//if (target!=null && !"".equals(Common.get(target))) sb.append(" target=\"").append(target).append("\"");
							sb.append(">").append(Common.get(rowArray[i])).append("</a>");
						} else sb.append(Common.get(rowArray[i]));
                        //System.out.println(sb.toString());
						sb.append("</td>\n");
					}
				}
				sb.append("</tr>\n");
				trFlag = false;
			}
    	} else {
    		if ("true".equals(queryAllFlag)) sb.append(" <tr class='highLight' ><td class='listTD' colspan='100'>查無資料，請您重新輸入查詢條件！</td></tr>");
    	}
		return sb.toString();
    }    
    
 
    public static String getLookupField(String sSQL) {
    	String rStr = "";
    	Database db = new Database();
    	ResultSet rs;	
    	try {
    		rs = db.querySQL(sSQL);
    		if (rs.next()){
    			rStr = Common.get(rs.getString(1));
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		db.closeAll();
    	}
    	return rStr;
    }
    
    public static String getLookupField(util.Database db, String sSQL) {
    	String rStr = "";	
    	try {
        	ResultSet rs = db.querySQL(sSQL);
    		if (rs.next()){
    			rStr = Common.get(rs.getString(1));
    		}
    		rs.getStatement().close();
    		rs.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return rStr;
    } 
    
    public static String getProcessStep(int choice){
    	String rStr = "";
    	String proc_step="";
        String[] process_step=new String[18];
        process_step[0]="A1;清查公告";
        process_step[1]="A2;待申報";
        process_step[2]="A3;受理申報";
        process_step[3]="A4;完成申報";
        process_step[4]="A5;待申請登記";
        process_step[5]="B6;受理登記";
        process_step[6]="B7;完成登記(結案)";
        process_step[7]="A8;辦理標售";
        process_step[8]="A9;結標(結案)";
        process_step[9]="AA;未能標售";
        process_step[10]="AB;囑託登記(結案)";
        process_step[11]="AC;保管款管理(9、11後續)";
        process_step[12]="AD;申領價金";
        process_step[13]="C4;受理讓售";
        process_step[14]="C5;完成讓售";
        process_step[15]="D5;完成贈與";
        process_step[16]="B8;其他方式登記(結案)";
        process_step[17]="B9;截止記載";
        if(choice==1){
        	rStr=process_step[0]+";"+process_step[4]+";"+process_step[5]+";"+process_step[6]+";"+process_step[10]+";"+process_step[16]+";"+process_step[17];
        }else if(choice==2){
        	rStr=process_step[0]+";"+process_step[1]+";"+process_step[2]+";"+process_step[3]+";"+process_step[4]+";"+process_step[5]+";"+process_step[6]+";"+process_step[7]+";"+process_step[8]+";"+process_step[9]+";"+process_step[10]+";"+process_step[16]+";"+process_step[11]+";"+process_step[12]+";"+process_step[17];
        }else if(choice==3){
        	rStr=process_step[0]+";"+process_step[4]+";"+process_step[5]+";"+process_step[6]+";"+process_step[16]+";"+process_step[17];
        }else if(choice==4){
        	rStr=process_step[0]+";"+process_step[4]+";"+process_step[6]+";"+process_step[16]+";"+process_step[17];
        }else if(choice==5){
        	rStr=process_step[0]+";"+process_step[4]+";"+process_step[5]+";"+process_step[6]+";"+process_step[7]+";"+process_step[8]+";"+process_step[9]+";"+process_step[10]+";"+process_step[16]+";"+process_step[11]+";"+process_step[12]+";"+process_step[17];
        }else if(choice==6){
        	rStr=process_step[2]+";"+process_step[3]+";"+process_step[4]+";"+process_step[5]+";"+process_step[6]+";"+process_step[16]+";"+process_step[17];
        }else if(choice==7){
        	rStr=process_step[13]+";"+process_step[14]+";"+process_step[4]+";"+process_step[5]+";"+process_step[6]+";"+process_step[16]+";"+process_step[17];
        }else if(choice==8){
        	rStr=process_step[15]+";"+process_step[4]+";"+process_step[5]+";"+process_step[6]+";"+process_step[16]+";"+process_step[17];
        }else if(choice==9){
        	rStr=process_step[0]+";"+process_step[16]+";"+process_step[17];
        }else if(choice==10){
        	rStr=process_step[0]+";"+process_step[7]+";"+process_step[8]+";"+process_step[9]+";"+process_step[10]+";"+process_step[16]+";"+process_step[11]+";"+process_step[12]+";"+process_step[17];
        }else if(choice==11){
        	rStr=process_step[0]+";"+process_step[4]+";"+process_step[5]+";"+process_step[6]+";"+process_step[16]+";"+process_step[17];
        }
    	
    	return rStr; 
    }
    
}