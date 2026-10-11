
$(document).ready(function () {
    
    console.log("funciona");
    
    $("#formUsuario").submit(function (event) {
        event.preventDefault();
        
         //$("#nome").val()
        const nome = $("#nome").val();
        const email = $("#email").val();
        const senha = $("#senha").val();
        const cargo = $("#cargo").val();

        if (nome === "") {
            alert("Digite o Nome!");
            return;
        }

        if (email === "") {
            alert("Digite o Email!");
            return;
        }

        if (senha === "") {
            alert("Digite a Senha!");
            return;
        }

        if (cargo === "") {
            alert("Escolha o Cargo!");
            return;
        }

        let usuario = {
            nome: nome,
            login: email,
            senha: senha,
            tipo: cargo
        };
        $.ajax({

            url: "/usuarioRest/criar", //usuarioRest

            type: "POST",

            contentType: "application/json",

            data: JSON.stringify(usuario),

            success: function (usuarioCriado) {

                alert("Usuario cadastrado! ID: " + usuarioCriado.id);
                window.location.href = "/menu/menuCadastro";
                console.log("test2");
            },

            error: function (xhr) {

                alert("Erro ao cadastrar Usuario.");
                console.log(xhr);
                console.log("foda");
            }
        });
    });
    
    $("#formEditarUsuario").submit(function (event) {

        event.preventDefault();

        let id = $("#id").val();

        const nome = $("#nome").val();
        const email = $("#email").val();
        const senha = $("#senha").val();
        const cargo = $("#cargo").val();

        if (nome === "") {
            alert("Digite o Nome!");
            return;
        }

        if (email === "") {
            alert("Digite o Email!");
            return;
        }

        if (senha === "") {
            alert("Digite a Senha!");
            return;
        }

        if (cargo === "") {
            alert("Escolha o Cargo!");
            return;
        }

        let usuario = {
            nome: nome,
            login: email,
            senha: senha,
            tipo: cargo
        };
        
        console.log("teste1");

        $.ajax({

            url: "/usuarioRest/" + id,

            type: "PUT",

            contentType: "application/json",

            data: JSON.stringify(usuario),

            success: function (resposta) {
                alert("Usuario cadastrado! ID: " + resposta.id);
                window.location.href = "/menu/menuGerente/ListaUsuarios"; 
            },

            error: function (xhr) {

                console.log(xhr);

                alert("Erro ao atualizar o usuario.");

            }

        });

    });
    
    $(".btnExcluir").on("click", function () {

        let id = $(this).data("id");

        console.log("ID do usuario:", id);

        if (!confirm("Deseja realmente excluir este Usuario?")) {
            return;
        }

        $.ajax({
            url: "/usuarioRest/" + id,
            type: "DELETE",

            success: function (resultado) {

                console.log("Resposta:", resultado);

                if (resultado === true) {

                    alert("Usuario excluído com sucesso!");

                    location.reload();

                } else {

                    alert("Usuario não encontrado.");

                }
            },

            error: function (xhr) {

                console.log("Erro:", xhr);

                alert("Erro ao excluir o Usuario.");
            }
        });

    });
});