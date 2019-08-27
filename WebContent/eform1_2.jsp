<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp"%>
<jsp:useBean id="obj" class="eform.eform1_2.EFORM1_2" scope="request">
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
<title><%=session.getAttribute("com_title")%>-權利人資料查詢</title>
</HEAD>
<script language="javascript">

	function getUnit(txtcity_no){
		var unit;

		if(txtcity_no == "") {
			$("#txtunit option").remove();
			$("#txtunit").append('<option value="">全部</option>');
			return;
		}

		
		$.ajax({
			type: "post",
			url: "eform1_2_ajax_1.jsp"
					+ "?txtcity_no=" + txtcity_no,
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


	function checkSubmit()
	{
		var str = "";

		if($("#txtqry_no").val() == "" && $("#txtqry_name").val() == "")
			str += "請輸入統編或姓名查詢!\n";
		if($("#unit").val() != "00"){ //如果session.unit = 00 可以略過
			if($("#txtrcv_yr").val() == "" || $("#txtrcv_yr").val() == "" || $("#txtrcv_no").val() == "")
				str += "請輸入收件年字號\n";
			if($("#txtsno").val() == "" || $("#txtsname").val() == "")
				str += "請輸入申請人資料\n";
			if(!$("#txtqry_purpose01").prop("checked") && !$("#txtqry_purpose02").prop("checked") && !$("#txtqry_purpose03").prop("checked"))
				str += "請選擇申請用途\n";
			if($("#txtqry_purpose03").prop("checked") && $("#txtqry_purpose03a").val() == "")
				str += "請輸入申請用途-其他說明內容\n";
			if($("#txtqry_oper").val() == "")
				str += "請輸入列印操作人員\n";
		}
		
		if(str != "")
		{
			alert(str);
			return false;
		}
		
		var url = "eform1_2qry.jsp"
			+ "?txtcity_no=" + $("#txtcity_no").val()
			+ "&txtunit=" + $("#txtunit").val()
			+ "&txtqry_no=" + $("#txtqry_no").val()
			+ "&txtqry_name=" + $("#txtqry_name").val();

		form1.action = "eform1_2qry.jsp";
		form1.method = "post";
		form1.target = "_blank";
		form1.submit();
		
	}
	
</script>
<%
	String unit = (String)session.getAttribute("unit");
	//System.out.println(unit);
	obj.setUnit(unit);
	//System.out.println(obj.getUnit());
%>
<BODY background="images/main_bg.jpg" topmargin="0">
	<FORM Method="post" name="form1" onsubmit="return checkSubmit();">
		<input type="hidden" id="unit" name="unit" value="<%=obj.getUnit()%>">
		<table border="0" width=630 cellspacing="0" cellpadding="0">
			<tr align="center" valign="middle">
				<td colspan="3"><font size="4" face="標楷體" color="#FF9900">權利人資料查詢</font>
					<hr size="1" color="#008000" width="630" align="left"></td>
			</tr>
			<tr>
				<td width="211">查詢縣市：
					<select name="txtcity_no" id="txtcity_no"
						style="background: ffffcc" onchange="getUnit(this.value)";>
							<option value="">全部</option>
							<%=obj.getOption("select kcde_2,kcde_2+'-'+kcnt as kcnt,krmk from rkeyn where kcde_1='45' and left(kcde_2,2)<>'/*' and left(kcde_2,2)<>'20' and left(kcde_2,2)<>'- ' and left(krmk,2)<>'00' order by krmk", "") %>
					</select>
				</td>
				<td width="288">事務所： 
					<select name="txtunit" id="txtunit"
						style="background: ffffcc">
							<option value="">全部</option>
					</select>
				</td>
				<td width="131" align="center" bgcolor="#FF00FF"><font
					color="#FFFFFF">權利人資料查詢</font></td>
			</tr>
			<tr>
				<td>統一編號：<input name="txtqry_no" id="txtqry_no" maxlength="10" size="10"
					tabindex="2"></td>
				<td>姓 名：<input name="txtqry_name" id="txtqry_name" maxlength="30" size="20"
					tabindex="3"></td>
				<td align="center"><font color="#333333" size="2"> <input
						type=button value="查詢" class=Button onClick="javascript:checkSubmit();"
						id=button1 name=button1 tabIndex="4"> &nbsp;<INPUT
						name="reset" type="reset" tabIndex="5" title="清除" value="清除" />
				</font></td>
			</tr>
			<tr>
				<td colspan="3"><hr size="1" color="#008000" width="660"
						align="left"></td>
			</tr>

			<tr>
				<td colspan="3">收件年字號： 
				<input name="txtrcv_yr" id="txtrcv_yr" maxlength="3"
					size="4">&nbsp;年&nbsp; 
				<input name="txtrcv_word" id="txtrcv_word"s
					maxlength="10" size="10">&nbsp;字第&nbsp; 
				<input
					name="txtrcv_no" id="txtrcv_no" maxlength="6" size="6">&nbsp;號
				</td>
			</tr>
			<tr>
				<td colspan="3">申請人統一編號： 
				<input name="txtsno" id="txtsno" maxlength="10"
					size="10"> 申請人姓名：
				<input name="txtsname" id="txtsname" maxlength="30"
					size="30">
				</td>
			</tr>
			<tr>
				<td colspan="3">代理人統一編號： 
				<input name="txtsno1" maxlength="10"
					size="10"> 代理人姓名：
				<input name="txtsname1" maxlength="30"
					size="30">
				</td>
			</tr>
			<tr>
				<td colspan="3">申請用途： 
				<input name="txtqry_purpose01" id="txtqry_purpose01" type="checkbox" value="1">申辦登記案件使用&nbsp;&nbsp; 
				<input name="txtqry_purpose02" id="txtqry_purpose02" type="checkbox" value="1">處理訴訟案件&nbsp;&nbsp;
				<input name="txtqry_purpose03" id="txtqry_purpose03" type="checkbox" value="1">其他
				<input name="txtqry_purpose03a" id="txtqry_purpose03a" maxlength="30" size="30">
				</td>
			</tr>
			<tr>
				<td colspan="3">列印操作人員： 
				<input name="txtqry_oper" id="txtqry_oper" maxlength="20" size="20">
				</td>
			</tr>
		</table>
		<hr size="1" color="#008000" width="630" align="left">
		</td>
		</tr>
		</table>
	</FORM>
</BODY>
</HTML>