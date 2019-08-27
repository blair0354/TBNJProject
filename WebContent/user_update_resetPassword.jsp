<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="home/head.jsp" %>
<jsp:useBean id="obj" class="eform.eform3_1.EFORM3_1" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	String txtuser_id = URLDecoder.decode(request.getParameter("txtuser_id"),"utf-8");
	String txtuser_name = URLDecoder.decode(request.getParameter("txtuser_name"),"utf-8");
	String txtpassword = URLDecoder.decode(request.getParameter("txtpassword"),"utf-8");
	String txtuser_mail = request.getParameter("txtuser_mail");
	String txtunit = request.getParameter("txtunit");
	String isStop = request.getParameter("isStop");
	String isManager = request.getParameter("isManager");
	String update_user = (String)session.getAttribute("uid");
	String update_time = obj.getSystemTime();
	String user_login_time = obj.getSystemTime();
	
	/*
	System.out.println(txtuser_id);
	System.out.println(txtuser_name);
	System.out.println(txtpassword);
	System.out.println(txtuser_mail);
	System.out.println(txtunit);
	System.out.println(isStop);
	System.out.println(isManager);
	System.out.println(update_user);
	System.out.println(update_time);
	System.out.println(user_login_time);
	*/
	
	
	//step 1.update etecuser
	obj.resetPassword(txtuser_name, txtpassword, txtuser_mail, txtunit, isStop, isManager, update_user, update_time, user_login_time, txtuser_id);
	//step 2. del etecuser_log
	obj.doDelLog(txtuser_id);
	//step 3. insert etecuser_log
	obj.doInsertLog(txtuser_id, txtpassword);
	
	out.clear();
	out.print("OK");
%>
