let empleados;

function getAllEmpleado(){
    
   fetch("http://localhost:8080/elpozitoWEB/api/empleado/getAllEmpleado"+"?token="+localStorage.getItem("token"))
            .then(response => response.json())
            .then(response => {
                empleados=response;
                let datos="";
                let i = 0;
                empleados.forEach((empleado)=>{
                     datos+="<tr>";
                     datos+="<td>"+empleado.idEmpleado+"</td>";
                     datos+="<td>"+empleado.codigo+"</td>";
                     datos+="<td>"+empleado.persona.nombre+"</td>";
                     datos+="<td>"+empleado.persona.apellidoPaterno+"</td>";
                     datos+="<td>"+empleado.persona.apellidoMaterno+"</td>";
                      if (empleado.activo === 1) {
                      datos += "<td>Activo</td>";
                     } else {
                     datos += "<td>Inactivo</td>";
                     }

                // Botón para editar siempre visible
                datos += "<td><button type='button' onclick=\"mostrarFormularioYModificar(" + i + ", 'formulario2')\" class='btn edit-btn'><i class='fa-solid fa-file-pen fa-2xl' style='color: #0D6EFD;'></i><br><span class='btn-text' style='color: #0D6EFD; font-size: 12px'>Editar</span></button>";

                // Botón para eliminar visible solo si el empleado está activo
                if (empleado.activo) {
                    datos += "<button type='button' class='btn delete-btn' onclick='eliminarEmpleado(" + empleado.idEmpleado + ")'><i class='fa-solid fa-trash fa-2xl' style='color: #DC3646;'></i><br><span class='btn-text' style='color: #DC3646; font-size: 12px'>Borrar</span> </button>";
                }

                // Botón para reactivar visible solo si el empleado está inactivo
                if (!empleado.activo) {
                    datos += "<button type='button' class='btn delete-btn' onclick='reactivarEmpleado(" + empleado.idEmpleado + ")'><i class='fa-solid fa-circle-check fa-2xl' style='color: green;'></i><br><span class='btn-text' style='color: green; font-size: 12px'>Reactivar</span></button>";
                }

                datos += "</td></tr>";
                     i++;
                });
                document.getElementById("tbEmpleados").innerHTML=datos;
                
    });
}

function mostrarFormularioYModificar(i, formularioId) {
    cerrarFormulario('formulario1'); // Cerrar formulario 1 antes de abrir el formulario 2
    mostrarFormulario(formularioId);
    modificarEmpleado(i);
}

function mostrarFormulario(id) {
    cerrarFormulario('formulario2'); // Cerrar formulario 2 antes de abrir cualquier otro formulario
    var formulario = document.getElementById(id);
    formulario.style.display = "block";
}
function cerrarFormulario(id) {
    var formulario = document.getElementById(id);
    formulario.style.display = "none";
}

function eliminarEmpleado(empleado) {
    let idEmpleado = empleado;

    fetch("http://localhost:8080/elpozitoWEB/api/empleado/delete?idE=" +idEmpleado+"&token="+localStorage.getItem("token"))
        .then(response => response.json())
        .then(response => {
            if (response.result === "success") {
//                Swal.fire("El empleado no se ha podido eliminar de manera exitosa :(", "", "error");
        Swal.fire("Error al eliminar el empleado", response.result, "Error");
            } else {
                
                Swal.fire("Empleado eliminado", response.result, "success");
                 getAllEmpleado();
            }
        });
}

