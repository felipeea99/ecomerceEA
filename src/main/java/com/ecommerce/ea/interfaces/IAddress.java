package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.AddressRequest;
import com.ecommerce.ea.DTOs.response.AddressResponse;
import com.ecommerce.ea.DTOs.update.AddressUpdate;
import com.ecommerce.ea.entities.Address;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IAddress {
    public Mono<AddressResponse> GetAddressById(int addressId);
    public Mono<List<AddressResponse>> GetListAddressesByUserId(UUID userId);
    public Mono<AddressResponse> AddAddress(AddressRequest addressRequest);
    public Mono<Boolean> DeleteAddress(int addressId);
    public Mono<AddressResponse> EditAddress(AddressUpdate addressUpdate);
}
