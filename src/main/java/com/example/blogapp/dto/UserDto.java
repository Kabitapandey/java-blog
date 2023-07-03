package com.example.blogapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private int id;

    @NotBlank(message = "First name must be provided")
    @Size(min = 2, max = 100, message = "Firstname must be in range of length of 2 to 100")
    private String firstName;

    @NotBlank(message = "Last name must be provided")
    @Size(min = 2, max = 100, message = "Lastname must be in range of length of 2 to 100")
    private String lastName;

    @NotBlank(message = "Email must be provided")
    @Email(message = "Invalid email format")

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password must be provided")
    private String password;

    private String roles;

    private List<AddressDto> address;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "Address is must be provided")
    private List<Integer> addressId;

    private List<BlogDto> blogs;
}
