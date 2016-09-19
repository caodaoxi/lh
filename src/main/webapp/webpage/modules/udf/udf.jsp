<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>自定义函数配置</title>
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
	<link href="${ctxStatic}/awesome/4.4/css/font-awesome.min.css" rel="stylesheet" />
	<link href="${ctxStatic}/common/css/style.css?v=3.2.0" type="text/css" rel="stylesheet" />
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox">
<div class="ibox-title">
		<h5>自定义函数配置</h5>
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
			<%--<span>关键词：&nbsp;</span>--%>
			<%--<form:input path="keyword" id="keyword" htmlEscape="false" maxlength="20" class="form-control input-sm"/>--%>
			<%--<span>所属类别：</span>--%>
			<%--<form:select id="parentId" path="parentId" class="form-control m-b" style="width:204px;margin-right: 79px;">--%>
				<%--<form:option value="" label=""/>--%>
				<%--<form:options items="${parentTypes}" itemLabel="parentName" itemValue="parentId" htmlEscape="false"/>--%>
			<%--</form:select>--%>
		 </div>
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="rtc:udf:add">
				<table:addRow url="${ctx}/rtc/udf/form" title="自定义函数"></table:addRow><!-- 增加按钮 -->
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
				<th><div style="width:auto;text-align:center;">方法全类名</div></th>
				<th><div style="width:auto;text-align:center;">方法描述</div></th>
				<th><div style="width:auto;text-align:center;">缓存清除策略</div></th>
				<th><div style="width:auto;text-align:center;">缓存生命周期</div></th>
				<th><div style="width:auto;text-align:center;">缓存清理间隔</div></th>
				<th><div style="width:auto;text-align:center;">操作</div></th>
			</tr>
		</thead>
		<tbody><%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
		<c:choose>
			<c:when test="${not empty page.list}">
				<c:forEach items="${page.list}" var="udf">
					<tr>
						<td>${udf.id}</td>
						<td>${udf.methodName}</td>
						<td>${udf.methodDescribe}</td>
						<td>${fns:getDictLabel(udf.cacheReferenceType, 'cache_reference_type', '')}</td>
						<td>${udf.maxAgeSeconds}</td>
						<td>${udf.purgeIntervalSeconds}</td>
						<td>
								<shiro:hasPermission name="rtc:udf:add">
									<a href="#" onclick="openDialogView('查看详情', '${ctx}/rtc/udf/form?id=${udf.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i>查看详情</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="rtc:epl:view">
									<a href="#" onclick="openDialog('编辑', '${ctx}/rtc/udf/form?id=${udf.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i>编辑</a>
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
	<!-- 引入jquery插件-->
	<script src="${ctxStatic}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
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
</body>
</html>