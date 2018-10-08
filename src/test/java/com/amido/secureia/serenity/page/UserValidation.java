package com.amido.secureia.serenity.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.annotations.At;
import net.thucydides.core.pages.PageObject;

@At("https://ip-172-31-25-165.eu-west-1.compute.internal/oxauth/auth/secureia/user-validation")
public class UserValidation extends PageObject {

	private final String path = "/user-validation";
	private final String usernameEntryId = "userValidationForm:username";
	private final String emailEntryId = "userValidationForm:email";
	private final String buttonSubmitId = "userValidationForm:continueButton";
	private final String errorMessageLabelCssSelector = "#userValidationForm > div > ul > span:nth-child(3)";

	@FindBy(id = usernameEntryId)
	private WebElement usernameEntry;

	@FindBy(id = emailEntryId)
	private WebElement emailEntry;

	@FindBy(id = buttonSubmitId)
	private WebElement buttonSubmit;
	
	@FindBy(css = errorMessageLabelCssSelector)
	private WebElement errorMessageLabel;

	public String getPath() {
		return path;
	}

	public void enterUsername(String username) {
		usernameEntry.clear();
		usernameEntry.sendKeys(username);
	}

	public void enterEmail(String email) {
		emailEntry.clear();
		emailEntry.sendKeys(email);
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
