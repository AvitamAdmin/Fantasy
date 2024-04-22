package com.avitam.fantasy11.qa.testPlans;

import com.avitam.fantasy11.core.SpringContext;
import com.avitam.fantasy11.qa.factories.SamsungTestConfigPageFactory;
import com.avitam.fantasy11.qa.framework.ExtentManager;
import com.avitam.fantasy11.qa.framework.QaConfig;
import com.avitam.fantasy11.qa.framework.TestContext;
import com.avitam.fantasy11.qa.pages.abstractpages.SamsungTestConfigAbstractPage;
import com.avitam.fantasy11.qa.testPlans.interfaces.TestPlanInterface;
import com.avitam.fantasy11.qa.utils.ReportUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.avitam.fantasy11.qa.utils.CheilStringUtils.*;


@Component
public abstract class AbstractTestPlan implements TestPlanInterface {
	private static final Logger LOG = LoggerFactory.getLogger(QaConfig.class);
	protected Map<String, Object> testData;
	protected AtomicInteger index = SpringContext.getBean(AtomicInteger.class);//test counter. allows tests to get a unique id to be able to decide which SKUs to pick from a list of SKUs in the testData. Initial value is 0.
	private ExtentManager extentManager;
	private SamsungTestConfigPageFactory samsungTestConfigPageFactory;

	@BeforeSuite
	public void beforeSuite(ITestContext context) {
		 /* Converts a Map<String, String> to a Map<String, Collection<String>> based on specific rules:
		  - If the String value contains commas, indicating a list of items (e.g., "item1, item2, item3"),
		                   it is converted into a Collection<String> (specifically a List<String>), with each comma-separated
						   value as a separate list element.
		  - If the String value does not contain commas, it is converted into a Collection<String> containing
		                   a single element (the original String)*/
		testData = context.getSuite().getXmlSuite().getAllParameters().entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						entry -> Arrays.asList(entry.getValue().split(COMMA_SEPARATED_VALUES_REGEX))
				));
	}

	/**
	 * executes generic test steps:
	 * creating testContext
	 * starting reporting
	 * instantiating the driver
	 * saving test identifier
	 * getting cookies
	 */
	@Test
	public void genericTestSteps() {
		TestContext context = SpringContext.getBean(TestContext.class);
		try {
			context.setTestIdentifier(index.getAndIncrement());

			context.setExtentTest(getExtentManager().startNewTest(getTestName(context), ""));
			context.getExtentTest().assignCategory(this.getClass().getSimpleName());

			ReportUtils.info(context, context.getLocalizedString(INSTANTIATING_THE_BROWSER), false);
			context.setDriver(SpringContext.getBean(WebDriver.class));
			context.setWebDriverWait(SpringContext.getBean(WebDriverWait.class, context.getDriver()));
			LOG.info(INITIALIZED_DRIVER_AND_WAIT);
			ReportUtils.info(context, context.getLocalizedString(BROWSER_INSTANTIATED_SUCCESSFULLY), false);

			LOG.info(GETTING_INSTANCE_OF_SAMSUNG_TEST_CONFIG_PAGE);
			SamsungTestConfigAbstractPage samsungTestConfigPage = getSamsungTestConfigPageFactory().getSamsungTestConfigPage();
			LOG.info(SETTING_UP_COOKIES);
			samsungTestConfigPage.setUpCookies(context, testData);

			testPlanSpecificSteps(context);
		} catch (Exception e) {
			LOG.error(e.getMessage() + e + System.lineSeparator() + Arrays.toString(Thread.currentThread().getStackTrace()));
		} finally {
			afterTestSteps(context);
		}
	}


	protected void afterTestSteps(TestContext context) {
		context.getDriver().quit();
	}

	private SamsungTestConfigPageFactory getSamsungTestConfigPageFactory() {
		return samsungTestConfigPageFactory = getFactory(samsungTestConfigPageFactory, SamsungTestConfigPageFactory.class);
	}

	private ExtentManager getExtentManager() {
		return extentManager = getFactory(extentManager, ExtentManager.class);
	}

	protected <T> T getFactory(T factory, Class<T> clazz) {
		if (factory == null) {
			factory = SpringContext.getBean(clazz);
		}
		return factory;
	}

}
