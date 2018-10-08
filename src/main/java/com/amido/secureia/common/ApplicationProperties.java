package com.amido.secureia.common;
import java.util.ResourceBundle;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Name(value="applicationProperties")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class ApplicationProperties {
    
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationProperties.class);
    
    public final String MAIL_SERVER_HOST = "resilienceDirect.mailServerHost";
    
    public final String MAIL_SERVER_USER = "resilienceDirect.mailServerUser";
    
    public final String MAIL_SERVER_PASSWORD = "resilienceDirect.mailServerPassword";
    
    public final String MAIL_FROM_ADDRESS = "resilienceDirect.mailFromAddress";
    
    public final String MAX_FAILED_LOGIN_ATTEMPTS = "resilienceDirect.maxFailedLoginAttempts";
    
    public final String EMAIL_SERVICE_NAME = "resilienceDirect.emailServiceName";
    
    public final String MAIL_SECURITY_CODE_SUBJECT = "resilienceDirect.mailSecurityCodeSubject";
    
    public final String MAIL_SECURITY_CODE_BODY = "resilienceDirect.mailSecurityCodeBody";

    public final String MAIL_REGISTRATION_SUBJECT = "resilienceDirect.mailRegistrationSubject";
    
    public final String MAIL_REGISTRATION_BODY = "resilienceDirect.mailRegistrationBody";

    public final String MAIL_PASSWORD_RESET_SUBJECT = "resilienceDirect.mailPasswordResetSubject";
    
    public final String MAIL_PASSWORD_RESET_BODY = "resilienceDirect.mailPasswordResetBody";
    
    public final String SECURITY_CODE_TTL = "resilienceDirect.securityCodeTTLMinutes";
    
    public final String REGISTRATION_URL = "resilienceDirect.registrationUrl";
    
    public final String PASSWORD_BLACKLIST = "resilienceDirect.passwordBlacklist";
    
    @In(value="org.jboss.seam.core.resourceLoader", required=true, create=true)
    public ResourceLoader resourceLoader;
    
    private ResourceBundle properties;
    
    @Create
    public void init() {
        LOG.debug("Loading resource bundle.");
        if (resourceLoader != null) {
            properties = resourceLoader.loadBundle("secureia");
            if (properties != null) {
                LOG.info("Application properties (secureia) loaded OK.");
                for (String key : properties.keySet()) {
                    LOG.info(String.format("%s=%s", key, properties.getString(key)));
                }
            } else {
                LOG.error("Application properties could not be loaded. secureia.properties not found!");
            }
        } else {
            LOG.error("Application properties could not be loaded. No resource loader found! ");
        }
    }
    
    public String get(String key) {
        return properties.getString(key);
    }
    
    public int getAsInteger(String key) {
    	return new Integer(get(key)).intValue();
    }

}