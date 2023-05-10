CREATE TABLE CLIENTES
(id int,
nome varchar(50) not null unique,
status int not null default 1,
PRIMARY KEY (ID));

CREATE TABLE PROCESSOS
(id int,
numero_do_processo int not null,
id_cliente int,
status int not null default 1 unique,
PRIMARY KEY (ID),
FOREIGN KEY (id_cliente) REFERENCES Clientes(id));

CREATE TABLE INTIMACOESPUBLICACOES
(id int,
tipo varchar(10) not null,
id_processo int,
data datetime not null,
texto varchar(300),
status int not null default 1,
PRIMARY KEY (ID),
FOREIGN KEY (id_processo) REFERENCES Processos(id));