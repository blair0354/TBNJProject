<%@ page contentType="text/html;charset=utf-8"%>
<jsp:useBean id="obj" scope="page" class="sys.wm.SYSWM001F"/>
<%
String newsID = request.getParameter("newsID");
String isHTML = "N";
obj.setNewsID(newsID);
obj.queryOne();
if ("checked".equals(obj.getIsHTML())) isHTML = "Y";
%>
<html>
<head>
<title>系統公告</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type"   content="text/html; charset=utf-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="private"/>
<link rel="stylesheet" href="js/default.css" type="text/css">
<script language="javascript" src="js/validate.js"></script>
<script language="javascript" src="js/function.js"></script>
<script language="javascript">
function getFile(fileID){
	if (fileID.length>0) {
		var arrFileName = fileID.split(":;:");
		if (arrFileName.length>1) {
		    var prop='';
		    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
		    prop=prop+'width=400,';
		    prop=prop+'height=400';
		    var url = "downloadFileSimple?fileID=" + fileID +":fileName=測試";    
		    window.open(url,'下傳檔案',prop);
		} else {
			alert("無法下載該檔案，因為檔案資訊不完整，若問題持續，請洽系統管理者!");
		}		
	} else {
		alert("目前沒有任何檔案可供下載!");	
	}
}
</script>
<style type="text/css">
</style>
</head>
<body>
<center>

<table class="table_form" width="96%">
	<tr>
		<td  colspan="2" class="td_form" style="background:blue;color:white;text-align:left">【系統公告】</td>
	</tr>
	<tr>		
		<td class="td_form" width="20%" >&nbsp;公告主旨：</td>
		<td class="td_form_white" style="text-align:left"><%=obj.getNewsSubject() %></td>
	</tr>	
	<tr>		
		<td class="td_form">&nbsp;公告期限：</td>
		<td class="td_form_white"  style="text-align:left">
			<%=util.Common.MaskDate(obj.getNewsDateS()) + " ~ " + util.Common.MaskDate(obj.getNewsDateE())%></td>
	</tr>		
	<tr>		
		<td class="td_form" >&nbsp;公告內容：</td>
		<td valign="top" class="td_form_white"  style="text-align:left" height="150"><%=util.Common.FormatStr(obj.getNewsContent(), isHTML) %>
</td>
	</tr>	
	<%=obj.getItemPictureHTML()%>
		
	<tr >
		<td class="td_form_white" colspan="2" style="text-align:center">
			<input class="toolbar_default" type="button" name=btnClose value="關閉視窗" onClick="window.close();">		</td>
	</tr>	
</table>
</center>

</body>
</html>

