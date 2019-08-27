<%@ page contentType="text/html;charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Language" content="zh-tw">
<link rel="stylesheet" type="text/css" href="js/font.css">
<title><%=session.getAttribute("com_title")%></title>
<script language="JavaScript">
	function showdate() {
		var vn = "Microsoft Internet Explorer";
		var some;
		if (navigator.appName != vn)
			some = "11";
		else
			some = "1911";
		var enabled = 0;
		today = new Date();
		var day;
		var date;
		if (today.getDay() == 0)
			day = "星期日"
		if (today.getDay() == 1)
			day = "星期一"
		if (today.getDay() == 2)
			day = "星期二"
		if (today.getDay() == 3)
			day = "星期三"
		if (today.getDay() == 4)
			day = "星期四"
		if (today.getDay() == 5)
			day = "星期五"
		if (today.getDay() == 6)
			day = "星期六"
		document.fgColor = "000000";
		date = (today.getYear()) + " 年 " + (today.getMonth() + 1) + " 月 "
				+ today.getDate() + " 日 " + day + "";
		document.write(date);
	}
</script>

<base target="main">

</head>
<body topmargin="2" leftmargin="2" background="images/left_bg.jpg">

	<div align="left">

		<table border="0" width="159" cellspacing="0" cellpadding="0">
			<tr>
				<td width="12"></td>
				<td width="139">
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="2"><p>
									<img src="images/u1.gif">
								</p></td>
						</tr>
						<tr>
							<td colspan="2" background="images/rb.gif">
								<table cellspacing="0" cellpadding="0" width="138">
									<tr>
										<td width="11">&nbsp;</td>
										<td width="105">
											<table cellspacing="0" border="1" bordercolorlight="#FFFFFF"
												bordercolordark="#FFFFFF" bgcolor="#e2ebea" class="c12">
												<tr>
													<td width="114"><%=session.getAttribute("uid")%></td>
												</tr>
												<tr bgcolor="Gainsboro">
													<td><%=session.getAttribute("uname")%></td>
												</tr>

												<tr bgcolor="Gainsboro">
													<td width="114"><script LANGUAGE="JavaScript"
															TYPE="text/javascript">
													<!--
														showdate();
													//-->
													</script></td>
												</tr>
											</table>
										</td>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="2"><img src="images/u2.gif"></td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>

						<tr>

							<td height="34" colspan="2">
								<p align="center">
									<a href="menu.jsp" title="回首頁" target="_parent"><img
										src=images/home.jpg border=0
										onmouseover='javascript:this.src=
										"images/home1.jpg"'
										onmouseout='javascript:this.src="images/home.jpg"'></a>
								</p>
							</td>
						</tr>
						<tr>

							<td height="32" colspan="2">
								<p align="center">
									<a href="index.jsp" title="登出" target="_parent"><img
										src=images/logout.jpg width="100" height="27" border=0
										onmouseover='javascript:this.src=
										"images/logout1.jpg"'
										onmouseout='javascript:this.src="images/logout.jpg"'></a>
								</p>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<p>&nbsp;</p>
	</div>
</body>
</html>