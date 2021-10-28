create table `sunflower_publisher_topic`
(
    `id`             int(5) primary key auto_increment comment 'card id',
    `publisher_host` varchar(100) not null comment 'pub',
    `topic_name`     varchar(100) not null comment 'sub'
);