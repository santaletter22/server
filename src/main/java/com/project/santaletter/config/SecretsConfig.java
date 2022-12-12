package com.project.santaletter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:secrets.yml")
public class SecretsConfig {

    @Value("${aes.key}")
    private String aesKey;

    @Value("${security.password.key}")
    private String securityPasswordKey;

    public String getAesKey() {
        return this.aesKey;
    }

    public String getSecurityPasswordKey() {
        return securityPasswordKey;
    }
}
