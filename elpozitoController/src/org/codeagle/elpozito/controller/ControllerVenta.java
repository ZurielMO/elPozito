/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.codeagle.elpozito.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import org.codeagle.elpozito.bd.ConexionMySQL;
import org.codeagle.elpozito.model.Cliente;
import org.codeagle.elpozito.model.DetalleVenta;
import org.codeagle.elpozito.model.Empleado;
import org.codeagle.elpozito.model.Persona;
import org.codeagle.elpozito.model.Producto;
import org.codeagle.elpozito.model.Usuario;
import org.codeagle.elpozito.model.Venta;
/**
 *
 * @author maico
 */
public class ControllerVenta {
    
 public void generarVenta(Venta v)
    {
        Connection conn = null;
        try
        {
            ConexionMySQL connMySQL = new ConexionMySQL();
            conn = connMySQL.open();
            conn.setAutoCommit(false);
            String query1 = "INSERT INTO venta(fechaHora,idCliente,idEmpleado,estatus) VALUES(NOW(),?,?,?);";
            PreparedStatement pstmt1 = conn.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
            String query2 = "INSERT INTO detalleVenta(idVenta,idProducto,precioVenta,cantidad) VALUES(?,?,?,?);";
            PreparedStatement pstmt2 = conn.prepareStatement(query2);
            String query3 = "UPDATE inventario SET existencias=existencias-? WHERE idProducto=?;";
            PreparedStatement pstmt3 = conn.prepareStatement(query3);
            String query4 ="SELECT existencias FROM inventario WHERE idProducto=?;";
            PreparedStatement pstmt4 = conn.prepareStatement(query4);
            ResultSet rs;
             ResultSet rs2;
            pstmt1.setInt(1, v.getCliente().getIdCliente());
            pstmt1.setInt(2, v.getEmpleado().getIdEmpleado());
            pstmt1.setInt(3, 1);
            pstmt1.executeUpdate();
            rs = pstmt1.getGeneratedKeys();
            if (rs.next())
            {
                v.setIdventa(rs.getInt(1));
 
                for (int i = 0; i < v.getListDV().size(); i++)
                {
                    pstmt4.setInt(1, v.getListDV().get(i).getProducto().getIdProducto());
//                    pstmt4.setInt(2, v.getEmpleado().getSucursal().getIdSucursal());
                    rs2=pstmt4.executeQuery();
                   int existenciasP=0;
                    if (rs2.next()) {
                    existenciasP=rs2.getInt(1);
                    }
                    if (v.getListDV().get(i).getCantidad()<=existenciasP) {
                        
                    
                    pstmt2.setInt(1, v.getIdventa());
                    pstmt2.setInt(2, v.getListDV().get(i).getProducto().getIdProducto());
                    pstmt2.setFloat(3, v.getListDV().get(i).getPrecioVenta());
                    pstmt2.setInt(4, v.getListDV().get(i).getCantidad());
                    pstmt2.addBatch();
                    pstmt3.setInt(1, v.getListDV().get(i).getCantidad());
                    pstmt3.setInt(2, v.getListDV().get(i).getProducto().getIdProducto());
//                    pstmt3.setInt(3, v.getEmpleado().getSucursal().getIdSucursal());
                    pstmt3.addBatch();
                }else{
                   conn.rollback();
                    }
                    }
                pstmt2.executeBatch();
                pstmt3.executeBatch();
                conn.commit();
            }
            else
            {
                conn.rollback();
            }
            rs.close();
            pstmt1.close();
            pstmt2.close();
            pstmt3.close();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
            try
            {
                conn.rollback();
            } catch (SQLException ex1)
            {
                ex1.printStackTrace();
            }
        } finally
        {
            try
            {
                if (!conn.isClosed())
                    conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }
  public List<DetalleVenta> getAllDV() {
    //Aqui va ña primera parte del paso 6
        List<DetalleVenta> listaDV = new ArrayList<>();
        try {
            //1) Crear la setencia SQL
            String query = "SELECT * FROM detalleVenta_View";
            //2) Se establece la conexion con la base de datos
            ConexionMySQL connMySQL = new ConexionMySQL();
            //3) Se abre la conexion y devuelve un tipo conexion
            Connection conn = connMySQL.open();
            //4) Se genera el statement para enviar la consulta
            PreparedStatement pstm = conn.prepareStatement(query);
            //5.Se prepara un ResultSet para obtener la respuesta de la BD
            ResultSet rs = pstm.executeQuery();
            //6) Recorrer el rs y extraer los datos

            while (rs.next()) {
              Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuarioEmpleado"));
                u.setNombreUsuario(rs.getString("nombreUsuarioEmpleado"));
                u.setContrasenia(rs.getString("contraseniaEmpleado"));
                u.setRol(rs.getString("rolEmpleado"));
                u.setToken(rs.getString("tokenEmpleado"));
                
                Persona pEmpelado = new Persona();
//                pEmpelado.setIdPersona(rs.getInt("idPersona"));
                pEmpelado.setFoto(rs.getString("fotoEmpleado"));
                pEmpelado.setFechaNacimiento(rs.getString("fechaNacimientoEmpleado"));
                pEmpelado.setTelefono(rs.getString("telefonoEmpleado"));
                pEmpelado.setDomicilio(rs.getString("domicilioEmpleado"));
                pEmpelado.setCodigoPostal(rs.getString("codigoPostalEmpleado"));
                pEmpelado.setCiudad(rs.getString("ciudadEmpleado"));
                pEmpelado.setEstado(rs.getString("estadoEmpleado"));
                pEmpelado.setNombre(rs.getString("nombreEmpleado"));
                pEmpelado.setApellidoPaterno(rs.getString("apellidoPaternoEmpleado"));
                pEmpelado.setApellidoMaterno(rs.getString("apellidoMaternoEmpleado"));
                pEmpelado.setGenero(rs.getString("generoEmpleado"));
                pEmpelado.setCurp(rs.getString("curpEmpleado"));
                pEmpelado.setRfc(rs.getString("rfcEmpleado"));
                
 
                  Persona pCliete = new Persona();
//                pCliete.setIdPersona(rs.getInt("idPerson"));
                pCliete.setFoto(rs.getString("fotoCliente"));
                pCliete.setFechaNacimiento(rs.getString("fechaNacimientoCliente"));
                pCliete.setTelefono(rs.getString("telefonoCliente"));
                pCliete.setDomicilio(rs.getString("domicilioCliente"));
                pCliete.setCodigoPostal(rs.getString("codigoPostalCliente"));
                pCliete.setCiudad(rs.getString("ciudadCliente"));
                pCliete.setEstado(rs.getString("estadoCliente"));
                pCliete.setNombre(rs.getString("nombreCliente"));
                pCliete.setApellidoPaterno(rs.getString("apellidoPaternoCliente"));
                pCliete.setApellidoMaterno(rs.getString("apellidoMaternoCliente"));
                pCliete.setGenero(rs.getString("generoCliente"));
                pCliete.setCurp(rs.getString("curpCliente"));
                pCliete.setRfc(rs.getString("rfcCliente"));

                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setEmail(rs.getString("emailCliente"));
                c.setFechaRegistro(rs.getString("fechaRegistroCliente"));
                c.setEstatus(rs.getInt("estatusCliente"));
                c.setPersona(pCliete);
                
                Empleado e = new Empleado();
                e.setIdEmpleado(rs.getInt("idEmpleado"));
                e.setCodigo(rs.getString("codigoEmpleado"));
                e.setFechaIngreso(rs.getString("fechaIngresoEmpleado"));
                e.setPuesto(rs.getString("puestoEmpleado"));
                e.setSalarioBruto(rs.getFloat("salarioBrutoEmpleado"));
                e.setActivo(rs.getInt("activoEmpleado"));
                e.setEmail(rs.getString("emailEmpleado"));
                e.setUsuario(u);
                e.setPersona(pEmpelado);

                 Producto p = new Producto();
                p.setCodigoBarras(rs.getString("Codigo_Barras"));
                p.setEstatus(rs.getInt("Estatus"));
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("Nombre_Producto"));
                p.setPrecioCompra(rs.getFloat("Precio_Compra"));
                p.setPrecioVenta(rs.getFloat("Precio_Venta"));
                p.setPresentacion(rs.getString("Presentacion"));
                p.setUnidadMedida(rs.getString("Unidad_Medida"));
                p.setCategoria(rs.getString("Categoria"));
                p.setCodigoProducto(rs.getString("Codigo_Producto"));
                
                Venta v = new Venta();
                v.setIdventa(rs.getInt("idVenta"));
                v.setFechaHora(rs.getString("fechaVenta"));
                v.setEstatus(rs.getInt("estatusVenta"));
                v.setEmpleado(e);
                v.setCliente(c);
                
                DetalleVenta DV = new DetalleVenta();
                DV.setProducto(p);
                DV.setVenta(v);
                DV.setCantidad(rs.getInt("cantidad"));
                DV.setPrecioVenta(rs.getFloat("precioVenta"));
                listaDV.add(DV);
             
                
//                listaDV.add(p);
            }
            //7) Cerrar todos los objetos
            rs.close();
            pstm.close();
            conn.close();
            connMySQL.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //8) Devolver la informacion
        return listaDV;
    }
    public List<Venta> getAllVenta() {
    List<Venta> listaV = new ArrayList<>();
    try {
        String query = "SELECT * FROM Venta_View";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(query);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
              Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuarioEmpleado"));
                u.setNombreUsuario(rs.getString("nombreUsuarioEmpleado"));
                u.setContrasenia(rs.getString("contraseniaEmpleado"));
                u.setRol(rs.getString("rolEmpleado"));
                u.setToken(rs.getString("tokenEmpleado"));
                
                Persona pEmpelado = new Persona();
//                pEmpelado.setIdPersona(rs.getInt("idPersona"));
                pEmpelado.setFoto(rs.getString("fotoEmpleado"));
                pEmpelado.setFechaNacimiento(rs.getString("fechaNacimientoEmpleado"));
                pEmpelado.setTelefono(rs.getString("telefonoEmpleado"));
                pEmpelado.setDomicilio(rs.getString("domicilioEmpleado"));
                pEmpelado.setCodigoPostal(rs.getString("codigoPostalEmpleado"));
                pEmpelado.setCiudad(rs.getString("ciudadEmpleado"));
                pEmpelado.setEstado(rs.getString("estadoEmpleado"));
                pEmpelado.setNombre(rs.getString("nombreEmpleado"));
                pEmpelado.setApellidoPaterno(rs.getString("apellidoPaternoEmpleado"));
                pEmpelado.setApellidoMaterno(rs.getString("apellidoMaternoEmpleado"));
                pEmpelado.setGenero(rs.getString("generoEmpleado"));
                pEmpelado.setCurp(rs.getString("curpEmpleado"));
                pEmpelado.setRfc(rs.getString("rfcEmpleado"));
                
 
                  Persona pCliete = new Persona();
//                pCliete.setIdPersona(rs.getInt("idPerson"));
                pCliete.setFoto(rs.getString("fotoCliente"));
                pCliete.setFechaNacimiento(rs.getString("fechaNacimientoCliente"));
                pCliete.setTelefono(rs.getString("telefonoCliente"));
                pCliete.setDomicilio(rs.getString("domicilioCliente"));
                pCliete.setCodigoPostal(rs.getString("codigoPostalCliente"));
                pCliete.setCiudad(rs.getString("ciudadCliente"));
                pCliete.setEstado(rs.getString("estadoCliente"));
                pCliete.setNombre(rs.getString("nombreCliente"));
                pCliete.setApellidoPaterno(rs.getString("apellidoPaternoCliente"));
                pCliete.setApellidoMaterno(rs.getString("apellidoMaternoCliente"));
                pCliete.setGenero(rs.getString("generoCliente"));
                pCliete.setCurp(rs.getString("curpCliente"));
                pCliete.setRfc(rs.getString("rfcCliente"));

                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setEmail(rs.getString("emailCliente"));
                c.setFechaRegistro(rs.getString("fechaRegistroCliente"));
                c.setEstatus(rs.getInt("estatusCliente"));
                c.setPersona(pCliete);
                
                Empleado e = new Empleado();
                e.setIdEmpleado(rs.getInt("idEmpleado"));
                e.setCodigo(rs.getString("codigoEmpleado"));
                e.setFechaIngreso(rs.getString("fechaIngresoEmpleado"));
                e.setPuesto(rs.getString("puestoEmpleado"));
                e.setSalarioBruto(rs.getFloat("salarioBrutoEmpleado"));
                e.setActivo(rs.getInt("activoEmpleado"));
                e.setEmail(rs.getString("emailEmpleado"));
                e.setUsuario(u);
                e.setPersona(pEmpelado);

           
            // Venta
            Venta v = new Venta();
            v.setIdventa(rs.getInt("idVenta"));
            v.setFechaHora(rs.getString("fechaVenta"));
            v.setEstatus(rs.getInt("estatusVenta"));
            v.setEmpleado(e);
            v.setCliente(c);
            listaV.add(v);
        }

        rs.close();
        pstm.close();
        conn.close();
        connMySQL.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return listaV;
}

     
 public ArrayList<DetalleVenta> searchDV(int id) throws SQLException {
    ArrayList<DetalleVenta> listDV = new ArrayList<>();
    String query = "SELECT * FROM detalleVenta_View WHERE idVenta=?";

    try (Connection conn = new ConexionMySQL().open();
         PreparedStatement pstm = conn.prepareStatement(query)) {

        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        listDV = listDetalle(rs);
        
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return listDV;
}
   public ArrayList<DetalleVenta> listDetalle(ResultSet rs) throws SQLException {
        ArrayList<DetalleVenta> detVen = new ArrayList<>();
        while (rs.next()) {
              Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuarioEmpleado"));
                u.setNombreUsuario(rs.getString("nombreUsuarioEmpleado"));
                u.setContrasenia(rs.getString("contraseniaEmpleado"));
                u.setRol(rs.getString("rolEmpleado"));
                u.setToken(rs.getString("tokenEmpleado"));
                
                Persona pEmpelado = new Persona();
//                pEmpelado.setIdPersona(rs.getInt("idPersona"));
                pEmpelado.setFoto(rs.getString("fotoEmpleado"));
                pEmpelado.setFechaNacimiento(rs.getString("fechaNacimientoEmpleado"));
                pEmpelado.setTelefono(rs.getString("telefonoEmpleado"));
                pEmpelado.setDomicilio(rs.getString("domicilioEmpleado"));
                pEmpelado.setCodigoPostal(rs.getString("codigoPostalEmpleado"));
                pEmpelado.setCiudad(rs.getString("ciudadEmpleado"));
                pEmpelado.setEstado(rs.getString("estadoEmpleado"));
                pEmpelado.setNombre(rs.getString("nombreEmpleado"));
                pEmpelado.setApellidoPaterno(rs.getString("apellidoPaternoEmpleado"));
                pEmpelado.setApellidoMaterno(rs.getString("apellidoMaternoEmpleado"));
                pEmpelado.setGenero(rs.getString("generoEmpleado"));
                pEmpelado.setCurp(rs.getString("curpEmpleado"));
                pEmpelado.setRfc(rs.getString("rfcEmpleado"));
                
 
                  Persona pCliete = new Persona();
//                pCliete.setIdPersona(rs.getInt("idPerson"));
                pCliete.setFoto(rs.getString("fotoCliente"));
                pCliete.setFechaNacimiento(rs.getString("fechaNacimientoCliente"));
                pCliete.setTelefono(rs.getString("telefonoCliente"));
                pCliete.setDomicilio(rs.getString("domicilioCliente"));
                pCliete.setCodigoPostal(rs.getString("codigoPostalCliente"));
                pCliete.setCiudad(rs.getString("ciudadCliente"));
                pCliete.setEstado(rs.getString("estadoCliente"));
                pCliete.setNombre(rs.getString("nombreCliente"));
                pCliete.setApellidoPaterno(rs.getString("apellidoPaternoCliente"));
                pCliete.setApellidoMaterno(rs.getString("apellidoMaternoCliente"));
                pCliete.setGenero(rs.getString("generoCliente"));
                pCliete.setCurp(rs.getString("curpCliente"));
                pCliete.setRfc(rs.getString("rfcCliente"));

                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setEmail(rs.getString("emailCliente"));
                c.setFechaRegistro(rs.getString("fechaRegistroCliente"));
                c.setEstatus(rs.getInt("estatusCliente"));
                c.setPersona(pCliete);
                
                Empleado e = new Empleado();
                e.setIdEmpleado(rs.getInt("idEmpleado"));
                e.setCodigo(rs.getString("codigoEmpleado"));
                e.setFechaIngreso(rs.getString("fechaIngresoEmpleado"));
                e.setPuesto(rs.getString("puestoEmpleado"));
                e.setSalarioBruto(rs.getFloat("salarioBrutoEmpleado"));
                e.setActivo(rs.getInt("activoEmpleado"));
                e.setEmail(rs.getString("emailEmpleado"));
                e.setUsuario(u);
                e.setPersona(pEmpelado);

                 Producto p = new Producto();
                p.setCodigoBarras(rs.getString("Codigo_Barras"));
                p.setEstatus(rs.getInt("Estatus"));
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("Nombre_Producto"));
                p.setPrecioCompra(rs.getFloat("Precio_Compra"));
                p.setPrecioVenta(rs.getFloat("Precio_Venta"));
                p.setPresentacion(rs.getString("Presentacion"));
                p.setUnidadMedida(rs.getString("Unidad_Medida"));
                p.setCategoria(rs.getString("Categoria"));
                p.setCodigoProducto(rs.getString("Codigo_Producto"));
                
                Venta v = new Venta();
                v.setIdventa(rs.getInt("idVenta"));
                v.setFechaHora(rs.getString("fechaVenta"));
                v.setEstatus(rs.getInt("estatusVenta"));
                v.setEmpleado(e);
                v.setCliente(c);
                
                DetalleVenta DV = new DetalleVenta();
                DV.setProducto(p);
                DV.setVenta(v);
                DV.setCantidad(rs.getInt("cantidad"));
                DV.setPrecioVenta(rs.getFloat("precioVenta"));
                detVen.add(DV);
        }
        return detVen;
    }
    }

