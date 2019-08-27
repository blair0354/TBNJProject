<%@ page contentType="text/html;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ include file="head.jsp" %>
<%
String af_lo = request.getParameter("af_lo");
String af_seq = request.getParameter("af_seq");
String af_fseq = request.getParameter("af_fseq");
String uri = request.getRequestURI();
String[] sql =new String[1];
sql[0]="delete from cattf where af_lo="+Common.sqlChar(af_lo)+" and af_seq="+Common.sqlChar(af_seq)+" and af_fseq="+Common.sqlChar(af_fseq);

Database db = new Database();
try {		
    System.out.println(sql[0]);
	db.excuteSQL(sql);				
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

%>
<html>
<head>
</head>
<body onload="window.opener.location.reload();self.close();">
</body>
</html>