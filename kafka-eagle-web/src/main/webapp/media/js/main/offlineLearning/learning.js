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