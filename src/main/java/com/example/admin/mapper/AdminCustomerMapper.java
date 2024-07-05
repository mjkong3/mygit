package com.example.admin.mapper;


import com.example.admin.dto.AdminCustomerDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminCustomerMapper {

    //유저 전체 조회
    List<AdminCustomerDto> custListAll();

    //유저 삭제
    void deleteCust(int custNum);

    //유저 검색
    List<AdminCustomerDto> searchCustomer(@Param("searchField")String searchField,
                                          @Param("searchValue")String searchValue,
                                          @Param("gradeName")String gradeName,
                                          @Param("pageSize") int pageSize,
                                          @Param("offset") int offset);

    int searchCustomerCount(@Param("searchField")String searchField,
                            @Param("searchValue")String searchValue,
                            @Param("gradeName")String gradeName);

    int searchCustomerCount();
}