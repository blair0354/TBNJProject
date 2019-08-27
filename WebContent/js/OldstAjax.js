function OldstAjax(){

	var alertStr = "";
	var i = 0, j=0;
	var sFlag = false;
	alertStr += checkEmpty(form1.ow09,"身分證字號");
	/*
	if (form1.propertyNo.value=="" || form1.serialNo.value=="") {
		form1.propertyNo.style.backgroundColor="";
		form1.serialNo.style.backgroundColor="";
	}
	*/
	if(alertStr.length!=0){
	/**	if (isObj(form1.propertyNo)) { form1.propertyNo.value=""; }		
		if (isObj(form1.propertyName)) { form1.propertyName.value=""; }
		if (isObj(form1.serialNo)) { form1.serialNo.value=""; }	
	**/
		return false;
	} else {
		var ow09 = form1.ow09.value;
		var ow_lo = form1.ow_lo.value;
		var ow_seq = form1.ow_seq.value; 
		var queryValue = "";	

		queryValue = "ow09="+ow09;
		queryValue+="&ow_lo="+ow_lo;
		queryValue+="&ow_seq="+ow_seq;
		//alert(queryValue);
		//傳送一個亂數資料,防止瀏灠器由快取直接取得資料導至資料錯誤
		var randomnumber=Math.floor(Math.random()*1000);
		queryValue += "&randomnumber="+randomnumber;
		
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;		
		//alert(queryValue);
		//window.open("../../home/XmlOldst.jsp?"+queryValue);
		if(xmlDoc.load("../../home/XmlOldst.jsp?"+queryValue)){	
		//alert("1");
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
			
				sFlag = true;

				if (isObj(form1.ow_lo)) {
						form1.ow_lo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("ow_lo");
				}
				if (isObj(form1.ow_seq)) {
					form1.ow_seq.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("ow_seq");
				}
				if (isObj(form1.ow09)) {
					form1.ow09.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("ow09");
				}
				if (isObj(form1.ow_name)) {
					form1.ow_name.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("ow_name");
				}
				if (isObj(form1.ow_addr)) {
					form1.ow_addr.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("ow_addr");
				}
				if (isObj(form1.ow15_1)) {
					form1.ow15_1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("ow15_1");
				}
				if (isObj(form1.ow15_2)) {
					form1.ow15_2.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("ow15_2");
				}
				if (isObj(form1.ow15_3)) {
					form1.ow15_3.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("ow15_3");
				}
				
				
									
			}
			
		}else{
		/** alert('Cant Load It!'); **/
			return false;	
		}
	}	
}