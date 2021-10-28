create table `sunflower_topic_word`
(
    `id`         int(5) primary key auto_increment comment 'card id',
    `topic_name` varchar(64) not null comment 'pub',
    `word_name`  varchar(64) not null comment 'sub'
);
