package com.amido.secureia.service;

import org.jboss.seam.core.ResourceLoader;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.testng.Assert;

import com.amido.secureia.common.ApplicationProperties;

public class EmailServiceImplTest {
	
	EmailServiceImpl emailService;
	
	@Before
	public void before() {
		emailService = new EmailServiceImpl();
		ApplicationProperties applicationProperties = new ApplicationProperties();
		applicationProperties.resourceLoader = new ResourceLoader();
		applicationProperties.init();
		emailService.applicationProperties = applicationProperties;
	}
	
	@Ignore // for one-off integration testing
	@Test
	public void testSendSecurityCode_integration() {
		
		Boolean result = emailService.sendSecurityCode("trevor.mackenzie@amido.com", "666");
		Assert.assertTrue(result);

	}
	
	@Ignore // for one-off integration testing
	@Test
	public void testSendRegistrationNotification_integration() {
		
		Boolean result = emailService.sendRegistrationNotification("trevor.mackenzie@amido.com", "tjmackenzie");
		Assert.assertTrue(result);

	}

	@Ignore // for one-off integration testing
	@Test
	public void testSendPasswordResetNotification_integration() {
		
		Boolean result = emailService.sendPasswordResetNotification("trevor.mackenzie@amido.com");
		Assert.assertTrue(result);

	}

}
