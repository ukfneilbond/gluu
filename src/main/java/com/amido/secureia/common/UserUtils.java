package com.amido.secureia.common;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.xdi.oxauth.model.common.User;
import org.xdi.oxauth.service.UserService;

import java.util.Date;

@Name(value="userUtils")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class UserUtils {

    @In(value="userService", required=true, create=true)
    private UserService userService;
    
    @In(value="applicationProperties", required=true, create=true)
    protected ApplicationProperties applicationProperties;

    protected static final String UA_FAILED_LOGIN_ATTEMPTS = "failedLoginAttempts";

    protected static final String UA_STATUS = "gluuStatus";

    protected static final String UA_STATUS_INACTIVE = "inactive";

    protected static final String UA_USERNAME = "uid";

    protected static final String UA_EMAIL = "mail";

    protected static final String UA_PASSWORD = "userPassword";

    protected static final String UA_SECURITY_CODE = "securityCode";

    protected static final String UA_SECURITY_CODE_EXPIRY = "securityCodeExpiry";
    
    protected static final String UA_SECURITY_CODE_VALIDATION_DISABLED = "securityCodeValidationDisabled";

    public Boolean isSecurityCodeValidationDisabled(User user) {
    	return new Boolean(user.getAttribute(UA_SECURITY_CODE_VALIDATION_DISABLED));
    }
    
    public void setSecurityCodeValidationDisabled(User user, Boolean value) {
    	user.setAttribute(UA_SECURITY_CODE_VALIDATION_DISABLED, value.toString());
    }
    
    /**
     * Returns True if the user exists with that userId and the email provided matched the 
     * registered email address and the user is not locked.
     */
    public Boolean isValidUser(String userId, String email) {
    	Boolean result = false;
    	User user = getUser(userId);
    	if (user != null) {
    		result = !isLocked(user) && getEmail(user).equals(email);
    	}
    	return result;
    }
    
    public void updateUserPassword(User user, String password) {
        if (user != null) {
            user.setAttribute(UA_PASSWORD, password);
            user.removeAttribute(UA_SECURITY_CODE);
            user.setAttribute(UA_SECURITY_CODE, " ");
            user.setAttribute(UA_SECURITY_CODE_EXPIRY, "1970-01-01T00:00:00.000Z");
            user.setAttribute(UA_SECURITY_CODE_VALIDATION_DISABLED, "false");
            userService.updateUser(user);
        }
    }

    public String getEmail(User user) {
        return user != null ? user.getAttribute(UA_EMAIL) : "";
    }

    public String getUsername(User user) {
        return user != null ? user.getAttribute(UA_USERNAME) : "";
    }
    
    public Boolean isLocked(User user) {
    	return UA_STATUS_INACTIVE.equals(user.getAttribute(UA_STATUS)) ? true : false; 
    }

    public void lockUser(User user) {
        if (user != null) {
            user.setAttribute(UA_STATUS, UA_STATUS_INACTIVE);
            userService.updateUser(user);
        }
    }

    public void incrementFailedLoginAttempts(User user) {
        int i = Integer.valueOf(user.getAttribute(UA_FAILED_LOGIN_ATTEMPTS)).intValue();
        user.setAttribute(UA_FAILED_LOGIN_ATTEMPTS, String.valueOf(i+1));
        userService.updateUser(user);
    }

    public void resetFailedLoginAttempts(User user) {
        user.setAttribute(UA_FAILED_LOGIN_ATTEMPTS, "0");
        userService.updateUser(user);
    }

    public Integer getFailedLoginAttempts(User user) {
        return Integer.valueOf(user.getAttribute(UA_FAILED_LOGIN_ATTEMPTS)).intValue();
    }

    public String generateSecurityCode(User user) {
        String randomSecurityCode = getRandomSecurityCode();
        user.setAttribute(UA_SECURITY_CODE, randomSecurityCode);
        user.setAttribute(UA_SECURITY_CODE_EXPIRY, getExpiryDateFormatAsString());
        userService.updateUser(user);
        return randomSecurityCode;
    }
    
    public void clearSecurityCode(User user) {
        user.removeAttribute(UA_SECURITY_CODE);
        user.removeAttribute(UA_SECURITY_CODE_EXPIRY);
        userService.updateUser(user);
    }

    public Boolean isSecurityCodeActive(User user) {
        String dateText = user.getAttribute(UA_SECURITY_CODE_EXPIRY);
        Boolean result = false;
        if(dateText != null && !("".equals(dateText))) {
            DateTimeFormatter df = ISODateTimeFormat.dateTime().withZoneUTC();
            Date expiredDate = df.parseDateTime(dateText).toDate();
            System.out.println("expiredDate: "+expiredDate);
            Date now = DateTime.now().toDate();
            System.out.println("now: "+now);
            result = now.before(expiredDate);
        }
        return result;
    }

    public String getExpiryDateFormatAsString() {
        DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime().withZoneUTC(); // Date should be in UTC format
        int ttlMinutes = applicationProperties.getAsInteger(applicationProperties.SECURITY_CODE_TTL);
        Date expiredDate = DateTime.now().plusMinutes(ttlMinutes).toDate();
        return dateTimeFormatter.print(expiredDate.getTime());
    }

    public String getSecurityCode(User user) {
        return user.getAttribute(UA_SECURITY_CODE);
    }

    /**
     * Generate a random 4 number security code.
     * @return
     */
    private String getRandomSecurityCode() {
        //generate a 4 digit integer 1000 <10000
        int randomPIN = (int)(Math.random()*9000)+1000;
        return String.valueOf(randomPIN);
    }

    public User getUser(String userId) {
        User user = userService.getUser(userId);
        return user;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
}
