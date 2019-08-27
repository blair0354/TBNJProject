package lca.ap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.DefaultTableModel;

import util.Common;
import util.Database;
import util.Datetime;
import util.ODatabase;
import util.ODatabase2;

public class LCAAP200F {

    private ServletContext context;
    
    private String uip;
    private String userID;
    private String unitID;
    
    private String state;
//    private String cty;
    private String vTable;
    private String detailVTable;
    private String detailCty;
    private String stateOneAll;
    private String recreation;
    private String msg = "";
    private String queryTime = "";
    private String dbUser;
    private String dbPsw;
    
    private Map unindexed = new LinkedHashMap();
    private Map detailStatus = new LinkedHashMap();
    
    private String[] strKeys;
    public String[] getStrKeys(){ return strKeys; }
	public void setStrKeys(String[] s){ this.strKeys=s; }
    
	
	private String IDXM_NO;
    public String getIDXM_NO(){ return Common.checkGet(IDXM_NO); }
	public void setIDXM_NO(String s){ this.IDXM_NO=Common.checkSet(s);}
	
	private String IDXM_SDT;
    public String getIDXM_SDT(){ return Common.checkGet(IDXM_SDT); }
	public void setIDXM_SDT(String s){ this.IDXM_SDT=Common.checkSet(s); }
	
	private String IDXM_EDT;
    public String getIDXM_EDT(){ return Common.checkGet(IDXM_EDT); }
	public void setIDXM_EDT(String s){ this.IDXM_EDT=Common.checkSet(s); }
	
	
	private String IDXM_FLAG;
    public String getIDXM_FLAG(){ return Common.checkGet(IDXM_FLAG); }
	public void setIDXM_FLAG(String s){ this.IDXM_FLAG=Common.checkSet(s); }
	
	private String IDXM_FLAGC;
    public String getIDXM_FLAGC(){ return Common.checkGet(IDXM_FLAGC); }
	public void setIDXM_FLAGC(String s){ this.IDXM_FLAGC=Common.checkSet(s); }

