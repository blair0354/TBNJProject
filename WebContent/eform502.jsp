<!--
程式目的：EFORM502
程式代號：EFORM502
程式日期：1020723
程式作者：TzuYi.Yang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp" %>
<jsp:useBean id="obj" scope="request" class="lca.ap.EFORM502">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList" />
<%
	obj.getLastProcess();  //取得上次產製紀錄

	if("doProcess".equals(obj.getState())){
		obj.doProcess();
	}else if("doQuery".equals(obj.getState())){
		objList = obj.doQuery();
	}else if("doReset".equals(obj.getState())){ 
		objList = new java.util.ArrayList();
		obj = new lca.ap.EFORM502();
		obj.setState("");
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
}

function doProcess(){
	
	if(form1.lastProcessState.value != "0" && form1.lastProcessState.value != "4" && form1.lastProcessState.value != ""){
		if(!confirm("資料產製中，是否欲重新產製?")){ return; }
	}else{  //若目前為產製完成，按下產製後，即使尚未刷新頁面也不可再按產製按鈕
		form1.lastProcessState.value = "1";
	}
	form1.state.value = "doProcess";
	form1.submit();
	//waitProcessFlag = true;
	//waitProcess("waitDoProcess", 0);
	//document.getElementsByName("waitDoProcess")[0].value = "產製中";
	
	//getProcessState("1");
}

function doQuery(){
	form1.state.value = "doQuery";
	if(!checkField()){ return; }
	form1.submit();
}

function doReset(){
	form1.state.value = "doReset";
	form1.submit();
}

//等待AJAX執行時慢慢點
/*
var waitProcessFlag = false;
function waitProcess(name, point){
	var pointMax = 5;
	var obj = document.getElementsByName(name)[0];
	if(point < pointMax && waitProcessFlag){
		obj.value += ".";
		point++;
		if(point == pointMax){
			obj.value = obj.value.replace(/[.]/g, "");
			point = 0;
		}
		setTimeout('waitProcess(\"waitDoProcess\", ' + point + ')', 1000);
	}
}
*/

//待控制事項
//1.若目前有產製作業進行中(log中有state不是0或4)，就不能進行產製
//2.若目前產製作業未完成，每格數秒重新讀取資料庫狀態一次

//以AJAX方式執行
/*
function getProcessState(step){
	var xmlDoc=document.createElement("xml");
	xmlDoc.async=false;
	var randomnumber=Math.floor(Math.random()*1000);

	var queryValue = "?step="+step+"&abc="+randomnumber;
	var loadPath = "home/xmlProcessEform5_2.jsp";
	if(xmlDoc.load(loadPath+queryValue)){
		if(xmlDoc.documentElement.childNodes.item(0).getAttribute("resultStr")!=""){
			waitProcessFlag = false;
			document.getElementsByName("waitDoProcess_1")[0].value = "產製完成";
		}
	}
}
*/
function queryOne(){}
</script>
</head>

<body topmargin="0" background="images/main_bg.jpg"	onLoad="chkUnitID();init();">
<form id="form1" name="form1" method="post">
<table border="0" width="700" cellspacing="0" cellpadding="0">
	<tr align="center">
		<td colspan="3"><font size="4" face="標楷體" color="#FF9900">土地所有權人性別統計(產製作業)</font>
		<hr size="1" color="#008000" width="700" align="left">
		</td>
	</tr>
	<tr align="center">
		<td colspan="2">
			<input class="toolbar_default" type="button" name="btn_doProcess" value="產製土地所有權人性別統計資料" onClick="doProcess();">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<font color="red" size="-1">(欲列印統計報表請至列印作業)</font>
		</td>
	</tr>
	<tr align="center">
		<td colspan="2">
			<font size="-1">
				上次資料產製時間：<%=obj.getLastProcessTime()%>
				<!-- 產製狀態代碼 -->
				<input type="hidden" name="lastProcessState" value="<%=obj.getLastProcessState()%>">
				<br />
				產製狀態：
				<input class="field_RO" type="text" name="lastProcessStateName" size="30" value="<%=obj.getLastProcessStateName()%>">
				&nbsp;&nbsp;
				<input class="field_RO" type="text" name="lastProcessDetail" size="30" value="<%=obj.getLastProcessDetail()%>">
			</font>
		</td>
	</tr>
	<tr align="center">
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td class="td_form" width="40%">產製人員：</td>
		<td class="td_form_white">
			<input class="field" name="q_USER_ID" maxlength="10" size="10" value="<%=obj.getQ_USER_ID()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form" width="40%">產製狀態：</td>
		<td class="td_form_white">
			<select class="field" type="select" name="q_PROCESS_STATE">
				<%=util.View.getOption("SELECT KCDE_2, KCNT FROM RKEYN WHERE KCDE_1 = 'ES'", obj.getQ_PROCESS_STATE())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form" width="40%">產製日期：</td>
		<td class="td_form_white">
			<%=util.View.getPopCalndar("field","q_PROCESS_DATE_S", obj.getQ_PROCESS_DATE_S())%>
			～
			<%=util.View.getPopCalndar("field","q_PROCESS_DATE_E", obj.getQ_PROCESS_DATE_E())%></td>
	</tr>
	<tr>
		<td align="right">
			<input class="toolbar_default" type="button" name="btn_doQuery" value="查　　詢" onClick="doQuery();">
			&nbsp;&nbsp;
		</td>
		<td align="left">
			&nbsp;&nbsp;
			<input class="toolbar_default" type="button" name="btn_doReset" value="清　　除" onClick="doReset();">
		</td>
	</tr>
	<tr>
		<td class="bg" colspan="3">
		<div id="listContainer">
		<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
			<thead id="listTHEAD">
				<tr>
					<th class="listTH" nowrap><a class="text_link_w" ><a class="text_link_w" >NO.</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">產製人員</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">產製狀態</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">開始日期</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">開始時間</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">結束日期</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',6,false);" href="#">結束時間</a></th>
				</tr>
			</thead>
			<tbody id="listTBODY">
				<%
					boolean primaryArray[] = {true,false,false,false,false,false,false};
					boolean displayArray[] = {false,true,true,true,true,true,true};
					String[] alignArray = {"center","center","center","center","center","center","center"};
					out.write(util.View.getQuerylist(primaryArray,displayArray,alignArray,objList,obj.getQueryAllFlag(),1));
				%>
			</tbody>
		</table>
		</div>
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