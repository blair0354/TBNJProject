/*
 *<br>程式目的：lcaap010f
 *<br>程式代號：lcaap010f
 *<br>程式日期：0970512
 *<br>程式作者：Richard Li
 *<br>--------------------------------------------------------
 *<br>修改作者　　修改日期　　　修改目的
 *<br>--------------------------------------------------------
 */

package lca.ap;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import util.*;
import util.report.ReportEnvironment;
import util.report.TableModelReportEnvironment;
import util.report.TableModelReportGenerator;

import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.swing.table.DefaultTableModel;

public class LCAAP140F extends QueryBean {
	String queryType;
	String print_kind;
	String print_type;

	String itemPicture1;
	String itemPicture2;
	

	String filestoreLocation;
	String reportLocation;
	String uip;
	String qry_date_start;
	String qry_time_start;
	String qry_date_end;
	String qry_time_end;
	String qry;
	String dqry;
	String qry_msg;
	String con;
	String qry_seq;
	String check_RHD10;//106增修案
	ServletContext context;
	Vector name_stock=new Vector();//存放查詢名字
	Vector sn_stock=new Vector();//存放統編
	Vector s_ym=new Vector();//比較基準日期
	
	ArrayList<String []> txtData = new ArrayList<String []>();

	public String getQueryType() {return checkGet(queryType);}
	public void setQueryType(String itemPicture1) {this.queryType = checkSet(itemPicture1);}
	
	public String getPrint_kind() {	return checkGet(print_kind);}
	public void setPrint_kind(String print_type) {this.print_kind = checkSet(print_type);}
	
	public String getPrint_type() {	return checkGet(print_type);}
	public void setPrint_type(String print_type) {this.print_type = checkSet(print_type);}
	
	public String getItemPicture1() {return checkGet(itemPicture1);}
	public void setItemPicture1(String itemPicture1) {this.itemPicture1 = checkSet(itemPicture1);}

	public String getItemPicture2() {return checkGet(itemPicture2);}
	public void setItemPicture2(String itemPicture2) {this.itemPicture2 = checkSet(itemPicture2);}

	public String getFilestoreLocation() {return checkGet(filestoreLocation);}
	public void setFilestoreLocation(String filestoreLocation) {this.filestoreLocation = checkSet(filestoreLocation);}

	public String getReportLocation() {return checkGet(reportLocation);}
	public void setReportLocation(String reportLocation) {this.reportLocation = checkSet(reportLocation);}

	public String getUip() {return checkGet(uip);}
	public void setUip(String uip) {this.uip = checkSet(uip);}

	public String getDqry() {return checkGet(dqry);	}
	public void setDqry(String dqry) {this.dqry = checkSet(dqry);}

	public String getQry() {return checkGet(qry);}
	public void setQry(String qry) {this.qry = checkSet(qry);}
	
	public String getQry_date_end() {return checkGet(qry_date_end);	}
	public void setQry_date_end(String qry_date_end) {this.qry_date_end = checkSet(qry_date_end);}
	
	public String getQry_date_start() {	return checkGet(qry_date_start);}
	public void setQry_date_start(String qry_date_start) {this.qry_date_start = checkSet(qry_date_start);}
	
	public String getQry_msg() {return checkGet(qry_msg);}
	public void setQry_msg(String qry_msg) {this.qry_msg = checkSet(qry_msg);}
	
	public String getQry_time_end() {return checkGet(qry_time_end);	}
	public void setQry_time_end(String qry_time_end) {this.qry_time_end = checkSet(qry_time_end);}
	
	public String getQry_time_start() {	return checkGet(qry_time_start);}
	public void setQry_time_start(String qry_time_start) {this.qry_time_start = checkSet(qry_time_start);}

	public String getCon() {return checkGet(con);}
	public void setCon(String con) {this.con = checkSet(con);}

	public String getQry_seq() {return checkGet(qry_seq);}
	public void setQry_seq(String qry_seq) {this.qry_seq = checkSet(qry_seq);}
	
	//106年增修案
	public String getCheck_RHD10() {return checkGet(check_RHD10);}
	public void setCheck_RHD10(String s) {this.check_RHD10 = checkSet(s);}
	

	public ServletContext getContext() {return context;	}
	public void setContext(ServletContext context) {this.context = context;	}

