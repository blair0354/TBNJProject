<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="util.Common"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLDecoder"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="home/head.jsp" %>
<jsp:useBean id="obj" class="eform.eform2_5.EFORM2_5" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%	
	String txtcity_no = Common.checkSet(request.getParameter("txtcity_no"));
	String txtunit = Common.checkSet(request.getParameter("txtunit"));
	String txtqry_no = Common.checkSet(request.getParameter("txtqry_no"));
	String txtqry_name = Common.big5ToIso(Common.checkSet(request.getParameter("txtqry_name")));
	String txtrcv_yr = Common.checkSet(request.getParameter("txtrcv_yr"));
	String txtrcv_word = Common.checkSet(request.getParameter("txtrcv_word"));
	String txtrcv_no = Common.checkSet(request.getParameter("txtrcv_no"));
	String txtsno = Common.checkSet(request.getParameter("txtsno"));
	String txtsname = Common.checkSet(request.getParameter("txtsname"));
	String txtsno1 = Common.checkSet(request.getParameter("txtsno1"));
	String txtsname1 = Common.checkSet(request.getParameter("txtsname1"));
	String txtqry_purpose01 = Common.checkSet(request.getParameter("txtqry_purpose01"));
	String txtqry_purpose02 = Common.checkSet(request.getParameter("txtqry_purpose02"));
	String txtqry_purpose03 = Common.checkSet(request.getParameter("txtqry_purpose03"));
	String txtqry_purpose03a = Common.checkSet(request.getParameter("txtqry_purpose03a"));
	String txtqry_oper = Common.checkSet(request.getParameter("txtqry_oper"));
	
	/*
	System.out.println("txtcity_no: " + txtcity_no);
	System.out.println("txtunit: " + txtunit);
	System.out.println("txtqry_no: " + txtqry_no);
	System.out.println("txtqry_name: " + Common.isoToBig5(txtqry_name));
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
	String CON = "權利人資料查詢";
	String QRY = 
			"市縣市=" + txtcity_no + ";" +
			"事務所=" + txtunit + ";" +
			"統一編號=" + txtqry_no + ";" +
			"姓名=" + Common.isoToBig5(txtqry_name);
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
	String QRY_PURPOSE01 = txtqry_purpose01;
	String QRY_PURPOSE02 = txtqry_purpose02;
	String QRY_PURPOSE03 = txtqry_purpose03;
	String QRY_PURPOSE03A = txtqry_purpose03a;
	String QRY_DATE_END ="";
	String QRY_TIME_END ="";
	
	
	/*		
	System.out.println(QRY_DATE_START);
	System.out.println(QRY_TIME_START);
	System.out.println(USERID);
	System.out.println(UNITID);
	System.out.println(IP);
	System.out.println(QRY);
	System.out.println(QRY_MSG);
	System.out.println(RCV_YR);
	System.out.println(RCV_WORD);
	System.out.println(RCV_NO);
	System.out.println(SNO);
	System.out.println(SNAME);
	System.out.println(SNO1);
	System.out.println(SNAME1);
	System.out.println(QRY_PURPOSE);
	System.out.println(QRY_OPER);
	System.out.println(QRY_PURPOSE01);
	System.out.println(QRY_PURPOSE02);
	System.out.println(QRY_PURPOSE03);
	System.out.println(QRY_PURPOSE03A);
	*/
	
	
		
	//step 1. 查詢資料
	List list = obj.doQuery(txtcity_no, txtunit, txtqry_no, txtqry_name);
	
	
	//step 2. 寫入查詢資料
	obj.insertBs_log(list, QRY_DATE_START, QRY_TIME_START, USERID, UNITID, IP, CON, QRY, QRY_MSG, RCV_YR, RCV_WORD, RCV_NO, SNO, SNAME, SNO1, SNAME1, QRY_PURPOSE, QRY_OPER, QRY_PURPOSE01, QRY_PURPOSE02, QRY_PURPOSE03, QRY_PURPOSE03A);
	
	
	
	//step 3.產出檔案
	if(list.size() > 0){
		obj.doPrintExcel(list,request,response);
		out.clear();
		out = pageContext.pushBody();
	}else{
		out.clear();
		out.write("<script> alert('查無權利人資料！'); </script>");
		out.write("<script> window.open('','_self','');window.close(); </script>");
	}
	
	
	QRY_DATE_END = obj.getSystemDate();
	QRY_TIME_END = obj.getSystemTime();
	
	
	
	//step 3. 寫入查詢結果
	//3-1 有資料
	if(list.size() > 0 ){
		obj.updateBs_log(QRY_DATE_END, QRY_TIME_END, "已完成查詢",QRY_DATE_START,QRY_TIME_START,IP);
		out.clear();
	}else{
		obj.updateBs_log(QRY_DATE_END, QRY_TIME_END, "無符合條件資料", QRY_DATE_START,QRY_TIME_START,IP);
	}
%>
