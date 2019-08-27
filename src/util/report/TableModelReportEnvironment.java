package util.report;

import java.sql.Connection;

import javax.swing.table.TableModel;

public class TableModelReportEnvironment extends ReportEnvironment {
	static public final String ENV_TABLE_MODEL = "TableModel";

	private static final long serialVersionUID = 1L;

	public TableModel getTableModel(){
		return (TableModel)get(ENV_TABLE_MODEL);
	}
	
	public void setTableModel(TableModel model){
		put(ENV_TABLE_MODEL,model);
	}
	

}
