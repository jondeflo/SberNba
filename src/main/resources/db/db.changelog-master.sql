-- liquibase formatted sql

-- changeset Andrey:1663343731114-1
CREATE TABLE "public"."rates" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, "description" VARCHAR(255), "timestamp" TIMESTAMP WITHOUT TIME ZONE, "discipline_id" BIGINT NOT NULL, "rate_id" BIGINT NOT NULL, "student_id" BIGINT NOT NULL, CONSTRAINT "rates_pkey" PRIMARY KEY ("id"));

-- changeset Andrey:1663343731114-2
CREATE TABLE "public"."s_disciplines" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, "name" VARCHAR(255), CONSTRAINT "s_disciplines_pkey" PRIMARY KEY ("id"));

-- changeset Andrey:1663343731114-3
CREATE TABLE "public"."s_groups" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, "name" VARCHAR(255), CONSTRAINT "s_groups_pkey" PRIMARY KEY ("id"));

-- changeset Andrey:1663343731114-4
CREATE TABLE "public"."s_rates" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, "description" VARCHAR(255), "rate" INTEGER, CONSTRAINT "s_rates_pkey" PRIMARY KEY ("id"));

-- changeset Andrey:1663343731114-5
CREATE TABLE "public"."students" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, "age" INTEGER, "name" VARCHAR(255), "surname" VARCHAR(255), "group_id" BIGINT NOT NULL, CONSTRAINT "students_pkey" PRIMARY KEY ("id"));

-- changeset Andrey:1663343731114-6
INSERT INTO "public"."rates" ("id", "description", "timestamp", "discipline_id", "rate_id", "student_id") VALUES (1, 'Almost as Perelman', '2022-09-16 12:16:12.718', 1, 4, 6),(2, 'Good', '2022-09-16 12:16:48.851', 1, 3, 1),(3, 'Acceptable', '2022-09-16 12:16:58.097', 1, 2, 1),(4, 'Good', '2022-09-16 12:17:10.713', 5, 3, 1),(5, 'Bad', '2022-09-16 12:17:21.733', 2, 1, 2),(6, 'Acceptable', '2022-09-16 12:17:26.853', 2, 2, 2),(7, 'Good', '2022-09-16 12:17:37.726', 4, 3, 2),(8, 'Good', '2022-09-16 12:17:51.959', 3, 3, 3),(9, 'Perfect', '2022-09-16 12:17:58.496', 3, 4, 4),(10, 'Good', '2022-09-16 12:18:12.338', 3, 3, 6),(11, 'Acceptable', '2022-09-16 12:18:22.591', 4, 2, 6),(12, 'Bad', '2022-09-16 12:18:31.695', 1, 1, 4),(13, 'Perfect', '2022-09-16 12:18:42.633', 5, 4, 4),(14, 'Acceptable', '2022-09-16 12:18:49.003', 5, 2, 7),(15, 'Good', '2022-09-16 12:18:56.791', 2, 3, 7),(16, 'Acceptable', '2022-09-16 12:19:10.369', 3, 2, 7),(17, 'Perfect', '2022-09-16 12:19:17.47', 3, 4, 8),(18, 'Perfect', '2022-09-16 12:19:24.784', 1, 4, 8),(19, 'Perfect', '2022-09-16 12:19:37.239', 5, 4, 6),(20, 'Perfect', '2022-09-16 12:19:46.507', 1, 4, 6),(21, 'Good', '2022-09-16 12:19:57.865', 4, 3, 8);;

-- changeset Andrey:1663343731114-7
INSERT INTO "public"."s_disciplines" ("id", "name") VALUES (1, 'Math'),(2, 'Physics'),(3, 'Chemistry'),(4, 'Biology'),(5, 'Medicine');;

-- changeset Andrey:1663343731114-8
INSERT INTO "public"."s_groups" ("id", "name") VALUES (1, 'Genius'),(2, 'Eggheads'),(3, 'Averages'),(4, 'Fools'),(5, 'Brainless');;

-- changeset Andrey:1663343731114-9
INSERT INTO "public"."s_rates" ("id", "description", "rate") VALUES (4, 'Perfect', 5),(1, 'Bad', 2),(3, 'Good', 4),(2, 'Acceptable', 3);;

-- changeset Andrey:1663343731114-10
INSERT INTO "public"."students" ("id", "age", "name", "surname", "group_id") VALUES (1, 20, 'Ivan', 'Ivanov', 3),(2, 21, 'Petr', 'Petrov', 1),(3, 21, 'Semen', 'Semenov', 2),(6, 42, 'Andrey', 'Goryunov', 1),(7, 23, 'Oleg', 'Olegov', 4),(8, 20, 'Vladimir', 'Vladimirov', 5),(5, 27, 'Anna', 'Pavlova', 3),(4, 25, 'Nikolai', 'Nikolaev', 4),(9, 29, 'Anna', 'Nepavlova', 5),(10, 21, 'Sergey', 'Semenov', 2);;

-- changeset Andrey:1663343731114-11
ALTER TABLE "public"."rates" ADD CONSTRAINT "fk4gffhamyl28gdvgkqco5km6v4" FOREIGN KEY ("student_id") REFERENCES "public"."students" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset Andrey:1663343731114-12
ALTER TABLE "public"."rates" ADD CONSTRAINT "fkrsyket0xrpo6axcvtopa2erwh" FOREIGN KEY ("discipline_id") REFERENCES "public"."s_disciplines" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset Andrey:1663343731114-13
ALTER TABLE "public"."students" ADD CONSTRAINT "fkticp806kcp7w9rxmh3v4v83ge" FOREIGN KEY ("group_id") REFERENCES "public"."s_groups" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

SELECT MAX(id) FROM students;
SELECT nextval('students_id_seq');
BEGIN;
LOCK TABLE students IN EXCLUSIVE MODE;
SELECT setval('students_id_seq', COALESCE((SELECT MAX(id)+1 FROM students), 1), false);
COMMIT;

SELECT MAX(id) FROM rates;
SELECT nextval('rates_id_seq');
BEGIN;
LOCK TABLE students IN EXCLUSIVE MODE;
SELECT setval('rates_id_seq', COALESCE((SELECT MAX(id)+1 FROM rates), 1), false);
COMMIT;