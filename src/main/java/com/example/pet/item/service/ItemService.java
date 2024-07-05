package com.example.pet.item.service;

import com.example.pet.category.dto.CategoryDto;
import com.example.pet.config.PageHandler;
import com.example.pet.item.dto.ItemDto;
import com.example.pet.item.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;
    private final PageHandler ph;


    public List<ItemDto> indexItem(){
        return itemMapper.indexItem();
    };

    public int insertItem(ItemDto itemDto){
        return itemMapper.insertItem(itemDto);
    }


    //전체 상품 목록 페이징 처리
    public List<ItemDto> itemAll(int pageSize, int offset){
        return itemMapper.itemAll(pageSize, offset);
    }
    public int itemAllCount(){
        return itemMapper.itemAllCount();
    }

    //카테고리별 상품 목록 페이징 처리
    public List<ItemDto> selectCate(String categoryName, int pageSize, int offset){
        return itemMapper.selectCate(categoryName, pageSize, offset);
    };
    public int selectCateCount(String categoryName){
        return itemMapper.selectCateCount(categoryName);
    };


    public ItemDto selectItem(Long num){
        return itemMapper.selectItem(num);
    }

    public int updateCnt(ItemDto itemDto){
        return itemMapper.updateCnt(itemDto);
    }

    public List<ItemDto> selectCate(String categoryName){
        return itemMapper.selectCate(categoryName);
    }

    //검색어
    public List<ItemDto> searchWord(String searchWord, int pageSize, int offset){
        System.out.println("상품 검색 서비스 실행");
        return itemMapper.searchItem(searchWord, pageSize, offset);
    };
    public int searchWordCount(String searchWord){
        return itemMapper.searchItemCount(searchWord);
    }
}
