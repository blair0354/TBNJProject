/*---------------------------------------------------|
| checkbox Toggle v1.0								 |
|----------------------------------------------------|
| This script can be used freely as long as you want |
| 													 |
| Clive Chang                                        |
| Updated: Dec 6, 2006                               |
|---------------------------------------------------*/

function isObj(obj){
	return (!((obj==null)||(obj==undefined)));
}

function cbToggle(s,objForm,objToggleAll,cbName) {
	this.objForm = objForm;
	this.objToggleAll = objToggleAll;
	this.cbName = cbName;		
	//onclick=\"ToggleAll(this, document.form1, 'strKeys');
	//Toggle(this,document.form1,'strKeys');		
	//node.attributes[”onclick”].value = “script_text_here()”;
	//onclick = function() {your_func_with_param(params)}
	//this.objToggleAll.onclick = this.ToggleAll;
	//this.objToggleAll.attributes["onclick"] = this.ToggleAll;	
	this.objToggleAll.onclick = function() { eval(s+".ToggleAll(objToggleAll)"); };
	
	var ml = this.objForm;
	var len = ml.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = ml.elements[i];
	    if (e.name == this.cbName) {
			//e.onclick = this.Toggle; //(e);
			//e.onclick = function() { objName.Toggle(e); };
			e.onclick = function() { eval(s+".Toggle(this)"); };			
	    }
	}	

	this.config = {
		cssHighLight	: 'highLights',
		cssUnHighLight	: 'highLight',
		cssTopLevel		: true
	}
};

cbToggle.prototype.Toggle = function(e) {
	if (e.checked) {
	   	this.Highlight(e);
   		this.objToggleAll.checked = this.AllChecked();
	} else {
	   	this.Unhighlight(e);
	   	this.objToggleAll.checked = false;
	}
};

cbToggle.prototype.ToggleAll = function(e) {
	if (e.checked) {
	    this.CheckAll();
	} else {
	    this.ClearAll();
	}
};

cbToggle.prototype.Check = function(e) {
	e.checked = true;
	this.Highlight(e);
};

cbToggle.prototype.Clear = function(e){
	e.checked = false;
	this.Unhighlight(e);
};

cbToggle.prototype.CheckAll = function() {
	var ml = this.objForm;
	var len = ml.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = ml.elements[i];
	    if (e.name == this.cbName) {
			this.Check(e);
	    }
	}
	this.objToggleAll.checked = true;
};

cbToggle.prototype.ClearAll = function() {
	var ml = this.objForm;
	var len = ml.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = ml.elements[i];
	    if (e.name == this.cbName) {
			this.Clear(e);
	    }
	}
	this.objToggleAll.checked = false;
};

cbToggle.prototype.Highlight = function(e) {    
	var r = null;
	if (this.config.cssTopLevel) {
		if (e.parentNode && e.parentNode.parentNode) {
			r = e.parentNode.parentNode;
		} else if (e.parentElement && e.parentElement.parentElement) {
			r = e.parentElement.parentElement;
		}
	} else {	
		if (e.parentNode) r = e.parentNode;
		else if (e.parentElement) r = e.parentElement;
	}
	if (r) r.className = this.config.cssHighLight;

};

cbToggle.prototype.Unhighlight = function(e) {
	var r = null;
	if (this.config.cssTopLevel) {
		if (e.parentNode && e.parentNode.parentNode) {
			r = e.parentNode.parentNode;
		} else if (e.parentElement && e.parentElement.parentElement) {
			r = e.parentElement.parentElement;
		}
	} else {	
		if (e.parentNode) r = e.parentNode;
		else if (e.parentElement) r = e.parentElement;
	}
	if (r) r.className = this.config.cssUnHighLight;
};

cbToggle.prototype.AllChecked = function() {
	ml = this.objForm;
	len = ml.elements.length;
	for(var i = 0 ; i < len ; i++) {
	    if (ml.elements[i].name == this.cbName && !ml.elements[i].checked) {
			return false;
	    }
	}
	return true;
};
    

cbToggle.prototype.AnyChecked = function() {
	for (var i = 0; i < this.objForm.elements.length; i++) {
	    var e = this.objForm.elements[i];
	    if (e.name == this.cbName && e.checked==true) {	    	
	    	return true;
	    }
	}
	return false;
};    
