
$(document).ready(function () {
    
    console.log("funciona");
    
    $("#formProduto").submit(function (event) {
        event.preventDefault();
        
         //$("#nome").val()
        let nome = $("#nome").val();
        let categoria = $("#categoria").val();
        let qnt = $("#qnt").val();
        let preco = $("#preco").val();
        let descricao = $("#descricao").val();
        
        const fornecedorId = $("#fornecedor").val();
        console.log("Fornecedor selecionado:", fornecedorId);
        
        let taxa = $("#taxa").val();

        if (nome === "") {
            alert("Digite o nome do Produto!");
            return;
        }

        if (categoria === "") {
            alert("Digite a Categoria do Produto!");
            return;
        }

        if (qnt === "") {
            alert("Digite a Quantidade!");
            return;
        }

        if (preco === "") {
            alert("Digite a Preço!");
            return;
        }

        if (descricao === "") {
            alert("Digite a Descriçao!");
            return;
        }

        if (fornecedorId === "") {
            alert("Escolha o Fornecedor!");
            return;
        }

        if (taxa === "") {
            alert("Digite a Taxa!");
            return;
        }

        let produto = {
            id: Date.now(),
            nome: nome,
            categoria: categoria,
            quantidade: qnt,
            preco: preco,
            observacoes: descricao,
            fornecedorId: fornecedorId,
            taxa: taxa
        };
        $.ajax({

            url: "/produtoRest/criar", //produtoRest

            type: "POST",

            contentType: "application/json",

            data: JSON.stringify(produto),

            success: function (resposta) {

                alert("Produto cadastrado! ID: " + resposta.id);
                window.location.href = "menu/menuCadastro"; 
            },

            error: function () {

                alert("Erro ao cadastrar Produto.");
                console.log("test3");
            }
        });
    });
    
    $("#formEditarProduto").submit(function (event) {

        event.preventDefault();

        let id = $("#id").val();

        let nome = $("#nome").val();
        let categoria = $("#categoria").val();
        let qnt = $("#qnt").val();
        let preco = $("#preco").val();
        let descricao = $("#descricao").val();
        
        const fornecedorId = $("#fornecedor").val();
        console.log("Fornecedor selecionado:", fornecedorId);
        
        let taxa = $("#taxa").val();

        if (nome === "") {
            alert("Digite o nome do Produto!");
            return;
        }

        if (categoria === "") {
            alert("Digite a Categoria do Produto!");
            return;
        }

        if (qnt === "") {
            alert("Digite a Quantidade!");
            return;
        }

        if (preco === "") {
            alert("Digite a Preço!");
            return;
        }

        if (descricao === "") {
            alert("Digite a Descriçao!");
            return;
        }

        if (fornecedorId === "") {
            alert("Escolha o Fornecedor!");
            return;
        }

        if (taxa === "") {
            alert("Digite a Taxa!");
            return;
        }

        let produto = {
            id: id,
            nome: nome,
            categoria: categoria,
            quantidade: qnt,
            preco: preco,
            observacoes: descricao,
            fornecedorId: fornecedorId,
            taxa: taxa
        };
        
        console.log("id: " + id);
        

        $.ajax({

            url: "/produtoRest/" + id,

            type: "PUT",

            contentType: "application/json",

            data: JSON.stringify(produto),

            success: function () {
                
                alert("Produto atualizado com sucesso!");
                window.location.href = "/menu/menuConsulta/consultaProdutos";
               

            },

            error: function (xhr) {

                console.log(xhr);

                alert("Erro ao atualizar o produto.");

            }

        });

    });
    
    $(".btnExcluir").on("click", function () {

        let id = $(this).data("id");

        console.log("ID do produto:", id);

        if (!confirm("Deseja realmente excluir este Produto?")) {
            return;
        }

        $.ajax({
            url: "/produtoRest/" + id,
            type: "DELETE",

            success: function (resultado) {

                console.log("Resposta:", resultado);

                if (resultado === true) {

                    alert("Produto excluído com sucesso!");

                    location.reload();

                } else {

                    alert("Produto não pode ser excluido.");

                }
            },

            error: function (xhr) {

                console.log("Erro:", xhr);

                alert("Erro ao excluir o Produto.");
            }
        });

    });
    
    
});