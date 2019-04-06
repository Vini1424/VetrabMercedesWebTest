package com.logmein.testing.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckPaymentPage {

	private final WebDriver browser;
	
	private By proceedToCheckoutButtonLocator = By.xpath("//span[text()='I confirm my order']");
    
	public CheckPaymentPage(WebDriver browser) {
        this.browser = browser;
    }
	
	public SuccessfulPaymentPage proceedToCheckout() {
		WebElement proceedToCheckout = browser.findElement(proceedToCheckoutButtonLocator);
		WaitAndClick(proceedToCheckout);
		
		return new SuccessfulPaymentPage(browser);
	}
	
	private void WaitAndClick(WebElement element) {
		(new WebDriverWait(browser, 20)).until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
}
