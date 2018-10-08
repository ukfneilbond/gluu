package com.amido.secureia.serenity.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;

public class PasswordReset extends PageObject {

	private final String path = "/reset-password";
	private final String errorMessageLabelCssSelector = "#resetPasswordForm > div > ul > span:nth-child(3)";

	@FindBy(id = "resetPasswordForm:password")
	private WebElement passwordEntry;

	@FindBy(id = "resetPasswordForm:passwordConfirmation")
	private WebElement passwordConfirmationEntry;

	@FindBy(id = "resetPasswordForm:submitButton")
	private WebElement buttonSubmit;

	@FindBy(css = errorMessageLabelCssSelector)
	private WebElement errorMessageLabel;

	public String getPath() {
		return path;
	}

	public void enterPassword(String validPassword) {
		passwordEntry.sendKeys(validPassword);
	}

	public void enterPasswordConfirmation(String validPassword) {
		passwordConfirmationEntry.sendKeys(validPassword);
	}

	public void clickButtonSubmit() {
		buttonSubmit.click();
	}

	public boolean isErrorMessageVisible() {
		return isElementVisible(By.cssSelector(errorMessageLabelCssSelector));
	}

	public String getErrorMessageText() {
		return errorMessageLabel.getText();
	}

}
