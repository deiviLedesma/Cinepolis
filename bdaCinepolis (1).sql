create DataBase bdacinepolis;

use bdacinepolis;

CREATE TABLE IF NOT EXISTS Paises (
    idPais INT AUTO_INCREMENT PRIMARY KEY,
    NombrePais VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS Ciudades (
    idCiudad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    cantidadHabitantes INT NOT NULL,
    idPaís INT NOT NULL,
    FOREIGN KEY (idPaís) REFERENCES Paises(idPais)
);

CREATE TABLE IF NOT EXISTS Generos (
    idGenero INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS Clasificaciones (
    idClasificacion INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS Peliculas (
    idPelicula INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(50) NOT NULL,
    duraciónEnMinutos int NOT NULL,
    sinopsis VARCHAR(150) NOT NULL,
    trailer VARCHAR(250),
    imagen VARCHAR(250),
    idPais INT NOT NULL,
    idGenero INT NOT NULL,
    idClasificacion INT NOT NULL,
    FOREIGN KEY (idPais) REFERENCES Paises(idPais),
    FOREIGN KEY (idGenero) REFERENCES Generos(idGenero),
    FOREIGN KEY (idClasificacion) REFERENCES Clasificaciones(idClasificacion)
);

CREATE TABLE IF NOT EXISTS Sucursales (
    idSucursal INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    ubicacion POINT,
    idCiudad INT NOT NULL,
    FOREIGN KEY (idCiudad) REFERENCES Ciudades(idCiudad)
);


CREATE TABLE IF NOT EXISTS Salas (
    idSala INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    capacidadAsientos INT NOT NULL,
    tiempoDeLimpiezaMinutos INT NOT NULL,
    precioActual DECIMAL,
    idSucursal INT NOT NULL,
    FOREIGN KEY (idSucursal) REFERENCES Sucursales(idSucursal)
);

CREATE TABLE IF NOT EXISTS Clientes (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(40) NOT NULL,
    apellidoPaterno VARCHAR(30) NOT NULL,
    apellidoMaterno VARCHAR(30),
    correoElectrónico VARCHAR(40) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    ubicación POINT NOT NULL,
    idCiudad INT NOT NULL,
    contraseña varchar(100),
    FOREIGN KEY (idCiudad) REFERENCES Ciudades(idCiudad)
);




CREATE TABLE IF NOT EXISTS Funciones (
    idFuncion INT AUTO_INCREMENT PRIMARY KEY,
    horaIniciaFuncion TIME NOT NULL,
    horaAcabaFuncion TIME NOT NULL,
    horaAcabaPelicula TIME NOT NULL,
    dia VARCHAR(50) NOT NULL,
    precio DECIMAL,
    idSala INT NOT NULL,
    idPelicula INT NOT NULL,
    FOREIGN KEY (idSala) REFERENCES Salas(idSala),
    FOREIGN KEY (idPelicula) REFERENCES Peliculas(idPelicula)
);

CREATE TABLE IF NOT EXISTS Compras (
    idCompra INT AUTO_INCREMENT PRIMARY KEY,
    codigoCompra VARCHAR(30),
    fechaHoraCompra DATETIME NOT NULL,
    nombreCliente VARCHAR(50) NOT NULL,
    correoCliente VARCHAR(50) NOT NULL,
    cantidadAsientos INT NOT NULL,
    metodoDePago VARCHAR(20),
    costoTotal DECIMAL,
    idCliente INT NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES Clientes(idCliente)
);

CREATE table IF NOT EXISTS compras_Funciones(
idCompraFunciones INT AUTO_INCREMENT PRIMARY KEY,
precio DECIMAL NOT NULL,
horaFuncion TIME,
idCompra INT NOT NULL,
idFuncion INT NOT NULL,
FOREIGN KEY (idCompra) REFERENCES Compras(idCompra),
FOREIGN KEY (idFuncion) REFERENCES Funciones(idFuncion)
);

CREATE TABLE IF NOT EXISTS Notificaciones (
    idNotificacion INT AUTO_INCREMENT PRIMARY KEY,
    idCliente INT NOT NULL,
    mensaje VARCHAR(255) NOT NULL,
    fecha DATE NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES Clientes(idCliente)
);




