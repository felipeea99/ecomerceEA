package com.ecommerce.prorider.controllers.store;

import com.ecommerce.prorider.AOP_Functions.annotations.ValidateStoreAccess;
import com.ecommerce.prorider.AOP_Functions.context.StoreContextHolder;
import com.ecommerce.prorider.DTOs.request.store.AddressRequest;
import com.ecommerce.prorider.DTOs.response.store.AddressResponse;
import com.ecommerce.prorider.DTOs.update.store.AddressUpdate;
import com.ecommerce.prorider.services.store.AddressService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    /// Services
    private final AddressService addressService;

    /// Constructor
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /// This EndPoint find an Address object base on the userId that is retrieved from the ValidateStoreAccess using the userId
    @GetMapping("/{addressId}")
    public AddressResponse findAddressById(@PathVariable int addressId){
       return this.addressService.findAddressById(addressId);
    }
    /// This EndPoint search for a List of Address objects, using the ValidateStoreAccess to retrieve the customerId
    @GetMapping("/{userId}")
    public List<AddressResponse> findAddressesByUserId(@PathVariable UUID userId){
        /// Gets the StoreContextHolder.customerId();
        UUID customerId = StoreContextHolder.getCustomerId();

        return this.addressService.findAddressesByUserId(userId);
    }

    /// This EndPoint adds a AddressObj to the database, it passes the customerId to the AddressRequest due to be available to extract the customerId from the
    /// ValidateStoreAccess Aspect
    @PostMapping("/{storeName}/{userId}")
    public AddressResponse addAddress(@Valid @RequestBody AddressRequest addressRequest){
        /// Gets the StoreContextHolder.customerId();
        UUID customerId = StoreContextHolder.getCustomerId();
        /// pass the customerId to the addressRequest object
        addressRequest.setCustomerId(customerId);
        return this.addressService.addAddress(addressRequest);
    }

    /// This EndPoint is used to edit a AddressObj, it receives the storeName and the AddressUpdate object, it does not require customerId
    /// due to is already on the address object
    @PutMapping("/{storeName}/{userId}")
    public AddressResponse editAddress(@Valid @RequestBody AddressUpdate addressUpdate){
        return this.addressService.editAddress(addressUpdate);
    }
    /// This EndPoint is used to delete a AddressObj, it receives the storeName and addressId, it does not require customerId
    /// due to is already on the address object
    @DeleteMapping("/{storeName}/{userId}/{addressId}")
    public Boolean deleteAddress(@PathVariable int addressId ){
        return this.addressService.deleteAddress(addressId);
    }

}
