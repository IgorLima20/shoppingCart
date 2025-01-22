package com.project.shoppingCart.services;

import com.project.shoppingCart.dtos.CartCreateDto;
import com.project.shoppingCart.dtos.CartRemoveDto;
import com.project.shoppingCart.models.Cart;
import com.project.shoppingCart.models.CartItem;
import com.project.shoppingCart.models.Product;
import com.project.shoppingCart.repositories.CartItemRepository;
import com.project.shoppingCart.repositories.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductService productService;

    public Cart findBySession(String session) {
        return this.cartRepository.findBySession(session);
    }

    public CartItem findByCartIdAndProductId(Long idCart, Long idProduct) {
        return this.cartItemRepository.findByCartIdAndProductId(idCart, idProduct);
    }

    public Cart addCart(String session) {
        Cart cart = new Cart();
        cart.setSession(session);
        return this.cartRepository.save(cart);
    }

    public Cart updateCart(Cart cart) {
        return this.cartRepository.save(cart);
    }

    public CartItem addCartItem(CartCreateDto cartItem) {
        Product product = this.productService.findById(cartItem.getProduct_id());
        CartItem cartItemUpdate = this.findByCartIdAndProductId(cartItem.getCart().getId(), product.getId());
        if (cartItemUpdate != null) {
            cartItemUpdate.setAmount(cartItemUpdate.getAmount() + cartItem.getAmount());
            return this.cartItemRepository.save(cartItemUpdate);
        }
        CartItem cartItemNew = new CartItem(cartItem.getAmount(), product, cartItem.getCart());
        return this.cartItemRepository.save(cartItemNew);
    }

    public Optional<CartItem> removeCartItem(CartRemoveDto cartItemRem) {
        CartItem cartItem = this.findByCartIdAndProductId(cartItemRem.getCart_id(), cartItemRem.getProduct_id());
        if (cartItem == null) {
            throw new EntityNotFoundException("Carrinho não localizado!");
        }
        if (cartItemRem.isDeleted() || cartItem.getAmount() <= 0) {
            this.deleteCartItem(cartItemRem.getCart_id(), cartItemRem.getProduct_id());
            return Optional.empty();
        }
        cartItem.setAmount(cartItem.getAmount() - cartItemRem.getAmount());
        return Optional.of(this.cartItemRepository.save(cartItem));
    }

    public void deleteCartItem(Long idCart, Long idProduct) {
        CartItem cartItem = this.findByCartIdAndProductId(idCart, idProduct);
        this.cartItemRepository.deleteById(cartItem.getId());
    }

    public void deleteCartInactive() {
        List<Cart> cartList = this.cartRepository.findByCartInactive();
        for (Cart cart : cartList) {
            this.cartItemRepository.deleteByCartId(cart.getId());
            this.cartRepository.delete(cart);
        }
    }
}
