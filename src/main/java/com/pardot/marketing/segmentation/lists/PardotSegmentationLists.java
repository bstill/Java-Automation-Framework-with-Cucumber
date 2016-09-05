package com.pardot.marketing.segmentation.lists;

import com.api.Reporting;
import com.api.Selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PardotSegmentationLists {
    private Reporting reporting;

    private String pageTitleText = "Lists";

    private By addListButton = By.id("listxistx_link_create");

    private By listsFilterField = By.name("table_filter");
    private By listsTable = By.id("listx_table");

    private By waitIndicator = By.id("indicator");

    public PardotSegmentationLists(Reporting reporting) {
        this.reporting = reporting;
    }

    public void isSegmentationListsPageLoaded(Selenium selenium) {
        reporting.writeStep("Verify Segmentation Lists Page Loaded");

        selenium.waitElementInvisible(waitIndicator);

        reporting.writeInfo("Verify Sign In Page Title is: " + pageTitleText);
        if (!selenium.getTitle().contains(pageTitleText)) {
            selenium.throwRuntimeException("Page Title is Not: " + pageTitleText, true);
        } else {
            reporting.writePass("Page Title Found");
        }
    }

    public void clickAddListButton(Selenium selenium) {
        reporting.writeStep("Open List Information");
        reporting.writeInfo("Click Add List Button");
        selenium.click(addListButton);
    }

    public void isListExist(Selenium selenium, String listName) {
        reporting.writeStep("Verify List Exists");

        reporting.writeInfo("Search for List: " + listName);
        selenium.clear(listsFilterField);
        selenium.sendKeys(listsFilterField, listName);

        selenium.waitElementInvisible(waitIndicator);

        List<WebElement> elements = selenium.findElements(listsTable, By.tagName("tr"));

        for(WebElement e : elements){
            if (selenium.getText(e).contains(listName)) {
                reporting.writePass("List Found");
                return;
            }
        }

        selenium.throwRuntimeException("List Not Found: " + listName, true);
    }

    public void isListNotExist(Selenium selenium, String listName) {
        reporting.writeStep("Verify List Does Not Exists");

        reporting.writeInfo("Search for List: " + listName);
        selenium.clear(listsFilterField);
        selenium.sendKeys(listsFilterField, listName);

        selenium.waitElementInvisible(waitIndicator);

        List<WebElement> elements = selenium.findElements(listsTable, By.tagName("tr"));

        for(WebElement e : elements){
            if (selenium.getText(e).contains(listName)) {
                selenium.throwRuntimeException("List Found: " + listName, true);
            }
        }

        reporting.writePass("List Not Found");
    }

    public void clickList(Selenium selenium, String listName) {
        reporting.writeStep("Select List");

        reporting.writeInfo("Search for List: " + listName);
        selenium.clear(listsFilterField);
        selenium.sendKeys(listsFilterField, listName);

        selenium.waitElementInvisible(waitIndicator);

        reporting.writeInfo("Click List");
        selenium.click(selenium.findChildElement(listsTable, By.linkText(listName)));
    }


}
