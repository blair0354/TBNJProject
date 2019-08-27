package util.report;

import java.util.HashMap;

import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;

public class TableModelReportGenerator extends ReportGenerator {

	protected TableModel model;
	static private Logger logger = Logger.getLogger(TableModelReportGenerator.class);
	
	public TableModelReportGenerator(TableModelReportEnvironment env) {
		super(env);
	}
	
	private void loadEnvironment(){
		model = ((TableModelReportEnvironment)env).getTableModel();
		
		StringBuffer msg = new StringBuffer();
		msg.append("Environment[");
		msg.append("TableModel:").append(model).append(",");
		msg.append("]");
		logger.debug(msg.toString());		
	}	

	protected JasperPrint getJasperPrint(HashMap parms) throws Exception {
		loadEnvironment();
		
		if(model==null) throw new IllegalArgumentException("TableModel is null");
		
		
		JRDataSource ds = getDataSource();
		parms.put(JRParameter.REPORT_DATA_SOURCE ,ds);
		JasperPrint print = JasperFillManager.fillReport(jasperFile,parms,ds);
		return print;
	}
	
	protected JRDataSource getDataSource() throws Exception{
		JRTableModelDataSource ds = new JRTableModelDataSource(model);
		return ds;
	}
	

}
