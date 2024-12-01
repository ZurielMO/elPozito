/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.codeagle.elpozito.REST;

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
import java.util.ArrayList;
import java.util.List;
import org.codeagle.elpozito.controller.ControllerAcceso;
import org.codeagle.elpozito.controller.ControllerVenta;
import org.codeagle.elpozito.model.DetalleVenta;
import org.codeagle.elpozito.model.Venta;

/**
 *
 * @author maico
 */
@Path("venta")
public class RESTVenta {
    @Path("generarVenta")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response generarVenta(@FormParam("v") @DefaultValue("") String venta) {
        Gson objGson = new Gson();
//        System.out.println(venta);
        Venta v = objGson.fromJson(venta, Venta.class);
//        System.out.println(v.toString());

        String out = "";
        ControllerVenta objCV = new ControllerVenta();

        objCV.generarVenta(v); //System.out.println(v);
        out = """
                        {"result":"Venta generada exitosamente" }
                               """;
        out = String.format(out);
        return Response.ok(out).build();
    }
    
    
    @POST
    @Path("busquedaVenta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response busquedaVenta(@FormParam("busqueda") @DefaultValue("") String busqueda, @FormParam("token") @DefaultValue("") String token) throws SQLException {
        ControllerVenta CV = new ControllerVenta();
        ControllerAcceso lc = new ControllerAcceso();
        String out = "";
        try {
            if (lc.authToken(token)) {
//               
                int ventaId = Integer.parseInt(busqueda);
                ArrayList<DetalleVenta> listDV = CV.searchDV(ventaId);
                Gson objGS = new Gson();
                objGS.toJson(listDV);
                System.out.println(listDV);
                out = objGS.toJson(listDV);
            } else {
                out = """
                {"error":"Token invalido"}
                """;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Response.ok(out).build();
    }
    
    
//     @Path("getAllVenta")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getAllVenta() {
//        String out = "";
//       
//                ControllerVenta CV = new ControllerVenta();
//
//                List<DetalleVenta> lista = CV.getAllDV();
//                Gson objGson = new Gson();
//                out = objGson.toJson(lista);
//
//           
//                out = """
//                {"sucess":"Bien"}
//                """;
//            
//        
//        return Response.ok(out).build();
//    }
    
    
    @Path("getAllDV")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDV()  {
        ControllerVenta CV = new ControllerVenta();
        String out = "";
        List<DetalleVenta>lista = CV.getAllDV();
        Gson objGson = new Gson();
        out = objGson.toJson(lista);
        return Response.ok(out).build();

    }
    @Path("getAllV")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllV()  {
        ControllerVenta V = new ControllerVenta();
        String out = "";
        List<Venta>lista = V.getAllVenta();
        Gson objGson = new Gson();
        out = objGson.toJson(lista);
        return Response.ok(out).build();

    }
}
