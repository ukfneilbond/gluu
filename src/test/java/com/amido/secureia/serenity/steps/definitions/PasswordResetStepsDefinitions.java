package com.amido.secureia.serenity.steps.definitions;

import java.util.HashMap;
import java.util.Map;

import com.amido.secureia.serenity.steps.*;
import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import com.amido.secureia.serenity.helpers.GetRequest;
import com.amido.secureia.serenity.helpers.PutRequest;
import com.amido.secureia.serenity.mocks.User;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;

public class PasswordResetStepsDefinitions {

	@Managed(driver = "chrome")
	WebDriver driver;

	@Steps
	LoginSteps loginSteps;

	@Steps
	UserValidationSteps userValidationSteps;

	@Steps
	CodeValidationSteps codeValidationSteps;

	@Steps
	PasswordResetSteps passwordResetSteps;

	@Steps
	GluuSteps gluuSteps;

	User currentUser;

	@After("@resetDefaultPassword")
	public void resetDefaultPassword(Scenario scenario) {
		if (currentUser != null && !currentUser.username.equals("notregistered")) {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("userPassword", "password");
			PutRequest.updateUserParams(currentUser.username, currentUser.id, parameters);
		}
	}

	@After("@resetDefaultPasswordAndUnlock")
	public void resetDefaultPasswordAndUnlock(Scenario scenario) {
		if (currentUser != null && !currentUser.username.equals("notregistered")) {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("userPassword", "password");
			parameters.put("failedLoginAttempts", "0");
			parameters.put("gluuStatus", "active");
			PutRequest.updateUserParams(currentUser.username, currentUser.id, parameters);
		}
	}

	@After("@resetSecurityCodeValidationDisabled")
	public void resetSecurityCodeValidationDisabled(Scenario scenario) {
		if (currentUser != null && !currentUser.username.equals("notregistered")) {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("securityCodeValidationDisabled", "true");
			PutRequest.updateUserParams(currentUser.username, currentUser.id, parameters);
		}
	}

	@After("@revertPasswordLockdown")
	public void revertPasswordLockdown() {
		if (currentUser != null && !currentUser.username.equals("notregistered")) {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("failedLoginAttempts", "0");
			parameters.put("gluuStatus", "active");
			PutRequest.updateUserParams(currentUser.username, currentUser.id, parameters);
		}
	}
	
	@Then("^I should see the reset/create password link with text \"([^\"]*)\"$")
	public void i_should_see_the_reset_create_password_link_with_text(String text) throws Throwable {
		loginSteps.shouldSeeResetCreatePasswordLink();
		loginSteps.shouldSeeResetCreatePasswordLinkWithText(text);
	}

	@When("^I click reset/create password link$")
	public void i_click_reset_create_password_link() throws Throwable {
		loginSteps.clickResetCreatePasswordLink();
	}

	@Then("^I should see the User Validation screen$")
	public void i_should_see_the_User_Validation_screen() throws Throwable {
		userValidationSteps.shouldBeOnUserValidationScreen(driver.getCurrentUrl());
		// assert the other screen elements!!!
	}

	@Given("^I am on the User Validation screen$")
	public void i_am_on_the_User_Validation_screen() throws Throwable {
		loginSteps.opensLoginPage();
		loginSteps.clickResetCreatePasswordLink();
	}

	@When("^I enter \"([^\"]*)\" as username and \"([^\"]*)\" as e-mail address and submit the page$")
	public void i_enter_as_username_and_as_e_mail_address_and_submit_the_page(String username, String email)
			throws Throwable {
		userValidationSteps.enterUsernameOnUserValidation(username);
		userValidationSteps.enterEmailOnUserValidation(email);
		userValidationSteps.submitUserValidationPage();
	}

	@Then("^I should be taken to Code Validation screen$")
	public void i_should_be_taken_to_Code_Validation_screen() throws Throwable {
		codeValidationSteps.shouldBeOnCodeValidationScreen(driver.getCurrentUrl());
	}

	@Then("^I should see error message \"([^\"]*)\" on User Validation screen$")
	public void i_should_see_error_message_on_User_Validation_screen(String errorMessage) throws Throwable {
		userValidationSteps.shouldSeeErrorMessageOnUserValidation();
		userValidationSteps.shouldErrorMessageHaveTextOnUserValidation(errorMessage);
	}

	@Then("^I should see Code Validation screen$")
	public void i_should_see_Code_Validation_screen() throws Throwable {
		codeValidationSteps.shouldBeOnCodeValidationScreen(driver.getCurrentUrl());
	}

