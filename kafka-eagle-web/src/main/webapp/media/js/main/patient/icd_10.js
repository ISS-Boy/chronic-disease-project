$(document).ready(function() {
    var table = $("#icd_10").DataTable({
        "ajax":"/ke/patient_analysis/icdlist/ajax",

    });
    $('#icd_10 tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );
    //添加icd
    $("#Icd_create-btn").click(function(){
        var icd = {};

        var icdCode = $('#ke_icd_code').val();
        var diseaseName = $('#ke_diseaseName').val();
        var helpCode = $('#ke_helpCode').val();
        if(icdCode.length==0||diseaseName.length==0||helpCode.length==0){
            $("#alert_mssage_icdadd").show();
            setTimeout(function() {
                $("#alert_mssage_icdadd").hide();
            }, 3000);
            return false;
        }else{

            icd.icdCode = icdCode;
            icd.diseaseName = diseaseName;
            icd.helpCode = helpCode;
            console.log(icd);
            $.ajax({
                url:"/ke/patient_analysis/icd_add",
                dataType:"json",
                type:"POST",
                contentType: "application/json",
                data:JSON.stringify(icd),
                success : function (data) {
                    if(data){
                        //console.log("添加成功");
                        alert("添加成功");
                        location.reload();
                    }else{
                        //alert("编码已存在！");
                        // $('#ke_icd_add_dialog').modal('show');
                        // $("#alert_mssage_icdadderror").show();
                        // setTimeout(function() {
                        //     $("#alert_mssage_icdadderror").hide();
                        // }, 3000);
                        alert("编码已存在！");
                    }
                },
                error:function (data)  {
                    console.log(data);
                    alert("error");
                }
            })
        }
    })
    //删除
    $('#deleteIcd').click( function () {
        var rowValue = table.row('.selected').data();
        // console.log();
        if(rowValue){
            var code = rowValue[1];
            console.log(code);
            if(confirm("确定要删除吗？")){
                $.ajax({
                    url:"/ke/icd/deleteIcdByCode?code="+code,
                    type:"get",
                    success: function (data) {
                        if(data === 'success') {
                            alert("删除成功！");
                        }
                    }
                })
            }
        }else{
            alert("请选择一条记录操作！");
        }

        table.row('.selected').remove().draw( false );
    } );
    //修改 ke_icd_modify_dialog
    $('#modifyIcd').click(function () {
        var rowValue = table.row('.selected').data();
        console.log(rowValue);
        if(rowValue){
            $('#ke_icd_modify_dialog').modal('show');
            var id = rowValue[0];
            var icdCode = rowValue[1];
            var diseaseName = rowValue[2];
            var helpCode = rowValue[3];
            $('#ke_modify_icd_id').val(id);
            $('#ke_modify_icd_code').val(icdCode);
            $('#ke_modify_diseaseName').val(diseaseName);
            $('#ke_modify_helpCode').val(helpCode);
            //console.log(snomedCode+snomedCnomen+snomedHelpCode);
        }else{
            alert("请选择一条记录操作");
            //$('#ke_snomed_modify_dialog').modal('hide');
        }
    })

    $('#Icd_modify-btn').click(function () {
        var icd = {};
        icd.id = $('#ke_modify_icd_id').val();
        icd.icdCode = $('#ke_modify_icd_code').val();
        icd.diseaseName =  $('#ke_modify_diseaseName').val();
        icd.helpCode = $('#ke_modify_helpCode').val();
        $.ajax({
            url: '/ke/patient_analysis/icd_modify',
            dataType:'json',
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(icd),
            success: function(data){
                if(data) {
                    $('#ke_icd_modify_dialog').modal('hide');
                    location.reload();
                    alert("修改成功");
                } else {
                    alert("编码已存在");
                }
            },
            error: function (data) {
                console.log(data)
            }
        })
    })

})