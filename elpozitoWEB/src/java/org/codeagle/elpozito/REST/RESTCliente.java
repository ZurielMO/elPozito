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
import org.codeagle.elpozito.controller.ControllerCliente;
import org.codeagle.elpozito.model.Cliente;

/**
 *
 * @author maico
 */

@Path("cliente")
public class RESTCliente {
  ControllerAcceso CA = new ControllerAcceso();
    @Path("getAllCliente")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCliente(@QueryParam("token") @DefaultValue("") String token) {
        String out = "";
        try {
            if (CA.authToken(token)) {
                ControllerCliente objCC = new ControllerCliente();

                List<Cliente> listaClientes = objCC.getAllCliente();
                Gson objGson = new Gson();
                out = objGson.toJson(listaClientes);

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
    public Response delete(@QueryParam("idC") @DefaultValue("") String idE, @QueryParam("token") @DefaultValue("") String token) {
        String out = "";
        try {
            if (CA.authToken(token)) {
                ControllerCliente objCC = new ControllerCliente();
                objCC.delete(Integer.parseInt(idE));
                out = """
                {"result":"Cliente eliminado exitosamente"}
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
    public Response reactivar(@QueryParam("idC") @DefaultValue("") String idE, @QueryParam("token") @DefaultValue("") String token) {
        String out = "";
        try {
            if (CA.authToken(token)) {
                ControllerCliente objCC = new ControllerCliente();
                objCC.reactivar(Integer.parseInt(idE));
                out = """
                {"result":"Cliente reactivado exitosamente"}
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
    public Response insert(@FormParam("c") @DefaultValue("") String cliente, @QueryParam("token") @DefaultValue("") String token) {
        Gson objGson = new Gson();
        //System.out.println("Impreso"+empleado);
        Cliente c = objGson.fromJson(cliente, Cliente.class);

        String out = "";

        ControllerCliente objCC = new ControllerCliente();
        try {
            if (CA.authToken(token)) {
                int idClienteGenerado = objCC.insert(c);
                out = """
                 {"result":"Cliente insertado exitosamente con id %s" }
               
                """;
                out = String.format(out, idClienteGenerado);
            }
        } catch (SQLException ex) {
            System.out.println(c);
            ex.printStackTrace();
            out = """
                 {"error":"Error al insertar el cliente" }
               
                """;
        }
        return Response.ok(out).build();
    }

    @Path("modificar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificar(@FormParam("c") @DefaultValue("") String cliente, @QueryParam("token") @DefaultValue("") String token) {
        Gson objGson = new Gson();
        Cliente c = objGson.fromJson(cliente, Cliente.class);

        String out = "";

        ControllerCliente objCC = new ControllerCliente();
        try {
            if (CA.authToken(token)) {
                int idClienteModificado = objCC.modificar(c);
                out = """
                 {"result":"Cliente modificado exitosamente con id %s" }
               
                """;
                out = String.format(out, idClienteModificado);
            }
        } catch (SQLException ex) {
            System.out.println(c);
            ex.printStackTrace();
            out = """
                 {"error":"Error al modificar cliente" }
               
                """;
        }
        return Response.ok(out).build();
    }

@POST
    @Path("busquedaC")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBusquedaC(@FormParam("busqueda") @DefaultValue("") String busqueda, @FormParam("token") @DefaultValue("") String token) throws SQLException {
        ControllerCliente clienteController = new ControllerCliente();
        ControllerAcceso lc = new ControllerAcceso();
        String out = "";
     
        System.out.println(busqueda);
        try {
            if (lc.authToken(token)) {
                ArrayList<Cliente> clientes = clienteController.search(busqueda);
                Gson objGS = new Gson();
                objGS.toJson(clientes);
                out = objGS.toJson(clientes);
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
    
    
}

