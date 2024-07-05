package com.example.pet.item.mapper;

import com.example.pet.category.dto.CategoryDto;
import com.example.pet.item.dto.ItemDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Mapper
public interface ItemMapper {

    List<ItemDto> indexItem();
    int insertItem(ItemDto itemDto);

    List<ItemDto> itemAll(@Param("pageSize") int pageSize, @Param("offset") int offset);
    int itemAllCount();

    ItemDto selectItem(Long num);

    int updateCnt(ItemDto itemDto);

    List<ItemDto> selectCate(String categoryName);

    //검색어
    List<ItemDto> searchItem(@Param("searchWord") String searchWord, @Param("pageSize") int pageSize, @Param("offset") int offset);
    int searchItemCount(String searchWord);



    List<ItemDto> selectCate(@Param("categoryName") String categoryName, @Param("pageSize") int pageSize, @Param("offset") int offset);
    int selectCateCount(@Param("categoryName") String categoryName);
}
