package util.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lca.ap.LCAAP200F;
import util.Common;
import util.Datetime;

public class ReportServletTxt extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String pageName = request.getParameter("pageName");

		if ("eform_3_7".equals(pageName)) {
			LCAAP200F LCAAP180FObj = new LCAAP200F();

			String recreation_str = Common.checkSet(request.getParameter("recreation_str")) != null ? Common.checkSet(request.getParameter("recreation_str")) : "";
			File file = LCAAP180FObj.getTxtFile(recreation_str, request, response);
			String fileName = file.getName();

			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

			ServletOutputStream os = response.getOutputStream();
			FileInputStream fis = new FileInputStream(fileName);

			byte[] buffer = new byte[4096];
			int length;
			while ((length = fis.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			fis.close();
			os.flush();
		}
		
		if ("eform_3_7_ViewCSV".equals(pageName)) {
			LCAAP200F LCAAP200CSV = new LCAAP200F();

			String sIDXM_NO = Common.checkSet(request.getParameter("sIDXM_NO")) != null ? Common.checkSet(request.getParameter("sIDXM_NO")) : "";
			File file = LCAAP200CSV.getViewCSVFile(sIDXM_NO, request, response);
			String fileName = file.getName();

			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

			ServletOutputStream os = response.getOutputStream();
			FileInputStream fis = new FileInputStream(fileName);

			byte[] buffer = new byte[4096];
			int length;
			while ((length = fis.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			fis.close();
			os.flush();
		}
		
		if ("eform_4_20".equals(pageName)) {
			String fileName = request.getParameter("fileName") != null ? request.getParameter("fileName") : "";
			String[] fileAry=fileName.split("::;:::");
			fileName=fileAry[0]+"\\"+fileAry[1]+"\\"+"eform4_20.xls";
			response.setContentType("APPLICATION/xls");
			response.setHeader("Content-Disposition", "attachment;filename=" + "eform4_20_"+util.Datetime.getYYYMMDD()+util.Datetime.getFHHMMSS()+".xls");
			
			ServletOutputStream os = response.getOutputStream();
			FileInputStream fis = new FileInputStream(fileName);

			byte[] buffer = new byte[4096];
			int length;
			while ((length = fis.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			fis.close();
			os.flush();
		}

	}
}
