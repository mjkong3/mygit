package com.example.pet.notice.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NoticeForm {

    @NotBlank(message = "제목 필수입력")
    private String title;

    @NotBlank(message = "내용 필수입력")
    private String content;

}
