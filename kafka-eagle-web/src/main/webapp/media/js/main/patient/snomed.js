
$(document).ready(function() {
    var table = $("#snomedTable").DataTable({
        "ajax":"/ke/patient_analysis/getSnomedlist/ajax"
    })
    //table.fnSetColumnVis( 0, false);//隐藏列不可访问
    $('#snomedTable tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );



    $('#deleteSnomed').click( function () {
        var rowValue = table.row('.selected').data();
        // console.log();
        if(rowValue){
            var code = rowValue[1];
            console.log(code);
            if(confirm("确定要删除吗？")){
                $.ajax({
                    url:"/ke/snomed/deleteSnomedByCode?code="+code,
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
    //--------------多选
    // $('#snomedTable tbody').on( 'click', 'tr', function () {
    //     $(this).toggleClass('selected');
    // } );
    //
    // $('#deleteSnomed').click( function () {
    //     var values = table.rows('.selected').data().toArray();
    //     console.log(values[0]);
    //     if(values.length>0) {
    //         var ids = [];
    //         for(var i=0;i<values.length;i++) {
    //             ids.push(values[i][0]);
    //         }
    //         console.log(ids);
    //         $.ajax({
    //             url: "/ke/snomed/deteteSnomedByCode",
    //             type:"GET",
    //             data: { ids: JSON.stringify(ids)}
    //         })
    //     } else {
    //         alert("请选择一条记录操作！")
    //     }
    //     table.row('.selected').remove().draw( false );
    // } );
    $('#modifySnomed').click(function () {
        var rowValue = table.row('.selected').data();
        console.log(rowValue);
        if(rowValue){
            $('#ke_snomed_modify_dialog').modal('show');
            var id = rowValue[0];
            var snomedCode = rowValue[1];
            var snomedCnomen = rowValue[2];
            var snomedHelpCode = rowValue[3];
            $('#ke_modify_snomed_id').val(id);
            $('#ke_modify_snomed_code').val(snomedCode);
            $('#ke_modify_snomedCnomen').val(snomedCnomen);
            $('#ke_modify_snomed_helpCode').val(snomedHelpCode);
            //console.log(snomedCode+snomedCnomen+snomedHelpCode);
        }else{
            alert("请选择一条记录操作");
            //$('#ke_snomed_modify_dialog').modal('hide');
        }
    })

    $('#snomed_modify-btn').click(function () {
        var snomed = {};
        snomed.id = $('#ke_modify_snomed_id').val();
        snomed.snomedCode = $('#ke_modify_snomed_code').val();
        snomed.snomedCnomen =  $('#ke_modify_snomedCnomen').val();
        snomed.helpCode = $('#ke_modify_snomed_helpCode').val();
        $.ajax({
            url: '/ke/patient_analysis/snomed_modify',
            dataType:'json',
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(snomed),
            success: function(data){
                if(data) {
                    $('#ke_snomed_modify_dialog').modal('hide');
                    location.reload();//
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
// function checksnomedCode() {
//     var snomedCode = $("#ke_snomed_code").val();
//     $.ajax({
//         url:"/ke/patient_analysis/checkSnomedCode?snomedCode="+snomedCode,
//         dataType:'json',
//         type: 'GET',
//         success: function (data) {
//             if (data) {
//                 $("#ke_snomed_code").tips({
//                     side:3,
//                     msg:'用户已存在',
//                     bg:'#AE81FF',
//                     time:3
//                 });
//                 $("#ke_snomed_code").focus();
//                 setTimeout("$('#ke_snomed_code').val('')",2000);
//                 return;
//             }
//
//         },
//         error: function (data) {
//             alert("error");
//         }
//
//     })
//
//
// }