let productos;

function getAllProducto(){
    fetch("http://localhost:8080/elpozitoWEB/api/producto/getAllProducto")
            .then(response => response.json())
            .then(response => {
                productos = response;
                let datos = "";
                let i = 0;
                productos.forEach((producto) => {
                    datos += "<tr>";
                    datos += "<td>" + producto.codigoProducto + "</td>";
                    datos += "<td>" + producto.nombre + "</td>";
                    datos += "<td>" + producto.precioVenta + "</td>";
//                    datos += "<td>" + producto.estatus + "</td>";
                      if (producto.estatus === 1) {
                      datos += "<td>Activo</td>";
                     } else {
                     datos += "<td>Inactivo</td>";
                     }
                    // Botón para editar siempre visible
                    datos += "<td><button type='button' onclick=\"mostrarFormularioYModificar(" + i + ", 'formulario2')\" class='btn edit-btn'><i class='fa-solid fa-file-pen fa-2xl' style='color: #0D6EFD;'></i><br><span class='btn-text' style='color: #0D6EFD; font-size: 12px'>Editar</span></button>";

                    // Botón para eliminar visible solo si el producto está activo (estatus = 1)
                    if (producto.estatus === 1) {
                        datos += "<button type='button' class='btn delete-btn' onclick='eliminarProducto("+producto.idProducto+")'><i class='fa-solid fa-trash fa-2xl' style='color: #DC3646;'></i><br><span class='btn-text' style='color: #DC3646; font-size: 12px'>Borrar</span> </button>";
                    }

                    // Botón para reactivar visible solo si el producto está eliminado (estatus = 0)
                    if (producto.estatus === 0) {
                        datos += "<button type='button' class='btn delete-btn' onclick='reactivarProducto("+producto.idProducto+")'><i class='fa-solid fa-circle-check fa-2xl' style='color: green;'></i><br><span class='btn-text' style='color: green; font-size: 12px'>Reactivar</span></button>";
                    }

                    datos += "</td></tr>";
                    i++;
                });
                document.getElementById("tbProductos").innerHTML = datos;
            });
}


