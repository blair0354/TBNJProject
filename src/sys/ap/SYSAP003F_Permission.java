package sys.ap;

import java.io.Serializable;

public class SYSAP003F_Permission implements Serializable{

	private static final long serialVersionUID = 1L;
	
	String programID;
	String authority;
	
	public SYSAP003F_Permission(String pid,String auth_type){
		programID = pid;
		authority = auth_type;
	}
	
	public String getAuthority() {
		return authority;
	}
	public String getProgramID() {
		return programID;
	}

	
	
	
}