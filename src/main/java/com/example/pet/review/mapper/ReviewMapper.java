package com.example.pet.review.mapper;

import com.example.pet.review.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewMapper {

    // 리뷰 추가
    int insertRV (ReviewDto reviewDto);

    // 리뷰 목록
    List<ReviewDto> listRVAll(@Param("pageSize") int pageSize, @Param("offset") int offset);
    int listRVAllCount();

    // 리뷰 검색
    ReviewDto selectRVInfo(int reviewNum);

    //작성된 리뷰인지 확인
    ReviewDto findRV(Long orderDetailNum);

    // 리뷰 삭제
    int deleteRV(int reviewNum);

    // 리뷰 수정
    int updateRV(Map map);


}
