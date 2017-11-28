/*
Created: 4/10/2016
Modified: 4/10/2016
Model: nathaz
Database: MySQL 5.6
*/


-- Create tables section -------------------------------------------------

-- Table QuestionSelected

CREATE TABLE `QuestionSelected`
(
  `QuestionId` Int NOT NULL,
  `RefSurveyId` Int NOT NULL,
  `InstructorSurveyId` Int(11) NOT NULL
)
;

ALTER TABLE `QuestionSelected` ADD  PRIMARY KEY (`QuestionId`,`RefSurveyId`,`InstructorSurveyId`)
;

-- Table Instructor Survey

CREATE TABLE `Instructor Survey`
(
  `InstructorSurveyId` Int(11) NOT NULL,
  `InstructorName` Varchar(20) NOT NULL,
  `InstructorEmail` Varchar(40) NOT NULL
)
;

ALTER TABLE `Instructor Survey` ADD  PRIMARY KEY (`InstructorSurveyId`)
;

-- Table Person

CREATE TABLE `Person`
(
  `PersonId` Int(11) NOT NULL,
  `PersonName` Varchar(30) NOT NULL
)
;

ALTER TABLE `Person` ADD  PRIMARY KEY (`PersonId`)
;

-- Table Response

CREATE TABLE `Response`
(
  `ResponseId` Int NOT NULL,
  `ResponseValue` Varchar(20) NOT NULL,
  `QuestionId` Int NOT NULL,
  `RefSurveyId` Int NOT NULL,
  `PersonId` Int(11) NOT NULL,
  `GUID` Int NOT NULL DEFAULT 1000 AUTO_INCREMENT,
  `InstructorSurveyId` Int(11) NOT NULL
)
;

ALTER TABLE `Response` ADD  PRIMARY KEY (`QuestionId`,`RefSurveyId`,`PersonId`,`GUID`,`InstructorSurveyId`)
;

-- Table QuestionsList

CREATE TABLE `QuestionsList`
(
  `QuestionId` Int NOT NULL,
  `QuestionDescription` Varchar(100) NOT NULL,
  `RefSurveyId` Int NOT NULL
)
;

ALTER TABLE `QuestionsList` ADD  PRIMARY KEY (`QuestionId`,`RefSurveyId`)
;

-- Table ReferenceSurvey

CREATE TABLE `ReferenceSurvey`
(
  `RefSurveyId` Int NOT NULL,
  `RefSurveyLink` Varchar(150) NOT NULL
)
;

ALTER TABLE `ReferenceSurvey` ADD  PRIMARY KEY (`RefSurveyId`)
;

-- Create relationships section ------------------------------------------------- 

ALTER TABLE `QuestionsList` ADD CONSTRAINT `on/ consists` FOREIGN KEY (`RefSurveyId`) REFERENCES `ReferenceSurvey` (`RefSurveyId`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `QuestionSelected` ADD CONSTRAINT `of/ for` FOREIGN KEY (`QuestionId`, `RefSurveyId`) REFERENCES `QuestionsList` (`QuestionId`, `RefSurveyId`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Response` ADD CONSTRAINT `for / consists of` FOREIGN KEY (`QuestionId`, `RefSurveyId`, `InstructorSurveyId`) REFERENCES `QuestionSelected` (`QuestionId`, `RefSurveyId`, `InstructorSurveyId`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Response` ADD CONSTRAINT `given by/ giving` FOREIGN KEY (`PersonId`) REFERENCES `Person` (`PersonId`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `QuestionSelected` ADD CONSTRAINT `on/ for` FOREIGN KEY (`InstructorSurveyId`) REFERENCES `Instructor Survey` (`InstructorSurveyId`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

