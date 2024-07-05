package com.example.pet.customer.service;

import com.example.pet.customer.dto.CustomerDto;
import com.example.pet.customer.dto.CustomerTotalPriceDto;
import com.example.pet.customer.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomerService {

    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public int insertCust(CustomerDto customerDto) {
        overlapId(customerDto.getCustId());
        overlapTel(customerDto.getCustTel());
        return customerMapper.insertCust(customerDto);
    }

    public CustomerDto loginCustomer(String id) {
        return customerMapper.loginCustomer(id);
    }

    public CustomerDto overlapId(String id) {
        return customerMapper.overlapId(id);
    }

    public CustomerDto overlapTel(String tel) {
        return customerMapper.overlapTel(tel);
    }

    public CustomerDto loginId(String id){
        return customerMapper.longinId(id);
    }


    public int updateTP(Map map){
        return customerMapper.updateTP(map);
    }

    public CustomerTotalPriceDto custTotalPrice(Long orderDetailNum){
        return customerMapper.custTotalPrice(orderDetailNum);
    }

    public CustomerDto selectCustomer(Long custNum){
        return customerMapper.selectCustomer(custNum);
    }

}
