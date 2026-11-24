package com.meudia.api.health;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "meu-dia-api");
        response.put("timestamp", java.time.Instant.now().toString());
        return response;
    }

    @GetMapping("/ready")
    public Map<String, Object> readiness() {
        Map<String, Object> response = new HashMap<>();
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            response.put("status", "READY");
            response.put("database", "UP");
        } catch (Exception e) {
            response.put("status", "NOT_READY");
            response.put("database", "DOWN");
            response.put("error", e.getMessage());
        }
        return response;
    }

    @GetMapping("/live")
    public Map<String, Object> liveness() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ALIVE");
        response.put("timestamp", java.time.Instant.now().toString());
        return response;
    }
}