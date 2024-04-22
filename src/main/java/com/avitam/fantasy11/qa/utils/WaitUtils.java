package com.avitam.fantasy11.qa.utils;

import com.avitam.fantasy11.qa.framework.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.avitam.fantasy11.qa.utils.CheilStringUtils.*;

public class WaitUtils {
    public static void waitForTextToBeVisible(TestContext context, String expectedText) {
        try {
            ReportUtils.info(context,
                    context.getLocalizedString(ACTION_DESCRIPTION) +
                            ": \"" +
                            context.getLocalizedString(WAITING_FOR_TEXT_TO_BE_VISIBLE_ON_PAGE) +
                            "\". " +
                            context.getLocalizedString(EXPECTED_TEXT_IS) +
                            ": \"" +
                            expectedText +
                            "\".",
                    false);
            waitForTextDisplayed(context, expectedText);
        } catch (Exception e) {
            ReportUtils.fail(context,
                    context.getLocalizedString(ERROR_ACTION_FAILED) +
                            ": \"" +
                            context.getLocalizedString(WAITING_FOR_TEXT_VISIBLE) +
                            "\". " +
                            context.getLocalizedString(EXPECTED_TEXT_IS) +
                            ": \"" +
                            expectedText +
                            "\". ",
                    e.getMessage(),
                    true);
        }
    }

    public static void waitForElementDisplayedAndClickable(TestContext context, By locator) {
        context.getWebDriverWait().until(ExpectedConditions.and(
                ExpectedConditions.elementToBeClickable(locator),
                ExpectedConditions.visibilityOfElementLocated(locator)
        ));
    }

    public static void waitForTextDisplayed(TestContext context, String expectedText) {
        context.getWebDriverWait().until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath(String.format(XPATH_CONTAINS_TEXT, expectedText)),
                expectedText));
    }
}
