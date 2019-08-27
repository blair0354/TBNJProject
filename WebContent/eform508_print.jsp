<!--
*<br>程式目的：
*<br>程式代號：
*<br>程式日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=utf-8" %>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="lca.ap.EFORM508">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	String t0 = request.getParameter("t0");
   		 obj.printExcel(request, response);
    out.clear();  
    out = pageContext.pushBody();
%>