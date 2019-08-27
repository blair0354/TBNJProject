<!--
程式目的：基本代碼管理
程式代號：sysca001f
撰寫日期：94/05/10
程式作者：griffin
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=utf-8" %>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj"  scope="request" class="sys.ca.SYSCA001F">
    <jsp:setProperty name='obj' property='*'/>
</jsp:useBean>
<jsp:useBean id="objList"  scope="page" class="java.util.ArrayList"/>
        
<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){	obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.ca.SYSCA001F)obj.queryOne();	
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
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css?1=ss" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>

<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if(alertStr.length==0) {
			form1.oldCodeKindID.value=form1.q_codeKindID.value;
		}		
	}else if(form1.state.value=="insert"||form1.state.value=="update"){	
		alertStr += checkEmpty(form1.codeKindID,"代碼種類種類");
		alertStr += checkEmpty(form1.codeID,"代碼編號");
		alertStr += checkEmpty(form1.codeName,"代碼名稱");
		if(alertStr.length==0) {
			form1.oldCodeKindID.value=form1.codeKindID.value;		
			form1.q_codeKindID.value=form1.codeKindID.value;
			form1.queryAllFlag.value="true";
		}
	}		
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function getCodeKind() {
	if (form1.q_code.value!="" && form1.q_code.value.length>2) {
		form1.q_code.value = form1.q_code.value.toUpperCase();
		getSelectedValue(form1.q_codeKindID, form1.q_code.value);
	}
}

function queryOne(codeKindID,codeKindName,codeID,codeName,codeCon1,codeCon2,codeCon3,memo,editID,editDate){
	form1.state.value="queryOne";
	form1.codeKindID.value=codeKindID;
	form1.codeID.value=codeID;
	form1.codeName.value=codeName;
	form1.codeCon1.value=codeCon1;
	form1.codeCon2.value=codeCon2;
	form1.codeCon3.value=codeCon3;
	form1.memo.value=memo;
	form1.editID.value=editID;
	form1.editDate.value=editDate;
	if (form1.codeKindID.value!="" && form1.codeID.value!="" && form1.codeName.value!="") {
		form1.state.value="queryOneSuccess";
		whatButtonFireEvent('queryOneSuccess');	
	}	
	//beforeSubmit();
	//form1.submit();
}

function init() {
	if (form1.q_codeKindID.value!="" && form1.q_codeKindID.value.length>2) form1.q_code.value = form1.q_codeKindID.value;
}

</script>

</head>
<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">


<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:150px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable" border="1">
		<tr>
			<td class="queryTDLable">代碼種類：</td>
			<td class="queryTDInput"><input type="text" class="field_Q" name="q_code" onBlur="getCodeKind();" size="10"><br><br>
				<select class="field_Q" type="select" name="q_codeKindID" >
				<%=util.View.getOption("select codeKindID, codeKindName from SYSCA_CodeKind order by codeKindName", obj.getQ_codeKindID())%>
				</select>
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
<tr><td class="bg" >
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
        <td class="td_form" >&nbsp;<font color="red">*</font>代碼種類：</td>
		<td class="td_form_white">
			<select class="field_P" type="select" name="codeKindID" >
			<%=util.View.getOption("select codeKindID, codeKindName from SYSCA_CodeKind order by codeKindName", obj.getCodeKindID())%>
			</select>
		</td>
	</tr>
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font>代碼編號：</td>
		<td class="td_form_white">
			<input class="field_P" type="text" name="codeID" size="10" maxlength="10" value="<%=obj.getCodeID()%>">
		</td>		
	</tr>	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font>代碼名稱：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="codeName" size="50" maxlength="25" value="<%=obj.getCodeName()%>">
		</td>		
	</tr>	
	<tr>		
		<td class="td_form">&nbsp;條件一：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="codeCon1" size="10" maxlength="20" value="<%=obj.getCodeCon1()%>">
		</td>		
	</tr>	
	<tr>		
		<td class="td_form">&nbsp;條件二：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="codeCon2" size="10" maxlength="20" value="<%=obj.getCodeCon2()%>">
		</td>		
	</tr>
	<tr>		
		<td class="td_form">&nbsp;條件三：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="codeCon3" size="10" maxlength="20" value="<%=obj.getCodeCon3()%>">
		</td>		
	</tr>		
	<tr>		
		<td class="td_form">&nbsp;代碼備註：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="memo" size="50" maxlength="50" value="<%=obj.getMemo()%>">			
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
	<input type="hidden" name="oldCodeKindID" value="<%=obj.getOldCodeKindID()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>

<!--List區============================================================-->
<tr><td class="bg" >
<div id="listContainer" >
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH"><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#" >代碼種類</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">代碼編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">代碼名稱</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
		<%
		boolean primaryArray[] = {true,  true, true, true, true, true, true, true, true, true};
		boolean displayArray[] = {false, true,  true, true, false, false, false, false, false, false};
		out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
		
		/*以下是util.View.getQuerylist()實作之範例
		StringBuffer sbHEML = new StringBuffer();
		if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
			sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
		}else{
			String rowArray[]=new String[4];
			java.util.Iterator it=objList.iterator();
			while(it.hasNext()){			
				rowArray= (String[])it.next();
				sbHEML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[2]+"')\" >");
				sbHEML.append(" <td class='listTD' >"+rowArray[1]+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rowArray[2]+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rowArray[3]+"</td></tr> ");			
			}
		}
		out.write(sbHEML.toString());	
		*/
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
		case "insert":
			if (form1.oldCodeKindID.value!="") form1.codeKindID.value=form1.oldCodeKindID.value;
			else if(form1.q_codeKindID.value!="") form1.codeKindID.value=form1.q_codeKindID.value;			
			break;	
	}
}
</script>
</body>
</html>