package util.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import util.Common;

import org.apache.log4j.Logger;

public abstract class ReportGenerator
{

    public static final String IMAGE_SESSION_KEY = "ReportImageSession";

    public static final String IMAGE_REQUEST_PARAMETER_KEY = "image_index";

    protected ReportEnvironment env;

    protected String format;

    protected String httpFileName;

    protected String htmlImagePattern;

    protected Long httpImageCacheTimeout;

    protected String jasperFile;

    public ReportGenerator(ReportEnvironment env) {
        this.env = env;

    }

    private Logger logger = Logger.getLogger(ReportGenerator.class);

    abstract protected JasperPrint getJasperPrint(HashMap parms) throws Exception;
    //abstract protected List getJasperPrintList(HashMap parms) throws Exception;

    Hashtable exportParameters;

    private void loadEnvironment() {
        exportParameters = new Hashtable();

        format = env.getFormat();
        httpFileName = env.getHttpFileName();
        htmlImagePattern = env.getHtmlImagePattern();
        httpImageCacheTimeout = env.getHttpImageCacheTimeout();
        jasperFile = env.getJasperFile();

        StringBuffer msg = new StringBuffer();
        msg.append("Environment[");
        msg.append("JasperFile:").append(jasperFile).append(",");
        msg.append("Format:").append(format).append(",");
        msg.append("httpFileName:").append(httpFileName).append(",");
        msg.append("httpImageCacheTimeout:").append(httpImageCacheTimeout).append(",");
        msg.append("htmlImagePattren:").append(htmlImagePattern).append(",");
        msg.append("]");
        logger.debug(msg.toString());
    }

