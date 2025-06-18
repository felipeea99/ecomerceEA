package com.ecommerce.ea.controllers;

import com.ecommerce.ea.AOP_Functions.annotations.ValidateStoreAccess;
import com.ecommerce.ea.DTOs.request.PhotoRequest;
import com.ecommerce.ea.DTOs.response.PhotoResponse;
import com.ecommerce.ea.DTOs.update.PhotoUpdate;
import com.ecommerce.ea.services.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {
///Services
private final PhotoService photoService;
/// Constructor
public PhotoController(PhotoService photoService) {
    this.photoService = photoService;
}

/// Add a single photo
@ValidateStoreAccess
@PostMapping("{storeName}")
public ResponseEntity<PhotoResponse> addPhoto(@RequestBody PhotoRequest photoRequest) {
    PhotoResponse createdPhoto = photoService.addPhoto(photoRequest);
    return ResponseEntity.ok(createdPhoto);
}

/// Edit a photo
@ValidateStoreAccess
@PutMapping("{storeName}")
public ResponseEntity<PhotoResponse> editPhoto(@RequestBody PhotoUpdate photoUpdate) {
    PhotoResponse updatedPhoto = photoService.editPhoto(photoUpdate);
    return ResponseEntity.ok(updatedPhoto);
}

/// Delete a photo by ID
@ValidateStoreAccess
@DeleteMapping("{storeName}/{photoID}")
public ResponseEntity<Boolean> deletePhoto(@PathVariable int photoID) {
    boolean deleted = photoService.deletePhoto(photoID);
    return ResponseEntity.ok(deleted);
}

/// Get all photos by product ID
@ValidateStoreAccess
@GetMapping("{storeName}/product/{productID}")
public ResponseEntity<List<PhotoResponse>> getAllPhotosByProductID(@PathVariable int productID) {
    List<PhotoResponse> photos = photoService.getAllPhotosByProductID(productID);
    return ResponseEntity.ok(photos);
}

/// Get all photos with index 0 by product ID
@ValidateStoreAccess
@GetMapping("/{storeName}/index-zero/{productID}")
public ResponseEntity<List<PhotoResponse>> getPhotosIndexZero(@PathVariable int productID) {
    List<PhotoResponse> photos = photoService.getPhotosIndexZero(productID);
    return ResponseEntity.ok(photos);
}


}