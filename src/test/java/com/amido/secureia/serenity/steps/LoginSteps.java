package com.amido.secureia.serenity.steps;

import com.amido.secureia.serenity.page.LoginPage;
import net.thucydides.core.annotations.Step;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by plyomdev on 13/03/17.
 */
public class LoginSteps {

    private LoginPage loginPage;

    @Step("Opens Login page")
    public void opensLoginPage() {
        loginPage.open();
    }

    @Step("Enter \"{0}\" as username")
    public void enterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @Step("Enter \"{0}\" as password")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @Step("Submit the page")
    public void submitThePage() {
        loginPage.clickSubmitButton();
    }

    @Step("Should be shown error message")
    public void shouldSeeErrorMessage() {
        assertThat(loginPage.isErrorMessageVisible()).isTrue();
    }

    @Step("Should error message have text \"{0}\"")
    public void shouldErrorMessageHaveText(String message) {
        assertThat(loginPage.getErrorMessageText()).isEqualTo(message);
    }

    @Step("Should be on Login screen with the url \"{0}\"")
    public void shouldBeOnLoginScreen(String currentUrl) {
        assertThat(currentUrl).contains(loginPage.getPath());
    }

    @Step("\"{0}\" st/nd/th time entering username\"{1}\" and password \"{2}\" and submit page")
    public void nthTimesEnterUserCredentialsAndSubmitPage(int i, String username, String password) {
        enterUsername(username);
        enterPassword(password);
        submitThePage();
    }

    @Step("Should be shown reset/create password link")
    public void shouldSeeResetCreatePasswordLink() {
        assertThat(loginPage.isResetCreatePasswordLinkVisible()).isTrue();
    }

    @Step("Should be shown reset/create password link with text \"{0}\"")
    public void shouldSeeResetCreatePasswordLinkWithText(String text) {
        assertThat(loginPage.getTextFromResetCreatePasswordLink()).isEqualTo(text);
    }

    @Step("Click reset/create password link")
    public void clickResetCreatePasswordLink() {
        loginPage.clickResetCreatePasswordLink();
    }

    @Step("Should be shown username input field")
    public void shouldSeeUsernameInputField() {
        assertThat(loginPage.isUsernameInputFieldVisible()).isTrue();
    }

    @Step("Should be shown password input field")
    public void shouldSeePasswordInputFeld() {
        assertThat(loginPage.isPasswordInputFieldVisible()).isTrue();
    }

    @Step("Should be shown button \"Login\"")
    public void shouldSeeButtonLogin() {
        assertThat(loginPage.isButtonLoginVisible()).isTrue();
    }

    @Step("Should be shown empty username input field")
    public void shouldSeeEmptyUsernameInputField() {
        assertThat(loginPage.getValueFromUsernameInputField()).isEmpty();
    }

    @Step("Should be shown empty password input field")
    public void shouldSeeEmptyPasswordInputField() {
        assertThat(loginPage.getValueFromPasswordInputField()).isEmpty();
    }

}
