<!--
*<br>程式目的：政府清查公告之土地及建物清冊
*<br>程式代號：cusca010r
*<br>程式日期：0970514
*<br>程式作者：
*<br>不折行:鄉鎮市區, 地/建號, 面積, 權利範圍
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="home/head.jsp" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.report.*"%>
<%@ page import="java.io.*"%>
<%@ page import="net.sf.jasperreports.engine.*"%>

<jsp:useBean id="obj" scope="request" class="lca.ap.LCAAP010F">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	out.clear();
    ServletContext context = getServletContext();
    String reportLocation = context.getInitParameter("reportLocation");
	//obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\cus\\ca"));
    TableModelReportEnvironment env = new TableModelReportEnvironment();
	javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	if(obj.getPrint_type().equals("3")){
		model=obj.getCSVResultModel();
	}else{
		model=obj.getResultModel();
	}
	if ((model==null)||(model.getDataVector().size()==0)) {
		out.write("資料庫裡沒有符合的資料<br>");
		out.write("回<a href='eform2_2.jsp'>權利人歸戶清冊產製</a>畫面");
		Common.updateDL_LOG("4",Datetime.getYYYMMDD(),Datetime.getHHMMSS(),obj.getQry_seq()); 
		return;
	}
	env.setTableModel(model);
	if(obj.getPrint_type().equals("2")){
		env.setJasperFile(this.getServletContext().getRealPath("/report/cus/ca/cusca010.jasper"));
		env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
	}else if(obj.getPrint_type().equals("1")){
		env.setJasperFile(this.getServletContext().getRealPath("/report/cus/ca/cusca010.jasper"));
		env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
	}else if(obj.getPrint_type().equals("3")){
		env.setJasperFile(this.getServletContext().getRealPath("/report/cus/ca/cusca011.jasper"));
		env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
	}
	HashMap parms = new HashMap();
	parms.put("print_date","製表日期："+Datetime.getYYYMMDD().substring(0,3)+"/"+Datetime.getYYYMMDD().substring(3,5)+"/"+Datetime.getYYYMMDD().substring(5)+"   "+Datetime.getHHMMSS().substring(0,2)+":"+Datetime.getHHMMSS().substring(2,4)+":"+Datetime.getHHMMSS().substring(4));
    
    TableModelReportGenerator generator = new TableModelReportGenerator(env);
    String vid=Common.getVMID();
    File tempDirectory = new File(reportLocation,vid);
	tempDirectory.mkdirs();
	//System.out.println(tempDirectory.getPath());
	if(obj.getPrint_type().equals("2")){
		java.io.File outputFile;
		outputFile = new java.io.File(tempDirectory.getPath()+java.io.File.separator+"cusca010_"+user.getUserID()+".pdf");
		generator.reportToFile(outputFile,parms);	
		Common.updateDL_LOG("2",vid+":;:cusca010_"+user.getUserID()+".pdf",Datetime.getYYYMMDD(),Datetime.getHHMMSS(),obj.getQry_seq());
		out.write("申請作業完成<br>可以至<a href='exportRP_Result.jsp'>歸戶結果查詢及下載作業</a>下載產製檔案<br>或者回<a href='eform2_2.jsp'>權利人歸戶清冊產製</a>畫面");
	}else if(obj.getPrint_type().equals("1")){
		java.io.File outputFile;
		outputFile = new java.io.File(tempDirectory.getPath()+java.io.File.separator+"cusca010_"+user.getUserID()+".xls");
		generator.reportToFile(outputFile,parms);
		Common.updateDL_LOG("2",vid+":;:cusca010_"+user.getUserID()+".xls",Datetime.getYYYMMDD(),Datetime.getHHMMSS(),obj.getQry_seq());
		out.write("申請作業完成<br>可以至<a href='exportRP_Result.jsp'>歸戶結果查詢及下載作業</a>下載產製檔案<br>或者回<a href='eform2_2.jsp'>權利人歸戶清冊產製</a>畫面");
	}else if(obj.getPrint_type().equals("3")){
		java.io.File outputFile;
		outputFile = new java.io.File(tempDirectory.getPath()+java.io.File.separator+"cusca010_"+user.getUserID()+".txt");
		generator.reportToFile(outputFile,parms,"MS950");	
		Common.updateDL_LOG("2",vid+":;:cusca010_"+user.getUserID()+".txt",Datetime.getYYYMMDD(),Datetime.getHHMMSS(),obj.getQry_seq());
		out.write("申請作業完成<br>可以至<a href='exportRP_Result.jsp'>歸戶結果查詢及下載作業</a>下載產製檔案<br>或者回<a href='eform2_2.jsp'>權利人歸戶清冊產製</a>畫面");
		//response.sendRedirect(application.getInitParameter("fileDownloadUrl")+"?fileName=" + outputFile);
	}
%>