package com.serviq.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProviderRequest {

    private String orgId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Provider type is required")
    private String providerType;

    @NotBlank(message = "Display name is required")
    private String displayName;

    private String description;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    private String phone;

    @NotBlank(message = "Address is required")
    private String address;
}
