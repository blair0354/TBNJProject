package util.report;

import java.util.Hashtable;

public class ReportEnvironment extends Hashtable{

	static public final String EVN_REPORT_FORMATE = "ReportFormate";
	static public final String ENV_JASPER_FILE = "JasperFile";
	static public final String ENV_HTTP_FILE_NAME = "HttpFileName";
	static public final String ENV_HTTP_IMAGE_CACHE_TIMEOUT = "ImageCacheTimeout";
	
	static public final String ENV_HTML_IMAGE_PATTERN = "ImagePattern";

	static public final String VAL_FORMAT_PDF = "PDF";
	static public final String VAL_FORMAT_HTML = "HTML";
	static public final String VAL_FORMAT_XLS = "XLS";
	static public final String VAL_FORMAT_CSV = "CSV";
	static public final String VAL_FORMAT_RTF = "RTF";
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getFormat() {
		return (String)get(EVN_REPORT_FORMATE);
	}
	public void setFormat(String format) {
		put(EVN_REPORT_FORMATE,format);
	}
	public String getHttpFileName() {
		return (String)get(ENV_HTTP_FILE_NAME);
	}
	public void setHttpFileName(String httpFormateFileName) {
		put(ENV_HTTP_FILE_NAME,httpFormateFileName);
	}
	public String getJasperFile() {
		return (String)get(ENV_JASPER_FILE);
	}
	public void setJasperFile(String jasperFile) {
		put(ENV_JASPER_FILE,jasperFile);
	}
	public String getHtmlImagePattern() {
		return (String)get(ENV_HTML_IMAGE_PATTERN);
	}
	public void setHtmlImagePattern(String pattren) {
		put(ENV_HTML_IMAGE_PATTERN,pattren);
	}
	public Long getHttpImageCacheTimeout() {
		return (Long)get(ENV_HTTP_IMAGE_CACHE_TIMEOUT);
	}
	
	public void setHttpImageCacheTimeout(Long time){
		put(ENV_HTTP_IMAGE_CACHE_TIMEOUT,time);
	}
	
	
	
	
	
	
}
