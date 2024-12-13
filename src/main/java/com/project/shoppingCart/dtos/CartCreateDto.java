package com.project.shoppingCart.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shoppingCart.models.Cart;
import lombok.Data;

@Data
public class CartCreateDto {

    private int amount;

    private Long product_id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cart cart;

}
