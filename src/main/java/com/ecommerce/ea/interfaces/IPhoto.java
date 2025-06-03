package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.PhotoRequest;
import com.ecommerce.ea.DTOs.response.PhotoResponse;
import com.ecommerce.ea.DTOs.update.PhotoUpdate;
import com.ecommerce.ea.entities.Photo;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IPhoto {
    Mono<PhotoResponse> AddPhoto(PhotoRequest photoRequest);
    Mono<List<PhotoResponse>> AddMultiplePhotos(int productID, List<MultipartFile> photos);
    Mono<PhotoResponse> EditPhoto(PhotoUpdate photoUpdate);
    Mono<Boolean> DeletePhoto(int photoID);
    Mono<List<PhotoResponse>> GetAllPhotosByProductID(int productID);
    Mono<Void>  PhotoOrder(int productID);
    Mono<List<PhotoResponse>> GetPhotosIndexZero();
}
