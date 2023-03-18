package com.yurim.blogsearch.config;

import com.yurim.blogsearch.config.properties.CircuitConfigProperties;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class CircuitConfig {

    /**
     * circuitbreaker 설정값을 동적으로 받아 FAILER 정책 반영할 수 있도록 함 (application.yml 파일 수정)
     * Hystrix, Sentinel, Spring Retry 등 다른 대안도 함께 사용 가능하나, Resilience에서 RateLimiter, Bulkhead, Retry 등도 적용 가능.
     * **/
    private final CircuitConfigProperties configProperties;


    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfig() {

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(configProperties.getFailureRate()) //decide circuitBreaker 'open' or not. default 50%
                .waitDurationInOpenState(Duration.ofMillis(configProperties.getWaitDurationInOpenState())) // maintain 'open' time. After DurationInOpenState, circuitBreaker's status modify to 'half-open'
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // count base OR time base . calculate failure rate
                .slidingWindowSize(configProperties.getSlidingWindowSize())
                .build();

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(configProperties.getTimeoutDurationSecond())) //configure fail
                .build();


        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build());
    }

}
