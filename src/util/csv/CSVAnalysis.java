package util.csv;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
* @author SinNeR 
* http://bbs.blueidea.com 
* 
* CSVAnalysis
*/
public class CSVAnalysis {
    private InputStreamReader fr = null;
    private boolean convertFlag = false;
    private ArrayList dataContainer = new ArrayList();
    public static final String DEL_CHAR = ",";
    public static final String AV_CHAR = "\"";
    public CSVAnalysis(String f) throws IOException {
        fr = new InputStreamReader(new FileInputStream(f));
    }
    public void setConvertFlag(boolean b) {
        convertFlag = b; 
    }
    public ArrayList analysis() throws IOException {
        BufferedReader br = new BufferedReader(fr);
        String rec = null;
        try {
            while ((rec = br.readLine()) != null) {
                ArrayList alLine = analysisLine(rec);
                dataContainer.add(alLine);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            br.close(); 



        }
        return dataContainer;
    }
    private ArrayList analysisLine(String strLine) {
        System.out.println(strLine);
        ArrayList al = new ArrayList();
        String[] dataArr = strLine.split(AV_CHAR);
        for (int i = 1; i < dataArr.length; i = i + 2) {
            if (convertFlag)
                al.add(CSVDecode(dataArr[i]));
            else
                al.add(dataArr[i]);
        } 

        return al;
    }
    public static String CSVEncode(String in){
        if ( in == null )
            return "";
        in.replaceAll("&","&amp;");
        in.replaceAll("\"","&quot;");        
        return in;
    }
    
    public static String CSVDecode(String in){
        if ( in == null )
            return "";
        in.replaceAll("&quot;","\"");
        in.replaceAll("&amp;","&"); 
        return in;
    } 

    public void close() throws IOException {
        fr.close();
    }
    public static void main(String[] args) {
        try {
            CSVAnalysis csvAna = new CSVAnalysis("C:\\test.csv");
            csvAna.setConvertFlag(true);
            ArrayList al = csvAna.analysis();
            for (int i = 0; i < al.size(); i++) {
                ArrayList al1 = (ArrayList) al.get(i);
                for (int j = 0; j < al1.size(); j++) { 
                    System.out.println(al1.get(j));
                }
            }
            csvAna.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 