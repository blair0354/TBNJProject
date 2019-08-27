<%@ page  contentType = "text/html;charset=utf-8"   import="java.util.Enumeration" isErrorPage="true" %>
<html>
<head>
<title>例外錯誤網頁</title>
</head>
<body >

<div style="font-size:32;color:blue">例外錯誤網頁</div>
<p>
　　使用者處理動作如下：<br>
　　(1)煩請記錄產生錯誤的執行動作<br>
　　(2)煩請記錄產生錯誤的輸入資料<br>
　　(3)煩請通知系統負責人員<br>

<p>
<input type=button name=btn_view value="顯示詳細錯誤訊息" onclick="btn_view_click();">

<div id="errorshow" style="display:none">
	<p>
	<!-- Print Application Attributes -->
	<table border="1">
		<tr>
		    <td colspan="2">Application Attributes</td>
		</tr>
		<%
		Enumeration appAttributeEnum = application.getAttributeNames();
		while (appAttributeEnum.hasMoreElements()) {
			String name = (String)appAttributeEnum.nextElement();
			%>
			<tr>
				<td><%= name %></td>
				<td><%= application.getAttribute(name) %></td>
			</tr>
		<%}%>
	</table>

	<p>
	<!-- Print Request Attributes -->
	<table border="1">
		<tr>
			<td colspan="2">Request Attributes</td>
		</tr>
		<%
		Enumeration attributeEnum = request.getAttributeNames();
		while (attributeEnum.hasMoreElements()) {
			String name = (String)attributeEnum.nextElement();
			%>
			<tr>
				<td><%= name %> </td>
				<td><%= request.getAttribute(name) %> </td>
			</tr>
		<%}%>
	</table>

	<p>
	<!-- Print Parameters -->
	<table border="1">
		<tr>
			<td colspan="2">Parameters</td>
		</tr>
		<%
		Enumeration parameterEnum = request.getParameterNames();
		while (parameterEnum.hasMoreElements()) {
			String name = (String)parameterEnum.nextElement();
			%>
			<tr>
				<td><%= name %> </td>
				<td><%= request.getParameter(name) %> </td>
			</tr>
		<%}%>
	</table>
</div>

</body>
</html>
<script>
function btn_view_click(){
	if (document.all.item("errorshow").style.display=="none"){
		document.all.item("errorshow").style.display = "block";
		document.all.item("btn_view").value="隱藏詳細錯誤訊息";
	}else{
		document.all.item("errorshow").style.display = "none";
		document.all.item("btn_view").value="顯示詳細錯誤訊息";
	}
}
</script>