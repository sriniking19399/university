insert into professor(name,department)values('John Smith','Computer Science'),('Mary Johnson','Physics'),('David Lee','Mathematics');

insert into course(name,credits,professorId) values('Introduction to Programming',3,2),('Quantum Mechanics',4,2),('Calculus',4,3);

insert into student(name,email) values('Alice Johnson','alice@example.com'),('Bob Davis','bob@example.com'),('Eva Wilson','eva@example.com');

insert into course_student(courseId,studentId)values(1,1),(1,2),(2,2),(2,3),(3,1),(3,3);