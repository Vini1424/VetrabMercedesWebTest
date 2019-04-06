package com.logmein.testing.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {

    private final WebDriver browser;

	private By searchBoxLocator = By.id("search_query_top");
	private By searchButtonLocator = By.cssSelector("button.btn.btn-default.button-search");
	private By addToCartButtonLocator = By.cssSelector("button.exclusive");
	private By addedOkIconLocator = By.xpath("//div[@id='layer_cart']/div/div/h2");
	private By proceedToCheckoutButtonLocator = By.xpath("//div[@class='button-container']/a");
    
	public SearchPage(WebDriver browser) {
        this.browser = browser;
    }
	
	public void putDressIntoCartDressByNameAndPrice(String dressName, String price) {
		WebElement searchBox = browser.findElement(searchBoxLocator);
		searchBox.sendKeys("Printed dress");
		
		WebElement searchButton = browser.findElement(searchButtonLocator);
		WaitAndClick(searchButton);
	
		String xpathForDress = String.format("//div[contains(@class, 'right-block') and contains(.//h5/a, '%s') and contains(.//div/span, '%s')]/h5/a", dressName, price);
		WebElement wantedDress = browser.findElement(By.xpath(xpathForDress));
		WaitAndClick(wantedDress);
		
		WebElement addToCartButton = browser.findElement(addToCartButtonLocator);
		WaitAndClick(addToCartButton);        
	}
	
	public boolean isProductAddedToMyCart() {
		WebElement okIcon = browser.findElement(addedOkIconLocator);
        (new WebDriverWait(browser, 5)).until(ExpectedConditions.visibilityOf(okIcon));
        
        return okIcon.getText().equals("Product successfully added to your shopping cart");
	}
	
	public SummaryPage proceedToCheckout() {
        WebElement proceedToCheckout = browser.findElement(proceedToCheckoutButtonLocator);
        WaitAndClick(proceedToCheckout);
        
        return new SummaryPage(browser);
	}
	
	private void WaitAndClick(WebElement element) {
		(new WebDriverWait(browser, 20)).until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
}
