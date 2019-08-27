var _setAutoCompleteOffFields = "";

//*********************************************
//程式功能：判斷物件是否正確
//*********************************************
function isObj(obj){
	return (!((obj==null)||(obj==undefined)));
}

//js classes
 function StringBuffer() { 
   this.buffer = []; 
 }
 StringBuffer.prototype.append = function append(string) { 
   this.buffer.push(string);
   return this; 
 }; 
 StringBuffer.prototype.toString = function toString() { 
   return this.buffer.join(""); 
 };  
 
 String.prototype.trim = function() { return this.replace(/^\s+|\s+$/, ''); };
 //js classes 


//*********************************************
var returnWindow;
document.onreadystatechange=function(){	
	if(document.readyState=="complete"){
		//禁止使用滑鼠右鍵
		//document.body.oncontextmenu=function(){ return false; }
		//本頁開啟(window.open)的所有視窗關閉
		document.body.onunload=function(){					
			if (isObj(returnWindow)){ returnWindow.close(); }
		}
	}
}

//*********************************************
//函數功能：土地標示, 改變第一層
//參　　數：MA_LO所別; q_ma46Name鄉鎮區;q_ma48Name段/小段;selectValue所選的值
//傳 回 值：無
//*********************************************
function changeCountry(MA_LO,q_ma46Name,q_ma48Name,selectValue){
	var queryValue=document.all.item(MA_LO).value;	
	
	//清除q_ma46Name
	if (isObj(document.all.item(q_ma46Name))) {		
		var obj2 = document.all.item(q_ma46Name);
		obj2.options.length=0;
		var obj2Option = document.createElement("Option");
		obj2.options.add(obj2Option);
		obj2Option.innerText = "請選擇";
		obj2Option.value = "";	
	}
	
	//清除signNo3
	if (isObj(document.all.item(q_ma48Name))) {		
		var obj3 = document.all.item(q_ma48Name);
		obj3.options.length=0;
		var obj3Option = document.createElement("Option");	
		obj3.options.add(obj3Option);
		obj3Option.innerText = "請選擇";
		obj3Option.value = "";			
	}	
	
	   var xmlDoc=document.createElement("xml");	
	   xmlDoc.async=false;			
	   if(xmlDoc.load("../../home/xmlCountry.jsp?MA_LO="+queryValue)){		
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(i=0; i<nodesLen; i++){
				signNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("signNo");
				signName=xmlDoc.documentElement.childNodes.item(i).getAttribute("signName");
				var oOption = document.createElement("Option");	
				obj2.options.add(oOption);
				oOption.innerText = signName;
				oOption.value = signNo;		
		      	if(signNo == selectValue){
        			oOption.selected=true;
				}           										
			}
		}
		
		
}
	
//*********************************************
//函數功能：土地標示, 改變第二層
//參　　數：signNo1縣市; signNo2鄉鎮區;signNo3段/小段;selectValue所選的值
//傳 回 值：無
//*********************************************	
function changeRoad(MA_LO,q_ma46Name,q_ma48Name,selectValue){
	var queryValue=document.all.item(q_ma46Name).value;	
	var ma_lo=document.all.item(MA_LO).value;	
	//清除signNo3
	if (isObj(document.all.item(q_ma48Name))) {		
		var obj3 = document.all.item(q_ma48Name);
		obj3.options.length=0;
		var obj3Option = document.createElement("Option");	
		obj3.options.add(obj3Option);
		obj3Option.innerText = "請選擇";
		obj3Option.value = "";			
	}	
	
	var xmlDoc=document.createElement("xml");	
	xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlRoad.jsp?MA_LO="+ma_lo+"&q_ma46="+queryValue)){		
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(i=0; i<nodesLen; i++){
				signNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("signNo");
				signName=xmlDoc.documentElement.childNodes.item(i).getAttribute("signName");
				var oOption = document.createElement("Option");	
				obj3.options.add(oOption);
				oOption.innerText = signName;
				oOption.value = signNo;		
		      	if(signNo == selectValue){
        			oOption.selected=true;
				}
			}
		}else{
			return false;	
		}
	
}	

