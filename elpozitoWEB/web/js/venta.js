let clientes;
let cliente;
let productos;
let productoVenta= [];

 function buscarClienteVenta(){ 
      let busqueda = 4;
    let parametros = {"busqueda":busqueda, "token":localStorage.getItem("token")};
    
    fetch("http://localhost:8080/elpozitoWEB/api/cliente/busquedaC",
    {method: "POST",
        headers: {'Content-Type':
                    'application/x-www-form-urlencoded;charset=UTF-8'},
        body:new URLSearchParams(parametros)
    }
    ).then(response => response.json())
    .then(response =>{
        clientes = response;
        cliente = response;
       i = 2;
        agregarCV(i);
    });
        
}

function buscarClienteV(){
    let busqueda = document.getElementById("txtBuscarClienteV").value;
    let parametros = {"busqueda":busqueda, "token":localStorage.getItem("token")};
    
    fetch("http://localhost:8080/elpozitoWEB/api/cliente/busquedaC",
    {method: "POST",
        headers: {'Content-Type':
                    'application/x-www-form-urlencoded;charset=UTF-8'},
        body:new URLSearchParams(parametros)
    }
    ).then(response => response.json())
    .then(response =>{
        cargarResultadoBC(response);
        
    });
}

function buscarProductoV(){
    let busqueda = document.getElementById("txtBuscarProductoV").value;
    let parametros = {"busqueda":busqueda, "token":localStorage.getItem("token")};
    
    fetch("http://localhost:8080/elpozitoWEB/api/producto/busquedaP",
    {method: "POST",
        headers: {'Content-Type':
                    'application/x-www-form-urlencoded;charset=UTF-8'},
        body:new URLSearchParams(parametros)
    }
    ).then(response => response.json())
    .then(response =>{
        cargarResultadoBCProd(response);
    });
}

function cargarResultadoBC (response){
    let datosTabla="";
    if (response.error!=null) {
    datosTabla+="Errorr en la busqueda";    
    }else if(response.length==0){
    datosTabla+="No se encontraron resultados";    
    }
    else{
        clientes = response;
        for(let i=0;i<response.length;i++){
    let nombre = response[i].persona.nombre+" "
                +response[i].persona.apellidoPaterno+" "
                +response[i].persona.apellidoMaterno;
           
            datosTabla+="<tr>";
    datosTabla+="<td>"+response[i].idCliente+"</td>";
    datosTabla+="<td>"+nombre+"</td>";
    datosTabla+="<td>"+response[i].persona.telefono+"</td>";
    datosTabla+="<td><button type='button' onclick='agregarCV("+i+");' class='btn edit-btn'><i class='fa-solid fa-circle-check fa-2xl' style='color: #0D6EFD;'></i><br><span class='btn-text' style='color: #0D6EFD; font-size: 12px'>Seleccionar</span></button>";
            datosTabla+="</td></tr>";        
            
        }
        document.getElementById("tbBusquedaClienteV").innerHTML=datosTabla;
    }   
}
function cargarResultadoBCProd(response){
  
    let datosTabla="";
    if(response.error!=null){
        datosTabla+="Error en la busqueda";
    } else if(response.length==0){
        datosTabla+="Tu busqueda no tiene resultados";
    } else {
        for(let i=0;i<response.length; i++){
            productos=response;
            datosTabla+="<tr>";
            datosTabla+="<td>"+response[i].codigoProducto+"</td>";
            datosTabla+="<td>"+response[i].nombre+"</td>";
            datosTabla+="<td>"+response[i].categoria+"</td>";
            datosTabla+="<td>"+response[i].precioVenta+"</td>";
            datosTabla+="<td><button type='button' onclick='agregarPV("+i+");' class='btn edit-btn'><i class='fa-solid fa-circle-check fa-2xl' style='color: #0D6EFD;'></i><br><span class='btn-text' style='color: #0D6EFD; font-size: 12px'>Seleccionar</span></button>";
            datosTabla += "<button type='button' onclick='buscarInventario("+response[i].idProducto+");' class='btn btn-btn' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='fa-solid fa-eye fa-2xl' style='color: #2F7C3E;'></i><br><span class='btn-text' style='color: #2F7C3E; font-size: 12px'>Ver</span></button>";

            datosTabla+="</td></tr>";
        }
    }
    document.getElementById("tbBusquedaProductoV").innerHTML=datosTabla;
//    productosVenta=response;
}


