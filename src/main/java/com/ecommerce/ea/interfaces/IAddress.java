package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.AddressRequest;
import com.ecommerce.ea.DTOs.response.AddressResponse;
import com.ecommerce.ea.DTOs.update.AddressUpdate;
import com.ecommerce.ea.entities.Address;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IAddress {
    public CompletableFuture<AddressResponse> GetAddressById(int addressId);
    public CompletableFuture<List<AddressResponse>> GetListAddressesByUserId(UUID userId);
    public CompletableFuture<AddressResponse> AddAddress(AddressRequest addressRequest);
    public CompletableFuture<Boolean> DeleteAddress(int addressId);
    public CompletableFuture<AddressResponse> EditAddress(AddressUpdate addressUpdate);
}
