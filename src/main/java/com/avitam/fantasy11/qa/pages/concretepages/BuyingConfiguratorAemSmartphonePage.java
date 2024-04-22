package com.avitam.fantasy11.qa.pages.concretepages;

import com.avitam.fantasy11.qa.framework.TestContext;
import com.avitam.fantasy11.qa.pages.abstractpages.BuyingConfiguratorAemAbstractPage;
import com.avitam.fantasy11.qa.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static avitam.fantasy11.qa.utils.CheilStringUtils.*;
import static avitam.fantasy11.qa.utils.OpenPageUtils.constructUrl;

@Component
public class BuyingConfiguratorAemSmartphonePage extends BuyingConfiguratorAemAbstractPage {
    @Value("${buying.configurator.aem.smartphone.page.url}")
    private String buyingConfiguratorAemSmartphonePageUrl;

    @Override
    public void openPage(TestContext context, Map<String, Object> testData, String urlEnding) {
        String url = constructUrl(TestDataUtils.getString(testData, TestDataUtils.Field.AEM_ENVIRONMENT), buyingConfiguratorAemSmartphonePageUrl, TestDataUtils.Field.AEM_ENVIRONMENT.toString()) + urlEnding;
        OpenPageUtils.openUrl(context, url, BUYING_CONFIGURATOR_AEM_PAGE_NAME);
    }

    @Override
    public void refuseTradeIn(TestContext context) {
        ClickUtils.click(context, TRADE_IN_NO_BUTTON, CLICKING_BUTTON_TO_OPT_OUT_OF_TRADEIN);
    }

    @Override
    public void refuseSamsungCare(TestContext context) {
        ClickUtils.click(context, SAMSUNG_CARE_PLUS_NO_BUTTON, CLICKING_BUTTON_TO_OPT_OUT_OF_SAMSUNG_CARE);
    }

    @Override
    public void addToCartFixedButtonAtBCbottom(TestContext context) {
        ClickUtils.click(context, ADD_TO_CART_FIXED_BUTTON_AT_BOTTOM_OF_MAIN_PAGE_OF_BUYING_CONFIGURATOR, CLICKING_ADD_TO_CART_FIXED_BUTTON);
        ReportUtils.info(context, EMPTY_STRING, true);
    }

    @Override
    public void addToCartStickyButtonAtUpsellingPage(TestContext context) {
        ClickUtils.click(context, ADD_TO_CART_STICKY_BUTTON_AT_UPSELL_PAGE, CLICKING_ADD_TO_CART_STICKY_BUTTON_AT_UPSELL_PAGE);
        ReportUtils.info(context, EMPTY_STRING, true);
    }

    @Override
    public void closeCustomerAgentMessagePopupAtBottomRightCorner(TestContext context) {
        JsUtils.removeElementFromPage(context, CUSTOMER_AGENT_MESSAGE_POPUP, CLOSING_CUSTOMER_SERVICE_AGENT_MESSAGE_POPUP);
        ReportUtils.info(context, EMPTY_STRING, true);
    }
}
