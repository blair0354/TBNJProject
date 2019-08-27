<%@ page contentType="text/html;charset=utf-8"%>
<script language="JavaScript">
<!--
function document.onmouseout()
{
var e , tmp ;
e = window.event.srcElement
tmp = e.id
if(tmp.indexOf('Layer') < 0 )
closemenu()

}

function selectchange(val1)
{
  
  var formsno = document.form1.length;
  var elementno = 0;
  
    for (j = 0 ; j < formsno ; j ++ )
    {
    
		if(document.form1.elements[j].type=='select-one')
		{
		  if(val1 == '1')
		  document.form1.elements[j].style.visibility = ''
		  else
		  document.form1.elements[j].style.visibility = 'hidden'
		  
		}
		
	}	
  
}

function MM_findObj(n, d)
{ //v3.0
  var p,i,x;  if(!d) d=document;
   if((p=n.indexOf("?"))>0&&parent.frames.length)
    {
     d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);
    }
   if(!(x=d[n])&&d.all) x=d.all[n];
   for (i=0;!x&&i<d.form1.length;i++) x=d.form1[i][n];
   for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
   return x;
}

function MM_showHideLayers()
{ //v3.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3)
   if ((obj=MM_findObj(args[i]))!=null)
    {
     v=args[i+2];
     if (obj.style)
      {
       obj=obj.style; v=(v=='show')?'visible':(v='hide')?'hidden':v;
      }
     obj.visibility=v;
	 
	 selectchange('2');
    }

}	
function closemenu()
{
selectchange('1')
}
//-->
</script>

<table width="1200" topmargin="0">
    <td width="100%">
	<a><img src=images/btn1.jpg alt="姓名統編對照" width="100" height="27" border=0 onmouseover="javascript:this.src='images/btn11.jpg';MM_showHideLayers('Layer1','','show','Layer2','','hide')" onmouseout="javascript:this.src='images/btn1.jpg';MM_showHideLayers('Layer1','','hide','Layer2','','hide')"></a>
	<a><img src=images/btn2.jpg alt="歸戶分類統計" width="100" height="27" border=0 onmouseover="javascript:this.src='images/btn21.jpg';MM_showHideLayers('Layer2','','show','Layer3','','hide')" onmouseout="javascript:this.src='images/btn2.jpg';MM_showHideLayers('Layer2','','hide','Layer3','','hide')"></a>
	<a><img src=images/btn4.jpg alt="歸戶清冊" width="100" height="27" border=0 onmouseover="javascript:this.src='images/btn21.jpg';MM_showHideLayers('Layer4','','show','Layer3','','hide')" onmouseout="javascript:this.src='images/btn4.jpg';MM_showHideLayers('Layer4','','hide','Layer3','','hide')"></a>
	<a><img src=images/btn3.jpg alt="系統管理" width="100" height="27" border=0 onmouseover="javascript:this.src='images/btn31.jpg';MM_showHideLayers('Layer3','','show','Layer1','','hide')" onmouseout="javascript:this.src='images/btn3.jpg';MM_showHideLayers('Layer3','','hide','Layer1','','hide')"></a>
	<a><img src=images/btn5.jpg alt="資料下載" width="100" height="27" border=0 onmouseover="javascript:this.src='images/btn21.jpg';MM_showHideLayers('Layer5','','show','Layer4','','hide')" onmouseout="javascript:this.src='images/btn5.jpg';MM_showHideLayers('Layer5','','hide','Layer4','','hide')"></a>
	<a href="menu.jsp" title="回首頁" target="_parent"><img src=images/home.jpg  border=0 onmouseover='javascript:this.src="images/home1.jpg"' onmouseout='javascript:this.src="images/home.jpg"'></a> 
	<a href="index.jsp" title="登出" target="_parent"><img src=images/logout.jpg  border=0 onmouseover='javascript:this.src="images/logout1.jpg"' onmouseout='javascript:this.src="images/logout.jpg"'></a> 

      <div id="Layer1" style="position: absolute; left: 16px; top: 27px; width: 150px; height: 64px; z-index: 6; visibility: hidden" onMouseOut="MM_showHideLayers('Layer1','','hide','Layer2','','hide')" onMouseOver="MM_showHideLayers('Layer1','','show','Layer2','','hide')"> 
        <table width="150" border="1" cellpadding="0" cellspacing="0" background="images/rb-1.gif">
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td width="150" height="28"> 
              <p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform1_2.jsp" target="main"><font color="#FFFFFF">※ 權利人資料查詢</font></a></p></td></tr>
    <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td width="150" height="28">
              <p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform2_5.jsp" target="main"><font color="#FFFFFF">※ 權利人姓名統編清冊</font></a></p></td></tr>
  </table>
