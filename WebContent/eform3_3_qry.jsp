<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="util.Common"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLDecoder"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="obj" class="eform.eform3_3.EFORM3_3" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%	
	String txtcity_no = util.Common.checkSet(request.getParameter("txtcity_no")+"' or 1=1--");
	String txtunit = util.Common.checkSet(request.getParameter("txtunit"));
	String txtcity_name = util.Common.checkSet(request.getParameter("txtcity_name"));
	String txtqry_msg = util.Common.checkSet(request.getParameter("txtqry_msg"));
	String txtqry_date_start = util.Common.checkSet(request.getParameter("txtqry_date_start"));
	String txtqry_date_end = util.Common.checkSet(request.getParameter("txtqry_date_end"));
	
	
	
	System.out.println("txtcity_no: " + txtcity_no);
	System.out.println("txtunit: " + txtunit);
	System.out.println("txtcity_name: " + txtcity_name);
	System.out.println("txtqry_msg: " + txtqry_msg);
	System.out.println("txtqry_date_start: " + txtqry_date_start);
	System.out.println("txtqry_date_end: " + txtqry_date_end);
	
	
	String QRY_DATE_START = obj.getSystemDate();
	String QRY_TIME_START = obj.getSystemTime();
	String QRY_DATE_END ="";
	String QRY_TIME_END ="";
	String USERID = session.getAttribute("uid").toString();
	String UNITID = session.getAttribute("unit").toString();
	String IP = session.getAttribute("fip").toString();
	String CON = "每日查詢紀錄清冊";
	String QRY = 
			"市縣市=" + txtcity_no + ";"
			+ "事務所=" + txtunit + ";"
			+ "查詢狀態=" + txtqry_msg +";"
			+ "製表日期：" + obj.getCreateTime() +";"
			+ "起訖查詢日期：" + txtqry_date_start + "~" + txtqry_date_end
			;
	String QRY_MSG = "尚未完成查詢";
	
	
	String RCV_YR = "";
	String RCV_WORD = "";
	String RCV_NO = "";
	String SNO = "";
	String SNAME = "";
	String SNO1 = "";
	String SNAME1 = "";
	String QRY_PURPOSE = "";
	String QRY_OPER = "";
	String LIDN = "";
	String LNAME = "";
	
	
		
	System.out.println("QRY_DATE_START:" + QRY_DATE_START);
	System.out.println("QRY_TIME_START:" + QRY_TIME_START);
	System.out.println("USERID:" + USERID);
	System.out.println("UNITID:" + UNITID);
	System.out.println("IP:" + IP);
	System.out.println("CON:" + CON);
	System.out.println("QRY:" + QRY);
	System.out.println("QRY_MSG:" + QRY_MSG);
	
	
	
	
	
	try{
		
	
	//step 1. 查詢資料
	List list = obj.doQuery(txtqry_date_start, txtqry_date_end, txtqry_msg, txtcity_no, txtunit);
	
	//step 2. 寫入查詢資料
	obj.insertBs_log(QRY_DATE_START, QRY_TIME_START, USERID, UNITID, IP, CON, QRY, QRY_MSG, RCV_YR, RCV_WORD, RCV_NO, SNO, SNAME, SNO1, SNAME1, QRY_PURPOSE, QRY_OPER, LIDN, LNAME);
	
	//step 3.產出檔案
	obj.doPrintExcel(list, request, response, txtqry_date_start, txtqry_date_end);
	out.clear();
	out = pageContext.pushBody();
	
	//step 3. 寫入查詢結果
	QRY_DATE_END = obj.getSystemDate();
	QRY_TIME_END = obj.getSystemTime();
	//3-1 有資料
	if(list.size() > 0)
		obj.updateBs_log(QRY_DATE_END, QRY_TIME_END, "已完成查詢",QRY_DATE_START,QRY_TIME_START,IP);
	else
		obj.updateBs_log(QRY_DATE_END, QRY_TIME_END, "無符合條件資料",QRY_DATE_START,QRY_TIME_START,IP);
		
	out.clear();
	
	}catch(Exception e){
		QRY_DATE_END = obj.getSystemDate();
		QRY_TIME_END = obj.getSystemTime();
		
		obj.updateBs_log(QRY_DATE_END, QRY_TIME_END, "查詢失敗",QRY_DATE_START,QRY_TIME_START,IP);
		e.printStackTrace();
		out.write("<script>alert('查詢失敗！'); window.open('','_self',''); window.close(); </script>");
	}
	
%>
