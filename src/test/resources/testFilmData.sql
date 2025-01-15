INSERT INTO MPA (NAME)
VALUES('G'), ('PG'), ('PG-13'), ('R'), ('NC-17');

INSERT INTO GENRES (NAME)
VALUES('Комедия'), ('Драма'), ('Мультфильм'), ('Триллер'), ('Документальный'), ('Боевик');

INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)
VALUES
('Prestige', 'Фильм Нолана с двумя Бэйлами и кучей Хью Джекманов', '2006-10-20', 125, 1),
('Terminator: Dark Fate', 'Очередной дурацкий терминатор', '2019-10-23', 128, 1);

INSERT INTO USERS (EMAIL, LOGIN, NAME, BIRTHDAY)
VALUES
    ('KevinNash@wolfpack.com', 'KevinNash', 'Kevin', '1959-07-09'),
    ('ScottyHall@Iam.Groot', 'ScottyHall', 'Scotty', '1958-10-20');