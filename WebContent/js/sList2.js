//*********************************************
//函數功能：資料列表中的Check box, 可以全選, 單選, 或全部取消選取
//*********************************************
    function Toggle(e, fname, sid)
    {
	if (e.checked) {
	    Highlight(e);
	    fname.toggleAll.checked = AllChecked(fname, sid);
	}
	else {
	    Unhighlight(e);
	    fname.toggleAll.checked = false;
	}
    }

    function ToggleAll(e, fname, sid)
    {
	if (e.checked) {
	    CheckAll(fname, sid);
	}
	else {
	    ClearAll(fname, sid);
	}
    }

    function Check(e)
    {
	e.checked = true;
	Highlight(e);
    }

    function Clear(e)
    {
	e.checked = false;
	Unhighlight(e);
    }

    function CheckAll(fname, sid)
    {
	var ml = fname;
	var len = ml.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = ml.elements[i];
	    if (e.name == sid) {
		Check(e);
	    }
	}
	ml.toggleAll.checked = true;
    }

    function ClearAll(fname, sid)
    {
	var ml = fname;
	var len = ml.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = ml.elements[i];
	    if (e.name == sid) {
		Clear(e);
	    }
	}
	ml.toggleAll.checked = false;
    }

    function Highlight(e)
    {
	var r = null;
	if (e.parentNode && e.parentNode.parentNode) {
	    r = e.parentNode.parentNode;
	}
	else if (e.parentElement && e.parentElement.parentElement) {
	    r = e.parentElement.parentElement;
	}
	if (r) {
	    if (r.className == "newHighlight") {
		r.className = "newHighlights";
	    }
	    else if (r.className == "highLight") {
		r.className = "highLights";
	    }
	}
    }

    function Unhighlight(e)
    {
	var r = null;
	if (e.parentNode && e.parentNode.parentNode) {
	    r = e.parentNode.parentNode;
	}
	else if (e.parentElement && e.parentElement.parentElement) {
	    r = e.parentElement.parentElement;
	}
	if (r) {
	    if (r.className == "newHighlights") {
		r.className = "newHighlight";
	    }
	    else if (r.className == "highLights") {
		r.className = "highLight";
	    }
	}
    }

    function AllChecked(fname, sid)
    {
	ml = fname;
	len = ml.elements.length;
	for(var i = 0 ; i < len ; i++) {
	    if (ml.elements[i].name == sid && !ml.elements[i].checked) {
		return false;
	    }
	}
	return true;
    }

