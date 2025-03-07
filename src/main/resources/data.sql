-- Initialize Roles
INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO roles (id, name) VALUES (2, 'ROLE_STAFF') ON CONFLICT DO NOTHING;
INSERT INTO roles (id, name) VALUES (3, 'ROLE_ADMIN') ON CONFLICT DO NOTHING;

-- Initial Admin User (email: admin@aptio.com, password: password)
-- Password is BCrypt hashed and would be 'password' for this initial user
INSERT INTO users (id, email, password, first_name, last_name, active, created_at, updated_at)
VALUES ('1', 'admin@aptio.com', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'Admin', 'User', true, '2025-02-26 12:00:00', '2025-02-26 12:00:00')
    ON CONFLICT DO NOTHING;
--
-- -- Assign Admin Role
INSERT INTO user_roles (user_id, role_id) VALUES ('1', 3) ON CONFLICT DO NOTHING;

-- Initialize Service Categories
INSERT INTO service_categories (id, name, description, active, created_at, updated_at)
VALUES ('1', 'Hair', 'Hair cutting, styling, and coloring services', true, '2025-02-26 12:00:00', '2025-02-26 12:00:00') ON CONFLICT DO NOTHING;

INSERT INTO service_categories (id, name, description, active, created_at, updated_at)
VALUES ('2', 'Nails', 'Manicure, pedicure, and nail art services', true, '2025-02-26 12:00:00', '2025-02-26 12:00:00') ON CONFLICT DO NOTHING;

INSERT INTO service_categories (id, name, description, active, created_at, updated_at)
VALUES ('3', 'Spa', 'Relaxation and wellness treatments', true, '2025-02-26 12:00:00', '2025-02-26 12:00:00') ON CONFLICT DO NOTHING;

INSERT INTO service_categories (id, name, description, active, created_at, updated_at)
VALUES ('4', 'Consultation', 'Style and beauty consultations', true, '2025-02-26 12:00:00', '2025-02-26 12:00:00') ON CONFLICT DO NOTHING;

-- Initialize Services
-- Hair Services
INSERT INTO services (id, name, description, duration, price, active, category_id, created_at, updated_at, image_url)
VALUES ('101', 'Haircut', 'Basic haircut service with styling', 30, 35.00, true, '1', '2025-02-26 12:00:00', '2025-02-26 12:00:00', 'https://images.unsplash.com/photo-1596178060810-72660affe577?q=80&w=300') ON CONFLICT DO NOTHING;

INSERT INTO services (id, name, description, duration, price, active, category_id, created_at, updated_at, image_url)
VALUES ('102', 'Hair Coloring', 'Full hair coloring service with premium products', 120, 120.00, true, '1', '2025-02-26 12:00:00', '2025-02-26 12:00:00', 'https://images.unsplash.com/photo-1560966571-385ea47ff258?q=80&w=300') ON CONFLICT DO NOTHING;

INSERT INTO services (id, name, description, duration, price, active, category_id, created_at, updated_at)
VALUES ('103', 'Beard Trim', 'Professional beard trimming and shaping', 20, 15.00, true, '1', '2025-02-26 12:00:00', '2025-02-26 12:00:00') ON CONFLICT DO NOTHING;

-- Nails Services
INSERT INTO services (id, name, description, duration, price, active, category_id, created_at, updated_at, image_url)
VALUES ('201', 'Manicure', 'Basic manicure service with polish', 45, 25.00, true, '2', '2025-02-26 12:00:00', '2025-02-26 12:00:00', 'https://images.unsplash.com/photo-1604654894610-df63bc536371?q=80&w=300') ON CONFLICT DO NOTHING;

INSERT INTO services (id, name, description, duration, price, active, category_id, created_at, updated_at)
VALUES ('202', 'Pedicure', 'Relaxing pedicure service with exfoliation', 60, 35.00, true, '2', '2025-02-26 12:00:00', '2025-02-26 12:00:00') ON CONFLICT DO NOTHING;

INSERT INTO services (id, name, description, duration, price, active, category_id, created_at, updated_at)
VALUES ('203', 'Gel Nails', 'Long-lasting gel nail application', 75, 45.00, true, '2', '2025-02-26 12:00:00', '2025-02-26 12:00:00') ON CONFLICT DO NOTHING;

