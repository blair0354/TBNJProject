<!--
程式目的：EFORM506
程式代號：EFORM506
程式日期：1060620
程式作者：Sya
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<%@page import="util.View" %>
<%@page import="lca.ap.EFORM506" %>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp" %>

<jsp:useBean id="obj" scope="request" class="lca.ap.EFORM506">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<%

	String strlen="";
	if("doSave".equals(obj.getState())){
		obj.getLogsSave();
	}

	ArrayList<String[]> al=new ArrayList<String[]>();	
		al=obj.getResultModel();
		
	ArrayList<String[]> all=new ArrayList<String[]>();	
		all=obj.getResultModelCTY();
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
	
	//存檔
	function doSave(){
		if(isCheckedAnyOne()){
			form1.state.value = "doSave";
			form1.submit();
		}else{
			alert("尚未勾選任何行政欄位！");
			return;
		}
	}

	//指定縣市
	function open_selected(obj){
		document.getElementById('ck_select').value = obj;
		for(var i=1 ; i<=22 ; i++){
			var tr_id = 'tr_' + i;
			var tr_cs = 'tr_' + i +'_'+ i;
			document.getElementById(tr_id).style.display='none';
			document.getElementById(tr_cs).style.display='none';
		}
		if(obj == 'A'){
			document.getElementById('tr_1').style.display='table-row';
			document.getElementById('tr_1_1').style.display='table-row';
		}else if(obj == 'B'){
			document.getElementById('tr_2').style.display='table-row';
			document.getElementById('tr_2_2').style.display='table-row';
		}else if(obj == 'C'){
			document.getElementById('tr_3').style.display='table-row';
			document.getElementById('tr_3_3').style.display='table-row';
		}else if(obj == 'D'){
			document.getElementById('tr_4').style.display='table-row';
			document.getElementById('tr_4_4').style.display='table-row';
		}else if(obj == 'E'){
			document.getElementById('tr_5').style.display='table-row';
			document.getElementById('tr_5_5').style.display='table-row';
		}else if(obj == 'F'){
			document.getElementById('tr_6').style.display='table-row';
			document.getElementById('tr_6_6').style.display='table-row';
		}else if(obj == 'G'){
			document.getElementById('tr_7').style.display='table-row';
			document.getElementById('tr_7_7').style.display='table-row';
		}else if(obj == 'H'){
			document.getElementById('tr_8').style.display='table-row';
			document.getElementById('tr_8_8').style.display='table-row';
		}else if(obj == 'I'){
			document.getElementById('tr_9').style.display='table-row';
			document.getElementById('tr_9_9').style.display='table-row';
		}else if(obj == 'J'){
			document.getElementById('tr_10').style.display='table-row';
			document.getElementById('tr_10_10').style.display='table-row';
		}else if(obj == 'K'){
			document.getElementById('tr_11').style.display='table-row';
			document.getElementById('tr_11_11').style.display='table-row';
		}else if(obj == 'M'){
			document.getElementById('tr_12').style.display='table-row';
			document.getElementById('tr_12_12').style.display='table-row';
		}else if(obj == 'N'){
			document.getElementById('tr_13').style.display='table-row';
			document.getElementById('tr_13_13').style.display='table-row';
		}else if(obj == 'O'){
			document.getElementById('tr_14').style.display='table-row';
			document.getElementById('tr_14_14').style.display='table-row';
		}else if(obj == 'P'){
			document.getElementById('tr_15').style.display='table-row';
			document.getElementById('tr_15_15').style.display='table-row';
		}else if(obj == 'Q'){
			document.getElementById('tr_16').style.display='table-row';
			document.getElementById('tr_16_16').style.display='table-row';
		}else if(obj == 'T'){
			document.getElementById('tr_17').style.display='table-row';
			document.getElementById('tr_17_17').style.display='table-row';
		}else if(obj == 'U'){
			document.getElementById('tr_18').style.display='table-row';
			document.getElementById('tr_18_18').style.display='table-row';
		}else if(obj == 'V'){
			document.getElementById('tr_19').style.display='table-row';
			document.getElementById('tr_19_19').style.display='table-row';
		}else if(obj == 'W'){
			document.getElementById('tr_20').style.display='table-row';
			document.getElementById('tr_20_20').style.display='table-row';
		}else if(obj == 'X'){
			document.getElementById('tr_21').style.display='table-row';
			document.getElementById('tr_21_21').style.display='table-row';
		}else if(obj == 'Z'){
			document.getElementById('tr_22').style.display='table-row';
			document.getElementById('tr_22_22').style.display='table-row';
		}else{
			for(var i=1 ; i<=22 ; i++){
				var tr_id = 'tr_' + i;
				var tr_id_i = 'tr_' + i + '_' + i;
				document.getElementById(tr_id).style.display='table-row';
				document.getElementById(tr_id_i).style.display='table-row';
			}
		}
	}

	function doWindowsOpen() {
		var config = "height=450,width=800,location=no,menubar=no,left=500,top=80,location=no";
		window.open('eform506_win.jsp', "Joseph", config);
	}

	function isCheckedAnyOne(){
		var checkboxs = document.getElementsByName('outputTable');

		var isAnyOneChecked = false;
		
		for (var i = 0; i < checkboxs.length; i++) {
			if(checkboxs[i].checked == true){
				isAnyOneChecked = true;
				break;
				}
		}

		return isAnyOneChecked;
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
<div id="controlTrCheckbox" onclick="firstColumnCheckboxDoToggleAllHandler(event);">
<table style="border:0;width:700;cellspacing:0;cellpadding:0">
	<tr>
		<td width="30%">
		<td width="70%">
	</tr>
	<tr align="center">
		<td colspan="2"><font size="4" face="標楷體" color="#FF9900">原住民地區行政區設定作業</font>
		<hr size="1" color="#008000" width="700" align="left">
		</td>
	</tr>
	<tr align="center">
		<td colspan="2">
			<input class="toolbar_default" type="button" name="btn_Save" value="存檔" onClick="doSave();"> &nbsp;&nbsp;
			<input class="toolbar_default" type="reset" name="btn_Re" value="重置" onClick=""> &nbsp;&nbsp;
			<input class="toolbar_default" type="button" name="btn_See" value="檢視存檔之行政區" onClick="doWindowsOpen();"> &nbsp;&nbsp;
		</td>
	</tr>
	<tr align="left">
		<td colspan="2">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input class="toolbar_default" type="button" name="btn_All" value="全選 " onClick="topButtonDoToggleAllHandler(event,1);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input class="toolbar_default" type="button" name="btn_notAll" value="取消全選" onClick="topButtonDoToggleAllHandler(event,2);">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<select name="cty" onchange="open_selected(this.options[this.selectedIndex].value);">
				<%=EFORM506.getOptionToOracle("select a.CTY,a.CTY||'-'||(select s.KCNT from RKEYN s where rownum=1 and s.KCDE_1='45' and s.KCDE_2=s.CTY and s.KCDE_2=a.CTY)AS CTYC from EFORM5_0 a where a.CTY_USER is not null order by a.CTY",obj.getCty()) %>
			</select>
		</td>
	</tr>
	<tr align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	
	<tr id="tr_1" align="center">
		<td><font size="-1">
			<input type="checkbox" class="countyClass" name="cty_cb" value="A"><%=obj.getA()%></font></td>
		<td align="left"><font size="-1">
			<% 
				
				int tmp_A=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("A")){
						tmp_A++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_A%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_1_1" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_2" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="B"><%=obj.getB()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_B=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("B")){
						tmp_B++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_B%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_2_2" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_3" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="C"><%=obj.getC()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_C=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("C")){
						tmp_C++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_C%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_3_3" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_4" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="D"><%=obj.getD()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_D=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("D")){
						tmp_D++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_D%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_4_4" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_5" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="E"><%=obj.getE()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_E=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("E")){
						tmp_E++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_E%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_5_5" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_6" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="F"><%=obj.getF()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_F=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("F")){
						tmp_F++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_F%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_6_6" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_7" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="G"><%=obj.getG()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_G=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("G")){
						tmp_G++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_G%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_7_7" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_8" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="H"><%=obj.getH()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_H=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("H")){
						tmp_H++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_H%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_8_8" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_9" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="I"><%=obj.getI()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_I=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("I")){
						tmp_I++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_I%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_9_9" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_10" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="J"><%=obj.getJ()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_J=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("J")){
						tmp_J++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_J%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_10_10" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_11" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="K"><%=obj.getK()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_K=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("K")){
						tmp_K++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_K%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_11_11" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_12" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="M"><%=obj.getM()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_L=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("L")){
						tmp_L++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_L%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_12_12" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_13" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="N"><%=obj.getN()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_M=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("M")){
						tmp_M++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_M%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_13_13" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_14" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="O"><%=obj.getO()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_N=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("N")){
						tmp_N++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_N%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_14_14" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_15" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="P"><%=obj.getP()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_O=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("O")){
						tmp_O++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_O%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_15_15" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_16" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="Q"><%=obj.getQ()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_P=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("P")){
						tmp_P++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_P%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_16_16" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_17" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="T"><%=obj.getT()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_Q=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("Q")){
						tmp_Q++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_Q%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_17_17" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_18" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="U"><%=obj.getU()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_R=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("R")){
						tmp_R++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_R%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_18_18" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_19" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="V"><%=obj.getV()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_S=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("S")){
						tmp_S++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_S%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_19_19" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_20" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="W"><%=obj.getW()%></font></td>
		<td align="left"><font size="-1">
		<% 
				int tmp_T=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("T")){
						tmp_T++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_T%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_20_20" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_21" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="X"><%=obj.getX()%></font></td>
		<td align="left"><font size="-1">
		<% 
				int tmp_U=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("U")){
						tmp_U++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_U%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_21_21" align="center">
		<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
	</tr>
	<tr id="tr_22" align="center">
		<td><font size="-1"><input type="checkbox" class="countyClass" name="cty_cb" value="Z"><%=obj.getZ()%></font></td>
		<td align="left"><font size="-1">
			<% 
				int tmp_V=0;
				for(int i=0;i<al.size();i++){
					String data[]=(String[])al.get(i);
					if(data[0].equals("V")){
						tmp_V++;
						strlen=(data[3]+"　　　　　　").substring(0,6);
						if(tmp_V%4==0){
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%><br>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%><br>	
			<%
							}
						}else{
							if(data[5].equals("Y")){
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>" checked="checked"><%=strlen%>
			<%		
							}else{
			%>
							<input type="checkbox" name="outputTable" value="<%=data[0]%>,<%=data[1]%>,<%=data[2]%>,<%=data[3]%>,<%=data[4]%>"><%=strlen%>	
			<%
							}				
						}
					}
				}
			%>
		</font></td>
	</tr>
	<tr id="tr_22_22" align="center">
		<td colspan="2"></td>
	</tr>
	<tr>
		<td>
			<input type="hidden" name="ck_select" id="ck_select" value=""/>
		</td>
	</tr>
