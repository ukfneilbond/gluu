package com.amido.secureia.serenity.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.annotations.At;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;

@DefaultUrl("https://ip-172-31-25-165.eu-west-1.compute.internal")
@At("https://ip-172-31-25-165.eu-west-1.compute.internal/oxauth/auth/secureia/login")
public class LoginPage extends PageObject {

	private final String path = "/login";
	private final String usernameEntryId = "loginForm:username";
	private final String passwordEntryId = "loginForm:password";
	private final String buttonLoginId = "loginForm:loginButton";
	private final String errorMessageLabelCssSelector = "#loginForm > div > ul > span:nth-child(3)";
	private final String resetCreatePasswordLinkId = "resetPassword";
	
	@FindBy(id = usernameEntryId)
	private WebElement usernameEntry;

	@FindBy(id = passwordEntryId)
	private WebElement passwordEntry;

	@FindBy(id = buttonLoginId)
	private WebElement buttonLogin;

	@FindBy(css = errorMessageLabelCssSelector)
	private WebElement errorMessageLabel;
	
	@FindBy(id = resetCreatePasswordLinkId)
	private WebElement resetCreatePasswordLink;

	public void enterUsername(String username) {
		usernameEntry.clear();
		usernameEntry.sendKeys(username);
	}

	public void enterPassword(String password) {
		passwordEntry.clear();
		passwordEntry.sendKeys(password);
	}

	public void clickSubmitButton() {
		buttonLogin.click();
	}

	public String getErrorMessageText() {
		return errorMessageLabel.getText();
	}

	public String getPath() {
		return path;
	}

	public boolean isUsernameInputFieldVisible() {
		return isElementVisible(By.id(usernameEntryId));
	}

	public boolean isPasswordInputFieldVisible() {
		return isElementVisible(By.id(passwordEntryId));
	}

	public boolean isButtonLoginVisible() {
		return isElementVisible(By.id(buttonLoginId));
	}

	public String getValueFromUsernameInputField() {
		return usernameEntry.getAttribute("value");
	}

	public String getValueFromPasswordInputField() {
		return passwordEntry.getAttribute("value");
	}

	public boolean isErrorMessageVisible() {
		return isElementVisible(By.cssSelector(errorMessageLabelCssSelector));
	}

	public boolean isResetCreatePasswordLinkVisible() {
		return isElementVisible(By.id(resetCreatePasswordLinkId));
	}

	public String getTextFromResetCreatePasswordLink() {
		return resetCreatePasswordLink.getText();
	}

	public void clickResetCreatePasswordLink() {
		resetCreatePasswordLink.click();
	}
}
