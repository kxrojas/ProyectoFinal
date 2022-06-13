document.getElementById('sumar').addEventListener('click', sumar);

    function sumar(){
        try {
            var inputCantidad = document.getElementById("valor");
            var valor = inputCantidad.value;
            inputCantidad.value = parseInt(valor) + parseInt("10");
        }catch {
            inputCantidad.value = parseInt(valor) + parseInt("10");
        }
    }

    document.getElementById("salir").addEventListener('click', salir)
    function salir() {
        window.location.href = "./perfilComprador.html"
    }

