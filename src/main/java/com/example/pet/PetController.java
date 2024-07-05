package com.example.pet;

import com.example.pet.category.dto.CategoryDto;
import com.example.pet.category.service.CategoryService;
import com.example.pet.constant.Role;
import com.example.pet.customer.dto.CustomerDto;
import com.example.pet.customer.form.CustomerForm;
import com.example.pet.customer.service.CustomerService;
import com.example.pet.item.dto.ItemDto;
import com.example.pet.item.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PetController {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final CustomerService customerService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String petForm(Model model){
        List<CategoryDto> categories = categoryService.cateList();
        model.addAttribute("categories", categories);

        List<ItemDto> items = itemService.indexItem();
        model.addAttribute("items",items);

        return "index";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        List<CategoryDto> categorys = categoryService.cateList();
        model.addAttribute("categories", categorys);
        return "/login";
    }

    @GetMapping("/reg")
    public String joinForm(Model model){
        List<CategoryDto> categorys = categoryService.cateList();
        model.addAttribute("categories", categorys);

        CustomerDto customerDto = new CustomerDto();
        model.addAttribute("customer", customerDto);
        return "register";
    }

/*    @GetMapping("/join")
    public String registerForm(Model model, HttpServletRequest request){

        List<CategoryDto> categorys = categoryService.cateList();
        model.addAttribute("categories", categorys);

        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        System.out.println(token.getHeaderName() + " = " + token.getToken());

        model.addAttribute("customer", new CustomerForm());
        return "/register";
    }*/

    @PostMapping("/reg")
    public String registerSubmit(@Valid CustomerForm customerForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("customer", customerForm);
            return "register";
        }

        try {
            CustomerDto findId = customerService.overlapId(customerForm.getCustId());
            if (findId != null) {
                throw new IllegalStateException("이미 가입한 아이디");
            }

            CustomerDto findTel = customerService.overlapTel(customerForm.getCustTel());
            if (findTel != null) {
                throw new IllegalStateException("이미 가입한 전화번호");
            }

            CustomerDto dto = new CustomerDto();
            dto.setCustId(customerForm.getCustId());

            //비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(customerForm.getCustPw());
            dto.setCustPw(encodedPassword);

            dto.setCustName(customerForm.getCustName());
            dto.setCustTel(customerForm.getCustTel());
            dto.setRole(Role.USER);

            customerService.insertCust(dto);
            model.addAttribute("successMessage","회원가입을 축하합니다");

        } catch (IllegalStateException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("customer", customerForm);
            return "register";
        }
        return "/login";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute(
                "loginErrorMsg",
                "아이디 또는 비밀번호를 다시 입력하세요");
        return "/login";
    }



}
