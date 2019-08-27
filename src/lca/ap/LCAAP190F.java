package lca.ap;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.ServletContext;

import util.Common;
import util.Database;
import util.Datetime;
import util.ODatabase;
import util.QueryBean;
import util.report.ReportEnvironment;
import util.report.TableModelReportEnvironment;
import util.report.TableModelReportGenerator;

public class LCAAP190F extends QueryBean {

    private ServletContext context;
    
    private String print_kind; // 報表種類
    //private String print_type; // 報表格式
    private String itemPicture1; // 統一編號多筆
    private String imp_kind;

    private String filestoreLocation;
    private String reportLocation;
    private String uip;
    private String qry_date_start;
    private String qry_time_start;
    private String qry_date_end;
    private String qry_time_end;
    private String qry;
    private String dqry;
    private String qry_msg;
    private String con;
    private String qry_seq;
    private String check_RHD10;//106增修案
    
	public String getImp_kind() {	return checkGet(imp_kind);}
	public void setImp_kind(String s) {this.imp_kind = checkSet(s);}
	
	public String getPrint_kind() {return checkGet(print_kind);}
    public void setPrint_kind(String s) {this.print_kind = checkSet(s);}


    public String getItemPicture1() {return checkGet(itemPicture1);}
    public void setItemPicture1(String s) {this.itemPicture1 = checkSet(s);}

    public String getFilestoreLocation() {return checkGet(filestoreLocation);}
    public void setFilestoreLocation(String s) {this.filestoreLocation = checkSet(s);}

    public String getReportLocation() {return checkGet(reportLocation);}
    public void setReportLocation(String s) {this.reportLocation = checkSet(s);}

    public String getUip() {return checkGet(uip);}
    public void setUip(String s) {this.uip = checkSet(s);}

    public String getDqry() {return checkGet(dqry);}
    public void setDqry(String s) {this.dqry = checkSet(s);}

    public String getQry() {return checkGet(qry);}
    public void setQry(String s) {this.qry = checkSet(s);}

    public String getQry_date_end() {return checkGet(qry_date_end);}
    public void setQry_date_end(String s) {this.qry_date_end = checkSet(s);}

    public String getQry_date_start() {return checkGet(qry_date_start);}
    public void setQry_date_start(String s) {this.qry_date_start = checkSet(s);}

    public String getQry_msg() {return checkGet(qry_msg);}
    public void setQry_msg(String s) {this.qry_msg = checkSet(s);}

    public String getQry_time_end() {return checkGet(qry_time_end);}
    public void setQry_time_end(String s) {this.qry_time_end = checkSet(s);}

    public String getQry_time_start() {return checkGet(qry_time_start);}
    public void setQry_time_start(String s) {this.qry_time_start = checkSet(s);}

    public String getCon() {return checkGet(con);}
    public void setCon(String s) {this.con = checkSet(s);}

    public String getQry_seq() {return checkGet(qry_seq);}
    public void setQry_seq(String s) {this.qry_seq = checkSet(s);}

    public ServletContext getContext() {return context;}
    public void setContext(ServletContext s) {this.context = s;}
	
    
    private ArrayList<String []> txtDataRe = new ArrayList<String []>();
    private ArrayList<String []> txtData = new ArrayList<String []>();
    
    ArrayList<String []> txtNoData = new ArrayList<String []>();
	ArrayList<String []> txtNoDataRe = new ArrayList<String []>();
    
	int loopCNT=0;//以1000筆人檔為1個for loop
	int nowCNT=0;//目前正在執行第幾個loop
    Vector columns = new Vector();//設定報表的表頭
    String print_date;//報表上的列印時間採用執行第一個loop時的時間
    String vid ="";//檔案生成的資料夾路徑
    String fileType="";//報表檔案類別
    
    int listNo=0;
    int hasDatalistNo=0;
    int NoDatalistNo=0;
    
