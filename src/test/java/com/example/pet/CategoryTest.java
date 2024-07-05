package com.example.pet;

import com.example.pet.category.dto.CategoryDto;
import com.example.pet.category.mapper.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CategoryTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void insertCate(){
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setCategoryName("외출,놀이,훈련");

        int result = categoryMapper.insertCate(categoryDto);
        assertThat(result).isEqualTo(1);
    }
}
