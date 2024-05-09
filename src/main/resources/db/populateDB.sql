DELETE FROM user_role;
DELETE FROM vote;
DELETE FROM users;
DELETE FROM meal;
DELETE FROM restaurant;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User1', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User2', 'user2@gmail.com', '{noop}user2');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('ADMIN', 100001),
       ('USER', 100002);

INSERT INTO restaurant (name)
VALUES ('Restaurant Schengen'),
       ('Brisket Eat & Fun'),
       ('Smoke BBQ');

INSERT INTO meal (name, price, restaurant_id)
VALUES ('Средиземноморский суп с морепродуктами', 670, 100003),
       ('Палтус с цветной капустой и миндальным соусом', 780, 100003),
       ('Бриошь с камамбером', 350, 100003),
       ('Томатный суп с беконом', 490, 100004),
       ('Копченый колбаски', 750, 100004),
       ('Овощи гриль', 340, 100004),
       ('Пирог Флорида', 440, 100004),
       ('Традиционный борщ с копченым ребром поросенка', 590, 100005),
       ('Ребро поросенка BBQ', 1300, 100005),
       ('Большой зеленый салат', 790, 100005),
       ('Брауни с карамелью и арахисом', 500, 100005);

INSERT INTO vote (USER_ID, RESTAURANT_ID)
VALUES (100000, 100003),
       (100001, 100004);