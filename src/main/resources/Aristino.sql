CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `username` varchar(255) UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(10) UNIQUE NOT NULL,
  `email` varchar(255) UNIQUE NOT NULL,
  `gender` varchar(5) NOT NULL,
  `birthday` date NOT NULL,
  `role` int DEFAULT 0,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
);

CREATE TABLE `categories` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
);

CREATE TABLE `products` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `quantity` int(3) NOT NULL,
  `import_price` int NOT NULL,
  `price` int NOT NULL,
  `img` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `category_id` int NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
);

CREATE TABLE `carts` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int UNIQUE NOT NULL,
  `total` int NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
);

CREATE TABLE `cart_items` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `cart_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity_buy` int NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
);

CREATE TABLE `orders` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_phone` varchar(10) NOT NULL,
  `user_address` varchar(255) NOT NULL,
  `total` int NOT NULL,
  `status` varchar(255) NOT NULL,
  `payment_method` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
);

CREATE TABLE `order_items` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `product_img` varchar(255) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `product_price` int NOT NULL,
  `quantity_buy` int NOT NULL,
  `total_price` int NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
);

ALTER TABLE `products` ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `carts` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `cart_items` ADD FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`);

ALTER TABLE `cart_items` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `order_items` ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `order_items` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);
