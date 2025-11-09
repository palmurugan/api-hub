package com.serviq.api.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SearchServiceResponse {

    private String id;

    private String orgId;

    private String serviceId;

    private String title;

    private String categoryId;

    private String category;

    private String providerId;

    private String providerName;

    private String primaryLocation;

    private List<String> locations;

    private Integer duration;

    private String unit;

    private BigDecimal price;

    private String currency;

    private Boolean isActive;
}
