drop table if exists user;
create table user
(
   id integer not null auto_increment,
   username varchar(255) not null,
   name varchar(255) not null,
   password varchar(255) not null,
   phone varchar(255) not null,
   role varchar(255) not null,
   primary key(id)
);
drop table if exists ratings;
create table ratings
(
   id integer not null auto_increment,
   user_id integer not null,
   repo_id integer not null,
   rating integer not null,
   primary key(id)
);