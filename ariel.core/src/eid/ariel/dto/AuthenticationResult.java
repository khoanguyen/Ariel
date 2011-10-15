package eid.ariel.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlRootElement
public class AuthenticationResult {
	private String loginId;
	private String passPhrase;

	@XmlElement
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	@XmlElement
	public String getPassPhrase() {
		return passPhrase;
	}

	public void setPassPhrase(String passPhrase) {
		this.passPhrase = passPhrase;
	}

	public AuthenticationResult(String loginId, String passPhrase) {
		setLoginId(loginId);
		setPassPhrase(passPhrase);
	}

	public AuthenticationResult() {
		this("", "");
	}
}
