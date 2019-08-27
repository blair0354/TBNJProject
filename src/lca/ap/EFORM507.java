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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.Vector;

import javax.servlet.ServletContext;

import util.*;

public class EFORM507 extends QueryBean {

	String imp_kind;
	String itemPicture1;
	ServletContext context;
	String filestoreLocation;
	String reportLocation;
	String msg = "";
	
	String ADCNT01;
	String ADCNT02;
	String ADCNT03;
	String ADCNT04;
	String ADCNT11;
	String ADCNT12;
	String ADCNT13;
	String ADCNT14;
	String ADCNT15;
	String ADCNT16;
	String ADCNT21;
	String ADCNT22;
	String ADCNT23;
	String ADCNT24;
	String ADCNT25;
	String ADCNT26;
	
	public String getADCNT01(){ return checkGet(ADCNT01); }	
	public void setADCNT01(String s){ ADCNT01=checkSet(s); }
	public String getADCNT02(){ return checkGet(ADCNT02); }	
	public void setADCNT02(String s){ ADCNT02=checkSet(s); }
	public String getADCNT03(){ return checkGet(ADCNT03); }	
	public void setADCNT03(String s){ ADCNT03=checkSet(s); }
	public String getADCNT04(){ return checkGet(ADCNT04); }	
	public void setADCNT04(String s){ ADCNT04=checkSet(s); }
	public String getADCNT11(){ return checkGet(ADCNT11); }	
	public void setADCNT11(String s){ ADCNT11=checkSet(s); }
	public String getADCNT12(){ return checkGet(ADCNT12); }	
	public void setADCNT12(String s){ ADCNT12=checkSet(s); }
	public String getADCNT13(){ return checkGet(ADCNT13); }	
	public void setADCNT13(String s){ ADCNT13=checkSet(s); }
	public String getADCNT14(){ return checkGet(ADCNT14); }	
	public void setADCNT14(String s){ ADCNT14=checkSet(s); }
	public String getADCNT15(){ return checkGet(ADCNT15); }	
	public void setADCNT15(String s){ ADCNT15=checkSet(s); }
	public String getADCNT16(){ return checkGet(ADCNT16); }	
	public void setADCNT16(String s){ ADCNT16=checkSet(s); }
	public String getADCNT21(){ return checkGet(ADCNT21); }	
	public void setADCNT21(String s){ ADCNT21=checkSet(s); }
	public String getADCNT22(){ return checkGet(ADCNT22); }	
	public void setADCNT22(String s){ ADCNT22=checkSet(s); }
	public String getADCNT23(){ return checkGet(ADCNT23); }	
	public void setADCNT23(String s){ ADCNT23=checkSet(s); }
	public String getADCNT24(){ return checkGet(ADCNT24); }	
	public void setADCNT24(String s){ ADCNT24=checkSet(s); }
	public String getADCNT25(){ return checkGet(ADCNT25); }	
	public void setADCNT25(String s){ ADCNT25=checkSet(s); }
	public String getADCNT26(){ return checkGet(ADCNT26); }	
	public void setADCNT26(String s){ ADCNT26=checkSet(s); }
	
	public String getImp_kind() {	return checkGet(imp_kind);}
	public void setImp_kind(String s) {this.imp_kind = checkSet(s);}
	
	public String getMsg() {	return checkGet(msg);}
	public void setMsg(String s) {	this.msg = checkGet(s);}
	
	public String getItemPicture1() {return checkGet(itemPicture1);}
	public void setItemPicture1(String s) {this.itemPicture1 = checkSet(s);}
	
	public ServletContext getContext() {return context;	}
	public void setContext(ServletContext context) {this.context = context;	}
	
	public String getFilestoreLocation() {return checkGet(filestoreLocation);}
	public void setFilestoreLocation(String s) {this.filestoreLocation = checkSet(s);}
	
