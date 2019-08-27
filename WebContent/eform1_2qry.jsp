<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="util.Common"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLDecoder"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="home/head.jsp" %>
<jsp:useBean id="obj" class="eform.eform1_2.EFORM1_2" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%	
	String txtcity_no = Common.checkSet(request.getParameter("txtcity_no"));
	String txtunit = Common.checkSet(request.getParameter("txtunit"));
	String txtqry_no = Common.checkSet(request.getParameter("txtqry_no"));
	String txtqry_name = Common.checkSet(Common.big5ToIso(request.getParameter("txtqry_name")));
	String txtrcv_yr = Common.checkSet(request.getParameter("txtrcv_yr"));
	String txtrcv_word = Common.checkSet(request.getParameter("txtrcv_word"));
	String txtrcv_no = Common.checkSet(request.getParameter("txtrcv_no"));
	String txtsno = Common.checkSet(request.getParameter("txtsno"));
	String txtsname = Common.checkSet(request.getParameter("txtsname"));
	String txtsno1 = Common.checkSet(request.getParameter("txtsno1"));
	String txtsname1 = Common.checkSet(request.getParameter("txtsname1"));
	String txtqry_purpose01 = Common.checkSet(request.getParameter("txtqry_purpose01"));
	String txtqry_purpose02 = Common.checkSet(request.getParameter("txtqry_purpose02"));
	String txtqry_purpose03 = Common.checkSet(request.getParameter("txtqry_purpose03"));
	String txtqry_purpose03a = Common.checkSet(request.getParameter("txtqry_purpose03a"));
	String txtqry_oper = Common.checkSet(request.getParameter("txtqry_oper"));
	
	/*
	System.out.println("txtcity_no: " + txtcity_no);
	System.out.println("txtunit: " + txtunit);
	System.out.println("txtqry_no: " + txtqry_no);
	System.out.println("txtqry_name: " + Common.isoToBig5(txtqry_name));
	System.out.println("txtrcv_yr: " + txtrcv_yr);
	System.out.println("txtrcv_word: " + txtrcv_word);
	System.out.println("txtrcv_no: " + txtrcv_no);
	System.out.println("txtsno: " + txtsno);
	System.out.println("txtsname: " + txtsname);
	System.out.println("txtsno1: " + txtsno1);
	System.out.println("txtsname1: " + txtsname1);
	System.out.println("txtqry_purpose01: " + txtqry_purpose01);
	System.out.println("txtqry_purpose02: " + txtqry_purpose02);
	System.out.println("txtqry_purpose03: " + txtqry_purpose03);
	System.out.println("txtqry_purpose03a: " + txtqry_purpose03a);
	System.out.println("txtqry_oper: " + txtqry_oper);
	*/
	
	String QRY_DATE_START = obj.getSystemDate();
	String QRY_TIME_START = obj.getSystemTime();
	String USERID = session.getAttribute("uid").toString();
	String UNITID = session.getAttribute("unit").toString();
	String IP = session.getAttribute("fip").toString();
	String CON = "權利人資料查詢";
	String QRY = 
			"市縣市=" + txtcity_no + ";" +
			"事務所=" + txtunit + ";" +
			"統一編號=" + txtqry_no + ";" +
			"姓名=" + Common.isoToBig5(txtqry_name);
	String QRY_MSG = "尚未完成查詢";
	String RCV_YR = txtrcv_yr;
	String RCV_WORD = txtrcv_word;
	String RCV_NO = txtrcv_no;
	String SNO = txtsno;
	String SNAME = txtsname;
	String SNO1 = txtsno1;
	String SNAME1 = txtsname1;
	String QRY_PURPOSE = "";
	String QRY_OPER = txtqry_oper;
	String QRY_PURPOSE01 = txtqry_purpose01;
	String QRY_PURPOSE02 = txtqry_purpose02;
	String QRY_PURPOSE03 = txtqry_purpose03;
	String QRY_PURPOSE03A = txtqry_purpose03a;
	String QRY_DATE_END ="";
	String QRY_TIME_END ="";
	
	
	/*		
	System.out.println(QRY_DATE_START);
	System.out.println(QRY_TIME_START);
	System.out.println(USERID);
	System.out.println(UNITID);
	System.out.println(IP);
	System.out.println(QRY);
	System.out.println(QRY_MSG);
	System.out.println(RCV_YR);
	System.out.println(RCV_WORD);
	System.out.println(RCV_NO);
	System.out.println(SNO);
	System.out.println(SNAME);
	System.out.println(SNO1);
	System.out.println(SNAME1);
	System.out.println(QRY_PURPOSE);
	System.out.println(QRY_OPER);
	System.out.println(QRY_PURPOSE01);
	System.out.println(QRY_PURPOSE02);
	System.out.println(QRY_PURPOSE03);
	System.out.println(QRY_PURPOSE03A);
	*/
	
	//step 1. 寫入相關資料
	obj.insertBs_log(QRY_DATE_START, QRY_TIME_START, USERID, UNITID, IP, CON, QRY, QRY_MSG, RCV_YR, RCV_WORD, RCV_NO, SNO, SNAME, SNO1, SNAME1, QRY_PURPOSE, QRY_OPER, QRY_PURPOSE01, QRY_PURPOSE02, QRY_PURPOSE03, QRY_PURPOSE03A);
		
	//step 2. 查詢資料
	List list = obj.doQuery(txtcity_no, txtunit, txtqry_no, txtqry_name);
	
	QRY_DATE_END = obj.getSystemDate();
	QRY_TIME_END = obj.getSystemTime();
	
	//step 3. 寫入查詢結果
	//3-1 有資料
	if(list.size() > 0 ){
		Map map = (Map)list.get(0);
		obj.updateBs_log(QRY_DATE_END, QRY_TIME_END, "已完成查詢", map.get("lidn").toString(), map.get("lnam").toString(),QRY_DATE_START,QRY_TIME_START,IP);
	}else{
		obj.updateBs_log(QRY_DATE_END, QRY_TIME_END, "無符合條件資料", "", "",QRY_DATE_START,QRY_TIME_START,IP);
	}
	
	
	
	obj.setQueryData(list);
	
