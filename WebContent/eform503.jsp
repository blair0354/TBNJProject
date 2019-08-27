<!--
程式目的：EFORM5_3
程式代號：EFORM5_3
程式日期：1020724
程式作者：TzuYi.Yang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp" %>
<jsp:useBean id="obj" scope="request" class="lca.ap.EFORM503">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<%
	obj.getLastProcess();  //取得上次產製紀錄
	
	String fileName = "";
	if("doProcess".equals(obj.getState())){
		ServletContext context = getServletContext();
		fileName = obj.doProcess(application.getRealPath("\\report\\lca\\ap"), context.getInitParameter("reportLocation"), "EFORM503");
		fileName = fileName.replace("\\", java.io.File.separator + java.io.File.separator);
	}
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
<script type="text/javascript">
function chkUnitID(){
	if(form1.unitID.value != "00"){
		alert("此作業只提供內政部地政司使用");
		top.location.reload();
	}
}

function checkField(){
	var alertStr="";
	if(alertStr.length!=0){ alert(alertStr); return false; }
	return true;
}

function init(){
	if(form1.doProcessResult.value != ""){
		alert(form1.doProcessResult.value);
		form1.doProcessResult.value = "";
	}
	
	if(form1.state.value == "doProcessSuccess"){
		var prop='';
	    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=no,scrollbars=yes,resizable=yes,';
	    prop=prop+'width=450,';
	    prop=prop+'height=160';
		window.open("home/fileDownload.jsp?fileID2="+'<%=fileName%>',"下載檔案",prop);
		
		form1.state.value = "";
	}
}

function doProcess(){
	if(form1.lastProcessState.value == ""){ alert("尚無產製紀錄！"); return; }
	if(form1.lastProcessState.value != "0"){ alert("資料產製中，待產製完成後才可列印報表！"); return; }
	form1.state.value = "doProcess";
	form1.submit();
}
</script>
</head>

<body topmargin="0" background="images/main_bg.jpg"	onLoad="chkUnitID();init();">
<form id="form1" name="form1" method="post">
<table border="0" width="700" cellspacing="0" cellpadding="0">
	<tr align="center">
		<td colspan="3"><font size="4" face="標楷體" color="#FF9900">土地所有權人性別統計(列印作業)</font>
		<hr size="1" color="#008000" width="700" align="left">
		</td>
	</tr>
	<tr align="center">
		<td colspan="2">
			<input class="toolbar_default" type="button" name="btn_doProcess" value="列印土地所有權人性別統計資料" onClick="doProcess();">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<font color="red" size="-1">(欲重新產製資料請至產製作業)</font>
		</td>
	</tr>
	<tr align="center">
		<td colspan="2">
			<font size="-1">
				上次資料產製時間：<%=obj.getLastProcessTime()%>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<!-- 產製狀態代碼 -->
				<input type="hidden" name="lastProcessState" value="<%=obj.getLastProcessState()%>">
				產製狀態：
				<input class="field_RO" type="text" name="lastProcessStateName" size="30" value="<%=obj.getLastProcessStateName()%>">
			</font>
		</td>
	</tr>
</table>
<hr size="1" color="#008000" width="700" align="left">

<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="userID" value="<%=session.getAttribute("uid")%>">
<input type="hidden" name="doProcessResult" value="<%=obj.getDoProcessResult()%>">
<input type="hidden" name="unitID" value="<%=session.getAttribute("unitid")%>">

</form>
</body>
</html>
