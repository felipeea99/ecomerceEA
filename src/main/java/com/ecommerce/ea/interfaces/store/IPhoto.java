package com.ecommerce.ea.interfaces.store;

import com.ecommerce.ea.DTOs.request.store.PhotoRequest;
import com.ecommerce.ea.DTOs.response.store.PhotoResponse;
import com.ecommerce.ea.DTOs.update.PhotoUpdate;
import com.ecommerce.ea.entities.store.Photo;

import java.util.List;
import java.util.UUID;

public interface IPhoto {
    Photo findByPhotoIdBaseForm(int photoId);
    PhotoResponse addPhoto(PhotoRequest photoRequest);
    List<PhotoResponse> addMultiplePhotos(List<PhotoRequest> photos);
    PhotoResponse editPhoto(PhotoUpdate photoUpdate);
    Boolean deletePhoto(int photoID);
    List<PhotoResponse> getAllPhotosByProductID(int productID);
    void photoOrder(int productID);
    List<PhotoResponse> getPhotosIndexZero(int productId);
    List<PhotoResponse> findAllPhotosByStoreId(UUID storeId);
    PhotoResponse ToPhotoResponseObj(Photo photo);
    Photo ToPhotoObj(PhotoRequest photoRequest);

    }
