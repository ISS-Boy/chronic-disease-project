

function setdata() {
    require.config({
        paths: {
            echarts: "/ke/media/js/track/echarts",
        },
    });

    require(
        [
            "echarts",
            "echarts/chart/main",
            "echarts/chart/map",
        ],
        function (echarts, BMapExtension) {

            // 初始化地图
            var BMapExt = new BMapExtension($('#main')[0], BMap, echarts,{
                enableMapClick: false
            })
            var map = BMapExt.getMap();
            var container = BMapExt.getEchartsContainer();

            var startPoint = {
                x: startlo,
                y:startla
            };
            var point = new BMap.Point(startPoint.x, startPoint.y);
            map.centerAndZoom(point, 18);
            map.enableScrollWheelZoom(true);
            map.setMapStyle({style:'normal'});
            //所有的属性参数都是存放于option对象中具体，最后通过setOption方法即可将自己定义的option实例化
            option = {
                color: ['gold','aqua','lime'],
                title : {
                    text: '',
                    subtext: '',
                    x:'center',
                    textStyle : {
                        color: '#fff',
                        fontSize:20,
                        fontWeight:'bold',
                    }
                },
                tooltip : {
                    show: false,
                    trigger:'item',
                    hideDelay:4000,
                    formatter: function(d) {
                        var jw= '经度：'+d.value[0]+'<br/>'
                        jw += '纬度：'+d.value[1]
                        return jw
                    }
                },
                color:['gold'],
                legend:{
                    data:['用户实时轨迹'],
                    x:'left',
                    orient:'vertical',
                    padding:[30,15,15,30],
                    textStyle:{
                        fontSize:17,
                        color:'rgb(204,204,204)',
                    },
                    selected:{
                        '用户实时轨迹':true,

                    },
                    selectedMode:'single',
                },
                series : [
                    {
                        name:'用户实时轨迹',
                        type:'map',
                        mapType: 'none',
                        data:[],

                        markLine : {
                            Symbol:['none', 'arrow'],
                            symbolSize:['0', '0.1'],
                            smooth:true,
                            smooth:0,
                            effect : {
                                show: true,
                                scaleSize: 1,
                                period: 30,
                                color: '#fff',
                                shadowBlur: 10
                            },
                            itemStyle : {
                                color: 'red',
                                normal: {
                                    color:function(param){
                                        return(param.data[0].value.colorValue);
                                    },
                                    borderWidth:3,
                                    lineStyle: {
                                        type: 'solid',
                                        width: 3,
                                        shadowBlur: 10
                                    },
                                    label:{show:false,value:''}
                                }
                            },
                            data : [
                                [{name:'p1'}, {name:'p2',value:{colorValue:'gold'}}],
                                [{name:'p2'}, {name:'p3',value:{colorValue:'gold'}}],
                                [{name:'p3'}, {name:'p4',value:{colorValue:'gold'}}],
                                [{name:'p4'}, {name:'p5',value:{colorValue:'gold'}}],
                                [{name:'p5'}, {name:'p6',value:{colorValue:'gold'}}],
                                [{name:'p6'}, {name:'p7',value:{colorValue:'gold'}}],
                                [{name:'p7'}, {name:'p8',value:{colorValue:'gold'}}],
                                [{name:'p8'}, {name:'p9',value:{colorValue:'gold'}}],
                                [{name:'p9'}, {name:'p10',value:{colorValue:'gold'}}]
                            ]
                        },
                        markPoint:{
                            symbolSize:function(v){
                                return v/5
                            },
                            effect:{
                                show:true,
                                type:'bounce',
                                period:3,
                            },
                            itemStyle:{
                                normal:{
                                    label:{
                                        show:false,
                                    },
                                },
                                emphasis:{
                                    label:{
                                        show:false,
                                    },
                                },
                            },
                            data:[
                                {name:'p10',value:30,
                                    tooltip:{
                                        formatter:''
                                    },
                                },

                            ],
                        },
                        geoCoord:data
                    },

                ]
            };
            var myChart = BMapExt.initECharts(container);
            window.onresize = myChart.onresize;
            BMapExt.setOption(option);
        }
    );
};
var data =[];
var startlo ;
var startla ;
function getdata() {
    var value1 = $('#user_id').val();
    $.ajax({
        type : "get",
        async : true,
        url : "/ke/monitor/longtitude/setData?value1="+value1,
        data : {},
        dataType : "json", //返回数据形式为json
        success : function(result) {
            if (result) {
                startlo= parseFloat(result[9].split(",")[0]);
                startla =parseFloat(result[9].split(",")[1]);
                data ={
                    'p1':[parseFloat(result[0].split(",")[0]),parseFloat(result[0].split(",")[1])],
                    'p2':[parseFloat(result[1].split(",")[0]),parseFloat(result[1].split(",")[1])],
                    'p3':[parseFloat(result[2].split(",")[0]),parseFloat(result[2].split(",")[1])],
                    'p4':[parseFloat(result[3].split(",")[0]),parseFloat(result[3].split(",")[1])],
                    'p5':[parseFloat(result[4].split(",")[0]),parseFloat(result[4].split(",")[1])],
                    'p6':[parseFloat(result[5].split(",")[0]),parseFloat(result[5].split(",")[1])],
                    'p7':[parseFloat(result[6].split(",")[0]),parseFloat(result[6].split(",")[1])],
                    'p8':[parseFloat(result[7].split(",")[0]),parseFloat(result[7].split(",")[1])],
                    'p9':[parseFloat(result[8].split(",")[0]),parseFloat(result[8].split(",")[1])],
                    'p10':[parseFloat(result[9].split(",")[0]),parseFloat(result[9].split(",")[1])]
                }
                setdata()
            }

        },
        error : function(errorMsg) {
            console.log("获取数据失败")
        }
    });
}
getdata()
setInterval("getdata()",60000)
