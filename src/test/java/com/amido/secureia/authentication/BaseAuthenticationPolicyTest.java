package com.amido.secureia.authentication;

import com.amido.secureia.common.*;
import org.jboss.seam.core.ResourceLoader;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.xdi.oxauth.model.common.User;
import org.xdi.oxauth.service.UserService;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verifyNoMoreInteractions;

public abstract class BaseAuthenticationPolicyTest {

    @Mock
    protected ContextsFacade contextsFacade;

    @Mock
    protected FacesMessagesFacade facesMessagesFacade;

    @Mock
    protected UserService userService;

    @Mock
    protected PersonAuthFacade personAuthFacade;

    @Mock
    protected SessionUtils sessionUtils;

    @Mock
    protected UserUtils userUtils;

    @Mock
    protected IdentityFacade identityFacade;

    protected ApplicationProperties applicationProperties;

    protected Map<String, Object> configurationAttributes = new HashMap<String, Object>();

    protected Map<String, String[]> requestParameters = new HashMap<String, String[]>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private void setMocks(AuthenticationPolicy policy) {
        policy.setFacesMessagesFacade(facesMessagesFacade);
        policy.setUserService(userService);
        policy.setPersonAuthFacade(personAuthFacade);
        policy.setSessionUtils(sessionUtils);
        policy.setIdentityFacade(identityFacade);
        policy.setUserUtils(userUtils);
    }

    private void setApplicationProperties(AuthenticationPolicy policy) {
        applicationProperties = new ApplicationProperties();
        applicationProperties.resourceLoader = new ResourceLoader();
        applicationProperties.init(); // will load the app properties from the test resources directory
        policy.setApplicationProperties(applicationProperties);
    }

    protected void initPolicy(AuthenticationPolicy policy) {
        setMocks(policy);
        setApplicationProperties(policy);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(contextsFacade, facesMessagesFacade, userService, personAuthFacade, sessionUtils, identityFacade, userUtils);
    }

    protected User getUser() {
        return new User();
    }

    protected PersonAuth getPersonAuthToResetPassword(boolean isPasswordResetRequested, boolean isSecurityCodeSent, boolean isSecurityCodeVerified) {
        PersonAuth personAuth = getPersonAuth();
        personAuth.setResetPasswordRequested(isPasswordResetRequested);
        personAuth.setSecurityCodeSent(isSecurityCodeSent);
        personAuth.setSecurityCodeVerified(isSecurityCodeVerified);
        return personAuth;
    }

    protected PersonAuth getPersonAuth() {
        return new PersonAuth();
    }
}
