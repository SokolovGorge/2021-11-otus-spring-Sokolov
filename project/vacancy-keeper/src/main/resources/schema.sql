DROP SCHEMA IF EXISTS VACANCY CASCADE;
CREATE SCHEMA VACANCY AUTHORIZATION vacancy;
GRANT ALL ON SCHEMA VACANCY TO vacancy;

CREATE TABLE VACANCY.CL_USER (ID BIGSERIAL NOT NULL,
                              LOGIN TEXT NOT NULL,
                              PASSWRD TEXT NOT NULL,
                              FIRST_NAME TEXT NOT NULL,
                              SUR_NAME TEXT NOT NULL,
                              PATH_NAME TEXT,
                              EMAIL TEXT,
                              TELEPHONE TEXT,
                              CONSTRAINT PK_CL_USER PRIMARY KEY (ID),
                              CONSTRAINT UQ_LOGIN UNIQUE (LOGIN)
);

CREATE TABLE VACANCY.CL_ROLE (
    ID BIGSERIAL NOT NULL,
    NAME TEXT NOT NULL,
    CONSTRAINT PK_CL_ROLE PRIMARY KEY (ID)
);

CREATE TABLE VACANCY.CL_USER_ROLE (
    USER_ID BIGINT NOT NULL,
    ROLE_ID BIGINT NOT NULL,
    CONSTRAINT PK_CL_USER_ROLE PRIMARY KEY (USER_ID, ROLE_ID),
    FOREIGN KEY (USER_ID) REFERENCES CL_USER(ID) ON DELETE CASCADE,
    FOREIGN KEY (ROLE_ID) REFERENCES CL_ROLE(ID) ON DELETE CASCADE
);


CREATE TABLE VACANCY.CL_PROF (ID BIGSERIAL NOT NULL,
                              CODE TEXT NOT NULL,
                              NAME TEXT NOT NULL,
                              CONSTRAINT PK_CL_PROF PRIMARY KEY (ID)
);

CREATE TABLE VACANCY.CL_AREA(ID BIGSERIAL NOT NULL,
                             CODE TEXT NOT NULL,
                             NAME TEXT NOT null,
                             CONSTRAINT PK_CL_AREA PRIMARY KEY (ID)
);

CREATE TABLE VACANCY.TASK (ID BIGSERIAL NOT NULL,
                           USER_ID BIGINT NOT NULL,
                           PROF_ID BIGINT NOT NULL,
                           AREA_ID BIGINT NOT NULL,
                           TITLE TEXT NOT NULL,
                           KEYWORDS TEXT,
                           CONSTRAINT PK_TASK PRIMARY KEY (ID),
                           FOREIGN KEY (USER_ID) REFERENCES CL_USER(ID) ON DELETE CASCADE,
                           FOREIGN KEY (PROF_ID) REFERENCES CL_PROF(ID),
                           FOREIGN KEY (AREA_ID) REFERENCES CL_AREA(ID)
);

CREATE TABLE VACANCY.VACANCY(ID BIGSERIAL NOT NULL,
                             SCODE TEXT NOT NULL,
                             SID TEXT NOT NULL,
                             NAME TEXT NOT NULL,
                             SALARY_MIN BIGINT,
                             SALARY_MAX BIGINT,
                             CURRENCY TEXT,
                             SCHEDULE TEXT,
                             ADDRESS TEXT,
                             ADDR_LAT FLOAT,
                             ADDR_LNG FLOAT,
                             EMPLOYER_NAME TEXT,
                             EMPLOYER_URL TEXT,
                             REQUIREMENT TEXT,
                             RESPONSIBILITY TEXT,
                             SOURCE_URL TEXT,
                             CONSTRAINT PK_VACANCY PRIMARY KEY (ID),
                             CONSTRAINT UQ_VACANCY UNIQUE (SCODE, SID)
);

CREATE TABLE VACANCY.VACANCY_LINK (ID BIGSERIAL NOT NULL,
                                   TASK_ID BIGINT NOT NULL,
                                   VACANCY_ID BIGINT NOT NULL,
                                   CONSTRAINT PK_VACANCY_LINK PRIMARY KEY (ID),
                                   CONSTRAINT UQ_VACANCY_LINK UNIQUE (TASK_ID, VACANCY_ID),
                                   FOREIGN KEY (TASK_ID) REFERENCES TASK(ID) ON DELETE CASCADE,
                                   FOREIGN KEY (VACANCY_ID) REFERENCES VACANCY(ID) ON DELETE CASCADE

);
