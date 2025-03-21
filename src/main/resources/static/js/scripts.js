$(document).ready(function () {
    $('.dynamic-update-rows').on('click', 'button[data-dynamic-update-rows-url]', function(){
        let url = $(this).data('dynamic-update-rows-url');
        console.log(url);

        let formData = $('form').serializeArray();
        let param = {};
        param["name"] = $(this).attr('name');
        param["value"] = $(this).val();
        formData.push(param);

        // Atualiaza a seção dinâmica
        $('#dynamicTableContents').load(url, formData);
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

    // autodismiss alerts
    window.setTimeout(function() {
        $(".alert").fadeTo(500, 0).slideUp(500, function(){
            $(this).remove();
        });
    }, 4000);


    $('.cpf').mask('000.000.000-00');
    $('.tel').mask('00000-0000', {selectOnFocus: true});



});

function atualizarIdTurma(indice,e) {
    var idTurma = e.options[e.selectedIndex].value;

    document.getElementById('turmas' + indice + '.id').value = idTurma;

    $('#turmas' + indice + '.id').val(idTurma);
}