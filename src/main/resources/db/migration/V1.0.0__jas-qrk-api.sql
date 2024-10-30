create sequence my_entity_SEQ start with 1 increment by 50;

create table my_entity (
    id bigint not null,
    field varchar(255),
    primary key (id)
);

 insert into my_entity (id, field) values(1, 'field-1');
 insert into my_entity (id, field) values(2, 'field-2');
 insert into my_entity (id, field) values(3, 'field-3');
 alter sequence my_entity_seq restart with 4;