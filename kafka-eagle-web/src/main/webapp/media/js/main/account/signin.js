$(document).ready(function() {
	$('#pwd').bind('focus', function() {
		$(this).attr('type', 'password');
	});

	$('#pwd').bind('keypress', function(event) {
		if (event.keyCode == "13") {
			contextFormValid();
		}
	});
	$("a[id='submit']").click(function() {
		contextFormValid();
	});
    changeCode();
    $("#codeImg").bind("click", changeCode);
});


$(document).keyup(function(event) {
    if (event.keyCode == 13) {
        $("#to-recover").trigger("click");
    }
});

function genTimestamp() {
    var time = new Date();
    return time.getTime();
}

function changeCode() {
    $("#codeImg").attr("src", "/ke/code?t=" + genTimestamp());
}