	public void ProcessStatus(){
		ArrayList<Object> objList=new ArrayList<Object>();
        Database sdb = new Database();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
        	conn = sdb.getConnection();
            stmt = conn.createStatement();
             
        	String sql = " select TOP 1 IDXM_NO,IDXM_SDATE,IDXM_STIME,IDXM_EDATE,IDXM_ETIME,IDXM_FLAG "
        		+ " ,(CASE IDXM_FLAG WHEN '00' THEN '檢核中' WHEN '01' THEN '檢核完成' WHEN '02' THEN '檢核失敗' ELSE '異常終止' END)AS IDXM_FLAGC "
        		+ "	from EFORM8M order by IDXM_NO desc "
        		+ "";
            rs = stmt.executeQuery(sql);
            if(rs.next()){
            	setIDXM_NO(rs.getString("IDXM_NO"));
            	setIDXM_SDT(Common.formatYYYMMDD(rs.getString("IDXM_SDATE"))+" "+Common.formatHHMMSS(rs.getString("IDXM_STIME")));
            	setIDXM_EDT(Common.formatYYYMMDD(rs.getString("IDXM_EDATE"))+" "+Common.formatHHMMSS(rs.getString("IDXM_ETIME")));
            	setIDXM_FLAG(rs.getString("IDXM_FLAG"));
            	setIDXM_FLAGC(rs.getString("IDXM_FLAGC"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }
	}
	
	
	
	/**
	 * 目的:檢核勾選要進行檢核的View
	 * @return
	 * @throws Exception
	 */
    public boolean doCheckView() throws Exception {
    	boolean retbol=false;
        try {
        	String lnArray[]=new String[2];
        	ArrayList lnList=getAllLN();
			Iterator it=null;
			String sIDXM_NO=insertEFORM8M();
			boolean runBol=true;
			if(!"".equals(sIDXM_NO)){
	        	for(String s : getStrKeys()){
	    			System.out.println("view-name:"+s);
	    			if(runBol){//如果有false就跳過後續的動作,顯示異常
	    				it=lnList.iterator();
		    			while(it.hasNext()){
		    				if(runBol){
		    					lnArray= (String[])it.next();
		    					runBol=checkView(sIDXM_NO,lnArray[0],lnArray[1],s);//如果回傳false就代表有出錯
		    				}
		    			}
	    			}
	    		}
	        	
	        	if(runBol){
	        		updateEFORM8M(sIDXM_NO,"01");
				}else{
					updateEFORM8M(sIDXM_NO,"02");
				}
			}
			
			
				
        } catch (Exception x) {
            System.out.println("Tomcat被終止");
            x.printStackTrace();
        } finally {
        	System.out.println("執行完畢!");
        }
        return retbol;
    }// method getResultModel end
    
    private String insertEFORM8M() {
    	String sIDXM_NO="";
        Database sdb = new Database();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
        	conn = sdb.getConnection();
            stmt = conn.createStatement();
             
        	String sql = " select (CAST(ISNULL(max(substring(IDXM_NO,8,3)),'000') AS INT)+1)as maxNo from EFORM8M where IDXM_SDATE="+Common.sqlChar(util.Datetime.getYYYMMDD());
            rs = stmt.executeQuery(sql);
            if(rs.next()){
            	String IDXM_SDATE=util.Datetime.getYYYMMDD();
            	String IDXM_STIME=util.Datetime.getHHMMSS();
            	sIDXM_NO=IDXM_SDATE+Common.formatFrontZero(rs.getString("maxNo"), 3);
            	
            	String exSql="insert into EFORM8M("
            		+"IDXM_NO "
            		+",IDXM_SDATE "
            		+",IDXM_STIME "
            		+",IDXM_FLAG "
            		+",IDXM_EDITID "
            		+")values("
            		+Common.sqlChar(sIDXM_NO)
            		+","+Common.sqlChar(IDXM_SDATE)
            		+","+Common.sqlChar(IDXM_STIME)
            		+","+Common.sqlChar("00")
            		+","+Common.sqlChar(this.getUserID())
            		+")";
            	stmt.execute(exSql);
            }
        }catch(SQLException e){
        	sIDXM_NO="";
            e.printStackTrace();
        }finally{
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }
        
        return sIDXM_NO;
    }
    
    private String updateEFORM8M(String sIDXM_NO,String sIDXM_FLAG) {
        Database sdb = new Database();
        Connection conn = null;
        Statement stmt = null;
        try{
        	conn = sdb.getConnection();
            stmt = conn.createStatement();
             
        	String IDXM_EDATE=util.Datetime.getYYYMMDD();
            String IDXM_ETIME=util.Datetime.getHHMMSS();
           
            	
            String exSql="update EFORM8M set "
            	+" IDXM_EDATE= "+Common.sqlChar(util.Datetime.getYYYMMDD())
            	+" ,IDXM_ETIME= "+Common.sqlChar(util.Datetime.getHHMMSS())
            	+" ,IDXM_FLAG= "+Common.sqlChar(sIDXM_FLAG)
            	+" where IDXM_NO="+Common.sqlChar(sIDXM_NO)
            	+"";
            stmt.execute(exSql);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeStatement(stmt);
            closeConnection(conn);
        }
        
        return sIDXM_NO;
    }
    
    private ArrayList<Object> getAllLN() {
    	ArrayList<Object> objList=new ArrayList<Object>();
        
        
        Database sdb = new Database();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
        	conn = sdb.getConnection();
            stmt = conn.createStatement();
             
        	String sql = " select a.krmk"
        		+ " ,(select s.KCDE_2 from rkeyn s where s.kcde_1 = '45' and s.kcde_2 = substring(a.krmk,1,1)) as CTY "
        		+ " from rkeyn a where a.kcde_1 = '55' and substring(a.krmk,2,1)<>'0' order by a.krmk";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
            	String rowArray[]=new String[2];
                rowArray[0]= rs.getString("CTY");
                rowArray[1]= rs.getString("krmk");
    			objList.add(rowArray);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }
        
        return objList;
    }
    
