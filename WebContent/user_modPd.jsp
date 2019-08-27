<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="home/head.jsp" %>
<jsp:useBean id="obj" class="eform.user_modPd.USER_MODPD">
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
<title>使用者密碼修改作業</title>
<%
	String state = obj.getState();
	String ms = "";
	if("checkUpdate".equals(state)){
		ms = obj.doUpdate(obj.getTxtAccNum(), obj.getOldAccPwd(), obj.getTxtAccPwd(), obj.getTxtAccMIL());
	}
	
	if(!"".equals(ms)){
		out.write("<script>alert('" + ms + "')</script>");
	}
%>
<%
	String msg = "";
	String checkType = request.getParameter("checkType");
	if("1".equals(checkType)){
		msg = "您的密碼使用期限已到期(三個月)，請先進行密碼修改後，才可登錄總歸戶系統，謝謝您!";
	}else if("2".equals(checkType)){
		msg = "您的密碼已重新設定，請先進行密碼修改後，才可登錄總歸戶系統，謝謝您!";
	}else{
		msg = "";
	}
%>
<%
	String uid = "";
	String password = "";
	if("1".equals(checkType) || "2".equals(checkType)){
		uid = (String)session.getAttribute("uid");
		password = (String)session.getAttribute("password");
		obj.selectEtecuser(uid, password);
	}
%>
<script language="javascript">

	//function checkPasswd(pwd)
	//{
	//	if(pwd.length != 12){
	//		alert("密碼請輸入至少12個字元以上的英文字母加數字組合，但不含空白鍵、*、@、#、%或其他符號！");
	//		return;
	//	}
	//	var re = /[a-zA-Z\d]/;
	//	if(!pwd.match(re)){
	//		alert("您的密碼只能是英文字母加數字組合，不能含空白鍵、*、@、#、%或其他符號 !");
	//	}
		
	//}


	//function equalPasswd()
	//{
	//	var pwd = document.getElementById("txtAccPwd").value;
	//	var pwd1 = document.getElementById("txtAccPwd1").value;
	//	if(pwd != pwd1){
	//		alert("密碼不一致，請重新輸入!");
	//		document.getElementById("txtAccPwd").value = "";
	//		document.getElementById("txtAccPwd1").value = "";
	//		document.getElementById("txtAccPwd").focus();
	//	}
	//}

	//function checkEmail(obj)
	//{	
	//	var email = obj.value;
	//	var re =/([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	//	if(email.length == 0)
	//	{
	//		alert("請輸入使用者Email!");
	//	}
	//	else if(!email.match(re))
	//	{
	//		alert("請輸入正確Email格式!");
	//		obj.value = "";
	//	}
	//	console.log(email);
	//}

	function checkSubmit()
	{
		var str = "";
		str += checkEmpty(document.getElementById("txtAccNum"),"使用者帳號");
		str += checkEmpty(document.getElementById("txtAccMIL"),"使用者email");
		str += checkEmail(document.getElementById("txtAccMIL"),"使用者email");
		str += checkEmpty(document.getElementById("oldAccPwd"),"登入舊密碼");
		str += checkEmpty(document.getElementById("txtAccPwd"),"登入新密碼");
		str += checkEmpty(document.getElementById("txtAccPwd1"),"密碼確認");
		
		if((document.getElementById("txtAccPwd").value).length != 12){
			str +="登入新密碼請輸入至少12個字元以上的英文字母加數字組合，但不含空白鍵、*、@、#、%或其他符號！"+"\n";
		}
		if((document.getElementById("txtAccPwd1").value).length != 12){
			str +="密碼請輸入至少12個字元以上的英文字母加數字組合，但不含空白鍵、*、@、#、%或其他符號！"+"\n";
		}
		var re = /[a-zA-Z\d]/;
		if(!(document.getElementById("txtAccPwd").value).match(re)){
			str +="登入新密碼只能是英文字母加數字組合，不能含空白鍵、*、@、#、%或其他符號 !";
		}
		if(!(document.getElementById("txtAccPwd1").value).match(re)){
			str +="密碼確認 只能是英文字母加數字組合，不能含空白鍵、*、@、#、%或其他符號 !";
		}
		if(document.getElementById("txtAccPwd").value != document.getElementById("txtAccPwd1").value){
			str +="登入新密碼、密碼確認 不一致，請重新輸入!";
		}
		
		if(str != "")
		{
			alert(str);
			return false;
		}

		document.getElementById("state").value = "checkUpdate";
		
	}
