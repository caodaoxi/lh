<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>风控规则配置</title>
	<!-- 装饰:默认 -->
	<meta name="decorator" content="default"/>
</head>
<body>
	<form:form id="inputForm" modelAttribute="db" action="${ctx}/rtc/db/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>用户名:</label></td>
		         <td class="width-35" ><form:input path="username" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>密码:</label></td>
		         <td  class="width-35" ><form:input path="dbPassword" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>数据库名称:</label></td>
		         <td  class="width-35" ><form:input path="dbName" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>数据库类型:</label></td>
		         <td  class="width-35" >
		         	<form:select path="dbType" class="form-control m-b required" maxlength="50">
		         		<form:option value="" label=""/>
						<form:options items="${fns:getDictList('db_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
		         </td>
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>数据库URL:</label></td>
		         <td  class="width-35" ><form:input path="dbUrl" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>数据库描述:</label></td>
		         <td  class="width-35" ><form:input path="dbDescribe" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>连接管理方式:</label></td>
		         <td  class="width-35" >
		         	<form:select path="connectionLifecycle" class="form-control m-b required" maxlength="50">
		         		<form:option value="" label=""/>
						<form:options items="${fns:getDictList('db_connection_lifecycle')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
		         </td>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>连接池类型:</label></td>
		         <td  class="width-35" >
		         	<form:select path="sourceFactory" class="form-control m-b required" maxlength="50">
		         		<form:option value="" label=""/>
						<form:options items="${fns:getDictList('db_source_factory')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
		         </td>				
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>连接池初始大小:</label></td>
		         <td  class="width-35" ><form:input path="initialSize" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>缓存清理策略:</label></td>
		         <td  class="width-35" >
		         	<form:select path="cacheReferenceType" class="form-control m-b required" maxlength="50">
		         		<form:option value="" label=""/>
						<form:options items="${fns:getDictList('db_cache_reference_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
		         </td>
		      </tr>
		     <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>缓存最大生命周期:</label></td>
		         <td  class="width-35" ><form:input path="maxAgeSeconds" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>缓存清理时间间隔:</label></td>
		         <td  class="width-35" ><form:input path="purgeIntervalSeconds" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right">数据库其他相关属性:</label></td>
				 <td class="width-35" colspan="3"><form:textarea path="dbProps" htmlEscape="false" rows="2" maxlength="200" class="form-control"/></td>
		      </tr>
		</tbody>
		</table>
	</form:form>
		
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
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
	</script>
</body>
</html>