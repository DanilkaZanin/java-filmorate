# Схема бд
![картинка](https://github.com/DanilkaZanin/java-filmorate/blob/add-database/src/main/resources/scheme.png)

## Таблицы и их поля:

### users

- id: Уникальный идентификатор пользователя (первичный ключ).
- email: Адрес электронной почты пользователя.
- name: Имя пользователя.
- birthday: Дата рождения пользователя.

### friends

- id: Уникальный идентификатор записи о дружбе (первичный ключ).
- user_id: Идентификатор пользователя, который имеет друга (внешний ключ, ссылается на users.id).
- friend_id: Идентификатор друга (внешний ключ, также ссылается на users.id).
- status: Статус дружбы (например, "CONFIRMED" для подтвержденной дружбы).

### films

- id: Уникальный идентификатор фильма (первичный ключ).
- film_name: Название фильма.
- description: Описание фильма.
- release_date: Дата выпуска фильма.
- duration: Продолжительность фильма в минутах.
- rating: Рейтинг фильма.

### likes

- id: Уникальный идентификатор записи о лайке (первичный ключ).
- film_id: Идентификатор фильма, который был лайкнут (внешний ключ, ссылается на films.id).
- user_id: Идентификатор пользователя, который поставил лайк (внешний ключ, ссылается на users.id).

### genres

- id: Уникальный идентификатор записи о жанре (первичный ключ).
- film_id: Идентификатор фильма, которому принадлежит жанр (внешний ключ, ссылается на films.id).
- name: Название жанра.

# Запросы к бд


#### Чтобы получить фильмы по популярности


    SELECT f.film_name AS name,
        f.description,
        f.release_date AS date,
        f.duration,
        f.rating
        g.name
        COUNT(l.id) AS likes
    FROM films AS f
        LEFT JOIN genres AS g ON f.id = g.film_id
        LEFT JOIN likes AS l ON f.id = l.film_id
    GROUP BY f.id 
        AND g.name
    ORDER BY likes DESC
    LIMIT 10;

Этот запрос вернет топ-10 фильмов с наибольшим количеством лайков, включая их жанры и другую информацию.

#### Чтобы узнать всех друзей(со статусом CONFIRMED)


    SELECT email,
        name,
        birthday
    FROM users
    WHERE id IN ( 
        SELECT friend_id
        FROM friends 
        WHERE status = 'CONFIRMED'
    );

#### Чтобы узнать название всех понравившихся фильмов юзера(с id = 1)
    
    
    SELECT film_name
    FROM films AS f
        INNER JOIN likes AS l ON f.id = l.film_id
    WHERE l.user_id = 1;
    