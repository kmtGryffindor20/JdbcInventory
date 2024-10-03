DROP TABLE IF EXISTS manufacturer_order_shipping_info;
DROP TABLE IF EXISTS manufacturer_orders_suppliers;
DROP TABLE IF EXISTS manufacturer_orders_products;
DROP TABLE IF EXISTS manufacturer_orders;
DROP TABLE IF EXISTS manufacturer_phone_numbers;
DROP TABLE IF EXISTS manufacturer_email_addresses;
DROP TABLE IF EXISTS product_manufacturers;
DROP TABLE IF EXISTS manufacturers;
DROP TABLE IF EXISTS orders_returned;
DROP TABLE IF EXISTS customer_orders_products;
DROP TABLE IF EXISTS sales_report;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS employee_email_addresses;
DROP TABLE IF EXISTS customer_order_shipping_info;
DROP TABLE IF EXISTS customer_orders;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS employees;

CREATE TABLE categories (
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL
);


CREATE TABLE customers (
    email VARCHAR(255) NOT NULL UNIQUE PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    shipping_address VARCHAR(255) NOT NULL,
    billing_address VARCHAR(255) NOT NULL
);


CREATE TABLE products (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    expiry_date DATE NOT NULL,
    stock_quantity INT NOT NULL,
    cost_price DECIMAL(10, 2) NOT NULL,
    maximim_retail_price DECIMAL(10, 2) NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);


CREATE TABLE manufacturers (
    manufacturer_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    manufacturer_name VARCHAR(255) NOT NULL,
    manufacturer_address VARCHAR(255) NOT NULL
);


CREATE TABLE manufacturer_email_addresses (
    manufacturer_id BIGINT NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(manufacturer_id)
);


CREATE TABLE manufacturer_phone_numbers (
    manufacturer_id BIGINT NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(manufacturer_id)
);


CREATE TABLE product_manufacturers (
    product_id BIGINT NOT NULL,
    manufacturer_id BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(manufacturer_id)
);


CREATE TABLE employees (
    employee_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    hire_date DATE NOT NULL,
    designation VARCHAR(255) NOT NULL,
    mangager_employee_id BIGINT,
    FOREIGN KEY (mangager_employee_id) REFERENCES employees(employee_id)
);

CREATE TABLE employee_email_addresses (
    employee_id BIGINT NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);


CREATE TABLE customer_orders (
    order_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    date_of_order DATE NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    processed_by_employee_id BIGINT NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    FOREIGN KEY (customer_email) REFERENCES customers(email),
    FOREIGN KEY (processed_by_employee_id) REFERENCES employees(employee_id)
);


CREATE TABLE customer_orders_products (
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES customer_orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);  


CREATE TABLE orders_returned (
    order_id BIGINT NOT NULL,
    return_date DATE NOT NULL,
    return_reason VARCHAR(255) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES customer_orders(order_id)
);


CREATE TABLE customer_order_shipping_info (
    shipping_info_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    shipping_date DATE NOT NULL,
    expected_delivery_date DATE NOT NULL,
    status VARCHAR(255) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES customer_orders(order_id)
);


CREATE TABLE manufacturer_orders (
    manufacturer_order_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    date_of_order DATE NOT NULL,
    processed_by_employee_id BIGINT NOT NULL,
    FOREIGN KEY (manufacturer_order_id) REFERENCES manufacturers(manufacturer_id),
    FOREIGN KEY (processed_by_employee_id) REFERENCES employees(employee_id)
);


CREATE TABLE manufacturer_orders_products (
    manufacturer_order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (manufacturer_order_id) REFERENCES manufacturer_orders(manufacturer_order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);


CREATE TABLE manufacturer_orders_suppliers (
    manufacturer_order_id BIGINT NOT NULL,
    supplier_manufacturer_id BIGINT NOT NULL,
    FOREIGN KEY (manufacturer_order_id) REFERENCES manufacturer_orders(manufacturer_order_id),
    FOREIGN KEY (supplier_manufacturer_id) REFERENCES manufacturers(manufacturer_id)
);


CREATE TABLE manufacturer_order_shipping_info (
    shipping_info_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    manufacturer_order_id BIGINT NOT NULL,
    shipping_date DATE NOT NULL,
    expected_delivery_date DATE NOT NULL,
    status VARCHAR(255) NOT NULL,
    FOREIGN KEY (manufacturer_order_id) REFERENCES manufacturer_orders(manufacturer_order_id)
);

CREATE TABLE sales_report (
    day INT NOT NULL,
    month INT NOT NULL,
    year INT NOT NULL,
    total_sales DECIMAL(10, 2) NOT NULL,
    total_orders INT NOT NULL,
    top_selling_product_id BIGINT NOT NULL,
    FOREIGN KEY (top_selling_product_id) REFERENCES products(product_id),
    PRIMARY KEY (day, month, year)
)

