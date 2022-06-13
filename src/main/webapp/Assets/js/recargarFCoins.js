
const add = async () =>{

    const fcoins = JSON.stringify({ "username":localStorage.getItem('username').toString(),"fcoins": parseFloat(document.getElementById('cantidad').value)});
    try {
        await fetch(`./api/users/${localStorage.getItem("username")}/fcoins`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: fcoins

        });

        window.location.href = "./index.html";

    } catch (err) {
        console.log(err);

    }
}
document.getElementById('btnAddFCOINS').addEventListener('click',add);