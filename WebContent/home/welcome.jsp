<%@ page contentType="text/html;charset=utf-8" %>
<jsp:useBean id="obj" scope="page" class="sys.wm.SYSWM001F"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Notice</title>
<link rel="stylesheet" href="../js/default.css" type="text/css">
<link rel="stylesheet" href="../js/style.css" type="text/css">
<style>
body {
	margin-top: 0px;
	margin-left: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
  
.showTD {
	padding: 2px 5px 2px 2px;
	border-left: 1px solid silver;
	border-bottom: 1px solid silver;
	border-right: 1px solid silver;
	text-align: left;
	height:24px;
}

.th{
	font-weight:normal;
	z-index: 20;
	padding: 4px 2px 2px 2px;
	color: white;
	text-align:center;
	height:23;	
	border-left: 1px solid white;
	border-bottom: 1px solid white;
	position:relative;
}

.sLink2:link {  font-family: "細明體", "新細明體";  color: #C90026; text-decoration: none }
.sLink2:visited {  font-family: "細明體", "新細明體"; color: #C90026; text-decoration: none }
.sLink2:active {  font-family: "細明體", "新細明體";  color: #000099}
.sLink2:hover {  font-family: "細明體", "新細明體";  text-decoration: none; color: #FF7E00; }
</style>
<script type="text/javascript" src="../js/function.js"></script>
<script language="javascript">
function goRent(caseNo){
	try {
		top.body.mainhead.document.all.item("pathname").innerHTML = "非公用財產系統 > > 出租作業 > > 出租案件管理作業 ";
	} catch(e) {}
	
	location.href="../npb/re/npbre001f.jsp?state=queryOne&queryAllFlag=true&caseNo="+caseNo+"&q_caseNoS="+caseNo+"&q_caseNoE="+caseNo;
	//E01000100010001
	//location.href="../unt/la/untla002f.jsp?state=queryAll&querySelectValue=land&q_signNo3=0001&q_signNo4=0001&q_signNo5=0001";
}

function popBoard(newsID){
	window.open("../popBoard.jsp?newsID="+newsID,"","top=100,left=210,width=600,height=420,scrollbars=yes,resizable=yes");	
}

function nodeclicked(){
	//alert(eval("path_"+nodeid));
	try {
		//top.body.mainhead.document.all.item("pathname").innerHTML = "市政資訊管理系統 > > 訊息公告 ";
		top.fbody.mainhead.document.getElementById("pathname").innerHTML = "地籍清理管理系統 > > 訊息公告 ";
	} catch(e) {}
}

/*列表檔案*/
function listToHTMLReport(listHeadName,listBodyName){
	var sb = new StringBuffer();
	sb.append("<table border='0' cellpadding='0' cellspacing='0' bgcolor='#000000' width='100%'><tr><td><table width='100%' border='0' cellpadding='1' cellspacing='1'>");
	//寫入Thead資料
	var listHead = document.getElementById(listHeadName);	
	for(i=0; i<listHead.rows.length; i++){
		sb.append("<tr bgcolor='#CCCCCC' align='center'>");
		for(j=0; j<listHead.rows[i].cells.length; j++){			
			if (isObj(listHead.rows[i].cells[j].childNodes[0].childNodes[0])){
				sb.append("<td>").append(listHead.rows[i].cells[j].childNodes[0].childNodes[0].nodeValue).append("</td>");
			}else{
				sb.append("<td>&nbsp;</td>");
			}
		}
		sb.append("</tr>");
	}
		
	//寫入Tbody資料	
	var listBody = document.getElementById(listBodyName);	
	for(i=0; i<listBody.rows.length; i++){
		sb.append("<tr bgcolor='#FFFFFF'>");	
		for(j=0; j<listBody.rows[i].cells.length; j++){
			if (isObj(listBody.rows[i].cells[j].childNodes[0])){
				sb.append("<td>").append(listBody.rows[i].cells[j].childNodes[0].nodeValue).append("</td>");
			}else{
				sb.append("<td>&nbsp;</td>");
			}
		}
		sb.append("</tr>");
	}	
	sb.append("</table></td></tr></table>");
	var ie = window.open();
	ie.document.write(sb.toString());
}
</script>
</head>
<body onLoad="nodeclicked();">
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="table24">
      
      <tr>
        <td valign="top">
              <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="../images/home_r1_c1.jpg" alt="系統公告" width="67" height="46" /></td>
                        <td width="100%" background="../images/notice1_01.gif" class="home_title">※系統公告</td>
                        <td class="home_title"><img src="../images/notice1_03.gif" width="18" height="46" /></td>
                      </tr>
                </table></td>
                </tr>
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <%
obj.setIsQuery("Y");
obj.setQ_newsCat("1");
obj.setPageSize(100000);
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
                        <td colspan="4"><img src="../images/home_r3_c1.jpg" alt="系統公告" width="100%"/></td>
                    </tr>
                </table></td>
                </tr>
                  <tr>
                    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                    <td width="1%"><img src="../images/notice_buttom_01.gif" width="15" height="16" /></td>
                    <td width="100%" background="../images/notice_buttom_02.gif"><img src="../images/notice_buttom_02.gif" width="1" height="16" /></td>
                    <td><img src="../images/notice_buttom_04.gif" /></td>
                  </tr>
                  </table></td>
                </tr>
          </table>
              <br></td>
    </tr>
 
</table>


<!-- 
<center>
	<span class="titleCFont" style="color:red"><h1>歡迎光臨</h1></span>
	<span class="titleCFont"><h1><%=application.getInitParameter("SystemCName")%></span><br>
	<span class="titleEFont" style="font-size:20"><%=application.getInitParameter("SystemEName")%></span>	
</center>
 -->
</body>
</html>