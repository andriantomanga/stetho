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
