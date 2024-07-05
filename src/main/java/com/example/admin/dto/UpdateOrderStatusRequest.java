package com.example.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateOrderStatusRequest {
    private List<Long> orderDetailNums;
    private String orderStatus;
}