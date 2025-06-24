package com.ecommerce.ea.interfaces.store;

import com.ecommerce.ea.DTOs.request.store.AddressRequest;
import com.ecommerce.ea.DTOs.response.store.AddressResponse;
import com.ecommerce.ea.DTOs.update.AddressUpdate;
import com.ecommerce.ea.entities.store.Address;

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
