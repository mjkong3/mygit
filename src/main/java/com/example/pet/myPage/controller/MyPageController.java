package com.example.pet.myPage.controller;

import com.example.pet.category.dto.CategoryDto;
import com.example.pet.config.PageHandler;
import com.example.pet.customer.dto.CustomerDto;
import com.example.pet.customer.dto.CustomerTotalPriceDto;
import com.example.pet.myPage.dto.MyPageDto;
import com.example.pet.category.service.CategoryService;
import com.example.pet.customer.service.CustomerService;
import com.example.pet.myPage.service.MyPageService;
import com.example.pet.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final CategoryService categoryService;

    @GetMapping("/myPage")
    public String myPage(Model model, Principal principal,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "5") int pageSize){

        List<CategoryDto> categorys = categoryService.cateList();
        model.addAttribute("categories", categorys);

        if (principal == null) {
            model.addAttribute("loginErrorMsg","로그인 후 사용가능합니다.");
            return "/login";
        }

        CustomerDto customerDto = customerService.loginCustomer(principal.getName());

        int totalCnt = myPageService.selectMyPageCount(customerDto.getCustNum());
        int offset = (page - 1) * pageSize;

        List<MyPageDto> list = myPageService.selectMyPage(customerDto.getCustNum(), pageSize, offset);

        System.out.println(customerDto);

        PageHandler pageHandler = new PageHandler(totalCnt, pageSize, page);
        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("list", list);
        model.addAttribute("customer", customerDto);

        return "/myPage";
    }

    @DeleteMapping("/order/cancel/{orderDetailNum}")
    public ResponseEntity deleteOrderDetail(@PathVariable("orderDetailNum") Long orderDetailNum){

        Map<String, Object> map = new HashMap<>();

        map.put("orderStatus", "CANCEL");
        map.put("orderDetailNum", orderDetailNum);

        myPageService.changeStatus(map);


        CustomerTotalPriceDto customerTotalPriceDto = customerService.custTotalPrice(orderDetailNum);

        int totalPrice = customerTotalPriceDto.getTotalPrice();
        int sum = customerTotalPriceDto.getPrice();
        Long custNum = customerTotalPriceDto.getCustNum();

        map.put("totalPrice", totalPrice - sum);
        map.put("custNum", custNum);

        customerService.updateTP(map);
        return new ResponseEntity<Long>(orderDetailNum, HttpStatus.OK);
    }

    @GetMapping("/reviewItem")
    public String reviewItem(Model model, Principal principal){

        List<CategoryDto> categories = categoryService.cateList();
        model.addAttribute("categories", categories);

        if (principal == null) {
            model.addAttribute("loginErrorMsg","로그인 후 사용가능합니다.");
            return "/login";
        }

        CustomerDto customerDto = customerService.loginCustomer(principal.getName());

        List<MyPageDto> list = myPageService.selectRVItem(customerDto.getCustNum());
        model.addAttribute("list", list);
        model.addAttribute("customer", customerDto);

        return "/review/reviewItem";
    }
}
