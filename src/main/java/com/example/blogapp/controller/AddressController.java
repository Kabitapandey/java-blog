package com.example.blogapp.controller;

import com.example.blogapp.dto.AddressDto;
import com.example.blogapp.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            description = "Endpoint for adding address",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid credentials/Unauthorized",
                            responseCode = "403"
                    )
            }
    )
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
    @Operation(
            description = "Endpoint for listing all address",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid credentials/Unauthorized",
                            responseCode = "403"
                    )
            }
    )
    public List<AddressDto> getAllAddress() {
        return this.addressService.getAddress();
    }

    @PutMapping("/address/{id}")
    @Operation(
            description = "Endpoint for updating address",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid credentials/Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Not found",
                            responseCode = "404"
                    )
            }
    )
    public AddressDto updateAddress(@RequestBody AddressDto addressDto, @PathVariable Integer id) {
        return this.addressService.updateAddress(addressDto, id);
    }

    @DeleteMapping("/address/{id}")
    @Operation(
            description = "Endpoint for deleting address",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid credentials/Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Not found",
                            responseCode = "404"
                    )
            }
    )
    public Map<String, Object> deleteAddress(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        this.addressService.deleteAddress(id);

        response.put("msg", "Address deleted successfully!");
        response.put("success", true);

        return response;
    }

    @GetMapping("/address/{id}")
    @Operation(
            description = "Endpoint for getting single address",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid credentials/Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Not found",
                            responseCode = "404"
                    )
            }
    )
    public AddressDto getSingleAddress(@PathVariable Integer id) {
        return this.addressService.getSingleAddress(id);
    }
}
