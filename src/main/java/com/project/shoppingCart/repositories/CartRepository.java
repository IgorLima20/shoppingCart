package com.project.shoppingCart.repositories;

import com.project.shoppingCart.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findBySession(String session);

    @Query(value = "SELECT c.* FROM carts c WHERE c.date_registry <= NOW() - INTERVAL 1 DAY",
           nativeQuery = true)
    List<Cart> findByCartInactive();

}
