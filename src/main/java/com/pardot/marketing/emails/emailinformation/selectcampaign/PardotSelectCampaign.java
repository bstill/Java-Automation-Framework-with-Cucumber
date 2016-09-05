package com.pardot.marketing.emails.emailinformation.selectcampaign;

import com.api.Reporting;
import com.api.Selenium;
import com.api.RandomData;
import org.openqa.selenium.*;

import java.util.List;

public class PardotSelectCampaign {
    private Reporting reporting;

    private RandomData random = new RandomData();

    private By selectFolderModalTitle = By.xpath(".//*[@id='asset-chooser-app-modal']/div[1]/h3");
    private String modalTitleText = "Select A Campaign";

    private By campaignListContainer = By.id("folder-contents");

    private By chooseSelectedButton = By.id("select-asset");

    private By waitIndicator = By.id("indicator");

    public PardotSelectCampaign(Reporting reporting) {
        this.reporting = reporting;
    }

    public void isSelectCampaignModalLoaded(Selenium selenium) {
        reporting.writeStep("Verify Select A Campaign Loaded");

        selenium.waitElementInvisible(waitIndicator);

        reporting.writeInfo("Verify Select A Campaign Modal Title is: " + modalTitleText);
        if (!selenium.getText(selectFolderModalTitle).contains(modalTitleText)) {
            selenium.throwRuntimeException("Page Title is Not: " + modalTitleText, true);
        } else {
            reporting.writePass("Page Title Found");
        }
    }

    public String selectRandomCampaign(Selenium selenium) {
        reporting.writeStep("Select Campaign");
        reporting.writeInfo("Find/Select Random Campaign");

        List<WebElement> elements = selenium.findElements(campaignListContainer, By.tagName("h4"));

        Integer index = random.getRandomNumber(elements.size());
        selenium.click(elements.get(index));

        return selenium.getText(elements.get(index));
    }

    public void clickChooseSelectedButton(Selenium selenium) {
        reporting.writeStep("Choose Selected Campaign");
        reporting.writeInfo("Click Choose Selected Button");
        selenium.click(chooseSelectedButton);
    }


}
