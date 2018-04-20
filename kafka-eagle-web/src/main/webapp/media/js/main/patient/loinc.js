$(document).ready(function() {
    $("#loincTable").dataTable({
        "ajax":"/ke/patient_analysis/getLoinclist/ajax"
    })
})