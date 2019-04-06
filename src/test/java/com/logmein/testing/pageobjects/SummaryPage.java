package com.logmein.testing.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SummaryPage {

	private final WebDriver browser;
	
	private By quantityIncreaseLocator = By.cssSelector("i.icon-plus");
	private By proceedToCheckoutButtonLocator = By.xpath("//div[@id='center_column']/p/a");
    
	public SummaryPage(WebDriver browser) {
        this.browser = browser;
    }

	public void upItemCountWithOne() {
        WebElement plusIcon = browser.findElement(quantityIncreaseLocator);
        WaitAndClick(plusIcon);

		try {
			//TODO: If I dont wait, an alert message shows up. But why we have to wait after the quantity modification? 
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public AddressPage proceedToCheckout() {
        WebElement proceedToCheckout = browser.findElement(proceedToCheckoutButtonLocator);
        WaitAndClick(proceedToCheckout);
        
        return new AddressPage(browser);
	}
	
	private void WaitAndClick(WebElement element) {
		(new WebDriverWait(browser, 20)).until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
}
