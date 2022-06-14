
let regform = document.getElementById("create-user");
const formData = new FormData(regform);

function getData() {
    let data = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value,
        rpassword: document.getElementById("rpass").value,
        role: document.getElementById("roleinput").value,
        fcoins: document.getElementById("Fcoins").value,
    };

    if (data.name === "" || data.username === "" || data.password === "" || data.rpassword === "" || data.role==="") {
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Todos los campos son requeridos",
        });
    } else {

        fetch(`./api/users/register`, {
            method: "POST",
            body: JSON.stringify(data),
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
        })
            .then((resp) => console.log(resp))
            .catch((error) => console.log(error));

        sessionStorage.setItem("newUser", JSON.stringify(data));

        let role = data.role;
        window.location = `../web/perfil${role}.html`;
    }
}