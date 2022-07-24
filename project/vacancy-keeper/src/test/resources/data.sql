insert into cl_user(id, login, passwrd, first_name, sur_name, path_name, email, telephone) values (1, 'sokolov', 'sokolov', 'Георгий', 'Соколов', 'Вячеславович', 'sokolov.mail.ru', '+7 915 1234');
insert into cl_user(id, login, passwrd, first_name, sur_name, path_name, email, telephone) values (2, 'ivanov', 'ivanov', 'Иван', 'Иванов', 'Иванович', 'ivanov.mail.ru', '+7 915 2345');
insert into cl_user(id, login, passwrd, first_name, sur_name, path_name, email, telephone) values (3, 'petrov', 'petrov', 'Петр', 'Петров', 'Петрович', 'petrov.mail.ru', '+7 915 4567');

insert into cl_role(id, name) values (1, 'ADMIN');
insert into cl_role(id, name) values (2, 'USER');

insert into cl_user_role(user_id, role_id) values (1, 1);
insert into cl_user_role(user_id, role_id) values (1, 2);
insert into cl_user_role(user_id, role_id) values (2, 2);
insert into cl_user_role(user_id, role_id) values (3, 2);

insert into cl_prof(id, code, name) values (1, '1', 'Автомойщик');
insert into cl_prof(id, code, name) values (2, '2', 'Автослесарь, автомеханик');
insert into cl_prof(id, code, name) values (3, '3', 'Мастер-приемщик');
insert into cl_prof(id, code, name) values (4, '4', 'Менеджер по продажам, менеджер по работе с клиентами');
insert into cl_prof(id, code, name) values (5, '5', 'Аналитик');
insert into cl_prof(id, code, name) values (6, '6', 'Арт-директор, креативный директор');
insert into cl_prof(id, code, name) values (7, '7', 'Гейм-дизайнер');
insert into cl_prof(id, code, name) values (8, '8', 'Дизайнер, художник');
insert into cl_prof(id, code, name) values (9, '9', 'Директор по информационным технологиям (CIO)');
insert into cl_prof(id, code, name) values (10, '10', 'Менеджер продукта');
insert into cl_prof(id, code, name) values (11, '11', 'Программист, разработчик');
insert into cl_prof(id, code, name) values (12, '12', 'Руководитель группы разработки');
insert into cl_prof(id, code, name) values (13, '13', 'Руководитель проектов');
insert into cl_prof(id, code, name) values (14, '14', 'Сетевой инженер');
insert into cl_prof(id, code, name) values (15, '15', 'Системный администратор');
insert into cl_prof(id, code, name) values (16, '16', 'Системный инженер');
insert into cl_prof(id, code, name) values (17, '17', 'Специалист по информационной безопасности');
insert into cl_prof(id, code, name) values (18, '18', 'Специалист технической поддержки');
insert into cl_prof(id, code, name) values (19, '19', 'Тестировщик');
insert into cl_prof(id, code, name) values (20, '20', 'Технический директор (CTO)');
insert into cl_prof(id, code, name) values (21, '21', 'Технический писатель');
insert into cl_prof(id, code, name) values (22, '22', 'Генеральный директор, исполнительный директор (CEO)');
insert into cl_prof(id, code, name) values (23, '23', 'Директор по информационным технологиям (CIO)');
insert into cl_prof(id, code, name) values (24, '24', 'Директор по маркетингу и PR (CMO)');
insert into cl_prof(id, code, name) values (25, '25', 'Директор по персоналу (HRD)');
insert into cl_prof(id, code, name) values (26, '26', 'Коммерческий директор (CCO)');
insert into cl_prof(id, code, name) values (27, '27', 'Начальник производства');
insert into cl_prof(id, code, name) values (28, '28', 'Операционный директор (COO)');
insert into cl_prof(id, code, name) values (29, '29', 'Технический директор (CTO)');
insert into cl_prof(id, code, name) values (30, '30', 'Финансовый директор (CFO)');
insert into cl_prof(id, code, name) values (31, '31', 'Ассистент врача');
insert into cl_prof(id, code, name) values (32, '32', 'Ветеринарный врач');
insert into cl_prof(id, code, name) values (33, '33', 'Врач');
insert into cl_prof(id, code, name) values (34, '34', 'Главный врач, заведующий отделением');
insert into cl_prof(id, code, name) values (35, '35', 'Заведующий аптекой');
insert into cl_prof(id, code, name) values (36, '36', 'Медицинская сестра');
insert into cl_prof(id, code, name) values (37, '37', 'Медицинский представитель');
insert into cl_prof(id, code, name) values (38, '38', 'Фармацевт-провизор');
insert into cl_prof(id, code, name) values (39, '39', 'Воспитатель, няня');
insert into cl_prof(id, code, name) values (40, '40', 'Научный специалист, исследователь, лаборант');
insert into cl_prof(id, code, name) values (41, '41', 'Психолог');
insert into cl_prof(id, code, name) values (42, '42', 'Учитель, преподаватель, педагог');
insert into cl_prof(id, code, name) values (43, '43', 'Водитель');
insert into cl_prof(id, code, name) values (44, '44', 'Дворник');
insert into cl_prof(id, code, name) values (45, '45', 'Курьер');
insert into cl_prof(id, code, name) values (46, '46', 'Официант, бармен, бариста');
insert into cl_prof(id, code, name) values (47, '47', 'Охранник');
insert into cl_prof(id, code, name) values (48, '48', 'Уборщица, уборщик');
insert into cl_prof(id, code, name) values (49, '49', 'Менеджер по закупкам');
insert into cl_prof(id, code, name) values (50, '50', 'Специалист по тендерам');

