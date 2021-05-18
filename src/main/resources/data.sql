CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    family_name VARCHAR(250) NOT NULL,
    date_of_birth DATE NOT NULL,
    address VARCHAR(250) NOT NULL,
    phone_number VARCHAR(250) NOT NULL
)