package com.example.controlleradvice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@ConfigurationProperties(prefix = "redis")
@Component
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisCacheNameDto {

    private HashMap<String, Long> cacheNames;
}
