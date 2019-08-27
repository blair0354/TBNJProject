<!--
程式目的：EFORM506
程式代號：EFORM506
程式日期：1060620
程式作者：Sya
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<%@page import="util.View" %>
<%@page import="lca.ap.EFORM506" %>

<jsp:useBean id="obj" scope="request" class="lca.ap.EFORM506">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<%
	ArrayList<String[]> al=new ArrayList<String[]>();	
	al=obj.getWinModel();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-control" content="no-cache" />
<link rel="stylesheet" href="js/default.css" type="text/css" />
<link rel="stylesheet" href="js/font.css" type="text/css" />
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript" src="js/function.js"></script>
<style type="text/css">
	#lowerTableArea {
    width: auto;
		height: 400px;
		overflow: auto;
		scrollbar-base-color:#eeeeee;
}
</style>
</head>
<body onload="dod();">
<form id="form1" name="form1" method="post" onSubmit="">
<!--Query區============================================================-->
<!--Form區============================================================-->
<div id="lowerTableArea" style="background-color:#F5F1C8;">
<div class="loader" id="loader"></div>
	<table><tr><td align="left">原住民地區行政區設定資料檔檢視</td></tr></table>
	<div style="background-color:#F0F0F0;">
	<table class="lowerTable" style="width:100%">  
		<tr bgcolor="#B9D0FF">
			<td class="td5Column_Others">縣市</td>
			<td class="td5Column_Others">行政區</td>
			<td class="td5Column_Others">異動人員</td>
			<td class="td5Column_Others">異動日期</td>
			<td class="td5Column_Others">異動時間</td>
		</tr>
	<% 
		for(int i=0;i<al.size();i++){
		String data[]=(String[])al.get(i);
	%>	
		<tr bgcolor="#DEDEDE">
			<td class="td5Column_Others"><%=data[0]%></td>
			<td class="td5Column_Others"><%=data[1]%></td>
			<td class="td5Column_Others"><%=data[2]%></td>
			<td class="td5Column_Others"><%=data[3]%></td>
			<td class="td5Column_Others"><%=data[4]%></td>
		</tr>	
	<% 	
		}
	%>
		<tr><td>&nbsp;</td></tr>
	</table>
	</div>
</div>
<!--Toolbar區============================================================-->
<!--List區============================================================-->
</form>
</body>
</html>
