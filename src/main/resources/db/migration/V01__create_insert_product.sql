CREATE TABLE product (
  id bigint NOT NULL AUTO_INCREMENT,
  code varchar(8) NOT NULL,
  model varchar(24) NOT NULL,
  name varchar(32) NOT NULL,
  price float DEFAULT 0,
  color varchar(12) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UKh3w5r1mx6d0e5c6um32dgyjej (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO product (code, model, name, price, color) values ('COD1', 'Model 1', 'Produto 1', 11.5, 'black');
INSERT INTO product (code, model, name, price, color) values ('COD2', 'Model 2', 'Produto 2', 12.6, 'red');
INSERT INTO product (code, model, name, price, color) values ('COD3', 'Model 3', 'Produto 3', 13.4, 'yellow');
INSERT INTO product (code, model, name, price, color) values ('COD4', 'Model 4', 'Produto 4', 14.7, 'blue');
INSERT INTO product (code, model, name, price, color) values ('COD5', 'Model 5', 'Produto 5', 15, 'pink');
INSERT INTO product (code, model, name) values ('COD6', 'Model 6', 'Produto 6');