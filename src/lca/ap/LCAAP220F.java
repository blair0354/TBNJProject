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
import java.util.ArrayList;
import java.util.List;

import util.*;

public class LCAAP220F extends QueryBean {

    String mailKey;
    String mailDate;
    String mailAddr;
    String mailSubj;
    String mailMsg;
    String mailTimeH;
    String mailTimeM;
    String mailTimeS;
    
    String mailDateTime;
    String sendDateTime;
    String sendType;
    
    String q_mailDate_s;
    String q_mailDate_e;
    String q_sendType;
    
    public ArrayList queryAll() throws Exception {
    	ArrayList<String[]> list = new ArrayList<String[]>();
    	Database sdb = new Database();
    	StringBuffer sql = new StringBuffer("select MailKey,MailDate,MailTime,MailAddr,sendType,sendDate,sendTime,MailSubj");
    	sql.append(" from MailCase where 1=1");
    	
    	if (!this.getQ_mailDate_s().equals("")) {
    		sql.append(" and MailDate >= ").append(Common.sqlChar(this.getQ_mailDate_s()));
		}
    	if (!this.getQ_mailDate_e().equals("")) {
    		sql.append(" and MailDate <= ").append(Common.sqlChar(this.getQ_mailDate_e()));
		}
    	if (!this.getQ_sendType().equals("")) {
    		sql.append(" and sendType = ").append(Common.sqlChar(this.getQ_sendType()));
		}
    	
    	//sql.append(" order by MailDate,MailTime,MailKey");
    	sql.append(" order by MailDate desc,MailTime desc,MailKey desc");
    	
    	try {
    		ResultSet rs = sdb.querySQL(sql.toString(), true);
        	String[] rowArray;
        	String sendTypeStr;
        	while(rs.next()) {
        		rowArray = new String[7];
        		rowArray[0] = Common.get(rs.getString("MailKey"));

        		sendTypeStr = Common.get(rs.getString("sendType"));
        		if (sendTypeStr.equals("1")) {
        			sendTypeStr = "待發送";
        		} else if (sendTypeStr.equals("2")) {
        			sendTypeStr = "已發送";
        		} else if (sendTypeStr.equals("3")) {
        			sendTypeStr = "發送失敗";
        		}
        		rowArray[1] = sendTypeStr;
        		rowArray[2] = Common.formatYYYMMDD(rs.getString("MailDate"), 4) + " " + Common.formatHHMMSS(rs.getString("MailTime"));
        		rowArray[3] = Common.formatYYYMMDD(rs.getString("sendDate"), 4) + " " + Common.formatHHMMSS(rs.getString("sendTime"));
        		rowArray[4] = Common.get(rs.getString("MailAddr"));
        		rowArray[5] = Common.get(rs.getString("MailSubj"));
        		rowArray[6] = "<input type=\"button\" onclick=\"checkState(this.name, '" + Common.get(rs.getString("MailKey")) + "');\" value=\"詳細資料\" name=\"queryOne\">";
        		
        		list.add(rowArray);
        	}
    	} catch (Exception e) {
            e.printStackTrace();
        } finally {
            sdb.closeAll();
        }
    	
    	return list;
    }
    
    public void queryOne() throws Exception {
    	Database sdb = new Database();
    	String sql = "select MailDate,MailTime,MailAddr,sendDate,sendTime,"
    			+ " (CASE sendType WHEN '1' THEN '待發送' WHEN '2' THEN '已發送' WHEN '3' THEN '發送失敗' ELSE '' END)as sendTypeC,"
    			+ " MailSubj,MailMsg,editID,editDate,editTime"
    			+ " from MailCase where MailKey=" + this.getMailKey();
    	try {
    		ResultSet rs = sdb.querySQL(sql.toString(), true);
        	while(rs.next()) {
        		this.setMailDateTime(Common.formatYYYMMDD(rs.getString("MailDate"), 4) + " " + Common.formatHHMMSS(rs.getString("MailTime")));
        		this.setSendDateTime(Common.formatYYYMMDD(rs.getString("sendDate"), 4) + " " + Common.formatHHMMSS(rs.getString("sendTime")));
        		this.setSendType(Common.get(rs.getString("sendTypeC")));
        		this.setMailAddr(Common.get(rs.getString("MailAddr")));
        		this.setMailSubj(Common.get(rs.getString("MailSubj")));
        		this.setMailMsg(Common.get(rs.getString("MailMsg")));
        	}
    	} catch (Exception e) {
            e.printStackTrace();
        } finally {
            sdb.closeAll();
        }
    }

