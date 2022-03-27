insert into suser(login, passwrd) values ('admin', 'pass');
insert into suser(login, passwrd) values ('user', 'password');
--
insert into srole(user_id, `name`) values (1, 'ADMIN');
insert into srole(user_id, `name`) values (2, 'USER');
--
insert into genre(`name`) values ('Детектив');
insert into genre(`name`) values ('Фантастика');
insert into genre(`name`) values ('Приключения');
insert into genre(`name`) values ('Роман');
insert into genre(`name`) values ('Научная книга');
--
insert into author(firstname, surname) values ('Агата', 'Кристи');
insert into author(firstname, surname) values ('Сергей', 'Лукьяненко');
insert into author(firstname, surname) values ('Артур', 'Конан Дойл');
insert into author(firstname, surname) values ('Лев', 'Толстой');
insert into author(firstname, surname) values ('Сергей', 'Капица');
--
insert into book(author_id, genre_id, title) values (1, 1, 'Восточный экспресс');
insert into book(author_id, genre_id, title) values (2, 2, 'Императоры иллюзий');
insert into book(author_id, genre_id, title) values (3, 3, 'Вокруг света за 80 дней');
insert into book(author_id, genre_id, title) values (4, 4, 'Война и мир');
insert into book(author_id, genre_id, title) values (5, 5, 'Парадоксы роста');
--
insert into remark(book_id, text) values (1, 'Примечание 11');
insert into remark(book_id, text) values (1, 'Примечание 12');
insert into remark(book_id, text) values (1, 'Примечание 13');
insert into remark(book_id, text) values (1, 'Примечание 14');
insert into remark(book_id, text) values (2, 'Примечание 21');
insert into remark(book_id, text) values (2, 'Примечание 22');
insert into remark(book_id, text) values (2, 'Примечание 23');
insert into remark(book_id, text) values (2, 'Примечание 24');
insert into remark(book_id, text) values (3, 'Примечание 31');
insert into remark(book_id, text) values (3, 'Примечание 32');
insert into remark(book_id, text) values (3, 'Примечание 33');
insert into remark(book_id, text) values (3, 'Примечание 34');
insert into remark(book_id, text) values (4, 'Примечание 41');
insert into remark(book_id, text) values (4, 'Примечание 42');
insert into remark(book_id, text) values (4, 'Примечание 43');
insert into remark(book_id, text) values (4, 'Примечание 44');

------------------------------ ACL -----------------------------------------------------
INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 0, 'ROLE_ADMIN'),
(2, 0, 'ROLE_USER');

INSERT INTO acl_class (id, class) VALUES
(1, 'ru.otus.weblibrary.domain.Book');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 1, 0),
(2, 1, 2, NULL, 1, 0),
(3, 1, 3, NULL, 1, 0),
(4, 1, 4, NULL, 1, 0),
(5, 1, 5, NULL, 1, 0);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure) VALUES
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 2, 1, 1, 1, 1, 1, 1),
(3, 3, 1, 1, 1, 1, 1, 1),
(4, 4, 1, 1, 1, 1, 1, 1),
(5, 5, 1, 1, 1, 1, 1, 1),
(6, 1, 2, 2, 1, 1, 1, 1),
(7, 2, 2, 2, 1, 1, 1, 1),
(8, 3, 2, 2, 1, 1, 1, 1),
(9, 4, 2, 2, 1, 1, 1, 1);
