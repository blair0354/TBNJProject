<%@ page contentType="text/html;charset=utf-8" import="util.*"%>
<%@ include file="head.jsp" %>
<jsp:useBean id="obj" scope="request" class="util.General">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String q_organID = request.getParameter("q_organID");
String q_organName = request.getParameter("q_organName");
String popOrganID = request.getParameter("popOrganID");
String popOrganName = request.getParameter("popOrganName");
String isLimit = request.getParameter("isLimit");
String jsFunction = request.getParameter("js");

String strJavaScript = "";
if (!"".equals(Common.get(jsFunction))) strJavaScript += "\nopener." + jsFunction + ";\n\n";

String sql=" select a.organID, a.organAName, a.organSName from SYSCA_Organ a where 1=1 " ;

//權限控制
if(!"Y".equals(isLimit)){
	if (!"Y".equals(user.getIsAdminManager())){
		if ("Y".equals(user.getIsOrganManager())){
			sql+=" and a.isManager='Y' and a.organID like '" + user.getOrganID().substring(0,5) +"%' ";
		}else{
			sql+=" and a.isManager='Y' and a.organID = '" + user.getOrganID()  +"' ";
		}
	} else {
		//sql+=" and a.ismanager='Y' ";
	}
}	
sql+=" and a.organID like '" + Common.get(q_organID) +"%' and (a.organAName like '%" + Common.get(q_organName) +"%' or a.organSName like '%" + Common.get(q_organName) + "%') "; 

String strList = obj.getOrganList(sql);
%>
<html>
<head>
<title>機關輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../js/default.css?1=ss" type="text/css">
<script language="javascript" src="../js/validate.js"></script>
<script language="javascript" src="../js/function.js"></script>

<script language="javascript">
function selectOrgan(selectID,selectName){
	if (isObj(opener.document.all.item("<%=popOrganID%>"))) {		
		opener.document.all.item("<%=popOrganID%>").value=selectID;		
	}
	if (isObj(opener.document.all.item("<%=popOrganName%>"))) {
		opener.document.all.item("<%=popOrganName%>").value=selectName;		
	}
	<%=strJavaScript%>	
	window.close();
}
</script>
</head>
<body topmargin="3" leftmargin="3" rightmargin="3" bottommargin="3">
<form id="form1" name="form1" method="post">
<input type="hidden" name="popOrganID" value="<%=popOrganID%>">
<input type="hidden" name="popOrganName" value="<%=popOrganName%>">
<input type="hidden" name="isLimit" value="<%=isLimit%>">

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg" >
	<div id="formContainer" style="height:70px">
	<table class="table_form" width="100%" height="100%">	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font>機關代號：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="q_organID" size="10" maxlength="10" value="<%=Common.get(q_organID)%>">
		</td>		
	</tr>	
	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font>機關名稱：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="q_organName" size="30" maxlength="30" value="<%=Common.get(q_organName)%>">
		</td>		
	</tr>	
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
	<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="window.close()">
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg" >
<div id="listContainer" style="height:250px">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH">&nbsp;</th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">機關代號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">機關名稱</a></th>
	</thead>
	<tbody id="listTBODY">
		<%=strList%>
	</tbody>
</table>
</div>
</td></tr>
</table>	
</form>
</body>
</html>