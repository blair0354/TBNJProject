package sch.sch_SendMail;

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

public class SCH_SendMail extends QueryBean {
    
    
    private FileWriter writer = null;
    String path;
    
    
    public SCH_SendMail(){
        initLogWriter();
    }

//    public void log(String msg){
//        try{
//            writer.write(msg + "\r\n");
//            writer.flush();
//        }
//        catch(IOException e1){
//            e1.printStackTrace();
//        }
//    }
    
    
    private void initLogWriter() {
        path = String.valueOf(ClassLoader.getSystemResource("")).replaceAll("file:/","").replaceAll("WEB-INF/classes/","")+ "LOG/";
        path+="sch_sendMail_" + Datetime.getYYYMMDD() + ".log";
        //try{
            //writer = new FileWriter(path + "sch_sendMail_" + Datetime.getYYYMMDD()+ Datetime.getHHMMSS() + ".log");
        	//writer = new FileWriter(path + "sch_sendMail_" + Datetime.getYYYMMDD() + ".log");
        //}
        //catch(IOException e1){
        //    e1.printStackTrace();
        //}
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


    public static void main(String[] args) {
    	
        initProxool();
        
//        for(int i=0;i<args.length;i++){
//        	System.out.println("args:"+args[i]);
//		  }
        	
        ArrayList<Object> MailCaseList=new ArrayList<Object>();
        Database db=new Database();
        SCH_SendMail rp1 = new SCH_SendMail();
        	
			
       	try{
           	String sysDate=util.Datetime.getYYYMMDD();
           	String sysTime=util.Datetime.getHHMMSS();
           	rp1.log("email發送-開始時間:"+sysDate+" "+sysTime);
            ResultSet rs = db.querySQL("select * from MailCase where sendType='1' and (MailDate+MailTime)<= "+Common.sqlChar(sysDate+sysTime));
            int count=0;
            while(rs.next()){
                	
//                    System.out.println("");
//                    System.out.println("MailKey:"+rs.getString("MailKey"));
//                    System.out.println("MailDate:"+rs.getString("MailDate"));
//                    System.out.println("MailTime:"+rs.getString("MailTime"));
//                    System.out.println("MailAddr:"+rs.getString("MailAddr"));
//                    System.out.println("sendType:"+rs.getString("sendType"));
//                    System.out.println("sendDate:"+rs.getString("sendDate"));
//                    System.out.println("sendTime:"+rs.getString("sendTime"));
//                    System.out.println("MailSubj:"+rs.getString("MailSubj"));
//                    System.out.println("MailMsg:"+rs.getString("MailMsg"));
//                    System.out.println("editID:"+rs.getString("editID"));
//                    System.out.println("editDate:"+rs.getString("editDate"));
//                    System.out.println("editTime:"+rs.getString("editTime"));
//                    System.out.println("");
                    
            	String rowArray[]=new String[12];
            	rowArray[0]= Common.get(rs.getString("MailKey"));
            	rowArray[1]= Common.get(rs.getString("MailDate"));
            	rowArray[2]= Common.get(rs.getString("MailTime"));
                rowArray[3]= Common.get(rs.getString("MailAddr"));
                rowArray[4]= Common.get(rs.getString("sendType"));
                rowArray[5]= Common.get(rs.getString("sendDate"));
                rowArray[6]= Common.get(rs.getString("sendTime"));
                rowArray[7]= Common.get(rs.getString("MailSubj"));
                rowArray[8]= Common.get(rs.getString("MailMsg"));
                rowArray[9]= Common.get(rs.getString("editID"));
                rowArray[10]= Common.get(rs.getString("editDate"));
                rowArray[11]= Common.get(rs.getString("editTime"));
                MailCaseList.add(rowArray);
                count++;
            }
            rs.getStatement().close();
            rs.close();
                
            if(count>0){
            	boolean sendState=rp1.sendMail(MailCaseList,sysDate,sysTime);
//              boolean sendState=sendMail(MailCaseList,sysDate,sysTime);
                if(sendState){
                	System.out.println(sysDate+" "+sysTime+"-寄件完成!");
                	rp1.log(sysDate+" "+sysTime+"-寄件完成!");
                }else{
                	System.out.println(sysDate+" "+sysTime+"-寄件失敗!");
                	rp1.log(sysDate+" "+sysTime+"-寄件失敗!");
                }
            }else{
               	rp1.log(sysDate+" "+sysTime+"-查無需寄送的信件!");
            }
            rp1.log("email發送-結束時間:"+util.Datetime.getYYYMMDD()+" "+util.Datetime.getHHMMSS());
       	}catch(Exception e){
       		e.printStackTrace();
       	} finally{
       		db.closeAll();
       	}
        
    }
    
    public void main2() {
    	
        //initProxool();
        ArrayList<Object> MailCaseList=new ArrayList<Object>();
    	Database db=new Database();
    	SCH_SendMail rp1 = new SCH_SendMail();
//        for(int i=0;i<args.length;i++){
//        	System.out.println("args:"+args[i]);
//        }
        //mySend();
    	
        try{
        	String sysDate=util.Datetime.getYYYMMDD();
        	String sysTime=util.Datetime.getHHMMSS();
            ResultSet rs = db.querySQL("select * from MailCase where sendType='1' and (MailDate+MailTime)<= "+Common.sqlChar(sysDate+sysTime));
            int count=0;
            while(rs.next()){
            	
//                System.out.println("");
//                System.out.println("MailKey:"+rs.getString("MailKey"));
//                System.out.println("MailDate:"+rs.getString("MailDate"));
//                System.out.println("MailTime:"+rs.getString("MailTime"));
//                System.out.println("MailAddr:"+rs.getString("MailAddr"));
//                System.out.println("sendType:"+rs.getString("sendType"));
//                System.out.println("sendDate:"+rs.getString("sendDate"));
//                System.out.println("sendTime:"+rs.getString("sendTime"));
//                System.out.println("MailSubj:"+rs.getString("MailSubj"));
//                System.out.println("MailMsg:"+rs.getString("MailMsg"));
//                System.out.println("editID:"+rs.getString("editID"));
//                System.out.println("editDate:"+rs.getString("editDate"));
//                System.out.println("editTime:"+rs.getString("editTime"));
//                System.out.println("");
                
                String rowArray[]=new String[12];
            	rowArray[0]= Common.get(rs.getString("MailKey"));
            	rowArray[1]= Common.get(rs.getString("MailDate"));
            	rowArray[2]= Common.get(rs.getString("MailTime"));
            	rowArray[3]= Common.get(rs.getString("MailAddr"));
            	rowArray[4]= Common.get(rs.getString("sendType"));
            	rowArray[5]= Common.get(rs.getString("sendDate"));
            	rowArray[6]= Common.get(rs.getString("sendTime"));
            	rowArray[7]= Common.get(rs.getString("MailSubj"));
            	rowArray[8]= Common.get(rs.getString("MailMsg"));
            	rowArray[9]= Common.get(rs.getString("editID"));
            	rowArray[10]= Common.get(rs.getString("editDate"));
            	rowArray[11]= Common.get(rs.getString("editTime"));
            	MailCaseList.add(rowArray);
            	count++;
            }
            rs.getStatement().close();
            rs.close();
            
            if(count>0){
            	boolean sendState=rp1.sendMail(MailCaseList,sysDate,sysTime);
            	if(sendState){
            		System.out.println(sysDate+" "+sysTime+"-寄件完成!");
            	}else{
            		System.out.println(sysDate+" "+sysTime+"-寄件失敗!");
            	}
            }else{
            	System.out.println(sysDate+" "+sysTime+"-查無需寄送的信件!");
            }
        }catch(Exception e){
            e.printStackTrace();
        } finally{
        	db.closeAll();
        }
    }
    
    
    
    //第四種 ，用commons-email-1.1.jar，還需要兩個額外的包mail.jar，activation.jar包
    /*public static boolean mySend(ArrayList<Object> MailCaseList,String sysDate,String sysTime) {
    	boolean ret=true;
    	Database db=new Database();
    	try{
    		
    		String Mail_ID="";
    		String Mail_PWD="";
    		String Mail_SMTP="";
    		String Mail_Addr="";
    		String Mail_PORT="";
    		String Mail_SSL="";
    		String Mail_TLS="";
    		
    		ResultSet rs = db.querySQL("select Mail_ID,Mail_PWD,Mail_SMTP,Mail_Addr,Mail_PORT,Mail_SSL,Mail_TLS from MailSet");
            if(rs.next()){
            	Mail_ID=Common.get(rs.getString("Mail_ID"));
            	Mail_PWD=Common.get(rs.getString("Mail_PWD"));
            	Mail_SMTP=Common.get(rs.getString("Mail_SMTP"));
            	Mail_Addr=Common.get(rs.getString("Mail_Addr"));
            	Mail_PORT=Common.get(rs.getString("Mail_PORT"));
            	Mail_SSL=Common.get(rs.getString("Mail_SSL"));
            	Mail_TLS=Common.get(rs.getString("Mail_TLS"));
            }
            rs.getStatement().close();
            rs.close();
            
    		
        	java.util.Iterator it=MailCaseList.iterator();
        	String rowArray[]=new String[12];
        	
    		//SimpleEmail email = null;
        	while(it.hasNext()){
        		try{
        			Thread.sleep(10000);
					rowArray= (String[])it.next();
						System.out.println("");
						System.out.print("mail-1");
						SimpleEmail email = new SimpleEmail();
				    	System.out.print("-2");
				    	//email.setDebug(true);
				    	System.out.print("-3");
				    	email.setHostName(Mail_SMTP);//smtp伺服器
				    	System.out.print("-4");
				    	System.out.print("-5");
				    	
				    	if("Y".equals(Mail_SSL)){
				    		email.setSSL(true);
				    		if(!"".equals(Mail_PORT)){
				    			email.setSslSmtpPort(Mail_PORT);
				    		}
				    	}else{
				    		if(!"".equals(Mail_PORT)){
					    		email.setSmtpPort(Integer.parseInt(Mail_PORT));
					    	}
				    	}
				    	
				    	System.out.print("-6");
				    	
				    	if("Y".equals(Mail_TLS)){
				    		email.setTLS(true);
				    	}
				    	
				    	System.out.print("-7");
				    	email.setAuthentication(Mail_ID,util.EnBase64.decodeBASE64(Mail_PWD));//寄件人帳號、密碼
				    	System.out.print("-8");
				    	email.setFrom(Mail_Addr, Mail_ID);//寄件人地址 ,寄件人姓名
			            System.out.print("-9");
			        	email.addTo(rowArray[3], rowArray[3]);//收件人
			        	System.out.print("-10");
			        	email.setSubject(rowArray[7]);//主旨
			        	System.out.print("-11");
			        	email.setCharset("UTF-8");//信件編碼
			        	System.out.print("-12");
			        	email.setMsg(rowArray[8]);//內容
			        	System.out.print("-13");
			        	email.send();//寄送
			        	System.out.print("-14:"+rowArray[0]);
			        	//System.out.println("");
	        		//db.exeSQL("update MailCase set sendType='2',sendDate="+Common.sqlChar(sysDate)+",sendTime="+Common.sqlChar(sysTime)+" where MailKey="+rowArray[0]);
        		}catch (EmailException e) {
        			e.printStackTrace();
        			//db.exeSQL("update MailCase set sendType='3',sendDate="+Common.sqlChar(sysDate)+",sendTime="+Common.sqlChar(sysTime)+" where MailKey="+rowArray[0]);
        		}finally{
        			
        		}
			}
        	System.out.println("");
    		
		}catch (Exception e) {
			ret=false;
			e.printStackTrace();
		}finally{
			db.closeAll();
		}

    	return ret;
    }*/
    
    
    public boolean sendMail(ArrayList<Object> MailCaseList,String sysDate,String sysTime) {
    	boolean ret=true;
    	
    	Database db=new Database();
    	try{
    		String Mail_ID="";
    		String Mail_PWD="";
    		String Mail_SMTP="";
    		String Mail_Addr="";
    		String Mail_PORT="";
    		String Mail_SSL="";
    		String Mail_TLS="";
    		String sMailKey="";
    		
    		ResultSet rs = db.querySQL("select Mail_ID,Mail_PWD,Mail_SMTP,Mail_Addr,Mail_PORT,Mail_SSL,Mail_TLS from MailSet");
            if(rs.next()){
            	Mail_ID=Common.get(rs.getString("Mail_ID"));
            	Mail_PWD=Common.get(rs.getString("Mail_PWD"));
            	Mail_SMTP=Common.get(rs.getString("Mail_SMTP"));
            	Mail_Addr=Common.get(rs.getString("Mail_Addr"));
            	Mail_PORT=Common.get(rs.getString("Mail_PORT"));
            	Mail_SSL=Common.get(rs.getString("Mail_SSL"));
            	Mail_TLS=Common.get(rs.getString("Mail_TLS"));
            }
            rs.getStatement().close();
            rs.close();
            

    		
    		final String username = Mail_ID;
    		final String password = util.EnBase64.decodeBASE64(Mail_PWD);
    		
    		Properties props = new Properties();
    		
    		/*TLS檢核寫法
    		props.put("mail.smtp.auth", "true");ok
    		props.put("mail.smtp.starttls.enable", "true");
    		props.put("mail.smtp.host", "smtp.gmail.com");ok
    		props.put("mail.smtp.port", "587");
    		*/
  		
    		/*SSL檢核寫法
    		props.put("mail.smtp.host", "smtp.gmail.com");
    		props.put("mail.smtp.socketFactory.port", "465");
    		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
    		props.put("mail.smtp.auth", "true");
    		props.put("mail.smtp.port", "465");
    		*/
    		
    		
    		/*GMail發送SSL、TLS檢核
    		props.put("mail.smtp.host",  "smtp.gmail.com");
    		props.put("mail.smtp.auth", "true");
    		props.put("mail.smtp.port", "587");
    		props.put("mail.smtp.starttls.enable", "true");
    		props.put("mail.smtp.socketFactory.port", "465");
    		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
    		 */
    		
    		props.put("mail.smtp.host",  Mail_SMTP);
    		props.put("mail.smtp.auth", "true");
    		//props.put("mail.smtp.port", Mail_PORT);
    		
    		if("Y".equals(Mail_TLS)){
    			props.put("mail.smtp.port", "587");
    			props.put("mail.smtp.starttls.enable", "true");
	    	}
    		
    		if("Y".equals(Mail_SSL)){
    			props.put("mail.smtp.socketFactory.port", "465");
    			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
    		}

    		Session session = Session.getInstance(props,
    		  new javax.mail.Authenticator() {
    			protected PasswordAuthentication getPasswordAuthentication() {
    				return new PasswordAuthentication(username, password);
    			}
    		  });

    		
    		
    		try {
//    			boolean sessionDebug = false;
//    			javax.mail.Session mailSession = javax.mail.Session.getInstance(props,auth);
//    			session.setDebug(sessionDebug);
//    		    Message message = new MimeMessage(session);
//    			
//    		    Transport trans = session.getTransport("smtp");
//    		    trans.connect(Mail_SMTP,Mail_ID, Mail_PWD);
    		    
    		    
    			java.util.Iterator it=MailCaseList.iterator();
            	String rowArray[]=new String[12];
        		//SimpleEmail email = null;
            	while(it.hasNext()){
            		System.out.println("");
            		System.out.print("mail-1");
            		rowArray= (String[])it.next();
            		sMailKey=rowArray[0];
            		System.out.print("-2");
	    			Message message = new MimeMessage(session);
	    			System.out.print("-3");
	    			message.setFrom(new InternetAddress(Mail_Addr));//寄件人email
	    			System.out.print("-4");
	    			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(rowArray[3]));//收件人email
	    			System.out.print("-5");
	    			message.setSubject(rowArray[7]);//mail主旨
	    			System.out.print("-6");
	    			message.setText(rowArray[8]);//mail內容
	    			System.out.print("-7");
	    			Transport.send(message);
	    			//trans.send(message);
	    			System.out.print("-8");
	    			System.out.println("-Done"+sMailKey);
	    			log("寄送成功:"+sMailKey);
	    			modifySendType(sMailKey,sysDate,sysTime,"2");
	    			log("異動成功:"+sMailKey);
            	}
    		} catch (MessagingException e) {
    			e.printStackTrace();
    			log("錯誤訊息-1:"+sMailKey+"\r\n"+e.getMessage());
    			modifySendType(sMailKey,sysDate,sysTime,"3");
    			throw new RuntimeException(e);
    		}
            
        	System.out.println("");
    		
		}catch (Exception e) {
			//ret=false;
			log("錯誤訊息-0:"+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
    	
		
		return ret;
	}
    
    public static boolean modifySendType(String sMailKey,String sysDate,String sysTime,String sSendType) {
    	boolean ret=true;
    	Database db=new Database();
        try{
        	db.exeSQL("update MailCase set sendType="+Common.sqlChar(sSendType)+",sendDate="+Common.sqlChar(sysDate)+",sendTime="+Common.sqlChar(sysTime)+" where MailKey="+sMailKey);
        }catch(Exception e){
            e.printStackTrace();
            ret=false;
        } finally{
        	db.closeAll();
        }
        return ret;
    }
    
    private static void initProxool() {
        try{
            JAXPConfigurator.configure(System.getProperty("proxool.file"),
//              JAXPConfigurator.configure("D:\\alex\\LandSource\\MOI_GKCJ\\MOI_GKCJ\\WebContent\\WEB-INF\\proxool.xml",
                                       false);
        }
        catch(ProxoolException e){
            e.printStackTrace();
        }
    }
    
    
}

class MyAuthenticator extends javax.mail.Authenticator {
	private String strUser;
	private String strPwd;
	public MyAuthenticator(String user, String password) {
		this.strUser = user;
		this.strPwd = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(strUser, strPwd);
	}
}
