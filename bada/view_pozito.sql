USE pozito;
-- Vista Producto:
CREATE OR REPLACE VIEW v_producto AS
SELECT
    p.idProducto AS idProducto,
    p.nombre AS Nombre_Producto,
    p.unidadMedida AS Unidad_Medida,
    p.presentacion AS Presentacion,
    p.categoria AS Categoria,
    p.precioCompra AS Precio_Compra,
    p.precioVenta AS Precio_Venta,
    p.codigoProducto AS Codigo_Producto,
    p.codigoBarras AS Codigo_Barras,
    p.estatus AS Estatus
FROM
    producto p;
    
    select * from v_producto;
-- -----------------------------------------------------
CREATE VIEW v_empleado AS
SELECT
    e.idEmpleado AS idEmpleado,
    e.codigo AS Empleado_Codigo,
    e.fechaIngreso AS Fecha_Ingreso,
    e.puesto AS Puesto,
    e.salarioBruto AS Salario_Bruto,
    e.activo AS Estatus_E,
    e.email AS Email_E,
    p.idPersona AS idPersona,
    p.foto  AS Foto_E,
    p.fechaNacimiento AS Fecha_Nacimiento,
    p.telefono AS Telefono_E,
    p.domicilio AS Domicilio_E,
    p.codigoPostal AS CP,
    p.ciudad AS Ciudad_E,
    p.estado AS Estado_E,
    p.nombre AS Nombre_E,
    p.apellidoPaterno AS Apellido_Paterno_E,
    p.apellidoMaterno AS Apellido_Materno_E,
    p.genero AS Genero_E,
    p.curp AS CURP_E,
    p.rfc AS RFC_E,
    u.idUsuario AS idUsuario,
    u.nombreUsuario AS Nombre_Usuario,
    u.contrasenia AS contrasenia,
    u.rol AS Rol,
    u.token AS Token
FROM
    empleado e
    JOIN persona p ON e.idPersona = p.idPersona
    JOIN usuario u ON e.idUsuario = u.idUsuario;  
    
CREATE VIEW v_clientes AS
SELECT
    c.idCliente AS idCliente,
    c.fechaRegistro AS Fecha_Registro,
    c.estatus AS Estatus_C,
    c.email AS Email_C,
    p.idPersona AS idPersona,
    p.foto  AS Foto_C,
    p.fechaNacimiento AS Fecha_Nacimiento,
    p.telefono AS Telefono_C,
    p.domicilio AS Domicilio_C,
    p.codigoPostal AS CP,
    p.ciudad AS Ciudad_C,
    p.estado AS Estado_C,
    p.nombre AS Nombre_C,
    p.apellidoPaterno AS Apellido_Paterno_C,
    p.apellidoMaterno AS Apellido_Materno_C,
    p.genero AS Genero_C,
    p.curp AS CURP_c,
    p.rfc AS RFC_C
FROM
    cliente c
    JOIN persona p ON c.idPersona = p.idPersona;
    
    SELECT * FROM v_clientes; 
   
   CREATE VIEW vista_inventario AS
SELECT
    inv.idInventario,
    inv.idProducto,
    prod.nombre AS nombre_producto,
    prod.unidadMedida,
    prod.presentacion,
    prod.categoria,
    prod.precioCompra,
    prod.precioVenta,
    prod.codigoProducto,
    prod.codigoBarras,
    prod.estatus AS estatus_producto,
    inv.existencias
FROM
    inventario inv
INNER JOIN producto prod ON inv.idProducto = prod.idProducto;
   
   
   
   
  DROP VIEW IF EXISTS Venta_View;

CREATE VIEW Venta_View AS
SELECT
    v.idVenta,
    v.fechaHora AS fechaVenta,
    v.estatus AS estatusVenta,
    c.idCliente AS idCliente,
    c.email AS emailCliente,
    c.fechaRegistro AS fechaRegistroCliente,
    c.estatus AS estatusCliente,
    pc.nombre AS nombreCliente,
    pc.apellidoPaterno AS apellidoPaternoCliente,
    pc.apellidoMaterno AS apellidoMaternoCliente,
    pc.genero AS generoCliente,
    pc.fechaNacimiento AS fechaNacimientoCliente,
    pc.rfc AS rfcCliente,
    pc.curp AS curpCliente,
    pc.domicilio AS domicilioCliente,
    pc.codigoPostal AS codigoPostalCliente,
    pc.ciudad AS ciudadCliente,
    pc.estado AS estadoCliente,
    pc.telefono AS telefonoCliente,
    pc.foto AS fotoCliente,
    e.idEmpleado AS idEmpleado,
    pe.nombre AS nombreEmpleado,
    pe.apellidoPaterno AS apellidoPaternoEmpleado,
    pe.apellidoMaterno AS apellidoMaternoEmpleado,
    pe.genero AS generoEmpleado,
    pe.fechaNacimiento AS fechaNacimientoEmpleado,
    pe.rfc AS rfcEmpleado,
    pe.curp AS curpEmpleado,
    pe.domicilio AS domicilioEmpleado,
    pe.codigoPostal AS codigoPostalEmpleado,
    pe.ciudad AS ciudadEmpleado,
    pe.estado AS estadoEmpleado,
    pe.telefono AS telefonoEmpleado,
    pe.foto AS fotoEmpleado,
    e.email AS emailEmpleado,
    e.codigo AS codigoEmpleado,
    e.fechaIngreso AS fechaIngresoEmpleado,
    e.puesto AS puestoEmpleado,
    e.salarioBruto AS salarioBrutoEmpleado,
    e.activo AS activoEmpleado,
    u.idUsuario AS idUsuarioEmpleado,
    u.nombreUsuario AS nombreUsuarioEmpleado,
    u.contrasenia AS contraseniaEmpleado,
    u.rol AS rolEmpleado,
    u.token AS tokenEmpleado