    public void reportToFile(File output, HashMap parms) throws Exception {
        loadEnvironment();
        logger.debug("Output File:" + output.getName());
        FileOutputStream fos = new FileOutputStream(output);
        try {
            if (ReportEnvironment.VAL_FORMAT_HTML.equals(format)) {

                if (htmlImagePattern != null) {
                    exportParameters.put(JRHtmlExporterParameter.IMAGES_DIR, new File(output.getParent(),
                            htmlImagePattern));
                    exportParameters.put(JRHtmlExporterParameter.IMAGES_URI, htmlImagePattern + File.separator);
                    exportParameters.put(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
                }

            }
            System.gc();
            //System.out.println("JVM MAX MEMORY 06: "+Runtime.getRuntime().maxMemory()/1024/1024+"M");
    		//System.out.println("JVM IS USING MEMORY 06:"+Runtime.getRuntime().totalMemory()/1024/1024+"M");
            JasperPrint printer = getJasperPrint(parms);
            System.gc();
            //System.out.println("JVM MAX MEMORY 07: "+Runtime.getRuntime().maxMemory()/1024/1024+"M");
    		//System.out.println("JVM IS USING MEMORY 07:"+Runtime.getRuntime().totalMemory()/1024/1024+"M");
            doExport(fos, printer);
            System.gc();
            //System.out.println("JVM MAX MEMORY 08: "+Runtime.getRuntime().maxMemory()/1024/1024+"M");
    		//System.out.println("JVM IS USING MEMORY 08:"+Runtime.getRuntime().totalMemory()/1024/1024+"M");
        } catch (Exception x) {
            logger.error(x.getMessage(), x);
            throw x;
        } finally {
            try {
                fos.close();
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
    
    
    
    public void reportToFile(File output, HashMap parms ,String choice) throws Exception {
        loadEnvironment();
        logger.debug("Output File:" + output.getName());
        FileOutputStream fos = new FileOutputStream(output);
        try {
            if (ReportEnvironment.VAL_FORMAT_HTML.equals(format)) {

                if (htmlImagePattern != null) {
                    exportParameters.put(JRHtmlExporterParameter.IMAGES_DIR, new File(output.getParent(),
                            htmlImagePattern));
                    exportParameters.put(JRHtmlExporterParameter.IMAGES_URI, htmlImagePattern + File.separator);
                    exportParameters.put(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
                }

            }

            JasperPrint printer = getJasperPrint(parms);

            doExport(fos, printer , choice);

        } catch (Exception x) {
            logger.error(x.getMessage(), x);
            throw x;
        } finally {
            try {
                fos.close();
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void reportToHttpResponse(HttpServletRequest request, HttpServletResponse response, HashMap parms) throws Exception {
    	loadEnvironment();
		try {
		    HttpSession session = request.getSession();
		    OutputStream os = response.getOutputStream();
		    // remove old image cache
		    ImageCacheHandler handler = (ImageCacheHandler) session.getAttribute(IMAGE_SESSION_KEY);
		    if (handler != null) {
		        handler.clearNow();
		        session.removeAttribute(IMAGE_SESSION_KEY);
		    }
		    // prepare httl head data
		    String filename = httpFileName;
		    if (filename == null) filename = Common.getVMID();   
		    HashMap imageMap = null;		
		    // set http header
		    if (ReportEnvironment.VAL_FORMAT_HTML.equals(format)) {
		        filename = filename + ".html";	
		        response.setContentType("text/html");
		        if (htmlImagePattern != null) {
		            imageMap = new HashMap();
		            exportParameters.put(JRHtmlExporterParameter.IMAGES_MAP, imageMap);
		            exportParameters.put(JRHtmlExporterParameter.IMAGES_URI, htmlImagePattern + "?"
		                    + IMAGE_REQUEST_PARAMETER_KEY + "=");
		            
	                if (httpImageCacheTimeout != null) {
	                    handler = new ImageCacheHandler(imageMap, httpImageCacheTimeout.longValue());
	                } else {
	                    handler = new ImageCacheHandler(imageMap);
	                }
	                session.setAttribute(IMAGE_SESSION_KEY, handler);		            
		        }		        
		    } else if (ReportEnvironment.VAL_FORMAT_PDF.equals(format)) {
		        filename = filename + ".pdf";		
		    } else if (ReportEnvironment.VAL_FORMAT_XLS.equals(format)) {
		        filename = filename + ".xls";
		    } else if (ReportEnvironment.VAL_FORMAT_CSV.equals(format)) {
		        filename = filename + ".csv";
            } else if (ReportEnvironment.VAL_FORMAT_RTF.equals(format)) {
            	filename = filename + ".rtf";
            	//ct = "application/rtf";		        
		    } else {
		        throw new IllegalArgumentException("Doesn't support format:" + format);
		    }	        
		    java.io.File outputFile = new java.io.File(System.getProperty("java.io.tmpdir")+java.io.File.separator+filename);
		    //reportToFile(outputFile,parms);	
	        FileOutputStream fos = new FileOutputStream(outputFile);
	        try {
	            JasperPrint printer = getJasperPrint(parms);
	            doExport(fos, printer);
	        } catch (Exception x) {
	            logger.error(x.getMessage(), x);
	            throw x;
	        } finally {
	            try {
	                fos.close();
	            } catch (Throwable e) {
	                logger.error(e.getMessage(), e);
	            }
	        }
	        if (ReportEnvironment.VAL_FORMAT_HTML.equals(format)) {
	        	response.setHeader("Content-disposition", "inline; filename=" + filename);
	        } else {
			    response.setContentLength((int)outputFile.length());	        
			    response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");	        	
	        }
		    FileInputStream is = null;	        
		    try{
		    	byte b[] = new byte[(int)outputFile.length()];
		    	if (outputFile.isFile())
		    	{
		    		response.flushBuffer();
		    	    is = new FileInputStream(outputFile);
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
		        outputFile.delete();
		    }
		} catch (Exception x) {
		    logger.error(x.getMessage(), x);
		    throw x;
		}
		
	}
    
    /*
     * public void reportToOutputStream(OutputStream output,HashMap parms)
     * throws Exception{ loadEnvironment(); JasperPrint printer =
     * getJasperPrint(parms); doExport(output,printer); }
     */

    private void doExport(OutputStream output, List jasperPrintlist,String[] sheetName) throws Exception {

        JRExporter exporter = null;

        // set jr parameter;
        
        //List listaReport = new ArrayList();
        //listaReport.add(printer);

        if (ReportEnvironment.VAL_FORMAT_HTML.equals(format)) {
            exporter = new JRHtmlExporter();
        } else if (ReportEnvironment.VAL_FORMAT_PDF.equals(format)) {
            exporter = new JRPdfExporter();
            exportParameters.put(JRPdfExporterParameter.FORCE_LINEBREAK_POLICY, Boolean.TRUE); 
            exportParameters.put(JRPdfExporterParameter.FORCE_SVG_SHAPES, Boolean.TRUE);
        } else if (ReportEnvironment.VAL_FORMAT_XLS.equals(format)) {
            exporter = new JExcelApiExporter();
            //exportParameters.put(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
            exportParameters.put(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exportParameters.put(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);  
            exportParameters.put(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED,Boolean.TRUE);  //這個參數是java.lang.Boolean的對象，是否允許自動修正Excel每個欄位的大小
            if (sheetName != null && sheetName.length > 0){
            	exportParameters.put(JRXlsExporterParameter.SHEET_NAMES, sheetName);
            }
           
        } else if (ReportEnvironment.VAL_FORMAT_RTF.equals(format)) {
        	exporter = new JRRtfExporter();
        } else {
            throw new IllegalArgumentException("Doesn't support format:" + format);
        }
        
           
        exportParameters.put(JRExporterParameter.OUTPUT_STREAM, output);
        //exportParameters.put(JRExporterParameter.JASPER_PRINT, printer);

        Enumeration enu = exportParameters.keys();
        JRExporterParameter parm;
        Object value;
        while (enu.hasMoreElements()) {
            parm = (JRExporterParameter) enu.nextElement();
            value = exportParameters.get(parm);
            exporter.setParameter(parm, value);
        }
        
        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintlist);
        exporter.exportReport();

    }
    
    private void doExport(OutputStream output, JasperPrint printer) throws Exception {

        JRExporter exporter = null;

        // set jr parameter;

        if (ReportEnvironment.VAL_FORMAT_HTML.equals(format)) {
            exporter = new JRHtmlExporter();
        } else if (ReportEnvironment.VAL_FORMAT_PDF.equals(format)) {
            exporter = new JRPdfExporter();
            //exportParameters.put(JRPdfExporterParameter.CHARACTER_ENCODING, "UTF-8");            
            exportParameters.put(JRPdfExporterParameter.FORCE_LINEBREAK_POLICY, Boolean.TRUE);  
            exportParameters.put(JRPdfExporterParameter.FORCE_SVG_SHAPES, Boolean.TRUE);
        } else if (ReportEnvironment.VAL_FORMAT_XLS.equals(format)) {
            //exporter = new JRXlsExporter();
            exporter = new JExcelApiExporter(); 
            //exportParameters.put(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
            exportParameters.put(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
            exportParameters.put(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.FALSE);
            exportParameters.put(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);
            
        } else if (ReportEnvironment.VAL_FORMAT_CSV.equals(format)) {
            //exporter = new JRXlsExporter();
            exporter = new JRCsvExporter(); 
            exportParameters.put(JRCsvExporterParameter.FIELD_DELIMITER, ",");
            //exportParameters.put(JRCsvExporterParameter.RECORD_DELIMITER, "\r\n");
            exportParameters.put(JRCsvExporterParameter.RECORD_DELIMITER,System.getProperty("line.separator"));
            //exportParameters.put(JRCsvExporterParameter.CHARACTER_ENCODING, "U");
        } else if (ReportEnvironment.VAL_FORMAT_RTF.equals(format)) {
        	exporter = new JRRtfExporter();
        } else {
            throw new IllegalArgumentException("Doesn't support format:" + format);
        }
        exportParameters.put(JRExporterParameter.OUTPUT_STREAM, output);
        exportParameters.put(JRExporterParameter.JASPER_PRINT, printer);
        
        Enumeration enu = exportParameters.keys();
        JRExporterParameter parm;
        Object value;
        while (enu.hasMoreElements()) {
            parm = (JRExporterParameter) enu.nextElement();
            value = exportParameters.get(parm);
            exporter.setParameter(parm, value);
        }
        System.gc();
        //System.out.println("JVM MAX MEMORY 09: "+Runtime.getRuntime().maxMemory()/1024/1024+"M");
		//System.out.println("JVM IS USING MEMORY 09:"+Runtime.getRuntime().totalMemory()/1024/1024+"M");
        exporter.exportReport();
        System.gc();
        //System.out.println("JVM MAX MEMORY 10: "+Runtime.getRuntime().maxMemory()/1024/1024+"M");
		//System.out.println("JVM IS USING MEMORY 10:"+Runtime.getRuntime().totalMemory()/1024/1024+"M");
    }
    
    private void doExport(OutputStream output, JasperPrint printer ,String choice) throws Exception {

        JRExporter exporter = null;

        // set jr parameter;

        if (ReportEnvironment.VAL_FORMAT_HTML.equals(format)) {
            exporter = new JRHtmlExporter();
        } else if (ReportEnvironment.VAL_FORMAT_PDF.equals(format)) {
            exporter = new JRPdfExporter();
            //exportParameters.put(JRPdfExporterParameter.CHARACTER_ENCODING, "UTF-8");            
            exportParameters.put(JRPdfExporterParameter.FORCE_LINEBREAK_POLICY, Boolean.TRUE);  
            exportParameters.put(JRPdfExporterParameter.FORCE_SVG_SHAPES, Boolean.TRUE);
        } else if (ReportEnvironment.VAL_FORMAT_XLS.equals(format)) {
            //exporter = new JRXlsExporter();
            exporter = new JExcelApiExporter(); 
            exportParameters.put(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
            exportParameters.put(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.FALSE);
            exportParameters.put(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);
            //exportParameters.put(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE); 
        } else if (ReportEnvironment.VAL_FORMAT_CSV.equals(format)) {
            //exporter = new JRXlsExporter();
            exporter = new JRCsvExporter(); 
            if(choice.equals("2")){
            	exportParameters.put(JRCsvExporterParameter.CHARACTER_ENCODING, "MS950");
            }
            exportParameters.put(JRCsvExporterParameter.FIELD_DELIMITER, ",");
            //exportParameters.put(JRCsvExporterParameter.RECORD_DELIMITER, "\r\n");
            exportParameters.put(JRCsvExporterParameter.RECORD_DELIMITER,System.getProperty("line.separator"));
            //exportParameters.put(JRCsvExporterParameter.CHARACTER_ENCODING, "U");
        } else if (ReportEnvironment.VAL_FORMAT_RTF.equals(format)) {
        	exporter = new JRRtfExporter();
        } else {
            throw new IllegalArgumentException("Doesn't support format:" + format);
        }
        exportParameters.put(JRExporterParameter.OUTPUT_STREAM, output);
        exportParameters.put(JRExporterParameter.JASPER_PRINT, printer);
        
        Enumeration enu = exportParameters.keys();
        JRExporterParameter parm;
        Object value;
        while (enu.hasMoreElements()) {
            parm = (JRExporterParameter) enu.nextElement();
            value = exportParameters.get(parm);
            exporter.setParameter(parm, value);
        }

        exporter.exportReport();

    }
}
