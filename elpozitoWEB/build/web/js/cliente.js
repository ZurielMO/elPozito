let clientes;

function getCliente() {
    fetch("http://localhost:8080/elpozitoWEB/api/cliente/getAllCliente" + "?token=" + localStorage.getItem("token"))
        .then(response => response.json())
        .then(response => {
            clientes = response;
            let datos = "";
            let i = 0;
            clientes.forEach((cliente) => {
                let dato1 = cliente.email + "<br>"
                        + cliente.fechaRegistro;

                let dato2 = cliente.persona.nombre + "<br>"
                        + cliente.persona.apellidoPaterno + "<br>"
                        + cliente.persona.apellidoMaterno;

                let dato3 = cliente.persona.genero + "<br>"
                        + cliente.persona.fechaNacimiento + "<br>"
                        + cliente.persona.rfc + "<br>"
                        + cliente.persona.curp;

                let dato4 = cliente.persona.domicilio + "<br>"
                        + cliente.persona.codigoPostal + "<br>"
                        + cliente.persona.ciudad + "<br>"
                        + cliente.persona.estado + "<br>"
                        + cliente.persona.telefono;

                datos += "<tr>";
                datos += "<td>" + cliente.idCliente + "</td>";
                datos += "<td>" + dato1 + "</td>";
                datos += "<td>" + dato2 + "</td>";
                datos += "<td>" + dato3 + "</td>";
                datos += "<td>" + dato4 + "</td>";
//                datos += "<td>" + cliente.estatus + "</td>";
                      if (cliente.estatus === 1) {
                      datos += "<td>Activo</td>";
                     } else {
                     datos += "<td>Inactivo</td>";
                     }
                // Botón para editar siempre visible
                datos += "<td><button type='button' onclick=\"mostrarFormularioYModificar(" + i + ", 'formulario2')\" class='btn edit-btn'><i class='fa-solid fa-file-pen fa-2xl' style='color: #0D6EFD;'></i><br><span class='btn-text' style='color: #0D6EFD; font-size: 12px'>Editar</span></button>";

                // Botón para eliminar visible solo si el cliente está activo
                if (cliente.estatus === 1) {
                    datos += "<button type='button' class='btn delete-btn' onclick='eliminarCliente(" + cliente.idCliente + ")'><i class='fa-solid fa-trash fa-2xl' style='color: #DC3646;'></i><br><span class='btn-text' style='color: #DC3646; font-size: 12px'>Borrar</span> </button>";
                }

                // Botón para reactivar visible solo si el cliente está inactivo
                if (cliente.estatus === 0) {
                    datos += "<button type='button' class='btn delete-btn' onclick='reactivarCliente(" + cliente.idCliente + ")'><i class='fa-solid fa-circle-check fa-2xl' style='color: green;'></i><br><span class='btn-text' style='color: green; font-size: 12px'>Reactivar</span></button>";
                }

                datos += "</td></tr>";
                i++;
            });
            document.getElementById("tbCliente").innerHTML = datos;
        });
}


function mostrarFormularioYModificar(i, formularioId) {
    cerrarFormulario('formulario1'); // Cerrar formulario 1 antes de abrir el formulario 2
    mostrarFormulario(formularioId);
    seleccionarCliente(i);
}
function mostrarFormulario(id) {
    var formulario = document.getElementById(id);
    formulario.style.display = "block";
}

function cerrarFormulario(id) {
    var formulario = document.getElementById(id);
    formulario.style.display = "none";
}


function eliminarCliente(cliente) {
    let idCliente = cliente;
    fetch("http://localhost:8080/elpozitoWEB/api/cliente/delete?idC=" +idCliente+"&token="+localStorage.getItem("token"))
        .then(response => response.json())
        .then(response => {
            if (response.result === "success") {
//                Swal.fire("El cliente no se ha podido eliminar de manera exitosa", "", "error");
        Swal.fire("Error al eliminar", response.result, "error");
            } else {
                Swal.fire("Cliente Eliminado", response.result, "success");
//                Swal.fire("Cliente Elimi", "", "success");
                 getCliente();
            }
        });
}

