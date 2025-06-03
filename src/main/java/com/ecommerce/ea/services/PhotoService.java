package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.PhotoRequest;
import com.ecommerce.ea.DTOs.response.PhotoResponse;
import com.ecommerce.ea.DTOs.update.PhotoUpdate;
import com.ecommerce.ea.entities.Photo;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.IPhoto;
import com.ecommerce.ea.repository.PhotoRepository;
import com.ecommerce.ea.repository.ProductSingleRepository;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class PhotoService implements IPhoto {

    private final PhotoRepository photoRepository;
    private  final ProductSingleRepository productRepository;

    public PhotoService(PhotoRepository photoRepository, ProductSingleRepository productRepository){
        this.photoRepository = photoRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Mono<PhotoResponse> AddPhoto(PhotoRequest photoRequest) {
        //Convert the photoRequest into photoObject
        Photo photo = photoRequest.ToPhotoObj();
        //Stores it on the photoObj variable and save it into the database
        Mono<Photo> photoObj = photoRepository.save(photo);
        //Convert the photoObj to PhotoResponseObj
        return photoObj.map(PhotoResponse::ToPhotoResponseObj);
    }

    @Override
    public Mono<List<PhotoResponse>> AddMultiplePhotos(int productID, List<MultipartFile> photos) {
        return null;
    }

    @Override
    public Mono<PhotoResponse> EditPhoto(PhotoUpdate photoUpdate) {
        return photoRepository.findById(photoUpdate.getPhotoID())
                .switchIfEmpty(Mono.error(new BadRequestException("photoId was not found on the database")))
                .flatMap(photo -> {
                    return productRepository.findById(photoUpdate.getProductId())
                            .switchIfEmpty(Mono.error(new BadRequestException("productId was not found on the database")))
                            .flatMap(product -> {
                                //Edit Changes
                                photo.setProduct(product);
                                photo.setPhotoValue(photoUpdate.getPhotoValue());
                                return photoRepository.save(photo);
                            });
                })
                //Convert it to PhotoResponse
                .map(PhotoResponse::ToPhotoResponseObj);
    }

    @Override
    public Mono<Boolean> DeletePhoto(int photoID) {
         return  photoRepository.deleteById(photoID).thenReturn(true).onErrorReturn(false);
    }

    @Override
    public Mono<List<PhotoResponse>> GetAllPhotosByProductID(int productID) {
        return productRepository.findById(productID)
                .switchIfEmpty(Mono.error(new BadRequestException("productId was not found on the database")))
                .then(photoRepository.findAllPhotosByProductId(productID).map(photos ->
                                        photos.stream().map(PhotoResponse::ToPhotoResponseObj).collect(Collectors.toList())));
    }


    @Override
    public Mono<Void> PhotoOrder(int productID) {

        if (productID == 0) {
            return Mono.error(new BadRequestException("ProductID is empty"));
        }

        return photoRepository.findByProductIDOrderByPhotosID(productID)
                .flatMap(photosList -> {
                    for (int index = 0; index < photosList.size(); index++) {
                        Photo photo = photosList.get(index);
                        if (photo.getIndex() != index) {
                            photo.setIndex(index);
                        }
                    }
                    return photoRepository.saveAll(photosList).then();
                });
    }



    @Override
    public Mono<List<PhotoResponse>> GetPhotosIndexZero() {
        return photoRepository.findByIndex(0) // Mono<List<Photo>>
                .map(photos -> photos.stream()
                        .map(PhotoResponse::ToPhotoResponseObj)
                        .collect(Collectors.toList())
                );
    }

}
