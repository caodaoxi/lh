<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>事件详情</title>
	<!-- 装饰:默认 -->
	<meta name="decorator" content="default"/>
</head>
<body>
	<form:form id="inputForm" modelAttribute="triggerRecord" action="${ctx}/trigger/record/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>事件类型：</label></td>
		         <td class="width-35" ><form:input path="eplName" htmlEscape="false" maxlength="50" disabled="true" class="form-control required"/></td>
		         <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>发生时间：</label></td>
		         <td class="width-35" ><form:input path="triggerTime" htmlEscape="false" maxlength="50" disabled="true" class="form-control required"/></td>
		      </tr>
		       <tr>
		         <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>资金账号：</label></td>
		         <td class="width-35" ><form:input path="fundid" htmlEscape="false" maxlength="50" disabled="true" class="form-control required"/></td>
		         <td  class="width-15 active">	<label class="pull-right">所属机构：</label></td>
		         <td class="width-35" ><form:input path="orgname" htmlEscape="false" maxlength="50" disabled="true" class="form-control required"/></td>
		      </tr>

			  <tr>
				  <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>规则描述:</label></td>
				  <td class="width-35" colspan="3"><form:textarea disabled="true" path="eplDescribe" htmlEscape="false" rows="2" maxlength="200" class="form-control"/></td>
			  </tr>
			  <tr>
				  <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>事件描述:</label></td>
				  <td class="width-35" colspan="3"><form:textarea disabled="true" path="triggerState" htmlEscape="false" rows="2" maxlength="200" class="form-control"/></td>
			  </tr>
			  <tr>
				  <td  class="width-15 active">	<label class="pull-right">交易详情：</label></td>
				  <td class="width-35" colspan="3"><table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					  <thead>
					  <tr>
						  <th>所属市场</th>
						  <th>股票代码</th>
						  <th>订单价格</th>
						  <th>订单数量</th>
						  <th>用户IP</th>
						  <th>交易时间</th>
					  </tr>
					  </thead>
					  <tbody>
					  <c:forEach items="${details}" var="detail">
						  <tr>
							  <td>
									  ${detail.market}
							  </td>
							  <td>
									  ${detail.stkcode}
							  </td>
							  <td>
									  ${detail.orderprice}
							  </td>
							  <td>
									  ${detail.orderqty}
							  </td>
							  <td>
									  ${detail.stationaddr}
							  </td>
							  <td>
								  <fmt:formatDate value="${detail.dt}" pattern="yyyy-MM-dd HH:mm:ss"/>
							  </td>
						  </tr>
					  </c:forEach>
					  </tbody>
				  </table>
			  </tr>
			  <tr>
				  <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>处理状态:</label></td>
				  <td class="width-35"  colspan="3"><form:radiobuttons path="status" items="${fns:getDictList('trigger_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/></td>
			  </tr>
			  <c:if test="${triggerRecord.status eq '3'}">
			  <tr>
				  <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>处理意见:</label></td>
				  <td class="width-35" colspan="3"><textarea name="opinion" htmlEscape="false" rows="2" maxlength="200" class="form-control"></textarea></td>
			  </tr>
			  </c:if>
			  <c:if test="${triggerRecord.status ne '3'}">
				  <tr>
					  <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>处理意见:</label></td>
					  <td class="width-35" colspan="3"><form:textarea path="opinion" disabled="true" htmlEscape="false" rows="2" maxlength="200" class="form-control"/></td>
				  </tr>
			  </c:if>
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
			//$("#name").focus();
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