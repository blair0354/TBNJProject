<%@page import="java.util.List"%>
<%//這邊要寫在最上方 使用 request.getRequestDispatcher 時 前面不能有response
	//System.out.println("uid:"+(String)session.getAttribute("uid"));
	//System.out.println("unit:"+(String)session.getAttribute("unit"));
	//String isManager = (String)session.getAttribute("isManager");
	String isManager=Common.getUserIsManager((String)session.getAttribute("uid"), (String)session.getAttribute("unit"));
	if(!"Y".equals(isManager)) {
			request.getRequestDispatcher("/menu_main.jsp").forward(request, response);
	}
%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp"%>
<jsp:useBean id="obj" class="eform.eform3_1.EFORM3_1" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<HTML>

<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-control" content="no-cache" />
<link rel="stylesheet" href="js/default.css" type="text/css" />
<link rel="stylesheet" href="js/font.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="inc/font.css">
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript" src="js/function.js"></script>
<script type="text/javascript" src="js/tablesoft.js"></script>
<script type="text/javascript" src="js/jquery-1.12.1.min.js"></script>
<title><%=session.getAttribute("com_title")%>-使用者管理</title>
</HEAD>
<%
	String unit = (String)session.getAttribute("unit");
	obj.setUnit(unit);
	
	if(request.getParameter("page") != null && request.getParameter("page") != "")
		obj.setPage(Integer.parseInt(request.getParameter("page")));
	if(request.getParameter("Txtcity_no") != null && request.getParameter("Txtcity_no") != "")
		obj.setTxtcity_no(request.getParameter("Txtcity_no"));
	
	
	List list = obj.doQuery(unit,obj.getTxtcity_no());
%>
<BODY background="images/main_bg.jpg" topmargin="0">
	<FORM Method="post" name="form1" action="eform3_1.jsp">
		<input type="hidden" id="unit" name="unit" value="<%=obj.getUnit()%>">
		<table border="0" width=630 cellspacing="0" cellpadding="0">
			<%if("00".equals(unit)){ %>
			<tr>
				<td width="500"><font size="3" face="標楷體" color="#FF9900">使用者資料管理－可輸入查詢條件查詢</font></td>			
				<td width="100"><a href='user_add.jsp'>[新增]</a></td>
			</tr>
			<tr>
				<td>
					縣市:
					<select 
						style="FONT-FAMILY: Arial; BACKGROUND: #ffffcc"
						name=txtcity_no
						id="txtcity_no">
						<option value="">全部</option>
						<%=obj.getOption("select KCDE_2,KCNT from rkeyn where kcde_1='45' order by KCDE_2", obj.getTxtcity_no()) %>
					</select>
					<input type="submit" value="查詢">
				</td>
			</tr>
			<%} else{%>
			<tr>
				<td width="500"></td>
				<td width="100"><a href='user_add.jsp'>[新增]</a></td>
			</tr>
			<%} %>
			
		</table>
		<hr size="1" color="#008000" width="630" align="left">
		<table class=c12 cellSpacing=0 cellPadding=4 background=images/background.gif border=1>
			<thead align="center">
				<tr style="FONT-SIZE: 10pt; COLOR: #ffffff" bgColor="#219c66">
					<td width="30">序號</td>
					<td width="90">使用者帳號</td>
					<td width="140">使用者名稱</td>
					<td width="160">所屬單位</td>
					<td width="60">管理者</td>
					<td width="60">是否停用</td>
					<td width="40">修改</td>
					<td width="40">刪除</td>
				</tr>
			</thead>
			<tbody>
				<%=obj.getDetail(list)%>
			</tbody>
		</table>
				<hr size="1" color="#008000" width="630" align="left">
		<table width="630">
			<tr>
				<td align="right" style="font-size: 14">
					<%=obj.showPage()%>
				</td>
			</tr>		
		</table>
		
	</FORM>
</BODY>
</HTML>