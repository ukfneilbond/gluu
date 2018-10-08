package com.amido.secureia.service;

import javax.mail.Message;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.simplejavamail.MailException;
import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;

import com.amido.secureia.common.ApplicationProperties;

@Name("emailServiceImpl")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class EmailServiceImpl implements EmailService {
	
    @In(value="applicationProperties", required=true, create=true)
    protected ApplicationProperties applicationProperties;
    
    private Boolean sendEmail(String recipientAddress, String subject, String body) {
    	
    	Boolean result = false;
    	
    		String mailServerHost = applicationProperties.get(applicationProperties.MAIL_SERVER_HOST);
    		String mailUser = applicationProperties.get(applicationProperties.MAIL_SERVER_USER);
    		String mailPassword = applicationProperties.get(applicationProperties.MAIL_SERVER_PASSWORD);
    		String fromAddress = applicationProperties.get(applicationProperties.MAIL_FROM_ADDRESS);
    		
        	Email email = new Email();
        	email.setFromAddress("", fromAddress);
        	email.addRecipient("", recipientAddress, Message.RecipientType.TO);
        	email.setSubject(subject);
        	email.setText(body);

			try {
				
				new Mailer(mailServerHost, 465, mailUser, mailPassword, TransportStrategy.SMTP_SSL).sendMail(email);
				result = true;
				
			} catch (MailException e) {
				e.printStackTrace();
				result = false;
			}
        
    	return result;
    	
    }

    public Boolean sendSecurityCode(String emailAddress, String securityCode) {
    	Boolean result = false;
    	if (emailAddress != null && securityCode != null) {
    		String subject = applicationProperties.get(applicationProperties.MAIL_SECURITY_CODE_SUBJECT);
    		String body = String.format(applicationProperties.get(applicationProperties.MAIL_SECURITY_CODE_BODY), securityCode);
    		result = sendEmail(emailAddress, subject, body);
    	}
    	return result;
    }

	public Boolean sendPasswordResetNotification(String emailAddress) {
    	Boolean result = false;
    	if (emailAddress != null) {
    		String subject = applicationProperties.get(applicationProperties.MAIL_PASSWORD_RESET_SUBJECT);
    		String body = applicationProperties.get(applicationProperties.MAIL_PASSWORD_RESET_BODY);
    		result = sendEmail(emailAddress, subject, body);
    	}
    	return result;
	}

	public Boolean sendRegistrationNotification(String emailAddress, String userId) {
    	Boolean result = false;
    	if (emailAddress != null) {
    		String subject = applicationProperties.get(applicationProperties.MAIL_REGISTRATION_SUBJECT);
    		String body = String.format(applicationProperties.get(applicationProperties.MAIL_REGISTRATION_BODY), userId);
    		result = sendEmail(emailAddress, subject, body);
    	}
    	return result;
	}
    
}
