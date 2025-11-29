package com.serviq.api.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
public class SearchServiceResponse {

    private UUID id;
    private UUID orgId;
    private UUID providerId;
    private UUID categoryId;
    private String providerName;
    private String title;
    private String description;
    private Integer duration;
    private String unit;
    private BigDecimal price;
    private String currency;
    private Integer maxCapacity;
    private Boolean isActive;
    private Map<String, Object> metadata;
    private String primaryLocation;
    private Set<LocationResponse> locations;
}
