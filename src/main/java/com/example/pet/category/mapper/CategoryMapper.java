package com.example.pet.category.mapper;

import com.example.pet.category.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    int insertCate(CategoryDto categoryDto);

    List<CategoryDto> cateList();
    int allCategoryCount();
}
