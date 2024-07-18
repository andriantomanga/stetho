package com.stetho.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Data
@EqualsAndHashCode(of = "url")
public class UrlCheck {
    private String name;
    private String url;
    private boolean status;
    private LocalDateTime lastChecked;

    public String getLastCheckedFormatted() {
        return lastChecked != null ? lastChecked.format(DateTimeFormatter.ofPattern("HH:mm:ss")) : null;
    }
}