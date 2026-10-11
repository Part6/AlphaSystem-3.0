
$(document).ready(function () {
    
    console.log("funciona");
    
    $("#formVenda").submit(function (event) {
        event.preventDefault();
        
         //$("#nome").val()
        const clienteId = $("#cliente").val();
        const data = $("#data").val();
        const produtoId = $("#produto").val();
        const observacoes = $("#observacoes").val();
        const qnt = $("#qnt").val();

        if (clienteId === "") {
            alert("Digite o nome do Cliente!");
            return;
        }

        if (data === "") {
            alert("Digite a Data da Venda!");
            return;
        }

        if (produtoId === "") {
            alert("Digite o Produto!");
            return;
        }

        if (observacoes === "") {
            alert("Digite as Observacoes da Venda!");
            return;
        }
        
        if (qnt === "") {
            alert("Digite a quantidade de itens da Venda!");
            return;
        }

        const venda = {
            clienteId: clienteId,
            produtoId: produtoId,
            data: data,
            observacoes: observacoes,
            quantidadeItens: qnt
        };
        
        $.ajax({

            url: "/vendaRest/criar", //vendaRest

            type: "POST",

            contentType: "application/json",

            data: JSON.stringify(venda),

            success: function (resposta) {

                alert("Venda cadastrada! ID: " + resposta.id); ///menu/menuCadastro
                window.location.href = "menu/menuCadastro"; 
            },

            error: function () {

                alert("Erro ao cadastrar Venda.");
                console.log("test3");
            }
        });
    });
    
    $("#formEditarVenda").submit(function (event) {

        event.preventDefault();

        let id = $("#id").val();

        const clienteId = $("#cliente").val();
        const data = $("#data").val();
        const produtoId = $("#produto").val();
        const observacoes = $("#observacoes").val();
        const qnt = $("#qnt").val();

        if (clienteId === "") {
            alert("Digite o nome do Cliente!");
            return;
        }

        if (data === "") {
            alert("Digite a Data da Venda!");
            return;
        }

        if (produtoId === "") {
            alert("Digite o Produto!");
            return;
        }

        if (observacoes === "") {
            alert("Digite as Observacoes da Venda!");
            return;
        }
        
        if (qnt === "") {
            alert("Digite a quantidade de itens da Venda!");
            return;
        }

        const venda = {
            clienteId: clienteId,
            produtoId: produtoId,
            data: data,
            observacoes: observacoes,
            quantidadeItens: qnt
        };
        
        console.log("id: " + id);

        $.ajax({

            url: "/vendaRest/" + id,

            type: "PUT",

            contentType: "application/json",

            data: JSON.stringify(venda),

            success: function () {
                
                alert("Venda atualizada com sucesso!");

                window.location.href = "/menu/menuConsulta/consultaVendas";
                

            },

            error: function (xhr) {

                console.log(xhr);

                alert("Erro ao atualizar o venda.");

            }

        });

    });
    
    $(".btnExcluir").on("click", function () {

        let id = $(this).data("id");

        console.log("ID do venda:", id);

        if (!confirm("Deseja realmente excluir este Venda?")) {
            return;
        }

        $.ajax({
            url: "/vendaRest/" + id,
            type: "DELETE",

            success: function (resultado) {

                console.log("Resposta:", resultado);

                if (resultado === true) {

                    alert("Venda excluído com sucesso!");

                    location.reload();

                } else {

                    alert("Venda não encontrado.");

                }
            },

            error: function (xhr) {

                console.log("Erro:", xhr);

                alert("Erro ao excluir o Venda.");
            }
        });

    });
});