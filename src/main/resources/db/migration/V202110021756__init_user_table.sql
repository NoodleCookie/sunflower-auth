create table `sunflower_user`
(
    `sun_id`          varchar(30) not null primary key comment 'user id',
    `sun_username`    varchar(30) not null unique comment 'username',
    `sun_password`    varchar(30) not null comment 'password',
    `sun_enable`      tinyint(1) not null comment '0 means able, 1 means disable',
    `sun_create_time` timestamp   not null comment 'account create time'
);