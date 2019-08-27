<%@ page contentType="text/html;charset=utf-8" %>
<jsp:useBean id="obj" scope="request" class="util.dtree.TreeView">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String openNodeID = util.Common.get(request.getParameter("openNodeID"));
String popId = util.Common.get(request.getParameter("popId"));
String popName = util.Common.get(request.getParameter("popName"));

String jsFunction = request.getParameter("js");

String strJavaScript = "";
if (!"".equals(util.Common.get(jsFunction))) strJavaScript += "\nopener." + jsFunction + ";\n\n";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Node Select</title>
	<link rel="stylesheet" href="../js/dtree.css" type="text/css" />
	<script type="text/javascript" language="javascript" src="../js/dtree.js"></script>
	<script type="text/javascript" language="javascript">
	function selectNode(obj,selectName){
		if (isObj(opener.document.all.item("<%=popId%>"))) {		
			opener.document.all.item("<%=popId%>").value=obj.value;		
		}
		if (isObj(opener.document.all.item("<%=popName%>"))) {
			opener.document.all.item("<%=popName%>").value=selectName;		
		}
		<%=strJavaScript%>	
		window.close();
	}
	</script>
</head>	
<body id="body" onload="init();">
<form name="form1" id="form1">
<div class="dtree">
	<p><a href="javascript: d.openAll();">open all</a> | <a href="javascript: d.closeAll();">close all</a></p>

	<script type="text/javascript" language="javascript">
		<!--

		d = new dTree('d');
		d.config.folderLinks=true;
		d.config.useCookies=false;
		d.config.useIcons = false;
		d.config.objForm =  document.form1;
		d.config.checkBoxName = "auth";
		
		//d.add('0','-1','功能選單','dTreeForm.jsp?sid=0&fid=-1&superName=功能選單','','','','','','','');				
		<%
		out.write(obj.buildCheckBoxTree("功能選單","selectNode",false,true));
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

</form>
</body>
</html>