const getAutor = async (e) => {
    document.getElementById("autor").value= localStorage.getItem("username");
}

const form = document.querySelector("form");
form.onsubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData(form);

    let autor = document.getElementById("name").value;
    let collection = document.getElementById("collection").value;

    let responseData = await fetch(`./api/users/${autor}/collections/${collection}`);
    let resultData = await responseData.json();

    let response = await fetch(`./api/users/${autor}/collections` , {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: new URLSearchParams(formData),

    });
    let result = response.json();

    sessionStorage.setItem("collection", result.collection);
    window.location.href = "../crearPieza.html";
}