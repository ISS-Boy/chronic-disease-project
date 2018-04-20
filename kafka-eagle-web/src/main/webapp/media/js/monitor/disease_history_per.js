
var data = [];
$.ajax({
    type : "get",
    async : true,
    url : "/ke/monitor/disease_history_per/setData",
    data : {},
    dataType : "json", //返回数据形式为json

    success : function(result) {
        if (result) {
            console
            var hypNum;
            var preNum
            var corNum;
            for(var i=0;i<result.length;i++) {
                hypNum = parseInt(result[0])
                preNum = parseInt(result[1])
                corNum = parseInt(result[2])

            }
            data.push({"value":hypNum,'name':'高血压'})
            data.push({"value":preNum,'name':'糖尿病'})
            data.push({"value":corNum,'name':'冠心病'})
            myChart.hideLoading();
            myChart.setOption(option,true);

        }
    },
    error : function(errorMsg) {
        alert("获取数据失败")
        myChart.hideLoading();


    }

});

var myChart = echarts.init(document.getElementById('main'));
myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画


option = {
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'left',
        data:['高血压','糖尿病','冠心病']
    },
    series: [
        {
            name:'访问来源',
            type:'pie',
            selectedMode: 'single',
            radius: [0, '30%'],

            label: {
                normal: {
                    position: 'inner'
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:data
        },
        {
            name:'访问来源',
            type:'pie',
            radius: ['40%', '55%'],

            data:data
        }
    ]
};
