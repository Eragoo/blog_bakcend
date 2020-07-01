package com.Eragoo.Blog.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("application.security")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityProps {
    private String signature;
    private Duration lifetime;
}
