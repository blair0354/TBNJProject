<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="home/head.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="inc/font.css">
<title><%=session.getAttribute("com_title")%></title>
</head>
<frameset framespacing="0" border="0" rows="77,*" frameborder="0">
	<frame name="banner" scrolling="no" noresize target="contents"
		src="menu_top.jsp">
	<frameset cols="162,*">
		<frame name="contents" target="main" scrolling="no" noresize
			src="menu_left.jsp">
		<frame name="main" src="menu_main.jsp">
	</frameset>
	<noframes>
		<body>
			<p>此網頁使用框架，但是您的瀏覽器並不支援。</p>
		</body>
	</noframes>
</frameset>
</html>