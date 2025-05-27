package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.entities.Address;

import java.util.List;

public interface IAddress {
    public Address GetAddressByUserId();
    public Address GetAddressByAddressId();
    public Address addAddress();
    public boolean deleteAddress();
    public Address editAddress();
    public List<Address> GetListAddressByUserId();
}
