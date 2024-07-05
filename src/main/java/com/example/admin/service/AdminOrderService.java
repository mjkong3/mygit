package com.example.admin.service;


import com.example.admin.dto.AdminOrderDto;
import com.example.admin.mapper.AdminOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminOrderService {

    @Autowired
    private AdminOrderMapper orderMapper;

///*    public List<AdminOrderDto> orderListAll() {
//        return orderMapper.orderListAll();
//    }*/


    public List<AdminOrderDto> getOrders(String searchType, String searchKeyword, String startDate,
                                         String endDate, String orderStatus,
                                         int pageSize, int offset) {
        return orderMapper.searchOrders
                (searchType, searchKeyword, startDate, endDate, orderStatus, pageSize, offset);
    }
    public int searchOrdersCount(String searchType, String searchKeyword,
                                 String startDate, String endDate, String orderStatus){
        return orderMapper.searchOrdersCount
                (searchType, searchKeyword, startDate, endDate, orderStatus);
    };


    public int updateStatus(Long orderDetailNum, String orderStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderDetailNum", orderDetailNum);
        map.put("orderStatus", orderStatus);
        return orderMapper.updateStatus(map);
    }
}
