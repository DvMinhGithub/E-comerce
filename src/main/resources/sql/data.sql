-- Clear existing data to avoid conflicts on restart
-- DELETE FROM product_sizes;
-- DELETE FROM product_images;
-- DELETE FROM product_colors;
-- DELETE FROM products;
-- DELETE FROM colors;
-- DELETE FROM categories;
-- DELETE FROM brands;
-- DELETE FROM users;
-- DELETE FROM roles;

-- Reset sequences for auto-incrementing IDs (PostgreSQL specific)
-- ALTER SEQUENCE roles_id_seq RESTART WITH 1;
-- ALTER SEQUENCE users_id_seq RESTART WITH 1;
-- ALTER SEQUENCE brands_id_seq RESTART WITH 1;
-- ALTER SEQUENCE categories_id_seq RESTART WITH 1;
-- ALTER SEQUENCE colors_id_seq RESTART WITH 1;
-- ALTER SEQUENCE products_id_seq RESTART WITH 1;
-- ALTER SEQUENCE product_images_id_seq RESTART WITH 1;
-- ALTER SEQUENCE product_sizes_id_seq RESTART WITH 1;

-- Roles
-- INSERT INTO roles (name, description) VALUES ('ADMIN', 'Quyền quản trị hệ thống');
-- INSERT INTO roles (name, description) VALUES ('USER', 'Quyền khách hàng tiêu chuẩn');

-- -- Users (sử dụng bcrypt cho mật khẩu 'Admin@123')
-- INSERT INTO users (
--     first_name,
--     middle_name,
--     last_name,
--     authenticated,
--     email,
--     password,
--     date_of_birth,
--     is_admin,
--     avatar_url,
--     avatar_id,
--     role_id,
--     created_at,
--     updated_at
-- ) VALUES (
--     'Admin',
--     NULL,
--     'Master',
--     true,
--     'admin@ecomerce.local',
--     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZ6h3kY2bQVWwtIg9Y9ycYzaPt6FZe',
--     '1990-01-01',
--     true,
--     NULL,
--     NULL,
--     1,
--     NOW(),
--     NOW()
-- );

-- Brands
INSERT INTO brands (name, is_active, user_id, created_at, updated_at) VALUES ('Nike', true, 1, NOW(), NOW());
INSERT INTO brands (name, is_active, user_id, created_at, updated_at) VALUES ('Adidas', true, 1, NOW(), NOW());
INSERT INTO brands (name, is_active, user_id, created_at, updated_at) VALUES ('Puma', true, 1, NOW(), NOW());
INSERT INTO brands (name, is_active, user_id, created_at, updated_at) VALUES ('Converse', true, 1, NOW(), NOW());

-- Categories
INSERT INTO categories (name, image_url, is_active, user_id, created_at, updated_at)
VALUES ('Running Shoes', NULL, true, 1, NOW(), NOW());
INSERT INTO categories (name, image_url, is_active, user_id, created_at, updated_at)
VALUES ('Sneakers', NULL, true, 1, NOW(), NOW());
INSERT INTO categories (name, image_url, is_active, user_id, created_at, updated_at)
VALUES ('Basketball Shoes', NULL, true, 1, NOW(), NOW());

-- Colors
INSERT INTO colors (name, is_active, user_id, created_at, updated_at) VALUES ('Black', true, 1, NOW(), NOW());
INSERT INTO colors (name, is_active, user_id, created_at, updated_at) VALUES ('White', true, 1, NOW(), NOW());
INSERT INTO colors (name, is_active, user_id, created_at, updated_at) VALUES ('Red', true, 1, NOW(), NOW());
INSERT INTO colors (name, is_active, user_id, created_at, updated_at) VALUES ('Blue', true, 1, NOW(), NOW());
INSERT INTO colors (name, is_active, user_id, created_at, updated_at) VALUES ('Green', true, 1, NOW(), NOW());

-- Products (sử dụng cấu trúc mới với brand dạng chuỗi và total_qty/total_sold)
INSERT INTO products (name, description, brand_id, category_id, price, total_qty, total_sold, user_id, created_at, updated_at)
VALUES (
    'Nike Air Force 1 ''07',
    'The radiance lives on in the Nike Air Force 1 ''07 với chất liệu bền bỉ và phong cách cổ điển.',
    1,
    2,
    2900000.00,
    150,
    0,
    1,
    NOW(),
    NOW()
);

