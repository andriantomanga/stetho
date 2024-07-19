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