//*********************************************
//函數功能：土地標示, 改變第二層
//參　　數：signNo1縣市; signNo2鄉鎮區;signNo3段/小段;selectValue所選的值
//傳 回 值：無
//*********************************************	
function changeRoad01(MA_LO,q_ma48Name,selectValue){
	var ma_lo=document.all.item(MA_LO).value;	
	//清除signNo3
	if (isObj(document.all.item(q_ma48Name))) {		
		var obj3 = document.all.item(q_ma48Name);
		obj3.options.length=0;
		var obj3Option = document.createElement("Option");	
		obj3.options.add(obj3Option);
		obj3Option.innerText = "請選擇";
		obj3Option.value = "";			
	}	
	
	var xmlDoc=document.createElement("xml");	
	xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlRoad.jsp?MA_LO="+ma_lo)){		
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(i=0; i<nodesLen; i++){
				signNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("signNo");
				signName=xmlDoc.documentElement.childNodes.item(i).getAttribute("signName");
				var oOption = document.createElement("Option");	
				obj3.options.add(oOption);
				oOption.innerText = signName;
				oOption.value = signNo;		
		      	if(signNo == selectValue){
        			oOption.selected=true;
				}
			}
		}else{
			return false;	
		}
	
}	
//*********************************************
//程式功能：將TEXTField清空
//*********************************************
function clearField(obj){
	obj.value="";
}

//*********************************************
//程式功能：將小寫轉為大寫,利用onkeypress="toUpper();"事件
//*********************************************
function toUpper(){
	var tmp=window.event.keyCode;
	if (tmp>=97 && tmp <=122){ tmp=tmp-32; }
	window.event.keyCode=tmp;
}

function isIE() {
	var nav = navigator.userAgent.toLowerCase();
	if ((nav.indexOf("msie") != -1)) return true;
	else return false;
}

//*********************************************
//程式功能：AJAX,擷取回應值
//*********************************************
function getRemoteData(uri,q,asyn)  {
	var x ;
	if(window.XMLHttpRequest){
	    x = new XMLHttpRequest();
	} else {
	   x = new ActiveXObject("Microsoft.XMLHTTP"); 
	}
	if (isObj(asyn) && asyn != null && asyn == true) {
		x.open('Post',uri,true); 		
	} else 	x.open('Post',uri,false); 
	x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	x.setRequestHeader( "If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT" );
	x.send(encodeURI('q='+q));
	return x.responseText.trim();
}

function getRemoteXML(uri,q,asyn)  {
	var x ;
	if(window.XMLHttpRequest){
	    x = new XMLHttpRequest();
	} else {
	   x = new ActiveXObject("Microsoft.XMLHTTP"); 
	}
	if (isObj(asyn) && asyn != null && asyn == true) {
		x.open('Post',uri,true); 		
	} else 	x.open('Post',uri,false); 
	x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	x.setRequestHeader( "If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT" );   
	x.send(encodeURI('q='+q));
	if (x.readyState == 4 && x.status == 200) return x.responseXML;
}
//*********************************************
 
//*********************************************
//函數功能：pop萬年曆輔助視窗,並回傳年月日(0920101)
//參　　數：dateField顯示日期的欄位名稱
//傳 回 值：無
//*********************************************
function popCalndar(dateField,js,sY,sM){
	if (isIE()) {
		var prop="";
		prop=prop+"scroll:yes;status:no;help:no;";
		prop=prop+"dialogWidth:210px;";
		prop=prop+"dialogHeight:240px";
		var strDate=window.showModalDialog('home/calendar.jsp?sY='+sY+'&sM='+sM,window,prop);
		if (isObj(strDate)){ 
			document.all.item(dateField).value=strDate;
			if (js!=null && js.length>0)	eval(js);
		}	
	} else {
		var prop="";
		var windowTop=(document.body.clientHeight-400)/2+180;
		var windowLeft=(document.body.clientWidth-400)/2+250;
		prop=prop+"width=280px,height=220,";
		prop=prop+"top="+windowTop+",";
		prop=prop+"left="+windowLeft+",";
		prop=prop+"scrollbars=no";	
		window.open('../../home/calendar_v2.jsp?dateField=' + dateField + '&sY='+sY+'&sM='+sM,'popCalendar',prop);			
	}
	if (js!=null && js.length>0) eval(js);
}
function popCalndar(dateField,js,sY,sM){
	var prop="";
	prop=prop+"scroll:yes;status:no;help:no;";
	prop=prop+"dialogWidth:210px;";
	prop=prop+"dialogHeight:240px";
	var strDate=window.showModalDialog('home/calendar.jsp?sY='+sY+'&sM='+sM,window,prop);
	if (isObj(strDate)){ 
		document.all.item(dateField).value=strDate;
		if (js!=null && js.length>0)	eval(js);
	}
}

//*********************************************
//函數功能：pop機關輔助視窗,並回傳機關代碼及名稱
//參　　數：popOrganID顯示機關代碼的欄位名稱; popOrganName顯示機關名稱的欄位名稱; isLimit是否顯示全部機關名稱
//傳 回 值：無
//*********************************************
function popOrgan(popOrganID,popOrganName,isLimit){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=420,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popOrgan.jsp?popOrganID="+popOrganID+"&popOrganName="+popOrganName+"&isLimit="+isLimit,"",prop);
}

