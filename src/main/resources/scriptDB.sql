DROP SCHEMA IF EXISTS user_management;

CREATE SCHEMA IF NOT EXISTS user_management DEFAULT CHARACTER SET utf8;
USE user_management;

DROP TABLE IF EXISTS user_management.user_account;

CREATE TABLE IF NOT EXISTS `user_management`.`user_account`
(
  user_id      int          not null auto_increment,
  username     varchar(16)  not null,
  password     varchar(100) not null,
  first_name   varchar(16)  not null,
  last_name    varchar(16)  not null,
  user_status  int          not null,
  created_date timestamp    not null default now(),
  PRIMARY KEY (`user_id`)
);

DROP TABLE IF EXISTS user_management.role;

CREATE TABLE IF NOT EXISTS user_management.role
(
  role_id   int         not null auto_increment,
  role_name varchar(10) not null,
  PRIMARY KEY (`role_id`)
);

DROP TABLE IF EXISTS user_management.user_account_role;

CREATE TABLE IF NOT EXISTS user_management.user_account_role
(
  id      int not null auto_increment,
  user_id int not null,
  role_id int not null,
  PRIMARY KEY (id)
);

alter table user_management.user_account_role
  add constraint user_role_FK1 foreign key (user_id)
    references user_management.user_account (user_id);

alter table user_management.user_account_role
  add constraint user_role_FK2 foreign key (role_id)
    references user_management.role (role_id);


CREATE TABLE Persistent_Logins
(
  username  varchar(16) not null,
  series    varchar(64) not null,
  token     varchar(64) not null,
  last_used timestamp   not null,
  PRIMARY KEY (series)
);

-- password admin1
insert into user_management.user_account (user_id, username, password, first_name, last_name, user_status)
values (1, 'admin', '$2a$10$tENP2xX98u7P9oYhXTbYAuvRxppKxuCOLwMyNhJ6b0C2IszWtByLK', 'admin', 'admin', 1);

-- password user1
insert into user_management.user_account (user_id, username, password, first_name, last_name, user_status)
values (2, 'user', '$2a$10$juTILDKWiyru482x.xEiaOGRoKIbdcLeFDWWx7Hm8xQmoJKM./VD2', 'user', 'user', 1);

insert into user_management.role (role_id, role_name)
values (1, 'ADMIN');

insert into user_management.role (role_id, role_name)
values (2, 'USER');

insert into user_management.user_account_role (id, user_id, role_id)
values (1, 1, 1);

insert into user_management.user_account_role (id, user_id, role_id)
values (2, 2, 2);
