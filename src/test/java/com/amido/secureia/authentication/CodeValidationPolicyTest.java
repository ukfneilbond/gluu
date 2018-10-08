package com.amido.secureia.authentication;

import com.amido.secureia.common.PersonAuth;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xdi.oxauth.model.common.User;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CodeValidationPolicyTest extends BaseAuthenticationPolicyTest {
    private CodeValidationPolicy policy;

    @Before
    public void setUp() {
        super.setUp();
        policy = new CodeValidationPolicy();
        initPolicy(policy);
    }

    @Test
    public void appliesTo_step3_whenResetPasswordIsRequestedAndSecurityCodeIsSent() {
        when(personAuthFacade.instance()).thenReturn(getPersonAuthToResetPassword(true, true, false));

        Boolean result = policy.appliesTo(3, getUser());
        Assert.assertTrue("Should apply when step value is 3 and reset password is requested.",result);

        verify(personAuthFacade).instance();
    }

    @Test
    public void appliesTo_step3_whenSecurityCodeIsNotSent() {
        when(personAuthFacade.instance()).thenReturn(getPersonAuthToResetPassword(true, false, false));

        Boolean result = policy.appliesTo(3, getUser());
        Assert.assertFalse("Should not apply when step value is 3 and security code is not sent.",result);

        verify(personAuthFacade).instance();
    }

    @Test
    public void appliesTo_step3_whenResetPasswordIsNotRequested() {
        when(personAuthFacade.instance()).thenReturn(getPersonAuthToResetPassword(false, false, false));

        Boolean result = policy.appliesTo(3, getUser());
        Assert.assertFalse("Should not apply when step value is 3 and reset password is not requested.",result);

        verify(personAuthFacade).instance();
    }

    @Test
    public void appliesTo_whenStepDiffersFrom3() {
        when(personAuthFacade.instance()).thenReturn(getPersonAuth());

        Boolean result = policy.appliesTo(4, getUser());
        Assert.assertFalse("Should not apply when step value differs from 3.",result);

        verify(personAuthFacade).instance();
    }

}
