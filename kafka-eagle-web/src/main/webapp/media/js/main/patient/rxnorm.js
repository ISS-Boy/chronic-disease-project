$(document).ready(function() {
    var table = $("#RxnormTable").DataTable({
        "ajax": "/ke/patient_analysis/getRxnormlist/ajax"
    })

    $('#RxnormTable tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });

})