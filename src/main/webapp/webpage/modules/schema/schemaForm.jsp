<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>Schema配置</title>
	<meta name="decorator" content="default"/>
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
					"<a href='#' onclick='saveRow(this, \"${ctx}/rtc/schema/saveField\")'><i class='fa fa-edit'></i>保存</a>" +
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
			if(rowId) {
				var ts = "<td value='" + rowId + "'>" + rowId + "</td>" +
						"<td value='" + $(tds[0]).val() + "'>" + $(tds[0]).val() + "</td>" +
						"<td value='" + $(tds[1]).val() + "'>" + $(tds[1]).val() + "</td>" +
						"<td value='" + $(tds[2]).val() + "'>" + $(tds[2]).val() + "</td>" +
						"<td style='text-align: center;width:110px;'><a href='#' onclick='deleteRow(this, \"${ctx}/rtc/schema/deleteField?id=" + $(tds[0]).val() + "\", true)' ><i class='fa fa-trash'></i>删除</a></td>";
			} else {
				var ts = "<td></td>" +
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
			var fieldName = $(inputs[0]).val()===undefined ? $(tds[1]).attr("value") : $(inputs[0]).val();
			var fieldType = $(inputs[1]).val()===undefined ? $(tds[2]).attr("value") : $(inputs[1]).val();
			var fieldDescribe = $(inputs[2]).val()===undefined ? $(tds[3]).attr("value") : $(inputs[2]).val();
			<%--
			if(rowId == '') {
				var tr= $("<td>" + rowId + "</td>" +
						"<td><input value='" + $(tds[1]).attr("value") + "' maxlength='50' class='form-control required'/></td>" +
						"<td><input value='" + $(tds[2]).attr("value") + "'maxlength='50' class='form-control required'/></td>" +
						"<td><input value='" + $(tds[3]).attr("value") + "'maxlength='50' class='form-control required'/></td>" +
						"<td style='text-align: center;width:110px;'>" +
						"<a href='#' onclick='deleteRow(this,\"${ctx}/rtc/schema/deleteField?id=" + $(tds[0]).attr("value") + "\", true)' ><i class='fa fa-trash'></i>删除</a>" +
						"<a href='#' onclick='saveRow(this, \"${ctx}/rtc/schema/saveField?id=" + $(tds[0]).attr("value") + "\")'><i class='fa fa-edit'></i>保存</a>" +
						"</td>");
			} else {
				var tr= $("<td value='" + rowId + "'>" + rowId + "</td>" +
						"<td><input value='" + $(tds[1]).attr("value") + "' maxlength='50' class='form-control required'/></td>" +
						"<td><input value='" + $(tds[2]).attr("value") + "'maxlength='50' class='form-control required'/></td>" +
						"<td><input value='" + $(tds[3]).attr("value") + "'maxlength='50' class='form-control required'/></td>" +
						"<td style='text-align: center;width:110px;'>" +
						"<a href='#' onclick='deleteRow(this,\"${ctx}/rtc/schema/deleteField?id=" + $(tds[0]).attr("value") + "\", true)' ><i class='fa fa-trash'></i>删除</a>" +
						"<a href='#' onclick='saveRow(this, \"${ctx}/rtc/schema/saveField?id=" + $(tds[0]).attr("value") + "\")'><i class='fa fa-edit'></i>保存</a>" +
						"</td>");
			}
--%>
			
			var tr= "<td value='" + rowId + "'>" + rowId + "</td>" +
						"<td value='" + fieldName + "'><input value='" + fieldName + "' maxlength='50' class='form-control required'/></td>" +
						"<td value='" + fieldType + "'><input value='" + fieldType + "'maxlength='50' class='form-control required'/></td>" +
						"<td value='" + fieldDescribe + "'><input value='" + fieldDescribe + "'maxlength='50' class='form-control required'/></td>" +
						"<td style='text-align: center;width:110px;'>" +
						"<a href='#' onclick='deleteRow(this,\"${ctx}/rtc/schema/deleteField?id=" + $(tds[0]).attr("value") + "\", true)' ><i class='fa fa-trash'></i>删除</a>" +
						"<a href='#' onclick='saveRow(this, \"${ctx}/rtc/schema/saveField?id=" + $(tds[0]).attr("value") + "\")'><i class='fa fa-edit'></i>保存</a>" +
						"</td>";
			$(row).empty();
			$(row).append($(tr));
		}
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  var isBool = false;
			  var schema = {"schemaId":$("#schemaId").val(), "schemaName": $("#schemaName").val(), "schemaDescribe" : $("#schemaDescribe").val()};
			  if($("#id").val() != '') schema.id = $("#id").val();
			  var trs = $("#contentTable").find("tbody tr");
			  var schemaFieldList = "";
			  $.each(trs, function(i, tr){
				  var tds = $(tr).find("td");
				  if($(tds[0]).attr("value") != undefined && $(tds[0]).attr("value") != '') {
					  schemaFieldList = schemaFieldList + $(tds[0]).attr("value") + "," + $("#schemaId").val() + "," + $(tds[1]).attr("value") + "," + $(tds[2]).attr("value") + "," + $(tds[3]).attr("value");
				  } else {
					  schemaFieldList = schemaFieldList + "," + $("#schemaId").val() + "," + $(tds[1]).attr("value") + "," + $(tds[2]).attr("value") + "," + $(tds[3]).attr("value");
				  }
				  if(i < trs.size() -1) schemaFieldList = schemaFieldList + "|";
			  });
