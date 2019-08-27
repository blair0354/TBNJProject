<!--
程式目的：mail發送及記錄查詢
程式代號：eform4_19, LCAAP220F
程式日期：1060417
程式作者：Rhonda Ke
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp" %>
<jsp:useBean id="obj" scope="request" class="lca.ap.LCAAP220F">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList" />
<%
	if("queryAll".equals(obj.getState())){
		objList = obj.queryAll();
	}

	if("queryOne".equals(obj.getState())){
		objList = obj.queryAll();
		obj.queryOne();
	}

	String msg = "";
	if("save".equals(obj.getState())){
		msg = obj.saveEmail();
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
<script type="text/javascript" src="js/cbToggle.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script language="javascript">
function chkUnitID(){
	if(form1.unitID.value != "00"){
		alert("此作業只提供內政部地政司使用");
		top.location.reload();
	}
}

function checkField(){
	
	var alertStr = '';
	if (form1.state.value == 'queryAll') {
		if (form1.q_mailDate_s.value != '') {
			alertStr += checkDate(form1.q_mailDate_s, '預定發送日期-起');
		}
		if (form1.q_mailDate_e.value != '') {
			alertStr += checkDate(form1.q_mailDate_e, '預定發送日期-迄');
		}
	}
	
	if (form1.state.value == 'save') {
		alertStr += checkEmpty(form1.mailDate, '預定發送日期');
		alertStr += checkDate(form1.mailDate, '預定發送日期');
		alertStr += checkEmpty(form1.mailTimeH, '預定發送時間-時');
		alertStr += checkHour(form1.mailTimeH, '預定發送時間-時');
		alertStr += checkEmpty(form1.mailTimeM, '預定發送時間-分');
		alertStr += checkMinute(form1.mailTimeM, '預定發送時間-分');
		alertStr += checkEmpty(form1.mailTimeS, '預定發送時間-秒');
		alertStr += checkMinute(form1.mailTimeS, '預定發送時間-秒');
		alertStr += checkEmpty(form1.mailAddr, '收件者');
		alertStr += checkEmail(form1.mailAddr, '收件者');
		alertStr += checkEmpty(form1.mailSubj, '主旨');
		alertStr += checkEmpty(form1.mailMsg, '內容');
	}
	
    if(alertStr != ''){
		alert(alertStr);
		return false;
	}else{
    	document.form1.submit();
    }
    return true;
}

function init() {
	$('#content').hide();
	$('.mailDetails').hide();
	$('.newMail').hide();
	
	<% if (!msg.equals("")) { %>
		alert('<%=msg%>');
	<% }%>
	
	<% if (obj.getState().equals("queryOne")) { %>
		showContent('mailDetails');
	<% }%>
}

function checkState(state, id) {
	document.form1.state.value = state;
	document.form1.mailKey.value = id;
	checkField();
}

function showContent(className) {
	$('#content').show();
	if (className == 'mailDetails') {
		$('.newMail').hide();
		$('.mailDetails').show();
		$('#mailAddr').addClass('field_RO');
		$('#mailSubj').addClass('field_RO');
		$('#mailMsg').addClass('field_RO');
	} else {
		$('.mailDetails').hide();
		$('.newMail').show();
		$('#mailAddr').removeClass('field_RO').val('');
		$('#mailSubj').removeClass('field_RO').val('');
		$('#mailMsg').removeClass('field_RO').val('');
	}
	
}

</script>
</head>

<body topmargin="0" background="images/main_bg.jpg"	onload="init();chkUnitID();">
<form id="form1" name="form1" method="post"	onSubmit="return checkField()">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="mailKey" value="">
	<input type="hidden" name="userID" value="<%=session.getAttribute("uid")%>">
<table border="0" width="700" cellspacing="0" cellpadding="0">
	<tr align="center" valign="middle">
		<td colspan="3"><font size="4" face="標楷體" color="#FF9900">email發送及記錄查詢</font>
		<hr size="1" color="#008000" width="700" align="left">
		</td>
	</tr>
	<tr>
		<td>預定發送日期：</td>
		<td>
			<%=util.View.getPopCalndar("field_Q","q_mailDate_s", obj.getQ_mailDate_s())%>
			~
			<%=util.View.getPopCalndar("field_Q","q_mailDate_e", obj.getQ_mailDate_e())%>
		</td>
	</tr>
	<tr>
		<td>發送情形：</td>
		<td>
			<select class="field_Q" id="q_sendType" name="q_sendType">
			<%=util.View.getTextOption("1;待發送;2;已發送;3;發送失敗;",obj.getQ_sendType()) %>
			</select>
		</td>
		<td>
			<input type="button" value="查詢" class="Button" onClick="checkState(this.name);" id="queryAllBtn" name="queryAll">
			<input type=button value="新增寄件" class="Button" onClick="showContent('newMail');" id="createMailBtn" name="createMailBtn">
			<input type="hidden" name="unitID" value="<%=session.getAttribute("unitid")%>">
		</td>
	</tr>
	<tr>
		<td colspan="3" style="padding: 10px 0 0;">
		<div id="listContainer" class="bg">
		<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
			<thead id="listTHEAD">
				<tr>
					<th class="listTH" nowrap>No.</th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">發送狀態</a></th>
					<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">預定發送時間</a></th>
					<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">完成發送時間</a></th>
					<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">收件者</a></th>
					<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">主旨</a></th>
					<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">內容</a></th>
					
				</tr>
			</thead>
			<tbody id="listTBODY">
				<%
					boolean primaryArray[] = { true, false, false, false, false, false, false };
					boolean displayArray[] = { false, true, true, true, true, true, true};
					String[] alignArray = { "center", "center", "center", "center", "left", "center", "center" };
					out.write(View.getQuerylist(primaryArray, displayArray, alignArray, objList, obj.getQueryAllFlag()));
				%>
			</tbody>
		</table>
		</div>
		</td>
	</tr>
</table>
<hr size="1" color="#008000" width="700" align="left">
<table width="700" class="bg" id="content">
	<tr align="center" valign="middle">
		<td colspan="2">
			<span class="newMail" style="color: #FF9900;">新增寄件</span>
			<span class="mailDetails" style="color: #FF9900;">mail詳細內容</span>
		<hr color="#008000" align="center">
		</td>
	</tr>
	<tr class="newMail">
		<td width="100"><font color="red">*</font>預定發送時間：</td>
		<td>
			<%=util.View.getPopCalndar("field","mailDate", obj.getMailDate())%>
			<input class="field" name="mailTimeH" maxlength="2" size="2" value="<%=obj.getMailTimeH()%>"> : 
			<input class="field" name="mailTimeM" maxlength="2" size="2" value="<%=obj.getMailTimeM()%>"> : 
			<input class="field" name="mailTimeS" maxlength="2" size="2" value="<%=obj.getMailTimeS()%>">
			(HH:MM:SS)
		</td>
	</tr>
	<tr class="mailDetails">
		<td>預定發送時間：</td>
		<td><input class="field_RO" name="mailDateTime" maxlength="50" size="50" value="<%=obj.getMailDateTime()%>"></td>
	</tr>
	<tr class="mailDetails">
		<td>完成發送時間：</td>
		<td><input class="field_RO" name="sendDateTime" maxlength="50" size="50" value="<%=obj.getSendDateTime()%>"></td>
	</tr>
	<tr class="mailDetails">
		<td>發送狀態：</td>
		<td><input class="field_RO" name="sendType" maxlength="50" size="50" value="<%=obj.getSendType()%>"></td>
	</tr>
	<tr>
		<td><font color="red" class="newMail">*</font>收件者：</td>
		<td><input name="mailAddr" id="mailAddr" maxlength="50" size="50" value="<%=obj.getMailAddr()%>"></td>
	</tr>
	<tr>
		<td><font color="red" class="newMail">*</font>主旨：</td>
		<td><input name="mailSubj" id="mailSubj" maxlength="50" size="50" value="<%=obj.getMailSubj()%>"></td>
	</tr>
	<tr>
		<td><font color="red" class="newMail">*</font>內容：</td>
		<td><textarea rows="8" cols="60" name="mailMsg" id="mailMsg"><%=obj.getMailMsg()%></textarea></td>
	</tr>
	<tr class="newMail">
		<td colspan="2" align="center">
			<input type="button" value="存檔" class="Button" onClick="checkState(this.name);" id="saveBtn" name="save">
		</td>
	</tr>
</table>
</form>
</body>
</html>
