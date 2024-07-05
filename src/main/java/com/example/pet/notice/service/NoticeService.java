package com.example.pet.notice.service;

import com.example.pet.notice.dto.NoticeDto;
import com.example.pet.notice.form.NoticeForm;
import com.example.pet.notice.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeMapper noticeMapper;

    // 공지 추가
    public int addNT(NoticeForm noticeform){
        NoticeDto noticeDto = makeNoticeDto(noticeform);
        noticeMapper.insertNT(noticeDto);

        return noticeDto.getNoticeNum();
    }

    // 공지 전체 목록 보기
    public List<NoticeDto> NTList(int pageSize, int offset){
        System.out.println("noticeService 동작");
        return noticeMapper.listNTAll(pageSize, offset);
    }
    public int listNTAllCount(){
        return noticeMapper.listNTAllCount();
    };


    // form을 dto 로 변환
    public NoticeDto makeNoticeDto(NoticeForm noticeForm){
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setNoticeTitle(noticeForm.getTitle());
        noticeDto.setNoticeContent(noticeForm.getContent());

        return noticeDto;
    }

    // dto 를 form 으로 변환
    public NoticeForm makeNoticeForm(NoticeDto noticeDto){
        NoticeForm noticeForm = new NoticeForm();
        noticeForm.setTitle(noticeForm.getTitle());
        noticeForm.setContent(noticeForm.getContent());

        return noticeForm;
    }

    public NoticeDto selectNT(int noticeNum){
        return noticeMapper.selectNT(noticeNum);
    };

    // 공지 삭제
    public int deleteNT(int noticeNum){
        return noticeMapper.deleteNT(noticeNum);
    };

    // 공지 수정
    public int updateNT(Map map){
        return noticeMapper.updateNT(map);
    };


    // 조회수 증가
    @Transactional
    public void increaseHit(int noticeNum) {
        noticeMapper.increaseHit(noticeNum);
    }


}
