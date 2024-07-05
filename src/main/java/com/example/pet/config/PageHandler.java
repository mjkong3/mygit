package com.example.pet.config;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PageHandler {
    private int totalCnt;       //총 게시물 수
    private int pageSize;       //한페이지에 표시할 데이터 수
    private int naviSize = 5;   //페이지 네비게이션 수
    private int totalPage;      //전체 페이지 수
    private int page;           //현재 페이지 번호
    private int beginPage;      //페이지 네비게이션의 첫번째 페이지
    private int endPage;        //페이지 네비게이션의 마지막 페이지
    private boolean firstPage;  //첫번째 페이지인지 확인
    private boolean lastPage;   //마지막 페이지인지 확인

    public PageHandler(int totalCnt, int pageSize, int page){
        this.totalCnt = totalCnt;
        this.pageSize = pageSize;
        this.page = page;

        totalPage=(int) Math.ceil((double) totalCnt/pageSize);
        beginPage = (page-1)/ naviSize * naviSize +1;
        endPage = Math.min(beginPage + (naviSize -1), totalPage);
        firstPage = (beginPage == 1);
        lastPage = (endPage == totalPage);
    }
}
