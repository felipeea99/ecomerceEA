package com.ecommerce.ea.DTOs.response;

import com.ecommerce.ea.entities.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SizeResponse {
    private int sizeId;
    private String size;

    public static SizeResponse ToSizeResponseObj(Size size){
        return new SizeResponse(
                size.getSizeId(),
                size.getSize()
        );
    }
}