function modificarEmpleado(i){
    document.getElementById("txtIdPersona").value = empleados[i].persona.idPersona;
    document.getElementById("txtNombre2").value = empleados[i].persona.nombre;
    document.getElementById("txtApellidoPaterno2").value = empleados[i].persona.apellidoPaterno;
    document.getElementById("txtApellidoMaterno2").value = empleados[i].persona.apellidoMaterno;
    document.getElementById("cmbGenero2").value = empleados[i].persona.genero;
    document.getElementById("txtFechaNacimiento2").value = empleados[i].persona.fechaNacimiento;
    document.getElementById("txtRFC2").value = empleados[i].persona.rfc;
    document.getElementById("txtCURP2").value = empleados[i].persona.curp;
    document.getElementById("txtDomicilio2").value = empleados[i].persona.domicilio;
    document.getElementById("txtCodigoPostal2").value = empleados[i].persona.codigoPostal;
    document.getElementById("txtCiudad2").value = empleados[i].persona.ciudad;
    document.getElementById("cbmEstado2").value = empleados[i].persona.estado;
    document.getElementById("txtTelefono2").value = empleados[i].persona.telefono;
    
    //Datos de empleado
    document.getElementById("txtIdEmpleado2").value = empleados[i].idEmpleado;
    document.getElementById("txtCodigoEmpleado2").value = empleados[i].codigo;
    document.getElementById("txtFechaIngreso2").value = empleados[i].fechaIngreso;
    document.getElementById("txtPuesto2").value = empleados[i].puesto;
    document.getElementById("txtSalarioBruto2").value = empleados[i].salarioBruto;
    document.getElementById("cmbEstatus2").value = empleados[i].activo;
     document.getElementById("txtEmail2").value =empleados[i].email;
    
     document.getElementById("txtIdUsuario").value = empleados[i].usuario.idUsuario;
    document.getElementById("txtNombreUsuario2").value = empleados[i].usuario.nombreUsuario;
    document.getElementById("txtContrasenia2").value = empleados[i].usuario.contrasenia;
    document.getElementById("cmbRol2").value = empleados[i].usuario.rol;
    document.getElementById("imgFoto").src = empleados[i].persona.foto;
}

function leerContenido(){
    var file = document.getElementById("inputFileImagen");
    cargarFotografia(file);
}
function leerFoto()
{
    let inputFile = document.getElementById("inputFileImagen").click();
}
function cargarFotografia(objetoInputFile)
{
    console.log(objetoInputFile);
    //Revisamos que el usuario haya seleccionado un archivo:
    if (objetoInputFile.files && objetoInputFile.files[0])
    {
        let reader = new FileReader();
        //Agregamos un oyente al lector del archivo para que,
        //en cuanto el usuario cargue una imagen, esta se lea
        //y se convierta de forma automatica en una cadena de Base64:
        reader.onload = function (e)
        {
            let fotoB64 = e.target.result;
            document.getElementById("imgFoto").src = fotoB64;
            document.getElementById("txtaCodigoImagen").value = fotoB64;
        };
        //Leemos el archivo que selecciono el usuario y lo
        //convertimos en una cadena con la Base64:
        reader.readAsDataURL(objetoInputFile.files[0]);
        }
}







function leerContenido2(){
    var file = document.getElementById("inputFileImagen2");
    cargarFotografia2(file);
}
function leerFoto2()
{
    let inputFile = document.getElementById("inputFileImagen2").click();
}
function cargarFotografia2(objetoInputFile)
{
    console.log(objetoInputFile);
    //Revisamos que el usuario haya seleccionado un archivo:
    if (objetoInputFile.files && objetoInputFile.files[0])
    {
        let reader = new FileReader();
        //Agregamos un oyente al lector del archivo para que,
        //en cuanto el usuario cargue una imagen, esta se lea
        //y se convierta de forma automatica en una cadena de Base64:
        reader.onload = function (e)
        {
            let fotoB64 = e.target.result;
            document.getElementById("imgFoto2").src = fotoB64;
            document.getElementById("txtaCodigoImagen2").value = fotoB64;
        };
        //Leemos el archivo que selecciono el usuario y lo
        //convertimos en una cadena con la Base64:
        reader.readAsDataURL(objetoInputFile.files[0]);
        }
}

