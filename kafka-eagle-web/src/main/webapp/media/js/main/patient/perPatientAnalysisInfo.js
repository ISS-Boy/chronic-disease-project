$(function () {
    $('#personalInfoTab a:first').tab('show');

})
$('#personalInfoTab a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
})