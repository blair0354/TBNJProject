
<!--
程式目的：群組資料管理
程式代號：sysap002f
程式日期：0940701
程式作者：Dennis Chen
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ap.SYSAP002F">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
    if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
    obj = (sys.ap.SYSAP002F)obj.queryOne();    
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
    obj.insert();
    obj.queryInsertOne();
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
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css" />
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;  //二維陣列, 新增時, 設定預設值
function checkField(){
    var alertStr="";
    if(form1.state.value=="queryAll"){
        //alertStr += checkQuery();
    }else if(form1.state.value=="insert"||form1.state.value=="update"){
        //alertStr += checkEmpty(form1.groupID,"群組代碼");
        alertStr += checkEmpty(form1.groupName,"群組名稱");
    }
    if(alertStr.length!=0){ alert(alertStr); return false; }
    beforeSubmit();
}
function queryOne(groupID){
    form1.groupID.value=groupID;
    form1.state.value="queryOne";
    beforeSubmit();
    form1.submit();
}

function checkURLBak(surl){
	columnTrim(form1.groupID);
	if(form1.groupID.value==""){
		alert("請先執行查詢!");
	}else if( (form1.state.value=="insert")||(form1.state.value=="update")){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		form1.action=surl;
		beforeSubmit();
		form1.submit();
	}
}

function checkURL(surl){
	columnTrim(form1.groupID);
	if(form1.groupID.value==""){
		alert("請先執行查詢!");
	}else if( (form1.state.value=="insert")||(form1.state.value=="update")){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		form1.action=surl;
		beforeSubmit();
		form1.submit();
	}
}

</script>
</head>

<body onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border1" width="100" HEIGHT="25">群組資料</td>
		<td ID=t2 CLASS="tab_border2" width="100"><a href="#" onClick="return checkURL('sysap003f_auth.jsp?hideAssigned=Y');">群組權限</a></td>		
		<td>&nbsp</td>
	</tr>
	<tr>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>		
	</tr>
</TABLE>
<!--Query區============================================================-->
<div id="queryContainer" style="width:300px;height:150px;display:none">
    <iframe id="queryContainerFrame"></iframe>
    <div class="queryTitle">查詢視窗</div>
    <table class="queryTable"  border="1">
    <tr>
        <td class="queryTDLable"  width="30%">群組代碼：</td>
        <td class="queryTDInput">
            <input class="field_Q" type="text" name="q_groupID" size="10" maxlength="10" value="<%=obj.getQ_groupID()%>">
        </td>
    </tr>
    <tr>
        <td class="queryTDLable"  width="30%">群組名稱：</td>
        <td class="queryTDInput">
            <input class="field_Q" type="text" name="q_groupName" size="20" maxlength="20" value="<%=obj.getQ_groupName()%>">
        </td>
    </tr>
    <tr>
        <td class="queryTDInput" colspan="2" style="text-align:center;">
            <input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
            <input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
        </td>
    </tr>
    </table>
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
    <div id="formContainer">
    <table class="table_form" width="100%" height="100%">
    <tr>
        <td class="td_form"><font color="red">*</font>群組代碼：</td>
        <td class="td_form_white">
            <input class="field_RO" type="text" name="groupID" size="10" maxlength="10" value="<%=obj.getGroupID()%>">
        </td>
    </tr>
    <tr>        
        <td class="td_form"><font color="red">*</font>群組名稱：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="groupName" size="20" maxlength="20" value="<%=obj.getGroupName()%>">
        </td>
    </tr>
    <tr>
        <td class="td_form">備註：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="memo" size="60" maxlength="100" value="<%=obj.getMemo()%>">
        </td>
    </tr>   
    <tr>
        <td class="td_form">異動人員/日期：</td>
        <td class="td_form_white">
            [<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
            <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
        </td>
    </tr>
    </table>
    </div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
    <input type="hidden" name="state" value="<%=obj.getState()%>">
    <input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
    <input type="hidden" name="userID" value="<%=user.getUserID()%>">
    <jsp:include page="../../home/toolbar.jsp" />
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
    <thead id="listTHEAD">
    <tr>
        <th class="listTH" ><a class="text_link_w">NO.</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">群組代碼</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">群組名稱</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">備註</a></th>
    </thead>
    <tbody id="listTBODY">
    <%
    boolean primaryArray[] = {true,false,false};
    boolean displayArray[] = {true,true,true};
    out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
    %>
    </tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>

