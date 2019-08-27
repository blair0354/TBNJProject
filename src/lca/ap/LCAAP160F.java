package lca.ap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import util.Common;
import util.ODatabase;

public class LCAAP160F {

    private ServletContext context;
    
    private String uip;
    private String userID;
    private String unitID;
    
    private String state;
    private String cty;
    private String detailCty;
    private String stateOneAll;
    private String recreation;
    private String msg = "";
    private String queryTime = "";
    
    private Map unindexed = new LinkedHashMap();
    private Map detailStatus = new LinkedHashMap();
    
    public void doCheckOne(){
        System.out.println("### doCheckOne");
        LinkedHashMap result = new LinkedHashMap();
        Map ctys = getCtys();
        boolean isOk = check(getCty());
        System.out.println("###->" + getCty() + ", " + ctys.get(getCty()) + ", " + isOk);
        if(!isOk){
            result.put(getCty(), ctys.get(getCtys()));
        }
        
        setQueryTime("");
        setStateOneAll("one");
        setUnindexed(result);
    }
    
    public void doCheckAll() {
        System.out.println("### doCheckAll");
        LinkedHashMap result = new LinkedHashMap();
        Map ctys = getCtys();
        Iterator i = ctys.keySet().iterator();
        while(i.hasNext()){
            String ctyCode = (String)i.next();
            String ctyName = (String)ctys.get(ctyCode);
            boolean isOk = check(ctyCode);
            System.out.println("###->" + ctyCode + ", " + ctyName + ", " + isOk);
            if(!isOk){
                result.put(ctyCode, ctyName);
            }
        }
        
        setQueryTime("");
        setStateOneAll("all");
        setUnindexed(result);
    }
    
    public void showDetail(){
        setQueryTime(getSystime());
        
        System.out.println("### showDetail");
        String cty = getDetailCty();
        System.out.println("### cty:" + cty);
        detailStatus = checkDetail(cty);
    }
    
    public List doRecreate() {
        ArrayList errMsg = new ArrayList();
        System.out.println("### doRecreate");
        String recreationStr = getRecreation();
        System.out.println("### recreationStr:" + recreationStr);
        String[] data = recreationStr.split(";");
        String user = data[0];
        for(int i = 1; i < data.length; i++){
            String tableName = data[i].split(":")[0];
            String indexName = data[i].split(":")[1];
            List tempErrMsg = createIndex(user, indexName, tableName);
            if(tempErrMsg.size() > 0){
                errMsg.addAll(tempErrMsg);
                if(msg.length() == 0){
                    msg = "INDEX重建失敗,請排除原因後再重新執行!";
                }
            }
        }
        
        if(msg.length() == 0){
            msg = "INDEX重建完成";
        }
        
        return errMsg;
    }
    
