package com.project.shoppingCart.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "cartItems")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="Necessário informar uma quantidade para o produto do carrinho.")
    @Column(nullable=false)
    private int amount;

    @Setter(value=AccessLevel.PRIVATE)
    @Column(nullable=false, precision=10)
    private double totalPrice;

    @NotNull(message="Necessário informar um produto para a operação.")
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    private Cart cart;

    @PrePersist
    @PreUpdate
    private void calculateTotalPrice() {
        this.totalPrice = this.product.getPrice() * this.amount;
    }

    public CartItem(int amount, Product product, Cart cart) {
        this.amount = amount;
        this.product = product;
        this.cart = cart;
    }

}