function agregarCV(i){
    cliente = clientes[i];
    let nombre = cliente.persona.nombre
    +" "+cliente.persona.apellidoPaterno
    +" "+cliente.persona.apellidoMaterno;
    document.getElementById("txtNombreClienteV").value=nombre;
}

function agregarPV(i) {
    let producto = productos[i];
    let tblBody = document.getElementById("tbProductosVenta");
    let filas = tblBody.getElementsByTagName("tr");

    // Buscar si el producto ya está presente en la tabla
    for (let j = 0; j < filas.length; j++) {
        let codigoProducto = filas[j].getElementsByTagName("td")[0].textContent;
        if (codigoProducto === producto.codigoProducto) {
            // Si el producto ya está en la tabla, incrementar la cantidad y recalcular el total
            let inputCantidad = filas[j].querySelector("input[type='number']");
            inputCantidad.value = parseInt(inputCantidad.value) + 1;
            recalcularTotal();
            return; // Salir de la función ya que no es necesario agregar una nueva fila
        }
    }

    // Si el producto no está presente, agregar una nueva fila
    let hilera = document.createElement("tr");
    let celda1 = document.createElement("td");
    let textoCelda1 = document.createTextNode(producto.codigoProducto);
    let celda2 = document.createElement("td");
    let textoCelda2 = document.createTextNode(producto.nombre);
    let celda3 = document.createElement("td");
    let textoCelda3 = document.createTextNode(producto.unidadMedida);
    let celda4 = document.createElement("td");
    let inputCantidad = document.createElement("input");
    inputCantidad.setAttribute("type", "number");
    inputCantidad.setAttribute("class", "form-control");
    inputCantidad.setAttribute("id", "cantidadProductos");
    inputCantidad.setAttribute("name", "nombre");
    inputCantidad.setAttribute("value", "1"); 
    inputCantidad.setAttribute("min", "1"); 
    inputCantidad.addEventListener("change", function() {
        recalcularTotal();
    });
    let celda5 = document.createElement("td");
    let textoCelda5 = document.createTextNode(producto.precioVenta);
    let celda6 = document.createElement("td");
    let botonEliminar = document.createElement("button");
    botonEliminar.innerHTML = "<i class='fa-solid fa-trash fa-2xl' style='color: #DC3646;'></i><br><span class='btn-text' style='color: #DC3646; font-size: 12px'>Borrar</span>";
    botonEliminar.setAttribute("type", "button");
    botonEliminar.setAttribute("class", "btn delete-btn");
    botonEliminar.addEventListener("click", function() {
        tblBody.removeChild(hilera); 
        recalcularTotal(); 
    });

    celda1.appendChild(textoCelda1);
    celda2.appendChild(textoCelda2);
    celda3.appendChild(textoCelda3);
    celda4.appendChild(inputCantidad);
    celda5.appendChild(textoCelda5);
    celda6.appendChild(botonEliminar);
    
    hilera.appendChild(celda1);
    hilera.appendChild(celda2);
    hilera.appendChild(celda3);
    hilera.appendChild(celda4);
    hilera.appendChild(celda5);
    hilera.appendChild(celda6);
    
    tblBody.appendChild(hilera);

    productoVenta.push(producto);
    recalcularTotal();
}

