<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>事件详情</title>
	<meta name="decorator" content="default"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox">
<div class="ibox-title">
		<h5>事件详情 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
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
		<form:form id="searchForm" modelAttribute="triggerRecord" action="${ctx}/trigger/record/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<div style="margin-bottom: 10px;">
				<span>事件类型：</span>
				<form:select id="eplId" path="eplId" class="form-control m-b" style="width:204px;margin-right: 50px;">
					<form:option value="" label=""/>
					<form:options items="${eplCategories}" itemLabel="eplName" itemValue="eplId" htmlEscape="false"/>
				</form:select>
				<span>处理状态：</span>
				<form:select id="status" path="status"  class="form-control m-b" style="width:204px;margin-right: 50px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('trigger_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span>所属机构：</span>
				<select path="orgid"  class="form-control m-b" style="width:204px;margin-right: 50px">
					<option value=""></option>
					<c:forEach items="${orgList}" var="org">
						<option value="${org.orgid}">${org.orgname}</option>
					</c:forEach>
				</select>
			</div>
			<div>
				<span>资金账号：</span>
				<form:input path="fundid" htmlEscape="false" maxlength="204"  class=" form-control input-sm" style="width:204px;margin-right: 50px;"/>
				<span>开始时间：</span>
				<form:input type="text" path="startDate" id="startDate" name="startDate" class="Wdate" onFocus="WdatePicker({beginDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"  style="width:204px;margin-right: 50px;height: 33px;border: 1px solid #e5e6e7;"/>
				<span>结束时间：</span>
				<form:input type="text"  path="endDate" id="endDate" name="endDate" class="Wdate" onFocus="WdatePicker({endDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"  style="width:204px;margin-right: 50px;height: 33px;border: 1px solid #e5e6e7;"/>
			</div>
		</div>
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<c:if test="${!requestScope.triggerRecord.self}">
			<shiro:hasPermission name="trigger:record:edit">
			    <table:editRow url="${ctx}/trigger/record/form" id="contentTable"  title="记录" width="800px" height="700px"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="trigger:record:del">
				<table:delRow url="${ctx}/trigger/record/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="trigger:record:export">
	       		<table:exportExcel url="${ctx}/trigger/record/export"></table:exportExcel><!-- 导出按钮 -->
	       </shiro:hasPermission>
	      
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		 </c:if>
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>

	<table id="contentTable" class="table table-striped table-bordered  table-hover table-condensed  dataTables-example dataTable no-footer">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th>发生时间</th>
				<th>事件类型</th>
				<th>资金账号</th>
				<th>所属机构</th>
				<th>事件描述</th>
				<th>处理状态</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="record">
			<tr>
				<td> <input type="checkbox" id="${record.id}" class="i-checks"></td>
				<td>${record.triggerTime}</td>
				<td>
					<a  href="#" title="${record.eplDescribe}">${fns:abbr(record.eplName,50)}</a>
				</td>
				<td>${record.fundid}</td>
				<td>${record.orgname}</td>
				<td>${record.triggerState}</td>
				<td>${fns:getDictLabel(record.status, 'trigger_status', '')}</td>
				<td>${record.dealTime}</td>
				<td>
					<shiro:hasPermission name="trigger:record:view">
						<a href="#" onclick="openDialogView('查看详情', '${ctx}/trigger/record/form?id=${record.id}','800px', '700px')" class="btn btn-info btn-xs btn-circle" ><i class="fa fa-search-plus"></i></a>
					</shiro:hasPermission>
					<shiro:hasPermission name="trigger:record:edit">
    					<a href="#" onclick="openDialog('立即处理', '${ctx}/trigger/record/form?id=${record.id}','800px', '700px')" class="btn btn-success btn-xs btn-circle" ><i class="fa fa-edit"></i></a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="trigger:record:del">
						<a href="${ctx}/trigger/record/delete?id=${record.id}" onclick="return confirmx('确认要删除该记录吗？', this.href)"   class="btn btn-danger btn-xs btn-circle"><i class="fa fa-trash"></i></a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>

<!-- 时间插件 -->
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</body>
</html>