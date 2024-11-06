INSERT INTO manufacturers (manufacturer_name, manufacturer_address) VALUES
('Apple Inc.', '1 Apple Park Way, Cupertino, CA 95014'),
('Samsung Electronics Co., Ltd.', '129 Samsung-ro, Maetan-dong, Yeongtong-gu, Suwon-si, Gyeonggi-do, South Korea'),
('Sony Corporation', '1-7-1 Konan, Minato-ku, Tokyo 108-0075, Japan'),
('Dell Technologies Inc.', '1 Dell Way, Round Rock, TX 78682'),
('Bose Corporation', '1 Boston Place, Boston, MA 02108'),
('IKEA', 'Dåvakvegen 14, 3560 Hemsedal, Norway'),
('Ashley Furniture', '1 Ashley Way, Arcadia, WI 54612'),
('Herman Miller', '855 East Main Avenue, Zeeland, MI 49464'),
('Sealy', '4200 International Parkway, Carrollton, TX 75007'),
('Sauder', '1000 Sauder Village Blvd, Archbold, OH 43502'),
('Levi Strauss & Co.', '1155 Battery St, San Francisco, CA 94111'),
('Nike Inc.', '1 Bowerman Drive, Beaverton, OR 97005'),
('Adidas AG', 'Adidas-Straße 1, 91074 Herzogenaurach, Germany'),
('H&M', 'Mäster Samuelsgatan 46A, 106 38 Stockholm, Sweden'),
('Inditex', 'Av. de la Diputación, 15, 15142 Arteijo, A Coruña, Spain'),
('Penguin Random House', '1745 Broadway, New York, NY 10019'),
('HarperCollins', '195 Broadway, New York, NY 10007'),
('Little, Brown and Company', '1290 Avenue of the Americas, New York, NY 10104'),
('Organic Valley', '1 Organic Way, La Farge, WI 54639'),
("Nature's Own", '1370 Williams Dr, Thomasville, GA 31757'),
('Happy Egg Co.', '1 Happy Egg Way, Newtown, Bucks, PA 18940'),
('Earthbound Farm', '1400 W 7th St #100, San Pedro, CA 90732'),
('Chiquita', '250 E Fifth St, Suite 2200, Cincinnati, OH 45202');


-- Insert manufacturer email addresses
INSERT INTO manufacturer_email_addresses (manufacturer_id, email_address) VALUES
(1, 'contact@apple.com'),
(2, 'info@samsung.com'),
(3, 'support@sony.com'),
(4, 'support@dell.com'),
(5, 'info@bose.com'),
(6, 'customer.service@ikea.com'),
(7, 'contact@ashleyfurniture.com'),
(8, 'info@hermanmiller.com'),
(9, 'service@sealy.com'),
(10, 'info@sauder.com'),
(11, 'customercare@levis.com'),
(12, 'info@nike.com'),
(13, 'contact@adidas.com'),
(14, 'customerservice@hm.com'),
(15, 'info@inditex.com'),
(16, 'contact@penguinrandomhouse.com'),
(17, 'info@harpercollins.com'),
(18, 'contact@littlebrown.com'),
(19, 'support@organicvalley.com'),
(20, 'info@naturesown.com'),
(21, 'info@happyegg.com'),
(22, 'contact@earthboundfarm.com'),
(23, 'info@chiquita.com');

INSERT INTO manufacturer_phone_numbers (manufacturer_id, phone_number) VALUES
(1, '(408) 996-1010'),
(2, '(031) 200-3000'),
(3, '(03) 6748-2111'),
(4, '(512) 338-4400'),
(5, '(800) 999-2673'),
(6, '(888) 888-4532'),
(7, '(800) 477-2222'),
(8, '(800) 426-9200'),
(9, '(800) 258-0000'),
(10, '(800) 783-3222'),
(11, '(888) 501-7841'),
(12, '(503) 671-6453'),
(13, '(800) 982-9337'),
(14, '(844) 662-6657'),
(15, '(34) 981-18-99-00'),
(16, '(800) 733-3000'),
(17, '(212) 207-7000'),
(18, '(800) 346-3258'),
(19, '(608) 625-1000'),
(20, '(800) 999-4999'),
(21, '(866) 210-1044'),
(22, '(831) 375-1115'),
(23, '(800) 333-5100');




