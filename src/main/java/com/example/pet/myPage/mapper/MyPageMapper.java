package com.example.pet.myPage.mapper;

import com.example.pet.myPage.dto.MyPageDto;
import com.example.pet.order.dto.OrderDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyPageMapper {

    List<MyPageDto> selectMyPage(@Param("custNum")Long custNum, @Param("pageSize") int pageSize, @Param("offset") int offset);
    int selectMyPageCount(Long custNum);

    int deleteOrder(Long orderDetailNum);

    int changeStatus(Map map);

    OrderDetailDto selectCancel(Map map);

    List<MyPageDto> selectRVItem(Long custNum);

}
