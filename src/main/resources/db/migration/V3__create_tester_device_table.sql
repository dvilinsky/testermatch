DROP TABLE IF EXISTS tester_device;
CREATE TABLE tester_device(
    testerId INT REFERENCES tester(testerId),
    deviceId INT REFERENCES device(deviceId)
)