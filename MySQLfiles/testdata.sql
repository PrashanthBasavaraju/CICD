INSERT INTO client (name, email, location, phone, created_at, updated_at) 
VALUES 
('John Doe', 'john.doe@example.com', 'New York', '1234567890', '2024-05-06 12:00:00', '2024-05-06 12:00:00'),
('Jane Smith', 'jane.smith@example.com', 'Los Angeles', '9876543210', '2024-05-07 13:00:00', '2024-05-07 13:00:00'),
('Michael Johnson', 'michael.johnson@example.com', 'Chicago', '4567890123', '2024-05-07 13:15:00', '2024-05-07 13:15:00'),
('Emily Brown', 'emily.brown@example.com', 'Houston', '7890123456', '2024-05-07 14:00:00', '2024-05-07 14:00:00'),
('Sarah Johnson', 'sarah.johnson@example.com', 'Miami', '2345678901', '2024-05-08 12:00:00', '2024-05-08 12:00:00'),
('David Wilson', 'david.wilson@example.com', 'Seattle', '8901234567', '2024-05-08 15:00:00', '2024-05-08 15:00:00'),
('Jessica Martinez', 'jessica.martinez@example.com', 'Dallas', '5678901234', '2024-05-08 15:45:00', '2024-05-08 15:45:00'),
('Christopher Taylor', 'christopher.taylor@example.com', 'Atlanta', '9012345678', '2024-05-08 16:00:00', '2024-05-08 16:00:00'),
('Olivia Anderson', 'olivia.anderson@example.com', 'San Francisco', '6789012345', '2024-05-09 12:00:00', '2024-05-09 12:00:00'),
('Daniel Rodriguez', 'daniel.rodriguez@example.com', 'Boston', '0123456789', '2024-05-09 12:30:00', '2024-05-09 12:30:00');

INSERT INTO project (name, description, start_date, end_date, created_at, updated_at, client_id) 
VALUES 
('Project 1', 'Description for Project 1', '2024-01-01', '2024-06-30', '2024-05-06 12:00:00', '2024-05-06 12:00:00', 1),
('Project 2', 'Description for Project 2', '2024-02-01', '2024-07-31', '2024-05-07 12:00:00', '2024-05-07 12:00:00', 2),
('Project 3', 'Description for Project 3', '2024-03-01', '2024-08-31', '2024-05-08 12:45:00', '2024-05-08 12:45:00', 2),
('Project 4', 'Description for Project 4', '2024-04-01', '2024-09-30', '2024-05-08 13:00:00', '2024-05-08 13:00:00', 3),
('Project 5', 'Description for Project 5', '2024-05-01', '2024-10-31', '2024-05-09 12:15:00', '2024-05-09 12:15:00', 3),
('Project 6', 'Description for Project 6', '2024-06-01', '2024-11-30', '2024-05-09 13:00:00', '2024-05-09 13:00:00', 5),
('Project 7', 'Description for Project 7', '2024-07-01', '2024-12-31', '2024-05-09 13:15:00', '2024-05-09 13:15:00', 7),
('Project 8', 'Description for Project 8', '2024-08-01', '2025-01-31', '2024-05-10 12:00:00', '2024-05-10 12:00:00', 8),
('Project 9', 'Description for Project 9', '2024-09-01', '2025-02-28', '2024-05-10 13:00:00', '2024-05-10 13:00:00', 9),
('Project 10', 'Description for Project 10', '2024-10-01', '2025-03-31', '2024-05-10 14:00:00', '2024-05-10 14:00:00', 10);


INSERT INTO employee_project_relation (employee_id, project_id, role) 
VALUES 
(1, 1, 'Team Lead'),
(2, 1, 'Tester'),
(3, 1, 'Developer'),
(4, 1, 'Tester'),
(5, 1, 'Developer'),
(6, 2, 'Team Lead'),
(7, 2, 'Tester'),
(1, 2, 'Developer'),
(9, 2, 'Tester'),
(10, 2, 'Developer');

INSERT INTO timesheet (start_time, hours, description, date, project_id) VALUES 
('08:00:00', '08:00:00', 'Worked on frontend design', '2024-05-01', 1),
('09:30:00', '06:30:00', 'Debugged backend API', '2024-05-02', 2),
('10:45:00', '07:15:00', 'Meeting with the client', '2024-05-03', 3),
('08:15:00', '08:45:00', 'Code review session', '2024-05-04', 1),
('09:00:00', '07:00:00', 'Implemented new feature', '2024-05-05', 2),
('10:00:00', '08:00:00', 'Testing phase', '2024-05-06', 3),
('08:30:00', '07:30:00', 'Documentation work', '2024-05-07', 1),
('09:15:00', '06:45:00', 'Client presentation', '2024-05-08', 2),
('10:30:00', '07:30:00', 'Refactoring codebase', '2024-05-09', 3),
('08:45:00', '08:15:00', 'Bug fixing', '2024-05-10', 1);

INSERT INTO invoice (duration, amount, created_at, updated_at) 
VALUES 
    ('08:00:00', 100.0, '2024-05-10 08:00:00', '2024-05-10 08:00:00'),
    ('10:30:00', 150.0, '2024-05-11 10:30:00', '2024-05-11 10:30:00'),
    ('06:45:00', 80.0, '2024-05-12 06:45:00', '2024-05-12 06:45:00'),
    ('07:15:00', 120.0, '2024-05-13 07:15:00', '2024-05-13 07:15:00'),
    ('09:00:00', 200.0, '2024-05-14 09:00:00', '2024-05-14 09:00:00'),
    ('05:45:00', 90.0, '2024-05-15 05:45:00', '2024-05-15 05:45:00'),
    ('11:00:00', 180.0, '2024-05-16 11:00:00', '2024-05-16 11:00:00'),
    ('08:30:00', 140.0, '2024-05-17 08:30:00', '2024-05-17 08:30:00'),
    ('06:00:00', 110.0, '2024-05-18 06:00:00', '2024-05-18 06:00:00'),
    ('12:15:00', 220.0, '2024-05-19 12:15:00', '2024-05-19 12:15:00');
    
    
    
    INSERT INTO role (type) VALUES ('Team Lead'),('Developer'),('Solution Architect'),('QA'),('Product Manager'),('Product Analyst');