INSERT INTO categories (category_name, category_description)
VALUES 
('Electronics', 'Innovating the Future, One Device at a Time.'),
('Furniture', 'Elevate Your Living Space with Our Stylish Designs.'),
('Clothing', 'Stay Fashionable with Our Latest Collections.'),
('Books', 'Explore the World Through the Pages of Our Books.'),
('Groceries', 'Fresh and Organic Produce for Your Daily Needs.'),
('Toys', 'Fun and Educational Toys for Kids of All Ages.'),
('Sports Equipment', 'Gear Up for Your Next Adventure.'),
('Beauty Products', 'Enhance Your Natural Beauty with Our Products.'),
('Office Supplies', 'Essential Supplies for Your Workplace.'),
('Automotive', 'Quality Parts and Accessories for Your Vehicle.'),
('Kitchen Appliances', 'Upgrade Your Kitchen with Our Appliances.'),
('Gardening Tools', 'Tools and Equipment for Your Gardening Needs.'),
('Fitness Gear', 'Get Fit and Stay Healthy with Our Fitness Gear.'),
('Pet Supplies', 'Quality Supplies for Your Furry Friends.'),
('Home Decor', 'Add a Touch of Elegance to Your Home.');


INSERT INTO products (product_name, expiry_date, stock_quantity, selling_price, maximum_retail_price, category_id, description) VALUES
-- Electronics (Category ID: 1)
('Apple iPhone 14', '2026-12-31', 50, 799.99, 999.99, 1, 'Latest model of Apple iPhone with advanced camera and display features.'),
('Samsung Galaxy S22', '2026-11-30', 40, 699.99, 899.99, 1, 'High-performance Samsung smartphone with stunning display and powerful battery.'),
('Sony Bravia 4K TV', '2027-06-30', 20, 999.99, 1199.99, 1, 'Ultra HD 4K Sony TV for a cinematic viewing experience at home.'),
('Dell Inspiron Laptop', '2027-08-15', 15, 649.99, 799.99, 1, 'Reliable Dell laptop suitable for work, study, and everyday tasks.'),
('Bose QuietComfort Headphones', '2027-07-20', 25, 249.99, 299.99, 1, 'Noise-cancelling Bose headphones for immersive audio quality.'),

-- Furniture (Category ID: 2)
('Wooden Dining Table', '2028-01-10', 10, 499.99, 599.99, 2, 'Classic wooden dining table, perfect for family gatherings.'),
('Leather Sofa Set', '2028-02-20', 8, 899.99, 1099.99, 2, 'Elegant leather sofa set for a comfortable and stylish living space.'),
('Office Chair', '2028-03-15', 30, 149.99, 199.99, 2, 'Ergonomic office chair with adjustable height and back support.'),
('Queen Size Bed', '2028-04-25', 12, 799.99, 999.99, 2, 'Comfortable queen-size bed for a restful sleep experience.'),
('Bookshelf', '2028-05-05', 18, 129.99, 169.99, 2, 'Spacious bookshelf to organize your books and decor.'),

-- Clothing (Category ID: 3)
("Levi's Jeans", '2026-11-01', 60, 39.99, 59.99, 3, "Classic Levi\'s jeans with a comfortable fit and durable fabric."),
('Nike Running Shoes', '2026-10-20', 45, 59.99, 79.99, 3, 'Lightweight Nike running shoes with excellent grip and support.'),
('Adidas T-Shirt', '2026-12-10', 100, 19.99, 29.99, 3, 'Comfortable Adidas t-shirt for casual wear and sports activities.'),
('H&M Jacket', '2026-09-15', 25, 49.99, 69.99, 3, 'Stylish H&M jacket for cool weather and everyday wear.'),
('Zara Dress', '2026-08-05', 40, 39.99, 59.99, 3, 'Elegant Zara dress suitable for various occasions.'),

