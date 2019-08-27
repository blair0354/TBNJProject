<%@ page contentType="application/xml;charset=utf-8" import="java.sql.*,util.*"%>
<%@ include file="head.jsp" %>
<%
String signNo1 = request.getParameter("signNo1");
String signNo2 = request.getParameter("signNo2");
String uri = request.getRequestURI();
String sql ="";
if(signNo1!=null){	
	sql=" select signNo, signName from SYSCA_Sign " +
		" where signNo like '" + signNo1.substring(0,1) + "__0000'" +
		" and signNo <> '" + signNo1 + "'";
}else{
	sql=" select signNo, signName from SYSCA_Sign " +
		" where signNo like '" + signNo2.substring(0,3) + "____'" +
		" and signNo <> '" + signNo2 + "'";
}

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		

	ResultSet rs = db.querySQL(sql);
	while (rs.next()){
		strXML.append("<record signNo=\"").append(rs.getString(1)).append("\" signName=\"").append(rs.getString(2)).append("\" uri=\"").append(uri).append("\" /> ");
	}			
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>