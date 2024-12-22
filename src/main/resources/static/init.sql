CREATE DATABASE IF NOT EXISTS `blogger` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `blogger`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `datejoined` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `user` (`id`, `datejoined`, `email`, `password_hash`, `role`, `username`) VALUES
(1, '2024-12-21 14:08:28.855000', 'daemonanimations3d@gmail.com', '$2a$10$dGpbbrThTbpXhRXAUH.0ZeICsoofFF67AzfZjCiwNMNjymyhS5ejC', 'user', 'MaxwellsDaemon'),
(2, '2024-12-21 15:42:45.770000', 'canderson184@my.gcu.edu', '$2a$10$KfNwGdWkMmQ2RMWvGo3/jO5uk1lE2cPiUArxWj6AGljVR4y.uWuWO', 'user', 'ChanceMA');

DROP TABLE IF EXISTS `post`;
CREATE TABLE IF NOT EXISTS `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_post_user` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `post` (`id`, `user_id`, `title`, `content`, `created_at`, `updated_at`) VALUES
(1, 1, 'My First Blog Post', 'Hello world! This is my first post.', '2024-12-22 14:00:00', '2024-12-22 14:00:00'),
(2, 2, 'Another Great Post', 'This is another piece of content.', '2024-12-22 14:05:00', '2024-12-22 14:05:00');