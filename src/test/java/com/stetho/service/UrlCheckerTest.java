/**
 * MIT License
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p>
 * Author: Nabil Andriantomanga
 */
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