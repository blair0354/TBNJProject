<!--
程式目的：EFORM508
程式代號：EFORM508
程式日期：1060620
程式作者：Sya
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*" %>
<%@page import="util.View" %>
<%@page import="lca.ap.EFORM506" %>
<%@include file="home/head.jsp" %>
<%@include file="menu_bar.jsp" %>

<jsp:useBean id="obj" scope="request" class="lca.ap.EFORM508">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList" />
<%
	int index_com = 0;
	String fileName = "";
	String tmp = "";
	ArrayList<String[]> al=new ArrayList<String[]>();
	
	if("init".equals(obj.getState())){
		obj.dateTimeModel();
		String[] firstValue=new String[16];
		firstValue[0]="";firstValue[1]="";firstValue[2]="";firstValue[3]="";firstValue[4]="";firstValue[5]="";
		firstValue[6]="";firstValue[7]="";firstValue[8]="";firstValue[9]="";firstValue[10]="";firstValue[11]="";
		firstValue[12]="";firstValue[13]="";firstValue[14]="";firstValue[15]="";
		obj.setOutputTable2(firstValue);
	}else if("doSelect".equals(obj.getState())){
		//al=obj.getResultModel();
		objList=obj.getResultModel();
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
<style type="text/css">

	.areaTr { 
    	background-color: #FFFFFF;
	}
	.areaTr2 { 
    	background-color: #f9ffff;
	}

	table.lowerTable_1 tr:hover th {
	background-color: yellow;
	}	

	table.lowerTable_1 {
	    width: 80%;
	    border-top-width: 0px;
	    border-right-width: 0px;
	    border-bottom-width: 0px;
	    border-left-width: 0px;
	    -webkit-margin-start: auto;
	    -webkit-margin-end: auto;
	    -webkit-border-horizontal-spacing: 1px;
	    -webkit-border-vertical-spacing: 1px;
	   	background-color: rgb(153, 153, 153);
	}
	
</style>
<script type="text/javascript">
	function chkUnitID(){
		if(form1.unitID.value != "00"){
			alert("此作業只提供內政部地政司使用");
			top.location.reload();
		}
	}

	function doSelect(){
		var alertStr="";
		form1.sd_h.value = form1.sd.value;
		form1.ed_h.value = form1.ed.value;
		alertStr+=checkDate(form1.sd_h,"產製日期起");
		alertStr+=checkDate(form1.ed_h,"產製日期迄");
		alertStr+=checkEmpty(form1.sd_h,"產製日期起");
		alertStr+=checkEmpty(form1.ed_h,"產製日期迄");
		
		if(alertStr != ''){
			alert(alertStr);
			return false;
		}else{
			form1.state.value = "doSelect";
			//form1.submit();
	    	document.form1.submit();
	    }
	}
	/*
	function doPrint(obj1,obj2){
	
		var  wEvent =  window.event;
			alert(wEvent);
		var eTarget  = wEvent.target ? wEvent.target : wEvent.srcElement;
			alert(eTarget);
		var clickedTDValue = eTarget.innerText;
			alert(clickedTDValue);
		return;
	
	}
	function chkYYYMMDD(yyymmdd){
		
		var strYMD = /(00[1-9]|0[1-9][0-9]|[1-9][0-9][0-9])(0[1-9]1[0-2])(0[1-9]|[1-2][0-9]|[3][0-1])/;

		alert(form1.sd_h.value);
		alert(form1.ed_h.value);
		alert(strYMD.test(yyymmdd));
		return strYMD.test(yyymmdd);
	}
	*/
	function tableDisplay(){
		if(form1.state.value === 'doSelect'){
			document.getElementById('table_Display').style.display='table-row';
		}
	}
	
	
	
	function isCheckedAnyOne(){
		var checkboxs = document.getElementsByName('outputTable');

		var isAnyOneChecked = false;
		
		for (var i = 0; i < checkboxs.length; i++) {
			if(checkboxs[i].checked == true){
				isAnyOneChecked = true;
				break;
				}
		}

		return isAnyOneChecked;
	}

	function queryOne(obj){
		if(obj == ''){
			alert("報表未產製完成，無法下載!");
		}else{
			if(isCheckedAnyOne()){
				form2.saveTemp.value = obj;

				var checkboxs = document.getElementsByName('outputTable');
				var checkboxs2 = document.getElementsByName('outputTable2');

				for (var i = 0; i < checkboxs.length; i++) {
					if(checkboxs[i].checked == true){
						checkboxs2[i].checked = true;
						//alert(checkboxs2[i].checked);
					}else{
						checkboxs2[i].checked = false;
					}
				}

				
					form2.t0.value = obj;
					//form1.state.value = "doPrint";
					form2.action = "eform508_print.jsp";
					//var config = "height=10,width=10,location=no,menubar=no,left=500,top=80,location=no";
					//window.open('eform508_print.jsp?t0='+obj , "Joseph", config);
					form2.submit();
					//form1.state.value = "doSelect";
			}else{
				alert("尚未勾選任何欄位！");
				return;
			}
		}
	}
	
	
</script>
</head>
<body topmargin="0" background="images/main_bg.jpg"	onLoad="chkUnitID();">
	<form id="form1" name="form1" method="post">
		<div id="controlTrCheckbox">
			<table style="border:0;width:700;cellspacing:0;cellpadding:0">
				<tr>
					<td width="10%">
					<td width="90%">
				</tr>
				<tr align="center">
					<td colspan="2"><font size="4" face="標楷體" color="#FF9900">原住民地區土地概況統計表 - 下載
					</font><hr size="1" color="#008000" width="700" align="left"></td>
				</tr>
				<tr align="center">
					<td colspan="2"><font size="-1"><font color="red">*</font>產製日期：
						<%=util.View.getPopCalndar("field","sd", obj.getSd())%>&nbsp;～&nbsp;
						<%=util.View.getPopCalndar("field","ed", obj.getEd())%>&nbsp;&nbsp;
						<input class="toolbar_default" type="button" name="btn_Qry" value="查詢 " onClick="doSelect();"><br>
					</font><hr size="1" color="#008000" width="700" align="left"></td>
				</tr>
				<tr align="center">
					<td>
						<input class="toolbar_default" type="button" name="btn_All" value=" 全　 選  " onClick="topButtonDoToggleAllHandler(event,1);"><br><br>
						<input class="toolbar_default" type="button" name="btn_notAll" value="取消全選" onClick="topButtonDoToggleAllHandler(event,2);">
					</td>
					<td align="left"><font size="-1">
						<input type="checkbox" name="outputTable" value="ADCNT01" <%="ADCNT01".equals(obj.getOutputTable2()[0])?"checked":""%>>標示部面積
						<input type="checkbox" name="outputTable" value="ADCNT02" <%="ADCNT02".equals(obj.getOutputTable2()[1])?"checked":""%>>土地筆數
						<input type="checkbox" name="outputTable" value="ADCNT03" <%="ADCNT03".equals(obj.getOutputTable2()[2])?"checked":""%>>原住民所有權人數
						<input type="checkbox" name="outputTable" value="ADCNT04" <%="ADCNT04".equals(obj.getOutputTable2()[3])?"checked":""%>>非原住民所有權人數<br>
						<input type="checkbox" name="outputTable" value="ADCNT11" <%="ADCNT11".equals(obj.getOutputTable2()[4])?"checked":""%>>原住民保留地標示部面積
						<input type="checkbox" name="outputTable" value="ADCNT12" <%="ADCNT12".equals(obj.getOutputTable2()[5])?"checked":""%>>原住民保留地土地筆數
						<input type="checkbox" name="outputTable" value="ADCNT13" <%="ADCNT13".equals(obj.getOutputTable2()[6])?"checked":""%>>原住民保留地原住民所有權人數<br>
						<input type="checkbox" name="outputTable" value="ADCNT14" <%="ADCNT14".equals(obj.getOutputTable2()[7])?"checked":""%>>原住民保留地非原住民所有權人數
						<input type="checkbox" name="outputTable" value="ADCNT15" <%="ADCNT15".equals(obj.getOutputTable2()[8])?"checked":""%>>原住民保留原住民持有面積
						<input type="checkbox" name="outputTable" value="ADCNT16" <%="ADCNT16".equals(obj.getOutputTable2()[9])?"checked":""%>>原住民保留地非原住民持有面積<br>
						<input type="checkbox" name="outputTable" value="ADCNT21" <%="ADCNT21".equals(obj.getOutputTable2()[10])?"checked":""%>>非原住民保留地標示部面積
						<input type="checkbox" name="outputTable" value="ADCNT22" <%="ADCNT22".equals(obj.getOutputTable2()[11])?"checked":""%>>非原住民保留地土地筆數
						<input type="checkbox" name="outputTable" value="ADCNT23" <%="ADCNT23".equals(obj.getOutputTable2()[12])?"checked":""%>>非原住民保留地原住民所有權人數<br>
						<input type="checkbox" name="outputTable" value="ADCNT24" <%="ADCNT24".equals(obj.getOutputTable2()[13])?"checked":""%>>非原住民保留地非原住民所有權人數
						<input type="checkbox" name="outputTable" value="ADCNT25" <%="ADCNT25".equals(obj.getOutputTable2()[14])?"checked":""%>>非原住民保留地原住民持有面積
						<input type="checkbox" name="outputTable" value="ADCNT26" <%="ADCNT26".equals(obj.getOutputTable2()[15])?"checked":""%>>非原住民保留地非原住民持有面積<br>
					</font></td>
				</tr>
				<tr align="center">
					<td colspan="2"><font size="-1"></font><hr size="1" color="#008000" width="700" align="left"></td>
				</tr>
				<tr align="center" id="table_Display" style="display: none;">
					<td colspan="2">
						<table class="lowerTable_1" id="table1">
						<thead id="listTHEAD">
						<tr>
							<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">序號</a></th>
							<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">產製人員</a></th>
							<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">產制時間</a></th>
							<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">統計完成時間</a></th>
							<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">產製結果</a></th>
							<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',6,false);" href="#">下載時間</a></th>
						</tr>
						<!-- 
							<tr>
								<th width="6%">序號</th>
								<th width="10%">產製人員</th>
								<th width="22%">產制時間</th>
								<th width="22%">統計完成時間</th>
								<th width="18%">產製結果</th>
								<th width="22%">下載時間</th>
							</tr>
						-->
						</thead>
						<tbody id="listTBODY">
						<%
							boolean primaryArray[] = {  true,false,false,false,false,false };
							boolean displayArray[] = {  false, true, true, true, true, true};
							String[] alignArray = {"center","center","center","center","center","center","center"};
							out.write(util.View.getQuerylist(primaryArray,displayArray,alignArray,objList,obj.getQueryAllFlag(),1));
						%>
						
						</tbody>
						</table>
					<hr size="1" color="#008000" width="700" align="left"></td>
				</tr>
			</table>
		</div>
		<input type="hidden" name="state" value="<%=obj.getState()%>">
		<input type="hidden" name="userID" value="<%=session.getAttribute("uid")%>">
		<input type="hidden" name="unitID" value="<%=session.getAttribute("unitid")%>">
		<input type="hidden" name="sd_h" value="">
		<input type="hidden" name="ed_h" value="">
		
		<input type="hidden" name="t4" value="">
	</form>
	<form id="form2" name="form2" method="post" style="display:none;">
		<input type="hidden" name="saveTemp" value="">
		<input type="checkbox" name="outputTable2" value="ADCNT01">標示部面積
		<input type="checkbox" name="outputTable2" value="ADCNT02">土地筆數
		<input type="checkbox" name="outputTable2" value="ADCNT03">原住民所有權人數
		<input type="checkbox" name="outputTable2" value="ADCNT04">非原住民所有權人數<br>
		<input type="checkbox" name="outputTable2" value="ADCNT11">原住民保留地標示部面積
		<input type="checkbox" name="outputTable2" value="ADCNT12">原住民保留地土地筆數
		<input type="checkbox" name="outputTable2" value="ADCNT13">原住民保留地原住民所有權人數<br>
		<input type="checkbox" name="outputTable2" value="ADCNT14">原住民保留地非原住民所有權人數
		<input type="checkbox" name="outputTable2" value="ADCNT15">原住民保留原住民持有面積
		<input type="checkbox" name="outputTable2" value="ADCNT16">原住民保留地非原住民持有面積<br>
		<input type="checkbox" name="outputTable2" value="ADCNT21">非原住民保留地標示部面積
		<input type="checkbox" name="outputTable2" value="ADCNT22">非原住民保留地土地筆數
		<input type="checkbox" name="outputTable2" value="ADCNT23">非原住民保留地原住民所有權人數<br>
		<input type="checkbox" name="outputTable2" value="ADCNT24">非原住民保留地非原住民所有權人數
		<input type="checkbox" name="outputTable2" value="ADCNT25">非原住民保留地原住民持有面積
		<input type="checkbox" name="outputTable2" value="ADCNT26">非原住民保留地非原住民持有面積<br>
		<input type="hidden" name="t0" value="">
		<input type="hidden" name="userID" value="<%=session.getAttribute("uid")%>">
	</form>
<script type="text/javascript">

	tableDisplay();
	function topButtonDoToggleAllHandler(e,index){
		e = e || window.event;	//另div的onclick觸發function能接到e
		var that = e.target ? e.target:e.srcElement;	//that即為有做動作的節點

		if((that.tagName === 'INPUT'||that.tagName === 'input') && that.className === 'toolbar_default'){
			//↑↑判斷tag為<input>且class="toolbar_default"的節點
            var nextTDNode = that.parentNode.nextSibling;
            //被勾選的checkBox上一層的下一個節點(<input>↑<td>→<td>)
            var checkBoxsInSecondTD = nextTDNode.getElementsByTagName('input');	//取得<td>底下的<input>tag

            if(index == 1){
	            for(var d=0;d<checkBoxsInSecondTD.length;d+=1){
					checkBoxsInSecondTD[d].checked = true;
				};
            }
            if(index == 2){
	            for(var d=0;d<checkBoxsInSecondTD.length;d+=1){
					checkBoxsInSecondTD[d].checked = false;
				};
            };
		};
	};
</script>
</body>
</html>
