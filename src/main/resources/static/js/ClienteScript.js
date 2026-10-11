
$(document).ready(function () {
    
    console.log("funciona");
    
    $("#formCliente").submit(function (event) {
        event.preventDefault();
        
        const nome = $("#nome").val(); // $("#nome").val()
        const cpf = $("#cpf").val();
        const email = $("#email").val();
        const cidade = $("#cidade").val();
        const estado = $("#estado").val();
        const cep = $("#cep").val();
        const rua = $("#rua").val();
        const ncasa = $("#nCasa").val();

        if (nome === "") {
            alert("Digite o nome do cliente!");
            return;
        }

        if (cpf === "") {
            alert("Digite o CPF!");
            return;
        }

        if (email === "") {
            alert("Digite o email!");
            return;
        }

        if (cidade === "") {
            alert("Digite a cidade!");
            return;
        }

        if (estado === "") {
            alert("Digite o estado!");
            return;
        }

        if (cep === "") {
            alert("Digite o CEP!");
            return;
        }

        if (rua === ""){
            alert("Digite a Rua!");
        }

        if (ncasa === ""){
            alert("Digite o Número da Casa!");
        }

        let cliente = {
            id: $("#id").val(),
            nome: nome,
            cpf: cpf,
            email: email,
            endereco: {
                rua: rua,
                cidade: cidade,
                estado: estado,
                numero: Number(ncasa),
                cep: Number(cep)
            }
        };
        $.ajax({

            url: "/clienteRest/criar",

            type: "POST",

            contentType: "application/json",

            data: JSON.stringify(cliente),

            success: function (clienteCriado) {

                alert("Cliente cadastrado! ID: " + clienteCriado.id);
                window.location.href = "menu/menuCadastro"; 
                console.log("test2");
            },

            error: function () {

                alert("Erro ao cadastrar filme.");
                console.log("test3");
            }
        });
    });
    
    $("#formEditarCliente").submit(function (event) {

        event.preventDefault();

        let id = $("#id").val();

        const nome = $("#nome").val(); // $("#nome").val()
        const cpf = $("#cpf").val();
        const email = $("#email").val();
        const cidade = $("#cidade").val();
        const estado = $("#estado").val();
        const cep = $("#cep").val();
        const rua = $("#rua").val();
        const ncasa = $("#nCasa").val();

        if (nome === "") {
            alert("Digite o nome do cliente!");
            return;
        }

        if (cpf === "") {
            alert("Digite o CPF!");
            return;
        }

        if (email === "") {
            alert("Digite o email!");
            return;
        }

        if (cidade === "") {
            alert("Digite a cidade!");
            return;
        }

        if (estado === "") {
            alert("Digite o estado!");
            return;
        }

        if (cep === "") {
            alert("Digite o CEP!");
            return;
        }

        if (rua === ""){
            alert("Digite a Rua!");
        }

        if (ncasa === ""){
            alert("Digite o Número da Casa!");
        }

        let cliente = {
            id: id,
            nome: nome,
            cpf: cpf,
            email: email,
            endereco: {
                rua: rua,
                cidade: cidade,
                estado: estado,
                numero: Number(ncasa),
                cep: Number(cep)
            }
        };
        
        console.log("teste2");

        $.ajax({

            url: "/clienteRest/" + id,

            type: "PUT",

            contentType: "application/json",

            data: JSON.stringify(cliente),

            success: function () {
                
                alert("Cliente atualizado com sucesso!");

                 window.location.href = "/menu/menuConsulta/consultaClientes";

            },

            error: function (xhr) {

                console.log(xhr);

                alert("Erro ao atualizar o cliente.");

            }

        });

    });
    
    $(".btnExcluir").on("click", function () {

        let id = $(this).data("id");

        console.log("ID do cliente:", id);

        if (!confirm("Deseja realmente excluir este Cliente?")) {
            return;
        }

        $.ajax({
            url: "/clienteRest/" + id,
            type: "DELETE",

            success: function (resultado) {

                console.log("Resposta:", resultado);

                if (resultado === true) {

                    alert("Cliente excluído com sucesso!");

                    location.reload();

                } else {

                    alert("Cliente não encontrado.");

                }
            },

            error: function (xhr) {

                console.log("Erro:", xhr);

                alert("Erro ao excluir o filme.");
            }
        });

    });
});