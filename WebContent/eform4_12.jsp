<!--
程式目的：lcaap010f
程式代號：lcaap010f
程式日期：0970512
程式作者：Richard Li
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp" %>
<jsp:useBean id="obj" scope="request" class="lca.ap.LCAAP120F">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList" />
<%
	ServletContext context = getServletContext();
	String filestoreLocation = context.getInitParameter("filestoreLocation"); 
	String reportLocation = context.getInitParameter("reportLocation");
	String uip = request.getRemoteAddr(); 
	obj.setContext(context);
	if("query".equals(obj.getState())){ 
		objList=obj.queryNameList();
	}
	if("clsQry".equals(obj.getState())){
		obj.setTxtcity_no("");
		obj.setTxtunit("");
		obj.setTxtqry_no("");
		obj.setTxtqry_name("");
		obj.setItemPicture1("");
		obj.setItemPicture2("");
		obj.setState("init");
		objList=new ArrayList();
	}
	if("genRP".equals(obj.getState())){
		obj.genReport();
		obj=new lca.ap.LCAAP120F();
		obj.setItemPicture1("");
		obj.setItemPicture2("");
		objList=new ArrayList();
		obj.setState("init");
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
function chkUnitID(){
	if(form1.unitID.value != "00"){
		alert("此作業只提供內政部地政司使用");
		top.location.reload();
	}
}

function NamesList(){
    if(form1.state.value=="query"){
    	alert("目前已經是完成查詢，如需重新查詢請先執行重新查詢");
    }
    var i=0;
    if(form1.txtqry_no.value==""){
    	i++;
    }
    if(form1.txtqry_name.value==""){
    	i++;
    }
    if(form1.itemPicture1.value==""){
    	i++;
    }
    if(form1.itemPicture2.value==""){
    	i++;
    }
    if(i==4){
        alert("請輸入產製條件");
    	return;
    }else if(i<3){
    	alert("權利人查詢方式僅能輸一種條件\n(統一編號、姓名、統一編號多筆查詢、姓名多筆查詢)，\n請重新輸入!!");
    	return;
    }
	form1.state.value="query";
	form1.submit();

}

function clearLimit(){
   	form1.state.value="clsQry";
	form1.submit();
}
   
function fldchk()
{
  	var errmsg = "";
	if((form1.unitID.value!="00") && (form1.userID.value!="ADMIN") ){
  		if(document.form1.txtrcv_no.value=="" || document.form1.txtrcv_word.value=="" || document.form1.txtrcv_no.value=="")
     		errmsg = errmsg + "請輸入收件年字號!\n"
  		if((!document.form1.txtqry_purpose01.checked)&&(!document.form1.txtqry_purpose02.checked )&&(!document.form1.txtqry_purpose03.checked))
     		errmsg = errmsg + "請選擇申請用途!\n"	 
  		if((document.form1.txtqry_purpose03.checked )&&(document.form1.txtqry_purpose03a.value=="" ))
     		errmsg = errmsg + "請輸入其他申請用途說明!\n"	 
  		if(document.form1.txtqry_oper.value=="" )
     		errmsg = errmsg + "請輸入列印操作人員!\n"	 	 
	}
    if (isObj(cb)) {
		if (cb.AnyChecked()==false) errmsg += "您尚未勾選任何資料欄位！\n";
	}
	if (errmsg!=""){
		alert(errmsg)
	}else{
	 	alert("報表產製中");
	 	form1.state.value="genRP";
	 	form1.submit();	
	} 
}

//上傳檔案視窗(與共用模組顯示文字不同)
function openUploadWindowCount(popFileID, popFileName){
	if (isObj(eval("form1."+popFileID))) {
	    var prop='';
	    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=no,scrollbars=yes,resizable=yes,';
	    prop=prop+'width=450,';
	    prop=prop+'height=160';
	    window.open('home/popUploadCount.jsp?popFileID=' + popFileID + '&popFileName='+popFileName,'上傳檔案',prop);
	} else {
		alert("欄位不存在,請檢查!");
		return ;
	}
}

</script>
</head>

<body topmargin="0" background="images/main_bg.jpg"	onLoad="chkUnitID();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post"	onSubmit="return checkField()">
<table border="0" width=660 cellspacing="0" cellpadding="0">
	<tr align="center" valign="middle">
		<td colspan="3"><font size="4" face="標楷體" color="#FF9900">勞保局建物歸戶清冊</font>
		<hr size="1" color="#008000" width="660" align="left">
		</td>
	</tr>
	<tr>
	    <%if(obj.getState().equals("init")){%>
		<td width="210">查詢縣市： <select class="field" type="select" id='txtcity_no' name="txtcity_no" onChange="changeCityUnit('txtcity_no','txtunit','');">
			<%=util.View.getOption("select kcde_2,kcde_2+'-'+kcnt as kcnt from rkeyn where kcde_1='45' and left(kcde_2,2)<>'/*' and left(kcde_2,2)<>'20' and left(kcde_2,2)<>'- ' and left(krmk,2)<>'00' order by krmk",obj.getTxtcity_no()) %>
			</select></td>
		<td width="291">&nbsp;事務所： <select name="txtunit" id='txtunit' style="background:ffffcc">
				<script>changeCityUnit('txtcity_no','txtunit','<%=obj.getTxtunit()%>');</script>
		</select></td>
		<%}else{%>
		<td width="210">查詢縣市： <select class="field" type="select" id='txtcity_no' name="txtcity_no" onfocus="defaultIndex=this.selectedIndex" onChange="this.selectedIndex=defaultIndex;">
			<%=util.View.getOption("select kcde_2,kcde_2+'-'+kcnt as kcnt from rkeyn where kcde_1='45' and left(kcde_2,2)<>'/*' and left(kcde_2,2)<>'20' and left(kcde_2,2)<>'- ' and left(krmk,2)<>'00' order by krmk",obj.getTxtcity_no()) %>
			</select></td>
		<td width="291">&nbsp;事務所： <select name="txtunit" id='txtunit' style="background:ffffcc" onfocus="defaultIndex=this.selectedIndex" onChange="this.selectedIndex=defaultIndex;">
				<script>changeCityUnit('txtcity_no','txtunit','<%=obj.getTxtunit()%>');</script>
		</select></td>
		<%}%>
		<td width="160" align="center" bgcolor="#FF00FF"><font color="#FFFFFF">勞保歸戶清冊</font></td>
	</tr>
	<tr>
		<td nowrap>統一編號： <input name="txtqry_no" maxlength="10" size="10" tabindex="2" value="<%=obj.getTxtqry_no()%>"></td>
		<td width="291" nowrap>&nbsp;姓名: <input name="txtqry_name" maxlength="40" size="30" tabindex="2" value="<%=obj.getTxtqry_name()%>"></td>
		<td align="center" nowrap><font color="#333333" size="2"> <input
			type=button value="查詢" class=Button onClick="javascript:NamesList();"
			id=button1 name=button1> &nbsp; <INPUT name="button2"
			type="button" tabIndex="4" title="重新輸入查詢條件" value="重新輸入查詢條件"  onClick="javascript:clearLimit();"/> </font></td>
	</tr>
	<tr>
		<td nowrap colspan="2">統一編號多筆：
			[<input class="field_RO" type="text" name="itemPicture1" size="20" maxlength="300" value="">]
			<input class="field" type="button" name="btn_itemPicture1" onclick="openUploadWindowCount('itemPicture1','');" value="上傳檔案">
			<input class="field" type="button" name="btn_itemPicture1Download" onclick="openDownloadWindow(form1.itemPicture1.value);" value="下載檔案">
		</td>
		<td>
			&nbsp;&nbsp;<font color="red" size="-1">上傳檔案請勿超過1000筆資料</font>
		</td>
	</tr>
	<tr>
		<td nowrap colspan="3">姓名多筆：
			[<input class="field_RO" type="text" name="itemPicture2" size="20" maxlength="300" value="">]
			<input class="field" type="button" name="btn_itemPicture2" onclick="openUploadWindowCount('itemPicture2','');" value="上傳檔案">
			<input class="field" type="button" name="btn_itemPicture2Download" onclick="openDownloadWindow(form1.itemPicture2.value);" value="下載檔案">
		</td>
	</tr>
	<tr>
		<td class="bg" colspan="3">
		<div id="listContainer">
		<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
			<thead id="listTHEAD">
				<tr>
					<th class="listTH" nowrap><a class="text_link_w" ><input type="checkbox" name="cbAll" value="toggleAll" class="field" />全選.</a></th>
					<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">統一編號</a></th>
					<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">姓名</a></th>
					<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">住址</a></th>
					<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">出生日期</a></th>
					
				</tr>
			</thead>
			<tbody id="listTBODY">
				<%
					boolean primaryArray[] = { true, true,true, true };
					boolean displayArray[] = { true, true, true, true};
					String[] alignArray = { "center", "center", "center", "center" };
					out.write(util.View.getCheckboxQuerylist(primaryArray,displayArray,alignArray,objList,obj.getQueryAllFlag(),"fds",null,null,"field"));
				%>
			</tbody>
		</table>
		</div>
		</td>
	</tr>
	<tr>
		<td colspan="3">收件年字號： <input name="txtrcv_yr" maxlength="3"
			size="4" value="<%=obj.getTxtrcv_yr()%>">&nbsp;年&nbsp; <input name="txtrcv_word"
			maxlength="10" size="10" value="<%=obj.getTxtrcv_word() %>">&nbsp;字第&nbsp; <input
			name="txtrcv_no" maxlength="6" size="6">&nbsp;號</td>
	</tr>
	<tr>
		<td colspan="3">申請人統一編號： <input name="txtsno" maxlength="10"
			size="10"> 申請人姓名：<input name="txtsname" maxlength="30"
			size="30"></td>
	</tr>
	<tr>
		<td colspan="3">代理人統一編號： <input name="txtsno1" maxlength="10"
			size="10"> 代理人姓名：<input name="txtsname1" maxlength="30"
			size="30"></td>
	</tr>
	<tr>
		<td colspan="3">申請用途： <input name="txtqry_purpose01"
			type="checkbox" value="1">申辦登記案件使用&nbsp;&nbsp; <input
			name="txtqry_purpose02" type="checkbox" value="1">處理訴訟案件&nbsp;&nbsp;
		<input name="txtqry_purpose03" type="checkbox" value="1">其他 <input
			name="txtqry_purpose03a" maxlength="30" size="30"></td>
	</tr>
	<tr>
		<td colspan="3">報表格式： 
		<input	name="print_type" type="radio" value="2">PDF&nbsp;&nbsp;
		<input name="print_type" type="radio" value="3">文字檔</td>
	</tr>
	<tr>
		<td colspan="3">列印操作人員： <input name="txtqry_oper" maxlength="20"
			size="20"></td>
	</tr>
	<tr>
		<td align="center" colspan="3"><font color="#333333" size="2"> <input
			type=button value="製表" class=Button onClick="javascript:fldchk();"
			id=button1 name=button1> &nbsp; <INPUT name="button2"
			type="button" class=Button value="清除" onClick="javascript:clearLimit();" /> </font></td>
	</tr>
    <input type="hidden" name="state">
    <input type="hidden" name="filestoreLocation" value="<%=filestoreLocation%>">	
    <input type="hidden" name="reportLocation" value="<%=reportLocation%>">	
    <input type="hidden" name="uip" value="<%=uip%>">
    <input type="hidden" name="userID" value="<%=session.getAttribute("uid")%>">
    <input type="hidden" name="unitID" value="<%=session.getAttribute("unitid")%>">
    <input type="hidden" name="fileQuery" value="<%=obj.getFileQuery()%>">
</table>
<hr size="1" color="#008000" width="660" align="left">
</td>
</tr>
</table>
</form>
<script language="javascript">
	var cb = new cbToggle('cb',document.form1,form1.cbAll,'fds');
	cb.config.cssTopLevel = true;
</script>
</body>
</html>
