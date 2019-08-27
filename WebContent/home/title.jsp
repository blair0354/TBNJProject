<%@ page contentType="text/html;charset=utf-8" %>
<%@ include file="head.jsp" %>
<%
String css = " style=\"background:blue;color:white;\"";
String css1 = "";
%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../js/default.css" type="text/css">
<style>
body {
	margin-top: 0px;
	margin-left: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

div.titleBar {
	font-family: "新細明體"; 
	font-size: 12px;
	background-color: #EDEDED;
	border-top:1px solid #000000;
	border-bottom:1px solid #000000;
	padding: 4px 8px 2px 8px;
	text-align: left;
}

a.titleBarButton {
	font-family: transparent; 
	font-size: transparent;
	background-color: transparent;
	border: 1px solid #EDEDED;
	color: #000000;
	padding: 2px 6px 0px 6px;
	text-decoration: none;
}

a.titleBarButton:hover {
	font-family: transparent; 
	font-size: transparent;
	background-color: transparent;
	border: 1px outset #EDEDED;
	color: #000000;
	padding: 2px 6px 0px 6px;
	text-decoration: none;
}

div#topbar{
	position:absolute;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	width: 550px;
	height: 70px;
	visibility: hidden;
	z-index: 100;
	overflow:auto;
}
#Layer1 {
	position:absolute;
	width:71px;
	height:34px;
	z-index:101;
	left: 846px;
	top: 13px;
}
</style>
<script type="text/javascript" src="../js/popup.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript">
var boxMsgFlag = false;
var broadcastMsg = "";

function isObj(obj){
	return (!((obj==null)||(obj==undefined)));
}

function titleBarButtonClick(thisobj){
	for(var i=0; i<document.links.length; i++){  
		obj = document.links[i];
		if (obj.className=="titleBarButton"){
			obj.style.background="#EDEDED";
			obj.style.color="#000000";
		}
	}
	thisobj.style.background="blue";	
	thisobj.style.color="#FFFFFF";
}

function openPersonalWindow(){
	var actItem = "b1";
	for(var i=0; i<document.links.length; i++){  
		var obj = document.links[i];
		if (obj.className=="titleBarButton" && obj.style.background=="blue"){
			actItem = obj.id;
		}
	}	
	var prop='';
	prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=no,scrollbars=yes,resizable=yes,';
	prop=prop+'width=750,';
	prop=prop+'height=390';
	window.open('../sys/ap/sysap001f_s.jsp?actItem='+actItem,'個人基本資料維護',prop);
}

function sessionTimeout() {
	if ("<%=user.getUserID()%>"=="") {
		var prop='';
		prop='scroll:yes;status:no;help:no;dialogWidth:300px;dialogHeight:120px';
		window.showModalDialog('sessionTimeout.jsp',window,prop);
			top.top.location.href='../index.jsp';
	} 
}

function showBoxMsg(msg,b) {
	if(msg !=null && msg.length!=0){    		
		//new popUp(760, 1, 200, 70, "boxMsg", msg, "white", "black", "10pt 細明體", "即時訊息", "#C8E8D9", "black", "#EFF9FF", "gray", "#EFF9FF", true, true, true, true, true);
		if (boxMsgFlag) {			
			//fadeboxout("boxMsg");
			changecontent("boxMsg",msg);			
			fadeboxin("boxMsg");
		} else {
			//790 , 1 , 200 , 70
			if (b!=null && b==true) new popUp(500 , 1 , 350 , 70 , "boxMsg" , msg, "white" , "black" , "10pt 新細明體" , "即時訊息" , "#C8E8D9" , "black" , "#EFF9FF", "#EFF9FF" , "black" , true , true , true , true , false , false , '../images/min.gif' , '../images/max.gif' , '../images/close.gif' , '../images/resize.gif');
			else new popUp(790 , 1 , 200 , 70 , "boxMsg" , msg, "white" , "black" , "10pt 新細明體" , "即時訊息" , "#C8E8D9" , "black" , "#EFF9FF", "#EFF9FF" , "black" , true , true , true , true , false , false , '../images/min.gif' , '../images/max.gif' , '../images/close.gif' , '../images/resize.gif');
			boxMsgFlag = true;
		}
	} else {
		if (boxMsgFlag) { 
			//hidebox("boxMsg");
			fadeboxout("boxMsg");
		}
	}
}

function sessionDestroyed() {	
	//getRemoteData('sessionDestroyed.jsp');
	//onunload="sessionDestroyed();" 
}

function BroadCastMsg() {
	var x = getRemoteData('msgBroadCast.jsp');	
	if (x!=null && x.length!=0) {
		if (x!=broadcastMsg) {
			showBoxMsg("<font color='red'>"+x+"</font>",true);
			broadcastMsg = x;
		}
	}
	window.setTimeout("BroadCastMsg()", 1000);
}
/**
	<a id="b2" class="titleBarButton" target="fbody" href="http://law.moj.gov.tw/" onClick="titleBarButtonClick(this);">法&nbsp;規&nbsp;查&nbsp;詢</a>&nbsp;|&nbsp;	
**/
</script>
</HEAD>
<body onLoad="BroadCastMsg();" onbeforeunload="sessionDestroyed();">
<form>
<!-- ImageReady Slices (Top.psd) -->
<TABLE WIDTH="100%" BORDER=0 CELLPADDING=0 CELLSPACING=0>
	<TR>
		<TD>
			<table border="0" width="100%" id="table1" cellpadding="0" style="border-collapse: collapse">
				<tr>
					<td>
			<IMG SRC="../images/Top_01.gif" WIDTH=245 HEIGHT=91 ALT=""></td>
					<td>
			<IMG SRC="../images/Top_02.gif" WIDTH=483 HEIGHT=91 ALT=""></td>
					<td width="100%" background="../images/Top_03.gif">
					<p align="right"><span class="text_link_b" >[<%=user.getUserName()+" | "+user.getOrganName()+" | "+user.getUnitID()%>]&nbsp;
			[<a class="text_link_b" href="logout.jsp" target="_top">登出</a>]</span></td>
				</tr>
			</table>
		</TD>
	</TR>
	<TR>
		<TD background="../images/Top_04.gif">
			<IMG SRC="../images/Top_04.gif" WIDTH=1010 HEIGHT=8 ALT=""></TD>
	</TR>
	<TR>
		<TD background="../images/Top_05.gif">
			<table border="0" width="100%" id="table2" cellpadding="3" style="border-collapse: collapse">
				<tr>
					<td>　</td>
					<td></td>
				</tr>
			</table>
		</TD>
	</TR>
</TABLE>
<!-- End ImageReady Slices -->
</form>
</BODY>
</HTML>