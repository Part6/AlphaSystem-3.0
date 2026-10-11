
$(document).ready(function () {
    
    console.log("funciona");
    
    $("#formFornecedor").submit(function (event) {
        event.preventDefault();
        
        const nome = $("#nome").val(); //$("#nome").val()
        const local = $("#local").val();

        if (nome === "") {
            alert("Digite o nome do Fornecedor!");
            return;
        }

        if (local === "") {
            alert("Digite a Localização!");
            return;
        }

        let fornecedor = {
            id: $("#id").val(),
            nome: nome,
            local: local
        };
        $.ajax({

            url: "/fornecedorRest/criar",

            type: "POST",

            contentType: "application/json",

            data: JSON.stringify(fornecedor),

            success: function (fornecedorCriado) {

                alert("Fornecedor cadastrado! ID: " + fornecedorCriado.id);
                
                console.log("test2");
            },

            error: function () {

                alert("Erro ao cadastrar Fornecedor.");
                console.log("test3");
            }
        });
    });
    
    $("#formEditarFornecedor").submit(function (event) {

        event.preventDefault();

        let id = $("#id").val();

        const nome = $("#nome").val(); //$("#nome").val()
        const local = $("#local").val();

        if (nome === "") {
            alert("Digite o nome do Fornecedor!");
            return;
        }

        if (local === "") {
            alert("Digite a Localização!");
            return;
        }

        let fornecedor = {
            id: id,
            nome: nome,
            local: local
        };
        
        console.log("teste1");

        $.ajax({

            url: "/fornecedorRest/" + id,

            type: "PUT",

            contentType: "application/json",

            data: JSON.stringify(fornecedor),

            success: function () {
                
                alert("Fornecedor atualizado com sucesso!");

                window.location.href = "/menu/menuGerente/ListaFornecedor";

            },

            error: function (xhr) {

                console.log(xhr);

                alert("Erro ao atualizar o fornecedor.");

            }

        });

    });
    
    $(".btnExcluir").on("click", function () {

        let id = $(this).data("id");

        console.log("ID do fornecedor:", id);

        if (!confirm("Deseja realmente excluir este Fornecedor?")) {
            return;
        }

        $.ajax({
            url: "/fornecedorRest/" + id,
            type: "DELETE",

            success: function (resultado) {

                console.log("Resposta:", resultado);

                if (resultado === true) {

                    alert("Fornecedor excluído com sucesso!");

                    location.reload();

                } else {

                    alert("Fornecedor não encontrado.");

                }
            },

            error: function (xhr) {

                console.log("Erro:", xhr);

                alert("Erro ao excluir o Fornecedor.");
            }
        });

    });
});