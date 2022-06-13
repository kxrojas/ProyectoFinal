const getDataAccount = async () => {

    var name = document.getElementById("username");
    var role = document.getElementById("role");
    var fcoins = document.getElementById("amountFcoins");

    if (localStorage.getItem("username") != null || localStorage.getItem("username") != undefined) {

        let response = await fetch(`./api/users/${localStorage.getItem("username")}?role=${localStorage.getItem("role")}`);
        let result = await response.json();

        name.innerHTML = `${result.username}`;
        role.innerHTML = `${result.role}`;

        let response2 = await fetch(`./api/users/${localStorage.getItem("username")}/fcoins`);
        let result2 = await response2.json();
        fcoins.placeholder = new Intl.NumberFormat().format(result2.fcoins.toString());
    }
};

window.addEventListener("DOMContentLoaded", getDataAccount());