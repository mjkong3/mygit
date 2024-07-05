package com.example.pet.notice.mapper;

import com.example.pet.notice.dto.NoticeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper

public interface NoticeMapper {

    // 공지 추가
    int insertNT(NoticeDto noticeDto);

    // 공지 목록
    List<NoticeDto> listNTAll(@Param("pageSize") int pageSize,
                              @Param("offset") int offset);
    int listNTAllCount();

    // 공지 검색
    NoticeDto selectNT(int noticeNum);

    // 공지 삭제
    int deleteNT(int noticeNum);

    // 공지 수정
    int updateNT(Map map);

    // 조회 수
    @Update("UPDATE notice SET hit = hit + 1 WHERE notice_num = #{noticeNum}")
    void increaseHit(@Param("noticeNum") int noticeNum);

}

