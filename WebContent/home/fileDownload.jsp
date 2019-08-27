<%@ page contentType="text/html;charset=utf-8" import="util.Common"%>
<%
String fileID = Common.get(request.getParameter("fileID"));
String fileID2= Common.get(request.getParameter("fileID2"));//107年新增因應新版tomcat不支援直接傳送路徑
String fileName = Common.isoToutf8(Common.get(request.getParameter("fileName")));
String reportName=Common.isoToutf8(Common.get(request.getParameter("reportName")));
String uid=Common.isoToutf8(Common.get(request.getParameter("uid")));
String qry_seq=Common.get(request.getParameter("qry_seq"));
if (!"".equals(fileID)) {
	response.sendRedirect("../downloadFileSimple?fileID=" + fileID);
}else if (!"".equals(fileID2)) {
	response.sendRedirect("../downloadFileSimple?fileID2=" + fileID2);
} else if (!"".equals(fileName)) {
	response.sendRedirect("../downloadFileSimple?fileName=" + fileName);
} else if (!"".equals(reportName))	{
	Common.updateDLLOGByDL(qry_seq);
	response.sendRedirect("../downloadFileSimple?reportName=" + reportName+"&uid="+session.getAttribute("uid"));
} else {
	response.sendError(404,"File Not Found!");
}
%>