INSERT INTO products (name, description, brand_id, category_id, price, total_qty, total_sold, user_id, created_at, updated_at)
VALUES (
    'Adidas Ultraboost 1.0',
    'Adidas PRIMEKNIT upper và công nghệ BOOST mang lại cảm giác êm ái trong từng bước chạy.',
    2,
    1,
    4800000.00,
    80,
    0,
    1,
    NOW(),
    NOW()
);

INSERT INTO products (name, description, brand_id, category_id, price, total_qty, total_sold, user_id, created_at, updated_at)
VALUES (
    'Puma LaMelo Ball MB.02',
    'Thiết kế đậm chất sân đấu với lớp phủ sống động và hiệu năng cao.',
    3,
    3,
    3500000.00,
    60,
    0,
    1,
    NOW(),
    NOW()
);

INSERT INTO products (name, description, brand_id, category_id, price, total_qty, total_sold, user_id, created_at, updated_at)
VALUES (
    'Converse Chuck 70',
    'Phiên bản Chuck 70 giữ nguyên tinh thần original nhưng hoàn thiện bằng vật liệu cao cấp.',
    4,
    2,
    1900000.00,
    200,
    0,
    1,
    NOW(),
    NOW()
);

-- Product Images (bao gồm image_order và created_at)
INSERT INTO product_images (product_id, image_url, image_order, created_at)
VALUES (1, 'https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/b7d9211c-26e7-431a-ac24-b0540fb3c00f/air-force-1-07-shoes-WrLlWX.png', 1, NOW());
INSERT INTO product_images (product_id, image_url, image_order, created_at)
VALUES (1, 'https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/120a31b0-efa7-445c-92a7-420b552ac04c/air-force-1-07-shoes-WrLlWX.png', 2, NOW());

INSERT INTO product_images (product_id, image_url, image_order, created_at)
VALUES (2, 'https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/2327a43d733d44449002af0f009c646d_9366/Ultraboost_1.0_Shoes_White_HQ4199_01_standard.jpg', 1, NOW());

INSERT INTO product_images (product_id, image_url, image_order, created_at)
VALUES (3, 'https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_1200,h_1200/global/377586/02/sv01/fnd/PNA/fmt/png/MB.02-Basketball-Shoes', 1, NOW());
INSERT INTO product_images (product_id, image_url, image_order, created_at)
VALUES (3, 'https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_1200,h_1200/global/377586/02/sv02/fnd/PNA/fmt/png/MB.02-Basketball-Shoes', 2, NOW());

INSERT INTO product_images (product_id, image_url, image_order, created_at)
VALUES (4, 'https://www.converse.com.vn/media/catalog/product/cache/e81e4f2e9c3b15aa363a33a4524a51a4/1/6/162058c-1.jpg', 1, NOW());

-- Product Sizes (sử dụng enum ProductSize: S, M, L, XL, XXL)
INSERT INTO product_sizes (product_id, size) VALUES (1, 'M');
INSERT INTO product_sizes (product_id, size) VALUES (1, 'L');
INSERT INTO product_sizes (product_id, size) VALUES (1, 'XL');

INSERT INTO product_sizes (product_id, size) VALUES (2, 'S');
INSERT INTO product_sizes (product_id, size) VALUES (2, 'M');
INSERT INTO product_sizes (product_id, size) VALUES (2, 'L');

INSERT INTO product_sizes (product_id, size) VALUES (3, 'M');
INSERT INTO product_sizes (product_id, size) VALUES (3, 'L');
INSERT INTO product_sizes (product_id, size) VALUES (3, 'XL');

INSERT INTO product_sizes (product_id, size) VALUES (4, 'S');
INSERT INTO product_sizes (product_id, size) VALUES (4, 'M');
INSERT INTO product_sizes (product_id, size) VALUES (4, 'L');

-- Product Colors
INSERT INTO product_colors (product_id, color_id) VALUES (1, 2); -- White
INSERT INTO product_colors (product_id, color_id) VALUES (1, 1); -- Black

INSERT INTO product_colors (product_id, color_id) VALUES (2, 2); -- White
INSERT INTO product_colors (product_id, color_id) VALUES (2, 4); -- Blue

INSERT INTO product_colors (product_id, color_id) VALUES (3, 3); -- Red
INSERT INTO product_colors (product_id, color_id) VALUES (3, 1); -- Black

INSERT INTO product_colors (product_id, color_id) VALUES (4, 1); -- Black
INSERT INTO product_colors (product_id, color_id) VALUES (4, 5); -- Green
