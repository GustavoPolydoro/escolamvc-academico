$(document).ready(function () {
    // Evento de clique para botões com o atributo 'data-dynamic-update-rows-url'
    $('.dynamic-update-rows').on('click', 'button[data-dynamic-update-rows-url]', function(){
        // Obtém a URL do atributo 'data-dynamic-update-rows-url'
        let url = $(this).data('dynamic-update-rows-url');
        console.log(url);

        // Serializa os dados do formulário em um array
        let formData = $('form').serializeArray();

        // Adiciona um parâmetro adicional ao array de dados do formulário
        let param = {};
        param["name"] = $(this).attr('name'); // Nome do botão
        param["value"] = $(this).val();       // Valor do botão
        formData.push(param);

        // Atualiza a seção dinâmica com o conteúdo carregado da URL
        $('#dynamicTableContents').load(url, formData);
    });

    // Evento de clique para botões com o atributo 'data-dynamic-update-rows-url-turma'
    $('.dynamic-update-rows-turma').on('click', 'button[data-dynamic-update-rows-url-turma]', function () {
        // Obtém a URL do atributo 'data-dynamic-update-rows-url-turma'
        let urlTurma = $(this).attr('data-dynamic-update-rows-url-turma');

        // Serializa os dados do formulário em um array
        let formDataTurma = $('form').serializeArray();

        // Adiciona um parâmetro adicional ao array de dados do formulário
        let param = {};
        param["name"] = $(this).attr('name'); // Nome do botão
        param["value"] = $(this).val();       // Valor do botão
        formDataTurma.push(param);

        // Atualiza a seção dinâmica com o conteúdo carregado da URL
        $('#dynamicTableContentsTurma').load(urlTurma, formDataTurma);
    });

    // Fecha automaticamente os alertas após 4 segundos
    window.setTimeout(function() {
        $(".alert").fadeTo(500, 0).slideUp(500, function(){
            $(this).remove(); // Remove o alerta do DOM após a animação
        });
    }, 4000);

    // Aplica máscaras aos campos de CPF e telefone
    $('.cpf').mask('000.000.000-00'); // Máscara para CPF
    $('.tel').mask('00000-0000', {selectOnFocus: true}); // Máscara para telefone
});


$('.dynamic-update-rows-turma').on('click', 'button[data-dynamic-update-rows-url-turma]', function () {
    let urlTurma = $(this).attr('data-dynamic-update-rows-url-turma');


    let formDataTurma = $('form').serializeArray();
    let param = {};
    param["name"] = $(this).attr('name');
    param["value"] = $(this).val();
    formDataTurma.push(param);


    // Atualiaza a seção dinâmica
    $('#dynamicTableContentsTurma').load(urlTurma, formDataTurma);

});


// Função para atualizar o ID da turma com base na seleção do usuário
function atualizarIdTurma(indice, e) {
    // Obtém o valor selecionado no dropdown
    var idTurma = e.options[e.selectedIndex].value;

    // Atualiza o valor do campo oculto correspondente ao ID da turma
    document.getElementById('turmas' + indice + '.id').value = idTurma;

    // Atualiza o valor do campo oculto usando jQuery (opcional)
    $('#turmas' + indice + '.id').val(idTurma);
}