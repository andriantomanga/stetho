package com.stetho.service;

import com.stetho.config.StethoProperties;
import com.stetho.model.UrlCheck;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class HealthCheckService {
    private final StethoProperties stethoProperties;
    private final UrlChecker urlChecker;

    @PostConstruct
    public void init() {
        checkUrls();
    }

    @Scheduled(fixedRateString = "#{stethoProperties.checkInterval}")
    public void checkUrls() {
        stethoProperties
                .getUrls()
                .forEach(this::updateUrlStatusAndLastChecked);
    }

    private void updateUrlStatusAndLastChecked(UrlCheck urlCheck) {
        var url = urlCheck.getUrl();
        var status = false;
        try {
            status = urlChecker.checkUrl(new URL(url));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL: " + url, e);
        }
        urlCheck.setStatus(status);
        urlCheck.setLastChecked(LocalDateTime.now());
        log.info("Checking availability of {} => Status : {}", url, status ? "OK" : "KO");
    }
}