//*********************************************
//函數功能：找出UNTLA_LAND資料表裡的propertyNo,propertyName,serialNo
//參　　數：strType, PN=以財產編號做Lookup, 其它以土地標示代碼做Lookup
//dataState:資料狀態, verify:審核, closing:月結, proofVerify:權狀審核
//enterOrg,ownership,signNo1,signNo2,signNo3,signNo4,signNo5
//*********************************************
function getPropertyNo(strType, dataState, verify, closing, proofVerify){
	var alertStr = "", chkStr="";
	var i = 0, j=0;
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	if (strType=="PN") {
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		if (form1.propertyNo.value=="" || form1.serialNo.value=="") {
			form1.propertyNo.style.backgroundColor="";
			form1.serialNo.style.backgroundColor="";					
		}
	} else {
		if ((form1.signNo1.value=="")||(form1.signNo2.value=="")||(form1.signNo3.value=="")||(form1.signNo4.value=="")||(form1.signNo5.value=="")){
			alertStr += "土地標示代碼不得為空白!\n";
		}
	}
		
	if(alertStr.length!=0){
	/**
		if (isObj(form1.propertyNo)) { form1.propertyNo.value=""; }
		if (isObj(form1.propertyName)) { form1.propertyName.value=""; }
		if (isObj(form1.propertyNoName)) { form1.propertyNoName.value=""; }		
		if (isObj(form1.dataState)) { form1.dataState.value="";	}
		if (isObj(form1.dataStateName)) { form1.dataStateName.value="";	}		
		if (isObj(form1.oldPropertyNo)) { form1.oldPropertyNo.value=""; }		
		if (isObj(form1.oldSerialNo)) { form1.oldSerialNo.value="";	}			
		if (isObj(form1.oldUseSeparate)) { form1.oldUseSeparate.value=""; }
		if (isObj(form1.oldUseSeparateName)) { form1.oldUseSeparateName.value=""; }		
		if (isObj(form1.oldUseKind)) { form1.oldUseKind.value="";}
		if (isObj(form1.oldUseKindName)) {form1.oldUseKindName.value=""; }		
		if (isObj(form1.oldField)) { form1.oldField.value="";	}
		if (isObj(form1.oldFieldName)) {form1.oldFieldName.value=""; }		
	**/
		return false;
	} else {	
		var enterOrg = form1.enterOrg.value;
		var ownership=form1.ownership.value;
		var sNo1=form1.signNo1.value;
		var sNo2=form1.signNo2.value;
		var sNo3=form1.signNo3.value;
		var sNo4=form1.signNo4.value;
		var sNo5=form1.signNo5.value;	
		var sPropertyNo = form1.propertyNo.value;
		var sPropertyName;
		var sSerialNo = form1.serialNo.value;
		
		if (isObj(form1.propertyName)) { sPropertyName = form1.propertyName.value; }						
		if (isObj(form1.propertyNoName)) { sPropertyName = form1.propertyNoName.value; }
		
		var sSignNo = "";
		var queryValue = "";
		if (strType=="PN") {		
			queryValue = "enterOrg="+enterOrg+"&ownership="+ownership+"&sType=PN&propertyNo="+sPropertyNo+"&propertyNoName="+sPropertyName+"&serialNo="+sSerialNo;
		} else {
			queryValue = "enterOrg="+enterOrg+"&ownership="+ownership+"&sType=SN&sNo1="+sNo1+"&sNo2="+sNo2+"&sNo3="+sNo3+"&sNo4="+sNo4+"&sNo5="+sNo5;
		}
		if (dataState=="1"||dataState=="2") queryValue += "&dataState=" + dataState;
		if (verify=="Y"||verify=="N") queryValue += "&verify=" + verify;
		if (parse(""+closing).length>0) queryValue += "&closing" + closing;
		if (parse(""+proofVerify).length>0) queryValue += "&proofVerify" + proofVerify;
		
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlUntlaLand.jsp?"+queryValue)){
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
				sSignNo = xmlDoc.documentElement.childNodes.item(i).getAttribute("signNo");			
				if (isObj(form1.signNo1)) {
					for (j = 0; j < form1.signNo1.options.length; j++) {						
						if (form1.signNo1.options[j].value==sSignNo.substring(0,1)+"000000") {
							form1.signNo1.options[j].selected=true;							
						}
					}
				}
				if (isObj(form1.signNo2)) {
					changeSignNo1('signNo1','signNo2','signNo3',sSignNo.substring(0,3)+'0000');					
				}
				if (isObj(form1.signNo3)) {
					changeSignNo2('signNo1','signNo2','signNo3',sSignNo.substring(0,7))						
				}		
				if (isObj(form1.signNo4)) {		
					form1.signNo4.value=sSignNo.substring(7,11);
				}										
				if (isObj(form1.signNo5)) {
					form1.signNo5.value=sSignNo.substring(11,15);
				}												
				form1.propertyNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyNo");
				form1.serialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("serialNo");
				if (isObj(form1.propertyName)) {
					form1.propertyName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName");
				}				
				if (isObj(form1.propertyNoName)) {
					form1.propertyNoName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName");
				}
				if (isObj(form1.propertyName1)) {
					form1.propertyName1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName1");
				}
				if (isObj(form1.dataState))	 {
					form1.dataState.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("dataState");
				}
				if (isObj(form1.dataStateName))	 {
					form1.dataStateName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("dataStateName");
				}				
				if (isObj(form1.oldPropertyNo))	 {
					form1.oldPropertyNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldPropertyNo");
				}
				if (isObj(form1.oldSerialNo))	 {
					form1.oldSerialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldSerialNo");
				}												
				if (isObj(form1.propertyKind))	 {
					form1.propertyKind.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyKind");
				}
				if (isObj(form1.propertykindName))	 {
					form1.propertykindName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertykindName");
				}												
				if (isObj(form1.fundType))	 {
					form1.fundType.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("fundType");
				}
				if (isObj(form1.fundTypeName))	 {
					form1.fundTypeName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("fundTypeName");
				}												
				if (isObj(form1.valuable))	 {
					form1.valuable.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("valuable");
				}												
				if (isObj(form1.taxCredit))	 {
					form1.taxCredit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("taxCredit");
				}												
				if (isObj(form1.grass))	 {
					form1.grass.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("grass");
				}												
				if (isObj(form1.grassName))	 {
					form1.grassName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("grassName");
				}											
				if (isObj(form1.originalBV))	 {
					form1.originalBV.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalBV");
				}
				//面積
				if (isObj(form1.area))	 {
					form1.area.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("area");
				}
				if (isObj(form1.holdNume))	 {
					form1.holdNume.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdNume");
				}	
				if (isObj(form1.holdDeno))	 {
					form1.holdDeno.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdDeno");
				}
				if (isObj(form1.holdArea))	 {
					form1.holdArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");
				}
				//舊面積
				if (isObj(form1.oldArea))	 {
					form1.oldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("area");
				}
				if (isObj(form1.oldHoldNume))	 {
					form1.oldHoldNume.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdNume");
				}	
				if (isObj(form1.oldHoldDeno))	 {
					form1.oldHoldDeno.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdDeno");
				}
				if (isObj(form1.oldHoldArea))	 {
					form1.oldHoldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");
				}
				//新面積
				if (isObj(form1.newArea))	 {
					form1.oldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("area");
				}
				if (isObj(form1.newHoldNume))	 {
					form1.oldHoldNume.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdNume");
				}	
				if (isObj(form1.newHoldDeno))	 {
					form1.oldHoldDeno.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdDeno");
				}
				if (isObj(form1.newHoldArea))	 {
					form1.oldHoldArea.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("holdArea");
				}
				
				if (isObj(form1.accountingTitle))	 {
					form1.accountingTitle.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("accountingTitle");
				}
				//帳面價值
				if (isObj(form1.bookUnit))	 {
					form1.bookUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookUnit");
				}		
				if (isObj(form1.bookValue))	 {
					form1.bookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
				}
				//舊帳面價值
				if (isObj(form1.oldBookUnit))	 {
					form1.oldBookUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookUnit");
				}		
				if (isObj(form1.oldBookValue))	 {
					form1.oldBookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
				}
				//新帳面價值
				if (isObj(form1.newBookUnit))	 {
					form1.oldBookUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookUnit");
				}		
				if (isObj(form1.newBookValue))	 {
					form1.oldBookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
				}
				if (isObj(form1.proofDoc))	 {
					form1.proofDoc.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proofDoc");
				}
				if (isObj(form1.ownershipDate))	 {
					form1.ownershipDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("ownershipDate");
				}				
				if (isObj(form1.sourceDate))	 {
					form1.sourceDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("sourceDate");
				}
				if (isObj(form1.useState))	 {						
					form1.useState.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useState");		
				}
				if (isObj(form1.useStateName))	 {
					form1.useStateName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useStateName");
				}
				if (isObj(form1.useSeparate))	 {
					form1.useSeparate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useSeparate");
				}
				if (isObj(form1.useSeparateName))	 {
					form1.useSeparateName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useSeparateName");
				}
				if (isObj(form1.useKind))	 {
					form1.useKind.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useKind");
				}
				if (isObj(form1.useKindName))	 {
					form1.useKindName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useKindName");
				}
				if (isObj(form1.field))	 {
					form1.field.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("field");
				}
				if (isObj(form1.fieldName))	 {
					form1.fieldName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("fieldName");
				}
				if (isObj(form1.originalBasis))	 {
					form1.originalBasis.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalBasis");
				}
				if (isObj(form1.originalDate))	 {
					form1.originalDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalDate");
				}
				if (isObj(form1.originalUnit))	 {
					form1.originalUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalUnit");
				}
				if (isObj(form1.oldUseSeparate))	 {
					form1.oldUseSeparate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useSeparate");
				}
				if (isObj(form1.oldUseSeparateName))	 {
					form1.oldUseSeparateName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useSeparateName");
				}											
				if (isObj(form1.oldUseKind)) {
					form1.oldUseKind.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useKind");
				}
				if (isObj(form1.oldUseKindName))	 {
					form1.oldUseKindName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useKindName");
				}				
				if (isObj(form1.oldField))	 {
					form1.oldField.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("field");
				}	
				if (isObj(form1.oldFieldName))	 {
					form1.oldFieldName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("fieldName");
				}	
				if (isObj(form1.check)) {
					form1.check.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("check");
				}

				if (isObj(form1.newUseSeparate)) {
					for (j = 0; j < form1.newUseSeparate.options.length; j++) {						
						if (form1.newUseSeparate.options[j].value==xmlDoc.documentElement.childNodes.item(i).getAttribute("useSeparate")) {
							form1.newUseSeparate.options[j].selected=true;
						}
					}
				}

				if (isObj(form1.newUseKind)) {
					for (j = 0; j < form1.newUseKind.options.length; j++) {
						if (form1.newUseKind.options[j].value==xmlDoc.documentElement.childNodes.item(i).getAttribute("useKind")) {
							form1.newUseKind.options[j].selected=true;
						}
					}
				}

				if (isObj(form1.newField)) {
					for (j = 0; j < form1.newField.options.length; j++) {
						if (form1.newField.options[j].value==xmlDoc.documentElement.childNodes.item(i).getAttribute("field")) {
							form1.newField.options[j].selected=true;
						}
					}
				}
				
				if (isObj(form1.proceedType)) {
					for (j = 0; j < form1.proceedType.options.length; j++) {
						if (form1.proceedType.options[j].value==xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedType")) {
							form1.proceedType.options[j].selected=true;
						}
					}					
				}
				if (isObj(form1.proceedTypeName))	 {
					form1.proceedTypeName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedTypeName");
				}	
				if (isObj(form1.proceedDateS))	 {
					form1.proceedDateS.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedDateS");
				}	
				if (isObj(form1.proceedDateE))	 {
					form1.proceedDateE.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("proceedDateE");
				}							
				if (isObj(form1.doorplate))	 {
					form1.doorplate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("doorplate");
				}											
			}
		}else{
			alert('Cant Load It!');
			return false;	
		}
	}	
}

