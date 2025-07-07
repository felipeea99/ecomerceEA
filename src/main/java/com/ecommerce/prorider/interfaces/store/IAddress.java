package com.ecommerce.prorider.interfaces.store;

import com.ecommerce.prorider.DTOs.request.store.AddressRequest;
import com.ecommerce.prorider.DTOs.response.store.AddressResponse;
import com.ecommerce.prorider.DTOs.update.store.AddressUpdate;
import com.ecommerce.prorider.entities.store.Address;

import java.util.List;
import java.util.UUID;

public interface IAddress {
    Address findAddressByIdBaseForm(int addressId);
    AddressResponse findAddressById(int addressId, UUID customerId);
    List<AddressResponse> findAddressesByUserId(UUID userId);
    AddressResponse addAddress(AddressRequest addressRequest);
    Boolean deleteAddress(int addressId);
    AddressResponse editAddress(AddressUpdate addressUpdate);
    AddressResponse ToAddressResponse(Address address);
    Address ToAddressObj(AddressRequest addressRequest);
}
