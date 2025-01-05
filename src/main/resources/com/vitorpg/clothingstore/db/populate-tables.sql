-- Inserção na tabela tb_User
INSERT INTO tb_User (name, email, password) VALUES
('João Silva', 'joao@exemplo.com', 'senha123'),
('Maria Oliveira', 'maria@exemplo.com', 'senhaSegura1'),
('Ana Souza', 'ana@exemplo.com', 'ana2023'),
('Carlos Pereira', 'carlos@exemplo.com', 'carlosSeguro');

-- Inserção na tabela tb_Style
INSERT INTO tb_Style (name) VALUES
('Casual'),
('Formal'),
('Esportivo'),
('Vintage'),
('Business Casual'),
('Athleisure'),
('Streetwear'),
('Chic');

-- Inserção na tabela tb_Color
INSERT INTO tb_Color (name) VALUES
('Vermelho'),
('Azul'),
('Preto'),
('Branco'),
('Verde'),
('Cinza'),
('Marrom'),
('Amarelo');

-- Inserção na tabela tb_Material
INSERT INTO tb_Material (name) VALUES
('Algodão'),
('Couro'),
('Jeans'),
('Poliéster'),
('Lã'),
('Seda'),
('Nylon'),
('Fleece');

-- Inserção na tabela tb_Supplier
INSERT INTO tb_Supplier (name) VALUES
('Fornecedor A'),
('Fornecedor B'),
('Fornecedor C'),
('Fornecedor D');

-- Inserção na tabela tb_Category
INSERT INTO tb_Category (name, sizeType) VALUES
('Camisas', 'LETTER'),
('Calças', 'NUMBER'),
('Jaquetas', 'LETTER'),
('Suéteres', 'LETTER'),
('Bermudas', 'NUMBER'),
('Casacos', 'LETTER');

-- Inserção na tabela tb_Size
INSERT INTO tb_Size (value, sizeType) VALUES
('PP', 'LETTER'),
('P', 'LETTER'),
('M', 'LETTER'),
('G', 'LETTER'),
('GG', 'LETTER'),
('34', 'NUMBER'),
('36', 'NUMBER'),
('38', 'NUMBER'),
('40', 'NUMBER'),
('42', 'NUMBER'),
('44', 'NUMBER'),
('46', 'NUMBER'),
('48', 'NUMBER'),
('50', 'NUMBER');

-- Inserção na tabela tb_Product
INSERT INTO tb_Product (name, gender, amount, price, categoryId, styleId, sizeId, colorId, materialId) VALUES
('Camisa Casual', 'UNISEX', 50, 29.99, 1, 1, 1, 2, 1),
('Calça Formal', 'MALE', 30, 49.99, 2, 2, 3, 3, 3),
('Jaqueta Esportiva', 'FEMALE', 20, 99.99, 3, 3, 2, 4, 2),
('Sapatos de Couro', 'MALE', 15, 120.00, 4, 4, 4, 1, 2),
('Suéter Business Casual', 'UNISEX', 40, 59.99, 5, 5, 1, 5, 1),
('Bermuda Athleisure', 'MALE', 35, 39.99, 6, 6, 2, 6, 2),
('Casaco Streetwear', 'FEMALE', 25, 129.99, 7, 7, 3, 7, 3),
('Chapéu Chic', 'UNISEX', 50, 19.99, 8, 8, 4, 8, 4),
('Suéter de Lã', 'MALE', 30, 79.99, 5, 5, 1, 1, 5),
('Blusa de Seda', 'FEMALE', 45, 89.99, 5, 8, 2, 2, 6),
('Jaqueta de Nylon', 'UNISEX', 50, 99.99, 7, 6, 3, 3, 7),
('Moletom Fleece', 'MALE', 20, 69.99, 7, 6, 2, 4, 8),
('Calça Cargo Verde', 'MALE', 40, 59.99, 2, 3, 3, 8, 8),
('Saia Amarela', 'FEMALE', 15, 49.99, 6, 8, 2, 5, 6);

-- Inserção na tabela tb_Sale
INSERT INTO tb_Sale (amount, dateTime, totalPrice, productId, vendorId) VALUES
(2, '2024-01-10 14:30:00', 375.00, 1, 1),

-- Inserção na tabela tb_Supply
INSERT INTO tb_Supply (deliveryPrice, price, productId, supplierId, date) VALUES
(10.00, 20.00, 1, 1, '2024-01-05'),
(15.00, 35.00, 2, 2, '2024-01-07'),
(8.00, 55.00, 3, 3, '2024-01-09'),
(12.00, 90.00, 4, 4, '2024-01-11'),
(12.00, 35.00, 5, 1, '2024-02-05'),
(18.00, 45.00, 6, 2, '2024-02-07'),
(10.00, 120.00, 7, 3, '2024-02-09'),
(6.00, 20.00, 8, 4, '2024-02-11'),
(14.00, 40.00, 9, 1, '2024-02-13'),
(8.00, 55.00, 10, 2, '2024-02-14');