//*********************************************
//函數功能：用enterOrg找出UNTMP_KeepUnit裡的unitNo, unitName
//參    數：(enterOrg, fieldName) 為 obj型態, selectedValue為文字值型態
//回　　傳：若有資料會回傳一個Option List於fieldName這個SE欄位中
//*********************************************
function getKeepUnit(enterOrg, fieldName, selectedValue) {
	var alertStr = "";	
	var organID = parse(enterOrg.value);
	if (isObj(fieldName)) {		
		var obj2 = fieldName;
		obj2.options.length=0;
		var obj2Option = document.createElement("Option");
		obj2.options.add(obj2Option);
		obj2Option.innerText = "請選擇";
		obj2Option.value = "";	
	}				
	if (organID<=0) { 
		return false; 
	} else {
		//清除keeper
		var xmlDoc=document.createElement("xml");	
		xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlKeepUnit.jsp?enterOrg="+organID)){		
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(i=0; i<nodesLen; i++){
				unitNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitNo");
				unitName=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitName");
				var oOption = document.createElement("Option");	
				obj2.options.add(oOption);
				oOption.innerText = unitName;
				oOption.value = unitNo;		
		      	if(unitNo == selectedValue){
        			oOption.selected=true;
				}           										
			}
		}else{
			return false;	
		}			
	}	
}

