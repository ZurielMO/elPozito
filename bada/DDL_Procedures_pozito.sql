USE pozito;
select * from persona;

-- Procedimiento Almacenado para generar el codigo de un nuevo empleado.
DROP PROCEDURE IF EXISTS generarCodigoEmpleado;
DELIMITER $$
CREATE PROCEDURE generarCodigoEmpleado(OUT codigo VARCHAR(8))
	BEGIN
		DECLARE anio INT;
		DECLARE mes VARCHAR(2);
		DECLARE num VARCHAR(4);
		SET anio  = RIGHT(year(now()),2);
		SET mes   = LPAD(RIGHT(month(now()),2), 2, '0');
		SET num   = (SELECT LPAD(MAX(idUsuario) + 1, 4, '0') FROM usuario);
		SET codigo= CONCAT(anio,mes,num);
	END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS modificarEmpleado;
DELIMITER $$

CREATE PROCEDURE modificarEmpleado(
                                          /* Datos Personales */
                                    IN    var_nombre          VARCHAR(64),    --  1
                                    IN    var_apellidoPaterno VARCHAR(64),    --  2
                                    IN    var_apellidoMaterno VARCHAR(64),    --  3
                                    IN  var_genero          VARCHAR(20),     --  4
                                    IN  var_fechaNacimiento VARCHAR(11),    --  5
                                    IN  var_rfc             VARCHAR(14),    --  6
                                    IN  var_curp            VARCHAR(19),    --  7
                                    IN    var_domicilio       VARCHAR(129),   --  8
                                    IN  var_cp              VARCHAR(11),    --  9
                                    IN  var_ciudad          VARCHAR(46),    -- 10
                                    IN  var_estado          VARCHAR(40),    -- 11
                                    IN    var_telefono        VARCHAR(20),    -- 12
                                    IN    var_foto            LONGTEXT,       -- 13                 
                                   
                                  /* Datos del Usuario    */
                                    IN  var_nombreUsuario   VARCHAR(45),    -- 15
                                    IN  var_contrasenia     VARCHAR(45),    -- 16
                                    IN  var_rol             VARCHAR(10),    -- 17
                                  /* Datos del Empleado */
                                    IN  var_email           VARCHAR(65),    -- 18
                                    IN  var_puesto          VARCHAR(25),    -- 19
                                    IN  var_salarioBruto    FLOAT,          -- 20
                                 
                                  /* Parametros de Salida */
                                    IN var_idPersona       INT,            -- 21
                                    IN var_idUsuario       INT,            -- 22
                                    IN var_idEmpleado      INT,            -- 23
                                    IN var_codigoEmpleado  VARCHAR(9)      -- 24
)
BEGIN
    -- Actualizamos los datos de la Persona:
    UPDATE persona
    SET
        nombre = var_nombre,
        apellidoPaterno = var_apellidoPaterno,
        apellidoMaterno = var_apellidoMaterno,
        genero = var_genero,
        fechaNacimiento = STR_TO_DATE(var_fechaNacimiento, '%Y-%m-%d'),
        rfc = var_rfc,
        curp = var_curp,
        domicilio = var_domicilio,
        codigoPostal = var_cp,
        ciudad = var_ciudad,
        estado = var_estado,
        telefono = var_telefono,
        foto = var_foto
    WHERE idPersona = var_idPersona;


    -- Actualizamos los datos del Usuario:
    UPDATE usuario
    SET
        nombreUsuario = var_nombreUsuario,
        contrasenia = var_contrasenia,
        rol = var_rol
    WHERE idUsuario = var_idUsuario;

    -- Actualizamos los datos del Empleado:
    UPDATE empleado
    SET
        email = var_email,
        puesto = var_puesto,
        salarioBruto = var_salarioBruto,
        codigo = var_codigoEmpleado
    WHERE idEmpleado = var_idEmpleado;
END
$$

DELIMITER ;