    /**
     * 目的:檢查地所的View是否查的到資料,並將檢查結果寫入EFORM8D
     * @param sIDXD_NO
     * @param sIDXD_CTY
     * @param sIDXD_LN
     * @param IDXD_VName
     * @return
     */
    private boolean checkView(String sIDXD_NO,String sIDXD_CTY,String sIDXD_LN, String IDXD_VName){
    	boolean ret=true;
    	String hasData="N";
        ODatabase odb = new ODatabase();
        Connection oConn = null;
        Statement oStmt = null;
        ResultSet rs = null;
        
        Database sdb = new Database();
        Connection sConn = null;
        Statement sStmt = null;
        
        
        try {
        	oConn = odb.getConnection();
            oStmt = oConn.createStatement();
            sConn = sdb.getConnection();
            sStmt = sConn.createStatement();
            System.out.print(sIDXD_LN+";"+IDXD_VName+"--"+util.Datetime.getYYYMMDD()+" "+util.Datetime.getHHMMSS());
        	String sql = "select * from " + IDXD_VName + " where ROWNUM=1 and UNIT = "+Common.sqlChar(sIDXD_LN);
        	rs = oStmt.executeQuery(sql);
        	if(rs.next()){// 檢查所有地所該檢視表是否有無資料
        		hasData="Y";
            }
        	
        	String exSql="insert into EFORM8D("
        		+" IDXD_NO"
        		+",IDXD_CTY"
        		+",IDXD_LN"
        		+",IDXD_VName"
        		+",IDXD_YN"
        		+")values("
        		+Common.sqlChar(sIDXD_NO)
        		+","+Common.sqlChar(sIDXD_CTY)
        		+","+Common.sqlChar(sIDXD_LN)
        		+","+Common.sqlChar(IDXD_VName)
        		+","+Common.sqlChar(hasData)
        		+")";
        	sStmt.execute(exSql);
        	System.out.println("~"+util.Datetime.getYYYMMDD()+" "+util.Datetime.getHHMMSS());
        }catch(SQLException e){
        	ret=false;
            e.printStackTrace();
        }finally{
            closeResultSet(rs);
            closeStatement(oStmt);
            closeConnection(oConn);
            closeStatement(sStmt);
            closeConnection(sConn);
        }
        
        return ret;
    }
    
    
    
    /**
     * 目的:取得所有的View
     * @return
     */
    public Map getViewTable() {
        LinkedHashMap result = new LinkedHashMap();
        
        String sql = " select VTABLE, (VTABLECN + '-' + VTABLE) as TNAME from EFORM6";
        
        Database sdb = new Database();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = sdb.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                result.put( Common.get(rs.getString(1)), Common.get(rs.getString(2))); // vTable, tName
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }
        
