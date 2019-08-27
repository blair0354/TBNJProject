/*
 *<br>程式目的：原住民地區行政區設定作業
 *<br>程式代號：
 *<br>撰寫日期： 
 *<br>程式作者：Sya
 *<br>--------------------------------------------------------
 *<br>修改作者　　修改日期　　　修改目的
 *<br>--------------------------------------------------------
 */

package lca.ap;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.*;

public class EFORM506 extends QueryBean {

	private String cty="";	//下拉選單
	private String[] outputTable;	//畫面勾選回傳值
	
	public String getCty() {return cty;}
	public void setCty(String cty) {this.cty = cty;}
	public String[] getOutputTable() {return outputTable;}
	public void setOutputTable(String[] outputTable) {this.outputTable = outputTable;}
	
	String msg = "";
	public String getMsg() {	return checkGet(msg);}
	public void setMsg(String s) {	this.msg = checkGet(s);}
	
	String A="";
	String B="";
	String C="";
	String D="";
	String E="";
	String F="";
	String G="";
	String H="";
	String I="";
	String J="";
	String K="";
	String M="";
	String N="";
	String O="";
	String P="";
	String Q="";
	String T="";
	String U="";
	String V="";
	String W="";
	String X="";
	String Z="";
	
	public String getA() {return A;}
	public void setA(String a) {A = a;}
	public String getB() {return B;}
	public void setB(String b) {B = b;}
	public String getC() {return C;}
	public void setC(String c) {C = c;}
	public String getD() {return D;}
	public void setD(String d) {D = d;}
	public String getE() {return E;}
	public void setE(String e) {E = e;}
	public String getF() {return F;}
	public void setF(String f) {F = f;}
	public String getG() {return G;}
	public void setG(String g) {G = g;}
	public String getH() {return H;}
	public void setH(String h) {H = h;}
	public String getI() {return I;}
	public void setI(String i) {I = i;}
	public String getJ() {return J;}
	public void setJ(String j) {J = j;}
	public String getK() {return K;}
	public void setK(String k) {K = k;}
	public String getM() {return M;}
	public void setM(String m) {M = m;}
	public String getN() {return N;}
	public void setN(String n) {N = n;}
	public String getO() {return O;}
	public void setO(String o) {O = o;}
	public String getP() {return P;}
	public void setP(String p) {P = p;}
	public String getQ() {return Q;}
	public void setQ(String q) {Q = q;}
	public String getT() {return T;}
	public void setT(String t) {T = t;}
	public String getU() {return U;}
	public void setU(String u) {U = u;}
	public String getV() {return V;}
	public void setV(String v) {V = v;}
	public String getW() {return W;}
	public void setW(String w) {W = w;}
	public String getX() {return X;}
	public void setX(String x) {X = x;}
	public String getZ() {return Z;}
	public void setZ(String z) {Z = z;}
	
	public ArrayList<String[]> getWinModel(){
		ArrayList<String[]> reStr = new ArrayList<String[]>();
		ODatabase db = new ODatabase();
		ResultSet rs = null;
		try {
			System.out.println("SQL : " + doSql_sel());
			rs = db.querySQL(doSql_sel());
			while(rs.next()){
				String[] winTmp = new String[5];
					winTmp[0] = Common.isoToBig5(rs.getString(2));
					winTmp[1] = Common.isoToBig5(rs.getString(4));
					winTmp[2] = rs.getString(6);
					winTmp[3] = rs.getString(7);
					winTmp[4] = rs.getString(8);
				reStr.add(winTmp);
			}
		}catch (Exception e) {
			e.printStackTrace();
			setErrorMsg("Error!:"+ e.toString().replaceAll("\"", "").replaceAll("'", ""));
	  	}finally {
	  		try{
	  			if (rs!=null){
	  				rs.close(); 
	  				rs=null;
	  			}
	  		}catch (Exception e){
	  			e.printStackTrace(); 
	  		}
	  		db.closeAll();
	  	}
		return reStr;
	}
	
