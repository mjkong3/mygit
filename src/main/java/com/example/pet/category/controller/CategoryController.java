package com.example.pet.category.controller;


import com.example.pet.category.dto.CategoryDto;
import com.example.pet.category.service.CategoryService;
import com.example.pet.config.PageHandler;
import com.example.pet.item.dto.ItemDto;
import com.example.pet.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ItemService itemService;
    private final PageHandler ph;

//    @GetMapping("/")
//    public String cateAll(Model model){
//        List<CategoryDto> list = categoryService.cateList();
//        model.addAttribute("cateList", list);
//        return "/fragments/header";
//    };


    //카테고리 별 상품 목록
    @GetMapping("/categoryItem/{categoryName}")
    public String cateItems(@PathVariable("categoryName") String categoryName,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "6") int pageSize,
                            Model model){
        //헤더의 카테고리 목록을 출력할 리스트
        List<CategoryDto> categories = categoryService.cateList();
        model.addAttribute("categories", categories);

        //페이징 처리
        int totalCnt;
        List<ItemDto> cateItems;
        int offset = (page - 1) * pageSize;

        //카테고리 별 상품 목록
        if(categoryName.equals("ALL")){
            totalCnt = itemService.itemAllCount();
            cateItems = itemService.itemAll(pageSize, offset);
        } else {
            totalCnt = itemService.selectCateCount(categoryName);
            cateItems = itemService.selectCate(categoryName, pageSize, offset);
        }

        PageHandler pageHandler = new PageHandler(totalCnt, pageSize, page);
        model.addAttribute("pageHandler", pageHandler);

        model.addAttribute("categoryItems", cateItems);
        model.addAttribute("categoryName", categoryName);

        return "categoryItem";
    }
}
