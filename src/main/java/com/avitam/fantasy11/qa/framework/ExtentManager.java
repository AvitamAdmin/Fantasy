package com.avitam.fantasy11.qa.framework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.avitam.fantasy11.core.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.avitam.fantasy11.qa.utils.CheilStringUtils.STARTING_NEW_REPORT;
import static com.avitam.fantasy11.qa.utils.CheilStringUtils.STARTING_NEW_REPORT;

@Component
public class ExtentManager {
    public static final String REPORT_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
            + "resources" + File.separator + "static" + File.separator + "reports" + File.separator;
    private static Logger LOG = LoggerFactory.getLogger(ExtentManager.class);
    private ExtentReports extentReports;

    public void startNewReport(String reportName) {
        flush();
        LOG.info(String.format(STARTING_NEW_REPORT, REPORT_PATH, reportName));
        extentReports = SpringContext.getBean(ExtentReports.class, REPORT_PATH + reportName);
    }

    public void flush() {
        if (extentReports != null) {
            extentReports.flush();
            extentReports = null; // Set to null so that a new instance is created next time
        }
    }

    public ExtentTest startNewTest(String testName, String testDescription) {
        return extentReports.createTest(testName, testDescription);
    }
}

