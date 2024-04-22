package com.avitam.fantasy11.qa.utils;

import com.avitam.fantasy11.qa.framework.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import static com.avitam.fantasy11.qa.utils.CheilStringUtils.JAVASCRIPT_REMOVE_ELEMENT;
import static com.avitam.fantasy11.qa.utils.ReportUtils.reportAction;
import static com.avitam.fantasy11.qa.utils.WaitUtils.waitForElementDisplayedAndClickable;

public class JsUtils {
    public static void removeElementFromPage(TestContext context, By locator, String actionDescriptionForReporting) {
        try {
            reportAction(context, locator, actionDescriptionForReporting);
            waitForElementDisplayedAndClickable(context, locator);
            ((JavascriptExecutor) context.getDriver()).executeScript(JAVASCRIPT_REMOVE_ELEMENT, context.getDriver().findElement(locator));
        } catch (Exception e) {
            reportAction(context, locator, actionDescriptionForReporting);
        }
    }
}
