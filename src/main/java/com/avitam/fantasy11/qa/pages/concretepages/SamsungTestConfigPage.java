package com.avitam.fantasy11.qa.pages.concretepages;

import com.avitam.fantasy11.qa.framework.TestContext;
import com.avitam.fantasy11.qa.pages.abstractpages.SamsungTestConfigAbstractPage;
import com.avitam.fantasy11.qa.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.avitam.fantasy11.qa.utils.CheilStringUtils.*;
import static com.avitam.fantasy11.qa.utils.OpenPageUtils.constructUrl;
import static com.avitam.fantasy11.qa.utils.TestDataUtils.Field;

@Component
public class SamsungTestConfigPage extends SamsungTestConfigAbstractPage {

    private static final By POPUP_CONSENT_BUTTON = By.id("truste-consent-button");
    private static final By STICKY_CONSENT_BUTTON = By.cssSelector("button.js-accept-consent");
    private static final By AEM_USERNAME_TEXT_FIELD = By.id("username");
    private static final By AEM_PASSWORD_TEXT_FIELD = By.id("password");
    private static final By AEM_LOGIN_SUBMIT_BUTTON = By.id("submit-button");
    @Value("${cookie.url}")
    private String cookieUrl;
    @Value("${aem.url}")
    private String AEM_URL;
    @Value("${connect.hybris.aem.url}")
    private String CONNECT_HYBRIS_AND_AEM_ENVIRONMENTS_URL;
    @Value("${aem.home.page}")
    private String AEM_HOME_PAGE;
    @Value("${hyb.home.page}")
    private String HYB_HOME_PAGE;

    // TODO: Implement multiple beans for SamsungTestConfigPage.java, each with a unique setUpCookies method.
    // The selection of these beans should be based on the environment profile.
    // This approach will eliminate the need for if-else conditions in the code.
    // NOTE: Current code should remain unchanged at this moment. After the implementation of profiles by Sri/Raja,
    // a refactoring of this section will be undertaken by Vladimirs.
    @Override
    public void setUpCookies(TestContext context, Map<String, Object> testData) {
        String hybEnv = TestDataUtils.getString(testData, Field.HYBRIS_ENVIRONMENT);
        String aemEnv = TestDataUtils.getString(testData, Field.AEM_ENVIRONMENT);
        if (!StringUtils.equals(hybEnv, TestDataUtils.PRD)) {
            //get the cookie
            OpenPageUtils.openUrl(context, constructUrl(hybEnv, cookieUrl, Field.HYBRIS_ENVIRONMENT.toString()), URL_TO_GET_COOKIES);

            //log in to AEM
            OpenPageUtils.openUrl(context, constructUrl(aemEnv, AEM_URL, Field.AEM_ENVIRONMENT.toString()), AEM_LOGIN_PAGE);
            FillUtils.enterText(context, AEM_USERNAME_TEXT_FIELD, "qauser01", ENTERING_AEM_USERNAME);//enter the actual username and password here
            FillUtils.enterText(context, AEM_PASSWORD_TEXT_FIELD, "samsungqa", ENTERING_AEM_PASSWORD);
            ClickUtils.click(context, AEM_LOGIN_SUBMIT_BUTTON, CLICKING_SUBMIT_BUTTON);
            WaitUtils.waitForTextToBeVisible(context, VISIT_YOUR_LOCATION);

            //connect Hybris and AEM
            OpenPageUtils.openUrl(context, getConnectHybrisAndAemEnvironmentsUrl(aemEnv, hybEnv), URL_THAT_CONNECTS_HYBRIS_AND_AEM);

            //open Hybris page and click the consent button
            OpenPageUtils.openUrl(context, constructUrl(hybEnv, HYB_HOME_PAGE, Field.HYBRIS_ENVIRONMENT.toString()), HYBRIS_HOME_PAGE_LABEL);
            ClickUtils.click(context, POPUP_CONSENT_BUTTON, CLICKING_POPUP_CONSENT_BUTTON_ON_HYBRIS_PAGE);
            ClickUtils.click(context, STICKY_CONSENT_BUTTON, CLICKING_STICKY_CONSENT_BUTTON_AT_THE_BOTTOM_OF_HYBRIS_PAGE);
        } else {
            //open AEM home page and click the consent button
            OpenPageUtils.openUrl(context, constructUrl(aemEnv, AEM_HOME_PAGE, Field.AEM_ENVIRONMENT.toString()), AEM_HOME_PAGE_LABEL);
            ClickUtils.click(context, POPUP_CONSENT_BUTTON, CLICKING_POPUP_CONSENT_BUTTON_ON_AEM);
        }
    }

    public String getConnectHybrisAndAemEnvironmentsUrl(String aemEnv, String hybEnv) {
        return CONNECT_HYBRIS_AND_AEM_ENVIRONMENTS_URL.replace(Field.HYBRIS_ENVIRONMENT.toString(), hybEnv).replace(Field.AEM_ENVIRONMENT.toString(), aemEnv);
    }
}
