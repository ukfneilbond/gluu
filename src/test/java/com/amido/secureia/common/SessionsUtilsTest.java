package com.amido.secureia.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionsUtilsTest {

    @Mock
    private ContextsFacade contextsFacade;

    @Mock
    private org.jboss.seam.contexts.Context context;

    private SessionUtils sessionUtils;

    @Before
    public void setUp() {
        sessionUtils = new SessionUtils();
        contextsFacade = mock(ContextsFacade.class);
        sessionUtils.setContextsFacade(contextsFacade);
        context = mock(org.jboss.seam.contexts.Context.class);
    }

    @Test
    public void getSessionAttribute_AUTHENTICATED_whenSessionAttributesIsNotNull() {
        Boolean expectedValue= true;
        String testKey = "authenticated";
        Map<String, Object> map = new HashMap<String, Object>();

        when(contextsFacade.sessionContext()).thenReturn(context);
        when(context.get("sessionAttributes")).thenReturn(map);

        sessionUtils.setSessionAttribute(testKey,expectedValue);
        Assert.assertTrue("Should return true, when user is authenticated.", sessionUtils.isAuthenticated());
    }

    @Test
    public void getSessionAttribute_PASSWORD_RESET_whenSessionAttributesIsNotNull() {
        Boolean expectedValue= true;
        String testKey = "password_reset";
        Map<String, Object> map = new HashMap<String, Object>();

        when(contextsFacade.sessionContext()).thenReturn(context);
        when(context.get("sessionAttributes")).thenReturn(map);

        sessionUtils.setSessionAttribute(testKey,expectedValue);
        Assert.assertTrue("Should return true, when password is reset.", sessionUtils.isPasswordReset());
    }

    @Test
    public void getSessionAttribute_FAILED_LOGIN_ATTEMPTS_whenSessionAttributesIsNotNull() {
        int expectedValue= 1;
        String testKey = "failed_login_attempts";
        Map<String, Object> map = new HashMap<String, Object>();

        when(contextsFacade.sessionContext()).thenReturn(context);
        when(context.get("sessionAttributes")).thenReturn(map);

        sessionUtils.setSessionAttribute(testKey,0);
        sessionUtils.incFailedLoginAttempts();

        int resultValue = sessionUtils.getFailedLoginAttempts();
        Assert.assertEquals("Should return 1 from value key, when attribute is incremented.",expectedValue, resultValue);
    }
}
