<%@ page language="java" contentType="application/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../home/head.jsp" %>
<%
	response.addHeader("Pragma", "No-cache");
	response.addHeader("Cache-Control", "no-cache");
	response.addDateHeader("Expires", 1);

	StringBuilder sql = new StringBuilder();
	Database db = null;
	ResultSet rs = null;
	String county=request.getParameter("county");
	//sql.append(" select * from rkeyn where kcde_1='55' and substring(krmk,1,1)='"+county+"' and substring(krmk,2,1)<>'0'");
	sql.append(" select * from rkeyn where kcde_1='55' ");
	if(!county.equals("")){
		sql.append("and substring(krmk,1,1)='"+county+"'");
	}
	sql.append("order by KRMK");
	StringBuilder strXML = new StringBuilder();
	strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	strXML.append("<ResultSet>");
	try {
		db = new Database();
		rs = db.querySQL(sql.toString());
		System.out.println(sql.toString());
		while (rs.next()){
			strXML.append("<record codeid=\""+Common.get(rs.getString("krmk"))+"\" ");
			strXML.append("codename=\""+Common.get(rs.getString("kcnt"))+"\" /> ");
		}			
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("--- home/xmlAddr.jsp SQL ERROR ---\n"+sql.toString());
	} finally {
		sql.setLength(0);
		try{ if(rs != null) { rs.close(); rs=null;}	}catch(Exception e){ e.printStackTrace(); }
		db.closeAll();
	}
	strXML.append("</ResultSet>");	

	out.write(strXML.toString());
%>
