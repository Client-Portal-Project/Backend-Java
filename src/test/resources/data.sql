INSERT INTO Users (email, password, firstName, lastName, approved)
    VALUES ('test@test.com','test', 'firstName', 'lastName', true);
INSERT INTO Applicants (aboutMe, educationLevel, educationField, employmentStatus, userId)
    VALUES ('aboutMe', 'educationLevel', 'educationField', 'employmentStatus', 1);
INSERT INTO Clients (companyName, userId)
    VALUES ('companyName', 1);
INSERT INTO Needs (amountNeeded, amountFulfilled, educationField, yearsExperience, extraDescription, jobTitle, educationLevel)
    VALUES (1,1,'educationField', 1, 'extraDescriptions', 'jobTitle', 'educationLevel');