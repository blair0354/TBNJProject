<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="home/head.jsp" %>
<jsp:useBean id="obj" class="eform.eform1_2.EFORM1_2" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	String txtcity_no = util.Common.checkSet(request.getParameter("txtcity_no"));
	//System.out.println(txtcity_no);
	
	
	String sql = obj.getOption(
			"select left(krmk,2) unit,left(krmk,2)+'-'+kcnt as kcnt from rkeyn"
			+ " where kcde_1='55' and left(kcde_2,2)<>'/*' and left(kcde_2,2)<>'- '"
			+ " and left(kcde_2,2)='01' and substring(krmk,2,1)<>'0'"
			+ " and krmk like '" + txtcity_no + "%'"
			+ " order by krmk"
			, "");
	//System.out.println(sql);
	
	out.println(sql);
%>