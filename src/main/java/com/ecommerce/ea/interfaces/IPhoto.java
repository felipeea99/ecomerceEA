package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.PhotoRequest;
import com.ecommerce.ea.DTOs.response.PhotoResponse;
import com.ecommerce.ea.DTOs.update.PhotoUpdate;
import com.ecommerce.ea.entities.Photo;

import java.util.List;

public interface IPhoto {
    Photo findByPhotoIdBaseForm(int photoId);
    PhotoResponse addPhoto(PhotoRequest photoRequest);
    List<PhotoResponse> addMultiplePhotos(int productID, List<PhotoRequest> photos);
    PhotoResponse editPhoto(PhotoUpdate photoUpdate);
    Boolean deletePhoto(int photoID);
    List<PhotoResponse> getAllPhotosByProductID(int productID);
    void photoOrder(int productID);
    List<PhotoResponse> getPhotosIndexZero(int productId);
    PhotoResponse ToPhotoResponseObj(Photo photo);
    Photo ToPhotoObj(PhotoRequest photoRequest);

    }
