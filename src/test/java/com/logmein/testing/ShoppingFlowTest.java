package com.logmein.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.FileSystems;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.logmein.testing.pageobjects.AddressPage;
import com.logmein.testing.pageobjects.CheckPaymentPage;
import com.logmein.testing.pageobjects.HomePage;
import com.logmein.testing.pageobjects.LoginPage;
import com.logmein.testing.pageobjects.PaymentPage;
import com.logmein.testing.pageobjects.SearchPage;
import com.logmein.testing.pageobjects.ShippingPage;
import com.logmein.testing.pageobjects.SuccessfulPaymentPage;
import com.logmein.testing.pageobjects.SummaryPage;

public class ShoppingFlowTest {

	private WebDriver browser;
	
	@Before
	public void Before() {
		String driverPath = FileSystems.getDefault().getPath("src/test/resources/geckodriver.exe").toString();
		System.setProperty("webdriver.gecko.driver", driverPath);
		
		FirefoxOptions options = new FirefoxOptions();
		browser = new FirefoxDriver(options);
	}
	
	@After
	public void After() {
		browser.quit();
	}
	
	@Test
	public void ShoppingFlowTestSteps() {
		LoginTest();
		PutDressIntoCartTest();
		PayProgressWithTwoItem();
		ChangeAdressInPaymentFlowTest();
		TotalAmountTest();
		PaymentSuccessTest();
	}

	@Test
	public void ShoppingFlowTestWithPOs() {
		HomePage homePage = new HomePage(browser);
    	LoginPage loginPage = homePage.signIn();
    	homePage = loginPage.loginAs("14vini24@gmail.com", "Testing123!");
    	
    	assertTrue("The account logged in is not the expected one!", homePage.isSignedIn());
    	
    	SearchPage searchPage = new SearchPage(browser);
    	searchPage.putDressIntoCartDressByNameAndPrice("Printed Dress", "$50.99");
    	
    	assertTrue("Cannot put the dress into the cart!", searchPage.isProductAddedToMyCart());
    	
    	SummaryPage summaryPage = searchPage.proceedToCheckout();
    	summaryPage.upItemCountWithOne();
    	AddressPage addressPage = summaryPage.proceedToCheckout();
    	addressPage.setDifferentInvoiceAndDeliveryAddress();
    	
    	assertTrue("Cannot set different delivery and invoice address!", addressPage.isShippingAddressIsDifferentThanDeliveryAddress());
    	
    	ShippingPage shippingPage = addressPage.proceedToCheckout();
    	shippingPage.checkInTermsAndServices();
    	PaymentPage paymentPage = shippingPage.proceedToCheckout();
    	
    	assertTrue("Total amount is incorrect!", paymentPage.isTotalAmountCorrect("$103.98"));
    	
    	CheckPaymentPage checkPaymentPage = paymentPage.proceedToCheckoutWithPayByCheck();
    	SuccessfulPaymentPage successfulPaymentPage = checkPaymentPage.proceedToCheckout();
    	
    	assertTrue("Payment failed!", successfulPaymentPage.isPaymentSucces());
	}

	private void LoginTest() {
		HomePage homePage = new HomePage(browser);
    	
    	LoginPage loginPage = homePage.signIn();
    	homePage = loginPage.loginAs("14vini24@gmail.com", "Testing123!");
    	
    	assertTrue("The account logged in is not the expected one!", homePage.isSignedIn());
	}


	private void PutDressIntoCartTest() {
		WebElement searchBox = browser.findElement(By.id("search_query_top"));
		searchBox.sendKeys("Printed dress");
		
		WebElement searchButton = browser.findElement(By.cssSelector("button.btn.btn-default.button-search"));
		WaitAndClick(searchButton);
	
		WebElement wantedDress = browser.findElement(By.xpath("//div[contains(@class, 'right-block') and contains(.//h5/a, 'Printed Dress') and contains(.//div/span, '$50.99')]/h5/a"));
		WaitAndClick(wantedDress);
		
		WebElement addToCartButton = browser.findElement(By.cssSelector("button.exclusive"));
		WaitAndClick(addToCartButton);
		
		WebElement okIcon = browser.findElement(By.xpath("//div[@id='layer_cart']/div/div/h2"));
        (new WebDriverWait(browser, 5)).until(ExpectedConditions.visibilityOf(okIcon));
        assertEquals("Success message is not the expected!", "Product successfully added to your shopping cart", okIcon.getText());
	}
	
	private void PayProgressWithTwoItem() {
		WebElement proceedToCheckout = browser.findElement(By.xpath("//div[@class='button-container']/a"));
        WaitAndClick(proceedToCheckout);
        
        WebElement plusIcon = browser.findElement(By.cssSelector("i.icon-plus"));
        WaitAndClick(plusIcon);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
        proceedToCheckout = browser.findElement(By.xpath("//div[@id='center_column']/p/a"));
        WaitAndClick(proceedToCheckout);
	}
	
	private void ChangeAdressInPaymentFlowTest() {
		WebElement addressCheckbox = browser.findElement(By.xpath("//span[@class='checked']"));
        WaitAndClick(addressCheckbox);
        
        WebElement deliveryAdress = browser.findElement(By.xpath("//div[@id='uniform-id_address_delivery']/span"));
        WebElement invoiceAdress = browser.findElement(By.xpath("//div[@id='uniform-id_address_invoice']/span"));
        
        if(deliveryAdress.getText().equals(invoiceAdress.getText())) {
        	Select firstBox = new Select(browser.findElement(By.xpath("//div[@id='uniform-id_address_delivery']/select")));
        	firstBox.selectByVisibleText("MyHome");
        	
        	Select secondBox = new Select(browser.findElement(By.xpath("//div[@id='uniform-id_address_invoice']/select")));
        	secondBox.selectByVisibleText("OtherHome");
        }
        
        assertNotEquals(invoiceAdress.getText(), deliveryAdress.getText());
	}
	
	private void TotalAmountTest() {
		WebElement proceedToCheckout = browser.findElement(By.xpath("//div[@id='center_column']/form/p/button[@name='processAddress']"));
		WaitAndClick(proceedToCheckout);
		
		WebElement termsAndServices = browser.findElement(By.xpath("//div[@id='uniform-cgv']/span"));
		WaitAndClick(termsAndServices);

		proceedToCheckout = browser.findElement(By.xpath("//button[@name='processCarrier']"));
		WaitAndClick(proceedToCheckout);
		
		WebElement totalPrice = browser.findElement(By.id("total_price"));
		assertEquals(totalPrice.getText(), "$103.98");
	}
	
	private void PaymentSuccessTest() {
		WebElement bankWire = browser.findElement(By.cssSelector("a.cheque"));
		WaitAndClick(bankWire);
		
		WebElement proceedToCheckout = browser.findElement(By.xpath("//span[text()='I confirm my order']"));
		WaitAndClick(proceedToCheckout);
		
		WebElement successField = browser.findElement(By.cssSelector("p.alert.alert-success"));
        WaitAndClick(successField);

        assertEquals(successField.getText(), "Your order on My Store is complete.");
	}
	
	private void WaitAndClick(WebElement element) {
		(new WebDriverWait(browser, 20)).until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
}
