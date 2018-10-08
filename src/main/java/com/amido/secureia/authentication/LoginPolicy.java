package com.amido.secureia.authentication;

import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Credentials;
import org.xdi.oxauth.model.common.User;

import com.amido.secureia.common.PersonAuth;

@Name(value="loginPolicy")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class LoginPolicy extends BaseAuthenticationPolicy {
	
	private final String LOCKED_MSG = "Your account has been locked. Please contact your administrator.";
	
	private final String ACCESS_DENIED_MSG = "Username and password do not match.";
	
    public Boolean appliesTo(int step, User user) {
        return (step == 1);
    }

    public String getPage() {
        return "/auth/secureia/login.xhtml";
    }
    
    public Boolean prepare(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user) {
		resetSessionAttributes();
		PersonAuth personAuth = personAuthFacade.instance();
		personAuth.setRegistrationUrl(applicationProperties.get(applicationProperties.REGISTRATION_URL));
        return true;
    }

	private void resetSessionAttributes() {
		sessionUtils.reset();
		PersonAuth personAuth = personAuthFacade.instance();
		personAuth.reset();
		Credentials credentials = identityFacade.sessionInstance().getCredentials();
		credentials.clear();
	}

	private int getFailedAttempts(User user) {
		return user != null ? userUtils.getFailedLoginAttempts(user) : 0;
    }
    
    private void incFailedAttempts(User user) { 
    	if (user != null) {
    		userUtils.incrementFailedLoginAttempts(user);
    	}
    }
    
    private void clearFailedAttempts(User user) {
    	if (user != null) {
    		userUtils.resetFailedLoginAttempts(user);
    	}
    }
        
    public Boolean authenticate(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user) {
    	
    	PersonAuth personAuth = personAuthFacade.instance();
    	
    	if (personAuth.isResetPasswordRequested()) {
    		return true;
    	} else {
    		return handleLogin(user);
    	}
    	
    }
    
    public Boolean handleLogin(User user) {
    	
    	int max = applicationProperties.getAsInteger(applicationProperties.MAX_FAILED_LOGIN_ATTEMPTS);
    	
    	if (getFailedAttempts(user) >= max) {

    		addUIErrorMessage(LOCKED_MSG);
    		return false;

    	} else {
    		
    		String username = identityFacade.sessionInstance().getCredentials().getUsername();
    		String password = identityFacade.sessionInstance().getCredentials().getPassword();
    		
    		try {
    			
				if (user != null) {
					
					userService.authenticate(username, password);
					clearFailedAttempts(user);
		    		sessionUtils.setAuthenticated(true);
		    		return true;

				} else {
					
					// User does not exist.
					addUIErrorMessage(ACCESS_DENIED_MSG);
					return false;
					
				}
				
    		} catch (Exception e) {

    			incFailedAttempts(user);
    			if (getFailedAttempts(user) >= max) {
    				userUtils.lockUser(user);
    				addUIErrorMessage(LOCKED_MSG);
    			} else {
    				addUIErrorMessage(ACCESS_DENIED_MSG);
    			}
    			return false;

    		}

    	}    	
    	
    }

	public Boolean isFinalStep() {
        return sessionUtils.isAuthenticated();
    }
    
}
