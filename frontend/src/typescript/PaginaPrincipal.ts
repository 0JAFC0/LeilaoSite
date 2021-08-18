async function irPaginaLogin () {
  window.location.href = "../pages/login.html";

}

async function listarprodutos () {
  const rawResponse = await fetch('http://localhost:8080/api/leilao', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    }
  });
  renderizarProdutos(rawResponse.json());
}

function addListener(lista : NodeListOf<Element>) {
  lista.forEach(elementLista => {
    let element = elementLista as HTMLElement;
    element.addEventListener("mousemove", () => {
      element.style.borderStyle = "solid";
      element.style.borderColor = "#E7E7E7"
    });

    element.addEventListener("mouseleave", () => {
      element.style.border = null;
    });

    element.addEventListener("click", () => {
      window.localStorage.setItem("produto", (element as HTMLInputElement).value);
      window.location.href = "../pages/telaLeilao.html";
    });
  });
}

function renderizarProdutos(json : Promise<any>) {
  let catalogo = document.getElementById("catalogo")
  json.then(dados => {
    let i = 0;
    let html = "";
    while(true) {
      if(dados[i] === undefined) {
        break;
      }
      let elemento = dados[i++];
      let usernameUltimoLance = "";
      if(elemento["usernameUltimoLance"] != null) {
        usernameUltimoLance = elemento["usernameUltimoLance"];
      }
      html += `<div class="item_catalogo" id="item_catalogo" value=${elemento["id"]}>
      <img style='display: block; margin-left: 2vw; margin-top: 1vh; border-radius: 10px; max-width: 80%;' height=200vh id='base64image' src='${elemento["listaProdutos"][0]["imagem"]}' />
      <label style='margin: auto;'id="data_inicio" for="">Data: ${elemento["dataInicio"]}</label>
      <label id="valor_atual" for="">Valor Atual: ${elemento["valorAtual"]}</label>
      <label id="nome_ultimo_lance" for="">${usernameUltimoLance}</label>
      </div>`;
    }
    catalogo.innerHTML = html;
    addListener(catalogo.querySelectorAll('[id=item_catalogo]'));
  })
}

async function listarCategoriasMain () {
  const rawResponse = await fetch('http://localhost:8080/api/categoria', {
  method: 'GET',
  headers: {
      'Content-Type': 'application/json',
      "Authorization": `Bearer ${window.localStorage.getItem("token")}`
  }
  });
  if(rawResponse.ok)
    renderizarCategoriasMain(rawResponse.json());
  else if(rawResponse.status == 403) {
      window.location.href = "../pages/login.html";
  }
}

function renderizarCategoriasMain(json : Promise<any>) {
  let categoria = document.getElementById("menu")
  json.then(dados => {
    let i = 0;
    let html = `<a href="" id="opcao">Tudo</a>`;
    while(true) {
        if(dados[i] === undefined) {
        break;
        }
        let elemento = dados[i++];
        html += `<a href="" id="opcao" value=${elemento["id"]}>${elemento["categoriaNome"]}</a>`;
    }
    categoria.innerHTML = html;
  })
}

function listenerMenuPrincipal() {
  document.getElementById("hmProduto").addEventListener("click", () => {
    window.location.href = "../pages/cadastrarProduto.html";
  });
  document.getElementById("hmLeilao").addEventListener("click", () => {
    window.location.href = "../pages/PaginaCadastrarProdutoLeiloar.html";
  });
}

listarprodutos();
listarCategoriasMain();
listenerMenuPrincipal();