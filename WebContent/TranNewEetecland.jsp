<%@ page contentType="text/html;charset=utf-8"%>
<jsp:useBean id="obj" scope="request" class="util.TranNewEtecland">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<%
	obj.doTranDb();
%>