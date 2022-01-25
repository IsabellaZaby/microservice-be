create table if not exists sensors
(
    id          serial primary key not null ,
    sensor_id   varchar(90),
    timestamp   timestamp,
    temperature numeric,
    humidity    decimal(5, 2) CHECK (humidity <= 100.00)
);

insert into sensors values (DEFAULT, '1234', '2021-03-31 9:30:20', 12, 23);
insert into sensors values (DEFAULT, '12345', '2022-01-01 9:30:20', 12, 100);
insert into sensors values (DEFAULT, '123', '2022-01-01 9:30:20', 12, 89);
insert into sensors values (DEFAULT, '1234', '2017-03-31 9:30:20', 45, 20);
insert into sensors values (DEFAULT, '12345', '2017-03-31 9:30:20', 30, 45);
insert into sensors values (DEFAULT, '123', '2017-03-31 9:30:20', 10, 33);
insert into sensors values (DEFAULT, '1234', '2022-01-18 9:30:20', 0, 77);
insert into sensors values (DEFAULT, '12345', '2017-03-31 9:30:20', 7, 65);
insert into sensors values (DEFAULT, '123', '2022-01-01 10:30:20', 29, 24);
insert into sensors values (DEFAULT, '1234', '2017-03-31 9:30:20', 35, 10);
insert into sensors values (DEFAULT, '12345', '2022-01-17 9:30:20', 9, 23);
insert into sensors values (DEFAULT, '123', '2017-03-31 9:30:20', 11, 23);
insert into sensors values (DEFAULT, '1234', '2017-03-31 9:30:20', 14, 16);
insert into sensors values (DEFAULT, '12345', '2017-03-31 9:30:20', 20, 17);
insert into sensors values (DEFAULT, '123', '2017-03-31 9:30:20', 16, 50);
insert into sensors values (DEFAULT, '1234', '2017-03-31 9:30:20', 40, 98);