-- Procedimiento almacenado para insertar un nuevo Empleado.
DROP PROCEDURE IF EXISTS insertarEmpleado;
DELIMITER $$
CREATE PROCEDURE insertarEmpleado(/* Datos Personales */
                                    IN    var_nombre          VARCHAR(64),    --  1
                                    IN    var_apellidoPaterno VARCHAR(64),    --  2
                                    IN    var_apellidoMaterno VARCHAR(64),    --  3
                                    IN  var_genero          VARCHAR(2),     --  4
                                    IN  var_fechaNacimiento VARCHAR(11),    --  5
                                    IN  var_rfc             VARCHAR(14),    --  6
                                    IN  var_curp            VARCHAR(19),    --  7
                                    IN    var_domicilio       VARCHAR(129),   --  8
                                    IN  var_cp              VARCHAR(11),    --  9
                                    IN  var_ciudad          VARCHAR(46),    -- 10
                                    IN  var_estado          VARCHAR(40),    -- 11
                                    IN    var_telefono        VARCHAR(20),    -- 12
                                    IN    var_foto            LONGTEXT,       -- 13
                                   
                                  /* Datos del Usuario    */
                                    IN  var_nombreUsuario   VARCHAR(45),    -- 14
                                    IN  var_contrasenia     VARCHAR(45),    -- 15
                                    IN  var_rol             VARCHAR(10),    -- 16
                                  /* Datos del Empleado */
                                    IN  var_email           VARCHAR(65),    -- 17
                                    IN  var_puesto          VARCHAR(25),    -- 18
                                    IN  var_salarioBruto    FLOAT,          -- 19
                                 
                                  /* Parametros de Salida */
                                    OUT var_idPersona       INT,            -- 20
                                    OUT var_idUsuario       INT,            -- 21
                                    OUT var_idEmpleado      INT,            -- 22
                                    OUT var_codigoEmpleado  VARCHAR(9)      -- 23
                                 )
    BEGIN
        -- Comenzamos insertando los datos de la Persona:
        INSERT INTO persona (nombre, apellidoPaterno, apellidoMaterno, genero,
                             fechaNacimiento, rfc, curp, domicilio, codigoPostal,
                             ciudad, estado, telefono, foto)
                    VALUES( var_nombre, var_apellidoPaterno, var_apellidoMaterno,
                            var_genero, STR_TO_DATE(var_fechaNacimiento, '%Y-%m-%d'),
                            var_rfc, var_curp, var_domicilio, var_cp,
                            var_ciudad, var_estado, var_telefono, var_foto);
       
        -- Obtenemos el ID de Persona que se genero:
        SET var_idPersona = LAST_INSERT_ID();
       
        -- Generamos el Codigo del Empleado porque lo necesitamos
        -- para generar el usuario:
        CALL generarCodigoEmpleado(var_codigoEmpleado);
       
        -- Insertamos los datos del Usuario que tendra el Empleado:
        INSERT INTO usuario (nombreUsuario, contrasenia, rol)
                    VALUES (var_nombreUsuario, var_contrasenia, var_rol);
        -- Recuperamos el ID de Usuario generado:
        SET var_idUsuario = LAST_INSERT_ID();
       
        -- Insertamos los datos del Empleado:
        INSERT INTO empleado(email, codigo, fechaIngreso, puesto, salarioBruto, activo,
                             idPersona, idUsuario)
                    VALUES(var_email, var_codigoEmpleado, NOW(), var_puesto, var_salarioBruto,
                           1, var_idPersona, var_idUsuario);
        -- Recuperamos el ID de Empleado generado:
        SET var_idEmpleado = LAST_INSERT_ID();
    END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS insertarProducto;
DELIMITER $$

CREATE PROCEDURE insertarProducto(
    /* Datos Personales */
    IN var_nombre VARCHAR(180), -- 1
    IN var_unidadMedida VARCHAR(25), -- 2
    IN var_presentacion VARCHAR(200), -- 3
    IN var_categoria VARCHAR(255), -- 4
    IN var_precioCompra FLOAT, -- 5
    IN var_precioVenta FLOAT, -- 6
    IN var_codigoProducto VARCHAR(255), -- 7
    IN var_codigoBarras VARCHAR(65), -- 8
    /* Parametros de Salida */
    OUT var_idProducto INT -- 9
)
BEGIN
    -- Insertamos los datos del producto:
    INSERT INTO producto (nombre, unidadMedida, presentacion, categoria,
                          precioCompra, precioVenta, codigoProducto, codigoBarras, estatus)
    VALUES(var_nombre, var_unidadMedida, var_presentacion, var_categoria,
           var_precioCompra, var_precioVenta, var_codigoProducto, var_codigoBarras, 1);
   
    -- Obtenemos el ID del producto que se generó:
    SET var_idProducto = LAST_INSERT_ID();
END
$$

DELIMITER ;





-- Procedure modificar producto:
DROP PROCEDURE IF EXISTS modificarProducto;
DELIMITER $$
CREATE PROCEDURE modificarProducto(
    /* Datos del Producto */
    IN var_nombre VARCHAR(180),
    IN var_unidadMedida VARCHAR(25),
    IN var_presentacion VARCHAR(200),
    IN var_categoria VARCHAR(255),
    IN var_precioCompra FLOAT,
    IN var_precioVenta FLOAT,
    IN var_codigoProducto VARCHAR(255),
    IN var_codigoBarras VARCHAR(65),
    /* Parametros de Salida */
    IN var_idProducto INT
)
BEGIN
    -- Actualizamos los datos del producto:
    UPDATE producto 
    SET nombre = var_nombre,
        unidadMedida = var_unidadMedida,
        presentacion = var_presentacion,
        categoria = var_categoria,
        precioCompra = var_precioCompra,
        precioVenta = var_precioVenta,
        codigoProducto = var_codigoProducto,
        codigoBarras = var_codigoBarras
    WHERE idProducto = var_idProducto;
