package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.PhotoRequest;
import com.ecommerce.ea.DTOs.response.PhotoResponse;
import com.ecommerce.ea.DTOs.update.PhotoUpdate;
import com.ecommerce.ea.entities.Photo;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.IPhoto;
import com.ecommerce.ea.repository.PhotoRepository;
import com.ecommerce.ea.repository.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class PhotoService implements IPhoto {

    private final PhotoRepository photoRepository;
    private  final ProductRepository productRepository;

    public PhotoService(PhotoRepository photoRepository, ProductRepository productRepository){
        this.photoRepository = photoRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CompletableFuture<PhotoResponse> AddPhoto(PhotoRequest photoRequest) {
        //Convert the photoRequest into photoObject
        Photo photo = photoRequest.ToPhotoObj();
        //Stores it on the photoObj variable and save it into the database
        Photo photoObj = photoRepository.save(photo);
        //Convert the photoObj to PhotoResponseObj
        PhotoResponse photoResponse = PhotoResponse.ToPhotoResponseObj(photoObj);
        return CompletableFuture.completedFuture(photoResponse);
    }

    @Override
    public CompletableFuture<List<PhotoResponse>> AddMultiplePhotos(int productID, List<MultipartFile> photos) {
        return null;
    }

    @Override
    public CompletableFuture<PhotoResponse> EditPhoto(PhotoUpdate photoUpdate) {
        //PhotoId validation and stores the result on the variable photoObj
         Photo photoObj = photoRepository.findById(photoUpdate.getPhotoID()).orElseThrow(()-> new BadRequestException("photoId was not found on the database"));
        //Edit and save the changes
         photoObj.setProduct(photoObj.getProduct());
         photoObj.setPhotoValue(photoObj.getPhotoValue());
         Photo photoSaved =  photoRepository.save(photoObj);
         //Convert photo to PhotoResponse
        PhotoResponse photoResponse = PhotoResponse.ToPhotoResponseObj(photoSaved);
        return CompletableFuture.completedFuture(photoResponse);
    }

    @Override
    public CompletableFuture<Boolean> DeletePhoto(int photoID) {
        try{
            photoRepository.deleteById(photoID);
            return CompletableFuture.completedFuture(true);
        }catch (Exception e){
            return CompletableFuture.completedFuture(false);
        }
    }

    @Override
    public CompletableFuture<List<PhotoResponse>> GetAllPhotosByProductID(int productID) {
        //productId validation
        productRepository.findById(productID).orElseThrow(() -> new NullPointerException("productId was not found on the database"));
        return photoRepository.findAllPhotosByProductId(productID).thenApply(photos -> photos.stream().map(PhotoResponse::ToPhotoResponseObj).collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<Void> PhotoOrder(int productID) {
        return CompletableFuture.runAsync(() -> {
            if (productID == 0) {
                throw new IllegalArgumentException("ProductID is empty");
            }

            List<Photo> photosList = photoRepository.findByProductIDOrderByPhotosID(productID);

            for (int index = 0; index < photosList.size(); index++) {
                Photo photo = photosList.get(index);
                if (photo.getIndex() != index) {
                    photo.setIndex(index);
                }
            }

            photoRepository.saveAll(photosList);
        });
    }

    @Override
    public CompletableFuture<List<PhotoResponse>> GetPhotosIndexZero() {
        return photoRepository.findByIndex(0)
                .thenApply(photos -> photos.stream()
                        .map(PhotoResponse::ToPhotoResponseObj)
                        .collect(Collectors.toList()));
    }
}
