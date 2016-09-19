<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>外部数据源配置</title>
	<meta name="decorator" content="default"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox">
<div class="ibox-title">
		<h5>外部数据源列表</h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="form_basic.html#">
				<i class="fa fa-wrench"></i>
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
	<form:form id="searchForm" action="${ctx}/rtc/db/list" method="post"  class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
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
			<shiro:hasPermission name="rtc:db:add">
				<table:addRow url="${ctx}/rtc/db/form" title="外部数据源" height="550px" width="950px"></table:addRow><!-- 增加按钮 -->
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
				<th><div style="width:auto;text-align:center;" >编号</div></th>
				<th><div style="width:auto;text-align:center;">用户名</div></th>
				<th><div style="width:auto;text-align:center;">类型</div></th>
				<th><div style="width:auto;text-align:center;">名称</div></th>
				<th><div style="width:auto;text-align:center;">URL</div></th>
				<th><div style="width:auto;text-align:center;">描述</div></th>
				<th><div style="width:auto;text-align:center;">管理方式</div></th>
				<th><div style="width:auto;text-align:center;">连接池</div></th>
				<th><div style="width:auto;text-align:center;">连接池大小</div></th>
				<th><div style="width:auto;text-align:center;">清理策略</div></th>
				<th><div style="width:auto;text-align:center;">缓存周期</div></th>
				<th><div style="width:auto;text-align:center;">清理间隔</div></th>
				<th><div style="text-align:center;" >操作</div></th>
			</tr>
		</thead>
		<tbody><%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
		<c:choose>
			<c:when test="${not empty page.list}">
				<c:forEach items="${page.list}" var="db">
					<tr>
						<td>${db.id}</td>
						<td>${db.username}</td>
						<td>${fns:getDictLabel(db.dbType, 'db_type', '')}</td>
						<td>${db.dbName}</td>
						<td>${db.dbUrl}</td>
						<td>${db.dbDescribe}</td>
						<td>${db.connectionLifecycle}</td>
						<td>${fns:getDictLabel(db.sourceFactory, 'db_source_factory', '')}</td>
						<td>${db.initialSize}</td>
						<td>${db.cacheReferenceType}</td>
						<td>${db.maxAgeSeconds}</td>
						<td>${db.purgeIntervalSeconds}</td>
						<td style="text-align: center;">
								<shiro:hasPermission name="rtc:db:view">
									<a href="#" onclick="openDialogView('外部数据源详情', '${ctx}/rtc/db/form1?id=${db.id}','950px', '550px')" class="btn btn-info btn-xs" ><i class="fa fa-search"></i>详情</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="rtc:db:edit">
									<a href="#" onclick="openDialog('编辑外部数据源', '${ctx}/rtc/db/form?id=${db.id}','950px', '550px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>编辑</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="rtc:db:del">
									<a href="${ctx}/rtc/db/delete?id=${db.id}" onclick="return confirmx('确定要删除该数据源吗？', this.href)" class="btn btn-danger btn-xs" ><i class="fa fa-trash"></i>删除</a>
								</shiro:hasPermission>
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