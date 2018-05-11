$(document).ready(function() {
    var table = $("#loincTable").DataTable({
        "ajax":"/ke/patient_analysis/getLoinclist/ajax"
    });

    $('#loincTable tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );

    //添加loinc
    $('#loinc_create-btn').on('click',function () {
        var url = '/ke/patient_analysis/loinc_add';
        var loincCode = $('#ke_loincCode').val();
        var loincComponent = $('#ke_loincComponent').val();
        var loincProperty = $('#ke_loincProperty').val();
        var loinc = {};
        loinc.loincCode=loincCode;
        loinc.loincComponent =loincComponent;
        loinc.loincProperty =loincProperty;
        if(loincCode.length==0||loincComponent.length==0||loincProperty.length==0){
            $("#alert_mssage_loinc_add").show();
            setTimeout(function() {
                $("#alert_mssage_loinc_add").hide();
            }, 3000);
            return false;
        }else {
            $.ajax({
                url: url,
                dataType: 'json',
                type: 'POST',
                contentType: "application/json",
                data: JSON.stringify(loinc),
                success: function (data) {
                    console.log('====data loinc===', data);
                    if(data){
                        alert("添加成功");
                    }else {
                        alert("编码已存在");
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
    $('#deleteloinc').click( function () {
        var rowValue = table.row('.selected').data();
        // console.log();
        if(rowValue){
            var code = rowValue[1];
            console.log(code);
            if(confirm("确定要删除吗？")){
                $.ajax({
                    url:"/ke/loinc/deleteLoincByCode?code="+code,
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

    //修改
    $('#modifyLoinc').click(function () {
        var rowValue = table.row('.selected').data();
        console.log(rowValue);
        if(rowValue){
            $('#ke_loinc_modify_dialog').modal('show');
            var id = rowValue[0];
            var loincCode = rowValue[1];
            var loincComponent = rowValue[2];
            var loincProperty = rowValue[3];
            $('#ke_modify_loinc_id').val(id);
            $('#ke_modify_loincCode').val(loincCode);
            $('#ke_modify_loincComponent').val(loincComponent);
            $('#ke_modify_loincProperty').val(loincProperty);
            //console.log(snomedCode+snomedCnomen+snomedHelpCode);
        }else{
            alert("请选择一条记录操作");
            //$('#ke_snomed_modify_dialog').modal('hide');
        }
    })

    $('#loinc_modify-btn').click(function () {
        var loinc = {};
        loinc.id = $('#ke_modify_loinc_id').val();
        loinc.loincCode = $('#ke_modify_loincCode').val();
        loinc.loincComponent =  $('#ke_modify_loincComponent').val();
        loinc.loincProperty = $('#ke_modify_loincProperty').val();
        console.log(loinc);
        $.ajax({
            url: '/ke/patient_analysis/loinc_modify',
            dataType:'json',
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(loinc),
            success: function(data){
                if(data) {
                    $('#ke_loinc_modify_dialog').modal('hide');
                    alert("修改成功");
                    location.reload();
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