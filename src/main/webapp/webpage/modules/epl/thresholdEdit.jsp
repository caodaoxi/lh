<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>阀值列表</title>
	<!-- 装饰:默认 -->
	<meta name="decorator" content="default"/>
</head>
<body>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
	  	<thead>
		  <tr>
			  <th>编号</th>
			  <th>名称</th>
			  <th>描述</th>
			  <th>阀值</th>
			  <th>操作</th>
		  </tr>
	  	</thead>
	    <tbody>
			<c:choose>
			   <c:when test="${not empty page.list}">
				   <c:forEach items="${page.list}" var="threshold">
								  <tr>
									  <td value="${threshold.id}">
										  ${threshold.id}
									  </td>
									  <td value="${threshold.thresholdName}">
										  ${threshold.thresholdName}
									  </td>
									  <td value="${threshold.thresholdDescribe}">
										  ${threshold.thresholdDescribe}
									  </td>
									  <td value="${threshold.thresholdValue}">
										  ${threshold.thresholdValue}
									  </td>
									  <td style="text-align: center;width:110px;">
										  <a href="#" onclick="clickRow(this,${threshold.eplId});" class="btn btn-success btn-xs"><i class="fa fa-edit"></i>修改</a>
									  </td>
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
     
     <script type="text/javascript" >
		function saveRow(row, url, eplId) {
			var tds = $(row).parent().parent().find("td input");
			var parent = $(row).parent().parent();
			var rowId = $(parent.find("td")[0]).attr("value");
			var thresholdName = $(parent.find("td")[1]).attr("value");
			var thresholdDescribe = $(parent.find("td")[2]).attr("value");
			var thresholdValue = $(tds[0]).val();
			var threshold = {
				id:rowId,	
				eplId:eplId,
				thresholdName:thresholdName,
				thresholdValue:thresholdValue,
				thresholdDescribe:thresholdDescribe,
			};
			confirmx('确定要保存该阀值吗？', function(){
				$.ajax({
					type: "POST",
					data:threshold,
					url: url,
					success: function(msg){
						var data = msg;
						if(data instanceof  String){
							data=eval("("+msg+")");
						}
						
						var ts = "<td value='" + data.id + "'>" + data.id + "</td>" +
						"<td value='" + data.thresholdName + "'>" + data.thresholdName + "</td>" +
						"<td value='" + data.thresholdDescribe + "'>" + data.thresholdDescribe + "</td>"+
						"<td value='" + data.thresholdValue + "'>" + data.thresholdValue + "</td>" +
						"<td style='text-align: center;width:110px;'>"+
						"<a href='#' onclick='clickRow(this,${threshold.eplId});' class='btn btn-success btn-xs'><i class='fa fa-edit'></i>修改</a></td>";
						parent.empty();
						parent.append($(ts));
					}
				});
	
			});
		}
	
		function clickRow(grid,eplId) {
			var row = $(grid).parent().parent();
			var tds = row.find("td");
			var tr= $("<td value='" + $(tds[0]).attr("value") + "'>" + $(tds[0]).attr("value") + "</td>" +
					"<td value='" + $(tds[1]).attr("value") + "'>" + $(tds[1]).attr("value") + "</td>" +
					"<td value='" + $(tds[2]).attr("value") + "'>" + $(tds[2]).attr("value") + "</td>" +
					"<td value='" + $(tds[3]).attr("value") + "'><input value='" + $(tds[3]).attr("value") + "'maxlength='20' class='form-control required'/></td>" +
					"<td style='text-align: center;width:110px;'>" +
					"<a href='#' onclick='saveRow(this, \"${ctx}/rtc/epl/saveThreshold?eplId="+eplId+"\")' class='btn btn-success btn-xs'><i class='fa fa-edit'></i>保存</a>" +
					"</td>");
			row.empty();
			row.append($(tr));
		};
	</script>
	</body>
</html>