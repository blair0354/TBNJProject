<%@ page contentType="text/html;charset=utf-8" %>
<jsp:useBean id="user" scope="session" class="util.User"/>
<script language="javascript">
<%
user.setUserID(request.getParameter("userID"));
user.setPassword(request.getParameter("oldPassword"));
if (!"".equals(user.getUserID()) && !"".equals(user.getPassword()) ){
	if(user.getUserID().indexOf("-")>-1 || user.getUserID().indexOf("'")>-1  || user.getUserID().indexOf("\"")>-1  || user.getPassword().indexOf("-")>-1  || user.getPassword().indexOf("'")>-1  || user.getPassword().indexOf("\"")>-1 ){
		out.println("alert('帳號或密碼輸入錯誤, 請重新輸入!');");
	}else{
		user = (util.User)user.checkUser();
		if ("".equals(user.getUserName())){
			out.println("alert('帳號或密碼錯誤, 請重新登入!');");
		}else if(request.getParameter("newPassword1")==null || request.getParameter("newPassword2")==null || request.getParameter("newPassword1").equals("") || !request.getParameter("newPassword1").equals(request.getParameter("newPassword2")) || request.getParameter("newPassword1").indexOf("-")>-1 || request.getParameter("newPassword1").indexOf("'")>-1 || request.getParameter("newPassword1").indexOf("\"")>-1){
			out.println("alert('新密碼輸入錯誤, 請重新輸入!');");
		}else{
			user.setPassword(request.getParameter("newPassword1"));
			user.updatePWD();
			String strHTML="";
			strHTML += "alert('變更密碼已完成！');";
			strHTML += "window.opener.form1.userID.value='" + user.getUserID() + "';";
			strHTML += "window.opener.form1.userPWD.value='" + user.getPassword() + "';" ;
			strHTML += "window.opener.form1.submit();";
			strHTML += "window.close();";	
			out.write(strHTML);	
		}
	}
}
%>
</script>
<html>
<head>
<title>變更密碼</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type"   content="text/html; charset=utf-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="private"/>
<link rel="stylesheet" href="js/default.css" type="text/css">
<script language="javascript" src="js/validate.js"></script>
<script language="javascript" src="js/function.js"></script>

<script language="javascript">
function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.userID,"使用者帳號");
	alertStr += checkEmpty(form1.oldPassword,"舊密碼");
	alertStr += checkEmpty(form1.newPassword1,"新密碼");
	alertStr += checkEmpty(form1.newPassword2,"確認新密碼");
	
	var newPassword1=document.all.item("newPassword1").value;
	var newPassword2=document.all.item("newPassword2").value;
	if (newPassword1!=newPassword2){
		alertStr += "[新密碼]和[確認新密碼]不同，請重新輸入！";
	}		
	if(alertStr.length!=0){
		alert(alertStr);
		return false;
	}	
}
</script>
</head>
<body class="body">
<center>
<form method="post" name="form1" onSubmit="return checkField();">
<table class="table_title" width="90%">

</table>
<table class="bg" width="90%">
	<tr>
		<td  colspan="2" class="td_form" style="background:blue;color:white">【變更密碼】</td>
	</tr>
	<tr>		
		<td class="td_form" width="30%" >&nbsp;使用者帳號：</td>
		<td class="td_form" style="text-align:left">
			<input type="text" class="field" name="userID" size="12" maxlength="10" value="" >
		</td>
	</tr>	
	<tr>		
		<td class="td_form" width="30%" >&nbsp;請輸入舊密碼：</td>
		<td class="td_form"  style="text-align:left">
			<input type="password" class="field" name="oldPassword" size="12" maxlength="20" value="" >
		</td>
	</tr>		
	<tr>		
		<td class="td_form" width="30%" >&nbsp;請輸入新密碼：</td>
		<td class="td_form"  style="text-align:left">
			<input type="password" class="field" name="newPassword1" size="12" maxlength="20" value="" >
		</td>
	</tr>		
	<tr>		
		<td class="td_form" width="30%" >&nbsp;確認新密碼：</td>
		<td class="td_form"  style="text-align:left">
			<input type="password" class="field" name="newPassword2" size="12" maxlength="20" value="" >
		</td>
	</tr>	
	<tr><td class="td_form" colspan="2" style="background-color:black"></td></tr>
	<tr >
		<td align="center" colspan="2">
			<input class="toolbar_default" type="submit" name=btnSave value="確　　定" >
			<input class="toolbar_default" type="reset"  name=btnReset value="重新輸入">
			<input class="toolbar_default" type="button" name=btnClose value="關閉視窗" onClick="window.close();">
		</td>
	</tr>	
</table>
</form>
</center>
</body>
</html>

