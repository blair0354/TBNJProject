<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" errorPage="error.jsp"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="util.*" %>
<%@ page import="util.Common" %>
<%@ include file="head.jsp" %>
<%
String ma_lo = request.getParameter("ma_lo");
String ma_seq=request.getParameter("ma_seq");
String editid=request.getParameter("editid");
int maxPostSize = 1024*1024*5; //5 M
if (!"".equals(Common.get(application.getInitParameter("filestoreLimit")))) {
	maxPostSize = Integer.parseInt(application.getInitParameter("filestoreLimit"));
}
    boolean isMultipart = false;

    String contentType = request.getContentType();

    if(contentType!=null){
        contentType = contentType.toLowerCase();
        if(contentType.startsWith("multipart/form-data")){
            isMultipart = true;
        }
    }

    MultipartRequest mr = null;
    String uploadCaseID = "";
    String fileName = "";
    
    if(isMultipart){
                
        uploadCaseID = new java.rmi.dgc.VMID().toString();
        uploadCaseID = uploadCaseID.replaceAll(":","_");
        
        File tempDirectory = new File(application.getInitParameter("filestoreLocation"));
        //File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        
        //String filePath = application.getInitParameter("filestoreLocation")+File.separator+uploadCaseID;
        //Common.CreateDir(filePath);
        //Common.MakeDir(new File(filePath));
        tempDirectory = new File(tempDirectory,uploadCaseID);                
        tempDirectory.mkdirs();               
        FileRenamePolicy policy = new DefaultFileRenamePolicy();
        String encoding = "utf-8";
        mr = new MultipartRequest(request,tempDirectory.getAbsolutePath(),maxPostSize,encoding,policy);
        //mr = new MultipartRequest(request,filePath,maxPostSize,encoding,policy);
        
               
    }
    Enumeration enu = mr.getFileNames();
    File uploadFile = null;
    if(enu.hasMoreElements()){
    	String name = (String)enu.nextElement();
        uploadFile = mr.getFile(name);
        int fileSize = (int) uploadFile.length();
        if (fileSize>0) {
            fileName = uploadFile.getName();
        } 
        Database db = new Database();
        String[] sql=new String[1];
    	try {
    		sql[0]=" insert into CATTF(" +
			" AF_FSEQ,"+
			" AF_LO,"+
			" AF_SEQ,"+
			" AF_FFOLDER,"+
			" AF_FNAME,"+
			" EDITID,"+
			" EDITDATE,"+
			" EDITTIME"+
			" )(select Isnull(max(af_fseq),0)+1 "+ ","+
		    Common.sqlChar(ma_lo) + "," +
		    Common.sqlChar(ma_seq) + "," +
			Common.sqlChar(uploadCaseID) + "," +
			Common.sqlChar(fileName) +","+
			Common.sqlChar(editid)+","+
			Common.sqlChar(util.Datetime.getYYYMMDD())+","+
			Common.sqlChar(util.Datetime.getHHMMSS())+
		    " from CATTF where af_lo="+
		    Common.sqlChar(ma_lo)+
		    " and af_seq="+Common.sqlChar(ma_seq)+
		    ")" ;
    		db.excuteSQL(sql);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		db.closeAll();
    	}
        
    }
      

%>
<html>