-- Books (Category ID: 4)
('The Great Gatsby', '2030-12-01', 100, 9.99, 14.99, 4, 'Classic novel by F. Scott Fitzgerald set in the Jazz Age.'),
('1984 by George Orwell', '2030-11-10', 120, 8.99, 12.99, 4, 'Dystopian novel by George Orwell about a totalitarian regime.'),
('To Kill a Mockingbird', '2030-10-05', 85, 7.99, 10.99, 4, "Harper Lee's novel exploring racial injustice in the Deep South."),
('The Catcher in the Rye', '2030-09-20', 90, 6.99, 9.99, 4, 'Coming-of-age story by J.D. Salinger about teenage rebellion.'),
('Pride and Prejudice', '2030-08-15', 75, 5.99, 8.99, 4, 'Romantic novel by Jane Austen exploring manners and marriage.'),

-- Groceries (Category ID: 5)
('Organic Milk', '2024-12-31', 200, 1.99, 2.99, 5, 'Fresh organic milk sourced from local farms.'),
('Whole Wheat Bread', '2024-11-30', 150, 0.99, 1.49, 5, 'Healthy whole wheat bread baked fresh daily.'),
('Free-range Eggs', '2024-10-25', 180, 2.99, 3.99, 5, 'Farm-fresh free-range eggs with rich flavor and nutrition.'),
('Fresh Spinach', '2024-09-20', 220, 1.49, 2.49, 5, 'Organic spinach leaves picked fresh for maximum nutrients.'),
('Bananas (1 Dozen)', '2024-08-15', 300, 0.99, 1.29, 5, 'A dozen ripe bananas, perfect for snacking and smoothies.');


INSERT INTO product_manufacturers (product_id, manufacturer_id, cost_price) VALUES
-- Electronics
(1, 1, 699.99),  -- Apple iPhone 14 by Apple Inc.
(2, 2, 599.99),  -- Samsung Galaxy S22 by Samsung Electronics
(3, 3, 999.99),  -- Sony Bravia 4K TV by Sony Corporation
(4, 4, 799.99),  -- Dell Inspiron Laptop by Dell Technologies
(5, 5, 299.99),  -- Bose QuietComfort Headphones by Bose Corporation

-- Furniture
(6, 6, 149.99),  -- Wooden Dining Table by IKEA
(7, 7, 499.99),  -- Leather Sofa Set by Ashley Furniture
(8, 8, 199.99),  -- Office Chair by Herman Miller
(9, 9, 399.99),  -- Queen Size Bed by Sealy
(10, 10, 99.99),  -- Bookshelf by Sauder

-- Clothing
(11, 11, 39.99),  -- Levi's Jeans by Levi Strauss & Co.
(12, 12, 79.99),  -- Nike Running Shoes by Nike Inc.
(13, 13, 24.99),  -- Adidas T-Shirt by Adidas AG
(14, 14, 59.99),  -- H&M Jacket by H&M
(15, 15, 49.99),  -- Zara Dress by Inditex

-- Books
(16, 16, 9.99),  -- The Great Gatsby by Penguin Random House
(17, 17, 14.99),  -- 1984 by George Orwell by HarperCollins
(18, 17, 12.99),  -- To Kill a Mockingbird by HarperCollins
(19, 18, 8.99),  -- The Catcher in the Rye by Little, Brown and Company
(20, 16, 10.99),  -- Pride and Prejudice by Penguin Random House

-- Groceries
(21, 19, 2.99),  -- Organic Milk by Organic Valley
(22, 20, 1.99),  -- Whole Wheat Bread by Nature's Own
(23, 21, 3.49),  -- Free-range Eggs by Happy Egg Co.
(24, 22, 1.49),  -- Fresh Spinach by Earthbound Farm
(25, 23, 0.99);  -- Bananas by Chiquita



