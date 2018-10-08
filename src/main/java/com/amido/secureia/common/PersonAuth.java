package com.amido.secureia.common;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name(value="personAuth")
@Scope(ScopeType.SESSION)
public class PersonAuth {

    private Boolean resetPasswordRequested;

    private String userId;

	private String email;
	
    private String securityCode;
    
    private Boolean securityCodeSent;

    private Boolean securityCodeVerified;
    
    private Boolean securityCodeValidationDisabled;		

    private String password;

    private String passwordConfirmation;
    
    private String registrationUrl;

    public PersonAuth() {
        reset();
    }

    public void reset() {
        setResetPasswordRequested(false);
        setSecurityCodeSent(false);
        setSecurityCodeVerified(false);
        setSecurityCode(null);
        setSecurityCodeValidationDisabled(false);
        setUserId(null);
        setEmail(null);
        setPassword(null);
        setPasswordConfirmation(null);
        setRegistrationUrl(null);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setResetPasswordRequested(Boolean resetPasswordRequested) {
        this.resetPasswordRequested = resetPasswordRequested;
    }

    public Boolean getResetPasswordRequested() {
        return resetPasswordRequested;
    }

    public Boolean isResetPasswordRequested() {
        return resetPasswordRequested;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Boolean isSecurityCodeSent() {
        return securityCodeSent;
    }

    public void setSecurityCodeSent(Boolean emailSecurityCodeSent) {
        this.securityCodeSent = emailSecurityCodeSent;
    }

    public Boolean isSecurityCodeVerified() {
        return securityCodeVerified;
    }

    public void setSecurityCodeVerified(Boolean securityCodeVerified) {
        this.securityCodeVerified = securityCodeVerified;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean isSecurityCodeValidationDisabled() {
		return securityCodeValidationDisabled;
	}

	public void setSecurityCodeValidationDisabled(Boolean securityCodeValidationDisabled) {
		this.securityCodeValidationDisabled = securityCodeValidationDisabled;
	}

	public String getRegistrationUrl() {
		return registrationUrl;
	}

	public void setRegistrationUrl(String registrationUrl) {
		this.registrationUrl = registrationUrl;
	}
	
}
