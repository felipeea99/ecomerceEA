package com.ecommerce.ea.controllers.store;

import com.ecommerce.ea.AOP_Functions.annotations.ValidateStoreAccess;
import com.ecommerce.ea.DTOs.request.store.PhotoRequest;
import com.ecommerce.ea.DTOs.response.store.PhotoResponse;
import com.ecommerce.ea.DTOs.update.store.PhotoUpdate;
import com.ecommerce.ea.services.store.PhotoService;
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
public PhotoResponse addPhoto(@RequestBody PhotoRequest photoRequest) {
    return photoService.addPhoto(photoRequest);
}

/// Edit a photo
@ValidateStoreAccess
@PutMapping("{storeName}")
public PhotoResponse editPhoto(@RequestBody PhotoUpdate photoUpdate) {
    return photoService.editPhoto(photoUpdate);
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
public List<PhotoResponse> getAllPhotosByProductID(@PathVariable int productID) {
    return photoService.getAllPhotosByProductID(productID);
}

/// Get all photos with index 0 by product ID
@ValidateStoreAccess
@GetMapping("/{storeName}/index-zero/{productID}")
public List<PhotoResponse> getPhotosIndexZero(@PathVariable int productID) {
   return photoService.getPhotosIndexZero(productID);
}


}