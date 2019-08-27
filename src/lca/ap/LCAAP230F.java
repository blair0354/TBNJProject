/*
 *<br>程式目的：各單位管理者清冊
 *<br>程式代號：lcaap230f
 *<br>程式日期：1060419
 *<br>程式作者：Rhonda Ke
 *<br>--------------------------------------------------------
 *<br>修改作者　　修改日期　　　修改目的
 *<br>--------------------------------------------------------
 */

package lca.ap;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import util.*;
import util.report.ReportEnvironment;
import util.report.TableModelReportEnvironment;
import util.report.TableModelReportGenerator;

import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.swing.table.DefaultTableModel;

public class LCAAP230F extends QueryBean {

    String txtcity_no;
    String txtunit;
    String noLoginDays;
    String isStop;
    String isSendMail;
    String isManager;
    String mailSubj;
    String mailMsg;
    
    String filestoreLocation;
    String reportLocation;
    
    ServletContext context;
    Vector toStopAcct = new Vector();	// 要停用的帳號
    Vector toSendMail = new Vector();	// 要寄信的mail

    public String genReport() throws Exception {
        TableModelReportEnvironment env = new TableModelReportEnvironment();
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
        Vector columns=new Vector();//這裡宣告是為了重新塞table表頭
         //select s.KCNT from RKEYN s,  etecuser a where s.KCDE_1='45' and s.KCDE_2=substring(a.unit,1,1);
        
        java.io.File outputFile = null;
        String vid = "";//亂數產生的資料夾名稱
        
        try {
        	
			columns.addElement("CTY");
			columns.addElement("UNITC");
			columns.addElement("user_id");
			columns.addElement("user_login_time");
			columns.addElement("user_mail");
			model = getResultModel();
			
            env.setTableModel(model);
            env.setJasperFile(this.getContext().getRealPath("/report/lca/ap/lcaap230.jasper"));
            env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
            
            HashMap parms = new HashMap();
            parms.put("title", "各單位管理清冊");
            
            String[] city_unit= getCityUnit();
            StringBuffer printer_condition = new StringBuffer("查詢條件：");
            printer_condition.append(this.getTxtcity_no().equals("")? "" : city_unit[0] + ", ");
            printer_condition.append(this.getTxtunit().equals("")? "" : city_unit[1] + ", ");
            printer_condition.append("超過" + this.getNoLoginDays() + "天未登入").append(",");
            printer_condition.append(this.getIsStop().equals("N") ? "不" : "").append("停用帳號").append(",");
            printer_condition.append(this.getIsSendMail().equals("N") ? "不" : "").append("寄送mail").append(",");
            
            if(this.getIsManager().equals("A") && this.getIsManager().equals("")){
            	printer_condition.append("全部使用者");
            }else if(this.getIsManager().equals("Y") ){
            	printer_condition.append("管理者");
            }else if(this.getIsManager().equals("N")){
            	printer_condition.append("一般使用者");
            }
            
            String print_date = "製表時間："
                    + Datetime.getYYYMMDD().substring(0, 3) + "/"
                    + Datetime.getYYYMMDD().substring(3, 5) + "/"
                    + Datetime.getYYYMMDD().substring(5) + "   "
                    + Datetime.getHHMMSS().substring(0, 2) + ":"
                    + Datetime.getHHMMSS().substring(2, 4) + ":"
                    + Datetime.getHHMMSS().substring(4);
            parms.put("printer_condition", printer_condition + "\n" + print_date);
            
            TableModelReportGenerator generator = new TableModelReportGenerator(env);
            vid = Common.getVMID();
            File tempDirectory = new File(this.getReportLocation(), vid);
            System.out.println("tempDirectory1:"+tempDirectory.toString());
            tempDirectory.mkdirs();
//System.out.println("this.getReportLocation():"+this.getReportLocation());
//System.out.println("tempDirectory:"+tempDirectory.toString());
//System.out.println("java.io.File.separator :"+java.io.File.separator);
            outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator +""+ "eform4_20.xls");
            generator.reportToFile(outputFile, parms);
                
        } catch (Exception x) {
//            Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime
//                    .getHHMMSS(), this.getQry_seq());
            System.out.println("異常==>" + x.toString());
            x.printStackTrace();
        }
        
