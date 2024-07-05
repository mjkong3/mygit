package com.example.admin.controller;


import com.example.admin.dto.AdminCategoryDto;
import com.example.admin.dto.AdminCustomerDto;
import com.example.admin.dto.AdminOrderDto;
import com.example.admin.dto.UpdateOrderStatusRequest;
import com.example.admin.service.AdminCategoryService;
import com.example.admin.service.AdminCustomerService;
import com.example.admin.service.AdminOrderService;
import com.example.pet.category.dto.CategoryDto;
import com.example.pet.category.service.CategoryService;
import com.example.pet.config.PageHandler;
import com.example.pet.constant.Role;
import com.example.pet.customer.dto.CustomerDto;
import com.example.pet.customer.dto.CustomerTotalPriceDto;
import com.example.pet.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private AdminCategoryService adminCategoryService;

    @Autowired
    private AdminCustomerService adminCustomerService;


    @Autowired
    private AdminOrderService orderService;

    @Autowired
    private CustomerService custService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomerService customerService;


    //메인 관리자페이지
    @GetMapping("/admin")
    public String adminAdminForm(Principal principal,
                                 Model model) {
        if (principal == null) {
            model.addAttribute("loginErrorMsg","로그인 후 사용가능합니다.");
            return "login";
        }
        CustomerDto dto = customerService.loginCustomer(principal.getName());
        if (! dto.getRole().equals(Role.ADMIN)) {
            model.addAttribute("loginErrorMsg","관리자만 사용가능합니다.");
            return "login";
        }

        return "admin/admin";
    }

    //카테고리 전체 조회
    @GetMapping("/manageCategory")
    public String manageCategory(Model model,
                                 Principal principal,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int pageSize) {
        if (principal == null) {
            model.addAttribute("loginErrorMsg","로그인 후 사용가능합니다.");
            return "login";
        }
        CustomerDto dto = customerService.loginCustomer(principal.getName());
        if (! dto.getRole().equals(Role.ADMIN)) {
            model.addAttribute("loginErrorMsg","관리자만 사용가능합니다.");
            return "login";
        }

        List<CategoryDto> categorys = categoryService.cateList();
        model.addAttribute("categories", categorys);

        int totalCnt = adminCategoryService.allCategoryCount();
        int offset = (page - 1) * pageSize;
        List<AdminCategoryDto> category = adminCategoryService.allCategory(pageSize,offset);
        PageHandler pageHandler = new PageHandler(totalCnt, pageSize, page);

        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("category", category);


        return "admin/manageCategory";
    }

    //카테고리 추가하기
    @PostMapping("/manageCategory")
    public String addCategory(@RequestParam("categoryName") String categoryName,
                              Principal principal,
                              Model model) {

        try {
            adminCategoryService.inCategory(categoryName);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "카테고리 등록 중에 에러 발생");
            return "admin/manageCategory";

        }
        return "redirect:/manageCategory";

    }

    // 카테고리 삭제
    @PostMapping("/deleteCategory")
    @ResponseBody
    public ResponseEntity<String> deleteCategory(@RequestParam("categoryName") String categoryName) {

        try {
            adminCategoryService.deleteCategory(categoryName);
            return ResponseEntity.ok("삭제 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
        }
    }

    //회원관리 검색 + 전체 조회
    @GetMapping("/manageCustomer")
    public String searchAllCust(@RequestParam(value = "searchField", required = false) String searchField,
                                @RequestParam(value = "searchValue", required = false) String searchValue,
                                @RequestParam(value = "gradeName", required = false) String gradeName,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int pageSize,
                                Principal principal,
                                Model model) {
        List<CategoryDto> categorys = categoryService.cateList();
        model.addAttribute("categories", categorys);
        if (principal == null) {
            model.addAttribute("loginErrorMsg","로그인 후 사용가능합니다.");
            return "login";
        }

        CustomerDto dto = customerService.loginCustomer(principal.getName());
        if (! dto.getRole().equals(Role.ADMIN)) {
            model.addAttribute("loginErrorMsg","관리자만 사용가능합니다.");
            return "login";
        }


        int totalCnt = adminCustomerService.searchCustomerCount(searchField, searchValue, gradeName);
        int offset = (page - 1) * pageSize;

        List<AdminCustomerDto> cust = adminCustomerService.searchCustomer(searchField, searchValue, gradeName, pageSize, offset);

        PageHandler pageHandler = new PageHandler(totalCnt, pageSize, page);

        model.addAttribute("searchField", searchField);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("gradeName", gradeName);
        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("cust", cust);
        return "admin/manageCustomer";
    }

    // 유저 삭제
    @PostMapping("/deleteCustomer")
    @ResponseBody
    public ResponseEntity<String> deleteCustomer(@RequestParam("custNum") int custNum) {
        try {
            adminCustomerService.deleteCust(custNum);
            return ResponseEntity.ok("삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
        }
    }


//    //전체 주문 리스트
//    @GetMapping("/manageOrder")
//    public String orderListAllView(Model model) {
//
//        List<AdminOrderListDto> order = orderService.orderListAll();
//        model.addAttribute("orders", order);
//
//        return "admin/manageOrder";
//    }

    //주문 검색
    @GetMapping("/manageOrder")
    public String orderListAllView(
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "orderStatus", required = false) String orderStatus,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            Principal principal,
            Model model) {

        List<CategoryDto> categories = categoryService.cateList();
        model.addAttribute("categories", categories);

        if (principal == null) {
            model.addAttribute("loginErrorMsg","로그인 후 사용가능합니다.");
            return "/login";
        }

        int totalCnt = orderService.searchOrdersCount(searchType, searchKeyword, startDate,
                                                        endDate, orderStatus);
        int offset = (page - 1) * pageSize;
        List<AdminOrderDto> orders = orderService.getOrders(searchType, searchKeyword, startDate,
                                                            endDate, orderStatus, pageSize, offset);

        PageHandler pageHandler = new PageHandler(totalCnt, pageSize, page);
        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("orders", orders);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("orderStatus", orderStatus);

        return "admin/manageOrder";
    }

    //주문상태 변경
    @PostMapping("/updateOrderStatus")
    public ResponseEntity<String> updateOrderStatus(@RequestBody UpdateOrderStatusRequest request) {

        try {
            request.getOrderDetailNums().forEach(orderDetailNum ->
                    orderService.updateStatus(orderDetailNum, request.getOrderStatus())
            );

            // 주문 상태가 'CANCEL'일 때 고객의 total_price 업데이트
            if ("CANCEL".equals(request.getOrderStatus())) {
                for (Long orderDetailNum : request.getOrderDetailNums()) {
                    CustomerTotalPriceDto customerTotalPriceDto = custService.custTotalPrice(orderDetailNum);
                    if (customerTotalPriceDto != null) {
                        int totalPriceToUpdate = customerTotalPriceDto.getTotalPrice() - (customerTotalPriceDto.getPrice() * customerTotalPriceDto.getItemCnt());

                        Map<String, Object> map = new HashMap<>();
                        map.put("custNum", customerTotalPriceDto.getCustNum());
                        map.put("totalPrice", totalPriceToUpdate);
                        custService.updateTP(map);
                    }
                }
            }
            return ResponseEntity.ok("주문 상태가 업데이트되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 상태 업데이트에 실패했습니다.");
        }
    }

}
