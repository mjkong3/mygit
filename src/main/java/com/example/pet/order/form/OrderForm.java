package com.example.pet.order.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderForm {

    @NotNull
    private List<Long> cartItemNum;

    private String custName;

    private String custTel;

    @NotBlank
    private String address;

    private String orderReq;
}
