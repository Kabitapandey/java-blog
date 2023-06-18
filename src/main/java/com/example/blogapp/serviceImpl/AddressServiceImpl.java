package com.example.blogapp.serviceImpl;

import com.example.blogapp.dto.AddressDto;
import com.example.blogapp.entities.Address;
import com.example.blogapp.exception.NotFoundException;
import com.example.blogapp.mappers.AddressMapper;
import com.example.blogapp.repository.AddressRepository;
import com.example.blogapp.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public void addAddress(AddressDto addressDto) {
        Address add = this.addressMapper.toEntity(addressDto);
        this.addressRepository.save(add);
    }

    @Override
    public List<AddressDto> getAddress() {
        List<Address> addresses = this.addressRepository.findAll();
        List<AddressDto> addressDtos = new ArrayList<>();

        for (Address add : addresses) {
            addressDtos.add(this.addressMapper.toDto(add));
        }

        return addressDtos;
    }

    @Override
    public AddressDto updateAddress(AddressDto addressDto, Integer id) {
        Address add = this.addressRepository.findById(id).orElseThrow(() -> new NotFoundException("Address"));

        if (addressDto.getAddress() != null) add.setAddress(addressDto.getAddress());


        Address updatedAddress = this.addressRepository.save(add);
        return this.addressMapper.toDto(updatedAddress);
    }

    @Override
    public void deleteAddress(Integer id) {
        Address address = this.addressRepository.findById(id).orElseThrow(() -> new NotFoundException("Address"));

        this.addressRepository.delete(address);
    }

    @Override
    public AddressDto getSingleAddress(Integer id) {
        Address address = this.addressRepository.findById(id).orElseThrow(() -> new NotFoundException("Address"));

        return this.addressMapper.toDto(address);
    }

}
