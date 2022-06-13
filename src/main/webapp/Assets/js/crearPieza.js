document.getElementById("autor").value=localStorage.getItem("autor");

const form = document.getElementById("createart");
createart = async (e) => {
    e.preventDefault();

    let autor = document.getElementById("autor").value;
    let collection = document.getElementById("collection").value;

    const response = await fetch(`./api/users/${autor}/collections/${collection}/arts`, {
        method: 'POST',
        body: new FormData(form)
    });

    window.location.href = "./perfilArtista.html";
}
form.addEventListener("submit", createart);

const getData = async () => {
    const dataCollectionNFTs = await fetch(`./api/users/${localStorage.getItem("autor")}/collections`).then(response => response.json());
    const collectionName = dataCollectionNFTs.map(collectionNft => ({collection: collectionNft.collection}))

    for (var i = 0; i < collectionName.length; i++) {
        document.getElementById("collection").innerHTML +=
            `<option>${collectionName[i].collection}</option>`;
    }
}
window.addEventListener("DOMContentLoaded", getData());