function mostrarFormularioYModificar(i, formularioId) {
    cerrarFormulario('formulario1'); // Cerrar formulario 1 antes de abrir el formulario 2
    mostrarFormulario(formularioId);
    modificarProducto(i);
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


function eliminarProducto(producto) {
    let idProducto = producto;
    fetch("http://localhost:8080/elpozitoWEB/api/producto/delete?idP=" + idProducto+"&token="+localStorage.getItem("token"))
            .then(response => response.json())
            .then(response => {
                if (response.result == "success") {
                    Swal.fire("Error al eliminar", response.result, "error");
                    //alert("Empleado eliminado");Producto eliminado
                } else {
                   Swal.fire("Producto Eliminado", response.result, "success");
                    //alert("Error al eliminar");
                }
                getAllProducto();
            });
}


function reactivarProducto(producto) {
    let idProducto = producto;
    fetch("http://localhost:8080/elpozitoWEB/api/producto/reactivar?idP=" + idProducto+"&token="+localStorage.getItem("token"))
            .then(response => response.json())
            .then(response => {
                if (response.result == "success") {
                    Swal.fire(response.result, "Error al activado");
            Swal.fire("Error al eliminar el producto", response.result, "error");
                    //alert("Empleado eliminado");Producto reactivado
                } else {
//                    Swal.fire(response.error, "Producto reactivado");
                    Swal.fire("Producto reactivado", response.result, "success");
                    //alert("Error al eliminar");
                }
                getAllProducto();
            });
}

function validarCampos1() {
    // Objeto que mapea los IDs de los elementos a los nombres que deseas mostrar
    let camposNombres = {
       "txtNombre1": "Nombre",
        "txtCodigoBarras1": "Código de Barras",
        "cmbUnidad1": "Unidad de Medida",
        "txtPresentacion1": "Presentación",
        "cmbCategoria1": "Categoría",
        "txtPrecioVenta1": "Precio de Venta",
        "txtPrecioCompra1": "Precio de Compra",
        "txtCodigoProducto1": "Código de Producto"
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
        "txtCodigoBarras2": "Código de Barras",
        "cmbUnidad2": "Unidad de Medida",
        "txtPresentacion2": "Presentación",
        "cmbCategoria2": "Categoría",
        "txtPrecioVenta2": "Precio de Venta",
        "txtPrecioCompra2": "Precio de Compra",
        "txtCodigoProducto2": "Código de Producto"
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


function insertProducto() {
    if (!validarCampos1()) {
        return; // Detener la ejecución si los campos no están llenos
    }
    // Obtener los valores ingresados por el usuario en el formulario
    let NOM = document.getElementById("txtNombre1").value;
    let CB = document.getElementById("txtCodigoBarras1").value;
    let UM = document.getElementById("cmbUnidad1").value;
    let PRE = document.getElementById("txtPresentacion1").value;
    let CAT = document.getElementById("cmbCategoria1").value;
    let PV = document.getElementById("txtPrecioVenta1").value;
    let PC = document.getElementById("txtPrecioCompra1").value;
    let CP =document.getElementById("txtCodigoProducto1").value;
    
    // Obtener los valores para el objeto persona
    let producto = {
        "nombre": NOM,
        "unidadMedida": UM,
        "presentacion": PRE,
        "categoria": CAT,
        "precioCompra": PC,
        "precioVenta": PV,
        "codigoProducto": CP,
        "codigoBarras": CB
    };  
      

    // Realizar la solicitud POST al servidor con el objeto empleado
    let params = { p: JSON.stringify(producto) };
    let ruta = "http://localhost:8080/elpozitoWEB/api/producto/insert?token="+localStorage.getItem("token");

    fetch(ruta, 
    {
        method: "POST",
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: new URLSearchParams(params)
    })
    .then(response => response.json())
    .then(response => {
        if (response.result)
            Swal.fire("Inserción correcta", response.result, "success");
        if (response.error)
            Swal.fire("Problemas al insertar", response.error, "error");
                getAllProducto();
    });

}

function guardarCambiosProducto() {
    if (!validarCampos2()) {
        return; // Detener la ejecución si los campos no están llenos
    }
    // Obtener los valores ingresados por el usuario en el formulario
    let iP = document.getElementById("txtIdProducto").value;
    let NOM = document.getElementById("txtNombre2").value;
    let UM = document.getElementById("cmbUnidad2").value;
    let PRE = document.getElementById("txtPresentacion2").value;
    let CAT = document.getElementById("cmbCategoria2").value;
    let PV = document.getElementById("txtPrecioVenta2").value;
    let PC = document.getElementById("txtPrecioCompra2").value;
    let CP =document.getElementById("txtCodigoProducto2").value;
    let CB =document.getElementById("txtCodigoBarras2").value;
    // Obtener los valores para el objeto persona
    let producto = {
        "nombre": NOM,
        "unidadMedida": UM,
        "presentacion": PRE,
        "categoria": CAT,
        "precioCompra": PC,
        "precioVenta": PV,
        "codigoProducto": CP,
        "codigoBarras": CB,
        "idProducto": iP
    };  
      

    // Realizar la solicitud POST al servidor con el objeto empleado
    let params = { p: JSON.stringify(producto) };
    let ruta = "http://localhost:8080/elpozitoWEB/api/producto/update?token="+localStorage.getItem("token");

    fetch(ruta, 
    {
        method: "POST",
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: new URLSearchParams(params)
    })
    .then(response => response.json())
    .then(response => {
        if (response.result)
            Swal.fire("Producto Modificado", response.result, "success");
        if (response.error)
            Swal.fire("Problemas al modificar", response.error, "error");
                getAllProducto();    
    });  
}



function modificarProducto(i){
    document.getElementById("txtIdProducto").value = productos[i].idProducto;
    document.getElementById("txtNombre2").value = productos[i].nombre;
    document.getElementById("cmbUnidad2").value = productos[i].unidadMedida;
    document.getElementById("txtPresentacion2").value = productos[i].presentacion;
    document.getElementById("cmbCategoria2").value = productos[i].categoria;
    document.getElementById("txtPrecioVenta2").value = productos[i].precioVenta;
    document.getElementById("txtPrecioCompra2").value = productos[i].precioCompra;
    document.getElementById("txtCodigoProducto2").value = productos[i].codigoProducto;
    document.getElementById("txtCodigoBarras2").value = productos[i].codigoBarras;

    
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
    const tableRows = document.querySelectorAll('#tbProductos tr');

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
getAllProducto();
filterTable('');
const searchInput = document.getElementById('searchInput');
searchInput.addEventListener('input', function () {
    const searchTerm = searchInput.value.toLowerCase();
    console.log('Término de búsqueda:', searchTerm); // Agregamos un console.log para verificar si el evento se está capturando correctamente
    filterTable(searchTerm);
});

function borrarFormulario1(){
document.getElementById("txtNombre1").value = "";
    document.getElementById("cmbUnidad1").value = "";
    document.getElementById("txtPresentacion1").value = "";
    document.getElementById("cmbCategoria1").value = "";
    document.getElementById("txtPrecioVenta1").value = "";
    document.getElementById("txtPrecioCompra1").value = "";
    document.getElementById("txtCodigoProducto1").value = "";
    document.getElementById("txtCodigoBarras1").value = "";
}
function borrarFormulario2(){
    document.getElementById("txtIdProducto").value = "";
    document.getElementById("txtNombre2").value = "";
    document.getElementById("cmbUnidad2").value = "";
    document.getElementById("txtPresentacion2").value = "";
    document.getElementById("cmbCategoria2").value = "";
    document.getElementById("txtPrecioVenta2").value = "";
    document.getElementById("txtPrecioCompra2").value = "";
    document.getElementById("txtCodigoProducto2").value = "";
    document.getElementById("txtCodigoBarras2").value = "";
}


// let empleado = JSON.parse(localStorage.getItem('empleado'));
//
//    // Verificar si el objeto empleado y su propiedad usuario existen
//    if (empleado && empleado.usuario) {
//        // Obtener el nombre de usuario del objeto empleado
//        let nombreUsuario = empleado.usuario.nombreUsuario;
//        
//        // Actualizar el contenido del span con el nombre de usuario
//        document.querySelector('.user-name').textContent = nombreUsuario;
//    }