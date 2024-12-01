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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codeagle.elpozito.controller.ControllerAcceso;
import org.codeagle.elpozito.controller.ControllerEmpleado;
import org.codeagle.elpozito.model.Empleado;


@Path("empleado")
public class RESTEmpleado {

    ControllerAcceso CA = new ControllerAcceso();
    @Path("getAllEmpleado")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("token") @DefaultValue("") String token) {
        String out = "";
        try {
            if (CA.authToken(token)) {
                ControllerEmpleado objCE = new ControllerEmpleado();

                List<Empleado> listaEmpleados = objCE.getAll();
                Gson objGson = new Gson();
                out = objGson.toJson(listaEmpleados);

            } else {
                out = """
                {"error":"Hubo un error en la autenticación"}
                """;
            }
        } catch (SQLException ex) {
            out = """
                {"error":"Hubo un error en la autenticación"}
                """;
        }
        return Response.ok(out).build();
    }

    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("idE") @DefaultValue("") String idE, @QueryParam("token") @DefaultValue("") String token) {
        String out = "";
        try {
            if (CA.authToken(token)) {
                ControllerEmpleado objCE = new ControllerEmpleado();
                objCE.delete(Integer.parseInt(idE));
                out = """
                {"result":"Empleado eliminado exitosamente"}
                """;
            }
        } catch (SQLException ex) {
            out = """
                {"error":"Hubo un error en la eliminacion"}
                """;
        }
        return Response.ok(out).build();
    }

    @Path("reactivar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response reactivar(@QueryParam("idE") @DefaultValue("") String idE, @QueryParam("token") @DefaultValue("") String token) {
        String out = "";
        try {
            if (CA.authToken(token)) {
                ControllerEmpleado objCE = new ControllerEmpleado();
                objCE.reactivar(Integer.parseInt(idE));
                out = """
                {"result":"Empleado reactivado exitosamente"}
                """;
            }
        } catch (SQLException ex) {
            out = """
                {"error":"Hubo un error en la reactivacion"}
                """;
        }
        return Response.ok(out).build();
    }

    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("e") @DefaultValue("") String empleado, @QueryParam("token") @DefaultValue("") String token) {
        Gson objGson = new Gson();
        //System.out.println("Impreso"+empleado);
        Empleado e = objGson.fromJson(empleado, Empleado.class);
        String out = "";

        ControllerEmpleado objCE = new ControllerEmpleado();
        try {
            if (CA.authToken(token)) {
                int idEmpleadoGenerado = objCE.insert(e);
                out = """
                 {"result":"Empleado insertado exitosamente con id %s" }
               
                """;
                out = String.format(out, idEmpleadoGenerado);
            }
        } catch (SQLException ex) {
            System.out.println(e);
            ex.printStackTrace();
            out = """
                 {"error":"Error al insertar empleado" }
               
                """;
        }
        return Response.ok(out).build();
    }

    @Path("modificar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificar(@FormParam("e") @DefaultValue("") String empleado, @QueryParam("token") @DefaultValue("") String token) {
        Gson objGson = new Gson();
        Empleado e = objGson.fromJson(empleado, Empleado.class);

        String out = "";

        ControllerEmpleado objCE = new ControllerEmpleado();
        try {
            if (CA.authToken(token)) {
                int idEmpleadoModificado = objCE.modificar(e);
                out = """
                 {"result":"Empleado modificado exitosamente con id %s" }
               
                """;
                out = String.format(out, idEmpleadoModificado);
            }
        } catch (SQLException ex) {
            System.out.println(e);
            ex.printStackTrace();
            out = """
                 {"error":"Error al modificar empleado" }
               
                """;
        }
        return Response.ok(out).build();
    }


}
