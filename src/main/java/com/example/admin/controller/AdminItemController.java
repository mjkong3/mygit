package com.example.admin.controller;

import com.example.admin.dto.AdminItemDto;
import com.example.admin.service.AdminCategoryService;
import com.example.admin.service.AdminItemService;
import com.example.pet.category.dto.CategoryDto;
import com.example.pet.category.service.CategoryService;
import com.example.pet.config.PageHandler;
import com.example.pet.item.form.ItemForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminItemController {


    @Value("${itemImgLocation}")
    private String itemImgLocation;

    @Autowired
    private AdminItemService itemService;

    @Autowired
    private AdminCategoryService adminCategoryService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/addProductAdd")
    public String addProduct(Model model){
        // 카테고리
        List<CategoryDto> category = categoryService.cateList();
        model.addAttribute("category", category);

        // 폼 객체 추가
        model.addAttribute("product", new ItemForm());
        return "admin/addProductAdd";
    }

    @PostMapping("/addProductAdd")
    public String addProducts(@Valid ItemForm itemForm, BindingResult bindingResult,
                              Model model, Principal principal) {

        if (principal == null) {
            model.addAttribute("loginErrorMsg",
                                "로그인 후 사용가능합니다.");
            return "/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage",
                                "상품 등록 필드를 모두 입력해야합니다!");
            model.addAttribute("product", itemForm);
            return "admin/addProductAdd";
        }

        try {
            AdminItemDto itemDto = new AdminItemDto();
            itemDto.setCategoryName(itemForm.getCategoryName());
            itemDto.setItemName(itemForm.getItemName());
            itemDto.setItemPrice(itemForm.getPrice());
            itemDto.setItemCnt(itemForm.getCount());
            itemDto.setItemInfo(itemForm.getItemDetail());

            MultipartFile itemImgFile = itemForm.getItemImgFile();
            if (!itemImgFile.isEmpty()) {
                String originalFilename = itemImgFile.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String savedFilename = UUID.randomUUID().toString() + extension;
                String filePath = itemImgLocation + "/" + savedFilename;
                File dir = new File(itemImgLocation);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                try {
                    itemImgFile.transferTo(new File(filePath));
                    itemDto.setItemImage("/images/item/" + savedFilename);
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("errorMessage",
                                        "파일 저장 중 오류가 발생했습니다.");
                    model.addAttribute("product", itemForm);
                    return "admin/addProductAdd";
                }
            }
            itemService.insertItem(itemDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                                "상품 등록 중 오류가 발생했습니다.");
            model.addAttribute("product", itemForm);
            return "admin/addProductAdd";
        }
        return "redirect:/addProduct";
    }




    // 상품 삭제
    @PostMapping("/deleteItem")
    @ResponseBody
    public ResponseEntity<String> deleteItem(@RequestParam("itemNum") int itemNum) {
        try {
            itemService.deleteItem(itemNum);
            return ResponseEntity.ok("삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
        }
    }

    // 상품 검색
    @GetMapping("/addProduct")
    public String searchProduct(
            @RequestParam(value = "searchField", required = false) String searchField,
            @RequestParam(value = "searchValue", required = false) String searchValue,
            @RequestParam(value = "categoryName", required = false) String categoryName,
            @RequestParam(value = "minStock", required = false) Integer minStock,
            @RequestParam(value = "maxStock", required = false) Integer maxStock,
            @RequestParam(value = "minPrice", required = false) Integer minPrice,
            @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            Principal principal,
            Model model) {

        if (principal == null) {
            model.addAttribute("loginErrorMsg","로그인 후 사용가능합니다.");
            return "/login";
        }

        // 카테고리
        List<CategoryDto> category = categoryService.cateList();
        model.addAttribute("category", category);

        int totalCnt = itemService.searckItemsCount(searchField, searchValue, categoryName, minStock, maxStock, minPrice, maxPrice);
        int offset = (page - 1) * pageSize;


        // 검색 조건에 따라 상품 리스트 조회
        List<AdminItemDto> item = itemService.searchItems(searchField, searchValue, categoryName, minStock, maxStock, minPrice, maxPrice, pageSize, offset);

        PageHandler pageHandler = new PageHandler(totalCnt, pageSize, page);

        model.addAttribute("searchField",searchField);
        model.addAttribute("searchValue",searchValue);
        model.addAttribute("categoryName",categoryName);
        model.addAttribute("minStock",minStock);
        model.addAttribute("maxStock",maxStock);
        model.addAttribute("minPrice",minPrice);
        model.addAttribute("maxPrice",maxPrice);
        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("item", item);

        return "admin/addProduct";
    }



}
