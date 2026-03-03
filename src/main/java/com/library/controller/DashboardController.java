package com.library.controller;

import com.library.dto.DashboardSummaryResponse;
import com.library.model.Dashboard;
import com.library.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/dashboards")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Dashboard> create(@RequestBody Dashboard dashboard) {
        return dashboardService.createDashboard(dashboard);
    }

    @GetMapping
    public Flux<Dashboard> getAll() {
        return dashboardService.getAllDashboards();
    }

    @GetMapping("/{id}")
    public Mono<Dashboard> getById(@PathVariable Long id) {
        return dashboardService.getDashboardById(id);
    }

    @PutMapping("/{id}")
    public Mono<Dashboard> update(@PathVariable Long id, @RequestBody Dashboard dashboard) {
        return dashboardService.updateDashboard(id, dashboard);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return dashboardService.deleteDashboard(id);
    }

    @GetMapping("/summary")
    public Mono<DashboardSummaryResponse> getSummary() {
        return dashboardService.getSummary();
    }
}
