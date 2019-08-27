<!--
程式目的：申請用途案件統計量表
程式代號：eform3_6
程式日期：1040730
程式作者：robin
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp" %>
<jsp:useBean id="obj" scope="request" class="lca.ap.LCAAP170F">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList" />
<%
	ServletContext context = getServletContext();
	String filestoreLocation = context.getInitParameter("filestoreLocation");
	String reportLocation = context.getInitParameter("reportLocation");
	String uip = request.getRemoteAddr(); 
	String unit = (String)session.getAttribute("unitid");
	String city = unit.substring(0,1);
	obj.setContext(context);
	if("init".equals(obj.getState())){
		obj.setQry_date_start(Datetime.getYYYMMDD());
		obj.setQry_date_end(Datetime.getYYYMMDD());
	}
	if("clsQry".equals(obj.getState())){
		if(unit.substring(1,2).equals("0")){
			obj.setTxtunit("");
			if(unit.equals("00")){
				obj.setTxtcity_no("");
			}
		}
		obj.setState("init");
		objList=new ArrayList();
	}
	String fileName="";
	if("genRP".equals(obj.getState())){
		fileName = obj.genReport();
		obj=new lca.ap.LCAAP170F();
		objList=new ArrayList();
		obj.setState("init");
		obj.setQry_date_start(Datetime.getYYYMMDD());
		obj.setQry_date_end(Datetime.getYYYMMDD());
	}
	
	String citySQL = "";
	if(!unit.equals("00")){
		citySQL = " and KCDE_2='"+city+"' ";
		obj.setTxtcity_no(city);
	}
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
<script type="text/javascript" src="js/tablesoft.js"></script>
<script type="text/javascript" src="js/cbToggle.js"></script>
<script type="text/javascript">
function clearLimit(){
   	form1.state.value="clsQry";
	form1.submit();
}
   
function fldchk()
{
  	var errmsg = "";
  	errmsg += checkEmpty(form1.qry_date_start,"查詢日期：起");
  	errmsg += checkDate(form1.qry_date_start,"查詢日期：起");
  	errmsg += checkEmpty(form1.qry_date_end,"查詢日期：迄");
  	errmsg += checkDate(form1.qry_date_end,"查詢日期：迄");
  	
	if (errmsg!=""){
		alert(errmsg)
	}else{
	 	//alert("報表產製中");
	 	form1.state.value="genRP";
		document.getElementById("txtcity_no").disabled=false;
		document.getElementById("txtunit").disabled=false;
	 	form1.submit();	
	} 
}

function changeCityUnit1(signNo1,signNo2,selectValue){
	var queryValue=document.getElementById(signNo1).value;	
	if (isObj(document.getElementById(signNo2))) {	
		var obj2 = document.getElementById(signNo2);
		obj2.options.length=0;
		var obj2Option = document.createElement("Option");
		obj2.options.add(obj2Option);
		obj2Option.innerText = "請選擇";
		obj2Option.value = "";	
		var xmlDoc;
		var xmlDoc=document.createElement("xml");	
		xmlDoc.async=false;
		if(xmlDoc.load("home/xmlCityUnit1.jsp?county="+queryValue)){		
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(i=0; i<nodesLen; i++){
				codeid=xmlDoc.documentElement.childNodes.item(i).getAttribute("codeid");
				codename=xmlDoc.documentElement.childNodes.item(i).getAttribute("codename");
				var oOption = document.createElement("Option");	
				obj2.options.add(oOption);
				oOption.innerText =codeid+"-"+ codename;
				oOption.value = codeid;		
 				if(codeid == selectValue){
         			oOption.selected=true;
  				}           										
			}
		}
	}
}

function init(){
	var unit = "<%=unit %>";
	if(unit!="00"){
		document.getElementById("txtcity_no").disabled=true;
		if(unit.indexOf("0")<0){
			document.getElementById("txtunit").value = unit;
			document.getElementById("txtunit").disabled=true;
		}
	}
	
	var fileName = "<%=fileName %>";
	if(fileName!=""){
		var prop='';
	    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=no,scrollbars=yes,resizable=yes,';
	    prop=prop+'width=450,';
	    prop=prop+'height=160';
		window.open("home/fileDownload.jsp?fileID2="+fileName,"下載檔案",prop);
	}
}

