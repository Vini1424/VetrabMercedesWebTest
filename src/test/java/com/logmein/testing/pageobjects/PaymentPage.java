package com.logmein.testing.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentPage {

	private final WebDriver browser;
	
	private By totalPriceLabelLocator = By.id("total_price");
	private By payByCheckButtonLocator = By.cssSelector("a.cheque");
    
	public PaymentPage(WebDriver browser) {
        this.browser = browser;
    }
	
	public boolean isTotalAmountCorrect(String wantedAmount) {
		WebElement totalPrice = browser.findElement(totalPriceLabelLocator);
		return totalPrice.getText().equals(wantedAmount);
	}
	
	public CheckPaymentPage proceedToCheckoutWithPayByCheck() {
		WebElement payByCheck = browser.findElement(payByCheckButtonLocator);
		WaitAndClick(payByCheck);
		
		return new CheckPaymentPage(browser);
	}
	
	private void WaitAndClick(WebElement element) {
		(new WebDriverWait(browser, 20)).until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
}
