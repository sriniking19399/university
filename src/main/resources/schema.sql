create table if not exists professor(
    id int primary key AUTO_INCREMENT,
    name text,
    department text
);
create table if not exists course(
    id int primary key AUTO_INCREMENT,
    name text,
    credits int,
    professorId int,
    foreign key(professorId)references professor(id)
);
create table if not exists student(
    id int primary key AUTO_INCREMENT,
    name text,
    email text
);

create table if not exists course_student(
    studentId int,
    courseId int,
    primary key(studentId,courseId),
    foreign key (studentId)references student(id),
    foreign key(courseId)references course(id)
);