<%@ page contentType="text/html;charset=utf-8" %>
<jsp:useBean id="obj" scope="request" class="util.dtree.TreeView">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String openNodeID = util.Common.get(request.getParameter("openNodeID"));
String q_groupID = util.Common.get(request.getParameter("q_groupID"));
String q_groupName = util.Common.get(request.getParameter("q_groupName"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Authority Center</title>
	<link rel="stylesheet" href="../js/dtree.css" type="text/css" />
	<script type="text/javascript" language="javascript" src="../js/dtree.js"></script>
	<script type="text/javascript" language="javascript">
	var popWin;
	var popWinFlag=false;
	
	function openTreeNode(tid) {	
		var dt = top.frames['dTreeFrame'].d;
		dt.openTo(tid, true);
	}	
	
	function reloadTree(tid) {
		var sScript = document.location.pathname;
		sScript = sScript.substring(sScript.lastIndexOf("/")+1);				
		window.location.href = "dTreeAuthorize.jsp?openNodeID="+tid; //sScript"?openNodeID="+tid;
		popWin.close();
	}
	
	function popFormFolders(url,event) { 	
		popWin = window.open(url,'MyWindow','scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,width=550,height=390');
	}
	
	function delFolders(fatherID, folderID) {
		if (confirm("您確定要刪除該目錄，並其下所有文件?")) {
			var sURL = "docDeleteFolder.asp?fatherID=" + fatherID + "&folderID=" + folderID;
			popWin = window.open(sURL,'MyWindow','scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,width=500,height=250');	
		}
	}	
	
	function disableLeftClick() {
		if (event.button==1) return false;
	}

	function listFormItem() {
		var ml = document.form1;
		var len = ml.elements.length;
		var n = 0, i = 0;

		for (i = 0; i < len; i++) {
		    var e = ml.elements[i];
		    document.write(e.name+'-'+e.value+'<br>');
		}
		return false;
	}
	</script>
</head>	
<body id="body" onload="init();">
<form name="form1" id="form1">
<div class="dtree">
	<a href="javascript: d.openAll();">open all</a> | <a href="javascript: d.closeAll();">close all</a><br /><br />

	<script type="text/javascript" language="javascript">
		<!--
		d = new dTree('d');
		d.config.folderLinks=true;
		d.config.useCookies=false;
		d.config.useIcons = false;
		d.config.objForm =  document.form1;
		d.config.checkBoxName = "auth";
		
		//alert(d.config.objForm.elements.length);
		//d.add('0','-1','<input type=checkbox class=checkbox name=auth id=0 value=0 onclick=d.checkBoxClick(this)> 功能選單','dTreeForm.jsp?sid=0&fid=-1','','','','','','','');		
		//d.add('0','-1','功能選單','dTreeForm.jsp?sid=0&fid=-1','','','','','','','');				
		<%
		out.write(obj.buildCheckBoxTree("功能選單","d.checkBoxClick",false,false));
		//out.write(obj.buildDTreeSortedField());
		obj.connClose();
		%>

		document.write(d);

		function init() {
			var treeNode = "<%=openNodeID%>";	
			if ("<%=openNodeID%>".length>0) {			
				//d.openTo(treeNode,true);
				d.openTo(treeNode);
			}
		}
		//-->		
	</script>

</div>

<input type="hidden" name="q_groupID" value="<%=q_groupID%>" />
<input type="hidden" name="q_groupName" value="<%=q_groupID%>" />

</form>


<br /><br /><br /><br /><br />
<p></p><p></p><p></p><p></p><p></p>
	<div id="divContext" class="contextmenu" style="border: 1px solid gray; display: none; position: absolute; background-color: #ffffd2;">
		<a id="aInsertNav" href="">新增</a><br />
		<a id="aUpdateNav" href="">修改</a><br />
		<a id="aDeleteNav" href="" onclick="return confirm('確定要刪除該節點和其下的所有子節點?');CloseContext();">刪除</a><br />
	</div>
	
	<script type="text/javascript" src="contextmenu.js"></script>
	
</body>
</html>