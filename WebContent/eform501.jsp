<!--
程式目的：EFORM501
程式代號：EFORM501
程式日期：1020731
程式作者：TzuYi.Yang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp" %>
<jsp:useBean id="obj" scope="request" class="lca.ap.EFORM501">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList" />
<%

	if ("queryAll".equals(obj.getState())) {
		if ("false".equals(obj.getQueryAllFlag())){ obj.setQueryAllFlag("true"); }
	}else if ("queryOne".equals(obj.getState())) {
		obj = (lca.ap.EFORM501)obj.queryOne();	
	}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
		obj.insert();
	}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
		obj.update();
	}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
		obj.delete();
	}

	if ("true".equals(obj.getQueryAllFlag())){
		objList = obj.queryAll();
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
<script type="text/javascript" src="js/tablesoft.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function chkUnitID(){
	if(form1.unitID.value != "00"){
		alert("此作業只提供內政部地政司使用");
		top.location.reload();
	}
}

function checkField(){
	var alertStr="";
	alertStr += checkAlphaInt(form1.MERGE_CTY,"合併縣市代碼");
	alertStr += checkAlphaInt(form1.CTY_USER,"縣市帳號");
	alertStr += checkAlphaInt(form1.MERGE_CTY_USER,"合併縣市帳號");
	alertStr += checkInt(form1.ORDERBY,"報表排序");
	if(alertStr.length!=0){ alert(alertStr); return false; }
	return true;
}

function init(){
	//隱藏部份功能按鈕setDisplayItem("spanListHidden", "H");
	document.all.item("spanListHidden").style.display = "none";	
	
	if(form1.doProcessResult.value != ""){
		alert(form1.doProcessResult.value);
		form1.doProcessResult.value = "";
	}
}

function queryOne(CTY){
	form1.CTY.value=CTY;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}
</script>
</head>

<body topmargin="0" background="images/main_bg.jpg"	onLoad="chkUnitID();whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--Query區============================================================-->
<div id="queryContainer" style="width:300px;height:100px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">縣市代碼：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_CTY" size="5" maxlength="1" value="<%=obj.getQ_CTY()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">縣市名稱：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_KCNT" size="10" maxlength="10" value="<%=obj.getQ_KCNT()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">實際有對應之縣市：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_CTY_YN">
				<%=util.View.getYNOption(obj.getQ_CTY_YN()) %>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
		    <input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name);">
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="清　　除">
		</td>
	</tr>
	</table>
</div>
<!--Form區============================================================-->
<table border="0" width="700" cellspacing="0" cellpadding="0">
	<tr align="center">
		<td colspan="4"><font size="4" face="標楷體" color="#FF9900">性別統計表縣市資料維護作業</font>
		<hr size="1" color="#008000" width="700" align="left">
		</td>
	</tr>
	<tr>
		<td class="td_form">縣市代碼/名稱：</td>
		<td class="td_form_white">
			<input class="field_P" name="CTY" maxlength="1" size="3" value="<%=obj.getCTY()%>">
			<input class="field" name="KCNT" maxlength="5" size="5" value="<%=obj.getKCNT()%>">
		</td>
		<td class="td_form">縣市帳號：</td>
		<td class="td_form_white">
			<input class="field" name="CTY_USER" maxlength="10" size="10" value="<%=obj.getCTY_USER()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">合併縣市代碼：</td>
		<td class="td_form_white">
			<input class="field" name="MERGE_CTY" maxlength="1" size="5" value="<%=obj.getMERGE_CTY()%>">
		</td>
		<td class="td_form">合併縣市帳號：</td>
		<td class="td_form_white">
			<input class="field" name="MERGE_CTY_USER" maxlength="10" size="10" value="<%=obj.getMERGE_CTY_USER()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">報表排序：</td>
		<td class="td_form_white">
			<input class="field" name="ORDERBY" maxlength="5" size="5" value="<%=obj.getORDERBY()%>">
		</td>
		<td class="td_form">實際有對應之縣市：</td>
		<td class="td_form_white">
			<select class="field" type="select" name="CTY_YN">
				<%=util.View.getYNOption(obj.getCTY_YN()) %>
			</select>
		</td>
	</tr>
<!--Toolbar區============================================================-->
	<tr>
		<td colspan="4" class="bg" style="text-align:center">
		<input type="hidden" name="state" value="<%=obj.getState()%>">
		<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
		<input type="hidden" name="userID" value="<%=session.getAttribute("uid")%>">
		<input type="hidden" name="doProcessResult" value="<%=obj.getDoProcessResult()%>">
		
		<input type="hidden" name="unitID" value="<%=session.getAttribute("unitid")%>">
		<jsp:include page="home/toolbar_EFORM501.jsp" />
		</td>
	</tr>

<!--List區============================================================-->
	<tr>
		<td class="bg" colspan="4">
		<div id="listContainer">
		<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
			<thead id="listTHEAD">
				<tr>
					<th class="listTH" nowrap><a class="text_link_w" ><a class="text_link_w">NO.</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">縣市代碼</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">縣市名稱</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">縣市帳號</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">報表排序</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">實際縣市</a></th>
				</tr>
			</thead>
			<tbody id="listTBODY">
				<%
					boolean primaryArray[] = {true,false,false,false,false};
					boolean displayArray[] = {true,true,true,true,true};
					String[] alignArray = {"center","center","center","center","center"};
					out.write(util.View.getQuerylist(primaryArray,displayArray,alignArray,objList,obj.getQueryAllFlag(),1));
				%>
			</tbody>
		</table>
		</div>
		</td>
	</tr>
</table>
<hr size="1" color="#008000" width="700" align="left">
</form>
</body>
</html>