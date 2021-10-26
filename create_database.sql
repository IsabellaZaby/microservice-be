create table if not exists sensors
(
    id          serial primary key not null ,
    sensor_id   varchar(90),
    timestamp   timestamp,
    temperature numeric,
    humidity    decimal(5, 2) CHECK (humidity <= 100.00)
);

insert into sensors values (DEFAULT, '1234', '2017-03-31 9:30:20', 12, 23);
insert into sensors values (DEFAULT, '12345', '2017-03-31 9:30:20', 12, 23);
insert into sensors values (DEFAULT, '123', '2017-03-31 9:30:20', 12, 23);