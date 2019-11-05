drop table if exists `test_member`;

create table `test_member` (
  `id` bigint(20) not null auto_increment primary key,
  `create_date` datetime not null default current_timestamp,
  `modify_date` datetime not null default current_timestamp,
  `username` varchar(32) not null,
  `phone` varchar(16) not null,
  `amount` decimal(18,2) default 0,
  `age` int(11) not null default 0,
  `height` float(10,2) not null default 0,
  `weight` double(10,4) not null default 0,
  `enable` bit(1) not null default b'0'
) engine=InnoDB default charset=utf8;