function validarCampos1() {
    // Objeto que mapea los IDs de los elementos a los nombres que deseas mostrar
    let camposNombres = {
        "txtNombre1": "Nombre",
        "txtApellidoPaterno1": "Apellido Paterno",
        "txtApellidoMaterno1": "Apellido Materno",
        "cmbGenero1": "Género",
        "txtFechaNacimiento1": "Fecha de Nacimiento",
        "txtTelefono1": "Teléfono",
        "txtNombreUsuario1": "Nombre de Usuario",
        "txtContrasenia1": "Contraseña",
        "txtEmail1": "Email",
        "txtSalarioBruto1": "Salario Bruto",
        "cmbRol1": "Rol",
        "txtPuesto1": "Puesto"
    };

    // Array que contiene los IDs de los elementos que quieres verificar
    let campos = Object.keys(camposNombres);

    // Iterar sobre cada ID y verificar si su valor es null
    for (let i = 0; i < campos.length; i++) {
        let elemento = document.getElementById(campos[i]);
        if (!elemento || elemento.value.trim() === "") {
            // Mostrar un mensaje de alerta con el nombre del campo
            Swal.fire("Error", `El campo '${camposNombres[campos[i]]}' no puede estar vacío`, "error");
            return false; // Retorna false para indicar que la validación ha fallado
        }
    }
    return true; // Retorna true si todos los campos están llenos
}
function validarCampos2() {
    // Objeto que mapea los IDs de los elementos a los nombres que deseas mostrar
    let camposNombres = {
        "txtNombre2": "Nombre",
        "txtApellidoPaterno2": "Apellido Paterno",
        "txtApellidoMaterno2": "Apellido Materno",
        "cmbGenero2": "Género",
        "txtFechaNacimiento2": "Fecha de Nacimiento",
        "txtTelefono2": "Teléfono",
        "txtNombreUsuario2": "Nombre de Usuario",
        "txtContrasenia2": "Contraseña",
        "txtEmail2": "Email",
        "txtSalarioBruto2": "Salario Bruto",
        "cmbRol2": "Rol",
        "txtPuesto2": "Puesto"
    };

    // Array que contiene los IDs de los elementos que quieres verificar
    let campos = Object.keys(camposNombres);

    // Iterar sobre cada ID y verificar si su valor es null
    for (let i = 0; i < campos.length; i++) {
        let elemento = document.getElementById(campos[i]);
        if (!elemento || elemento.value.trim() === "") {
            // Mostrar un mensaje de alerta con el nombre del campo
            Swal.fire("Error", `El campo '${camposNombres[campos[i]]}' no puede estar vacío`, "error");
            return false; // Retorna false para indicar que la validación ha fallado
        }
    }
    return true; // Retorna true si todos los campos están llenos
}

