package com.amido.secureia.authentication;

import java.util.Map;

import com.amido.secureia.common.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.xdi.oxauth.model.common.User;
import org.xdi.oxauth.service.UserService;

public abstract class BaseAuthenticationPolicy implements AuthenticationPolicy {
    
    @In(value="facesMessagesFacade", required=true, create=true)
    protected FacesMessagesFacade facesMessagesFacade;
    
    @In(value="sessionUtils", required=true, create=true)
    protected SessionUtils sessionUtils;

    @In(value="userUtils", required=true, create=true)
    protected UserUtils userUtils;
    
    @In(value="applicationProperties", required=true, create=true)
    protected ApplicationProperties applicationProperties;
    
    @In(value="userService", required=true, create=true)
    protected UserService userService;
    
    @In(value="identityFacade", required=true, create=true)
    protected IdentityFacade identityFacade;

    @In(value="personAuthFacade", required=true, create=true)
    protected PersonAuthFacade personAuthFacade;

    protected static final String MESSAGES_UI_CONTROL_ID = "secureiaMessage";

    public abstract String getPage();
    
    public String getPageURI() {
        String pageURI = this.getPage();
        pageURI = pageURI.substring(0, pageURI.indexOf("."));
        return String.format("/oxauth%s.htm", pageURI).toLowerCase();
    }

    public abstract Boolean prepare(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user);    
    
    public Boolean checkAndPrepare(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user) {
    	return this.prepare(configurationAttributes, requestParameters, user);    	
    }
    
    public abstract Boolean authenticate(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user);
    
    public Boolean checkAndAuthenticate(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user) {
    	return authenticate(configurationAttributes, requestParameters, user);
    }
    
    protected void addUIErrorMessage(String message) {
        FacesMessages facesMessages = facesMessagesFacade.instance(); 
        if (facesMessages.getCurrentMessages() != null) {
            facesMessages.getCurrentMessages().clear();
        }
        facesMessages.addToControl(MESSAGES_UI_CONTROL_ID, StatusMessage.Severity.ERROR, message);
    }

    public Boolean isFinalStep() {
        return false;
    }
    
    public void setFacesMessagesFacade(FacesMessagesFacade facesMessagesFacade) {
        this.facesMessagesFacade = facesMessagesFacade;
    }

    public void setPersonAuthFacade(PersonAuthFacade personAuthFacade){
        this.personAuthFacade = personAuthFacade;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setSessionUtils(SessionUtils sessionUtils) {
        this.sessionUtils = sessionUtils;
    }

    public void setIdentityFacade(IdentityFacade identityFacade) {
        this.identityFacade = identityFacade;
    }

    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public void setUserUtils(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

    protected void log(String message){
        System.out.println("SECUREIA " + message);
    }
}
