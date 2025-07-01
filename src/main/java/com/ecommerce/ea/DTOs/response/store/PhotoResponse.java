package com.ecommerce.ea.DTOs.response.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoResponse {
    private int photoId;
    private String photoValue;
    private int index;
    private int productId;
}
