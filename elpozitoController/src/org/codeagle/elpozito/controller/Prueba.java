package org.codeagle.elpozito.controller;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codeagle.elpozito.bd.ConexionMySQL;
import org.codeagle.elpozito.model.DetalleVenta;
import org.codeagle.elpozito.model.Empleado;
import org.codeagle.elpozito.model.Persona;
import org.codeagle.elpozito.model.Usuario;
import org.codeagle.elpozito.model.Venta;


public class Prueba {
   
    public static void main(String[] args) {
//        probarConexion();
//probarEncriptar();
//probarLogin();
//probarAuthToken();
//probarIndetificarEmpleado();
//probarVenta();
//probarVistaDV();
//getAll();
getAllV();
    }
    
    public static void probarAuthToken(){
        ControllerAcceso CA = new ControllerAcceso();
        try {
            boolean r=CA.authToken("899b81502286b5415d77d4a03876285badaf6444d2bfe79c74222cebfa52b845");
            System.out.println(r);
        } catch (SQLException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void probarEncriptar(){
        Usuario u = new Usuario();
        u.setNombreUsuario("admin");
        u.setContrasenia("123");
        u.encode();
        System.out.println(u.toString());
    }
     public static void probarConexion(){
            ConexionMySQL objMYSQL  = new ConexionMySQL();
            Connection conn= objMYSQL.open();
            System.out.println(conn.toString());
            objMYSQL.close();
    }
      public static void probarLogin(){
             Usuario u = new Usuario();
        u.setNombreUsuario("admin");
        u.setContrasenia("123");
        u.encode();
        ControllerAcceso ca = new ControllerAcceso();
        try {
            ca.login(u);
             if (u.getIdUsuario()>0) {
             u.colocarToken();
             ca.saveToken(u);
         }
            System.out.println(u.toString());
        } catch (SQLException ex) {
ex.printStackTrace();        }
    }
      
public static void probarIndetificarEmpleado(){
       ControllerAcceso CA = new ControllerAcceso();
       Empleado e = new Empleado();
       Usuario u = new Usuario();
       Persona p = new Persona();
       e.setPersona(p);
       e.setUsuario(u);
       u.setIdUsuario(1);
        try {
            CA.indetificarEmp(u,e);
                 System.out.print(e.toString());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); 
        }
    }
////Mio:
////public static void probarVenta(){
////    Sucursal s = new Sucursal();
////    s.setIdSucursal(1);
////    Empleado e = new Empleado();
////    e.setIdEmpleado(1);
////    Cliente c = new Cliente();
////    c.setIdCliente(9);
////    Producto p1 = new Producto();
////   Producto p2 = new Producto();
////   Producto p3 = new Producto();
////   Producto p4 = new Producto();
////   p1.setIdProducto(1);
////   p2.setIdProducto(2);
////   p3.setIdProducto(3);
////   p4.setIdProducto(4);
////   List<DetalleVenta> listDV = new ArrayList<DetalleVenta>();
////   DetalleVenta dv1= new DetalleVenta();
////   dv1.setProducto(p1);
////   dv1.setPrecioVenta(150);
////   dv1.setCantidad(5);
////   listDV.add(dv1);
////   DetalleVenta dv2= new DetalleVenta();
////   dv1.setProducto(p2);
////   dv1.setPrecioVenta(150);
////   dv1.setCantidad(5);
////   listDV.add(dv2);
////   DetalleVenta dv3= new DetalleVenta();
////   dv1.setProducto(p3);
////   dv1.setPrecioVenta(150);
////   dv1.setCantidad(5);
////   listDV.add(dv3);
////    DetalleVenta dv4= new DetalleVenta();
////   dv1.setProducto(p4);
////   dv1.setPrecioVenta(150);
////   dv1.setCantidad(5);
////   listDV.add(dv4);
////   Venta v = new Venta();
////   v.setCliente(c);
////   v.setEmpleado(e);
////   v.setEstatus(1);
////   v.setListaDV(listDV);
////   ControllerVenta cv = new ControllerVenta();
////   cv.generarVenta(v);
////}
////Zuriel:
//public static void probarVenta(){
//    Sucursal s = new Sucursal();
//    s.setIdSucursal(1);
//    Empleado e = new Empleado();
//    e.setIdEmpleado(1);
//    e.setSucursal(s);
//    Cliente c = new Cliente();
//    c.setIdCliente(2);
//    Producto p1 = new Producto();
//    Producto p2 = new Producto();
//    Producto p3 = new Producto();
//    Producto p4 = new Producto();
//    p1.setIdProducto(1);
//    p2.setIdProducto(2);
//    p3.setIdProducto(3);
//    p4.setIdProducto(4);
//    List<DetalleVenta> listadetalleVenta = new ArrayList<DetalleVenta>();
//    DetalleVenta dv1 = new DetalleVenta();
//    dv1.setProducto(p1);
//    dv1.setPrecioVenta(150);
//    dv1.setCantidad(5);
//    listadetalleVenta.add(dv1);
//    DetalleVenta dv2 = new DetalleVenta();
//    dv2.setProducto(p2);
//    dv2.setPrecioVenta(150);
//    dv2.setCantidad(5);
//    listadetalleVenta.add(dv2);
//    DetalleVenta dv3 = new DetalleVenta();
//    dv3.setProducto(p3);
//    dv3.setPrecioVenta(150);
//    dv3.setCantidad(5);
//    listadetalleVenta.add(dv3);
//    DetalleVenta dv4 = new DetalleVenta();
//    dv4.setProducto(p4);
//    dv4.setPrecioVenta(150);
//    dv4.setCantidad(5);
//    listadetalleVenta.add(dv4);
//    Venta v = new Venta();
//    v.setCliente(c);
//    v.setEmpleado(e);
//    v.setEstatus(1);
//    v.setListaDV(listadetalleVenta);
//    ControllerVenta cv = new ControllerVenta();
//    cv.generarVenta(v);
//}
//
//public static void probarVistaDV(){
//    int idVenta = 1; // Coloca aquí el id de venta que deseas buscar
//
//        ControllerVenta controller = new ControllerVenta(); // Suponiendo que el nombre de tu controlador es DetalleVentaController
//
//        try {
//            List<DetalleVenta> detalleVentaList = controller.search(idVenta);
//            if (!detalleVentaList.isEmpty()) {
//                System.out.println("Detalle de venta encontrado para el id " + idVenta + ":");
//                for (DetalleVenta detalleVenta : detalleVentaList) {
//                    // Aquí puedes imprimir o realizar operaciones con cada detalleVenta encontrado
//                    System.out.println(detalleVenta.toString());
//                }
//            } else {
//                System.out.println("No se encontró ningún detalle de venta para el id " + idVenta);
//            }
//        } catch (SQLException ex) {
//            System.out.println("Error al buscar detalles de venta: " + ex.getMessage());
//        }
//    }
//
public static void getAll(){
        ControllerVenta dv = new ControllerVenta();
        List<DetalleVenta>lista=dv.getAllDV();
        for (DetalleVenta detalleVenta : lista) {
            System.out.println(detalleVenta.toString());
        } 
    }
public static void getAllV(){
        ControllerVenta v = new ControllerVenta();
        List<Venta>lista=v.getAllVenta();
        for (Venta venta : lista) {
            System.out.println(venta.toString());
        } 
    }
}

