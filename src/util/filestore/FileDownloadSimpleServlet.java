package util.filestore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringEscapeUtils;
import util.Common;

 public class FileDownloadSimpleServlet extends javax.servlet.http.HttpServlet {
        
	public final String PARM_FILE_ID = "fileID";
	public final String PARM_FILE_ID2 = "fileID2";
	private static final long serialVersionUID = 1L;
	
	public FileDownloadSimpleServlet() {
		super();
	}

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doProcess(req,res);       
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doProcess(req,res);
    }
        
    protected void doProcess(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        
        ServletContext context = getServletContext();
        String filestoreLocation = context.getInitParameter("filestoreLocation");
        String reportLocation =context.getInitParameter("reportLocation");
        String fileID = request.getParameter(PARM_FILE_ID);//路徑來源為filestoreLocation
        String fileID2 = request.getParameter(PARM_FILE_ID2);//路徑來源為reportLocation
        String fileName = request.getParameter("fileName");
        String reportName=request.getParameter("reportName");
        String uid=request.getParameter("uid");
        String[] arrFileName;  
        File sFile = null;
        if(fileID==null && fileName==null && reportName==null && fileID2==null) {
            response.sendError(404,"Parameter Not Found !");
            return;
        } else if (fileID!=null){
        	if (context.getServerInfo().toLowerCase().indexOf("tomcat")>0) fileID = fileID;        	
        	arrFileName = fileID.split(":;:");
        	sFile = new File(filestoreLocation+File.separator+arrFileName[0]+File.separator+arrFileName[1]);
        } else if (fileID2!=null){
        	if (context.getServerInfo().toLowerCase().indexOf("tomcat")>0) fileID2 = fileID2;        	
        	arrFileName = fileID2.split(":;:");
        	sFile = new File(reportLocation+File.separator+arrFileName[0]+File.separator+arrFileName[1]);
        	fileID=arrFileName[1];
        }else if (reportName!=null){
        	//System.out.println("TEXT");
        	if (context.getServerInfo().toLowerCase().indexOf("tomcat")>0) fileID = reportName;
        		arrFileName = fileID.split(":;:");
        		String fn=arrFileName[1].substring(0,arrFileName[1].lastIndexOf("."));
        		//File df = new File(reportLocation+File.separator+arrFileName[0]+File.separator+fn+".rar");
        		File df = new File(reportLocation+File.separator+arrFileName[0]+File.separator+fn+".zip");//108年增修案改為產出ZIP檔
        		if(df.exists()){
        			if(!df.delete()){
        				System.out.println("刪檔失敗,請查明異常原因");
        			}
        		}
        		if(!df.exists()){
        			//Common.RARFile(fn, reportLocation+File.separator+arrFileName[0]+File.separator+arrFileName[1], reportLocation+File.separator+arrFileName[0]+File.separator, uid);
        			Common.ZIPFile(fn, reportLocation+File.separator+arrFileName[0]+File.separator+arrFileName[1], reportLocation+File.separator+arrFileName[0]+File.separator, uid);//108年增修案改為產出ZIP檔
        		}
        	    //sFile = new File(reportLocation+File.separator+arrFileName[0]+File.separator+fn+".rar");
        		sFile = new File(reportLocation+File.separator+arrFileName[0]+File.separator+fn+".zip");//108年增修案改為產出ZIP檔
        } else {
        	if (context.getServerInfo().toLowerCase().indexOf("tomcat")>0) {
        		fileID = fileName;
        		sFile = new File(fileName);
        	} else {
        		fileID = fileName;
        		sFile = new File(fileName);
        	}        	
        }
	    //System.out.println(sFile.exists()) ;   
        if(sFile!=null && sFile.exists()){	        
	        String ct = ContentTypeConfiguration.getContentType(fileID);
	        if(ct!=null){
	            response.setContentType(ct);
	        }	        
	        //response.setContentType("text/html;charset=UTF-8");
	        if ((int)sFile.length()>0) {
	        	/**
				FileInputStream in = new FileInputStream(sFile);
	        	response.setHeader("Content-Disposition", "attachment; filename=\"" + sFile.getName() + "\"");
	        	int i; 
	        	OutputStream os = null;	   
	        	try{	        	
		        	while ((i=in.read()) != -1) { 
		        		os.write(i); 
		        	}
		        }catch(Exception x){
		        	try{
		        		response.sendError(500,x.getMessage());
		        	}catch(Exception e){}
		        }finally{
		            os.close();
		            in.close();
		        }	
		        **/	        
	        	
	        	//response.setContentType("application/download");
		        response.setContentLength((int)sFile.length());
		        //response.setCharacterEncoding("UTF-8");
		        //response.setHeader("Content-Type", "application/download");
		        //response.setHeader("Content-Disposition", "inline; filename=" + Common.big5ToIso(sFile.getName()));
		        
		        response.setHeader("Content-Disposition", "attachment; filename=\"" + Common.big5ToIso(sFile.getName()) + "\"");
		        
		        OutputStream os = null;
		        InputStream is = null;
		        
		        try{
		        	byte b[] = new byte[(int)sFile.length()];
		        	if (sFile.isFile())
		        	{
		        		response.flushBuffer();
		        	    is = new FileInputStream(sFile);
		        	    os = response.getOutputStream();
		        	    int read = 0;
		        	    while ((read = is.read(b)) != -1)
		        	    {
		        	        os.write(b,0,read);
		        	    }
		        	}
		        }catch(Exception x){
		        	try{
		        		response.sendError(500,x.getMessage());
		        	}catch(Exception e){}
		        }finally{
		            os.close();
		            is.close();
		        }
		        
	        } else {
	            response.sendError(404,"File Is Empty !");
	            return;
	        }
        } else {
            response.sendError(404,"File Not Found !");
            return;        	
        }       
    }

	
}