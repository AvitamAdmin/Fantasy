package com.avitam.fantasy11.qa.factories;

import com.avitam.fantasy11.qa.pages.abstractpages.BuyingConfiguratorAemAbstractPage;
import com.avitam.fantasy11.qa.pages.concretepages.BuyingConfiguratorAemSmartphonePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BuyingConfiguratorAemPageFactory {

    private final ApplicationContext context;

    @Autowired
    public BuyingConfiguratorAemPageFactory(ApplicationContext context) {
        this.context = context;
    }

    public BuyingConfiguratorAemAbstractPage getBuyingConfiguratorAemPage() {
        return context.getBean(BuyingConfiguratorAemSmartphonePage.class);
    }
}
