
const btnBuy = async (input, condition)=>{

    var cantidad = localStorage.getItem('cantidadCompras');
    var data = input.id;
    var dataBuyJSON = JSON.parse(data);
    var id = dataBuyJSON.id.toString().split("\\")[1];
    let dataOwner = await fetch(`./api/owners/arts/${id}`).then(response => response.json());

    if (localStorage.getItem('username')!=null || localStorage.getItem('username') != undefined ) {
        if (dataOwner.username != localStorage.getItem('username')) {
            if (cantidad == null) {

                localStorage.setItem('cantidadCompras', 1);
                localStorage.setItem('buy1', data);
                if (condition=='buy') {
                    window.location.href = './shoppingCart.html';
                }else {
                    document.getElementById("numCantCompras").innerHTML=`&nbsp;${localStorage.getItem('cantidadCompras')}`;
                }
            } else {
                let exist = false;
                for (var i = 1; i <= cantidad && !exist; i++) {
                    if (localStorage.getItem(`buy${i}`) != data) {
                    } else {
                        exist = true;
                        if (condition=='buy') {
                            window.location.href = "./shoppingCart.html";
                        }
                    }
                }
                if (!exist) {
                    cantidad = parseInt(cantidad) + parseInt(1);
                    localStorage.setItem(`buy${cantidad}`, data);
                    localStorage.setItem('cantidadCompras', cantidad);
                    if (condition=='buy') {
                        window.location.href = "./shoppingCart.html";
                    }else {
                        document.getElementById("numCantCompras").innerHTML=`&nbsp;${localStorage.getItem('cantidadCompras')}`;
                    }
                }
            }
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Este NFT ya es tuyo!',
            })
        }
    }else{
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Inicie Sesión para Comprar!',
        })
    }
}

window.addEventListener("DOMContentLoaded",
    getDataArts(document.getElementById("card")),
    getDataCollection(), getArtistsData());