    public String saveEmail() throws Exception {
    	Database sdb = new Database();
        String msg = "";

        try {
            StringBuffer saveSQL = new StringBuffer("insert into MailCase(");
            saveSQL.append("MailDate,MailTime,MailAddr,sendType,MailSubj,MailMsg,editID,editDate,editTime)");
            saveSQL.append(" values (");
            saveSQL.append(" '").append(this.getMailDate()).append("', ");
            saveSQL.append(" '").append(this.getMailTimeH() + this.getMailTimeM() + this.getMailTimeS()).append("', ");
            saveSQL.append(" '").append(this.getMailAddr()).append("', ");
            saveSQL.append(" '1', ");
            saveSQL.append(" '").append(this.getMailSubj()).append("', ");
            saveSQL.append(" '").append(this.getMailMsg()).append("', ");
            saveSQL.append(" '").append(this.getUserID()).append("', ");
            saveSQL.append(" '").append(Datetime.getYYYMMDD()).append("', ");
            saveSQL.append(" '").append(Datetime.getHHMMSS()).append("')");
            
            sdb.excuteSQL(new String[]{saveSQL.toString()});
            msg = "存檔成功";
            
        } catch (Exception e) {
            e.printStackTrace();
            msg = "存檔失敗";
        } finally {
            sdb.closeAll();
        }
        return msg;
    }
    
	public String getMailKey() {
		return checkGet(mailKey);
	}

	public void setMailKey(String mailKey) {
		this.mailKey = checkSet(mailKey);
	}

	public String getMailDate() {
		return checkGet(mailDate);
	}

	public void setMailDate(String mailDate) {
		this.mailDate = checkSet(mailDate);
	}

	public String getMailAddr() {
		return checkGet(mailAddr);
	}

	public void setMailAddr(String mailAddr) {
		this.mailAddr = checkSet(mailAddr);
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

	public String getMailTimeH() {
		return checkGet(mailTimeH);
	}

	public void setMailTimeH(String mailTimeH) {
		this.mailTimeH = checkSet(mailTimeH);
	}

	public String getMailTimeM() {
		return checkGet(mailTimeM);
	}

	public void setMailTimeM(String mailTimeM) {
		this.mailTimeM = checkSet(mailTimeM);
	}

	public String getMailTimeS() {
		return checkGet(mailTimeS);
	}

	public void setMailTimeS(String mailTimeS) {
		this.mailTimeS = checkSet(mailTimeS);
	}

	public String getSendType() {
		return checkGet(sendType);
	}

	public void setSendType(String sendType) {
		this.sendType = checkSet(sendType);
	}

	public String getMailDateTime() {
		return checkGet(mailDateTime);
	}

	public void setMailDateTime(String mailDateTime) {
		this.mailDateTime = checkSet(mailDateTime);
	}

	public String getSendDateTime() {
		return checkGet(sendDateTime);
	}

	public void setSendDateTime(String sendDateTime) {
		this.sendDateTime = checkSet(sendDateTime);
	}

	public String getQ_mailDate_s() {
		return checkGet(q_mailDate_s);
	}

	public void setQ_mailDate_s(String q_mailDate_s) {
		this.q_mailDate_s = checkSet(q_mailDate_s);
	}

	public String getQ_mailDate_e() {
		return checkGet(q_mailDate_e);
	}

	public void setQ_mailDate_e(String q_mailDate_e) {
		this.q_mailDate_e = checkSet(q_mailDate_e);
	}

	public String getQ_sendType() {
		return checkGet(q_sendType);
	}

	public void setQ_sendType(String q_sendType) {
		this.q_sendType = checkSet(q_sendType);
	}
	
}
