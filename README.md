# java-filmorate
![Схема базы данных](./thedge19.png)

## Описание БД

---

### USERS
Пользователи<br>
<b>id</b> - ключевое поле, автоинкремент<br>
<b>email, login, name, birthday</b> - данные о пользователе

---

### FRIENDS
Вспомогательная таблица для хранения данных о друзьях пользователей<br>

<b>user_id</b> - кому направлен запрос на дружбу<br>
<b>friend_id</b> - от кого направлен запрос на дружбу<br>
<b>are_friends(default: false) </b> - принят ли запрос на дружбу

---

### FILMS
Фильмы<br>
<b>id</b> - ключевое поле, автоинкремент<br>
<b>name, description, release_date, duration</b> - данные о фильме<br>
<b>rating</b> - ссылка на MPA фильма

---

### films_genres
Вспомогательная таблица для взаимосвязи фильмов с их жанрами<br>
<b>film_id</b> - идентификатор фильма<br>
<b>genre_id</b> - идентификатор жанра

---

### GENRES
Все возможные жанры фильмов<br>
<b>id</b> - ключевое поле, автоинкремент<br>
<b>title</b> - название жанра

---

### LIKES
Вспомогательная таблица лайков фильмам от пользователей<br>
<b>id</b> - ключевое поле, автоинкремент<br>
<b>user_id</b> - идентификатор пользователя, лайкнувшего фильм<br>
<b>film_id</b> - идентификатор лайкнутого фильма

---