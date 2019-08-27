<%@ page contentType="text/html;charset=UTF-8" import="java.sql.ResultSet,java.sql.DatabaseMetaData,util.*" %>
<%@ page import="org.json.simple.*"%>
<%
response.addHeader("Pragma", "No-cache");
response.addHeader("Cache-Control", "no-cache");
response.addDateHeader("Expires", 1);

String q = util.Common.get(request.getParameter("q"));

JSONObject item=new JSONObject();
if (!q.equals("") && q.length()<21){
	Database db = new Database();
	try {
		ResultSet rs = db.querySQL("select id, pid, name, url, title, target, icon, iconOpen, opened, btnRead, btnWrite from sysap_program where id='"+q.replaceAll("'", "''")+"'");
		String[] colNames = db.getColumnNames(rs);
		while (rs.next()) {
			for(int j=0;j<colNames.length;j++){
				item.put(colNames[j],Common.get(rs.getString(colNames[j])));	   
			}
		}
		out.write(item.toString());
	}catch(Exception e) {
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
}	
 

 
%>