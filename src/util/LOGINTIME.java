package util;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;
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

public class LOGINTIME extends QueryBean {

    private ServletContext context;
    
    private String ulid;
    
    public ServletContext getContext() {return context;}
    public void setContext(ServletContext s) {this.context = s;}
    
    public String getUlid() {return ulid;}
    public void setUlid(String s) {this.ulid = s;}
	
    
	public boolean ModifyUseTime(String sessID){
		boolean ret=false;
		Database db = new Database();
		
		  java.util.Date currentDateTime = new java.util.Date();
		  System.out.println(currentDateTime);
		  System.out.println(currentDateTime.getTime());
		  Timestamp currentTimestamp = new Timestamp(currentDateTime.getTime());
		  System.out.println(currentTimestamp);
		  System.out.println(currentTimestamp.getTime());
	
		try {
			String sql ="update etecuser set user_login_time="+Common.sqlChar(currentTimestamp.toString())+" where user_id="+Common.sqlChar(sessID);
			System.out.println("loingSql:"+sql);
			db.exeSQL(sql);
			ret=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return ret;
	}
    
}
