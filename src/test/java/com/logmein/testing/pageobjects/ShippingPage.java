package com.logmein.testing.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShippingPage {

	private final WebDriver browser;
	
	private By termAndServicesCheckboxLocator = By.xpath("//div[@id='uniform-cgv']/span");
	private By proceedToCheckoutButtonLocator = By.xpath("//button[@name='processCarrier']");
    
	public ShippingPage(WebDriver browser) {
        this.browser = browser;
    }
	
	public void checkInTermsAndServices() {
		WebElement termsAndServices = browser.findElement(termAndServicesCheckboxLocator);
		WaitAndClick(termsAndServices);
	}
	
	public PaymentPage proceedToCheckout() {
		WebElement proceedToCheckout = browser.findElement(proceedToCheckoutButtonLocator);
		WaitAndClick(proceedToCheckout);
		
		return new PaymentPage(browser);
	}
	
	private void WaitAndClick(WebElement element) {
		(new WebDriverWait(browser, 20)).until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
}
