function addRow(eplId){
	var table = $("#contentTable");
	var tr= $("<tr ondblclick='clickRow(this,"+eplId+")'>" +
			"<td></td>" +
			"<td><input maxlength='50' class='form-control required'/></td>" +
			"<td><input maxlength='50' class='form-control required'/></td>" +
			"<td><input maxlength='50' class='form-control required'/></td>" +
			"<td style='text-align: center;width:110px;'>" +
			"<a href='#' onclick='deleteRow(this, \"${ctx}/rtc/epl/deleteThreshold\", false)' ><i class='fa fa-trash'></i>删除</a>" +
			"<a href='#' onclick='saveRow(this, \"/fk/a/rtc/epl/saveThreshold\", "+eplId+")'><i class='fa fa-edit'></i>保存</a>" +
			"</td></tr>");
	table.append(tr);
}

function deleteRow(row, url, isOld) {
	var td = $(row);
	if(isOld) {
		confirmx('要删除该字段吗？', function(){
			$.ajax({
				type: "POST",
				url: url,
				success: function(msg){
					var td = $(row);
					td.parent().parent().remove();
				}
			});

		});

	} else {
		td.parent().parent().remove();
	}
}

function saveRow(row, url, eplId) {
	var tds = $(row).parent().parent().find("td input");
	var parent = $(row).parent().parent();
	var rowId = $(parent.find("td")[0]).attr("value");
	var threshold = {
		id:rowId,	
		eplId:eplId,
		thresholdName:$(tds[0]).val(),
		thresholdValue:$(tds[1]).val(),
		thresholdDescribe:$(tds[2]).val(),
	};
	confirmx('确定要保存该阀值吗？', function(){
		$.ajax({
			type: "POST",
			data:threshold,
			url: url,
			success: function(msg){
				var data = msg;
				if(data instanceof  String){
					data=eval("("+msg+")");
				}
				
				var ts = "<td value='" + data.id + "'>" + data.id + "</td>" +
				"<td value='" + data.thresholdName + "'>" + data.thresholdName + "</td>" +
				"<td value='" + data.thresholdValue + "'>" + data.thresholdValue + "</td>" +
				"<td value='" + data.thresholdDescribe + "'>" + data.thresholdDescribe + "</td>" +
				"<td style='text-align: center;width:110px;'>"+
				"<a href='${ctx}/rtc/epl/deleteThreshold?id="+data.id+"&eplId="+data.eplId+"' onclick='return confirmx('确定要删除该阀值吗？', this.href);' >"+
				"<i class='fa fa-trash'></i>删除</a></td>";
				parent.empty();
				parent.append($(ts));
			}
		});

	});
}

function clickRow(row,eplId) {
	var parent = $(row).parent();
	var tds = $(row).find("td");
	var tr= $("<td value='" + $(tds[0]).attr("value") + "'>" + $(tds[0]).attr("value") + "</td>" +
			"<td><input value='" + $(tds[1]).attr("value") + "' maxlength='50' class='form-control required'/></td>" +
			"<td><input value='" + $(tds[2]).attr("value") + "'maxlength='50' class='form-control required'/></td>" +
			"<td><input value='" + $(tds[3]).attr("value") + "'maxlength='50' class='form-control required'/></td>" +
			"<td style='text-align: center;width:110px;'>" +
			"<a href='#' onclick='deleteRow(this,\"${ctx}/rtc/epl/deleteThreshold?id=" + $(tds[0]).attr("value") + "\", true)' ><i class='fa fa-trash'></i>删除</a>" +
			"<a href='#' onclick='saveRow(this, \"/fk/a/rtc/epl/saveThreshold?eplId="+eplId+"\")'><i class='fa fa-edit'></i>保存</a>" +
			"</td>");
	$(row).empty();
	$(row).append($(tr));
};