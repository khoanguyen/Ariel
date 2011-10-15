package eid.ariel.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlRootElement
public class AuthenticationInfo {
	private String loginId;
	private String passHash;
	
	@XmlElement
	public String getPassHash() {
		return passHash;
	}
	
	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}
	
	@XmlElement
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