//*********************************************
//函數功能：用enterOrg,unitNo找出UNTMP_Keeper裡的keeperNo, keeperName
//參    數：(enterOrg, unitNo, fieldName) 為 obj型態, selectedValue為文字值型態
//回　　傳：若有資料會回傳一個Option List於fieldName這個SE欄位中
//*********************************************
function getKeeper(enterOrg, unitNo, fieldName, selectedValue) {
	var alertStr = "";	
	var organID = parse(enterOrg.value);
	var uNo = parse(unitNo.value);
	if (isObj(fieldName)) {		
		var obj2 = fieldName;
		obj2.options.length=0;
		var obj2Option = document.createElement("Option");
		obj2.options.add(obj2Option);
		obj2Option.innerText = "請選擇";
		obj2Option.value = "";	
	}				
	if (organID<=0 || uNo.length<=0) { 
		return false; 
	} else {
		//清除keeper
		var xmlDoc=document.createElement("xml");	
		xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlKeeper.jsp?enterOrg="+organID+"&unitNo="+uNo)){		
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(i=0; i<nodesLen; i++){
				keeperNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("keeperNo");
				keeperName=xmlDoc.documentElement.childNodes.item(i).getAttribute("keeperName");
				var oOption = document.createElement("Option");	
				obj2.options.add(oOption);
				oOption.innerText = keeperName;
				oOption.value = keeperNo;		
		      	if(keeperNo == selectedValue){
        			oOption.selected=true;
				}           										
			}
		}else{
			return false;	
		}			
	}	
}

