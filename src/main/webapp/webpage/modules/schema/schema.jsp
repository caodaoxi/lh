<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>数据源格式配置列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		$(document).ready(function() {
	        //外部js调用
	        laydate({
	            elem: '#beginDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	    })
	</script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox">
<div class="ibox-title">
		<h5>数据源格式配置</h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="form_basic.html#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" action="${ctx}/rtc/schema/list" method="post"  class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>关键词：&nbsp;</span>
				<input id="keyword" name="keyword" type="text" maxlength="20" class="form-control input-sm"/>
		 </div>
	</form:form>
	<br/>
	</div>
	</div>
	
	
		<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:log:del">
				<table:addRow url="${ctx}/rtc/schema/form" title="菜单"></table:addRow><!-- 增加按钮 -->
				<%--<table:exportExcel url="${ctx}/data/report/exportStockCustomerStatistics" ></table:exportExcel><!-- 删除按钮 -->--%>
				<%--<button class="btn btn-white btn-sm "  onclick="confirmx('确认要清空日志吗？','${ctx}/sys/log/empty')"  title="清空"><i class="fa fa-trash"></i> 清空</button>--%>
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th><div style="width:auto;text-align:center;" >ID</div></th>
				<th><div style="width:auto;text-align:center;">格式编码</div></th>
				<th><div style="width:auto;text-align:center;">Schema名称</div></th>
				<th><div style="width:auto;text-align:center;" >Schema描述</div></th>
				<th><div style="width:120px;text-align:center;" >操作</div></th>
			</tr>
		</thead>
		<tbody><%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
		<c:choose>
			<c:when test="${not empty page.list}">
				<c:forEach items="${page.list}" var="schema">
					<tr>
						<td>${schema.id}</td>
						<td>${schema.schemaId}</td>
						<td>${schema.schemaName}</td>
						<td>${schema.schemaDescribe}</td>
						<td style="text-align: center;width:209px;">
								<a href="#" onclick="openDialogView('字段列表', '${ctx}/rtc/schema/field?schemaId=${schema.schemaId}','300px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i>字段</a>
								<a href="#" onclick="openDialog('编辑Schema', '${ctx}/rtc/schema/form?id=${schema.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>编辑</a>
								<a href="${ctx}/rtc/schema/delete?id=${schema.id}&schemaId=${schema.schemaId}" onclick="return confirmx('要删除该Schema吗？', this.href)" class="btn btn-danger btn-xs" ><i class="fa fa-trash"></i>删除</a>
						</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="main_info">
					<td colspan="100" class="center" align="center">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
	
	<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>