
function handlerSelect(encounter_id) {
    /*
    * medicationTable
    *
    * */
    var userId = $('.eventDetail_btn').val();
    console.log(userId);
    var medicationTable = $("#medicationDetail").DataTable({
       "ajax": "/ke/patient_analysis/history/getMedicationDetailById?encounter_id="+encounter_id+"&userId="+userId,
        "order": [[ 2, "desc" ]],
        "columnDefs":[{
            "targets":[0],
            "data":[0],
            "render":function (data,type,row,meta){
                console.log(data);
                return  '<input type="button"  class="btn btn-info medicationScb" title="该编码对应的疾病" data-container="body" data-toggle="popover" data-placement="top" value="'+data+'"  />';
                //return "<button class='update btn btn-primary btn-ls' data-toggle='modal' data-target=''>编辑</button> <a class='delete btn btn-danger btn-ls' href='#'>删除</a>";

            }
        }]
    });
        //点击单元格
    $('#medicationDetail tbody').on( 'click', 'td:first-of-type', function () {
        //var cellData = medicationTable.cell( this ).data();
        //var rowValue = table.row(this).data().toString().split(",");//获取某一行，存为数组
        var rxcode = medicationTable.cell( this ).data();//Rxnorm码
        $.ajax({
            url:"/ke/patient_analysis/findRxnormDescription?rxcode="+rxcode,
            type:"get",
            dataType: "json",
            success:function(data){
               // console.log(data);//这里 不执行  后台传过来的是字符串。不是json,放进map 解决
                console.log(data.data);
                // $("[data-toggle='popover']").popover();
                // $(".medicationScb").attr("data-content",data.data);
                $(".medicationScb").popover().click(function() {
                    $(this).attr("data-content",data.data);
                });
            },
            error: function (data) {
                console.log(data);
            }
        })

    } );
    //悬浮在单元格上mouseenter
    // $('#medicationDetail tbody').on( 'mouseenter', 'td:first-of-type', function () {
    //
    // } );

    /*
    * allergyTable
    *
    * */
    var allergyTable =  $("#allergyDetail").DataTable({
        "ajax": "/ke/patient_analysis/history/getAllergyDetailById?encounter_id="+encounter_id+"&userId="+userId
    });

    /*
    * carePlanTable
    *
    * */
    var carePlanTable = $("#carePlanDetail").DataTable({
        "ajax": "/ke/patient_analysis/history/getCarePlanDetailById?encounter_id="+encounter_id+"&userId="+userId,
        "order": [[ 2, "desc" ]],
        "columnDefs":[{
            "targets":[0],
            "data":[0],
            "render":function (data,type,row,meta){
                //console.log(data);
                return  '<input type="button"  class="btn btn-info careplanScb" title="该编码对应的疾病" data-container="body" data-toggle="popover" data-placement="top" value="'+data+'"  />';
                //return "<button class='update btn btn-primary btn-ls' data-toggle='modal' data-target=''>编辑</button> <a class='delete btn btn-danger btn-ls' href='#'>删除</a>";

            }
        }]
    });
    $('#carePlanDetail tbody').on( 'click', 'td:first-of-type', function () {
        var scode = carePlanTable.cell( this ).data();//snomed码
        $.ajax({
            url:"/ke/patient_analysis/findSnomedCnomen?scode="+scode,
            type:"get",
            dataType: "json",
            success:function(data){
                //console.log(data);//这里 不执行  后台传过来的是字符串。不是json,放进map 解决
                console.log(data.data);
                // $("[data-toggle='popover']").popover();
                //$(".careplanScb").attr("data-content",data.data);
                $(".careplanScb").popover().click(function() {
                    $(this).attr("data-content",data.data);
                });
            },
            error: function (data) {
                console.log(data);
            }
        })
        //alert(scode);

    } );

    /*
    * conditonTable
    *
    * */
    var conditionTable = $("#conditionDetail").DataTable({
        "ajax": "/ke/patient_analysis/history/getConditionDetailById?encounter_id="+encounter_id+"&userId="+userId,
        "order": [[ 2, "desc" ]],
        "columnDefs":[{
            "targets":[0],
            "data":[0],
            "render":function (data,type,row,meta){
                //console.log(data);
                return  '<input type="button"  class="btn btn-info conditionScb" title="该编码对应的疾病" data-container="body" data-toggle="popover" data-placement="top" value="'+data+'"  />';
                //return "<button class='update btn btn-primary btn-ls' data-toggle='modal' data-target=''>编辑</button> <a class='delete btn btn-danger btn-ls' href='#'>删除</a>";

            }
        }]

    });
    $('#conditionDetail tbody').on( 'click', 'td:first-of-type', function () {
        var scode = conditionTable.cell( this ).data();//snomed码
        $.ajax({
            url:"/ke/patient_analysis/findSnomedCnomen?scode="+scode,
            type:"get",
            dataType: "json",
            success:function(data){
                //console.log(data);//这里 不执行  后台传过来的是字符串。不是json,放进map 解决
                console.log(data.data);
                // $("[data-toggle='popover']").popover();
                 //$(".conditionScb").attr("data-content",data.data);
                if(data.data){
                    $(".conditionScb").popover().click(function() {
                        $(this).attr("data-content",data.data);
                    });
                }else {
                    alert("很抱歉，暂且查不到该编码对应的信息！");
                }
            },
            error: function (data) {
                console.log(data);
            }
        })
        //alert(scode);

    } );

    /*
     *immunizationTable
     *
     * */
    var immunizationTable = $("#immunizationDetail").DataTable({
        "ajax": "/ke/patient_analysis/history/getImmunizationDetailById?encounter_id="+encounter_id+"&userId="+userId
    });

    $("#ObservationDetail").DataTable({
        "ajax": "/ke/patient_analysis/history/getObservationDetailById?encounter_id="+encounter_id+"&userId="+userId,
        "sScrollX":"100%",
        "columns": [
            { "data": "body_height" },
            { "data": "body_mass_index" },
            { "data": "body_weight" },
            { "data": "calcium" },
            { "data": "carbon_dioxide" },
            { "data": "chloride" },
            { "data": "creatinine" },
            { "data": "estimated_glomerular_filtration_rate" },
            { "data": "glucose" },
            { "data": "hemoglobin_A1c_or_hemoglobin_total_in_blood" },
            { "data": "high_density_lipoprotein_cholesterol" },
            { "data": "low_density_lipoprotein_cholesterol" },
            { "data": "microalbumin_creatinine_ratio" },
            { "data": "potassium" },
            { "data": "sodium" },
            { "data": "systolic_blood_pressure" },
            { "data": "total_cholesterol" },
            { "data": "triglycerides" },
            { "data": "urea_uitrogen" },
        ]

    });

    $.fn.dataTable.ext.errMode = 'throw';
}


