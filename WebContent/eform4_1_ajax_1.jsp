<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="obj" class="eform.eform4_1.EFORM4_1" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	String txtcity_no = util.Common.checkSet(request.getParameter("txtcity_no"));
	//System.out.println(txtcity_no);
	
	
	String sql = obj.getOption(
			"select distinct kcde_2,kcnt,cty from rkeyn"
			+" where kcde_1='46' and substr(kcde_2,1,2)<>'/*' and substr(kcde_2,1,2)<>'- '"
			+" and CTY='" + txtcity_no + "'"
			+" order by cty,kcde_2"
			, "");
	//System.out.println(sql);
	
	out.println(sql);
	
%>