package com.ecommerce.ea.services;

import com.ecommerce.ea.entities.Photo;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.IPhoto;
import com.ecommerce.ea.repository.PhotoRepository;
import com.ecommerce.ea.repository.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PhotoService implements IPhoto {

    private final PhotoRepository photoRepository;
    private  final ProductRepository productRepository;

    public PhotoService(PhotoRepository photoRepository, ProductRepository productRepository){
        this.photoRepository = photoRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CompletableFuture<Photo> AddPhoto(Photo photo) {
         Photo photoObj = photoRepository.save(photo);
        return CompletableFuture.completedFuture(photoObj);
    }

    @Override
    public CompletableFuture<List<Photo>> AddMultiplePhotos(int productID, List<MultipartFile> photos) {
        return null;
    }

    @Override
    public CompletableFuture<Photo> EditPhoto(Photo photo) {
         Photo photoObj = photoRepository.findById(photo.getPhotoID()).orElseThrow(()-> new BadRequestException("photoId was not found on the database"));
         photoObj.setProduct(photo.getProduct());
         photoObj.setPhotoValue(photo.getPhotoValue());
         photoRepository.save(photo);
        return CompletableFuture.completedFuture(photo);
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
    public CompletableFuture<List<Photo>> GetAllPhotosByProductID(int productID) {
        productRepository.findById(productID).orElseThrow(() -> new NullPointerException("productId was not found on the database"));
        return photoRepository.findAllPhotosByProductId(productID);
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
    public CompletableFuture<List<Photo>> GetPhotosIndexZero() {
        return null;
    }
}
