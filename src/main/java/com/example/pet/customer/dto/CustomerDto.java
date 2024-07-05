package com.example.pet.customer.dto;

import com.example.pet.constant.Role;
import lombok.Data;

@Data
public class CustomerDto {

    private Long custNum;
    private String custName;
    private String custId;
    private String custPw;
    private String custTel;
    private Integer totalPrice;
    private String grade;
    private Role role;
}
