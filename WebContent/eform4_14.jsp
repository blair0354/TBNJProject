<!--
程式目的：LCAAP140
程式代號：LCAAP140
程式日期：1020103
程式作者：TzuYi.Yang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp" %>
<jsp:useBean id="obj" scope="request" class="lca.ap.LCAAP140F">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<%
	ServletContext context = getServletContext();
	String filestoreLocation = context.getInitParameter("filestoreLocation");
	String reportLocation = context.getInitParameter("reportLocation");
	String uip = request.getRemoteAddr(); 
	obj.setContext(context);
	
	if("clsQry".equals(obj.getState())){
		obj.setItemPicture1("");
		obj.setItemPicture2("");
		obj.setState("init");
	}
	if("genRP".equals(obj.getState())){
		obj.genReport();
		obj=new lca.ap.LCAAP140F();
		obj.setItemPicture1("");
		obj.setItemPicture2("");
		obj.setState("init");
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
   
function fldchk()
{
  	var errmsg = "";
/* 	
  	if(form1.queryType[0].checked == false && form1.queryType[1].checked == false){
  		errmsg = errmsg + "請選擇欲上傳的條件種類!\n";
  	}
  	
  	if(form1.itemPicture1.value == "" && form1.itemPicture2.value == ""){
  		errmsg = errmsg + "請上傳欲查詢的文字檔!\n";
  	}
  	
  	if(form1.print_kind[0].checked == false && form1.print_kind[1].checked == false){
  		errmsg = errmsg + "請選擇報表種類!\n";
  	}
  	
  	if(form1.print_type[0].checked == false && form1.print_type[1].checked == false){
  		errmsg = errmsg + "請選擇報表格式!\n";
  	}
*/
	if (errmsg!=""){
		alert(errmsg);
	}else{
	 	alert("報表產製中");
	 	form1.state.value="genRP";
	 	form1.submit();	
	} 
}

function clearLimit(){
   	form1.state.value="clsQry";
	form1.submit();
}

//一次只能選擇一種檔案查詢方式
function choiceQuery(v){
	if(v == "1"){
		form1.btn_itemPicture1.disabled = false;
		form1.btn_itemPicture1Download.disabled = false;
		form1.itemPicture2.value = "";
		form1.btn_itemPicture2.disabled = true;
		form1.btn_itemPicture2Download.disabled = true;
	}else{
		form1.btn_itemPicture2.disabled = false;
		form1.btn_itemPicture2Download.disabled = false;
		form1.itemPicture1.value = "";
		form1.btn_itemPicture1.disabled = true;
		form1.btn_itemPicture1Download.disabled = true;
	}
	
}

function checkPrint_kind(){
	if(form1.print_kind[1].checked){
		spanRHD10.style.display="";
	}else{
		form1.check_RHD10.checked=false;
		spanRHD10.style.display="none";
	}
}

</script>
</head>

<body topmargin="0" background="images/main_bg.jpg"	onLoad="chkUnitID();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post"	onSubmit="return checkField();">
<table border="0" width=660 cellspacing="0" cellpadding="0">
	<tr align="center" valign="middle">
		<td colspan="3"><font size="4" face="標楷體" color="#FF9900">勞保局批次歸戶清冊</font>
		<hr size="1" color="#008000" width="660" align="left">
		</td>
	</tr>
	<tr>
		<td><input name="queryType" type="radio" value="1" onClick="choiceQuery(this.value);">統一編號多筆：</td>
		<td>
			[<input class="field_RO" type="text" name="itemPicture1" size="20" maxlength="300" value="">]
			<input class="field" type="button" name="btn_itemPicture1" onclick="openUploadWindow('itemPicture1','');" value="上傳檔案" disabled>
			<input class="field" type="button" name="btn_itemPicture1Download" onclick="openDownloadWindow(form1.itemPicture1.value);" value="下載檔案" disabled>
		</td>
	</tr>
	<tr>
		<td><input name="queryType" type="radio" value="2" onClick="choiceQuery(this.value);">姓名多筆：</td>
		<td>
			[<input class="field_RO" type="text" name="itemPicture2" size="20" maxlength="300" value="">]
			<input class="field" type="button" name="btn_itemPicture2" onclick="openUploadWindow('itemPicture2','');" value="上傳檔案" disabled>
			<input class="field" type="button" name="btn_itemPicture2Download" onclick="openDownloadWindow(form1.itemPicture2.value);" value="下載檔案" disabled>
		</td>
	</tr>
	<tr>
		<td>報表種類：</td>
		<td>
		<input name="print_kind" type="radio" value="1" onClick="checkPrint_kind()">土地&nbsp;&nbsp;
		<input name="print_kind" type="radio" value="2" onClick="checkPrint_kind()">建物
		<span id="spanRHD10" style="display:none" ><input name="check_RHD10" type="checkbox" value="Y" >顯示基地座落</span>
		</td>
	</tr>
	<tr>
		<td>報表格式：</td>
		<td>
		<input name="print_type" type="radio" value="2">PDF&nbsp;&nbsp;
		<input name="print_type" type="radio" value="3">文字檔
		</td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<font color="#333333" size="2">
				<input type=button id="button1" name="button1" value="製表" class=Button onClick="javascript:fldchk();">&nbsp;
				<input name="button2" type="button" class=Button value="清除" onClick="javascript:clearLimit();" />
			</font>
		</td>
	</tr>
</table>
<hr size="1" color="#008000" width="660" align="left">
<input type="hidden" name="state">
<input type="hidden" name="filestoreLocation" value="<%=filestoreLocation%>">	
<input type="hidden" name="reportLocation" value="<%=reportLocation%>">	
<input type="hidden" name="uip" value="<%=uip%>">
<input type="hidden" name="userID" value="<%=session.getAttribute("uid")%>">
<input type="hidden" name="unitID" value="<%=session.getAttribute("unitid")%>">
</form>
</body>
</html>
