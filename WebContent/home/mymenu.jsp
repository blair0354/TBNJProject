<%@ page contentType="text/html;charset=utf-8" %>
<jsp:useBean id="user" scope="session" class="util.User"/>
<jsp:useBean id="obj" scope="request" class="sys.ap.SYSAP003F_AUTH">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Menu</title>
<link rel="stylesheet" href="../js/dtree.css" type="text/css"></link>
<link rel="stylesheet" href="../js/default.css" type="text/css"></link>
<script type="text/javascript" src="../js/dtree.js"></script>
<style>		
	.treeNode{color:#0000FF;font-size:13;font-family:Arial,新細明體,細明體;}
	.buttonStyle{
		width:160;
		FONT-SIZE: 12px;
	    COLOR: #333333;
		BORDER-TOP-STYLE: groove;
		FONT-FAMILY: "Verdana";
		BORDER-RIGHT-STYLE: groove;
		BORDER-LEFT-STYLE: groove;
		BACKGROUND-COLOR: lightyellow;
		BORDER-BOTTOM-STYLE: groove
		border-top-width: 1px;
		border-right-width: 1px;
		border-bottom-width: 1px;
		border-left-width: 1px
		text-decoration: none;
		border-style: solid;
		border-color: #CCCCCC #666666 #666666 #CCCCCC; 
	}
	
	body {
		font-family: 新細明體, 細明體, Verdana, Geneva, Arial, Helvetica, sans-serif;
		font-size: 12px;
		margin-top: 0px;
		margin-left: 0px;
		margin-right: 0px;
		margin-bottom: 0px;
	}		
</style>

</head>

<body class="background3" topmargin="0" leftmargin="0" >


<table border="0" width="100%" cellspacing="0" cellpadding="0">
<tr><td valign="top" style="padding-top:5px;">
<div class="dtree">
	<script type="text/javascript">
		<!--
		d = new dTree('d');
		d.config.target = 'mainframe';
		d.config.folderLinks=false;
		d.config.useCookies=false;		
		d.config.objPath = top.fbody.mainhead.document.getElementById("pathname");//top.fbody.mainhead.document.all.item("pathname");
		<%
		if (user.getIsAdminManager().equals("Y") && user.getIsManager().equals("Y")) {
			out.write(obj.buildAuthorizeMenu("d","功能選單",user.getGroupID(),"Y"));
		} else {
			out.write(obj.buildAuthorizeMenu("d","功能選單",user.getGroupID(),"N"));
		}			
		%>		
		document.write(d);
		//-->
	</script>

</div>

</td></tr>
</table>
</body>
</html>
