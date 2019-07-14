DROP TABLE IF EXISTS bug;
CREATE TABLE bug(
    bugId INT NOT NULL PRIMARY KEY,
    testerId INT REFERENCES tester(testerId),
    deviceId INT REFERENCES device(deviceId)
);