	@Given("^I am on the Code Validation screen with user details \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_am_on_the_Code_Validation_screen_with_user_details_and(String username, String email)
			throws Throwable {
		currentUser = User.getUserWithUsername(username);
		loginSteps.opensLoginPage();
		loginSteps.clickResetCreatePasswordLink();
		userValidationSteps.enterUsernameOnUserValidation(username);
		userValidationSteps.enterEmailOnUserValidation(email);
		userValidationSteps.submitUserValidationPage();
	}

	@When("^I enter valid code and submit the page$")
	public void i_enter_valid_code_and_submit_the_page() throws Throwable {
		codeValidationSteps.enterCodeOnCodeValidation(GetRequest.getSecurityCode(currentUser.id));
		codeValidationSteps.submitCodeValidationPage();
	}

	@Then("^I should be taken to Password Reset screen$")
	public void i_should_be_taken_to_Password_Reset_screen() throws Throwable {
		passwordResetSteps.shouldBeOnPasswordResetScreen(driver.getCurrentUrl());
	}

	@When("^I enter invalid code and submit the page$")
	public void i_enter_invalid_code_and_submit_the_page() throws Throwable {
		codeValidationSteps.enterCodeOnCodeValidation("0000");
		codeValidationSteps.submitCodeValidationPage();
	}

	@Then("^I should see error message \"([^\"]*)\" on Code Validation screen$")
	public void i_should_see_error_message_on_Code_Validation_screen(String errorMessage) throws Throwable {
		codeValidationSteps.shouldSeeErrorMessageOnCodeValidation();
		codeValidationSteps.shouldErrorMessageHaveTextOnCodeValidation(errorMessage);
	}

	@Then("^I should see Password Reset screen$")
	public void i_should_see_Password_Reset_screen() throws Throwable {
		passwordResetSteps.shouldBeOnPasswordResetScreen(driver.getCurrentUrl());
		// assert the other screen elements!!!
	}

	@Given("^I am on the Password Reset screen with user details \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_am_on_the_Password_Reset_screen_with_user_details_and(String username, String email)
			throws Throwable {
		currentUser = User.getUserWithUsername(username);
		loginSteps.opensLoginPage();
		loginSteps.clickResetCreatePasswordLink();
		userValidationSteps.enterUsernameOnUserValidation(username);
		userValidationSteps.enterEmailOnUserValidation(email);
		userValidationSteps.submitUserValidationPage();
		codeValidationSteps.enterCodeOnCodeValidation(GetRequest.getSecurityCode(currentUser.id));
		codeValidationSteps.submitCodeValidationPage();
	}

	@When("^I enter valid password and password confirmation and submit the page$")
	public void i_enter_valid_password_and_password_confirmation_and_submit_the_page() throws Throwable {
		passwordResetSteps.enterPasswordAndPasswordConfirmation("1q2w3e4r", "1q2w3e4r");
		passwordResetSteps.submitPasswordResetPage();
	}

	@Then("^I should be taken to Login page$")
	public void i_should_be_taken_to_Login_page() throws Throwable {
		loginSteps.shouldBeOnLoginScreen(driver.getCurrentUrl());
	}

	@When("^I enter \"([^\"]*)\" as password and \"([^\"]*)\" as password confirmation and submit the page$")
	public void i_enter_as_password_and_as_password_confirmation_and_submit_the_page(String password,
			String passwordConfirmation) throws Throwable {
		passwordResetSteps.enterPasswordAndPasswordConfirmation(password, passwordConfirmation);
		passwordResetSteps.submitPasswordResetPage();
	}

	@Given("^I \"([^\"]*)\" had password \"([^\"]*)\" and I reset password to \"([^\"]*)\"$")
	public void i_had_and_I_reset_password_to(String username, String oldpassword, String newpassword) throws Throwable {
		currentUser = User.getUserWithUsername(username);
		loginSteps.opensLoginPage();
		loginSteps.enterUsername(username);
		loginSteps.enterPassword(oldpassword);
		loginSteps.submitThePage();
		gluuSteps.logout();
		loginSteps.opensLoginPage();
		loginSteps.clickResetCreatePasswordLink();
		userValidationSteps.enterUsernameOnUserValidation(username);
		userValidationSteps.enterEmailOnUserValidation(currentUser.email);
		userValidationSteps.submitUserValidationPage();
		codeValidationSteps.enterCodeOnCodeValidation(GetRequest.getSecurityCode(currentUser.id));
		codeValidationSteps.submitCodeValidationPage();
		passwordResetSteps.enterPasswordAndPasswordConfirmation(newpassword, newpassword);
		passwordResetSteps.submitPasswordResetPage();
	}
	
	@Then("^I should see error message \"([^\"]*)\" on Password Reset screen$")
	public void i_should_see_error_message_on_Password_Reset_screen(String errorMessage) throws Throwable {
		passwordResetSteps.shouldSeeErrorMessageOnResetPassword();
		passwordResetSteps.shouldErrorMessageHaveTextOnResetPassword(errorMessage);
	}

	@When("^I enter valid password \"([^\"]*)\" and password confirmation \"([^\"]*)\" and submit the page$")
	public void i_enter_valid_password_and_password_confirmation_and_submit_the_page(String password, String passwordConfirmation) throws Throwable {
		passwordResetSteps.enterPasswordAndPasswordConfirmation(password, passwordConfirmation);
		passwordResetSteps.submitPasswordResetPage();
	}

	@Given("^I \"([^\"]*)\" have Security Code Validation disabled and I reset password to \"([^\"]*)\"$")
	public void iHaveSecurityCodeValidationDisabledAndIResetPasswordTo(String username, String newPassword) throws Throwable {
		currentUser = User.getUserWithUsername(username);
		loginSteps.opensLoginPage();
		loginSteps.clickResetCreatePasswordLink();
		userValidationSteps.enterUsernameOnUserValidation(username);
		userValidationSteps.enterEmailOnUserValidation(currentUser.email);
		userValidationSteps.submitUserValidationPage();
		passwordResetSteps.enterPasswordAndPasswordConfirmation(newPassword, newPassword);
		passwordResetSteps.submitPasswordResetPage();
	}

	@Given("^I \"([^\"]*)\" am locked and I am on the User Validation screen$")
	public void i_am_locked_and_I_am_on_the_User_Validation_screen(String username) throws Throwable {
		currentUser = User.getUserWithUsername(username);
		loginSteps.opensLoginPage();
		for (int i = 0; i < 5; i++) {
			loginSteps.nthTimesEnterUserCredentialsAndSubmitPage(i + 1, username, "invalid");
		}
		loginSteps.clickResetCreatePasswordLink();
	}
}
