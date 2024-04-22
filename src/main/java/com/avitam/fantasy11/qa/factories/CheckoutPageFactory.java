package com.avitam.fantasy11.qa.factories;

import com.avitam.fantasy11.qa.pages.abstractpages.CheckoutAbstractPage;
import com.avitam.fantasy11.qa.pages.concretepages.CheckoutPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CheckoutPageFactory {
    private final ApplicationContext context;

    @Autowired
    public CheckoutPageFactory(ApplicationContext context) {
        this.context = context;
    }

    public CheckoutAbstractPage getCheckoutPage() {
        return context.getBean(CheckoutPage.class);
    }
}
