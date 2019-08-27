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

import java.sql.ResultSet;

import util.*;

public class LCAAP210F extends QueryBean {

    String mail_ID;
    String mail_PWD;
    String mail_SMTP;
    String mail_Addr;
    String mail_PORT;
    String mail_SSL;
    String mail_TLS;
    String editID;
    String editDate;
    String editTime;
    String testMail;
    
	public String getMail_ID() {return checkGet(mail_ID);}
	public void setMail_ID(String s) {this.mail_ID = checkSet(s);}
	public String getMail_PWD() {return checkGet(mail_PWD);}
	public void setMail_PWD(String s) {this.mail_PWD = checkSet(s);}
	public String getMail_SMTP() {return checkGet(mail_SMTP);}
	public void setMail_SMTP(String s) {this.mail_SMTP = checkSet(s);}	
	public String getMail_Addr() {return checkGet(mail_Addr);}
	public void setMail_Addr(String s) {this.mail_Addr = checkSet(s);}
	public String getMail_PORT() {return checkGet(mail_PORT);}
	public void setMail_PORT(String s) {this.mail_PORT = checkSet(s);}
	public String getMail_SSL() {return checkGet(mail_SSL);}
	public void setMail_SSL(String s) {this.mail_SSL = checkSet(s);}
	public String getMail_TLS() {return checkGet(mail_TLS);}
	public void setMail_TLS(String s) {this.mail_TLS = checkSet(s);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String s) {this.editID = checkSet(s);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String s) {this.editDate = checkSet(s);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String s) {this.editTime = checkSet(s);}
	public String getTestMail() {return checkGet(testMail);}
	public void setTestMail(String s) {this.testMail = checkSet(s);}
	
	

	public LCAAP210F queryMailSet() throws Exception{
		LCAAP210F obj = this;
    	Database qdb = new Database();
        try {
            String qsql = "select Mail_ID,Mail_PWD,Mail_SMTP,Mail_Addr,Mail_PORT,Mail_SSL,Mail_TLS,editID,editDate,editTime from MailSet";
            ResultSet rs = qdb.querySQL(qsql, true);
            if (rs.next()) {
            	obj.setMail_ID(rs.getString("Mail_ID"));
            	obj.setMail_PWD(util.EnBase64.decodeBASE64(rs.getString("Mail_PWD")));
            	obj.setMail_SMTP(rs.getString("Mail_SMTP"));
            	obj.setMail_Addr(rs.getString("Mail_Addr"));
            	obj.setMail_PORT(rs.getString("Mail_PORT"));
            	obj.setMail_SSL(rs.getString("Mail_SSL"));
            	obj.setMail_TLS(rs.getString("Mail_TLS"));
            	obj.setEditID(rs.getString("editID"));
            	obj.setEditDate(Common.formatYYYMMDD(rs.getString("editDate")));
            	obj.setEditTime(Common.formatHHMMSS(rs.getString("editTime")));
            }
            rs.getStatement().close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	qdb.closeAll();
        }
        return obj;
    }
	
    public String saveEmail() throws Exception {
    	Database sdb = new Database();
        String msg = "";
        try {
            String[] saveSQL = new String[2];
            saveSQL[0]="delete from MailSet";
            saveSQL[1]="insert into MailSet(" +
            		"Mail_ID," +
            		"Mail_PWD," +
            		"Mail_SMTP," +
            		"Mail_Addr," +
            		"Mail_PORT," +
            		"Mail_SSL," +
            		"Mail_TLS," +
            		"editID," +
            		"editDate," +
            		"editTime" +
            		")values(" +
            		Common.sqlChar(getMail_ID())+","+
            		Common.sqlChar(util.EnBase64.encodeBASE64(getMail_PWD()))+","+
            		Common.sqlChar(getMail_SMTP())+","+
            		Common.sqlChar(getMail_Addr())+","+
            		Common.sqlChar(getMail_PORT())+","+
            		Common.sqlChar(getMail_SSL())+","+
            		Common.sqlChar(getMail_TLS())+","+
            		Common.sqlChar(getEditID())+","+
            		Common.sqlChar(util.Datetime.getYYYMMDD())+","+
            		Common.sqlChar(util.Datetime.getHHMMSS())
            		+")";
            sdb.excuteSQL(saveSQL);
            msg = "存檔成功";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "存檔失敗";
        } finally {
            sdb.closeAll();
        }
        return msg;
    }
}
