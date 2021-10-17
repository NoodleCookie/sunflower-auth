create table `sunflower_word_explain`
(
    `id`           int(5) primary key auto_increment comment 'explain id',
    `type`         varchar(50)  not null comment 'part of speech',
    `ch`           varchar(512) not null comment 'chi',
    `created_by`   varchar(30)  not null comment 'create user',
    `created_time` timestamp    not null comment 'create time'
);