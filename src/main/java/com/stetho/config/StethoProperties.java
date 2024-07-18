package com.stetho.config;

import com.stetho.model.UrlCheck;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "stetho")
@Data
public class StethoProperties {
    private List<UrlCheck> urls;
    private long checkInterval;
}