END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS insertarCliente;
DELIMITER $$
CREATE PROCEDURE insertarCliente(/* Datos Personales */
                                    IN    var_nombre          VARCHAR(64),    --  1
                                    IN    var_apellidoPaterno VARCHAR(64),    --  2
                                    IN    var_apellidoMaterno VARCHAR(64),    --  3
                                    IN  var_genero          VARCHAR(2),     --  4
                                    IN  var_fechaNacimiento VARCHAR(11),    --  5
                                    IN  var_rfc             VARCHAR(14),    --  6
                                    IN  var_curp            VARCHAR(19),    --  7
                                    IN    var_domicilio       VARCHAR(129),   --  8
                                    IN  var_cp              VARCHAR(11),    --  9
                                    IN  var_ciudad          VARCHAR(46),    -- 10
                                    IN  var_estado          VARCHAR(40),    -- 11
                                    IN    var_telefono        VARCHAR(20),    -- 12
                                    IN    var_foto            LONGTEXT,       -- 13
                                  /* Datos del Cliente */
                                    IN  var_email          VARCHAR(65),    -- 14
                                 
                                  /* Parametros de Salida */
                                    OUT var_idPersona       INT,            -- 15
                                     OUT var_idCliente       INT            -- 16
                                 )
    BEGIN
        -- Comenzamos insertando los datos de la Persona:
        INSERT INTO persona (nombre, apellidoPaterno, apellidoMaterno, genero,
                             fechaNacimiento, rfc, curp, domicilio, codigoPostal,
                             ciudad, estado, telefono, foto)
                    VALUES( var_nombre, var_apellidoPaterno, var_apellidoMaterno,
                            var_genero, STR_TO_DATE(var_fechaNacimiento, '%Y-%m-%d'),
                            var_rfc, var_curp, var_domicilio, var_cp,
                            var_ciudad, var_estado, var_telefono, var_foto);
       
        -- Obtenemos el ID de Persona que se genero:
        SET var_idPersona = LAST_INSERT_ID();
       
       
        -- Insertamos los datos del Cliente:
        INSERT INTO cliente(email,  fechaRegistro, estatus, idPersona)
                    VALUES(var_email, NOW(),1, var_idPersona);
        -- Recuperamos el ID de Cliente generado:
        SET var_idCliente = LAST_INSERT_ID();
    END
$$
DELIMITER ;

        -- ---------------------------------------------------------- 

DROP PROCEDURE IF EXISTS modificarCliente;
DELIMITER $$
CREATE PROCEDURE modificarCliente(
                                     /* Datos Personales */
                                    IN    var_nombre          VARCHAR(64),    --  1
                                    IN    var_apellidoPaterno VARCHAR(64),    --  2
                                    IN    var_apellidoMaterno VARCHAR(64),    --  3
                                    IN  var_genero          VARCHAR(2),     --  4
                                    IN  var_fechaNacimiento VARCHAR(11),    --  5
                                    IN  var_rfc             VARCHAR(14),    --  6
                                    IN  var_curp            VARCHAR(19),    --  7
                                    IN    var_domicilio       VARCHAR(129),   --  8
                                    IN  var_cp              VARCHAR(11),    --  9
                                    IN  var_ciudad          VARCHAR(46),    -- 10
                                    IN  var_estado          VARCHAR(40),    -- 11
                                    IN    var_telefono        VARCHAR(20),    -- 12
                                    IN    var_foto            LONGTEXT,       -- 13
                                  /* Datos del Cliente */
                                    IN  var_email          VARCHAR(65),    -- 14
                                 
                                  /* Parametros de Salida */
                                    iN var_idPersona       INT,            -- 15
                                     IN var_idCliente       INT            -- 16
                                 )
BEGIN
    -- Actualizamos los datos de la Persona:
    UPDATE persona
    SET
        nombre = var_nombre,
        apellidoPaterno = var_apellidoPaterno,
        apellidoMaterno = var_apellidoMaterno,
        genero = var_genero,
        fechaNacimiento = STR_TO_DATE(var_fechaNacimiento, '%Y-%m-%d'),
        rfc = var_rfc,
        curp = var_curp,
        domicilio = var_domicilio,
        codigoPostal = var_cp,
        ciudad = var_ciudad,
        estado = var_estado,
        telefono = var_telefono,
        foto = var_foto
    WHERE idPersona = var_idPersona;


    -- Actualizamos los datos del Empleado:
    UPDATE cliente
    SET
        email = var_email
    WHERE idCliente = var_idCliente;
END
$$

DELIMITER ;