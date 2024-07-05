package com.example.admin.service;

import com.example.admin.dto.AdminCategoryDto;
import com.example.admin.mapper.AdminCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCategoryService {

    @Autowired
    private AdminCategoryMapper categoryMapper;

    //카테고리 등록
    public int inCategory(String categoryName) {
        return categoryMapper.createCategory(categoryName);
    }

    //카테고리 전체 조회
    public List<AdminCategoryDto> allCategory(int pageSize, int offset) {
        return categoryMapper.allCategory(pageSize, offset);
    }
    public int allCategoryCount(){
        return categoryMapper.allCategoryCount();
    }

    //카테고리 삭제
    public void deleteCategory(String categoryName) {
        categoryMapper.deleteCategory(categoryName);
    }

//    //카테고리 수정
//    public void updateCategory(String oldCategoryName, String newCategoryName) {
//        categoryMapper.updateCategory(oldCategoryName, newCategoryName);
//    }

}
