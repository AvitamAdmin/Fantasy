package com.avitam.fantasy11.qa.factories;

import com.avitam.fantasy11.qa.pages.abstractpages.CartAbstractPage;
import com.avitam.fantasy11.qa.pages.concretepages.CartPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CartPageFactory {
    private final ApplicationContext context;

    @Autowired
    public CartPageFactory(ApplicationContext context) {
        this.context = context;
    }

    public CartAbstractPage getCartPage() {
        return context.getBean(CartPage.class);
    }
}
