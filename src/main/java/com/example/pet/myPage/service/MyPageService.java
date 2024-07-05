package com.example.pet.myPage.service;

import com.example.pet.myPage.dto.MyPageDto;
import com.example.pet.order.dto.OrderDetailDto;
import com.example.pet.myPage.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageMapper mapper;


    public List<MyPageDto> selectMyPage(Long custNum, int pageSize, int offset){
        return mapper.selectMyPage(custNum, pageSize, offset);
    }
    public int selectMyPageCount(Long custNum){
        return mapper.selectMyPageCount(custNum);
    };


    public int deleteOrder(Long orderDetailNum){
        return mapper.deleteOrder(orderDetailNum);
    }

    public int changeStatus(Map map){
        return mapper.changeStatus(map);
    }

    public OrderDetailDto selectCancel(Map map){
        return mapper.selectCancel(map);
    }

    public  List<MyPageDto> selectRVItem(Long custNum){
        List<MyPageDto> list = mapper.selectRVItem(custNum);
        return list;
    };
}
