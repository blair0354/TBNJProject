/*
 *<br>程式目的：lcaap010f
 *<br>程式代號：lcaap010f
 *<br>程式日期：0970512
 *<br>程式作者：Richard Li
 *<br>--------------------------------------------------------
 *<br>修改作者　　修改日期　　　修改目的
 *<br>--------------------------------------------------------
 */

package lca.ap;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import util.*;
import util.report.ReportEnvironment;
import util.report.TableModelReportEnvironment;
import util.report.TableModelReportGenerator;

import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.swing.table.DefaultTableModel;

public class LCAAP180F extends QueryBean {
	String queryType;
	String print_kind;
	String print_type;
	String imp_kind;

	String itemPicture1;
	String itemPicture2;
	

	String filestoreLocation;
	String reportLocation;
	String uip;
	String qry_date_start;
	String qry_time_start;
	String qry_date_end;
	String qry_time_end;
	String qry;
	String dqry;
	String qry_msg;
	String con;
	String qry_seq;
	ServletContext context;
	Vector name_stock=new Vector();//存放查詢名字
	Vector sn_stock=new Vector();//存放統編
	Vector s_ym=new Vector();//比較基準日期
	
	int loopCNT=0;//以1000筆人檔為1個for loop
	int nowCNT=0;//目前正在執行第幾個loop
	String print_date;//報表上的列印時間採用執行第一個loop時的時間
	String vid ="";//檔案生成的資料夾路徑
	Vector columns = new Vector();//設定報表的表頭
	String fileType="";//報表檔案類別
	
	String check_RHD10;//106增修案
	
	ArrayList<String []> txtData = new ArrayList<String []>();
	ArrayList<String []> txtDataRe = new ArrayList<String []>();
	
	ArrayList<String []> txtNoData = new ArrayList<String []>();
	ArrayList<String []> txtNoDataRe = new ArrayList<String []>();

	public String getQueryType() {return checkGet(queryType);}
	public void setQueryType(String s) {this.queryType = checkSet(s);}
	
	public String getPrint_kind() {	return checkGet(print_kind);}
	public void setPrint_kind(String s) {this.print_kind = checkSet(s);}
	
	public String getPrint_type() {	return checkGet(print_type);}
	public void setPrint_type(String s) {this.print_type = checkSet(s);}
	
	public String getImp_kind() {	return checkGet(imp_kind);}
	public void setImp_kind(String s) {this.imp_kind = checkSet(s);}
	
	public String getItemPicture1() {return checkGet(itemPicture1);}
	public void setItemPicture1(String s) {this.itemPicture1 = checkSet(s);}

	public String getItemPicture2() {return checkGet(itemPicture2);}
	public void setItemPicture2(String s) {this.itemPicture2 = checkSet(s);}

	public String getFilestoreLocation() {return checkGet(filestoreLocation);}
	public void setFilestoreLocation(String s) {this.filestoreLocation = checkSet(s);}

	public String getReportLocation() {return checkGet(reportLocation);}
	public void setReportLocation(String s) {this.reportLocation = checkSet(s);}

	public String getUip() {return checkGet(uip);}
	public void setUip(String s) {this.uip = checkSet(s);}

	public String getDqry() {return checkGet(dqry);	}
	public void setDqry(String s) {this.dqry = checkSet(s);}

	public String getQry() {return checkGet(qry);}
	public void setQry(String s) {this.qry = checkSet(s);}
	
	public String getQry_date_end() {return checkGet(qry_date_end);	}
	public void setQry_date_end(String s) {this.qry_date_end = checkSet(s);}
	
	public String getQry_date_start() {	return checkGet(qry_date_start);}
	public void setQry_date_start(String s) {this.qry_date_start = checkSet(s);}
	
	public String getQry_msg() {return checkGet(qry_msg);}
	public void setQry_msg(String s) {this.qry_msg = checkSet(s);}
	
	public String getQry_time_end() {return checkGet(qry_time_end);	}
	public void setQry_time_end(String s) {this.qry_time_end = checkSet(s);}
	
	public String getQry_time_start() {	return checkGet(qry_time_start);}
	public void setQry_time_start(String s) {this.qry_time_start = checkSet(s);}

	public String getCon() {return checkGet(con);}
	public void setCon(String con) {this.con = checkSet(con);}

	public String getQry_seq() {return checkGet(qry_seq);}
	public void setQry_seq(String qry_seq) {this.qry_seq = checkSet(qry_seq);}

	public ServletContext getContext() {return context;	}
	public void setContext(ServletContext context) {this.context = context;	}

	//106年增修案
	public String getCheck_RHD10() {return checkGet(check_RHD10);}
	public void setCheck_RHD10(String s) {this.check_RHD10 = checkSet(s);}
	
