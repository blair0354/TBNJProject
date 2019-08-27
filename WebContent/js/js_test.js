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
function popCalndar(dateField,js){
	if (isIE()) {
		var prop="";
		prop=prop+"scroll:yes;status:no;help:no;";
		prop=prop+"dialogWidth:210px;";
		prop=prop+"dialogHeight:240px";		
		var strDate=window.showModalDialog(getVirtualPath() + 'home/calendar.html',"",prop);
		if (isObj(strDate)){ 
			document.all.item(dateField).value=strDate;
			if (js!=null && js.length>0)	eval(js);
		}		
	} else {
		prop="modal=yes,";
		var windowTop=(document.body.clientHeight-400)/2+100;
		var windowLeft=(document.body.clientWidth-400)/2+100;
		prop=prop+"width=210height=240,";
		prop=prop+"top="+windowTop+",";
		prop=prop+"left="+windowLeft+",";
		prop=prop+"scrollbars=no";	
		var strDate=window.open(getVirtualPath() + 'calendar_adv.htm',"",prop);
		if (isObj(strDate)){ 
			document.all.item(dateField).value=strDate;
			if (js!=null && js.length>0)	eval(js);
		}		
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
//函數功能：pop財產編號輔助視窗,並回傳財產編號及名稱
//參　　數：popPropertyNo顯示財產編號的欄位名稱; popPropertyName顯示財產編號名稱的欄位名稱; preWord 財產編號前置詞
//傳 回 值：無
//*********************************************
function popProperty(popPropertyNo,popPropertyName,preWord){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popProperty.jsp?popPropertyNo="+popPropertyNo+"&popPropertyName="+popPropertyName+"&preWord="+preWord,"",prop);
}

//*********************************************
//函數功能：pop廠商輔助視窗,並回傳廠商編號及名稱
//參　　數：popStoreNo顯示廠商代碼的欄位名稱; popStoreName顯示廠商名稱的欄位名稱
//傳 回 值：無
//*********************************************
function popStore(popStoreNo,popStoreName){
	var prop="";
	prop=prop+"width=630,height=450,";
	prop=prop+"top=150,";
	prop=prop+"left=300,";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../unt/ba/untba001f.jsp?popStoreNo="+popStoreNo+"&popStoreName="+popStoreName,"",prop);
}

//*********************************************
//函數功能：pop文號輔助視窗,並回傳文號編號及名稱
//參　　數：popDocNo顯示文號代碼的欄位名稱; popDocName顯示文號名稱的欄位名稱
//傳 回 值：無
//*********************************************
function popDocNo(popDocNo,popDocName){
	var prop="";
	prop=prop+"width=630,height=450,";
	prop=prop+"top=150,";
	prop=prop+"left=300,";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../unt/ba/untba002f.jsp?popDocNo="+popDocNo+"&popDocName="+popDocName,"",prop);
}

//*********************************************
//函數功能：pop保管單位輔助視窗,並回傳文號編號及名稱
//參　　數：popUnitNo顯示保管單位代碼的欄位名稱; popUnitName顯示保管單位名稱的欄位名稱
//傳 回 值：無
//*********************************************
function popKeepUnit(popUnitNo,popUnitName){
	var prop="";
	prop=prop+"width=630,height=450,";
	prop=prop+"top=150,";
	prop=prop+"left=300,";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../unt/ba/untba003f.jsp?popUnitNo="+popUnitNo+"&popUnitName="+popUnitName,"",prop);
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
		} catch(e) {
		}
	}
	//if (isObj(form1.permissionField)) doPermission(form1.permissionField.value); 
	return false;
} 

