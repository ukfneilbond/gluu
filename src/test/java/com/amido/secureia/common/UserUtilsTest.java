package com.amido.secureia.common;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.xdi.oxauth.model.common.User;
import org.xdi.oxauth.service.UserService;
import java.util.Date;

import static org.mockito.Mockito.verifyNoMoreInteractions;

public class UserUtilsTest {

    @Mock
    private UserService userService;

    private UserUtils userUtils;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userUtils = new UserUtils();
        userUtils.setUserService(userService);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void isThereActiveSecurityCode_whenDateSecurityCodeIsExpired() {
        User user = new User();
        user.setAttribute(UserUtils.UA_SECURITY_CODE_EXPIRY, getExpiredDate());

        Boolean result = userUtils.isSecurityCodeActive(user);
        Assert.assertFalse("Should return false when date security code is expired .", result);
    }

    @Test
    public void isThereActiveSecurityCode_whenDateSecurityCodeIsEmpty() {
        User user = new User();
        user.setAttribute(UserUtils.UA_SECURITY_CODE_EXPIRY, "");

        Boolean result = userUtils.isSecurityCodeActive(user);
        Assert.assertFalse("Should return false when date security code is empty .", result);
    }

    @Test
    public void isThereActiveSecurityCode_whenDateSecurityCodeIsNull() {
        User user = new User();

        Boolean result = userUtils.isSecurityCodeActive(user);
        Assert.assertFalse("Should return false when date security code is null .", result);
    }

    private String getExpiredDate() {
        DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime().withZoneUTC();
        Date expiredDate = DateTime.now().minusHours(1).toDate();
        return dateTimeFormatter.print(expiredDate.getTime());
    }
}
