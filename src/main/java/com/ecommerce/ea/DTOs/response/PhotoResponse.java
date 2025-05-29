package com.ecommerce.ea.DTOs.response;

import com.ecommerce.ea.entities.Photo;
import com.ecommerce.ea.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoResponse {
    private int photoID;
    private String photoValue;
    private int index;
    private Product productId;

    public static PhotoResponse ToPhotoResponseObj(Photo photo){
        return new PhotoResponse(
                photo.getPhotoID(),
                photo.getPhotoValue(),
                photo.getIndex(),
                photo.getProduct()
        );
    }

}
