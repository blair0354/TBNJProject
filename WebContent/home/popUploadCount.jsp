<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" errorPage="error.jsp"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%@ page import="util.Common" %>
<%@ include file="head.jsp" %>
<%
String popFileID = request.getParameter("popFileID");
String popFileName = Common.isoToutf8(Common.get(request.getParameter("popFileName")));
int maxPostSize = 1024*1024*5; //5 M
if (!"".equals(Common.get(application.getInitParameter("filestoreLimit")))) {
	maxPostSize = Integer.parseInt(application.getInitParameter("filestoreLimit"));
}
    boolean isMultipart = false;

    String contentType = request.getContentType();

    if(contentType!=null){
        contentType = contentType.toLowerCase();
        if(contentType.startsWith("multipart/form-data")){
            isMultipart = true;
        }
    }

    MultipartRequest mr = null;
    String uploadCaseID = "";
    String actionType = "";
    String fileName = "";
    
    if(isMultipart){
                
        uploadCaseID = new java.rmi.dgc.VMID().toString();
        uploadCaseID = uploadCaseID.replaceAll(":","_");
        
        File tempDirectory = new File(application.getInitParameter("filestoreLocation"));
        //File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        
        //String filePath = application.getInitParameter("filestoreLocation")+File.separator+uploadCaseID;
        //Common.CreateDir(filePath);
        //Common.MakeDir(new File(filePath));
        tempDirectory = new File(tempDirectory,uploadCaseID);                
        tempDirectory.mkdirs();               
        
        FileRenamePolicy policy = new DefaultFileRenamePolicy();
        String encoding = "utf-8";
        
        mr = new MultipartRequest(request,tempDirectory.getAbsolutePath(),maxPostSize,encoding,policy);
        //mr = new MultipartRequest(request,filePath,maxPostSize,encoding,policy);
        actionType = mr.getParameter("actionType");
        popFileID = mr.getParameter("popFileID");
        popFileName = mr.getParameter("popFileName");                
    }
    
    String actionResult = "noAction";
    String actionMessage = "";
    
    if("doUpload".equals(actionType)){
        
        Enumeration enu = mr.getFileNames();
        File uploadFile = null;
        
        if(enu.hasMoreElements()){
            String name = (String)enu.nextElement();
            uploadFile = mr.getFile(name);
            int fileSize = (int) uploadFile.length();
            if (fileSize>0) {
            	//讀取檔案筆數(行數)是否超過限制
            	BufferedReader br = new BufferedReader(new FileReader(uploadFile));
    			int countLine = 0;
    			while (br.ready()) {
    				br.readLine();
    				countLine++;
    			}
    			if(countLine > 1000){
    				actionResult = "doUploadFail";
                    actionMessage = "筆數超過限制，請重新調整！";
    			}else{
	                actionResult = "doUploadSuccess";
	                actionMessage = "上傳成功";
	                fileName = uploadFile.getName();
    			}
            } else {
            	Common.RemoveDirectory(uploadFile.getParentFile());
                //uploadFile.delete();            	
                actionResult = "doUploadFail";
                actionMessage = "上傳失敗，因為上傳的檔案中無任何內容！";                
            }
            /**
            if(!"".equals(Common.get(popFileName))) {            	
            	String[] arrFileName = popFileName.split(":;:");
            	if (arrFileName.length>1) {
	                File removeDirectory = new File(application.getInitParameter("filestoreLocation")+File.separator+arrFileName[0]+File.separator+arrFileName[1]);
	                Common.RemoveDirectory(removeDirectory);
            	}                
            }
            **/
        }else{
            actionResult = "doUploadFail";
            actionMessage = "找不到上傳的檔案";            
        }
        
        
    }
    

    

%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../js/default.css" type="text/css">
<script language="javascript" src="../js/validate.js"></script>
<script language="javascript" src="../js/function.js"></script>
<script language="javascript">
var actionResult = '<%=actionResult%>';
var actionMessage = '<%=actionMessage%>';

function checkAfterAction(){    
    switch(actionResult){
    case 'noAction':
        break;
    case 'doUploadSuccess':
        alert(actionMessage); 
		if (isObj(opener.document.all.item("<%=popFileID%>"))) {
			opener.document.all.item("<%=popFileID%>").value='<%=uploadCaseID%>:;:'+'<%=fileName%>';		
		}
        window.close();
        break;
    case 'doUploadFail':
        alert(actionMessage);
        break;
    }
}

function checkField(){
    if(form1.FILE.value == ""){
            alert("您必需指定檔案");
            return false;
    } else {
    	var extPos = form1.FILE.value.lastIndexOf(".");
    	var ext = form1.FILE.value.toLowerCase().substring(extPos+1);
    	var allowExt = "<%=application.getInitParameter("filestoreAllowedExtList")%>";
    	if (extPos==-1) {
    		alert("無法判斷您上傳的檔案格式，請檢查檔案是否有副檔名並重新輸入!");
    		return false;
    	}
    	if (parse(allowExt).length>0) {
    		if (allowExt.search(ext)== -1 ) {
    			alert("上傳的檔案格式必須是<%=application.getInitParameter("filestoreAllowedExtList")%>，請重新輸入!");
    			return false;
    		}
    	}    	
    }
    form1.actionType.value = "doUpload";
}

function cancelUpload(){
    window.close();
}

</script>
</head>

<body topmargin="5" onload="checkAfterAction()">
<form id="form1" name="form1"  method="post" enctype="multipart/form-data" onSubmit="return checkField();">
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
    <table class="table_form" width="100%" height="100%">
    <tr>
      <td colspan="2" class="td_form_white">** 上傳成功後，請記得存檔，否則資料庫裡不會有記錄<br>
        ** 上傳的檔案限制為1000筆，請勿超過。</td>
      </tr>
    <tr>
        <td class="td_form">上載檔案路徑：</td>
        <td class="td_form_white">
           <input class="field" TYPE="file" name="FILE">
        </td>
    </tr>
    </table>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
    <input type="hidden" name="uploadCaseID" value="<%=uploadCaseID%>" >
    <input type="hidden" name="popFileID" value="<%=popFileID%>" >    
    <input type="hidden" name="popFileName" value="<%=popFileName%>" >        
    <input type="hidden" name="actionType" value="" >
    &nbsp;|
    <input class="toolbar_default" type="submit" name="upload"	value="上傳檔案">&nbsp;|                      
    <input class="toolbar_default" type="button" name="cancel"	value="取消上傳" onClick="cancelUpload()">&nbsp;|                          
</td></tr>

</table>
</form>
</body>
</html>