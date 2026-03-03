INSERT INTO library_user (full_name, email, phone, active) VALUES
('Aarav Sharma', 'aarav.sharma@example.com', '+91-9876543210', TRUE),
('Emma Johnson', 'emma.johnson@example.com', '+1-555-1144', TRUE),
('Noah Williams', 'noah.williams@example.com', '+1-555-9911', FALSE);

INSERT INTO book (title, author, isbn, published_year, available) VALUES
('Reactive Spring', 'Josh Long', 'ISBN-1001', 2020, TRUE),
('Clean Code', 'Robert C. Martin', 'ISBN-1002', 2008, TRUE),
('Domain-Driven Design', 'Eric Evans', 'ISBN-1003', 2003, FALSE);

INSERT INTO dashboard (widget_name, widget_type, status, display_order) VALUES
('User Activity', 'LINE_CHART', 'ACTIVE', 1),
('Book Availability', 'PIE_CHART', 'ACTIVE', 2),
('Overdue Requests', 'TABLE', 'INACTIVE', 3);
