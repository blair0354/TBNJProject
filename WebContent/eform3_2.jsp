<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.catalina.Session"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp" %>
<jsp:useBean id="obj" class="eform.eform3_2.EFORM3_2" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
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

<script language="javascript">

//function checkPasswd(pwd)
//{
	//if(pwd.length != 12){
	//	alert("密碼請輸入至少12個字元以上的英文字母加數字組合，但不含空白鍵、*、@、#、%或其他符號！");
	//	return;
	//}
	//var re = /[a-zA-Z\d]/;
	//if(!pwd.match(re)){
	//	alert("您的密碼只能是英文字母加數字組合，不能含空白鍵、*、@、#、%或其他符號 !");
	//}
//}


//function equalPasswd()
//{
	//var pwd = document.getElementById("txtpassword").value;
	//var pwd1 = document.getElementById("txtpassword1").value;
	//if(pwd != pwd1){
	//	alert("密碼不一致，請重新輸入!");
	//	document.getElementById("txtpassword").value = "";
	//	document.getElementById("txtpassword1").value = "";
	//	document.getElementById("txtpassword").focus();
	//}
//}

//function checkEmail(obj)
//{	
	//var email = obj.value;
	//var re =/([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	//if(email.length == 0)
	//{
	//	alert("請輸入使用者Email!");
	//}
	//else if(!email.match(re))
	//{
	//	alert("請輸入正確Email格式!");
	//	obj.value = "";
	//}
	//console.log(email);
//}


function checkSubmit()
{
	var str = "";
	str += checkEmpty(document.getElementById("txtpassword"),"登入密碼");
	str += checkEmpty(document.getElementById("txtpassword1"),"確認密碼");
	str += checkEmpty(document.getElementById("txtuser_mail"),"使用者email");
	str += checkEmail(document.getElementById("txtuser_mail"),"使用者email");
	
	if((document.getElementById("txtpassword").value).length != 12){
		str +="登入密碼請輸入至少12個字元以上的英文字母加數字組合，但不含空白鍵、*、@、#、%或其他符號！"+"\n";
	}
	if((document.getElementById("txtpassword1").value).length != 12){
		str +="密碼請輸入至少12個字元以上的英文字母加數字組合，但不含空白鍵、*、@、#、%或其他符號！"+"\n";
	}
	var re = /[a-zA-Z\d]/;
	if(!(document.getElementById("txtpassword").value).match(re)){
		str +="登入密碼只能是英文字母加數字組合，不能含空白鍵、*、@、#、%或其他符號 !";
	}
	if(!(document.getElementById("txtpassword1").value).match(re)){
		str +="密碼確認 只能是英文字母加數字組合，不能含空白鍵、*、@、#、%或其他符號 !";
	}
	if(document.getElementById("txtpassword").value != document.getElementById("txtpassword1").value){
		str +="密碼不一致，請重新輸入!";
	}
	if(str != "")
	{
		alert(str);
		return false;
	}

	document.getElementById("state").value = "doUpdate";
	
}

</script>
<title>密碼修改</title>
</head>
<%
String uid = (String)session.getAttribute("uid");
String uname = (String)session.getAttribute("uname");
String state = obj.getState();
if("doUpdate".equals(state)){
	String msg = obj.doUpdate();
	out.write("<script> alert('" + msg + "');</script>");
}

//查詢使用者
Map map = obj.queryEtecuser(uid).get(0);
%>
<body background="images/main_bg.jpg" topmargin="0" OnLoad="<!--  document.form1.txtpassword.focus();-->	">
	<form name="form1" method="post" onsubmit="return checkSubmit();">
	<input type="hidden" name="user_id" value="<%=uid%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
		<div>
			<table border="1" width="660" cellspacing="1">
				<tr> 
	              	<td align="right" vAlign="center" class="small" colspan="4" bgcolor="#E7E7E7"> 
	                	<p align="center"><font color="#0000FF">使用者密碼修改</font></td>
	            </tr>
	            <tr> 
		            <td width="123"  align="right">異動日期</td>
		            
		            <td width="254"  align="left"><font color="#0000FF"><%=obj.getSystemDate()%></font></td>
		            	
		            <td width="87"  align="right">異動人員</td>
		            
		            <td width="173"  align="left"><font color="#0000FF"><%=map.get("user_name")%></font></td>
		            
	            </tr>
	            <tr> 
	              	<td align="right" vAlign="center" width="123" class="small"><font color="#0000FF">使用者帳號</font></td>
	              	
	              	<td colspan="3" align="left"><font color="#0000FF"><%=map.get("user_id")%></font></td>
	              	
	            </tr>
	            <tr> 
	              	<td align="right" vAlign="center" width="123" class="small"><font color="#0000FF">使用者名稱</font></td>
	              	
	              	<td colspan="3" align="left"><%=map.get("user_name")%></td>
	              	
	            </tr>
	            <tr> 
	              	<td width="123" align="right" class="small" >
	              		<font color="#FF0000">*</font>
	              		<font color="#0000FF">登入密碼</font>
	             	</td>
	             	
	              	<td colspan="3" align="left">
	              		<input 
	              			name="txtpassword" 
	              			type="password" 
	              			maxlength="12" 
	              			size="20" 
	              			tabindex="5" 
	              			value="" 
	              			>
	              	</td>
	            </tr>
	            <tr> 
	              	<td width="123" align="right" class="small" >
	              		<font color="#FF0000">*</font>
	              		<font color="#0000FF">密碼確認</font>
	             	</td>
	             	
	              	<td colspan="3" align="left">
	              		<input 
	              			name="txtpassword1" 
	              			type="password" 
	              			maxlength="12" 
	              			size="20" 
	              			tabindex="5" 
	              			>
	              	</td>
	            </tr>
				<tr> 
	              	<td width="123" align="right" class="small" >
	              		<font color="#FF0000">*</font>
	              		<font color="#0000FF">使用者email</font>
	              	</td>
	              	
	              	<td colspan="3" align="left">
	              		<input 
	              			name="txtuser_mail" 
	              			type="text" 
	              			maxlength="50" 
	              			size="50" 
	              			tabindex="5" 
	              			value="<%=map.get("user_mail")%>"
	              			>
	              	</td>
	            </tr>
	            <tr> 
	              	<td colspan="7" bgcolor="#E7E7E7">
	              		<p align="left" style="line-height: 100%; margin-top: 0; margin-bottom: 6">
	              			<font color="#FF0000">說明：</font>
	              			有 
	                  		<font color="#FF0000">*</font>
	                  		註記表示一定要輸入（
	                  		<font color="#0000FF">不可空白</font>
	                  		）。
	                  	</p>
	                  	
	                	<p align="center" style="margin-top: 4; margin-bottom: 0"> 
	                  		<input type="submit" value="存檔" name="submit" tabindex="60">
	                  		<input type="reset" value="重填" name="clear" tabindex="61">
	                 	</p>
	              </td>
	            </tr>
			</table>
		</div>
	</form>
</body>
</html>