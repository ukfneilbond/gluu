package com.amido.secureia.common;

import java.util.HashMap;
import java.util.Map;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xdi.oxauth.model.common.User;
import org.xdi.oxauth.service.UserService;
import com.amido.secureia.authentication.AuthenticationPolicy;

@Name(value="sessionUtils")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class SessionUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(SessionUtils.class);
    
    public static final String SA_CURRENT_POLICY = "current_policy";
    
    public static final String SA_AUTH_STEP = "auth_step";
    
    public static final String SA_FAILED_LOGIN_ATTEMPTS = "failed_login_attempts";
    
    public static final String SA_AUTHENTICATED = "authenticated";
    
    public static final String SA_PASSWORD_RESET = "password_reset";
    
    @In(value="contextsFacade", required=true, create=true)
    private ContextsFacade contextsFacade;
    
    @In(value = "identityFacade", required = true, create = true)
    private IdentityFacade identityFacade;
    
    @In(value = "userService", required = true, create = true)
    private UserService userService;
    
    /**
     * Reset all session variables.
     */
    public void reset() {
        setPasswordReset(false);
        setAuthenticated(false);
        setFailedLoginAttempts(0);
    }
    
    public void incFailedLoginAttempts() {
    	int currentValue = getFailedLoginAttempts() +1;
        setFailedLoginAttempts(currentValue);
        int atempts = getFailedLoginAttempts();
        System.out.println("atempts: "+atempts);
    }
    
    public int getFailedLoginAttempts() {
    	Integer result = getSessionAttribute(SA_FAILED_LOGIN_ATTEMPTS, Integer.class); 
    	return result.intValue(); 
    }
    
    public void setFailedLoginAttempts(int value) {
    	setSessionAttribute(SA_FAILED_LOGIN_ATTEMPTS, value);
    }
    
    public void setCurrentPolicy(AuthenticationPolicy policy) {
        setSessionAttribute(SA_CURRENT_POLICY, policy);
    }
    
    public AuthenticationPolicy getCurrentPolicy() {
        return getSessionAttribute(SA_CURRENT_POLICY, AuthenticationPolicy.class);
    }
    
    public Integer getAuthStep() {
        Integer authStep = Integer.valueOf(getEventAttribute(SA_AUTH_STEP, String.class));
        return authStep;
    }
    
    public void setAuthenticated(Boolean authenticated) {
    	setSessionAttribute(SA_AUTHENTICATED, authenticated);
    }
    
    public Boolean isAuthenticated() {
    	return getSessionAttribute(SA_AUTHENTICATED, Boolean.class);
    }

    public void setPasswordReset(Boolean value) {
    	setSessionAttribute(SA_PASSWORD_RESET, value);
    }

    public Boolean isPasswordReset() {
        return getSessionAttribute(SA_PASSWORD_RESET, Boolean.class);
    }

    /**
     * Return the named session attribute value
     * @param key Attribute name
     * @param type Value type
     * @return
     */
    public <T extends Object> T getEventAttribute(String key, Class<T> type) {
        T result = null;
        @SuppressWarnings("unchecked")
        Map<String, Object> sessionAttributes = (Map<String, Object>) contextsFacade.eventContext().get("sessionAttributes");
        if (sessionAttributes != null) {
            result = type.cast(sessionAttributes.get(key));
        }
        return result;
    }
    
    /**
     * Return the named session attribute value
     * @param key Attribute name
     * @param type Value type
     * @return
     */
    public <T extends Object> T getSessionAttribute(String key, Class<T> type) {
        T result = null;
        @SuppressWarnings("unchecked")
        Map<String, Object> sessionAttributes = (Map<String, Object>) contextsFacade.sessionContext().get("sessionAttributes");
        if (sessionAttributes != null) {
            result = type.cast(sessionAttributes.get(key));
        }
        return result;
    }
    
    /**
     * Set a named session attribute value
     * @param key Attribute name
     * @param value Attribute value
     */
    public void setSessionAttribute(String key, Object value) {
        @SuppressWarnings("unchecked")
        Map<String, Object> sessionAttributes = (Map<String, Object>) contextsFacade.sessionContext().get("sessionAttributes");
        if (sessionAttributes == null) {
            sessionAttributes = new HashMap<String, Object>();
            contextsFacade.sessionContext().set("sessionAttributes", sessionAttributes);
        }
        sessionAttributes.put(key, value);
    }
    
    public void removeAttribute(String key) {
        @SuppressWarnings("unchecked")
        Map<String, Object> sessionAttributes = (Map<String, Object>) contextsFacade.sessionContext().get("sessionAttributes");
        if (sessionAttributes != null) {
            sessionAttributes.remove(key);
        }
    }
    
    /**
     * Returns the OxAuth User object for the current authentication session OR NULL when the user is not found
     */
    public User getUser() {
        String userId = identityFacade.sessionInstance().getCredentials().getUsername();
        return userService.getUser(userId);
    }


    public void setContextsFacade(ContextsFacade contextsFacade) {
        this.contextsFacade = contextsFacade;
    }

}
