package com.example.pet.myPage.dto;

import com.example.pet.constant.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyPageDto {
    private LocalDateTime orderDate;
    private String custName;
    private String itemName;
    private String itemImage;
    private int itemCnt;
    private int price;
    private String address;
    private OrderStatus orderStatus;
    private Long orderNum;
    private Long orderDetailNum;
}