</script>
</head>

<body topmargin="0" background="images/main_bg.jpg"	onLoad="showErrorMsg('<%=obj.getErrorMsg()%>');init()">
<form id="form1" name="form1" method="post"	onSubmit="">
<table border="0" width=660 cellspacing="0" cellpadding="0">
	<tr align="center" valign="middle">
		<td colspan="3">
			<font size="4" face="標楷體" color="#FF9900">申請用途案件統計量表</font>
			<hr size="1" color="#008000" width="660" align="left">
		</td>
	</tr>
	<tr>
	    <%if(obj.getState().equals("init")){%>
			<td width="210">查詢縣市： 
				<select style="background:ffffcc" type="select" id='txtcity_no' name="txtcity_no" onChange="changeCityUnit1('txtcity_no','txtunit','');">
					<%=util.View.getOption("select KCDE_2,(KCDE_2+'-'+KCNT) from RKEYN where KCDE_1='45' "+citySQL+" order by KCDE_2",obj.getTxtcity_no()) %>
				</select>
			</td>
			<td width="291">&nbsp;事務所： 
				<select name="txtunit" id='txtunit' style="background:ffffcc">
					<script>changeCityUnit1('txtcity_no','txtunit','<%=obj.getTxtunit()%>');</script>
				</select>
			</td>
		<%}else{%>
			<td width="210">查詢縣市： 
				<select class="field" type="select" id='txtcity_no' name="txtcity_no" onfocus="defaultIndex=this.selectedIndex" onChange="changeCityUnit1('txtcity_no','txtunit','');">
					<%=util.View.getOption("select kcde_2,kcde_2+'-'+kcnt as kcnt from rkeyn where kcde_1='45' and left(kcde_2,2)<>'/*' and left(kcde_2,2)<>'20' and left(kcde_2,2)<>'- ' and left(krmk,2)<>'00' "+citySQL+" order by krmk",obj.getTxtcity_no()) %>
				</select>
			</td>
			<td width="291">&nbsp;事務所： 
				<select name="txtunit" id='txtunit' style="background:ffffcc" onfocus="defaultIndex=this.selectedIndex" onChange="this.selectedIndex=defaultIndex;">
					<script>changeCityUnit('txtcity_no','txtunit','<%=obj.getTxtunit()%>');</script>
				</select>
			</td>
		<%}%>
		<td width="160" align="center" bgcolor="#FF00FF"><font color="#FFFFFF">申請用途案件統計量表</font></td>
	</tr>
	<tr>
		<td colspan="2">查詢狀態： 
			<select name=txtqrymsg style="background:ffffcc">
				<option value=""  selected>全部</option>
				<option value="已完成查詢">已完成查詢</option>
				<option value="尚未完成查詢">尚未完成查詢</option>
			</select>
		</td>	
		<td align="center" >
			<font color="#333333" size="2">
			<input type=button value="製表" class=Button onClick="javascript:fldchk();" id=button1 name=button1> &nbsp; 
			<INPUT type="button" name="button2" tabIndex="4" title="清除" value="清除"  onClick="javascript:clearLimit();"/>
			</font>
		</td>
	</tr>
	<tr>
		<td colspan="3"><font color="red">*</font>查詢日期：起 <%=util.View.getPopCalndar("field","qry_date_start", obj.getQry_date_start())%>~迄<%=util.View.getPopCalndar("field","qry_date_end", obj.getQry_date_end())%></td>
	</tr>
    <input type="hidden" name="state">
    <input type="hidden" name="filestoreLocation" value="<%=filestoreLocation%>">	
    <input type="hidden" name="reportLocation" value="<%=reportLocation%>">	
    <input type="hidden" name="uip" value="<%=uip%>">
    <input type="hidden" name="userID" value="<%=session.getAttribute("uid")%>">
    <input type="hidden" name="unitID" value="<%=session.getAttribute("unitid")%>">
    <input type="hidden" name="print_type" value="1">
</table>
<hr size="1" color="#008000" width="660" align="left">
</form>
</body>
</html>