    /**
	 * 目地:將文字檔寫入EFORM7
	 * 處理:
	 *    getImp_kind()=1 >> 重新匯入:先將EFORM7刪除,再將文字檔內窩全部寫入EFORM7
	 *    getImp_kind()=2 >> 更新匯入:該統編(姓名)資EFORM7中無資料才寫入
	 * @return
	 */
	public boolean ModifyEFORM7(){
		boolean ret=false;
		Database db = new Database();
		
		String IMP_YMD=util.Datetime.getYYYMMDD();
		String IMP_TIME=util.Datetime.getHHMMSS();
		
		try {
			
			java.util.Map<String, String> MapEFORM7 = new java.util.HashMap<String, String>();
			ArrayList<String []> txtDataImp = new ArrayList<String []>();
			txtDataImp = Common.getFileArray(filestoreLocation, itemPicture1, 1);
			
			if("1".equals(getImp_kind())){
				db.exeSQL("delete from EFORM7 where TQRY_TYPE='3'");
			}else if("2".equals(getImp_kind())){//更新匯入
				String checkSQL="select TQRY_ID from EFORM7 where TQRY_TYPE='3'";
				ResultSet rs=db.querySQL(checkSQL);
				while(rs.next()){
					MapEFORM7.put(rs.getString(1),"Y");
				}
				rs.getStatement().close();
				rs.close();
			}
			
			Vector vImpTxtSQL=new Vector();
			
			boolean insBol=true;
			if(txtDataImp.size()>0){
				int i=0;
				for(String s[] : txtDataImp){
					System.out.println(++i+":"+s[0]);

					if("1".equals(getImp_kind())){//重新匯入
						vImpTxtSQL.add("insert into EFORM7(TQRY_TYPE,TQRY_ID,IMP_TYPE,IMP_YMD,IMP_TIME)values('3',"+Common.sqlChar(s[0])+","+Common.sqlChar("1")+","+Common.sqlChar(IMP_YMD)+","+Common.sqlChar(IMP_TIME)+")");
					}else if("2".equals(getImp_kind())){//更新匯入
						if(!"Y".equals(Common.get(MapEFORM7.get(s[0])))){
							vImpTxtSQL.add("insert into EFORM7(TQRY_TYPE,TQRY_ID,IMP_TYPE,IMP_YMD,IMP_TIME)values('3',"+Common.sqlChar(s[0])+","+Common.sqlChar("2")+","+Common.sqlChar(IMP_YMD)+","+Common.sqlChar(IMP_TIME)+")");
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
        // 先將文字檔的資料取出
        //System.out.println("### A:" + filestoreLocation+"### B:" + itemPicture1);
        txtDataRe = EFORM7Array();//Common.getFileArray(filestoreLocation, itemPicture1, 1);
        
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
	            genLandReport(); // 土地
	        }else{
	            genBuildReport(); // 建物
	        }
	        
	        min=max;
		}
		if(loopCNT>0){
			genNoDataReport();//最後執行查無資料的報表產製
		}
    }
    
    
    public ArrayList<String []> EFORM7Array(){
    	Database db = new Database();
    	ArrayList<String []> reList = new ArrayList<String []>();
    	String temp[] = null;
    	try {
    		String sql= " select TQRY_ID from EFORM7 where TQRY_TYPE = '3' order by TQRY_ID";//國軍退撫會統編比對
    		setErrorMsg("無統一編號資料可進行比對,請先匯入統一編號文字檔!");
    		ResultSet rs = db.querySQL(sql);
    		while(rs.next()){
    			temp = new String[2];
    			temp[0] = Common.get(rs.getString(1));
    			reList.add(temp);
    			setErrorMsg("");
    		}
    		rs.getStatement().close();
    		rs.close();
    	} catch (Exception e) {
    		setErrorMsg("請先選擇統一編號比對!");
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
			qry_seq = "M" + qry_date_start + qry_time_start;
			this.setQry_seq(qry_seq);
		}else{//建物
			qry_seq = "N" + qry_date_start + qry_time_start;
			this.setQry_seq(qry_seq);
		}
		
		//從105年開始只產製txt檔
		fileType=".txt";
		
		if (this.getPrint_kind().equals("1")) {//土地文字檔	
			 columns.addElement("f1");
             columns.addElement("f2");
             columns.addElement("f3");
             columns.addElement("f4");
             columns.addElement("f5");
             columns.addElement("f6");
             columns.addElement("f7");
             columns.addElement("f8");
             columns.addElement("f9");
             columns.addElement("f10");
             columns.addElement("f11");
             columns.addElement("f12");
             columns.addElement("f13");
             columns.addElement("f14");
             columns.addElement("f15");
             columns.addElement("f16");
             columns.addElement("f17");
             columns.addElement("f18");
             columns.addElement("f19");
             columns.addElement("f20");
             columns.addElement("f21");
             columns.addElement("f22");
             columns.addElement("f23");
             columns.addElement("f24");
		}else if (this.getPrint_kind().equals("2")) {//建物文字檔
			columns.addElement("f1");
            columns.addElement("f2");
            columns.addElement("f3");
            columns.addElement("f4");
            columns.addElement("f5");
            columns.addElement("f6");
            columns.addElement("f7");
            columns.addElement("f8");
            columns.addElement("f9");
            columns.addElement("f10");
            columns.addElement("f11");
            columns.addElement("f12");
            columns.addElement("f13");
            columns.addElement("f14");
            columns.addElement("f15");
            columns.addElement("f16");
            columns.addElement("f17");
            columns.addElement("f18");
            if("Y".equals(getCheck_RHD10())){
				columns.addElement("HD4849");//目前19
				columns.addElement("HD4849C");
			}
		}	
	}

//土地
public void genLandReport() {
	TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	Vector rowData = new Vector();// 這裡宣告是為了重新塞table內容
	Database sdb = new Database();
	try{
		rowData = getLandCSVResultModel();
		env.setTableModel(model);
		env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca191.jasper"));
		env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
		String username = sdb.getLookupField("select user_name from etecuser where user_id="+ Common.sqlChar(this.getUserID()));
		HashMap parms = new HashMap();
		parms.put("print_date", "製表日期："+print_date);
		parms.put("print_count", "筆數："+ String.valueOf(rowData.size()));
		model.setDataVector(rowData, columns);
		parms.put("printer_usr", username);
		parms.put("printer_unit", this.getUnit());
		TableModelReportGenerator generator = new TableModelReportGenerator(env);
		File tempDirectory = new File(reportLocation, vid);
		tempDirectory.mkdirs();
		java.io.File outputFile;
		
		if(nowCNT==loopCNT && nowCNT==1){//產出檔案時,若cowCNT=1且nowCNT=loopCN代表只有一個檔案
			outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq() + fileType);
		}else{//產出檔案時有多個檔案需在檔名後加上(X)
			outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq()+"("+nowCNT+")" + fileType);
		}
		
        generator.reportToFile(outputFile, parms, "MS950");
        Common.updateDL_LOG("2", vid + ":;:"+ this.getQry_seq() + fileType, Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
	}catch(Exception x){
		Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
		x.printStackTrace();
	}finally{
		sdb.closeAll();
	}
}
    



public void genBuildReport() {
	TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	Vector rowData = new Vector();// 這裡宣告是為了重新塞table內容
	Database sdb = new Database();
	try{
		rowData = getBuildCSVResultModel();
		env.setTableModel(model);
		if("Y".equals(getCheck_RHD10())){
			env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca192_2.jasper"));
		}else{
			env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca192.jasper"));
		}
		
		env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
		String username = sdb.getLookupField("select user_name from etecuser where user_id="+ Common.sqlChar(this.getUserID()));

		HashMap parms = new HashMap();
		parms.put("print_date", "製表日期："+print_date);
		parms.put("print_count", "筆數："+ String.valueOf(rowData.size()));
		model.setDataVector(rowData, columns);
		parms.put("printer_usr", username);
		parms.put("printer_unit", this.getUnit());
		TableModelReportGenerator generator = new TableModelReportGenerator(env);
		File tempDirectory = new File(reportLocation, vid);
		tempDirectory.mkdirs();
		java.io.File outputFile;
		
		if(nowCNT==loopCNT && nowCNT==1){//產出檔案時,若cowCNT=1且nowCNT=loopCN代表只有一個檔案
			outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq() + fileType);
		}else{//產出檔案時有多個檔案需在檔名後加上(X)
			outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq()+"("+nowCNT+")" + fileType);
		}
		
		generator.reportToFile(outputFile, parms, "MS950");
		Common.updateDL_LOG("2", vid + ":;:"+ this.getQry_seq() + fileType, Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());

	}catch(Exception x){
		Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
		x.printStackTrace();
	}finally{
		sdb.closeAll();
	}
}
    
