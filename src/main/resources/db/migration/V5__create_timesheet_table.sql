CREATE TABLE IF NOT EXISTS timesheet (
    id INT AUTO_INCREMENT PRIMARY KEY,
    start_time TIME NOT NULL,
    hours TIME NOT NULL,
    description TEXT,
    date DATE NOT NULL,
    project_id INT NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project(id)
);