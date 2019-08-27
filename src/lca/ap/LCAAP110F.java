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

public class LCAAP110F extends QueryBean {

	String txtcity_no;
	String txtunit;
	String txtqry_no;
	String txtqry_name;
	String itemPicture1;
	String itemPicture2;
	String print_type;
	String txtrcv_yr;
	String txtrcv_word;
	String txtrcv_no;
	String txtsno;
	String txtsname;
	String txtsno1;
	String txtsname1;
	String txtqry_purpose01;
	String txtqry_purpose02;
	String txtqry_purpose03;
	String txtqry_purpose03a;
	String txtqry_oper;
	String fds[];
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
	ServletContext context;
	Vector name_stock=new Vector();//存放查詢名字
	Vector sn_stock=new Vector();//存放統編
	
	String fileQuery;//紀錄是否是利用上傳的檔案作查詢
	public String getFileQuery() {return checkGet(fileQuery);}
	public void setFileQuery(String s) {this.fileQuery = checkSet(s);}
	
	static Map<String, String> s_ym_1 = new LinkedHashMap<String, String>();//比較基準日期(統一編號)
	static Map<String, String> s_ym_2 = new LinkedHashMap<String, String>();//比較基準日期(姓名)

	public String[] getFds() {
		return fds;
	}

	public void setFds(String[] fds) {
		this.fds = fds;
	}

	public String getItemPicture1() {return checkGet(itemPicture1);}
	public void setItemPicture1(String itemPicture1) {this.itemPicture1 = checkSet(itemPicture1);}

	public String getItemPicture2() {return checkGet(itemPicture2);}
	public void setItemPicture2(String itemPicture2) {this.itemPicture2 = checkSet(itemPicture2);}

	public String getTxtcity_no() {	return checkGet(txtcity_no);}
	public void setTxtcity_no(String txtcity_no) {this.txtcity_no = checkSet(txtcity_no);}

	public String getTxtqry_name() {return checkGet(txtqry_name);}
	public void setTxtqry_name(String txtqry_name) {this.txtqry_name = checkSet(txtqry_name);}

	public String getTxtqry_no() {return checkGet(txtqry_no);}
	public void setTxtqry_no(String txtqry_no) {this.txtqry_no = checkSet(txtqry_no);}

	public String getTxtqry_oper() {return checkGet(txtqry_oper);}
	public void setTxtqry_oper(String txtqry_oper) {this.txtqry_oper = checkSet(txtqry_oper);}

	public String getTxtqry_purpose01() {return checkGet(txtqry_purpose01);	}
	public void setTxtqry_purpose01(String txtqry_purpose01) {this.txtqry_purpose01 = checkSet(txtqry_purpose01);}

	public String getTxtqry_purpose02() {return checkGet(txtqry_purpose02);}
	public void setTxtqry_purpose02(String txtqry_purpose02) {this.txtqry_purpose02 = checkSet(txtqry_purpose02);}
	
	public String getTxtqry_purpose03() {return checkGet(txtqry_purpose03);}
	public void setTxtqry_purpose03(String txtqry_purpose03) {this.txtqry_purpose03 = checkSet(txtqry_purpose03);}

	public String getTxtqry_purpose03a() {return checkGet(txtqry_purpose03a);}
	public void setTxtqry_purpose03a(String txtqry_purpose03a) {this.txtqry_purpose03a = checkSet(txtqry_purpose03a);}

	public String getTxtrcv_no() {return checkGet(txtrcv_no);}
	public void setTxtrcv_no(String txtrcv_no) {this.txtrcv_no = checkSet(txtrcv_no);}

	public String getTxtrcv_word() {return checkGet(txtrcv_word);}
	public void setTxtrcv_word(String txtrcv_word) {this.txtrcv_word = checkSet(txtrcv_word);}

	public String getTxtrcv_yr() {return checkGet(txtrcv_yr);}
	public void setTxtrcv_yr(String txtrcv_yr) {this.txtrcv_yr = checkSet(txtrcv_yr);}

