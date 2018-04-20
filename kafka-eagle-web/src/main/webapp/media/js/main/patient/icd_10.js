$(document).ready(function() {
    $("#icd_10").dataTable({
        "ajax":"/ke/patient_analysis/icdlist/ajax"
    })
    function contextFormValid() {
        var ke_icd_code = $("#ke_icd_code").val();
        var ke_diseaseName = $("#ke_diseaseName").val();
        var ke_helpCode = $("#ke_helpCode").val();

        if (ke_icd_code.length == 0 || ke_diseaseName.length == 0 || ke_helpCode.length == 0 ) {
            alert("bunengweikong");
            $("#alert_mssage").show();
            setTimeout(function() {
                $("#alert_mssage").hide()
            }, 3000);
            return false;
        }
        return true;
    }


})