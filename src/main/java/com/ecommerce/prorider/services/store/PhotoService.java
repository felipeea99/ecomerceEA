package com.ecommerce.prorider.services.store;

import com.ecommerce.prorider.DTOs.request.store.PhotoRequest;
import com.ecommerce.prorider.DTOs.response.store.PhotoResponse;
import com.ecommerce.prorider.DTOs.update.store.PhotoUpdate;
import com.ecommerce.prorider.entities.store.Photo;
import com.ecommerce.prorider.entities.store.Product;
import com.ecommerce.prorider.exceptions.BadRequestException;
import com.ecommerce.prorider.interfaces.store.IPhoto;
import com.ecommerce.prorider.repository.store.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PhotoService implements IPhoto {

    private final PhotoRepository photoRepository;
    private  final ProductService productService;

    public PhotoService(PhotoRepository photoRepository, ProductService productService){
        this.photoRepository = photoRepository;
        this.productService = productService;
    }

    @Override
    public Photo findByPhotoIdBaseForm(int photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new BadRequestException("photoId was not found on the database"));
    }

    /// Add a photo object to the database
    @Override
    public PhotoResponse addPhoto(PhotoRequest photoRequest) {
        //Convert the photoRequest into photoObject
        Photo photo = this.ToPhotoObj(photoRequest);
        //Photo Order
        this.photoOrder(photoRequest.getProductId());
        //Stores it on the photoObj variable and save it into the database
        Photo photoObj = photoRepository.save(photo);
        //Convert the photoObj to PhotoResponseObj
        return this.ToPhotoResponseObj(photoObj);
    }
    /// Add Multiples Photos objects to the database
    @Override
    public List<PhotoResponse> addMultiplePhotos(List<PhotoRequest> photos) {
        List<PhotoResponse> photoResponseList = new ArrayList<>();
        for (PhotoRequest photo: photos){
           PhotoResponse photoSaved = this.addPhoto(photo);
           this.photoOrder(photo.getProductId());
            photoResponseList.add(photoSaved);

        }
        return photoResponseList;
    }

    @Override
    public PhotoResponse editPhoto(PhotoUpdate photoUpdate) {
        // Photo Validation
        Photo photo = this.findByPhotoIdBaseForm(photoUpdate.getPhotoId());

        // Product Validation
        Product product = productService.findProductByIdBaseForm(photoUpdate.getProductId());

        //Edit Changes
        photo.setProduct(product);
        photo.setPhotoValue(photoUpdate.getPhotoValue());
        Photo photoSaved = photoRepository.save(photo);
        //Convert "photoSave" to PhotoResponse
        return this.ToPhotoResponseObj(photoSaved);
    }
    /// Delete a photo object to the database base on a productId
    @Override
    public Boolean deletePhoto(int photoID) {
        //photoId validation
        this.findByPhotoIdBaseForm(photoID);
        //Delete photoObj
        photoRepository.deleteById(photoID);
        return true;
    }

    /// Get All photos objects from the database base on a productId
    @Override
    public List<PhotoResponse> getAllPhotosByProductID(int productID) {
        //photoId validation
        productService.findProductByIdBaseForm(productID);
        //Retrieve all the photos base on the productId
        List<Photo> photoList = photoRepository.findAllPhotosByProductId(productID);

        return photoList.stream().map(this::ToPhotoResponseObj).toList();
    }


    /// This function sets the index in 0, and increase the index of the new Products
    @Override
    public void photoOrder(int productID) {
        if (productID == 0) {
            throw new BadRequestException("ProductID is empty");
        }
        /// Retrieve all the photos base on the productId
        List<Photo> photosList = photoRepository.findByProductIDOrderByPhotosID(productID);
        /// Index assignation
        for (int index = 0; index < photosList.size(); index++) {
            Photo photo = photosList.get(index);
            if (photo.getIndex() != index) {
                photo.setIndex(index);
            }
        }

        photoRepository.saveAll(photosList);
    }



    /// This function is the one used for the product main photo to show
    @Override
    public List<PhotoResponse> getPhotosIndexZero(int productId) {
        //it retrieves all the photos on the databases with index of 0
        List<Photo> photoList = photoRepository.findByIndexZero(productId);
        // Convert each photo from photoList into PhotoResponse type
        return photoList.stream().map(this::ToPhotoResponseObj).toList();
    }
    /// This function is used to Collect all the photos from a store using storeId, it is used to avoid calling multiple times the database
    /// So, it calls one time and after that should make the matches correspondingly
    @Override
    public List<PhotoResponse> findAllPhotosByStoreId(UUID storeId) {
        List<Photo> photoList = photoRepository.findAllPhotosByStoreId(storeId);
        return photoList.stream().map(this::ToPhotoResponseObj).toList();
    }

    @Override
    public PhotoResponse ToPhotoResponseObj(Photo photo) {
        PhotoResponse photoObj = new PhotoResponse();
        photoObj.setPhotoValue(photoObj.getPhotoValue());
        photoObj.setIndex(photoObj.getIndex());
        photoObj.setProductId(photo.getProduct().getProductId());
        return photoObj;
    }

    @Override
    public Photo ToPhotoObj(PhotoRequest photoRequest) {
        /// Product Inicialization
        Product product = productService.findProductByIdBaseForm(photoRequest.getProductId());
        /// Photo transformation
        Photo photo = new Photo();
        photo.setPhotoValue(photoRequest.getPhotoValue());
        photo.setProduct(product);

        return photo;
    }

}
