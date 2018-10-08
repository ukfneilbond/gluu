package com.amido.secureia.service;

public interface EmailService {

    public Boolean sendSecurityCode(String emailAddress, String securityCode);
    
    public Boolean sendPasswordResetNotification(String emailAddress);
    
    public Boolean sendRegistrationNotification(String emailAddress, String userId);
    
}
