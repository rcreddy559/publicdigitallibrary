package com.library.service.impl;

import com.library.dto.DashboardSummaryResponse;
import com.library.exception.DashboardNotFoundException;
import com.library.model.Dashboard;
import com.library.repository.BookRepository;
import com.library.repository.DashboardRepository;
import com.library.repository.UserRepository;
import com.library.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public Mono<Dashboard> createDashboard(Dashboard dashboard) {
        dashboard.setId(null);
        return dashboardRepository.save(dashboard);
    }

    @Override
    public Flux<Dashboard> getAllDashboards() {
        return dashboardRepository.findAll();
    }

    @Override
    public Mono<Dashboard> getDashboardById(Long id) {
        return dashboardRepository.findById(id)
                .switchIfEmpty(Mono.error(new DashboardNotFoundException(id)));
    }

    @Override
    public Mono<Dashboard> updateDashboard(Long id, Dashboard dashboard) {
        return getDashboardById(id)
                .flatMap(existing -> {
                    existing.setWidgetName(dashboard.getWidgetName());
                    existing.setWidgetType(dashboard.getWidgetType());
                    existing.setStatus(dashboard.getStatus());
                    existing.setDisplayOrder(dashboard.getDisplayOrder());
                    return dashboardRepository.save(existing);
                });
    }

    @Override
    public Mono<Void> deleteDashboard(Long id) {
        return getDashboardById(id).flatMap(existing -> dashboardRepository.deleteById(existing.getId()));
    }

    @Override
    public Mono<DashboardSummaryResponse> getSummary() {
        return Mono.zip(
                        userRepository.count(),
                        bookRepository.count(),
                        dashboardRepository.count(),
                        userRepository.countActiveUsers(),
                        bookRepository.countAvailableBooks()
                )
                .map(tuple -> DashboardSummaryResponse.builder()
                        .totalUsers(tuple.getT1())
                        .totalBooks(tuple.getT2())
                        .totalDashboardWidgets(tuple.getT3())
                        .activeUsers(tuple.getT4())
                        .availableBooks(tuple.getT5())
                        .build());
    }
}
