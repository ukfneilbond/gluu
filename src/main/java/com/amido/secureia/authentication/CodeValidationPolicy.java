package com.amido.secureia.authentication;

import java.util.Map;
import com.amido.secureia.common.PersonAuth;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.xdi.oxauth.model.common.User;

@Name(value="codeValidationPolicy")
@Scope(ScopeType.APPLICATION)
@AutoCreate
@ApplicationScoped
public class CodeValidationPolicy extends BaseAuthenticationPolicy {
	
	private final String CODE_MISMATCH_MSG = "Security code does not match.";

    public Boolean appliesTo(int step, User user) {
        PersonAuth personAuth = personAuthFacade.instance();
        return (step == 3 
        		&& personAuth.isResetPasswordRequested() 
        		&& personAuth.isSecurityCodeSent()
        		&& !personAuth.isSecurityCodeValidationDisabled());
    }

    public String getPage() {
        return "/auth/secureia/code-validation.xhtml";
    }
    
    public Boolean prepare(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user) {
        return true;
    }
    
    public Boolean authenticate(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user) {
        
    	PersonAuth personAuth = personAuthFacade.instance();
    	User userObj = userService.getUser(personAuth.getUserId());
        String codeRequired = userUtils.getSecurityCode(userObj);
        String codeGiven = personAuth.getSecurityCode();
        
        if ((codeGiven != null) && (codeGiven.equals(codeRequired))) {
        	
            personAuth.setSecurityCodeVerified(true);
            return true;
            
        } else {
        	
        	addUIErrorMessage(CODE_MISMATCH_MSG);
        	return false;
        	
        }
        
    }
}
