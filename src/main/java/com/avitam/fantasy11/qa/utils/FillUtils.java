package com.avitam.fantasy11.qa.utils;

import com.avitam.fantasy11.qa.framework.TestContext;
import org.openqa.selenium.By;

import static com.avitam.fantasy11.qa.utils.CheilStringUtils.TEXT_TO_BE_ENTERED;
import static com.avitam.fantasy11.qa.utils.ReportUtils.reportAction;
import static com.avitam.fantasy11.qa.utils.ReportUtils.reportFailure;
import static com.avitam.fantasy11.qa.utils.WaitUtils.waitForElementDisplayedAndClickable;


public class FillUtils {

    public static void enterText(TestContext context, By locator, String textToBeEntered, String actionDescriptionForReporting) {
        try {
            reportAction(context, locator, actionDescriptionForReporting);
            ReportUtils.info(context,
                    context.getLocalizedString(TEXT_TO_BE_ENTERED) +
                            ": \"" +
                            textToBeEntered +
                            "\".",
                    false);
            waitForElementDisplayedAndClickable(context, locator);
            context.getDriver().findElement(locator).sendKeys(textToBeEntered);
        } catch (Exception e) {
            reportFailure(context, locator, actionDescriptionForReporting, e);
        }
    }
}
