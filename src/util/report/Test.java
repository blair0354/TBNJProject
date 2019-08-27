package util.report;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRParameter;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			String str = "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=tapb;SelectMethod=cursor";

			Connection conn = DriverManager.getConnection(str, "sa", "sa00");
			
			try{
			SQLReportEnvironment env = new SQLReportEnvironment();
			
			env.setJasperFile("D:/Java/iReport-0.5.0/Report1.jasper");
			env.setConnection(conn);
			env.setUseDataSource(true);
			env.setDataSourceSQL("Select * from SYSAP_MenuNode Where ProgramID is not null");
			
			
			
			SQLReportGenerator generator = new SQLReportGenerator(env);
			

			
			HashMap parms= new HashMap();
			parms.put("ReportName","1234");
			parms.put("SubReportName","5678");
			parms.put("SubReportFile","d:/java/iReport-0.5.0/Sub1.jasper");
			
			//如果useDataSource,則subreport需自己加入Connection
			parms.put(JRParameter.REPORT_CONNECTION ,conn);
			
			
			env.setFormat(ReportEnvironment.VAL_FORMAT_HTML);
			env.setHtmlImagePattern("images");
			HashMap imageMap = new HashMap();
			generator.reportToFile(new File("d:/temp/TestReport1.html"),parms);

			env.setFormat(ReportEnvironment.VAL_FORMAT_XLS);
			generator.reportToFile(new File("d:/temp/TestReport1.xls"),parms);
			
			env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
			generator.reportToFile(new File("d:/temp/TestReport1.pdf"),parms);

			}finally{
				conn.close();
			}

		} catch (Exception x) {
			x.printStackTrace();
		}
		System.out.println("DONE!");

	}

}
