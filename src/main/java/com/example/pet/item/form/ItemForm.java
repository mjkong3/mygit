package com.example.pet.item.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ItemForm {
    private int id;

    @NotBlank
    private String itemName;

    @NotBlank
    private String categoryName;

    @NotNull
    private int count;

    @NotNull
    private int price;

    private MultipartFile itemImgFile;

    @NotBlank
    private String itemDetail;

}
