
/*
*<br>程式目的：使用者資料權限管理
*<br>程式代號：sysap001f
*<br>程式日期：0950321
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.ap;

import java.sql.ResultSet;
import java.util.ArrayList;
import util.*;


public class SYSAP001F extends QueryBean{


String empID;
String empPWD;
String empName;
String groupID;
String organID;
String organIDName;
String unitID;
String empTitle;
String empTel;
String empFax;
String empEmail;
String takeJobDate;
String isStop;
String isOrganManager;
String isAdminManager;
String isManager;
String isDeptMgr;
String managerName;
String managerTitle;
String managerTel;
String memo;
String organ_mark;
String old_passwd;

String q_empID;
String q_empName;
String q_organID;
String q_organIDName;
String q_groupID;
String q_unitID;
String q_isStop;
String q_isAdminManager;
String q_isManager;


public String getEmpID(){ return checkGet(empID); }
public void setEmpID(String s){ empID=checkSet(s); }
public String getEmpPWD(){ return checkGet(empPWD); }
public void setEmpPWD(String s){ empPWD=checkSet(s); }
public String getEmpName(){ return checkGet(empName); }
public void setEmpName(String s){ empName=checkSet(s); }
public String getGroupID(){ return checkGet(groupID); }
public void setGroupID(String s){ groupID=checkSet(s); }
public String getOrganID(){ return checkGet(organID); }
public void setOrganID(String s){ organID=checkSet(s); }
public String getOrganIDName(){ return checkGet(organIDName); }
public void setOrganIDName(String s){ organIDName=checkSet(s); }
public String getUnitID(){ return checkGet(unitID); }
public void setUnitID(String s){ unitID=checkSet(s); }
public String getEmpTitle(){ return checkGet(empTitle); }
public void setEmpTitle(String s){ empTitle=checkSet(s); }
public String getEmpTel(){ return checkGet(empTel); }
public void setEmpTel(String s){ empTel=checkSet(s); }
public String getEmpFax(){ return checkGet(empFax); }
public void setEmpFax(String s){ empFax=checkSet(s); }
public String getEmpEmail(){ return checkGet(empEmail); }
public void setEmpEmail(String s){ empEmail=checkSet(s); }
public String getTakeJobDate(){ return checkGet(takeJobDate); }
public void setTakeJobDate(String s){ takeJobDate=checkSet(s); }
public String getIsStop(){ return checkGet(isStop); }
public void setIsStop(String s){ isStop=checkSet(s); }
public String getIsOrganManager(){ return checkGet(isOrganManager); }
public void setIsOrganManager(String s){ isOrganManager=checkSet(s); }
public String getIsAdminManager(){ return checkGet(isAdminManager); }
public void setIsAdminManager(String s){ isAdminManager=checkSet(s); }
public String getIsManager(){ return checkGet(isManager); }
public void setIsManager(String s){ isManager=checkSet(s); }
public String getIsDeptMgr(){ return checkGet(isDeptMgr); }
public void setIsDeptMgr(String s){ isDeptMgr=checkSet(s); }
public String getManagerName(){ return checkGet(managerName); }
public void setManagerName(String s){ managerName=checkSet(s); }
public String getManagerTitle(){ return checkGet(managerTitle); }
public void setManagerTitle(String s){ managerTitle=checkSet(s); }
public String getManagerTel(){ return checkGet(managerTel); }
public void setManagerTel(String s){ managerTel=checkSet(s); }
public String getMemo(){ return checkGet(memo); }
public void setMemo(String s){ memo=checkSet(s); }

public String getOld_passwd(){ return checkGet(old_passwd); }
public void setOld_passwd(String s){ old_passwd=checkSet(s); }

public String getOrgan_mark(){ return checkGet(organ_mark); }
public void setOrgan_mark(String s){ organ_mark=checkSet(s); }

public String getQ_empID(){ return checkGet(q_empID); }
public void setQ_empID(String s){ q_empID=checkSet(s); }
public String getQ_empName(){ return checkGet(q_empName); }
public void setQ_empName(String s){ q_empName=checkSet(s); }
public String getQ_organID(){ return checkGet(q_organID); }
public void setQ_organID(String s){ q_organID=checkSet(s); }
public String getQ_organIDName(){ return checkGet(q_organIDName); }
public void setQ_organIDName(String s){ q_organIDName=checkSet(s); }
public String getQ_groupID(){ return checkGet(q_groupID); }
public void setQ_groupID(String s){ q_groupID=checkSet(s); }
public String getQ_isAdminManager(){ return checkGet(q_isAdminManager); }
public void setQ_isAdminManager(String s){ q_isAdminManager=checkSet(s); }
public String getQ_isManager(){ return checkGet(q_isManager); }
public void setQ_isManager(String s){ q_isManager=checkSet(s); }
public String getQ_isStop(){ return checkGet(q_isStop); }
public void setQ_isStop(String s){ q_isStop=checkSet(s); }
public String getQ_unitID(){ return checkGet(q_unitID); }
public void setQ_unitID(String s){ q_unitID=checkSet(s); }

//傳回執行insert前之檢查sql
protected String[][] getInsertCheckSQL(){
    String[][] checkSQLArray = new String[1][4];
    checkSQLArray[0][0]=" select count(*) as checkResult from SYSAP_Emp where 1=1 " + 
        " and empID = " + Common.sqlChar(empID) + 
        "";
    checkSQLArray[0][1]=">";
    checkSQLArray[0][2]="0";
    checkSQLArray[0][3]="該筆資料己重複，請重新輸入！";
    return checkSQLArray;
}


//傳回insert sql
protected String[] getInsertSQL(){
	if ("".equals(getEmpPWD()))	setEmpPWD(this.getEmpID());
	
    String[] execSQLArray = new String[1];
    execSQLArray[0]=" insert into SYSAP_Emp(" +
            " empID,"+
            " empPWD,"+
            " empName,"+
            " groupID,"+
            " organID,"+
            " unitID,"+
            " empTitle,"+
            " empTel,"+
            " empFax,"+
            " empEmail,"+
            //" takeJobDate,"+
            " isStop,"+
            //" isOrganManager,"+
            " isAdminManager,"+
            //" isManager,"+
            //" isDeptMgr,"+
            //" managerName,"+
            //" managerTitle,"+
            //" managerTel,"+
            " memo,"+
            " organ_mark,"+
            " editID,"+
            " editDate,"+
            " editTime"+
        " )Values(" +
            Common.sqlChar(empID) + "," +
            Common.sqlChar(Common.getMD5PassWord(empPWD)) + "," +
            Common.sqlChar(empName) + "," +
            Common.sqlChar(groupID) + "," +
            Common.sqlChar(organID) + "," +
            Common.sqlChar(unitID) + "," +
            Common.sqlChar(empTitle) + "," +
            Common.sqlChar(empTel) + "," +
            Common.sqlChar(empFax) + "," +
            Common.sqlChar(empEmail) + "," +
            //Common.sqlChar(takeJobDate) + "," +
            Common.sqlChar(isStop) + "," +
            //Common.sqlChar(isOrganManager) + "," +
            Common.sqlChar(isAdminManager) + "," +
            //Common.sqlChar(isManager) + "," +
            //Common.sqlChar(isDeptMgr) + "," +
            //Common.sqlChar(managerName) + "," +
            //Common.sqlChar(managerTitle) + "," +
            //Common.sqlChar(managerTel) + "," +
            Common.sqlChar(memo) + "," +
            Common.sqlChar(organ_mark) + "," +
            Common.sqlChar(getEditID()) + "," +
            Common.sqlChar(getEditDate()) + "," +
            Common.sqlChar(getEditTime()) + ")" ;
    return execSQLArray;
}


//傳回update sql
protected String[] getUpdateSQL(){
    String[] execSQLArray = new String[1];
    execSQLArray[0]=" update SYSAP_Emp set ";
    execSQLArray[0]+=" empName = " + Common.sqlChar(empName) + ",";
    execSQLArray[0]+=" groupID = " + Common.sqlChar(groupID) + ",";
    execSQLArray[0]+=" organID = " + Common.sqlChar(organID) + ",";
    execSQLArray[0]+=" unitID = " + Common.sqlChar(unitID) + ",";
    execSQLArray[0]+=" empTitle = " + Common.sqlChar(empTitle) + ",";
    execSQLArray[0]+=" empTel = " + Common.sqlChar(empTel) + ",";
    execSQLArray[0]+=" empFax = " + Common.sqlChar(empFax) + ",";
    execSQLArray[0]+=" empEmail = " + Common.sqlChar(empEmail) + ",";
    if(!(empPWD.trim()).equals(old_passwd.trim())){
    	execSQLArray[0]+=" empPWD = " + Common.sqlChar(Common.getMD5PassWord(empPWD)) + ",";
    }
    //" takeJobDate = " + Common.sqlChar(takeJobDate) + "," +
    execSQLArray[0]+=" isStop = " + Common.sqlChar(isStop) + ",";
            //" isOrganManager = " + Common.sqlChar(isOrganManager) + "," +
    execSQLArray[0]+=" isAdminManager = " + Common.sqlChar(isAdminManager) + ",";
            //" isManager = " + Common.sqlChar(isManager) + "," +
            //" isDeptMgr = " + Common.sqlChar(isDeptMgr) + "," +
            //" managerName = " + Common.sqlChar(managerName) + "," +
            //" managerTitle = " + Common.sqlChar(managerTitle) + "," +
            //" managerTel = " + Common.sqlChar(managerTel) + "," +
    execSQLArray[0]+=" memo = " + Common.sqlChar(memo) + ",";
    execSQLArray[0]+=" organ_mark = " + Common.sqlChar(organ_mark) + "," ;
    execSQLArray[0]+=" editID = " + Common.sqlChar(getEditID()) + ",";
    execSQLArray[0]+=" editDate = " + Common.sqlChar(getEditDate()) + ",";
    execSQLArray[0]+=" editTime = " + Common.sqlChar(getEditTime()); 
    execSQLArray[0]+=" where 1=1 ";
    execSQLArray[0]+=" and empID = " + Common.sqlChar(empID);
    execSQLArray[0]+="";
    return execSQLArray;
}


//傳回update personal sql
protected String[] getUpdatePersonalSQL(){
	String[] execSQLArray = new String[1];
	execSQLArray[0]=" update SYSAP_Emp set ";
	execSQLArray[0]+=" empName = " + Common.sqlChar(empName) + "," ;
	execSQLArray[0]+=" unitID = " + Common.sqlChar(unitID) + ",";
	execSQLArray[0]+=" empTitle = " + Common.sqlChar(empTitle) + ",";
	execSQLArray[0]+=" empTel = " + Common.sqlChar(empTel) + ",";
	execSQLArray[0]+=" empFax = " + Common.sqlChar(empFax) + ",";
	execSQLArray[0]+=" empEmail = " + Common.sqlChar(empEmail) + ",";
	if(!(empPWD.trim()).equals(old_passwd.trim())){
        execSQLArray[0]+=" empPWD = " + Common.sqlChar(Common.getMD5PassWord(empPWD)) + ",";          
	}
          //" takeJobDate = " + Common.sqlChar(takeJobDate) + "," +
          //" managerName = " + Common.sqlChar(managerName) + "," +
          //" managerTitle = " + Common.sqlChar(managerTitle) + "," +
          //" managerTel = " + Common.sqlChar(managerTel) + "," +
	execSQLArray[0]+=" memo = " + Common.sqlChar(memo) + ",";
	execSQLArray[0]+=" organ_mark = " + Common.sqlChar(organ_mark) + "," ;
	execSQLArray[0]+=" editID = " + Common.sqlChar(getEditID()) + ",";
	execSQLArray[0]+=" editDate = " + Common.sqlChar(getEditDate()) + ",";
	execSQLArray[0]+=" editTime = " + Common.sqlChar(getEditTime()) ;
	execSQLArray[0]+=" where 1=1 ";
	execSQLArray[0]+=" and empID = " + Common.sqlChar(empID);
	execSQLArray[0]+="";
	System.out.println("empPWD==>"+empPWD);
	System.out.println("old_passwd==>"+old_passwd);
	return execSQLArray;
}

public void updatePersonal(){
	Database db = new Database();
	try {
		if (!beforeExecCheck(getUpdateCheckSQL())){
			setStateUpdateError();
		}else{					
			db.excuteSQL(getUpdatePersonalSQL());		
			setStateUpdateSuccess();
			setErrorMsg("修改完成");			
		}	
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}			
}	


//傳回delete sql
protected String[] getDeleteSQL(){
    String[] execSQLArray = new String[1];
    execSQLArray[0]=" delete SYSAP_Emp where 1=1 " +
            " and empID = " + Common.sqlChar(empID) +
            "";
    return execSQLArray;
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public SYSAP001F  queryOne() throws Exception{
    Database db = new Database();
    SYSAP001F obj = this;
    try {
        String sql=" select a.empID, a.empPWD, a.empName, a.groupID, a.organID, a.unitID, a.empTitle, a.empTel, a.empFax, a.empEmail, a.takeJobDate, a.isStop, a.isOrganManager, a.isAdminManager, a.isManager, a.isDeptMgr, a.managerName, a.managerTitle, a.managerTel, a.memo, a.editID, a.editDate, a.editTime, a.organ_mark, b.organSName "+
            " from SYSAP_Emp a, SYSCA_Organ b where 1=1 " +
            " and a.empID = " + Common.sqlChar(empID) +
            " and b.organID = a.organID "+
           	"";
        ResultSet rs = db.querySQL(sql);
        if (rs.next()){
            obj.setEmpID(rs.getString("empID"));
            obj.setEmpPWD(rs.getString("empPWD"));
            obj.setEmpName(rs.getString("empName"));
            obj.setGroupID(rs.getString("groupID"));
            obj.setOrganID(rs.getString("organID"));
            obj.setUnitID(rs.getString("unitID"));
            obj.setEmpTitle(rs.getString("empTitle"));
            obj.setEmpTel(rs.getString("empTel"));
            obj.setEmpFax(rs.getString("empFax"));
            obj.setEmpEmail(rs.getString("empEmail"));
            //obj.setTakeJobDate(rs.getString("takeJobDate"));
            obj.setIsStop(rs.getString("isStop"));
            //obj.setIsOrganManager(rs.getString("isOrganManager"));
            obj.setIsAdminManager(rs.getString("isAdminManager"));
            //obj.setIsManager(rs.getString("isManager"));
            //obj.setIsDeptMgr(rs.getString("isDeptMgr"));
            //obj.setManagerName(rs.getString("managerName"));
            //obj.setManagerTitle(rs.getString("managerTitle"));
            //obj.setManagerTel(rs.getString("managerTel"));
            obj.setMemo(rs.getString("memo"));
            obj.setEditID(rs.getString("editID"));
            obj.setEditDate(rs.getString("editDate"));
            obj.setEditTime(rs.getString("editTime"));
            obj.setOrganIDName(rs.getString("organSName"));
            obj.setOrgan_mark(rs.getString("organ_mark"));
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
    try {
        String sql=" select a.empID, a.empName, a.empTitle, a.empTel "+
            " from SYSAP_Emp a where 1=1 "; 
            if (!"".equals(getQ_empID()))
                sql+=" and a.empID = " + Common.sqlChar(getQ_empID()) ;
            if (!"".equals(getQ_groupID()))
                sql+=" and a.groupID = " + Common.sqlChar(getQ_groupID()) ;            
            if (!"".equals(getQ_empName()))
                sql+=" and a.empName like '%" + getQ_empName() +"%' ";
            if (!"".equals(getQ_organID()))
                sql+=" and a.organID = " + Common.sqlChar(getQ_organID()) ;
            if (!"".equals(getQ_isStop()))
                sql+=" and a.isStop = " + Common.sqlChar(getQ_isStop()) ;
            //if (!"".equals(getQ_isManager()))
                //sql+=" and a.isManager = " + Common.sqlChar(getQ_isManager()) ;
            if (!"".equals(getQ_isAdminManager()))
                sql+=" and a.isAdminManager = " + Common.sqlChar(getQ_isAdminManager()) ;
            if (!"".equals(getQ_unitID()))
                sql+=" and a.unitID like '%" + getQ_unitID() + "%' ";
            //System.out.print(sql);
            
        ResultSet rs = db.querySQL(sql+" order by a.organID, a.empID ", true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;                
	            String rowArray[]=new String[4];
	            rowArray[0]=rs.getString("empID"); 
	            rowArray[1]=rs.getString("empName"); 
	            rowArray[2]=rs.getString("empTitle"); 
	            rowArray[3]=rs.getString("empTel"); 
	            objList.add(rowArray);
				count++;
			} while (rs.next());
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
