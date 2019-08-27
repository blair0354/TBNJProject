/*
 *<br>程式目的：申請用途案件統計量表
 *<br>程式代號：LCAAP170F
 *<br>程式日期：1040730
 *<br>程式作者：robin
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

public class LCAAP170F extends QueryBean {

    String txtcity_no;
    String txtunit;
    String txtqrymsg;
    String print_type;
    String filestoreLocation;
    String reportLocation;
    String uip;
    String qry_date_start;
    String qry_date_end;
    String con;
    String qry_seq;
    String qry;
    ServletContext context;

    public String getTxtcity_no() { return checkGet(txtcity_no);}
    public void setTxtcity_no(String txtcity_no) {this.txtcity_no = checkSet(txtcity_no);}

    public String getTxtunit() {return checkGet(txtunit);}
    public void setTxtunit(String txtunit) {this.txtunit = checkSet(txtunit);}

    public String getFilestoreLocation() {return checkGet(filestoreLocation);}
    public void setFilestoreLocation(String filestoreLocation) {this.filestoreLocation = checkSet(filestoreLocation);}

    public String getReportLocation() {return checkGet(reportLocation);}
    public void setReportLocation(String reportLocation) {this.reportLocation = checkSet(reportLocation);}

    public String getPrint_type() { return checkGet(print_type);}
    public void setPrint_type(String print_type) {this.print_type = checkSet(print_type);}

    public String getUip() {return checkGet(uip);}
    public void setUip(String uip) {this.uip = checkSet(uip);}

    public String getQry_date_end() {return checkGet(qry_date_end); }
    public void setQry_date_end(String qry_date_end) {this.qry_date_end = checkSet(qry_date_end);}
    
    public String getQry_date_start() { return checkGet(qry_date_start);}
    public void setQry_date_start(String qry_date_start) {this.qry_date_start = checkSet(qry_date_start);}
    
    public String getTxtqrymsg() {return checkGet(txtqrymsg);}
    public void setTxtqrymsg(String txtqrymsg) {this.txtqrymsg = checkSet(txtqrymsg);}
    
    public String getCon() {return checkGet(con);}
    public void setCon(String con) {this.con = checkSet(con);}

    public String getQry_seq() {return checkGet(qry_seq);}
    public void setQry_seq(String qry_seq) {this.qry_seq = checkSet(qry_seq);}
    
    public String getQry() {return checkGet(qry);}
    public void setQry(String qry) {this.qry = checkSet(qry);}

    public ServletContext getContext() {return context; }
    public void setContext(ServletContext context) {this.context = context; }

    public DefaultTableModel getResultModel(String preSQLstr) throws Exception {
        System.out.println("### getResultModel..");
        DefaultTableModel model = new javax.swing.table.DefaultTableModel();
        Database db = new Database();
        Vector columns = new Vector();
        columns.addElement("col00");
        columns.addElement("col01");
        columns.addElement("col02");
        columns.addElement("col03");
        columns.addElement("col04");
        try {
            Vector rowData = new Vector();
            
            String sqlstr = "select " +"\n"
							+ 	"(select KCNT from RKEYN where KCDE_1='45' and KCDE_2=unit) as city,unit,"+"\n"//縣市名稱
							+ 	"SUM(A1)as QRY_PURPOSE01_CNT,"+"\n"//申辦登記案件使用統計筆數
							+ 	"SUM(A2)as QRY_PURPOSE02_CNT,"+"\n"//處理訴訟案件統計筆數
							+ 	"SUM(A3)as QRY_PURPOSE03_CNT"+"\n"//其他統計筆數
							+ " from ( "+"\n"
							+ 	"select"+"\n"
							+ 		"(CASE SUBSTRING(ISNULL(UNITID, ''),1,1) WHEN 'L' THEN 'B' WHEN 'R' THEN 'D' WHEN 'S' THEN 'E' ELSE SUBSTRING(ISNULL(UNITID, ''),1,1) END) as unit, "+"\n"
							+ 		"(CASE WHEN ISNULL(QRY_PURPOSE01,'')<>'' THEN 1 ELSE 0 END) AS A1,"+"\n"
							+ 		"(CASE WHEN ISNULL(QRY_PURPOSE02,'')<>'' THEN 1 ELSE 0 END) AS A2,"+"\n"
							+ 		"(CASE WHEN ISNULL(QRY_PURPOSE03,'')<>'' THEN 1 ELSE 0 END) AS A3"+"\n"
							+ 	" from BS_LOG "+"\n"
							+ 	" where 1=1 "+"\n"
							+ 	" and ISNULL(QRY_USERTYPE,'')<>'' "+"\n"
							+	preSQLstr+"\n"
							+ ") a group by unit order by unit"
							;
            System.out.println("sqlstr==>"+sqlstr);
            ResultSet rs = db.querySQL(sqlstr);
            int[] total = new int[]{0,0,0}; 
            while (rs.next()) {
                Vector data = new Vector();
                data.add(rs.getString("city"));
                data.add(Common.valueFormat(rs.getInt("QRY_PURPOSE01_CNT")+""));
                total[0] +=rs.getInt("QRY_PURPOSE01_CNT");
                data.add(Common.valueFormat(rs.getInt("QRY_PURPOSE02_CNT")+""));
                total[1] +=rs.getInt("QRY_PURPOSE02_CNT");
                data.add(Common.valueFormat(rs.getInt("QRY_PURPOSE03_CNT")+""));
                total[2] +=rs.getInt("QRY_PURPOSE03_CNT");
                data.add(Common.valueFormat((rs.getInt("QRY_PURPOSE01_CNT")+rs.getInt("QRY_PURPOSE02_CNT")+rs.getInt("QRY_PURPOSE03_CNT"))+""));
                rowData.add(data);
                
            }
            rs.getStatement().close();
            rs.close();
            if (rowData.size() == 0) {
                Vector data = new Vector();
                data.addElement("無符合條件");
                data.addElement("");
                data.addElement("");
                data.addElement("");
                data.addElement("");
                rowData.add(data);
            }else{
                Vector data = new Vector();
                data.addElement("合計");
                data.addElement(Common.valueFormat(total[0]+""));
                data.addElement(Common.valueFormat(total[1]+""));
                data.addElement(Common.valueFormat(total[2]+""));
                data.addElement(Common.valueFormat((total[0]+total[1]+total[2])+""));
                rowData.add(data);
            }
             //System.out.println("rowData==>"+rowData.size());
            model.setDataVector(rowData, columns);
            this.setState("init");
        } catch (Exception x) {
            x.printStackTrace();
        } finally {
            db.closeAll();
        }
        return model;
    }// method getResultModel end

    public String genReport() throws Exception {
        System.out.println("### genReport!");
        setBS_LOGStr();
        String fileName="";
        String unitName = "全國";
        TableModelReportEnvironment env = new TableModelReportEnvironment();
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
        try {
            System.out.println("### genReport!-2");
            
            String preSQLstr = "";
            if(!"".equals(getQry_date_start())){
            	preSQLstr += " and ISNULL(QRY_DATE_START,'') >= '"+getQry_date_start()+"'";
            }
            if(!"".equals(getQry_date_end())){
            	preSQLstr += " and ISNULL(QRY_DATE_START,'') <= '"+getQry_date_end()+"'";
            }
            System.out.println("### genReport!-3");
            if(!"".equals(getTxtcity_no())){
            	switch(getTxtcity_no().toCharArray()[0]){
	            	case 'B':
	            		preSQLstr += " and SUBSTRING(ISNULL(UNITID, ''),1,1) in ('L','B') ";
	            		break;
	            	case 'D':
	            		preSQLstr += " and SUBSTRING(ISNULL(UNITID, ''),1,1) in ('R','D') ";
	            		break;
	            	case 'E':
	            		preSQLstr += " and SUBSTRING(ISNULL(UNITID, ''),1,1) = '"+getTxtcity_no()+"'";
	            		break;
	            	default:
	            		preSQLstr += " and SUBSTRING(ISNULL(UNITID, ''),1,1) = '"+getTxtcity_no()+"'";
	            		break;
            	}
            }
            System.out.println("### genReport!-4");
            if(!"".equals(getTxtunit())){
            	if(getTxtunit().equals("BD")){
            		preSQLstr += " and UNITID in ('LA','BD') ";
            	}else if(getTxtunit().equals("BE")){
            		preSQLstr += " and UNITID in ('LB','BE') ";
            	}else if(getTxtunit().equals("BF")){
            		preSQLstr += " and UNITID in ('LC','BF') ";
            	}else if(getTxtunit().equals("BH")){
            		preSQLstr += " and UNITID in ('LF','BH') ";
            	}else if(getTxtunit().equals("BG")){
            		preSQLstr += " and UNITID in ('LE','BG') ";
            	}else if(getTxtunit().equals("BI")){
            		preSQLstr += " and UNITID in ('LG','BI') ";
            	}else if(getTxtunit().equals("BJ")){
            		preSQLstr += " and UNITID in ('LH','BJ') ";
            	}else if(getTxtunit().equals("DD")){
            		preSQLstr += " and UNITID in ('RA','DD') ";
            	}else if(getTxtunit().equals("DE")){
            		preSQLstr += " and UNITID in ('RB','DE') ";
            	}else if(getTxtunit().equals("DF")){
            		preSQLstr += " and UNITID in ('RC','DF') ";
            	}else if(getTxtunit().equals("DG")){
            		preSQLstr += " and UNITID in ('RD','DG') ";
            	}else if(getTxtunit().equals("DH")){
            		preSQLstr += " and UNITID in ('RE','DH') ";
            	}else if(getTxtunit().equals("DI")){
            		preSQLstr += " and UNITID in ('RF','DI') ";
            	}else if(getTxtunit().equals("DJ")){
            		preSQLstr += " and UNITID in ('RG','DJ') ";
            	}else if(getTxtunit().equals("DK")){
            		preSQLstr += " and UNITID in ('RH','DK') ";
            	}else if(getTxtunit().equals("EF")){
            		preSQLstr += " and UNITID in ('SA','EF') ";
            	}else if(getTxtunit().equals("EG")){
            		preSQLstr += " and UNITID in ('SB','EG') ";
            	}else if(getTxtunit().equals("EH")){
            		preSQLstr += " and UNITID in ('SC','EH') ";
            	}else if(getTxtunit().equals("EI")){
            		preSQLstr += " and UNITID in ('SD','EI') ";
            	}else if(getTxtunit().equals("EJ")){
            		preSQLstr += " and UNITID in ('SE','EJ') ";
            	}else if(getTxtunit().equals("EK")){
            		preSQLstr += " and UNITID in ('SF','EK') ";
            	}else if(getTxtunit().equals("EL")){
            		preSQLstr += " and UNITID in ('SG','EL') ";
            	}else{
            		preSQLstr += " and ISNULL(UNITID, '') = '"+getTxtunit()+"'";
            	}
            }
            System.out.println("### genReport!-5");
            if(!"".equals(getTxtqrymsg())){
            	preSQLstr += " and ISNULL(QRY_MSG,'')= '"+getTxtqrymsg()+"'";
            }
            System.out.println("### genReport!-6");
            model = getResultModel(preSQLstr);
            System.out.println("### genReport!-7");
            env.setTableModel(model);
            env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca170.jasper"));
            if (this.getPrint_type().equals("2")) {
                env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
            }
            if (this.getPrint_type().equals("1")) {
                 env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
            }
            if (this.getPrint_type().equals("3")) {
                env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
            }
            
            Database sdb = new Database();
//            String username = sdb.getLookupField("select user_name from etecuser where user_id=" + Common.sqlChar(this.getUserID()));
            System.out.println("### genReport!-8");
            HashMap parms = new HashMap();
            parms.put("qryDate", qry_date_range);
            parms.put("printDate", printDateTime);
            
            String dataA0 = sdb.getLookupField("select count(*) from BS_LOG where QRY_USERTYPE='1' and ISNULL(QRY_PURPOSE01,'')<>''" + preSQLstr);
            parms.put("dataA0", Common.valueFormat(dataA0));//	民眾申請+申辦登記案件使用
            String dataA1 = sdb.getLookupField("select count(*) from BS_LOG where QRY_USERTYPE='1' and ISNULL(QRY_PURPOSE02,'')<>''" + preSQLstr);
            parms.put("dataA1", Common.valueFormat(dataA1));//	民眾申請+處理訴訟案
            String dataA2 = sdb.getLookupField("select count(*) from BS_LOG where QRY_USERTYPE='1' and ISNULL(QRY_PURPOSE03,'')<>''" + preSQLstr);
            parms.put("dataA2", Common.valueFormat(dataA2));//	民眾申請+其他
            parms.put("totalA", Common.valueFormat((Integer.parseInt(dataA0)+Integer.parseInt(dataA1)+Integer.parseInt(dataA2))+""));//民眾申請合計
            String dataB0 = sdb.getLookupField("select count(*) from BS_LOG where QRY_USERTYPE='2' and ISNULL(QRY_PURPOSE01,'')<>''" + preSQLstr);
            parms.put("dataB0", Common.valueFormat(dataB0));//	公務用+申辦登記案件使用
            String dataB1 = sdb.getLookupField("select count(*) from BS_LOG where QRY_USERTYPE='2' and ISNULL(QRY_PURPOSE02,'')<>''" + preSQLstr);
            parms.put("dataB1", Common.valueFormat(dataB1));//	公務用+處理訴訟案
            String dataB2 = sdb.getLookupField("select count(*) from BS_LOG where QRY_USERTYPE='2' and ISNULL(QRY_PURPOSE03,'')<>''" + preSQLstr);
            parms.put("dataB2", Common.valueFormat(dataB2));//	公務用+其他
            parms.put("totalB", Common.valueFormat((Integer.parseInt(dataB0)+Integer.parseInt(dataB1)+Integer.parseInt(dataB2))+""));//公務用合計
            parms.put("sum0", Common.valueFormat((Integer.parseInt(dataA0)+Integer.parseInt(dataB0))+""));//申辦登記案件使用合計
            parms.put("sum1", Common.valueFormat((Integer.parseInt(dataA1)+Integer.parseInt(dataB1))+""));//處理訴訟案合計
            parms.put("sum2", Common.valueFormat((Integer.parseInt(dataA2)+Integer.parseInt(dataB2))+""));//其他合計
            parms.put("totalSum", Common.valueFormat((Integer.parseInt(dataA0)+Integer.parseInt(dataA1)+Integer.parseInt(dataA2)+
            		Integer.parseInt(dataB0)+Integer.parseInt(dataB1)+Integer.parseInt(dataB2))+""));//各項合計
            if(!getTxtcity_no().equals("") && !getTxtcity_no().equals("0")){
            	unitName = sdb.getLookupField("select KCNT from RKEYN where KCDE_1='45' and KCDE_2='"+getTxtcity_no()+"' ");
            }
            if(!getTxtunit().equals("") && !getTxtunit().substring(1,2).equals("0")){
            	unitName += sdb.getLookupField("select KCNT from RKEYN where KCDE_1='55' and KRMK='"+getTxtunit()+"' ");
            }
            parms.put("unitName", unitName);
            
            TableModelReportGenerator generator = new TableModelReportGenerator(env);
            String vid = Common.getVMID();
            File tempDirectory = new File(reportLocation, vid);
            tempDirectory.mkdirs();
            System.out.println(tempDirectory.getPath());
            java.io.File outputFile;
            
            String sql = "Update BS_LOG set QRY_DATE_END=" + Common.sqlChar(Datetime.getYYYMMDD())
            		+ " ,QRY_TIME_END=" + Common.sqlChar(Datetime.getHHMMSS())
            		+ " , QRY_MSG=" + Common.sqlChar("已完成查詢")
            		+ " Where Ip=" + Common.sqlChar(uip)
            		+ " And qry_date_start=" + Common.sqlChar(qry_start_date)
            		+ " And qry_time_start=" + Common.sqlChar(qry_start_time);
            sdb.excuteSQL(new String[]{sql});
            
           if (this.getPrint_type().equals("2")) {
               //fileName = "D:\\\\reportTest"+"\\\\"+vid + "\\\\" +  Datetime.getYYYMMDD() + Datetime.getHHMMSS() + ".pdf";
        	   fileName = Datetime.getYYYMMDD() + Datetime.getHHMMSS() + ".pdf";
        	   String fileNameRoot = this.getReportLocation()+"\\\\"+vid + "\\\\" + fileName;
               outputFile = new java.io.File(fileNameRoot);
                generator.reportToFile(outputFile, parms);
                fileName=vid+":;:"+fileName;
            }
            if (this.getPrint_type().equals("1")) {
            	//fileName = "D:\\\\reportTest"+"\\\\"+vid + "\\\\" +  Datetime.getYYYMMDD() + Datetime.getHHMMSS() + ".xls";
            	fileName = Datetime.getYYYMMDD() + Datetime.getHHMMSS() + ".xls";
            	String fileNameRoot = this.getReportLocation()+"\\\\"+vid + "\\\\" + fileName;
                outputFile = new java.io.File(fileNameRoot);
                generator.reportToFile(outputFile, parms);
                fileName=vid+":;:"+fileName;
            }
            if (this.getPrint_type().equals("3")) {
            	//fileName = "D:\\\\reportTest"+"\\\\"+vid + "\\\\" + Datetime.getYYYMMDD() + Datetime.getHHMMSS() + ".txt";
            	fileName = Datetime.getYYYMMDD() + Datetime.getHHMMSS() + ".txt";
            	String fileNameRoot = this.getReportLocation()+"\\\\"+vid + "\\\\" + fileName;
                outputFile = new java.io.File(fileNameRoot);
                generator.reportToFile(outputFile, parms, "MS950");
                fileName=vid+":;:"+fileName;
            }
        } catch (Exception x) {
            System.out.println("### genReport!-20");
            System.out.println("異常==>" + x.toString());
            x.printStackTrace();
        }
        System.out.println("fileName===>"+fileName);
        return fileName;
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
            System.out.println("異常==>" + e.toString());
        } finally {
            odb.closeAll();
        }
        return printer_unit;
    }
    
    private String qry_start_date;
    private String qry_start_time;
    private String printDateTime;
    private String qry_date_range;
    
    public void setBS_LOGStr() throws Exception {
    	qry_start_date = Datetime.getYYYMMDD();
    	qry_start_time = Datetime.getHHMMSS();
        int hh = Integer.parseInt(qry_start_time.substring(0, 2));
    	printDateTime = Common.formatYYYMMDD(qry_start_date,4)+" "
                + (hh>=12?"下午":"上午")+" "
                + Common.formatFrontZero((hh>=12?hh-12:hh)+"", 2) + ":"
                + qry_start_time.substring(2, 4) + ":"
                + qry_start_time.substring(4);
    	qry_date_range = Common.formatYYYMMDD(this.getQry_date_start(),4)+"~"+Common.formatYYYMMDD(this.getQry_date_end(),4);
        con = "申請用途案件統計量表";
        qry = "市縣市=" + this.getTxtcity_no() + ";事務所=" + this.getTxtunit()
                + ";查詢狀態=" + this.getTxtqrymsg() + ";列印日期：="
                + printDateTime + ";起訖查詢日期：=" + qry_date_range;
        Common.insertBS_LOG(qry_start_date, qry_start_time, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", 
                "", "", "","", "", "", "", "", null, null, null, null, null, null);
    }
}
 