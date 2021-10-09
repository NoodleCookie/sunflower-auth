create table `sunflower_blog`
(
    `bid`           varchar(30) not null primary key comment 'user id',
    `b_title`       varchar(30) not null unique comment 'username',
    `b_content_url`     varchar(100) comment 'content',
    `b_create_time` timestamp   not null comment 'account create time'
);