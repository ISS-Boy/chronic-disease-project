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
    //添加
    $("#cvx_create-btn").click(function(){
        var cvx = {};
        var cvxcode = $('#ke_add_cvx_code').val();
        var cvxDescription = $('#ke_add_cvxDescription').val();
        var helpCode = $('#ke_add_helpCode').val();
        if(cvxcode.length==0||cvxDescription.length==0||helpCode.length==0){
            $("#alert_cvx_add_mssage").show();
            setTimeout(function() {
                $("#alert_cvx_add_mssage").hide();
            }, 3000);
            return false;
        }else{

            cvx.cvxcode = cvxcode;
            cvx.cvxDescription = cvxDescription;
            cvx.helpCode = helpCode;
            console.log(cvx);
            $.ajax({
                url:"/ke/patient_analysis/cvx_add",
                dataType:"json",
                type:"POST",
                contentType: "application/json",
                data:JSON.stringify(cvx),
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
    $('#deleteCvx').click( function () {
        var rowValue = table.row('.selected').data();
        if(rowValue){
            var code = rowValue[1];
            if(confirm("确定要删除吗？")){
                $.ajax({
                    url:"/ke/cvx/deleteCvxByCode?code="+code,
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
    $('#modifyCvx').click(function () {
        var rowValue = table.row('.selected').data();
        if(rowValue){
            $('#ke_cvx_modify_dialog').modal('show');
            var id = rowValue[0];
            var cvxcode = rowValue[1];
            var cvxDescription = rowValue[2];
            var cvxHelpCode = rowValue[3];
            $('#ke_modify_cvx_id').val(id);
            $('#ke_modify_cvx_code').val(cvxcode);
            $('#ke_modify_cvxDescription').val(cvxDescription);
            $('#ke_modify_cvx_helpCode').val(cvxHelpCode);
        }else{
            alert("请选择一条记录操作");
        }
    })
//修改
    $('#cvx_modify-btn').click(function () {
        var snomed = {};
        snomed.id = $('#ke_modify_cvx_id').val();
        snomed.cvxcode = $('#ke_modify_cvx_code').val();
        snomed.cvxDescription =  $('#ke_modify_cvxDescription').val();
        snomed.helpCode = $('#ke_modify_cvx_helpCode').val();
        $.ajax({
            url: '/ke/patient_analysis/cvx_modify',
            dataType:'json',
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(snomed),
            success: function(data){
                if(data) {
                    $('#ke_cvx_modify_dialog').modal('hide');
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
});