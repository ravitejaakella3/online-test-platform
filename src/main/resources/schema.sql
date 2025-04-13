-- Drop existing tables in correct order (due to foreign key constraints)
DROP TABLE IF EXISTS student_answers CASCADE;
DROP TABLE IF EXISTS questions CASCADE;
DROP TABLE IF EXISTS tests CASCADE;
DROP TABLE IF EXISTS students CASCADE;

-- Create tables
CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE tests (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    duration INTEGER NOT NULL
);

CREATE TABLE questions (
    id SERIAL PRIMARY KEY,
    test_id BIGINT REFERENCES tests(id),
    text TEXT NOT NULL
);

CREATE TABLE student_answers (
    id SERIAL PRIMARY KEY,
    student_id BIGINT REFERENCES students(id),
    test_id BIGINT REFERENCES tests(id),
    question_id BIGINT REFERENCES questions(id),
    response TEXT,
    status VARCHAR(20),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    CONSTRAINT unique_student_test_question UNIQUE (student_id, test_id, question_id)
);