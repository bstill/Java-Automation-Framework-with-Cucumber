package com.pardot.login;

import com.api.Reporting;
import com.api.Selenium;
import org.openqa.selenium.By;

public class PardotLogin {
    private Reporting reporting;

    private By emailAddressField = By.id("email_address");
    private By passwordField = By.id("password");
    private By logInButton = By.name("commit");

    private String pageTitleText = "Sign In";

    public PardotLogin(Reporting reporting) {
        this.reporting = reporting;
    }

    public void isLogInPageLoaded(Selenium selenium) {
        reporting.writeStep("Verify Sign In Page Loaded");

        reporting.writeInfo("Verify Sign In Page Title is: " + pageTitleText);
        if (!selenium.getTitle().contains(pageTitleText)) {
            selenium.throwRuntimeException("Page Title is Not: " + pageTitleText, true);
        } else {
            reporting.writePass("Page Title Found");
        }
    }

    public void loginPardot(Selenium selenium, String userName, String password) {
        reporting.writeStep("Perform Log In");

        reporting.writeInfo("Enter Email/Username: " + userName  );
        selenium.sendKeys(emailAddressField, userName);

        reporting.writeInfo("Enter Password");
        selenium.sendKeys(passwordField, password);

        reporting.writeInfo("Click Log In Button");
        selenium.click(logInButton);
    }

}
