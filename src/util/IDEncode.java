/**
 * @(#)IDEncode.java
 *
 *
 * @author 
 * @version 1.00 2015/12/12
 */
package util;

public class IDEncode {
    private final static String EncodeString1="ThesnowglowswhiteonthemountaintonightNotafootprinttobeseenAkingdomofisolationanditlookslikeImtheQueenThewindishowlinglikethisswirlingstorminsideCouldntkeepitinHeavenknowsItriedDontletthemindontletthemseeBethegoodgirlyoualwayshavetobeConcealdontfeeldontletthemknowWellnowtheyknowLetitgoCantholditbackanymoreTurnawayandslamthedoorHeavenknowsItrieditstruewellmakeabetterdayjustyouandmeMypowerflurriesthroughtheairintothegroundthisisbookappleorangemoi";
    private final static String EncodeString2="YouthinkyouownwhateverlandyoulandonTheearthisjustadeadthingyoucanclaimButIknoweveryrockandtreeandcreatureHasalifehasaspirithaYouthinktheonlypeoplewhoarepeopleArethepeoplewholookandthinklikeyouButifyouwalkthefootstepsofastrangerYoulearnthingsyouneverknewyouneverknewHaveyoueverheardthewolfcrytothebluecornmoonOraskedthegrinningbobcatwhyheCanyousingwithallthevoicesofthemountaingrinnedComerunthehiddenpinetrailsoftheforesthousefatheruginmaopsnHearty";
    /**
     * Creates a new instance of <code>IDEncode</code>.
     */
    private IDEncode() {
    }
    
    static public String EncodePassword(String id){
    	int idx=0;
    	String Result="";
    	if(id.length()==0){
    		return Result;
    	}
    	//算出整個字串的index
    	for(int i=0;i<id.length();i++){
    		//判斷是否為數字,如果為數字直接進行相加,如果不是則轉成ascii後相加
    		if(isNum(id.charAt(i))){
    			idx+=Integer.parseInt(String.valueOf(id.charAt(i)));
    		}else{
    			idx+=(int)id.charAt(i);
    		}
    	}
    	Result=String.format("%04d", idx);
// 		Result=Result+id;
    	if(idx>420) idx = idx - 420; 
    	if(idx>420) idx = idx - 420; 
    	for(int j=0;j<id.length();j++){
    		idx++;
//    		Result=Result+EncodeString1.charAt(idx);
    		Result=Result+String.valueOf(String.format("%02x",EncodeString1.charAt(idx) ^ id.charAt(j)));
//    		Result=Result+String.valueOf(EncodeString1.charAt(idx) ^ id.charAt(j));
//    		Result=Result+EncodeString2.charAt(idx);
     		Result=Result+String.valueOf(String.format("%02x",EncodeString2.charAt(idx) ^ id.charAt(j)));
//     		Result=Result+String.valueOf(EncodeString2.charAt(idx) ^ id.charAt(j));
//    		Result=Result+(char)(EncodeString1.charAt(idx) ^ id.charAt(j));
//   		Result=Result+(char)(EncodeString2.charAt(idx) ^ id.charAt(j));
    	}
    	return Result;
    }
    
    static public String DecodePassword(String id){
    	int idx=0;
    	String Result="";
    	if(id.length()==0){
    		return Result;
    	}
    	idx=Integer.parseInt(id.substring(0,4));
    	id=id.substring(4);
       	if(idx>420) idx = idx - 420; 
    	if(idx>420) idx = idx - 420; 
    	for(int j=0;j<id.length();j++){
    		idx++;
    		if((j+2)<id.length()){
    			String tmp=fromHexString(id.substring(j,j+2));
    			Result=Result+(char)(EncodeString1.charAt(idx) ^ tmp.charAt(0));
    		}
     		j=j+3;
    	}
    	return Result;
    }
    
    
    //判斷是否為數字函式
    static public boolean isNum(char msg){
		if(java.lang.Character.isDigit(msg)){
			return true;
		}
		return false;
	}
	
    //16進制轉換成字元
    static public String fromHexString(String hex) {
	    StringBuilder str = new StringBuilder();
	    for (int i = 0; i < hex.length(); i+=2) {
	        str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
	    }
	    return str.toString();
	}	
    
}
