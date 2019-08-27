package util.report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


public class SQLReportGenerator extends ReportGenerator { 

	
	static private Logger logger = Logger.getLogger(SQLReportGenerator.class);
	
	protected Connection conn;
	protected String dsQuerySQL;
	protected boolean useDataSource;
	
	public SQLReportGenerator(SQLReportEnvironment env){
		super(env);
	}
	
	private void loadEnvironment(){
		useDataSource = ((SQLReportEnvironment)env).isUseDataSource();
		conn = ((SQLReportEnvironment)env).getConnection();
		dsQuerySQL = ((SQLReportEnvironment)env).getDataSourceSQL();
		
		StringBuffer msg = new StringBuffer();
		msg.append("Environment[");
		msg.append("Connection:").append(conn).append(",");
		msg.append("UseDataSource:").append(useDataSource).append(",");
		msg.append("DataSourceSQL:").append(dsQuerySQL).append(",");
		msg.append("]");
		logger.debug(msg.toString());		
	}
	
	
	protected JasperPrint getJasperPrint(HashMap parms) throws Exception{
		loadEnvironment();
		logger.debug("Parameter:"+parms.toString());
		// check data
		
		// check data
		if(jasperFile==null){
			throw new IllegalArgumentException("JasperFile is null");
		}
		
		if(conn==null){
			throw new IllegalArgumentException("Connection is null");
		}
		
		if(useDataSource && dsQuerySQL==null){
			throw new IllegalArgumentException("Query SQL is null");
		}
		
		JasperPrint print;
		
		if(useDataSource){
			JRDataSource ds = getDataSource();
			parms.put(JRParameter.REPORT_DATA_SOURCE ,ds);
			print = JasperFillManager.fillReport(jasperFile,parms,ds);
		}else{
			parms.put(JRParameter.REPORT_CONNECTION ,conn);
			print = JasperFillManager.fillReport(jasperFile,parms,conn);
		}
		return print;
		
	}
	
	
	protected JRDataSource getDataSource() throws Exception{
		ResultSet rs = conn.createStatement().executeQuery(dsQuerySQL);
		return new JRResultSetDataSource(rs);
		
	}

}
