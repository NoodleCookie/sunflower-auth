create table `sunflower_word_audio`
(
    `word`         varchar(30) primary key,
    `audio`        blob,
    `created_by`   varchar(30) not null comment 'account create user',
    `created_time` timestamp   not null comment 'account create time'
);