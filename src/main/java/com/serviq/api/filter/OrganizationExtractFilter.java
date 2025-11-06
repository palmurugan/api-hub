package com.serviq.api.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(-1)
public class OrganizationExtractFilter implements WebFilter {

    private static final String ORG_ID = "d9100327-5ae3-41be-922d-64e1c890ac86";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Get Authorization header
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer "

            // Example: extract something from token
            String userId = extractUserIdFromToken(token);

            // Add it to request attributes (can be accessed later)
            exchange.getAttributes().put("userId", userId);
        }

        // Continue filter chain
        return chain.filter(exchange).contextWrite(ctx -> ctx.put("orgId", ORG_ID));
    }

    private String extractUserIdFromToken(String token) {
        // Mock logic: extract something from JWT or custom token
        // You can use JWT libraries like Nimbus or jjwt here.
        return "user-" + token.hashCode();
    }

}
