<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>事件统计</title>
	<!-- 装饰:默认 -->
	<meta name="decorator" content="default"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox">
<div class="ibox-title">
		<h5>事件统计 </h5>
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
	
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
		<form:form id="searchForm" modelAttribute="triggerRecord"  method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div class="form-group">
			<span>开始时间：</span>
			<input type="text" id="startDate" name="startDate" class="Wdate" onFocus="WdatePicker({beginDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"  style="width:204px;margin-right: 50px;height: 33px;border: 1px solid #e5e6e7;"/>
			<span>结束时间：</span>
			<input type="text" id="endDate" name="endDate" class="Wdate" onFocus="WdatePicker({endDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"  style="width:204px;margin-right: 50px;height: 33px;border: 1px solid #e5e6e7;"/>
			<span>事件类型：</span>
			<form:select id="eplId" path="eplId" class="form-control m-b" style="width:204px;margin-right: 79px;">
				<form:option value="" label=""/>
				<form:options items="${eplCategories}" itemLabel="eplName" itemValue="eplId" htmlEscape="false"/>
			</form:select>
			<div hidden="true">
				<span>处理状态：</span>
				<form:select id="status" path="status"  class="form-control m-b" style="width:204px;margin-right: 79px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('trigger_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
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
				<th>事件类型</th>
				<th>已处理</th>
				<th>未处理</th>
				<th>忽略</th>
				<th>处理中</th>
				<th>总数</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="triggerRecord">
			<tr>
				<td>
					<c:if test="${triggerRecord.eplId == null}">
						汇总: 
					</c:if>
					<c:if test="${triggerRecord.eplId != null}">
						${fns:abbr(triggerRecord.eplName,50)}
					</c:if>
				</td>
				<td>
					<a href="#" onclick="changePage(${triggerRecord.eplId == null ? 0 : triggerRecord.eplId},1);" >${triggerRecord.dealCount}</a>
				</td>
				<td>
					<a href="#" onclick="changePage(${triggerRecord.eplId == null ? 0 : triggerRecord.eplId},3);" >${triggerRecord.undealCount}</a>
				</td>
				<td>
					<a href="#" onclick="changePage(${triggerRecord.eplId == null ? 0 : triggerRecord.eplId},2);" >${triggerRecord.ignoreCount}</a>
				</td>
				<td>
					<a href="#" onclick="changePage(${triggerRecord.eplId == null ? 0 : triggerRecord.eplId},4);" >${triggerRecord.verificationCount}</a>
				</td>
				<td>
					<a href="#" onclick="changePage(${triggerRecord.eplId == null ? 0 : triggerRecord.eplId},'');" >${triggerRecord.totalCount}</a>
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
<script type="text/javascript">
/**
 * 点击数字跳转
 * @param time
 * @param eplId
 * @param status
 */
function changePage(eplId,status){
    var   startDate = $("#startDate").val(), 
            endDate = $("#endDate").val(), 
            eplId = $("#eplId").val() == "" ? (eplId == 0 ? "" : eplId) : $("#eplId").val(),
            status = status;
	top.openTab("${ctx}/trigger/record?startDate="+startDate+"&endDate="+endDate+"&eplId="+eplId+"&status="+status,"事件详情", false);
};
</script>
</body>
</html>