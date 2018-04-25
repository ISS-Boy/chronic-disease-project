$(document).ready(function() {
    var monitorTable = $("#monitor").DataTable({
        "ajax": "/ke/monitor/monitorGroupList",
        "columns": [
            { "data": "monitorGroupId" },
            { "data": "createTime", "render": function (data) {
                return new Date(data)
            }},
            { "data": "creator" },
            { "data": "state" },
            { "data": "imageId" },
            { "data": "serviceId" },
        ],
        "columnDefs":[{
            "targets":[6],
            "data": "monitorGroupId",
            "render":function (data,type,row,meta){
                console.log(data);
                return  '<button type="button" class="btn-xs btn-success-outline runMonitor" onclick="window.location.href=\'/ke/monitor/runMonitorGroup?monitorGroupId=' + data +'\'">运行</button>'+
                    '<button type="button" class="btn-success btn-xs showMonitors" onclick="window.location.href=\'/ke/monitor/showMonitorGroup?monitorGroupId=' + data +'\'" value="'+data+'">查看</button>' +
                    '<button type="button" class="btn-danger btn-xs delMonitor" onclick="window.location.href=\'/ke/monitor/deleteMonitorGroup?monitorGroupId=' + data +'\'">删除</button>' +
                    '<button type="button" class="btn-danger-outline btn-xs stopMonitor" onclick="window.location.href=\'/ke/monitor/stopMonitorGroup?monitorGroupId=' + data +'\'">停止</button>';
            }

        }]

    });
});

function runM(mid) {
    alert(mid);
}