//*********************************************
//函數功能：顯示後端處理錯誤訊息
//參　　數：錯誤訊息
//傳 回 值：無
//*********************************************
function showErrorMsg(error){
	var msg=error;		
	if(msg !=null && msg.length!=0){
		var strMsg = "新增完成,修改完成,刪除完成";
		var sFlag = false;
		try {	
			var arrMsg = strMsg.split(",");
			for (var i=0; i<arrMsg.length; i++) {
				if (arrMsg[i]==msg) sFlag = true;
			}
			if (sFlag) top.frames['title'].showBoxMsg(msg);
			else alert(msg);
		} catch(e) {		
		  alert(msg);	
		}	  
	} else {
		try {	
			if (isObj(top.frames['title'])) top.frames['title'].showBoxMsg(msg);
		} catch(e) {}
	}
	if (isObj(form1.permissionField)) doPermission(form1.permissionField.value); 
	return false;
} 

//*********************************************
//函數功能：權限按鈕設定，這是用東牆補西牆的做法，千萬別把它用到別的系統
//*********************************************
function doPermission(s) {
	try {
		if (s!=null && s=="Q") setDisplayItem('spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,btnMaintain,btnMaintain1,btnMaintain2,btnMaintain3,btnMaintain4,btnMaintain5,approveAll','H');
	} catch(e) {}
}
//*********************************************
//函數功能：由身分証號碼的判斷男女
//參　　數：IDcolumn身分証號碼;Sexcolumn性別欄位
//傳 回 值：
//*********************************************
function ChangeSex(IDcolumn,Sexcolumn){
	if( IDcolumn.value.length >= 2 ){
		Sexcolumn.value=IDcolumn.value.substr(1,1);
	}  
}
//*********************************************
//函數功能：取得下拉選項的預設值
//參　　數：elem:欄位物件;val:預設值
//傳 回 值：
//*********************************************
function getSelectedValue(elem,val) {
	var i=0;
	if (val !=""){
		for (i=0;i< elem.length;i++){
			if (elem.options[i].value==val)	{
				elem.options[i].selected=true;
				return;
		    }
		}
	}
}
//*********************************************
//程式功能：取得今天日期（YYYMMDD）
//*********************************************
function getToday(s){
	var myDate = new Date();		
	if (s!=null) myDate = s;

	var shif = 0;
	var intYear = myDate.getYear();
	if (intYear<300) shif = 1900;
	var myYear = (myDate.getYear()-1911+shif).toString();	
	var myMonth = (myDate.getMonth()+1).toString();
	var myDay = myDate.getDate().toString();

	if (myYear.length<=2){ myYear="0"+myYear; }
	if (myMonth.length<=1){ myMonth="0"+myMonth; }
	if (myDay.length<=1){ myDay="0"+myDay; }	
	myToday = myYear + myMonth + myDay;
	return myToday;
}
//*********************************************
//程式功能：取得現在時間（HHMMSS）
//*********************************************
function getNow(){
	myTime = new Date();
	var myHour = myTime.getHours().toString();
	var myMinute = myTime.getMinutes().toString();
	var mySecond = myTime.getSeconds().toString();

	if (myHour.length<=1){ myHour="0"+myHour; }
	if (myMinute.length<=1){ myMinute="0"+myMinute; }
	if (mySecond.length<=1){ mySecond="0"+mySecond; }	
	myNow = myHour + myMinute + mySecond;
	return myNow;
}

function getNumeric(s) {
	if (s==null || s=="" || isNaN(parseFloat(s))) return 0;
	else return parseFloat(s);
}

//*********************************************
//程式功能：取得虛擬路徑,return "../../"
//*********************************************
function getVirtualPath(){
	var pathName=window.location.pathname;		
	if (pathName.substring(0,1)=="/"){
		pathName=pathName.substring(1,pathName.length);
	}
	var pathArray=pathName.split("/");			
	var programLayer=pathArray.length - 2	
	var virtualPath="";
	for(i=0; i<programLayer; i++){
		virtualPath=virtualPath+"../";
	}
	return virtualPath;
}

//*********************************************
//函數功能：將檔名路徑改為javascript寫法
//參　　數：strPath:原始檔名路徑
//傳 回 值：
//*********************************************
function getDoublePath(strPath){
	var strReturn="";
	var i;
	var start=0;
	for(i=0; i<strPath.length; i++){
		if ("\\"==strPath.substring(i,i+1)){
			strReturn=strReturn + strPath.substring(start,i) +"\\";
			start=i;
		}
	}
	strReturn=strReturn + strPath.substring(start,strPath.length) ;
	return strReturn;
}

