package com.example.blogapp.controller;

import com.example.blogapp.dto.AddressDto;
import com.example.blogapp.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AddressController {
    private final AddressService addressService;


    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/address")
    public Map<String, Object> addAddress(@Valid @RequestBody AddressDto addressDto, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach((err) -> {
                String msg = err.getDefaultMessage();
                String field = ((FieldError) err).getField();
                response.put(field, msg);
            });

            return response;
        }

        this.addressService.addAddress(addressDto);

        response.put("msg", "Address added successfully!");
        response.put("success", true);

        return response;
    }

    @GetMapping("/address")
    public List<AddressDto> getAllAddress() {
        return this.addressService.getAddress();
    }

    @PutMapping("/address/{id}")
    public AddressDto updateAddress(@RequestBody AddressDto addressDto, @PathVariable Integer id) {
        return this.addressService.updateAddress(addressDto, id);
    }

    @DeleteMapping("/address/{id}")
    public Map<String, Object> deleteAddress(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        this.addressService.deleteAddress(id);

        response.put("msg", "Address deleted successfully!");
        response.put("success", true);

        return response;
    }

    @GetMapping("/address/{id}")
    public AddressDto getSingleAddress(@PathVariable Integer id) {
        return this.addressService.getSingleAddress(id);
    }
}
