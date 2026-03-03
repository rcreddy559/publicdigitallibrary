package com.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryResponse {

    private Long totalUsers;
    private Long totalBooks;
    private Long totalDashboardWidgets;
    private Long activeUsers;
    private Long availableBooks;
}
