package com.project.shoppingCart.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="A descrição do produto não pode ser vazia.")
    @Size(min=1, max=100, message="A descrição precisa ter entre 1 a 100 caracteres.")
    @Column(nullable=false, length=100)
    private String descriptionProduct;

    @NotNull(message="Necessário informar um preço para o produto.")
    @Column(nullable=false, precision=10)
    private double price;

    @OneToMany(mappedBy="product")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<CartItem> cartItems;

}