//*********************************************
//函數功能：根據選擇縣市來呈現所別
//參　　數：signNo1縣市; signNo2所別;selectValue所選的值
//傳 回 值：無
//*********************************************
function changeCityUnit(signNo1,signNo2,selectValue){
	var queryValue=document.getElementById(signNo1).value;	
	//alert("test");
	//清除signNo2
	if (isObj(document.getElementById(signNo2))) {	
		alert	
		var obj2 = document.getElementById(signNo2);
		obj2.options.length=0;
		var obj2Option = document.createElement("Option");
		obj2.options.add(obj2Option);
		obj2Option.innerText = "請選擇";
		obj2Option.value = "";	
		if (queryValue!=""){		
			var xmlDoc;
			var xmlDoc=document.createElement("xml");	
			xmlDoc.async=false;
			if(xmlDoc.load("home/xmlCityUnit.jsp?county="+queryValue)){		
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
	
	
}

//*********************************************
//函數功能：土地標示, 改變第一層
//參　　數：signNo1縣市; signNo2鄉鎮區;signNo3段/小段;selectValue所選的值
//傳 回 值：無
//*********************************************
function changeSignNo1(signNo1,signNo2,signNo3,selectValue){
	var queryValue=document.all.item(signNo1).value;	

	//清除signNo2
	if (isObj(document.all.item(signNo2))) {		
		var obj2 = document.all.item(signNo2);
		obj2.options.length=0;
		obj2.options.add(new Option("請選擇",""));
		
		if (queryValue!=""){		
			var xmlDoc;
			if (isIE()) xmlDoc = getRemoteXML("../../home/xmlSign.jsp?signNo1="+queryValue);
			else xmlDoc = (new DOMParser()).parseFromString(getRemoteData("../../home/xmlSign.jsp?signNo1="+queryValue), "text/xml");	
			var root = xmlDoc.getElementsByTagName('ResultSet')[0];		
			var items = root.getElementsByTagName("record");
			for (var iNode = 0; iNode < items.length; iNode++) {
				var signNo = items[iNode].getAttribute("signNo");
				var signName = items[iNode].getAttribute("signName");
				var oOption = new Option(signName,signNo);
				obj2.options.add(oOption);
				if(signNo == selectValue) oOption.selected=true;
			}
		}			
	}
	
	//清除signNo3
	if (isObj(document.all.item(signNo3))) {		
		var obj3 = document.all.item(signNo3);
		obj3.options.length=0;
		obj3.options.add(new Option("請選擇",""));		
	}		
}
	
//*********************************************
//函數功能：土地標示, 改變第二層
//參　　數：signNo1縣市; signNo2鄉鎮區;signNo3段/小段;selectValue所選的值
//傳 回 值：無
//*********************************************	
function changeSignNo2(signNo1,signNo2,signNo3,selectValue){
	var queryValue=document.all.item(signNo2).value;	
	
	//清除signNo3
	if (isObj(document.all.item(signNo3))) {		
		var obj3 = document.all.item(signNo3);
		obj3.options.length=0;
		obj3.options.add(new Option("請選擇",""));
		if (queryValue!=""){		
			var xmlDoc;
			if (isIE()) xmlDoc = getRemoteXML("../../home/xmlSign.jsp?signNo2="+queryValue);
			else xmlDoc = (new DOMParser()).parseFromString(getRemoteData("../../home/xmlSign.jsp?signNo2="+queryValue), "text/xml");	
			var root = xmlDoc.getElementsByTagName('ResultSet')[0];		
			var items = root.getElementsByTagName("record");
			var s = "";
			for (var iNode = 0; iNode < items.length; iNode++) {
				var signNo = items[iNode].getAttribute("signNo");
				var signName = items[iNode].getAttribute("signName");
				var oOption = new Option(signName,signNo);
				obj3.options.add(oOption);
				if(signNo == selectValue) oOption.selected=true;
			}
		}		
	}	
}	


//*********************************************
//函數功能：地址, 改變第一層
//參　　數：addr1縣市; addr2鄉鎮區;addr3村里;selectValue所選的值
//傳 回 值：無
//*********************************************
function changeAddr1(addr1,addr2,addr3,selectValue){
	var queryValue=document.all.item(addr1).value;	
	
	//清除addr2
	if (isObj(document.all.item(addr2))) {	
		var obj2 = document.all.item(addr2);
		obj2.options.length=0;
		obj2.options.add(new Option("請選擇",""));
		if (queryValue!=""){		
			var xmlDoc;
			if (isIE()) xmlDoc = getRemoteXML("../../home/xmlAddr.jsp?addr1="+queryValue);
			else xmlDoc = (new DOMParser()).parseFromString(getRemoteData("../../home/xmlAddr.jsp?addr1="+queryValue), "text/xml");	
			var root = xmlDoc.getElementsByTagName('ResultSet')[0];		
			var items = root.getElementsByTagName("record");
			for (var iNode = 0; iNode < items.length; iNode++) {
				var addrID = items[iNode].getAttribute("addrID");
				var addrName = items[iNode].getAttribute("addrName");
				var oOption = new Option(addrName,addrID);
				obj2.options.add(oOption);
				if(addrID == selectValue) oOption.selected=true;
			}
		}		
	}
	
	//清除addr3
	if (isObj(document.all.item(addr3))) {		
		var obj3 = document.all.item(addr3);
		obj3.options.length=0;
		obj3.options.add(new Option("請選擇",""));		
	}	

}

//*********************************************
//函數功能：地址, 改變第二層
//參　　數：addr1縣市; addr2鄉鎮區;addr3村里;selectValue所選的值
//傳 回 值：無
//*********************************************
function changeAddr2(addr1,addr2,addr3,selectValue){
	var queryValue=document.all.item(addr2).value;	
	
	//清除addr3
	if (isObj(document.all.item(addr3))) {		
		var obj3 = document.all.item(addr3);
		obj3.options.length=0;
		obj3.options.add(new Option("請選擇",""));
		
		if (queryValue!=""){		
			var xmlDoc;
			if (isIE()) xmlDoc = getRemoteXML("../../home/xmlAddr.jsp?addr2="+queryValue);
			else xmlDoc = (new DOMParser()).parseFromString(getRemoteData("../../home/xmlAddr.jsp?addr2="+queryValue), "text/xml");	
			var root = xmlDoc.getElementsByTagName('ResultSet')[0];		
			var items = root.getElementsByTagName("record");
			var s = "";
			for (var iNode = 0; iNode < items.length; iNode++) {
				var addrID = items[iNode].getAttribute("addrID");
				var addrName = items[iNode].getAttribute("addrName");
				var oOption = new Option(addrName,addrID);
				obj3.options.add(oOption);
				if(addrID == selectValue) oOption.selected=true;
			}
		}						
	}	
}


//*********************************************
//函數功能：取小數位數
//參　　數：v數值; dp小數位數,isUp是否是捨五入
//傳 回 值：無
//*********************************************
function roundp(v, dp , isUp) { 
	var v1 , v2, sdp ="" ,dp1;
	var sh = Math.pow(10 , dp);
	if(!isNaN(parseInt(v))){
		v = ""+v;		
		sdp = (""+sh).substr(1);	
		if (v.indexOf('.') == -1 ){					 
			if(dp > 0) v = v+'.'+sdp;
		}else{
			v1 = v.substr(0,v.indexOf(".")); //整數部分
			v2 = v.substr(v1.length+1,v.length); //小數部分 
			if(isUp != "Y"){
				v = v1 + "." + (v2 + sdp).substr(0,dp);
			}else{
				v2 = "0."+ v2 ;
				dp1 = ""+(Math.round(v2 * sh) / sh) ;	
				if (dp1.indexOf('.') == -1 ){
					v = v1 +'.' +sdp;
				}else{
					var dp1ln = dp1.substring(dp1.indexOf('.')+1,dp1.length).length;
					if(dp1ln < dp){
						sdp = "";
						for (var i = dp1ln ; i < dp ; i++) sdp = sdp + "0";
						dp1 = dp1 + sdp;					
					}					
					v = v1 +'.' + dp1.substr(2);
				}		
			}
		}
		return v ;
	}
	return "";
} 

//*********************************************
//函數功能：比較兩個日期
//參　　數：objDateS,objDateE為起訖日期物件本身, strType可以是d(Day),m(Month),y(Year)
//傳 回 值：假如正確是傳回一字串值;假如錯誤則傳回空字串
//*********************************************
function getDateDiff(sType, objDateS, objDateE ){
	var sdate, edate;
	var dates, datee, dated;
	var one_day=1000*60*60*24;
	var strType;

	if (isObj(sType.value)) strType = parse(sType.value);
	strType = parse(sType);
		
	if (isObj(objDateS.value)) sdate = parse(objDateS.value);
	else sdate = parse(objDateS);
	
	if (isObj(objDateE.value)) edate = parse(objDateE.value);
	else edate = parse(objDateE);	
	
	if( sdate.length==7 && edate.length==7 && strType.length>0){
		dates = new Date(parseInt(sdate.substring(0,3),10)+1911, sdate.substring(3,5)-1, sdate.substring(5,7));
		datee = new Date(parseInt(edate.substring(0,3),10)+1911, edate.substring(3,5)-1, edate.substring(5,7));
		if (strType=="d") {
			return (Math.ceil((datee-dates)/one_day));
		} else if (strType=="y") {
			return (parseInt(edate.substring(0,3),10)-parseInt(sdate.substring(0,3),10));
		} else if (strType=="m") {
			var sMonth = dates.getMonth() + (dates.getFullYear() * 12);
			var eMonth = datee.getMonth() + (datee.getFullYear() * 12);		
			return (eMonth-sMonth);			
		}		
	} else {
		return "";
	}
}

//*********************************************
//函數功能：某一日期加上一定期間的日或月或年
//參　　數：strType可以是d(Day),m(Month),y(Year); sNum數值; objDate為日期物件或日期
//傳 回 值：傳回加上特定期間之後的日期
//*********************************************
function getDateAdd(sType, sNum, objDate){
 	var sdate, rdate, tdate;
	var myYear, myMonth, myDay;
	var intNumber;
	var sNumber;
	var intYear, intMon, intDay;
	var strType;

	if (isObj(sType.value)) strType = parse(sType.value);
	strType = parse(sType);
		
	if (isObj(sNum.value)) sNumber = parse(sNum.value);
	else sNumber = sNum;
	
	if (isObj(objDate.value)) sdate = parse(objDate.value);
	else sdate = parse(objDate);
		
	if (sNumber=="") sNumber = 0;	
	if (isNaN(sNumber)) intNumber = 0;
	else intNumber = parseInt(sNumber,10);

	if(sdate.length==7 && strType.length>0) {
		intYear = parseInt(sdate.substring(0,3),10)+1911;
		intMon = parseInt(sdate.substring(3,5),10)-1;
		intDay = parseInt(sdate.substring(5,7),10);	
		if (strType=="d") {
			rdate = new Date(intYear, intMon, intDay+intNumber);
		} else if (strType=="m") {
			tdate =MaxDay(new Date(intYear, intMon+intNumber, 1));
			if(intDay>tdate){
			     intDay=tdate;
			}	
			rdate = new Date(intYear, intMon+intNumber, intDay);
		} else if (strType=="y") {
			rdate = new Date(intYear+intNumber, intMon, intDay);
			rdate = rdate.valueOf()
			rdate = rdate + -1 * 24 * 60 * 60 * 1000
			rdate = new Date(rdate)
		}
		//the bullshit js trancate 19xx to xx
		if (rdate.getYear()<2000) myYear = (rdate.getYear()-11).toString()
		else myYear = (rdate.getYear()-1911).toString();				
		//myYear = (rdate.getYear()-1911).toString();
		myMonth = (rdate.getMonth()+1).toString();
		myDay = rdate.getDate().toString();
			
		if (myYear.length<=2){ myYear="0"+myYear; }
		if (myMonth.length<=1){ myMonth="0"+myMonth; }
		if (myDay.length<=1){ myDay="0"+myDay; }	
		myToday = myYear + myMonth + myDay;
		return myToday;
		
	} else {
		return "";
	}		
}

//*********************************************
//函數功能：取得當月最大日期
//參　　數：objDate為日期物件或日期
//傳 回 值：傳回加上特定期間之後的日期
//*********************************************
 function MaxDay(date){
        var year = date.getFullYear();
        var month = date.getMonth()+1;
        if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12)
            return 31;
        else if(month==4 || month==6 || month==9 || month==11)
            return 30;
        else if(month==2)
        {
            var d = new Date(year+"/2/29");
            if(d.getMonth()==1) return 29;
            else return 28
        }
 }


//*********************************************
//函數功能：以填入的值,來選擇select的項目
//參　　數：select的物值,及填入的值
//傳 回 值：無
//*********************************************
function SelectItemByValue(obj,value){
    var isExit = false;
    for(var i=0;i<obj.options.length;i++){
        if(obj.options[i].value == value){
            obj.options[i].selected = true;
            return true;
        }
    }
    alert("無此代碼,請重新輸入");
    return false;
}

//*********************************************
//函數功能：以Select的value,來填入text的value
//參　　數：select的物件,及text的物件
//傳 回 值：無
//*********************************************
function GetSelectValue(obj,obj2){
    obj2.value=obj.value; 
}

//地建號的格式化
function GetFormatMa49(obj,obj2){
	var alertStr="";
    if((obj.value!="")&(obj2.value!="")){
		 if((obj.value=="A")||(obj.value=="B")||(obj.value=="C")){
		 	var tmpstr=obj2.value.split("-");
		 	if(tmpstr.length==2){
		 	    var zerostr="";
		 		for(i=0;i<(4-tmpstr[0].length);i++){
		 			zerostr+="0";
		 		}
		 		tmpstr[0]=zerostr+tmpstr[0];
		 		zerostr="";
		 		for(j=0;j<(4-tmpstr[1].length);j++){
		 		    zerostr+="0";
		 		}
		 		tmpstr[1]=zerostr+tmpstr[1];
		 		obj2.value=tmpstr[0]+"-"+tmpstr[1];
		 	}else{
		 	    if(tmpstr[0].length==8){
		 	    	obj2.value=tmpstr[0].substring(0,4)+"-"+tmpstr[0].substring(4);
		 	    }else{
		 	    	var zerostr="";
		 			for(i=0;i<(4-tmpstr[0].length);i++){
		 				zerostr+="0";
		 			}
		 			obj2.value=zerostr+tmpstr[0]+"-0000";
		 		}	
		 	}
		 }else if((obj.value=="D")||(obj.value=="E")||(obj.value=="F")){
		 	var tmpstr=obj2.value.split("-");
		 	if(tmpstr.length==2){
		 	    var zerostr="";
		 		for(i=0;i<(5-tmpstr[0].length);i++){
		 			zerostr+="0";
		 		}
		 		tmpstr[0]=zerostr+tmpstr[0];
		 		zerostr="";
		 		for(j=0;j<(3-tmpstr[1].length);j++){
		 		    zerostr+="0";
		 		}
		 		tmpstr[1]=zerostr+tmpstr[1];
		 		obj2.value=tmpstr[0]+"-"+tmpstr[1];
		 	}else{
		 	    if(tmpstr[0].length==8){
		 	    	obj2.value=tmpstr[0].substring(0,5)+"-"+tmpstr[0].substring(5);
		 	    }else{
		 	    	var zerostr="";
		 			for(i=0;i<(5-tmpstr[0].length);i++){
		 				zerostr+="0";
		 			}
		 			obj2.value=zerostr+tmpstr[0]+"-000";
		 		}	
		 	}
		 }
	}
}

function openUploadWindow(popFileID, popFileName){
	if (isObj(eval("form1."+popFileID))) {
	    var prop='';
	    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=no,scrollbars=yes,resizable=yes,';
	    prop=prop+'width=450,';
	    prop=prop+'height=160';
	    window.open('home/popUploadSimple.jsp?popFileID=' + popFileID + '&popFileName='+popFileName,'上傳檔案',prop);
	} else {
		alert("欄位不存在,請檢查!");
		return ;
	}
}

function openDownloadWindow(fileID, fileName){
    var prop='';
    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
    prop=prop+'width=400,';
    prop=prop+'height=400';
	if (parse(fileID).length>0) {
		var arrFileName = fileID.split(":;:");
		if (arrFileName.length>1) {
		    var url = "downloadFileSimple?fileID=" + fileID;    
		    window.open(url,'popDownload',prop);
		} else {
			alert("無法下載該檔案，因為檔案資訊不完整，若問題持續，請洽系統管理!");
		}		
	} else if (parse(fileName).length>0) {
	    var url = "downloadFileSimple?fileName=" + fileName;    
		window.open(url,'popDownload',prop);		
	} else {
		alert("目前沒有任何檔案可供下載!");	
	}
}

function addLoadEventListener(func) {
  	var oldonload = document.onload;
	document.onload = function() {
		oldonload();			
		func();
	}
}

function setAutocompleteOff(strField, autoFlag) {	
	if (strField.trim().length>0) {
		var arrField = strField.split(",");
		if (arrField.length>0) {
		 	for(var i=0; i<arrField.length; i++){
		 		if (isObj(document.all.item(arrField[i]))) {		 		  	
					obj = document.all.item(arrField[i]);
					if (autoFlag=="on") obj.setAttribute('AutoComplete','on');
					else obj.setAttribute('AutoComplete','off');				
				}
			}
		}
	}
}

/*submit之前將select打開*/
function beforeSubmit(){
	for(var i=0; i<document.forms[0].elements.length; i++){  			
		obj = document.forms[0].elements[i];
		if((obj.type=="select-one")||(obj.type=="select-multiple")||(obj.type=="select")||(obj.type=="checkbox")||(obj.type=="radio")) {		
			obj.disabled = false;
		}
	}
}

/**
for dsFieldLookup
*/
function changeDsField(q,targetObj,selectValue) {	
	if (isObj(document.all.item(targetObj))) {
		var x = getRemoteData('../../ajax/jsonDsFieldLookup.jsp',q);		
		var obj3 = document.all.item(targetObj);
		obj3.type = "select-one";
		obj3.options.length=0;
		var obj3Option = new Option("請選擇","");		
		obj3.options.add(obj3Option);
				
		var fd = eval('(' + x + ')'); 
		var i;
		for (i=0; i<fd.length; i++) {
			var flKeyField =  fd[i].flKeyField;			
			var oOption = new Option(fd[i].flDisField,flKeyField);
			obj3.options.add(oOption);
	    	if(flKeyField == selectValue) oOption.selected=true;			
		}
	}else{
		alert("主表單查詢欄位設定之對應關係出錯，請重新執行！\n\n若問題持續，請洽相關承辦人員或系統建置廠商。\n");
	}
}

/**
**/
function changeAddr(q,targetObj,selectValue) {	
	if (isObj(document.all.item(targetObj))) {
		var x = getRemoteData('../../ajax/jsonAddr.jsp',q);		
		var obj3 = document.all.item(targetObj);
		obj3.options.length=0;
		var obj3Option = new Option("請選擇","");		
		obj3.options.add(obj3Option);
								
		var fd = eval('(' + x + ')'); 
		var i;
		for (i=0; i<fd.length; i++) {
			var fdKey =  fd[i].addrName;			
			var oOption = new Option(fdKey,fdKey);
			obj3.options.add(oOption);
	    	if(fdKey == selectValue) oOption.selected=true;			
		}
	}else{		
		alert("發生未預期的錯誤，請重新執行！\n\n若問題持續，請洽相關承辦人員或系統建置廠商。\n");
	}
}

function genJsonObjString(){
	var errorflag=false;
	var q = new Object();
	for(var i=0; i<document.forms[0].elements.length; i++){
		obj = document.forms[0].elements[i];
		if ((obj.value=="") && (obj.type!="button")){
		} else {
			q[obj.name]=obj.value;
		}
	}
	return q;
}

function parseQueryOne(x) {
    if (x!=null && x.length>0) {
    	alert(x);
		var fd = eval('(' + x + ')'); 
        for (var i in fd){
          var field = document.getElementById(i);
          if(field !=null){
             if(typeof field.type!= 'undefined'){
                field.value=fd[i];
             } else{
                field.innerHTML=fd[i];
             }
          }
        }   
    }
}

function formatMa48(obj,obj2){
	if(obj2.value!=""){
		var x="";
		var tmpstr=obj2.value;
		for(j=0;j<(4-tmpstr.length);j++){
			x+="0";
		}
		//alert(tmpstr);
		obj2.value=x+tmpstr;
		SelectItemByValue(obj,obj2.value);
	}
}

function formatMa01(obj,obj2){
	var alertStr="";
    if(obj2.value!=""){
		 if((obj.value=="C")||(obj.value=="F")){
		 	var tmpstr=obj2.value.split("-");
		 	if(tmpstr.length==2){
		 	    var zerostr="";
		 		for(i=0;i<(4-tmpstr[0].length);i++){
		 			zerostr+="0";
		 		}
		 		tmpstr[0]=zerostr+tmpstr[0];
		 		zerostr="";
		 		for(j=0;j<(3-tmpstr[1].length);j++){
		 		    zerostr+="0";
		 		}
		 		tmpstr[1]=zerostr+tmpstr[1];
		 		obj2.value=tmpstr[0]+"-"+tmpstr[1];
		 	}else{
		 	    if(tmpstr[0].length==7){
		 	    	obj2.value=tmpstr[0].substring(0,4)+"-"+tmpstr[0].substring(4);
		 	    }else{
		 	    	var zerostr="";
		 			for(i=0;i<(4-tmpstr[0].length);i++){
		 				zerostr+="0";
		 			}
		 			obj2.value=zerostr+tmpstr[0]+"-000";
		 		}	
		 	}
		 }else{
		 	var zerostr="";
		 	for(i=0;i<(4-(obj2.value.length));i++){
		 		zerostr+="0";
		 	}
		 	obj2.value=zerostr+obj2.value;
		 }
	}
}

function nNumFormat(tValue){
	var reValue=0;
	if(tValue!=""){
	   for(i = tValue.length ; i > 0;i--){  
		tValue = tValue.replace(/,/i,"")  ;
	   }
	   reValue=tValue;
	}
	return reValue;
}


//將回上頁 的功能 Disabled，這是一個很差的處理方式，若有其它解決辦法，請用別的
window.history.forward(1);