	//土地PDF
	public DefaultTableModel getLandResultModel() throws Exception {
		qry_date_start = Datetime.getYYYMMDD();
		qry_time_start = Datetime.getHHMMSS();
		qry_seq = "J" + qry_date_start + qry_time_start;
		this.setQry_seq(qry_seq);
		con = "勞保局土地歸戶清冊";
		qry = "市縣市=;事務所=;統一編號=;姓名=";
		qry += ";統編批次=" + this.getItemPicture1() + ";姓名批次=" + this.getItemPicture2();
		
		Common.insertDL_LOG(qry_seq, "J", this.getUserID(), this.getUnitID(), qry, this.getPrint_type(), qry_date_start, qry_time_start);
		
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		ODatabase db = new ODatabase();
		ResultSet rs_txt = null;
		ResultSet rs_ow = null;
		ResultSet rs = null;
		
		Vector columns = new Vector();
		columns.addElement("lidn");//1
		columns.addElement("lnam");//2
		columns.addElement("aa45");//3
		columns.addElement("aa46");//4
		columns.addElement("ba48");//5
		columns.addElement("ba49");//6
		columns.addElement("ba49_1");//7
		columns.addElement("bb06");//8
		columns.addElement("bb07");//9
		columns.addElement("bb05");//10
		columns.addElement("bb15_1");//11
		columns.addElement("s_ym");//12

		try {
			Vector rowData = new Vector();
			//將原本透過使用者勾選的資料，改成從上傳的文字檔全數直接至rlnid搜尋後全數取出
			for(String s[] : txtData){
				boolean dataExist = false;
				
				String qLidn = "";  //查詢統一編號
				String qLnam = "";  //查詢名稱
				String s_ym = Common.get(s[1]);  //比較基準日期
				String sql_txt = " select lidn, lnam, cty, unit from rlnid "
						+ " where 1=1 ";
				//根據上傳的條件種類決定
				if(queryType.equals("1")){
					qLidn = Common.get(s[0]);
					sql_txt += " and LIDN = " + Common.sqlChar(qLidn);
				}else{
					qLnam = Common.get(s[0]);
					sql_txt += " and LNAM = " + Common.sqlChar(qLnam);
				}
				sql_txt += " order by lidn, cty, unit ";
//System.out.println("sql_txt="+sql_txt);			
				rs_txt = db.querySQL(sql_txt);
				while(rs_txt.next()){
Long start = System.currentTimeMillis();
					String lidn = rs_txt.getString("lidn");
					String lnam = rs_txt.getString("lnam");
					String cty = rs_txt.getString("cty");
					String unit = rs_txt.getString("unit");
					
					Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", "", "", "", "", "", "", lidn, Common.isoToBig5(lnam), "", "", "", "", qry_seq, "2");
					
					String sql_ow = "select "
							+ " rb.cty, rb.unit, rb.ba48, rb.ba49, rb.bb06, rb.bb07, rb.bb05, rb.bb09, rb.bb15_1, rb.bb15_2, rb.bb15_3, "
							+ " rk06.kcnt as ch_bb06, "
							+ " rk15.kcnt as cht_bb15 "  
							+ " from RBLOW RB "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='06' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk06 "  /*人檔資料*/
							+ " on rk06.KCDE_2 =  rb.bb06 "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='15' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk15 "  /*人檔資料*/
							+ " on rk15.KCDE_2 =  rb.bb15_1 "
							+ " where 1=1 "
							+ " and rb.bb09 = " + Common.sqlChar(lidn)  /*人檔資料*/
							+ " and rb.cty = " + Common.sqlChar(cty)  /*人檔資料*/
							+ " and rb.unit = " + Common.sqlChar(unit)  /*人檔資料*/
							+ " order by rb.cty, rb.unit, rb.ba48, rb.ba49, rb.bb01 ";
//System.out.println("sql_ow="+sql_ow);
					rs_ow = db.querySQL(sql_ow);
					while (rs_ow.next()) {
						String sql_id = " select "
								+ " ra.cty,ra.unit,ra.aa48, ra.aa49, ra.aa45, ra.aa46, ra.aa10, ra.aa11, ra.aa12, ra.aa16, "
								+ " (select max(kcnt) from rkeyn where kcde_1='45' and kcde_2=ra.aa45 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_cty, "  /*人檔資料*/
								+ " (select max(kcnt) from rkeyn where kcde_1='46' and kcde_2=ra.aa46 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa46, " /*人檔資料*/ 
								+ " (select max(kcnt) from rkeyn where kcde_1='48' and kcde_2=ra.aa48 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa48 "  /*人檔資料*/ 
								+ " FROM RALID RA "
								+ " where 1=1 "
								+ " and ra.aa48 = " + Common.sqlChar(rs_ow.getString("ba48"))
								+ " and ra.aa49 = " + Common.sqlChar(rs_ow.getString("ba49"))
								+ " and ra.cty = " + Common.sqlChar(cty)
								+ " and ra.unit = " + Common.sqlChar(unit);
//System.out.println("sql_id="+sql_id);
						rs = db.querySQL(sql_id);
						while (rs.next()) {
							dataExist = true;
							Vector data = new Vector();
							data.add(Common.get(lidn));
							data.add(Common.isoToMS950(lnam));
							data.add(Common.isoToMS950(rs.getString("ch_cty")));
							data.add(Common.isoToMS950(rs.getString("ch_aa46")));
							data.add(Common.isoToMS950(rs.getString("ch_aa48")));
							data.add(Common.get(rs_ow.getString("ba49").substring(0, 4)));
							data.add(Common.get(rs_ow.getString("ba49").substring(4)));
							data.add(Common.isoToMS950(rs_ow.getString("ch_bb06")));
							data.add(Common.get(rs_ow.getString("bb07")));
							data.add(Common.get(rs_ow.getString("bb05")));
							if (Common.get(rs_ow.getString("bb15_1")).equals("")) {
								data.add(Common.get(rs_ow.getString("bb15_2")) + " 分之 " + Common.get(rs_ow.getString("bb15_3")));
							} else {
								data.add(Common.isoToMS950(rs_ow.getString("cht_bb15")) + Common.get(rs_ow.getString("bb15_2")) + " 分之 "+ Common.get(rs_ow.getString("bb15_3")));
							}
							data.add(s_ym);  //比較基準日期
							rowData.add(data);
						}
						rs.getStatement().close();
						rs.close();
					}
					rs_ow.getStatement().close();
					rs_ow.close();
					
System.out.println("LCAAP140F土地-->> query: lidn=" + lidn + ", cty=" + cty + ", unit=" + unit + ", time=" + (System.currentTimeMillis() - start));
				}
				rs_txt.getStatement().close();
				rs_txt.close();
				
				if(!dataExist){
					Vector data = new Vector();
					data.addElement(qLidn);
					data.addElement(qLnam);
					data.addElement("");
					data.addElement("");
					data.addElement("無符合條件");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement(s_ym);
					rowData.add(data);
				}
			}
			model.setDataVector(rowData, columns);
			Common.updateBS_LOG(Datetime.getYYYMMDD(), Datetime.getFHHMMSS(),"已完成查詢", qry_seq);
			this.setState("init");
		} catch (Exception e) {
			Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			e.printStackTrace();
		} finally {
			try { if (rs_txt!=null){ rs_txt.close(); rs_txt=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs_ow!=null){ rs_ow.close(); rs_ow=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
			db.closeAll();
		}
		return model;
	}

	//土地TXT
	public DefaultTableModel getLandCSVResultModel() throws Exception {
		qry_date_start = Datetime.getYYYMMDD();
		qry_time_start = Datetime.getHHMMSS();
		qry_seq = "J" + qry_date_start + qry_time_start;
		this.setQry_seq(qry_seq);
		con = "勞保局土地歸戶清冊";
		qry = "市縣市=;事務所=;統一編號=;姓名=";
		qry += ";統編批次=" + this.getItemPicture1() + ";姓名批次=" + this.getItemPicture2();
		
		Common.insertDL_LOG(qry_seq, "J", this.getUserID(), this.getUnitID(),qry, this.getPrint_type(), qry_date_start, qry_time_start);
		
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		ODatabase db = new ODatabase();
		ResultSet rs_txt = null;
		ResultSet rs_ow = null;
		ResultSet rs = null;
		
		Vector columns = new Vector();
		columns.addElement("lidn");//1
		columns.addElement("lnam");//2
		columns.addElement("aa45");//3
		columns.addElement("chtaa45");//4
		columns.addElement("aa46");//5
		columns.addElement("chtaa46");//6
		columns.addElement("ba48");//7
		columns.addElement("chtba48");//8
		columns.addElement("ba49");//9
		columns.addElement("ba49_1");//10
		columns.addElement("bb06");//11
		columns.addElement("chtbb06");//12
		columns.addElement("bb07");//13
		columns.addElement("bb05");//14
		columns.addElement("bb15_1");//15
		columns.addElement("cht_bb15");//16
		columns.addElement("bb15_2");//17
		columns.addElement("bb15_3");//18
		columns.addElement("s_ym");//19

		try {
			Vector rowData = new Vector();
			//將原本透過使用者勾選的資料，改成從上傳的文字檔全數直接至rlnid搜尋後全數取出
			for(String s[] : txtData){
				boolean dataExist = false;
				
				String qLidn = "";  //查詢統一編號
				String qLnam = "";  //查詢名稱
				String s_ym = Common.get(s[1]);  //比較基準日期
				String sql_txt = " select lidn, lnam, cty, unit from rlnid "
						+ " where 1=1 ";
				//根據上傳的條件種類決定
				if(queryType.equals("1")){
					qLidn = Common.get(s[0]);
					sql_txt += " and LIDN = " + Common.sqlChar(qLidn);
				}else{
					qLnam = Common.get(s[0]);
					sql_txt += " and LNAM = " + Common.sqlChar(qLnam);
				}
				sql_txt += " order by LIDN, cty, unit ";
			
//System.out.println("sql_txt="+sql_txt);
				rs_txt = db.querySQL(sql_txt);
				while(rs_txt.next()){
Long start = System.currentTimeMillis();
					String lidn = rs_txt.getString("lidn");
					String lnam = rs_txt.getString("lnam");
					String cty = rs_txt.getString("cty");
					String unit = rs_txt.getString("unit");
					
					Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", "", "", "", "", "", "", lidn, Common.isoToBig5(lnam), "", "", "", "", qry_seq, "2");
					
					String sql_ow = "select "
							+ " rb.cty, rb.unit, rb.ba48, rb.ba49, rb.bb06, rb.bb07, rb.bb05, rb.bb09, rb.bb15_1, rb.bb15_2, rb.bb15_3, "
							+ " rk06.kcnt as ch_bb06, "
							+ " rk15.kcnt as cht_bb15 "  
							+ " from RBLOW RB "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='06' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk06 "  /*人檔資料*/
							+ " on rk06.KCDE_2 =  rb.bb06 "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='15' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk15 "  /*人檔資料*/
							+ " on rk15.KCDE_2 =  rb.bb15_1 "
							+ " where 1=1 "
							+ " and rb.bb09 = " + Common.sqlChar(lidn)  /*人檔資料*/
							+ " and rb.cty = " + Common.sqlChar(cty)  /*人檔資料*/
							+ " and rb.unit = " + Common.sqlChar(unit)  /*人檔資料*/
							+ " order by rb.cty, rb.unit, rb.ba48, rb.ba49, rb.bb01 ";
//System.out.println("sql_ow="+sql_ow);
					rs_ow = db.querySQL(sql_ow);
					while (rs_ow.next()) {
						String sql_id = " select "
								+ " ra.cty,ra.unit,ra.aa48, ra.aa49, ra.aa45, ra.aa46, ra.aa10, ra.aa11, ra.aa12, ra.aa16, "
								+ " (select max(kcnt) from rkeyn where kcde_1='45' and kcde_2=ra.aa45 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_cty, "  /*人檔資料*/
								+ " (select max(kcnt) from rkeyn where kcde_1='46' and kcde_2=ra.aa46 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa46, " /*人檔資料*/ 
								+ " (select max(kcnt) from rkeyn where kcde_1='48' and kcde_2=ra.aa48 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa48 "  /*人檔資料*/ 
								+ " FROM RALID RA "
								+ " where 1=1 "
								+ " and ra.aa48 = " + Common.sqlChar(rs_ow.getString("ba48"))
								+ " and ra.aa49 = " + Common.sqlChar(rs_ow.getString("ba49"))
								+ " and ra.cty = " + Common.sqlChar(cty)
								+ " and ra.unit = " + Common.sqlChar(unit);
//System.out.println("sql_id="+sql_id);
						rs = db.querySQL(sql_id);
						while (rs.next()) {
							dataExist = true;
							Vector data = new Vector();
							data.add(Common.get(lidn));
							data.add(Common.isoToMS950(lnam));
							data.add(Common.get(rs.getString("aa45")));
							data.add(Common.isoToMS950(rs.getString("ch_cty")));
							data.add(Common.get(rs.getString("aa46")));
							data.add(Common.isoToMS950(rs.getString("ch_aa46")));
							data.add(Common.get(rs.getString("aa48")));
							data.add(Common.isoToMS950(rs.getString("ch_aa48")));
							data.add(Common.get(rs_ow.getString("ba49").substring(0, 4)));
							data.add(Common.get(rs_ow.getString("ba49").substring(4)));
							data.add(Common.get(rs_ow.getString("bb06")));
					        data.add(Common.isoToMS950(rs_ow.getString("ch_bb06")));
							data.add(Common.get(rs_ow.getString("bb07")));
							data.add(Common.get(rs_ow.getString("bb05")));
							data.add(Common.get(rs_ow.getString("bb15_1")));
							data.add(Common.isoToMS950(rs_ow.getString("cht_bb15")));
							data.add(Common.get(rs_ow.getString("bb15_2")));
							data.add(Common.get(rs_ow.getString("bb15_3")));
							data.add(s_ym);  //比較基準日期
							rowData.add(data);
						}
						rs.getStatement().close();
						rs.close();
					}
					rs_ow.getStatement().close();
					rs_ow.close();

System.out.println("LCAAP140F土地-->> query: lidn=" + lidn + ", cty=" + cty + ", unit=" + unit + ", time=" + (System.currentTimeMillis() - start));
				}
				rs_txt.getStatement().close();
				rs_txt.close();
				
				if(!dataExist){
					Vector data = new Vector();
					data.addElement(qLidn);
					data.addElement(qLnam);
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("無符合條件");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement(s_ym);
					rowData.add(data);
				}
			}
			model.setDataVector(rowData, columns);
			Common.updateBS_LOG(Datetime.getYYYMMDD(), Datetime.getFHHMMSS(),"已完成查詢", qry_seq);
			this.setState("init");
		} catch (Exception e) {
			Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			e.printStackTrace();
		} finally {
			try { if (rs_txt!=null){ rs_txt.close(); rs_txt=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs_ow!=null){ rs_ow.close(); rs_ow=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
			db.closeAll();
		}
		return model;
	}

	//建物PDF
	public DefaultTableModel getBuildResultModel() throws Exception {
		qry_date_start = Datetime.getYYYMMDD();
		qry_time_start = Datetime.getHHMMSS();
		qry_seq = "K" + qry_date_start + qry_time_start;
		this.setQry_seq(qry_seq);
		con = "勞保局建物歸戶清冊";
		qry = "市縣市=;事務所=;統一編號=;姓名=";
		qry += ";統編批次=" + this.getItemPicture1() + ";姓名批次=" + this.getItemPicture2();
		
		Common.insertDL_LOG(qry_seq, "K", this.getUserID(), this.getUnitID(), qry, this.getPrint_type(), qry_date_start, qry_time_start);
		
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		ODatabase db = new ODatabase();
		ResultSet rs_txt = null;
		ResultSet rs_ow = null;
		ResultSet rs = null;
		
		Vector columns = new Vector();
		columns.addElement("lidn");//1
		columns.addElement("lnam");//2
		columns.addElement("dd45");//3
		columns.addElement("dd46");//4
		columns.addElement("ed48");//5
		columns.addElement("ed49");//6
		columns.addElement("ed49_1");//7
		columns.addElement("ee06");//8
		columns.addElement("ee07");//9
		columns.addElement("ee05");//10
		columns.addElement("ee15_1");//11
		columns.addElement("dd09");//12
		columns.addElement("s_ym");//13
		
		if("Y".equals(getCheck_RHD10())){
			columns.addElement("HD4849C");
		}
		
		try {
			Vector rowData = new Vector();
			//將原本透過使用者勾選的資料，改成從上傳的文字檔全數直接至rlnid搜尋後全數取出
			for(String s[] : txtData){
				boolean dataExist = false;
				
				String qLidn = "";  //查詢統一編號
				String qLnam = "";  //查詢名稱
				String s_ym = Common.get(s[1]);  //比較基準日期
				String sql_txt = " select lidn, lnam, cty, unit from rlnid "
						+ " where 1=1 ";
				//根據上傳的條件種類決定
				if(queryType.equals("1")){
					qLidn = Common.get(s[0]);
					sql_txt += " and LIDN = " + Common.sqlChar(qLidn);
				}else{
					qLnam = Common.get(s[0]);
					sql_txt += " and LNAM = " + Common.sqlChar(qLnam);
				}
				sql_txt += " order by LIDN, cty, unit ";

//System.out.println("sql_txt="+sql_txt);
				rs_txt = db.querySQL(sql_txt);
				while(rs_txt.next()){
Long start = System.currentTimeMillis();
					String lidn = rs_txt.getString("lidn");
					String lnam = rs_txt.getString("lnam");
					String cty = rs_txt.getString("cty");
					String unit = rs_txt.getString("unit");
					
					Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", "", "", "", "", "", "", lidn, Common.isoToBig5(lnam), "", "", "", "", qry_seq, "2");
					
					String sql_ow = "select "
							+ " re.cty, re.unit, re.ed48, re.ed49, re.ee09, re.ee06, re.ee07, re.ee05, re.ee15_1, re.ee15_2, re.ee15_3, "
							+ " rk06.kcnt as ch_ee06, "
							+ " rk15.kcnt as cht_ee15 "
							+ " from REBOW RE "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='06' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk06 "  /*人檔資料*/
							+ " on rk06.KCDE_2 =  re.ee06 "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='15' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk15 "  /*人檔資料*/
							+ " on rk15.KCDE_2 =  re.ee15_1 "
							+ " where 1=1 "
							+ " and re.ee09 = " + Common.sqlChar(lidn)  /*人檔資料*/
							+ " and re.cty = " + Common.sqlChar(cty)  /*人檔資料*/
							+ " and re.unit = " + Common.sqlChar(unit)  /*人檔資料*/
							+ " order by re.cty, re.unit, re.ed48, re.ed49, re.ee01";
//System.out.println("sql_ow="+sql_ow);
					rs_ow = db.querySQL(sql_ow);
					while(rs_ow.next()){
						String sql_id = " select "
								+ " rd.dd45 as aa45, rd.dd46 as aa46, rd.dd48 as aa48, rd.dd49 as aa49, rd.dd09, rd.cty, rd.unit, "
								+ " (select max(kcnt) from rkeyn where kcde_1='45' and kcde_2=rd.dd45 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_cty, "  /*人檔資料*/
								+ " (select max(kcnt) from rkeyn where kcde_1='46' and kcde_2=rd.dd46 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa46, " /*人檔資料*/ 
								+ " (select max(kcnt) from rkeyn where kcde_1='48' and kcde_2=rd.dd48 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa48 "  /*人檔資料*/ 
								+ " FROM RDBID RD "
								+ " where 1=1 "
								+ " and rd.dd48 = " + Common.sqlChar(rs_ow.getString("ed48"))
								+ " and rd.dd49 = " + Common.sqlChar(rs_ow.getString("ed49"))
								+ " and rd.cty = " + Common.sqlChar(cty)
								+ " and rd.unit = " + Common.sqlChar(unit);
//System.out.println("sql_id="+sql_id);
						rs = db.querySQL(sql_id);
						while (rs.next()) {
							dataExist = true;
							Vector data = new Vector();
							data.add(Common.get(lidn));
							data.add(Common.isoToMS950(lnam));
							data.add(Common.isoToMS950(rs.getString("ch_cty")));
							data.add(Common.isoToMS950(rs.getString("ch_aa46")));
							data.add(Common.isoToMS950(rs.getString("ch_aa48")));
							data.add(Common.get(rs_ow.getString("ed49").substring(0, 5)));
							data.add(Common.get(rs_ow.getString("ed49").substring(5)));
						    data.add(Common.isoToMS950(rs_ow.getString("ch_ee06")));
							data.add(Common.get(rs_ow.getString("ee07")));
							data.add(Common.get(rs_ow.getString("ee05")));
							if (Common.get(rs_ow.getString("ee15_1")).equals("")) {
								data.add(Common.get(rs_ow.getString("ee15_2")) + " 分之 "+ Common.get(rs_ow.getString("ee15_3")));
								data.add(Common.isoToMS950(rs.getString("dd09")));
							} else {
								data.add(Common.isoToMS950(rs_ow.getString("cht_ee15")) + Common.get(rs_ow.getString("ee15_2")) + " 分之 " + Common.get(rs_ow.getString("ee15_3")));
								data.add(Common.isoToMS950(rs.getString("dd09")));
							}
							data.add(Common.get(s_ym));  //比較基準日期
							
							if("Y".equals(getCheck_RHD10())){
								data.add(getRHD10List(rs_ow.getString("ed48"),rs_ow.getString("ed49"),cty,unit)[1]);
	                            
							}
							
							rowData.add(data);
						}
						rs.getStatement().close();
						rs.close();
					}
					rs_ow.getStatement().close();
					rs_ow.close();
					
System.out.println("LCAAP140F建物-->> query: lidn=" + lidn + ", cty=" + cty + ", unit=" + unit + ", time=" + (System.currentTimeMillis() - start));
				}
				rs_txt.getStatement().close();
				rs_txt.close();
				
				if(!dataExist){
					Vector data = new Vector();
					data.addElement(qLidn);
					data.addElement(qLnam);
					data.addElement("");
					data.addElement("");
					data.addElement("無符合條件");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement(s_ym);
					if("Y".equals(getCheck_RHD10())){
						columns.addElement("");
					}
					rowData.add(data);
				}
			}
			model.setDataVector(rowData, columns);
			Common.updateBS_LOG(Datetime.getYYYMMDD(), Datetime.getFHHMMSS(),"已完成查詢", qry_seq);
			this.setState("init");
		} catch (Exception e) {
			Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			e.printStackTrace();
		} finally {
			try { if (rs_txt!=null){ rs_txt.close(); rs_txt=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs_ow!=null){ rs_ow.close(); rs_ow=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
			db.closeAll();
		}
		return model;
	}

	//建物TXT
	public DefaultTableModel getBuildCSVResultModel() throws Exception {
		qry_date_start = Datetime.getYYYMMDD();
		qry_time_start = Datetime.getHHMMSS();
		qry_seq = "K" + qry_date_start + qry_time_start;
		this.setQry_seq(qry_seq);
		con = "勞保局建物歸戶清冊";
		qry = "市縣市=;事務所=;統一編號=;姓名=";
		qry += ";統編批次=" + this.getItemPicture1() + ";姓名批次="
				+ this.getItemPicture2();
		
		Common.insertDL_LOG(qry_seq, "K", this.getUserID(), this.getUnitID(),qry, this.getPrint_type(), qry_date_start, qry_time_start);
		
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		ODatabase db = new ODatabase();
		ResultSet rs_txt = null;
		ResultSet rs_ow= null;
		ResultSet rs = null;
		
		Vector columns = new Vector();
		columns.addElement("lidn");//1
		columns.addElement("lnam");//2
		columns.addElement("dd45");//3
		columns.addElement("chtdd45");//4
		columns.addElement("dd46");//5
		columns.addElement("chtdd46");//6
		columns.addElement("ed48");//7
		columns.addElement("chted48");//8
		columns.addElement("ed49");//9
		columns.addElement("ed49_1");//10
		columns.addElement("ee06");//11
		columns.addElement("chtee06");//11
		columns.addElement("ee07");//12
		columns.addElement("ee05");//13
		columns.addElement("ee15_1");//14
		columns.addElement("chtee15");//15
		columns.addElement("ee15_2");//16
		columns.addElement("ee15_3");//17
		columns.addElement("dd09");//19
		columns.addElement("s_ym");//20
		if("Y".equals(getCheck_RHD10())){
			columns.addElement("HD4849");
			columns.addElement("HD4849C");
		}

		try {
			Vector rowData = new Vector();
			//將原本透過使用者勾選的資料，改成從上傳的文字檔全數直接至rlnid搜尋後全數取出
			for(String s[] : txtData){
				boolean dataExist = false;
				
				String qLidn = "";  //查詢統一編號
				String qLnam = "";  //查詢名稱
				String s_ym = Common.get(s[1]);  //比較基準日期
				String sql_txt = " select lidn, lnam, cty, unit from rlnid "
						+ " where 1=1 ";
				//根據上傳的條件種類決定
				if(queryType.equals("1")){
					qLidn = Common.get(s[0]);
					sql_txt += " and LIDN = " + Common.sqlChar(qLidn);
				}else{
					qLnam = Common.get(s[0]);
					sql_txt += " and LNAM = " + Common.sqlChar(qLnam);
				}
				sql_txt += " order by LIDN, cty, unit ";
			
//System.out.println("sql_txt="+sql_txt);
				rs_txt = db.querySQL(sql_txt);
				while(rs_txt.next()){
Long start = System.currentTimeMillis();
					String lidn = rs_txt.getString("lidn");
					String lnam = rs_txt.getString("lnam");
					String cty = rs_txt.getString("cty");
					String unit = rs_txt.getString("unit");
					
					Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, "", "", "", "", "", "", "", "", lidn, Common.isoToBig5(lnam), "", "", "", "", qry_seq, "2");
					
					String sql_ow = "select "
							+ " re.cty, re.unit, re.ed48, re.ed49, re.ee09, re.ee06, re.ee07, re.ee05, re.ee15_1, re.ee15_2, re.ee15_3, "
							+ " rk06.kcnt as ch_ee06, "
							+ " rk15.kcnt as cht_ee15 "
							+ " from REBOW RE "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='06' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk06 "  /*人檔資料*/
							+ " on rk06.KCDE_2 =  re.ee06 "
							+ " left outer join (select kcde_2, cty, unit, kcnt from rkeyn where kcde_1='15' and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") rk15 "  /*人檔資料*/
							+ " on rk15.KCDE_2 =  re.ee15_1 "
							+ " where 1=1 "
							+ " and re.ee09 = " + Common.sqlChar(lidn)  /*人檔資料*/
							+ " and re.cty = " + Common.sqlChar(cty)  /*人檔資料*/
							+ " and re.unit = " + Common.sqlChar(unit)  /*人檔資料*/
							+ " order by re.cty, re.unit, re.ed48, re.ed49, re.ee01";
//System.out.println("sql_ow="+sql_ow);
					rs_ow = db.querySQL(sql_ow);
					while(rs_ow.next()){
						String sql_id = " select "
								+ " rd.dd45 as aa45, rd.dd46 as aa46, rd.dd48 as aa48, rd.dd49 as aa49, rd.dd09, rd.cty, rd.unit, "
								+ " (select max(kcnt) from rkeyn where kcde_1='45' and kcde_2=rd.dd45 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_cty, "  /*人檔資料*/
								+ " (select max(kcnt) from rkeyn where kcde_1='46' and kcde_2=rd.dd46 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa46, " /*人檔資料*/ 
								+ " (select max(kcnt) from rkeyn where kcde_1='48' and kcde_2=rd.dd48 and cty=" + Common.sqlChar(cty) + " and unit=" + Common.sqlChar(unit) + ") as ch_aa48 "  /*人檔資料*/ 
								+ " FROM RDBID RD "
								+ " where 1=1 "
								+ " and rd.dd48 = " + Common.sqlChar(rs_ow.getString("ed48"))
								+ " and rd.dd49 = " + Common.sqlChar(rs_ow.getString("ed49"))
								+ " and rd.cty = " + Common.sqlChar(cty)
								+ " and rd.unit = " + Common.sqlChar(unit);
//System.out.println("sql_id="+sql_id);
						rs = db.querySQL(sql_id);
						while (rs.next()) {
							dataExist = true;
							Vector data = new Vector();
							data.add(Common.get(lidn));
							data.add(Common.isoToMS950(lnam));
							data.add(Common.get(rs.getString("aa45")));// 3
							data.add(Common.isoToMS950(rs.getString("ch_cty")));
							data.add(Common.get(rs.getString("aa46")));
							data.add(Common.isoToMS950(rs.getString("ch_aa46")));
							data.add(Common.get(rs.getString("aa48")));
							data.add(Common.isoToMS950(rs.getString("ch_aa48")));
							data.add(Common.get(rs_ow.getString("ed49").substring(0, 5)));
							data.add(Common.get(rs_ow.getString("ed49").substring(5)));
							data.add(Common.get(rs_ow.getString("ee06")));
							data.add(Common.isoToMS950(rs_ow.getString("ch_ee06")));
							data.add(Common.get(rs_ow.getString("ee07")));
							data.add(Common.get(rs_ow.getString("ee05")));
							data.add(Common.get(rs_ow.getString("ee15_1")));
							data.add(Common.isoToMS950(rs_ow.getString("cht_ee15")));
							data.add(Common.get(rs_ow.getString("ee15_2")));
							data.add(Common.get(rs_ow.getString("ee15_3")));
							data.add(Common.isoToMS950(rs.getString("dd09")));
							data.add(Common.get(s_ym));//18  比較基準日期
							
							if("Y".equals(getCheck_RHD10())){
								
								String[] arryRHD10=getRHD10List(rs_ow.getString("ed48"),rs_ow.getString("ed49"),cty,unit);
	                            data.add(arryRHD10[0].replaceAll(",", ";"));
	                            data.add(arryRHD10[1].replaceAll(",", ";"));
							}
							
							rowData.add(data);
						}
						rs.getStatement().close();
						rs.close();
					}
					rs_ow.getStatement().close();
					rs_ow.close();
					
System.out.println("LCAAP140F建物-->> query: lidn=" + lidn + ", cty=" + cty + ", unit=" + unit + ", time=" + (System.currentTimeMillis() - start));
				}
				rs_txt.getStatement().close();
				rs_txt.close();
				
				if(!dataExist){
					Vector data = new Vector();
					data=new Vector();
					data.addElement(qLidn);
					data.addElement(qLnam);
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("無符合條件");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement("");
					data.addElement(s_ym);
					if("Y".equals(getCheck_RHD10())){
						columns.addElement("");
						columns.addElement("");
					}
					rowData.add(data);
				}
			}
			model.setDataVector(rowData, columns);
			Common.updateBS_LOG(Datetime.getYYYMMDD(), Datetime.getFHHMMSS(),"已完成查詢", qry_seq);
			this.setState("init");
		} catch (Exception e) {
			Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			e.printStackTrace();
		} finally {
			try { if (rs_txt!=null){ rs_txt.close(); rs_txt=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs_ow!=null){ rs_ow.close(); rs_ow=null;} } catch (Exception e) { e.printStackTrace(); }
			try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
			db.closeAll();
		}
		return model;
	}

	public void genReport() throws Exception {
		//先將文字檔的資料取出
		if(queryType.equals("1")){
			txtData = Common.getFileArray(filestoreLocation, itemPicture1, 2);
		}else{
			txtData = Common.getFileArray(filestoreLocation, itemPicture2, 2);
		}

		if(getPrint_kind().equals("1")){
			genLandReport();  //土地
		}else{
			genBuildReport();  //建物
		}
	}

	//土地
	public void genLandReport(){
		TableModelReportEnvironment env = new TableModelReportEnvironment();
		javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		Vector columns=new Vector();//這裡宣告是為了重新塞table表頭
		Vector rowData=new Vector();//這裡宣告是為了重新塞table內容
		try {
			if (this.getPrint_type().equals("3")) {
				columns.addElement("lidn");
				columns.addElement("lnam");
				columns.addElement("aa45");
				columns.addElement("chtaa45");
				columns.addElement("aa46");
				columns.addElement("chtaa46");
				columns.addElement("ba48");
				columns.addElement("chtba48");
				columns.addElement("ba49");
				columns.addElement("ba49_1");
				columns.addElement("bb06");
				columns.addElement("chtbb06");
				columns.addElement("bb07");
				columns.addElement("bb05");
				columns.addElement("bb15_1");
				columns.addElement("cht_bb15");
				columns.addElement("bb15_2");
				columns.addElement("bb15_3");//目前18
				columns.addElement("s_ym");
				model = this.getLandCSVResultModel();
			} else {
				columns.addElement("lidn");
				columns.addElement("lnam");
				columns.addElement("aa45");
				columns.addElement("aa46");
				columns.addElement("ba48");
				columns.addElement("ba49");
				columns.addElement("ba49_1");
				columns.addElement("bb06");
				columns.addElement("bb07");
				columns.addElement("bb05");
				columns.addElement("bb15_1");//目前11
				columns.addElement("s_ym");
				model = getLandResultModel();
			}
			if ((model == null) || (model.getDataVector().size() == 0)) {
				Common.updateDL_LOG("4", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());

			} else {
				env.setTableModel(model);
				if (this.getPrint_type().equals("2")) {
					env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca102.jasper"));
					env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
				}
				if (this.getPrint_type().equals("3")) {
					env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca101.jasper"));
					env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
				}
				Database sdb = new Database();
				String username = sdb.getLookupField("select user_name from etecuser where user_id=" + Common.sqlChar(this.getUserID()));
				HashMap parms = new HashMap();
				parms.put("print_date", "製表日期："
						+ Datetime.getYYYMMDD().substring(0, 3) + "/"
						+ Datetime.getYYYMMDD().substring(3, 5) + "/"
						+ Datetime.getYYYMMDD().substring(5) + "   "
						+ Datetime.getHHMMSS().substring(0, 2) + ":"
						+ Datetime.getHHMMSS().substring(2, 4) + ":"
						+ Datetime.getHHMMSS().substring(4));

				Vector data = model.getDataVector();
				boolean compare = false;
				if (model.getRowCount() == 1) {
					Vector data01 = (Vector) data.elementAt(0);
					String compareStr = (String) data01.elementAt(0);
					if (compareStr.equals("")) {
						parms.put("print_count", "筆數：0");
						compare = true;
						if (this.getPrint_type().equals("3")) {
							for(int i=0;i<sn_stock.size();i++){								
								Vector contentdata = new Vector();
								contentdata.addElement(sn_stock.elementAt(i));
						    	contentdata.addElement(name_stock.elementAt(i));
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("無符合條件");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement(s_ym.elementAt(i));
								rowData.add(contentdata);
							}
						}else{
						    for(int i=0;i<sn_stock.size();i++){
						    	Vector contentdata = new Vector();
						    	contentdata.addElement(sn_stock.elementAt(i));
						    	contentdata.addElement(name_stock.elementAt(i));
						    	contentdata.addElement("");
						    	contentdata.addElement("");
						    	contentdata.addElement("");
						    	contentdata.addElement("無符合條件");
						    	contentdata.addElement("");
						    	contentdata.addElement("");
						    	contentdata.addElement("");
						    	contentdata.addElement("");
						    	contentdata.addElement("");
						    	contentdata.addElement(s_ym.elementAt(i));
						    	rowData.add(contentdata);
						    }	
						}
						model.setDataVector(rowData, columns);
					} else {
						parms.put("print_count", "筆數："	+ String.valueOf(model.getRowCount()));
					}
				}else{
					parms.put("print_count", "筆數："	+ String.valueOf(model.getRowCount()));
				}
				parms.put("printer_usr", username);
				parms.put("printer_unit", this.getUnit());
				TableModelReportGenerator generator = new TableModelReportGenerator(env);
				
				String vid = Common.getVMID();
				File tempDirectory = new File(reportLocation, vid);
				tempDirectory.mkdirs();
				if (this.getPrint_type().equals("2")) {
					java.io.File outputFile;
					outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq() + ".pdf");
					generator.reportToFile(outputFile, parms);
					if (compare) {
						Common.updateDL_LOG("4", vid + ":;:"+ this.getQry_seq() + ".pdf", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
					} else {
						Common.updateDL_LOG("2", vid + ":;:"+ this.getQry_seq() + ".pdf", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
					}
				} else if (this.getPrint_type().equals("3")) {
					java.io.File outputFile;
					outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq() + ".txt");
					generator.reportToFile(outputFile, parms, "MS950");
					if (compare) {
						Common.updateDL_LOG("4", vid + ":;:" + this.getQry_seq() + ".txt", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
					} else {
						Common.updateDL_LOG("2", vid + ":;:" + this.getQry_seq() + ".txt", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
					}
				}
			}
		} catch (Exception x) {
			Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			x.printStackTrace();
		}
	}
	
	//建物
	public void genBuildReport(){
		TableModelReportEnvironment env = new TableModelReportEnvironment();
		javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		Vector columns=new Vector();//這裡宣告是為了重新塞table表頭
		Vector rowData=new Vector();//這裡宣告是為了重新塞table內容
		try {
			if (this.getPrint_type().equals("3")) {
				columns.addElement("lidn");
				columns.addElement("lnam");
				columns.addElement("dd45");
				columns.addElement("chtdd45");
				columns.addElement("dd46");
				columns.addElement("chtdd46");
				columns.addElement("ed48");
				columns.addElement("chted48");
				columns.addElement("ed49");
				columns.addElement("ed49_1");
				columns.addElement("ee06");
				columns.addElement("chtee06");
				columns.addElement("ee07");
				columns.addElement("ee05");
				columns.addElement("ee15_1");
				columns.addElement("chtee15");
				columns.addElement("ee15_2");
				columns.addElement("ee15_3");
				columns.addElement("dd09");//目前19
				columns.addElement("s_ym");
				if("Y".equals(getCheck_RHD10())){
					columns.addElement("HD4849");//目前19
					columns.addElement("HD4849C");
				}
				model = this.getBuildCSVResultModel();
			} else {
				columns.addElement("lidn");
				columns.addElement("lnam");
				columns.addElement("dd45");
				columns.addElement("dd46");
				columns.addElement("ed48");
				columns.addElement("ed49");
				columns.addElement("ed49_1");
				columns.addElement("ee06");
				columns.addElement("ee07");
				columns.addElement("ee05");
				columns.addElement("ee15_1");
				columns.addElement("dd09");//目前12
				columns.addElement("s_ym");
				if("Y".equals(getCheck_RHD10())){
					columns.addElement("HD4849C");
				}
				model = getBuildResultModel();
			}
			if ((model == null) || (model.getDataVector().size() == 0)) {
				Common.updateDL_LOG("4", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
		
			} else {
				env.setTableModel(model);
				if (this.getPrint_type().equals("2")) {
					
					if("Y".equals(getCheck_RHD10())){
						env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca112_2.jasper"));
						env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
					}else{
						env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca112.jasper"));
						env.setFormat(ReportEnvironment.VAL_FORMAT_PDF);
					}
		
				} else if (this.getPrint_type().equals("3")) {
					if("Y".equals(getCheck_RHD10())){
						env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca111_2.jasper"));
						env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
					}else{
						env.setJasperFile(this.getContext().getRealPath("/report/cus/ca/cusca111.jasper"));
						env.setFormat(ReportEnvironment.VAL_FORMAT_CSV);
					}
				}
				Database sdb = new Database();
				String username = sdb.getLookupField("select user_name from etecuser where user_id=" + Common.sqlChar(this.getUserID()));
		
				HashMap parms = new HashMap();
				parms.put("print_date", "製表日期："
						+ Datetime.getYYYMMDD().substring(0, 3) + "/"
						+ Datetime.getYYYMMDD().substring(3, 5) + "/"
						+ Datetime.getYYYMMDD().substring(5) + "   "
						+ Datetime.getHHMMSS().substring(0, 2) + ":"
						+ Datetime.getHHMMSS().substring(2, 4) + ":"
						+ Datetime.getHHMMSS().substring(4));

				Vector data = model.getDataVector();
				boolean compare = false;
				if (model.getRowCount() == 1) {
					Vector data01 = (Vector) data.elementAt(0);
					String compareStr = (String) data01.elementAt(0);
					if (compareStr.equals("")) {
						parms.put("print_count", "筆數：0");
						compare = true;
						if (this.getPrint_type().equals("3")) {
							for(int i=0;i<sn_stock.size();i++){
								Vector contentdata = new Vector();
								contentdata.addElement(sn_stock.elementAt(i));
						    	contentdata.addElement(name_stock.elementAt(i));
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("無符合條件");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement("");
								contentdata.addElement(s_ym.elementAt(i));
								if("Y".equals(getCheck_RHD10())){
									columns.addElement("");//目前19
									columns.addElement("");
								}
								rowData.add(contentdata);
							}
						}else{
						    for(int i=0;i<sn_stock.size();i++){
						    	Vector contentdata = new Vector();
						    	contentdata.addElement(sn_stock.elementAt(i));
						    	contentdata.addElement(name_stock.elementAt(i));
						    	contentdata.addElement("");
						    	contentdata.addElement("");
						    	contentdata.addElement("");
						    	contentdata.addElement("無符合條件");
						    	contentdata.addElement("");
						    	contentdata.addElement("");
						    	contentdata.addElement("");
						    	contentdata.addElement("");
						    	contentdata.addElement("");
						    	contentdata.addElement("");
						    	contentdata.addElement(s_ym.elementAt(i));
						    	if("Y".equals(getCheck_RHD10())){
									columns.addElement("");
								}
						    	rowData.add(contentdata);
						    }	
						}
						model.setDataVector(rowData, columns);
					} else {
						parms.put("print_count", "筆數："	+ String.valueOf(model.getRowCount()));
					}
				}else{
					parms.put("print_count", "筆數："	+ String.valueOf(model.getRowCount()));
				}
				parms.put("printer_usr", username);
				parms.put("printer_unit", this.getUnit());
				TableModelReportGenerator generator = new TableModelReportGenerator(env);
				
				String vid = Common.getVMID();
				File tempDirectory = new File(reportLocation, vid);
				tempDirectory.mkdirs();
				if (this.getPrint_type().equals("2")) {
					java.io.File outputFile;
					outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq() + ".pdf");
					generator.reportToFile(outputFile, parms);
					if (compare) {
						Common.updateDL_LOG("4", vid + ":;:"+ this.getQry_seq() + ".pdf", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
					} else {
						Common.updateDL_LOG("2", vid + ":;:"+ this.getQry_seq() + ".pdf", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
					}
				} else if (this.getPrint_type().equals("3")) {
					java.io.File outputFile;
					outputFile = new java.io.File(tempDirectory.getPath() + java.io.File.separator + this.getQry_seq() + ".txt");
					generator.reportToFile(outputFile, parms, "MS950");
					if (compare) {
						Common.updateDL_LOG("4", vid + ":;:"+ this.getQry_seq() + ".txt", Datetime.getYYYMMDD(),Datetime.getHHMMSS(), this.getQry_seq());
					} else {
						Common.updateDL_LOG("2", vid + ":;:"+ this.getQry_seq() + ".txt", Datetime.getYYYMMDD(),Datetime.getHHMMSS(), this.getQry_seq());
					}
				}
			}
		} catch (Exception x) {
			Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			x.printStackTrace();
		}
	}

	public String getUnit() throws Exception {
		String printer_unit = "";
		Database odb = new Database();
		try {
			String country = odb
					.getLookupField("select kcnt from rkeyn where kcde_1='45' and kcde_2="
							+ Common.sqlChar(this.getUnitID().substring(0, 1)));
			String cht_unit = odb
					.getLookupField("select kcnt from rkeyn where kcde_1='55' and kcde_2='01' and krmk="
							+ Common.sqlChar(this.getUnitID()));
			printer_unit = country + " " + cht_unit;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			odb.closeAll();
		}
		return printer_unit;
	}
	
	/**
     * 目的:回傳建物所在的地號
     * @return String arry
     * @throws Exception
     */
    public String[] getRHD10List(String HD48,String HD49,String CTY,String UNIT) throws Exception {
        ODatabase db = new ODatabase();
        String ret[]=new String[2];
        ret[0]="";//格式:段代碼+地號
        ret[1]="";//格式:段代碼中文+地號格式化
        int count=0;
        try {
        	String sql = " select a.HA48,a.HA49 ,b.kcnt,substr(a.HA49,1,4)||'-'||substr(a.HA49,5,4)as HA49C "
        		+ " from RHD10 a left join RKEYN b on a.CTY=b.CTY and a.UNIT=b.UNIT and b.KCDE_1='48' and b.Kcde_2=a.ha48 "
        		+ " where 1=1 "
        		+ " and a.HD48 = "+Common.sqlChar(Common.get(HD48))
        		+ " and a.HD49 = "+Common.sqlChar(Common.get(HD49))
        		+ " and a.CTY = "+Common.sqlChar(Common.get(CTY))
        		+ " and a.UNIT = "+Common.sqlChar(Common.get(UNIT))
        		+ " order by a.HA48,a.HA49 "
        		+ "";
        	ResultSet rs=db.querySQL(sql);
        	while(rs.next()){
        		if(count>0){
        			ret[0]+=",";
        			ret[1]+=",";
        		}
        		ret[0]+=rs.getString("HA48")+rs.getString("HA49");
        		ret[1]+=Common.isoToMS950(rs.getString("kcnt"))+rs.getString("HA49C");
        		count++;
        	}
        	rs.getStatement().close();
        	rs.close();
        } catch (Exception x) {
            x.printStackTrace();
        } finally {
            db.closeAll();
        }
        return ret;
    }// method getResultModel end
}
