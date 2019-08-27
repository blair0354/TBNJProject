<%@ page contentType="text/html;charset=utf-8" import="java.sql.*,util.*"%>
<%@ include file="head.jsp" %>
<%!
//將日期加年月日
private String getDotDate(String sDate) {
	if (sDate!=null && sDate.length()>3) {		
		if (sDate.length()==7) return sDate.substring(0,3)+"."+sDate.substring(3,5)+"."+sDate.substring(5);
		else if (sDate.length()==5) return sDate.substring(0,3)+"."+sDate.substring(3,5);
		else return sDate;
	}
	return "";
}    
%>
<%
String popField = Common.get(request.getParameter("popField"));
String preWord = Common.get(request.getParameter("preWord"));
String isLookup = Common.get(request.getParameter("isLookup"));
String jsFunction = Common.get(request.getParameter("js"));
String strJavaScript = "";

String q_bulletinDate = Common.get(request.getParameter("q_bulletinDate"));
String q_suiteDateS = Common.get(request.getParameter("q_suiteDateS"));
String q_suiteDateE = Common.get(request.getParameter("q_suiteDateE"));

if ("Y".equals(Common.get(isLookup))) {
	strJavaScript = "" +	
	"	if (isObj(opener.document.all.item(\"suitDateS\"))) {\n" +
	"		opener.document.all.item(\"suitDateS\").value=suiteDateS;\n" +
	"	}\n" +
	"	if (isObj(opener.document.all.item(\"suitDateE\"))) {\n" +
	"		opener.document.all.item(\"suitDateE\").value=suiteDateE;\n" +
	"	}\n" +
	"	if (isObj(opener.document.all.item(\"suiteDateS\"))) {\n" +
	"		opener.document.all.item(\"suiteDateS\").value=suiteDateS;\n" +
	"	}\n" +
	"	if (isObj(opener.document.all.item(\"suiteDateE\"))) {\n" +
	"		opener.document.all.item(\"suiteDateE\").value=suiteDateE;\n" +
	"	}\n";	
}

if (!"".equals(Common.get(jsFunction))) strJavaScript += "\nopener." + jsFunction + ";\n\n";

StringBuffer sbHTML = new StringBuffer();
if (Common.get(preWord).length()>0) {
	int count = 0;
	Database db = new Database();
	String sql = "select bulletindate, suitdates, suitdatee from UNTLA_BulletinDate where bulletinkind='"+preWord+"'";
	if (!"".equals(Common.get(q_bulletinDate)))	
		sql += " and bulletinDate = " + Common.sqlChar(q_bulletinDate);
	if (!"".equals(Common.get(q_suiteDateS)))
		sql += " and suiteDateS>="+Common.sqlChar(q_suiteDateS);
	if (!"".equals(Common.get(q_suiteDateE)))
		sql += " and suiteDateE<="+ Common.sqlChar(q_suiteDateE);
	ResultSet rs = db.querySQL(sql+" order by bulletindate desc");	
	
	while (rs.next()) {
		StringBuffer strLink = new StringBuffer(0).append(Common.sqlChar(rs.getString("bulletindate")) ).append( "," ).append(
			Common.sqlChar(rs.getString("suitdates")) ).append( "," ).append(
			Common.sqlChar(rs.getString("suitdatee")));				
		sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectBulletinDate(").append( strLink ).append( ")\" >");
		sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
		sbHTML.append(" <td class='listTD' >").append(getDotDate(rs.getString("bulletindate"))).append("</td> ");
		sbHTML.append(" <td class='listTD' >").append(getDotDate(rs.getString("suitdates")));
		sbHTML.append("~").append(getDotDate(rs.getString("suitdatee"))).append("</td> ");		
		sbHTML.append(" </tr> ");	
		count++;
	}
}
%>
<html>
<head>
<title>公告年月輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../js/default.css?1=ss" type="text/css">
<script language="javascript" src="../js/validate.js"></script>
<script language="javascript" src="../js/function.js"></script>
<script language="javascript" src="../js/tablesoft.js"></script>

<script language="javascript">
function selectBulletinDate(bulletinDate,suiteDateS,suiteDateE){
	if (isObj(opener.document.all.item("<%=popField%>"))) {		
		opener.document.all.item("<%=popField%>").value=bulletinDate;			
	}
	<%=strJavaScript%>
	window.close();
}

function checkField(){
	var sb = new StringBuffer();
	if ((form1.q_bulletinDate.value.length==0)&&(form1.q_suiteDateS.value.length==0)&&(form1.q_suiteDateE.value.length==0)){
		sb.append("請至少輸入一個查詢條件!");
	}
	sb.append(checkYYYMM(form1.q_bulletinDate,"公告年月"));
	sb.append(checkDate(form1.q_suiteDateS,"適用期間-起"));
	sb.append(checkDate(form1.q_suiteDateE,"適用期間-起"));
	if (sb.toString().length>0) {
		alert(sb.toString());
		return false;
	} else {
		return true;
	}
}
</script>
</head>
<body topmargin="3" leftmargin="3" rightmargin="3" bottommargin="3">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="popField" value="<%=popField%>">
<input type="hidden" name="preWord" value="<%=preWord%>">
<input type="hidden" name="isQuery" value="true">
<input type="hidden" name="isLookup" value="<%=isLookup%>">
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg" >
	<div id="formContainer" style="height:70px">
	<table class="table_form" width="100%" height="100%">	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font>公告年月：</td>
		<td class="td_form_white">
			<input class="field_Q" type="text" name="q_bulletinDate" size="5" maxlength="5" value="<%=Common.get(q_bulletinDate)%>">
		</td>		
	</tr>	
	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font>適用期間：</td>
		<td class="td_form_white">
			起<%=util.View.getPopCalndar("field_Q","q_suiteDateS",q_suiteDateS)%>　訖<%=util.View.getPopCalndar("field_Q","q_suiteDateE",q_suiteDateE)%>
		</td>		
	</tr>	
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
	<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="window.close()">
</td></tr>

<!--List區============================================================-->
<tr><td class="bg" >
<div id="listContainer" style="height:300px">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
	<thead id="listTHEAD">
	<tr>
		<th class="listTH">&nbsp;</th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">公告年月</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">適用期間</a></th>
	</tr>		
	</thead>
	<tbody id="listTBODY">
		<%=sbHTML%>
	</tbody>
</table>
</div>
</td></tr>
</table>	
</form>
</body>
</html>