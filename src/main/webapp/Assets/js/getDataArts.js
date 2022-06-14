
const getDataArts = async (artsDiv) => {

    //likes
  let data = null;
  let dataLikes = null;
  let heartLikesStatus = null;

  let styleClassCardTopRankLikes = "";

      let url = window.location.href

       if(url.includes("perfilComprador")){
          data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts`).then(response => response.json());
          if(artsDiv.id != "cardfavorites"){
              getDataArts(document.getElementById("cardfavorites"));
          }else{
              data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts/likes`).then(response => response.json());
          }
      } else if(url.includes("perfilArtista")){
          data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts/likes`).then(response => response.json());
      } else{
        data = await fetch("./api/arts").then(response => response.json());

        //invierte la lista tomando los últimos creados
        data.reverse();

        //Toma solo las primeras 6 artes
       while(data.length>6 ){
           data.length = data.length - 1;
       }
       //modifica el id por la ruta de la imagen

       data.map(function(element) {
         element.id = "NFTS\\"+element.id;
       })

        if(artsDiv.id != "card_LikesRanking"){
            getDataArts(document.getElementById("card_LikesRanking"));
        }else{
            styleClassCardTopRankLikes = "cardTopRankLikes card-dimensionsTopLiked";
            data = await fetch("./api/arts/likes").then(response => response.json());
        }
      }

      const listTotalLikes = await fetch("./api/arts/likes/list").then(response => response.json());
      const listTotalLikesByUser = await fetch(`./api/users/${localStorage.getItem('username')}/likes`).then(response => response.json());


      let innerhtml = "";

      for (const data1 of data) {
          const {id, collection, title, author, price, forSale} = data1;
          let idNFT = id.toString().split("\\")[1];
          let type = "";

          const artTotalLike = listTotalLikes.filter(data => (data.idImage === idNFT));

          if (artTotalLike.length!=0) {
              dataLikes = artTotalLike[0].likes;
         }else {
              dataLikes=0;
          }

          const likeByArt = listTotalLikesByUser.filter(data => (data.idImage === idNFT));
          if (likeByArt.length!=0) {
              heartLikesStatus = likeByArt[0].likes;
          }else {
              heartLikesStatus=0;
          }
          if (heartLikesStatus === 0) {
              heartLikesStatus = "Assets/svg/heart-unfill.svg";
          } else {
              heartLikesStatus = "Assets/svg/heart-fill.svg";
          }
          innerhtml += `



<div class="col-md-4 card-position "> 
    <div class=" ${styleClassCardTopRankLikes} card mb-4 shadow-sm card-dimensions">
        <div class="imgBx">
            <img class="bd-placeholder-img card-img-top" width="100%" height="100%" style="border-radius: 3.5%;" src="${id}" preserveaspectratio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail">
        </div>

        <div class="content card-content">
            <h3 class="card-text" id="item1">Titulo: ${title}</h3>
            <p class="card-text" id="item1">
            Autor: ${author}
            <br>
            Colección: ${collection}
            </p>
            <p class="text-muted">Precio: ${new Intl.NumberFormat().format(price)}</p>
            <p class="text-muted">Likes:
                <button class="btn-like" onclick="btnLike('${idNFT}','${type}')">
                    <img id="heartStatus${idNFT}"" src="${heartLikesStatus}" width="15px">
                </button>
                <span id="amountLikes${idNFT}" aria-valuetext="">${dataLikes}</span>
            </p>`;
        
  //No agrega los botones de compra y carrito de compras
  if(url.includes("perfilComprador") && (artsDiv.id ==="cardOwner")|| url.includes("perfilArtista")){
      innerhtml += `
      </div>
    </div>
</div>`;
  }
  //Agrega los botones de compra y carrito de compras
  else{
      innerhtml += `
      <input type="submit" id='${JSON.stringify(data1)}'  class="btn btn-sm btn-outline-secondary" value="Comprar" onclick="btnBuy(this,'buy')">     
      <input type="submit" id='${JSON.stringify(data1)}'  class="btn btn-sm btn-outline-secondary" value="Añadir al Carro" onclick="btnBuy(this,'add')">        
  
      </div>
    </div>
</div>`;
  }
      }
      artsDiv.innerHTML += innerhtml;
}

const getDataCollection = async () =>   {
    var imagesCol = document.getElementById("cardcol")
    if (window.location.toString().includes("artistAccount")) {
        dataCollection = await fetch(`./api/users/${localStorage.getItem("username")}/collections`).then(response => response.json());
    } else {
        dataCollection = await fetch("./api/collections").then(response => response.json());
    }


    for (const dataCollection1 of dataCollection) {
        const {username, collection} = dataCollection1;

        const dataCollectionNFTs = await fetch(`./api/users/${username}/collections/${collection}/arts`).then(response => response.json());

        const urlNfts = dataCollectionNFTs.map(collectionNft => ({id: collectionNft.id, author: collectionNft.author}))


        if (urlNfts.length == 0 && window.location.toString().includes("artistAccount")){
            imagesCol.innerHTML += `
       <div class="col-md-4 card-position">
        <div class="card mb-4 shadow-sm card-dimensions" style="padding: 50%" id="modalNFTs" onclick="getDataModal('${collection.toString()}')"  data-toggle="modal" data-target=".bd-example-modal-lg">
        <div class="imgBx">
            <table>
                <tbody id="tableCollections${collection}">
                    <tr id="rowTable${collection}">
                    <p>Aún no has agregado ninguna pieza</p>
                    </tr>
                </table>
            </tbody>
        </div>
        <div class="content">
            <div class="card-body">
                <h3 class="card-text" id="collectionCollection">Colección: ${collection}</h3>
                <p class="card-text text-muted" id="collectionAuthor">Autor: ${document.getElementById("nameArtist").textContent}</p>
            </div>
        </div>
    </div>
</div>
   `;
        } else {

            //Get number arts from the collection
            let imgsArts ="";
            for(let i = 0; i < urlNfts.length && i<=3; i++){
                imgsArts+=`<img class="imageCollection" width="100%" height="100%" src="${urlNfts[i]?.id}" preserveaspectratio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail">`
            }

            imagesCol.innerHTML += `
       <div class="col-md-4 card-position">
    <div class="card mb-4 shadow-sm card-dimensions" id="modalNFTs" onclick="getDataModal('${collection.toString()}','${username.toString()}')"  data-toggle="modal" data-target=".bd-example-modal-lg">
        <div class="imgBx collectionCatalogue">
            ${imgsArts}
        </div>
        <div class="content">
            <div class="card-body" id="item1">
                <h3 class="card-text" id="collectionCollection">Colección: ${collection}</h3>
                <p class="card-text text-muted" id="collectionAuthor">Autor: ${urlNfts[0]?.author}</p>
            </div>
        </div>
    </div>
</div>
   `;
        }
    }
};

