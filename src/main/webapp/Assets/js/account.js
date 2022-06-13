let sidebar = document.querySelector(".sidebar");
  let closeBtn = document.querySelector("#btn");
  let searchBtn = document.querySelector(".bx-search");

//Añade un respectivo escuchador al botón para que este pueda realizar alguna acción
  closeBtn.addEventListener("click", ()=>{
    sidebar.classList.toggle("open");
    menuBtnChange();
  });

//Añade un respectivo escuchador al botón para que este pueda realizar alguna acción
  searchBtn.addEventListener("click", ()=>{ 
    sidebar.classList.toggle("open");
    menuBtnChange(); 
  });

//Genera una respectiva acción dependiendo si el botón es accionado o no
  function menuBtnChange() {
   if(sidebar.classList.contains("open")){
     closeBtn.classList.replace("bx-menu", "bx-menu-alt-right");
   }else {
     closeBtn.classList.replace("bx-menu-alt-right","bx-menu");
   }
  }


if(window.location.href.includes("artistAccount")){
  window.addEventListener("DOMContentLoaded", getDataArts(document.getElementById("cardfavorites")));
}else{
  window.addEventListener("DOMContentLoaded", getDataArts(document.getElementById("cardOwner")));
}


  