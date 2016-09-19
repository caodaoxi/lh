<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>规则配置</title>
	<!-- 装饰:默认 -->
	<meta name="decorator" content="default"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox">
<div class="ibox-title">
		<h5>规则配置</h5>
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
	<form:form id="searchForm" action="${ctx}/rtc/epl/list" modelAttribute="epl" method="post"  class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div class="form-group">
			<span>关键词：&nbsp;</span>
			<form:input path="keyword" id="keyword" htmlEscape="false" maxlength="20" class="form-control input-sm"/>
			<span>所属类别：</span>
			<form:select id="parentId" path="parentId" class="form-control m-b" style="width:204px;margin-right: 79px;">
				<form:option value="" label=""/>
				<form:options items="${parentTypes}" itemLabel="parentName" itemValue="parentId" htmlEscape="false"/>
			</form:select>
		 </div>
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="rtc:epl:add">
				<table:addRow url="${ctx}/rtc/epl/form" title="规则" height="550px"></table:addRow><!-- 增加按钮 -->
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
				<th><div style="width:auto;text-align:center;">所属类别</div></th>
				<th><div style="width:auto;text-align:center;">规则名称</div></th>
				<th><div style="width:auto;text-align:center;">规则描述</div></th>
				<th><div style="width:auto;text-align:center;">状态</div></th>
				<th><div style="width:280px;text-align:center;">操作</div></th>
			</tr>
		</thead>
		<tbody><%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
		<c:choose>
			<c:when test="${not empty page.list}">
				<c:forEach items="${page.list}" var="epl">
					<tr>
						<td>${epl.eplId}</td>
						<td>${epl.parentName}</td>
						<td>${epl.eplName}</td>
						<td>
							<span title="${epl.eplDescribe}">${fns:abbr(epl.eplDescribe,88)}</span>
						</td>
						<td>
							<c:choose>
								<c:when test="${epl.status == 1}">
									启用
								</c:when>
								<c:otherwise>
									停用
								</c:otherwise>
							</c:choose>
						</td>
						<td>
								<shiro:hasPermission name="rtc:epl:view">
									<a href="#" onclick="openDialogView('规则详情', '${ctx}/rtc/epl/form1?id=${epl.id}','800px', '550px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i>详情</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="rtc:epl:edit">
									<a href="#" onclick="openDialog('规则编辑', '${ctx}/rtc/epl/form?id=${epl.id}','800px', '550px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>编辑</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="rtc:epl:del">
									<a href="${ctx}/rtc/epl/delete?id=${epl.id}&eplId=${epl.eplId}" onclick="return confirmx('确定要删除该规则吗？', this.href);" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i>删除</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="rtc:epl:add">
									<a href="#" onclick="openDialogView('阀值列表', '${ctx}/rtc/epl/threshold?eplId=${epl.eplId}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i>阀值</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="epl:threshold:view">
									<a href="#" onclick="openDialogView('阀值列表', '${ctx}/rtc/epl/thresholdEdit?eplId=${epl.eplId}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>阀值修改</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="rtc:epl:changeStatus">
									<c:choose>
										<c:when test="${epl.status == 1}">
											<a href="${ctx}/rtc/epl/changeStatus?id=${epl.id}&status=${epl.status}&eplId=${epl.eplId}&isAlarm=${epl.isAlarm}" onclick="return confirmx('确定要停用该规则吗？', this.href)" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>停用</a>
										</c:when>
										<c:otherwise>
											<a href="${ctx}/rtc/epl/changeStatus?id=${epl.id}&status=${epl.status}&eplId=${epl.eplId}&isAlarm=${epl.isAlarm}" onclick="return confirmx('确定要启用该规则吗？', this.href)" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>启用</a>
										</c:otherwise>
									</c:choose>
								</shiro:hasPermission>
								<shiro:hasPermission name="rtc:epl:alarm">
									<a href="#" onclick="openDialog('报警设置', '${ctx}/rtc/epl/alarmForm?id=${epl.id}&eplId=${epl.eplId}&isAlarm=${epl.isAlarm}&status=${epl.status}','800px', '600px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>报警设置</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="rtc:epl:alarm">
									<c:choose>
										<c:when test="${epl.isAlarm == 1}">
											<a href="${ctx}/rtc/epl/deleteAlarm?id=${epl.id}&eplId=${epl.eplId}&status=${epl.status}" onclick="return confirmx('确定要取消报警吗？', this.href)"  class="btn btn-danger btn-xs" ><i class="fa fa-edit"></i>取消报警</a>
										</c:when>
									</c:choose>
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