        if (outputFile == null) {
        	return "";
        }
        
        return vid;
    }

    
    public DefaultTableModel getResultModel() throws Exception {
    	
    	Date dNow = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	String NowDT=formatter.format(dNow);
    	
        DefaultTableModel model = new javax.swing.table.DefaultTableModel();
        Database sdb = new Database();
        Vector columns = new Vector();
//        Common.insertDL_LOG(qry_seq, "H", this.getUserID(), this.getUnitID(),
//                qry, this.getPrint_type(), qry_date_start, qry_time_start);
        
        columns.addElement("CTY");
		columns.addElement("UNITC");
		columns.addElement("user_id");
		columns.addElement("user_login_time");
		columns.addElement("user_mail");
		columns.addElement("isManager");
		columns.addElement("isStop");
	
		
        try {
            Vector rowData = new Vector();
            
        	StringBuffer sql = new StringBuffer("select ");
        	sql.append(" (select s.KCNT from RKEYN s where s.KCDE_1='45' and s.KCDE_2=substring(a.unit,1,1))as CTY,");
        	//sql.append(" (select s.KCNT from RKEYN s where s.KCDE_1='55' and substring(KRMK,1,1)=substring(a.unit,1,1) and s.KCDE_2=a.unit)as UNITC,");
        	sql.append(" (select s.KCNT from RKEYN s where s.KCDE_1='55' and s.KRMK=a.unit)as UNITC,");
        	sql.append(" a.user_id,a.user_login_time,a.user_mail");
        	sql.append(" ,(CASE ISNULL(a.isStop,'') WHEN 'Y' THEN '是' ELSE '否' END)as isStopYN ");
        	sql.append(" ,(CASE ISNULL(a.isManager,'') WHEN 'Y' THEN '是' ELSE '否' END)as isManagerYN ");
        	sql.append(" from etecuser a where 1=1 ");
        	if (!this.getTxtcity_no().equals("")) {
        		sql.append(" and substring(a.unit,1,1)=").append(Common.sqlChar(this.getTxtcity_no()));
        	}
        	if (!this.getTxtunit().equals("")) {
        		sql.append(" and a.unit = ").append(Common.sqlChar(this.getTxtunit()));
        	}
        	if (!this.getIsManager().equals("") && !this.getIsManager().equals("A")) {
        		sql.append(" and ISNULL(a.isManager,'') = ").append(Common.sqlChar(this.getIsManager()));
        	}
        	sql.append(" and (");
        	sql.append("     (a.user_login_time < (SELECT DATEADD(day,-" + this.getNoLoginDays() + ",getdate())))");
        	sql.append("      or ");
        	sql.append("     (ISNULL(a.user_login_time,'')='')");
        	sql.append(" ) order by a.unit");
        	
        	ResultSet rs = sdb.querySQL(sql.toString());
        	Vector data;
        	while (rs.next()) {
        		data = new Vector();
        		data.add(Common.get(rs.getString("CTY")));
        		data.add(Common.get(rs.getString("UNITC")));
        		data.add(Common.get(rs.getString("user_id")));
        		data.add(Common.get(rs.getString("user_login_time")));
        		data.add(Common.get(rs.getString("user_mail")));
        		data.add(Common.get(rs.getString("isManagerYN")));
        		data.add(Common.get(rs.getString("isStopYN")));
        		
        		
        		rowData.add(data);
        		
        		// 停用帳號設定
        		if (this.getIsStop().equals("Y")) {
        			String stopSQL = "update etecuser set " +
        					" isStop='Y' " +
        					" ,update_user="+ Common.sqlChar(this.getUserID())+
        					" ,update_time="+ Common.sqlChar(NowDT)+
        					" where user_id=" + Common.sqlChar(rs.getString("user_id"));
        			toStopAcct.addElement(stopSQL);	
        		}
        		
        		// 寄送email設定
        		if (this.getIsSendMail().equals("Y") && !Common.get(rs.getString("user_mail")).equals("")) {
        			String stopSQL = "insert into MailCase(MailDate,MailTime,MailAddr,sendType,MailSubj,MailMsg,editID,editDate,editTime)"
        					+ "values("
        					+ Common.sqlChar(Datetime.getYYYMMDD()) + ", "
        					+ Common.sqlChar(Datetime.getHHMMSS()) + ", "
        					+ Common.sqlChar(rs.getString("user_mail")) + ", "
        					+ "'1', "
        					+ Common.sqlChar(this.getMailSubj()) + ", "
        					+ Common.sqlChar(this.getMailMsg()) + ", "
        					+ Common.sqlChar(this.getUserID()) + ", "
        					+ Common.sqlChar(Datetime.getYYYMMDD()) + ", "
        					+ Common.sqlChar(Datetime.getHHMMSS())
        					+ ")";
        			toSendMail.addElement(stopSQL);	
        		}
        	}
        	rs.getStatement().close();
            rs.close();
            
            model.setDataVector(rowData, columns);
            this.setState("init");
            
            // 執行
            if (this.getIsStop().equals("Y")) {
            	sdb.excuteSQL(toStopAcct);
            }
            if (this.getIsSendMail().equals("Y")) {
            	sdb.excuteSQL(toSendMail);
            }
            
        	
            if (rowData.size() == 0) {
            	data = new Vector();
                data.addElement("");
                data.addElement("");
                data.addElement("無符合條件");
                data.addElement("");
                rowData.add(data);
            }
            
        } catch (Exception x) {
//            Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime
//                    .getHHMMSS(), this.getQry_seq());
            x.printStackTrace();
        } finally {
            sdb.closeAll();
        }
        return model;
    }// method getResultModel end

    public String[] getCityUnit() throws Exception {
        String[] printer_city_unit = new String[2];
        Database sdb = new Database();
        try {
        	if(!"".equals(this.getTxtcity_no())){
        		String country = sdb
                    	.getLookupField("select kcnt from rkeyn where kcde_1='45' and kcde_2="
                            + Common.sqlChar(this.getTxtcity_no().substring(0, 1)));
            	printer_city_unit[0] = country;
        	}
        	
        	if(!"".equals(this.getTxtunit())){
        		String cht_unit = sdb
                    	.getLookupField("select kcnt from rkeyn where kcde_1='55' and kcde_2='01' and krmk="
                            + Common.sqlChar(this.getTxtunit()));
            	printer_city_unit[1] = cht_unit;
        	}
        	
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("異常==>" + e.toString());
        } finally {
            sdb.closeAll();
        }
        return printer_city_unit;
    }

    public String getTxtcity_no() {
        return checkGet(txtcity_no);
    }

    public void setTxtcity_no(String txtcity_no) {
        this.txtcity_no = checkSet(txtcity_no);
    }

    public String getTxtunit() {
        return checkGet(txtunit);
    }

    public void setTxtunit(String txtunit) {
        this.txtunit = checkSet(txtunit);
    }
    
    public String getNoLoginDays() {
		return checkGet(noLoginDays);
	}

	public void setNoLoginDays(String noLoginDays) {
		this.noLoginDays = checkSet(noLoginDays);
	}

	public String getIsStop() {
		return checkGet(isStop);
	}

	public void setIsStop(String isStop) {
		this.isStop = checkSet(isStop);
	}

	public String getIsSendMail() {
		return checkGet(isSendMail);
	}

	public void setIsSendMail(String isSendMail) {
		this.isSendMail = checkSet(isSendMail);
	}
	
	public String getIsManager() {
		return checkGet(isManager);
	}

	public void setIsManager(String s) {
		this.isManager = checkSet(s);
	}

	public String getMailSubj() {
		return checkGet(mailSubj);
	}

	public void setMailSubj(String mailSubj) {
		this.mailSubj = checkSet(mailSubj);
	}

	public String getMailMsg() {
		return checkGet(mailMsg);
	}

	public void setMailMsg(String mailMsg) {
		this.mailMsg = checkSet(mailMsg);
	}

	public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
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
}
