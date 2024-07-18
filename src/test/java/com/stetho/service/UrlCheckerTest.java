package com.stetho.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlCheckerTest {
    @Mock
    private URL url;
    @Mock
    private HttpURLConnection mockConnection;

    @Test
    public void should_return_true_when_url_cnx_is_ok() throws IOException {
        // Given
        when(mockConnection.getResponseCode()).thenReturn(200);
        when(url.openConnection()).thenReturn(mockConnection);

        // When
        assertTrue(new UrlChecker().checkUrl(url));

        // Then
        assertAll(
                () -> verify(mockConnection).setRequestMethod("GET"),
                () -> verify(mockConnection).connect(),
                () -> verify(mockConnection).disconnect()
        );
    }

    @Test
    public void should_return_false_when_url_cnx_is_not_ok() throws IOException {
        // Given
        when(mockConnection.getResponseCode()).thenReturn(404);
        when(url.openConnection()).thenReturn(mockConnection);

        // When
        assertFalse(new UrlChecker().checkUrl(url));

        // Then
        assertAll(
                () -> verify(mockConnection).setRequestMethod("GET"),
                () -> verify(mockConnection).connect(),
                () -> verify(mockConnection).disconnect()
        );
    }

    @Test
    public void should_return_false_when_exception_is_thrown_during_cnx() throws IOException {
        // Given
        when(mockConnection.getResponseCode()).thenThrow(new IOException("Simulated IOException"));
        when(url.openConnection()).thenReturn(mockConnection);

        // When
        assertFalse(new UrlChecker().checkUrl(url));

        // Then
        assertAll(
                () -> verify(mockConnection).setRequestMethod("GET"),
                () -> verify(mockConnection).connect(),
                () -> verify(mockConnection).disconnect()
        );
    }
}