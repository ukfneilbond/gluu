package com.amido.secureia.authentication;

import com.amido.secureia.common.PersonAuth;
import com.amido.secureia.service.EmailService;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.xdi.oxauth.model.common.User;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserValidationPolicyTest extends BaseAuthenticationPolicyTest {

    private UserValidationPolicy policy;

    @Mock
    private EmailService emailService;

    @Before
    public void setUp() {
        super.setUp();
        policy = new UserValidationPolicy();
        policy.setEmailService(emailService);
        initPolicy(policy);
    }

    @Test
    public void appliesTo_step2_whenResetPasswordIsRequested() {
        when(personAuthFacade.instance()).thenReturn(getPersonAuthToResetPassword(true, false, false));

        Boolean result = policy.appliesTo(2, getUser());
        Assert.assertTrue("Should apply when step value is 2 and reset password is requested.",result);

        verify(personAuthFacade).instance();
    }

    @Test
    public void appliesTo_step2_whenResetPasswordIsNoTRequested() {
        when(personAuthFacade.instance()).thenReturn(getPersonAuth());

        Boolean result = policy.appliesTo(2, getUser());
        Assert.assertFalse("Should not apply when reset password is not requested.",result);

        verify(personAuthFacade).instance();
    }

    @Test
    public void appliesTo_whenStepDiffersFrom2() {
        when(personAuthFacade.instance()).thenReturn(getPersonAuth());

        Boolean result = policy.appliesTo(3, getUser());
        Assert.assertFalse("Should not apply when step value differs from 2.",result);

        verify(personAuthFacade).instance();
    }

}
