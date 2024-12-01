package org.codeagle.elpozito.REST;

import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import org.codeagle.elpozito.controller.ControllerAcceso;
import org.codeagle.elpozito.model.Empleado;
import org.codeagle.elpozito.model.Persona;
import org.codeagle.elpozito.model.Usuario;



@Path("acceso")
public class RESTAcceso {

    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response logIn(@FormParam("usuario") @DefaultValue("") String usuario) {
        Gson objGS = new Gson();
        Usuario u = objGS.fromJson(usuario, Usuario.class);
        Empleado e = new Empleado();
        Persona p = new Persona();
        u.encode();
        String out = "";
        ControllerAcceso objCA = new ControllerAcceso();
        try {
            objCA.login(u);
            if (u.getIdUsuario() > 0) {
                u.colocarToken();
                objCA.saveToken(u);
                e.setPersona(p);
                e.setUsuario(u);
                objCA.indetificarEmp(u, e);
            }
            out = objGS.toJson(e);
        } catch (Exception ex) {
            out = """
    {"error":"%s"}
    """;
            String.format(out, ex.getMessage());
        }
        return Response.ok(out).build();
    }

    @Path("logout")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response logOut(@FormParam("t") @DefaultValue("") String t) {
        String out = "";
        System.out.println(t);
        ControllerAcceso objCA = new ControllerAcceso();
        try {
            objCA.deleteToken(t);
            out = """
    {"result":"Ok"}
    """;
        } catch (SQLException ex) {
            out = """
    {"error":"ERROR al cerrar sesión, consulta al administrador del sistema"}
    """;
        }
        return Response.ok(out).build();
    }

}
