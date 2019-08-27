
document.onmouseover = mOver ;
document.onmouseout = mOut ;

function mOver() {
	var eSrc = window.event.srcElement ;
	if (eSrc.className == "item") {
		window.event.srcElement.className = "highlight";
	}
}

function mOut() {
	var eSrc = window.event.srcElement ;
	if (eSrc.className == "highlight") {
		window.event.srcElement.className = "item";
	}
}


var bV=parseInt(navigator.appVersion);
NS4=(document.layers) ? true : false;
IE4=((document.all)&&(bV>=4))?true:false;
ver4 = (NS4 || IE4) ? true : false;

isExpanded = false;

function getIndex($1) {
	ind = null;
	for (i=0; i<document.layers.length; i++) {
		whichEl = document.layers[i];
		if (whichEl.id == $1) {
			ind = i;
			break;
		}
	}
	return ind;
}

function arrange() {
	nextY = document.layers[firstInd].pageY + document.layers[firstInd].document.height;
	for (i=firstInd+1; i<document.layers.length; i++) {
		whichEl = document.layers[i];
		if (whichEl.visibility != "hide") {
			whichEl.pageY = nextY;
			nextY += whichEl.document.height;
		}
	}
}

function FolderInit(){
	if (NS4) {
	firstEl = "mParent";
	firstInd = getIndex(firstEl);
	showAll();
		for (i=0; i<document.layers.length; i++) {
			whichEl = document.layers[i];
			if (whichEl.id.indexOf("Child") != -1) whichEl.visibility = "hide";
		}
		arrange();
	}
	else {
		tempColl = document.all.tags("DIV");
		for (i=0; i<tempColl.length; i++) {
			if (tempColl(i).className == "child") tempColl(i).style.display = "none";
		}
	}
}

function FolderExpand($1,$2) {
	if (!ver4) return;
	if (IE4) {
	  ExpandIE($1,$2) 
	}else {
      alert("ns");
	  ExpandNS($1,$2) 
	}
}

function ExpandIE($1,$2) {
	Expanda = eval($1 + "a");
	Expanda.blur()
	ExpandChild = eval($1 + "Child");
    
    if ($2 != "top") { 
        ExpandTree = eval($1 + "Tree");
		ExpandFolder = eval($1 + "Folder");
	}
	
	if (ExpandChild.style.display == "none") {
	    ExpandChild.style.display = "block";
        if ($2 != "top") { 
            if ($2 == "last") {
           	   ExpandTree.src = "../image/menu/Lminus.gif";
            }else {
               ExpandTree.src = "../image/menu/Tminus.gif"; 
            }
			ExpandFolder.src = "../image/menu/openfoldericon.gif";	
		}else {
		    mTree.src = "../image/menu/topopen.gif"; 
		}
	}else {
	    ExpandChild.style.display = "none";
        if ($2 != "top") { 
	        if ($2 == "last") {
	          ExpandTree.src = "../image/menu/Lplus.gif"; 
	        }else {
              ExpandTree.src = "../image/menu/Tplus.gif"; 
	        }
			ExpandFolder.src = "../image/menu/foldericon.gif";
		}
		else {
		  mTree.src = "../image/menu/top.gif"; 
		}
	}
}

function ExpandNS($1,$2) {
	ExpandChild = eval("document." + $1 + "Child")
        if ($2 != "top") { 
		ExpandTree = eval("document." + $1 + "Parent.document." + $1 + "Tree")
		ExpandFolder = eval("document." + $1 + "Parent.document." + $1 + "Folder")
	}	
	if (ExpandChild.visibility == "hide") {
		ExpandChild.visibility = "show";
                if ($2 != "top") { 
               		if ($2 == "last") { ExpandTree.src = "../image/menu/Lminus.gif"; }
			else { ExpandTree.src = "../image/menu/Tminus.gif"; }
			ExpandFolder.src = "../image/menu/openfoldericon.gif";	
		}
		else { mTree.src = "../image/menu/topopen.gif"; }
	}
	else {
		ExpandChild.visibility = "hide";
                if ($2 != "top") { 
               		if ($2 == "last") { ExpandTree.src = "../image/menu/Lplus.gif"; }
			else { ExpandTree.src = "../image/menu/Tplus.gif"; }
			ExpandFolder.src = "../image/menu/foldericon.gif";	
		}
		else { mTree.src = "../image/menu/top.gif"; }
	}
	arrange();
}

function showAll() {
	for (i=firstInd; i<document.layers.length; i++) {
		whichEl = document.layers[i];
		whichEl.visibility = "show";
	}
}


with (document) {
	write("<STYLE TYPE='text/css'>");
	if (NS4) {
		write(".parent { color: black; font-size:9pt; line-height:0pt; color:black; text-decoration:none; margin-top: 0px; margin-bottom: 0px; position:absolute; visibility:hidden }");
		write(".child { text-decoration:none; font-size:9pt; line-height:15pt; position:absolute }");
	        write(".item { color: black; text-decoration:none }");
	        write(".highlight { color: blue; text-decoration:none }");
	}
	else {
		write(".parent { font: 12px/13px; Times; text-decoration: none; color: black }");
		write(".child { font:12px/13px Times; display:none }");
	        write(".item { color: black; text-decoration:none; cursor: hand }");
	        write(".highlight { color: blue; text-decoration:none }");
	        write(".icon { margin-right: 5 }")
	}
	write("</STYLE>");
}

onload = FolderInit;