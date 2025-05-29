package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.AddressRequest;
import com.ecommerce.ea.DTOs.response.AddressResponse;
import com.ecommerce.ea.DTOs.update.AddressUpdate;
import com.ecommerce.ea.entities.Address;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.IAddress;
import com.ecommerce.ea.repository.AddressRepository;
import com.ecommerce.ea.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AddressService implements IAddress {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository){
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CompletableFuture<AddressResponse> GetAddressById(int addressId) {

        //Search it and stores it on the variable
        Address addressObj = addressRepository.findById(addressId)
                .orElseThrow(() -> new BadRequestException("addressId was not found on the database"));
        //Convert it to AddressResponse
        AddressResponse addressResponse = AddressResponse.toAddressResponseObj(addressObj);

        return CompletableFuture.completedFuture(addressResponse);
    }

    @Override
    public CompletableFuture<List<AddressResponse>> GetListAddressesByUserId(UUID userId) {
        //userId validation
        userRepository.findById(userId).orElseThrow(() -> new BadRequestException("userId was not found on the database"));
        //retrieve the list of addresses, then once is ready apply the stream to enable functional methods, to be available to map them, then collect them in a list
        return addressRepository.findAllAddressesByUserIdAsync(userId)
                .thenApply(addressList ->
                        addressList.stream()
                                .map(AddressResponse::toAddressResponseObj)
                                .collect(Collectors.toList())
                );
    }

    @Transactional
    @Override
    public CompletableFuture<AddressResponse> AddAddress(AddressRequest addressRequest) {
        //Convert it to Address Object
        Address addressObj =  addressRequest.ToAddressObj();
        //Save it and stores it on the variable
        Address addressSaved = addressRepository.save(addressObj);
        //Convert it to Response
        AddressResponse addressResponse = AddressResponse.toAddressResponseObj(addressSaved);

        return CompletableFuture.completedFuture(addressResponse);
    }

    @Override
    public CompletableFuture<Boolean> DeleteAddress(int addressId) {
        try {
            addressRepository.deleteById(addressId);
            return CompletableFuture.completedFuture(true);
        }
        catch (Exception e) {
            return CompletableFuture.completedFuture(false);
        }
    }

    @Override
    public CompletableFuture<AddressResponse> EditAddress(AddressUpdate addressUpdate) {
        //AddressId validation
       Address addressObj =  addressRepository.findById(addressUpdate.getAddressId())
               .orElseThrow(() -> new BadRequestException("AddressId was not found on the database"));
       //Make the changes
       addressObj.setTown(addressUpdate.getTown());
       addressObj.setColony(addressUpdate.getColony());
       addressObj.setStreet(addressUpdate.getStreet());
       addressObj.setCountry(addressUpdate.getCountry());
       addressObj.setNumber(addressUpdate.getNumber());
       //Save changes
       Address addressSaved = addressRepository.save(addressObj);

       AddressResponse addressResponse = AddressResponse.toAddressResponseObj(addressSaved);

        return CompletableFuture.completedFuture(addressResponse);
    }
}
