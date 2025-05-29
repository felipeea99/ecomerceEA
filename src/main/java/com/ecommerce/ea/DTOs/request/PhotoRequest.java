package com.ecommerce.ea.DTOs.request;

import com.ecommerce.ea.entities.Photo;
import com.ecommerce.ea.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoRequest {
    @NotBlank(message = "photoValue is mandatory")
    private String photoValue;
    private int index;
    @NotNull(message = "productId is mandatory")
    private Product productId;

    public Photo ToPhotoObj(){
       Photo photo = new Photo();
       photo.setPhotoValue(photoValue);
       photo.setIndex(index);
       photo.setProduct(productId);

       return photo;
    }
}
