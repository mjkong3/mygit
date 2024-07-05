package com.example.pet.customer.mapper;

import com.example.pet.customer.dto.CustomerDto;
import com.example.pet.customer.dto.CustomerTotalPriceDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CustomerMapper {

    int insertCust(CustomerDto customerDto);

    CustomerDto overlapId(String id);

    CustomerDto overlapTel(String tel);

    CustomerDto loginCustomer(String id);

    int loginId(String id);

    CustomerDto longinId(String id);

    int updateTP(Map map);

    CustomerTotalPriceDto custTotalPrice(Long orderDetailNum);

    CustomerDto selectCustomer(Long custNum);
}