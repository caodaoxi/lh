<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>报警设置</title>
	<!-- 装饰:默认 -->
	<meta name="decorator" content="default"/>
</head>
<body>
	<form:form id="inputForm" modelAttribute="epl" action="${ctx}/rtc/epl/saveAlarm" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="alarmId"/>
		<form:hidden path="status"/>
		<form:hidden path="eplId"/>
		<form:hidden path="alarmEplStr"/>
		<form:hidden path="alarmThresholdStr"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		         <tr>
			         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>报警编号:</label></td>
			         <td  class="width-35" ><form:input path="alarmEplId" htmlEscape="false" maxlength="50" class="form-control required"/></td>
			         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>报警名称:</label></td>
			         <td  class="width-35" ><form:input path="alarmName" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         </tr>
		         <tr>
			         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>报警接收组:</label></td>
			         <td class="width-35" >
			         	<form:select path="alarmGroupId" class="form-control m-b required" maxlength="50">
			         		<form:option value="" label=""/>
			         		<form:options items="${alarmGroups}" itemLabel="groupName" itemValue="groupId" htmlEscape="false"/>
						</form:select>
			         </td>
			         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>报警类型:</label></td>
			         <td class="width-35" >
			         	<form:select path="alarmType" class="form-control m-b required" maxlength="50">
			         		<form:option value="" label=""/>
							<form:options items="${fns:getDictList('epl_alarm_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
			         </td>
				  </tr>
			      <tr>
			         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>报警规则SQL:</label></td>
					 <td class="width-35" colspan="3"><form:textarea path="alarmSQL" htmlEscape="false" rows="2" maxlength="500" class="form-control required"/></td>
			      </tr>
			      <tr>
			         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>报警内容模板:</label></td>
					 <td class="width-35" colspan="3"><form:textarea path="alarmTemplate" htmlEscape="false" rows="2" maxlength="200" class="form-control required"/></td>
			      </tr>
			      <tr>
			         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>报警规则描述:</label></td>
					 <td class="width-35" colspan="3"><form:textarea path="alarmDescribe" htmlEscape="false" rows="2" maxlength="200" class="form-control required"/></td>
			      </tr>
			      <tr>
			         <td  class="width-15 active"><label class="pull-right">报警规则阀值列表:</label></td>
					  <td class="width-35" colspan="3">
						  <table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
						  	<thead>
							  <tr>
								  <th>编号</th>
								  <th>名称</th>
								  <th>阀值</th>
								  <th>描述</th>
								  <th>操作</th>
							  </tr>
						  	</thead>
						    <tbody>
							  <c:forEach items="${epl.alarmThresholdList}" var="alarmThreshold">
								  <tr ondblclick="clickRow(this)">
									  <td value="${alarmThreshold.id}">
										  ${alarmThreshold.id}
									  </td>
									  <td value="${alarmThreshold.thresholdName}">
										  ${alarmThreshold.thresholdName}
									  </td>
									  <td value="${alarmThreshold.thresholdValue}">
										  ${alarmThreshold.thresholdValue}
									  </td>
									  <td value="${alarmThreshold.thresholdDescribe}">
										  ${alarmThreshold.thresholdDescribe}
									  </td>
									  <td style="text-align: center;width:110px;">
										  <a href="#" onclick="deleteRow(this, '${ctx}/rtc/epl/deleteAlarmThreshold?id=${alarmThreshold.id}&status=${epl.status}', true)" ><i class="fa fa-trash"></i>删除</a>
									  </td>
								  </tr>
							  </c:forEach>
						    </tbody>
					      </table>
						  <div id="addtrdiv" style="margin-top:8px; width: 12%;float: right;">
							  <a href="#" onclick="addRow()" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i>添加字段</a>
						  </div>
					  </td>
				  </tr>
		</tbody>
		</table>
	</form:form>
	<script type="text/javascript">
	function addRow(){
		var table = $("#contentTable");
		var tr= $("<tr ondblclick='clickRow(this)'>" +
				"<td></td>" +
				"<td><input maxlength='50' class='form-control required'/></td>" +
				"<td><input maxlength='50' class='form-control required'/></td>" +
				"<td><input maxlength='50' class='form-control required'/></td>" +
				"<td style='text-align: center;width:110px;'>" +
				"<a href='#' onclick='deleteRow(this)' ><i class='fa fa-trash'></i>删除</a>" +
				"<a href='#' onclick='saveRow(this, \"${ctx}/rtc/epl/saveAlarmThreshold?status="+ $("#status").val() + "\")'><i class='fa fa-edit'></i>保存</a>" +
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

	function saveRow(row, url, id) {
		var tds = $(row).parent().parent().find("td input");
		var parent = $(row).parent().parent();
		var rowId = $(parent.find("td")[0]).attr("value");
		var ts = "";
		if(rowId) {
			ts = "<td value='" + rowId + "'>" + rowId + "</td>" +
					"<td value='" + $(tds[0]).val() + "'>" + $(tds[0]).val() + "</td>" +
					"<td value='" + $(tds[1]).val() + "'>" + $(tds[1]).val() + "</td>" +
					"<td value='" + $(tds[2]).val() + "'>" + $(tds[2]).val() + "</td>" +
					"<td style='text-align: center;width:110px;'><a href='#' onclick='deleteRow(this, \"${ctx}/rtc/epl/deleteAlarmThreshold?id=" + $(tds[0]).val() + "&status="+ $("#status").val() + "\", true)' ><i class='fa fa-trash'></i>删除</a></td>";
		} else {
			ts = "<td></td>" +
					"<td value='" + $(tds[0]).val() + "'>" + $(tds[0]).val() + "</td>" +
					"<td value='" + $(tds[1]).val() + "'>" + $(tds[1]).val() + "</td>" +
					"<td value='" + $(tds[2]).val() + "'>" + $(tds[2]).val() + "</td>" +
					"<td style='text-align: center;width:110px;'><a href='#' onclick='deleteRow(this)' ><i class='fa fa-trash'></i>删除</a></td>";
		}
		parent.empty();
		parent.append($(ts));
	};

	function clickRow(row) {
		var tds = $(row).find("td");
		var inputs = $(row).find("td input");
		var rowId = $(tds[0]).attr("value") ? $(tds[0]).attr("value") : '';
		var thresholdName = $(inputs[0]).val()===undefined ? $(tds[1]).attr("value") : $(inputs[0]).val();
		var thresholdValue = $(inputs[1]).val()===undefined ? $(tds[2]).attr("value") : $(inputs[1]).val();
		var thresholdDescribe = $(inputs[2]).val()===undefined ? $(tds[3]).attr("value") : $(inputs[2]).val();
		
		var tr= "<td value='" + rowId + "'>" + rowId + "</td>" +
					"<td value='" + thresholdName + "'><input value='" + thresholdName + "' maxlength='50' class='form-control required'/></td>" +
					"<td value='" + thresholdValue + "'><input value='" + thresholdValue + "'maxlength='50' class='form-control required'/></td>" +
					"<td value='" + thresholdDescribe + "'><input value='" + thresholdDescribe + "'maxlength='50' class='form-control required'/></td>" +
					"<td style='text-align: center;width:110px;'>" +
					"<a href='#' onclick='deleteRow(this,\"${ctx}/rtc/epl/deleteAlarmThreshold?id=" + $(tds[0]).attr("value") + "&status="+ $("#status").val() + "\", true)' ><i class='fa fa-trash'></i>删除</a>" +
					"<a href='#' onclick='saveRow(this, \"${ctx}/rtc/epl/saveAlarmThreshold?id=" + $(tds[0]).attr("value") + "&status=="+ $("#status").val() + "\")'><i class='fa fa-edit'></i>保存</a>" +
					"</td>";
		$(row).empty();
		$(row).append($(tr));
	}
	var validateForm;
	function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
	  if(validateForm.form()){
		  var isBool = false;
		  var epl = {"id":$("#id").val(), "isAlarm": $("#isAlarm").val(), "alarmEplId" : $("#alarmEplId").val(), "alarmName" : $("#alarmName").val(), "alarmGroupId" : $("#alarmGroupId").val(), 
				  "alarmType" : $("#alarmType").val(), "alarmSQL" : $("#alarmSQL").val(), "alarmTemplate" : $("#alarmTemplate").val(), "alarmDescribe" : $("#alarmDescribe").val()};
		  if($("#alarmId").val() != '') epl.id = $("#alarmId").val();
		  var trs = $("#contentTable").find("tbody tr");
		  var alarmThresholdStr = "";
		  $.each(trs, function(i, tr){
			  var tds = $(tr).find("td");
			  if($(tds[0]).attr("value") != undefined && $(tds[0]).attr("value") != '') {
				  alarmThresholdStr = alarmThresholdStr + $(tds[0]).attr("value") + "," + $("#alarmEplId").val() + "," + $(tds[1]).attr("value") + "," + $(tds[2]).attr("value") + "," + $(tds[3]).attr("value");
			  } else {
				  alarmThresholdStr = alarmThresholdStr + "," + $("#alarmEplId").val() + "," + $(tds[1]).attr("value") + "," + $(tds[2]).attr("value") + "," + $(tds[3]).attr("value");
			  }
			  if(i < trs.size() -1) alarmThresholdStr = alarmThresholdStr + "|";
		  });
		  $("#alarmThresholdStr").val(alarmThresholdStr);
		  $("#inputForm").submit();
		  return true;
	  }

	  return false;
	}
	$(document).ready(function() {
		validateForm = $("#inputForm").validate({
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
	});
	function onchange1(item){
		//console.log($(item).find("option:selected").val());
		var trs = $(item).parent().parent().parent().find("tr");

		if($(item).find("option:selected").val() == 1){
			for(var i=1; i<=trs.size(); i++){
				$(trs[i]).show();
			}
		}else{
			for(var i=1; i<=trs.size(); i++){
				$(trs[i]).hide();
			}
		}
	}
	</script>
</body>
</html>