    //土地TXT
    public Vector getLandCSVResultModel() throws Exception {
        
        con = "國軍退輔會批次歸戶清冊(土地)";
        qry = "市縣市=;事務所=;統一編號=;姓名=";
        qry += ";統編批次=" + this.getItemPicture1();
        
        if(nowCNT==1){//第1個loop才要做insert到DL的LOG檔
        	//Common.insertDL_LOG(qry_seq, "M", this.getUserID(), this.getUnitID(),qry, this.getPrint_type(), qry_date_start, qry_time_start);
        	Common.insertDL_LOG(qry_seq, "M", this.getUserID(), this.getUnitID(),qry, "3", qry_date_start, qry_time_start);
        }
        
        Connection conn = null;
        Statement stmt_txt = null;
        Statement stmt_ow = null;
        Statement stmt = null;
        Statement stmt_mr = null;
        ResultSet rs_txt = null;
        ResultSet rs_ow = null;
        ResultSet rs = null;
        ResultSet rs_mr = null;

        ODatabase db = new ODatabase();
        Vector rowData = new Vector();
        
        Database db_sr = new Database();
        Vector SQLContainer=new Vector();
        
        try {
            conn = db.getConnection();
            stmt_txt = conn.createStatement();
            stmt_ow = conn.createStatement();
            stmt = conn.createStatement();
            stmt_mr = conn.createStatement();
            //將原本透過使用者勾選的資料，改成從上傳的文字檔全數直接至rlnid搜尋後全數取出
            int ii=0;
            for(String s[] : txtData){
            	++ii;
                String qLidn = s[0]; // 查詢統一編號
                System.out.println(ii+".LCAAP150F土地-->> query: lidn=" + s[0]);
                boolean dataExist = false;
                String sql_txt = 
                        "  select lidn, lnam, cty, unit from rlnid "
                        + " where 1=1 "
                        + "   and LIDN = " + Common.sqlChar(qLidn)
                        + " order by LIDN, cty, unit ";
            
                rs_txt = stmt_txt.executeQuery(sql_txt);
                while(rs_txt.next()){
                    String lidn = rs_txt.getString("lidn");
                    String lnam = rs_txt.getString("lnam");
                    String cty = rs_txt.getString("cty");
                    String unit = rs_txt.getString("unit");
                    
                    if(nowCNT==1){//第1個loop才要做insert到DL的LOG檔
                    	Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", "", "", "", "", "", "", lidn, Common.isoToBig5(lnam), "", "", "", "", qry_seq, "2");
                    }
                    
                    String sql_ow = 
                            "  select rb.cty, rb.unit, rb.ba48, rb.ba49, rb.bb06, rb.bb07, rb.bb05, "
                            + "       rb.bb09, rb.bb15_1, rb.bb15_2, rb.bb15_3, rk06.kcnt as ch_bb06, "
                            + "       rk15.kcnt as cht_bb15, bb21, bb01,"
                            + "       (select krmk from rkeyn where rownum=1 and kcde_1 = '00' and kcde_2 = 'V1' and cty = " + Common.sqlChar(cty) + " and unit = " + Common.sqlChar(unit) + ") as date_bb21 "  
                            + "  from RBLOW RB "
                            + "  left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1 = '06' and cty = " + Common.sqlChar(cty) + " and unit = " + Common.sqlChar(unit) + ") rk06 "  /*人檔資料*/
                            + "    on rk06.KCDE_2 =  rb.bb06 "
                            + "  left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1 = '15' and cty = " + Common.sqlChar(cty) + " and unit = " + Common.sqlChar(unit) + ") rk15 "  /*人檔資料*/
                            + "    on rk15.KCDE_2 =  rb.bb15_1 "
                            + " where 1=1 "
                            + "   and rb.bb09 = " + Common.sqlChar(lidn)  /*人檔資料*/
                            + "   and rb.cty = " + Common.sqlChar(cty)  /*人檔資料*/
                            + "   and rb.unit = " + Common.sqlChar(unit)  /*人檔資料*/
                            + " order by rb.cty, rb.unit, rb.ba48, rb.ba49, rb.bb01 ";
                    
                    //System.out.println("##### sql1:" + sql_ow);

                    rs_ow = stmt_ow.executeQuery(sql_ow);
                    while (rs_ow.next()) {
                        String sql_id = 
                                "  select ra.cty,ra.unit,ra.aa48, ra.aa49, ra.aa45, ra.aa46, "
                                + "       ra.aa10, "
                                + "       ra.aa11, "
                                + "       (select kcnt from rkeyn where rownum=1 and kcde_1 = '11' and kcde_2 = ra.aa11 and cty = " + Common.sqlChar(cty) + " and unit = " + Common.sqlChar(unit) + ") as ch_aa11, "
                                + "       ra.aa12,"
                                + "       (select kcnt from rkeyn where rownum=1 and kcde_1 = '12' and kcde_2 = ra.aa12 and cty = " + Common.sqlChar(cty) + " and unit = " + Common.sqlChar(unit) + ") as ch_aa12, "
                                + "       ra.aa16, "
                                + "       (select kcnt from rkeyn where rownum=1 and kcde_1 = '45' and kcde_2 = ra.aa45 and cty = " + Common.sqlChar(cty) + " and unit = " + Common.sqlChar(unit) + ") as ch_cty, "  /*人檔資料*/
                                + "       (select kcnt from rkeyn where rownum=1 and kcde_1 = '46' and kcde_2 = ra.aa46 and cty = " + Common.sqlChar(cty) + " and unit = " + Common.sqlChar(unit) + ") as ch_aa46, " /*人檔資料*/ 
                                + "       (select kcnt from rkeyn where rownum=1 and kcde_1 = '48' and kcde_2 = ra.aa48 and cty = " + Common.sqlChar(cty) + " and unit = " + Common.sqlChar(unit) + ") as ch_aa48 "  /*人檔資料*/ 
                                + "  FROM RALID RA "
                                + " where 1=1 "
                                + "   and ra.aa48 = " + Common.sqlChar(rs_ow.getString("ba48"))
                                + "   and ra.aa49 = " + Common.sqlChar(rs_ow.getString("ba49"))
                                + "   and ra.cty = " + Common.sqlChar(cty)
                                + "   and ra.unit = " + Common.sqlChar(unit);

                        rs = stmt.executeQuery(sql_id);
                        while (rs.next()) {
                            String sql_mr = 
                                    "   SELECT RL.LIDN, RL.LNAM     "
                                    + "   FROM RMNGR MR, RLNID RL  "
                                    + "  WHERE 1 = 1 "
                                    + "    AND MR.cty = " + Common.sqlChar(cty)
                                    + "    AND MR.unit = " + Common.sqlChar(unit)
                                    + "    AND RL.cty = " + Common.sqlChar(cty)
                                    + "    AND RL.unit = " + Common.sqlChar(unit)
                                    + "    AND MR.MM13 = RL.LIDN     "
                                    + "    AND MR.MM48 = " + Common.sqlChar(rs_ow.getString("ba48"))
                                    + "    AND MR.MM49 = " + Common.sqlChar(rs_ow.getString("ba49"))
                                    + "    AND MR.MM01 = " + Common.sqlChar(rs_ow.getString("bb01"));
                            
                            boolean isMngrExist = false;
                            rs_mr = stmt_mr.executeQuery(sql_mr);
                            while(rs_mr.next()){
                            	dataExist = true;
                                isMngrExist = true;
                                Vector data = new Vector();
                                data.add(Common.get(lidn)); // 0.權利人統編
                                data.add(Common.isoToMS950(lnam)); // 1.權利人姓名
                                data.add(Common.get(rs.getString("aa45"))); // 2.縣市
                                data.add(Common.isoToMS950(rs.getString("ch_cty"))); // 3.縣市-名稱
                                data.add(Common.get(rs.getString("aa46"))); // 4.鄉鎮市區
                                data.add(Common.isoToMS950(rs.getString("ch_aa46"))); // 5.鄉鎮市區-名稱
                                data.add(Common.get(rs.getString("aa48"))); // 6.段小段
                                data.add(Common.isoToMS950(rs.getString("ch_aa48"))); // 7.段小段-名稱
                                data.add(Common.get(rs.getString("aa49"))); // 8.地號
                                data.add(Common.get(rs.getString("aa10"))); // 9.面積
                                data.add(Common.get(rs_ow.getString("bb01"))); // 10.登次
                                data.add(Common.get(rs_ow.getString("bb15_1"))); // 11.權利範圍類別
                                data.add(Common.isoToMS950(rs_ow.getString("cht_bb15"))); // 12.權利範圍類別-名稱
                                data.add(Common.get(rs_ow.getString("bb15_2"))); // 13.權利範圍-分母
                                data.add(Common.get(rs_ow.getString("bb15_3"))); // 14.權利範圍-分子
                                data.add(Common.get(rs.getString("aa11"))); // 15.使用分區
                                data.add(Common.isoToMS950(rs.getString("ch_aa11"))); // 16.使用分區-名稱
                                data.add(Common.get(rs.getString("aa12"))); // 17.使用地類別
                                data.add(Common.isoToMS950(rs.getString("ch_aa12"))); // 18.使用地類別-名稱
                                data.add(Common.isoToMS950(rs.getString("aa16"))); // 19.公告土地現值
                                data.add(Common.get(rs_ow.getString("date_bb21"))); // 20.申報地價日期
                                //System.out.println("1.#####: date_bb21:" + Common.get(rs_ow.getString("date_bb21")));
                                data.add(Common.get(rs_ow.getString("bb21"))); // 21.申報地價
                                data.add(Common.get(rs_mr.getString("lidn"))); // 22.管理者統編
                                data.add(Common.isoToMS950(rs_mr.getString("lnam"))); // 23.管理者姓名
                                
                                rowData.add(data);
                            }
                            closeResultSet(rs_mr);
                            
                            if(!isMngrExist){
                            	dataExist = true;
                                Vector data = new Vector();
                                data.add(Common.get(lidn)); // 0.權利人統編
                                data.add(Common.isoToMS950(lnam)); // 1.權利人姓名
                                data.add(Common.get(rs.getString("aa45"))); // 2.縣市
                                data.add(Common.isoToMS950(rs.getString("ch_cty"))); // 3.縣市-名稱
                                data.add(Common.get(rs.getString("aa46"))); // 4.鄉鎮市區
                                data.add(Common.isoToMS950(rs.getString("ch_aa46"))); // 5.鄉鎮市區-名稱
                                data.add(Common.get(rs.getString("aa48"))); // 6.段小段
                                data.add(Common.isoToMS950(rs.getString("ch_aa48"))); // 7.段小段-名稱
                                data.add(Common.get(rs.getString("aa49"))); // 8.地號
                                data.add(Common.get(rs.getString("aa10"))); // 9.面積
                                data.add(Common.get(rs_ow.getString("bb01"))); // 10.登次
                                data.add(Common.get(rs_ow.getString("bb15_1"))); // 11.權利範圍類別
                                data.add(Common.isoToMS950(rs_ow.getString("cht_bb15"))); // 12.權利範圍類別-名稱
                                data.add(Common.get(rs_ow.getString("bb15_2"))); // 13.權利範圍-分母
                                data.add(Common.get(rs_ow.getString("bb15_3"))); // 14.權利範圍-分子
                                data.add(Common.get(rs.getString("aa11"))); // 15.使用分區
                                data.add(Common.isoToMS950(rs.getString("ch_aa11"))); // 16.使用分區-名稱
                                data.add(Common.get(rs.getString("aa12"))); // 17.使用地類別
                                data.add(Common.isoToMS950(rs.getString("ch_aa12"))); // 18.使用地類別-名稱
                                data.add(Common.isoToMS950(rs.getString("aa16"))); // 19.公告土地現值
                                data.add(Common.get(rs_ow.getString("date_bb21"))); // 20.申報地價日期
                                //System.out.println("1.#####: date_bb21:" + Common.get(rs_ow.getString("date_bb21")));
                                data.add(Common.get(rs_ow.getString("bb21"))); // 21.申報地價
                                data.add(""); // 22.管理者統編
                                data.add(""); // 23.管理者姓名
                                
                                rowData.add(data);
                            }
                        }
                        closeResultSet(rs);
                    }
                    closeResultSet(rs_ow);
                    
                    //System.out.println("LCAAP150F土地-->> query: lidn=" + lidn+ ", cty=" + cty + ", unit=" + unit);
                }
                closeResultSet(rs_txt);
                
                
                //沒有資料的
                if(!dataExist){
                	SQLContainer.add("insert into ND_LOG (NQRY_SEQ,NQRY_ID)values("+Common.sqlChar(this.getQry_seq())+","+Common.sql2Char(s[0])+")");
                }
            }
            
            
            if(SQLContainer != null){
				db_sr.excuteSQL(SQLContainer);//將查無資料的人檔資料寫入ND_LOG
			}
            
            Common.updateBS_LOG(Datetime.getYYYMMDD(), Datetime.getFHHMMSS(), "已完成查詢", qry_seq);
            this.setState("init");
        }
        catch(Exception e){
            Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
            e.printStackTrace();
        }
        finally{
            closeResultSet(rs_mr);
            closeResultSet(rs);
            closeResultSet(rs_ow);
            closeResultSet(rs_txt);
            closeStatement(stmt_mr);
            closeStatement(stmt);
            closeStatement(stmt_ow);
            closeStatement(stmt_txt);
            closeConnection(conn);
            db.closeAll();
            db_sr.closeAll();
        }
        
        return rowData;
    }


