
$(document).ready(function () {
    
    console.log("funciona");
    
    $("#formLogin").on("submit", function (event) {
        event.preventDefault();
        const login = $("#login").val().trim();
        const senha = $("#senha").val();

        if (!login || !senha) {
            alert("Informe o login e a senha.");
            return;
        }
        
        console.log("funciona2");

        $.ajax({
            url: "/usuarioRest/login",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ login: login, senha: senha }),
            success: function (usuario) {
                window.location.href = "/menu";
            },
            error: function (xhr) {
                console.log("Erro:", xhr);
                alert("Não foi possível autenticar. Tente novamente.");
            }
        });
    });
});
