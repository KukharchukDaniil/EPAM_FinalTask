CREATE TABLE `courses` (
                           `id` BIGINT NOT NULL AUTO_INCREMENT,
                           `course_name` varchar(100) DEFAULT NULL,
                           `course_description` varchar(400) DEFAULT NULL,
                           `course_category` enum('math','biology','chemistry','computer_science') DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `solutions` (
                             `id` BIGINT NOT NULL AUTO_INCREMENT,
                             `task_id` BIGINT NOT NULL,
                             `user_id` BIGINT NOT NULL,
                             `solution_status` enum('sent','graded','not_solved') DEFAULT NULL,
                             `value` varchar(300) DEFAULT NULL,
                             `mark` INT UNSIGNED DEFAULT NULL,
                             `comment` varchar(100) DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             KEY `task_id_idx` (`task_id`),
                             KEY `user_id_idx` (`user_id`,`task_id`),
                             CONSTRAINT `task` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`) ON DELETE CASCADE,
                             CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tasks` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `course_id` bigint DEFAULT NULL,
                         `name` varchar(45) DEFAULT NULL,
                         `description` varchar(245) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `sss_idx` (`course_id`),
                         CONSTRAINT `course_id` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `username` varchar(30) DEFAULT NULL,
                         `name` varchar(45) DEFAULT NULL,
                         `password` varchar(30) DEFAULT NULL,
                         `role` enum('student','admin','teacher') DEFAULT 'student',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users_courses` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `course_id` bigint NOT NULL,
                                 `user_id` bigint NOT NULL,
                                 PRIMARY KEY (`id`,`course_id`,`user_id`),
                                 KEY `users_courses_ibfk_1` (`course_id`),
                                 KEY `users_courses_ibfk_2` (`user_id`),
                                 CONSTRAINT `users_courses_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
                                 CONSTRAINT `users_courses_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;