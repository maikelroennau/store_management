insert into user (username, password, name) VALUES ( 'maikel.ronnau', 'senha', 'Maikel Ronnau');
insert into user (username, password, name) VALUES ( 'ronnau.maikel', 'ahnas', 'Ronnau Maikel');

insert into product (name, description, image_path) VALUES ('Caixa de jornais','Lorem Ipsum e simplesmente uma simulacao de texto da industria tipografica e de impressos, e vem sendo utilizado desde o seculo XVI, quando um impressor desconhecido pegou uma bandeja de tipos e os embaralhou para fazer um livro de modelos de tipos.', 'images/produto_1.png');
insert into product (name, description, image_path) VALUES ('Jornal', 'Lorem Ipsum e simplesmente uma simulacao de texto da industria tipografica e de impressos, e vem sendo utilizado desde o seculo XVI, quando um impressor desconhecido pegou uma bandeja de tipos e os embaralhou para fazer um livro de modelos de tipos.', 'images/produto_2.png');

insert into comment (comment, date_time, liked, product_id, user_id) VALUES ('Muito bom.', '2017-11-15 20:32:17', true, 1, 1);

insert into comment (comment, date_time, liked, product_id, user_id) VALUES ('Sensacional!', '2017-11-15 20:32:17', true, 1, 2);
insert into comment (comment, date_time, liked, product_id, user_id) VALUES ('Baixa qualidade', '2017-11-15 20:32:17', false, 2, 2);