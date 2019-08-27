<!--
程式目的：各單位管理者清冊
程式代號：eform4_20, lcaap230f
程式日期：1060419
程式作者：Rhonda Ke
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<jsp:useBean id="obj" scope="request" class="lca.ap.LCAAP230F">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList" />
<%
	ServletContext context = getServletContext();
	String filestoreLocation = context.getInitParameter("filestoreLocation"); 
	String reportLocation = context.getInitParameter("reportLocation");
	String uip = request.getRemoteAddr(); 
	obj.setContext(context);
	
	if("init".equals(obj.getState())){
		obj.setNoLoginDays("90");
	}
	
	if("genRP".equals(obj.getState())){
		String fileFolder = obj.genReport();
		if (!fileFolder.equals("")) {
			String url = request.getContextPath()+"/ReportServletTxt?pageName=eform_4_20&fileName="+reportLocation+"::;:::"+fileFolder;
			System.out.println(url);
	    	response.sendRedirect(url);
		}
	}
	
%>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp" %>
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

function init() {
	if(form1.unitID.value != "00"){
		alert("此作業只提供內政部地政司使用");
		top.location.reload();
	}
	
	$('.content').hide();
	$('#SendMailMemo').hide();
	
	<% if (obj.getState().equals("init")) {%>
		form1.isStopN.checked = true;
		form1.isSendMailN.checked = true;
		form1.isManagerA.checked = true;
	<%} else { %>
		form1.isStop<%=obj.getIsStop()%>.checked = true;
		form1.isSendMail<%=obj.getIsSendMail()%>.checked = true;
		form1.isManager<%=obj.getIsManager()%>.checked = true;
	<%}%>
}
   
function checkField() {
  	var alertStr = "";
  	
  	alertStr += checkEmpty(form1.noLoginDays, '未登入系統天數');
  	alertStr += checkInt(form1.noLoginDays, '未登入系統天數');
  	//alertStr += checkEmpty(form1.isStop, '是否停用帳號');
  	//alertStr += checkEmpty(form1.isSendMail, '是否寄送mail');
  	
  	
  	
  	if (form1.isSendMailY.checked) {
  		alertStr += checkEmpty(form1.mailSubj, 'mail主旨');
  		alertStr += checkEmpty(form1.mailMsg, 'mail內容');
  	}
  	
	
  	
	if (alertStr!=""){
		alert(alertStr);
		return false;
	}else{
		if(form1.isStop[0].checked == true){
			alertStr="產製報表並將超過"+form1.noLoginDays.value+"未登入的帳號停用!\n";
		}
		if(form1.isSendMail[0].checked == true){
			alertStr+="產製報表並寄送EMAIL給超過"+form1.noLoginDays.value+"未登入的帳號!";
		}
		
		if (alertStr!=""){
			if(confirm(alertStr)){
			 	alert("報表產製中!");
			 	form1.state.value="genRP";
			 	return true;
		 	}else{
		 		return false;
		 	}
	 	}else{
	 		alert("報表產製中!");
			form1.state.value="genRP";
			return true;
	 	}
	}
	
	
}

function showMailContent(val) {
	if (val) {
		$('.content').show();
		$('#SendMailMemo').show();
	} else {
		$('.content').hide();
		$('#SendMailMemo').hide();
	}
}

</script>
</head>

<body topmargin="0" background="images/main_bg.jpg"	onLoad="showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post"	onSubmit="return checkField()">
	<input type="hidden" name="state">
    <input type="hidden" name="filestoreLocation" value="<%=filestoreLocation%>">	
    <input type="hidden" name="reportLocation" value="<%=reportLocation%>">	
    <input type="hidden" name="uip" value="<%=uip%>">
    <input type="hidden" name="userID" value="<%=session.getAttribute("uid")%>">
    <input type="hidden" name="unitID" value="<%=session.getAttribute("unitid")%>">
