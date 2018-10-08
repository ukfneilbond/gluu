package com.amido.secureia.service;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("emailServiceMock")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class EmailServiceMock implements EmailService {

    public Boolean sendSecurityCode(String emailAddress, String securityCode) {
        return !("harrison@demo.com".equals(emailAddress));
    }

	public Boolean sendPasswordResetNotification(String emailAddress) {
		return true;
	}

	public Boolean sendRegistrationNotification(String emailAddress, String userId) {
		return true;
	}
}
