package com.ecommerce.prorider.services.store;

import com.ecommerce.prorider.DTOs.request.store.AddressRequest;
import com.ecommerce.prorider.DTOs.response.store.AddressResponse;
import com.ecommerce.prorider.DTOs.update.store.AddressUpdate;
import com.ecommerce.prorider.entities.store.Address;
import com.ecommerce.prorider.exceptions.BadRequestException;
import com.ecommerce.prorider.interfaces.store.IAddress;
import com.ecommerce.prorider.repository.store.AddressRepository;
import com.ecommerce.prorider.services.auth.UserAccService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService implements IAddress {

    private final AddressRepository addressRepository;
    private final UserAccService userAccService;

    public AddressService(AddressRepository addressRepository, UserAccService userAccService){
        this.addressRepository = addressRepository;
        this.userAccService = userAccService;
    }

    @Override
    public Address findAddressByIdBaseForm(int addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new BadRequestException("addressId was not found on the database"));
    }

    /// Retrieve an Address Object from the database using a addressId
    @Override
    @Async
    public AddressResponse findAddressById(int addressId) {
        Address addressObj = this.findAddressByIdBaseForm(addressId);
        //Transform the object from Address to AddressResponse and return it
        return this.ToAddressResponse(addressObj);

    }

    /// Retrieve a List of Addresses from the database using a userId
    @Override
    public List<AddressResponse> findAddressesByUserId(UUID customerId) {
        //Search the customerId and store the objects on the List
        List<Address> addressList = addressRepository.findAllAddressesByCustomerId(customerId);
        //Convert the "addressList" of Address type to AddressResponse and return it
        return addressList.stream().map(this::ToAddressResponse).toList();
    }

    /// Add an Address Object to the database
    @Transactional
    @Override
    public AddressResponse addAddress(AddressRequest addressRequest) {
        // Convert to Address entity
        Address addressObj = this.ToAddressObj(addressRequest);
        Address addressSaved = addressRepository.save(addressObj);
        //return the AddressResponse Object
        return this.ToAddressResponse(addressSaved);
    }


    /// Delete an Address Object from the database base on and addressId
    @Override
    public Boolean deleteAddress(int addressId) {
        //Verify if the addressId exists
        this.findAddressByIdBaseForm(addressId);
        //Deletes the addressObj from the database
            addressRepository.deleteById(addressId);
        //Returns the value
            return true;
    }

    /// Edits an Address Object of the database base on and AddressUpdate.addressId
    @Override
    public AddressResponse editAddress(AddressUpdate addressUpdate) {
        //AddressId validation
        Address address =  this.findAddressByIdBaseForm(addressUpdate.getAddressId());
        // Make the changes
        address.setTown(addressUpdate.getTown());
        address.setColony(addressUpdate.getColony());
        address.setStreet(addressUpdate.getStreet());
        address.setCountry(addressUpdate.getCountry());
        address.setNumber(addressUpdate.getNumber());
        // save the object on the database and store it in the "addressSaved" variable
        Address addressSaved = addressRepository.save(address);
        return  this.ToAddressResponse(addressSaved);
    }
    /// Address Transformations
    @Override
    public AddressResponse ToAddressResponse(Address address) {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setAddressId(address.getAddressId());
        addressResponse.setTown(address.getTown());
        addressResponse.setNumber(address.getNumber());
        addressResponse.setCountry(address.getCountry());
        addressResponse.setStreet(address.getStreet());
        addressResponse.setColony(address.getColony());
        return addressResponse;
    }

    @Override
    public Address ToAddressObj(AddressRequest addressRequest) {
        //User validation
      UserAccService user = userAccService.findByUserId(addressRequest.getCustomerId());
        //Transformation
        Address address = new Address();
        address.setCountry(addressRequest.getCountry());
        address.setStreet(addressRequest.getStreet());
        address.setNumber(addressRequest.getNumber());
        address.setColony(addressRequest.getColony());
        address.setTown(addressRequest.getTown());
        address.setCustomer(customer);
        return address;
    }

}
