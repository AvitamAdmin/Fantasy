package com.avitam.fantasy11.qa.pages.abstractpages;

import com.avitam.fantasy11.qa.framework.TestContext;
import org.openqa.selenium.By;

import java.util.Map;

public abstract class BuyingConfiguratorAemAbstractPage {
    public static final By TRADE_IN_NO_BUTTON = By.cssSelector(".js-no-tradein .s-cta-text");
    public static final By SAMSUNG_CARE_PLUS_NO_BUTTON = By.cssSelector(".js-smc-none .s-option-name");
    public static final By ADD_TO_CART_FIXED_BUTTON_AT_BOTTOM_OF_MAIN_PAGE_OF_BUYING_CONFIGURATOR = By.cssSelector("[class=s-hubble-total-cta]");
    public static final By ADD_TO_CART_STICKY_BUTTON_AT_MAIN_PAGE_OF_BUYING_CONFIGURATOR = By.cssSelector("[class=hubble-price-bar__price-cta]");
    public static final By ADD_TO_CART_STICKY_BUTTON_AT_UPSELL_PAGE = By.cssSelector("[class='cta cta--contained cta--emphasis addon-continue-btn']");
    public static final By CUSTOMER_AGENT_MESSAGE_POPUP = By.cssSelector("[class='spr-chat__proactive-box spr-live-chat-frame css-fcb04n eonj1wj2']");

    public void openPage(TestContext context, Map<String, Object> testData, String urlEnding) {
    }

    public void refuseTradeIn(TestContext context) {
    }

    public void refuseSamsungCare(TestContext context) {
    }

    public void addToCartFixedButtonAtBCbottom(TestContext context) {
    }

    public void addToCartStickyButtonAtUpsellingPage(TestContext context) {
    }

    public void closeCustomerAgentMessagePopupAtBottomRightCorner(TestContext context) {
    }

    public void retrieveFieldFromPage(TestContext context, String field) {
    }

    public enum Field {
        CHRISTMAS_BANNER("#chr-banner"),
        NEW_YEAR_BANNER("#new-year-banner");

        private final String field;

        Field(String field) {
            this.field = field;
        }

        public String toString() {
            return field;
        }
    }
}
