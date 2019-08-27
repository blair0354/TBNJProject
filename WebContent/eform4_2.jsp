<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp"%>
<jsp:useBean id="obj" class="eform.eform4_2.EFORM4_2" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-control" content="no-cache" />
<link rel="stylesheet" href="js/default.css" type="text/css" />
<link rel="stylesheet" href="js/font.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="js/font.css">
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript" src="js/function.js"></script>
<script type="text/javascript" src="js/tablesoft.js"></script>
<script type="text/javascript" src="js/jquery-1.12.1.min.js"></script>
<title><%=session.getAttribute("com_title")%>-私有土地歸戶資料查詢</title>
</head>
<script language="javascript">
	
	function selectCityNo(cityNo){

		$.ajax({
			type: "post",
			url: "eform4_2_ajax_1.jsp"
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
		
		if($("#txtcity_no").val() == "" || $("#txtunit").val() == ""){
			msg += "請選擇查詢縣市/鄉鎮\n";
		}

		if($("#unit").val() != "00"){
			if($("#txtrcv_yr").val() == "" || $("#txtrcv_word").val() == "" || $("#txtrcv_no").val() == ""){
				msg += "請輸入收件年字號\n";
			}
			if($("#txtsno").val() == "" || $("#txtsname").val() == ""){
				msg += "請輸入申請人資料\n";
			}
			if(!$("#txtqry_purpose01").prop("checked") && !$("#txtqry_purpose02").prop("checked") && !$("#txtqry_purpose03").prop("checked"))
				msg += "請選擇申請用途\n";
			if($("#txtqry_purpose03").prop("checked") && $("#txtqry_purpose03a").val() == "")
				msg += "請輸入申請用途-其他說明內容\n";
			if($("#txtqry_oper").val() == "")
				msg += "請輸入列印操作人員\n";
			
		}
		
		
		if(msg.length > 0){
			alert(msg);
			return false;
		}


		form1.action = "eform4_2_qry.jsp";
		form1.method = "post";
		form1.target = "_blank";
		form1.submit();
	}

	function setName(obj,changName){
		var index = obj.selectedIndex;
		var name = obj.options[index].text;
		if(name.indexOf("-") > 0){
			name = name.substring(2);
		}
		$("#" + changName).val(name);
	}
</script>
<%
String unit = (String)session.getAttribute("unit");
obj.setUnit(unit);
%>
<body background="images/main_bg.jpg" topmargin="0">
	<FORM Method=post name="form1" onsubmit="return checkSubmit();">
	<input type="hidden" id="unit" name="unit" value="<%=obj.getUnit()%>">
		<table border="0" width=637 cellspacing="0" cellpadding="0">
			<tr align="center" valign="middle">
				<td colspan="2"><font size="4" face="標楷體" color="#FF9900">私有土地歸戶分類統計表
				</font>
					<hr size="1" color="#008000" width="660" align="left"></td>
			</tr>
			<tr>
				<td width="417">查詢縣市：
					<select style="background: ffffcc" id="txtcity_no" name="txtcity_no" onchange="selectCityNo(this.value);setName(this,'txtcity_name');">
						<%=util.View.getOption("select kcde_2,kcde_2+'-'+kcnt as kcnt from rkeyn where kcde_1='45' and left(kcde_2,2)<>'/*' and left(kcde_2,2)<>'20' and left(kcde_2,2)<>'- ' and left(krmk,2)<>'00' order by krmk", "")%>
					</select>
					<input type="hidden" id="txtcity_name" name="txtcity_name">
				</td>
				<td width="243" align="center" bgcolor="#FF00FF"><font
					color="#FFFFFF">私有土地歸戶分類統計表</font></td>
			</tr>
			<tr>
				<td>鄉鎮： 
				<select id="txtunit" name=txtunit style="background: ffffcc" onchange="setName(this,'txtunit_name');" >
						<option value="">未選取</option>
				</select>
				<input type="hidden" id="txtunit_name" name="txtunit_name">
				</td>
				<td align="center"><font color="#333333" size="2"> <input
						type=button value="製表" class=Button onClick="checkSubmit();"
						id=button1 name=button1> &nbsp; <INPUT name="reset"
						type="reset" tabIndex="4" title="清除" value="清除" />
				</font></td>
			</tr>
			<tr>
				<td colspan="3"><hr size="1" color="#008000" width="660"
						align="left"></td>
			</tr>

			<tr>
				<td colspan="3">收件年字號： 
				<input 	
					id="txtrcv_yr" 
					name="txtrcv_yr" 
					maxlength="3"
					size="4">&nbsp;年&nbsp; 
				<input 
					id="txtrcv_word" 
					name="txtrcv_word"
					maxlength="10" 
					size="10">&nbsp;字第&nbsp; 
				<input 
					id="txtrcv_no"
					name="txtrcv_no" 
					maxlength="6" 
					size="6">&nbsp;號
				</td>
			</tr>
			<tr>
				<td colspan="3">
				申請人統一編號： 
				<input 
					id="txtsno"
					name="txtsno" 
					maxlength="10"
					size="10"> 
				申請人姓名：
				<input 
					id="txtsname"
					name="txtsname" 
					maxlength="30"
					size="30">
				</td>
			</tr>
			<tr>
				<td colspan="3">
				代理人統一編號： 
				<input 
					id="txtsno1"
					name="txtsno1" 
					maxlength="10"
					size="10"> 
				代理人姓名：
				<input 
					ido="txtsname1"
					name="txtsname1" 
					maxlength="30"
					size="30">
				</td>
			</tr>
			<tr>
				<td colspan="3">
				申請用途： 
				<input 
					id="txtqry_purpose01"
					name="txtqry_purpose01"
					type="checkbox" 
					value="1">
				申辦登記案件使用&nbsp;&nbsp; 
				<input
					id="txtqry_purpose02"
					name="txtqry_purpose02" 
					type="checkbox" 
					value="1">
				處理訴訟案件&nbsp;&nbsp;
				<input 
					id="txtqry_purpose03"
					name="txtqry_purpose03" 
					type="checkbox" 
					value="1">
				其他
				<input 
					id="txtqry_purpose03a"
					name="txtqry_purpose03a" 
					maxlength="30" 
					size="30">
				</td>
			</tr>
			<tr>
				<td colspan="3">
				列印操作人員： 
				<input 
					id="txtqry_oper"
					name="txtqry_oper"
					maxlength="20" 
					size="20">
				</td>
			</tr>
		</table>
		<hr size="1" color="#008000" width="660" align="left">
		</td>
		</tr>
		</table>
	</FORM>
</body>
</html>