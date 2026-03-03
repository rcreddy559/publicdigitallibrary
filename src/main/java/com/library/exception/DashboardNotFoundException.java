package com.library.exception;

public class DashboardNotFoundException extends RuntimeException {

    public DashboardNotFoundException(Long id) {
        super("Dashboard not found with id: " + id);
    }
}
