create table `sunflower_publisher_subscriber`
(
    `id`         int(5) primary key auto_increment comment 'card id',
    `publisher`  varchar(64) not null comment 'pub',
    `subscriber` varchar(64) not null comment 'sub'
);

create table `sunflower_topic`
(
    `name`         varchar(100) primary key not null comment 'name',
    `description`  varchar(512)
);
