package com.example.pet.category.service;

import com.example.pet.category.dto.CategoryDto;
import com.example.pet.category.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;

    public int insertCate(CategoryDto categoryDto){

        int result = categoryMapper.insertCate(categoryDto);
        return result;
    }

    public List<CategoryDto> cateList(){
        return categoryMapper.cateList();
    };
}
