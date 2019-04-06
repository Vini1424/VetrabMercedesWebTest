package com.logmein.testing.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	
    private final WebDriver browser;
    private final String homeUrl = "http://automationpractice.com/";

    private By signInButtonLocator = By.cssSelector("a.login");
    private By myAccountButtonLocator = By.cssSelector("a.account");

    public HomePage(WebDriver browser) {
        this.browser = browser;
    }

    public LoginPage signIn() {
    	browser.get(homeUrl);
    	browser.findElement(signInButtonLocator).click();
        return new LoginPage(browser);
    }
    
    public boolean isSignedIn() {
    	WebElement myAccountButton = browser.findElement(myAccountButtonLocator);
        (new WebDriverWait(browser, 5)).until(ExpectedConditions.elementToBeClickable(myAccountButton));

        return myAccountButton.getText().equals("Mercedes Vetráb");
    }
}
