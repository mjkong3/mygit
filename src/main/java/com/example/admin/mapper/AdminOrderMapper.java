package com.example.admin.mapper;


import com.example.admin.dto.AdminOrderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminOrderMapper {

    //List<AdminOrderDto> orderListAll();

    List<AdminOrderDto> searchOrders(@Param("searchType") String searchType,
                                     @Param("searchKeyword") String searchKeyword,
                                     @Param("startDate") String startDate,
                                     @Param("endDate") String endDate,
                                     @Param("orderStatus") String orderStatus,
                                     @Param("pageSize") int pageSize,
                                     @Param("offset") int offset);

    int searchOrdersCount(@Param("searchType") String searchType,
                          @Param("searchKeyword") String searchKeyword,
                          @Param("startDate") String startDate,
                          @Param("endDate") String endDate,
                          @Param("orderStatus") String orderStatus);

    int updateStatus (Map map);
}
