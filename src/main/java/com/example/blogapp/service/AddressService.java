package com.example.blogapp.service;

import com.example.blogapp.dto.AddressDto;

import java.util.List;


public interface AddressService {
    void addAddress(AddressDto address);

    List<AddressDto> getAddress();

    AddressDto updateAddress(AddressDto address, Integer id);

    void deleteAddress(Integer id);

    AddressDto getSingleAddress(Integer id);
}