-- Spa Services
INSERT INTO services (id, name, description, duration, price, active, category_id, created_at, updated_at, image_url)
VALUES ('301', 'Facial', 'Refreshing facial treatment for all skin types', 45, 55.00, true, '3', '2025-02-26 12:00:00', '2025-02-26 12:00:00', 'https://images.unsplash.com/photo-1596178060671-7a80dc8059ea?q=80&w=300') ON CONFLICT DO NOTHING;

INSERT INTO services (id, name, description, duration, price, active, category_id, created_at, updated_at, image_url)
VALUES ('302', 'Massage', 'Full body relaxation massage', 60, 70.00, true, '3', '2025-02-26 12:00:00', '2025-02-26 12:00:00', 'https://images.unsplash.com/photo-1600334129128-685c5582fd35?q=80&w=300') ON CONFLICT DO NOTHING;

-- Consultation Services
INSERT INTO services (id, name, description, duration, price, active, category_id, created_at, updated_at)
VALUES ('401', 'Style Consultation', 'Personal style consultation with our experts', 30, 40.00, false, '4', '2025-02-26 12:00:00', '2025-02-26 12:00:00') ON CONFLICT DO NOTHING;

-- Initialize Business Settings
INSERT INTO business_settings (id, business_name, business_hours_start, business_hours_end, days_open,
                               default_appointment_duration, time_slot_interval, allow_overlapping_appointments,
                               buffer_time_between_appointments, address, phone, email, website, updated_at)
VALUES (1, 'Aptio Appointment System', '09:00:00', '18:00:00', '0111110', 30, 15, false, 5,
        '123 Business St, City, State 12345', '555-123-4567', 'contact@aptio.com', 'https://aptio.com', '2025-02-26 12:00:00')
    ON CONFLICT DO NOTHING;

-- Initialize Sample Customers
INSERT INTO customers (id, first_name, last_name, email, phone, birth_date, gender, active, total_visits, total_spent, registration_date, updated_at, profile_image)
VALUES ('1001', 'John', 'Doe', 'john.doe@example.com', '555-123-4567', '1985-06-15', 'Male', true, 5, 350.00, '2025-02-26 12:00:00', '2025-02-26 12:00:00', 'https://randomuser.me/api/portraits/men/1.jpg')
    ON CONFLICT DO NOTHING;

INSERT INTO customers (id, first_name, last_name, email, phone, birth_date, gender, active, total_visits, total_spent, registration_date, updated_at, profile_image)
VALUES ('1002', 'Jane', 'Smith', 'jane.smith@example.com', '555-234-5678', '1990-03-22', 'Female', true, 8, 620.00, '2025-02-26 12:00:00', '2025-02-26 12:00:00', 'https://randomuser.me/api/portraits/women/2.jpg')
    ON CONFLICT DO NOTHING;

-- Initialize Customer Notes
INSERT INTO customer_notes (id, customer_id, content, created_by, created_at)
VALUES ('10001', '1001', 'Prefers appointments in the morning', 'Admin', '2025-02-26 12:00:00')
    ON CONFLICT DO NOTHING;

INSERT INTO customer_notes (id, customer_id, content, created_by, created_at)
VALUES ('10002', '1002', 'Allergic to certain products', 'Admin', '2025-02-26 12:00:00')
    ON CONFLICT DO NOTHING;

-- Initialize Resources
INSERT INTO resources (id, name, type, capacity, is_available, color, created_at, updated_at)
VALUES ('R001', 'Room 1', 'Room', 1, true, '#9C27B0', '2025-02-26 12:00:00', '2025-02-26 12:00:00')
    ON CONFLICT DO NOTHING;

INSERT INTO resources (id, name, type, capacity, is_available, color, created_at, updated_at)
VALUES ('R002', 'Room 2', 'Room', 2, true, '#E91E63', '2025-02-26 12:00:00', '2025-02-26 12:00:00')
    ON CONFLICT DO NOTHING;

INSERT INTO resources (id, name, type, capacity, is_available, color, created_at, updated_at)
VALUES ('R003', 'Massage Table 1', 'Equipment', 1, true, '#795548', '2025-02-26 12:00:00', '2025-02-26 12:00:00')
    ON CONFLICT DO NOTHING;

INSERT INTO resources (id, name, type, capacity, is_available, color, created_at, updated_at)
VALUES ('R004', 'Styling Station 1', 'Station', 1, true, '#607D8B', '2025-02-26 12:00:00', '2025-02-26 12:00:00')
    ON CONFLICT DO NOTHING;