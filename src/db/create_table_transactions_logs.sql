CREATE TABLE `transactions_logs` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `started_at` date NOT NULL,
  `completed_at` date DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;