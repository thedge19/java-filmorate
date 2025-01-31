DROP ALL OBJECTS;

CREATE TABLE IF NOT EXISTS mpa
(
    ID   BIGINT            NOT NULL AUTO_INCREMENT,
    NAME CHARACTER VARYING NOT NULL,
    CONSTRAINT MPA_PK PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS films
(
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         varchar NOT NULL,
    description  varchar,
    release_date date,
    duration     integer,
    MPA_ID       integer REFERENCES MPA (ID)
);

CREATE TABLE IF NOT EXISTS users
(
    id       INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email    varchar NOT NULL,
    login    varchar NOT NULL,
    name     varchar,
    birthday date
);

CREATE TABLE IF NOT EXISTS likes
(
    user_id INTEGER REFERENCES users (id),
    film_id INTEGER REFERENCES films (id)
);

CREATE TABLE IF NOT EXISTS friends
(
    user_id     INTEGER REFERENCES USERS (id),
    friend_id   INTEGER REFERENCES USERS (id),
    are_friends boolean
);

CREATE TABLE IF NOT EXISTS genres
(
    id    INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS films_genres
(
    film_id  INTEGER REFERENCES FILMS (id),
    genre_id INTEGER REFERENCES GENRES (id)
);


