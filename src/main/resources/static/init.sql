SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
CREATE DATABASE IF NOT EXISTS `blogger` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `blogger`;

DROP TABLE IF EXISTS `post`;
CREATE TABLE IF NOT EXISTS `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_post_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

INSERT INTO `post` (`id`, `user_id`, `title`, `content`, `created_at`, `updated_at`) VALUES
(2, 2, 'Another Great Post', 'This is another piece of content.', '2024-12-22 14:05:00.000000', '2024-12-22 14:05:00.000000'),
(3, 1, 'Have Another!', 'Lorem ipsum odor amet, consectetuer adipiscing elit. Venenatis leo maecenas tristique accumsan dis torquent senectus blandit nostra? Massa pretium phasellus elementum at bibendum eros. Placerat posuere fusce nisl; curabitur nunc per auctor. Magnis euismod nostra dictumst maecenas nisi. Ex donec maximus torquent finibus ridiculus.\r\n\r\nLuctus leo posuere; cras purus vestibulum mauris blandit. Gravida leo dui senectus malesuada amet turpis natoque dui. Potenti ridiculus enim volutpat elementum lectus velit montes aenean. Gravida ridiculus magnis sociosqu phasellus gravida sed feugiat. Ornare praesent tempus congue sem; potenti nec ullamcorper. Proin natoque pharetra felis libero, ultricies metus penatibus. Primis eleifend maximus mi vulputate litora nisl.\r\n\r\nVel justo varius dictum rutrum vestibulum viverra dignissim. Curabitur leo laoreet sociosqu phasellus dapibus odio netus. Lacinia erat curabitur vehicula tellus libero pellentesque tempus. Placerat habitasse consequat sapien quis maximus tincidunt himenaeos curabitur laoreet. Quisque dignissim vivamus vivamus conubia gravida urna. Dictum nec interdum mattis dictumst class ornare turpis eu. Aenean laoreet pharetra nisi pulvinar consequat penatibus pharetra mus fringilla. Nostra sit feugiat proin convallis, vivamus nec.\r\n\r\nPer quisque scelerisque aenean mus nisi volutpat litora fringilla. In dignissim convallis nulla dolor, pulvinar dictum tellus. Curae per erat a ipsum mauris; elit aptent. Class ornare magna ridiculus; rutrum rhoncus cursus mollis etiam. Ad id ante ipsum imperdiet neque suspendisse. Scelerisque dictum dis fringilla; senectus ornare diam viverra quis. Vitae ipsum vehicula faucibus cras nisi vivamus parturient. Turpis cras conubia lacinia maecenas morbi euismod rhoncus in. Porta tincidunt velit nec pulvinar ullamcorper nullam tortor.\r\n\r\nQuisque inceptos penatibus ante class cras hac. Curabitur in ex tristique tellus, erat molestie. Velit fusce risus semper nisl placerat hac. Duis semper vivamus aptent gravida lacinia. Cubilia vivamus potenti maximus euismod; rutrum libero mi iaculis. Tempus magnis luctus gravida nibh eleifend ipsum scelerisque quis adipiscing.', '2024-12-22 17:23:42.630000', '2024-12-22 21:08:40.784000'),
(4, 2, 'Har har har', 'This is some funny stuff right here, I\'m not gonna lie', '2024-12-22 21:57:02.079000', '2024-12-22 21:57:02.079000'),
(5, 1, 'Let\'s do another', 'Here\'s another post to test out creating new posts! ', '2024-12-22 22:25:59.508000', '2024-12-22 22:25:59.508000');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

INSERT INTO `user` (`id`, `datejoined`, `email`, `password_hash`, `role`, `username`) VALUES
(1, '2024-12-21 14:08:28.855000', 'daemonanimations3d@gmail.com', '$2a$10$dGpbbrThTbpXhRXAUH.0ZeICsoofFF67AzfZjCiwNMNjymyhS5ejC', 'user', 'MaxwellsDaemon'),
(2, '2024-12-21 15:42:45.770000', 'canderson184@my.gcu.edu', '$2a$10$KfNwGdWkMmQ2RMWvGo3/jO5uk1lE2cPiUArxWj6AGljVR4y.uWuWO', 'user', 'ChanceMA'),
(3, '2024-12-22 22:27:27.071000', 'Example@google.com', '$2a$10$RhRs.Tb9I.ttuGiuQUyZteLGthRIG4PfsgPBJHgrsxAwwp2x.FwpS', 'user', 'AnotherUser'),
(4, '2024-12-22 22:34:31.219000', 'test@example.com', '$2a$10$dqf/.ot8.i6.h1nGkhrSUONddc5rp0dUBm3n2cpV1sh8ABOrlKKhO', 'user', 'TestUser');


ALTER TABLE `post`
  ADD CONSTRAINT `FK_post_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
