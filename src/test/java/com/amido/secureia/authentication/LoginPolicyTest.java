package com.amido.secureia.authentication;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginPolicyTest extends BaseAuthenticationPolicyTest {
    private LoginPolicy policy;

    @Before
    public void setUp() {
        super.setUp();
        policy = new LoginPolicy();
        initPolicy(policy);
    }

    @Test
    public void appliesTo_whenStep1() {
        Boolean result = policy.appliesTo(1, getUser());
        Assert.assertTrue("Should always apply when step value is 1.",result);
    }

    @Test
    public void appliesTo_whenStepDiffersFrom1() {
        Boolean result = policy.appliesTo(2, getUser());
        Assert.assertFalse("Should not apply when step value differs from 1.",result);
    }

}
