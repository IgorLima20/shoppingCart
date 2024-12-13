package com.project.shoppingCart.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=100, unique=true)
    private String session;

    @UpdateTimestamp
    @Column(nullable=false)
    private LocalDateTime dateRegistry;

    @Setter(AccessLevel.PRIVATE)
    @Column(nullable=false, precision=10)
    private double totalCart;

    @OneToMany(mappedBy="cart")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<CartItem> cartItems;

    @PrePersist
    @PreUpdate
    private void calculateTotalCart() {
        double totalItems = 0;
        List<CartItem> cartItems = this.getCartItems();
        if (cartItems != null) {
            for (CartItem cartItem : cartItems) {
                totalItems = totalItems + cartItem.getTotalPrice();
            }
        }
        this.totalCart = totalItems;
    }

}
