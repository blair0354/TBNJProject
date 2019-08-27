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
import util.ConverterLibreOffice;
import util.Database;
import util.Datetime;
import util.ODatabase;
import util.QueryBean;
import util.report.ReportEnvironment;
import util.report.TableModelReportEnvironment;
import util.report.TableModelReportGenerator;

public class LCAAP150F extends QueryBean {

    private ServletContext context;
    
    private String print_kind; // 報表種類
    private String print_type; // 報表格式
    private String itemPicture1; // 統一編號多筆

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
    
    private Vector sn_stock = new Vector();// 存放統編
	
    private ArrayList<String []> txtData = new ArrayList<String []>();

    public void genReport() throws Exception {
        // 先將文字檔的資料取出
        System.out.println("### A:" + filestoreLocation);
        System.out.println("### B:" + itemPicture1);
        txtData = Common.getFileArray(filestoreLocation, itemPicture1, 1);
        System.out.println("### C:" + txtData.size());
        
        for(int i = 0; i < txtData.size(); i++){
            System.out.println("######## " + i + " ########");
            String[] data = (String[]) txtData.get(i);
            for(int j = 0; j < data.length; j++){
                System.out.println(j + ":" + data[j]);
            }
        }
        if(getPrint_kind().equals("1")){
            System.out.println("### D:");
            genLandReport(); // 土地
            System.out.println("### E:");
        }
        else{
            System.out.println("### F:");
            genBuildReport(); // 建物
            System.out.println("### G:");
        }
    }
    
