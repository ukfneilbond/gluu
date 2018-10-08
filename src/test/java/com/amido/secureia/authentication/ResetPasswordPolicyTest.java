package com.amido.secureia.authentication;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xdi.oxauth.model.common.User;

import com.amido.secureia.common.PasswordValidationResult;

public class ResetPasswordPolicyTest extends BaseAuthenticationPolicyTest {

    private ResetPasswordPolicy policy;

    @Before
    public void setUp() {
        super.setUp();
        policy = new ResetPasswordPolicy();
        initPolicy(policy);
    }

    @Test
    public void appliesTo_step4_whenResetPasswordIsRequestedAndSecurityCodeSentAndEmailIsVerified() {
        User user = getUser();
        when(personAuthFacade.instance()).thenReturn(getPersonAuthToResetPassword(true, true, true));

        Boolean result = policy.appliesTo(4, user);
        Assert.assertTrue("Should apply when step value is 4, reset password is requested and email is verified.",result);

        verify(personAuthFacade).instance();
    }

    @Test
    public void appliesTo_step4_whenEmailIsNotVerified() {
        User user = getUser();
        when(personAuthFacade.instance()).thenReturn(getPersonAuthToResetPassword(true, true, false));

        Boolean result = policy.appliesTo(4, user);
        Assert.assertFalse("Should not apply when step value is 4 and email is not verified.",result);

        verify(personAuthFacade).instance();
    }

    @Test
    public void appliesTo_step4_whenSecurityCodeIsNotSent() {
        User user = getUser();
        when(personAuthFacade.instance()).thenReturn(getPersonAuthToResetPassword(true, false, false));

        Boolean result = policy.appliesTo(4, user);
        Assert.assertFalse("Should not apply when step value is 4 and email security code is not sent.",result);

        verify(personAuthFacade).instance();
    }

    @Test
    public void appliesTo_step4_whenResetPasswordIsNotRequested() {
        when(personAuthFacade.instance()).thenReturn(getPersonAuthToResetPassword(false, false, false));

        Boolean result = policy.appliesTo(4, getUser());
        Assert.assertFalse("Should not apply when step value is 4 and reset password is not requested.",result);

        verify(personAuthFacade).instance();
    }

    @Test
    public void appliesTo_whenStepDiffersFrom4() {
        when(personAuthFacade.instance()).thenReturn(getPersonAuth());

        Boolean result = policy.appliesTo(3, getUser());
        Assert.assertFalse("Should not apply when step value differs from 4.",result);

        verify(personAuthFacade).instance();
    }

    @Test
    public void testValidationPassword_whenOnlySpecialChars() {
    	PasswordValidationResult result = policy.validatePassword("!@£$%^&*(","!@£$%^&*(");
    	Assert.assertEquals(PasswordValidationResult.ERROR_NO_LETTERS_OR_NUMBERS, result);
    }
    
    @Test
    public void testValidationPassword_whenOK() {
    	PasswordValidationResult result = policy.validatePassword("pssword1!","pssword1!");
    	Assert.assertEquals(PasswordValidationResult.OK, result);
    }
    
    @Test
    public void testValidationPassword_whenContainsBlacklistItem() {
    	PasswordValidationResult result = policy.validatePassword("password1","password1");
    	Assert.assertEquals(PasswordValidationResult.ERROR_COMMON_PASSWORD, result);
    }
    
    @Test
    public void testValidationPassword_whenBadConfirmation() {
    	PasswordValidationResult result = policy.validatePassword("password1","password2");
    	Assert.assertEquals(PasswordValidationResult.ERROR_CONFIRMATION, result);
    }
    
    @Test
    public void testValidationPassword_whenNoLetters() {
    	PasswordValidationResult result = policy.validatePassword("123456789@","123456789@");
    	Assert.assertEquals(PasswordValidationResult.ERROR_NO_LETTERS_OR_NUMBERS, result);
    }
    
    @Test
    public void testValidationPassword_whenNoNumbers() {
    	PasswordValidationResult result = policy.validatePassword("sadasdas@","sadasdas@");
    	Assert.assertEquals(PasswordValidationResult.ERROR_NO_LETTERS_OR_NUMBERS, result);
    }

    
}
