package com.example.pet.item.dto;

import lombok.Data;

@Data
public class ItemDto {
    private Long itemNum;
    private String categoryName;
    private String itemName;
    private Integer itemPrice;
    private Integer itemCnt;
    private String itemImage;
    private String itemInfo;
}
