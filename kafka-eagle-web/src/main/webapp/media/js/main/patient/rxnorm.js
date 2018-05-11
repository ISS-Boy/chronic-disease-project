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

    $("#rxnorm_create-btn").click(function(){
        var rxnorm = {};

        var rxcode = $('#ke_rxnorm_code').val();
        var rxDescription = $('#ke_rxDescription').val();
        var helpCode = $('#ke_rxnorm_helpCode').val();
        if(rxcode.length==0||rxDescription.length==0||helpCode.length==0){
            $("#alert_rxnorm_add_mssage").show();
            setTimeout(function() {
                $("#alert_rxnorm_add_mssage").hide();
            }, 3000);
            return false;
        }else{
            rxnorm.rxcode = rxcode;
            rxnorm.rxDescription = rxDescription;
            rxnorm.helpCode = helpCode;
            console.log(rxnorm);
            $.ajax({
                url:"/ke/patient_analysis/rxnorm_add",
                dataType:"json",
                type:"POST",
                contentType: "application/json",
                data:JSON.stringify(rxnorm),
                success : function (data) {
                    if(data){
                        //console.log("添加成功");
                        alert("添加成功");
                        location.reload();
                    }else{
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
    $('#deleterxnorm').click( function () {
        var rowValue = table.row('.selected').data();
        // console.log();
        if(rowValue){
            var code = rowValue[1];
            console.log(code);
            if(confirm("确定要删除吗？")){
                $.ajax({
                    url:"/ke/rxnorm/deleterxnormByCode?code="+code,
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
    $('#modifyrxnorm').click(function () {
        var rowValue = table.row('.selected').data();
        console.log(rowValue);
        if(rowValue){
            $('#ke_rxnorm_modify_dialog').modal('show');
            var id = rowValue[0];
            var rxcode = rowValue[1];
            var rxDescription = rowValue[2];
            var helpCode = rowValue[3];
            $('#ke_modify_rxnorm_id').val(id);
            $('#ke_modify_rxnorm_code').val(rxcode);
            $('#ke_modify_diseaseName').val(rxDescription);
            $('#ke_modify_rxnorm_helpCode').val(helpCode);
            //console.log(snomedCode+snomedCnomen+snomedHelpCode);
        }else{
            alert("请选择一条记录操作");
            //$('#ke_snomed_modify_dialog').modal('hide');
        }
    })

    $('#rxnorm_modify-btn').click(function () {
        var rxnorm = {};
        rxnorm.id = $('#ke_modify_rxnorm_id').val();
        rxnorm.rxcode = $('#ke_modify_rxnorm_code').val();
        rxnorm.rxDescription =  $('#ke_modify_diseaseName').val();
        rxnorm.helpCode = $('#ke_modify_rxnorm_helpCode').val();
        $.ajax({
            url: '/ke/patient_analysis/rxnorm_modify',
            dataType:'json',
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(rxnorm),
            success: function(data){
                if(data) {
                    $('#ke_rxnorm_modify_dialog').modal('hide');
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