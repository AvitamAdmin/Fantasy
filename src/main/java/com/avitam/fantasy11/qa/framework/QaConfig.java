package com.avitam.fantasy11.qa.framework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
import com.avitam.fantasy11.qa.utils.TestDataUtils;
import com.avitam.fantasy11.qa.utils.TestDataUtils.Field;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.avitam.fantasy11.qa.utils.CheilStringUtils.*;

@Configuration
public class QaConfig {
    Logger LOG = LoggerFactory.getLogger(QaConfig.class);

    @Value("${testng.webdriver.wait.for.element.seconds}")
    int webDriverWaitDuration;

    @Autowired
    private MessageSource messageSource;

    @Bean
    @Scope("prototype")
    public TestNG xmlSuite(String testPlanClassName, HashMap<String, String> testData) {

        XmlSuite suite = new XmlSuite();
        suite.setName("MyTestSuite");
        suite.setParameters(testData);

        XmlTest test = new XmlTest(suite);
        test.setName("aTestName");

        XmlClass testClass = new XmlClass(testPlanClassName);
        test.getClasses().add(testClass);

        TestNG testNG = new TestNG();
        testNG.setXmlSuites(Arrays.asList(suite));
        int invocationCount = TestDataUtils.getInt(testData, Field.TEST_COUNT);
        int maxThreadCount = TestDataUtils.getInt(testData, Field.MAX_THREAD_COUNT);

        if (invocationCount < 0 || invocationCount > 100000) {
            LOG.error(String.format(INVOCATION_COUNT_INVALID_ERROR, invocationCount));
            invocationCount = 1;
        }
        if (maxThreadCount < 0 || maxThreadCount > 10000) {
            LOG.error(String.format(MAX_THREAD_COUNT_INVALID_ERROR, maxThreadCount));
            maxThreadCount = 2;
        }
        // TODO check how to use invocation count in latest version.
        // testNG.setAnnotationTransformer(new AnnotationTransformer(invocationCount, maxThreadCount));
        testNG.setThreadCount(maxThreadCount);
        return testNG;
    }

    @Bean
    @Scope("prototype")
    @Profile("dev")
    public WebDriver webDriverDev() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments(CHROME_OPTIONS_REMOTE_ALLOW_ORIGINS);
        options.addArguments(CHROME_OPTIONS_DISABLE_GPU);
        options.addArguments(CHROME_OPTIONS_WINDOW_SIZE);
        options.addArguments(CHROME_OPTIONS_INCOGNITO);
        options.addArguments(CHROME_OPTIONS_DISABLE_DEV_SHM_USAGE);
        options.addArguments(CHROME_OPTIONS_DISABLE_POPUP_BLOCKING);
        return new ChromeDriver(options);
    }

    @Bean
    @Scope("prototype")
    @Profile("stage")
    public WebDriver webDriverStaged() {
        LOG.info(WEB_DRIVER_MANAGER_SETUP);
        WebDriverManager.chromedriver().setup();
        LOG.info(WEB_DRIVER_MANAGER_SETUP_COMPLETED);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(CHROME_OPTIONS_NO_SANDBOX);
        chromeOptions.addArguments(CHROME_OPTIONS_REMOTE_ALLOW_ORIGINS);
        chromeOptions.addArguments(CHROME_OPTIONS_HEADLESS);
        chromeOptions.addArguments(CHROME_OPTIONS_REMOTE_DEBUGGING_PORT);
        chromeOptions.addArguments(CHROME_OPTIONS_DISABLE_DEV_SHM_USAGE);
        chromeOptions.addArguments(CHROME_OPTIONS_DISABLE_GPU);
        chromeOptions.addArguments(CHROME_OPTIONS_INCOGNITO);
        chromeOptions.addArguments(CHROME_OPTIONS_DISABLE_POPUP_BLOCKING);
        chromeOptions.setBinary(CHROME_OPTIONS_BINARY);
        chromeOptions.addArguments(CHROME_OPTIONS_WINDOW_SIZE);
        LOG.info(CREATING_CHROME_DRIVER_INSTANCE);
        WebDriver driver = new ChromeDriver(chromeOptions);
        LOG.info(CHROME_DRIVER_INSTANCE_CREATED);
        return driver;
    }

    @Bean
    @Scope("prototype")
    public WebDriverWait webDriverWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(webDriverWaitDuration));
    }

    @Bean
    @Scope("prototype")
    public ExtentReports extentReports(String reportPath) {
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

        ExtentSparkReporterConfig config = spark.config();
        config.setDocumentTitle(messageSource.getMessage(EXTENT_REPORT_TITLE, null, LocaleContextHolder.getLocale()));
        config.setTimeStampFormat(EXTENT_REPORT_TIMESTAMP_FORMAT);
        config.setJs(EXTENT_REPORT_CUSTOM_JS_CODE);

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(spark);

        return extentReports;
    }

    @Bean
    @Scope("prototype")
    public AtomicInteger atomicInteger() {
        return new AtomicInteger();
    }
}
