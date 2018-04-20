$(document).ready(function() {

    var monitorTable = $("#monitor").DataTable({
        "ajax": "/ke/monitor/monitorList",
        "columns": [
            { "data": "monitor_id" },
            { "data": "monitor_name" },
            { "data": "monitor_group_id" },
            { "data": "json" },
            { "data": "metric_name" },
            { "data": "jar" },
            { "data": "monitor_id" },
        ],
        "columnDefs":[{
            "targets":[6],
            "data": "monitor_id",
            "render":function (data,type,row,meta){
                console.log(data);
                return  '<button class="btn-xs btn-success-outline runMonitor" onclick="runM('+data+')">运行</button>'+
                        '<button class="btn-success btn-xs modifyMonitor" value="'+data+'">修改</button>' +
                        '<button class="btn-danger btn-xs delMonitor" value="'+data+'">删除</button>' +
                        '<button class="btn-danger-outline btn-xs stopMonitor" value="'+data+'">停止</button>';


            }
        }]
    });
    // $('#monitor tbody').on( 'mouseenter', 'td:last-of-type', function () {
    //     //var rowValue = monitorTable.row(this).data();
    //     //console.log(rowValue);
    //     $('.runMonitor').click(function () {
    //         var value = $(this).val();
    //         alert(value);
    //     })
    // } );
    function runM(mid) {
         alert(mid);
    }

});