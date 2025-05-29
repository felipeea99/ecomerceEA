package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.PhotoRequest;
import com.ecommerce.ea.DTOs.response.PhotoResponse;
import com.ecommerce.ea.DTOs.update.PhotoUpdate;
import com.ecommerce.ea.entities.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IPhoto {
    CompletableFuture<PhotoResponse> AddPhoto(PhotoRequest photoRequest);
    CompletableFuture<List<PhotoResponse>> AddMultiplePhotos(int productID, List<MultipartFile> photos);
    CompletableFuture<PhotoResponse> EditPhoto(PhotoUpdate photoUpdate);
    CompletableFuture<Boolean> DeletePhoto(int photoID);
    CompletableFuture<List<PhotoResponse>> GetAllPhotosByProductID(int productID);
    CompletableFuture<Void>  PhotoOrder(int productID);
    CompletableFuture<List<PhotoResponse>> GetPhotosIndexZero();
}
