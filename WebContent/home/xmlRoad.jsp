<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ include file="head.jsp" %>
<%
String ma_lo = request.getParameter("MA_LO");
String q_ma46 = request.getParameter("q_ma46");
String uri = request.getRequestURI();
String sql ="";
if((q_ma46!=null)&&(!q_ma46.equals(""))){	
	sql="select kcde_2,kcnt from ckeyn where kcde_1='48' and kcde_2<>'/*  */' and krmk='"+q_ma46+"'  order by kcde_2";
}else if((ma_lo!=null)&&(!ma_lo.equals(""))){
	sql="select kcde_2,kcnt from ckeyn where kcde_1='48' and kcde_2<>'/*  */' and krmk in (select kcde_2 from ckeyn where kcde_1='46' and kcde_2<>'/*  */' and krmk='"+ma_lo+"') order by kcde_2";
}else{
	sql="select kcde_2,kcnt from ckeyn where kcde_1='48' and kcde_2<>'/*  */' order by kcde_2";
}

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		
    //System.out.println("sql==>"+sql);
	ResultSet rs = db.querySQL(sql);
	while (rs.next()){
		strXML.append("<record signNo=\"").append(rs.getString("kcde_2")).append("\" signName=\"").append(rs.getString("kcnt")).append("\" uri=\"").append(uri).append("\" /> ");
	}			
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>