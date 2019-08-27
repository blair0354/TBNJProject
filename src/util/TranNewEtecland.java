package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.PasswordAuthentication;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import util.Common;
import util.Database;
import util.Datetime;
import util.QueryBean;

public class TranNewEtecland extends QueryBean {
    
    
    private FileWriter writer = null;
    String path;
    
    
    public TranNewEtecland(){
        initLogWriter();
    }

    
    
    private void initLogWriter() {
        path = String.valueOf(ClassLoader.getSystemResource("")).replaceAll("file:/","").replaceAll("WEB-INF/classes/","")+ "LOG/";
        path+="TranNewEtecland_" + Datetime.getYYYMMDD() + ".log";
    }
    
    public boolean log(String text){
        if(text.equals("")){
            return false;
        }
        File file = new File(path);//建立檔案，準備寫檔
        try{
            BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"UTF-8"));
            bufWriter.write(text+"\r\n");
            bufWriter.close();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("寫檔發生錯誤!");
            return false;
        }
        return true;
    }


    public void finalize(){
        closeWriter(writer);
    }
    
    private void closeWriter(Writer writer){
        if(writer != null){
            try{
                writer.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    public void doTranDb() {
    	
    	DatabaseTran dbTran = new DatabaseTran();
		Connection connection = dbTran.getConnection();

    	Database db=new Database();
   	
        try{
        	
            ResultSet rs_bs_log = db.querySQL("select * from BS_LOG");
            int count=0;
            String sql_BS_LOG="insert into BS_LOG("+
        			" QRY_DATE_START"+
        			",QRY_TIME_START"+
        			",QRY_DATE_END"+
        			",QRY_TIME_END"+
        			",USERID"+
        			",UNITID"+
        			",IP"+
        			",CON"+
        			",QRY"+
        			",QRY_MSG"+
        			",RCV_YR"+
        			",RCV_WORD"+
        			",RCV_NO"+
        			",SNO"+
        			",SNAME"+
        			",SNO1"+
        			",SNAME1"+
        			",QRY_PURPOSE"+
        			",QRY_OPER"+
        			",LIDN"+
        			",LNAME"+
        			",QRY_PURPOSE01"+
        			",QRY_PURPOSE02"+
        			",QRY_PURPOSE03"+
        			",QRY_PURPOSE03A"+
        			",QRY_SEQ"+
        			",QRY_USERTYPE"+
        			")values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement psBS_LOG = connection.prepareStatement(sql_BS_LOG);
            final int batchSizeIns = 1000;
            int countIns = 0;
            while(rs_bs_log.next()){   	
            	psBS_LOG.setString(1,rs_bs_log.getString("QRY_DATE_START"));
            	psBS_LOG.setString(2,rs_bs_log.getString("QRY_TIME_START"));
            	psBS_LOG.setString(3,rs_bs_log.getString("QRY_DATE_END"));
            	psBS_LOG.setString(4,rs_bs_log.getString("QRY_TIME_END"));
            	psBS_LOG.setString(5,rs_bs_log.getString("USERID"));
            	psBS_LOG.setString(6,rs_bs_log.getString("UNITID"));
            	psBS_LOG.setString(7,rs_bs_log.getString("IP"));
            	psBS_LOG.setString(8,rs_bs_log.getString("CON"));
            	psBS_LOG.setString(9,rs_bs_log.getString("QRY"));
            	psBS_LOG.setString(10,rs_bs_log.getString("QRY_MSG"));
            	psBS_LOG.setString(11,rs_bs_log.getString("RCV_YR"));
            	psBS_LOG.setString(12,rs_bs_log.getString("RCV_WORD"));
            	psBS_LOG.setString(13,rs_bs_log.getString("RCV_NO"));
            	psBS_LOG.setString(14,rs_bs_log.getString("SNO"));
            	psBS_LOG.setString(15,rs_bs_log.getString("SNAME"));
            	psBS_LOG.setString(16,rs_bs_log.getString("SNO1"));
            	psBS_LOG.setString(17,rs_bs_log.getString("SNAME1"));
            	psBS_LOG.setString(18,rs_bs_log.getString("QRY_PURPOSE"));
            	psBS_LOG.setString(19,rs_bs_log.getString("QRY_OPER"));
            	psBS_LOG.setString(20,rs_bs_log.getString("LIDN"));
            	psBS_LOG.setString(21,rs_bs_log.getString("LNAME"));
            	psBS_LOG.setString(22,rs_bs_log.getString("QRY_PURPOSE01"));
            	psBS_LOG.setString(23,rs_bs_log.getString("QRY_PURPOSE02"));
            	psBS_LOG.setString(24,rs_bs_log.getString("QRY_PURPOSE03"));
            	psBS_LOG.setString(25,rs_bs_log.getString("QRY_PURPOSE03A"));
            	psBS_LOG.setString(26,rs_bs_log.getString("QRY_SEQ"));
            	psBS_LOG.setString(27,rs_bs_log.getString("QRY_USERTYPE"));
            	psBS_LOG.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		psBS_LOG.executeBatch();
				}
            }
            psBS_LOG.executeBatch(); // insert remaining records
            psBS_LOG.close();			
            rs_bs_log.getStatement().close();
            rs_bs_log.close();
            
            
            ResultSet rs_dl_log = db.querySQL("select * from DOWNLOAD_LOG");
            count=0;
            String sql_dl_log="insert into DOWNLOAD_LOG("
            		+" DQRY_SEQ"
            		+",DQRY_FLAG"
            		+",DQRY_USERID"
            		+",DQRY_UNITID"
            		+",DQRY"
            		+",DQRY_STATUS"
            		+",DQRY_FILE_STYLE"
            		+",DQRY_FILE_NAME"
            		+",DQRY_DATE_START"
            		+",DQRY_TIME_START"
            		+",DQRY_DATE_END"
            		+",DQRY_TIME_END"
            		+",DOWNLOAD_DATE"
            		+",DOWNLOAD_TIME"
            		+")values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps_dl_log = connection.prepareStatement(sql_dl_log);
            countIns = 0;
            while(rs_dl_log.next()){   	
            	ps_dl_log.setString(1,rs_dl_log.getString("DQRY_SEQ"));
            	ps_dl_log.setString(2,rs_dl_log.getString("DQRY_FLAG"));
            	ps_dl_log.setString(3,rs_dl_log.getString("DQRY_USERID"));
            	ps_dl_log.setString(4,rs_dl_log.getString("DQRY_UNITID"));
            	ps_dl_log.setString(5,rs_dl_log.getString("DQRY"));
            	ps_dl_log.setString(6,rs_dl_log.getString("DQRY_STATUS"));
            	ps_dl_log.setString(7,rs_dl_log.getString("DQRY_FILE_STYLE"));
            	ps_dl_log.setString(8,rs_dl_log.getString("DQRY_FILE_NAME"));
            	ps_dl_log.setString(9,rs_dl_log.getString("DQRY_DATE_START"));
            	ps_dl_log.setString(10,rs_dl_log.getString("DQRY_TIME_START"));
            	ps_dl_log.setString(11,rs_dl_log.getString("DQRY_DATE_END"));
            	ps_dl_log.setString(12,rs_dl_log.getString("DQRY_TIME_END"));
            	ps_dl_log.setString(13,rs_dl_log.getString("DOWNLOAD_DATE"));
            	ps_dl_log.setString(14,rs_dl_log.getString("DOWNLOAD_TIME"));
            	ps_dl_log.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		ps_dl_log.executeBatch();
				}
            }
            ps_dl_log.executeBatch(); // insert remaining records
            ps_dl_log.close();			
            rs_dl_log.getStatement().close();
            rs_dl_log.close();
            
            
            
            ResultSet rs_etecuser = db.querySQL("select * from etecuser");
            count=0;
            String sql_etecuer="insert into etecuser("
            		+" user_id"
            		+",user_name"
            		+",password"
            		+",unit"
            		+",update_user"
            		+",update_time"
            		+",isStop"
            		+",isManager"
            		+",user_mail"
            		+",user_login_time"
            		+")values(?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement ps_etecuser = connection.prepareStatement(sql_etecuer);
            countIns = 0;
            while(rs_etecuser.next()){   	
            	ps_etecuser.setString(1,rs_etecuser.getString("user_id"));
            	ps_etecuser.setString(2,rs_etecuser.getString("user_name"));
            	ps_etecuser.setString(3,rs_etecuser.getString("password"));
            	ps_etecuser.setString(4,rs_etecuser.getString("unit"));
            	ps_etecuser.setString(5,rs_etecuser.getString("update_user"));
            	ps_etecuser.setString(6,rs_etecuser.getString("update_time"));
            	ps_etecuser.setString(7,rs_etecuser.getString("isStop"));
            	ps_etecuser.setString(8,rs_etecuser.getString("isManager"));
            	ps_etecuser.setString(9,rs_etecuser.getString("user_mail"));
            	ps_etecuser.setString(10,rs_etecuser.getString("user_login_time"));
            	ps_etecuser.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		ps_etecuser.executeBatch();
				}
            }
            ps_etecuser.executeBatch(); // insert remaining records
            ps_etecuser.close();			
            rs_etecuser.getStatement().close();
            rs_etecuser.close();
            
            
            ResultSet rs_etecuser_log = db.querySQL("select * from etecuser_log");
            count=0;
            String sql_etecuer_log="insert into etecuser_log("
            		+" user_id"
            		+",password"
            		+",update_time"
            		+")values(?,?,?)";
            
            PreparedStatement ps_etecuser_log = connection.prepareStatement(sql_etecuer_log);
            countIns = 0;
            while(rs_etecuser_log.next()){   	
            	ps_etecuser_log.setString(1,rs_etecuser_log.getString("user_id"));
            	ps_etecuser_log.setString(2,rs_etecuser_log.getString("password"));
            	ps_etecuser_log.setString(3,rs_etecuser_log.getString("update_time"));
            	ps_etecuser_log.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		ps_etecuser_log.executeBatch();
				}
            }
            ps_etecuser_log.executeBatch(); // insert remaining records
            ps_etecuser_log.close();			
            rs_etecuser_log.getStatement().close();
            rs_etecuser_log.close();
            
            
            
            ResultSet rs_MailCase = db.querySQL("select * from MailCase");
            count=0;
            String sql_MailCase="insert into MailCase("
            		//+" MailKey"
            		+" MailDate"
            		+",MailTime"
            		+",MailAddr"
            		+",sendType"
            		+",sendDate"
            		+",sendTime"
            		+",MailSubj"
            		+",MailMsg"
            		+",editID"
            		+",editDate"
            		+",editTime"
            		+")values("
            		+"?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement ps_MailCase = connection.prepareStatement(sql_MailCase);
            countIns = 0;
            while(rs_MailCase.next()){   	
            	//ps_MailCase.setString(1,rs_MailCase.getString("MailKey"));
            	ps_MailCase.setString(1,rs_MailCase.getString("MailDate"));
            	ps_MailCase.setString(2,rs_MailCase.getString("MailTime"));
            	ps_MailCase.setString(3,rs_MailCase.getString("MailAddr"));
            	ps_MailCase.setString(4,rs_MailCase.getString("sendType"));
            	ps_MailCase.setString(5,rs_MailCase.getString("sendDate"));
            	ps_MailCase.setString(6,rs_MailCase.getString("sendTime"));
            	ps_MailCase.setString(7,rs_MailCase.getString("MailSubj"));
            	ps_MailCase.setString(8,rs_MailCase.getString("MailMsg"));
            	ps_MailCase.setString(9,rs_MailCase.getString("editID"));
            	ps_MailCase.setString(10,rs_MailCase.getString("editDate"));
            	ps_MailCase.setString(11,rs_MailCase.getString("editTime"));
            	ps_MailCase.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		ps_MailCase.executeBatch();
				}
            }
            ps_MailCase.executeBatch(); // insert remaining records
            ps_MailCase.close();			
            rs_MailCase.getStatement().close();
            rs_MailCase.close();
            
            
            
            ResultSet rs_MailSet = db.querySQL("select * from MailSet");
            count=0;
            String sql_MailSet="insert into MailSet("
            		+" Mail_ID"
            		+",Mail_PWD"
            		+",Mail_SMTP"
            		+",Mail_Addr"
            		+",Mail_PORT"
            		+",Mail_SSL"
            		+",Mail_TLS"
            		+",editID"
            		+",editDate"
            		+",editTime"
            		+")values("
            		+"?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement ps_MailSet = connection.prepareStatement(sql_MailSet);
            countIns = 0;
            while(rs_MailSet.next()){   	
            	ps_MailSet.setString(1,rs_MailSet.getString("Mail_ID"));
            	ps_MailSet.setString(2,rs_MailSet.getString("Mail_PWD"));
            	ps_MailSet.setString(3,rs_MailSet.getString("Mail_SMTP"));
            	ps_MailSet.setString(4,rs_MailSet.getString("Mail_Addr"));
            	ps_MailSet.setString(5,rs_MailSet.getString("Mail_PORT"));
            	ps_MailSet.setString(6,rs_MailSet.getString("Mail_SSL"));
            	ps_MailSet.setString(7,rs_MailSet.getString("Mail_TLS"));
            	ps_MailSet.setString(8,rs_MailSet.getString("editID"));
            	ps_MailSet.setString(9,rs_MailSet.getString("editDate"));
            	ps_MailSet.setString(10,rs_MailSet.getString("editTime"));
            	ps_MailSet.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		ps_MailSet.executeBatch();
				}
            }
            ps_MailSet.executeBatch(); // insert remaining records
            ps_MailSet.close();			
            rs_MailSet.getStatement().close();
            rs_MailSet.close();
            
            
            ResultSet rs_RKEYN = db.querySQL("select * from RKEYN");
            count=0;
            String sql_RKEYN="insert into RKEYN("
            		+" KCDE_1"
            		+",KCDE_2"
            		+",KCNT"
            		+",KRMK"
            		+")values("
            		+"?,?,?,?)";
            
            PreparedStatement ps_RKEYN = connection.prepareStatement(sql_RKEYN);
            countIns = 0;
            while(rs_RKEYN.next()){   	
            	ps_RKEYN.setString(1,rs_RKEYN.getString("KCDE_1"));
            	ps_RKEYN.setString(2,rs_RKEYN.getString("KCDE_2"));
            	ps_RKEYN.setString(3,rs_RKEYN.getString("KCNT"));
            	ps_RKEYN.setString(4,rs_RKEYN.getString("KRMK"));
            	ps_RKEYN.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		ps_RKEYN.executeBatch();
				}
            }
            ps_RKEYN.executeBatch(); // insert remaining records
            ps_RKEYN.close();			
            rs_RKEYN.getStatement().close();
            rs_RKEYN.close();
            
            
            ResultSet rs_EFORM3_5_LOG = db.querySQL("select * from EFORM3_5_LOG");
            count=0;
            String sql_EFORM3_5_LOG="insert into EFORM3_5_LOG("
            		//+" ID"
            		+" USER_ID"
            		+",USER_IP"
            		+",DO_DATETIME"
            		+",DO_TYPE"
            		+",DO_ORAUSER"
            		+",RECRE_IDX_TAB"
            		+",RECRE_IDX_SQL"
            		+",RECRE_EXCEPTION"
            		+")values("
            		+"?,?,?,?,?,?,?,?)";
            
            PreparedStatement ps_EFORM3_5_LOG = connection.prepareStatement(sql_EFORM3_5_LOG);
            countIns = 0;
            while(rs_EFORM3_5_LOG.next()){   	
            	//ps_EFORM3_5_LOG.setString(1,rs_EFORM3_5_LOG.getString("ID"));
            	ps_EFORM3_5_LOG.setString(1,rs_EFORM3_5_LOG.getString("USER_ID"));
            	ps_EFORM3_5_LOG.setString(2,rs_EFORM3_5_LOG.getString("USER_IP"));
            	ps_EFORM3_5_LOG.setString(3,rs_EFORM3_5_LOG.getString("DO_DATETIME"));
            	ps_EFORM3_5_LOG.setString(4,rs_EFORM3_5_LOG.getString("DO_TYPE"));
            	ps_EFORM3_5_LOG.setString(5,rs_EFORM3_5_LOG.getString("DO_ORAUSER"));
            	ps_EFORM3_5_LOG.setString(6,rs_EFORM3_5_LOG.getString("RECRE_IDX_TAB"));
            	ps_EFORM3_5_LOG.setString(7,rs_EFORM3_5_LOG.getString("RECRE_IDX_SQL"));
            	ps_EFORM3_5_LOG.setString(8,rs_EFORM3_5_LOG.getString("RECRE_EXCEPTION"));
            	ps_EFORM3_5_LOG.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		ps_EFORM3_5_LOG.executeBatch();
				}
            }
            ps_EFORM3_5_LOG.executeBatch(); // insert remaining records
            ps_EFORM3_5_LOG.close();			
            rs_EFORM3_5_LOG.getStatement().close();
            rs_EFORM3_5_LOG.close();
            
            
            
            ResultSet rs_EFORM5_LOG = db.querySQL("select * from EFORM5_LOG");
            count=0;
            String sql_EFORM5_LOG="insert into EFORM5_LOG("
            		+" ID"
            		+",RPT_ID"
            		+",USER_ID"
            		+",PROCESS_STATE"
            		+",PROCESS_DATE_S"
            		+",PROCESS_DATE_E"
            		+",PROCESS_TIME_S"
            		+",PROCESS_TIME_E"
            		+",PROCESS_STATE_NOTE"
            		+")values("
            		+"?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement ps_EFORM5_LOG = connection.prepareStatement(sql_EFORM5_LOG);
            countIns = 0;
            while(rs_EFORM5_LOG.next()){   	
            	ps_EFORM5_LOG.setString(1,rs_EFORM5_LOG.getString("ID"));
            	ps_EFORM5_LOG.setString(2,rs_EFORM5_LOG.getString("RPT_ID"));
            	ps_EFORM5_LOG.setString(3,rs_EFORM5_LOG.getString("USER_ID"));
            	ps_EFORM5_LOG.setString(4,rs_EFORM5_LOG.getString("PROCESS_STATE"));
            	ps_EFORM5_LOG.setString(5,rs_EFORM5_LOG.getString("PROCESS_DATE_S"));
            	ps_EFORM5_LOG.setString(6,rs_EFORM5_LOG.getString("PROCESS_DATE_E"));
            	ps_EFORM5_LOG.setString(7,rs_EFORM5_LOG.getString("PROCESS_TIME_S"));
            	ps_EFORM5_LOG.setString(8,rs_EFORM5_LOG.getString("PROCESS_TIME_E"));
            	ps_EFORM5_LOG.setString(9,rs_EFORM5_LOG.getString("PROCESS_STATE_NOTE"));
            	ps_EFORM5_LOG.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		ps_EFORM5_LOG.executeBatch();
				}
            }
            ps_EFORM5_LOG.executeBatch(); // insert remaining records
            ps_EFORM5_LOG.close();			
            rs_EFORM5_LOG.getStatement().close();
            rs_EFORM5_LOG.close();
            
            
            
            ResultSet rs_EFORM6 = db.querySQL("select * from EFORM6");
            count=0;
            String sql_EFORM6="insert into EFORM6("
            		+" VNAME"
            		+",VFIELD"
            		+",VTABLE"
            		+",VTABLECN"
            		+")values("
            		+"?,?,?,?)";
            
            PreparedStatement ps_EFORM6 = connection.prepareStatement(sql_EFORM6);
            countIns = 0;
            while(rs_EFORM6.next()){   	
            	ps_EFORM6.setString(1,rs_EFORM6.getString("VNAME"));
            	ps_EFORM6.setString(2,rs_EFORM6.getString("VFIELD"));
            	ps_EFORM6.setString(3,rs_EFORM6.getString("VTABLE"));
            	ps_EFORM6.setString(4,rs_EFORM6.getString("VTABLECN"));
            	ps_EFORM6.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		ps_EFORM6.executeBatch();
				}
            }
            ps_EFORM6.executeBatch(); // insert remaining records
            ps_EFORM6.close();			
            rs_EFORM6.getStatement().close();
            rs_EFORM6.close();
            
            
            ResultSet rs_EFORM6_USER = db.querySQL("select * from EFORM6_USER");
            count=0;
            String sql_EFORM6_USER="insert into EFORM6_USER("
            		+" ORAUSER"
            		+",TRACTY"
            		+",TRAUNIT"
            		+")values("
            		+"?,?,?)";
            
            PreparedStatement ps_EFORM6_USER = connection.prepareStatement(sql_EFORM6_USER);
            countIns = 0;
            while(rs_EFORM6_USER.next()){   	
            	ps_EFORM6_USER.setString(1,rs_EFORM6_USER.getString("ORAUSER"));
            	ps_EFORM6_USER.setString(2,rs_EFORM6_USER.getString("TRACTY"));
            	ps_EFORM6_USER.setString(3,rs_EFORM6_USER.getString("TRAUNIT"));
            	ps_EFORM6_USER.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		ps_EFORM6_USER.executeBatch();
				}
            }
            ps_EFORM6_USER.executeBatch(); // insert remaining records
            ps_EFORM6_USER.close();			
            rs_EFORM6_USER.getStatement().close();
            rs_EFORM6_USER.close();
            
            
            ResultSet rs_EFORM7 = db.querySQL("select * from EFORM7");
            count=0;
            String sql_EFORM7="insert into EFORM7("
            		+" TQRY_TYPE"
            		+",TQRY_ID"
            		+",TQRY_NAME"
            		+",TQRY_YM"
            		+",IMP_TYPE"
            		+",IMP_YMD"
            		+",IMP_TIME"
            		+")values("
            		+"?,?,?,?,?,?,?)";
            
            PreparedStatement ps_EFORM7 = connection.prepareStatement(sql_EFORM7);
            countIns = 0;
            while(rs_EFORM7.next()){   	
            	ps_EFORM7.setString(1,rs_EFORM7.getString("TQRY_TYPE"));
            	ps_EFORM7.setString(2,rs_EFORM7.getString("TQRY_ID"));
            	ps_EFORM7.setString(3,rs_EFORM7.getString("TQRY_NAME"));
            	ps_EFORM7.setString(4,rs_EFORM7.getString("TQRY_YM"));
            	ps_EFORM7.setString(5,rs_EFORM7.getString("IMP_TYPE"));
            	ps_EFORM7.setString(6,rs_EFORM7.getString("IMP_YMD"));
            	ps_EFORM7.setString(7,rs_EFORM7.getString("IMP_TIME"));
            	ps_EFORM7.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		ps_EFORM7.executeBatch();
				}
            }
            ps_EFORM7.executeBatch(); // insert remaining records
            ps_EFORM7.close();			
            rs_EFORM7.getStatement().close();
            rs_EFORM7.close();
            

            ResultSet rs_EFORM8D = db.querySQL("select * from EFORM8D");
            count=0;
            String sql_EFORM8D="insert into EFORM8D("
            		+" IDXD_NO"
            		+",IDXD_CTY"
            		+",IDXD_LN"
            		+",IDXD_VName"
            		+",IDXD_YN"
            		+")values("
            		+"?,?,?,?,?)";
            
            PreparedStatement ps_EFORM8D = connection.prepareStatement(sql_EFORM8D);
            countIns = 0;
            while(rs_EFORM8D.next()){   	
            	ps_EFORM8D.setString(1,rs_EFORM8D.getString("IDXD_NO"));
            	ps_EFORM8D.setString(2,rs_EFORM8D.getString("IDXD_CTY"));
            	ps_EFORM8D.setString(3,rs_EFORM8D.getString("IDXD_LN"));
            	ps_EFORM8D.setString(4,rs_EFORM8D.getString("IDXD_VName"));
            	ps_EFORM8D.setString(5,rs_EFORM8D.getString("IDXD_YN"));
            	ps_EFORM8D.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		ps_EFORM8D.executeBatch();
				}
            }
            ps_EFORM8D.executeBatch(); // insert remaining records
            ps_EFORM8D.close();			
            rs_EFORM8D.getStatement().close();
            rs_EFORM8D.close();
            
            
            
            ResultSet rs_EFORM8M = db.querySQL("select * from EFORM8M");
            count=0;
            String sql_EFORM8M="insert into EFORM8M("
            		+" IDXM_NO"
            		+",IDXM_SDATE"
            		+",IDXM_STIME"
            		+",IDXM_EDATE"
            		+",IDXM_ETIME"
            		+",IDXM_FLAG"
            		+",IDXM_EDITID"
            		+")values("
            		+"?,?,?,?,?,?,?)";
            
            PreparedStatement ps_EFORM8M = connection.prepareStatement(sql_EFORM8M);
            countIns = 0;
            while(rs_EFORM8M.next()){   	
            	ps_EFORM8M.setString(1,rs_EFORM8M.getString("IDXM_NO"));
            	ps_EFORM8M.setString(2,rs_EFORM8M.getString("IDXM_SDATE"));
            	ps_EFORM8M.setString(3,rs_EFORM8M.getString("IDXM_STIME"));
            	ps_EFORM8M.setString(4,rs_EFORM8M.getString("IDXM_EDATE"));
            	ps_EFORM8M.setString(5,rs_EFORM8M.getString("IDXM_ETIME"));
            	ps_EFORM8M.setString(6,rs_EFORM8M.getString("IDXM_FLAG"));
            	ps_EFORM8M.setString(7,rs_EFORM8M.getString("IDXM_EDITID"));
            	ps_EFORM8M.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		ps_EFORM8M.executeBatch();
				}
            }
            ps_EFORM8M.executeBatch(); // insert remaining records
            ps_EFORM8M.close();			
            rs_EFORM8M.getStatement().close();
            rs_EFORM8M.close();
            
            
            ResultSet rs_ND_LOG = db.querySQL("select * from ND_LOG");
            count=0;
            String sql_ND_LOG="insert into ND_LOG("
            		+" NQRY_SEQ"
            		+",NQRY_ID"
            		+",NQRY_NAME"
            		+",NQRY_YM"
            		+")values("
            		+"?,?,?,?)";
            
            PreparedStatement ps_ND_LOG = connection.prepareStatement(sql_ND_LOG);
            countIns = 0;
            while(rs_ND_LOG.next()){   	
            	ps_ND_LOG.setString(1,rs_ND_LOG.getString("NQRY_SEQ"));
            	ps_ND_LOG.setString(2,rs_ND_LOG.getString("NQRY_ID"));
            	ps_ND_LOG.setString(3,rs_ND_LOG.getString("NQRY_NAME"));
            	ps_ND_LOG.setString(4,rs_ND_LOG.getString("NQRY_YM"));
            	ps_ND_LOG.addBatch();
            	
            	if(++countIns % batchSizeIns == 0) {
            		ps_ND_LOG.executeBatch();
				}
            }
            ps_ND_LOG.executeBatch(); // insert remaining records
            ps_ND_LOG.close();			
            rs_ND_LOG.getStatement().close();
            rs_ND_LOG.close();
            
            
            
			connection.close();
          
        }catch(Exception e){
            e.printStackTrace();
        } finally{
        	db.closeAll();
        }
    }
    
}
