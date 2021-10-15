create table `sunflower_word_card`
(
    `id`           int(5) primary key auto_increment comment 'card id',
    `eng`          varchar(50)  not null comment 'eng',
    `chi`          varchar(100) not null comment 'chi',
    `created_by`   varchar(30)  not null comment 'account create user',
    `created_time` timestamp    not null comment 'account create time'
);