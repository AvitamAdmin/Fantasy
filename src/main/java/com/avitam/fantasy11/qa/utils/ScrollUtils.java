package com.avitam.fantasy11.qa.utils;

import com.avitam.fantasy11.qa.framework.TestContext;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static com.avitam.fantasy11.qa.utils.CheilStringUtils.JAVASCRIPT_SCROLL_INTO_VIEW;

public class ScrollUtils {
    public static void scrollIntoView(TestContext context, WebElement element) {
        ((JavascriptExecutor) context.getDriver()).executeScript(JAVASCRIPT_SCROLL_INTO_VIEW, element);
    }
}
