<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:useBean id="user" scope="session" class="util.User"/>
<jsp:useBean id="obj" scope="request" class="sys.ap.SYSAP003F_AUTH">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
if (user.getIsAdminManager().equals("Y")) {
	if (obj.getState().equals("submit")) obj.updateAuth();
%>
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title></title>
<link rel="stylesheet" href="../../js/default.css" type="text/css" />
<link rel="stylesheet" href="../../js/dtree.css" type="text/css" />
<style>
.bg { 
  background-color: #FBFFFD;
  border: 1px solid #EDEDED;
  color: #000000;
  cursor: default;
  left: 0px;
  margin: 1px;
  padding: 2px 6px 0px 6px;
  top: 0px;
  width:175px;
  height:400px;
 }


.root{
  background-color: #FFFFFF;
}

.folder{
  background-color: #FFFFFF;
}

.program{
  background-color: #BBBBFF;
  font-size: 12px;
}

.query{
  background-color: #AAFFAA;
  font-size: 12px;
  
}

.maintain{
  background-color: #FFAAAA;
  font-size: 12px;

}

.showAll{
  background-color: #FFFFF;
  font-size: 12px;

}

</style>
<script language="javascript" src="../../js/dtree.js"></script>
<script language="javascript">


function checkListIsSelect(buttonName){
	var alertStr="";	
	if(buttonName=="add" && !d.hasElementChecked()){
		alertStr += "[你必須先選擇左邊功能選單某一節點或多重節點]";
	}
	if(buttonName=="remove" && !s.hasElementChecked){
		alertStr += "[你必須先選擇右邊功能選單某一節點或多重節點]";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }

	if(buttonName=="add"){
		document.all("optype").value = "add";
	}else{
		document.all("optype").value = "remove";
	}
	form1.state.value = "submit";
	//return false;
}

function checkURL(surl){
	form1.action=surl;
	form1.submit();
}

function init() {
	var msg = "<%=obj.getErrorMsg()%>";
	if (msg!="") alert(msg);
}
</script>
</head>

<body onload="init();">
<form id="form1" name="form1" method="post" action="sysap003f_auth.jsp">
<input type="hidden" name="groupID" value="<%=obj.getGroupID()%>">
<input type="hidden" name="groupName" value="<%=obj.getGroupName()%>">
<input type="hidden" name="q_groupID" value="<%=obj.getQ_groupID()%>">
<input type="hidden" name="q_groupName" value="<%=obj.getQ_groupName()%>">
<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
<input type="hidden" name="state" value="queryOne">	
<input type="hidden" name="optype">			
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2" width="100"><a href="#" onClick="return checkURL('sysap002f.jsp');">群組資料</a></td>		
		<td ID=t2 CLASS="tab_border1" width="100" HEIGHT="25">群組權限</td>		
		<td>&nbsp</td>
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>	
		<td class="tab_line2"></td>	
	</tr>
</TABLE>
<!--end Tabl-->

<table border="1" width="100%"><tr><td>
    <table class="table_form" width="100%" height="25">
    <tr>
        <td class="td_form" width="80">群組代碼：</td>
        <td class="td_form_white">
            <%=obj.getGroupID()%>
        </td>       
        <td class="td_form" width="80">群組名稱：</td>
        <td class="td_form_white">
            <%=obj.getGroupName()%>
        </td>
    </tr>
    </table>
</td></tr>
<tr><td>
<table border="1" width="100%">
	<tr>
	    <td valign="top" style="width:280">
		<div id="d" class="dtree">
			<a href="javascript: d.openAll();">open all</a> | <a href="javascript: d.closeAll();">close all</a><br /><br />
		
			<script type="text/javascript" language="javascript">
				<!--
				var d = new dTree('d','../../images/');
				d.config.folderLinks=true;
				d.config.useCookies=false;
				d.config.useIcons = false;				
				d.config.objForm =  document.form1;
				d.config.checkboxPrefix = "d";
				<%=obj.buildCheckBoxTree("d","請選擇要授權的項目","auth","d","d.checkBoxClick",obj.getGroupID(),true,false,false)%>		
				document.write(d);			
				//-->		
			</script>
		
		</div>
		</td>
		
		<td style="width:100" valign="top">
		    <table width="100%" border="0">
		         <tr>
		        	 <td align="center" valign="middle" width="90">
		        	 	<table width="100%" border="0">
		        	 	<tr>
							<td><input type="radio" name="authoritytype" value="Q" ></td>
							<td><span class="query"><span class="query">查詢權限</span></span></td>
						</tr>
		        	 	<tr>
							<td><input type="radio" name="authoritytype" value="M" checked="true"></td>
							<td><span class="maintain">維護權限</span></td>
						</tr>
						<!-- 
		        	 	<tr>
							<td></td><td><span class="program">可用程式</span></td>
						</tr>
						 -->
						</table>
						<br>
						<input class="toolbar_default" type="submit" name="add"    value="   > >   " onClick="return checkListIsSelect(this.name)">
		        	 	<br>
						<br>
						<br>
						<input class="toolbar_default" type="submit" name="remove" value="   < <   " onClick="return checkListIsSelect(this.name)">
		        	 	<br>
						<br>
						<br>
		        	 </td>
		         
		         </tr>
		    </table>    
		</td>
		
		<td valign="top" style="width:280">
		<div id="s" class="dtree">
			<a href="javascript: s.openAll();">open all</a> | <a href="javascript: s.closeAll();">close all</a><br /><br />
		
			<script type="text/javascript" language="javascript">
				<!--
				var s = new dTree('s','../../images/');
				s.config.folderLinks=true;
				s.config.useCookies=false;
				s.config.useIcons = false;
				s.config.objForm =  document.form1;
				s.config.checkboxPrefix = "s";					
				s.config.checkParent = false;				
				<%
				out.write(obj.buildCheckBoxTree("s","已授權項目","authed","s","s.checkBoxClick",obj.getGroupID(),false,false,false));
				%>		
				document.write(s);
				//-->		
			</script>
		
		</div>
		&nbsp;
		</td>
	</tr>

</table>
</td></tr></table>
</form>
</body>
</html>
<%
}
%>

