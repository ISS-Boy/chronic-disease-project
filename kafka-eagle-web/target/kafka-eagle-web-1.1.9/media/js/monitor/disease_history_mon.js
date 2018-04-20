
var myChart = echarts.init(document.getElementById('main'));
myChart.showLoading();
setData()
 function setData(){
     var initData = [];
    $.ajax({
        type : "get",
        async : true,
        url : "/ke/monitor/disease_history_mon/setData",
        data : {},
        dataType : "json", //返回数据形式为json

        success : function(result) {
            if (result) {

                var month;
                var valueM
                var valueF;
                for(var i=0;i<result.length;i++) {
                    month = result[i].split(":")[0];
                    valueF = result[i].split(":")[1];
                    valueM = result[i].split(":")[2];
                    initData.push(valueF+":"+valueM)
                }

                var fData = function() {
                    var data = [];
                    for (var i = 0; i < initData.length; i++) {
                        data.push(parseInt(initData[i].split(":")[0]));
                    }

                    return data;
                }();
                var mData = function() {
                    var data = [];
                    for (var i = 0; i < initData.length; i++) {
                        data.push(parseInt(initData[i].split(":")[1]));
                    }
                    return data;
                }();
                var m_NumData = function() {
                    var data = [];
                    for (var i = 0; i < initData.length; i++) {
                        data.push(parseInt(initData[i].split(":")[0])+parseInt(initData[i].split(":")[1]));
                    }

                    return data;
                }();
                var NumData = function() {
                    var data = 0;

                    for (var i = 0; i < initData.length; i++) {
                        data = data+parseInt(initData[i].split(":")[0])+parseInt(initData[i].split(":")[1]);
                    }

                    return data;
                }();
                var xData = function() {
                    var data = [];
                    for (var i = 1; i < 13; i++) {
                        data.push(i + "月份");
                    }
                    return data;
                }();

                myChart.hideLoading();
                myChart.setOption({  backgroundColor: "#344b58",
                    "title": {
                        "text": "本年患者人数总计"+NumData+"人",

                        x: "4%",

                        textStyle: {
                            color: '#fff',
                            fontSize: '22'
                        },
                        subtextStyle: {
                            color: '#90979c',
                            fontSize: '16',

                        },
                    },
                    "tooltip": {
                        "trigger": "axis",
                        "axisPointer": {
                            "type": "shadow",
                            textStyle: {
                                color: "#fff"
                            }

                        },
                    },
                    "grid": {
                        "borderWidth": 0,
                        "top": 110,
                        "bottom": 95,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    "legend": {
                        x: '4%',
                        top: '11%',
                        textStyle: {
                            color: '#90979c',
                        },
                        "data": ['女', '男', '平均']
                    },


                    "calculable": true,
                    "xAxis": [{
                        "type": "category",
                        "axisLine": {
                            lineStyle: {
                                color: '#90979c'
                            }
                        },
                        "splitLine": {
                            "show": false
                        },
                        "axisTick": {
                            "show": false
                        },
                        "splitArea": {
                            "show": false
                        },
                        "axisLabel": {
                            "interval": 0,

                        },
                        "data": xData,
                    }],
                    "yAxis": [{
                        "type": "value",
                        "splitLine": {
                            "show": false
                        },
                        "axisLine": {
                            lineStyle: {
                                color: '#90979c'
                            }
                        },
                        "axisTick": {
                            "show": false
                        },
                        "axisLabel": {
                            "interval": 0,

                        },
                        "splitArea": {
                            "show": false
                        },

                    }],
                    "dataZoom": [{
                        "show": true,
                        "height": 30,
                        "xAxisIndex": [
                            0
                        ],
                        bottom: 30,
                        "start": 10,
                        "end": 80,
                        handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
                        handleSize: '110%',
                        handleStyle:{
                            color:"#d3dee5",

                        },
                        textStyle:{
                            color:"#fff"},
                        borderColor:"#90979c"


                    }, {
                        "type": "inside",
                        "show": true,
                        "height": 15,
                        "start": 1,
                        "end": 35
                    }],
                    "series": [{
                        "name": "女",
                        "type": "bar",
                        "stack": "总量",
                        "barMaxWidth": 35,
                        "barGap": "10%",
                        "itemStyle": {
                            "normal": {
                                "color": "rgba(255,144,128,1)",
                                "label": {
                                    "show": true,
                                    "textStyle": {
                                        "color": "#fff"
                                    },
                                    "position": "insideTop",
                                    formatter: function(p) {
                                        return p.value > 0 ? (p.value) : '';
                                    }
                                }
                            }
                        },
                        "data": fData,
                    },

                        {
                            "name": "男",
                            "type": "bar",
                            "stack": "总量",
                            "itemStyle": {
                                "normal": {
                                    "color": "rgba(0,191,183,1)",
                                    "barBorderRadius": 0,
                                    "label": {
                                        "show": true,
                                        "position": "top",
                                        formatter: function(p) {
                                            return p.value > 0 ? (p.value) : '';
                                        }
                                    }
                                }
                            },
                            "data": mData,

                        },
                        {
                            "name": "总人数",
                            "type": "line",
                            "stack": "总量",
                            symbolSize:10,
                            symbol:'circle',

                            "itemStyle": {
                                "normal": {
                                    "color": "rgba(252,230,48,1)",
                                    "barBorderRadius": "",
                                    "label": {
                                        "show": true,
                                        "position": "top",
                                        formatter: function(p) {
                                            return p.value > 0 ? (p.value) : '';
                                        }
                                    }
                                }
                            },
                            "data": m_NumData,

                        },
                    ]});
            }
        },
        error : function(errorMsg) {
            alert("获取数据失败")
            console.log(errorMsg)

        }

    });



};



