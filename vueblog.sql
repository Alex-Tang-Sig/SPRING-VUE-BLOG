DROP DATABASE IF EXISTS vueblog;
CREATE DATABASE vueblog;
USE vueblog;

DROP TABLE IF EXISTS m_user;
CREATE TABLE m_user (
    id bigint(20) not null auto_increment,
    username varchar(64) not null,
    avatar varchar(255) default null,
    email varchar(64) not null,
    password varchar(64) not null,
    status int(5) not null,
    created datetime default null,
    last_login datetime default null,
    primary key(`id`),
    key `uk_username` (`username`) using btree
) engine=InnoDB default  charset=utf8;

insert into `m_user` values('1', 'alex', 'https://imahe-1663', 'ting_tang@yeah.nedt', '96e79218965eb72c92a549dd5a330112', '0', '2020-02-01 10:44:01', null);

DROP TABLE IF EXISTS m_blog;
CREATE TABLE m_blog (
    id bigint(20) not null auto_increment,
    user_id bigint(20) not null,
    title varchar(255) not null,
    description varchar(255) not null,
    content longtext,
    created datetime not null on update CURRENT_TIMESTAMP,
    status tinyint(4) default null,
    primary key (`id`)
) engine=InnoDB default charset=utf8;

insert into m_blog(id, user_id, title, description, content, created, status)
values(1, 1, 'dfsfsd', 'dfsfdsf','dfsfdsfdsfdsfsdfcontent','2012-12-12 23:12:22', 0);
