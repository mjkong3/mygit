package com.example.admin.dto;

import lombok.Data;

@Data
public class AdminItemDto {
    private Integer itemNum;
    private String categoryName;
    private String itemName;
    private Integer itemPrice;
    private Integer itemCnt;
    private String itemImage;
    private String itemInfo;
}
