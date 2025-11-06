package com.serviq.api.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "microservices")
public class MicroserviceProperties {

    private Map<String, ServiceConfig> services = new HashMap<>();

    public ServiceConfig getServiceConfig(String serviceName) {
        return services.get(serviceName);
    }

    @Data
    public static class ServiceConfig {
        private String baseUrl;
        private Integer connectionTimeout = 5000;
        private Integer responseTimeout = 5000;
        private Integer readTimeout = 5000;
        private Integer writeTimeout = 5000;
        private Integer maxRetries = 3;
        private Long retryBackoffMillis = 500L;
    }
}
