package com.example.admin.dto;

import com.example.pet.constant.Role;
import lombok.Data;

@Data
public class AdminCustomerDto {

    private Integer custNum;
    private String custName;
    private String custId;
    private String custPw;
    private String custTel;
    private Integer totalPrice;
    private String grade;
    private Role role;
}
