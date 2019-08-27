<!--
程式目的：機關管理作業
程式代號：sysca002f
程式日期：0940603
程式作者：griffin
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ca.SYSCA002F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if (user.getIsAdminManager().equals("Y")) {
	if ("queryAll".equals(obj.getState())) {
		if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
	}else if ("queryOne".equals(obj.getState())) {
		obj = (sys.ca.SYSCA002F)obj.queryOne();	
	}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
		obj.insert();
	}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
		obj.update();
	}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
		obj.delete();
	}
	if ("true".equals(obj.getQueryAllFlag())){
		objList = obj.queryAll();
		if(objList.size()>0){
			//System.out.print(obj.getMa_seq());
			if (obj.getOrganID().equals("")){
				String[] tmpobj=(String[])objList.get(0);
				obj.setOrganID(tmpobj[0]);
				obj=obj.queryOne();
			}
		}
	}
%>

<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css?s=ss" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		//alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="update"){
		alertStr += checkEmpty(form1.organID,"機關代號");
		alertStr += checkEmpty(form1.organAName,"機關全銜");
		//alertStr += checkEmpty(form1.organSName,"機關簡稱");
		//alertStr += checkEmpty(form1.isManager,"是否管理機關");
		//alertStr += checkEmail(form1.organEmail,"電子郵件");
		//alertStr += checkLen(form1.memo, "備註", 250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(organID){
	form1.organID.value=organID;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function choiceFirst(){
	form1.organID.value="";
	form1.currentPage.value="1";
	form1.submit();
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:600px;height:80px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable" border="1">
	<tr>
		<td class="queryTDLable">機關代號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_organID" size="20" maxlength="20" value="<%=obj.getQ_organID()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">機關全銜：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_organAName" size="50" maxlength="50" value="<%=obj.getQ_organAName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="button" name="querySubmit" value="確　　定" onClick="choiceFirst();">
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
		<td class="td_form">&nbsp;<font color="red">*</font>機關代號：</td>
		<td class="td_form_white">
			<input class="field_P" type="text" name="organID" size="20" maxlength="20" value="<%=obj.getOrganID()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">&nbsp;<font color="red">*</font>機關全銜：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="organAName" size="50" maxlength="50" value="<%=obj.getOrganAName()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">&nbsp;機關簡稱：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="organSName" size="30" maxlength="20" value="<%=obj.getOrganSName()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">&nbsp;機關電話：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="organTel" size="10" maxlength="10" value="<%=obj.getOrganTel()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">&nbsp;機關傳真：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="organFax" size="20" maxlength="20" value="<%=obj.getOrganFax()%>">
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
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH"><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">機關代號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">機關簡稱</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false};
	boolean displayArray[] = {true,true};
	String alignArray[] = {"left","left"};
	out.write(util.View.getQuerylist(primaryArray,displayArray,alignArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language="javascript">
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "clear":
			queryOne(form1.organID.value);
	}
}

function gotoPage(iPageNo)
{
    pageListener.beforeGotoPage();
    if (isObj(form1.state)) form1.state.value = "queryAll";
    form1.organID.value="";
	form1.currentPage.value = iPageNo;
	form1.submit();
}
</script>
</body>
</html>
<%
} else {
	out.println("<br><br><br><p align=center>對不起，您沒有足夠的權限執行此功能，若有問題，請洽系統管理者或承辦人員！<br><br></p>");		
}
%>