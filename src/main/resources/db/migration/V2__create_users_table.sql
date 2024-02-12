CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    encrypted_password VARCHAR(100) NOT NULL
);

CREATE INDEX idx_email ON users (email);
