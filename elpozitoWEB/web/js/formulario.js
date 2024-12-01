function mostrarFormulario(id) {
    var formulario = document.getElementById(id);
    formulario.style.display = "block";
}

function cerrarFormulario(id) {
    var formulario = document.getElementById(id);
    formulario.style.display = "none";
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