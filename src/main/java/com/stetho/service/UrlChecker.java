package com.stetho.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@Slf4j
public class UrlChecker {
    private static final int CNX_OK_RESPONSE_CODE = 200;
    private static final String GET_REQUEST_METHOD = "GET";

    public boolean checkUrl(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(GET_REQUEST_METHOD);
            connection.connect();
            return CNX_OK_RESPONSE_CODE == connection.getResponseCode();
        } catch (IOException e) {
            log.error("Error checking URL {}: {}", url, e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
