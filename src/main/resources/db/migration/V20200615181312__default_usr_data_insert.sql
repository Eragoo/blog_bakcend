insert into role (name)
values ('REGULAR_USER');

insert into usr (login, username, password, role_id)
values ('admin', 'admin', '$2a$10$8pGE/kU67uuWVuHYRcYvW.7cGNERTKAKsK3I0LRZJ8RSqntKf7yFC',
        (select id from role where name = 'def'));