CREATE TABLE IF NOT EXISTS employee_project_relation (
    employee_id INT NOT NULL,
    project_id INT NOT NULL,
    role VARCHAR(100) NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employee(id),
    FOREIGN KEY (project_id) REFERENCES project(id),
    PRIMARY KEY (employee_id, project_id)
);

// take the role values and update epr table if not exixtex or null