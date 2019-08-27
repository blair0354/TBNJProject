<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:useBean id="obj" class="eform.index.INDEX" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-control" content="no-cache" />
<meta http-equiv="Expires" content="0">
<link rel="stylesheet" href="js/default.css" type="text/css" />
<link rel="stylesheet" href="js/font.css" type="text/css" />
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript" src="js/function.js"></script>
<script type="text/javascript" src="js/tablesoft.js"></script>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	
	if("init".equals(obj.getState())){
		session.setAttribute("com_title", "內政部中部辦公室－(總歸戶查詢網頁)");
		session.setAttribute("regok", "false"); //預設為false，通過所有檢核可登入系統就設為true。
		session.setAttribute("pass", 0); //記錄密碼輸入錯誤次數
		session.setAttribute("uid", ""); //登入名稱
		session.setAttribute("uname", ""); //帳號中文名
		session.setAttribute("isManager", ""); //是否為系統管理者 是=Y、否=N
		session.setAttribute("city_no", ""); //縣市代號
		session.setAttribute("unit", ""); //地所代號
		session.setAttribute("unitid", ""); //地所代號
		session.setAttribute("fip", ""); //使用者IP位址
		
	}

	int pass = Integer.parseInt(session.getAttribute("pass").toString());
%>
<script language="javascript">
function checkSubmit()
{
	var str = "";
	str += checkEmpty(document.getElementById("txtusr"),"登入名稱");
	str += checkEmpty(document.getElementById("txtuserword"),"密碼");
	if(str != "")
	{
		alert(str);
		return false;
	}

	document.getElementById("state").value = "login";
	
}
</script>
<%
String state = obj.getState();
ArrayList<Map<String,String>> list = null;
if("login".equals(state)){
	list =  obj.selectEtecuser();
	if(list.size() > 0){
		Map<String,String> map = list.get(0);
		session.setAttribute("uid", map.get("user_id")); //登入名稱
		session.setAttribute("uname", map.get("user_name")); //帳號中文名
		session.setAttribute("isManager", map.get("isManager")); //是否為系統管理者 是=Y、否=N
		session.setAttribute("city_no", map.get("city_no")); //縣市代號
		session.setAttribute("unit", map.get("unit")); //地所代號
		session.setAttribute("unitid", map.get("unitid")); //地所代號
		session.setAttribute("pass", 0); //記錄密碼輸入錯誤次數
		session.setAttribute("password", obj.getTxtuserword()); //記錄密碼
		
		state = obj.getState();
		
		
	}else{
		session.setAttribute("pass",++pass);//增加1次
		out.write("<script>alert('帳號、密碼錯誤！請重新輸入!');</script>");
	}
	
	
}
%>
<%
	if(pass >= 3){
		out.write("<script>alert('錯誤次數已達三次！'); window.open('','_self',''); window.close(); </script>");
	}
%>
<%
	if("loginSuccess".equals(state)){
		Map<String,String> map = list.get(0);
		String isStop = map.get("isStop"); //是否停用
		String isOverdue = map.get("isOverdue"); //是否達三個月
		
		if("Y".equals(isStop)){
			out.write("<script>alert('此帳號已被停用！');</script>");
		}
		else if("Y".equals(isOverdue)){
			request.getRequestDispatcher("user_modPd.jsp?checkType=1").forward(request, response);
		}
		else if(obj.getTxtusr().equals(obj.getTxtuserword())){
			request.getRequestDispatcher("user_modPd.jsp?checkType=2").forward(request, response);
		}else{//成功
			session.setAttribute("regok", "true"); //預設為false，通過所有檢核可登入系統就設為true。
			session.setAttribute("pass", 0); //記錄密碼輸入錯誤次數
			session.setAttribute("uid", map.get("user_id")); //登入名稱
			session.setAttribute("uname", map.get("user_name")); //帳號中文名
			session.setAttribute("isManager", map.get("isManager")); //是否為系統管理者 是=Y、否=N
			session.setAttribute("city_no", map.get("city_no")); //縣市代號
			session.setAttribute("unit", map.get("unit")); //地所代號
			session.setAttribute("fip", request.getRemoteAddr()); //使用者IP位址
			
			response.sendRedirect("loginTime.jsp");
		}
	}
%>
<title><%=session.getAttribute("com_title")%></title>
</head>
<body background="images/c07_bg.jpg"
	OnLoad="document.loginform.txtusr.focus();">
	<form action="" method="post" name="loginform" onsubmit="return checkSubmit();">
	<input type="hidden" id="state" name="state" value="<%=state%>">
		<table border="0" width="680" cellspacing="5">
			<tr>
				<td valign="top">
					<p></p>
					<hr color="#800000" size="0">
					<div align="center">
						<center>
							<table border="0" width="680">
								<tr>
									<td height="150" valign="top"><div align="left">
											<img src="images/logo.jpg" width="751" height="154"
												border="1">
										</div></td>
								</tr>
							</table>
							<table border="0" width="680">
								<tr>
									<td valign="top"><img src="images/login.jpg" height="38"></td>
									<td rowspan="2" valign="top"><img src="images/title.jpg"
										height="68"></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2">
										<font color="#333333" size="2">
											登入名稱 
											<INPUT name="txtusr" type="text" tabIndex="1" size="10"
											value=""> 
											
											密碼
											<INPUT name="txtuserword"
											type="password" tabIndex="2" size="20" maxlength="20" value="" />
											
											<INPUT
											type="submit" value="登入" title="登入" tabIndex="3"
											style="background-color: #F0FFFF" />
											
											<INPUT type="reset"
											value="清除" title="清除" tabIndex="4"
											style="background-color: #FFE6E1" />
											
											<INPUT type="button"
											value="密碼修改" title="密碼修改" tabIndex="5"
											style="background-color: #FFE1FF"
											onClick="location.href='user_modPd.jsp'" />
									</font></td>
								</tr>
							</table>
						</center>
					</div>
					<hr color="#800000" size="0">
					<div align="left">
						<font color="red" size="3"> <b>
								※忘記密碼請洽直轄市政地政局或各縣市政府或地政事務所之系統管理人員~重設密碼~ <br>或將帳號、聯絡電話MAIL至「sunny@land.moi.gov.tw」。
						</b>
						</font>
					</div>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>