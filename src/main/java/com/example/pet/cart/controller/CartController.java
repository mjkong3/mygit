package com.example.pet.cart.controller;

import com.example.pet.cart.dto.CartDetailDto;
import com.example.pet.category.dto.CategoryDto;
import com.example.pet.cart.form.CartForm;
import com.example.pet.cart.service.CartService;
import com.example.pet.category.service.CategoryService;
import com.example.pet.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CustomerService customerService;
    private final CategoryService categoryService;

    @GetMapping("/shoppingbasket")
    public String show(Model model, Principal principal){
        List<CategoryDto> categorys = categoryService.cateList();
        model.addAttribute("categories", categorys);
        if (principal == null) {
            model.addAttribute("loginErrorMsg","로그인 후 사용가능합니다.");
            return "/login";
        }

        String id = principal.getName();

        List<CartDetailDto> list = cartService.cartItemList(id);
        model.addAttribute("list", list);

        return "/shoppingbasket.html";
    }

    //장바구니 클릭 시 실행
    @PostMapping("/cart")
    public ResponseEntity order(@RequestBody @Valid CartForm cartForm, BindingResult bindingResult, Principal principal, Model model){
        List<CategoryDto> categorys = categoryService.cateList();
        model.addAttribute("categories", categorys);

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }

            return  new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String id = principal.getName();
        Long cartItemNum;

        try{
            cartItemNum = cartService.addCart(cartForm, id);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        System.out.println("cartItemNum : " + cartItemNum);

        return new ResponseEntity<Long>(cartItemNum, HttpStatus.OK);
    }


    @PatchMapping("/cartItem/{cartItemNum}")
    public ResponseEntity updateCartItem(@PathVariable("cartItemNum") Long cartItemNum, int count, Principal principal){

//        String custId = principal.getName();;
//        CustomerDto customerDto = customerService.loginCustomer(custId);

        if(count < 0){
            return new ResponseEntity<String>("최소 1개 이상 주문하셔야합니다.", HttpStatus.BAD_REQUEST);
        }

        cartService.updateCartItemCount(cartItemNum, count);
        return new ResponseEntity<Long>(cartItemNum, HttpStatus.OK);
    }

    @DeleteMapping("/cartItem/{cartItemNum}")
    public ResponseEntity deleteCartItem(@PathVariable("cartItemNum") Long cartItemNum){

        cartService.deleteCartItem(cartItemNum);
        return new ResponseEntity<Long>(cartItemNum, HttpStatus.OK);
    }
}
