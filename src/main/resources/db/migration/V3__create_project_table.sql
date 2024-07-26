CREATE TABLE IF NOT EXISTS project (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    client_id INT NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id)
);