package eid.ariel.dto;

public class AuthenticationInfo {
	private String loginId;
	private String passHash;
	
	public String getPassHash() {
		return passHash;
	}
	
	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}
	
	public String getLoginId() {
		return loginId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public AuthenticationInfo() {
		this("", "");
	}
	
	public AuthenticationInfo(String loginId, String passHash) { 
		setLoginId(loginId);
		setPassHash(passHash);
	}
}
