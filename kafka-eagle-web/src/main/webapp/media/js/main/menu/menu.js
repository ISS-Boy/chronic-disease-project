
function addMenu(){
    var diag = new Dialog();
    diag.Width = 555;
    diag.Height = 344;
    diag.Title = "新增菜单";
    diag.URL = "toAdd";
    diag.CancelEvent = function(){ //关闭事件
        window.location.href="/ke/menu/menu";
        diag.close();
    };
    diag.show();
}



function saveMenu() {
	if ($('#menuName').val() == "") {
        $("#menuName").tips({
            side:3,
            msg:'请填写菜单名称',
            bg:'#AE81FF',
            time:3
        });
        $("#menuName").focus();
		return;
	}
    if ($('#menuUrl').val() == "") {
        $("#menuUrl").tips({
            side:3,
            msg:'请填写资源路径',
            bg:'#AE81FF',
            time:3
        });
        $("#menuUrl").focus();
        return;
    }
    if ($('#menuOrder').val() == "") {
        $("#menuOrder").tips({
            side:3,
            msg:'请填写菜单顺序',
            bg:'#AE81FF',
            time:3
        });
        $("#menuOrder").focus();
        return;
    }

    $('#addMenuDialog').submit();
}


function editMenu(id){
    var diag = new Dialog();
    diag.Width = 555;
    diag.Height = 344;
    diag.Title = "编辑菜单";
    diag.URL = "/ke/menu/toEdit?id=" + id;
    diag.CancelEvent = function(){ //关闭事件
        window.location.href="/ke/menu/menu";
        diag.close();
    };
    diag.show();
}


function delMenu(id) {
    if(confirm("确定要删除该菜单吗？其下子菜单将一并删除！")){
        var url = "/ke/menu/delMenu?id=" + id;
        $.get(url,function(data){
            document.location.reload();
        });
    }
}

