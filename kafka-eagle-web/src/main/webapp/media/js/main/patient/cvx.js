$(document).ready(function() {
    var table = $("#cvxTable").DataTable({
        "ajax": "/ke/patient_analysis/getCvxlist/ajax"
    })

    $('#cvxTable tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });

})