function insertEmpleado(){
     if (!validarCampos1()) {
        return; // Detener la ejecución si los campos no están llenos
    }
    let ci=document.getElementById("imgFoto2").src; //Muajaja
    let nom=document.getElementById("txtNombre1").value;//Muajaja
    let ap=document.getElementById("txtApellidoPaterno1").value; //Muajaja
    let am=document.getElementById("txtApellidoMaterno1").value; //Muajaja
    let gen=document.getElementById("cmbGenero1").value; //Muajaja
    let fn=document.getElementById("txtFechaNacimiento1").value; //Muajaja
    let rfc=document.getElementById("txtRFC1").value; //Muajaja
    let curp=document.getElementById("txtCURP1").value; //Muajaja
    let dom=document.getElementById("txtDomicilio1").value;//Muajaja
    let cp=document.getElementById("txtCodigoPostal1").value;//Muajaja
    let ciu=document.getElementById("txtCiudad1").value; //Muajaja
    let edo=document.getElementById("cbmEstado1").value; //Muajaja
    let tel=document.getElementById("txtTelefono1").value; //Muajaja
    
    let nu=document.getElementById("txtNombreUsuario1").value;
    let cst=document.getElementById("txtContrasenia1").value;
    let email=document.getElementById("txtEmail1").value;
    let sb=document.getElementById("txtSalarioBruto1").value;
    
    nom=normalizar(nom);
        nom=sanitizar(nom);
        ap=normalizar(ap);
        ap=sanitizar(ap);
        am=normalizar(am);
        am=sanitizar(am);
        gen=normalizar(gen);
        gen=sanitizar(gen);
        fn=normalizar(fn);
        fn=sanitizar(fn);
        rfc=normalizar(rfc);
        rfc=sanitizar(rfc);
        curp=normalizar(curp);
        curp=sanitizar(curp);
        dom=normalizar(dom);
        dom=sanitizar(dom);
        cp=normalizar(cp);
        cp=sanitizar(cp);
        ciu=normalizar(ciu);
        ciu=sanitizar(ciu);
        edo=normalizar(edo);
        edo=sanitizar(edo);
        tel=normalizar(tel);
        tel=sanitizar(tel);

        nu=normalizar(nu);
        nu=sanitizar(nu);
        cst=normalizar(cst);
        cst=sanitizar(cst);
        sb=normalizar(sb);
        sb=sanitizar(sb);
    
    //idPersona:idP,
    let persona={
        nombre:nom,
        apellidoPaterno:ap,
        apellidoMaterno:am,
        genero:gen,
        fechaNacimiento:fn,
        rfc:rfc,
        curp:curp,
        domicilio:dom,
        codigoPostal:cp,
        ciudad:ciu,
        estado:edo,
        telefono:tel,
        foto:ci
    };

    let usuario={
        
    nombreUsuario:nu,
    contrasenia:cst,
    //falta ponerlo en el index
    rol:document.getElementById("cmbRol1").value//Muajaja
    };
   

    
    let empleado = {
        email:email,
        puesto:document.getElementById("txtPuesto1").value, //Muajaja
        salarioBruto:sb,
        persona:persona,
        usuario:usuario,
    };
   // alert(JSON.stringify(empleado));

    let params = {e: JSON.stringify(empleado)};
    
    let ruta = "http://localhost:8080/elpozitoWEB/api/empleado/insert?"+"&token="+localStorage.getItem("token");
    ;
    
    fetch(ruta,
    {
        method:"POST",
        headers:{'Content-Type':
                    'application/x-www-form-urlencoded;charset=UTF-8'},
        body: new URLSearchParams(params)
    })
            .then(response => response.json())
            .then(response => {
                if(response.result)
                  Swal.fire("Empleado agregado", response.result,"success");
                getAllEmpleado();
//                borrarFormulario();
                if(response.error)
                  Swal.fire("Problemas al agregar el empleado",response.error, "error");
            
    });
}

