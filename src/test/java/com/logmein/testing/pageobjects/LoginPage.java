package com.logmein.testing.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
    private final WebDriver browser;

    private By emailLocator = By.id("email");
    private By passwordLocator = By.id("passwd");
    private By submitLoginButtonLocator = By.id("SubmitLogin");

    public LoginPage(WebDriver browser) {
        this.browser = browser;
        
        // wait until the page is interactable
        WebElement submitLoginButton = browser.findElement(submitLoginButtonLocator);
        (new WebDriverWait(browser, 5)).until(ExpectedConditions.elementToBeClickable(submitLoginButton));
    }

    public LoginPage typeEmail(String email) {
        browser.findElement(emailLocator).sendKeys(email);
        return this;	
    }

    public LoginPage typePassword(String password) {
        browser.findElement(passwordLocator).sendKeys(password);
        return this;	
    }

    public HomePage submitLogin() {
        browser.findElement(submitLoginButtonLocator).click();
        return new HomePage(browser);	
    }

    public HomePage loginAs(String email, String password) {
    	typeEmail(email);
        typePassword(password);
        return submitLogin();
    }
}