</table>
</div>
<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="userID" value="<%=session.getAttribute("uid")%>">
<input type="hidden" name="unitID" value="<%=session.getAttribute("unitid")%>">

</form>
<script type="text/javascript">

/*
bindDivCheckBoxToListenOnChange();

function bindDivCheckBoxToListenOnChange(){

    if(window.addEventListener){

      document.getElementById('controlTrCheckbox').addEventListener('change', firstColumnCheckboxDoToggleAllHandler, false);                	
        
        }else{
			alert("TEST");
        	document.getElementById('controlTrCheckbox').attachEvent('onchange',firstColumnCheckboxDoToggleAllHandler);
            }

	
	}
*/

	//使用div批量處理不同縣市單獨全選，精簡程式碼
	function firstColumnCheckboxDoToggleAllHandler(e){
		e = e || window.event;	//另div的onclick觸發function能接到e
		var that = e.target ? e.target:e.srcElement;	//that即為有做動作的節點
	
		if((that.tagName === 'INPUT'||that.tagName === 'input') && that.className === 'countyClass'){
			//↑↑判斷tag為<input>且class="countyClass"的節點
			var isChecked = that.checked;	//當前勾選狀態，全選為true，取消全選為false
            var nextTDNode = that.parentNode.parentNode.nextSibling;
            //被勾選的checkBox上一層再上一層的下一個節點(<input>↑<font>↑<td>→<td>)
            var TRNode = nextTDNode.parentNode;	//再上一層找到<TR>(<td>↑<tr>)
            var secondTD = TRNode.getElementsByTagName('td')[1];	//下一層的第二個<td>(<tr>↓<td>[0],<td>[1])
			var checkBoxsInSecondTD = secondTD.getElementsByTagName('input');	//取得<td>底下的<input>tag

			if(isChecked){
				for(var d=0;d<checkBoxsInSecondTD.length;d+=1){
					checkBoxsInSecondTD[d].checked = true;
				}
			}else{
				for(var d=0;d<checkBoxsInSecondTD.length;d+=1){
					checkBoxsInSecondTD[d].checked = false;
				}
			}
		}
	  }


	function topButtonDoToggleAllHandler(e,index){
		e = e || window.event;	//另div的onclick觸發function能接到e
		var that = e.target ? e.target:e.srcElement;	//that即為有做動作的節點

		if((that.tagName === 'INPUT'||that.tagName === 'input') && that.className === 'toolbar_default'){
			//↑↑判斷tag為<input>且class="countyClass"的節點
            var nextTDNode = that.parentNode.parentNode.nextSibling;
            //被勾選的checkBox上一層再上一層的下一個節點(<input>↑<font>↑<td>→<td>)
            var TRNode = nextTDNode.parentNode;	//再上一層找到<TR>(<td>↑<tr>)
            var checkBoxsInSecondTD = TRNode.getElementsByTagName('input');	//取得<td>底下的<input>tag

            if(index == 1){
	            for(var d=0;d<checkBoxsInSecondTD.length;d+=1){
					checkBoxsInSecondTD[d].checked = true;
				}
            }
            if(index == 2){
	            for(var d=0;d<checkBoxsInSecondTD.length;d+=1){
					checkBoxsInSecondTD[d].checked = false;
				}
            }
		}
	}
</script>
</body>
</html>
