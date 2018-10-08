package com.amido.secureia.serenity.steps;

import net.thucydides.core.annotations.Step;
import com.amido.secureia.serenity.page.UserValidation;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by plyomdev on 13/03/17.
 */
public class UserValidationSteps {

    private UserValidation userValidationPage;

    @Step("Should be on User Validation screen with the url \"{0}\"")
    public void shouldBeOnUserValidationScreen(String currentUrl) {
        assertThat(currentUrl).contains(userValidationPage.getPath());
    }

    @Step("Enter \"{0}\" as username")
    public void enterUsernameOnUserValidation(String username) {
        userValidationPage.enterUsername(username);
    }

    @Step("Enter \"{0}\" as e-mail address")
    public void enterEmailOnUserValidation(String email) {
        userValidationPage.enterEmail(email);
    }

    @Step("Submit the page")
    public void submitUserValidationPage() {
        userValidationPage.clickButtonSubmit();
    }

    @Step("Should be shown error message")
    public void shouldSeeErrorMessageOnUserValidation() {
        assertThat(userValidationPage.isErrorMessageVisible()).isTrue();
    }

    @Step("Should error message have text \"{0}\"")
    public void shouldErrorMessageHaveTextOnUserValidation(String errorMessage) {
        assertThat(userValidationPage.getErrorMessageText()).isEqualTo(errorMessage);
    }
}
