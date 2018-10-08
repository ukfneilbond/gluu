package com.amido.secureia.serenity.page;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;

import io.appium.java_client.pagefactory.WithTimeout;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;

public class GluuPage extends PageObject {

	private final String path = "identity/home?cid=";
	
	@WithTimeout(time = 10, unit = TimeUnit.SECONDS)
	@FindBy(css = "body > div.rf-ntf.rf-ntf-inf.rf-ntf-pos-tr > div > div.rf-ntf-sum")
	public WebElement notification;

	@WithTimeout(time = 10, unit = TimeUnit.SECONDS)
	@FindBy(className = "bg-green")
	public List<WebElement> buttons;

	@WithTimeout(time = 10, unit = TimeUnit.SECONDS)
	@FindBy(className = "user-menu")
	public WebElement userMenu;

	@FindBy(className = "btn-primary")
	public WebElement signInAgain;
	
	public String getTextFromNotification() {
		return notification.getText();
	}

	public String getPath() {
		return path;
	}

	public void logout() {
		getDriver().navigate().refresh();
		userMenu.click();
		buttons.get(1).click();
	}

}
