<!--
程式目的：總歸戶檢視表檢核及重建作業
程式代號：
程式日期：1050914
程式作者：Rhonda.Ke
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<jsp:useBean id="obj" scope="request" class="lca.ap.LCAAP200F">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<%
	ServletContext context = getServletContext();
	String uip = request.getRemoteAddr(); 
	obj.setContext(context);
    
    Map result = new LinkedHashMap();
    Map detailResult = new LinkedHashMap();
    List createErrMsg = new ArrayList();
    
    obj.ProcessStatus();
    
    if("doCheck".equals(obj.getState())){
        obj.doCheckView();
        obj.ProcessStatus();
    }
    //if("one".equals(obj.getState())){
    //    obj.doCheckOne();
    //}
    //else if("all".equals(obj.getState())){
    //    obj.doCheckAll();
    //}
    //else if("detail".equals(obj.getState())){
    //    obj.showDetail();
    //}
    else if("recreate".equals(obj.getState())){
        createErrMsg = obj.doRecreate();
        //obj.showDetail();
    }
    else if("createSQLTxt".equals(obj.getState())){
    	String viewStr="";
    	for(String s : obj.getStrKeys()){
    		//obj.setRecreation(obj.getRecreation()+"::;::"+s);
    		viewStr+=s+"::;::";
    	}
    	obj.setRecreation(viewStr);
    	System.out.println(obj.getRecreation());
    	String url = request.getContextPath()+"/ReportServletTxt?pageName=eform_3_7&recreation_str=" + obj.getRecreation();
    	response.sendRedirect(url);
    }
    else if("reportViewCSV".equals(obj.getState())){
    	String viewStr="";
    	for(String s : obj.getStrKeys()){
    		//obj.setRecreation(obj.getRecreation()+"::;::"+s);
    		viewStr+=s+"::;::";
    	}
    	String url = request.getContextPath()+"/ReportServletTxt?pageName=eform_3_7_ViewCSV&sIDXM_NO=" + obj.getIDXM_NO();
    	response.sendRedirect(url);
    }
    
%>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-control" content="no-cache" />
<link rel="stylesheet" href="js/default.css" type="text/css" />
<link rel="stylesheet" href="js/font.css" type="text/css" />
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript" src="js/function.js"></script>
<script type="text/javascript">
function chkUnitID(){
	if(form1.unitID.value != "00"){
		alert("此作業只提供內政部地政司使用");
		top.location.reload();
	}
}

function doCheck(cName){
	var alertStr="請選取要檢核的View!";
	for(i=0; i<document.getElementsByName(cName).length; i++) {
		
		if(document.getElementsByName(cName)[i].checked){
			alertStr="";
		}
	}
	
	if(alertStr !=""){
		alert(alertStr);
	}else{
		if(confirm('確定進行View的檢核?')){
			trPS1.style.display="none";
			trPS2.style.display="";
	    	document.form1.state.value = "doCheck";
	    	document.form1.submit();
    	}
    }
}


function recreate(recreation_str){
	if (document.form1.dbUser.value == '' || document.form1.dbPsw.value == '') {
		alert('請填入資料庫管理者帳號/密碼');
		return;
	}
	
	if(confirm('確定進行View的重建?')){
    	document.form1.state.value = 'recreate';
    	document.form1.recreation.value = recreation_str;
    	document.form1.submit();
    }
    //alert(user_acct);
}

function createSQLTxt() {
	document.form1.state.value = 'createSQLTxt';
    document.form1.submit();
}

function doPrintCSV() {
	document.form1.state.value = 'reportViewCSV';
    document.form1.submit();
}

function check_all(cName){
	var bol=true;
	for(i=0; i<document.getElementsByName(cName).length; i++) {
		if(document.getElementsByName(cName)[i].checked){
			bol=false;
		}
	}
	
    for(i=0; i<document.getElementsByName(cName).length; i++) {
		document.getElementsByName(cName)[i].checked=bol;
	}
} 
</script>
</head>