function guardarCambios(){
    if (!validarCampos2()) {
        return; // Detener la ejecución si los campos no están llenos
    }
    let idP=document.getElementById("txtIdPersona").value; //Muajaja
    let ci=document.getElementById("imgFoto").src; //Muajaja
    let nom=document.getElementById("txtNombre2").value;//Muajaja
    let ap=document.getElementById("txtApellidoPaterno2").value; //Muajaja
    let am=document.getElementById("txtApellidoMaterno2").value; //Muajaja
    let gen=document.getElementById("cmbGenero2").value; //Muajaja
    let fn=document.getElementById("txtFechaNacimiento2").value; //Muajaja
    let rfc=document.getElementById("txtRFC2").value; //Muajaja
    let curp=document.getElementById("txtCURP2").value; //Muajaja
    let dom=document.getElementById("txtDomicilio2").value;//Muajaja
    let cp=document.getElementById("txtCodigoPostal2").value;//Muajaja
    let ciu=document.getElementById("txtCiudad2").value; //Muajaja
    let edo=document.getElementById("cbmEstado2").value; //Muajaja
    let tel=document.getElementById("txtTelefono2").value; //Muajaja
    
   let nu=document.getElementById("txtNombreUsuario2").value;
    let cst=document.getElementById("txtContrasenia2").value;
    let sb=document.getElementById("txtSalarioBruto2").value;
    

        nom=normalizar(nom);
        nom=sanitizar(nom);
        ap=normalizar(ap);
        ap=sanitizar(ap);
        am=normalizar(am);
        am=sanitizar(am);
        gen=normalizar(gen);
        gen=sanitizar(gen);
        fn=normalizar(fn);
        fn=sanitizar(fn);
        rfc=normalizar(rfc);
        rfc=sanitizar(rfc);
        curp=normalizar(curp);
        curp=sanitizar(curp);
        dom=normalizar(dom);
        dom=sanitizar(dom);
        cp=normalizar(cp);
        cp=sanitizar(cp);
        ciu=normalizar(ciu);
        ciu=sanitizar(ciu);
        edo=normalizar(edo);
        edo=sanitizar(edo);
        tel=normalizar(tel);
        tel=sanitizar(tel);
        
        nu=normalizar(nu);
        nu=sanitizar(nu);
        cst=normalizar(cst);
        cst=sanitizar(cst);
        sb=normalizar(sb);
        sb=sanitizar(sb);
    
    //idPersona:idP,
    let persona={idPersona:idP,
        nombre:nom,
        apellidoPaterno:ap,
        apellidoMaterno:am,
        genero:gen,
        fechaNacimiento:fn,
        rfc:rfc,
        curp:curp,
        domicilio:dom,
        codigoPostal:cp,
        ciudad:ciu,
        estado:edo,
        telefono:tel,
        foto:ci
    };   
    let usuario={
    idUsuario:document.getElementById("txtIdUsuario").value, //Muajaja
    nombreUsuario:nu,
    contrasenia:cst,
    rol:document.getElementById("cmbRol2").value//Muajaja
    };
   
     
    let empleado = {
        idEmpleado:document.getElementById("txtIdEmpleado2").value, //Muajaja
        codigo:document.getElementById("txtCodigoEmpleado2").value,//Muajaja
        email:document.getElementById("txtEmail2").value,//Muajaja
        fechaIngreso:document.getElementById("txtFechaIngreso2").value, //Muajaja
        puesto:document.getElementById("txtPuesto2").value, //Muajaja
        salarioBruto:sb,
        activo:document.getElementById("cmbEstatus2").value, //Muajaja
        persona:persona,
        usuario:usuario,
    };
    let params = {e: JSON.stringify(empleado)};
    
    let ruta = "http://localhost:8080/elpozitoWEB/api/empleado/modificar?"+"&token="+localStorage.getItem("token");
    
    fetch(ruta,
    {
        method:"POST",
        headers:{'Content-Type':
                    'application/x-www-form-urlencoded;charset=UTF-8'},
        body: new URLSearchParams(params)
    })
            .then(response => response.json())
            .then(response => {
                if(response.result)
                  Swal.fire("Modificación Correcta", response.result,"success");
               getAllEmpleado();
//                borrarFormulario();
                if(response.error)
                  Swal.fire("Problemas al Modificar ",response.error, "error");
             
    });

}



function reactivarEmpleado(empleado) {
    let idEmpleado = empleado;

    fetch("http://localhost:8080/elpozitoWEB/api/empleado/reactivar?idE="+idEmpleado+"&token="+localStorage.getItem("token"))
        .then(response => response.json())
        .then(response => {
            if (response.result === "success") {
//                Swal.fire("El empleado no se ha podido reactivar de manera exitosa :(", "", "error");
        Swal.fire("Error al reactivar el empleado", response.result, "error");
            } else {
                
//                Swal.fire("El empleado se ha reactivado exitosamente :)", "", "success");
Swal.fire("Empleado reactivado", response.result, "success");
                    getAllEmpleado();
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




function mostrarImagen(event) {
    const input = event.target;
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const imagen = document.getElementById('imagenSeleccionada');
            imagen.src = e.target.result;
            document.getElementById('vistaPreviaImagen').style.display = 'block';
        }
        reader.readAsDataURL(input.files[0]);
    }
}

function mostrarImagen2(event) {
    const input = event.target;
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const imagen = document.getElementById('imagenSeleccionada2');
            imagen.src = e.target.result;
            document.getElementById('vistaPreviaImagen2').style.display = 'block';
        }
        reader.readAsDataURL(input.files[0]);
    }
}