</div>
    
      <div id="Layer2" style="position: absolute; left: 121px; top: 27px; width: 161px; height: 55px; z-index: 5; visibility: hidden" onMouseOut="MM_showHideLayers('Layer2','','hide','Layer3','','hide')" onMouseOver="MM_showHideLayers('Layer2','','show','Layer3','','hide')"> 
        <table border="1" bgcolor="#219C66" cellspacing="0" cellpadding="0" width="159" background="images/rb-1.gif">
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_1.jsp" target="main"><font color="#FFFFFF">※ 公有土地歸戶清冊</font></a></p></td></tr>
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_2.jsp" target="main"><font color="#FFFFFF">※ 私有土地歸戶清冊</font></a></p></td></tr>

	  <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform502.jsp" target="main"><font color="#FFFFFF">※ 土地所有權人性別統計產製作業</font></a></p></td></tr>
	  <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform503.jsp" target="main"><font color="#FFFFFF">※ 土地所有權人性別統計列印作業</font></a></p></td></tr>
	  <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform504.jsp" target="main"><font color="#FFFFFF">※ 非都農業用地性別統計產製作業</font></a></p></td></tr>
	  <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform505.jsp" target="main"><font color="#FFFFFF">※ 非都農業用地性別統計列印作業</font></a></p></td></tr>
      <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform506.jsp" target="main"><font color="#FFFFFF">※ 原住民地區行政區設定作業</font></a></p></td></tr>
      <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform507.jsp" target="main"><font color="#FFFFFF">※ 原住民族地區土地概況統計表產製作業</font></a></p></td></tr>
      <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform508.jsp" target="main"><font color="#FFFFFF">※ 原住民族地區土地概況統計表下載作業</font></a></p></td></tr>
  </table>
</div>
       
      <div id="Layer4" style="position: absolute; left: 222px; top: 28px; width: 240px; height: 55px; z-index: 5; visibility: hidden" onMouseOut="MM_showHideLayers('Layer4','','hide','Layer3','','hide')" onMouseOver="MM_showHideLayers('Layer4','','show','Layer3','','hide')"> 
        <table border="1" bgcolor="#219C66" cellspacing="0" cellpadding="0" width="240" background="images/rb-1.gif">
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_3.jsp" target="main"><font color="#FFFFFF">※ 土地所有權人歸戶清冊</font></a></p></td></tr>
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_4.jsp" target="main"><font color="#FFFFFF">※ 建物所有權人歸戶清冊</font></a></p></td></tr>
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_5.jsp" target="main"><font color="#FFFFFF">※土地所有權人管理者歸戶清冊</font></a></p></td></tr>        
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_6.jsp" target="main"><font color="#FFFFFF">※ 建物所有權人管理者歸戶清冊</font></a></p></td></tr>        
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_7.jsp" target="main"><font color="#FFFFFF">※ 土地他項權利人歸戶清冊</font></a></p></td></tr>        
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_8.jsp" target="main"><font color="#FFFFFF">※ 建物他項權利人歸戶清冊</font></a></p></td></tr>        
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_9.jsp" target="main"><font color="#FFFFFF">※ 土地他項權利人管理者歸戶清冊</font></a></p></td></tr>        
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_10.jsp" target="main"><font color="#FFFFFF">※ 建物他項權利人管理者歸戶清冊</font></a></p></td></tr>        
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform2_2.jsp" target="main"><font color="#FFFFFF">※ 權利人歸戶清冊</font></a></p></td></tr>
         <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_13.jsp" target="main"><font color="#FFFFFF">※ 批次歸戶清冊</font></a></p></td></tr>                 
         <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_14.jsp" target="main"><font color="#FFFFFF">※ 勞保批次歸戶清冊</font></a></p></td></tr>   
         <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_15.jsp" target="main"><font color="#FFFFFF">※ 國軍退輔會批次歸戶清冊</font></a></p></td></tr>   
                     <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_16.jsp" target="main"><font color="#FFFFFF">※ 勞保批次歸戶清冊(匯入資料庫)</font></a></p></td></tr>   
         <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_17.jsp" target="main"><font color="#FFFFFF">※ 國軍退輔會批次歸戶清冊(匯入資料庫)</font></a></p></td></tr> 
      </table>
