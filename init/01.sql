DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES (1,_binary '','$2a$10$zFvKS9s01TW2JNUQGJxype8idma74TOZgY8.sQ7AfFP9EAcCCxUwG','user','marc'),(2,_binary '','$2a$10$XsBEDMbgK0h29Kc7YD8nUOlzzOPVy/y3cr3Jtq1m.cC4P88dt97xe','user','lars'),(3,_binary '','$2a$10$HdqHYVN8iMAfs8.QYin4/OS515Qur89JVN7x3Z//J0B6Shg8wDstO','user','morten');
UNLOCK TABLES;