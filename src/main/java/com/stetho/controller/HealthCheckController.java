package com.stetho.controller;

import com.stetho.service.HealthCheckService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HealthCheckController {
    private final HealthCheckService healthCheckService;

    @GetMapping("/")
    public String getIndex(Model model) {
        var urlChecks = healthCheckService.getStethoProperties().getUrls();
        model.addAttribute("urlChecks", urlChecks);
        return "index";
    }

    @GetMapping("/refresh")
    public String refreshUtlCheckList(Model model) {
        var urlChecks = healthCheckService.getStethoProperties().getUrls();
        model.addAttribute("urlChecks", urlChecks);
        return "fragments/urlCheckList";
    }
}
