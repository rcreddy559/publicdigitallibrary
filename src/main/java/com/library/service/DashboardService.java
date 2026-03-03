package com.library.service;

import com.library.dto.DashboardSummaryResponse;
import com.library.model.Dashboard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DashboardService {

    Mono<Dashboard> createDashboard(Dashboard dashboard);

    Flux<Dashboard> getAllDashboards();

    Mono<Dashboard> getDashboardById(Long id);

    Mono<Dashboard> updateDashboard(Long id, Dashboard dashboard);

    Mono<Void> deleteDashboard(Long id);

    Mono<DashboardSummaryResponse> getSummary();
}