	public String getReportLocation() {return checkGet(reportLocation);}
	public void setReportLocation(String s) {this.reportLocation = checkSet(s);}
	
	
	/**
	 * 目地:將文字檔寫入EFORM7
	 * 處理:
	 *    getImp_kind()=1 >> 重新匯入:先將EFORM7刪除,再將文字檔內窩全部寫入EFORM7
	 *    getImp_kind()=2 >> 更新匯入:該統編(姓名)資EFORM7中無資料才寫入
	 * @return
	 */
	public String ModifyEFORM7(){
		//先將文字檔的資料取出
		
		ODatabase db = new ODatabase();
		Connection connection = db.getConnection();
		String IMP_YMD = util.Datetime.getYYYMMDD();
		String IMP_TIME = util.Datetime.getHHMMSS();
		String tmpId = getUserID();

		try {
			
			ArrayList<String> txtDataImp = new ArrayList<String>();
			System.out.println("LOOK = > " + getItemPicture1());
			String tmp[] = getItemPicture1().split(":;:");
			String tmpp = filestoreLocation +"/"+ tmp[0]+"/"+tmp[1];
			
			txtDataImp = FileR(tmpp);
			
			
			if(txtDataImp.size()>0){
				System.out.println("開始匯入時間:"+util.Datetime.getHHMMSS());
				if("1".equals(getImp_kind())){//重新匯入
					String sqlDel = "TRUNCATE TABLE ABLNID";
					db.exeSQL(sqlDel);
				}
				
				int i=0;
				
				String tABID="";
				if("2".equals(getImp_kind())){
					
					String delSql="Delete from ABLNID where ABID=?";
					
					PreparedStatement psDel = connection.prepareStatement(delSql);
					
					final int batchSizeDel = 1000;
					
					int countDel = 0;
					for(String s : txtDataImp){
						tABID=s.replaceAll(",", "");
						
						//System.out.println(++i+":"+s);
						psDel.setString(1, tABID);
						psDel.addBatch();

						if(++countDel % batchSizeDel == 0) {
							psDel.executeBatch();
						}
						
					}
					psDel.executeBatch(); // insert remaining records
					psDel.close();
				}
				
				
				String insSql="insert into ABLNID(ABID,EDITID,EDITDATE,EDITTIME)values(?,?,?,?)";				
				PreparedStatement psIns = connection.prepareStatement(insSql);
				final int batchSizeIns = 1000;
				int countIns = 0;
				i=0;
				for(String s : txtDataImp){
					tABID=s.replaceAll(",", "");
					//System.out.println(++i+":"+s);
					
					psIns.setString(1, tABID);
					psIns.setString(2, tmpId);
					psIns.setString(3, IMP_YMD);
					psIns.setString(4, IMP_TIME);
					psIns.addBatch();
					
					
					if(++countIns % batchSizeIns == 0) {
						psIns.executeBatch();
					}
					
				}
				psIns.executeBatch(); // insert remaining records
				psIns.close();
				connection.close();
				System.out.println("完成匯入時間:"+util.Datetime.getHHMMSS());
			}
			
			if("1".equals(getImp_kind())){//重新匯入
				msg = "匯入完成!";
			}else if("2".equals(getImp_kind())){//更新匯入
				msg = "更新完成!";
			}
			
		} catch (Exception e) {
			msg="匯入失敗";
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return msg;
	}

		  public ArrayList<String> FileR(String url) {   
		        ArrayList<String> txtDataImp = new ArrayList<String>();
		        try{
		        	FileReader fr = new FileReader(url);
		        	BufferedReader br = new BufferedReader(fr);
		        	while (br.ready()) {
		        		//System.out.println(br.readLine());	readLine()sysout會另程式跳行
				        String tmp = br.readLine();
		                txtDataImp.add(tmp);
				    }
				
				    fr.close();
				}catch(IOException e){
					e.printStackTrace(); 
				}
			return txtDataImp;
		  }
		    
		  public ArrayList<String[]> getQryModel(){
				ArrayList<String[]> reStr = new ArrayList<String[]>();
				ODatabase db = new ODatabase();
				ResultSet rs = null;
				try {
					String sql = "select count(*)as CNT,EDITDATE,EDITTIME"
								+ " from ABLNID group by EDITDATE,EDITTIME"
								+ " order by EDITDATE,EDITTIME";
					rs = db.querySQL(sql);
					while(rs.next()){
						String[] Tmp = new String[3];
							Tmp[0] = Common.formatYYYMMDD(rs.getString(2), 4);
							Tmp[1] = Common.formatHHMMSS(rs.getString(3));
							Tmp[2] = rs.getString(1);
						reStr.add(Tmp);
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
			
		  public String doIns(){
			ODatabase db = new ODatabase();
			ResultSet rs = null;
			String str="";
			String tmp ="";
			
			String IMP_YMD = util.Datetime.getYYYMMDD();
			String IMP_TIME = util.Datetime.getHHMMSS();
			String tmpId = getUserID();
			
			try{
				
				String sql = "select (to_number(substr(max(AMKID),8,10))+1)as tAMKID"
							+ " from ABCNT01M where substr(AMKID,1,7) =" + Common.sqlChar(IMP_YMD)
							+ " group by AMKID";
				rs = db.querySQL(sql);
				
				while(rs.next()){
					tmp = rs.getString(1);
				}
				
				if(tmp.length()==1){
					str = IMP_YMD + "00" + tmp;
				}else if(tmp.length()==2){
					str = IMP_YMD + "0" + tmp;
				}else if(tmp.length()==3){
					str = IMP_YMD + tmp;
				}else if("".equals(tmp)){
					str = IMP_YMD + "001";
				}
				
				String sqlIns = "insert into ABCNT01M(AMKID,useID,useDate,useTime,AMFlag)"
							+ "values("
							+ Common.sqlChar(str) + ","
							+ Common.sqlChar(tmpId) + ","
							+ Common.sqlChar(IMP_YMD) + ","
							+ Common.sqlChar(IMP_TIME) + ","
							+ "'01')";
				db.querySQL(sqlIns);
				boolean bolABCNT=doProcessABCNT(str);
				if(bolABCNT){
					msg="統計完成!";
				}else{
					msg="統計失敗!";
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.closeAll();
			}
			return str;
			  
		  }
		  
		  
		//取得上次產製紀錄
			public boolean doProcessABCNT(String sAMKID){
				boolean ret=true;
				
				if(!"".equals(sAMKID)){
					
					ResultSet rs = null;
					ODatabase odb = new ODatabase();
					Vector SQLContainer=new Vector();
					
					String[] rowArray;
					String insSql="";
					String AMFlag="03";
					boolean[] cntBol=new boolean[5];
					cntBol[0]=false;
					cntBol[1]=false;
					cntBol[2]=false;
					cntBol[3]=false;
					cntBol[4]=false;
					
					try {
						ArrayList<Object> townList=getTownAL();
						java.util.Iterator it=townList.iterator();
						while(it.hasNext()){
							rowArray= (String[])it.next();//[0]=AB45,[1]=AB45C,[2]=AB46,[3]=AB46C,[4]=OAB45,[5]=dbUser
							//System.out.println("");
							System.out.print(rowArray[0]+";"+rowArray[1]+";"+rowArray[2]+";"+rowArray[3]+";"+rowArray[4]+";"+rowArray[5]);
							cntBol[0]=ADCNT_A(rowArray[5],rowArray[2]);
							
							if(cntBol[0]){
								cntBol[1]=ADCNT_B(rowArray[5],rowArray[2]);
							}else{
								System.out.println("產製異常0===>終止統計!");
								ret=false;
								break;
							}
							
							if(cntBol[1]){
								cntBol[2]=ADCNT_C(rowArray[5],rowArray[2]);
							}else{
								System.out.println("產製異常1===>終止統計!");
								ret=false;
								break;
							}
							
							if(cntBol[2]){
								cntBol[3]=ADCNT_D(rowArray[5],rowArray[2]);
							}else{
								System.out.println("產製異常2===>終止統計!");
								ret=false;
								break;
							}
							
							if(cntBol[3]){
								cntBol[4]=ADCNT_E(rowArray[5],rowArray[2]);
							}else{
								System.out.println("產製異常3===>終止統計!");
								ret=false;
								break;
							}
							
							
							if(cntBol[0] && cntBol[1] && cntBol[2] && cntBol[3] && cntBol[4]){
								
								setADCNT21(Double.toString(Double.parseDouble(getADCNT01())-Double.parseDouble(getADCNT11())));				
								setADCNT22(Integer.toString(Integer.parseInt(getADCNT02())-Integer.parseInt(getADCNT12())));
								setADCNT26(Double.toString(Double.parseDouble(getADCNT21())-Double.parseDouble(getADCNT25())));
								
								insSql= " Insert into ABCNT01D(AMKID,AD45,AD45C,AD46,AD46C,OAD45,ADCNT01,"+
										"   ADCNT02,ADCNT03,ADCNT04,ADCNT11,ADCNT12,ADCNT13,ADCNT14,"+
										"   ADCNT15,ADCNT16,ADCNT21,ADCNT22,ADCNT23,ADCNT24,ADCNT25,"+
										"   ADCNT26" +
										")Values("+
										Common.sqlChar(sAMKID)+","+
										Common.sqlChar(rowArray[0])+","+
										Common.sqlChar(rowArray[1])+","+
										Common.sqlChar(rowArray[2])+","+
										Common.sqlChar(rowArray[3])+","+
										Common.sqlChar(rowArray[4])+","+
										Common.sqlChar(getADCNT01())+","+
										Common.sqlChar(getADCNT02())+","+
										Common.sqlChar(getADCNT03())+","+
										Common.sqlChar(getADCNT04())+","+
										Common.sqlChar(getADCNT11())+","+
										Common.sqlChar(getADCNT12())+","+
										Common.sqlChar(getADCNT13())+","+
										Common.sqlChar(getADCNT14())+","+
										Common.sqlChar(getADCNT15())+","+
										Common.sqlChar(getADCNT16())+","+
										Common.sqlChar(getADCNT21())+","+
										Common.sqlChar(getADCNT22())+","+
										Common.sqlChar(getADCNT23())+","+
										Common.sqlChar(getADCNT24())+","+
										Common.sqlChar(getADCNT25())+","+
										Common.sqlChar(getADCNT26())+
										")";
								SQLContainer.add(Common.big5ToIso(insSql));
								System.out.println(insSql);
							}else{
								System.out.println("產製異常4===>終止統計!");
								ret=false;
								break;
							}
						}
						
						if(ret){
							if(SQLContainer.size()>0){
								AMFlag="02";
								odb.excuteSQL(SQLContainer);//將比對結果寫入資料庫
							}else{
								AMFlag="05";
							}	
						}
						
						updateFlag(sAMKID,AMFlag);//更新主檔的flag
						
					} catch (Exception e) {
						System.out.println("產製異常5===>終止統計!");
						e.printStackTrace();
						updateFlag(sAMKID,"03");//更新主檔的flag
					} finally {
						try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
						odb.closeAll();
					}
				}else{
					ret=false;
				}
			  	return ret;
			}
			
			
			/**
			 * 更新主檔ABCNT01M的flag
			 * @param sAMKID
			 * @param sAMFlag
			 * @return
			 */
			public boolean updateFlag(String sAMKID,String sAMFlag){
				
				boolean ret=true;

				ODatabase odb = new ODatabase();
				
				try {
					String uSql="update ABCNT01M set "
						+"susDate="+Common.sqlChar(util.Datetime.getYYYMMDD())
						+",susTime="+Common.sqlChar(util.Datetime.getHHMMSS())
						+",AMFlag="+Common.sqlChar(sAMFlag)
						+"where AMKID="+Common.sqlChar(sAMKID)
						+"";
					odb.exeSQL(uSql);//將比對結果寫入資料庫
					System.out.println(uSql);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					odb.closeAll();
				}
			  	return ret;
			}

			/**
			 * 目的:取得要進行統計的縣市、行政區及資料庫的使用者
			 * @return ArrayList
			 */
			public ArrayList getTownAL(){
				ODatabase odb = new ODatabase();
				ResultSet rs = null;
				ArrayList retList = new ArrayList();
				try {
					String sql = " Select a.AB45,a.AB45C,a.AB46,a.AB46C,a.OAB45 "+"\n"
						+ " ,(select s.CTY_USER from EFORM5_0 s where s.CTY=a.OAB45)AS CTY_USER1 "+"\n"
						+ " ,(select s.MERGE_CTY_USER from EFORM5_0 s where s.MERGE_CTY=a.OAB45)AS CTY_USER2 "+"\n"
						+ " FROM ABTOWN a "+"\n"
						+ " order by a.AB45,a.OAB45,a.AB46 "+"\n";
					rs=odb.querySQL(sql);
					while(rs.next()){
						String rowArray[] = new String[6];
						rowArray[0] = Common.get(rs.getString("AB45"));
						rowArray[1] = Common.isoToBig5(rs.getString("AB45C"));
						rowArray[2] = Common.get(rs.getString("AB46"));
						rowArray[3] =Common.isoToBig5(rs.getString("AB46C"));
						rowArray[4] = Common.get(rs.getString("OAB45"));
						if(!"".equals( Common.get(rs.getString("CTY_USER1")))){
							rowArray[5] = Common.get(rs.getString("CTY_USER1"));
						}else if(!"".equals( Common.get(rs.getString("CTY_USER2")))){
							rowArray[5] = Common.get(rs.getString("CTY_USER2"));
						}
						retList.add(rowArray);
					}
					rs.getStatement().close();
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
					odb.closeAll();
				}
				return retList;
			}
			
			/**
			 * 取得ADCNT01、ADCNT02
			 * @return
			 */
			public boolean ADCNT_A(String dbCtyUser,String sAB46){
				boolean ret=true;
				ODatabase odb = new ODatabase();
				ResultSet rs = null;
				try {
					String sql = " SELECT COUNT(*)as ADCNT02,NVL(SUM(NVL(AA10,0)),0)as ADCNT01 "
						+ " FROM "+dbCtyUser+".RALID"
						+ " WHERE AA46="+Common.sqlChar(sAB46)
						+ "";
					System.out.println("ADCNT_A:"+sql);
					rs=odb.querySQL(sql);
					if(rs.next()){
						setADCNT01(rs.getString("ADCNT01"));
						setADCNT02(rs.getString("ADCNT02"));
					}
					rs.getStatement().close();
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
					ret=false;
				} finally {
					try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
					odb.closeAll();
				}
				return ret;
			}
			
			
			/**
			 * 取得ADCNT03、ADCNT04
			 * @return
			 */
			public boolean ADCNT_B(String dbCtyUser,String sAB46){
				boolean ret=true;
				ODatabase odb = new ODatabase();
				ResultSet rs = null;
				try {
					int BCNT=ADCNT_B_TMP(dbCtyUser,sAB46);//取得所有權總人數
					
					String sql = " SELECT COUNT(*)as ADCNT03 "+"\n"
						+ " FROM ABLNID,"+dbCtyUser+".RBLOW B,"+dbCtyUser+".RALID A "+"\n"
						+ " WHERE A.AA46="+Common.sqlChar(sAB46)+"\n"
						+ " AND B.CTY=A.CTY "+"\n"
						+ " AND B.UNIT=A.UNIT "+"\n"
						+ " AND B.BA48=A.AA48 "+"\n"
						+ " AND B.BA49=A.AA49 "+"\n"
						+ " AND ABID=B.BB09 "+"\n"
						+ "";
					System.out.println("ADCNT_B:"+sql);
					rs=odb.querySQL(sql);
					while(rs.next()){
						setADCNT03(rs.getString("ADCNT03"));
						setADCNT04(Integer.toString(BCNT-rs.getInt("ADCNT03")));
					}
					rs.getStatement().close();
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
					ret=false;
				} finally {
					try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
					odb.closeAll();
				}
				return ret;
			}
			
			/**
			 * 取得土地所有權總人數
			 * @return
			 */
			public int ADCNT_B_TMP(String dbCtyUser,String sAB46){
				int ret=0;
				ODatabase odb = new ODatabase();
				ResultSet rs = null;
				try {
					String sql = " SELECT COUNT(*)as BCNT "+"\n"
						+ " FROM "+dbCtyUser+".RBLOW B,"+dbCtyUser+".RKEYN K "+"\n"
						+ " WHERE K.KCDE_1='48' "+"\n"
						+ " AND K.KRMK="+Common.sqlChar(sAB46)+"\n"
						+ " AND B.CTY=K.CTY "+"\n"
						+ " AND B.UNIT=K.UNIT "+"\n"
						+ " AND B.BA48=K.KCDE_2 "+"\n"
						+ "";
					System.out.println("ADCNT_B_TMP:"+sql);
					rs=odb.querySQL(sql);
					if(rs.next()){
						ret=rs.getInt("BCNT");
					}
					rs.getStatement().close();
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
					odb.closeAll();
				}
				return ret;
			}
			
			/**
			 * 取得ADCNT11、ADCNT12
			 * @return
			 */
			public boolean ADCNT_C(String dbCtyUser,String sAB46){
				boolean ret=true;
				ODatabase odb = new ODatabase();
				ResultSet rs = null;
				try {
					String sql = " SELECT COUNT(*)as ADCNT12,NVL(SUM(NVL(AA10,0)),0)as ADCNT11 "+"\n"
						+ "	FROM "+dbCtyUser+".RALID A "+"\n"
						+ "	WHERE AA46="+Common.sqlChar(sAB46)+"\n"
						+ "	AND EXISTS( "+"\n"
						+ "		SELECT 1 FROM "+dbCtyUser+".RGALL G"+"\n"
						+ "		WHERE G.CTY=A.CTY"+"\n"
						+ "		AND G.UNIT=A.UNIT"+"\n"
						+ "		AND G.GG00='A'"+"\n"
						+ "		AND G.GG48=A.AA48"+"\n"
						+ "		AND G.GG49=A.AA49"+"\n"
						+ "		AND G.GG30_1='A7'" +"\n"
						+ " )"+"\n"
						+ "";
						
					System.out.println("ADCNT_C:"+sql);
					rs=odb.querySQL(sql);
					while(rs.next()){
						setADCNT11(rs.getString("ADCNT11"));
						setADCNT12(rs.getString("ADCNT12"));
					}
					rs.getStatement().close();
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
					ret=false;
				} finally {
					try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
					odb.closeAll();
				}
				return ret;
			}
			
			
			/**
			 * 取得ADCNT13、ADCNT14、ADCNT15、ADCNT16
			 * @return
			 */
			public boolean ADCNT_D(String dbCtyUser,String sAB46){
				boolean ret=true;
				ODatabase odb = new ODatabase();
				ResultSet rs = null;
				try {
					int BCNT1=ADCNT_D_TMP(dbCtyUser,sAB46);//原民地總所有權人數
					
					String sql = " SELECT COUNT(*)as ADCNT13, "+"\n"
						+ "	to_char(NVL(SUM(NVL(AA10,0)*NVL(BB15_3,0)/NVL(BB15_2,1)),0),'99999999999990.99')as ADCNT15 "+"\n"
						+ "	FROM ABLNID,"+dbCtyUser+".RBLOW B,"+dbCtyUser+".RALID A "+"\n"
						+ "	WHERE AA46="+Common.sqlChar(sAB46)+"\n"
						+ "	AND EXISTS( "+"\n"
						+ "		SELECT 1 FROM "+dbCtyUser+".RGALL G "+"\n"
						+ "		WHERE G.CTY=A.CTY "+"\n"
						+ "		AND G.UNIT=A.UNIT "+"\n"
						+ "		AND G.GG00='A' "+"\n"
						+ "		AND G.GG48=A.AA48 "+"\n"
						+ "		AND G.GG49=A.AA49 "+"\n"
						+ "		AND GG30_1='A7'" +"\n"
						+ " ) "+"\n"
						+ "	AND B.CTY=A.CTY "+"\n"
						+ "	AND B.UNIT=A.UNIT "+"\n"
						+ "	AND B.BA48=A.AA48 "+"\n"
						+ "	AND B.BA49=A.AA49 "+"\n"
						+ "	AND ABID=B.BB09 "+"\n"
						+ "";
					System.out.println("ADCNT_D:"+sql);
					rs=odb.querySQL(sql);
					while(rs.next()){
						setADCNT13(rs.getString("ADCNT13"));
						setADCNT14(Integer.toString(BCNT1-rs.getInt("ADCNT13")));
						setADCNT15(rs.getString("ADCNT15"));
						setADCNT16(Double.toString(Double.parseDouble(getADCNT11())-rs.getDouble("ADCNT15")));
					}
					rs.getStatement().close();
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
					ret=false;
				} finally {
					try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
					odb.closeAll();
				}
				return ret;
			}
			
			/**
			 * 取得原民地總所有權人數
			 * @return
			 */
			public int ADCNT_D_TMP(String dbCtyUser,String sAB46){
				int ret=0;
				ODatabase odb = new ODatabase();
				ResultSet rs = null;
				try {
					String sql = " SELECT COUNT(*)as BCNT1 "+"\n"
						+ " FROM "+dbCtyUser+".RBLOW B,"+dbCtyUser+".RALID A "+"\n"
						+ " WHERE A.AA46="+Common.sqlChar(sAB46)+"\n"
						+ " AND B.CTY=A.CTY "+"\n"
						+ " AND B.UNIT=A.UNIT "+"\n"
						+ " AND B.BA48=A.AA48 "+"\n"
						+ " AND B.BA49=A.AA49 "+"\n"
						+ " AND EXISTS( "+"\n"
						+ " 	SELECT 1 FROM "+dbCtyUser+".RGALL G "+"\n"
						+ " 	WHERE G.CTY=A.CTY "+"\n"
						+ " 	AND G.UNIT=A.UNIT "+"\n"
						+ " 	AND G.GG00='A' "+"\n"
						+ " 	AND G.GG48=A.AA48 "+"\n"
						+ " 	AND G.GG49=A.AA49 "+"\n"
						+ " 	AND G.GG30_1='A7' "+"\n"
						+ " )"+"\n"
						+ "";
					System.out.println("ADCNT_D_TMP:"+sql);
					rs=odb.querySQL(sql);
					if(rs.next()){
						ret=rs.getInt("BCNT1");
					}
					rs.getStatement().close();
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
					odb.closeAll();
				}
				return ret;
			}
			
			
			/**
			 * 取得ADCNT23、ADCNT24、ADCNT25
			 * @return
			 */
			public boolean ADCNT_E(String dbCtyUser,String sAB46){
				boolean ret=true;
				ODatabase odb = new ODatabase();
				ResultSet rs = null;
				try {
					int BCNT2=ADCNT_E_TMP(dbCtyUser,sAB46);//非原民地總所有權人數
					
					String sql = " SELECT COUNT(*)as ADCNT23 "
						+ " ,to_char(NVL(SUM(NVL(AA10,0)*NVL(BB15_3,0)/NVL(BB15_2,1)),0),'99999999999990.99')as ADCNT25 "+"\n"
						+ " FROM ABLNID, "+dbCtyUser+".RBLOW B,"+dbCtyUser+".RALID A "+"\n"
						+ " WHERE AA46="+Common.sqlChar(sAB46)+"\n"
						+ " AND NOT EXISTS( "+"\n"
						+ " 	SELECT 1 FROM "+dbCtyUser+".RGALL G "+"\n"
						+ " 	WHERE G.CTY=A.CTY "+"\n"
						+ " 	AND G.UNIT=A.UNIT "+"\n"
						+ " 	AND G.GG00='A' "+"\n"
						+ " 	AND G.GG48=A.AA48 "+"\n"
						+ " 	AND G.GG49=A.AA49 "+"\n"
						+ " 	AND G.GG30_1='A7' "+"\n"
						+ " ) "+"\n"
						+ " AND B.CTY=A.CTY "+"\n"
						+ " AND B.UNIT=A.UNIT "+"\n"
						+ " AND B.BA48=A.AA48 "+"\n"
						+ " AND B.BA49=A.AA49 "+"\n"
						+ " AND ABID=B.BB09 "+"\n"
						+ "";

					System.out.println("ADCNT_E:"+sql);
					rs=odb.querySQL(sql);
					while(rs.next()){
						setADCNT23(rs.getString("ADCNT23"));
						setADCNT24(Integer.toString(BCNT2-rs.getInt("ADCNT23")));
						setADCNT25(rs.getString("ADCNT25"));
					}
					rs.getStatement().close();
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
					ret=false;
				} finally {
					try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
					odb.closeAll();
				}
				return ret;
			}
			
			/**
			 * 取得非原民地總所有權人數
			 * @return
			 */
			public int ADCNT_E_TMP(String dbCtyUser,String sAB46){
				int ret=0;
				ODatabase odb = new ODatabase();
				ResultSet rs = null;
				try {
					String sql = " SELECT COUNT(*)as BCNT2 "+"\n"
						+ " FROM "+dbCtyUser+".RBLOW B,"+dbCtyUser+".RALID A "+"\n"
						+ " WHERE AA46="+Common.sqlChar(sAB46)+"\n"
						+ " AND NOT EXISTS( "+"\n"
						+ " 	SELECT 1 FROM "+dbCtyUser+".RGALL G "+"\n"
						+ " 	WHERE G.CTY=A.CTY "+"\n"
						+ " 	AND G.UNIT=A.UNIT "+"\n"
						+ " 	AND G.GG00='A' "+"\n"
						+ " 	AND G.GG48=A.AA48 "+"\n"
						+ " 	AND G.GG49=A.AA49 "+"\n"
						+ " 	AND G.GG30_1='A7'" +"\n"
						+ " ) "+"\n"
						+ " AND B.CTY=A.CTY "+"\n"
						+ " AND B.UNIT=A.UNIT "+"\n"
						+ " AND B.BA48=A.AA48 "+"\n"
						+ " AND B.BA49=A.AA49 "+"\n"
						+ "";

					System.out.println("ADCNT_E_TMP:"+sql);
					rs=odb.querySQL(sql);
					if(rs.next()){
						ret=rs.getInt("BCNT2");
					}
					rs.getStatement().close();
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try { if (rs!=null){ rs.close(); rs=null;} } catch (Exception e) { e.printStackTrace(); }
					odb.closeAll();
				}
				return ret;
			}
			
}