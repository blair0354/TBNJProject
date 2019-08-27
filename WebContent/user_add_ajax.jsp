<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="home/head.jsp" %>
<jsp:useBean id="obj" class="eform.eform3_1.EFORM3_1" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	String txtcity_no = request.getParameter("txtcity_no");
	//System.out.println(txtcity_no);
	
	
	String sql = obj.getOption(
			"select s.krmk,s.kcnt from rkeyn s where s.kcde_1='55' and s.kcde_2='01' and krmk like '" + txtcity_no + "%' order by krmk"
			, "");
	//System.out.println(sql);
	
	out.println(sql);
	
%>