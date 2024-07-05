package com.example.pet.notice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeDto {

    private int noticeNum;
    private String noticeTitle;
    private int hit;
    private LocalDateTime noticeDate;
    private String noticeContent;

}
