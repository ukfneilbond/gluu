package com.amido.secureia.serenity.steps.definitions;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import com.amido.secureia.serenity.helpers.PutRequest;
import com.amido.secureia.serenity.mocks.User;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import com.amido.secureia.serenity.steps.LoginSteps;
import com.amido.secureia.serenity.steps.GluuSteps;

public class AuthenticationStepsDefinitions {

	@Managed(driver = "chrome")
	WebDriver driver;

	@Steps
	LoginSteps loginSteps;

	@Steps
	GluuSteps gluuSteps;

	User currentUser;
	
	@After("@revertPasswordLockdown")
	public void revertPasswordLockdown() {
		if (currentUser != null && !currentUser.username.equals("notregistered")) {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("failedLoginAttempts", "0");
			parameters.put("gluuStatus", "active");
			PutRequest.updateUserParams(currentUser.username, currentUser.id, parameters);
		}
	}

	@Given("^I \"([^\"]*)\" am on the Login screen$")
	public void i_am_on_the_Login_screen(String username) throws Throwable {
		currentUser = User.getUserWithUsername(username);
		loginSteps.opensLoginPage();
	}

	@Given("^I have the login page url$")
	public void i_have_the_login_page_url() throws Throwable {

	}

	@When("^I enter \"([^\"]*)\" as username and \"([^\"]*)\" as password and submit the page$")
	public void i_enter_as_username_and_as_password_and_submit_the_page(String username, String password)
			throws Throwable {
		loginSteps.enterUsername(username);
		loginSteps.enterPassword(password);
		loginSteps.submitThePage();
	}

	@When("^I enter the Login page$")
	public void i_enter_the_Login_page() throws Throwable {
		loginSteps.opensLoginPage();
	}

	@Then("^I \"([^\"]*)\" should be authenticated on Gluu$")
	public void i_should_be_authenticated_on_Gluu(String name) throws Throwable {
		gluuSteps.shouldSeeFirstNameOnHeading(name);
		gluuSteps.shouldSeeGluuURL(driver.getCurrentUrl());
	}

	@Then("^I \"([^\"]*)\" should see error message \"([^\"]*)\"$")
	public void i_should_see_error_message(String username, String message) throws Throwable {
		loginSteps.shouldSeeErrorMessage();
		loginSteps.shouldErrorMessageHaveText(message);
	}

	@Then("^I should see the Login screen$")
	public void i_should_see_the_Login_screen() throws Throwable {
		loginSteps.shouldBeOnLoginScreen(driver.getCurrentUrl());
		loginSteps.shouldSeeUsernameInputField();
		loginSteps.shouldSeePasswordInputFeld();
		loginSteps.shouldSeeButtonLogin();
		loginSteps.shouldSeeEmptyUsernameInputField();
		loginSteps.shouldSeeEmptyPasswordInputField();
	}

	@When("^I try to authenticate (\\d+) times \"([^\"]*)\" using \"([^\"]*)\" password$")
	public void i_try_to_authenticate_times_using_password(int repeat, String username, String password)
			throws Throwable {
		for (int i = 0; i < repeat; i++) {
			loginSteps.nthTimesEnterUserCredentialsAndSubmitPage(i + 1, username, password);
		}
	}

	@Given("^I \"([^\"]*)\" am locked and I am on the Login screen$")
	public void i_am_locked_and_I_am_on_the_Login_screen(String username) throws Throwable {
		currentUser = User.getUserWithUsername(username);
		loginSteps.opensLoginPage();
		for (int i = 0; i < 5; i++) {
			loginSteps.nthTimesEnterUserCredentialsAndSubmitPage(i + 1, username, "invalid");
		}
		loginSteps.opensLoginPage();
	}

	@Given("^I \"([^\"]*)\" tried (\\d+) times unsucessful login and I am on the Login screen$")
	public void i_tried_times_unsucessful_login_and_I_am_on_the_Login_screen(String username, int repeat)
			throws Throwable {
		loginSteps.opensLoginPage();
		for (int i = 0; i < repeat; i++) {
			loginSteps.nthTimesEnterUserCredentialsAndSubmitPage(i + 1, username, "invalid");
		}
		loginSteps.opensLoginPage();
	}

	@When("^I logout$")
	public void i_logout() throws Throwable {
		gluuSteps.logout();
	}
}
