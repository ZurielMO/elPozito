package org.codeagle.elpozito.REST;

/**
 *
 * @author maico
 */
import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import org.codeagle.elpozito.controller.ControllerAcceso;
import org.codeagle.elpozito.controller.ControllerProducto;
import org.codeagle.elpozito.model.Inventario;
import org.codeagle.elpozito.model.Producto;

//http://localhost:8080/elpozitoWEB/api/producto/getAllProducto
@Path("producto")
public class RESTProducto {
    @Path("getAllProducto")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducto() {
        ControllerProducto objCP = new ControllerProducto();
        String out = "";
        List<Producto> listaProductos = objCP.getAllProducto();
        Gson objGson = new Gson();
        out = objGson.toJson(listaProductos);
        return Response.ok(out).build();
    }

    //------------------------------------------------------------------------------------------------------
    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("idP") @DefaultValue("0") String idP, @QueryParam("token") @DefaultValue("") String token) {
        String out = "";
        ControllerAcceso ca = new ControllerAcceso();
        try {
            if(ca.authToken(token)){
            ControllerProducto objCP = new ControllerProducto();
            objCP.delete(Integer.parseInt(idP));
            out = """
                {"result":"Producto eliminado exitosamente"}
                """;
            }
            else{
                out = """
                 {"error":"token invalido" }
               
                """;
            }
        } catch (SQLException ex) {
            out = """
                {"error":"Hubo un error en la eliminacion"}
                """;
        }
        return Response.ok(out).build();
    }

    //------------------------------------------------------------------------------------------------------
    @Path("reactivar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response reactivar(@QueryParam("idP") @DefaultValue("0") String idP, @QueryParam("token") @DefaultValue("") String token) {
        String out = "";
        ControllerAcceso ca = new ControllerAcceso();
        try {
            if(ca.authToken(token)){
            ControllerProducto objCP = new ControllerProducto();
            objCP.reactivar(Integer.parseInt(idP));
            out = """
                {"result":"Producto reactivado exitosamente"}
                """;
            }
            else{
                out = """
                 {"error":"token invalido" }
               
                """;
            }
        } catch (SQLException ex) {
            out = """
                {"error":"Hubo un error en la reactivacion"}
                """;
        }
        return Response.ok(out).build();
    }

    //------------------------------------------------------------------------------------------------------
    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("p") @DefaultValue("") String producto, @QueryParam("token") @DefaultValue("") String token) {
        Gson objGson = new Gson();
        //System.out.println("Impreso"+empleado);
        Producto p = objGson.fromJson(producto, Producto.class);

        String out = "";
        ControllerProducto objCP = new ControllerProducto();
        ControllerAcceso ca = new ControllerAcceso();

        try {
            if(ca.authToken(token)){
            int idProductoGenerado = objCP.insert(p);
            out = """
                 {"result":"Producto insertado exitosamente con id %s" }
               
                """;
            out = String.format(out, idProductoGenerado);
            }
            else{
                out = """
                 {"error":"token invalido" }
               
                """;
            }
        } catch (SQLException ex) {
            System.out.println(p);
            ex.printStackTrace();
            out = """
                 {"error":"Error al insertar Producto" }
               
                """;
        }
        return Response.ok(out).build();
    }

    //------------------------------------------------------------------------------------------------------
    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@FormParam("p") @DefaultValue("") String producto, @QueryParam("token") @DefaultValue("") String token) {
        Gson objGson = new Gson();
        //System.out.println("Impreso"+empleado);
        Producto p = objGson.fromJson(producto, Producto.class);

        String out = "";
        ControllerProducto objCP = new ControllerProducto();
        ControllerAcceso ca = new ControllerAcceso();

        try {
            if(ca.authToken(token)){
            int idProductoGenerado = objCP.update(p);
            out = """
                 {"result":"Producto modificado exitosamente con id %s" }
               
                """;
            out = String.format(out, idProductoGenerado);
            }
            else{
                out = """
                 {"error":"token invalido" }
               
                """;
            }
        } catch (SQLException ex) {
            System.out.println(p);
            ex.printStackTrace();
            out = """
                 {"error":"Error al modificar Producto" }
               
                """;
        }
        return Response.ok(out).build();
    }
    
    
    //------------------------------------------------------------------------------------------------------  
     @POST
    @Path("busquedaP")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBusquedaP(@FormParam("busqueda") @DefaultValue("") String busqueda, 
            @FormParam("token") @DefaultValue("") String token) throws SQLException {
         ControllerProducto controllerProducto = new ControllerProducto();
        ControllerAcceso lc = new ControllerAcceso();
        String out = "";

        try {
                List<Producto> productos = controllerProducto.search(busqueda);
                Gson objGS = new Gson();
                objGS.toJson(productos);
                out = objGS.toJson(productos);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Response.ok(out).build();
    }
    
    
    @POST
    @Path("busquedaI")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBusquedaI(@FormParam("id") @DefaultValue("") String id, 
            @FormParam("token") @DefaultValue("") String token) throws SQLException {
         ControllerProducto controllerProducto = new ControllerProducto();
        ControllerAcceso lc = new ControllerAcceso();
        String out = "";

        try {
                List<Inventario> productos = controllerProducto.searchI(id);
                Gson objGS = new Gson();
                objGS.toJson(productos);
                out = objGS.toJson(productos);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Response.ok(out).build();
    }
}

