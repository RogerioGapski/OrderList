CREATE TABLE users_tb (
    id BINARY(16) PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(60) NOT NULL
);

CREATE TABLE addresses_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    city VARCHAR(50) NOT NULL,
    street VARCHAR(50) NOT NULL,
    number VARCHAR(50) NOT NULL,
    user_id BINARY(16) NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users_tb(id)
);

CREATE TABLE categories_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE products_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock INTEGER NOT NULL,
    category_id BIGINT NOT NULL,

    FOREIGN KEY(category_id) REFERENCES categories_tb(id)
);

CREATE TABLE orders_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    create_date TIMESTAMP NOT NULL,
    status ENUM('PENDING', 'PAID', 'SHIPPED', 'DELIVERED') NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    payment_type ENUM('PIX', 'CREDIT_CARD', 'DEBIT_CARD') NOT NULL,
    user_id BINARY(16) NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users_tb(id)
);


CREATE TABLE items_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    quantity INTEGER NOT NULL,
    unitary_price DECIMAL(10, 2) NOT NULL,
    product_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    user_id BINARY(16) NOT NULL,

    FOREIGN KEY(product_id) REFERENCES products_tb(id),
    FOREIGN KEY(order_id) REFERENCES orders_tb(id),
    FOREIGN KEY(user_id) REFERENCES users_tb(id)
);

CREATE TABLE roles_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name ENUM('ROLE_USER', 'ROLE_ADMIN') NOT NULL
);

CREATE TABLE users_roles (
    user_id BINARY(16) NOT NULL,
    role_id BIGINT NOT NULL,

    PRIMARY KEY(user_id, role_id),

    FOREIGN KEY(user_id) REFERENCES users_tb(id),
    FOREIGN KEY(role_id) REFERENCES roles_tb(id)
);