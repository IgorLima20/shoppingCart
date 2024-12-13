package com.project.shoppingCart.controllers;

import com.project.shoppingCart.dtos.CartCreateDto;
import com.project.shoppingCart.models.Cart;
import com.project.shoppingCart.models.CartItem;
import com.project.shoppingCart.services.CartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> findBySession(HttpSession httpSession) {
        Cart cart = this.cartService.findBySession(httpSession.getId());
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartItem> addCartItem(@RequestBody @Valid CartCreateDto cartItem, HttpSession httpSession) {
        Cart cart = this.cartService.findBySession(httpSession.getId());
        if (cart == null) {
            try {
              cart = this.cartService.addCart(httpSession.getId());
            } catch (Exception ex) {
                httpSession.invalidate();
                throw ex;
            }
        }
        cartItem.setCart(cart);
        CartItem newCartItem = this.cartService.addCartItem(cartItem);
        cart.setDateRegistry(LocalDateTime.now());
        this.cartService.updateCart(cart);
        return new ResponseEntity<CartItem>(newCartItem, HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<CartItem> deleteCartItem(@PathVariable Long id, HttpSession httpSession) {
        Cart cart = this.cartService.findBySession(httpSession.getId());
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        this.cartService.deleteCartItem(cart.getId(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
