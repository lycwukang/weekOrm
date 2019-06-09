create table `test_member` (
  `id` bigint(20) not null auto_increment primary key,
  `create_date` datetime not null default current_timestamp,
  `username` varchar(32) not null,
  `phone` varchar(16) not null,
  `amount` decimal(18,2) default 0
) engine=InnoDB default charset=utf8;