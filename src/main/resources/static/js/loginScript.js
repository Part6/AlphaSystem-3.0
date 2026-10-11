
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
                alert("Bem-vindo(a), " + usuario.nome + "!");
                window.location.href = "/menu";
            },
            error: function (xhr) {
                const mensagem = xhr.responseJSON && xhr.responseJSON.mensagem
                    ? xhr.responseJSON.mensagem
                    : "Não foi possível autenticar. Tente novamente.";
                alert(mensagem);
                console.error("Falha no login:", xhr.status, xhr.responseText);
            }
        });
    });
});
