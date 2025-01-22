package com.project.shoppingCart.dtos;

import lombok.Data;

@Data
public class CartRemoveDto {

    private boolean deleted = false;

    private int amount;

    private Long product_id;

    private Long cart_id;

}
