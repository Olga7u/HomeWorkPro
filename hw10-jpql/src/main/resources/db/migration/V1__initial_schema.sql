-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence if not exists client_SEQ start with 1 increment by 1;

create table if not exists client
(
    id   bigint not null primary key,
    name varchar(50) not null
);

-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
create table if not exists address
(
    id bigserial not null primary key,
    street varchar(50) not null,
    client_id bigint not null unique
);

alter table if exists address
       add constraint address_client
       foreign key (client_id)
       references client;

create table if not exists phone
(
    id bigserial not null primary key,
    number varchar(20) not null,
    client_id bigint not null
);

alter table if exists phone
       add constraint phone_client
       foreign key (client_id)
       references client;