//Aqui esta lo que agregué de la busqueda Maico
function filterTable(searchTerm) {
    const tableRows = document.querySelectorAll('#tbEmpleados tr');

    tableRows.forEach(row => {
        const cells = row.getElementsByTagName('td');
        let found = false;

        for (let i = 0; i < cells.length; i++) {
            const cellContent = cells[i].textContent.toLowerCase();

            if (cellContent.includes(searchTerm)) {
                found = true;
                break;
            }
        }

        if (found) {
            row.style.display = ''; // Mostrar la fila
        } else {
            row.style.display = 'none'; // Ocultar la fila
        }
    });
}
getAllEmpleado();
filterTable('');
const searchInput = document.getElementById('searchInput');
searchInput.addEventListener('input', function () {
    const searchTerm = searchInput.value.toLowerCase();
    console.log('Término de búsqueda:', searchTerm); // Agregamos un console.log para verificar si el evento se está capturando correctamente
    filterTable(searchTerm);
});


function borrarFormulario1(){
document.getElementById("imgFoto2").src = "";
document.getElementById("txtNombre1").value = "";
document.getElementById("txtApellidoPaterno1").value = "";
document.getElementById("txtApellidoMaterno1").value = "";
document.getElementById("cmbGenero1").value = "";
document.getElementById("txtFechaNacimiento1").value = "";
document.getElementById("txtRFC1").value = "";
document.getElementById("txtCURP1").value = "";
document.getElementById("txtDomicilio1").value = "";
document.getElementById("txtCodigoPostal1").value = "";
document.getElementById("txtCiudad1").value = "";
document.getElementById("cbmEstado1").value = "";
document.getElementById("txtTelefono1").value = "";
document.getElementById("txtEmail1").value = "";
document.getElementById("txtNombreUsuario1").value = "";
document.getElementById("txtContrasenia1").value = "";
document.getElementById("txtSalarioBruto1").value = "";
}
function borrarFormulario2(){
     document.getElementById("txtIdPersona").value = "";
    document.getElementById("txtNombre2").value ="";
    document.getElementById("txtApellidoPaterno2").value = "";
    document.getElementById("txtApellidoMaterno2").value = "";
    document.getElementById("cmbGenero2").value = "";
    document.getElementById("txtFechaNacimiento2").value = "";
    document.getElementById("txtRFC2").value = "";
    document.getElementById("txtCURP2").value = "";
    document.getElementById("txtDomicilio2").value = "";
    document.getElementById("txtCodigoPostal2").value = "";
    document.getElementById("txtCiudad2").value = "";
    document.getElementById("cbmEstado2").value ="";
    document.getElementById("txtTelefono2").value = "";
    document.getElementById("imgFoto").src = "";
    //Datos de empleado
    document.getElementById("txtFechaIngreso2").value = "";
    document.getElementById("txtPuesto2").value = "";
    document.getElementById("txtSalarioBruto2").value = "";
    document.getElementById("cmbEstatus2").value = "";
     document.getElementById("txtEmail2").value = "";
    
     document.getElementById("txtIdUsuario").value = "";
    document.getElementById("txtNombreUsuario2").value = "";
    document.getElementById("txtContrasenia2").value = "";
    document.getElementById("cmbRol2").value = "";
}
