create table `sunflower_mall_commodity`
(
    `sku`            varchar(100) primary key,
    `commodity_name` varchar(100),
    `pic_url`        varchar(200),
    `created_by`     varchar(30),
    `created_time`   timestamp
);