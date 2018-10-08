package com.amido.secureia.serenity.steps;

import net.thucydides.core.annotations.Step;
import com.amido.secureia.serenity.page.CodeValidation;

import static org.assertj.core.api.Assertions.assertThat;

public class CodeValidationSteps {

    private CodeValidation codeValidationPage;

    @Step("Should be on Code Validation screen with the url \"{0}\"")
    public void shouldBeOnCodeValidationScreen(String currentUrl) {
        assertThat(currentUrl).contains(codeValidationPage.getPath());
    }

    @Step("Enter valid code \"{0}\"")
    public void enterCodeOnCodeValidation(String code) {
        codeValidationPage.enterCode(code);
    }

    @Step("Submit the page")
    public void submitCodeValidationPage() {
        codeValidationPage.clickButtonSubmit();
    }

    @Step("Should be shown error message")
    public void shouldSeeErrorMessageOnCodeValidation() {
        assertThat(codeValidationPage.isErrorMessageVisible()).isTrue();
    }

    @Step("Should error message have text \"{0}\"")
    public void shouldErrorMessageHaveTextOnCodeValidation(String errorMessage) {
        assertThat(codeValidationPage.getErrorMessageText()).isEqualTo(errorMessage);
    }
}
