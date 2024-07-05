package com.example.admin.service;


import com.example.admin.dto.AdminItemDto;
import com.example.admin.mapper.AdminItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminItemService {

    private final AdminItemMapper itemMapper;

    //제품 등록
    public int insertItem(AdminItemDto itemDto){
        return itemMapper.insertItem(itemDto);
    }

    //전체 제품 조회
    public List<AdminItemDto> itemListAll() {
        return itemMapper.itemListAll();
    }

    //제품 삭제
    public void deleteItem(int itemNum) {
        itemMapper.deleteItem(itemNum);
    }

    //제품 검색
    public List<AdminItemDto> searchItems(String searchField, String searchValue,
                                    String categoryName, Integer minStock,
                                    Integer maxStock, Integer minPrice, Integer maxPrice,
                                          int pageSize,int offset) {
        return itemMapper.searchItems(searchField, searchValue, categoryName, minStock, maxStock, minPrice, maxPrice, pageSize, offset);
    }

    public int searckItemsCount(String searchField, String searchValue,
                                String categoryName, Integer minStock,
                                Integer maxStock, Integer minPrice, Integer maxPrice){
        return itemMapper.searchItemsCount(searchField, searchValue, categoryName, minStock, maxStock, minPrice, maxPrice);
    }

}
