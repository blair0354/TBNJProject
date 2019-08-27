<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="home/head.jsp" %>
<jsp:useBean id="obj" class="eform.eform3_1.EFORM3_1" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	String txtuser_id = Common.checkSet(request.getParameter("txtuser_id"));
	String txtuser_name = Common.checkSet(request.getParameter("txtuser_name"));
	String txtpassword = Common.checkSet(request.getParameter("txtpassword"));
	String txtuser_mail = Common.checkSet(request.getParameter("txtuser_mail"));
	String txtunit = Common.checkSet(request.getParameter("txtunit"));
	String isStop = Common.checkSet(request.getParameter("isStop"));
	String isManager = Common.checkSet(request.getParameter("isManager"));
	String update_user = Common.checkSet((String)session.getAttribute("uid"));
	String update_time = obj.getSystemTime();
	
	/*
	System.out.println(txtuser_id);
	System.out.println(txtuser_name);
	System.out.println(txtpassword);
	System.out.println(txtuser_mail);
	System.out.println(txtunit);
	System.out.println(isStop);
	System.out.println(isManager);
	System.out.println(update_user);
	System.out.println(update_time);
	*/
	
	String cIsManager=Common.getUserIsManager((String)session.getAttribute("uid"), (String)session.getAttribute("unit"));
	if("Y".equals(cIsManager)){
		//step 1. 檢查密碼
		if(!"".equals(txtpassword)){
			int cnt = obj.queryPwd(txtuser_id, txtpassword);
			
			if(cnt > 0){
				out.write("<script>alert('不能使用最近三次的歷史密碼！');</script>");	
				out.write("<script>window.location.href = 'user_update.jsp?txtuser_id=" + util.IDEncode.EncodePassword(txtuser_id) + "';</script>");
			}else{
				//step 2.update log
				obj.updateLog(txtuser_name, txtpassword, txtuser_mail, txtunit, isStop, isManager, update_user, update_time, txtuser_id);
				
				//step 3.insert etecuser
				obj.doInsertLog(txtuser_id, txtpassword);
				
				//step 4.query log
				int logCnt = obj.queryLogCnt(txtuser_id);
				while(logCnt > 3){
					obj.delLogCnt(txtuser_id);
					logCnt = obj.queryLogCnt(txtuser_id);
				}
				
				out.write("<script>alert('存檔成功!');</script>");	
				out.write("<script>window.location.href = 'eform3_1.jsp';</script>");
			}
		}else{
			//step 2.update log
			obj.updateLog(txtuser_name, txtpassword, txtuser_mail, txtunit, isStop, isManager, update_user, update_time, txtuser_id);
			out.write("<script>alert('存檔成功!!');</script>");	
			out.write("<script>window.location.href = 'eform3_1.jsp';</script>");
		}
	}else{
		out.write("<script>alert('修改失敗!');</script>");
		out.write("<script>window.location.href = 'menu_main.jsp';</script>");
	}
	
	
	
%>
<script>
	//window.opener = null;
	//window.open('', '_self', '');
	//window.close();
</script>