INSERT INTO customers (email, first_name, last_name, phone_number, shipping_address, billing_address) VALUES
('johndoe@example.com', 'John', 'Doe', '555-1234', '123 Main St, Springfield', '456 Maple Ave, Springfield'),
('janesmith@example.com', 'Jane', 'Smith', '555-5678', '789 Oak St, Springfield', '321 Elm St, Springfield'),
('michaelj@example.com', 'Michael', 'Johnson', '555-8765', '987 Birch St, Springfield', '654 Pine St, Springfield'),
('emilyd@example.com', 'Emily', 'Davis', '555-4321', '345 Cedar St, Springfield', '210 Ash St, Springfield'),
('davidwilson@example.com', 'David', 'Wilson', '555-1122', '678 Walnut St, Springfield', '234 Oak St, Springfield'),
('sarahm@example.com', 'Sarah', 'Miller', '555-2233', '890 Willow St, Springfield', '567 Maple St, Springfield'),
('jamesbrown@example.com', 'James', 'Brown', '555-3344', '432 Chestnut St, Springfield', '789 Birch St, Springfield'),
('lindaanderson@example.com', 'Linda', 'Anderson', '555-4455', '210 Palm St, Springfield', '876 Walnut St, Springfield'),
('robertm@example.com', 'Robert', 'Martinez', '555-5566', '345 Spruce St, Springfield', '123 Cedar St, Springfield'),
('patriciat@example.com', 'Patricia', 'Thomas', '555-6677', '567 Aspen St, Springfield', '234 Birch St, Springfield');


INSERT INTO employees (first_name, last_name, phone_number, hire_date, designation, manager_employee_id) VALUES
('Alice', 'Johnson', '1234567890', '2023-05-01', 'CEO', NULL),          -- CEO with no manager
('Bob', 'Smith', '2345678901', '2023-06-15', 'CTO', 1),                 -- CTO reporting to Alice
('Carol', 'Williams', '3456789012', '2023-07-20', 'CFO', 1),            -- CFO reporting to Alice
('David', 'Brown', '4567890123', '2023-08-10', 'Senior Developer', 2),  -- Developer reporting to Bob
('Eve', 'Davis', '5678901234', '2023-09-05', 'Product Manager', 2),     -- Product Manager reporting to Bob
('Frank', 'Wilson', '6789012345', '2023-10-12', 'Financial Analyst', 3),-- Financial Analyst reporting to Carol
('Grace', 'Martinez', '7890123456', '2023-11-01', 'HR Manager', 1),     -- HR Manager reporting to Alice
('Hank', 'Garcia', '8901234567', '2024-01-15', 'Software Engineer', 4), -- Engineer reporting to David
('Ivy', 'Anderson', '9012345678', '2024-02-10', 'Marketing Manager', 3); -- Marketing Manager reporting to Carol



INSERT INTO customer_orders (date_of_order, customer_email, processed_by_employee_id, payment_method) VALUES
('2024-11-01', 'johndoe@example.com', 5, 'CARD'),
('2024-11-02', 'janesmith@example.com', 5, 'UPI'),
('2024-11-03', 'michaelj@example.com', 5, 'CASH'),
('2024-11-03', 'emilyd@example.com', 6, 'CASH'),
('2024-11-04', 'davidwilson@example.com', 5, 'CARD');


INSERT INTO customer_orders_products (order_id, product_id, quantity) VALUES
-- Order 1
(1, 1, 2),  -- 2 Apple iPhone 14
(1, 5, 1),  -- 1 Bose QuietComfort Headphones

-- Order 2
(2, 7, 1),  -- 1 Leather Sofa Set
(2, 8, 3),  -- 3 Office Chairs

-- Order 3
(3, 13, 2), -- 2 Adidas T-Shirts
(3, 16, 1), -- 1 The Great Gatsby Book

-- Order 4
(4, 19, 1), -- 1 Pride and Prejudice Book
(4, 21, 4), -- 4 Organic Milk

-- Order 5
(5, 2, 1),  -- 1 Samsung Galaxy S22
(5, 6, 2);  -- 2 Wooden Dining Tables

