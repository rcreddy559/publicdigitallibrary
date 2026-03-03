package com.library.repository;

import com.library.model.Dashboard;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DashboardRepository extends ReactiveCrudRepository<Dashboard, Long> {
}
