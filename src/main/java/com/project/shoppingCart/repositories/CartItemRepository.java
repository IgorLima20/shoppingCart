package com.project.shoppingCart.repositories;

import com.project.shoppingCart.models.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndProductId(long idCart, long idProduct);

    @Transactional
    @Modifying
    void deleteByCartId(long idCart);

}
