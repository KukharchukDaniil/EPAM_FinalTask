create database courses_db;
USE courses_db;

create table Users
(
    id       bigint primary key auto_increment not null,
    name varchar(30),
    username varchar(30),
    password varchar(30),
    role     enum ('student','admin','teacher')

);
alter table Users
    AUTO_INCREMENT = 0;

create table Courses
(
    id                  bigint primary key auto_increment not null,
    course_name         varchar(100),
    course_description  varchar(200),
    course_category enum ('math','biology','chemistry','computer_science')
);
alter table Courses
    AUTO_INCREMENT = 0;
create table Users_Courses
(
    id        bigint primary key auto_increment not null,
    course_id bigint,
    user_id   bigint
);

alter table Users_Courses
    add foreign key (course_id) references Courses (id),
    add foreign key (user_id) references Users (id),
    AUTO_INCREMENT = 0;
create table Marks
(
    student_id bigint,
    id         bigint primary key auto_increment,
    mark_date  date
);
alter table Marks
    add foreign key (student_id) references Users_Courses (id),
    AUTO_INCREMENT = 0;
insert into users(name, username, password, role)
values ('Daniel','admin', 'admin', 'admin');
insert into courses(course_name, course_category)
VALUES ('Example course', 'math');
insert into users_courses(course_id, user_id)
VALUES ('1', '1');




