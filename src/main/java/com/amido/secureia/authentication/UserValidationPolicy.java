package com.amido.secureia.authentication;

import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.xdi.oxauth.model.common.User;

import com.amido.secureia.common.PersonAuth;
import com.amido.secureia.service.EmailService;

@Name(value="userValidationPolicy")
@Scope(ScopeType.APPLICATION)
@AutoCreate
@ApplicationScoped
public class UserValidationPolicy extends BaseAuthenticationPolicy {
	
	private final String CODE_NOT_SENT_MSG = "Security code could not be sent. Please contact your administrator.";
	
	private final String USER_CREDENTIALS_NOT_RECOGNISED_MSG = "Username and email are not recognised.";
	
    @In(value="emailServiceMock", required=true, create=true)
    private EmailService emailService;

    public Boolean appliesTo(int step, User user) {
        PersonAuth personAuth = personAuthFacade.instance();
        return (step == 2 && personAuth.isResetPasswordRequested());
    }

    public String getPage() {
        return "/auth/secureia/user-validation.xhtml";
    }
    
    public Boolean prepare(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user) {
        return true;
    }
    
    private Boolean sendSecurityCode(String userId) {
		String securityCode = null;
		
		User user = userService.getUser(userId);
		
		if (userUtils.isSecurityCodeActive(user)) {
			securityCode = userUtils.getSecurityCode(user);
		} else {
			securityCode = userUtils.generateSecurityCode(user);
		}
		
		String email = userUtils.getEmail(user);
		
    	return emailService.sendSecurityCode(email, securityCode);
    	
    }
    
    public Boolean authenticate(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user) {
    	
    	PersonAuth personAuth = personAuthFacade.instance();
    	
    	String userId = personAuth.getUserId();
    	String email = personAuth.getEmail();
    	
    	if (userUtils.isValidUser(userId, email)) {
    		
    		User _user = userService.getUser(userId);
    		personAuth.setSecurityCodeValidationDisabled(userUtils.isSecurityCodeValidationDisabled(_user));
    		
    		if (personAuth.isSecurityCodeValidationDisabled()) {
    			
    			return true;
    		
    		} else {
        		
    			if (sendSecurityCode(userId)) {
        			
        			personAuth.setSecurityCodeSent(true);
        			return true;
        			
        		} else {
        			
            		addUIErrorMessage(CODE_NOT_SENT_MSG);
            		return false;
        			
        		}
        		
    		}
    		
    	} else {

    		addUIErrorMessage(USER_CREDENTIALS_NOT_RECOGNISED_MSG);
    		return false;
    		
    	}
    	
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