%>
<script language="javascript">
	function changPage(pageNum,totalPage){

		//alert("pageNum: " + pageNum + "\ntotalPage: " +totalPage);
		
		if(pageNum < 1){
			//alert("已經到第1頁囉!");
			return;
		}

		if(pageNum > totalPage){
			//alert("已經到最後頁囉!");
			return;
		}
			
		form1.action = "eform1_2qry.jsp?pageNum=" + pageNum;
		form1.method = "post";
		form1.submit();
	}
</script>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Language" content="zh-tw">
<link rel="stylesheet" type="text/css" href="js/font.css">
<title><%=session.getAttribute("com_title")%>-權利人資料查詢</title>
</HEAD>
<body background="images/main_bg.jpg" topmargin="0">
<form name="form1">
<input type="hidden" name="txtcity_no" value="<%=txtcity_no%>">
<input type="hidden" name="txtunit" value="<%=txtunit%>">
<input type="hidden" name="txtqry_no" value="<%=txtqry_no%>">
<input type="hidden" name="txtqry_name" value="<%=Common.isoToBig5(txtqry_name)%>">
</form>
	<table width="660" topmargin="0" align="center" border="0">
		<tr>
			<td colspan="0" align="center"><font size="3"><img
					src=images/luluicon21.gif> 權利人資料查詢 <img
					src=images/luluicon21.gif></font></td>
			<td align="right"><p style="line-height: 100%; margin: 0" align="right"> <a href="javascript:window.close()">[關閉]</a>
		</tr>
		<%--↓↓↓↓↓沒資料顯示時↓↓↓↓↓↓ --%>
		<% 
			if(obj.getQueryData().size() <= 0 ){
		%>
		<tr>
			<td width="70%" align="left" colspan="2">
				<table width="100%" border="1"><tr><td>查無權利人資料!</td></tr></table>
			</td>
		</tr>
		<%--↑↑↑↑↑↑沒資料顯示時↑↑↑↑↑↑ --%>
		<%}else{ 
			int dataCount = obj.getQueryData().size();//資料筆數
			int pageSize = 100; //每頁筆數
			//System.out.println("dataCount:" + dataCount);
			int totalPage = dataCount/pageSize;
			totalPage = (dataCount%pageSize) == 0 ? totalPage : totalPage + 1; //不足一頁要+1
			//System.out.println("totalPage:" + totalPage);
			int pageNum = 1;
			if(request.getParameter("pageNum") != null)
				pageNum = Integer.parseInt(request.getParameter("pageNum"));
			//System.out.println(pageNum);
		%>
		<tr>
			<td width="70%" align="center" colspan="2">
				<img src="images/arrow01.gif" border=0 alt="上一頁" onclick="changPage('<%=pageNum-1%>','<%=totalPage%>');">
				<img src="images/arrow02.gif" border=0 alt="下一頁" onclick="changPage('<%=pageNum+1%>','<%=totalPage%>');">
				目前在第
				<select onchange="changPage(this.value,'<%=totalPage%>');">
					<%
					
					for(int i = 1; i <= totalPage; i++) out.write("<option value='" + (i) + "'" + (i == pageNum ? "selected":"") + ">" + (i) + "</option>");%>
				</select>
				頁|共<%=totalPage%>頁
			</td>
		</tr>
		
		<tr>
			<td style="border:2px ridge;" colspan="2" width="660">
				
		<%
		 for(int i = ((pageNum -1) * pageSize); i < ((pageNum * pageSize) > dataCount ? dataCount:(pageNum * pageSize)); i++){
			 Map map = (Map)list.get(i);
			 
				out.println("<table width='660'>");
				out.println("<tr>");
				 
					 out.println("<td align='left' width='330'>");
					 out.println("統一編號:" + map.get("lidn"));
					 out.println("</td>");
					 
					 out.println("<td align='left' wight='330'>");
					 out.println("類&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;別:" + map.get("kcnt"));
					 out.println("</td>");
					 
				out.println("</tr>");
				 
				out.println("<tr>");
				 
					 out.println("<td align='left' width='330'>");
					 out.println("姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:" + map.get("lnam"));
					 out.println("</td>");
					 
					 out.println("<td align='left' wight='330'>");
					 out.println("出生日期:" + (map.get("lbir_2").toString().length() == 7 ? map.get("lbir_2").toString().substring(0,3)+"年"+map.get("lbir_2").toString().substring(3,5)+"月"+map.get("lbir_2").toString().substring(5)+"日":map.get("lbir_2")));
					 out.println("</td>");
				 
			 	out.println("</tr>");
			 
			 	out.println("<tr>");
				 
					 out.println("<td align='left' colspan='2'>");
					 out.println("住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:" + map.get("ladr"));
					 out.println("</td>");
				 
		 		out.println("</tr>");
		 		
		 		out.println("<tr>");
			 		out.println("<td colspan='2'>");
			 			out.println("<hr color='#008000'>");
			 		out.println("</td>");
		 		out.println("</tr>");
			 	
			 	out.println("</table>");
		 }
		%>
			</td>
		</tr>
		<%}%>
	</table>
</body>
</html>