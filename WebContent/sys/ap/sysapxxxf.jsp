<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="../../home/head.jsp" %>
<%
if (user.getIsAdminManager().equals("Y")) {
	util.MsgUtil.setMsg(util.Common.get(request.getParameter("msg")));
	
%>

<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;  //二維陣列, 新增時, 設定預設值
function checkField(){
    var alertStr=checkEmpty(form1.msg,"廣播訊息");
    if(alertStr.length!=0){ alert(alertStr); return false; }
}

function clearField(){
	form1.msg.value="";
	form1.submit();
}
</script>
</head>
<body topmargin="0">
<br>
<form id="form1" name="form1" method="post">
<table align="center" cellpadding="0" cellspacing="0">
<!--Form區============================================================-->
<tr><td class="bg">

    <table class="table_form" width="100%" height="100%">
    <tr>
    <td class="td_lable">請輸入要廣播的訊息</td>
    </tr>
    <tr>
        <td align="center" class="td_form_white" >
            <textarea name="msg" cols="60" rows="4" class="field"><%=util.MsgUtil.getMsg()%></textarea></td>
    </tr>
    <tr>
        <td class="queryTDInput" colspan="3" style="text-align:center;">
            <input class="toolbar_default" followPK="false" type="submit" name="btnSubmit" value="確　　定" >
            　
            <input class="toolbar_default" followPK="false" type="button" name="btnCancel" value="取　　消" onClick="clearField();">        </td>
    </tr>
    </table>

</td></tr>
</table>
</form>
</body>
</html>
<%
}
%>
