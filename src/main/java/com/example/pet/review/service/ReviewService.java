package com.example.pet.review.service;

import com.example.pet.review.dto.ReviewDto;
import com.example.pet.review.form.ReviewForm;
import com.example.pet.review.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;

    // 리뷰 추가
    public int addRV(ReviewDto reviewDto){
        reviewMapper.insertRV(reviewDto);

        return reviewDto.getReviewNum();
    }


    // 리뷰 전체 목록 보기
    public List<ReviewDto> RVList(int pageSize, int offset){
        System.out.println("reviewService 동작");
        return reviewMapper.listRVAll(pageSize, offset);
    }
    public int RVListCount(){
        return reviewMapper.listRVAllCount();
    }

    // form을 dto 로 변환
    public ReviewDto makeReviewDto(ReviewForm reviewForm){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewTitle(reviewForm.getTitle());
        reviewDto.setReviewContent(reviewForm.getContent());

        return reviewDto;
    }

    // dto 를 form 으로 변환
    public ReviewForm makeReviewForm(ReviewDto reviewDto){
        ReviewForm reviewForm = new ReviewForm();
        reviewForm.setTitle(reviewForm.getTitle());
        reviewForm.setContent(reviewForm.getContent());

        return reviewForm;
    }

    public ReviewDto selectRV(int reviewNum){
        return reviewMapper.selectRVInfo(reviewNum);
    };

    // 리뷰 삭제
    public int deleteRV(int reviewNum){
        return reviewMapper.deleteRV(reviewNum);
    };

    // 리뷰 수정
    public int updateRV(Map map){
        return reviewMapper.updateRV(map);
    };

    //작성된 리뷰인지 확인
    public ReviewDto findRV(Long orderDetailNum){
        return reviewMapper.findRV(orderDetailNum);
    };


}