	//建物TXT
	public Vector getBuildCSVResultModel() throws Exception {
	    
		con = "國軍退輔會批次歸戶清冊(建物)";
		qry = "市縣市=;事務所=;統一編號=;姓名=";
		qry += ";統編批次=" + this.getItemPicture1();
		
		if(nowCNT==1){//第1個loop才要做insert到DL的LOG檔
			//Common.insertDL_LOG(qry_seq, "N", this.getUserID(), this.getUnitID(),qry, this.getPrint_type(), qry_date_start, qry_time_start);
			Common.insertDL_LOG(qry_seq, "N", this.getUserID(), this.getUnitID(),qry, "3", qry_date_start, qry_time_start);
		}
		
        Connection conn = null;
        Statement stmt_txt = null;
        Statement stmt_ow = null;
        Statement stmt = null;
        Statement stmt_mr = null;
        ResultSet rs_txt = null;
        ResultSet rs_ow = null;
        ResultSet rs = null;
        ResultSet rs_mr = null;

        ODatabase db = new ODatabase();
        Vector rowData = new Vector();
        
        Database db_sr = new Database();
        Vector SQLContainer=new Vector();

        try {
            conn = db.getConnection();
            stmt_txt = conn.createStatement();
            stmt_ow = conn.createStatement();
            stmt = conn.createStatement();
            stmt_mr = conn.createStatement();
			//將原本透過使用者勾選的資料，改成從上傳的文字檔全數直接至rlnid搜尋後全數取出
            
			for(String s[] : txtData){
				System.out.print("讀取筆數:"+ ++listNo);
				boolean dataExist=false;
				String qLidn = s[0];  //查詢統一編號
				String sql_txt = 
				        "  select lidn, lnam, cty, unit from rlnid "
						+ " where 1=1 "
				        + "   and LIDN = " + Common.sqlChar(qLidn)
				        + " order by LIDN, cty, unit ";

				rs_txt = stmt_txt.executeQuery(sql_txt);
				while(rs_txt.next()){
					String lidn = rs_txt.getString("lidn");
					String lnam = rs_txt.getString("lnam");
					String cty = rs_txt.getString("cty");
					String unit = rs_txt.getString("unit");
 
					if(nowCNT==1){//第1個loop才要做insert到DL的LOG檔
						Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", "", "", "", "", "", "", lidn, Common.isoToBig5(lnam), "", "", "", "", qry_seq, "2");
					}
					
					String sql_ow = 
					        "  select re.cty, re.unit, re.ed48, re.ed49, re.ee09, re.ee06, re.ee01,"
					        + "       re.ee07, re.ee05, re.ee15_1, re.ee15_2, re.ee15_3, "
					        + "       rk06.kcnt as ch_ee06, rk15.kcnt as cht_ee15 "
							+ "  from REBOW RE "
							+ "  left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='06' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk06 "  /*人檔資料*/
							+ "    on rk06.KCDE_2 =  re.ee06 "
							+ "  left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='15' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk15 "  /*人檔資料*/
							+ "    on rk15.KCDE_2 =  re.ee15_1 "
							+ " where 1=1 "
							+ "   and re.ee09 = " + Common.sqlChar(lidn)  /*人檔資料*/
							+ "   and re.cty = " + Common.sqlChar(cty)  /*人檔資料*/
							+ "   and re.unit = " + Common.sqlChar(unit)  /*人檔資料*/
							+ " order by re.cty, re.unit, re.ed48, re.ed49, re.ee01";

					rs_ow = stmt_ow.executeQuery(sql_ow);
					while(rs_ow.next()){
						String sql_id = 
						        "  select rd.dd45, rd.dd46, rd.dd48, rd.dd08, "
						        + "       rd.dd49, rd.dd09, rd.cty, rd.unit, "
								+ "       (select kcnt from rkeyn where rownum=1 and kcde_1='45' and kcde_2=rd.dd45 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_cty, "  /*人檔資料*/
								+ "       (select kcnt from rkeyn where rownum=1 and kcde_1='46' and kcde_2=rd.dd46 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_dd46, " /*人檔資料*/ 
								+ "       (select kcnt from rkeyn where rownum=1 and kcde_1='48' and kcde_2=rd.dd48 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_dd48 "  /*人檔資料*/ 
								+ "  FROM RDBID RD "
								+ " where 1=1 "
								+ "   and rd.dd48 = " + Common.sqlChar(rs_ow.getString("ed48"))
								+ "   and rd.dd49 = " + Common.sqlChar(rs_ow.getString("ed49"))
								+ "   and rd.cty = " + Common.sqlChar(cty)
								+ "   and rd.unit = " + Common.sqlChar(unit);

						rs = stmt.executeQuery(sql_id);
						while (rs.next()) {
						    String sql_mr = 
                                    "   SELECT RL.LIDN, RL.LNAM     "
                                    + "   FROM RMNGR MR, RLNID RL  "
                                    + "  WHERE 1 = 1 "
                                    + "    AND MR.cty = " + Common.sqlChar(cty)
                                    + "    AND MR.unit = " + Common.sqlChar(unit)
                                    + "    AND RL.cty = " + Common.sqlChar(cty)
                                    + "    AND RL.unit = " + Common.sqlChar(unit)
                                    + "    AND MR.MM13 = RL.LIDN     "
                                    + "    AND MR.MM48 = " + Common.sqlChar(rs_ow.getString("ed48"))
                                    + "    AND MR.MM49 = " + Common.sqlChar(rs_ow.getString("ed49"))
                                    + "    AND MR.MM01 = " + Common.sqlChar(rs_ow.getString("ee01"));
                            
                            boolean isMngrExist = false;
                            rs_mr = stmt_mr.executeQuery(sql_mr);
                            while(rs_mr.next()){
                            	if(!dataExist){
                                	System.out.print(" 有資料:"+ ++hasDatalistNo);
                                }
                            	dataExist=true;
                                isMngrExist = true;
    							Vector data = new Vector();
    							data.add(Common.get(lidn)); // 1.統編
    							data.add(Common.isoToMS950(lnam)); // 2.姓名
    							data.add(Common.get(rs.getString("dd45")));// 3.縣市代碼
    							data.add(Common.isoToMS950(rs.getString("ch_cty"))); //4.縣市-名稱
    							data.add(Common.get(rs.getString("dd46"))); // 5.鄉鎮市區-代碼
    							data.add(Common.isoToMS950(rs.getString("ch_dd46"))); //6.鄉鎮市區-名稱
    							data.add(Common.get(rs.getString("dd48"))); // 7.段小段-代碼
    							data.add(Common.isoToMS950(rs.getString("ch_dd48"))); // 8.段小段-名稱
    							data.add(Common.get(rs.getString("dd49"))); // 9.建號
    							data.add(Common.isoToMS950(rs.getString("dd09"))); // 10.建物門牌
    							data.add(Common.get(rs.getString("dd08"))); // 11.總面積
    							data.add(Common.get(rs_ow.getString("ee01"))); //12.登記次序 
    							data.add(Common.get(rs_ow.getString("ee15_1"))); //13.權利範圍類別-代碼
    							data.add(Common.isoToMS950(rs_ow.getString("cht_ee15"))); //14.權利範圍類別-名稱
    							data.add(Common.get(rs_ow.getString("ee15_2"))); // 15.權利範圍-分母
    							data.add(Common.get(rs_ow.getString("ee15_3"))); //16.權利範圍-分子
    							data.add(Common.get(rs_mr.getString("lidn"))); //17.管理者統編
                                data.add(Common.isoToMS950(rs_mr.getString("lnam")));//18.管理者姓名

                                if("Y".equals(getCheck_RHD10())){
    								String[] arryRHD10=getRHD10List(rs_ow.getString("ed48"),rs_ow.getString("ed49"),cty,unit);
    	                            data.add(arryRHD10[0].replaceAll(",", ";"));
    	                            data.add(arryRHD10[1].replaceAll(",", ";"));
    							}

    							rowData.add(data);
                            }
                            closeResultSet(rs_mr);   
                            
                            if(!isMngrExist){
                            	if(!dataExist){
                                	System.out.print(" 有資料:"+ ++hasDatalistNo);
                                }
                            	dataExist=true;
                                Vector data = new Vector();
                                data.add(Common.get(lidn)); // 1.統編
                                data.add(Common.isoToMS950(lnam)); // 2.姓名
                                data.add(Common.get(rs.getString("dd45")));// 3.縣市代碼
                                data.add(Common.isoToMS950(rs.getString("ch_cty"))); //4.縣市-名稱
                                data.add(Common.get(rs.getString("dd46"))); // 5.鄉鎮市區-代碼
                                data.add(Common.isoToMS950(rs.getString("ch_dd46"))); //6.鄉鎮市區-名稱
                                data.add(Common.get(rs.getString("dd48"))); // 7.段小段-代碼
                                data.add(Common.isoToMS950(rs.getString("ch_dd48"))); // 8.段小段-名稱
                                data.add(Common.get(rs.getString("dd49"))); // 9.建號
                                data.add(Common.isoToMS950(rs.getString("dd09"))); // 10.建物門牌
                                data.add(Common.get(rs.getString("dd08"))); // 11.總面積
                                data.add(Common.get(rs_ow.getString("ee01"))); //12.登記次序 
                                data.add(Common.get(rs_ow.getString("ee15_1"))); //13.權利範圍類別-代碼
                                data.add(Common.isoToMS950(rs_ow.getString("cht_ee15"))); //14.權利範圍類別-名稱
                                data.add(Common.get(rs_ow.getString("ee15_2"))); // 15.權利範圍-分母
                                data.add(Common.get(rs_ow.getString("ee15_3"))); //16.權利範圍-分子
                                data.add(""); //17.管理者統編
                                data.add(""); //18.管理者姓名
                                
                                if("Y".equals(getCheck_RHD10())){
    								String[] arryRHD10=getRHD10List(rs_ow.getString("ed48"),rs_ow.getString("ed49"),cty,unit);
    	                            data.add(arryRHD10[0].replaceAll(",", ";"));
    	                            data.add(arryRHD10[1].replaceAll(",", ";"));
    							}
                                
                                rowData.add(data);
                            }
                            
						}
						closeResultSet(rs);
					}
					closeResultSet(rs_ow);
					
                    System.out.print(" LCAAP150F建物-->> query: lidn=" + lidn
                            + ", cty=" + cty + ", unit=" + unit);
				}
				closeResultSet(rs_txt);
				
				//沒有資料的
                if(!dataExist){
                	System.out.print("無資料:"+ ++NoDatalistNo+" 應證有資料:"+(listNo-NoDatalistNo));
                	SQLContainer.add("insert into ND_LOG (NQRY_SEQ,NQRY_ID)values("+Common.sqlChar(this.getQry_seq())+","+Common.sql2Char(s[0])+")");
                }
                System.out.println(" ");
			}
			
			if(SQLContainer != null){
				db_sr.excuteSQL(SQLContainer);//將查無資料的人檔資料寫入ND_LOG
			}
			
			Common.updateBS_LOG(Datetime.getYYYMMDD(), Datetime.getFHHMMSS(),"已完成查詢", qry_seq);
			this.setState("init");
        }
        catch(Exception e){
            Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
            e.printStackTrace();
        }
        finally{
            closeResultSet(rs_mr);
            closeResultSet(rs);
            closeResultSet(rs_ow);
            closeResultSet(rs_txt);
            closeStatement(stmt_mr);
            closeStatement(stmt);
            closeStatement(stmt_ow);
            closeStatement(stmt_txt);
            closeConnection(conn);
            db_sr.closeAll();
            db.closeAll();
        }
        
		return rowData;
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

			for(String s[] : txtNoData){
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
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				data.addElement("");
				rowData.add(data);

			}
			model.setDataVector(rowData, columns);
				
			env.setTableModel(model);
			env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca191.jasper"));
			env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
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
			generator.reportToFile(outputFile, parms, "MS950");
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

			for(String s[] : txtNoData){
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
				rowData.add(data);
				
			}
			model.setDataVector(rowData, columns);
					
			env.setTableModel(model);
			env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca192.jasper"));
			env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);

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
				
			generator.reportToFile(outputFile, parms, "MS950");
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
	
    private void closeConnection(Connection conn) {

        if(conn != null){
            try{
                conn.close();
            }
            catch(SQLException e){
            }
        }

    }

    private void closeStatement(Statement stmt) {
        if(stmt != null){
            try{
                stmt.close();
            }
            catch(SQLException e){
            }
        }
    }

    private void closeResultSet(ResultSet rs) {
        if(rs != null){
            try{
                rs.close();
            }
            catch(SQLException e){
            }
        }
    }
	
  //106年增修案
	public String getCheck_RHD10() {return checkGet(check_RHD10);}
	public void setCheck_RHD10(String s) {this.check_RHD10 = checkSet(s);}
    
}