function recalcularTotal() {
    let total = 0;
    let inputsCantidad = document.querySelectorAll("#tbProductosVenta input[type='number']");
    inputsCantidad.forEach(input => {
        let cantidad = parseInt(input.value);
        let precioVenta = parseFloat(input.parentNode.nextElementSibling.textContent);
        total += cantidad * precioVenta;
    });
    total = total.toFixed(2);
    document.getElementById("txtTotal").value = total;
}
function cancelarVenta(){
    document.getElementById("txtTotal").value="";
    document.getElementById("tbProductosVenta").innerHTML="";
    document.getElementById("tbBusquedaProductoV").innerHTML="";
    document.getElementById("txtNombreClienteV").value="";
    document.getElementById("txtBuscarProductoV").value="";
    document.getElementById("tbBusquedaClienteV").innerHTML="";
    document.getElementById("txtBuscarClienteV").value="";
    cliente=null;
    clientes=null;
    producto=null;
    productos=null;
    productoVenta=[];
    buscarClienteVenta();
}
function generarVenta(){
     if (!cliente) {    
        Swal.fire("Por favor selecciona un cliente antes de generar la venta", "", "error");
        return;
    }
    if (productoVenta.length === 0) {   
        Swal.fire("Por favor selecciona al menos un producto antes de generar la venta", "", "error");
        return;
    }
    let listaDV=[];
    
    for (var i = 0; i < productoVenta.length; i++) {
        let detalleVenta={
            precioVenta:productoVenta[i].precioVenta,
            cantidad:parseInt(document.querySelectorAll("#tbProductosVenta input[type='number']")[i].value),
            producto:productoVenta[i]
        };
        listaDV.push(detalleVenta);
    }
    
    
    let venta={
        cliente:cliente,
        empleado:JSON.parse(localStorage.getItem("empleado")),
        estatus:1,
        listDV: listaDV
    };
    let parametros={v:JSON.stringify(venta)};
    
//    alert(JSON.stringify(parametros));
    fetch("http://localhost:8080/elpozitoWEB/api/venta/generarVenta?",
    {method: "POST",
        headers: {'Content-Type':
                    'application/x-www-form-urlencoded;charset=UTF-8'},
        body:new URLSearchParams(parametros)
    })
    .then(response => response.json())
    .then(response =>{
                if (response.result === "success") {
                Swal.fire("Hubo un eror generar la venta", "", "error"); 
            } else {                     
        Swal.fire("Se genero la venta correctamene", "", "success");
        cancelarVenta();
//        location.reload();
        
            }
    })
     .catch(error => {
        console.error('Error al generar la venta:', error);
        Swal.fire("Hubo un error al generar la venta", "", "error");
    });
}

function buscarInventario(idProd){
    let id = idProd;
    let parametros = {"id":id, "token":localStorage.getItem("token")};
    
    fetch("http://localhost:8080/elpozitoWEB/api/producto/busquedaI",
    {method: "POST",
        headers: {'Content-Type':
                    'application/x-www-form-urlencoded;charset=UTF-8'},
        body:new URLSearchParams(parametros)
    }
    ).then(response => response.json())
    .then(response =>{
        cargarInventario(response);
    });
}
function cargarInventario(response){
  
    let datosTabla="";
    if(response.error!=null){
        datosTabla+="Error en la busqueda";
    } else if(response.length==0){
        datosTabla+="Tu busqueda no tiene resultados";
    } else {
        for(let i=0;i<response.length; i++){
            inv=response;
            datosTabla+="<tr>";
            datosTabla+="<td>"+inv[i].producto.codigoProducto+"</td>";
            datosTabla+="<td>"+inv[i].producto.nombre+"</td>";
            datosTabla+="<td>"+inv[i].producto.presentacion+"</td>";
            datosTabla+="<td>"+inv[i].producto.precioCompra+"</td>";
            datosTabla+="<td>"+inv[i].existencias+"</td>";
            datosTabla+="</tr>";
        }
    }
    document.getElementById("tbInventario").innerHTML=datosTabla;
//    productosVenta=response;
}





const myModal = document.getElementById('myModal')
const myInput = document.getElementById('myInput')

myModal.addEventListener('shown.bs.modal', () => {
  myInput.focus()
})