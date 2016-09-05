package com.pardot.marketing.segmentation.lists.list;

import com.api.Reporting;
import com.api.Selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PardotSegmentationList {
    private Reporting reporting;

    private By editListLink = By.linkText("Edit");

    private By listsContainer= By.id("listxProspect_table");

    private By waitIndicator = By.id("indicator");

    public PardotSegmentationList(Reporting reporting) {
        this.reporting = reporting;
    }

    public void isListPageLoaded(Selenium selenium, String pageTitleText) {
        reporting.writeInfo("---> Verify List Page Loaded");

        selenium.waitElementInvisible(waitIndicator);

        reporting.writeInfo("-----> Verify List Page Title is: " + pageTitleText);
        if (!selenium.getTitle().contains(pageTitleText)) {
            selenium.throwRuntimeException("Page Title is Not: " + pageTitleText, true);
        } else {
            reporting.writePass("Page Title Found");
        }
    }

    public void clickEditListLink(Selenium selenium) {
        reporting.writeStep("---> Open List Information");

        reporting.writeInfo("-----> Click Edit List Button");
        selenium.click(editListLink);
    }

    public void isListProspectExist(Selenium selenium, String prospectName) {
        reporting.writeInfo("---> Verify Prospect Added to List: " + prospectName);

        List<WebElement> elements = selenium.findElements(listsContainer, By.tagName("tr"));

        for(WebElement e : elements){
            List<WebElement> cells = selenium.findElements(e, By.tagName("td"));

            for(WebElement td : cells){
                if (selenium.getText(td).trim().equals(prospectName)) {
                    reporting.writePass("Found Prospect");
                    return;
                }
            }
        }

        selenium.throwRuntimeException("Prospect Not Found: " + prospectName, true);
    }


}
