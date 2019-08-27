
<%@ page contentType="text/html;charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../js/style.css" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript">

function doMax(){
	//top.frames['body'].bodyFrame.cols = '12,0,*';
    //parent.parent.bodyFrame.cols = '5,0,*';
    top.fbody.document.getElementById("bodyFrame").cols = '12,0,*';
    document.getElementById('max').style.display = 'none';
    document.getElementById('normal').style.display = 'inline';    
}

function doNormal(){
    //top.frames['body'].bodyFrame.cols = '12,200,*';
	top.fbody.document.getElementById("bodyFrame").cols = '12,200,*';
    document.getElementById('max').style.display= 'inline';
    document.getElementById('normal').style.display = 'none';
}
</script>
</head>
<body  class="background2" bgcolor="#CAE7EE" topmargin="0" leftmargin="0" style="width:10;height:550;filter:progid:DXImageTransform.Microsoft.Alpha( Opacity=100, FinishOpacity=20, Style=1, StartX=0,  FinishX=100, StartY=0, FinishY=100);" onload="doNormal();">

<table width="100%" cellspacing="0" cellpadding="0" border="0" height="100%">
<tr>
    <td align="left" valign="top">
       <span id="max">
       <a href="#" onclick="doMax()">←</a>
       </span>
       <span id="normal">
       <a href="#" onclick="doNormal()">→</a>
       </span>
    </td>    
</tr>
</table>

</body>
</html>