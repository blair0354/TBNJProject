/*
*<br>程式目的：常用函數
*<br>程式代號：Common
*<br>撰寫日期：93/12/01
*<br>程式作者：KangDa Info
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/

package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.PrivilegedAction;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.ServletContext;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.File;
import java.net.ConnectException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;


public class ConverterLibreOffice {
	
	public ConverterLibreOffice() {
		 // avoid instantiation...
	}
	
    public void convert(String inputFilePath, String outputFilePath) throws Exception {
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);
        this.convert(inputFile, outputFile);
    }
   
    /**
     * Convert office document to PDF
     *
     * @param inputFile - File of original office document (*.doc, *.xls, *.ppt)
     * @param outputFile - File of target pdf document
     * @throws ConnectException
     */
    public void convert(File inputFile, File outputFile) throws Exception {
      //connect to an OpenOffice.org instance running on port 8100
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);

        try {
            connection.connect();

            // convert
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);
        } catch (Exception e) {
            throw e;
        } finally {
            //close the connection
            try {
                if (connection != null) {
                    connection.disconnect();
                    connection = null;
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

}