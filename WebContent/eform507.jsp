<!--
程式目的：EFORM507
程式代號：EFORM507
程式日期：1060703
程式作者：Sya
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<%@page import="util.View" %>
<%@page import="lca.ap.EFORM507" %>
<%@include file="home/head.jsp" %>
<%@include file="menu_bar.jsp" %>

<jsp:useBean id="obj" scope="request" class="lca.ap.EFORM507">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<%
	ServletContext context = getServletContext();
	String filestoreLocation = context.getInitParameter("filestoreLocation");
	String reportLocation = context.getInitParameter("reportLocation");
	String uip = request.getRemoteAddr(); 
	obj.setContext(context);

	String f = "";
	
	if("impTxt".equals(obj.getState())){
		obj.ModifyEFORM7();
	}else if("ins".equals(obj.getState())){
		if(!"".equals(obj.getItemPicture1())){
			String retMsg=obj.ModifyEFORM7();
			if("匯入完成!".equals(obj.getMsg()) || "更新完成!".equals(obj.getMsg())){
				obj.doIns();
			}
		}else{
			obj.doIns();
		}
	}
	
	ArrayList<String[]> al=new ArrayList<String[]>();	
	al=obj.getQryModel();
	
	for (int i = 0; i < al.size(); i++) {
		String data[] = (String[]) al.get(i);
		if(data[2].equals("0")){
			f = "0";
		}else{
			f = "1";
		}
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-control" content="no-cache" />
<link rel="stylesheet" href="js/default.css" type="text/css" />
<link rel="stylesheet" href="js/font.css" type="text/css" />
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript" src="js/function.js"></script>
<script type="text/javascript">
	function chkUnitID(){
		if(form1.unitID.value != "00"){
			alert("此作業只提供內政部地政司使用");
			top.location.reload();
		}
	}
	
	function doImpotTxt(){
		var errmsg = "";
	  	if(form1.itemPicture1.value == ""){
	  		errmsg = errmsg + "請上傳欲匯入的文字檔!\n";
	  	}
	  	if (errmsg!=""){
			alert(errmsg);
		}else{
			var impMsg='';
			if(form1.imp_kind[0].checked){
				impMsg='清空現有資料再重新匯入身分證字號';
			}else if(form1.imp_kind[1].checked){
				impMsg='保留現有資料，將檔案內容更新匯入';
			}
		 	if(!confirm(impMsg)){
				return false;
			}else{
		 		form1.state.value="impTxt";
				form1.submit();
			}
		} 
	}

	function doInsert(){
		var errmsg = "";
	  	if(form1.itemPicture1.value == ""){
		  	if('<%=f%>' == '0'){
		  		errmsg = "請先匯入身分證字號";
			}else{
				errmsg = "未上傳附件，將以現有資料進行統計";
			}
	  	}else{
	  		if(form1.imp_kind[0].checked){
		  		errmsg = "匯入身分字號並執行產製";
	  		}else if(form1.imp_kind[1].checked){
				errmsg = "保留現有資料，將檔案內容更新匯入身分字號並執行產製";
			}
		}
	  	
		 	if(!confirm(errmsg)){
				return false;
			}else{
		 		form1.state.value="ins";
				form1.submit();
			}
		} 
	
	function showMsg(obj){
		if(obj != ''){
			alert(obj);
		}
	}
	
</script>
</head>

<body topmargin="0" background="images/main_bg.jpg"	onLoad="chkUnitID();showMsg('<%=obj.getMsg()%>');">
<form id="form1" name="form1" method="post">
<table style="border:0;width:700;cellspacing:0;cellpadding:0">
	<tr>
		<td width="30%">
		<td width="40%">
		<td width="30%">
	</tr>
	<tr align="center">
		<td colspan="3"><font size="4" face="標楷體" color="#FF9900">原住民族地區土地概況統計表產製作業</font>
		<hr size="1" color="#008000" width="700" align="left">
		</td>
	</tr>
	<tr align="center">
		<td colspan="3">身分證字號匯入：
			<input name="imp_kind" type="radio" value="1" <%=!"2".equals(obj.getImp_kind())?"checked":""%>>重新匯入
			<input name="imp_kind" type="radio" value="2" <%="2".equals(obj.getImp_kind())?"checked":""%>>更新匯入
			[<input class="field_RO" type="text" name="itemPicture1" size="20" maxlength="300" value="">]
			<input class="field" type="button" name="btn_itemPicture1" onclick="openUploadWindow('itemPicture1','');" value="上傳檔案" >
			<input type=button id="button3" name="button4" value="匯入文字檔" class=Button onClick="doImpotTxt();">
		</td>
	</tr>
	<tr align="center">
		<td colspan="3">
			&nbsp;
			<input class="Button" type="button" name="" value="產製報表" onClick="doInsert();">
		</td>
	</tr>
	<tr align="left">
		<td colspan="3">匯入檔案：只匯入檔案不進行報表統計。</td>
	</tr>
	<tr align="left">
		<td colspan="3">產製報表：有上傳檔案就會將檔案匯入並統計；未上傳檔案就依現有資料做統計。</td>
	</tr>
	<tr align="center">
		<td colspan="3"><font>
			<%
				for (int i = 0; i < al.size(); i++) {
					String data[] = (String[]) al.get(i);
					if(i == 0){
			%>
			<label id="LblTextCount" style="color: red"><%=data[0]%>&nbsp;<%=data[1]%>-首次匯入<%=data[2]%>筆</label><br>
			<% 
					}else{
			%>
			<label id="LblTextCount" style="color: red"><%=data[0]%>&nbsp;<%=data[1]%>-更新匯入<%=data[2]%>筆</label><br>
			<%}} %>
			
		</font></td>
	</tr>
</table>
<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="filestoreLocation" value="<%=filestoreLocation%>">	
<input type="hidden" name="reportLocation" value="<%=reportLocation%>">	
<input type="hidden" name="userID" value="<%=session.getAttribute("uid")%>">
<input type="hidden" name="unitID" value="<%=session.getAttribute("unitid")%>">

</form>
</body>
</html>
