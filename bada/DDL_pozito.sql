-- -----------------------------------------------------
-- Artifact:    DDL_el_pozito_v1.sql
-- Version:     1.0
-- Date:        2024-03-12 06:47:00
-- Author:      Codeagle
-- Comments:    Realización inicial de la base de datos
-- -----------------------------------------------------
DROP DATABASE IF EXISTS pozito;
CREATE DATABASE pozito;
USE pozito;

-- -----------------------------------------------------
-- Table persona
-- -----------------------------------------------------
CREATE TABLE persona 
(
  idPersona         INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  nombre            VARCHAR(45) NOT NULL,
  apellidoPaterno   VARCHAR(45) NOT NULL,
  apellidoMaterno   VARCHAR(45) NOT NULL DEFAULT '',
  genero            VARCHAR(20)  NOT NULL DEFAULT 'O', -- H: Hombre; M: Mujer; O: Otro
  fechaNacimiento   DATE NOT NULL,
  rfc               VARCHAR(15) NULL DEFAULT '',
  curp              VARCHAR(19)  NULL DEFAULT '',
  domicilio         VARCHAR(129)  NULL DEFAULT '',
  codigoPostal      VARCHAR(11)  NULL DEFAULT '',
  ciudad            VARCHAR(46)  NULL DEFAULT '',
  estado            VARCHAR(40)  NULL DEFAULT '',
  telefono          VARCHAR(20) NOT NULL DEFAULT '',
  foto              LONGTEXT  NULL
);

-- -----------------------------------------------------
-- Table usuario
-- -----------------------------------------------------
CREATE TABLE usuario 
(
  idUsuario         INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  nombreUsuario     VARCHAR(100) UNIQUE NOT NULL,
  contrasenia       VARCHAR(100) NOT NULL,
  rol               VARCHAR(100) NOT NULL,    -- ADMC: Administrador del Sistema Central
  token              VARCHAR(100)            -- ADMS: Administrador de Sucursal
                                            -- EMPS: Empleado de Sucursal
);

-- -----------------------------------------------------
-- Table sucursal
-- -----------------------------------------------------


-- -----------------------------------------------------
-- Table empleado
-- -----------------------------------------------------
CREATE TABLE empleado 
(
  idEmpleado    INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  email         VARCHAR(40) NOT NULL,
  codigo        VARCHAR(10) UNIQUE NOT NULL,
  fechaIngreso  DATE NOT NULL,
  puesto        VARCHAR(45) NOT NULL,
  salarioBruto  FLOAT NOT NULL,
  activo        INT NOT NULL DEFAULT 1,
  idPersona     INT NOT NULL,
  idUsuario     INT NOT NULL,
  CONSTRAINT fk_idPersona_empleado
    FOREIGN KEY (idPersona)
    REFERENCES persona (idPersona)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_idUsuario_empleado
    FOREIGN KEY (idUsuario)
    REFERENCES usuario (idUsuario)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table cliente
-- -----------------------------------------------------
CREATE TABLE cliente 
(
  idCliente     INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  email         VARCHAR(45) NOT NULL DEFAULT '',
  fechaRegistro DATE NOT NULL,
  estatus       INT NOT NULL DEFAULT 1, -- 0: Inactivo; 1: Activo
  idPersona     INT NOT NULL,
  CONSTRAINT fk_idpersona_cliente
    FOREIGN KEY (idPersona)
    REFERENCES persona (idPersona)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table producto
-- -----------------------------------------------------
CREATE TABLE producto 
(
  idProducto        INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  nombre            VARCHAR(180) NOT NULL DEFAULT '',
  unidadMedida      VARCHAR(25) NOT NULL DEFAULT '',
  presentacion      VARCHAR(200) NOT NULL,
  categoria         VARCHAR(255) NOT NULL DEFAULT '',
  precioCompra      FLOAT NOT NULL DEFAULT 0.0,
  precioVenta       FLOAT NOT NULL DEFAULT 0.0,
  codigoProducto    VARCHAR(255) NOT NULL DEFAULT '',
  codigoBarras      VARCHAR(65)  NOT NULL DEFAULT '',
  estatus           INT NOT NULL DEFAULT 1 -- 0: Inactivo; 1: Activo;
);

-- -----------------------------------------------------
-- Las Existencias de los Productos estan en funcion
-- de cada sucursal. Por eso esta tabla es necesaria.
-- -----------------------------------------------------
CREATE TABLE inventario 
(
  idInventario  INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  idProducto    INT NOT NULL,
  existencias   INT NOT NULL DEFAULT 0,
  CONSTRAINT fk_idProducto_inventario
    FOREIGN KEY (idProducto)
    REFERENCES producto (idProducto)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table venta
-- -----------------------------------------------------
CREATE TABLE venta 
(
  idVenta       INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  fechaHora     DATETIME NOT NULL,
  estatus       INT NOT NULL DEFAULT 1, -- 0: Cancelada o Eliminada;    -- 1: Realizada;      -- 2: Pendiente;
  idCliente     INT NOT NULL,
  idEmpleado    INT NOT NULL,
  CONSTRAINT fk_idEmpleado_venta
    FOREIGN KEY (idEmpleado)
    REFERENCES empleado (idEmpleado)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_idCliente_venta
    FOREIGN KEY (idCliente)
    REFERENCES cliente (idCliente)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table detalleVenta
-- -----------------------------------------------------
CREATE TABLE detalleVenta 
(
  idProducto    INT NOT NULL,
  idVenta       INT NOT NULL,
  cantidad      INT NOT NULL,
  precioVenta   FLOAT NOT NULL,
  CONSTRAINT fk_idProducto_dv
    FOREIGN KEY (idProducto)
    REFERENCES producto (idProducto)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_idVenta_dv
    FOREIGN KEY (idVenta)
    REFERENCES venta (idVenta)
    ON DELETE CASCADE
    ON UPDATE RESTRICT
);

