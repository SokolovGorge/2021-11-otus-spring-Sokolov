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



