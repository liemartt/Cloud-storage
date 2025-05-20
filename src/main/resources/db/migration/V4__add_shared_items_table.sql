CREATE TABLE shared_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_path VARCHAR(255) NOT NULL,
    shared_with_user_id INT NOT NULL,
    role VARCHAR(20) NOT NULL,
    shared_by_user_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (shared_with_user_id) REFERENCES users(id),
    FOREIGN KEY (shared_by_user_id) REFERENCES users(id)
); 