//***************************************************************
//函數功能：找出UNTLA_Value資料表裡的最大的 suitDateS ,valueUnit
//參　　數：以財產編號做Lookup,
//enterOrg,ownership,propertyNo,serialNo
//***************************************************************
function getValue(){
	var alertStr = "";
	var i = 0, j=0;
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	alertStr += checkEmpty(form1.propertyNo,"財產編號");
	alertStr += checkEmpty(form1.serialNo,"財產分號");
	
	if(alertStr.length!=0){
		form1.propertyNo.style.backgroundColor="";
		form1.serialNo.style.backgroundColor="";
		return false;
	} else {	
		var senterOrg = form1.enterOrg.value;
		var sownership=form1.ownership.value;	
		var sPropertyNo = form1.propertyNo.value;
		var sSerialNo = form1.serialNo.value;
		var originalDate = form1.originalDate.value;
		var originalBasis = form1.originalBasis.value;
		var originalUnit = form1.originalUnit.value;
	
		var bulletinDate = "";
		var valueUnit = "";
		var queryValue = "";	
		var differ = "";
		var q2 = "";
		queryValue = "enterOrg="+senterOrg+"&ownership="+sownership+"&propertyNo="+sPropertyNo+"&serialNo="+sSerialNo+"&originalDate="+originalDate+"&originalBasis="+originalBasis+"&originalUnit="+originalUnit;
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlUntlaValue.jsp?"+queryValue)){
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){						
				//最大公告日期 bulletinDate
				if (isObj(form1.bulletinDate)) {
					form1.bulletinDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bulletinDate");
				}
				//公告地價 valueUnit										
				if (isObj(form1.valueUnit))	 {
					form1.valueUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("valueUnit");
				}
			}												
		}else{
/**			alert('getvalue -- UNTLA_Value -- Cant Load It!');
**/
			return false;	
		}
	}	
}


