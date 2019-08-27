
<!--
程式目的：個人基本資料維護
程式代號：sysap001f_s
程式日期：0950518
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ap.SYSAP001F">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String actItem = request.getParameter("actItem");
if (util.Common.get(user.getUserID()).length()>0) {
	obj.setEmpID(user.getUserID());	
	String confirm = request.getParameter("confirm");
	if (util.Common.get(confirm).equals("確　定　更　新")) {
		obj.setEditID(user.getUserID());
		obj.updatePersonal();
	}	
	obj = (sys.ap.SYSAP001F)obj.queryOne();
	
%>

<html>
<head>
<title>個人基本資料維護</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;  //二維陣列, 新增時, 設定預設值

function checkField(){
    var alertStr="";
	alertStr += checkEmpty(form1.empName,"使用者姓名");
	alertStr += checkEmpty(form1.empTel,"電話"); 
	alertStr += checkEmail(form1.empEmail,"電子郵件信箱");
	alertStr += checkEmpty(form1.empEmail,"電子郵件信箱");
	alertStr += checkEmpty(form1.empPWD, "密碼");
	alertStr += checkDate(form1.takeJobDate,"到職日");          
    if(alertStr.length!=0){ alert(alertStr); return false; }
    form1.submit();
}

function init(s) {	
	if ((s!=null) && (s=="修改完成")) {
		if (confirm("個人基本資料更新完成，是否要關閉視窗？")){
			window.close();
		}		
	} else {
		showErrorMsg(s);
	}
}

function closePersonalWindow() {
	window.close();
}

function activeItem() {
	if (isObj(opener.document.all.item('<%=actItem%>'))) {
		opener.titleBarButtonClick(opener.document.all.item('<%=actItem%>'));
		opener.document.all.item('<%=actItem%>').focus();
	}
}
</script>

</head>

<body topmargin="5" onLoad="init('<%=obj.getErrorMsg()%>');" onbeforeunload="activeItem();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
    <table class="table_form" width="100%" height="100%">
    <tr>
      <td class="td_form"><font color="red">*</font>姓名：</td>
      <td class="td_form_white">
            <input class="field" type="text" name="empName" size="10" maxlength="10" value="<%=obj.getEmpName()%>">        </td>
        <td class="td_form">所屬單位：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="unitID" size="10" maxlength="10" value="<%=obj.getUnitID()%>">        </td>
    </tr>
    <tr>
        <td class="td_form">職稱：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="empTitle" size="15" maxlength="30" value="<%=obj.getEmpTitle()%>">        </td>
        <td class="td_form">到職日：</td>
        <td class="td_form_white">
            <%=util.View.getPopCalndar("field","takeJobDate",obj.getTakeJobDate())%>        </td>
    </tr>
    <tr>
      <td class="td_form"><font color="red">*</font>電話：</td>
      <td class="td_form_white">
            <input class="field" type="text" name="empTel" size="15" maxlength="30" value="<%=obj.getEmpTel()%>">        </td>
      <td class="td_form">傳真：</td>
      <td class="td_form_white">
            <input class="field" type="text" name="empFax" size="15" maxlength="30" value="<%=obj.getEmpFax()%>">        </td>
    </tr>
    <tr>
      <td class="td_form"><font color="red">*</font>電子郵件信箱：</td>
      <td class="td_form_white">
            <input class="field" type="text" name="empEmail" size="15" maxlength="50" value="<%=obj.getEmpEmail()%>">        </td>
        <td class="td_form"><font color="red">*</font>密碼：</td>
        <td class="td_form_white"><input class="field" type="password" name="empPWD" size="15" maxlength="30" value="<%=obj.getEmpPWD()%>"></td>
    </tr>
    <tr>
      <td class="td_form">主管姓名：</td>
      <td class="td_form_white">
            <input class="field" type="text" name="managerName" size="15" maxlength="30" value="<%=obj.getManagerName()%>">        </td>
      <td class="td_form">主管職稱：</td>
      <td class="td_form_white">
            <input class="field" type="text" name="managerTitle" size="15" maxlength="30" value="<%=obj.getManagerTitle()%>">        </td>
    </tr>
    <tr>
      <td class="td_form">主管電話：</td>
      <td class="td_form_white">
            <input class="field" type="text" name="managerTel" size="15" maxlength="30" value="<%=obj.getManagerTel()%>">        </td>        
        <td class="td_form">&nbsp;</td>
        <td class="td_form_white">&nbsp;</td>
    </tr>
    <tr>        
        <td class="td_form">備註：</td>
        <td class="td_form_white" colspan="3">
            <input class="field" type="text" name="memo" size="60" maxlength="200" value="<%=obj.getMemo()%>">        </td>
    </tr>
    <tr>
        <td class="td_form">異動人員/日期：</td>
        <td class="td_form_white" colspan="3">
            [<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
            <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]        </td>
    </tr>
    </table>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
<span id="spanConfirm">
<input class="toolbar_default" type="submit" followPK="false" name="confirm" value="確　定　更　新" >　<input class="toolbar_default" type="button" followPK="false" name="btnClose" value="關　閉　視　窗" onClick="closePersonalWindow();">
</span>
</td></tr>

</table>
</form>

</body>
</html>
<%
} else {
	out.println("<br><br><br><p align=center>對不起，找不到您的個人資料，若問題持續，請洽系統管理者或承辦人員！<br><br></p>");	
}
%>