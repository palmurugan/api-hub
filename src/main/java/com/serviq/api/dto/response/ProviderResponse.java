package com.serviq.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderResponse {
    private String id;
    private String orgId;
    private String name;
    private String providerType;
    private String displayName;
    private String description;
    private String email;
    private String phone;
    private String address;
    private String timezone;
    private Boolean isActive;
}
