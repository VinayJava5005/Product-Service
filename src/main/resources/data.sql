DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS product_quantity;

create table product
(
    id          varchar(36) primary key,
    name        varchar(50)  not null,
    description varchar(1024) not null,
    cost        float        not null
);

create table product_quantity
(
    id         long primary key auto_increment,
    quantity   int not null,
    product_id varchar(36),
    foreign key (product_id) references product (id)
);


insert into product(id, name, description, cost)
values ('35fdd5b8-b7e0-11ea-b3de-0242ac130004', 'Google Pixel 3a', '(Clearly White, 64 GB)  (4 GB RAM)', 29999);
insert into product(id, name, description, cost)
values ('40e021a2-b7e0-11ea-b3de-0242ac130004', 'Nokia 6.1 Plus', '(Blue, 64 GB)  (6 GB RAM)', 11329);


insert into product_quantity(id, quantity, product_id)
values (1, 100, '35fdd5b8-b7e0-11ea-b3de-0242ac130004');
insert into product_quantity(id, quantity, product_id)
values (2, 50, '40e021a2-b7e0-11ea-b3de-0242ac130004');