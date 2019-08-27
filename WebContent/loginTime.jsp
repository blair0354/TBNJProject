<!--
程式目的：LCAAP140
程式代號：LCAAP140
程式日期：1020103
程式作者：TzuYi.Yang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<%@ include file="home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="util.LOGINTIME">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<%
System.out.println(123);
	ServletContext context = getServletContext();
	System.out.println(223);
	obj.setContext(context);
	System.out.println("context:"+obj.getContext());
	if(obj.ModifyUseTime(""+session.getAttribute("uid"))){
		response.sendRedirect("menu.jsp");
	}else{
		System.out.println("帳號異常!");
		response.sendRedirect("index.jsp");
	}
%>