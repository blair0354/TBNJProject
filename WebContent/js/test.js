
var vq3,vq5,vq6,vq7,vq8,vq9;

if (forms[0].q43.value == ''){ 
	vq3='   　';
}else{ 
	vq3=lpad(forms[0].q43.value,3,'0')+'鄰';
}

if (forms[0].q45.value==''){
	vq5=''
}else{
	vq5=chnumbertype('1',forms[0].q45.value)+'巷';
}

if (forms[0].q46.value==''){
	vq6='';
}else{
	vq6=chnumbertype('1',forms[0].q46.value)+'弄';
}

if (forms[0].q47.value=='') {
	vq7=''
}else{
	vq7=chnumbertype('1',forms[0].q47.value)+'號';
}

if (forms[0].q48.value=='') {
	vq8='';
}else{
	vq8=chnumbertype('3',forms[0].q48.value)+'樓'
}

if (forms[0].q49.value=='') {
	vq9=''
}else{
	vq9='之'+chnumbertype('1',forms[0].q49.value)
}
forms[0].jobqry.value= forms[0].q10.value+rpad(forms[0].q42.value,4,'　')+vq3 +rpad((chnumbertype('2',forms[0].q44.value)+vq5+vq6+vq7+vq8+vq9),25,'　');