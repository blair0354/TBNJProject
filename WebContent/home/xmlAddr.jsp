<%@ page contentType="application/xml;charset=utf-8" import="java.sql.*,util.*"%>
<%

String addr1 = request.getParameter("addr1");
String addr2 = request.getParameter("addr2");
String sql ="";
if(addr1!=null){	
	if(addr1.equals("6300000000")||addr1.equals("6400000000")){
		sql=" select addrID, addrName, zipCode from SYSCA_Addr " +
			" where addrID like '" + addr1.substring(0,2) + "___00000'" +
			" and addrID <> '" + addr1 + "'";
	}else{
		sql=" select addrID, addrName, zipCode from SYSCA_Addr " +
			" where addrID like '" + addr1.substring(0,5) + "__000'" +
			" and addrID <> '" + addr1 + "'";					
	}		
}else{
	sql=" select addrID , addrName, zipCode from SYSCA_Addr "+
	    " where addrID like '"+addr2.substring(0,7)+"___'"+
	    " and addrID <> '" + addr2 + "'";
}
//out.write(sql);
StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		

	ResultSet rs = db.querySQL(sql);
	while (rs.next()){
		strXML.append("<record addrID=\""+rs.getString(1)+"\" addrName=\""+rs.getString(2)+"\" zipcode=\""+Common.get(rs.getString(3))+"\" /> ");
	}			
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());

%>