</div>
      <div id="Layer3" style="position: absolute; left: 330px; top: 26px; width: 185px; height: 55px; z-index: 5; visibility: hidden" onMouseOut="MM_showHideLayers('Layer3','','hide','Layer1','','hide')" onMouseOver="MM_showHideLayers('Layer3','','show','Layer1','','hide')"> 
        <table border="1" bgcolor="#219C66" cellspacing="0" cellpadding="0" width="182" background="images/rb-1.gif">
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''"> 
            <td width="144" height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform3_1.jsp" target="main"><font color="#FFFFFF">※ 使用者管理</font></a></p></td>
          </tr>
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td width="144" height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform3_3.jsp" target="main"><font color="#FFFFFF">※ 每日查詢紀錄清冊</font></a></p></td>
          </tr>
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td width="144" height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform3_4.jsp" target="main"><font color="#FFFFFF">※ 申請案件管理清冊</font></a></p></td>
          </tr>				  		  
          <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''"> 
            <td width="144" height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform3_2.jsp" target="main"><font color="#FFFFFF">※ 密碼修改</font></a></p></td>
          </tr>
	        <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td width="240" height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform501.jsp" target="main"><font color="#FFFFFF">※ 性別統計表縣市資料維護作業</font></a></p></td>
	        </tr>
	        <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td width="240" height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform3_5.jsp" target="main"><font color="#FFFFFF">※ 總歸戶索引檔檢核及重建作業</font></a></p></td>
	        </tr>
	        <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td width="240" height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform3_6.jsp" target="main"><font color="#FFFFFF">※ 申請用途案件統計量表</font></a></p></td>
	        </tr>
	        <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td width="240" height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform3_7.jsp" target="main"><font color="#FFFFFF">※  總歸戶View檢核及重建作業</font></a></p></td>
	        </tr>
	        <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td width="240" height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_20.jsp" target="main"><font color="#FFFFFF">※  各單位管理者清冊</font></a></p></td>
	        </tr>
	        <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td width="240" height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_18.jsp" target="main"><font color="#FFFFFF">※  mail帳號設定</font></a></p></td>
	        </tr>
	        <tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td width="240" height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="eform4_19.jsp" target="main"><font color="#FFFFFF">※  mail發送及查記查詢</font></a></p></td>
	        </tr>
        </table>
      </div>

      <div id="Layer5" style="position: absolute; left: 432px; top: 26px; width: 161px; height: 55px; z-index: 5; visibility: hidden" onMouseOut="MM_showHideLayers('Layer5','','hide','Layer4','','hide')" onMouseOver="MM_showHideLayers('Layer5','','show','Layer4','','hide')"> 
        <table border="1" bgcolor="#219C66" cellspacing="0" cellpadding="0" width="159" background="images/rb-1.gif"><tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
            <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="exportRP_Result.jsp" target="main"><font color="#FFFFFF">※ 歸戶結果查詢及下載作業</font></a></p></td></tr>
            	<tr onmouseover="this.style.background='226699'"  onmouseout="this.style.background=''">
              <td  height="28"><p style="line-height: 100%; margin-top: 4; margin-bottom: 0"><a href="doc/TBNJ.rar" target="main"><font color="#FFFFFF">※ 操作手冊下載</font></a></p></td></tr>
  </table>
    </div></td>
  </table>






