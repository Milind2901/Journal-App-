package net.engineeringdigest.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // made a bean with endpoint
public class HealthCheck {

    @GetMapping("/health-check")  // Mapping the fn with a path having GET call
    public String healthCheck(){

        return "OK";

    }
}
