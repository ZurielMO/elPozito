function ingresar(){
    let usu = document.getElementById("txtUsuario").value;
    let con = document.getElementById("txtContrasenia").value;
    usu=normalizar(usu);
    usu=sanitizar(usu);
    con=normalizar(con);
    con=sanitizar(con);

let usuario={nombreUsuario:usu, contrasenia:con};
let parametro ={usuario: JSON.stringify(usuario)};
//alert(JSON.stringify(parametro));
fetch ("http://localhost:8080/elpozitoWEB/api/acceso/login", 
{method:"POST",
    headers:{'Content-Type':
                'application/x-www-form-urlencoded;charset=UTF-8'},
    body:new URLSearchParams(parametro)
}
).then(response => response.json())
.then(response => {
//    alert(JSON.stringify(response));
    if(response.usuario.idUsuario>0 && response.usuario.idUsuario!=null){
        //alert("Acceso Concedido");

Swal.fire("Acceso Concedido", response.result,"success");
localStorage.setItem("token",response.usuario.token);
localStorage.setItem("empleado",JSON.stringify(response));
window.location.href= "http://localhost:8080/elpozitoWEB/html/menuPrincipal.html";
    }else if (response.idUsuario==0){
        //alert("Acceso Denegado");
        Swal.fire("Acceso Denegado ",response.error, "error");
    }else {
        alert(response.error);
    }
});
}

function normalizar(texto){
     for (let i = 0; i<texto.length; i++){
    texto = texto.replace("á","a");
    texto = texto.replace("é","e");
    texto = texto.replace("í","i");
    texto = texto.replace("ó","o");
    texto = texto.replace("ú","u");
    texto = texto.replace("ü","u");
    texto = texto.replace("ñ","n");
 
    texto = texto.replace("Á","A");
    texto = texto.replace("É","E");
    texto = texto.replace("Í","I");
    texto = texto.replace("Ó","O");
    texto = texto.replace("Ú","U");
    texto = texto.replace("Ü","U");
    texto = texto.replace("Ñ","N");
     }return texto;
}

function sanitizar(texto){
    for (let i = 0; i<texto.length; i++){
    texto = texto.replace("(","");
    texto = texto.replace(")","");
    texto = texto.replace(",","");
    texto = texto.replace(".","");
    texto = texto.replace(";","");
    texto = texto.replace(":","");
    texto = texto.replace("*","");
    texto = texto.replace("|","");
    texto = texto.replace("&","");
    texto = texto.replace("\"","");
    }return texto;
}

function salir(){
    let parametro = {t:localStorage.getItem("token")};
    let ruta = "http://localhost:8080/elpozitoWEB/api/acceso/logout";
    fetch (ruta, 
{method:"POST",
    headers:{'Content-Type':
                'application/x-www-form-urlencoded;charset=UTF-8'},
    body:new URLSearchParams(parametro)
}
).then(response => response.json())
.then(response =>{
    if(response.result){
    window.location.href="http://localhost:8080/elpozitoWEB/";
    localStorage.removeItem("token");
    localStorage.removeItem("empleado");
}
else if (response.error){
    alert(response.error);
}
}
);
}


