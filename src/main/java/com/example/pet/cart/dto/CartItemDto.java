package com.example.pet.cart.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long cartItemNum;
    private Long cartNum;
    private Long itemNum;
    private int count;
}
