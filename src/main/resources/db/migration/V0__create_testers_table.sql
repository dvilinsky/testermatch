DROP TABLE IF EXISTS tester;
CREATE TABLE tester(
    testerId INT NOT NULL PRIMARY KEY,
    firstName TEXT,
    lastName TEXT,
    country TEXT,
    lastLogin TIMESTAMP
);