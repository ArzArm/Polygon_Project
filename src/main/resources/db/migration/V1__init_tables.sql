create table convertor.eurotousd (
    id serial primary key,
    date date NOT NULL,
    averageprice double precision NOT NULL,
    closeprice double precision NOT NULL,
    highestprice double precision NOT NULL,
    lowestPrice double precision NOT NULL,
    openprice double precision NOT NULL);

create table security.users (
                           id serial,
                           username varchar(255) NOT NULL,
                           password varchar(500) NOT NULL,
                           role varchar (255) NOT NULL);