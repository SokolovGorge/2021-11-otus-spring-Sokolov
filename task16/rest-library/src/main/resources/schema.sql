DROP TABLE IF EXISTS REMARK;
DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS AUTHOR;
DROP TABLE IF EXISTS GENRE;

CREATE TABLE AUTHOR (
                        ID BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                        FIRSTNAME VARCHAR(255) NOT NULL,
                        SURNAME VARCHAR(255) NOT NULL
);

CREATE TABLE GENRE (
                       ID BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                       NAME VARCHAR(255) NOT NULL
);

CREATE TABLE BOOK (
                      ID BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                      AUTHOR_ID BIGINT NOT NULL,
                      GENRE_ID BIGINT NOT NULL,
                      TITLE VARCHAR(255) NOT NULL,
                      FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHOR(ID),
                      FOREIGN KEY (GENRE_ID) REFERENCES GENRE(ID)
);

CREATE TABLE REMARK (
                        ID BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                        BOOK_ID BIGINT NOT NULL,
                        TEXT VARCHAR(1024) NOT NULL,
                        FOREIGN KEY (BOOK_ID) REFERENCES BOOK(ID) ON DELETE CASCADE
);
