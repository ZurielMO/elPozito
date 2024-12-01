let detalleventa;
let venta;
function getAllVenta(){
    
   fetch("http://localhost:8080/elpozitoWEB/api/venta/getAllV")
            .then(response => response.json())
            .then(response => {
                venta=response;

                let datos="";
                let i = 0;
               for(let i=0;i<venta.length;i++){
                   
                   let nombreCliente = venta[i].cliente.persona.nombre+" "
                +venta[i].cliente.persona.apellidoPaterno+" "
                +venta[i].cliente.persona.apellidoMaterno; 
              let nombreEmpleado = venta[i].empleado.persona.nombre+" "
                +venta[i].empleado.persona.apellidoPaterno+" "
                +venta[i].empleado.persona.apellidoMaterno;  
                     datos+="<tr>";
                     datos+="<td>"+venta[i].idventa+"</td>";
                     datos+="<td>"+nombreEmpleado+"</td>";
                     datos+="<td>"+nombreCliente+"</td>";
                     datos+="<td>"+venta[i].fechaHora+"</td>";
                     datos+="<td><button type='button' onclick='buscarVenta("+venta[i].idventa+");' class='btn btn-btn' data-bs-toggle='modal' data-bs-target='#staticBackdrop'><i class='fa-solid fa-eye fa-2xl' style='color: #2F7C3E;'></i><br><span class='btn-text' style='color: #2F7C3E; font-size: 12px'>Ver</span></button></td>";
                     datos+="</tr>";
                }
                document.getElementById("tbVenta").innerHTML=datos;
                
    });
}


function buscarVenta(id){
    let busqueda = id;
    let parametros = {"busqueda":busqueda,"token":localStorage.getItem("token")};
    fetch("http://localhost:8080/elpozitoWEB/api/venta/busquedaVenta",
    {method: "POST",
        headers: {'Content-Type':
                    'application/x-www-form-urlencoded;charset=UTF-8'},
        body:new URLSearchParams(parametros)
    }
    ).then(response => response.json())
    .then(response =>{
        cargarResultadoVenta(response);
        
    });
}
function cargarResultadoVenta (response){
    let datosTabla="";
    if (response.error!=null) {
    datosTabla+="Error en la busqueda";    
    }else if(response.length==0){
    datosTabla+="No se encontraron resultados";    
    }
    else{       
        detalleventa = response;
        
        for(let i=0;i<detalleventa.length;i++){
            datosTabla+="<tr>";
    datosTabla+="<td>"+detalleventa[i].producto.codigoProducto+"</td>";
    datosTabla+="<td>"+detalleventa[i].producto.nombre+"</td>";
    datosTabla+="<td>"+detalleventa[i].producto.presentacion+"</td>";
    datosTabla+="<td>"+detalleventa[i].precioVenta+"</td>";
    datosTabla+="<td>"+detalleventa[i].cantidad+"</td>";
    datosTabla+="</tr>";        
            
        }
        document.getElementById("tbDV").innerHTML=datosTabla;
    }   
}

//Aqui esta lo que agregué de la busqueda Maico
function filterTable(searchTerm) {
    const tableRows = document.querySelectorAll('#tbVenta tr');

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
getAllVenta();
filterTable('');
const searchInput = document.getElementById('searchInput');
searchInput.addEventListener('input', function () {
    const searchTerm = searchInput.value.toLowerCase();
    console.log('Término de búsqueda:', searchTerm); // Agregamos un console.log para verificar si el evento se está capturando correctamente
    filterTable(searchTerm);
});
               
const myModal = document.getElementById('myModal')
const myInput = document.getElementById('myInput')

myModal.addEventListener('shown.bs.modal', () => {
  myInput.focus()
})



