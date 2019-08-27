/*
*<br>程式目的：資料庫共用函數
*<br>程式代號：Database
*<br>撰寫日期：93/12/01
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/
package util;

import java.io.File;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ODatabase2 {
	
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs=null;	
	String ora_user="";
	String ora_password="";
	
	public ODatabase2(String dbUserID,String dbUserPW){
		ora_user=dbUserID;
		ora_password=dbUserPW;
		try { 
		    //Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName(getWebXmlOraDri("1"));
		} 
		catch(ClassNotFoundException e) { 
		    System.out.println("找不到驅動程式類別"); 
		}
	}	

	/**
	 * <br>
	 * <br>目的：取得Connection
	 * <br>參數：無
	 * <br>傳回：傳回Connection
	*/
	public Connection getConnection() {
		if (conn!=null) {
			return conn;
		} else {
			try {
				conn = DriverManager.getConnection(getWebXmlOraDri("2"),ora_user,ora_password);
			} catch (SQLException e) {				
				e.printStackTrace();			
			}
			return conn;
		}
	}

	/**
	 * <br>
	 * <br>目的：取得Forward Only Statement
	 * <br>參數：Connection
	 * <br>傳回：傳回Statement
	*/
	public Statement getForwardStatement(Connection conn) {
		try {
			if (conn==null) conn = getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}
	
	public Statement getForwardStatement() {
		try {
			if (conn==null) conn = getConnection();
			//if (stmt!=null) stmt.close();
			return conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}	

	/**
	 * <br>
	 * <br>目的：取得Scroll Statement
	 * <br>參數：Connection
	 * <br>傳回：傳回Statement
	*/
	public Statement getScrollStatement(Connection conn){
		try {
			if (conn==null) conn = getConnection();
			//if (stmt!=null) stmt.close();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//return conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;		
	}

	public Statement getScrollStatement(){
		try {
			if (conn==null) conn = getConnection();
			//else return conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;		
	}
	
	public PreparedStatement getPreparedStatement(String sql){
		try {
			if (conn==null) conn = getConnection();
			//if (stmt!=null) stmt.close();
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
  	 * <br>
  	 * <br>目的：取得資料集的裡的欄位名稱　
  	 * <br>參數：ResultSet
  	 * <br>傳回：欄位名稱集合
  	*/	
	public String[] getColumnNames(ResultSet rs) throws SQLException{
		   ResultSetMetaData md = rs.getMetaData();
		   String[] colNames = new String[md.getColumnCount()];
		   for(int i = 0; i < colNames.length; i++){
		      String nomColonne = md.getColumnName(i+1);
		      colNames[i] = nomColonne;
		   }
		   return colNames;
	}		
	
	/**
  	 * <br>
  	 * <br>目的：執行查詢sql　
  	 * <br>參數：無
  	 * <br>傳回：傳回ResultSet
  	*/
	public ResultSet querySQL(String sql) throws Exception{
		return getForwardStatement().executeQuery(sql);		
	}
	
	/**
  	 * <br>
  	 * <br>目的：執行可更新的查詢sql　
  	 * <br>參數：無
  	 * <br>傳回：傳回ResultSet
  	*/
	public ResultSet queryUpdateSQL(String sql) throws Exception{
		if (conn==null) conn = getConnection();		
		//ResultSet.CONCUR_UPDATABLE
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		return stmt.executeQuery(sql);
	}
	
	/**
     * <br>
     * <br>目的：執行查詢sql　
     * <br>參數：無
     * <br>傳回：傳回ResultSet
    */
    public ResultSet querySQL(String sql,boolean scrollable) throws Exception{
        if(scrollable){        	
        	return getScrollStatement().executeQuery(sql);            
        }else{
            return querySQL(sql);
        }        
    }       	
    
	/**
  	 * <br>
  	 * <br>目的：執行sql　
  	 * <br>參數：無
  	 * <br>傳回：傳回ResultSet
  	*/
	public void excuteSQL(String[] sql) throws Exception{
		try {
			if (conn==null) conn = getConnection();
			conn.setAutoCommit(false);
			//stmt = getForwardStatement(conn);
			stmt = getForwardStatement();
			for(int i=0; i<sql.length; i++){
			    if(!"".equals(sql[i].toString()))
			        stmt.executeUpdate(sql[i]);
			}
			stmt.close();
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			System.out.println("excuteSQL Exception = "+sql[0]);
			throw new Exception(e);			
		} 	
	}	
	/**
  	 * <br>
  	 * <br>目的：執行sql　
  	 * <br>參數：無
  	 * <br>傳回：傳回ResultSet
  	*/
	public void excuteSQL(Vector sql) throws Exception{
		try {
			if (conn==null) conn = getConnection();
			conn.setAutoCommit(false);
			//stmt = getForwardStatement(conn);
			stmt = getForwardStatement();
			for(int i=0; i<sql.size(); i++){
			    if(!"".equals(sql.elementAt(i).toString()))
			        stmt.executeUpdate(sql.elementAt(i).toString());
			}
			stmt.close();
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			System.out.println("excuteSQL Exception = "+sql.elementAt(0).toString());
			throw new Exception(e);			
		} 	
	}	
	
	/**
  	 * <br>
  	 * <br>目的：執行查詢sql(可捲動)　
  	 * <br>參數：無
  	 * <br>傳回：傳回ResultSet
  	*/
	public ResultSet querySQLByScroll(String sql) throws Exception{
		//conn = getConnection();
		if (conn==null) conn = getConnection();
		stmt = getScrollStatement(conn);
		rs = stmt.executeQuery(sql);
		return rs;
	}	
	
	/**
	 * <br>
	 * <br>目的：結束Connection, Statement, ResultSet
	 * <br>參數：Connection, Statement, ResultSet
	 * <br>傳回：無
	*/
	public void closeAll(){
		try {
			if (rs!=null){ rs.close(); }
			if (stmt!=null){ stmt.close(); }
			if (conn!=null){ conn.close(); }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//有些時候自動編號的位數有限,必須先清空原來的資料方能使Key不重覆, 就必須使用到以下的函式
	//例如在非公用批次開徵時就得用到它	
	public void setAutoCommit(boolean sFlag) throws SQLException {
	    try {
	      if ( conn != null ) {
		  	conn.setAutoCommit(sFlag);
	      }
	    } catch (SQLException sqle) {	      
	      throw new SQLException("SQLException: Unable to setAutoCommit properties.\n"+sqle);
	    }
	}
		  
	public void doCommit() throws Exception {
		if ( conn != null ) {		
			try {	
				conn.commit();
			} catch (SQLException sqle) {
				conn.rollback();	  	
				throw new SQLException("SQLException: Unable to commit sqlConnection.\n"+sqle);
			}
		}
	}
	
	public void doRollback() throws Exception {
		try {
			if ( conn != null ) {
				conn.rollback();
			}			
		} catch (SQLException sqle) {				  	
			throw new SQLException("SQLException: Unable to rollback transaction.\n"+sqle);
		}
	}
	
	/**
	 * 此函式沒有交易控制,需得自己維護整個交易,使用時要慬慎
	 */
	public void exeSQL(String[] sql) throws Exception{
		if (conn == null) conn = getConnection();
		try {
			//stmt = getForwardStatement(conn);
			//stmt = getForwardStatement();
			stmt = conn.createStatement();
			for(int i=0; i<sql.length; i++){
			    if(!"".equals(sql[i].toString()))
			        stmt.executeUpdate(sql[i]);
			}			
			stmt.close();
		} catch (Exception e) {
			System.out.println("excuteSQL Exception = "+sql[0]);
			throw new Exception(e);			
		} 	
	}	
	
	/**
	 * 此函式沒有交易控制,需得自己維護整個交易,使用時要慬慎
	 */
	public void exeSQL(String sSQL) throws Exception {
		if (conn == null) conn = getConnection();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sSQL);
			stmt.close();
		} catch (Exception sqle) {	
			System.out.println("excuteSQL Exception = "+sSQL);					
			throw new Exception(sqle);
		}		
	}	
	
    public String getLookupField(String sSQL) {
    	String rStr = "";    		
    	try {
    		ResultSet rs = querySQL(sSQL);
    		if (rs.next()){
    			rStr = Common.get(rs.getString(1));
    		}
    		rs.getStatement().close();
    		rs.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return rStr;
    }	
    
    public String getLookupField(String sSQL, String s) {
    	String rStr = "";    		
    	try {
    		ResultSet rs = querySQL(sSQL);
    		if (rs.next()){
    			rStr = Common.get(rs.getString(1));
    		} else {
    			rStr = s;
    		}
    		rs.getStatement().close();
    		rs.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return rStr;
    }
    
    public String getOption(String sql, String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
        try {
        	ResultSet rs = querySQL(sql);
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                
                rtnStr = rtnStr + "<option value='" + id + "' ";
                if (selectedOne!= null && selectedOne.equals(id)) {
                    rtnStr = rtnStr + " selected ";
                }
                rtnStr = rtnStr + ">" + name + "</option>\n";
            }
        } catch (Exception ex) {
            rtnStr = "<option value=''>查詢錯誤</option>";
            ex.printStackTrace();
        }
        return rtnStr;
    }
    
    /**
     * 目地:取得oracle連線的Driver或URL
     * @param x='1'就回傳Drvier; x='2'就回傳URL
     * @return
     */
    public String getWebXmlOraDri(String x){
		String ret="";
		//System.out.println("url2:"+base.getFile());
		//long lasting = System.currentTimeMillis();
		try {
			URL base = this.getClass().getResource("../../proxool.xml");
			File f = new File(base.getFile());
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			NodeList nl = doc.getElementsByTagName("proxool");
			String aliasStr="";
			String dbDriverStr="";
			String dbUrlStr="";
			for (int i = 0; i < nl.getLength(); i++) {
				aliasStr=doc.getElementsByTagName("alias").item(i).getFirstChild().getNodeValue();
				dbDriverStr=doc.getElementsByTagName("driver-class").item(i).getFirstChild().getNodeValue();
				dbUrlStr=doc.getElementsByTagName("driver-url").item(i).getFirstChild().getNodeValue();

				if("Centeral".equals(aliasStr)){
					//System.out.print("driver-url:"+driverUrlStr);
					if("1".equals(x)){
						ret=dbDriverStr;
					}else if("2".equals(x)){
						ret=dbUrlStr;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
}
