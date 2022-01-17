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





