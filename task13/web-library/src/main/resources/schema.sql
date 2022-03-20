DROP TABLE IF EXISTS REMARK;
DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS AUTHOR;
DROP TABLE IF EXISTS GENRE;
DROP TABLE IF EXISTS SROLE;
DROP TABLE IF EXISTS SUSER;

CREATE TABLE SUSER (ID BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                        LOGIN VARCHAR(255) NOT NULL,
                        PASSWRD VARCHAR(255) NOT NULL,
                        UNIQUE KEY UQ_LOGIN (LOGIN)
);

CREATE TABLE SROLE (ID BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                    USER_ID BIGINT NOT NULL,
                    NAME VARCHAR(255) NOT NULL,
                    FOREIGN KEY (USER_ID) REFERENCES SUSER(ID) ON DELETE CASCADE
);

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

--------------------------- ACL --------------------------------------------------------
CREATE TABLE IF NOT EXISTS acl_sid (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    principal tinyint(1) NOT NULL,
    sid varchar(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_1 (sid,principal)
    );

CREATE TABLE IF NOT EXISTS acl_class (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    class varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_2 (class)
    );

CREATE TABLE IF NOT EXISTS acl_entry (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    acl_object_identity bigint(20) NOT NULL,
    ace_order int(11) NOT NULL,
    sid bigint(20) NOT NULL,
    mask int(11) NOT NULL,
    granting tinyint(1) NOT NULL,
    audit_success tinyint(1) NOT NULL,
    audit_failure tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order)
    );

CREATE TABLE IF NOT EXISTS acl_object_identity (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    object_id_class bigint(20) NOT NULL,
    object_id_identity bigint(20) NOT NULL,
    parent_object bigint(20) DEFAULT NULL,
    owner_sid bigint(20) DEFAULT NULL,
    entries_inheriting tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
    );

ALTER TABLE acl_entry
    ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);
