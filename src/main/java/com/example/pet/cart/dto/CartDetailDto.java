package com.example.pet.cart.dto;

import lombok.Data;

@Data
public class CartDetailDto {
    private Long cartItemNum;
    private Long cartNum;
    private String itemName;
    private String itemImage;
    private int itemPrice;
    private int count;
    private Long itemNum;
    private String custName;
}
