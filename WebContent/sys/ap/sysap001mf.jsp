
<!--
程式目的：使用者資料管理
程式代號：sysap001f
程式日期：0950321
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ap.SYSAP001F">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<%
if (user.getIsAdminManager().equals("Y")) {
	if ("queryAll".equals(obj.getState())) {
	    if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
	}else if ("queryOne".equals(obj.getState())) {
	    obj = (sys.ap.SYSAP001F)obj.queryOne();    
	}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	    obj.insert();
	}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	    obj.update();
	}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	    obj.delete();
	}
	if ("true".equals(obj.getQueryAllFlag())){
	    objList = obj.queryAll();
	    if(objList.size()>0){
			//System.out.print(obj.getMa_seq());
			if (obj.getEmpID().equals("")){
				String[] tmpobj=(String[])objList.get(0);
				obj.setEmpID(tmpobj[0]);
				obj=obj.queryOne();
			}
		}
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
var insertDefault;  //二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("isStop","N"),
	new Array("isOrganManager","N"),
	new Array("isAdminManager","N"),
	new Array("isManager","N"),
	new Array("isDeptMgr","N"),		
	new Array("organID","<%=user.getOrganID()%>"),
	new Array("organIDName","<%=user.getOrganName()%>")	
);

function checkField(){
    var alertStr="";
    if(form1.state.value=="queryAll"){
        //alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
        alertStr += checkEmpty(form1.empID,"使用者代碼");
        alertStr += checkEmpty(form1.empName,"使用者姓名");
        alertStr += checkEmpty(form1.groupID,"加入群組");
        alertStr += checkEmpty(form1.isStop,"是否停用");
        //alertStr += checkEmpty(form1.isOrganManager,"是否為上層機關");
        alertStr += checkEmpty(form1.isAdminManager,"是否系統管理者");
        //alertStr += checkEmpty(form1.isManager,"是否為財產管理人員");
        alertStr += checkEmpty(form1.organID,"所屬機關");
        //alertStr += checkEmpty(form1.empTel,"電話"); 
        //alertStr += checkDate(form1.takeJobDate,"到職日");  
		if (form1.state.value=="update"||form1.state.value=="updateError") {
			alertStr += checkEmpty(form1.empPWD,"登入密碼");
			alertStr += checkEmpty(form1.empPWD1,"登入密碼確認");
		}		
        if (form1.empPWD.value!=form1.empPWD1.value) {
			alertStr += "登入密碼和登入密碼確認資料不一致，請重新輸入！";
		}
    }
    if(alertStr.length!=0){ alert(alertStr); return false; }
    beforeSubmit();
}