<body topmargin="0" background="images/main_bg.jpg" onLoad="chkUnitID();">
<form id="form1" name="form1" method="post">
<input type='hidden' name='state' value='<%=obj.getState()%>'>
<input type='hidden' name='stateOneAll' value='<%=obj.getStateOneAll()%>'>
<input type='hidden' name='detailVTable' value='<%=obj.getDetailVTable()%>'>
<input type='hidden' name='detailCty' value='<%=obj.getDetailCty()%>'>
<input type='hidden' name='recreation' value='<%=obj.getRecreation()%>'>
<table border="0" width=660 cellspacing="0" cellpadding="0">
	<tr align="center" valign="middle">
		<td><font size="4" face="標楷體" color="#FF9900">總歸戶檢視表檢核及重建作業</font>
		    <hr size="1" color="#008000" width="660" align="left">
		</td>
	</tr>
    <tr><td>&nbsp;</td></tr>
	<tr>
		<td>
            <table width="660">
                <tr>
                    <td colspan='3'><b>勾選View：</b><input type='button' name='btn1' value='全選/取消' onclick="check_all('strKeys');"/></td>
                </tr>
                            <% Map vTable = obj.getViewTable(); 
                               Iterator i = vTable.keySet().iterator();
                               int intBr=0;
                               while(i.hasNext()){
                            	   intBr++;
                                   String vTableCode = (String)i.next();
                                   String vTableName = (String)(vTable.get(vTableCode));
                            %>
                            	<%if(intBr==1){%> <tr> <%}%>
                               <td><input type="checkbox" name="strKeys" value='<%=vTableCode%>' checked><%=vTableName%></td>
                                <%if(intBr==3){%> </tr> <%intBr=0; }%>
                            <%}%>
                    <tr>
                    <td colspan='3'>
                        <input type='button' name='btn2' value='檢核勾選的View' onclick="doCheck('strKeys');" />
                    </td>
                    </tr>
            </table>
        </td>
	</tr>
    
    <tr id="trPS1">
    	<td><font color="red"><center>
    		開始檢核時間:<b><%=Common.get(obj.getIDXM_SDT())%></b><br>
    		完成檢核時間:<b><%=Common.get(obj.getIDXM_EDT())%></b><br>
    		檢核情形:<b><%=Common.get(obj.getIDXM_FLAGC())%></b><br>
    		<%if("01".equals(obj.getIDXM_FLAG())){%>
    			<input type='button' name='btn3' value='檢核成果下載' onclick="doPrintCSV()" />
    		<%}%>
    		</center>
    		</font>
    	</td>
    </tr>
    <tr id="trPS2" style="display:none">
    	<td><font color="red"><center>
    		檢核中! 可稍後再進入本作業下載檢核成果!
    		</center>
    	</td>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td><hr size="1" color="#008000" width="660" align="left"></td></tr>
    <tr>
		                 <td colspan='3'>
		                  	<input type='button' name='btn_createSQLTxt' value='匯出勾選的View建置指令' onclick="createSQLTxt()"/>
		                 </td>
		            </tr>	
		            <tr>
		                 <td colspan='3'>
		                  	資料庫管理者 - 帳號/密碼 <input type="text" name="dbUser" value="ETEC" > / <input type="password" name="dbPsw"> <input type='button' name='btn_recreate' value='重建View' onclick="recreate('RLNID')"/>
		                 </td>
		            </tr>
    
</table>
<input type="hidden" name="uip" value="<%=uip%>">
<input type="hidden" name="userID" value="<%=session.getAttribute("uid")%>">
<input type="hidden" name="unitID" value="<%=session.getAttribute("unitid")%>">
</form>
<%
if(obj.getMsg().length() > 0){
%>
<script>alert('<%=obj.getMsg()%>');</script>
<%
}

if(!createErrMsg.isEmpty()){
%>
<table width="660">
    <tr>
        <td>錯誤訊息</td>
    </tr>
<%
    for(int e = 0; e < createErrMsg.size(); e++){
        String[] msg = (String[])createErrMsg.get(e);
%>
    <tr>
        <td>執行<%=msg[0]%>檢視表重建失敗： <%=msg[1]%></td>
    </tr>
<%
    }
%>
</table>
<%
}
%>
</body>
</html>
