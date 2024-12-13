package com.project.shoppingCart.Schedulers;

import com.project.shoppingCart.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CartScheduler {

    @Autowired
    private CartService cartService;

    @Scheduled(fixedDelay = 600000)
    public void cartInactive() {
        this.cartService.deleteCartInactive();
    }

}