    private boolean check(String cty){
        String[] ctyUser = getCtyUser(cty); // 可能有2個使用者
        for(int u = 0 ; u < ctyUser.length; u++){
            Map existedIndex = getExistedIndex(ctyUser[u]); // 該使用者的index
            Map indexConfig = getIndexConfig(); // index設定檔
            Iterator i = indexConfig.keySet().iterator();
            while(i.hasNext()){ // 從設定檔比對現存index
                String tabIndex = (String)i.next(); 
                // 將設定檔的資料(欄位以逗號(,)隔開)
                String[] idxColumns = ((String)indexConfig.get(tabIndex)).split(",");
                for(int idxCol = 0; idxCol < idxColumns.length; idxCol++){
                    String column = idxColumns[idxCol];
                    
                    // 現在DB, 該table的index設定
                    LinkedHashSet set = (LinkedHashSet)existedIndex.get(tabIndex);
                    if(set == null || !set.contains(column)){
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    /* 先保留修改
    private boolean check(String cty){
        String[] ctyUser = getCtyUser(cty); // 可能有2個使用者
        for(int u = 0 ; u < ctyUser.length; u++){
            Map existedIndex = getExistedIndex(ctyUser[u]); // 該使用者的index
            Map indexConfig = getIndexConfig(); // index設定檔
            Iterator i = indexConfig.keySet().iterator();
            while(i.hasNext()){ // 從設定檔比對現存index
                String tabIndex = (String)i.next(); 
                // 將設定檔的資料(欄位以逗號(,)隔開)
                String[] idxColumns = ((String)indexConfig.get(tabIndex)).split(",");
                LinkedHashSet existedSet = (LinkedHashSet)existedIndex.get(tabIndex);
                if(existedSet == null || existedSet.isEmpty()
                   || existedSet.size() != idxColumns.length){

                    return false;
                }
                
                for(int idxCol = 0; idxCol < idxColumns.length; idxCol++){
                    String column = idxColumns[idxCol];
                    
                    // 現在DB, 該table的index設定
                    if(!existedSet.contains(column)){
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    */
    
    private Map checkDetail(String cty){
        System.out.println("### checkDetail cty:" + cty);
        LinkedHashMap result = new LinkedHashMap();
        
        String[] ctyUser = getCtyUser(cty); // 可能有2個使用者
        for(int u = 0 ; u < ctyUser.length; u++){
            LinkedHashMap tableStatus = new LinkedHashMap();
            String userStr = ctyUser[u];
            
            Map existedIndex = getExistedIndex(ctyUser[u]); // 該使用者的index
            Map indexConfig = getIndexConfig(); // index設定檔
            Iterator i = indexConfig.keySet().iterator();
            while(i.hasNext()){ // 從設定檔比對現存index
                String tabIndex = (String)i.next(); 
                String tableName = tabIndex.split(":")[0];
                
                Boolean currStatus = (Boolean)tableStatus.get(tableName);
                if(currStatus == null){
                    tableStatus.put(tableName, new Boolean("true"));
                }

                // 將設定檔的資料(欄位以逗號(,)隔開)
                String[] idxColumns = ((String)indexConfig.get(tabIndex)).split(",");
                for(int idxCol = 0; idxCol < idxColumns.length; idxCol++){
                    String column = idxColumns[idxCol];
                    // 現在DB, 該table的index設定
                    LinkedHashSet set = (LinkedHashSet)existedIndex.get(tabIndex);
                    if(set == null || !set.contains(column)){
                        tableStatus.put(tableName, new Boolean("false"));
                        userStr += ";" + tabIndex;
                        break;
                    }
                }
            }
            
            result.put(userStr, tableStatus);
        }
        
        System.out.println("### checkDetail:" + result);
        return result;
    }
    
    private Map getExistedIndex(String ctyUser){
        String sql =
                "   SELECT A.TABLE_OWNER, A.TABLE_NAME, A.INDEX_NAME, A.COLUMN_NAME        "
                + "   FROM DBA_IND_COLUMNS A, DBA_INDEXES B                                "
                + "  WHERE 1 = 1                                                           "
                + "    AND A.TABLE_NAME IN ('RALID',                                       "
                + "                         'RBLOW',                                       "
                + "                         'RDBID',                                       "
                + "                         'REBOW',                                       "
                + "                         'RCLOR',                                       "
                + "                         'RLNID',                                       "
                + "                         'RKEYN',                                       "
                + "                         'RMNGR',                                       "
                + "                         'RTOGH')                                       "
                + "    AND A.INDEX_NAME NOT LIKE 'I_SNAP%'                                 ";
        
        if(cty != null && cty.length() > 0){
            sql += "    AND A.TABLE_OWNER = '" + ctyUser + "'                       "; 
        }
                
        sql += "    AND B.TABLE_NAME = A.TABLE_NAME                                     ";
        sql += "    AND B.INDEX_NAME = A.INDEX_NAME                                     ";
        sql += "    AND B.TABLE_OWNER = A.TABLE_OWNER                                   ";
        sql += "  ORDER BY A.TABLE_OWNER, A.TABLE_NAME, A.INDEX_NAME, A.COLUMN_POSITION ";

        System.out.println("SQL-1:" + sql);
        
        ODatabase db = new ODatabase();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        LinkedHashMap result = new LinkedHashMap();
        try{
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String TABLE_NAME = rs.getString("TABLE_NAME");
                String INDEX_NAME = rs.getString("INDEX_NAME");
                String COLUMN_NAME = rs.getString("COLUMN_NAME");
                
                String key = TABLE_NAME + ":" + INDEX_NAME;
                LinkedHashSet columns = (LinkedHashSet)result.get(key);
                if(columns == null){
                    columns = new LinkedHashSet();
                    result.put(key, columns);
                }

                columns.add(COLUMN_NAME);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }
        
        System.out.println("#### getExistedIndex:" + result);
        
        return result;
    }
    
    private Map getIndexConfig(){
        String sql = 
                "SELECT TAB_NAME, IDX_NAME, IDX_COLUMNS FROM EFORM3_5_INIT ORDER BY TAB_NAME";

        ODatabase db = new ODatabase();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        LinkedHashMap result = new LinkedHashMap();
        try{
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String TAB_NAME = rs.getString("TAB_NAME");
                String IDX_NAME = rs.getString("IDX_NAME");
                String IDX_COLUMNS = rs.getString("IDX_COLUMNS");
                
                String key = TAB_NAME + ":" + IDX_NAME;
                result.put(key, IDX_COLUMNS);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }
        
        System.out.println("#### getIndexConfig:" + result);
        
        return result;
    }
    
    private List createIndex(String ctyUser, String indexName, String table){
        
        ArrayList errMsg = new ArrayList();
        Map indexConfig = getIndexConfig();
        String columnStr = (String)indexConfig.get(table + ":" + indexName);
        
        String sql_drop = "DROP INDEX " + ctyUser + "." + indexName;
        
        String sql_create = 
                "CREATE INDEX " + ctyUser + "." + indexName + " ON " + ctyUser + "." + table + " (" + columnStr + ")";
        
        System.out.println("### drop:" + sql_drop);
        System.out.println("### create:" + sql_create);
        ODatabase db = new ODatabase();
        Connection conn = null;
        Statement stmt = null;

        String datetime = getSystime();
        try{
            conn = db.getConnection();
            stmt = conn.createStatement();

            try{
                stmt.execute(sql_drop);
                Common.logEFORM3_5_LOG(getUserID(), getUip(), datetime, ctyUser, table, sql_drop, "");
            }
            catch(SQLException e){
                System.out.println("drop index fail:" + e.getMessage());
                e.printStackTrace();
                Common.logEFORM3_5_LOG(getUserID(), getUip(), datetime, ctyUser, table, sql_drop, e.getMessage());
            }
            
            try{
                stmt.execute(sql_create);
                Common.logEFORM3_5_LOG(getUserID(), getUip(), datetime, ctyUser, table, sql_create, "");
            }
            catch(SQLException e){
                System.out.println("create index fail:" + e.getMessage());
                e.printStackTrace();
                Common.logEFORM3_5_LOG(getUserID(), getUip(), datetime, ctyUser, table, sql_create, e.getMessage());
                errMsg.add(new String[]{ctyUser, table, sql_create, e.getMessage()});
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            Common.logEFORM3_5_LOG(getUserID(), getUip(), datetime, ctyUser, table, "", e.getMessage());
            errMsg.add(new String[]{ctyUser, table, sql_create, e.getMessage()});
        }
        finally{
            closeStatement(stmt);
            closeConnection(conn);
        }
        
        return errMsg;
    }

    // 取得所有縣市
    public Map getCtys() {
        LinkedHashMap result = new LinkedHashMap();
        
        String sql = 
                " SELECT CTY, KCNT FROM EFORM5_0 WHERE CTY_YN = 'Y' ORDER BY CTY ";
        
        ODatabase db = new ODatabase();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                result.put(Common.get(rs.getString(1)), Common.isoToBig5(rs.getString(2)));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }
        
        return result;
    }
    
    public String[] getCtyUser(String cty) {
        String[] result = new String[0];
        
        String sql = 
                " SELECT CTY_USER, MERGE_CTY_USER FROM EFORM5_0 WHERE CTY = '" + cty + "' ";
        
        ODatabase db = new ODatabase();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                String CTY_USER = Common.get(rs.getString("CTY_USER"));
                String MERGE_CTY_USER = Common.get(rs.getString("MERGE_CTY_USER"));
                if(MERGE_CTY_USER.length() > 0){
                    result = new String[2];
                    result[0] = CTY_USER;
                    result[1] = MERGE_CTY_USER;
                }
                else{
                    result = new String[1];
                    result[0] = CTY_USER;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }
        
        return result;
    }
    
    private String getSystime(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int sec = c.get(Calendar.SECOND);
        return year + "/" + month + "/" + day + " "
                + (hour < 10 ? "0" + hour : hour) + ":"
                + (min < 10 ? "0" + min : min) + ":"
                + (sec < 10 ? "0" + sec : sec);        
    }
    
    private void closeConnection(Connection conn) {

        if(conn != null){
            try{
                conn.close();
            }
            catch(SQLException e){
            }
        }
    }

    private void closeStatement(Statement stmt) {
        if(stmt != null){
            try{
                stmt.close();
            }
            catch(SQLException e){
            }
        }
    }

    private void closeResultSet(ResultSet rs) {
        if(rs != null){
            try{
                rs.close();
            }
            catch(SQLException e){
            }
        }
    }

    public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

    public String getState() {
        return state == null ? "" : state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCty() {
        return cty == null ? "" : cty;
    }

    public void setCty(String cty) {
        this.cty = cty;
    }

    public String getDetailCty() {
        return detailCty;
    }

    public void setDetailCty(String detailCty) {
        this.detailCty = detailCty;
    }

    public String getStateOneAll() {
        return stateOneAll;
    }

    public void setStateOneAll(String stateOneAll) {
        this.stateOneAll = stateOneAll;
    }

    public String getRecreation() {
        return recreation;
    }

    public void setRecreation(String recreation) {
        this.recreation = recreation;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
    }

    public String getUip() {
        return uip;
    }

    public void setUip(String uip) {
        this.uip = uip;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public Map getUnindexed() {
        return unindexed;
    }

    public void setUnindexed(Map unindexed) {
        this.unindexed = unindexed;
    }
    
    public Map getDetailStatus() {
        return detailStatus;
    }

    public void setDetailStatus(Map detailStatus) {
        this.detailStatus = detailStatus;
    }

    public void setUnindexedCty(String[] unindexedCty){
        if(unindexedCty != null){
            for(int i = 0 ; i < unindexedCty.length; i++){
                String[] cty = unindexedCty[i].split(":");
                unindexed.put(cty[0], cty[1]);
                System.out.println("unindexedCty:" + cty[0] + "," + cty[1]);
            }
        }
    }
}