</script>
</head>
<body background="images/main_bg.jpg" topmargin="0"
	OnLoad="document.forms.txtAccNum.focus()">
	<table border="0" cellspacing="0" cellpadding="0">

		<tr>
			<td valign="top" colspan="3" height="1"><p
					style="line-height: 150%; margin: 0" align="right">
					<a href="" onclick="history.go(-1);"><u>回上頁</u></a></p></td>
		</tr>

		<tr>
			<td valign="top"></td>
			<td valign="top">
				<form action="user_modPd.jsp" method=post name=forms onsubmit="return checkSubmit();">
				<input type="hidden" id="state" name="state" value="<%=state%>">
					<div>	
						<table border="1" width="450" cellspacing="1">
							<tr>
								<td align="right" vAlign="center" class="small" colspan="4"
									bgcolor="#E7E7E7">
									<p align="center">
										<font color="#0000FF">使用者密碼修改</font>
								</td>
							</tr>
							<tr>
								<td align="right" vAlign="center" class="small"><font
									color="#FF0000">*</font> <font color="#0000FF">使用者帳號</font></td>
								<td colspan="3" align="left"><input name="txtAccNum"
									maxlength="10" size="10" tabindex="2"
									value="<%=obj.getTxtAccNum()%>"></td>
							</tr>
							<tr>
								<td align="right" vAlign="center" class="small"><font
									color="#FF0000">*</font> <font color="#0000FF">使用者email</font></td>
								<td colspan="3" align="left"><input name="txtAccMIL"
									maxlength="40" size="40" tabindex="2"
									value="<%=obj.getTxtAccMIL()%>"></td>
							</tr>
							<tr>
								<td align="right" vAlign="center" class="small"><font
									color="#FF0000">*</font> <font color="#0000FF">登入舊密碼</font></td>
								<td colspan="3" align="left"><input name="oldAccPwd"
									type="password" maxlength="20" size="20" tabindex="3"
									value="<%=obj.getOldAccPwd()%>"></td>
							</tr>
							<tr>
								<td align="right" class="small"><font color="#FF0000">*</font>
									<font color="#0000FF">登入新密碼</font></td>
								<td colspan="3" align="left"><input name="txtAccPwd"
									type="password" maxlength="20" size="20" tabindex="5"
									></td>
							</tr>
							<tr>
								<td align="right" class="small"><font color="#FF0000">*</font>
									<font color="#0000FF">密碼確認</font></td>
								<td colspan="3" align="left"><input name="txtAccPwd1"
									type="password" maxlength="20" size="20" tabindex="5"
									>
								</td>
							</tr>
							<tr>
								<td colspan="7" bgcolor="#E7E7E7">
									<p align="left"
										style="line-height: 100%; margin-top: 0; margin-bottom: 6">
										<font color="#FF0000">說明：</font>有 <font color="#FF0000">*
										</font>註記表示一定要輸入（<font color="#0000FF">不可空白</font>）。
									</p>
									<p align="center" style="margin-top: 4; margin-bottom: 0">
										<input type="submit" value="存檔" name="submit" tabindex="60">
										<input type="reset" value="重填" name="clear" tabindex="61">
								</td>
							</tr>
						</table>
					</div>
				</form>

			</td>
			<td valign="top"></td>
		</tr>
		<tr>
			<td valign="top" colspan="3" height="1"><p
					style="line-height: 150%; margin: 0" align="right">
					<a href="" onclick="history.go(-1);"><u>回上頁</u></a></td>
		</tr>
	</table>
	<table width="450">
		<tr><tr>
			<td valign="top" colspan="3" height="1">
				<p style="line-height: 150%; margin: 0;" align="center">
					<font color="red" size="4"><%=msg%></font> 
				</p>
			</td>
		</tr></tr>
	</table>
	
	<p style="LINE-HEIGHT: 100%; MARGIN-BOTTOM: 0px; MARGIN-TOP: 0px"></p>
</body>
</html>