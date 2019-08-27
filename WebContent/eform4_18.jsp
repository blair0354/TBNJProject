<!--
程式目的：mail帳號設定
程式代號：eform4_18, LCAAP210F
程式日期：1060417
程式作者：Rhonda Ke
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp" %>
<%@ page import = "java.io.*,java.util.*,javax.mail.*"%>
<%@ page import = "javax.mail.internet.*,javax.activation.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<jsp:useBean id="obj" scope="request" class="lca.ap.LCAAP210F">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<%

System.out.println("aa:"+session.getAttribute("uid").toString());
//System.out.println("ab:"+session.getAttribute("unit").toString());
//System.out.println("ac:"+session.getAttribute("isManager").toString());
	
	String msg = "";
	if("save".equals(obj.getState())){
		obj.setEditID(session.getAttribute("uid").toString());
		msg = obj.saveEmail();
		obj.setState("init");
	}else if("testMail".equals(obj.getState())){
		//===================測試用
		 String result;
		   // Recipient's email ID needs to be mentioned.
		   String to = obj.getTestMail();

		   // Sender's email ID needs to be mentioned
		   String from = obj.getMail_Addr();

		   // Assuming you are sending email from localhost
		   //String host = "smtp.ginmao.com.tw";

		   // Get system properties object
		   Properties properties = System.getProperties();

		   // Setup mail server
		   properties.setProperty("mail.smtp.host", obj.getMail_SMTP());
		   properties.setProperty("mail.user", obj.getMail_ID());
		   properties.setProperty("mail.password",  util.EnBase64.decodeBASE64(obj.getMail_PWD()));
		   
		   
		   properties.setProperty("mail.smtp.auth", "true");

		   // Get the default Session object.
		   Session mailSession = Session.getDefaultInstance(properties);
		   try{
			      // Create a default MimeMessage object.
			      MimeMessage message = new MimeMessage(mailSession);
			      // Set From: header field of the header.
			      message.setFrom(new InternetAddress(from));
			      // Set To: header field of the header.
			      message.addRecipient(Message.RecipientType.TO,
			                               new InternetAddress(to));
			      // Set Subject: header field
			      message.setSubject("This is the Subject Line!");
			      // Now set the actual message
			      message.setText("This is actual message");
			      // Send message
			      Transport.send(message);
			      result = "Sent message successfully....";
			   }catch (MessagingException mex) {
			      mex.printStackTrace();
			      result = "Error: unable to send message....";
			   }
			   msg=result;
	}
	obj = (lca.ap.LCAAP210F)obj.queryMailSet();
	
	
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
<script type="text/javascript" src="js/tablesoft.js"></script>
<script type="text/javascript" src="js/cbToggle.js"></script>
<script language="javascript">
function chkUnitID(){
	if(form1.unitID.value != "00"){
		alert("此作業只提供內政部地政司使用");
		top.location.reload();
	}
}

function checkField(){
	
	var alertStr = '';
	alertStr += checkEmpty(form1.mail_ID,"寄件人帳號");
	alertStr += checkEmpty(form1.mail_PWD,"寄件人密碼");
	alertStr += checkEmpty(form1.mail_SMTP,"SMTP伺服器");
	alertStr += checkEmpty(form1.mail_Addr,"寄件人信箱");
	alertStr += checkEmail(form1.mail_Addr,"寄件人信箱");
	alertStr += checkEmpty(form1.mail_PORT,"SMTP伺服器");
	
    if(alertStr != ''){
		alert(alertStr);
		return false;
	}else{
		document.form1.state.value = 'save';
    	document.form1.submit();
    }
    return true;
}

function doTestMail(){
	var alertStr = '';
	alertStr += checkEmpty(form1.mail_ID,"寄件人帳號");
	alertStr += checkEmpty(form1.mail_PWD,"寄件人密碼");
	alertStr += checkEmpty(form1.mail_SMTP,"SMTP伺服器");
	alertStr += checkEmpty(form1.mail_Addr,"寄件人信箱");
	alertStr += checkEmail(form1.mail_Addr,"寄件人信箱");
	alertStr += checkEmpty(form1.mail_PORT,"SMTP伺服器");
	
    if(alertStr != ''){
		alert(alertStr);
		return false;
	}else{
		document.form1.state.value = 'testMail';
    	document.form1.submit();
    }
    return true;
}

function init() {
	var msg = '<%=msg%>';
	if (msg != '') {
		alert(msg);
	}
}

</script>
</head>

<body topmargin="0" background="images/main_bg.jpg"	onLoad="init();chkUnitID();">
<form id="form1" name="form1" method="post"	onSubmit="return checkField()">
<table border="0" width="660" cellspacing="0" cellpadding="0">
	<tr align="center" valign="middle">
		<td colspan="3"><font size="4" face="標楷體" color="#FF9900">email帳號設定</font>
		<hr size="1" color="#008000" width="660" align="left">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>寄件人帳號：</td>
		<td><input name="mail_ID" type="text"  size="20" maxlength="50" value="<%=obj.getMail_ID()%>"></td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>寄件人密碼：</td>
		<td><input name="mail_PWD" type="password"  size="21" maxlength="200" value="<%=obj.getMail_PWD()%>"></td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>寄件人信箱：</td>
		<td><input name="mail_Addr" type="text" size="50" maxlength="200" value="<%=obj.getMail_Addr()%>"></td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>SMTP伺服器：</td>
		<td>
			<input name="mail_SMTP" type="text" value="<%=obj.getMail_SMTP()%>">&nbsp;&nbsp;
			連接埠:<input name="mail_PORT" type="text" size="4" maxlength="4" value="<%=obj.getMail_PORT()%>">
		</td>
	</tr>
	<tr>
		<td align="right">驗證方式：</td>
		<td>
			<input type="checkBox" name="mail_SSL"  value="Y" <%="Y".equals(obj.getMail_SSL())?"checked":""%> >SSL驗證
			&nbsp;&nbsp;
			<input type="checkBox" name="mail_TLS"  value="Y" <%="Y".equals(obj.getMail_TLS())?"checked":""%> >TLS驗證
		</td>
	</tr>
	<tr>
		<td align="right">測試信箱:</td>
		<td>
			<input type="text" name="testMail" size="50" maxlength="50" value="<%=obj.getTestMail()%>">
		</td>
	</tr>
	<tr>
		<td align="right">異動人員/時間：</td>
		<td>[<%=obj.getEditID()%> / <%=obj.getEditDate()%> <%=obj.getEditTime()%>]</td>
	</tr>
	<tr>
		<td align="right"> </td>
		<td style="padding: 5px;">
			<input type="submit" value="存檔" class="Button" />
			<input type="button" id="testBtn" value="測試"  onClick="doTestMail();" />
			<input type="reset" value="取消異動" class="Button" />
			<input type="hidden" name="state" value="<%=obj.getState()%>">
			<input type="hidden" name="unitID" value="<%=session.getAttribute("unitid")%>">
		</td>
	</tr>
</table>
</form>
</body>
</html>