	/**
	 * 目地:將文字檔寫入EFORM7
	 * 處理:
	 *    getImp_kind()=1 >> 重新匯入:先將EFORM7刪除,再將文字檔內窩全部寫入EFORM7
	 *    getImp_kind()=2 >> 更新匯入:該統編(姓名)資EFORM7中無資料才寫入
	 * @return
	 */
	public boolean ModifyEFORM7(){
		boolean ret=false;
		//先將文字檔的資料取出
		
		String wInsFieldSQL="";
		String sTQRY_TYPE="";
		Database db = new Database();
		
		String IMP_YMD=util.Datetime.getYYYMMDD();
		String IMP_TIME=util.Datetime.getHHMMSS();
		
		//String temp[] = null;
		try {
			
			if(queryType.equals("1")){
				wInsFieldSQL="TQRY_ID";//勞保匯入統編格式
				sTQRY_TYPE="1";//勞保匯入統編格式
			}else{
				wInsFieldSQL="TQRY_NAME";//勞保匯入姓名格式
				sTQRY_TYPE="2";//勞保匯入姓名格式
			}
			
			java.util.Map<String, String> MapEFORM7 = new java.util.HashMap<String, String>();
			ArrayList<String []> txtDataImp = new ArrayList<String []>();
			txtDataImp = Common.getFileArray(filestoreLocation, itemPicture1, 2);
			
			if("1".equals(getImp_kind())){
				db.exeSQL("delete from EFORM7 where TQRY_TYPE="+Common.sqlChar(sTQRY_TYPE));
			}else if("2".equals(getImp_kind())){//更新匯入
				String checkSQL="select "+wInsFieldSQL+" from EFORM7 where TQRY_TYPE="+Common.sqlChar(sTQRY_TYPE);
				ResultSet rs=db.querySQL(checkSQL);
				while(rs.next()){
					MapEFORM7.put(rs.getString(1),"Y");
				}
				rs.getStatement().close();
				rs.close();
			}
			
			Vector vImpTxtSQL=new Vector();
			//Vector vImpTxtSQL2=new Vector();
			
			boolean insBol=true;
			//String checkSQL="";
			if(txtDataImp.size()>0){
				int i=0;
				for(String s[] : txtDataImp){
					System.out.println(++i+":"+s[0]+","+s[1]);

					if("1".equals(getImp_kind())){//重新匯入
						vImpTxtSQL.add("insert into EFORM7(TQRY_TYPE,"+wInsFieldSQL+",TQRY_YM,IMP_TYPE,IMP_YMD,IMP_TIME)values("+Common.sqlChar(sTQRY_TYPE)+","+Common.sqlChar(s[0])+","+Common.sqlChar(s[1])+","+Common.sqlChar("1")+","+Common.sqlChar(IMP_YMD)+","+Common.sqlChar(IMP_TIME)+")");
					}else if("2".equals(getImp_kind())){//更新匯入
						if(!"Y".equals(Common.get(MapEFORM7.get(s[0])))){
							vImpTxtSQL.add("insert into EFORM7(TQRY_TYPE,"+wInsFieldSQL+",TQRY_YM,IMP_TYPE,IMP_YMD,IMP_TIME)values("+Common.sqlChar(sTQRY_TYPE)+","+Common.sqlChar(s[0])+","+Common.sqlChar(s[1])+","+Common.sqlChar("2")+","+Common.sqlChar(IMP_YMD)+","+Common.sqlChar(IMP_TIME)+")");
						}
					}
				}
				
				if(vImpTxtSQL != null){
					db.excuteSQL(vImpTxtSQL);//將查無資料的人檔資料寫入EFORM7
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return ret;
	}
	
	public void genReport() throws Exception {
		
		txtDataRe=EFORM7Array();//讀取EFORM7的暫存資料並回傳ArrayList
		if(txtDataRe.size()>0){
		
			doSetReport();//設定報表表頭、報表格式、報表產製時間
			
			loopCNT=txtDataRe.size()/10000;//每10000個權利人就切成一個loop
			if(txtDataRe.size()%10000>0){//有餘數就需將loop數+1,ex:4231筆資料就會有5個loop
				loopCNT+=1;
			}
			System.out.println("loopCNT:"+loopCNT);
			
			int min=0;//當次loop權利人的起始筆數
			int max=0;//當次loop權利人的最後一筆筆數
			for(nowCNT=1;nowCNT<=loopCNT;nowCNT++){
				
				if(nowCNT==loopCNT){
					max=txtDataRe.size();
				}else{
					max=nowCNT*10000;
				}
				txtData.clear();
				for(int i=min;i<max;i++){
					txtData.add(txtDataRe.get(i));
				}
				
				if(getPrint_kind().equals("1")){
					genLandReport();  //土地
				}else{
					genBuildReport();  //建物
				}
				
				min=max;
			}
		}
	}
	
public ArrayList<String []> EFORM7Array(){
	Database db = new Database();
	ArrayList<String []> reList = new ArrayList<String []>();
	String temp[] = null;
	try {
		String sql="";
		if("1".equals(getQueryType())){
			sql = " select TQRY_ID,TQRY_YM from EFORM7 where TQRY_TYPE = '1' order by TQRY_ID";//勞保身分證字號比對
			setErrorMsg("無統一編號資料可進行比對,請先匯入統一編號文字檔!");
		}else if("2".equals(getQueryType())){
			sql = " select TQRY_NAME,TQRY_YM from EFORM7 where TQRY_TYPE = '2'  order by TQRY_NAME";//勞保姓名比對
			setErrorMsg("無姓名資料可進行比對,請先匯入姓名文字檔!");
		}
		ResultSet rs = db.querySQL(sql);
		while(rs.next()){
			temp = new String[2];
			temp[0] = Common.get(rs.getString(1));
			temp[1] = Common.get(rs.getString(2));
			reList.add(temp);
			setErrorMsg("");
		}
		rs.getStatement().close();
		rs.close();
	} catch (Exception e) {
		setErrorMsg("請先選擇統一編號或姓名比對!");
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return reList;
}
	
public void doSetReport(){
		
		print_date = Datetime.getYYYMMDD().substring(0, 3) + "/"
			+ Datetime.getYYYMMDD().substring(3, 5) + "/"
			+ Datetime.getYYYMMDD().substring(5) + "   "
			+ Datetime.getHHMMSS().substring(0, 2) + ":"
			+ Datetime.getHHMMSS().substring(2, 4) + ":"
			+ Datetime.getHHMMSS().substring(4);
			
		qry_date_start = Datetime.getYYYMMDD();
		qry_time_start = Datetime.getHHMMSS();
		vid = Common.getVMID();
		
		if(getPrint_kind().equals("1")){//土地
				qry_seq = "J" + qry_date_start + qry_time_start;
				this.setQry_seq(qry_seq);
		}else{//建物
				qry_seq = "K" + qry_date_start + qry_time_start;
				this.setQry_seq(qry_seq);
		}
		
		if (this.getPrint_type().equals("3")) {//文字檔
			fileType=".txt";
		}else{//PDF檔
			fileType=".pdf";
		}
		
		if (this.getPrint_kind().equals("1") && this.getPrint_type().equals("3")) {//土地文字檔	
			columns.addElement("lidn");
			columns.addElement("lnam");
			columns.addElement("aa45");
			columns.addElement("chtaa45");
			columns.addElement("aa46");
			columns.addElement("chtaa46");
			columns.addElement("ba48");
			columns.addElement("chtba48");
			columns.addElement("ba49");
			columns.addElement("ba49_1");
			columns.addElement("bb06");
			columns.addElement("chtbb06");
			columns.addElement("bb07");
			columns.addElement("bb05");
			columns.addElement("bb15_1");
			columns.addElement("cht_bb15");
			columns.addElement("bb15_2");
			columns.addElement("bb15_3");//目前18
			columns.addElement("s_ym");
		}else if (this.getPrint_kind().equals("1") && this.getPrint_type().equals("2")) {//土地PDF
			columns.addElement("lidn");
			columns.addElement("lnam");
			columns.addElement("aa45");
			columns.addElement("aa46");
			columns.addElement("ba48");
			columns.addElement("ba49");
			columns.addElement("ba49_1");
			columns.addElement("bb06");
			columns.addElement("bb07");
			columns.addElement("bb05");
			columns.addElement("bb15_1");//目前11
			columns.addElement("s_ym");
		}else if (this.getPrint_kind().equals("2") && this.getPrint_type().equals("3")) {//建物文字檔
			columns.addElement("lidn");
			columns.addElement("lnam");
			columns.addElement("dd45");
			columns.addElement("chtdd45");
			columns.addElement("dd46");
			columns.addElement("chtdd46");
			columns.addElement("ed48");
			columns.addElement("chted48");
			columns.addElement("ed49");
			columns.addElement("ed49_1");
			columns.addElement("ee06");
			columns.addElement("chtee06");
			columns.addElement("ee07");
			columns.addElement("ee05");
			columns.addElement("ee15_1");
			columns.addElement("chtee15");
			columns.addElement("ee15_2");
			columns.addElement("ee15_3");
			columns.addElement("dd09");//目前19
			columns.addElement("s_ym");
			if("Y".equals(getCheck_RHD10())){
				columns.addElement("HD4849");
				columns.addElement("HD4849C");
			}
		}else if (this.getPrint_kind().equals("2") && this.getPrint_type().equals("2")) {//建物PDF
			columns.addElement("lidn");
			columns.addElement("lnam");
			columns.addElement("dd45");
			columns.addElement("dd46");
			columns.addElement("ed48");
			columns.addElement("ed49");
			columns.addElement("ed49_1");
			columns.addElement("ee06");
			columns.addElement("ee07");
			columns.addElement("ee05");
			columns.addElement("ee15_1");
			columns.addElement("dd09");//目前12
			columns.addElement("s_ym");
			if("Y".equals(getCheck_RHD10())){
				columns.addElement("HD4849C");
			}
		}
		
	}
	
	
	//土地
	public void genLandReport(){
		TableModelReportEnvironment env = new TableModelReportEnvironment();
		javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		Vector rowData=new Vector();//這裡宣告是為了重新塞table內容
		try {
			if (this.getPrint_type().equals("3")) {
				model = this.getLandCSVResultModel();
			} else {
				model = getLandResultModel();
			}
			if ((model == null) || (model.getDataVector().size() == 0)) {
				Common.updateDL_LOG("4", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			} else {
				env.setTableModel(model);
				if (this.getPrint_type().equals("2")) {
					env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca182.jasper"));
					env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
				}
				if (this.getPrint_type().equals("3")) {
					env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca181.jasper"));
					env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
				}
				Database sdb = new Database();
				String username = sdb.getLookupField("select user_name from etecuser where user_id=" + Common.sqlChar(this.getUserID()));
				HashMap parms = new HashMap();
				parms.put("print_date", "製表日期："+print_date);
				parms.put("print_count", "筆數："	+ String.valueOf(model.getRowCount()));
				parms.put("printer_usr", username);
				parms.put("printer_unit", this.getUnit());
				TableModelReportGenerator generator = new TableModelReportGenerator(env);
				java.io.File outputFile;
				File tempDirectory = new File(reportLocation, vid);
				tempDirectory.mkdirs();
				
				if(nowCNT==loopCNT && nowCNT==1){//產出檔案時,若cowCNT=1且nowCNT=loopCN代表只有一個檔案
					outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq() + fileType);
				}else{//產出檔案時有多個檔案需在檔名後加上(X)
					outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq()+"("+nowCNT+")" + fileType);
				}
				
				if (this.getPrint_type().equals("2")) {//產製PDF檔
					generator.reportToFile(outputFile, parms);
				} else if (this.getPrint_type().equals("3")) {//產製TXT檔
					generator.reportToFile(outputFile, parms, "MS950");
				}
				
				if(nowCNT==loopCNT){//代表執行到最後一個loop需更新DS的LOG內容
					Common.updateDL_LOG("2", vid + ":;:"+ this.getQry_seq() + fileType, Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
					genNoDataReport();
				}
				
			}
		} catch (Exception x) {
			Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			x.printStackTrace();
		}
	}
	
	//土地PDF
	public DefaultTableModel getLandResultModel() throws Exception {
		int checkRow=1;
		con = "勞保局土地歸戶清冊";
		qry = "市縣市=;事務所=;統一編號=;姓名=";
		qry += ";統編批次=" + this.getItemPicture1() + ";姓名批次=" + this.getItemPicture2();
		
		if(nowCNT==1){//第1個loop才要做insert到DL的LOG檔
			Common.insertDL_LOG(qry_seq, "J", this.getUserID(), this.getUnitID(), qry, this.getPrint_type(), qry_date_start, qry_time_start);
		}
		
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		ODatabase db = new ODatabase();
		ResultSet rs_txt = null;
		ResultSet rs_ow = null;
		ResultSet rs = null;
		
		Database db_sr = new Database();
		Vector SQLContainer=new Vector();
		
		try {
			Vector rowData = new Vector();
			//將原本透過使用者勾選的資料，改成從上傳的文字檔全數直接至rlnid搜尋後全數取出
			for(String s[] : txtData){
				boolean dataExist = false;
				
				String qLidn = "";  //查詢統一編號
				String qLnam = "";  //查詢名稱
				String s_ym = Common.get(s[1]);  //比較基準日期
				String sql_txt = " select lidn, lnam, cty, unit from rlnid "
						+ " where 1=1 ";
				//根據上傳的條件種類決定
				if(queryType.equals("1")){
					qLidn = Common.get(s[0]);
					sql_txt += " and LIDN = " + Common.sqlChar(qLidn);
				}else{
					qLnam = Common.get(s[0]);
					sql_txt += " and LNAM = " + Common.sqlChar(qLnam);
				}
				sql_txt += " order by lidn, cty, unit ";
//System.out.println("sql_txt="+sql_txt);			
				rs_txt = db.querySQL(Common.Ms950ToIso(sql_txt));
				while(rs_txt.next()){
Long start = System.currentTimeMillis();
					String lidn = rs_txt.getString("lidn");
					String lnam = rs_txt.getString("lnam");
					String cty = rs_txt.getString("cty");
					String unit = rs_txt.getString("unit");
					
					if(nowCNT==1 && checkRow==1){//第1個loop才要做insert到BS的LOG檔
						Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", "", "", "", "", "", "", lidn, Common.isoToBig5(lnam), "", "", "", "", qry_seq, "2");
					}
					
					String sql_ow = "select "
							+ " rb.cty, rb.unit, rb.ba48, rb.ba49, rb.bb06, rb.bb07, rb.bb05, rb.bb09, rb.bb15_1, rb.bb15_2, rb.bb15_3, "
							+ " rk06.kcnt as ch_bb06, "
							+ " rk15.kcnt as cht_bb15 "  
							+ " from RBLOW RB "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='06' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk06 "  /*人檔資料*/
							+ " on rk06.KCDE_2 =  rb.bb06 "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='15' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk15 "  /*人檔資料*/
							+ " on rk15.KCDE_2 =  rb.bb15_1 "
							+ " where 1=1 "
							+ " and rb.bb09 = " + Common.sqlChar(lidn)  /*人檔資料*/
							+ " and rb.cty = " + Common.sqlChar(cty)  /*人檔資料*/
							+ " and rb.unit = " + Common.sqlChar(unit)  /*人檔資料*/
							+ " order by rb.cty, rb.unit, rb.ba48, rb.ba49, rb.bb01 ";
//System.out.println("sql_ow="+sql_ow);
					rs_ow = db.querySQL(sql_ow);
					while (rs_ow.next()) {
						String sql_id = " select "
								+ " ra.cty,ra.unit,ra.aa48, ra.aa49, ra.aa45, ra.aa46, ra.aa10, ra.aa11, ra.aa12, ra.aa16, "
								+ " (select max(kcnt) from rkeyn where kcde_1='45' and kcde_2=ra.aa45 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_cty, "  /*人檔資料*/
								+ " (select max(kcnt) from rkeyn where kcde_1='46' and kcde_2=ra.aa46 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa46, " /*人檔資料*/ 
								+ " (select max(kcnt) from rkeyn where kcde_1='48' and kcde_2=ra.aa48 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa48 "  /*人檔資料*/ 
								+ " FROM RALID RA "
								+ " where 1=1 "
								+ " and ra.aa48 = " + Common.sqlChar(rs_ow.getString("ba48"))
								+ " and ra.aa49 = " + Common.sqlChar(rs_ow.getString("ba49"))
								+ " and ra.cty = " + Common.sqlChar(cty)
								+ " and ra.unit = " + Common.sqlChar(unit);
//System.out.println("sql_id="+sql_id);
						rs = db.querySQL(sql_id);
						while (rs.next()) {
							dataExist = true;
							Vector data = new Vector();
							data.add(Common.get(lidn));
							data.add(Common.isoToMS950(lnam));
							data.add(Common.isoToMS950(rs.getString("ch_cty")));
							data.add(Common.isoToMS950(rs.getString("ch_aa46")));
							data.add(Common.isoToMS950(rs.getString("ch_aa48")));
							data.add(Common.get(rs_ow.getString("ba49").substring(0, 4)));
							data.add(Common.get(rs_ow.getString("ba49").substring(4)));
							data.add(Common.isoToMS950(rs_ow.getString("ch_bb06")));
							data.add(Common.get(rs_ow.getString("bb07")));
							data.add(Common.get(rs_ow.getString("bb05")));
							if (Common.get(rs_ow.getString("bb15_1")).equals("")) {
								data.add(Common.get(rs_ow.getString("bb15_2")) + " 分之 " + Common.get(rs_ow.getString("bb15_3")));
							} else {
								data.add(Common.isoToMS950(rs_ow.getString("cht_bb15")) + Common.get(rs_ow.getString("bb15_2")) + " 分之 "+ Common.get(rs_ow.getString("bb15_3")));
							}
							data.add(s_ym);  //比較基準日期
							rowData.add(data);
						}
						rs.getStatement().close();
						rs.close();
					}
					rs_ow.getStatement().close();
					rs_ow.close();
					
System.out.println("LCAAP140F土地-->>"+nowCNT+"-"+checkRow+". query: lidn=" + lidn + ", cty=" + cty + ", unit=" + unit + ", time=" + (System.currentTimeMillis() - start));
checkRow++;
				}
				rs_txt.getStatement().close();
				rs_txt.close();
				
				if(!dataExist){
					Vector data = new Vector();
					data.addElement(qLidn);
					data.addElement(qLnam);
					data.addElement("");
					data.addElement("");
					data.addElement("無符合條件");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement(s_ym);
					rowData.add(data);
					SQLContainer.add("insert into ND_LOG (NQRY_SEQ,NQRY_ID,NQRY_NAME,NQRY_YM)values("+Common.sqlChar(this.getQry_seq())+","+Common.sql2Char(qLidn)+","+Common.sql2Char(qLnam)+","+Common.sql2Char(s_ym)+")");
				}
			}
			model.setDataVector(rowData, columns);
			
			if(SQLContainer != null){
				db_sr.excuteSQL(SQLContainer);//將查無資料的人檔資料寫入ND_LOG
			}
			
			if(nowCNT==loopCNT){//最後一個loop執行結束才要做update到BS的LOG檔
				Common.updateBS_LOG(Datetime.getYYYMMDD(), Datetime.getFHHMMSS(),"已完成查詢", qry_seq);
			}
			
			this.setState("init");
		} catch (Exception e) {
			Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			e.printStackTrace();
		} finally {
			try { if (rs_txt!=null){ rs_txt.close(); rs_txt=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs_ow!=null){ rs_ow.close(); rs_ow=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
			db.closeAll();
			db_sr.closeAll();
		}
		return model;
	}

	//土地TXT
	public DefaultTableModel getLandCSVResultModel() throws Exception {
		int checkRow=1;
		con = "勞保局土地歸戶清冊";
		qry = "市縣市=;事務所=;統一編號=;姓名=";
		qry += ";統編批次=" + this.getItemPicture1() + ";姓名批次=" + this.getItemPicture2();
		
		if(nowCNT==1){//第1個loop才要做insert到DL的LOG檔
			Common.insertDL_LOG(qry_seq, "J", this.getUserID(), this.getUnitID(),qry, this.getPrint_type(), qry_date_start, qry_time_start);
		}
		
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		ODatabase db = new ODatabase();
		ResultSet rs_txt = null;
		ResultSet rs_ow = null;
		ResultSet rs = null;
		
		Database db_sr = new Database();
		Vector SQLContainer=new Vector();
		
		try {
			Vector rowData = new Vector();
			//將原本透過使用者勾選的資料，改成從上傳的文字檔全數直接至rlnid搜尋後全數取出
			int i=0;
			for(String s[] : txtData){
				++i;
				System.out.println(i+"."+s[0]+":"+s[1]);
				boolean dataExist = false;
				
				String qLidn = "";  //查詢統一編號
				String qLnam = "";  //查詢名稱
				String s_ym = Common.get(s[1]);  //比較基準日期
				String sql_txt = " select lidn, lnam, cty, unit from rlnid "
						+ " where 1=1 ";
				//根據上傳的條件種類決定
				if(queryType.equals("1")){
					qLidn = Common.get(s[0]);
					sql_txt += " and LIDN = " + Common.sqlChar(qLidn);
				}else{
					qLnam = Common.get(s[0]);
					sql_txt += " and LNAM = " + Common.sqlChar(qLnam);
				}
				sql_txt += " order by LIDN, cty, unit ";
			
//System.out.println("sql_txt="+sql_txt);
				rs_txt = db.querySQL(Common.Ms950ToIso(sql_txt));
				while(rs_txt.next()){
Long start = System.currentTimeMillis();
					String lidn = rs_txt.getString("lidn");
					String lnam = rs_txt.getString("lnam");
					String cty = rs_txt.getString("cty");
					String unit = rs_txt.getString("unit");
					
					if(nowCNT==1 && checkRow==1){//第1個loop才要做insert到BS的LOG檔
						Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", "", "", "", "", "", "", lidn, Common.isoToBig5(lnam), "", "", "", "", qry_seq, "2");
					}
					
					String sql_ow = "select "
							+ " rb.cty, rb.unit, rb.ba48, rb.ba49, rb.bb06, rb.bb07, rb.bb05, rb.bb09, rb.bb15_1, rb.bb15_2, rb.bb15_3, "
							+ " rk06.kcnt as ch_bb06, "
							+ " rk15.kcnt as cht_bb15 "  
							+ " from RBLOW RB "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='06' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk06 "  /*人檔資料*/
							+ " on rk06.KCDE_2 =  rb.bb06 "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='15' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk15 "  /*人檔資料*/
							+ " on rk15.KCDE_2 =  rb.bb15_1 "
							+ " where 1=1 "
							+ " and rb.bb09 = " + Common.sqlChar(lidn)  /*人檔資料*/
							+ " and rb.cty = " + Common.sqlChar(cty)  /*人檔資料*/
							+ " and rb.unit = " + Common.sqlChar(unit)  /*人檔資料*/
							+ " order by rb.cty, rb.unit, rb.ba48, rb.ba49, rb.bb01 ";
//System.out.println("sql_ow="+sql_ow);
					rs_ow = db.querySQL(sql_ow);
					while (rs_ow.next()) {
						String sql_id = " select "
								+ " ra.cty,ra.unit,ra.aa48, ra.aa49, ra.aa45, ra.aa46, ra.aa10, ra.aa11, ra.aa12, ra.aa16, "
								+ " (select max(kcnt) from rkeyn where kcde_1='45' and kcde_2=ra.aa45 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_cty, "  /*人檔資料*/
								+ " (select max(kcnt) from rkeyn where kcde_1='46' and kcde_2=ra.aa46 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa46, " /*人檔資料*/ 
								+ " (select max(kcnt) from rkeyn where kcde_1='48' and kcde_2=ra.aa48 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa48 "  /*人檔資料*/ 
								+ " FROM RALID RA "
								+ " where 1=1 "
								+ " and ra.aa48 = " + Common.sqlChar(rs_ow.getString("ba48"))
								+ " and ra.aa49 = " + Common.sqlChar(rs_ow.getString("ba49"))
								+ " and ra.cty = " + Common.sqlChar(cty)
								+ " and ra.unit = " + Common.sqlChar(unit);
//System.out.println("sql_id="+sql_id);
						rs = db.querySQL(sql_id);
						while (rs.next()) {
							dataExist = true;
							Vector data = new Vector();
							data.add(Common.get(lidn));
							data.add(Common.isoToMS950(lnam));
							data.add(Common.get(rs.getString("aa45")));
							data.add(Common.isoToMS950(rs.getString("ch_cty")));
							data.add(Common.get(rs.getString("aa46")));
							data.add(Common.isoToMS950(rs.getString("ch_aa46")));
							data.add(Common.get(rs.getString("aa48")));
							data.add(Common.isoToMS950(rs.getString("ch_aa48")));
							data.add(Common.get(rs_ow.getString("ba49").substring(0, 4)));
							data.add(Common.get(rs_ow.getString("ba49").substring(4)));
							data.add(Common.get(rs_ow.getString("bb06")));
					        data.add(Common.isoToMS950(rs_ow.getString("ch_bb06")));
							data.add(Common.get(rs_ow.getString("bb07")));
							data.add(Common.get(rs_ow.getString("bb05")));
							data.add(Common.get(rs_ow.getString("bb15_1")));
							data.add(Common.isoToMS950(rs_ow.getString("cht_bb15")));
							data.add(Common.get(rs_ow.getString("bb15_2")));
							data.add(Common.get(rs_ow.getString("bb15_3")));
							data.add(s_ym);  //比較基準日期
							rowData.add(data);
						}
						rs.getStatement().close();
						rs.close();
					}
					rs_ow.getStatement().close();
					rs_ow.close();

System.out.println("LCAAP140F土地-->>"+nowCNT+"-"+checkRow+". query: lidn=" + lidn + ", cty=" + cty + ", unit=" + unit + ", time=" + (System.currentTimeMillis() - start));
checkRow++;


				}
				rs_txt.getStatement().close();
				rs_txt.close();
				
				if(!dataExist){
					Vector data = new Vector();
					data.addElement(qLidn);
					data.addElement(qLnam);
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("無符合條件");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement(s_ym);
					rowData.add(data);
					SQLContainer.add("insert into ND_LOG (NQRY_SEQ,NQRY_ID,NQRY_NAME,NQRY_YM)values("+Common.sqlChar(this.getQry_seq())+","+Common.sql2Char(qLidn)+","+Common.sql2Char(qLnam)+","+Common.sql2Char(s_ym)+")");
				}
			}
			model.setDataVector(rowData, columns);
			
			if(SQLContainer != null){
				db_sr.excuteSQL(SQLContainer);//將查無資料的人檔資料寫入ND_LOG
			}
			
			if(nowCNT==loopCNT){//最後一個loop執行結束才要做update到BS的LOG檔
				Common.updateBS_LOG(Datetime.getYYYMMDD(), Datetime.getFHHMMSS(),"已完成查詢", qry_seq);
			}
			
			this.setState("init");
		} catch (Exception e) {
			Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			e.printStackTrace();
		} finally {
			try { if (rs_txt!=null){ rs_txt.close(); rs_txt=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs_ow!=null){ rs_ow.close(); rs_ow=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
			db.closeAll();
			db_sr.closeAll();
		}
		return model;
	}
	
	
	//建物
	public void genBuildReport(){
		TableModelReportEnvironment env = new TableModelReportEnvironment();
		javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		try {
			if (this.getPrint_type().equals("3")) {
				model = this.getBuildCSVResultModel();
			} else {
				model = getBuildResultModel();
			}
			if ((model == null) || (model.getDataVector().size() == 0)) {
				Common.updateDL_LOG("4", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
		
			} else {
				env.setTableModel(model);
				if (this.getPrint_type().equals("2")) {
					if("Y".equals(getCheck_RHD10())){
						env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca184_2.jasper"));
						env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
					}else{
						env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca184.jasper"));
						env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
					}
		
				} else if (this.getPrint_type().equals("3")) {
					if("Y".equals(getCheck_RHD10())){
						env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca183_2.jasper"));
						env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
					}else{
						env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca183.jasper"));
						env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
					}
				}
				Database sdb = new Database();
				String username = sdb.getLookupField("select user_name from etecuser where user_id=" + Common.sqlChar(this.getUserID()));
				HashMap parms = new HashMap();
				parms.put("print_date", "製表日期："+print_date);
				parms.put("print_count", "筆數："	+ String.valueOf(model.getRowCount()));
				parms.put("printer_usr", username);
				parms.put("printer_unit", this.getUnit());
				TableModelReportGenerator generator = new TableModelReportGenerator(env);
				File tempDirectory = new File(reportLocation, vid);
				java.io.File outputFile;
				tempDirectory.mkdirs();
				
				if(nowCNT==loopCNT && nowCNT==1){//產出檔案時,若cowCNT=1且nowCNT=loopCN代表只有一個檔案
					outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq() + fileType);
				}else{//產出檔案時有多個檔案需在檔名後加上(X)
					outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq()+"("+nowCNT+")" + fileType);
				}
				
				if(this.getPrint_type().equals("2")) {//產製PDF檔
					generator.reportToFile(outputFile, parms);
				}else if (this.getPrint_type().equals("3")) {	//產製TXT檔
					generator.reportToFile(outputFile, parms, "MS950");
				}
				
				if(nowCNT==loopCNT){//代表執行到最後一個loop需更新DS的LOG內容
					Common.updateDL_LOG("2", vid + ":;:"+ this.getQry_seq() + fileType, Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
					genNoDataReport();//產出查無資料的報懷
				}
				
			}
		} catch (Exception x) {
			Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			x.printStackTrace();
		}
	}
	
	//建物TXT
	public DefaultTableModel getBuildCSVResultModel() throws Exception {
		int checkRow=1;
		con = "勞保局建物歸戶清冊";
		qry = "市縣市=;事務所=;統一編號=;姓名=";
		qry += ";統編批次=" + this.getItemPicture1() + ";姓名批次="
				+ this.getItemPicture2();
		
		
		if(nowCNT==1){//第1個loop才要做insert到DL的LOG檔
			Common.insertDL_LOG(qry_seq, "K", this.getUserID(), this.getUnitID(),qry, this.getPrint_type(), qry_date_start, qry_time_start);
		}
		
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		ODatabase db = new ODatabase();
		ResultSet rs_txt = null;
		ResultSet rs_ow= null;
		ResultSet rs = null;
		
		Database db_sr = new Database();
		Vector SQLContainer=new Vector();
		
		try {
			Vector rowData = new Vector();
			//將原本透過使用者勾選的資料，改成從上傳的文字檔全數直接至rlnid搜尋後全數取出
			for(String s[] : txtData){
				boolean dataExist = false;
				
				String qLidn = "";  //查詢統一編號
				String qLnam = "";  //查詢名稱
				String s_ym = Common.get(s[1]);  //比較基準日期
				String sql_txt = " select lidn, lnam, cty, unit from rlnid "
						+ " where 1=1 ";
				//根據上傳的條件種類決定
				if(queryType.equals("1")){
					qLidn = Common.get(s[0]);
					sql_txt += " and LIDN = " + Common.sqlChar(qLidn);
				}else{
					qLnam = Common.get(s[0]);
					sql_txt += " and LNAM = " + Common.sqlChar(qLnam);
				}
				sql_txt += " order by LIDN, cty, unit ";
			
//System.out.println("sql_txt="+sql_txt);
				rs_txt = db.querySQL(Common.Ms950ToIso(sql_txt));
				while(rs_txt.next()){
Long start = System.currentTimeMillis();
					String lidn = rs_txt.getString("lidn");
					String lnam = rs_txt.getString("lnam");
					String cty = rs_txt.getString("cty");
					String unit = rs_txt.getString("unit");
					
					if(nowCNT==1 && checkRow==1){//第1個loop才要做insert到BS的LOG檔
						Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", "", "", "", "", "", "", lidn, Common.isoToBig5(lnam), "", "", "", "", qry_seq, "2");
					}
					
					String sql_ow = "select "
							+ " re.cty, re.unit, re.ed48, re.ed49, re.ee09, re.ee06, re.ee07, re.ee05, re.ee15_1, re.ee15_2, re.ee15_3, "
							+ " rk06.kcnt as ch_ee06, "
							+ " rk15.kcnt as cht_ee15 "
							+ " from REBOW RE "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='06' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk06 "  /*人檔資料*/
							+ " on rk06.KCDE_2 =  re.ee06 "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='15' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk15 "  /*人檔資料*/
							+ " on rk15.KCDE_2 =  re.ee15_1 "
							+ " where 1=1 "
							+ " and re.ee09 = " + Common.sqlChar(lidn)  /*人檔資料*/
							+ " and re.cty = " + Common.sqlChar(cty)  /*人檔資料*/
							+ " and re.unit = " + Common.sqlChar(unit)  /*人檔資料*/
							+ " order by re.cty, re.unit, re.ed48, re.ed49, re.ee01";
//System.out.println("sql_ow="+sql_ow);
					rs_ow = db.querySQL(sql_ow);
					while(rs_ow.next()){
						String sql_id = " select "
								+ " rd.dd45 as aa45, rd.dd46 as aa46, rd.dd48 as aa48, rd.dd49 as aa49, rd.dd09, rd.cty, rd.unit, "
								+ " (select max(kcnt) from rkeyn where kcde_1='45' and kcde_2=rd.dd45 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_cty, "  /*人檔資料*/
								+ " (select max(kcnt) from rkeyn where kcde_1='46' and kcde_2=rd.dd46 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa46, " /*人檔資料*/ 
								+ " (select max(kcnt) from rkeyn where kcde_1='48' and kcde_2=rd.dd48 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa48 "  /*人檔資料*/ 
								+ " FROM RDBID RD "
								+ " where 1=1 "
								+ " and rd.dd48 = " + Common.sqlChar(rs_ow.getString("ed48"))
								+ " and rd.dd49 = " + Common.sqlChar(rs_ow.getString("ed49"))
								+ " and rd.cty = " + Common.sqlChar(cty)
								+ " and rd.unit = " + Common.sqlChar(unit);
//System.out.println("sql_id="+sql_id);
						rs = db.querySQL(sql_id);
						while (rs.next()) {
							dataExist = true;
							Vector data = new Vector();
							data.add(Common.get(lidn));
							data.add(Common.isoToMS950(lnam));
							data.add(Common.get(rs.getString("aa45")));// 3
							data.add(Common.isoToMS950(rs.getString("ch_cty")));
							data.add(Common.get(rs.getString("aa46")));
							data.add(Common.isoToMS950(rs.getString("ch_aa46")));
							data.add(Common.get(rs.getString("aa48")));
							data.add(Common.isoToMS950(rs.getString("ch_aa48")));
							data.add(Common.get(rs_ow.getString("ed49").substring(0, 5)));
							data.add(Common.get(rs_ow.getString("ed49").substring(5)));
							data.add(Common.get(rs_ow.getString("ee06")));
							data.add(Common.isoToMS950(rs_ow.getString("ch_ee06")));
							data.add(Common.get(rs_ow.getString("ee07")));
							data.add(Common.get(rs_ow.getString("ee05")));
							data.add(Common.get(rs_ow.getString("ee15_1")));
							data.add(Common.isoToMS950(rs_ow.getString("cht_ee15")));
							data.add(Common.get(rs_ow.getString("ee15_2")));
							data.add(Common.get(rs_ow.getString("ee15_3")));
							data.add(Common.isoToMS950(rs.getString("dd09")));
							data.add(Common.get(s_ym));//18  比較基準日期
							
							if("Y".equals(getCheck_RHD10())){
								String[] arryRHD10=getRHD10List(rs_ow.getString("ed48"),rs_ow.getString("ed49"),cty,unit);
	                            data.add(arryRHD10[0].replaceAll(",", ";"));
	                            data.add(arryRHD10[1].replaceAll(",", ";"));
							}

							rowData.add(data);
						}
						rs.getStatement().close();
						rs.close();
					}
					rs_ow.getStatement().close();
					rs_ow.close();
					
System.out.println("LCAAP140F建物-->>"+nowCNT+"-"+checkRow+". query: lidn=" + lidn + ", cty=" + cty + ", unit=" + unit + ", time=" + (System.currentTimeMillis() - start));
checkRow++;
				}
				rs_txt.getStatement().close();
				rs_txt.close();
				
				if(!dataExist){
					Vector data = new Vector();
					data=new Vector();
					data.addElement(qLidn);
					data.addElement(qLnam);
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("無符合條件");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement(s_ym);
					if("Y".equals(getCheck_RHD10())){
						columns.addElement("");
						columns.addElement("");
					}
					rowData.add(data);
					SQLContainer.add("insert into ND_LOG (NQRY_SEQ,NQRY_ID,NQRY_NAME,NQRY_YM)values("+Common.sqlChar(this.getQry_seq())+","+Common.sql2Char(qLidn)+","+Common.sql2Char(qLnam)+","+Common.sql2Char(s_ym)+")");
				}
			}
			model.setDataVector(rowData, columns);
			
			if(SQLContainer != null){
				db_sr.excuteSQL(SQLContainer);//將查無資料的人檔資料寫入ND_LOG
			}
			
			if(nowCNT==loopCNT){//最後一個loop執行結束才要做update到BS的LOG檔
				Common.updateBS_LOG(Datetime.getYYYMMDD(), Datetime.getFHHMMSS(),"已完成查詢", qry_seq);
			}
			this.setState("init");
		} catch (Exception e) {
			Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			e.printStackTrace();
		} finally {
			try { if (rs_txt!=null){ rs_txt.close(); rs_txt=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs_ow!=null){ rs_ow.close(); rs_ow=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
			db.closeAll();
			db_sr.closeAll();
		}
		return model;
	}
	

	//建物PDF
	public DefaultTableModel getBuildResultModel() throws Exception {
		int checkRow=1;
		con = "勞保局建物歸戶清冊";
		qry = "市縣市=;事務所=;統一編號=;姓名=";
		qry += ";統編批次=" + this.getItemPicture1() + ";姓名批次=" + this.getItemPicture2();
		
		if(nowCNT==1){//第1個loop才要做insert到DL的LOG檔
			Common.insertDL_LOG(qry_seq, "K", this.getUserID(), this.getUnitID(), qry, this.getPrint_type(), qry_date_start, qry_time_start);
		}
		
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		ODatabase db = new ODatabase();
		ResultSet rs_txt = null;
		ResultSet rs_ow = null;
		ResultSet rs = null;
		
		Database db_sr = new Database();
		Vector SQLContainer=new Vector();
		
		try {
			Vector rowData = new Vector();
			//將原本透過使用者勾選的資料，改成從上傳的文字檔全數直接至rlnid搜尋後全數取出
			for(String s[] : txtData){
				boolean dataExist = false;
				
				String qLidn = "";  //查詢統一編號
				String qLnam = "";  //查詢名稱
				String s_ym = Common.get(s[1]);  //比較基準日期
				String sql_txt = " select lidn, lnam, cty, unit from rlnid "
						+ " where 1=1 ";
				//根據上傳的條件種類決定
				if(queryType.equals("1")){
					qLidn = Common.get(s[0]);
					sql_txt += " and LIDN = " + Common.sqlChar(qLidn);
				}else{
					qLnam = Common.get(s[0]);
					sql_txt += " and LNAM = " + Common.sqlChar(qLnam);
				}
				sql_txt += " order by LIDN, cty, unit ";

//System.out.println("sql_txt="+sql_txt);
				rs_txt = db.querySQL(Common.Ms950ToIso(sql_txt));
				while(rs_txt.next()){
Long start = System.currentTimeMillis();
					String lidn = rs_txt.getString("lidn");
					String lnam = rs_txt.getString("lnam");
					String cty = rs_txt.getString("cty");
					String unit = rs_txt.getString("unit");
					
					if(nowCNT==1 && checkRow==1){//第1個loop才要做insert到BS的LOG檔
						Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", "", "", "", "", "", "", lidn, Common.isoToBig5(lnam), "", "", "", "", qry_seq, "2");
					}
					
					String sql_ow = "select "
							+ " re.cty, re.unit, re.ed48, re.ed49, re.ee09, re.ee06, re.ee07, re.ee05, re.ee15_1, re.ee15_2, re.ee15_3, "
							+ " rk06.kcnt as ch_ee06, "
							+ " rk15.kcnt as cht_ee15 "
							+ " from REBOW RE "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='06' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk06 "  /*人檔資料*/
							+ " on rk06.KCDE_2 =  re.ee06 "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='15' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk15 "  /*人檔資料*/
							+ " on rk15.KCDE_2 =  re.ee15_1 "
							+ " where 1=1 "
							+ " and re.ee09 = " + Common.sqlChar(lidn)  /*人檔資料*/
							+ " and re.cty = " + Common.sqlChar(cty)  /*人檔資料*/
							+ " and re.unit = " + Common.sqlChar(unit)  /*人檔資料*/
							+ " order by re.cty, re.unit, re.ed48, re.ed49, re.ee01";
//System.out.println("sql_ow="+sql_ow);
					rs_ow = db.querySQL(sql_ow);
					while(rs_ow.next()){
						String sql_id = " select "
								+ " rd.dd45 as aa45, rd.dd46 as aa46, rd.dd48 as aa48, rd.dd49 as aa49, rd.dd09, rd.cty, rd.unit, "
								+ " (select max(kcnt) from rkeyn where kcde_1='45' and kcde_2=rd.dd45 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_cty, "  /*人檔資料*/
								+ " (select max(kcnt) from rkeyn where kcde_1='46' and kcde_2=rd.dd46 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa46, " /*人檔資料*/ 
								+ " (select max(kcnt) from rkeyn where kcde_1='48' and kcde_2=rd.dd48 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa48 "  /*人檔資料*/ 
								+ " FROM RDBID RD "
								+ " where 1=1 "
								+ " and rd.dd48 = " + Common.sqlChar(rs_ow.getString("ed48"))
								+ " and rd.dd49 = " + Common.sqlChar(rs_ow.getString("ed49"))
								+ " and rd.cty = " + Common.sqlChar(cty)
								+ " and rd.unit = " + Common.sqlChar(unit);
//System.out.println("sql_id="+sql_id);
						rs = db.querySQL(sql_id);
						while (rs.next()) {
							dataExist = true;
							Vector data = new Vector();
							data.add(Common.get(lidn));
							data.add(Common.isoToMS950(lnam));
							data.add(Common.isoToMS950(rs.getString("ch_cty")));
							data.add(Common.isoToMS950(rs.getString("ch_aa46")));
							data.add(Common.isoToMS950(rs.getString("ch_aa48")));
							data.add(Common.get(rs_ow.getString("ed49").substring(0, 5)));
							data.add(Common.get(rs_ow.getString("ed49").substring(5)));
						    data.add(Common.isoToMS950(rs_ow.getString("ch_ee06")));
							data.add(Common.get(rs_ow.getString("ee07")));
							data.add(Common.get(rs_ow.getString("ee05")));
							if (Common.get(rs_ow.getString("ee15_1")).equals("")) {
								data.add(Common.get(rs_ow.getString("ee15_2")) + " 分之 "+ Common.get(rs_ow.getString("ee15_3")));
								data.add(Common.isoToMS950(rs.getString("dd09")));
							} else {
								data.add(Common.isoToMS950(rs_ow.getString("cht_ee15")) + Common.get(rs_ow.getString("ee15_2")) + " 分之 " + Common.get(rs_ow.getString("ee15_3")));
								data.add(Common.isoToMS950(rs.getString("dd09")));
							}
							data.add(Common.get(s_ym));  //比較基準日期
							
							if("Y".equals(getCheck_RHD10())){
								data.add(getRHD10List(rs_ow.getString("ed48"),rs_ow.getString("ed49"),cty,unit)[1]);
							}
							
							rowData.add(data);
						}
						rs.getStatement().close();
						rs.close();
					}
					rs_ow.getStatement().close();
					rs_ow.close();
					
System.out.println("LCAAP140F建物-->>"+nowCNT+"-"+checkRow+". query: lidn=" + lidn + ", cty=" + cty + ", unit=" + unit + ", time=" + (System.currentTimeMillis() - start));
checkRow++;

				}
				rs_txt.getStatement().close();
				rs_txt.close();
				
				if(!dataExist){
					Vector data = new Vector();
					data.addElement(qLidn);
					data.addElement(qLnam);
					data.addElement("");
					data.addElement("");
					data.addElement("無符合條件");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement(s_ym);
					if("Y".equals(getCheck_RHD10())){
						data.addElement("");
					}
					rowData.add(data);
					SQLContainer.add("insert into ND_LOG (NQRY_SEQ,NQRY_ID,NQRY_NAME,NQRY_YM)values("+Common.sqlChar(this.getQry_seq())+","+Common.sql2Char(qLidn)+","+Common.sql2Char(qLnam)+","+Common.sql2Char(s_ym)+")");
				}
			}
			model.setDataVector(rowData, columns);
			
			if(SQLContainer != null){
				db_sr.excuteSQL(SQLContainer);//將查無資料的人檔資料寫入ND_LOG
			}
			
			if(nowCNT==loopCNT){//最後一個loop執行結束才要做update到BS的LOG檔
				Common.updateBS_LOG(Datetime.getYYYMMDD(), Datetime.getFHHMMSS(),"已完成查詢", qry_seq);
			}
			this.setState("init");
		} catch (Exception e) {
			Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			e.printStackTrace();
		} finally {
			try { if (rs_txt!=null){ rs_txt.close(); rs_txt=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs_ow!=null){ rs_ow.close(); rs_ow=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
			db.closeAll();
			db_sr.closeAll();
		}
		return model;
	}


public boolean genNoDataReport(){
	boolean retBol=true;
	Database db = new Database();
	//Vector rowData=new Vector();
	try {
		String[] arr=new String[3];
		ResultSet rs =db.querySQL("select * from ND_LOG where NQRY_SEQ="+Common.sqlChar(this.getQry_seq()));
		while(rs.next()){
			arr = new String[3];
			arr[0]=Common.get(rs.getString("NQRY_ID"));
			arr[1]=Common.get(rs.getString("NQRY_NAME"));
			arr[2]=Common.get(rs.getString("NQRY_YM"));
			txtNoDataRe.add(arr);
		}
		rs.getStatement().close();
		rs.close();
		int NDLoopCNT=0;
		int NDNowCNT=0;
		if(txtNoDataRe.size()>0){
			NDLoopCNT=txtNoDataRe.size()/50000;//每50000個權利人就切成一個loop
			if(txtNoDataRe.size()%50000>0){//有餘數就需將loop數+1,ex:604231筆資料就會有2個loop
				NDLoopCNT+=1;
			}
			
			int NDmin=0;//當次loop權利人的起始筆數
			int NDmax=0;//當次loop權利人的最後一筆筆數
			for(NDNowCNT=1;NDNowCNT<=NDLoopCNT;NDNowCNT++){
				if(NDNowCNT==NDLoopCNT){
					NDmax=txtNoDataRe.size();
				}else{
					NDmax=NDNowCNT*50000;
				}
				txtNoData.clear();
				for(int i=NDmin;i<NDmax;i++){
					txtNoData.add(txtNoDataRe.get(i));
				}

				
				if(getPrint_kind().equals("1")){
					genNoDataLandReport(NDNowCNT,NDLoopCNT);  //土地
				}else{
					genNoDataBuildReport(NDNowCNT,NDLoopCNT);  //建物
				}
				NDmin=NDmax;
			}
			
		}
		db.exeSQL("delete from ND_LOG where NQRY_SEQ="+Common.sqlChar(this.getQry_seq()));//產製查無資料的報表後要將暫存資料刪除
	}catch (Exception x) {
		x.printStackTrace();
		retBol=false;
	}finally{
		db.closeAll();
	}
	return retBol;
}
	
	//土地(無資料報表)
public boolean genNoDataLandReport(int sMinloop,int sMaxloop){
	boolean retBol=true;
	TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	Vector rowData=new Vector();//這裡宣告是為了重新塞table內容
	Database db = new Database();
	try {
		//ResultSet rs =db.querySQL("select * from ND_LOG where NQRY_SEQ="+Common.sqlChar(this.getQry_seq()));
		//while(rs.next()){
		for(String s[] : txtNoData){
			if (this.getPrint_type().equals("3")) {
				Vector data = new Vector();
				data.addElement(Common.get(s[0]));
				data.addElement(Common.get(s[1]));
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("無符合條件");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement(Common.get(s[2]));
				rowData.add(data);
			}else{
				Vector data = new Vector();
				data.addElement(Common.get(s[0]));
				data.addElement(Common.get(s[1]));
				data.addElement("");
				data.addElement("");
				data.addElement("無符合條件");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement(Common.get(s[2]));
				rowData.add(data);
			}
		}
		model.setDataVector(rowData, columns);
			
		env.setTableModel(model);
		if (this.getPrint_type().equals("2")) {
			env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca182.jasper"));
			env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
		}
		if (this.getPrint_type().equals("3")) {
			env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca181.jasper"));
			env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
		}
			//Database sdb = new Database();
		String username = db.getLookupField("select user_name from etecuser where user_id=" + Common.sqlChar(this.getUserID()));
		HashMap parms = new HashMap();
		parms.put("print_date", "製表日期："+print_date);			
		parms.put("print_count", "筆數："	+ String.valueOf(model.getRowCount()));
		parms.put("printer_usr", username);
		parms.put("printer_unit", this.getUnit());
		TableModelReportGenerator generator = new TableModelReportGenerator(env);
			
		File tempDirectory = new File(reportLocation, vid);
		tempDirectory.mkdirs();
		java.io.File outputFile;
		
		if(sMinloop==sMaxloop && sMinloop==1){
			outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq() + "[NoData]"+fileType);
		}else{
			outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq() + "[NoData]("+sMinloop+")"+fileType);
		}
		
		if (this.getPrint_type().equals("2")) {
			generator.reportToFile(outputFile, parms);
		} else if (this.getPrint_type().equals("3")) {
			generator.reportToFile(outputFile, parms, "MS950");
		}
	} catch (Exception x) {
		x.printStackTrace();
		retBol=false;
	}finally{
		db.closeAll();
	}
	return retBol;
}
	
	
	
//建物(無資料報表)
public boolean genNoDataBuildReport(int sMinloop,int sMaxloop){
	boolean retBol=true;
	TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	//Vector columns=new Vector();//這裡宣告是為了重新塞table表頭
	Vector rowData=new Vector();//這裡宣告是為了重新塞table內容
	Database db = new Database();
	try {
		//ResultSet rs =db.querySQL("select * from ND_LOG where NQRY_SEQ="+Common.sqlChar(this.getQry_seq()));
		//while(rs.next()){
		for(String s[] : txtNoData){
			if (this.getPrint_type().equals("3")) {
				Vector data = new Vector();
				data.addElement(Common.get(s[0]));
				data.addElement(Common.get(s[1]));
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("無符合條件");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement(Common.get(s[2]));
				rowData.add(data);
			}else{						
				Vector data = new Vector();
				data.addElement(Common.get(s[0]));
				data.addElement(Common.get(s[1]));
				data.addElement("");
				data.addElement("");
				data.addElement("無符合條件");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement(Common.get(s[2]));
				rowData.add(data);
				}
			}
			model.setDataVector(rowData, columns);
				
			env.setTableModel(model);
			if (this.getPrint_type().equals("2")) {
				env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca184.jasper"));
				env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
			}
			if (this.getPrint_type().equals("3")) {
				env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca183.jasper"));
				env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
			}
			String username = db.getLookupField("select user_name from etecuser where user_id=" + Common.sqlChar(this.getUserID()));
			HashMap parms = new HashMap();
			parms.put("print_date", "製表日期："+print_date);
			parms.put("print_count", "筆數："	+ String.valueOf(model.getRowCount()));
			parms.put("printer_usr", username);
			parms.put("printer_unit", this.getUnit());
			TableModelReportGenerator generator = new TableModelReportGenerator(env);
			File tempDirectory = new File(reportLocation, vid);
			tempDirectory.mkdirs();
			java.io.File outputFile;
			
			if(sMinloop==sMaxloop && sMinloop==1){
				outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq() + "(NoData)"+fileType);
			}else{
				outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq() + "[NoData]("+sMinloop+")"+fileType);
			}
			
			
			if(this.getPrint_type().equals("2")){
				generator.reportToFile(outputFile, parms);
			}else if (this.getPrint_type().equals("3")){
				generator.reportToFile(outputFile, parms, "MS950");
			}
	} catch (Exception x) {
		x.printStackTrace();
		retBol=false;
	}finally{
		db.closeAll();
	}
	return retBol;
}

	public String getUnit() throws Exception {
		String printer_unit = "";
		Database odb = new Database();
		try {
			String country = odb
					.getLookupField("select kcnt from rkeyn where kcde_1='45' and kcde_2="
							+ Common.sqlChar(this.getUnitID().substring(0, 1)));
			String cht_unit = odb
					.getLookupField("select kcnt from rkeyn where kcde_1='55' and kcde_2='01' and krmk="
							+ Common.sqlChar(this.getUnitID()));
			printer_unit = country + " " + cht_unit;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			odb.closeAll();
		}
		return printer_unit;
	}
	
	/**
     * 目的:回傳建物所在的地號
     * @return String arry
     * @throws Exception
     */
    public String[] getRHD10List(String HD48,String HD49,String CTY,String UNIT) throws Exception {
        ODatabase db = new ODatabase();
        String ret[]=new String[2];
        ret[0]="";//格式:段代碼+地號
        ret[1]="";//格式:段代碼中文+地號格式化
        int count=0;
        try {
        	String sql = " select a.HA48,a.HA49 ,b.kcnt,substr(a.HA49,1,4)||'-'||substr(a.HA49,5,4)as HA49C "
        		+ " from RHD10 a left join RKEYN b on a.CTY=b.CTY and a.UNIT=b.UNIT and b.KCDE_1='48' and b.Kcde_2=a.ha48 "
        		+ " where 1=1 "
        		+ " and a.HD48 = "+Common.sqlChar(Common.get(HD48))
        		+ " and a.HD49 = "+Common.sqlChar(Common.get(HD49))
        		+ " and a.CTY = "+Common.sqlChar(Common.get(CTY))
        		+ " and a.UNIT = "+Common.sqlChar(Common.get(UNIT))
        		+ " order by a.HA48,a.HA49 "
        		+ "";
        	ResultSet rs=db.querySQL(sql);
        	while(rs.next()){
        		if(count>0){
        			ret[0]+=",";
        			ret[1]+=",";
        		}
        		ret[0]+=rs.getString("HA48")+rs.getString("HA49");
        		ret[1]+=Common.isoToMS950(rs.getString("kcnt"))+rs.getString("HA49C");
        		count++;
        	}
        	rs.getStatement().close();
        	rs.close();
        } catch (Exception x) {
            x.printStackTrace();
        } finally {
            db.closeAll();
        }
        return ret;
    }// method getResultModel end
}
