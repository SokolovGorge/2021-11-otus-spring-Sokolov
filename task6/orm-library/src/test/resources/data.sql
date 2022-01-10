insert into genre(`name`) values ('Детектив');
insert into genre(`name`) values ('Фантастика');

--
insert into author(firstname, surname) values ('Агата', 'Кристи');
insert into author(firstname, surname) values ('Сергей', 'Лукьяненко');
--
insert into book(author_id, genre_id, title) values (1, 1, 'Восточный экспресс');
insert into book(author_id, genre_id, title) values (1, 1, 'Императоры иллюзий');
--
insert into remark(book_id, text) values (1, 'Примечание 11');
insert into remark(book_id, text) values (1, 'Примечание 12');
insert into remark(book_id, text) values (1, 'Примечание 13');
insert into remark(book_id, text) values (1, 'Примечание 14');
insert into remark(book_id, text) values (2, 'Примечание 21');
insert into remark(book_id, text) values (2, 'Примечание 22');
insert into remark(book_id, text) values (2, 'Примечание 23');
insert into remark(book_id, text) values (2, 'Примечание 24');