<table border="0" width="660" cellspacing="0" cellpadding="0">
	<tr align="center" valign="middle">
		<td colspan="3"><font size="4" face="標楷體" color="#FF9900">各單位管理者清冊</font>
		<hr size="1" color="#008000" width="660" align="left">
		</td>
	</tr>
	<tr>
	    <%if(obj.getState().equals("init")){%>
		<td colspan="2">
			查詢縣市： 
			<select class="field" id="txtcity_no" name="txtcity_no" onChange="changeCityUnit('txtcity_no','txtunit','');">
				<%=util.View.getOption("select kcde_2,kcde_2+'-'+kcnt as kcnt from rkeyn where kcde_1='45' and left(kcde_2,2)<>'/*' and left(kcde_2,2)<>'20' and left(kcde_2,2)<>'- ' and left(krmk,2)<>'00' order by krmk",obj.getTxtcity_no()) %>
			</select>
			&nbsp;事務所： 
			<select name="txtunit" id="txtunit" style="background:ffffcc">
				<script>changeCityUnit('txtcity_no','txtunit','<%=obj.getTxtunit()%>');</script>
			</select>
		</td>
		<%}else{%>
		<td colspan="2">
			查詢縣市：
			<select class="field" id="txtcity_no" name="txtcity_no" onfocus="defaultIndex=this.selectedIndex" onChange="this.selectedIndex=defaultIndex;">
				<%=util.View.getOption("select kcde_2,kcde_2+'-'+kcnt as kcnt from rkeyn where kcde_1='45' and left(kcde_2,2)<>'/*' and left(kcde_2,2)<>'20' and left(kcde_2,2)<>'- ' and left(krmk,2)<>'00' order by krmk",obj.getTxtcity_no()) %>
			</select>
			&nbsp;事務所： 
			<select name="txtunit" id="txtunit" style="background:ffffcc" onfocus="defaultIndex=this.selectedIndex" onChange="this.selectedIndex=defaultIndex;">
				<script>changeCityUnit('txtcity_no','txtunit','<%=obj.getTxtunit()%>');</script>
			</select>
		</td>
		<%}%>
	</tr>
	<tr>
		<td colspan="2"><font color="red">*</font>未登入系統天數：
		<input name="noLoginDays" id="noLoginDays" maxlength="10" size="10" tabindex="2" value="<%=obj.getNoLoginDays()%>">
		</td>
	</tr>
	<tr>
		<td colspan="2"><font color="red">*</font>帳號種類：
			<input name="isManager" id="isManagerA" type="radio" value="A"> 全部
			<input name="isManager" id="isManagerY" type="radio" value="Y"> 管理者
			<input name="isManager" id="isManagerN" type="radio" value="N"> 一般使用者
		</td>
		<td>
			
		</td>
	</tr>
	<tr>
		<td colspan="2"><font color="red">*</font>是否停用帳號：
			<input name="isStop" id="isStopY" type="radio" value="Y"> 是
			<input name="isStop" id="isStopN" type="radio" value="N"> 否
		</td>
		<td>
			
		</td>
	</tr>
	<tr>
		<td colspan="2"><font color="red">*</font>是否寄送mail： 
			<input name="isSendMail" type="radio" id="isSendMailY" value="Y" onclick="showMailContent(true);"> 是
			<input name="isSendMail" type="radio" id="isSendMailN" value="N" onclick="showMailContent(false);"> 否
			<span id="SendMailMemo"><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;信件將於每日17:00開始寄出!</font>
		</td>
	</tr>
	<tr class="content">
		<td nowrap><font color="red">*</font>mail主旨： </td>
		<td><input name="mailSubj" id="mailSubj" maxlength="50" size="50" tabindex="2" value="<%=obj.getMailSubj()%>"></td>
	</tr>
	<tr class="content">
		<td nowrap><font color="red">*</font>mail內容：</td>
		<td><textarea rows="10" cols="50" name="mailMsg" id="mailMsg"><%=obj.getMailMsg() %></textarea></td>
	</tr>
	<tr>
		<td align="center" colspan="2" style="padding: 5px;">
			<input type="submit" value="製表" class="Button" id="button1" name="button1">
		</td>
	</tr>
</table>
</form>
</body>
</html>
