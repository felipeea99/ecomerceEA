package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.entities.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IPhoto {
    CompletableFuture<Photo> AddPhoto(Photo photo);
    CompletableFuture<List<Photo>> AddMultiplePhotos(int productID, List<MultipartFile> photos);
    CompletableFuture<Photo> EditPhoto(Photo photo);
    CompletableFuture<Boolean> DeletePhoto(int photoID);
    CompletableFuture<List<Photo>> GetAllPhotosByProductID(int productID);
    CompletableFuture<Void>  PhotoOrder(int productID);
    CompletableFuture<List<Photo>> GetPhotosIndexZero();
}
