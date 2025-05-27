package com.ecommerce.ea.services;

import com.ecommerce.ea.entities.Address;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.IAddress;
import com.ecommerce.ea.repository.AddressRepository;
import com.ecommerce.ea.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class AddressService implements IAddress {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository){
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CompletableFuture<Address> GetAddressById(int addressId) {

        Address addressObj = addressRepository.findById(addressId)
                .orElseThrow(() -> new BadRequestException("addressId was not found on the database"));

        return CompletableFuture.completedFuture(addressObj);
    }

    @Override
    public CompletableFuture<List<Address>> GetListAddressesByUserId(UUID userId) {

        //userId validation
        userRepository.findById(userId).orElseThrow(() -> new BadRequestException("userId was not found on the database"));
        //retrieve the list of addresses
       return addressRepository.findAllAddressesByUserIdAsync(userId);
    }

    @Transactional
    @Override
    public CompletableFuture<Address> AddAddress(Address address) {
        Address addressObj = addressRepository.save(address);
        return CompletableFuture.completedFuture(addressObj);
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
    public CompletableFuture<Address> EditAddress(Address address) {

       Address addressObj =  addressRepository.findById(address.getAddressId())
               .orElseThrow(() -> new BadRequestException("AddressId was not found on the database"));

       addressObj.setTown(address.getTown());
       addressObj.setColony(address.getColony());
       addressObj.setStreet(address.getStreet());
       addressObj.setCountry(address.getCountry());
       addressObj.setNumber(address.getNumber());

       addressRepository.save(addressObj);

        return CompletableFuture.completedFuture(addressObj);
    }
}
