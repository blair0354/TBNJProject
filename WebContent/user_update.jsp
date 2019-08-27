<%@page import="util.Common"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%//這邊要寫在最上方 使用 request.getRequestDispatcher 時 前面不能有response
	//System.out.println("uid:"+(String)session.getAttribute("uid"));
	//System.out.println("unit:"+(String)session.getAttribute("unit"));
	//String isManager = (String)session.getAttribute("isManager");
	String isManager=Common.getUserIsManager((String)session.getAttribute("uid"), (String)session.getAttribute("unit"));
	if(!"Y".equals(isManager)) {
			request.getRequestDispatcher("/menu_main.jsp").forward(request, response);
	}
%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp"%>
<jsp:useBean id="obj" class="eform.eform3_1.EFORM3_1" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<HTML>

<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-control" content="no-cache" />
<link rel="stylesheet" href="js/default.css" type="text/css" />
<link rel="stylesheet" href="js/font.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="inc/font.css">
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript" src="js/function.js"></script>
<script type="text/javascript" src="js/tablesoft.js"></script>
<script type="text/javascript" src="js/jquery-1.12.1.min.js"></script>
<title><%=session.getAttribute("com_title")%>-使用者帳號資料修改作業</title>
</HEAD>
<%
	String unit = (String)session.getAttribute("unit");
	String citySQL = "";
	if("00".equals(unit)){
		citySQL = "select KCDE_2,KCNT from rkeyn where kcde_1='45' order by KCDE_2";
	}else{
		citySQL = "select KCDE_2,KCNT from rkeyn where kcde_1='45' and kcde_2='" + unit.substring(0,1) + "' order by KCDE_2";
	}
	List list = obj.queryUser(util.IDEncode.DecodePassword(request.getParameter("txtuser_id")));
	Map map = (Map)list.get(0);
%>
<script language="javascript">
	function getUnit(cityNo){
		$.ajax({
			type: "post",
			url: "user_add_ajax.jsp"
					+ "?txtcity_no=" + cityNo,
			datatype: "text",
			async: false,
			success: function(response){
				if(response.length > 10){
					unit = response.substr(8);
					$("#txtunit option").remove();
					$("#txtunit").append(unit);
				}
			}
		});
	}


	function checkSubmit(){
		var msg = "";
		
		if($("#txtuser_id").val() == ""){
			msg += "請輸入 使用者帳號\n";
		}
		if($("#txtuser_name").val() == ""){
			msg += "請輸入 使用者名稱\n";
		}
		else{

			var regx = /[^\u4e00-\u9fa5a-zA-Z0-9]/;
			if(regx.test($("#txtuser_name").val())){
				//msg += "使用者名稱 請輸入中、英、數字\n";
			} 
		}
		var reg = /[^a-zA-Z0-9]/;
		if(!$("#txtpassword").val() == ""){
			
			if($("#txtpassword").val().length < 12){
				msg += "登入密碼必須大於12字元\n";
				$("#txtpassword").val("");
			}
			
			else if(reg.test($("#txtpassword").val())){
				msg += "登入密碼 請輸入 英文字母加數字組合，但不含空白鍵、*、@、#、%或其他符號！\n";
				$("#txtpassword").val("");
			}
		}
		

		
		
		if($("#txtpassword").val() != $("#txtpassword1").val()){
			msg += "登入密碼與確認密碼不符合\n";
			$("#txtpassword").val("");
			$("#txtpassword1").val("");
		}
		var regMail = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]+$/;
		if($("#txtuser_mail").val() == ""){
			msg += "請輸入 使用者email";
		}else if(!regMail.test($("#txtuser_mail").val())){
			msg += "使用者email請輸入正確Email格式！";
		}
		
		
		if(msg.length > 0){
			alert(msg);
			return false;
		}else{
			if($("#txtpassword").val() == "" && $("#txtpassword1").val() == ""){
				alert("未輸入”登入密碼”、”確認密碼”，將沿用原密碼不進行異動!\n");
			}
			if(confirm("是否確定存檔")){
				
				form1.action = "user_update_control.jsp";
				form1.method = "post";
				//form1.target = "_blank";
				form1.submit();
			}
		}


	}

	function reSetPassword(){
		if(confirm("確定重設密碼?")){
			var msg ="";
			if($("#txtuser_name").val() == ""){
				msg += "請輸入 使用者名稱\n";
			}
			else{

				var regx = /[^\u4e00-\u9fa5a-zA-Z0-9]/;
				if(regx.test($("#txtuser_name").val())){
					msg += "使用者名稱 請輸入中、英、數字\n";
				} 
			}
			var regMail = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]+$/;
			if($("#txtuser_mail").val() == ""){
				msg += "請輸入 使用者email";
			}else if(!regMail.test($("#txtuser_mail").val())){
				msg += "使用者email請輸入正確Email格式！";
			}
			if(msg.length > 0){
				alert(msg);
				return;
			}
			
			$.ajax({
				type: "post",
				url: encodeURI(encodeURI("user_update_resetPassword.jsp"
						+ "?txtuser_name=" + $("#txtuser_name").val()
						+ "&txtpassword=" + $("#txtuser_id").val()
						+ "&txtuser_mail=" + $("#txtuser_mail").val()
						+ "&txtunit=" + $("#txtunit").val()
						+ "&isStop=" + $("#isStop").val()
						+ "&isManager=" + $("#isManager").val()
						+ "&txtuser_id=" + $("#txtuser_id").val()
						)),
				datatype: "text",
				async: false,
				success: function(response){
					if(response.length > 0){
						alert("存檔成功!");
						window.location.href = 'eform3_1.jsp';
					}
				}
			});
		}
	}

	function reSetAccount(){
		if(confirm("是否進行帳號啟用? 帳號啟用將自動重設密碼!")){
			var msg ="";
			if($("#txtuser_name").val() == ""){
				msg += "請輸入 使用者名稱\n";
			}
			else{

				var regx = /[^\u4e00-\u9fa5a-zA-Z0-9]/;
				if(regx.test($("#txtuser_name").val())){
					msg += "使用者名稱 請輸入中、英、數字\n";
				} 
			}
			var regMail = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]+$/;
			if($("#txtuser_mail").val() == ""){
				msg += "請輸入 使用者email";
			}else if(!regMail.test($("#txtuser_mail").val())){
				msg += "使用者email請輸入正確Email格式！";
			}
			if(msg.length > 0){
				alert(msg);
				return;
			}
			$.ajax({
				type: "post",
				url: encodeURI(encodeURI("user_update_resetPassword.jsp"
						+ "?txtuser_name=" + $("#txtuser_name").val()
						+ "&txtpassword=" + $("#txtuser_id").val()
						+ "&txtuser_mail=" + $("#txtuser_mail").val()
						+ "&txtunit=" + $("#txtunit").val()
						+ "&isStop=" + "N"
						+ "&isManager=" + $("#isManager").val()
						+ "&txtuser_id=" + $("#txtuser_id").val()
						)),
				datatype: "text",
				async: false,
				success: function(response){
					if(response.length > 0){
						alert("帳號啟動成功!");
						window.location.href = 'eform3_1.jsp';
					}
				}
			});
		}
	}

