-- Inserção na tabela tb_User
INSERT INTO tb_User (name, email, password) VALUES
('John Doe', 'john@example.com', 'password123'),
('Jane Smith', 'jane@example.com', 'securePass1'),
('Alice Johnson', 'alice@example.com', 'alice2023'),
('Bob Brown', 'bob@example.com', 'bobSecure');

-- Inserção na tabela tb_Style
INSERT INTO tb_Style (name) VALUES
('Casual'),
('Formal'),
('Sportswear'),
('Vintage');

-- Inserção na tabela tb_Color
INSERT INTO tb_Color (name) VALUES
('Red'),
('Blue'),
('Black'),
('White');

-- Inserção na tabela tb_Material
INSERT INTO tb_Material (name) VALUES
('Cotton'),
('Leather'),
('Denim'),
('Polyester');

-- Inserção na tabela tb_Supplier
INSERT INTO tb_Supplier (name) VALUES
('Supplier A'),
('Supplier B'),
('Supplier C'),
('Supplier D');

-- Inserção na tabela tb_Category
INSERT INTO tb_Category (name) VALUES
('Shirts'),
('Pants'),
('Jackets'),
('Shoes');

-- Inserção na tabela tb_Size
INSERT INTO tb_Size (value, sizeType) VALUES
('S', 'LETTER'),
('M', 'LETTER'),
('L', 'LETTER'),
('42', 'NUMBER');

-- Relacionamento entre Categoria e Tamanho
INSERT INTO tb_CategorySize (categoryId, sizeId) VALUES
(1, 1),
(1, 2),
(2, 3),
(4, 4);

-- Inserção na tabela tb_Product
INSERT INTO tb_Product (name, gender, amount, price, categoryId, styleId, sizeId, colorId, materialId) VALUES
('Casual Shirt', 'UNISEX', 50, 29.99, 1, 1, 1, 2, 1),
('Formal Pants', 'MALE', 30, 49.99, 2, 2, 3, 3, 3),
('Sports Jacket', 'FEMALE', 20, 99.99, 3, 3, 2, 4, 2),
('Leather Shoes', 'MALE', 15, 120.00, 4, 4, 4, 1, 2);

-- Inserção na tabela tb_Sale
INSERT INTO tb_Sale (amount, dateTime, totalPrice, productId, vendorId) VALUES
(3, '2024-01-10 14:30:00', 89.97, 1, 1),
(1, '2024-01-12 10:00:00', 49.99, 2, 2),
(2, '2024-01-15 16:45:00', 199.98, 3, 3),
(1, '2024-01-20 12:00:00', 120.00, 4, 4);

-- Inserção na tabela tb_Supply
INSERT INTO tb_Supply (deliveryPrice, price, productId, supplierId, date, status) VALUES
(10.00, 20.00, 1, 1, '2024-01-05', 'Delivered'),
(15.00, 35.00, 2, 2, '2024-01-07', 'Pending'),
(8.00, 55.00, 3, 3, '2024-01-09', 'Delivered'),
(12.00, 90.00, 4, 4, '2024-01-11', 'In Transit');
