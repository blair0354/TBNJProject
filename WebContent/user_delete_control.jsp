<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="home/head.jsp" %>
<jsp:useBean id="obj" class="eform.eform3_1.EFORM3_1" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	String txtuser_id = util.IDEncode.DecodePassword(request.getParameter("txtuser_id"));
	
	
	//System.out.println(txtuser_id);
	
	String cIsManager=Common.getUserIsManager((String)session.getAttribute("uid"), (String)session.getAttribute("unit"));
	if("Y".equals(cIsManager)){
		obj.doDelete(txtuser_id);
		out.write("<script>alert('刪除成功!');</script>");	
		out.write("<script>window.location.href = 'eform3_1.jsp';</script>");
	}else{
		out.write("<script>alert('刪除失敗!');</script>");
		out.write("<script>window.location.href = 'menu_main.jsp';</script>");
	}
%>
<script>
	//window.opener = null;
	//window.open('', '_self', '');
	//window.close();
</script>