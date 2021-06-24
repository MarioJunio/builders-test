CREATE TABLE customer(
    id bigint primary key auto_increment,
    name varchar(60) not null,
    email varchar(300),
    phone varchar(20),
    document varchar(20),
    gender varchar(10),
    created_at timestamp,
    version bigint default 0
);