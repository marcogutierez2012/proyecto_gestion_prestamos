DROP DATABASE IF EXISTS SISTEMA_PRESTAMOS;

CREATE DATABASE IF NOT EXISTS SISTEMA_PRESTAMOS;

USE SISTEMA_PRESTAMOS;

create table rol(
	id_rol int primary key auto_increment,
	descripcion varchar(255) not null,
    estado smallint not null	
);

insert into rol (descripcion, estado) values
("Ninguno", 0),
("Administrador", 0),
("Inversionista", 0),
("Jefe de Prestamista", 0),
("Prestamista", 0),
("Prestatario", 0);


create table opcion(
	id_opcion int primary key auto_increment,
    descripcion varchar(255) not null,
	estado smallint not null,
    ruta text not null,
    tipo smallint not null
);

create table rol_has_opcion(
    id_rol int not null,
	id_opcion int not null,
    primary key(id_rol,id_opcion),
    foreign key(id_rol) references rol(id_rol),
    foreign key(id_opcion) references opcion(id_opcion)
);

create table zona(
	id_zona int primary key auto_increment,
    descripcion varchar(255) not null,
	estado smallint not null
);

INSERT INTO zona (descripcion, estado) VALUES
('Ninguno', 0),
('Ancón', 0),
('Ate', 0),
('Barranco', 0),
('Breña', 0),
('Carabayllo', 0),
('Cercado de Lima', 0),
('Chaclacayo', 0),
('Chorrillos', 0),
('Cieneguilla', 0),
('Comas', 0),
('El Agustino', 0),
('Independencia', 0),
('Jesús María', 0),
('La Molina', 0),
('La Victoria', 0),
('Lince', 0),
('Los Olivos', 0),
('Lurigancho', 0),
('Lurín', 0),
('Magdalena del Mar', 0),
('Miraflores', 0),
('Pachacámac', 0),
('Pucusana', 0),
('Pueblo Libre', 0),
('Puente Piedra', 0),
('Punta Hermosa', 0),
('Punta Negra', 0),
('Rímac', 0),
('San Bartolo', 0),
('San Borja', 0),
('San Isidro', 0),
('San Juan de Lurigancho', 0),
('San Juan de Miraflores', 0),
('San Luis', 0),
('San Martín de Porres', 0),
('San Miguel', 0),
('Santa Anita', 0),
('Santa María del Mar', 0),
('Santa Rosa', 0),
('Santiago de Surco', 0),
('Surquillo', 0),
('Villa El Salvador', 0),
('Villa María del Triunfo', 0);


create table usuario(
	id_usuario int primary key auto_increment,
    username varchar(255) not null unique,
    nombres varchar(255) not null,
	ape_paterno varchar(255) not null,
    ape_materno varchar(255) not null,
	password varchar(255) not null,
    email varchar(255) not null,
    telefono varchar(255) not null,
    dni varchar(255) not null,
    estado smallint default 0 not null,
    id_rol int not null,
    id_zona int default 1 not null,
    id_usuario_lider int default 1 not null,
    foreign key(id_rol) references rol(id_rol),
    foreign key(id_zona) references zona(id_zona),
    foreign key(id_usuario_lider) references usuario(id_usuario) ON DELETE CASCADE
);

/* usuario Administrador */
/* LA CONTRASEÑA ES 123456 PERO EN LA BASE DE DATOS ESTA ENCRIPTADA*/
insert into usuario(username, nombres, ape_paterno, ape_materno, password, email, telefono, dni, id_rol) values 
("admin", "Marco Antonio", "Gutierrez", "Ferro", "$2a$12$PhUX50tBAer.dM5TgxbGl.MDcE0bX5Zh6lpADV0jH0q0RqMa6uyNq", "marcogutierrez@gmail.com", "992478429", "74878420", 2);

/* usuario Inversionista */
/* LA CONTRASEÑA ES 123456 PERO EN LA BASE DE DATOS ESTA ENCRIPTADA*/
insert into usuario(username, nombres, ape_paterno, ape_materno, password, email, telefono, dni,estado,id_rol, id_zona, id_usuario_lider) values 
("inver", "Jerson", "Laureano", "Poma", "$2a$12$U44DHuPlLAfDlhzL9KFxGOaf/u6Vc9OF1VJQG4P.NnhVeLyC2OASC", "jersonlaureano@gmail.com", "987654321", "7774444",0,3,25,1);

create table solicitud(
	id_solicitud int primary key auto_increment,
    monto double not null,
    interes double not null,
    fecha_registro datetime not null,
    fecha_inicio_prestamo datetime not null,
    fecha_fin_prestamo datetime not null,
    dias smallint not null,
    diaslaborales smallint not null,
    pagodiario double not null,
    estado varchar(20) not null,
    id_prestatario int not null,
    id_prestamista int not null,
    foreign key (id_prestatario) references usuario(id_usuario) ON DELETE CASCADE,
    foreign key (id_prestamista) references usuario(id_usuario)ON DELETE CASCADE
);

create table cuota(
	id_cuota int primary key not null auto_increment,
    numero_cuota smallint not null,
    monto_cuota double not null,
    fecha_pago datetime not null,
    fecha_vencimiento datetime not null,
    estado varchar(20) not null,
	id_solicitud int not null,
    foreign key(id_solicitud) references solicitud(id_solicitud)
);

CREATE TABLE monto (
    id_monto int PRIMARY KEY AUTO_INCREMENT,
    monto1 DECIMAL(12,2),
    monto2 DECIMAL(12,2),
    monto3 DECIMAL(12,2),
    monto4 DECIMAL(12,2),
    monto5 DECIMAL(12,2)
);

CREATE TABLE prestamos (
    id_prestamos INT PRIMARY KEY AUTO_INCREMENT,
    id_monto INT,
    duracion_dias INT,
    interes INT,
	CONSTRAINT fk_prestamos_monto2 FOREIGN KEY (id_monto) REFERENCES monto(id_monto)
);

-- Insertar un registro en la tabla monto
INSERT INTO monto (monto1, monto2, monto3, monto4, monto5) VALUES (150.00, 200.00, 250.00, 300.00, 350.00);

-- Insertar tres registros en la tabla prestamos, asociados al mismo id_monto
INSERT INTO prestamos (id_monto, duracion_dias, interes) VALUES (1, 15, 10);
INSERT INTO prestamos (id_monto, duracion_dias, interes) VALUES (1, 20, 12);
INSERT INTO prestamos (id_monto, duracion_dias, interes) VALUES (1, 25, 15);
INSERT INTO prestamos (id_monto, duracion_dias, interes) VALUES (1, 30, 17);
INSERT INTO prestamos (id_monto, duracion_dias, interes) VALUES (1, 35, 19);

SELECT
    p.duracion_dias,
    ROUND(m.monto1 + (m.monto1 * (p.interes / 100)), 2) AS monto1_calculado,
    ROUND(m.monto2 + (m.monto2 * (p.interes / 100)), 2) AS monto2_calculado,
    ROUND(m.monto3 + (m.monto3 * (p.interes / 100)), 2) AS monto3_calculado,
    ROUND(m.monto4 + (m.monto4 * (p.interes / 100)), 2) AS monto4_calculado,
    ROUND(m.monto5 + (m.monto5 * (p.interes / 100)), 2) AS monto5_calculado,
    p.interes
FROM
    prestamos p
INNER JOIN
    monto m ON p.id_monto = m.id_monto;
