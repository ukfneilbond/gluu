package com.amido.secureia.serenity.steps;

import net.thucydides.core.annotations.Step;
import com.amido.secureia.serenity.page.PasswordReset;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by plyomdev on 13/03/17.
 */
public class PasswordResetSteps {

    private PasswordReset passwordResetPage;

    @Step("Should be on Password Reset screen with the url \"{0}\"")
    public void shouldBeOnPasswordResetScreen(String currentUrl) {
        assertThat(currentUrl).contains(passwordResetPage.getPath());
    }

    @Step("Submit the page")
    public void submitPasswordResetPage() {
        passwordResetPage.clickButtonSubmit();
    }

    @Step("Enter password \"{0}\" and password confirmation \"{0}\"")
    public void enterPasswordAndPasswordConfirmation(String password, String passwordConfirmation) {
        passwordResetPage.enterPassword(password);
        passwordResetPage.enterPasswordConfirmation(passwordConfirmation);
    }

    @Step("Should be shown error message")
    public void shouldSeeErrorMessageOnResetPassword() {
        assertThat(passwordResetPage.isErrorMessageVisible()).isTrue();
    }

    @Step("Should error message have text \"{0}\"")
    public void shouldErrorMessageHaveTextOnResetPassword(String errorMessage) {
        assertThat(passwordResetPage.getErrorMessageText()).isEqualTo(errorMessage);
    }

}