insert into cl_area(id, code, name) values (1, '1', 'Москва');
insert into cl_area(id, code, name) values (2, '2', 'Санкт-Петербург');
insert into cl_area(id, code, name) values (3, '3', 'Уфа');
insert into cl_area(id, code, name) values (4, '4', 'Екатеринбург');
insert into cl_area(id, code, name) values (5, '5', 'Новосибирск');
insert into cl_area(id, code, name) values (6, '6', 'Горно-Алтайск');
insert into cl_area(id, code, name) values (7, '7', 'Барнаул');
insert into cl_area(id, code, name) values (8, '8', 'Благовещенск');
insert into cl_area(id, code, name) values (9, '9', 'Архангельск');
insert into cl_area(id, code, name) values (10, '10', 'Астрахань');
insert into cl_area(id, code, name) values (11, '11', 'Белгород');
insert into cl_area(id, code, name) values (12, '12', 'Брянск');
insert into cl_area(id, code, name) values (13, '13', 'Улан-Удэ');
insert into cl_area(id, code, name) values (14, '14', 'Владивосток');
insert into cl_area(id, code, name) values (15, '15', 'Владимир');
insert into cl_area(id, code, name) values (16, '16', 'Волгоград');
insert into cl_area(id, code, name) values (17, '17', 'Вологда');
insert into cl_area(id, code, name) values (18, '18', 'Воронеж');
insert into cl_area(id, code, name) values (19, '19', 'Махачкала');
insert into cl_area(id, code, name) values (20, '20', 'Биробиджан');
insert into cl_area(id, code, name) values (21, '21', 'Иваново');
insert into cl_area(id, code, name) values (22, '22', 'Иркутск');
insert into cl_area(id, code, name) values (23, '23', 'Нальчик');
insert into cl_area(id, code, name) values (24, '24', 'Калининград');
insert into cl_area(id, code, name) values (25, '25', 'Элиста');
insert into cl_area(id, code, name) values (26, '26', 'Калуга');
insert into cl_area(id, code, name) values (27, '27', 'Петропавловск-Камчатский');
insert into cl_area(id, code, name) values (28, '28', 'Черкесск');
insert into cl_area(id, code, name) values (29, '29', 'Кемерово');
insert into cl_area(id, code, name) values (30, '30', 'Киров');
insert into cl_area(id, code, name) values (31, '31', 'Сыктывкар');
insert into cl_area(id, code, name) values (32, '32', 'Кострома');
insert into cl_area(id, code, name) values (33, '33', 'Краснодар');
insert into cl_area(id, code, name) values (34, '34', 'Красноярск');
insert into cl_area(id, code, name) values (35, '35', 'Курган');
insert into cl_area(id, code, name) values (36, '36', 'Курск');
insert into cl_area(id, code, name) values (37, '37', 'Липецк');
insert into cl_area(id, code, name) values (38, '38', 'Магадан');
insert into cl_area(id, code, name) values (39, '39', 'Йошкар-Ола');
insert into cl_area(id, code, name) values (40, '40', 'Саранск');
insert into cl_area(id, code, name) values (41, '41', 'Мурманск');
insert into cl_area(id, code, name) values (42, '42', 'Нижний Новгород');
insert into cl_area(id, code, name) values (43, '43', 'Великий Новгород');
insert into cl_area(id, code, name) values (44, '44', 'Омск');
insert into cl_area(id, code, name) values (45, '45', 'Орел');
insert into cl_area(id, code, name) values (46, '46', 'Оренбург');
insert into cl_area(id, code, name) values (47, '47', 'Пенза');
insert into cl_area(id, code, name) values (48, '48', 'Пермь');
insert into cl_area(id, code, name) values (49, '49', 'Петрозаводск');
insert into cl_area(id, code, name) values (50, '50', 'Псков');

insert into task(id, user_id, prof_id, area_id, title, keywords) values (1, 1, 11, 1, 'Разработчик Spring', 'spring');
insert into task(id, user_id, prof_id, area_id, title, keywords) values (2, 1, 11, 2, 'Разработчик Spring', 'spring');

insert into vacancy(id, scode, sid, name) values (1, 'HH', '1', 'Вакансия 1');
insert into vacancy(id, scode, sid, name) values (2, 'HH', '2', 'Вакансия 2');
insert into vacancy(id, scode, sid, name) values (3, 'HH', '3', 'Вакансия 3');
insert into vacancy(id, scode, sid, name) values (4, 'HH', '4', 'Вакансия 4');

insert into vacancy_link(id, task_id, vacancy_id) values (1, 1, 1);
insert into vacancy_link(id, task_id, vacancy_id) values (2, 1, 2);
insert into vacancy_link(id, task_id, vacancy_id) values (3, 1, 3);
insert into vacancy_link(id, task_id, vacancy_id) values (4, 1, 4);



