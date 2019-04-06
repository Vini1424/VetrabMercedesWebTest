package com.logmein.testing.pageobjects;

import static org.junit.Assert.assertNotEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddressPage {

	private final WebDriver browser;
	
	private By useSameAddressCheckboxLocator = By.xpath("//span[@class='checked']");
	private By deliveryAddressSpanLocator = By.xpath("//div[@id='uniform-id_address_delivery']/span");
	private By invoiceAddressSpanLocator = By.xpath("//div[@id='uniform-id_address_invoice']/span");
	private By deliveryAddressDropdownLocator = By.xpath("//div[@id='uniform-id_address_delivery']/select");
	private By invoiceAddressDropdownLocator = By.xpath("//div[@id='uniform-id_address_invoice']/select");
	private By proceedToCheckoutButtonLocator = By.xpath("//div[@id='center_column']/form/p/button[@name='processAddress']");
	
	public AddressPage(WebDriver browser) {
        this.browser = browser;
    }

	public void setDifferentInvoiceAndDeliveryAddress() {
		WebElement addressCheckbox = browser.findElement(useSameAddressCheckboxLocator);
        WaitAndClick(addressCheckbox);
        
        WebElement deliveryAddress = browser.findElement(deliveryAddressSpanLocator);
        WebElement invoiceAddress = browser.findElement(invoiceAddressSpanLocator);
        
        if(deliveryAddress.getText().equals(invoiceAddress.getText())) {
        	Select firstBox = new Select(browser.findElement(deliveryAddressDropdownLocator));
        	firstBox.selectByVisibleText("MyHome");
        	
        	Select secondBox = new Select(browser.findElement(invoiceAddressDropdownLocator));
        	secondBox.selectByVisibleText("OtherHome");
        }
        
        assertNotEquals(invoiceAddress.getText(), deliveryAddress.getText());
	}
	
	public boolean isShippingAddressIsDifferentThanDeliveryAddress() {
        WebElement deliveryAddress = browser.findElement(deliveryAddressSpanLocator);
        WebElement invoiceAddress = browser.findElement(invoiceAddressSpanLocator);
        
        return !invoiceAddress.getText().equals(deliveryAddress.getText());
	}
	
	public ShippingPage proceedToCheckout() {
		WebElement proceedToCheckout = browser.findElement(proceedToCheckoutButtonLocator);
		WaitAndClick(proceedToCheckout);
        
        return new ShippingPage(browser);
	}
	
	private void WaitAndClick(WebElement element) {
		(new WebDriverWait(browser, 20)).until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
	
}
