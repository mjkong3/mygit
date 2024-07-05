package com.example.pet.customer.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerForm {

    @NotBlank(message = "필수입력")
    private String custId;

    @NotBlank(message = "필수입력")
    private String custPw;

    @NotBlank(message = "필수입력")
    private String custName;

    @NotBlank(message = "필수입력")
    private String custTel;
}