//*********************************************
//函數功能：權限按鈕設定
//*********************************************
function doPermission(s) {
	try {
		if (s!=null && s=="1") setDisplayItem('spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,btnMaintain,btnMaintain1,btnMaintain2,btnMaintain3,btnMaintain4,btnMaintain5,approveOne,approveAll','H');
		if (s!=null && s=="2") setDisplayItem('approveOne,approveAll','H');
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
function getToday(){
	myDate = new Date();
	myYear = (myDate.getYear()-1911).toString();	
	myMonth = (myDate.getMonth()+1).toString();
	myDay = myDate.getDate().toString();

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


function lpad(vstr,strlen,vfixstr)
{
  var vstrlen = vstr.length;
  var text = vstr;
  while (vstrlen < strlen)
  {
    if (vstrlen == strlen) 	break; 	
  	else {
	  text =  vfixstr + text;
	  vstrlen = vstrlen + vfixstr.length;
	}
  }
  if (vstr.length == 0) {
    for (i=0;i < strlen;i++) {
     text = text + vfixstr;
	}
  }
  return (text);
}

function rpad(vstr,strlen,vfixstr){
  var vstrlen = vstr.length;
  var text = vstr;
  if (typeof(vstr)=="undefined"){
	  vstr = '';
  }
  while (vstrlen < strlen){
    if (vstrlen == strlen){
 	   break;
 	}else {
	  text =   text + vfixstr ;
	  vstrlen = vstrlen + vfixstr.length
	}
  }
  return (text);
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
	}
	
	//清除addr3
	if (isObj(document.all.item(addr3))) {		
		var obj3 = document.all.item(addr3);
		obj3.options.length=0;
		obj3.options.add(new Option("請選擇",""));
		
		if (queryValue!=""){
			var x = getRemoteData(getVirtualPath() + "home/jsonAddr.aspx?addr1="+queryValue);
			var sign = eval('(' + x + ')'); 
			var i;
			for (i=0; i<sign.length; i++) {
				var addr =  sign[i].addrID;			
				var oOption = new Option(sign[i].addrName,addr);
				obj2.options.add(oOption);
		    	if(addr == selectValue) oOption.selected=true;			
			}
		}			
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
		var obj3Option = new Option("請選擇","");		
		obj3.options.add(obj3Option);				
		if (queryValue!=""){
			var x = getRemoteData(getVirtualPath() + "home/jsonAddr.aspx?addr2="+queryValue);
			var sign = eval('(' + x + ')'); 
			var i;
			for (i=0; i<sign.length; i++) {
				var addr =  sign[i].addrID;			
				var oOption = new Option(sign[i].addrName,addr);
				obj3.options.add(oOption);
		    	if(addr == selectValue) oOption.selected=true;			
			}
		}					
	}	
}


//*********************************************
//函數功能：用途計畫, 改變第一層
//參　　數：no1業務計劃; no2工作計劃;no3分支計劃;selectValue所選的值;bugCode
//傳 回 值：無
//*********************************************
function changeOBUGCode1(no1,no2,no3,selectValue,bugCode){
	var queryValue=document.all.item(no1).value;	
	
	if (bugCode!=null && isObj(document.all.item(bugCode))) {
	    document.all.item(bugCode).value = rpad(queryValue,6,'0');
	}

	//清除no2
	if (isObj(document.all.item(no2))) {		
		var obj2 = document.all.item(no2);
		obj2.options.length=0;
		obj2.options.add(new Option("請選擇",""));
	}
		
	if (isObj(document.all.item(no3))) {			
	    //清除no3
		var obj3 = document.all.item(no3);
		obj3.options.length=0;
		obj3.options.add(new Option("請選擇",""));
		
		if (queryValue!=""){
			var x = getRemoteData(getVirtualPath() + "home/jsonOBUGCode.aspx?no1="+queryValue);
			var jsonObj = eval('(' + x + ')'); 
			var i;
			for (i=0; i<jsonObj.length; i++) {
				var no =  jsonObj[i].shortCode;			
				var oOption = new Option(jsonObj[i].codeName,no);
				obj2.options.add(oOption);
		    	if(no == selectValue) {
		    	    oOption.selected=true;
		    	    if (bugCode!=null && isObj(document.all.item(bugCode))) document.all.item(bugCode).value = jsonObj[i].fullCode;
		    	}
			}
		}			
	}	
}

//*********************************************
//函數功能：用途計畫, 改變第二層
//參　　數：no1業務計劃; no2工作計劃;no3分支計劃;selectValue所選的值;bugCode全碼
//傳 回 值：無
//*********************************************
function changeOBUGCode2(no1,no2,no3,selectValue,bugCode){
    var code1=document.all.item(no1).value;	
	var queryValue=document.all.item(no2).value;
	
	if (bugCode!=null && isObj(document.all.item(bugCode))) {
	    document.all.item(bugCode).value = rpad(code1+queryValue,6,'0');
	}
			
	//清除no3
	if (isObj(document.all.item(no3))) {		
		var obj3 = document.all.item(no3);
		obj3.options.length=0;
		var obj3Option = new Option("請選擇","");		
		obj3.options.add(obj3Option);				
		if (queryValue!=""){
			var x = getRemoteData(getVirtualPath() + "home/jsonOBUGCode.aspx?no1="+code1+"&no2="+queryValue);
			var jsonObj = eval('(' + x + ')'); 
			var i;
			for (i=0; i<jsonObj.length; i++) {
				var no =  jsonObj[i].shortCode;			
				var oOption = new Option(jsonObj[i].codeName,no);
				obj3.options.add(oOption);
		    	if(no == selectValue)  {
		    	    oOption.selected=true;			
		    	    if (bugCode!=null && isObj(document.all.item(bugCode))) document.all.item(bugCode).value = jsonObj[i].fullCode;
		    	}
			}
		}					
	}	
}

//*********************************************
//函數功能：用途計畫, 改變第三層
//參　　數：no1業務計劃; no2工作計劃;no3分支計劃;bugCode全碼
//傳 回 值：無
//*********************************************
function changeOBUGCode3(no1,no2,no3,bugCode){
    if (bugCode!=null && isObj(document.all.item(bugCode))) {
        var code1=document.all.item(no1).value;
        var code2=document.all.item(no2).value;
	    var code3=document.all.item(no3).value;    
	    var obj = document.all.item(bugCode);
	    if (code3!="") obj.value = code1+code2+code3;
	    else obj.value = rpad(code1+code2,6,'0');
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
	var sdate, rdate;
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
			rdate = new Date(intYear, intMon+intNumber, intDay);
		} else if (strType=="y") {
			rdate = new Date(intYear+intNumber, intMon, intDay);
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


function openUploadWindow(popFileID, popFileName){
	if (isObj(eval("form1."+popFileID))) {
	    var prop='';
	    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=no,scrollbars=yes,resizable=yes,';
	    prop=prop+'width=450,';
	    prop=prop+'height=160';
	    window.open(getVirtualPath()+'home/popUploadSimple.aspx?popFileID=' + popFileID + '&popFileName='+popFileName,'上傳檔案',prop);
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
		    var url = "../../downloadFileSimple?fileID=" + fileID;    
		    window.open(url,'popDownload',prop);
		} else {
			alert("無法下載該檔案，因為檔案資訊不完整，若問題持續，請洽系統管理!");
		}		
	} else if (parse(fileName).length>0) {
	    var url = "../../downloadFileSimple?fileName=" + fileName;    
		window.open(url,'popDownload',prop);		
	} else {
		alert("目前沒有任何檔案可供下載!");	
	}
}

//非公用裡常用到, 所以放一個在這裡
function selectProperty(intType) {
	var propertyType = "";
	if (parse(form1.q_propertyType.value).length>0) propertyType=form1.q_propertyType.value;
	else propertyType = "1,2";
		
	switch (intType) {
		case "1":
			popProperty('q_propertyNoS','q_propertyNoSName',propertyType);
			break;
		case "2":
			popProperty('q_propertyNoE','q_propertyNoEName',propertyType);
			break;
	}
}


function popBulletinDate(popField,preWord,isLookup,js){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popBulletinDate.jsp?popField="+popField+"&preWord="+preWord+"&isLookup="+isLookup+"&js="+js,"",prop);
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


//*********************************************
//程式功能：將field_must_default欄位設為readonly
//參　　數：allobj集合物件
//*********************************************
function controlEdit(allobj){		
    for( var i=0; i<allobj.length; i++){
		if ( (allobj(i).className=="field_P")||(allobj(i).className=="btn_PKey") ){
			allobj(i).className="field_RO";			
			allobj(i).onblur="";
			allobj(i).onfocus="";
			if ((allobj(i).type=="text")||(allobj(i).type=="textarea")){				
				allobj(i).readOnly=true;
			}else if ((allobj(i).type=="select-one")||(allobj(i).type=="select-multiple")){	
				allobj(i).disabled=true;
			}else if ((allobj(i).type=="checkbox")||(allobj(i).type=="radio")){
				allobj(i).disabled=true;
			}else{
				allobj(i).disabled=true;
				allobj(i).style.display="none";
			}
		}
	} 
}

//*********************************************
//程式功能：將所有欄位設為readonly
//參　　數：allobj集合物件
//備　　註：btnClose為例外
//*********************************************
function controlRead(allobj){	
	for( var i=0; i<allobj.length; i++){
		if (allobj(i).className!="field_showname"){
			if ((allobj(i).type=="text")||(allobj(i).type=="textarea")){
				allobj(i).readOnly=true;
				allobj(i).className="field_RO";
				allobj(i).onblur="";
				allobj(i).onfocus="";
			}else if ((allobj(i).type=="select")||(allobj(i).type=="select-one")||(allobj(i).type=="select-multiple")){
				allobj(i).disabled=true;
				allobj(i).className="field_RO";
				allobj(i).onblur="";
				allobj(i).onfocus="";				
			}else if ((allobj(i).type=="checkbox")||(allobj(i).type=="radio")){
				allobj(i).disabled=true;
				allobj(i).className="field_RO";
				allobj(i).onblur="";
				allobj(i).onfocus="";
			}else{
				allobj(i).disabled=true;
				allobj(i).style.display="none";
			}
		}
	} 
	if (isObj(document.all.item("btnClose"))){	//關閉按鈕
		document.all.item("btnClose").disabled=false;	
		document.all.item("btnClose").style.display="inline";	
	}
	if (isObj(document.all.item("btnPrint"))){	//列印按鈕
		document.all.item("btnPrint").disabled=false;	
		document.all.item("btnPrint").style.display="inline";	
	}	
		
}


function popCode(popKind,popCode,popCodeName,js){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+100;
	var windowLeft=(document.body.clientWidth-400)/2+100;
	prop=prop+"width=550,height=420,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	if (js==null) js = "";
	returnWindow=window.open(getVirtualPath() + "home/popCode.aspx?popKind="+popKind+"&popCode="+popCode+"&popCodeName="+popCodeName+"&js="+js,"",prop);
}

function getCodeName(codeKindID,codeID,targetObj) {
    if (isObj(targetObj)) {
        var x = getRemoteData(getVirtualPath() + 'home/jsonCode.aspx?codeKindID='+codeKindID+'&codeID='+codeID);
        if ((x==null) || (x=="")) {        
            alert("查無該代碼資料,若有問題, 請用輔助視窗輸入!");
        } else targetObj.value = x;
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
	return genJsonObjString();
	//if (isObj(form1.jsonObjString)) form1.jsonObjString.value = q.toJSONString();
}

function parseQueryOne(x) {
    if (x!=null && x.length>0) {
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


function getURLParameter( url, name )
{
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var results = regex.exec( url );
  if( results == null )
    return "";
  else
    return results[1];
}

function setLength(obj, len){
	obj.setAttribute('maxLength',len);
}


Number.prototype.toFixed=function(len)
{
    var add = 0;
    var s,temp;
    var s1 = this + "";
    var start = s1.indexOf(".");
    if(s1.substr(start+len+1,1)>=5)add=1;
    var temp = Math.pow(10,len);
    s = Math.floor(this * temp) + add;
    return s/temp;
}

function addCommas( s ) { 
    if (s!=null && s.length>0) {
        var sValue = s;
        var r = "";
        var arr = s.split(".");
        if (arr!=null && arr.length>0) {
            sValue = arr[0];
            if (arr.length>1) r = "." + arr[1];
	        var sRegExp = new RegExp('(-?[0-9]+)([0-9]{3})'); 
	        while(sRegExp.test(sValue)) { 
		        sValue = sValue.replace(sRegExp, '$1,$2'); 
	        }
	        return sValue + r;            
        }
    }
	return sValue;
}


//將回上頁 的功能 Disabled，這是一個很差的處理方式，若有其它解決辦法，請用別的
window.history.forward(1);
