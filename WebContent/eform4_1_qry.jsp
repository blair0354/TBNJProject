<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="util.Common"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLDecoder"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="obj" class="eform.eform4_1.EFORM4_1" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%	
	String txtcity_no = util.Common.checkSet(request.getParameter("txtcity_no");
	String txtunit = util.Common.checkSet(request.getParameter("txtunit");
	String txtcity_name = util.Common.checkSet(request.getParameter("txtcity_name"));
	String txtunit_name = util.Common.checkSet(request.getParameter("txtunit_name"));
	String txtrcv_yr = util.Common.checkSet(request.getParameter("txtrcv_yr"));
	String txtrcv_word = util.Common.checkSet(request.getParameter("txtrcv_word"));
	String txtrcv_no = util.Common.checkSet(request.getParameter("txtrcv_no"));
	String txtsno = util.Common.checkSet(request.getParameter("txtsno"));
	String txtsname = util.Common.checkSet(request.getParameter("txtsname"));
	String txtsno1 = util.Common.checkSet(request.getParameter("txtsno1"));
	String txtsname1 = util.Common.checkSet(request.getParameter("txtsname1"));
	String txtqry_purpose01 = util.Common.checkSet(request.getParameter("txtqry_purpose01"));
	String txtqry_purpose02 = util.Common.checkSet(request.getParameter("txtqry_purpose02"));
	String txtqry_purpose03 = util.Common.checkSet(request.getParameter("txtqry_purpose03"));
	String txtqry_purpose03a = util.Common.checkSet(request.getParameter("txtqry_purpose03a"));
	String txtqry_oper = util.Common.checkSet(request.getParameter("txtqry_oper"));
	
	/*
	System.out.println("txtcity_no: " + txtcity_no);
	System.out.println("txtunit: " + txtunit);
	System.out.println("txtcity_name: " + txtcity_name);
	System.out.println("txtunit_name: " + txtunit_name);
	System.out.println("txtrcv_yr: " + txtrcv_yr);
	System.out.println("txtrcv_word: " + txtrcv_word);
	System.out.println("txtrcv_no: " + txtrcv_no);
	System.out.println("txtsno: " + txtsno);
	System.out.println("txtsname: " + txtsname);
	System.out.println("txtsno1: " + txtsno1);
	System.out.println("txtsname1: " + txtsname1);
	System.out.println("txtqry_purpose01: " + txtqry_purpose01);
	System.out.println("txtqry_purpose02: " + txtqry_purpose02);
	System.out.println("txtqry_purpose03: " + txtqry_purpose03);
	System.out.println("txtqry_purpose03a: " + txtqry_purpose03a);
	System.out.println("txtqry_oper: " + txtqry_oper);
	*/
	
	
	String QRY_DATE_START = obj.getSystemDate();
	String QRY_TIME_START = obj.getSystemTime();
	String USERID = session.getAttribute("uid").toString();
	String UNITID = session.getAttribute("unit").toString();
	String IP = session.getAttribute("fip").toString();
	String CON = "公有土地歸戶分類統計表";
	String QRY = 
			"市縣市=" + txtcity_name + ";" 
			+ "鄉鎮市區=" + txtunit_name;
	String QRY_MSG = "尚未完成查詢";
	String RCV_YR = txtrcv_yr;
	String RCV_WORD = txtrcv_word;
	String RCV_NO = txtrcv_no;
	String SNO = txtsno;
	String SNAME = txtsname;
	String SNO1 = txtsno1;
	String SNAME1 = txtsname1;
	String QRY_PURPOSE = "";
	String QRY_OPER = txtqry_oper;
	String LIDN = "";
	String LNAME = "";
	String QRY_PURPOSE01 = txtqry_purpose01;
	String QRY_PURPOSE02 = txtqry_purpose02;
	String QRY_PURPOSE03 = txtqry_purpose03;
	String QRY_PURPOSE03A = txtqry_purpose03a;
	String QRY_DATE_END ="";
	String QRY_TIME_END ="";
	
	
		
	System.out.println("QRY_DATE_START:" + QRY_DATE_START);
	System.out.println("QRY_TIME_START:" + QRY_TIME_START);
	System.out.println("USERID:" + USERID);
	System.out.println("UNITID:" + UNITID);
	System.out.println("IP:" + IP);
	System.out.println("QRY:" + QRY);
	System.out.println("QRY_MSG:" + QRY_MSG);
	System.out.println("RCV_YR:" + RCV_YR);
	System.out.println("RCV_WORD:" + RCV_WORD);
	System.out.println("RCV_WORD:" + RCV_WORD);
	System.out.println("SNO:" + SNO);
	System.out.println("SNAME:" + SNAME);
	System.out.println("SNO1:" + SNO1);
	System.out.println("SNAME1:" + SNAME1);
	System.out.println("QRY_PURPOSE:" + QRY_PURPOSE);//固定空值
	System.out.println("QRY_OPER:" + QRY_OPER);
	System.out.println("LIDN:" + LIDN);//固定空值
	System.out.println("LNAME:" + LNAME);//固定空值
	System.out.println("QRY_PURPOSE01:" + QRY_PURPOSE01);
	System.out.println("QRY_PURPOSE02:" + QRY_PURPOSE02);
	System.out.println("QRY_PURPOSE03:" + QRY_PURPOSE03);
	System.out.println("QRY_PURPOSE03A:" + QRY_PURPOSE03A);
	
	
	try{
		
	//step 1. 查詢資料
	Map map = obj.doQuery(txtcity_no, txtunit);
	
	
	//step 2. 寫入查詢資料
	obj.insertBs_log(QRY_DATE_START, QRY_TIME_START, USERID, UNITID, IP, CON, QRY, QRY_MSG, RCV_YR, RCV_WORD, RCV_NO, SNO, SNAME, SNO1, SNAME1, QRY_PURPOSE, LIDN, LNAME, QRY_OPER, QRY_PURPOSE01, QRY_PURPOSE02, QRY_PURPOSE03, QRY_PURPOSE03A);
	
	
	//step 3.產出檔案
	obj.doPrintExcel(map,request,response,txtcity_name,txtunit_name);
	out.clear();
	out = pageContext.pushBody();
	
	//step 3. 寫入查詢結果
	QRY_DATE_END = obj.getSystemDate();
	QRY_TIME_END = obj.getSystemTime();
	//3-1 有資料
	obj.updateBs_log(QRY_DATE_END, QRY_TIME_END, "已完成查詢",QRY_DATE_START,QRY_TIME_START,IP);
	out.clear();
	
	}catch(Exception e){
		QRY_DATE_END = obj.getSystemDate();
		QRY_TIME_END = obj.getSystemTime();
		
		obj.updateBs_log(QRY_DATE_END, QRY_TIME_END, "查詢失敗",QRY_DATE_START,QRY_TIME_START,IP);
		e.printStackTrace();
		out.write("<script>alert('查詢失敗！'); window.open('','_self',''); window.close(); </script>");
	}
%>
