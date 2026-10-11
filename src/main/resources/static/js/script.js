function entrar() {
    const login = document.getElementById("login").value;
    const senha = document.getElementById("senha").value;

    if (login === "" || senha === "") {
        alert("Preencha o login e a senha!");
        return;
    }

    // Depois podemos trocar isso pela validação no banco
    window.location.href = "../Tela inicial/index.html";
}


// ---------- LIMPAR FORMULÁRIO ----------

function limparFormulario(formulario) {
    document.getElementById(formulario).reset();
}


// ---------- CONFIRMAÇÃO DE EXCLUSÃO ----------





// ---------- MENSAGEM DE SUCESSO ----------

function mensagemSucesso(mensagem) {
    alert(mensagem);
}