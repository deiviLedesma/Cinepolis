-- validaciones
ALTER TABLE Clientes
ADD CONSTRAINT unique_email UNIQUE (correoElectrónico);

ALTER TABLE Peliculas
MODIFY trailer VARCHAR(250) DEFAULT NULL;

ALTER TABLE Peliculas
MODIFY duraciónEnMinutos INT CHECK (duraciónEnMinutos > 0);

-- triggers y stored procedures
DELIMITER //

CREATE TRIGGER Cumpleaños
AFTER INSERT ON Clientes
FOR EACH ROW
BEGIN
    IF DAY(NEW.fechaNacimiento) = DAY(CURDATE()) AND MONTH(NEW.fechaNacimiento) = MONTH(CURDATE()) THEN
        INSERT INTO Notificaciones (idCliente, mensaje, fecha) 
        VALUES (NEW.idCliente, 'Feliz cumpleaños', CURDATE());
    END IF;
END //

DELIMITER ;


DELIMITER //

CREATE PROCEDURE ComprarBoletos (
    IN p_idFuncion INT,
    IN p_idCliente INT,
    IN p_cantidadAsientos INT
)
BEGIN
    DECLARE disponible INT;

    -- Verificar disponibilidad de asientos
    SELECT (capacidadAsientos - (SELECT IFNULL(SUM(cantidadAsientos), 0) FROM Clientes_Compra_Funciones WHERE idFuncion = p_idFuncion)) INTO disponible
    FROM Salas JOIN Funciones ON Salas.idSala = Funciones.idSala
    WHERE Funciones.idFuncion = p_idFuncion;

    IF disponible >= p_cantidadAsientos THEN
        -- Insertar la compra
        INSERT INTO Clientes_Compra_Funciones (fechaCompra, cantidadAsientos, costo, idFuncion, idCliente)
        VALUES (CURDATE(), p_cantidadAsientos, (SELECT precio FROM Funciones WHERE idFuncion = p_idFuncion) * p_cantidadAsientos, p_idFuncion, p_idCliente);
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No hay suficientes asientos disponibles.';
    END IF;
END //

DELIMITER ;




