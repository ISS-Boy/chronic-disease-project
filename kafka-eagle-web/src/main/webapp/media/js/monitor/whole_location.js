var myChart = echarts.init(document.getElementById('main'));
myChart.showLoading();
setData();
function setData(){
    var value1 = $('#year').val();
    var value2 = $('#disease').val();
    $.ajax({
        type: "get",
        async: true, //异步执行
        url: "/ke/monitor/whole_country/setData?value1="+value1+"&value2="+value2,
        data: {},
        dataType: "json", //返回数据形式为json
        success: function (result) {
            if (result) {
                console.log(result)
                var value;
                var valueF;
                var valueM
                var name;
                var name_title = $('#year').val()+"各地区"+$('#disease').val()+"新增患者统计"
                var subname = ''
                var nameColor = " rgb(55, 75, 113)"
                var name_fontFamily = '等线'
                var subname_fontSize = 15
                var name_fontSize = 18
                var mapName = 'china'
                var data = []
                var toolTipData = []
                var geoCoordMap = {};
                /*获取地图数据*/
                myChart.showLoading();
                var mapFeatures = echarts.getMap(mapName).geoJson.features;
                myChart.hideLoading();
                mapFeatures.forEach(function (v) {
                    // 地区名称
                    var name = v.properties.name;
                    console.log(name)
                    // 地区经纬度
                    geoCoordMap[name] = v.properties.cp;

                });
                var max = 480,
                    min = 9; // todo
                var maxSize4Pin = 100,
                    minSize4Pin = 20;
                var convertData = function (data) {
                    var res = [];
                    for (var i = 0; i < data.length; i++) {
                        var geoCoord = geoCoordMap[data[i].name];
                        if (geoCoord) {
                            res.push({
                                name: data[i].name,
                                value: geoCoord.concat(data[i].value),
                            });
                        }
                    }
                    return res;
                };
                for (var i = 0; i < result.length; i++) {
                    valueF = parseInt(result[i].split(":")[1]);
                    valueM = parseInt(result[i].split(":")[2]);
                    value = valueF + valueM;
                    name = result[i].split(":")[0];
                    data.push({"name": name, "value": value});
                    console.log(data)
                    toolTipData.push({
                        "name": name,
                        "value": [{"name": "男性患者", "value": valueF}, {"name": "女性患者", "value": valueM}]
                    });
                }

                myChart.hideLoading();
                myChart.setOption({
                    title: {
                        text: name_title,
                        subtext: subname,
                        x: 'center',
                        textStyle: {
                            color: nameColor,
                            fontFamily: name_fontFamily,
                            fontSize: name_fontSize
                        },
                        subtextStyle: {
                            fontSize: subname_fontSize,
                            fontFamily: name_fontFamily
                        }
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: function (params) {
                            if (typeof(params.value)[2] == "undefined") {
                                var toolTiphtml = ''
                                for (var i = 0; i < toolTipData.length; i++) {
                                    if (params.name == toolTipData[i].name) {
                                        toolTiphtml += toolTipData[i].name + ':<br>'
                                        for (var j = 0; j < toolTipData[i].value.length; j++) {
                                            toolTiphtml += toolTipData[i].value[j].name + ':' + toolTipData[i].value[j].value + "<br>"
                                        }
                                    }
                                }
                                return toolTiphtml;
                            } else {
                                var toolTiphtml = ''
                                for (var i = 0; i < toolTipData.length; i++) {
                                    if (params.name == toolTipData[i].name) {
                                        toolTiphtml += toolTipData[i].name + ':<br>'
                                        for (var j = 0; j < toolTipData[i].value.length; j++) {
                                            toolTiphtml += toolTipData[i].value[j].name + ':' + toolTipData[i].value[j].value + "<br>"
                                        }
                                    }
                                }

                                // console.log(convertData(data))
                                return toolTiphtml;
                            }
                        }
                    },
                    visualMap: {
                        show: true,
                        min: 0,
                        max: 1000,
                        left: 'left',
                        top: 'bottom',
                        text: ['高', '低'], // 文本，默认为数值文本
                        calculable: true,
                        seriesIndex: [1],
                        inRange: {
                            color: ['#00467F', '#A5CC82'] // 蓝绿
                        }
                    },
                    geo: {
                        show: true,
                        map: mapName,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: false,
                            }
                        },
                        roam: true,
                        itemStyle: {
                            normal: {
                                areaColor: '#031525',
                                borderColor: '#3B5077',
                            },
                            emphasis: {
                                areaColor: '#2B91B7',
                            }
                        }
                    },
                    series: [{
                        name: '散点',
                        type: 'scatter',
                        coordinateSystem: 'geo',
                        data: convertData(data),
                        symbolSize: 0,
                        label: {
                            normal: {
                                formatter: '{b}',
                                position: 'right',
                                show: true
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: '#05C3F9'
                            }
                        }
                    },
                        {
                            type: 'map',
                            map: mapName,
                            geoIndex: 0,
                            aspectScale: 0.75, //长宽比
                            showLegendSymbol: false, // 存在legend时显示
                            label: {
                                normal: {
                                    show: true
                                },
                                emphasis: {
                                    show: false,
                                    textStyle: {
                                        color: '#fff'
                                    }
                                }
                            },
                            roam: true,
                            itemStyle: {
                                normal: {
                                    areaColor: '#031525',
                                    borderColor: '#3B5077',
                                },
                                emphasis: {
                                    areaColor: '#2B91B7'
                                }
                            },
                            animation: false,
                            data: data
                        },
                        {
                            name: '点',
                            type: 'scatter',
                            coordinateSystem: 'geo',
                            symbol: 'pin', //气泡
                            symbolSize: 50,
                            label: {
                                normal: {
                                    show: true,
                                    textStyle: {
                                        color: '#fff',
                                        fontSize: 9,
                                    }
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: '#F62157', //标志颜色
                                }
                            },
                            zlevel: 6,
                            data: convertData(data),
                        },
                        {
                            name: 'Top 5',
                            type: 'effectScatter',
                            coordinateSystem: 'geo',
                            data: convertData(data.sort(function (a, b) {
                                return b.value - a.value;
                            }).slice(0, 34)),
                            symbolSize: 10,
                            showEffectOn: 'render',
                            rippleEffect: {
                                brushType: 'stroke'
                            },
                            hoverAnimation: true,
                            label: {
                                normal: {
                                    formatter: '{b}',
                                    position: 'right',
                                    show: true
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: 'yellow',
                                    shadowBlur: 10,
                                    shadowColor: 'yellow'
                                }
                            },
                            zlevel: 1
                        },

                    ]
                });

            }

        },
        error: function (errorMsg) {
            myChart.hideLoading();
        }
    });
};