function reactivarCliente(cliente) {
    let idCliente = cliente;
    fetch("http://localhost:8080/elpozitoWEB/api/cliente/reactivar?idC=" +idCliente+"&token="+localStorage.getItem("token"))
           .then(response => response.json())
        .then(response => {
            if (response.result === "success") {
//                Swal.fire("Error al reactivar el cliente", "", "error");
                Swal.fire("Error al reactivar al cliente", response.result, "error");
            } else {
                
                Swal.fire("Cliente reactivado", response.result, "success");
                 getCliente();
            }
        });
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
        "txtEmail1": "Email"
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
        "txtEmail2": "Email"
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

function insetarCliente() {
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
    let email=document.getElementById("txtEmail1").value; //Muajaja
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
    let cliente = {
        "email":email,
        "persona": persona
    };
    // Realizar la solicitud POST al servidor con el objeto empleado
    let params = { c: JSON.stringify(cliente) };
    let ruta = "http://localhost:8080/elpozitoWEB/api/cliente/insert?"+"&token="+localStorage.getItem("token");
    fetch(ruta, 
    {
        method: "POST",
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: new URLSearchParams(params)
    })
   .then(response => response.json())
            .then(response => {
                if(response.result)
                  Swal.fire("Cliente Agregado", response.result,"success");
                getCliente();
//                borrarFormulario();
                if(response.error)
                  Swal.fire("Problemas al Agregar ",response.error, "error");
            
    });
}

function seleccionarCliente(i) {
    // Datos persona
    document.getElementById("txtIdPersona").value = clientes[i].persona.idPersona;
    document.getElementById("txtNombre2").value = clientes[i].persona.nombre;
    document.getElementById("txtApellidoPaterno2").value = clientes[i].persona.apellidoPaterno;
    document.getElementById("txtApellidoMaterno2").value = clientes[i].persona.apellidoMaterno;
    document.getElementById("cmbGenero2").value = clientes[i].persona.genero;
    document.getElementById("txtFechaNacimiento2").value = clientes[i].persona.fechaNacimiento;
    document.getElementById("txtRFC2").value = clientes[i].persona.rfc;
    document.getElementById("txtCURP2").value = clientes[i].persona.curp;
    document.getElementById("txtDomicilio2").value = clientes[i].persona.domicilio;
    document.getElementById("txtCodigoPostal2").value = clientes[i].persona.codigoPostal;
    document.getElementById("txtCiudad2").value = clientes[i].persona.ciudad;
    document.getElementById("cbmEstado2").value = clientes[i].persona.estado;
    document.getElementById("txtTelefono2").value = clientes[i].persona.telefono;
    document.getElementById("imgFoto").src = clientes[i].persona.foto;
    // Datos empleado
    document.getElementById("txtIdCliente2").value= clientes[i].idCliente;
    document.getElementById("txtEmail2").value = clientes[i].email;
    
   
   
}

function modificarCliente() {
    if (!validarCampos2()) {
        return; // Detener la ejecución si los campos no están llenos
    }
    // Obtener los valores ingresados por el usuario en el formulario
    let iP=document.getElementById("txtIdPersona").value;
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
     let persona={
        idPersona:iP,
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
    let iC= document.getElementById("txtIdCliente2").value;
    let email=document.getElementById("txtEmail2").value;
    let cliente = {
        idCliente:iC,
        email:email,
        persona: persona
    };
    
    

    // Realizar la solicitud POST al servidor con el objeto empleado
    let params = { c: JSON.stringify(cliente) };
    let ruta = "http://localhost:8080/elpozitoWEB/api/cliente/modificar?"+"&token="+localStorage.getItem("token");
    fetch(ruta, 
    {
        method: "POST",
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: new URLSearchParams(params)
    })
    .then(response => response.json())
            .then(response => {
                if(response.result)
                  Swal.fire("Modificación Correcta", response.result,"success");
                getCliente()();
//                borrarFormulario();
                if(response.error)
                  Swal.fire("Problemas al Modificar ",response.error, "error");
             
    });
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



function leerContenidoCliente(){
    var file = document.getElementById("inputFileImagen");
    cargarFotografiaCliente(file);
}
function leerFotoCliente()
{
    let inputFile = document.getElementById("inputFileImagen").click();
}
function cargarFotografiaCliente(objetoInputFile)
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







function leerContenidoCliente2(){
    var file = document.getElementById("inputFileImagen2");
    cargarFotografiaCliente2(file);
}
function leerFotoCliente2()
{
    let inputFile = document.getElementById("inputFileImagen2").click();
}
function cargarFotografiaCliente2(objetoInputFile)
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


//Aqui esta lo que agregué de la busqueda Maico
function filterTable(searchTerm) {
    const tableRows = document.querySelectorAll('#tbCliente tr');

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
getCliente();
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
    // Datos cliente
    document.getElementById("txtIdCliente2").value= "";
    document.getElementById("txtEmail2").value =""; 
}