</script>
<BODY background="images/main_bg.jpg" topmargin="0">
		<table width="660" cellspacing="0">
			<tr>
				<td align="right">
					<a href = "eform3_1.jsp">[回上頁]</a>
				</td>
			</tr>
		</table>
	<FORM Method="post" id="form1" name="form1" onsubmit="return checkSubmit();">
		<table border="1" width="660" cellspacing="0">
			<tr> 
              <td align="right" vAlign="center" class="small" colspan="4" bgcolor="#E7E7E7" > 
              <p align="center"><font color="#0000FF">使用者帳號資料修改</font></td>
            </tr>
            <tr> 
              <td width="131"  align="right">異動日期</td>
              <td width="220"  align="left"> <input name="txtupdate_time" maxlength="10" size="10" tabindex="1" value="<%=obj.createDate()%>" type="hidden" > 
                <font color="#0000FF"><%=map.get("update_time")%></font></td>
              <td width="102"  align="right">建檔人員</td>
              <td width="184"  align="left"> <input name="txtupdate_user" maxlength="10" size="10" tabindex="2" value="<%=session.getAttribute("uname")%>" type="hidden"> 
                <font color="#0000FF"><%=session.getAttribute("uname")%></font></td>
            </tr>
            <tr> 
              <td align="right" vAlign="center" width="131" class="small">
                <font color="#0000FF">使用者帳號</font></td>
              <td colspan="3" align="left"> <input id="txtuser_id" name="txtuser_id" maxlength="10" size="10" tabindex="2" type="hidden" value="<%=map.get("user_id")%>"> 
              <%=map.get("user_id")%>
              </td>
            </tr>
            <tr> 
              <td align="right" vAlign="center" width="131" class="small"><font color="#FF0000">*</font> 
			  <font color="#0000FF">使用者名稱</font></td>
              <td colspan="3" align="left"> <input id="txtuser_name" name="txtuser_name" maxlength="25" size="25" tabindex="3" value="<%=map.get("user_name")%>"> 
              </td>
            </tr>
            <tr> 
              <td width="131" align="right" class="small" ><font color="#FF0000"></font>
			  <font color="#0000FF">登入密碼</font></td>
              <td colspan="3" align="left"> <input id="txtpassword" name="txtpassword" type="password" maxlength="20" size="20" tabindex="5" onBlur="checkPasswd();"> 
              </td>
            </tr>
            <tr> 
              <td width="131" align="right" class="small" ><font color="#FF0000"></font>
			  <font color="#0000FF">密碼確認</font></td>
              <td colspan="3" align="left"> <input id="txtpassword1" name="txtpassword1" type="password" maxlength="20" size="20" tabindex="5"> 
              </td>
            </tr>
			<tr> 
              <td width="123" align="right" class="small" ><font color="#FF0000">*</font> 
                <font color="#0000FF">使用者email</font></td>
              <td colspan="3" align="left"> <input id="txtuser_mail" name="txtuser_mail" type="text" maxlength="100" size="50" tabindex="5" value="<%=map.get("user_mail")%>"> 
              </td>
            </tr>
            <tr> 
              <td width="131" align="right" >
			  <font color="#0000FF">使用者所屬縣市</font></td>
              <td colspan="3" align="left"> 
					<select style="BACKGROUND: #ffffcc"
							name=txtcity_no
							id="txtcity_no"
							onchange="getUnit(this.value);">
						<%=obj.getOption(citySQL, map.get("unit").toString().substring(0,1)) %>
					</select>
              </td>
            </tr>
            <tr>
				<td width="131" align="right" ><font color="#FF0000">*</font>
					<font color="#0000FF">使用者單位</font></td>
				<td colspan="3" align="left">
					<select name="txtunit" id="txtunit" style="background:ffffcc">
						<%=obj.getOption("select s.krmk,s.kcnt from rkeyn s where s.kcde_1='55' and s.kcde_2='01' and krmk like '" + map.get("unit").toString().substring(0,1) +"%' order by krmk", (String)map.get("unit")) %>
					</select>
				</td>
            </tr>
            <tr> 
              <td width="131" align="right" class="small" >
			  <font color="#0000FF">是否停用</font></td>
              <td colspan="1" align="left"><select id="isStop" name="isStop" style='font-family: Arial; background:ffffcc' size='1'><%=Common.get(obj.getYN((String)map.get("isStop")))%>
              </select>
              </td>
	           	<td width="131" align="right" class="small" >
			  	<font color="#0000FF">最近登入日期</font></td>
	           	<td colspan="1" align="left"><%=Common.get((String)map.get("user_login_time"))%></td>
            </tr>
            <tr> 
              <td width="131" align="right" class="small" >
			  <font color="#0000FF">是否為管理者</font></td>
              <td colspan="3" align="left"><select id="isManager" name="isManager" style='font-family: Arial; background:ffffcc' size='1'><%=Common.get(obj.getYN((String)map.get("isManager")))%></select>
              </td>
            </tr>
            <tr>
            	<td width="131" align="right" class="small" >
			  	<font color="#0000FF">前次異動日期</font></td>
            	<td colspan="3" align="left"><%=map.get("update_time")%></td>
            </tr>
            <tr>
            	<td width="131" align="right" class="small" >
			  	<font color="#0000FF">前次異動人員</font></td>
            	<td colspan="3" align="left"><%=map.get("update_userc")%></td>
            </tr>
            <tr> 
              <td colspan="7" bgcolor="#E7E7E7"> <p align="left" style="line-height: 100%; margin-top: 0; margin-bottom: 6"><font color="#FF0000">說明：</font>有 
                  <font color="#FF0000">* </font>註記表示一定要輸入（<font color="#0000FF">不可空白</font>）。　　　</p>
                <p align="center" style="margin-top: 4; margin-bottom: 0"> 
                  <input type="submit" value="存檔" name="submit" tabindex="60">
                  <input type="reset" value="重填" name="clear" tabindex="61">
                  <INPUT type="button"  value="重設密碼" title="重設密碼" style="background-color:#33BBFF" onClick="reSetPassword();"/>
                  <INPUT type="button"  value="啟用帳號" title="啟用帳號" style="background-color:#33BBFF" onClick="reSetAccount();" <%if("N".equals((String)map.get("isStop"))) out.write("disabled");%>/>
              </td>
            </tr>
		</table>
	</FORM>
</BODY>
</HTML>