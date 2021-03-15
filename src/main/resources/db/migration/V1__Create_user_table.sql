create table user
(
    id           int auto_increment primary key,
    account_id   varchar(50) not null,
    name         varchar(30) null,
    token        char(36)    null,
    gmt_create   bigint      null,
    gmt_modified bigint      null,
    constraint user_account_id_uindex
        unique (account_id)
);