FROM
    venta v
    JOIN cliente c ON v.idCliente = c.idCliente
    JOIN persona pc ON c.idPersona = pc.idPersona
    JOIN empleado e ON v.idEmpleado = e.idEmpleado
    JOIN persona pe ON e.idPersona = pe.idPersona
    JOIN usuario u ON e.idUsuario = u.idUsuario;
    
    
    
    
    
    
    DROP VIEW IF EXISTS detalleVenta_View;

CREATE VIEW detalleVenta_View AS
SELECT
    dv.idProducto AS idProducto_Dv,
    dv.idVenta AS idVenta,
    dv.cantidad AS cantidad,
    dv.precioVenta AS precioVenta,
    p.idProducto AS idProducto,
    p.nombre AS Nombre_Producto,
    p.unidadMedida AS Unidad_Medida,
    p.presentacion AS Presentacion,
    p.categoria AS Categoria,
    p.precioCompra AS Precio_Compra,
    p.precioVenta AS Precio_Venta,
    p.codigoProducto AS Codigo_Producto,
    p.codigoBarras AS Codigo_Barras,
    p.estatus AS Estatus,
    v.fechaHora AS fechaVenta,
    v.estatus AS estatusVenta,
    c.idCliente AS idCliente,
    c.email AS emailCliente,
    c.fechaRegistro AS fechaRegistroCliente,
    c.estatus AS estatusCliente,
    pc.nombre AS nombreCliente,
    pc.apellidoPaterno AS apellidoPaternoCliente,
    pc.apellidoMaterno AS apellidoMaternoCliente,
    pc.genero AS generoCliente,
    pc.fechaNacimiento AS fechaNacimientoCliente,
    pc.rfc AS rfcCliente,
    pc.curp AS curpCliente,
    pc.domicilio AS domicilioCliente,
    pc.codigoPostal AS codigoPostalCliente,
    pc.ciudad AS ciudadCliente,
    pc.estado AS estadoCliente,
    pc.telefono AS telefonoCliente,
    pc.foto AS fotoCliente,
    e.idEmpleado AS idEmpleado,
    pe.nombre AS nombreEmpleado,
    pe.apellidoPaterno AS apellidoPaternoEmpleado,
    pe.apellidoMaterno AS apellidoMaternoEmpleado,
    pe.genero AS generoEmpleado,
    pe.fechaNacimiento AS fechaNacimientoEmpleado,
    pe.rfc AS rfcEmpleado,
    pe.curp AS curpEmpleado,
    pe.domicilio AS domicilioEmpleado,
    pe.codigoPostal AS codigoPostalEmpleado,
    pe.ciudad AS ciudadEmpleado,
    pe.estado AS estadoEmpleado,
    pe.telefono AS telefonoEmpleado,
    pe.foto AS fotoEmpleado,
    e.email AS emailEmpleado,
    e.codigo AS codigoEmpleado,
    e.fechaIngreso AS fechaIngresoEmpleado,
    e.puesto AS puestoEmpleado,
    e.salarioBruto AS salarioBrutoEmpleado,
    e.activo AS activoEmpleado,
    u.idUsuario AS idUsuarioEmpleado,
    u.nombreUsuario AS nombreUsuarioEmpleado,
    u.contrasenia AS contraseniaEmpleado,
    u.rol AS rolEmpleado,
    u.token AS tokenEmpleado
FROM
    detalleVenta dv
    JOIN producto p ON dv.idProducto = p.idProducto
    JOIN venta v ON dv.idVenta = v.idVenta
    JOIN cliente c ON v.idCliente = c.idCliente
    JOIN persona pc ON c.idPersona = pc.idPersona
    JOIN empleado e ON v.idEmpleado = e.idEmpleado
    JOIN persona pe ON e.idPersona = pe.idPersona
    JOIN usuario u ON e.idUsuario = u.idUsuario;
