package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.entities.Address;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IAddress {
    public CompletableFuture<Address> GetAddressById(int addressId);
    public CompletableFuture<List<Address>> GetListAddressesByUserId(UUID userId);
    public CompletableFuture<Address> AddAddress(Address address);
    public CompletableFuture<Boolean> DeleteAddress(int addressId);
    public CompletableFuture<Address> EditAddress(Address address);
}
