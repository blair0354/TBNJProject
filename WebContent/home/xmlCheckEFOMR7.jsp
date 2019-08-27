<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ include file="head.jsp" %>
<%
String sTQRY_TYPE = request.getParameter("sTQRY_TYPE");
//String sql="select IMP_TYPE,IMP_YMD,IMP_TIME from EFORM7 where TQRY_TYPE="+Common.sqlChar(sTQRY_TYPE)+" order by IMP_TYPE";
String sql="select IMP_TYPE,IMP_YMD,IMP_TIME,count(*)as CNT from EFORM7 where TQRY_TYPE="+Common.sqlChar(sTQRY_TYPE)+" and IMP_TYPE='1' group by IMP_TYPE,IMP_YMD,IMP_TIME"
		+" UNION ALL "
		+" select IMP_TYPE,IMP_YMD,IMP_TIME,count(*)as CNT from EFORM7 where TQRY_TYPE="+Common.sqlChar(sTQRY_TYPE)+" and IMP_TYPE='2' group by IMP_TYPE,IMP_YMD,IMP_TIME" 
		+" order by IMP_TYPE";

String EFORM7_MSG="";
String EFORM7_YN="N";
String EFORM7_FILE_MSG="";
int EFORM7_CNT=0;
if("1".equals(sTQRY_TYPE)){
	EFORM7_FILE_MSG="勞保局統編";
}else if("2".equals(sTQRY_TYPE)){
	EFORM7_FILE_MSG="勞保局姓名";
}else if("3".equals(sTQRY_TYPE)){
	EFORM7_FILE_MSG="國軍退撫會統編";
}

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		
    //System.out.println("sql==>"+sql);
	ResultSet rs = db.querySQL(sql);
	while (rs.next()){
		EFORM7_YN="Y";
		String imp_ymd=util.Common.formatYYYMMDD(rs.getString("IMP_YMD"), 2);
		String imp_time=util.Common.formatHHMMSS(rs.getString("IMP_TIME"));
		if("1".equals(rs.getString("IMP_TYPE"))){
			EFORM7_MSG+="\n"+imp_ymd+" "+imp_time+"-首次匯入"+rs.getInt("CNT")+"筆";
		}else if("2".equals(rs.getString("IMP_TYPE"))){
			EFORM7_MSG+="\n"+imp_ymd+" "+imp_time+"-更新匯入"+rs.getInt("CNT")+"筆";
		}
		EFORM7_CNT+=rs.getInt("CNT");
	}
	if("Y".equals(EFORM7_YN)){
		EFORM7_FILE_MSG+="-共匯入"+EFORM7_CNT+"筆"+EFORM7_MSG;
	}else{
		EFORM7_FILE_MSG+="無匯入記錄!";
	}
	strXML.append("<record EFORM7_YN=\"").append(EFORM7_YN).append("\" EFORM7_FILE_MSG=\"").append(EFORM7_FILE_MSG).append("\" /> ");
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>