const getDataModal = async (collection,username) => {
    //Ventana emrgente modal
    var imagesModal = document.getElementById("cardsCollection");

    const dataCollectionNFTs = await fetch(`./api/users/${username}/collections/${collection}/arts`).then(response => response.json());
    const dataNFTs = dataCollectionNFTs.map(collectionNft => ({author: collectionNft.author}))

    imagesModal.innerHTML = `<h2 id="ofertas" class="card_tittle">Colección: ${collection} <p class="text-muted">By: ${dataNFTs[0].author}</p></h2>
            <!--Guia js-->
            <div class="card-group contenedor-social" id="socialcard">
              <section class="py-5" style="width: 100%;">
                <div class="album py-5">
                  <div class="container">
                    <div class="row" id="cardNftCatologue">
                    </div>
                  </div>
                </div>
              </section>
            </div>`;



        var buyButtons = "";
        var habilitar ="";

        if (window.location.toString().includes("perfilArtista")) {

            if (forSale == true) {

                habilitar="Deshabilitar";

            } else {
                habilitar="Habilitar";
            }

            buyButtons = ` 
            <input type="submit" id='${JSON.stringify(dataCollectionNFTs1)}'  class="btn btn-sm btn-outline-secondary item1" value="${habilitar} Compra" onclick="btnDeshabilitar(this)">
            <input type="submit" id='${JSON.stringify(dataCollectionNFTs1)}'  class="btn btn-sm btn-outline-secondary item1" value="Editar" onclick="">
            `;
        }

            else {


            if (forSale == true) {
                buyButtons = ` 
 
            <input type="submit" id='${JSON.stringify(dataCollectionNFTs1)}'  class="btn btn-sm btn-outline-secondary item1" value="Comprar" onclick="btnBuy(this,'buy')">
            <input type="submit" id='${JSON.stringify(dataCollectionNFTs1)}'  class="btn btn-sm btn-outline-secondary item1" value="Añadir al Carro" onclick="btnBuy(this,'add')">`;
            } else {
                buyButtons = ` 
            <span class="btn btn-sm btn-outline-secondary item1 d-inline-block" tabindex="0" data-toggle="tooltip" title="El Artista deshabilitó la venta de este arte o ya fue vendida">
            No Disponible
            <span>
            `;
            }
        }

            var cardNftCatalogue = document.getElementById("cardNftCatologue");

            cardNftCatalogue.innerHTML += `
        <div class="col-md-4 card-position "> 
            <div class="  card mb-4 shadow-sm card-dimensions">
            <div class="imgBx">
                <img class="bd-placeholder-img card-img-top" width="100%" height="100%" style="border-radius: 3.5%;" src="${id}" preserveaspectratio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail">
            </div>

            <div class="content card-content">
                <h4 class="card-text" id="item1">Titulo: ${title}</h4>
                <p class="text-muted" style="">Precio: $${new Intl.NumberFormat().format(price)}</p>
                <p class="text-muted" style="text-align: center;">Likes:
                    <button class="btn-like" onclick="btnLike('${idNFT}','${type}')">
                        <img id="heartStatusModal${idNFT}"" src="${heartLikesStatus}" width="15px">
                    </button>
                    <span id="amountLikesModal${idNFT}" aria-valuetext="">${dataLikes}</span>
                </p>
               ${buyButtons}    
            </div>
        </div>
                    `;
}


const params = new Proxy(new URLSearchParams(window.location.search), {
    get: (searchParams, prop) => searchParams.get(prop),
  });
  
