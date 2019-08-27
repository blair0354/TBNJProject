<!--
程式目的：LCAAP140
程式代號：LCAAP140
程式日期：1020103
程式作者：TzuYi.Yang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp" %>
<jsp:useBean id="obj" scope="request" class="lca.ap.LCAAP160F">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<%
	ServletContext context = getServletContext();
	String uip = request.getRemoteAddr(); 
	obj.setContext(context);
    
    Map result = new LinkedHashMap();
    Map detailResult = new LinkedHashMap();
    List createErrMsg = new ArrayList();
    
    if("one".equals(obj.getState())){
        obj.doCheckOne();
    }
    else if("all".equals(obj.getState())){
        obj.doCheckAll();
    }
    else if("detail".equals(obj.getState())){
        obj.showDetail();
    }
    else if("recreate".equals(obj.getState())){
        createErrMsg = obj.doRecreate();
        obj.showDetail();
    }
    
    result = obj.getUnindexed();
    detailResult = obj.getDetailStatus();
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
<script type="text/javascript">
function chkUnitID(){
	if(form1.unitID.value != "00"){
		alert("此作業只提供內政部地政司使用");
		top.location.reload();
	}
}

function doCheck(check_type){
    document.form1.state.value = check_type;
    document.form1.submit();
}

function showDetail(cty_code){
    document.form1.state.value = 'detail';
    document.form1.detailCty.value = cty_code;
    document.form1.submit();
}

function recreate(recreation_str){
    document.form1.state.value = 'recreate';
    document.form1.recreation.value = recreation_str;
    document.form1.submit();
    //alert(user_acct);
}
</script>
</head>

<body topmargin="0" background="images/main_bg.jpg" onLoad="chkUnitID();">
<form id="form1" name="form1" method="post">
<input type='hidden' name='state' value='<%=obj.getState()%>'>
<input type='hidden' name='stateOneAll' value='<%=obj.getStateOneAll()%>'>
<input type='hidden' name='detailCty' value='<%=obj.getDetailCty()%>'>
<input type='hidden' name='recreation' value='<%=obj.getRecreation()%>'>
<table border="0" width=660 cellspacing="0" cellpadding="0">
	<tr align="center" valign="middle">
		<td><font size="4" face="標楷體" color="#FF9900">總歸戶索引檔檢核及重建作業</font>
		    <hr size="1" color="#008000" width="660" align="left">
		</td>
	</tr>
    <tr><td>&nbsp;</td></tr>
	<tr>
		<td>
            <table width="660">
                <tr>
                    <td><b>選擇縣市：</b>
                        <select name='cty'>
                            <% Map ctys = obj.getCtys(); 
                               Iterator i = ctys.keySet().iterator();
                               while(i.hasNext()){
                                   String ctyCode = (String)i.next();
                                   String ctyName = (String)ctys.get(ctyCode);
                            %>
                                <option value='<%=ctyCode%>' <%=ctyCode.equals(obj.getCty()) ? "selected" : "" %>><%=ctyName%>&nbsp;<%=ctyCode%></option>
                            <% } %>
                        </select>
                    </td>
                    <td>
                        <input type='button' name='btn1' value='指定檢核' onclick='doCheck("one");'/>
                        <input type='button' name='btn2' value='全部檢核' onclick='doCheck("all");' />
                    </td>
                </tr>
            </table>
        </td>
	</tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td><hr size="1" color="#008000" width="660" align="left"></td></tr>
    <% if(!"".equals(obj.getState()) && !"".equals(obj.getStateOneAll())){
           String check_type = "";
           if("one".equals(obj.getStateOneAll())){
               check_type = "指定檢核";
           }
           if("all".equals(obj.getStateOneAll())){
               check_type = "全部檢核";
           }
    %>
          <tr><td><b>檢核方式：<font color='RED'><%=check_type%></font><b></td></tr>   
    <%
       }
    %>
    <% 
    if(!"".equals(obj.getState())){
    if(!result.isEmpty()){ %>
    <tr>
        <td>
            <table width="660">
                <tr>
                    <td><b>索引檔檢核異常縣市：</b></td>
                </tr>
                <tr>
                    <td>
                        <%
                            int idx = 1;
                            i = result.keySet().iterator();
                            while(i.hasNext()){
                                String ctyCode = (String)i.next();
                                String ctyName = (String)ctys.get(ctyCode);
                                %>
                                      <input type='button' name='btn_cty' value='<%=ctyName%>' onclick='showDetail("<%=ctyCode%>")'/>&nbsp;&nbsp;&nbsp;
                                      <input type='hidden' name='unindexedCty' value='<%=ctyCode%>:<%=ctyName%>'/>
                                <%
                                if(idx > 1 && idx % 9 == 0){
                                %>
                                    <br><br>
                                <%
                                }
                                idx++;
                            }
                        %>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr><td><hr size="1" color="#008000" width="660" align="left"></td></tr>
    <tr>
        <td>
            <% if(detailResult.size() > 0){ %>
            <table>
                <tr>
                    <td><b>查詢時間：</b><%=obj.getQueryTime()%></td>
                </tr>
                <tr>
                    <td>
                        <table width="660">
                            <% 
                              boolean isNeedCreate = false;
                              Map ctyName = obj.getCtys();
                              Iterator dr = detailResult.keySet().iterator();
                              while(dr.hasNext()){
                                  String ctyUser = (String)dr.next();
                                  int cnt = 0;
                                  String detailStr = "";
                                  LinkedHashMap currStatus = (LinkedHashMap)detailResult.get(ctyUser);
                                  Iterator cs = currStatus.keySet().iterator();
                                  while(cs.hasNext()){
                                      String tab = (String)cs.next();
                                      Boolean status = ((Boolean)currStatus.get(tab)).booleanValue();
                                      String tableName = tab.split(":")[0];
                                      System.out.println("### jsp:" + tab + "," + currStatus.get(tab));
                                      if(cnt % 5 == 0){
                                          detailStr += "<tr>";
                                      }
                                      
                                      if(status){
                                          detailStr += "<td align='center'><img src='./images/green_tile.gif'/><br>" + tableName + "&nbsp;</td>";
                                      }
                                      else{
                                          isNeedCreate = true;
                                          detailStr += "<td align='center'><img src='./images/red_tile.gif'/><br>" + tableName + "&nbsp;</td>";
                                      }
                                      
                                      if(cnt % 5 == 4){
                                          detailStr += "</tr>";
                                      }
                                      cnt++;
                                  }
                            %>
                            <tr>
                                <td colspan='5'><%=ctyName.get(obj.getDetailCty())%> (<%=ctyUser.split(";")[0]%>)&nbsp;<input type='button' name='btn_recreate' value='重建索引' <%=isNeedCreate ? "onclick='recreate(\"" + ctyUser + "\")'" : ""%> <%=isNeedCreate ? "" : "disabled"%>/></td>
                            </tr>
                            <%=detailStr%>
                            <%
                              }
                            %>
                            
                        </table>
                    </td>
                </tr>
            </table>
            <% } %>
        </td>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <% }
       else{
    %>
    <tr><td>無異常索引檔！</td></tr>   
    <%
       }
       }
    %>
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
        <td>資料庫使用者名稱</td>
        <td>資料表</td>
        <td>sql</td>
        <td>錯誤訊息</td>
    </tr>
<%
    for(int e = 0; e < createErrMsg.size(); e++){
        String[] msg = (String[])createErrMsg.get(e);
%>
    <tr>
        <td><%=msg[0]%></td>
        <td><%=msg[1]%></td>
        <td><%=msg[2]%></td>
        <td><%=msg[3]%></td>
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
