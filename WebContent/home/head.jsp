<%@page import="util.*" %>
<%@page import="util.report.*" %>
<%@page import="java.sql.ResultSet" %>
<%
String userid =Common.get((String)request.getParameter("uid"));
String unitid =Common.get((String)request.getParameter("unitid"));
if ("".equals(userid)){
	userid=Common.get((String)session.getAttribute("uid"));
	unitid=Common.get((String)session.getAttribute("unitid"));
	if("".equals(userid)){
		out.println("<script language=\"javascript\">");
		out.println("var prop='';");
		out.println("prop=prop+'width=300px,height=120,scrollbars=no'");
		out.println("window.open('" + request.getContextPath() + "/home/sessionTimeout.jsp','sessionTimeout',prop);");
		out.println("top.top.location.href='/TBNJ';");
		out.println("</script>");
		return;
	}
}else{
	session.setAttribute("uid",userid);
	session.setAttribute("unitid",unitid);
}

%>
