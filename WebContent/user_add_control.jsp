<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="home/head.jsp" %>
<jsp:useBean id="obj" class="eform.eform3_1.EFORM3_1" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	String txtuser_id = util.Common.checkSet(request.getParameter("txtuser_id"));
	String txtuser_name = util.Common.checkSet(request.getParameter("txtuser_name"));
	String txtpassword = util.Common.checkSet(request.getParameter("txtpassword"));
	String txtuser_mail = util.Common.checkSet(request.getParameter("txtuser_mail"));
	String txtunit = util.Common.checkSet(request.getParameter("txtunit"));
	String isStop = util.Common.checkSet(request.getParameter("isStop"));
	String isManager = util.Common.checkSet(request.getParameter("isManager"));
	String update_user = (String)session.getAttribute("uid");
	String update_time = obj.getSystemTime();
	
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
	*/
	//
	String cIsManager=Common.getUserIsManager((String)session.getAttribute("uid"), (String)session.getAttribute("unit"));
	if("Y".equals(cIsManager)){
	//step 1. 檢查帳號是否存在
		int cnt = obj.queryUserId(txtuser_id);
		if(cnt > 0){
			out.write("<script>alert('此使用者帳號已存在!');</script>");
			out.write("<script>window.location.href = 'user_add.jsp';</script>");
		}else{
			//2. 寫入etecuser
			obj.doInsert(txtuser_id, txtuser_name, txtpassword, txtuser_mail, txtunit, isStop, isManager, update_user, update_time);
			//3 寫入log
			obj.doInsertLog(txtuser_id,txtpassword);
			out.write("<script>alert('新增完成!');</script>");
			out.write("<script>window.location.href = 'eform3_1.jsp';</script>");
		}
	}else{
		out.write("<script>alert('新增失敗!');</script>");
		out.write("<script>window.location.href = 'menu_main.jsp';</script>");
	}
	
%>