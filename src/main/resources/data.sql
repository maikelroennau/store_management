insert into role (name) VALUES ( 'ROLE_ADMIN');
insert into role (name) VALUES ( 'ROLE_USER');
--                                                              senha: maikel
insert into user (username, password, name) VALUES ( 'maikel', '$2a$10$no9vZdh80b5uEHtrqTbDi.DQgnhUOo0gLL9WuWp5nprcaYbekbEGa', 'Maikel Ronnau');
insert into user_role(user_id, role_id) VALUES(1, 1);