//***************************************************************
//函數功能：找出UNTLA_Price資料表裡的priceUnit公告現值
//enterOrg,ownership,propertyNo,serialNo,bulletinDate
//***************************************************************
function getUNTLA_Price(enterOrg, ownership, propertyNo, serialNo, bulletinDate){
	var alertStr = "";
	var i = 0, j=0;
	var senterOrg="", sownership="", spropertyNo="", sserialNo="", sbulletinDate="";
	if (isObj(enterOrg.value)) senterOrg = enterOrg.value;
	else senterOrg = enterOrg;
	
	if (isObj(ownership.value)) sownership = ownership.value;
	else sownership = ownership;
	
	if (isObj(propertyNo.value)) spropertyNo = propertyNo.value;
	else spropertyNo = propertyNo;
	
	if (isObj(serialNo.value)) sserialNo = serialNo.value;
	else sserialNo = serialNo;
	
	if (isObj(bulletinDate.value)) sbulletinDate = bulletinDate.value;
	else sbulletinDate = bulletinDate;
	
	if (parse(senterOrg).length>0 && parse(sownership).length>0 && parse(spropertyNo).length>0 && parse(sserialNo).length>0) {
		if (parse(sbulletinDate)=="") sbulletinDate=getToday().substring(0,3)+"01";
		queryValue = "enterOrg="+senterOrg+"&ownership="+sownership+"&propertyNo="+spropertyNo+"&serialNo="+sserialNo+"&bulletinDate="+sbulletinDate;
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlUntlaPrice.jsp?"+queryValue)){
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
				if (isObj(form1.bulletinDate)) form1.bulletinDate.value = sbulletinDate;
				if (isObj(form1.priceUnit)) form1.priceUnit.value = xmlDoc.documentElement.childNodes.item(i).getAttribute("priceUnit");				
			}
		}else{
			alert('Failed to load xmlUNtlaPrice!');
			return false;
		}
	}	
}

//***************************************************************
//函數功能：找出UNTLA_Value資料表裡的valueUnit公告地價
//enterOrg,ownership,propertyNo,serialNo,bulletinDate
//***************************************************************
function getUNTLA_Value(){
	var alertStr = "";
	var i = 0, j=0;
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	alertStr += checkEmpty(form1.propertyNo,"財產編號");
	alertStr += checkEmpty(form1.serialNo,"財產分號");
			
	if(alertStr.length!=0){
		form1.ownership.style.backgroundColor="";
		form1.propertyNo.style.backgroundColor="";
		form1.serialNo.style.backgroundColor="";	
		return false;
	} else {	
		var senterOrg = form1.enterOrg.value;
		var sownership=form1.ownership.value;	
		var sPropertyNo = form1.propertyNo.value;
		var sSerialNo = form1.serialNo.value;
		var sBulletinDate = "";
		if (isObj(form1.bulletinDate)) sBulletinDate = form1.bulletinDate.value;
	
		if (parse(senterOrg).length>0 && parse(sownership).length>0 && parse(sPropertyNo).length>0 && parse(sSerialNo).length>0) {
			queryValue = "enterOrg="+senterOrg+"&ownership="+sownership+"&propertyNo="+sPropertyNo+"&serialNo="+sSerialNo+"&bulletinDate="+sBulletinDate;
			var xmlDoc=document.createElement("xml");		
			xmlDoc.async=false;			
			if(xmlDoc.load("../../home/xmlUntlaValue2.jsp?"+queryValue)){
				for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
					if (isObj(form1.bulletinDate)) form1.bulletinDate.value = xmlDoc.documentElement.childNodes.item(i).getAttribute("bulletinDate");
					if (isObj(form1.valueUnit)) form1.valueUnit.value = xmlDoc.documentElement.childNodes.item(i).getAttribute("valueUnit");		
					if (isObj(form1.suitDateS)) form1.suitDateS.value = xmlDoc.documentElement.childNodes.item(i).getAttribute("suitDateS");
					if (isObj(form1.suitDateE)) form1.suitDateE.value = xmlDoc.documentElement.childNodes.item(i).getAttribute("suitDateE");
				}
			}else{
				alert('Failed to load xmlUNtlaValue2!');
				return false;
			}		
		}
	}
}
