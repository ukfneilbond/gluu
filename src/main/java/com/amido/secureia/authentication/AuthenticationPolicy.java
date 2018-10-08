package com.amido.secureia.authentication;

import java.util.Map;
import com.amido.secureia.common.*;
import org.xdi.oxauth.model.common.User;

import org.xdi.oxauth.service.UserService;

public interface AuthenticationPolicy {
        
    void setFacesMessagesFacade(FacesMessagesFacade facesMessagesFacade);

    void setPersonAuthFacade(PersonAuthFacade personAuthFacade);

    void setUserService(UserService userService);

    void setSessionUtils(SessionUtils sessionUtils);

    void setUserUtils(UserUtils userUtils);

    void setIdentityFacade(IdentityFacade identityFacade);

    void setApplicationProperties(ApplicationProperties applicationProperties);

    Boolean appliesTo(int step, User user);
    
    String getPage();
    
    Boolean checkAndPrepare(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user);
    
    Boolean prepare(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user);
    
    Boolean checkAndAuthenticate(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user);
    
    Boolean authenticate(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, User user);

    Boolean isFinalStep();
    
}
