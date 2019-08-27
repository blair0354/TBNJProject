<%@ page contentType="text/html;charset=utf-8" %>
<jsp:useBean id="obj" scope="request" class="util.dtree.TreeEntry">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String btnSave = request.getParameter("btnSave");
String exeKind = util.Common.get(request.getParameter("exeKind"));
String sid = util.Common.get(request.getParameter("sid"));
String fid = util.Common.get(request.getParameter("fid"));
String js = "";
if (!"".equals(util.Common.get(btnSave))) {
	if ("insert".equals(obj.getState())) {
		obj.insert();
	} else if ("update".equals(obj.getState())) {
		obj.update();		
	} else if ("delete".equals(obj.getState())) {
		obj.delete();		
	}	
} else {
	obj.setId(sid);
	obj.setPid(fid);
	obj.setState(exeKind);	
	if ("insert".equals(exeKind)) {
		obj.setPid(obj.getId());
		obj.setPname(obj.getNodeName(obj.getId()));
		obj.setSorted("1000");
		obj.setId("");
	} else if ("update".equals(exeKind)) {
		obj = (util.dtree.TreeEntry) obj.queryOne();	
	} else if ("delete".equals(exeKind)) {
		obj.delete();
	} else {
		obj = (util.dtree.TreeEntry) obj.queryOne();
		obj.setState("update");		
	}
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>

<head>
<title>dTree</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="stylesheet" href="../js/default.css" type="text/css"></link>
<script language="javascript" src="../js/validate.js"></script>
<script language="javascript" src="../js/function.js"></script>
<script language="javascript">
	var popWin;
	var popWinFlag=false;
	
	function checkField(){
		var alertStr="";

		alertStr += checkEmpty(form1.pid,"父節點");
		alertStr += checkAlphaInt(form1.pid,"父節點");		
		//alertStr += checkEmpty(form1.id,"節點代碼");
		//alertStr += checkAlphaInt(form1.id,"節點代碼");
		alertStr += checkEmpty(form1.name,"節點名稱");
		
		if (form1.sorted.value!="") alertStr += checkInt(form1.sorted,"排序" );	
		if(alertStr.length!=0){ alert(alertStr); return false; }
	}
	
	function popNodeSelectForm() { 	
		popWin = window.open("dTreeNodeSelect.jsp?popId=pid&popName=pname",'dTreeNodeSelect','scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,width=350,height=200');
	}
		
	function popWinClose() {
		popWin.close();
	}
	
	function init() {
		var s = "<%=obj.getState()%>";
		if (s=="insertSuccess" || s=="updateSuccess" || s=="deleteSuccess") {	
			if (isObj(opener)) {
				opener.reloadTree('<%=obj.getId()%>');		
			} else {
				window.location.href = "dTreeManage.jsp?openNodeID=<%=obj.getPid()%>";			
			}
		}
	}
	
	function getNode(nId) {
		var x = getRemoteData("dTreeGetNode.jsp",nId);
		var s = eval('(' + x + ')'); 
		form1.pname.value=s.name;	
	}



</script>
</head>

<body topmargin="0" onload="init();">
<form id="form1" name="form1" method="post" onsubmit="return checkField()">
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<table class="table_form" width="100%" height="100%">
	<tr>
	<td class="td_form"><font color="red">*</font>父節點：</td>
	<td class="td_form_white"><input name="pid" type="text" class="field_P" id="pid" value="<%=obj.getPid()%>" autocomplete="off" onchange="getNode(this.value);" size="20" maxlength="20" />
	  <input name="btnSelectPid" type="button" id="btnSelectPid" value="..." class="toolbar_default" onclick="popNodeSelectForm();" /> 
	  <input name="pname" id="pname" type="text" class="field_RO" value="<%=obj.getPname()%>" /></td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>節點代碼：</td>
		<td class="td_form_white">
			<input name="id" type="text" class="field_PRO" id="id" value="<%=obj.getId()%>" size="20" maxlength="20">　
			排序：<input name="sorted" type="text" class="field" id="sorted" value="<%=obj.getSorted()%>" size="9" maxlength="9" />		</td>
		</tr>
	<tr>
	  <td class="td_form"><font color="red">*</font>節點名稱：</td>
	  <td class="td_form_white">
			<input name="name" type="text" class="field" id="name" value="<%=obj.getName()%>" size="45" maxlength="50">		</td>
	  </tr>
	<tr>
	  <td class="td_form">URL：</td>
	  <td class="td_form_white"><input name="url" type="text" class="field" id="url" value="<%=obj.getUrl()%>" size="45" maxlength="120" /></td>
	</tr>
	<tr>
	  <td class="td_form">Title：</td>
	  <td class="td_form_white">
			<input name="title" type="text" class="field" id="title" value="<%=obj.getTitle()%>" size="45" maxlength="100">		</td>
	</tr>
	<tr>
	<td class="td_form">Target：</td>
	<td class="td_form_white"><input name="target" type="text" class="field_P" id="target" value="<%=obj.getTarget()%>" size="20" maxlength="20" /></td>
	</tr>
	<tr>
	<td class="td_form">Icon：</td>
	<td class="td_form_white"><input name="icon" type="text" class="field_P" id="icon" value="<%=obj.getIcon()%>" size="45" maxlength="50" /></td>
	</tr>
	<tr>
	<td class="td_form">IconOpen：</td>
	<td class="td_form_white"><input name="iconOpen" type="text" class="field_P" id="iconOpen" value="<%=obj.getIconOpen()%>" size="45" maxlength="50" /></td>
	</tr>
	<tr>
	<td class="td_form">Opened：</td>
	<td class="td_form_white"><input name="opened" type="text" class="field_P" id="opened" value="<%=obj.getOpened()%>" size="10" maxlength="10" /></td>
	</tr>
	<tr>
	<td class="td_form">btnRead：</td>
	<td class="td_form_white"><input name="btnRead" type="text" class="field_P" id="btnRead" value="<%=obj.getBtnRead()%>" size="45" maxlength="50" /></td>
	</tr>
	<tr>
	<td class="td_form">btnWrite：</td>
	<td class="td_form_white"><input name="btnWrite" type="text" class="field_P" id="btnWrite" value="<%=obj.getBtnWrite()%>" size="45" maxlength="50" /></td>
	</tr>
	</table>
</td>
</tr>
<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
<span id="spanToolbar">&nbsp;|
<span id="spanInsert">
<input class="toolbar_default" type="submit" name="btnSave"  value="存　檔">&nbsp;|
</span>
</span>	
</td></tr>
</table>
</form>
</body>

</html>