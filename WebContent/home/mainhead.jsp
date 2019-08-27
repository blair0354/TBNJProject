<%@ page contentType="text/html;charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<style>
.bg { 
	font-size: 12px;
	background-color: lightyellow;
	border: 1px solid #EDEDED;
	color: darkred;
	cursor: default;
	left: 0px;
	margin: 1px;
	padding: 2px 6px 0px 6px;
	top: 0px;
	width:100%;
	height:20px;
}
</style>
</head>
<body topmargin="5" leftmargin="0" rightmargin="20" rightmargin="5">

<div class="bg" valign="middle" id="direction">
現在位置 ：<span id="pathname"></span>
</div>
<script language="javascript">
try {
	//top.body.menu.d.config.objPath = document.getElementById("pathname");
	top.fbody.menu.d.config.objPath = document.getElementById("pathname");
}catch(e) {}
</script>

</body>
</html>