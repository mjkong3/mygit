package com.example.admin.controller;

import com.example.pet.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class AdminPetController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @GetMapping("/register.html")
//    public String registerForm(Model model, HttpServletRequest request){
//        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
//        System.out.println(token.getHeaderName() + " = " + token.getToken());
//
//        model.addAttribute("customer", new CustomerForm());
//        return "register";
//    }

//    @GetMapping("/login_proc")
//    public String login(){
//        return "/login";
//    }

//    @GetMapping(value = "/login/error")
//    public String loginError(Model model){
//        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 다시 입력하세요");
//
//        return "/login";
//    }

}
