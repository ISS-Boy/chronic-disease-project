$(document).ready(function() {

    $("#ke-add-user-btn").click(function () {
        $('#ke_user_add_dialog').modal('show');

    });

    $(".modify-dt").click(function () {
        $("#ke_user_modify_dialog").modal('show');
        var id =$(this).val();
        /*var url = "/ke/system/datatest/findDataById";*/
       $.ajax({
           type:'get',
           dataType:'json',
           url:'/ke/system/datatest/findDataById/' +id+'/ajax',
           success : function(datas){
               $("#ke_rtxno_name_modify").val(datas.dNumb);
               $("#ke_real_name_modify").val(datas.name);
               $("#ke_user_name_modify").val(datas.description);
               $("#ke_user_email_modify").val(datas.value);
               $("#ke_user_id_modify").val(id);
           }
       })
    })

})


