var myChart = echarts.init(document.getElementById('main'));
myChart.showLoading();
setdata();

function setdata(){
    var hypNum_2018;
    var preNum_2018;
    var corNum_2018;
    var hypNum_2017;
    var preNum_2017;
    var corNum_2017;
    var hypNum_2016;
    var preNum_2016;
    var corNum_2016;
    var data_hypNum_2018 =[];
    var data_preNum_2018 =[];
    var data_corNum_2018 =[];
    var data_hypNum_2017 =[];
    var data_preNum_2017 =[];
    var data_corNum_2017 =[];
    var data_hypNum_2016 =[];
    var data_preNum_2016 =[];
    var data_corNum_2016 =[];
    var data_hyp;
    var data_pre;
    var data_cor;
    var  dataMap_hyp = {};
    var  dataMap_pre={};
    var  dataMap_cor={};

    $.ajax({
        type : "get",
        async : true,
        url : "/ke/monitor/disease_history_timeline/setData",
        data : {},
        dataType : "json", //返回数据形式为json
        success : function(result) {
            if (result) {
                hypNum_2018 = result[0]["2018h"];
                preNum_2018 = result[0]["2018p"];
                corNum_2018 = result[0]["2018c"];
                hypNum_2017 = result[0]["2017h"];
                preNum_2017 = result[0]["2017p"];
                corNum_2017 = result[0]["2017c"];
                hypNum_2016 = result[0]["2016h"];
                preNum_2016 = result[0]["2016p"];
                corNum_2016 = result[0]["2016c"];
                for (var i = 0; i < hypNum_2018.length; i++) {
                    var value = parseInt(hypNum_2018[i].toString().split(":")[1])
                    data_hypNum_2018.push(value);
                }
                for (var i = 0; i < preNum_2018.length; i++) {
                    data_preNum_2018.push(parseInt(preNum_2018[i].toString().split(":")[1]));
                }
                for (var i = 0; i < corNum_2018.length; i++) {
                    data_corNum_2018.push(parseInt(corNum_2018[i].toString().split(":")[1]));
                }
                for (var i = 0; i < hypNum_2017.length; i++) {
                    data_hypNum_2017.push(parseInt(hypNum_2017[i].toString().split(":")[1]));
                }
                console.log(data_hypNum_2017)
                for (var i = 0; i < preNum_2017.length; i++) {
                    data_preNum_2017.push(parseInt(preNum_2017[i].toString().split(":")[1]));
                }
                for (var i = 0; i < corNum_2017.length; i++) {
                    data_corNum_2017.push(parseInt(corNum_2017[i].toString().split(":")[1]));
                }

                for (var i = 0; i < hypNum_2016.length; i++) {
                    data_hypNum_2016.push(parseInt(hypNum_2016[i].toString().split(":")[1]));
                }
                for (var i = 0; i < preNum_2016.length; i++) {
                    data_preNum_2016.push(parseInt(preNum_2016[i].toString().split(":")[1]));
                }
                for (var i = 0; i < corNum_2016.length; i++) {
                    data_corNum_2016.push(parseInt(corNum_2016[i].toString().split(":")[1]));

                }
                data_hyp = {2018:data_hypNum_2018,
                    2017:data_hypNum_2017,
                    2016:data_hypNum_2016
                };
                data_pre = {2018:data_preNum_2018,
                    2017:data_preNum_2017,
                    2016:data_preNum_2016
                };
                data_cor = {2018:data_corNum_2018,
                    2017:data_corNum_2017,
                    2016:data_corNum_2016
                };


                myChart.hideLoading();
                dataMap_hyp = dataFormatter(data_hyp);
                dataMap_pre= dataFormatter(data_pre);
                dataMap_cor= dataFormatter(data_cor);
                console.log(dataMap_hyp)

                myChart.setOption({   baseOption: {
                        timeline: {
                            // y: 0,
                            axisType: 'category',
                            // realtime: false,
                            // loop: false,
                            autoPlay: true,
                            // currentIndex: 2,
                            playInterval: 3000,
                            // controlStyle: {
                            //     position: 'left'
                            // },
                            data: [
                                '2016','2017','2018',
                            ],
                            label: {
                            }
                        },

                        tooltip: {},
                        legend: {
                            x: 'right',
                            data: ['高血压', '糖尿病', '冠心病'],
                        },
                        calculable : true,
                        grid: {
                            top: 80,
                            bottom: 100
                        },
                        xAxis: [
                            {
                                'type': 'category',
                                'axisLabel': {'interval': 0},
                                'data': [
                                    '北京', '\n天津', '河北', '\n山西', '内蒙古', '\n辽宁', '吉林', '\n黑龙江',
                                    '上海', '\n江苏', '浙江', '\n安徽', '福建', '\n江西', '山东', '\n河南',
                                    '湖北', '\n湖南', '广东', '\n广西', '海南', '\n重庆', '四川', '\n贵州',
                                    '云南', '\n西藏', '陕西', '\n甘肃', '青海', '\n宁夏', '新疆'
                                ],
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value',
                                name: '患者人数',
                                max: 30
                            }
                        ],
                        series: [

                            {name: '高血压', type: 'bar'},
                            {name: '糖尿病', type: 'bar'},
                            {name: '冠心病', type: 'bar'},
                            {
                                name: '全国人数统计',
                                type: 'pie',
                                center: ['75%', '35%'],
                                radius: '28%'
                            }
                        ]
                    },
                    options: [
                        {

                            title: {text: '2016全国患者情况分布'},
                            series: [
                                {data: dataMap_hyp['2016']},
                                {data: dataMap_pre['2016']},
                                {data: dataMap_cor['2016']},
                                {data: [
                                        {name: '高血压', value: dataMap_hyp['2016sum']},
                                        {name: '糖尿病', value: dataMap_pre['2016sum']},
                                        {name: '冠心病', value: dataMap_cor['2016sum']}
                                    ]}
                            ]
                        },
                        {
                            title : {text: '2017全国患者情况分布'},
                            series : [

                                {data: dataMap_hyp['2017']},
                                {data: dataMap_pre['2017']},
                                {data: dataMap_cor['2017']},
                                {data: [
                                        {name: '高血压', value: dataMap_hyp['2017sum']},
                                        {name: '糖尿病', value: dataMap_pre['2017sum']},
                                        {name: '冠心病', value: dataMap_cor['2017sum']}
                                    ]}
                            ]
                        },
                        {
                            title : {text: '2018全国患者情况分布'},
                            series : [

                                {data: dataMap_hyp['2018']},
                                {data: dataMap_pre['2018']},
                                {data: dataMap_cor['2018']},
                                {data: [
                                        {name: '高血压', value: dataMap_hyp['2018sum']},
                                        {name: '糖尿病', value: dataMap_pre['2018sum']},
                                        {name: '冠心病', value: dataMap_cor['2018sum']}
                                    ]}
                            ]
                        },

                    ]});

            }

        },
        error : function(errorMsg) {
            alert("获取数据失败")
            myChart.hideLoading();
        }
    });

}




function dataFormatter(obj) {
    var pList = ['北京','天津','河北','山西','内蒙古','辽宁','吉林','黑龙江','上海','江苏','浙江','安徽','福建','江西','山东','河南','湖北','湖南','广东','广西','海南','重庆','四川','贵州','云南','西藏','陕西','甘肃','青海','宁夏','新疆'];
    var temp;
    for (var year = 2016; year <= 2018; year++) {
        var max = 0;
        var sum = 0;
        temp = obj[year];
        for (var i = 0, l = temp.length; i < l; i++) {
            max = Math.max(max, temp[i]);
            sum += temp[i];
            obj[year][i] = {
                name : pList[i],
                value : temp[i]
            }
        }
        obj[year + 'max'] = Math.floor(max / 100) * 100;
        obj[year + 'sum'] = sum;
    }
    return obj;
}



