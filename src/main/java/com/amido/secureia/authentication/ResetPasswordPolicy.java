package com.amido.secureia.authentication;

import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.xdi.oxauth.model.common.User;

import com.amido.secureia.common.PasswordValidationResult;
import com.amido.secureia.common.PersonAuth;
import com.amido.secureia.service.EmailService;

@Name(value="resetPasswordPolicy")
@Scope(ScopeType.APPLICATION)
@AutoCreate
@ApplicationScoped
public class ResetPasswordPolicy extends BaseAuthenticationPolicy {
    
    public static final String CONTAINS_LETTER = ".*[a-zA-Z]+.*";
    
    public static final String CONTAINS_NUMBER = ".*\\d+.*";
    
    @In(value="emailServiceMock", required=true, create=true)
    private EmailService emailService;

    public Boolean appliesTo(int step, User user) {
        PersonAuth personAuth = personAuthFacade.instance();
        return ( personAuth.isResetPasswordRequested() && 
        	   ( (step == 4 && personAuth.isSecurityCodeSent() && personAuth.isSecurityCodeVerified()) ||
        	     (step == 3 && personAuth.isSecurityCodeValidationDisabled()) ) );
    }

    public String getPage() {
        return "/auth/secureia/reset-password.xhtml";
    }
    
    public Boolean prepare(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user) {
        return true;
    }
    
    public Boolean authenticate(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user) {
    	
    	PersonAuth personAuth = personAuthFacade.instance();
    	User userObj = userService.getUser(personAuth.getUserId());
    	
    	PasswordValidationResult validationResult = validatePassword(personAuth.getPassword(), personAuth.getPasswordConfirmation());
    	
    	if (validationResult.equals(PasswordValidationResult.OK)) {

            userUtils.updateUserPassword(userObj, personAuth.getPassword());
            emailService.sendPasswordResetNotification(userUtils.getEmail(userObj));
            sessionUtils.setPasswordReset(true);
            return true;
    		
    	} else {
    		
    		addUIErrorMessage(validationResult.getMessage());
    		return false;
    		
    	}

    }
    
    public Boolean isPasswordBanned(String password) {
    	Boolean result = false;
    	if (password != null) {
    		String blacklistCSV = applicationProperties.get(applicationProperties.PASSWORD_BLACKLIST);
    		String passwordLowercase = password.toLowerCase();
    		String[] list = blacklistCSV.split(",");
    		for (String word : list) {
    			result = passwordLowercase.contains(word.toLowerCase());
    			if (result) {
    				break;
    			}
			}
    	}
    	return result;
    }
    
    public PasswordValidationResult validatePassword(String password, String passwordConfirmation) {
        
    	if (password == null) {
        	return PasswordValidationResult.ERROR_LENGTH;
        } else if (!password.equals(passwordConfirmation)) {
        	return PasswordValidationResult.ERROR_CONFIRMATION;
        } else if (checkInputLength(password, 8, 32)) {
        	return PasswordValidationResult.ERROR_LENGTH;
        } else if (!password.matches(CONTAINS_LETTER) || !password.matches(CONTAINS_NUMBER)) {
        	return PasswordValidationResult.ERROR_NO_LETTERS_OR_NUMBERS;
        } else if (isPasswordBanned(password)){
        	return PasswordValidationResult.ERROR_COMMON_PASSWORD;
        } else {
        	return PasswordValidationResult.OK;
        }

    }

    private boolean checkInputLength(String content, int minLength, int maxLength) {
        return (content.length() < minLength) || (content.length() > maxLength);
    }
    
}
