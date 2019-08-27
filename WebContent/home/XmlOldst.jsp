<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="util.*" %>
<%@ page import="java.sql.ResultSet"%> 
<%
String ow09 = request.getParameter("ow09");
String ow_lo=request.getParameter("ow_lo");
String ow_seq=request.getParameter("ow_seq");


String sql ="";
if(ow09!=null){	
	sql = " select * "+
	" from cownr where 1=1 " + 
	" and ow09 = " + Common.sqlChar(ow09) +
	" and ow_lo= " + Common.sqlChar(ow_lo)+
	" and ow_seq=" + Common.sqlChar(ow_seq);
}
System.out.println("===========================:"+sql);
StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");	
Database db = new Database();
	try {		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			strXML.append("<record " + 
							" ow_lo=\""+Common.get(rs.getString("ow_lo")) + "\""+
							" ow_seq=\""+Common.get(rs.getString("ow_seq")) + "\""+
							" ow09=\""+Common.get(rs.getString("ow09")) + "\""+
							" ow_name=\""+Common.get(rs.getString("ow_name")) + "\""+
							" ow_addr=\""+Common.get(rs.getString("ow_addr")) + "\""+
							" ow15_1=\""+Common.get(rs.getString("ow15_1")) + "\""+
							" ow15_2=\""+Common.get(rs.getString("ow15_2")) + "\""+
							" ow15_3=\""+Common.get(rs.getString("ow15_3")) + "\""+
						  " /> ");
		} 
		
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}
strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>