//			  schema.schemaFieldListStr = schemaFieldList;
			  $("#schemaFieldListStr").val(schemaFieldList)
			  $.ajaxSettings.async = false;
			  $.getJSON("${ctx}/rtc/schema/save", schema, function(json){
				  alert(json);
				  isBool = true;
			  });
			  $("#inputForm").submit();
//			  $.ajaxSettings.async = true;
//			  $('inputForm').submit(schema, function(event) {
//				  alert(1)
//				  return false;
//			  });
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			$("#schemaId").focus();
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
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="schema" action="${ctx}/rtc/schema/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="schemaFieldListStr" name="schemaFieldListStr"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td  class="width-15 active"><label class="pull-right">格式编码:</label></td>
		         <td class="width-35" ><form:input id="schemaId" path="schemaId" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         <td  class="width-15 active"><label class="pull-right">Schema名称:</label></td>
		         <td  class="width-35" ><form:input path="schemaName" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>Schema描述:</label></td>
				 <td class="width-35" colspan="3"><form:textarea path="schemaDescribe" htmlEscape="false" rows="2" maxlength="200" class="form-control"/></td>
		      </tr>
			  <tr>
		         <td  class="width-15 active"><label class="pull-right">字段列表:</label></td>
		         <%--<td class="width-35" ><form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control"/></td>--%>
				  <td class="width-35" colspan="3">
					  <table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable" schemaId="${schema.schemaId}">
					  	<thead>
						  <tr>
							  <th>ID</th>
							  <th>字段名</th>
							  <th>字段类型</th>
							  <th>字段说明</th>
							  <th>操作</th>
						  </tr>
					  	</thead>
					    <tbody>
						  <c:forEach items="${schema.schemaFieldList}" var="schemaField">
							  <tr ondblclick="clickRow(this)">
								  <td value="${schemaField.id}">
									  ${schemaField.id}
								  </td>
								  <td value="${schemaField.fieldName}">
									  ${schemaField.fieldName}
								  </td>
								  <td value="${schemaField.fieldType}">
									  <%--<select>--%>
										  <%--<option value ="String">String</option>--%>
										  <%--<option value ="boolean">boolean</option>--%>
										  <%--<option value="integer">integer</option>--%>
										  <%--<option value="long">long</option>--%>
										  <%--<option value="double">double</option>--%>
										  <%--<option value="double">float</option>--%>
										  <%--<option value="byte">byte</option>--%>
									  <%--</select>--%>
									  ${schemaField.fieldType}
								  </td>
								  <td value="${schemaField.fieldDescribe}">
									  ${schemaField.fieldDescribe}
								  </td>
								  <td style="text-align: center;width:110px;">
									  <a href="#" onclick="deleteRow(this, '${ctx}/rtc/schema/deleteField?id=${schemaField.id}', true)" ><i class="fa fa-trash"></i>删除</a>
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
</body>
</html>