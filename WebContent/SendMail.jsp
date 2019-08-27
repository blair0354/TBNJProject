<%@ page contentType="text/html;charset=utf-8"%>
<jsp:useBean id="obj" scope="request" class="sch.sch_SendMail.SCH_SendMail">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<%
	obj.main2();
%>