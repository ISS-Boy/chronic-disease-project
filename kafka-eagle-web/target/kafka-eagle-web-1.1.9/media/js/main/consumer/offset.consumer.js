$(document).ready(function() {
	var url = window.location.href;
	var tmp = url.split("offset/")[1];
	var group = tmp.split("/")[0];
	var topic = tmp.split("/")[1];
	$("#topic_name_header").find("strong").html("<a href='/ke/consumers/offset/" + group + "/" + topic + "/realtime'>" + topic + "</a>");

	var offset = 0;

	function offsetDetail() {
		$("#offset_topic_info").append("<div id='div_children" + offset + "'><table id='result_children" + offset + "' class='table table-bordered table-hover' width='100%'><thead><tr><th>Partition</th><th>LogSize</th><th>Offset</th><th>Lag</th><th>Owner</th><th>Created</th><th>Modify</th></tr></thead></table></div>");
		if (offset > 0) {
			$("#div_children" + (offset - 1)).remove();
		}
		$("#result_children" + offset).dataTable({
			"searching" : false,
			"bSort" : false,
			"bLengthChange" : false,
			"bProcessing" : true,
			"bServerSide" : true,
			"fnServerData" : retrieveData,
			"sAjaxSource" : "/ke/consumer/offset/" + group + "/" + topic + "/ajax",
			"aoColumns" : [ {
				"mData" : 'partition'
			}, {
				"mData" : 'logsize'
			}, {
				"mData" : 'offset'
			}, {
				"mData" : 'lag'
			}, {
				"mData" : 'owner'
			}, {
				"mData" : 'created'
			}, {
				"mData" : 'modify'
			} ]
		});

		offset++;
	}
    function retrieveData(sSource, aoData, fnCallback) {
        function retrieveData(sSource, aoData, fnCallback) {
            function retrieveData(sSource, aoData, fnCallback) {
                function retrieveData(sSource, aoData, fnCallback) {

	function retrieveData(sSource, aoData, fnCallback) {
		$.ajax({
			"type" : "get",
			"contentType" : "application/json",
			"url" : sSource,
			"dataType" : "json",
			"data" : {
				aoData : JSON.stringify(aoData)
			},
			"success" : function(data) {
				fnCallback(data)
			}
		});
	}

	offsetDetail();

	// 5s/per to the background service request details of the state of offset .
	// setInterval(offsetDetail, 1000 * 30);
});