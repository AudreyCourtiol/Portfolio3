# Portfolio3
--creating and filling the 4 tables

DROP TABLE IF EXISTS Professor;

CREATE TABLE IF NOT EXISTS Professor(
ProfessorID integer Primary key, ProfessorName TEXT NOT NULL, ProfessorAddress TEXT NOT NULL
);

DROP TABLE IF EXISTS Course;

CREATE TABLE IF NOT EXISTS Course( CourseID integer PRIMARY KEY , CourseName TEXT NOT NULL, ProfessorID integer,
FOREIGN KEY (ProfessorID) REFERENCES Professor (ProfessorID) ON DELETE RESTRICT ON UPDATE CASCADE
);

DROP TABLE IF EXISTS Student;

CREATE TABLE IF NOT EXISTS Student( StudentID integer PRIMARY KEY, StudentName TEXT NOT NULL, StudentAddress TEXT NOT NULL
);

DROP TABLE IF EXISTS Grade;

CREATE TABLE IF NOT EXISTS Grade( Grade FLOAT, CourseID TEXT NOT NULL, StudentID TEXT NOT NULL,
FOREIGN KEY (CourseID) REFERENCES Course (CourseName) ON DELETE RESTRICT ON UPDATE CASCADE,
FOREIGN KEY (StudentID) REFERENCES Student (StudentName)  ON DELETE RESTRICT ON UPDATE CASCADE,
PRIMARY KEY (CourseID, StudentID)
);

INSERT INTO Professor (ProfessorID, ProfessorName, ProfessorAddress) VALUES (1, 'Line Reinhardt', '4800 Nykøbing F Denmark'),
(2,'Bo Holst','4000 Roskilde Denmark');

INSERT INTO Course (CourseID, CourseName, ProfessorID) VALUES (1, 'SD 2020 autumn', 1),
(2, 'SD 2021 autumn', 1),
(3, 'ES1 2020 autumn', 2);

INSERT INTO Student(StudentID, StudentName, StudentAddress)
VALUES (1, 'Aisha Lincoln', '4800 Nykøbing F Denmark'), (2, 'Anya Nielsen', '4800 Nykøbing F Denmark'),
(3, 'Alfred Jensen', 'Karlskrona Sweden'), (4, 'Berta Bertelsen', '7190 Billund Denmark'),
(5, 'Albert Antonsen', '4180 Sorø Denmark'), (6, 'Eske Eriksen', '4863 Eskildstrup Denmark'),
(7, 'Olaf Olesen', '5000 Odense Denmark'), (8, 'Salma Simonsen', 'Stockholm Sweden'),
(9, 'Theis Thomasen', '4340 Tølløse Denmark'), (10, 'Janet Jensen', '4040 Jyllinge Denmark'),
(11, 'Egon Damdrup', '4000 Roskilde, Denmark');

INSERT INTO Grade (Grade, CourseID, StudentID)
VALUES (12, 1, 1), (10, 3, 1), (null, 2, 2), (12, 3, 2), (7, 1, 3), (10, 3, 3), (null, 2, 4), (2, 3, 4), (10, 1, 5),
(7, 3, 5), (null, 2, 6), (10, 3, 6), (4, 1, 7), (12, 3, 7), (null, 2, 8), (12, 3, 8), (12, 1, 9), (12, 3, 9),
(null, 2, 10), (7, 3, 10), (null, 2, 11), (2, 3, 11);

CREATE VIEW allinfo as
select S.StudentID as SID,
C.CourseID as CID, C.ProfessorID as PID,
P.ProfessorID as PID,

       G.StudentID from Grade as g
        inner join Student S on g.StudentID = S.StudentName
        inner join Course C on g.CourseID = C.CourseName
        inner join Professor P on C.ProfessorID = P.ProfessorID;

-- select student and print out courses taken

--ask to see grade(s)

--if grade = null, type in grade

--get average grade of a student

SELECT AVG(Grade)
FROM Grade where StudentID=1;

SELECT AVG(Grade)
FROM Grade where StudentID=2;

SELECT AVG(Grade)
FROM Grade where StudentID=3;

SELECT AVG(Grade)
FROM Grade where StudentID=4;

SELECT AVG(Grade)
FROM Grade where StudentID=5;

SELECT AVG(Grade)
FROM Grade where StudentID=6;

SELECT AVG(Grade)
FROM Grade where StudentID=7;

SELECT AVG(Grade)
FROM Grade where StudentID=8;

SELECT AVG(Grade)
FROM Grade where StudentID=9;

SELECT AVG(Grade)
FROM Grade where StudentID=10;

SELECT AVG(Grade)
FROM Grade where StudentID=11;

--get average grade in a course

SELECT AVG(Grade)
FROM Grade where CourseID=1;

SELECT AVG(Grade)
FROM Grade where CourseID=2;

SELECT AVG(Grade)
FROM Grade where CourseID=3;