	public String getTxtsname() {return checkGet(txtsname);}
	public void setTxtsname(String txtsname) {this.txtsname = checkSet(txtsname);}

	public String getTxtsname1() {return checkGet(txtsname1);}
	public void setTxtsname1(String txtsname1) {this.txtsname1 = (txtsname1);}

	public String getTxtsno() {	return checkGet(txtsno);}
	public void setTxtsno(String txtsno) {this.txtsno = checkSet(txtsno);}

	public String getTxtsno1() {return checkGet(txtsno1);}
	public void setTxtsno1(String txtsno1) {this.txtsno1 = checkSet(txtsno1);}

	public String getTxtunit() {return checkGet(txtunit);}
	public void setTxtunit(String txtunit) {this.txtunit = checkSet(txtunit);}

	public String getFilestoreLocation() {return checkGet(filestoreLocation);}
	public void setFilestoreLocation(String filestoreLocation) {this.filestoreLocation = checkSet(filestoreLocation);}

	public String getReportLocation() {return checkGet(reportLocation);}
	public void setReportLocation(String reportLocation) {this.reportLocation = checkSet(reportLocation);}

	public String getPrint_type() {	return checkGet(print_type);}
	public void setPrint_type(String print_type) {this.print_type = checkSet(print_type);}

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

	public ServletContext getContext() {return context;	}
	public void setContext(ServletContext context) {this.context = context;	}

	
	
	public ArrayList queryNameList() throws Exception {
		//紀錄此次查詢是否是透過上傳的檔案
		if (!this.getItemPicture1().equals("") || !this.getItemPicture1().equals("")) {
			setFileQuery("Y");
		}else{
			setFileQuery("N");
		}
		
		ODatabase odb = new ODatabase();
		ArrayList objList = new ArrayList();

		try {
			String sql = "select DISTINCT TRIM(lidn) lidn, TRIM(lnam) lnam, TRIM(ladr) ladr, TRIM(lbir_2) lbir_2 from rlnid where 1=1";
			if (!this.getTxtqry_no().equals("")) {
				sql += " and lidn=" + Common.sqlChar(txtqry_no);
				sn_stock.add(txtqry_no);
			}
			if (!this.getTxtqry_name().equals("")) {
				sql += " and lnam=" + Common.sqlChar(txtqry_name);
				name_stock.add(txtqry_name);
			}
			if (!this.getItemPicture1().equals("")) {				
				sql += " and lidn in (" + Common.getQueryList(filestoreLocation, itemPicture1) + ")";

				String[] tmp=Common.getQueryList(filestoreLocation, itemPicture1).split(",");
				for(int i=0;i<tmp.length;i++){
					sn_stock.add(tmp[i]);
				}
				
				//統一編號、比較基準日期
				for(String list[] : Common.getFileArray(filestoreLocation, itemPicture1, 2)){
					s_ym_1.put(list[0], list[1]);
				}
			}
			if (!this.getItemPicture2().equals("")) {				
				sql += " and lnam in (" + Common.getQueryList(filestoreLocation, itemPicture2) + ")";
				String[] tmp=Common.getQueryList(filestoreLocation, itemPicture2).split(",");
				for(int i=0;i<tmp.length;i++){name_stock.add(tmp[i]);
				}
				
				//姓名、比較基準日期
				for(String list[] : Common.getFileArray(filestoreLocation, itemPicture2, 2)){
					s_ym_2.put(list[0], list[1]);
				}
			}
			if (!this.getTxtcity_no().equals("")) {
				sql += " and cty=" + Common.sqlChar(txtcity_no);
			}
			if (!this.getTxtunit().equals("")) {
				sql += " and unit=" + Common.sqlChar(txtunit);
			}
			sql += " group by lidn,lnam,ladr,lbir_2 order by lnam,lidn";
			if(sn_stock.size()>name_stock.size()){
				for(int i=0;i<sn_stock.size();i++){
					name_stock.add("");
				}
			}
			if(name_stock.size()>sn_stock.size()){
				for(int i=0;i<name_stock.size();i++){
					sn_stock.add("");
				}
			}
//System.out.println("LCAAP110F_SQL="+sql);
			ResultSet rs = odb.querySQL(Common.Ms950ToIso(sql), true);
			if (rs.next()) {
				rs.beforeFirst();
				while (rs.next()) {
					String rowArray[] = new String[4];
					rowArray[0] = Common.get(rs.getString("lidn"));
					rowArray[1] = Common.isoToMS950(Common.get(rs.getString("lnam")));
					rowArray[2] = Common.isoToMS950(Common.get(rs.getString("ladr")));
					rowArray[3] = Common.get(rs.getString("lbir_2"));
					objList.add(rowArray);
					
				}
			} else {
				for(int i=0;i<sn_stock.size();i++){
					String rowArray[] = new String[4];
					rowArray[0] = (String)sn_stock.elementAt(i);
					rowArray[1] = (String)name_stock.elementAt(i);
					rowArray[2] = "無符合條件";
					rowArray[3] = "";
					objList.add(rowArray);
				}	
				
			}

			rs.getStatement().close();
			// rs.close();
			// setStateQueryAllSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			odb.closeAll();
		}
		return objList;
	}

