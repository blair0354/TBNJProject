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
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp" %>
<jsp:useBean id="obj" scope="request" class="lca.ap.LCAAP020F">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList" />
<%
	ServletContext context = getServletContext();
	String filestoreLocation = context.getInitParameter("filestoreLocation");
	String reportLocation = context.getInitParameter("reportLocation");
	if("".equals(obj.getQry_date_start())){
		obj.setQry_date_start(Datetime.getYYYMMDD());
		obj.setQry_date_end(Datetime.getYYYMMDD());
	}
	if("query".equals(obj.getState())){ 
		objList=obj.queryAll();
		obj.setState("");
	}
	if("clean".equals(obj.getState())){ 
		objList=new java.util.ArrayList();
		obj=new lca.ap.LCAAP020F();
		obj.setState("");
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
<script language="javascript">
function fldchk()
{
  var errmsg = "";


	if (errmsg!="")	    
	{
	alert(errmsg)
	}
	else
	{
	   form1.state.value="query";
	   form1.submit();	
	} 
}   

function clean(){
	form1.state.value="clean";
	form1.submit();
}


function queryOne(qry_seq,filename){
    var ss=form1.userID.value;
    if(filename==""){
    	alert("無產製檔案可提供下載");
    }else{
    	var prop='';
	    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=no,scrollbars=yes,resizable=yes,';
	    prop=prop+'width=450,';
	    prop=prop+'height=160';
		window.open('home/fileDownload.jsp?qry_seq=' + qry_seq + '&reportName='+filename+'&uid='+ss,'下載檔案',prop);
	}	
}


function bb()
{
	
		listContainer.style.width="10px";
		listContainer.style.width=600;
	//listContainer.style.overflowX = "auto";
	
	
}
</script>
</head>

<body topmargin="0" background="images/main_bg.jpg"	onload="showErrorMsg('<%=obj.getErrorMsg()%>');bb();" onresize="bb();">
<form id="form1" name="form1" method="post"	onSubmit="return checkField()">
<table border="0" width="600" cellspacing="0" cellpadding="0">
	<tr align="center" valign="middle">
		<td colspan="3"><font size="4" face="標楷體" color="#FF9900">歸戶結果查詢及下載作業</font>
		<hr size="1" color="#008000" width="600" align="left">
		</td>
	</tr>
	<tr>
		<td colspan="3">產製序號： <input name="qry_seq" maxlength="14" size="14" value="<%=obj.getQry_seq()%>"></td>
	</tr>
	<tr>
		<td colspan="3">申請日期：起 <%=util.View.getPopCalndar("field","qry_date_start", obj.getQry_date_start())%>~迄<%=util.View.getPopCalndar("field","qry_date_end", obj.getQry_date_end())%></td>
	</tr>
	<%
		 //if((unitid.equals("00"))&&(userid.equals("ADMIN"))){
		if((unitid.equals("00"))){
	%>
	<tr>
		<td colspan="3">產製人員： <input name="qry_userid" maxlength="13" size="13" value="<%=obj.getQry_userid()%>"></td>
	</tr>
	<%}else{ %>
		<input type="hidden" name="qry_userid" value="<%=userid%>">
	<%} %>
	<tr>
		<td colspan="3">產製種類：<select class="field" type="select" id='qry_flag' name="qry_flag">
			<%=util.View.getTextOption("A;權利人歸戶清冊;B;土地所有權人歸戶清冊;C;建物所有權人歸戶清冊;D;土地他項權利人歸戶清冊;E;建物他項權利人歸戶清冊;F;土地所有權人管理者歸戶清冊;G;建物所有權人管理者歸戶清冊;H;土地他項權利人管理者歸戶清冊;I;建物他項權利人管理者歸戶清冊;J;勞保局土地歸戶清冊;K;勞保局建物歸戶清冊;L;批次歸戶清冊;M;國軍退輔會土地歸戶清冊;N;國軍退輔會建物歸戶清冊",obj.getQry_flag())%>
			</select></td>
	</tr>
	<tr>
		<td colspan="3">產製結果：<select class="field" type="select" id='qry_status' name="qry_status">
			<%=util.View.getTextOption("1;產製中;2;產製完成可下載;3;已下載;4;無符合條件資料;5;中斷服務請重新產製;6;產製失敗",obj.getQry_status())%>
			</select></td>
	</tr>
	<tr>
		<td align="center" colspan="3"><font color="#333333" size="2"> <input type=button value="查詢" class=Button onClick="javascript:fldchk();"
			id=button1 name=button1> &nbsp; <INPUT name="reset"
			type="button" name="button2" tabIndex="4" onClick="javascript:clean();" title="清除" value="清除" /> </font></td>
	</tr>
	
	<tr>
		<td class="bg" colspan="3">
		<div id="listContainer">
		<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
			<thead id="listTHEAD">
				<tr>
					<th class="listTH" nowrap><a class="text_link_w" ><a class="text_link_w" >NO.</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">產製編號</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">申請日期</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">使用者所屬單位</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">使用者名稱</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">產製種類</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',6,false);" href="#">產製結果</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',7,false);" href="#">報表完成日期</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',8,false);" href="#">報表完成時間</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',9,false);" href="#">報表格式</a></th>
					<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',10,false);" href="#">檔案下載日期</a></th>
				</tr>
			</thead>
			<tbody id="listTBODY">
				<%
					boolean primaryArray[] = { true, true,false,false,false,false,false,false,false,false,false,false };
					boolean displayArray[] = { false, false, true, true, true, true, true, true, true, true, true, true};
					String[] alignArray = {"center","center","center","center","center","center","center","center","center","center","center","center"};
					out.write(util.View.getQuerylist(primaryArray,displayArray,alignArray,objList,obj.getQueryAllFlag(),1));
				%>
			</tbody>
		</table>
		</div>
		</td>
	</tr>
   <input type="hidden" name="state">
    <input type="hidden" name="filestoreLocation" value="<%=filestoreLocation%>">	
    <input type="hidden" name="reportLocation" value="<%=reportLocation%>">	
    <input type="hidden" name="userID" value="<%=session.getAttribute("uid")%>">
	
	
	<tr>
		<td colspan="3">
		<hr size="1" color="#008000" width="600" align="left">
		產製編號＝產製種類+產製日期(YYYMMDD)+產製時間(HHMMSS)
		</td>
	</tr>
	<tr>
		<td width=13% valign="top">產製種類：</td>
		<td colspan="2" align="left">
			A=權利人歸戶清冊(EFORM2_2)<br>
			B=土地所有權人歸戶清冊(EFORM4_3)<br>
			C=建物所有權人歸戶清冊(EFORM4_4)<br>
			D=土地他項權利人歸戶清冊(EFORM4_7)<br>
			E=建物他項權利人歸戶清冊(EFORM4_8)<br>
			F=土地所有權人管理者歸戶清冊(EFORM4_5)<br>
			G=建物所有權人管理者歸戶清冊(EFORM4_6)<br>
			H=土地他項權利人管理者歸戶清冊(EFORM4_9)<br>
			I=建物他項權利人管理者歸戶清冊(EFORM4_10)<br>
			J=勞保局土地歸戶清冊(EFORM4_14)<br>
			K=勞保局建物歸戶清冊(EFORM4_14)<br>
			L=批次歸戶(EFORM4_13)<br>
			M=國軍退輔會土地歸戶清冊(EFORM4_15)<br>
			N=國軍退輔會建物歸戶清冊(EFORM4_15)
		</td>
	</tr>
   
</table>
</form>
</body>
</html>
