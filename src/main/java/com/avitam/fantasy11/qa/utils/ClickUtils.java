package com.avitam.fantasy11.qa.utils;

import com.avitam.fantasy11.qa.framework.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.avitam.fantasy11.qa.utils.ReportUtils.reportAction;
import static com.avitam.fantasy11.qa.utils.ReportUtils.reportFailure;
import static com.avitam.fantasy11.qa.utils.WaitUtils.waitForElementDisplayedAndClickable;


public class ClickUtils {
    public static void click(TestContext context, By locator, String actionDescriptionForReporting) {
        try {
            reportAction(context, locator, actionDescriptionForReporting);
            WebElement element = context.getDriver().findElement(locator);
            ScrollUtils.scrollIntoView(context, element);
            waitForElementDisplayedAndClickable(context, locator);
            element.click();
        } catch (Exception e) {
            reportFailure(context, locator, actionDescriptionForReporting, e);
        }
    }
}