	public DefaultTableModel getResultModel() throws Exception {
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		ODatabase db = new ODatabase();
		Vector columns = new Vector();
		Vector ownerV = queryOwner();
		Common.insertDL_LOG(qry_seq, "J", this.getUserID(), this.getUnitID(),
				qry, this.getPrint_type(), qry_date_start, qry_time_start);
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
			if (ownerV.size() > 0) {
				for (int i = 0; i < ownerV.size(); i++) {
					String[] ownerdata = (String[]) ownerV.elementAt(i);
					String sqlstr = "select cty,unit,ba48,ba49,bb06,bb07,bb05,bb09,bb15_1,bb15_2,bb15_3 ";
					sqlstr += "from rblow where 1=1";
					sqlstr += " and bb09=" + Common.sqlChar(ownerdata[0]);
					sqlstr += " and cty=" + Common.sqlChar(ownerdata[3]);
					sqlstr += " and unit=" + Common.sqlChar(ownerdata[4]);
					sqlstr += " order by cty,unit,ba48,ba49,bb01";
//System.out.println();
//System.out.println("----->產製PDF_SQL="+sqlstr);
					ResultSet rs = null;
					try {
						rs = db.querySQL(sqlstr);
					}catch (Exception x) {
						x.printStackTrace();
					}
					while (rs.next()) {
						Vector data = new Vector();
						String sqlstr01 = "select aa45,aa46,aa48,aa49,aa10,aa11,aa12,aa16 ";
						sqlstr01 += "from ralid where aa48="+ Common.sqlChar(rs.getString("ba48"));
						sqlstr01 += " and aa49="+ Common.sqlChar(rs.getString("ba49"));
						sqlstr01 += " and cty="	+ Common.sqlChar(rs.getString("cty"));
						sqlstr01 += " and unit="+ Common.sqlChar(rs.getString("unit"));
						//sqlstr01 += " and (cty || unit || aa48 || aa49) <> 'FFE122301600028' ";  //資料有問題，先行過濾
						sqlstr01 += " order by aa48,aa49";
//System.out.println("---------->產製PDF_01_SQL"+sqlstr01);

						ResultSet rs01 = null;
						try {
							rs01 = db.querySQL(sqlstr01);
						}catch (Exception x) {
							x.printStackTrace();
						}
						while (rs01.next()) {
							data.add(ownerdata[0]);
							data.add(ownerdata[1]);
							String ch_cty = db.getLookupField("select kcnt from rkeyn where kcde_1='45' and kcde_2="
											+ Common.sqlChar(rs01.getString("aa45"))
											+ " and cty="+ Common.sqlChar(rs.getString("cty"))
											+ " and unit="+ Common.sqlChar(rs.getString("unit")));
							data.add(Common.isoToMS950(ch_cty));
							String ch_aa46 = db.getLookupField("select kcnt from rkeyn where kcde_1='46' and kcde_2="
									+ Common.sqlChar(rs01.getString("aa46"))
									+ " and cty="+ Common.sqlChar(rs.getString("cty"))
									+ " and unit="+ Common.sqlChar(rs.getString("unit")));
					        data.add(Common.isoToMS950(ch_aa46));
							String ch_aa48 = db.getLookupField("select kcnt from rkeyn where kcde_1='48' and kcde_2="
											+ Common.sqlChar(rs01.getString("aa48"))
											+ " and cty="+ Common.sqlChar(rs.getString("cty"))
											+ " and unit="+ Common.sqlChar(rs.getString("unit")));
							data.add(Common.isoToMS950(ch_aa48));
							data.add(Common.get(rs.getString("ba49").substring(0, 4)));
							data.add(Common.get(rs.getString("ba49").substring(4)));
							String ch_bb06 = db.getLookupField("select kcnt from rkeyn where kcde_1='06' and kcde_2="
									+ Common.sqlChar(rs.getString("bb06"))
									+ " and cty="+ Common.sqlChar(rs.getString("cty"))
									+ " and unit="+ Common.sqlChar(rs.getString("unit")));
					        data.add(Common.isoToMS950(ch_bb06));
							data.add(Common.get(rs.getString("bb07")));
							data.add(Common.get(rs.getString("bb05")));
							if (Common.get(rs.getString("bb15_1")).equals("")) {
								data.add(Common.get(rs.getString("bb15_2")) + " 分之 "+ Common.get(rs.getString("bb15_3")));
							} else {
								String cht_bb15 = db.getLookupField("select kcnt from rkeyn where kcde_1='15' and kcde_2="
												+ Common.sqlChar(rs.getString("bb15_1"))
												+ " and cty="+ Common.sqlChar(rs.getString("cty"))
												+ " and unit="+ Common.sqlChar(rs.getString("unit")));
								data.add(Common.isoToMS950(cht_bb15)+ Common.get(rs.getString("bb15_2")) + " 分之 "+ Common.get(rs.getString("bb15_3")));
							}
							
							if(getFileQuery().equals("Y")){
								if(s_ym_1 != null){
									data.add(Common.get(s_ym_1.get(ownerdata[0])));//18  比較基準日期
								}else if(s_ym_2 != null){
									data.add(Common.get(s_ym_2.get(ownerdata[1])));//18  比較基準日期
								}
							}else{
								data.add("");
							}
						}
						rs01.getStatement().close();
						rs01.close();
						rowData.add(data);
					}
					rs.getStatement().close();
					rs.close();
				}
			}
			if (rowData.size() == 0) {
				Vector data = new Vector();
				data.addElement("");
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
				rowData.add(data);
			}
			 //System.out.println("rowData==>"+rowData.size());
			model.setDataVector(rowData, columns);
			Common.updateBS_LOG(Datetime.getYYYMMDD(), Datetime.getFHHMMSS(),"已完成查詢", qry_seq);
			this.setState("init");
		} catch (Exception x) {
			Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			x.printStackTrace();
		} finally {
			db.closeAll();
		}
		return model;
	}// method getResultModel end

	public DefaultTableModel getCSVResultModel() throws Exception {
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		ODatabase db = new ODatabase();
		Vector columns = new Vector();
		Vector ownerV = queryOwner();
		Common.insertDL_LOG(qry_seq, "J", this.getUserID(), this.getUnitID(),qry, this.getPrint_type(), qry_date_start, qry_time_start);
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
			if (ownerV.size() > 0) {
				for (int i = 0; i < ownerV.size(); i++) {
					String[] ownerdata = (String[]) ownerV.elementAt(i);
					String sqlstr = "select cty,unit,ba48,ba49,bb06,bb07,bb05,bb09,bb15_1,bb15_2,bb15_3 ";
					sqlstr += "from rblow where 1=1";
					sqlstr += " and bb09=" + Common.sqlChar(ownerdata[0]);
					sqlstr += " and cty=" + Common.sqlChar(ownerdata[3]);
					sqlstr += " and unit=" + Common.sqlChar(ownerdata[4]);
					sqlstr += " order by cty,unit,ba48,ba49,bb01";
//System.out.println();
//System.out.println("----->產製TXT_SQL="+sqlstr);
					ResultSet rs = null;
					try {
						rs = db.querySQL(sqlstr);
					}catch (Exception x) {
						x.printStackTrace();
					}
					while (rs.next()) {
						Vector data = new Vector();
						String sqlstr01 = "select aa45,aa46,aa48,aa49,aa10,aa11,aa12,aa16 ";
						sqlstr01 += "from ralid where aa48="+ Common.sqlChar(rs.getString("ba48"));
						sqlstr01 += " and aa49="+ Common.sqlChar(rs.getString("ba49"));
						sqlstr01 += " and cty="+ Common.sqlChar(rs.getString("cty"));
						sqlstr01 += " and unit="+ Common.sqlChar(rs.getString("unit"));
						//sqlstr01 += " and (cty || unit || aa48 || aa49) <> 'FFE122301600028' ";  //資料有問題，先行過濾
						sqlstr01 += " order by aa48,aa49";
//System.out.println("---------->產製TXT_01_SQL"+sqlstr01);

						ResultSet rs01 = null;
						try {
							rs01 = db.querySQL(sqlstr01);
						}catch (Exception x) {
							x.printStackTrace();
						}
						while (rs01.next()) {
							data.add(ownerdata[0]);// 1
							data.add(ownerdata[1]);// 2
							data.add(Common.get(rs01.getString("aa45")));// 3
							String ch_cty = db.getLookupField("select kcnt from rkeyn where kcde_1='45' and kcde_2="
											+ Common.sqlChar(rs01.getString("aa45"))
											+ " and cty="+ Common.sqlChar(rs.getString("cty"))
											+ " and unit="+ Common.sqlChar(rs.getString("unit")));
							data.add(Common.isoToMS950(ch_cty));// 4
							data.add(Common.get(rs01.getString("aa46")));// 5
							String cht_aa46 = db.getLookupField("select kcnt from rkeyn where kcde_1='46' and kcde_2="+ Common.sqlChar(rs01.getString("aa46"))
											+ " and cty="+ Common.sqlChar(rs.getString("cty"))
											+ " and unit="+ Common.sqlChar(rs.getString("unit")));
							data.add(Common.isoToMS950(cht_aa46));// 6
							data.add(Common.get(rs01.getString("aa48")));// 7
							String cht_aa48 = db.getLookupField("select kcnt from rkeyn where kcde_1='48' and kcde_2="+ Common.sqlChar(rs01.getString("aa48"))
											+ " and cty="+ Common.sqlChar(rs.getString("cty"))
											+ " and unit="+ Common.sqlChar(rs.getString("unit")));
							data.add(Common.isoToMS950(cht_aa48));// 8
							data.add(Common.get(rs.getString("ba49").substring(0, 4)));// 9
							data.add(Common.get(rs.getString("ba49").substring(4)));// 10
							data.add(Common.get(rs.getString("bb06")));// 11
							String ch_bb06 = db.getLookupField("select kcnt from rkeyn where kcde_1='06' and kcde_2="
									+ Common.sqlChar(rs.getString("bb06"))
									+ " and cty="+ Common.sqlChar(rs.getString("cty"))
									+ " and unit="+ Common.sqlChar(rs.getString("unit")));
					        data.add(Common.isoToMS950(ch_bb06));// 12
							data.add(Common.get(rs.getString("bb07")));// 13
							data.add(Common.get(rs.getString("bb05")));// 14
							data.add(Common.get(rs.getString("bb15_1")));// 15
							String cht_bb15 = db.getLookupField("select kcnt from rkeyn where kcde_1='15' and kcde_2="+ Common.sqlChar(rs.getString("bb15_1"))
											+ " and cty="+ Common.sqlChar(rs.getString("cty"))
											+ " and unit="+ Common.sqlChar(rs.getString("unit")));
							data.add(Common.isoToMS950(cht_bb15));// 16
							data.add(Common.get(rs.getString("bb15_2")));//17
							data.add(Common.get(rs.getString("bb15_3")));//18

							if(getFileQuery().equals("Y")){
								if(s_ym_1 != null){
									data.add(Common.get(s_ym_1.get(ownerdata[0])));//19  比較基準日期
								}else if(s_ym_2 != null){
									data.add(Common.get(s_ym_2.get(ownerdata[1])));//19  比較基準日期
								}
							}else{
								data.add("");
							}
						}
						rs01.getStatement().close();
						rs01.close();
						rowData.add(data);
					}
					rs.getStatement().close();
					rs.close();
				}
			}
			if (rowData.size() == 0) {
				Vector data = new Vector();
				data=new Vector();
				data.addElement("");
				data.addElement("");
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
				rowData.add(data);
			}
			model.setDataVector(rowData, columns);
			Common.updateBS_LOG(Datetime.getYYYMMDD(), Datetime.getFHHMMSS(),"已完成查詢", qry_seq);
			this.setState("init");
		} catch (Exception x) {
			Common.updateDL_LOG("6", Datetime.getYYYMMDD(), Datetime.getHHMMSS(), this.getQry_seq());
			x.printStackTrace();
		} finally {
			db.closeAll();
		}
		return model;

	}// method getResultModel end

	public void genReport() throws Exception {
//System.out.println("--------------------LCAAP110F--------------------");
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
				model = this.getCSVResultModel();
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
				model = getResultModel();
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
				String username = sdb
						.getLookupField("select user_name from etecuser where user_id="
								+ Common.sqlChar(this.getUserID()));
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
								
								String s_ym = "";  //比較基準日期
								if(getFileQuery().equals("Y")){
									if(s_ym_1 != null){
										s_ym = Common.get(s_ym_1.get(sn_stock.elementAt(i)));
									}else if(s_ym_2 != null){
										s_ym = Common.get(s_ym_2.get(name_stock.elementAt(i)));
									}
								}
								
								Vector contentdata = new Vector();
								contentdata.addElement(sn_stock.elementAt(i));
						    	contentdata.addElement(name_stock.elementAt(i));
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
								contentdata.addElement("");
								contentdata.addElement(s_ym);
								rowData.add(contentdata);
							}
						}else{
						    for(int i=0;i<sn_stock.size();i++){
						    	
						    	String s_ym = "";  //比較基準日期
								if(getFileQuery().equals("Y")){
									if(s_ym_1 != null){
										s_ym = Common.get(s_ym_1.get(sn_stock.elementAt(i)));
									}else if(s_ym_2 != null){
										s_ym = Common.get(s_ym_2.get(name_stock.elementAt(i)));
									}
								}
								
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
						    	contentdata.addElement(s_ym);
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
			//	parms.put("print_count", "筆數："
			//			+ String.valueOf(model.getRowCount()));
				parms.put("printer_usr", username);
				parms.put("printer_unit", this.getUnit());
				TableModelReportGenerator generator = new TableModelReportGenerator(env);
				String vid = Common.getVMID();
				File tempDirectory = new File(reportLocation, vid);
				tempDirectory.mkdirs();
				// System.out.println(tempDirectory.getPath());
				if (this.getPrint_type().equals("2")) {
					java.io.File outputFile;
					outputFile = new java.io.File(tempDirectory.getPath()
							+ java.io.File.separator + "cusca102_"+ this.getUserID() + ".pdf");
					generator.reportToFile(outputFile, parms);
					if (compare) {
					Common.updateDL_LOG("4", vid + ":;:cusca102_"+ this.getUserID() + ".pdf", Datetime.getYYYMMDD(),
							Datetime.getHHMMSS(), this.getQry_seq());
					} else {
						Common.updateDL_LOG("2", vid + ":;:cusca102_"+ this.getUserID() + ".pdf", Datetime.getYYYMMDD(),
								Datetime.getHHMMSS(), this.getQry_seq());
					}
				}
				if (this.getPrint_type().equals("3")) {
					java.io.File outputFile;
					outputFile = new java.io.File(tempDirectory.getPath()
							+ java.io.File.separator + "cusca101_" + this.getUserID() + ".txt");
					generator.reportToFile(outputFile, parms, "MS950");
					if (compare) {
					Common.updateDL_LOG("4", vid + ":;:cusca101_" + this.getUserID() + ".txt", Datetime.getYYYMMDD(),
							Datetime.getHHMMSS(), this.getQry_seq());
					} else {
						Common.updateDL_LOG("2", vid + ":;:cusca101_" + this.getUserID() + ".txt", Datetime.getYYYMMDD(),
								Datetime.getHHMMSS(), this.getQry_seq());
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

	public Vector queryOwner() throws Exception {
		qry_date_start = Datetime.getYYYMMDD();
		qry_time_start = Datetime.getHHMMSS();
		qry_seq = "J" + qry_date_start + qry_time_start;
		this.setQry_seq(qry_seq);
		con = "勞保局土地歸戶清冊";
		qry = "市縣市=" + this.getTxtcity_no() + ";事務所=" + this.getTxtunit()
				+ ";統一編號=" + this.getTxtqry_no() + ";姓名="
				+ this.getTxtqry_name();
		qry += ";統編批次=" + this.getItemPicture1() + ";姓名批次="
				+ this.getItemPicture2();
		ODatabase odb = new ODatabase();
		Database sdb = new Database();
		Vector odata = new Vector();
		sn_stock=new Vector();
		name_stock=new Vector();
		try {
			for (int j = 0; j < fds.length; j++) {
				String[] str = fds[j].split(",");
				String sql = "select * from rlnid where 1=1";
				sn_stock.addElement(str[0]);
				sql += " and lidn=" + Common.sqlChar(str[0]);
				if (str.length > 1) {
					sql += " and  NVL(trim(lnam),'X')=NVL(" + Common.sqlChar(str[1])+ ",'X')";
					name_stock.addElement(str[1]);
				} else {
					name_stock.addElement("");
					sql += " and lnam is null";
				}
				if (str.length > 2) {
					sql += " and  NVL(trim(ladr),'X')=NVL(" + Common.sqlChar(str[2])+ ",'X')";
				} else {
					sql += " and ladr is null";
				}
				if (str.length == 4) {
					sql += " and trim(lbir_2)=NVL(" + Common.sqlChar(str[3])+ ",lbir_2)";
				} else {
					sql += " and lbir_2 is null ";
				}
				if (!this.getTxtcity_no().equals("")) {
					sql += " and cty=" + Common.sqlChar(txtcity_no);
				}
				if (!this.getTxtunit().equals("")) {
					sql += " and unit=" + Common.sqlChar(txtunit);
				}
				sql += " order by lidn,cty,unit";
				ResultSet rs = odb.querySQL(Common.Ms950ToIso(sql));
				// System.out.println("sql==>"+sql);
				Common.insertBS_LOG(qry_date_start, qry_time_start, this.getUserID(), this.getUnitID(), uip, con, qry, this	.getTxtrcv_yr(), this.getTxtrcv_word(), 
						this.getTxtrcv_no(), this.getTxtsno(), this.getTxtsname(),this.getTxtsno1(), this.getTxtsname1(), 
						this.getTxtqry_oper(), str[0], str[1], this	.getTxtqry_purpose01(), this.getTxtqry_purpose02(), 
						this.getTxtqry_purpose03(), this.getTxtqry_purpose03a(), qry_seq, "");
				while (rs.next()) {
					String[] tmpdata = new String[5];
					tmpdata[0] = Common.get(rs.getString("lidn"));
					tmpdata[1] = Common.isoToMS950(Common.get(rs.getString("lnam")));
					tmpdata[2] = Common.isoToMS950(Common.get(rs.getString("ladr")));
					tmpdata[3] = Common.get(rs.getString("cty"));
					tmpdata[4] = Common.get(rs.getString("unit"));
					odata.add(tmpdata);
				}
				rs.getStatement().close();
				rs.close();
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			odb.closeAll();
		}
		return odata;
	}

}
