package com.verizon.netpulse.performance_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.verizon.netpulse.performance_backend.model.NetworkMetrics;
import com.verizon.netpulse.performance_backend.service.NetworkMetricsService;

@Controller
public class NetworkMetricsController {

    @Autowired
    private NetworkMetricsService networkMetricsService;

    @GetMapping("/metrics/{userId}")
    public ResponseEntity<List<NetworkMetrics>> getMetricsByUserId(@PathVariable Integer userId) {
        List<NetworkMetrics> metrics = networkMetricsService.getMetricsByUserId(userId);
        if (metrics.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/performance")
    public String getMetrics(@RequestParam Integer userId, Model model) {
        // Assuming the userId is stored in the session
        // Integer userId = (Integer) model.getAttribute("userId");
        
        // Fetch the metrics for the user
        List<NetworkMetrics> metrics = networkMetricsService.getMetricsByUserId(userId);
        
        // Add the metrics to the model to pass it to the Thymeleaf template
        model.addAttribute("metrics", metrics);
        model.addAttribute("userId", userId);
        System.out.println(model);
        
        return "metric"; // returns the Thymeleaf template named "metrics.html"
    }
}

