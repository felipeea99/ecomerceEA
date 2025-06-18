package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.AddressRequest;
import com.ecommerce.ea.DTOs.response.AddressResponse;
import com.ecommerce.ea.DTOs.update.AddressUpdate;
import com.ecommerce.ea.entities.Address;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
