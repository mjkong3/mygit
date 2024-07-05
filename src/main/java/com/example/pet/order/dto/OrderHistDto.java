package com.example.pet.order.dto;

import lombok.Data;

@Data
public class OrderHistDto {
    private String itemName;
    private String itemImage;
    private int itemPrice;
    private int itemCnt;
    private String custName;
    private String custTel;
    private String address;
}
