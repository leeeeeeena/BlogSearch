package com.yurim.blogsearch.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "circuit")
public class CircuitConfigProperties {

    private int failureRate;

    private int waitDurationInOpenState;

    private int timeoutDurationSecond;

    private int slidingWindowSize;

}
