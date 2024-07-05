package com.example.pet.order.dto;

import com.example.pet.constant.OrderStatus;
import lombok.Data;

@Data
public class OrderDetailDto {
    private Long orderDetailNum;
    private Long orderNum;
    private Long itemNum;
    private int itemCnt;
    private int price;
    private OrderStatus orderStatus;
}