    //土地
    public void genLandReport() {
        TableModelReportEnvironment env = new TableModelReportEnvironment();
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
        Vector columns = new Vector();// 這裡宣告是為了重新塞table表頭
        Vector rowData = new Vector();// 這裡宣告是為了重新塞table內容
        try{
            if(this.getPrint_type().equals("3")){
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
                rowData = getLandCSVResultModel();
            }
            else{
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
                rowData = getLandResultModel();
            }

            if((rowData == null) || (rowData.size() == 0)){
                Common.updateDL_LOG("4", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
            }
            else{
                env.setTableModel(model);
                if(this.getPrint_type().equals("1")){
                    env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca122.jasper"));
                    env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
                }
                if(this.getPrint_type().equals("3")){
                    env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca121.jasper"));
                    env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
                }
                
                Database sdb = new Database();
                String username = sdb.getLookupField("select user_name from etecuser where user_id="
                        + Common.sqlChar(this.getUserID()));
                
                HashMap parms = new HashMap();
                parms.put("print_date", "製表日期："
                        + Datetime.getYYYMMDD().substring(0, 3) + "/"
                        + Datetime.getYYYMMDD().substring(3, 5) + "/"
                        + Datetime.getYYYMMDD().substring(5) + "   "
                        + Datetime.getHHMMSS().substring(0, 2) + ":"
                        + Datetime.getHHMMSS().substring(2, 4) + ":"
                        + Datetime.getHHMMSS().substring(4));

                boolean compare = false;
                if(rowData.size() == 0){
                    compare = true;
                    parms.put("print_count", "筆數：0");
                    if(this.getPrint_type().equals("3")){
                        for(int i = 0; i < sn_stock.size(); i++){
                            Vector contentdata = new Vector();
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("無符合條件");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            rowData.add(contentdata);
                        }
                    }
                    else{
                        for(int i = 0; i < sn_stock.size(); i++){
                            Vector contentdata = new Vector();
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("無符合條件");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            rowData.add(contentdata);
                        }
                    }
                }
                else{
                    parms.put("print_count", "筆數："
                            + String.valueOf(rowData.size()));
                }
                
                model.setDataVector(rowData, columns);
                
                parms.put("printer_usr", username);
                parms.put("printer_unit", this.getUnit());
                TableModelReportGenerator generator = new TableModelReportGenerator(env);

                String vid = Common.getVMID();
                File tempDirectory = new File(reportLocation, vid);
                tempDirectory.mkdirs();
                if(this.getPrint_type().equals("1")){
                    java.io.File outputFile;
                    outputFile = new java.io.File(tempDirectory.getPath()
                            + java.io.File.separator
                            + this.getQry_seq() + ".xls");
                    generator.reportToFile(outputFile, parms);
                    
                  //108年增修案改為提供ODS檔案，所以將xls轉為ods檔
                    ConverterLibreOffice conlo=new ConverterLibreOffice();
                    conlo.convert(tempDirectory.getPath()+ java.io.File.separator+this.getQry_seq()+".xls"
                    		,tempDirectory.getPath()+ java.io.File.separator+this.getQry_seq()+".ods");
                    
                  //108年增修案改為提供ODS檔案，轉出ods檔後將xls檔刪除
                    if(outputFile.exists()){
            			if(!outputFile.delete()){
            				System.out.println("刪檔失敗,請查明異常原因");
            			}
            		}
                    
                    if(compare){
                        //Common.updateDL_LOG("4", vid + ":;:"+ this.getQry_seq() + ".xls", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
                    	//108年增修案改為提供ODS檔案
                        Common.updateDL_LOG("4", vid + ":;:"+ this.getQry_seq() + ".ods", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
                    }
                    else{
                        //Common.updateDL_LOG("2", vid + ":;:"+ this.getQry_seq() + ".xls", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
                    	//108年增修案改為提供ODS檔案
                        Common.updateDL_LOG("2", vid + ":;:"+ this.getQry_seq() + ".ods", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
                    }
                }
                else if(this.getPrint_type().equals("3")){
                    java.io.File outputFile;
                    outputFile = new java.io.File(tempDirectory.getPath()
                            + java.io.File.separator
                            + this.getQry_seq() + ".txt");
                    generator.reportToFile(outputFile, parms, "MS950");
                    if(compare){
                        Common.updateDL_LOG("4", vid + ":;:"
                                + this.getQry_seq() + ".txt", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
                    }
                    else{
                        Common.updateDL_LOG("2", vid + ":;:"
                                + this.getQry_seq() + ".txt", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
                    }
                }
            }
        }
        catch(Exception x){
            Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
            x.printStackTrace();
        }
    }

    public void genBuildReport() {
        TableModelReportEnvironment env = new TableModelReportEnvironment();
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
        Vector columns = new Vector();// 這裡宣告是為了重新塞table表頭
        Vector rowData = new Vector();// 這裡宣告是為了重新塞table內容
        try{
            if(this.getPrint_type().equals("3")){
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
                rowData = getBuildCSVResultModel();
            }else{
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
                if("Y".equals(getCheck_RHD10())){
					columns.addElement("HD4849C");
				}
                rowData = getBuildResultModel();
            }
            
            if((rowData == null) || (rowData.size() == 0)){
                Common.updateDL_LOG("4", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
            }
            else{
                env.setTableModel(model);
                if(this.getPrint_type().equals("1")){
                	if("Y".equals(getCheck_RHD10())){
                		env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca132_2.jasper"));
                		env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
                	}else{
                		env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca132.jasper"));
                		env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
                	}

                }else if(this.getPrint_type().equals("3")){
                	if("Y".equals(getCheck_RHD10())){
                		env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca131_2.jasper"));
                		env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
                	}else{
                		env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca131.jasper"));
                        env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
                	}
                }
                
                Database sdb = new Database();
                String username = sdb.getLookupField("select user_name from etecuser where user_id="
                        + Common.sqlChar(this.getUserID()));

                HashMap parms = new HashMap();
                parms.put("print_date", "製表日期："
                        + Datetime.getYYYMMDD().substring(0, 3) + "/"
                        + Datetime.getYYYMMDD().substring(3, 5) + "/"
                        + Datetime.getYYYMMDD().substring(5) + "   "
                        + Datetime.getHHMMSS().substring(0, 2) + ":"
                        + Datetime.getHHMMSS().substring(2, 4) + ":"
                        + Datetime.getHHMMSS().substring(4));

                boolean compare = false;
                if(rowData.size() == 0){
                    compare = true;
                    parms.put("print_count", "筆數：0");
                    if(this.getPrint_type().equals("3")){
                        for(int i = 0; i < sn_stock.size(); i++){
                            Vector contentdata = new Vector();
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("無符合條件");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            if("Y".equals(getCheck_RHD10())){
								columns.addElement("");//目前19
								columns.addElement("");
							}
                            rowData.add(contentdata);
                        }
                    }else{
                        for(int i = 0; i < sn_stock.size(); i++){
                            Vector contentdata = new Vector();
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("無符合條件");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            contentdata.addElement("");
                            if("Y".equals(getCheck_RHD10())){
								columns.addElement("");
							}
                            rowData.add(contentdata);
                        }
                    }
                }
                else{
                    parms.put("print_count", "筆數："
                            + String.valueOf(rowData.size()));
                }
                
                model.setDataVector(rowData, columns);
                
                parms.put("printer_usr", username);
                parms.put("printer_unit", this.getUnit());
                TableModelReportGenerator generator = new TableModelReportGenerator(env);

                String vid = Common.getVMID();
                File tempDirectory = new File(reportLocation, vid);
                tempDirectory.mkdirs();
                if(this.getPrint_type().equals("1")){
                    java.io.File outputFile;
                    outputFile = new java.io.File(tempDirectory.getPath()
                            + java.io.File.separator
                            + this.getQry_seq() + ".xls");
                    generator.reportToFile(outputFile, parms);
                    
                  //108年增修案改為提供ODS檔案，所以將xls轉為ods檔
                    ConverterLibreOffice conlo=new ConverterLibreOffice();
                    conlo.convert(tempDirectory.getPath()+ java.io.File.separator+this.getQry_seq()+".xls"
                    		,tempDirectory.getPath()+ java.io.File.separator+this.getQry_seq()+".ods");
                    
                  //108年增修案改為提供ODS檔案，轉出ods檔後將xls檔刪除
                    if(outputFile.exists()){
            			if(!outputFile.delete()){
            				System.out.println("刪檔失敗,請查明異常原因");
            			}
            		}
                    
                    if(compare){
                        //Common.updateDL_LOG("4", vid + ":;:"+ this.getQry_seq() + ".xls", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
                    	//108年增修案改為提供ODS檔案
                        Common.updateDL_LOG("4", vid + ":;:"+ this.getQry_seq() + ".ods", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
                    }
                    else{
                        //Common.updateDL_LOG("2", vid + ":;:"+ this.getQry_seq() + ".xls", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
                    	//108年增修案改為提供ODS檔案
                        Common.updateDL_LOG("2", vid + ":;:"+ this.getQry_seq() + ".ods", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
                    }
                }
                else if(this.getPrint_type().equals("3")){
                    java.io.File outputFile;
                    outputFile = new java.io.File(tempDirectory.getPath()
                            + java.io.File.separator
                            + this.getQry_seq() + ".txt");
                    generator.reportToFile(outputFile, parms, "MS950");
                    if(compare){
                        Common.updateDL_LOG("4", vid + ":;:"
                                + this.getQry_seq() + ".txt", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
                    }
                    else{
                        Common.updateDL_LOG("2", vid + ":;:"
                                + this.getQry_seq() + ".txt", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
                    }
                }
            }
        }
        catch(Exception x){
            Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
            x.printStackTrace();
        }
    }
    
    //土地TXT
    public Vector getLandCSVResultModel() throws Exception {
        
        qry_date_start = Datetime.getYYYMMDD();
        qry_time_start = Datetime.getHHMMSS();
        
        qry_seq = "M" + qry_date_start + qry_time_start;
        this.setQry_seq(qry_seq);
        con = "國軍退輔會批次歸戶清冊(土地)";
        qry = "市縣市=;事務所=;統一編號=;姓名=";
        qry += ";統編批次=" + this.getItemPicture1();
        
        Common.insertDL_LOG(qry_seq, "M", this.getUserID(), this.getUnitID(),qry, this.getPrint_type(), qry_date_start, qry_time_start);
        
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
        try {
            conn = db.getConnection();
            stmt_txt = conn.createStatement();
            stmt_ow = conn.createStatement();
            stmt = conn.createStatement();
            stmt_mr = conn.createStatement();
            //將原本透過使用者勾選的資料，改成從上傳的文字檔全數直接至rlnid搜尋後全數取出
            for(String s[] : txtData){
                String qLidn = s[0]; // 查詢統一編號
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
                    
                    Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", "", "", "", "", "", "", lidn, Common.isoToBig5(lnam), "", "", "", "", qry_seq, "2");
                    
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
                    
                    System.out.println("##### sql1:" + sql_ow);

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
                                System.out.println("1.#####: date_bb21:" + Common.get(rs_ow.getString("date_bb21")));
                                data.add(Common.get(rs_ow.getString("bb21"))); // 21.申報地價
                                data.add(Common.get(rs_mr.getString("lidn"))); // 22.管理者統編
                                data.add(Common.isoToMS950(rs_mr.getString("lnam"))); // 23.管理者姓名
                                
                                rowData.add(data);
                            }
                            closeResultSet(rs_mr);
                            
                            if(!isMngrExist){
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
                                System.out.println("1.#####: date_bb21:" + Common.get(rs_ow.getString("date_bb21")));
                                data.add(Common.get(rs_ow.getString("bb21"))); // 21.申報地價
                                data.add(""); // 22.管理者統編
                                data.add(""); // 23.管理者姓名
                                
                                rowData.add(data);
                            }
                        }
                        closeResultSet(rs);
                    }
                    closeResultSet(rs_ow);

                    System.out.println("LCAAP150F土地-->> query: lidn=" + lidn
                            + ", cty=" + cty + ", unit=" + unit);
                }
                closeResultSet(rs_txt);
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
        }
        
        return rowData;
    }

	//土地PDF
	public Vector getLandResultModel() throws Exception {
        
        qry_date_start = Datetime.getYYYMMDD();
        qry_time_start = Datetime.getHHMMSS();
        
        qry_seq = "M" + qry_date_start + qry_time_start;
        this.setQry_seq(qry_seq);
        con = "國軍退輔會批次歸戶清冊(土地)";
        qry = "市縣市=;事務所=;統一編號=;姓名=";
        qry += ";統編批次=" + this.getItemPicture1();
        
        Common.insertDL_LOG(qry_seq, "M", this.getUserID(), this.getUnitID(),qry, this.getPrint_type(), qry_date_start, qry_time_start);
        
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
        try {
            conn = db.getConnection();
            stmt_txt = conn.createStatement();
            stmt_ow = conn.createStatement();
            stmt = conn.createStatement();
            stmt_mr = conn.createStatement();
            //將原本透過使用者勾選的資料，改成從上傳的文字檔全數直接至rlnid搜尋後全數取出
            for(String s[] : txtData){
                String qLidn = s[0]; // 查詢統一編號
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
                    
                    Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", "", "", "", "", "", "", lidn, Common.isoToBig5(lnam), "", "", "", "", qry_seq, "2");
                    
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
                    
                    System.out.println("##### sql2:" + sql_ow);

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
                                isMngrExist = true;
                                Vector data = new Vector();
                                data.add(Common.get(lidn)); // 0.權利人統編
                                data.add(Common.isoToMS950(lnam)); // 1.權利人姓名
                                data.add(Common.isoToMS950(rs.getString("ch_cty"))); // 3.縣市-名稱
                                data.add(Common.isoToMS950(rs.getString("ch_aa46"))); // 5.鄉鎮市區-名稱
                                data.add(Common.isoToMS950(rs.getString("ch_aa48"))); // 7.段小段-名稱
                                data.add(Common.get(rs.getString("aa49"))); // 8.地號
                                data.add(rs.getDouble("aa10")); // 9.面積
                                data.add(Common.get(rs_ow.getString("bb01"))); // 10.登次
                                
                                // 11.權利範圍類別 12.權利範圍類別-名稱 13.權利範圍-分母 14.權利範圍-分子
                                String bb15 = Common.get(rs_ow.getString("bb15_1"))
                                        + Common.isoToMS950(rs_ow.getString("cht_bb15"))
                                        + Common.get(rs_ow.getString("bb15_2")) + "分之"
                                        + Common.get(rs_ow.getString("bb15_3")); 
                                data.add(bb15);
                                
                                data.add(Common.isoToMS950(rs.getString("ch_aa11"))); // 16.使用分區-名稱
                                data.add(Common.isoToMS950(rs.getString("ch_aa12"))); // 18.使用地類別-名稱
                                data.add(rs.getDouble("aa16")); // 19.公告土地現值
                                data.add(Common.get(rs_ow.getString("date_bb21"))); // 20.申報地價日期
                                System.out.println("2.#####: date_bb21:" + Common.get(rs_ow.getString("date_bb21")));
                                data.add(rs_ow.getDouble("bb21")); // 21.申報地價
                                data.add(Common.get(rs_mr.getString("lidn"))); // 22.管理者統編
                                data.add(Common.isoToMS950(rs_mr.getString("lnam"))); // 23.管理者姓名
                                
                                rowData.add(data);
                            }
                            closeResultSet(rs_mr);
                            
                            if(!isMngrExist){
                                Vector data = new Vector();
                                data.add(Common.get(lidn)); // 0.權利人統編
                                data.add(Common.isoToMS950(lnam)); // 1.權利人姓名
                                data.add(Common.isoToMS950(rs.getString("ch_cty"))); // 3.縣市-名稱
                                data.add(Common.isoToMS950(rs.getString("ch_aa46"))); // 5.鄉鎮市區-名稱
                                data.add(Common.isoToMS950(rs.getString("ch_aa48"))); // 7.段小段-名稱
                                data.add(Common.get(rs.getString("aa49"))); // 8.地號
                                data.add(rs.getDouble("aa10")); // 9.面積
                                data.add(Common.get(rs_ow.getString("bb01"))); // 10.登次
                                
                                // 11.權利範圍類別 12.權利範圍類別-名稱 13.權利範圍-分母 14.權利範圍-分子
                                String bb15 = Common.get(rs_ow.getString("bb15_1"))
                                        + Common.isoToMS950(rs_ow.getString("cht_bb15"))
                                        + Common.get(rs_ow.getString("bb15_2")) + "分之"
                                        + Common.get(rs_ow.getString("bb15_3")); 
                                data.add(bb15);
                                
                                data.add(Common.isoToMS950(rs.getString("ch_aa11"))); // 16.使用分區-名稱
                                data.add(Common.isoToMS950(rs.getString("ch_aa12"))); // 18.使用地類別-名稱
                                data.add(rs.getDouble("aa16")); // 19.公告土地現值
                                data.add(Common.get(rs_ow.getString("date_bb21"))); // 20.申報地價日期
                                System.out.println("2.#####: date_bb21:" + Common.get(rs_ow.getString("date_bb21")));
                                data.add(rs_ow.getDouble("bb21")); // 21.申報地價
                                data.add(""); // 22.管理者統編
                                data.add(""); // 23.管理者姓名
                                
                                rowData.add(data);
                            }
                        }
                        closeResultSet(rs);
                    }
                    closeResultSet(rs_ow);

                    System.out.println("LCAAP150F土地-->> query: lidn=" + lidn
                            + ", cty=" + cty + ", unit=" + unit);
                }
                closeResultSet(rs_txt);
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
        }
        
        return rowData;
    }

	//建物TXT
	public Vector getBuildCSVResultModel() throws Exception {
	    
		qry_date_start = Datetime.getYYYMMDD();
		qry_time_start = Datetime.getHHMMSS();
		
		qry_seq = "N" + qry_date_start + qry_time_start;
		this.setQry_seq(qry_seq);
		con = "國軍退輔會批次歸戶清冊(建物)";
		qry = "市縣市=;事務所=;統一編號=;姓名=";
		qry += ";統編批次=" + this.getItemPicture1();
		
		Common.insertDL_LOG(qry_seq, "N", this.getUserID(), this.getUnitID(),qry, this.getPrint_type(), qry_date_start, qry_time_start);
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

        try {
            conn = db.getConnection();
            stmt_txt = conn.createStatement();
            stmt_ow = conn.createStatement();
            stmt = conn.createStatement();
            stmt_mr = conn.createStatement();
			//將原本透過使用者勾選的資料，改成從上傳的文字檔全數直接至rlnid搜尋後全數取出
			for(String s[] : txtData){
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
 
                    Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", "", "", "", "", "", "", lidn, Common.isoToBig5(lnam), "", "", "", "", qry_seq, "2");

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
					
                    System.out.println("LCAAP150F建物-->> query: lidn=" + lidn
                            + ", cty=" + cty + ", unit=" + unit);
				}
				closeResultSet(rs_txt);
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
        }
        
		return rowData;
	}
	
	//建物PDF
    public Vector getBuildResultModel() throws Exception {
        
        qry_date_start = Datetime.getYYYMMDD();
        qry_time_start = Datetime.getHHMMSS();
        
        qry_seq = "N" + qry_date_start + qry_time_start;
        this.setQry_seq(qry_seq);
        con = "國軍退輔會批次歸戶清冊(建物)";
        qry = "市縣市=;事務所=;統一編號=;姓名=";
        qry += ";統編批次=" + this.getItemPicture1();
        
        Common.insertDL_LOG(qry_seq, "N", this.getUserID(), this.getUnitID(),qry, this.getPrint_type(), qry_date_start, qry_time_start);
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

        try {
            conn = db.getConnection();
            stmt_txt = conn.createStatement();
            stmt_ow = conn.createStatement();
            stmt = conn.createStatement();
            stmt_mr = conn.createStatement();
            //將原本透過使用者勾選的資料，改成從上傳的文字檔全數直接至rlnid搜尋後全數取出
            for(String s[] : txtData){
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
                    
                    Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", "", "", "", "", "", "", lidn, Common.isoToBig5(lnam), "", "", "", "", qry_seq, "2");
                    
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
                                isMngrExist = true;
                                Vector data = new Vector();
                                data.add(Common.get(lidn)); // 1.統編
                                data.add(Common.isoToMS950(lnam)); // 2.姓名
                                data.add(Common.isoToMS950(rs.getString("ch_cty"))); //4.縣市-名稱
                                data.add(Common.isoToMS950(rs.getString("ch_dd46"))); //6.鄉鎮市區-名稱
                                data.add(Common.isoToMS950(rs.getString("ch_dd48"))); // 8.段小段-名稱
                                data.add(Common.get(rs.getString("dd49"))); // 9.建號
                                data.add(Common.isoToMS950(rs.getString("dd09"))); // 10.建物門牌
                                data.add(rs.getDouble("dd08")); // 11.總面積
                                data.add(Common.get(rs_ow.getString("ee01"))); //12.登記次序
                                
                                // 13.權利範圍類別 14.權利範圍類別-名稱 15.權利範圍-分母 16.權利範圍-分子
                                String ee15 = Common.get(rs_ow.getString("ee15_1"))
                                        + Common.isoToMS950(rs_ow.getString("cht_ee15"))
                                        + Common.get(rs_ow.getString("ee15_2")) + "分之"
                                        + Common.get(rs_ow.getString("ee15_3")); 
                                data.add(ee15);
                                
                                data.add(Common.get(rs_mr.getString("lidn"))); //17.管理者統編
                                data.add(Common.isoToMS950(rs_mr.getString("lnam"))); //18.管理者姓名
                                
                                if("Y".equals(getCheck_RHD10())){
    								data.add(getRHD10List(rs_ow.getString("ed48"),rs_ow.getString("ed49"),cty,unit)[1]);
    							}
                                
                                rowData.add(data);
                            }
                            closeResultSet(rs_mr);
                            
                            if(!isMngrExist){
                                Vector data = new Vector();
                                data.add(Common.get(lidn)); // 1.統編
                                data.add(Common.isoToMS950(lnam)); // 2.姓名
                                data.add(Common.isoToMS950(rs.getString("ch_cty"))); //4.縣市-名稱
                                data.add(Common.isoToMS950(rs.getString("ch_dd46"))); //6.鄉鎮市區-名稱
                                data.add(Common.isoToMS950(rs.getString("ch_dd48"))); // 8.段小段-名稱
                                data.add(Common.get(rs.getString("dd49"))); // 9.建號
                                data.add(Common.isoToMS950(rs.getString("dd09"))); // 10.建物門牌
                                data.add(rs.getDouble("dd08")); // 11.總面積
                                data.add(Common.get(rs_ow.getString("ee01"))); //12.登記次序
                                
                                // 13.權利範圍類別 14.權利範圍類別-名稱 15.權利範圍-分母 16.權利範圍-分子
                                String ee15 = Common.get(rs_ow.getString("ee15_1"))
                                        + Common.isoToMS950(rs_ow.getString("cht_ee15"))
                                        + Common.get(rs_ow.getString("ee15_2")) + "分之"
                                        + Common.get(rs_ow.getString("ee15_3")); 
                                data.add(ee15);
                                
                                data.add(""); //17.管理者統編
                                data.add(""); //18.管理者姓名
                                
                                if("Y".equals(getCheck_RHD10())){
    								data.add(getRHD10List(rs_ow.getString("ed48"),rs_ow.getString("ed49"),cty,unit)[1]);
    							}
                                
                                rowData.add(data);
                            }
                        }
                        closeResultSet(rs);
                    }
                    closeResultSet(rs_ow);
                    
                    System.out.println("LCAAP150F建物-->> query: lidn=" + lidn
                            + ", cty=" + cty + ", unit=" + unit);
                }
                closeResultSet(rs_txt);
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
        }
        
        return rowData;
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
	
    public String getPrint_kind() {
        return checkGet(print_kind);
    }

    public void setPrint_kind(String print_type) {
        this.print_kind = checkSet(print_type);
    }

    public String getPrint_type() {
        return checkGet(print_type);
    }

    public void setPrint_type(String print_type) {
        this.print_type = checkSet(print_type);
    }

    public String getItemPicture1() {
        return checkGet(itemPicture1);
    }

    public void setItemPicture1(String itemPicture1) {
        this.itemPicture1 = checkSet(itemPicture1);
    }

    public String getFilestoreLocation() {
        return checkGet(filestoreLocation);
    }

    public void setFilestoreLocation(String filestoreLocation) {
        this.filestoreLocation = checkSet(filestoreLocation);
    }

    public String getReportLocation() {
        return checkGet(reportLocation);
    }

    public void setReportLocation(String reportLocation) {
        this.reportLocation = checkSet(reportLocation);
    }

    public String getUip() {
        return checkGet(uip);
    }

    public void setUip(String uip) {
        this.uip = checkSet(uip);
    }

    public String getDqry() {
        return checkGet(dqry);
    }

    public void setDqry(String dqry) {
        this.dqry = checkSet(dqry);
    }

    public String getQry() {
        return checkGet(qry);
    }

    public void setQry(String qry) {
        this.qry = checkSet(qry);
    }

    public String getQry_date_end() {
        return checkGet(qry_date_end);
    }

    public void setQry_date_end(String qry_date_end) {
        this.qry_date_end = checkSet(qry_date_end);
    }

    public String getQry_date_start() {
        return checkGet(qry_date_start);
    }

    public void setQry_date_start(String qry_date_start) {
        this.qry_date_start = checkSet(qry_date_start);
    }

    public String getQry_msg() {
        return checkGet(qry_msg);
    }

    public void setQry_msg(String qry_msg) {
        this.qry_msg = checkSet(qry_msg);
    }

    public String getQry_time_end() {
        return checkGet(qry_time_end);
    }

    public void setQry_time_end(String qry_time_end) {
        this.qry_time_end = checkSet(qry_time_end);
    }

    public String getQry_time_start() {
        return checkGet(qry_time_start);
    }

    public void setQry_time_start(String qry_time_start) {
        this.qry_time_start = checkSet(qry_time_start);
    }

    public String getCon() {
        return checkGet(con);
    }

    public void setCon(String con) {
        this.con = checkSet(con);
    }

    public String getQry_seq() {
        return checkGet(qry_seq);
    }

    public void setQry_seq(String qry_seq) {
        this.qry_seq = checkSet(qry_seq);
    }

    public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }
    
  //106年增修案
	public String getCheck_RHD10() {return checkGet(check_RHD10);}
	public void setCheck_RHD10(String s) {this.check_RHD10 = checkSet(s);}
}
