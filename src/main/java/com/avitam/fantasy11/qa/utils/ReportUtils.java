package com.avitam.fantasy11.qa.utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.avitam.fantasy11.qa.framework.ExtentManager;
import com.avitam.fantasy11.qa.framework.TestContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static com.avitam.fantasy11.qa.utils.CheilStringUtils.*;


public class ReportUtils {

    private static Logger LOG = LoggerFactory.getLogger(ReportUtils.class);

    public static void info(TestContext context, String details, boolean takeScreenshot) {
        doLog(context, Status.INFO, details, takeScreenshot);
    }

    public static void fail(TestContext context, String details, String spanErrorMessage, boolean takeScreenshot) {
        doLog(context, Status.INFO, details + generateSpanLog(spanErrorMessage), takeScreenshot);
    }

    public static void reportFailure(TestContext context, By locator, String actionDescriptionForReporting, Exception e) {
        String localizedActionDescriptionForReporting = context.getLocalizedString(actionDescriptionForReporting);
        ReportUtils.fail(context,
                String.format(REPORT_MESSAGE,
                        context.getLocalizedString(ERROR_ACTION_FAILED),
                        localizedActionDescriptionForReporting,
                        context.getLocalizedString(ELEMENT_CODE),
                        locator.toString()),
                e.getMessage(),
                true);
    }

    public static void reportAction(TestContext context, By locator, String actionDescriptionForReporting) {
        String localizedActionDescriptionForReporting = context.getLocalizedString(actionDescriptionForReporting);
        ReportUtils.info(context,
                String.format(REPORT_MESSAGE,
                        context.getLocalizedString(ACTION_DESCRIPTION),
                        localizedActionDescriptionForReporting,
                        context.getLocalizedString(ELEMENT_CODE),
                        locator.toString()),
                false);
    }

    private static String generateSpanLog(String message) {
        if (StringUtils.isNotEmpty(message)) {
            return String.format(HTML_BADGE_TEMPLATE, StringEscapeUtils.escapeHtml4(message));
        } else {
            return EMPTY_STRING;
        }
    }

    private static void doLog(TestContext context, Status status, String details, boolean takeScreenshot) {
        LOG.info(String.format(ADDING_EXTENT_REPORTS_ENTRY_TO_TEST,
                context.getExtentTest().getModel().getName(),
                details));

        if (takeScreenshot) {
            context.getExtentTest().log(status, details, MediaEntityBuilder.createScreenCaptureFromPath(takePageScreenshot(context)).build());
        } else {
            context.getExtentTest().log(status, details);
        }
    }

    /**
     * takes a screenshot and returns its path
     *
     * @param context
     * @return
     */
    private static String takePageScreenshot(TestContext context) {
        String currentTimeInMills = String.valueOf(System.currentTimeMillis());

        //looking for the unique file name
        File file;
        String newFileName;
        int counter = 1;
        do {
            newFileName = String.format(FILE_NAME_FORMAT, currentTimeInMills, counter);

            file = new File(ExtentManager.REPORT_PATH + SCREENSHOTS_DIR + newFileName);
            counter++;
        } while (file.exists());

        //taking a screenshot
        File src = ((TakesScreenshot) context.getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, file);
        } catch (IOException e) {
            fail(context,
                    String.format(KEY_VALUE_FORMAT,
                            context.getLocalizedString(ERROR_SCREENSHOT_FAILED),
                            e.getMessage()),
                    EMPTY_STRING,
                    false);
        }

        return SCREENSHOTS_DIR + newFileName;
    }


}
