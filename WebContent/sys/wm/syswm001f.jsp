<!--
程式目的：網站訊息管理作業
程式代號：syswm001f
程式日期：0941019
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.wm.SYSWM001F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.wm.SYSWM001F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
}
%>

<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("newsCat", "1"),
	new Array("newsDateS", getToday()),
	new Array("newsDateE", "<%=util.Datetime.getDateAdd("m",1,util.Datetime.getYYYMMDD())%>")
);
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="update"){
		alertStr += checkEmpty(form1.newsCat,"訊息類別");
		alertStr += checkEmpty(form1.newsSubject,"主旨");
		alertStr += checkEmpty(form1.newsContent,"內容");
		alertStr += checkEmpty(form1.newsDateS,"上檔日期");
		alertStr += checkEmpty(form1.newsDateE,"下檔日期");
		alertStr += checkDateSE(form1.newsDateS, form1.newsDateE, "上下檔日期");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(newsID){
	form1.newsID.value=newsID;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init() {
	for (var i=1; i<11; i++) {
		if (parse(document.all.item("itemPicture"+i).value).length>0) setFormItem("btn_itemPicture"+i+"Download","O");
	}
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:300px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">訊息類別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_newsCat">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='MSG' ", obj.getQ_newsCat())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">訊息編號：</td>
		<td class="queryTDInput">
			起：<input class="field_Q" type="text" name="q_newsIDS" size="10" maxlength="10" value="<%=obj.getQ_newsIDS()%>">
			~訖：<input class="field_Q" type="text" name="q_newsIDE" size="10" maxlength="10" value="<%=obj.getQ_newsIDE()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">主旨：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_newsSubject" size="25" maxlength="255" value="<%=obj.getQ_newsSubject()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">內容：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_newsContent" size="25" maxlength="100" value="<%=obj.getQ_newsContent()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">上下檔日期：</td>
		<td class="queryTDInput">
			起：<%=util.View.getPopCalndar("field_Q","q_newsDateS",obj.getQ_newsDateS())%>
			~訖：<%=util.View.getPopCalndar("field_Q","q_newsDateE",obj.getQ_newsDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">訊息類別：</td>
		<td class="td_form_white">
			<select class="field" type="select" name="newsCat">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='MSG' ", obj.getNewsCat())%>
			</select>
		</td>
		<td class="td_form">訊息編號：</td>
		<td class="td_form_white">
			[<input class="field_PRO" type="text" name="newsID" size="10" maxlength="10" value="<%=obj.getNewsID()%>"> ]
		</td>
	</tr>
	<tr>
	  <td class="td_form">主旨：</td>
	  <td class="td_form_white"><input class="field" type="text" name="newsSubject" size="35" maxlength="255" value="<%=obj.getNewsSubject()%>"></td>
	  <td class="td_form">HTML註記：</td>
	  <td class="td_form_white"><input class="field" type="checkbox" name="isHTML" value="Y" <%=obj.getIsHTML()%>>
      </td>
	</tr>
	<tr>
      <td class="td_form">上檔日期：</td>
      <td class="td_form_white"><%=util.View.getPopCalndar("field","newsDateS",obj.getNewsDateS())%> </td>
      <td class="td_form">下檔日期：</td>
      <td class="td_form_white"><%=util.View.getPopCalndar("field","newsDateE",obj.getNewsDateE())%> </td>
	  </tr>
	<tr>
		<td class="td_form">內容：</td>
		<td colspan="3" class="td_form_white">
			<textarea class="field" name="newsContent" cols="60" rows="4"><%=obj.getNewsContent()%></textarea>
		</td>
		</tr>
	<tr>
      <td class="td_form">附件1：</td>
      <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture1",obj.getItemPicture1())%></td>
	  </tr>
	<tr>
      <td class="td_form">附件2：</td>
      <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture2",obj.getItemPicture2())%></td>
	  </tr>
	<tr>
      <td class="td_form">附件3：</td>
      <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture3",obj.getItemPicture3())%></td>
	  </tr>
	<tr>
      <td class="td_form">附件4：</td>
      <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture4",obj.getItemPicture4())%></td>
	  </tr>
	<tr>
      <td class="td_form">附件5：</td>
      <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture5",obj.getItemPicture5())%></td>
	  </tr>
	<tr>
      <td class="td_form">附件6：</td>
      <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture6",obj.getItemPicture6())%></td>
	  </tr>
	<tr>
      <td class="td_form">附件7：</td>
      <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture7",obj.getItemPicture7())%></td>
	  </tr>
	<tr>
      <td class="td_form">附件8：</td>
      <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture8",obj.getItemPicture8())%></td>
	  </tr>
	<tr>
      <td class="td_form">附件9：</td>
      <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture9",obj.getItemPicture9())%></td>
	  </tr>
	<tr>
      <td class="td_form">附件10：</td>
      <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture10",obj.getItemPicture10())%></td>
	  </tr>	  
	<tr>		
	<tr>	
	  <td class="td_form">異動人員/日期：</td>
	  <td colspan="3" class="td_form_white">[
	    <input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
	    /
        <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">
        ]</td>
	  </tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="filestoreLocation" value="<%=application.getInitParameter("filestoreLocation")%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">訊息編號</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">主旨</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">上檔日期</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">下檔日期</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">異動人員</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,false,false,false};
	boolean displayArray[] = {true,true,true,true,true};
	String	alignArray[]   = {"center","left","center","center","center","center"};	
	out.write(util.View.getQuerylist(primaryArray,displayArray,alignArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>