        return result;
    }
    

    
    
    
   /**
    * 目的:進行View的重建
    */
    public List doRecreate() {
    	System.out.println("### doRecreate");
        String recreationStr = getRecreation();
        System.out.println("### recreationStr:" + recreationStr);
    	ArrayList errMsg = new ArrayList();
    	
    	// 先檢查密碼對不對
    	//if (this.getDbPsw().equals("ETEC_ETEC")) {
    		
            // 取得所有的縣市使用者
            Database sdb = new Database();
            Connection conn = null;
            conn = sdb.getConnection();
            
            ArrayList<String[]> userList = null;
            try {
            	
            	// 取得所有的縣市使用者
                userList = getEFORM6_USERList(conn);;
            	
            } catch(Exception e){
                e.printStackTrace();
            } finally{
                //closeConnection(conn);
            }
            String[] sql=new String[getStrKeys().length];
            String[] viewRS = null;
            
            int i=0;
            for(String s : getStrKeys()){
	            try {
	            	// 取得要產製的View的名稱、欄位內容
	            	viewRS = getEFORM6Arr(conn, s);
	            	
	            } catch(Exception e){
	                e.printStackTrace();
	            } finally{
	                //closeConnection(conn);
	            }
            
	            // 組合創view的指令
	        	sql[i] = getReCreateViewSQL(userList, viewRS);
	        	System.out.println("重建view的sql的指令");
	        	System.out.println(sql[i]);
	        	i++;
            }
            closeConnection(conn);
        	// 執行重建檢視表
            Statement stmt = null;
            ResultSet rs = null;
        	try {
        		System.out.println("getDbUser:"+getDbUser()+" getDbPsw:"+getDbPsw());
        		ODatabase2 db_ora = new ODatabase2(getDbUser(),getDbPsw());
                Connection o_conn = null;
                o_conn = db_ora.getConnection();
                if(o_conn != null){
	                Statement reCreateStmt = null;
	                reCreateStmt = o_conn.createStatement();
	                
	                for(String ss : sql){
	                	//reCreateStmt.execute("CREATE OR REPLACE FORCE VIEW ABC (a,b,c) AS select 'A','B','D' from dual"); 
	                	reCreateStmt.execute(ss);
	                	//System.out.println("ss:"+ss);
	                }
	                
	                
	                this.setMsg("重建檢視表完成");
                }else{
                	errMsg.add(new String[]{recreationStr, "帳號/密碼輸入錯誤！"});
                	this.setMsg("帳號/密碼輸入錯誤！");
                }
                
        	} catch(SQLException e){
                e.printStackTrace();
                errMsg.add(new String[]{recreationStr, e.getMessage()});
                this.setMsg("重建檢視表失敗");
            } finally{
                closeResultSet(rs);
                closeStatement(stmt);
                closeConnection(conn);
                
            }
    	//} else {
    	//	errMsg.add(new String[]{recreationStr, "密碼輸入錯誤！"});
    	//	this.setMsg("密碼輸入錯誤！");
    	//}

        return errMsg;
    }
    
    /**
     * 目的:取得每個縣市的ORACLE USER帳號,如果是台南、台中要再取得合併前原縣的所代號及合併後的新代號
     * @param conn
     * @return
     * @throws Exception
     */
    public ArrayList<String[]> getEFORM6_USERList(Connection conn) throws Exception {
    	Statement stmt = null;
        ResultSet rs = null;
        ArrayList<String[]> userList = new ArrayList<String[]>();
        
        try {
        	String sql1 = "select ORAUSER, TRACTY, TRAUNIT from EFORM6_USER";
        	stmt = conn.createStatement();
        	rs = stmt.executeQuery(sql1);
        	while (rs.next()) {
        		userList.add(new String[]{rs.getString(1), rs.getString(2), rs.getString(3)});
        	}
        } catch(SQLException e){
            e.printStackTrace();
            throw new Exception("取得EFORM6_USER失敗\n" + e.getMessage()); 
        } finally{
            closeResultSet(rs);
            closeStatement(stmt);
        }
        
        return userList;
    }
    
    
    /**
     * 目的:取得要產製的View的名稱、欄位內容
     * @param conn
     * @param recreationStr
     * @return
     * @throws Exception
     */
    public String[] getEFORM6Arr(Connection conn, String recreationStr) throws Exception {
    	Statement stmt = null;
        ResultSet rs = null;
    	String[] viewRS = null;
    	
    	try {
    		String viewSQL;
        	// 取得要產製的View的名稱、欄位內容
    		viewSQL = "select VNAME, VTABLE ,VFIELD from EFORM6 where VTABLE = " + Common.sqlChar(recreationStr);
        	stmt = conn.createStatement();
        	rs = stmt.executeQuery(viewSQL);
        	while (rs.next()) {
        		viewRS = new String[]{rs.getString(1), rs.getString(2), rs.getString(3)};
        	}
    	} catch(SQLException e){
    		e.printStackTrace();
    		throw new Exception("取得EFORM6失敗\n" + e.getMessage()); 
        } finally{
            closeResultSet(rs);
            closeStatement(stmt);
        }
    	
    	return viewRS;
    }
    
    /**
     * 目的:組出建置View的語法
     * @param userList
     * @param viewRS
     * @return
     */
    public String getReCreateViewSQL(ArrayList<String[]> userList, String[] viewRS) {
    	
    	int index = 0;
    	StringBuffer sql = new StringBuffer("CREATE OR REPLACE FORCE VIEW ");
    	sql.append(viewRS[0]).append("\n").append(" (").append(viewRS[2]).append(") as ").append("\n");
    	String colnums;
    	for (String[] strArr : userList) {
    		colnums = viewRS[2];
    		if (strArr[0].equals("L1L00H03")) {
    			colnums = colnums.replaceAll("CTY", "DECODE(CTY,'L','B')AS CTY");
    			colnums = colnums.replaceAll("UNIT", "DECODE(UNIT,'LA','BD','LB','BE','LC','BF','LE','BG','LF','BH','LG','BI','LH','BJ','LI','BK')AS UNIT");
    		}
    		if (strArr[0].equals("L1R00H03")) {
    			colnums = colnums.replaceAll("CTY", "DECODE(CTY,'R','D')AS CTY");
    			colnums = colnums.replaceAll("UNIT", "DECODE(UNIT,'RA','DD','RB','DE','RC','DF','RD','DG','RE','DH','RF','DI','RG','DJ','RH','DK')AS UNIT");
    		} 
    		
    		sql.append(" SELECT ").append(colnums).append("\n");
    		sql.append(" FROM ").append(strArr[0] + "." + viewRS[1]).append("\n");
    		
    		if (index != (userList.size()-1)) {
    			sql.append(" UNION ALL ").append("\n");
    		}
    		
    		index++;
    	}
    	return sql.toString();
    	
    }
    
    
    /**
     * 目的:取得內容為建置View的語法的文字檔
     * @param recreation_str
     * @param request
     * @param response
     * @return
     */
	public File getTxtFile(String recreation_str, HttpServletRequest request, HttpServletResponse response) {
		File file = null;
		
    	Database sdb = new Database();
    	Connection conn = null;
        conn = sdb.getConnection();
        
        ArrayList<String[]> userList = null;
        String[] viewRS = null;
        String sql = "";
        
        try {
        	userList = getEFORM6_USERList(conn);
        } catch(Exception e){
            e.printStackTrace();
        } finally{
        	//closeConnection(conn);
        }
        
        String[] viewArry=recreation_str.split("::;::");
        for(String s : viewArry){
	        try {
	    		viewRS = getEFORM6Arr(conn, s);
	        } catch(Exception e){
	            e.printStackTrace();
	        } finally{
	        	//closeConnection(conn);
	        }
        
        
        	sql += getReCreateViewSQL(userList, viewRS)+";"+"\n\n";
        }
        closeConnection(conn);
        
		try {
			String fileName = "CREATE_VIEW_ALL"+ "_" + Datetime.getYYYMMDD() + "_" + Datetime.getHHMMSS() + ".txt";
			PrintWriter pw = new PrintWriter(fileName);
			pw.print(sql);
			pw.close();
			
			file = new File(fileName);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return file;
		
    }
	
	/**
     * 目的:取得View的檢核結果的CSV檔
     * @param recreation_str
     * @param request
     * @param response
     * @return
     */
	public File getViewCSVFile(String sIDXM_NO, HttpServletRequest request, HttpServletResponse response) {
		File file = null;
		
    	Database sdb = new Database();
    	Connection conn = null;
        conn = sdb.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        
        String csvStr="";
        try {
        	String sql = "select "
        		+ " (select s.KCNT from rkeyn s where s.kcde_1='45' and s.kcde_2=a.IDXD_CTY)as CTYC "
        		+ " ,(select s.KCNT from rkeyn s where s.kcde_1='55' and s.krmk=a.IDXD_LN)as LNC "
        		+ " ,a.IDXD_VName,(CASE a.IDXD_YN WHEN 'Y' THEN '有資料' WHEN 'N' THEN '無資料' ELSE '' END)AS IDXD_YN_F"
        		+ " from EFORM8D a where IDXD_NO="+Common.sqlChar(sIDXM_NO)
        		+ " order by IDXD_VName,a.IDXD_CTY,a.IDXD_LN";
        	stmt = conn.createStatement();
        	rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        		csvStr+=rs.getString("CTYC")+","+rs.getString("LNC")+","+rs.getString("IDXD_VName")+","+rs.getString("IDXD_YN_F")+"\n";
        	}
        } catch(Exception e){
            e.printStackTrace();
        } finally{
        	closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }
        

        
		try {
			String fileName = sIDXM_NO+"_VIEW_CHECK.csv";
			PrintWriter pw = new PrintWriter(fileName);
			pw.print(csvStr);
			pw.close();
			
			file = new File(fileName);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return file;
		
    }
   

    private String getSystime(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int sec = c.get(Calendar.SECOND);
        return year + "/" + month + "/" + day + " "
                + (hour < 10 ? "0" + hour : hour) + ":"
                + (min < 10 ? "0" + min : min) + ":"
                + (sec < 10 ? "0" + sec : sec);        
    }
    
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
    
    
    

    public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

    public String getState() {
        return state == null ? "" : state;
    }

    public void setState(String state) {
        this.state = state;
    }

//    public String getCty() {
//        return cty == null ? "" : cty;
//    }
//
//    public void setCty(String cty) {
//        this.cty = cty;
//    }
    
    public String getvTable() {
		return vTable == null ? "" : vTable;
	}

	public void setvTable(String vTable) {
		this.vTable = vTable;
	}
	
	public String getDetailVTable() {
		return detailVTable == null ? "" : detailVTable;
	}

	public void setDetailVTable(String detailVTable) {
		this.detailVTable = detailVTable;
	}

	public String getDetailCty() {
        return detailCty;
    }

    public void setDetailCty(String detailCty) {
        this.detailCty = detailCty;
    }

    public String getStateOneAll() {
        return stateOneAll;
    }

    public void setStateOneAll(String stateOneAll) {
        this.stateOneAll = stateOneAll;
    }

    public String getRecreation() {
        return recreation;
    }

    public void setRecreation(String recreation) {
        this.recreation = recreation;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
    }

    public String getUip() {
        return uip;
    }

    public void setUip(String uip) {
        this.uip = uip;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }
    
    public String getDbUser() {
		return dbUser == null ? "" : dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPsw() {
		return dbPsw == null ? "" : dbPsw;
	}

	public void setDbPsw(String dbPsw) {
		this.dbPsw = dbPsw;
	}

	public Map getUnindexed() {
        return unindexed;
    }

    public void setUnindexed(Map unindexed) {
        this.unindexed = unindexed;
    }
    
    public Map getDetailStatus() {
        return detailStatus;
    }

    public void setDetailStatus(Map detailStatus) {
        this.detailStatus = detailStatus;
    }

    public void setUnindexedCty(String[] unindexedCty){
        if(unindexedCty != null){
            for(int i = 0 ; i < unindexedCty.length; i++){
                String[] cty = unindexedCty[i].split(":");
                unindexed.put(cty[0], cty[1]);
                System.out.println("unindexedCty:" + cty[0] + "," + cty[1]);
            }
        }
    }
}
