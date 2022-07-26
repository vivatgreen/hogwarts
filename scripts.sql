SELECT*
FROM student;

SELECT*
FROM student
where age >= 10
  AND age <= 20;

SELECT name
from student;

SELECT *
FROM student
WHERE name LIKE '%п%'
   or name LIKE '%П%';

SELECT *
FROM student
WHERE age < id;

SELECT *
FROM student
ORDER BY age;