	//存檔時先刪除後新增
	public void getLogsSave(){
		ODatabase db = new ODatabase();
		try{
			String[] data = getOutputTable();
			String date = util.Datetime.getYYYMMDD();
			String time = util.Datetime.getHHMMSS();
	        String uId = getUserID();
	        
				db.exeSQL(doSql_del());			
				
				for (int i = 0; i < data.length; i++) {
					System.out.println("check outputTable " + " " + data[i]);
					String[] tmp = data[i].split(",");
					db.querySQL(Common.big5ToIso(doSql_ins(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4],uId,date,time)));
				}
				setMsg("原住民行政區存檔完成!");
		}catch (Exception e) {
			e.printStackTrace();
			setMsg("原住民行政區存檔失敗!");
			setErrorMsg("Error!:"+ e.toString().replaceAll("\"", "").replaceAll("'", ""));
	  	}finally {
	  		db.closeAll();
	  	}	
	}
		
	//畫面載入時撈個縣市的區資料
	public ArrayList<String[]> getResultModel(){
		ArrayList<String[]> reStr = new ArrayList<String[]>();
		ODatabase db = new ODatabase();
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ResultSet rs5 = null;
		try {
			System.out.println("SQL1 : " + doSql_1());
			rs = db.querySQL(doSql_1());
			while(rs.next()){
				String tmp_cty = rs.getString(1);
				String tmp_cty_user = rs.getString(2);
				String tmp_mcty = rs.getString(3);
				String tmp_mcty_user = rs.getString(4);
				String ch = "";
				
				try{
					System.out.println("SQL2 : " + doSql_2(tmp_cty,tmp_cty_user));
					rs2 = db.querySQL(doSql_2(tmp_cty,tmp_cty_user));
					while(rs2.next()){
						System.out.println("SQL4 : " + doSql_sel());
						rs4 = db.querySQL(doSql_sel());
						String[] tmp_sql2 = new String[6];
							tmp_sql2[0] = rs2.getString(1);
							tmp_sql2[1] = rs2.getString(2);
							tmp_sql2[2] = Common.isoToBig5(rs2.getString(3));
							tmp_sql2[3] = Common.isoToBig5(rs2.getString(4));
							tmp_sql2[4] = rs2.getString(5);
							while(rs4.next()){
								String[] tmp_sql_sel = new String[2];
								tmp_sql_sel[0] = rs4.getString(3);
								tmp_sql_sel[1] = rs4.getString(5);
								
								if((tmp_sql2[1].equals(tmp_sql_sel[0]))&&(tmp_sql2[4].equals(tmp_sql_sel[1]))){
									ch = "Y";
									break;
								}else{
									ch = "N";
								}
							}
							tmp_sql2[5] = ch;
						reStr.add(tmp_sql2);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
				if(tmp_mcty != null && tmp_mcty_user != null){
					try{
						System.out.println("SQL3 : " + doSql_3(tmp_cty,tmp_cty_user,tmp_mcty,tmp_mcty_user));
						rs3 = db.querySQL(doSql_3(tmp_cty,tmp_cty_user,tmp_mcty,tmp_mcty_user));
						while(rs3.next()){
							System.out.println("SQL5 : " + doSql_sel());
							rs5 = db.querySQL(doSql_sel());
							String[] tmp_sql3 = new String[6];
								tmp_sql3[0] = rs3.getString(1);
								tmp_sql3[1] = rs3.getString(2);
								tmp_sql3[2] = Common.isoToBig5(rs3.getString(3));
								tmp_sql3[3] = Common.isoToBig5(rs3.getString(4));
								tmp_sql3[4] = rs3.getString(5);
								while(rs5.next()){
									String[] tmp_sql_sel = new String[2];
									tmp_sql_sel[0] = rs5.getString(3);
									tmp_sql_sel[1] = rs5.getString(5);
									
									if((tmp_sql3[1].equals(tmp_sql_sel[0]))&&(tmp_sql3[4].equals(tmp_sql_sel[1]))){
										ch = "Y";
										break;
									}else{
										ch = "N";
									}
								}
								tmp_sql3[5] = ch;
							reStr.add(tmp_sql3);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			setErrorMsg("Error!:"+ e.toString().replaceAll("\"", "").replaceAll("'", ""));
	  	}finally {
	  		try{
	  			if (rs5!=null){
	  				rs5.close(); 
	  				rs5=null;
	  			}
	  		}catch (Exception e){
	  			e.printStackTrace(); 
	  		}
	  		try{
	  			if (rs4!=null){
	  				rs4.close(); 
	  				rs4=null;
	  			}
	  		}catch (Exception e){
	  			e.printStackTrace(); 
	  		}
	  		try{
	  			if (rs3!=null){
	  				rs3.close(); 
	  				rs3=null;
	  			}
	  		}catch (Exception e){
	  			e.printStackTrace(); 
	  		}
	  		try{
	  			if (rs2!=null){
	  				rs2.close(); 
	  				rs2=null;
	  			}
	  		}catch (Exception e){
	  			e.printStackTrace(); 
	  		}
	  		try{
	  			if (rs!=null){
	  				rs.close(); 
	  				rs=null;
	  			}
	  		}catch (Exception e){
	  			e.printStackTrace(); 
	  		}
	  		db.closeAll();
	  	}
		return reStr;
	}
	
	public String doSql_1() {
		String sql = " select a.CTY,a.CTY_USER,a.MERGE_CTY,a.MERGE_CTY_USER,a.CTY||'-'||"
				+ " (select s.KCNT from RKEYN s where rownum=1 and s.kcde_2 is not null and s.kcde_2 not like '%/*%' and  s.KCDE_1='45' and s.KCDE_2=s.CTY and s.KCDE_2=a.CTY)AS CTYC"
				+ " from EFORM5_0 a where a.CTY_USER is not null order by a.CTY";
		return sql;
	}
	
	public String doSql_2(String tmp_cty, String tmp_cty_user){
		String sql = " select ('" + tmp_cty + "')AS AB45,(a.KCDE_2)AS AB46,"
				+ " (select s.KCNT from " + tmp_cty_user + ".RKEYN s"
				+ " where rownum=1  and s.kcde_2 is not null and s.kcde_2 not like '%/*%' and s.KCDE_1='45' and s.KCDE_2=s.CTY and s.KCDE_2='" + tmp_cty + "')as AB45C,"
				+ " (select s.KCNT from " + tmp_cty_user + ".RKEYN s"
				+ " where rownum=1  and s.kcde_2 is not null and s.kcde_2 not like '%/*%' "
				+ " and s.KCDE_1='46' and s.KCDE_2=a.KCDE_2 and s.KRMK=s.CTY and s.KRMK='" + tmp_cty + "')as AB46C,"
				+ " ('" + tmp_cty + "')AS OAB45 from " + tmp_cty_user + ".RKEYN a"
				+ " where a.KCDE_1='46' and a.KRMK=a.CTY and a.CTY='" + tmp_cty + "'" 
				+ "  and a.kcde_2 is not null and a.kcde_2 not like '%/*%' "
				+ " group by a.KCDE_1,a.KCDE_2,a.KRMK,a.CTY order by a.KCDE_2";
		return sql;
	}
	
	public String doSql_3(String tmp_cty, String tmp_cty_user, String tmp_mcty, String tmp_mcty_user){
		String sql = "select ('" + tmp_cty + "')AS AB45,(a.KCDE_2)AS AB46,"
				+ " (select s.KCNT from " + tmp_cty_user + ".RKEYN s"
				+ " where rownum=1 and s.kcde_2 is not null and s.kcde_2 not like '%/*%' and s.KCDE_1='45' and s.KCDE_2=s.CTY and s.KCDE_2='" + tmp_cty + "')as AB45C,"
				+ " (select s.KCNT from " + tmp_mcty_user + ".RKEYN s"
				+ " where rownum=1 and s.kcde_2 is not null and s.kcde_2 not like '%/*%'"
				+ " and s.KCDE_1='46' and s.KCDE_2=a.KCDE_2 and s.KRMK=s.CTY and s.KRMK='" + tmp_mcty + "')as AB46C,"
				+ " ('" + tmp_mcty + "')AS OAB45 from " + tmp_mcty_user + ".RKEYN a"
				+ " where a.KCDE_1='46' and a.KRMK=a.CTY and a.CTY='" + tmp_mcty + "'"
				+ "  and a.kcde_2 is not null and a.kcde_2 not like '%/*%' "
				+ " group by a.KCDE_1,a.KCDE_2,a.KRMK,a.CTY order by a.KCDE_2";
		
		return sql;
	}
	
	public String doSql_del(){
		String sql = "delete from ABTOWN";
		
		return sql;
	}
	
	public String doSql_ins(String Column1, String Column2, String Column3, String Column4, String Column5, String id, String date, String time){
		String sql = "insert into ABTOWN (AB45, AB45C, AB46, AB46C, OAB45, EDITID, EDITDATE, EDITTIME)"
				+ " VALUES ('" + Column1 + "','" + Column3 + "','" + Column2 + "','" + Column4 + "','" + Column5
				+ "','" + id + "','" + date + "','" + time + "')";
		return sql;
	}
	
	public String doSql_sel(){
		String sql = "select * from ABTOWN";
		
		return sql;
	}
	
	public String doCtySql(){
		String sql = "select a.CTY,a.CTY_USER,a.MERGE_CTY,a.MERGE_CTY_USER,a.CTY||'-'||"
				+ "(select s.KCNT from RKEYN s where  rownum=1 and s.kcde_2 is not null and s.kcde_2 not like '%/*%' and s.KCDE_1='45' and s.KCDE_2=s.CTY and s.KCDE_2=a.CTY)AS CTYC"
				+ " from EFORM5_0 a where a.CTY_USER is not null order by a.CTY";
		
		return sql;
	}
	
	//畫面載入時撈個縣市的區資料
			public ArrayList<String[]> getResultModelCTY(){
				ArrayList<String[]> reStr = new ArrayList<String[]>();
				ODatabase db = new ODatabase();
				ResultSet rs = null;
				try {
					System.out.println("SQL99 : " + doCtySql());
					rs = db.querySQL(doCtySql());
					int indexSave = 0;
					while(rs.next()){
						indexSave++;
						String[] tmp = new String[5];
							tmp[0] = rs.getString(1);
							tmp[1] = rs.getString(2);
							tmp[2] = rs.getString(3);
							tmp[3] = rs.getString(4);
							tmp[4] = Common.isoToBig5(rs.getString(5));
							//System.out.println("5 ==> " + Common.isoToBig5(rs.getString(5)));
							if(indexSave == 1){
								A = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 2){
								B = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 3){
								C = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 4){
								D = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 5){
								E = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 6){
								F = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 7){
								G = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 8){
								H = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 9){
								I = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 10){
								J = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 11){
								K = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 12){
								M = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 13){
								N = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 14){
								O = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 15){
								P = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 16){
								Q = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 17){
								T = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 18){
								U = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 19){
								V = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 20){
								W = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 21){
								X = Common.isoToBig5(rs.getString(5));
							}
							if(indexSave == 22){
								Z = Common.isoToBig5(rs.getString(5));
							}
							
						reStr.add(tmp);
					}
				}catch (Exception e) {
					e.printStackTrace();
					setErrorMsg("Error!:"+ e.toString().replaceAll("\"", "").replaceAll("'", ""));
			  	}finally {
			  		try{
			  			if (rs!=null){
			  				rs.close(); 
			  				rs=null;
			  			}
			  		}catch (Exception e){
			  			e.printStackTrace(); 
			  		}
			  		db.closeAll();
			  	}
				return reStr;
			}
			
	/**
     * <br>
     * <br>目的：組合html option語法函數 
     * <br>參數：(1)sql字串 (2)被選的value 
     * <br>傳回：加上html option element 
     */
    static public String getOptionToOracle(String sql, String selectedOne) {
        String rtnStr = "<option value=''>全部</option>";
        ODatabase db = new ODatabase();
        try {
        	ResultSet rs = db.querySQL(sql);
            while (rs.next()) {
                String id = rs.getString(1);
                String name = Common.isoToBig5(rs.getString(2));
                
                rtnStr = rtnStr + "<option value='" + id + "' ";
                if (selectedOne!= null && selectedOne.equals(id)) {
                    rtnStr = rtnStr + " selected ";
                }
                rtnStr = rtnStr + ">" + name + "</option>\n";
            }
        } catch (Exception ex) {
            rtnStr = "<option value=''>查詢錯誤</option>";
            ex.printStackTrace();
        } finally {
 			db.closeAll();
        }        
        
        return rtnStr;
    }
    
}