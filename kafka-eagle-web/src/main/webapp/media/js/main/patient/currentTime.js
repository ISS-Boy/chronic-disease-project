
    setInterval(function show(){
        var d = new Date();
        var year= d.getFullYear();
        var month = d.getMonth()+1;
        var day = d.getDate();
        var hour=d.getHours();       //获取当前小时数(0-23)
        var minute=d.getMinutes();     //获取当前分钟数(0-59)
        var second=d.getSeconds();
        now =year+"年"+month+"月"+day+"日  "+hour+"时"+minute+"分"+second+"秒";
        $('.current-time').html(now);
    },1000);


