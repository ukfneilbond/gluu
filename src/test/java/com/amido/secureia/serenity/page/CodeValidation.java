package com.amido.secureia.serenity.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.annotations.At;
import net.thucydides.core.pages.PageObject;

@At("https://ip-172-31-25-165.eu-west-1.compute.internal/oxauth/auth/secureia/code-validation")
public class CodeValidation extends PageObject {

	private final String path = "/code-validation";
	private final String codeEntryId = "codeValidationForm:securityCode";
	private final String buttonSubmitId = "codeValidationForm:continueButton";
	private final String errorMessageLabelCssSelector = "#codeValidationForm > div > ul > span:nth-child(3)";

	@FindBy(id = codeEntryId)
	private WebElement codeEntry;

	@FindBy(id = buttonSubmitId)
	private WebElement buttonSubmit;

	@FindBy(css = errorMessageLabelCssSelector)
	private WebElement errorMessageLabel;

	public String getPath() {
		return path;
	}

	public void enterCode(String code) {
		codeEntry.sendKeys(code);
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
