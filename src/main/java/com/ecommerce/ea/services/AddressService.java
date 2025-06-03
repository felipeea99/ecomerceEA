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
import reactor.core.publisher.Mono;

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
    public Mono<AddressResponse> GetAddressById(int addressId) {
        //Search the addressId and return the object
       return addressRepository.findById(addressId)
                .switchIfEmpty(Mono.error(new BadRequestException("addressId was not found on the database")))
               .map(AddressResponse::toAddressResponseObj);
    }

    @Override
    public Mono<List<AddressResponse>> GetListAddressesByUserId(UUID userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new BadRequestException("userId was not found on the database")))
                .flatMap(user ->
                        addressRepository.findAllAddressesByUserIdAsync(userId) //search all the ones with the given userId
                                .switchIfEmpty(Mono.error(new BadRequestException("No addresses found for user"))) //throws exception if any
                                .map(addressList ->
                                        addressList.stream()
                                                .map(AddressResponse::toAddressResponseObj)
                                                .collect(Collectors.toList())
                                )
                );
    }

    @Transactional
    @Override
    public Mono<AddressResponse> AddAddress(AddressRequest addressRequest) {
        //Convert it to Address Object
        Address addressObj =  addressRequest.ToAddressObj();
        //Save it and stores it on the variable
        Mono<Address> addressSaved = addressRepository.save(addressObj);
        //Convert it to Response
        return addressSaved.map(AddressResponse::toAddressResponseObj);

    }

    @Override
    public Mono<Boolean> DeleteAddress(int addressId) {
          return addressRepository.deleteById(addressId)
                  .thenReturn(true)
                  .onErrorReturn(false);
    }

    @Override
    public Mono<AddressResponse> EditAddress(AddressUpdate addressUpdate) {
        return addressRepository.findById(addressUpdate.getAddressId()) //Get the addressId
                .switchIfEmpty(Mono.error(new BadRequestException("AddressId was not found on the database"))) //throw any exception if any
                .flatMap(address -> {
                    // Make the changes
                    address.setTown(addressUpdate.getTown());
                    address.setColony(addressUpdate.getColony());
                    address.setStreet(addressUpdate.getStreet());
                    address.setCountry(addressUpdate.getCountry());
                    address.setNumber(addressUpdate.getNumber());
                    // save the object
                    return addressRepository.save(address);
                })
                .map(AddressResponse::toAddressResponseObj); //apply the response method
    }

}
