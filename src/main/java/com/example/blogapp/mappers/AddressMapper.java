package com.example.blogapp.mappers;

import com.example.blogapp.dto.AddressDto;
import com.example.blogapp.entities.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toEntity(AddressDto addressDto);

    AddressDto toDto(Address address);
}
