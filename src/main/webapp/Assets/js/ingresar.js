

let form = document.getElementById("log-in-form");
form.onsubmit = async (e) => {
    e.preventDefault();

    let role = document.getElementById("roleInput").value;

    let data = {
        "username": document.getElementById("username").value,
        "password": document.getElementById("password").value,
    }

    let response = await fetch("./api/users/login", {
        "method": "POST",
        "body": JSON.stringify(data),
        "headers": {
            "Content-type": "application/json"
        }
    });

    let message = response.json();
    alert(message);
    if (role=='Artista') {
        window.location.href = "./perfilArtista.html";
    }
    else {
        window.location.href = "./perfilComprador.html";
    }
}