


/*
*<br>程式目的：群組資料管理
*<br>程式代號：sysap002f
*<br>程式日期：0940701
*<br>程式作者：Dennis Chen
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ap;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;

public class SYSAP002F extends SuperBean{


String groupID;
String groupName;
String memo;

String q_groupID;
String q_groupName;

public String getGroupID(){ return checkGet(groupID); }
public void setGroupID(String s){ groupID=checkSet(s); }
public String getGroupName(){ return checkGet(groupName); }
public void setGroupName(String s){ groupName=checkSet(s); }
public String getMemo(){ return checkGet(memo); }
public void setMemo(String s){ memo=checkSet(s); }

public String getQ_groupID(){ return checkGet(q_groupID); }
public void setQ_groupID(String s){ q_groupID=checkSet(s); }
public String getQ_groupName(){ return checkGet(q_groupName); }
public void setQ_groupName(String s){ q_groupName=checkSet(s); }


//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
    String[][] checkSQLArray = new String[1][4];
    checkSQLArray[0][0]=" select count(*) as checkResult from SYSAP_Group where 1=1 " + 
        " and groupID = " + Common.sqlChar(groupID) + 
        "";
    checkSQLArray[0][1]=">";
    checkSQLArray[0][2]="0";
    checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
    return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
    String[] execSQLArray = new String[1];
    execSQLArray[0]=" insert into SYSAP_Group(" +
            " groupID,"+
            " groupName,"+
            " memo,"+
            " editID,"+
            " editDate,"+
            " editTime"+
        " )(select Isnull(max(groupID),0)+1 " +","+
            //Common.sqlChar(groupID) + "," +
            Common.sqlChar(groupName) + "," +
            Common.sqlChar(memo) + "," +
            Common.sqlChar(getEditID()) + "," +
            Common.sqlChar(getEditDate()) + "," +
            Common.sqlChar(getEditTime()) + " from sysap_group)" ;
    return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
    String[] execSQLArray = new String[1];
    execSQLArray[0]=" update SYSAP_Group set " +
            " groupName = " + Common.sqlChar(groupName) + "," +
            " memo = " + Common.sqlChar(memo) + "," +
            " editID = " + Common.sqlChar(getEditID()) + "," +
            " editDate = " + Common.sqlChar(getEditDate()) + "," +
            " editTime = " + Common.sqlChar(getEditTime()) + 
        " where 1=1 " + 
            " and groupID = " + Common.sqlChar(groupID) +
            "";
    return execSQLArray;
}


//傳回delete sql
protected String[] getDeleteSQL(){
    String[] execSQLArray = new String[2];
    execSQLArray[0]=" delete from SYSAP_Group where 1=1 " +
            " and groupID = " + Common.sqlChar(groupID) +
            "";
    execSQLArray[1]=" delete from SYSAP_AUTHORITY where 1=1 "+
            " and groupID= " + Common.sqlChar(groupID) +
    	    "";
    return execSQLArray;
}

/**
 * <br>
 * <br>目的：依GroupName查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSAP002F  queryInsertOne() throws Exception{
    Database db = new Database();
    SYSAP002F obj = this;
    try {
        String sql=" select a.groupID, a.groupName, a.memo, a.editID, a.editDate, a.editTime  "+
            " from SYSAP_Group a where 1=1 " +
            " and a.groupName = " + Common.sqlChar(groupName) +
            "";
        ResultSet rs = db.querySQL(sql);
        if (rs.next()){
            obj.setGroupID(rs.getString("groupID"));
            obj.setGroupName(rs.getString("groupName"));
            obj.setMemo(rs.getString("memo"));
            obj.setEditID(rs.getString("editID"));
            obj.setEditDate(rs.getString("editDate"));
            obj.setEditTime(rs.getString("editTime"));
        }
        setStateQueryOneSuccess();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        db.closeAll();
    }
    return obj;
}

/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSAP002F  queryOne() throws Exception{
    Database db = new Database();
    SYSAP002F obj = this;
    try {
        String sql=" select a.groupID, a.groupName, a.memo, a.editID, a.editDate, a.editTime  "+
            " from SYSAP_Group a where 1=1 " +
            " and a.groupID = " + Common.sqlChar(groupID) +
            "";
        ResultSet rs = db.querySQL(sql);
        if (rs.next()){
            obj.setGroupID(rs.getString("groupID"));
            obj.setGroupName(rs.getString("groupName"));
            obj.setMemo(rs.getString("memo"));
            obj.setEditID(rs.getString("editID"));
            obj.setEditDate(rs.getString("editDate"));
            obj.setEditTime(rs.getString("editTime"));
        }
        setStateQueryOneSuccess();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        db.closeAll();
    }
    return obj;
}

/**
 * <br>
 * <br>目的：依查詢欄位查詢多筆資料
 * <br>參數：無
 * <br>傳回：傳回ArrayList
*/

public ArrayList queryAll() throws Exception{
    Database db = new Database();
    ArrayList objList=new ArrayList();
    int counter=0;
    try {
        String sql=" select a.groupID, a.groupName, a.memo "+
            " from SYSAP_Group a where 1=1 "; 
            if (!"".equals(getQ_groupID()))
                sql+=" and a.groupID like " + Common.sqlChar(getQ_groupID()) ;
            if (!"".equals(getQ_groupName()))
                sql+=" and a.groupName like " + Common.sqlChar(getQ_groupName()) ;
        ResultSet rs = db.querySQL(sql);
        while (rs.next()){
            counter++;
            String rowArray[]=new String[3];
            rowArray[0]=rs.getString("groupID"); 
            rowArray[1]=rs.getString("groupName"); 
            rowArray[2]=rs.getString("memo"); 
            objList.add(rowArray);
            if (counter==getListLimit()){
                setErrorMsg(getListLimitError());
                break;
            }
        }
        setStateQueryAllSuccess();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        db.closeAll();
    }
    return objList;
}

}