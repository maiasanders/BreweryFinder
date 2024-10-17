package com.techelevator.model.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryPostDto {
    @NotBlank(message = "field categoryName can not be blank")
    private String categoryName;

    public CategoryPostDto(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryPostDto() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
