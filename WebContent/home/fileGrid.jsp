<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" errorPage="error.jsp"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="util.*" %>
<%@ page import="util.Common" %>
<%@ include file="head.jsp" %>
<%
String ma_lo = request.getParameter("ma_lo");
String ma_seq=request.getParameter("ma_seq");

%>
<html>
<head>
<title>檔案列表</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type"   content="text/html; charset=utf-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="private"/>
<link rel="stylesheet" href="../js/default.css" type="text/css">
<script language="javascript" src="../js/validate.js"></script>
<script language="javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/tablesoft.js"></script>
<script language="javascript">
function deleteRow(af_lo,af_seq,af_fseq)
{
 	window.open("../home/xmlDelete.jsp?af_lo="+af_lo+"&af_seq="+af_seq+"&af_fseq="+af_fseq);	
 }

</script>
</head>
<body>
<table id="myTable" name="myTable" width="100%" border="1">
	<%
		Database db = new Database();
    	try {
			String sql="select * from cattf where 1=1";
			sql+=" and af_lo="+Common.sqlChar(ma_lo);
			sql+=" and af_seq="+Common.sqlChar(ma_seq);
			ResultSet rs=db.querySQL(sql);
		    while(rs.next()){
   %>
	<tr>		
		<td width="90%"><a class='text_link_b' href='../downloadFileSimple?fileID=<%=rs.getString("AF_FFOLDER")%>:;:<%=rs.getString("AF_FNAME")%>'><%=rs.getString("AF_FNAME")%></a></td>
		<td width="10%"><button class="button" id="11" onclick="deleteRow('<%=rs.getString("AF_LO")%>','<%=rs.getString("AF_SEQ")%>','<%=rs.getString("AF_FSEQ")%>')">刪除</button></td>
	</tr>	
	<%
		    }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	%>
</table>
</body>
</html>

