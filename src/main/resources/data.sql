SET REFERENTIAL_INTEGRITY FALSE ;

TRUNCATE TABLE user_role RESTART IDENTITY;
TRUNCATE TABLE vote RESTART IDENTITY;
TRUNCATE TABLE  users RESTART IDENTITY;
TRUNCATE TABLE meal RESTART IDENTITY;
TRUNCATE TABLE restaurant RESTART IDENTITY;

SET REFERENTIAL_INTEGRITY TRUE;

INSERT INTO users (name, email, password)
VALUES ('User1', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User2', 'user2@gmail.com', '{noop}user2');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('USER', 2),
       ('ADMIN', 2),
       ('USER', 3);

INSERT INTO restaurant (name)
VALUES ('Restaurant Schengen'),
       ('Brisket Eat & Fun'),
       ('Smoke BBQ');

INSERT INTO meal (name, price, restaurant_id)
VALUES ('Средиземноморский суп с морепродуктами', 670, 1),
       ('Палтус с цветной капустой и миндальным соусом', 780, 1),
       ('Бриошь с камамбером', 350, 1),
       ('Томатный суп с беконом', 490, 2),
       ('Копченый колбаски', 750, 2),
       ('Овощи гриль', 340, 2),
       ('Пирог Флорида', 440, 2),
       ('Традиционный борщ с копченым ребром поросенка', 590, 3),
       ('Ребро поросенка BBQ', 1300, 3),
       ('Большой зеленый салат', 790, 3),
       ('Брауни с карамелью и арахисом', 500, 3);

INSERT INTO vote (USER_ID, RESTAURANT_ID)
VALUES (1, 1),
       (2, 2);