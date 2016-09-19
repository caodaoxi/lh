<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>风控规则配置</title>
	<!-- 装饰:默认 -->
	<meta name="decorator" content="default"/>
</head>
<body>
	<form:form id="inputForm" modelAttribute="epl" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>规则编号:</label></td>
		         <td class="width-35" ><form:input id="eplId" path="eplId" htmlEscape="false" maxlength="50" class="form-control required" disabled="true" /></td>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>规则名称:</label></td>
		         <td  class="width-35" ><form:input path="eplName" htmlEscape="false" maxlength="50" class="form-control required" disabled="true" /></td>
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>规则父类型:</label></td>
		         <td  class="width-35" >
					<div style="position:relative;">
						<span style="margin-left:220px;width:18px;overflow:hidden;">
							<form:select path="parentId" style="width:238px;margin-left:-220px" class="form-control m-b" disabled="true">
								<form:option value="" label=""/>
								<form:options items="${parentTypes}" itemLabel="parentName" itemValue="parentId" htmlEscape="false"/>
							</form:select>
						</span>
						<form:input path="parentName" htmlEscape="false" maxlength="50" class="form-control required" style="width:220px;position:absolute;left:0px;" disabled="true" />
					</div>
		         </td>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>启用状态:</label></td>
		         <td  class="width-35" >
		         	<form:radiobuttons path="status" items="${fns:getDictList('epl_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required" disabled="true" />
		         </td>
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>SQL:</label></td>
				 <td class="width-35" colspan="3"><form:textarea path="epl" htmlEscape="false" rows="2" class="form-control required" disabled="true" /></td>
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right"><font color="red">*</font>中文描述模板:</label></td>
				 <td class="width-35" colspan="3"><form:textarea path="textState" htmlEscape="false" rows="2" maxlength="200" class="form-control required" disabled="true" /></td>
		      </tr>
		      <tr>
		         <td  class="width-10 active"><label class="pull-right">规则描述:</label></td>
				 <td class="width-35" colspan="3"><form:textarea path="eplDescribe" htmlEscape="false" rows="2" maxlength="200" class="form-control" disabled="true" /></td>
		      </tr>
		</tbody>
		</table>
	</form:form>
</body>
</html>