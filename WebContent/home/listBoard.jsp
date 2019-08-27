<%@ page contentType="text/html;charset=utf-8"%>
<jsp:useBean id="obj" scope="request" class="sys.wm.SYSWM001F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String q = request.getParameter("q");
String sTitle = "文件下載";
//增加判斷上下檔日期區間的sql語法960803
obj.setIsQuery("Y");
//增加判斷上下檔日期區間的sql語法960803
if (util.Common.get(q).equals("")) {
	q = "2";
	obj.setQ_newsCat("2");
}
if (util.Validate.checkInt(q)) {
	sTitle = util.View.getLookupField("select codeName from sysca_code where codekindid='MSG' and codeid='" + q + "'");
	obj.setQ_newsCat(q);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Notice</title>
<link rel="stylesheet" href="../js/default.css" type="text/css">
<link rel="stylesheet" href="../js/style.css" type="text/css">
<style>
.sLink2:link {  font-family: "細明體", "新細明體";  color: #C90026; text-decoration: none }
.sLink2:visited {  font-family: "細明體", "新細明體"; color: #C90026; text-decoration: none }
.sLink2:active {  font-family: "細明體", "新細明體";  color: #000099}
.sLink2:hover {  font-family: "細明體", "新細明體";  text-decoration: none; color: #FF7E00; }
</style>

<script type="text/javascript">
/*顯示查詢視窗*/
function queryShow(queryName){
	var queryObj=document.all.item(queryName);		
	var objHeight= queryObj.style.height;
	var objWidth= queryObj.style.width;	
	objHeight= objHeight.substring(0,objHeight.length-2);
	objWidth= objWidth.substring(0,objWidth.length-2);
	queryObj.style.top=(document.body.clientHeight-Number(objHeight)-80)/2;
	queryObj.style.left=(document.body.clientWidth-Number(objWidth))/2;	
	queryObj.style.display="block";  	
}
/*隱藏查詢視窗*/
function queryHidden(queryName){
	var queryObj=document.all.item(queryName);		
	queryObj.style.display="none";
	/**
  	document.all.item("insert").disabled = false;   
  	document.all.item("queryAll").disabled = false; 	
  	btnFollowPK();
  	**/
}

function popBoard(newsID){
	window.open("../popBoard.jsp?newsID="+newsID,"","top=100,left=210,width=600,height=420,scrollbars=yes,resizable=yes");	
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
}	

function nodeclicked(){
	//alert(eval("path_"+nodeid));
	try {
		top.fbody.mainhead.document.getElementById("pathname").innerHTML = "<%=sTitle%>";
	} catch(e) {}
}
</script>
</head>
<body onLoad="nodeclicked();">
<form id="form1" name="form1" method="post">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:150px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
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
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('queryContainer');">
		</td>
	</tr>
	</table>
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="page.jsp" />
</td></tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                    <td><img src="../images/home4_r1_c1.jpg" alt="q" width="67" height="46" /></td>
                    <td width="100%" background="../images/notice3_01.gif" class="home_title"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                    <td class="home_title">※<%=sTitle%></td>
                    <td align="right"><input class="toolbar_default" type="button" followPK="false" name="queryAll" value="查　詢" onClick="queryShow('queryContainer')"></td>
                  </tr>
                  </table></td>
                    <td class="home_title"><img src="../images/notice3_03.gif" alt="q" width="17" height="46" /></td>
                  </tr>
                </table></td>
                </tr>
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <%
java.util.Iterator it= obj.queryAll().iterator();
String[] rowArray=new String[5];
while(it.hasNext()){
	rowArray=(String[])it.next();
%>                    
         <tr>
           <td align="right" background="../images/notice_line_01.gif" ><img src="../images/notice_line_01.gif" width="3" height="3" /></td>
           <td width="1%" class="sLink2"><img src="../images/home_icon01.gif" alt="系統公告" /></td>
           <td width="100%" bgcolor="#F7F7F7" class="sLink2"><a class="sLink2" href="#" onClick="popBoard('<%=rowArray[0]%>')"><%=rowArray[1] + " " + util.Common.MaskDate(rowArray[2])%></a></td>
           <td background="../images/notice_line_05.gif" class="sLink2"><img src="../images/notice_line_05.gif" width="3" height="3" /></td>
         </tr>
<%
}
%>  					

                      <tr>
                        <td colspan="4"><img src="../images/home_r3_c1.jpg" alt="q" width="100%"/></td>
                    </tr>
                </table></td>
                </tr>
                  <tr>
                    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                    <td width="1%"><img src="../images/notice_buttom_01.gif" alt="q" width="15" height="16" /></td>
                    <td width="100%" background="../images/notice_buttom_02.gif"><img src="../images/notice_buttom_02.gif" alt="q" width="1" height="16" /></td>
                    <td><img src="../images/notice_buttom_04.gif" alt="q" /></td>
                  </tr>
                </table></td>
                </tr>
  </table>
<input type="hidden" name="q" value="<%=q%>">
<input type="hidden" name="q_newsCat" value="<%=q%>">
</form>         
</body>
</html>      
<%
} 
%>
