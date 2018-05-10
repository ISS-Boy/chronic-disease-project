function checkAge() {
    if (0 > $('#ageStart').val()) {
        $("#ageStart").tips({
            side:3,
            msg:'年龄不能小于0',
            bg:'#AE81FF',
            time:3
        });
        $("#ageStart").focus();
        setTimeout("$('#ageStart').val('')",2000);
        return;
    }

    if (0 > $('#ageEnd').val()) {
        $("#ageEnd").tips({
            side:3,
            msg:'年龄不能小于0',
            bg:'#AE81FF',
            time:3
        });
        $("#ageEnd").focus();
        setTimeout("$('#ageEnd').val('')",2000);
        return;
    }

    if ($('#ageStart').val() > $('#ageEnd').val()) {
        $("#ageEnd").tips({
            side:3,
            msg:'年龄起止时间冲突',
            bg:'#AE81FF',
            time:3
        });
        $("#ageEnd").focus();
        setTimeout("$('#ageEnd').val('')",2000);
        return;
    }
}

function searchUsers() {
    $('#searchForm').submit();
}


var userIds="", ages="", gender="", diseases="", metric="", dateTo="";
var kResult=0, rThreshold=0, frequencyThreshold=0, analysisWindowStartSize=3, alphabetSize=0, paaSize=0, slidingWindowSize=0;

function nextStep() {
    userIds = "123456";
    alert("1");
    $.ajax({
        type: "POST",
        url: '/ke/offlineLearning/nextStep',
        data: {userid:userIds},
        dataType:'json',
        cache: false,
        // success: function(data){
        //     if("success" == data.result){
        //         $("#userForm").submit();
        //         $("#zhongxin").hide();
        //         $("#zhongxin2").show();
        //     }else{
        //         $("#loginname").css("background-color","#D16E6C");
        //         setTimeout("$('#loginname').val('此用户名已存在!')",500);
        //     }
        // }
    });
    alert("2");
}


function showme() {
    alert(userIds);
}