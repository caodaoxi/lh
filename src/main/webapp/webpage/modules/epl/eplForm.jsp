<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>风控规则配置</title>
	<!-- 装饰:默认 -->
	<meta name="decorator" content="default"/>
</head>
<body>
	<form:form id="inputForm" modelAttribute="epl" action="${ctx}/rtc/epl/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="thresholdListStr" name="thresholdListStr"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>规则编号:</label></td>
		         <td class="width-35" ><form:input id="eplId" path="eplId" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>规则名称:</label></td>
		         <td  class="width-35" ><form:input path="eplName" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>规则父类型:</label></td>
		         <td  class="width-35" >
					<div style="position:relative;">
						<span style="margin-left:220px;width:18px;overflow:hidden;">
							<form:select path="parentId" style="width:238px;margin-left:-220px" class="form-control m-b" onchange="onchange2(this);">
								<form:option value="" label=""/>
								<form:options items="${parentTypes}" itemLabel="parentName" itemValue="parentId" htmlEscape="false"/>
							</form:select>
						</span>
						<form:input path="parentName" htmlEscape="false" maxlength="50" class="form-control required" style="width:220px;position:absolute;left:0px;"/>
					</div>
		         </td>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>启用状态:</label></td>
		         <td  class="width-35" >
		         	<form:radiobuttons path="status" items="${fns:getDictList('epl_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
		         </td>
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>SQL:</label></td>
				 <td class="width-35" colspan="3"><form:textarea path="epl" htmlEscape="false" rows="2" class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>中文描述模板:</label></td>
				 <td class="width-35" colspan="3"><form:textarea path="textState" htmlEscape="false" rows="2" maxlength="200" class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right">规则描述:</label></td>
				 <td class="width-35" colspan="3"><form:textarea path="eplDescribe" htmlEscape="false" rows="2" maxlength="200" class="form-control"/></td>
		      </tr>
		</tbody>
		</table>
	</form:form>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  var isBool = false;
			  var epl = {"eplId":$("#eplId").val(), "parentId": $("#parentId").val(), "eplName": $("#eplName").val(), "epl": $("#epl").val(), "status": $("#status").val(), 
					  "textState": $("#textState").val(), "eplDescribe" : $("#eplDescribe").val()};
			  if($("#id").val() != '') epl.id = $("#id").val();
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
			if($(item).find("option:selected").val() == 1){
				$($(item).parent().parent().find("td")[2]).show();
				$($(item).parent().parent().find("td")[3]).show();
			}else{
				$($(item).parent().parent().find("td")[2]).hide();
				$($(item).parent().parent().find("td")[3]).hide();
			}
		}
		
		function onchange2(item){
			$(item).parent().parent().find("input").val($(item).find("option:selected").text());
		}
	</script>
</body>
</html>