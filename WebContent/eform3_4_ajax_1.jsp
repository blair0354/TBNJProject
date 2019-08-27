<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="obj" class="eform.eform3_4.EFORM3_4" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String txtcity_no = util.Common.checkSet(request.getParameter("txtcity_no"));
String unit = util.Common.checkSet(request.getParameter("unit"));
System.out.println("txtcity_no : " + txtcity_no);
System.out.println("unit : " + unit);

String sql = 
		"select left(krmk,2) unit,left(krmk,2)+'-'+kcnt as kcnt from rkeyn "
		+ "where kcde_1='55' and left(kcde_2,2)='01' and left(kcde_2,2)<>'/*' "
		+ "and left(kcde_2,2)<>'- ' and left(krmk,2) like '" + txtcity_no + "%' order by krmk";

System.out.println(sql);

String opt = obj.getOption(sql);

out.println(opt);
	
%>