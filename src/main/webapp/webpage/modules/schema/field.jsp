<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>字段列表</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<sys:message content="${message}"/>
	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		<thead>
		<tr>
			<th><div style="width:auto;text-align:center;">字段名</div></th>
			<th><div style="width:auto;text-align:center;">字段类型</div></th>
			<th><div style="width:auto;text-align:center;">字段描述</div></th>
		</tr>
		</thead>
		<tbody>
	   <c:choose>
		   <c:when test="${not empty page.list}">
			   <c:forEach items="${page.list}" var="schemaField">
				   <tr>
					   <td>${schemaField.fieldName}</td>
					   <td>${schemaField.fieldType}</td>
					   <td>${schemaField.fieldDescribe}</td>
				   </tr>
			   </c:forEach>
		   </c:when>
		   <c:otherwise>
			   <tr class="main_info">
				   <td colspan="100" class="center" align="center">数据格式没有定义</td>
			   </tr>
		   </c:otherwise>
	   </c:choose>
	</tbody>
	</table>
</body>
</html>