package com.logmein.testing.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SuccessfulPaymentPage {

	private final WebDriver browser;
	
	private By paymentAlertboxLocator = By.cssSelector("p.alert.alert-success");
    
	public SuccessfulPaymentPage(WebDriver browser) {
        this.browser = browser;
    }

	public boolean isPaymentSucces() {
		WebElement successField = browser.findElement(paymentAlertboxLocator);
		(new WebDriverWait(browser, 20)).until(ExpectedConditions.elementToBeClickable(successField));

		return successField.getText().equals("Your order on My Store is complete.");
	}
}
