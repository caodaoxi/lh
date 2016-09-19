<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>风控规则配置</title>
	<!-- 装饰:默认 -->
	<meta name="decorator" content="default"/>
	<!-- 指定双核浏览器默认以何种方式渲染页面, 默认webkit内核 -->
	<meta name="renderer" content="webkit">
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<!-- 文档兼容模式, 强制IE使用IE8/IE9渲染 -->
	<meta http-equiv="X-UA-Compatible" content="IE=9,IE=10" />
	<!-- 期限: 可以用于设定网页的到期时间。一旦网页过期，必须到服务器上重新传输 -->
	<meta http-equiv="Expires" content="0">
	<!-- 清除缓存 -->
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-store">
	<!-- 表格插件 -->
	<link href="${ctxStatic}/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
	<!-- jeeplus -->
	<link href="${ctxStatic}/common/jeeplus.css" type="text/css" rel="stylesheet" />
	<!-- 引入bootstrap插件 -->
	<link href="${ctxStatic}/bootstrap/3.3.4/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<!-- 图标插件 -->
	<link href="${ctxStatic}/common/css/style.css?v=3.2.0" type="text/css" rel="stylesheet" />
</head>
<body>
	<form:form id="inputForm" modelAttribute="udf" action="${ctx}/rtc/udf/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>函数全类名:</label></td>
		         <td class="width-35" ><form:input id="methodName" path="methodName" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>缓存生命周期:</label></td>
		         <td  class="width-35" ><form:input path="maxAgeSeconds" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>缓存清除策略:</label></td>
		         <td  class="width-35" >
		         	<form:select path="cacheReferenceType" class="form-control m-b required" maxlength="50">
		         		<form:option value="" label="请选择清除策略"/>
						<form:options items="${fns:getDictList('cache_reference_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
		         </td>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>缓存清除间隔:</label></td>
		         <td  class="width-35" ><form:input path="purgeIntervalSeconds" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>函数描述:</label></td>
				 <td class="width-35" colspan="3"><form:textarea path="methodDescribe" htmlEscape="false" rows="2" maxlength="200" class="form-control"/></td>
		      </tr>
		</tbody>
		</table>
	</form:form>
		
	<!-- 引入jquery插件 -->
	<script src="${ctxStatic}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.14.0/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.14.0/localization/messages_zh.min.js" type="text/javascript"></script>
	<!-- 页面加载进度条插件 -->
	<script src="${ctxStatic}/pace/pace.min.js"></script>
	<!-- 表格插件 -->
	<script src="${ctxStatic}/dataTables/jquery.dataTables.min.js"></script>
	<script src="${ctxStatic}/dataTables/dataTables.bootstrap.js"></script>
	<!-- jeeplus -->
	<script src="${ctxStatic}/common/jeeplus.js" type="text/javascript"></script>
	<!-- 引入bootstrap插件 -->
	<script src="${ctxStatic}/bootstrap/3.3.4/js/bootstrap.min.js"  type="text/javascript"></script>
	<!-- 引入自定义文件 -->
	<script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/common/content.js" type="text/javascript"></script>
	<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>
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