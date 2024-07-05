package com.example.pet.review.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReviewForm {

    @NotBlank(message = "제목 필수입력")
    private String title;

    @NotBlank(message = "내용 필수입력")
    private String content;

    private MultipartFile img;
}
