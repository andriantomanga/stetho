package com.stetho.service;

import com.stetho.config.StethoProperties;
import com.stetho.model.UrlCheck;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HealthCheckServiceTest {
    @Mock
    private StethoProperties stethoProperties;
    @Mock
    private UrlChecker urlChecker;
    @InjectMocks
    private HealthCheckService sut;

    @Test
    void should_correctly_update_all_url_status_and_last_checked() {
        // Given
        var urlChecks = List.of(
                UrlCheck.builder().url("http://example.com").build(),
                UrlCheck.builder().url("http://example.org").build(),
                UrlCheck.builder().url("http://invalid-url").build()
        );
        when(stethoProperties.getUrls()).thenReturn(urlChecks);
        when(urlChecker.checkUrl(any())).thenAnswer(i -> {
            URL currentUrl = i.getArgument(0);
            return !currentUrl.toString().contains("http://invalid-url");
        });

        // When
        sut.checkUrls();

        // Then
        urlChecks.forEach(urlCheck -> {
            if ("http://invalid-url".equals(urlCheck.getUrl())) {
                assertFalse(urlCheck.isStatus());
            } else {
                assertTrue(urlCheck.isStatus());
            }
            assertNotNull(urlCheck.getLastChecked());
        });
    }
}