package filter;

import util.Database;
import java.io.IOException;     
import javax.servlet.GenericServlet;     
import javax.servlet.ServletConfig;     
import javax.servlet.ServletException;     
import javax.servlet.ServletRequest;     
import javax.servlet.ServletResponse;     


public class MyContextListener extends GenericServlet {
	
	 public void init(ServletConfig config){ 
		//context = event.getServletContext(); 
		Database db=new Database();
		try {
			System.out.println("START");
			String[] sql=new String[1];
			sql[0]="update download_log set dqry_status='5' where dqry_status='1'";
			db.excuteSQL(sql);
			db.closeAll();
		}catch(Exception x) {
			System.out.println(x.toString());
		}finally{
			db.closeAll();
		}
		
	}

	 public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {  
		//this.context = null;
		Database db=new Database();
		try {
			System.out.println("STOP");
			String[] sql=new String[1];
			sql[0]="update download_log set dqry_status='5' where dqry_status='1'";
			db.excuteSQL(sql);
			db.closeAll();
		}catch(Exception x) {
			System.out.println(x.toString());
		}finally{
			db.closeAll();
		}
	}
}
