
/**
for Organ Group Switch
*/
function switchOrganGroup(q,obj,targetObj,selectValue) {	
	if (isObj(targetObj)) {
		if (isObj(obj)) q += "&organID=" + obj.value;
		var x = getRemoteData('../../ajax/jsonGroup.jsp',q);		
		var obj3 = targetObj;
		obj3.type = "select-one";
		obj3.options.length=0;
		var obj3Option = new Option("請選擇","");	
		obj3.options.add(obj3Option);
				
		var fd = eval('(' + x + ')'); 
		var i;
		for (i=0; i<fd.length; i++) {
			var keyField =  fd[i].keyField;			
			var oOption = new Option(fd[i].disField,keyField);
			obj3.options.add(oOption);
	    	if(keyField == selectValue) oOption.selected=true;			
		}
	}else{
		alert("group field not found");
	}
}