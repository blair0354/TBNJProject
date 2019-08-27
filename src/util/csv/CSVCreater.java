package util.csv;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Vector;

import util.Database;


/**
* @author SinNeR 
* http://bbs.blueidea.com 
* 
* CSVCreater
*/
public class CSVCreater {
    private FileOutputStream fos = null;
    private StringBuffer sb = null;
    private boolean convertFlag = false;
    public static final String DEL_CHAR = ",";
    public static final String AV_CHAR = "\"";
    public CSVCreater(String arg) throws IOException {
        fos = new FileOutputStream(arg, false);
        sb = new StringBuffer();
    }
    public void setData(String data) {
        if (convertFlag)
            data = CSVEncode(data); 
        //sb.append(AV_CHAR);
        sb.append(data);
        //sb.append(AV_CHAR);
        sb.append(DEL_CHAR);
    }
    public void setConvertFlag(boolean b) {
        convertFlag = b;
    }
    public void writeLine() {
        if (sb.charAt(sb.length() - 1) == ',')
            sb.delete(sb.length() - 1, sb.length());
        sb.append("\r\n");
    }
    public void writeDataByLine(String[] args) {
        for (int i = 0; i < args.length; i++)
            setData(args[i]); 
        writeLine();
    }
    public void close() throws IOException {
        try {
            fos.write(sb.toString().getBytes());
        } catch (IOException e) {
            throw e;
        } finally {
            fos.close();
        }
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
    
    public static void makeFile(){
    	try {
            Database db=new Database();
            try {
				ResultSet rs=db.querySQL("select aa46,aa48,substring(aa49,1,2) as aa49 from lca.land group by aa46,aa48,substring(aa49,1,2) order by aa46,aa48,substring(aa49,1,2)");
				Vector tmpVector=new Vector();
				while(rs.next()){
					String[] tp=new String[3];
					tp[0]=rs.getString("aa46").trim();
					tp[1]=rs.getString("aa48").trim();
					tp[2]=rs.getString("aa49").trim();
					tmpVector.add(tp);
				}
				for(int i=0;i<tmpVector.size();i++){
					String[] tp=(String[])tmpVector.elementAt(i);
					String aa46=tp[0];
					String aa48=tp[1];
					String aa49=tp[2];
					CSVCreater csvCre = new CSVCreater("C:\\CVSTEMP\\"+tp[0]+tp[1]+tp[2]+".txt");
		            csvCre.setConvertFlag(true); 
		            ResultSet rs01=db.querySQL("select * from lca.land where aa46='"+tp[0]+"' and aa48='"+tp[1]+"' and substring(aa49,1,2)='"+tp[2]+"'");
				    while(rs01.next()){
				    	csvCre.setData(rs01.getString("aa48"));
				    	csvCre.setData(rs01.getString("aa49"));
				    	csvCre.setData(rs01.getString("aa46"));
				    	csvCre.setData(rs01.getString("aa10"));
				    	csvCre.setData(rs01.getString("aa16"));
				    	csvCre.setData(rs01.getString("aa17"));
				    	csvCre.writeLine();
				    }
				    csvCre.close();
				}
				System.out.println("Finish");
			} catch (Exception e) {
				// TODO 自動產生 catch 區塊
				e.printStackTrace();
			}
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        try {
            Database db=new Database();
            try {
				ResultSet rs=db.querySQL("select aa46,aa48,substring(aa49,1,2) as aa49 from lca.land group by aa46,aa48,substring(aa49,1,2)");
				while (rs.next()){
					CSVCreater csvCre = new CSVCreater("C:\\"+rs.getString("aa46")+rs.getString("aa48")+rs.getString("aa49")+".csv");
		            csvCre.setConvertFlag(true); 
		            ResultSet rs01=db.querySQL("select * from lca.land where aa46='"+rs.getString("aa46")+"' and aa48='"+rs.getString("aa48")+"' and substring(aa49,1,2)='"+rs.getString("aa49")+"'");
				    while(rs01.next()){
				    	csvCre.setData(rs01.getString("aa48"));
				    	csvCre.setData(rs01.getString("aa49"));
				    	csvCre.setData(rs01.getString("aa46"));
				    	csvCre.setData(rs01.getString("aa10"));
				    	csvCre.setData(rs01.getString("aa16"));
				    	csvCre.setData(rs01.getString("aa17"));
				    	csvCre.writeLine();
				    }
				    csvCre.close();
				}
			} catch (Exception e) {
				// TODO 自動產生 catch 區塊
				e.printStackTrace();
			}
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 