function queryOne(empID){
    form1.empID.value=empID;
    form1.state.value="queryOne";
    beforeSubmit();
    form1.submit();
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:580px;height:200px;display:none">
    <iframe id="queryContainerFrame"></iframe>
    <div class="queryTitle">查詢視窗</div>
    <table class="queryTable"  border="1">
    <tr>
        <td class="queryTDLable">使用者代碼：</td>
        <td class="queryTDInput">
            <input class="field_Q" type="text" name="q_empID" size="10" maxlength="10" value="<%=obj.getQ_empID()%>">
        </td>
        <td class="queryTDLable">是否停用：</td>
        <td class="queryTDInput">
            <select class="field_Q" type="select" name="q_isStop">
            <%=util.View.getYNOption(obj.getQ_isStop())%>
            </select>
        </td>        
    </tr>
    <tr>
        <td class="queryTDLable">使用者姓名：</td>
        <td class="queryTDInput">
            <input class="field_Q" type="text" name="q_empName" size="20" maxlength="20" value="<%=obj.getQ_empName()%>">
        </td>
        <td class="queryTDLable">系統管理者：</td>
        <td class="queryTDInput">
            <select class="field_Q" type="select" name="q_isAdminManager">
            <%=util.View.getYNOption(obj.getQ_isAdminManager())%>
            </select>
        </td>          
    </tr>
    <tr>
        <td class="queryTDLable">所屬機關：</td>
        <td class="queryTDInput">
            <%=util.View.getPopOrgan("field_Q","q_organID",obj.getQ_organID(),obj.getQ_organIDName())%>
        </td>
        <td class="queryTDLable">所屬單位：</td>
        <td class="queryTDInput">
            <input class="field_Q" type="text" name="q_unitID" size="10" maxlength="10" value="<%=obj.getQ_unitID()%>">
        </td>         
    </tr>
	<tr>
        <td class="queryTDLable">所屬群組：</td>	
		<td  class="queryTDInput">
			<select class="field_Q" type="select" name="q_groupID">
            	<%=util.View.getOption("Select groupID,groupName from SYSAP_Group order by groupID", obj.getQ_groupID())%>
            </select>		
		</td>
        <td class="queryTDLable"></td>
        <td class="queryTDInput">
            
        </td> 
	</tr>
	
    <tr>
        <td class="queryTDInput" colspan="4" style="text-align:center;">
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
        <td class="td_form"><font color="red">*</font>使用者代碼：</td>
        <td class="td_form_white">
            <input class="field_P" type="text" name="empID" size="10" maxlength="10" value="<%=obj.getEmpID()%>">        </td>
        <td class="td_form"><font color="red">*</font>使用者姓名：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="empName" size="10" maxlength="10" value="<%=obj.getEmpName()%>">        </td>
    </tr>
    <tr>        
        <td class="td_form"><font color="red">*</font>加入群組：</td>
        <td class="td_form_white">
            <select class="field" type="select" name="groupID">
            <%=util.View.getOption("Select groupID,groupName from SYSAP_Group order by groupID", obj.getGroupID())%>
            </select>        </td>
        <td class="td_form"><font color="red">*</font>是否停用：</td>
        <td class="td_form_white">
            <select class="field" type="select" name="isStop">
            <%=util.View.getYNOption(obj.getIsStop())%>
            </select>        </td>        
    </tr>
    <tr>        
        <td class="td_form">縣市別：</td>
    	<td class="td_form_white">
    		<select class="field" type="select" name="organ_mark">
            <%=util.View.getOption("select kcde_2,kcnt from ckeyn where kcde_1 in ('45') and  kcde_2 not like '/*%' order by kcde_2",obj.getOrgan_mark()) %>
			</select>
    	</td>
        <td class="td_form"><font color="red">*</font>系統管理者：</td>
        <td class="td_form_white">
            <select class="field" type="select" name="isAdminManager">
            <%=util.View.getYNOption(obj.getIsAdminManager())%>
            </select>        </td>        
    </tr>    
    <tr>
        <td class="td_form"><font color="red">*</font>所屬機關：</td>
        <td class="td_form_white">
            <%=util.View.getPopOrgan("field","organID",obj.getOrganID(),obj.getOrganIDName())%>        </td>
        <td class="td_form">所屬單位：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="unitID" size="10" maxlength="10" value="<%=obj.getUnitID()%>">        </td>
    </tr>
    <tr>
        <td class="td_form">職稱：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="empTitle" size="15" maxlength="30" value="<%=obj.getEmpTitle()%>">        </td>
        <td class="td_form">電話：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="empTel" size="15" maxlength="30" value="<%=obj.getEmpTel()%>">        </td>
    </tr>
    <tr>
        <td class="td_form">傳真：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="empFax" size="15" maxlength="30" value="<%=obj.getEmpFax()%>">        </td>
        <td class="td_form">電子郵件信箱：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="empEmail" size="15" maxlength="50" value="<%=obj.getEmpEmail()%>">        </td>
    </tr>
     <tr>
    	<td class="td_form">登入密碼：</td>
    	<td class="td_form_white"><input class="field" type="password" name="empPWD" size="15" maxlength="30" value="<%=obj.getEmpPWD()%>"></td>
    	<td class="td_form">登入密碼確認：</td>
    	<td class="td_form_white"><input class="field" type="password" name="empPWD1" size="15" maxlength="30" value="<%=obj.getEmpPWD()%>"></td>
    </tr>
    <tr>        
        <td class="td_form">異動人員/日期：</td>
        <td class="td_form_white">[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
          /
          <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">] </td>
        <td class="td_form">&nbsp;</td>
        <td class="td_form_white">&nbsp;</td>
    </tr>
    </table>
    <input type="hidden" name="old_passwd" value="<%=obj.getEmpPWD()%>">
    </div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
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
        <th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">使用者代碼</a></th>
        <th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">使用者姓名</a></th>
        <th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">職稱</a></th>
        <th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">電話</a></th>
	</tr>		
    </thead>	
    <tbody id="listTBODY">
    <%
    boolean primaryArray[] = {true,false,false,false};
    boolean displayArray[] = {true,true,true,true};
    out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
    %>
    </tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language="javascript">
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "clear":
			queryOne(form1.empID.value);
	}
}

function gotoPage(iPageNo)
{
    pageListener.beforeGotoPage();
    if (isObj(form1.state)) form1.state.value = "queryAll";
    form1.empID.value="";
	form1.currentPage.value = iPageNo;
	form1.submit();
}
</script>
</body>
</html>
<%
} else {
	out.println("<br><br><br><p align=center>對不起，您沒有足夠的權限執行此功能，若有問題，請洽系統管理者或承辦人員！<br><br></p>");		
}
%>