package com.ecommerce.ea.DTOs.response;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

public class CategoryResponse {
    private int categoryId;
    private String categoryName;
}
