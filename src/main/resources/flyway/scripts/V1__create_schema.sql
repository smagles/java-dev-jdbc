create TABLE `buildings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(45) NOT NULL,
  `flats_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

create TABLE `flats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) NOT NULL,
  `level` tinyint(1) NOT NULL,
  `square` float NOT NULL,
  `building_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_fk2building_idx` (`building_id`),
  CONSTRAINT `fk_fk2building` FOREIGN KEY (`building_id`) REFERENCES `buildings` (`id`)
);

create TABLE `members` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `email` varchar(100) NOT NULL,
  `access_right` tinyint(1) NOT NULL,
  `flat_ownership` tinyint(10) NOT NULL,
  `flat_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_member_flat_idx` (`flat_id`),
  CONSTRAINT `fk_member_flat` FOREIGN KEY (`flat_id`) REFERENCES `flats` (`id`)
);

create TABLE `members_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` enum('member','staff','board member','chairperson') DEFAULT 'member',
  `member_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_role2member_idx` (`member_id`),
  CONSTRAINT `fk_role2member` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
);

create TABLE `residents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `member_id` int(11) NOT NULL,
  `flat_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_resident_member_idx` (`member_id`),
  KEY `fk_resident_flat_idx` (`flat_id`),
  CONSTRAINT `fk_resident_flat` FOREIGN KEY (`flat_id`) REFERENCES `flats` (`id`),
  CONSTRAINT `fk_resident_member` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
);
