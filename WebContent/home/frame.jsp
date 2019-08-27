<%@ page contentType="text/html;charset=utf-8" %>
<html>
<head>
<title><%=application.getInitParameter("SystemCName")%></title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
</head>
<frameset rows="94,*" cols="*" border="0" framespacing="0"> 
	<frame id="title" name="title" border="0" framespacing="0" frameborder="0" noresize src="title.jsp"  scrolling="no">
	<frame id="fbody" name="fbody"  border="0" framespacing="0" frameborder="0" noresize src="body.jsp?sysID=<%=session.getAttribute("sysID")%>" scrolling="yes">	
</frameset>
</html>
