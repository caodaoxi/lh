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
			  <th>阀值</th>
			  <th>描述</th>
			  <th>操作</th>
		  </tr>
	  	</thead>
	    <tbody>
			<c:choose>
			   <c:when test="${not empty page.list}">
				   <c:forEach items="${page.list}" var="threshold">
								  <tr ondblclick="clickRow(this, ${threshold.eplId})">
									  <td value="${threshold.id}">
										  ${threshold.id}
									  </td>
									  <td value="${threshold.thresholdName}">
										  ${threshold.thresholdName}
									  </td>
									  <td value="${threshold.thresholdValue}">
										  ${threshold.thresholdValue}
									  </td>
									  <td value="${threshold.thresholdDescribe}">
										  ${threshold.thresholdDescribe}
									  </td>
									  <td style="text-align: center;width:110px;">
										  <a href="${ctx}/rtc/epl/deleteThreshold?id=${threshold.id}&eplId=${threshold.eplId}" onclick="return confirmx('确定要删除该阀值吗？', this.href);" ><i class="fa fa-trash"></i>删除</a>
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
	  <div id="addtrdiv" style="margin-top:8px; width: 12%;float: right;">
		  <a href="#" onclick="addRow(${threshold.eplId})" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i>添加字段</a>
	  </div>
	  
	<script type="text/javascript" >
		function addRow(eplId){
			var table = $("#contentTable");
			var tr= $("<tr ondblclick='clickRow(this,"+eplId+")'>" +
					"<td></td>" +
					"<td><input maxlength='50' class='form-control required'/></td>" +
					"<td><input maxlength='50' class='form-control required'/></td>" +
					"<td><input maxlength='50' class='form-control required'/></td>" +
					"<td style='text-align: center;width:110px;'>" +
					"<a href='#' onclick='deleteRow(this, \"${ctx}/rtc/epl/deleteThreshold\", false)' ><i class='fa fa-trash'></i>删除</a>" +
					"<a href='#' onclick='saveRow(this, \"${ctx}/rtc/epl/saveThreshold\", "+eplId+")'><i class='fa fa-edit'></i>保存</a>" +
					"</td></tr>");
			table.append(tr);
		}
	
		function deleteRow(row, url, isOld) {
			var td = $(row);
			if(isOld) {
				confirmx('要删除该字段吗？', function(){
					$.ajax({
						type: "POST",
						url: url,
						success: function(msg){
							var td = $(row);
							td.parent().parent().remove();
						}
					});
	
				});
	
			} else {
				td.parent().parent().remove();
			}
		}
	
		function saveRow(row, url, eplId) {
			var tds = $(row).parent().parent().find("td input");
			var parent = $(row).parent().parent();
			var rowId = $(parent.find("td")[0]).attr("value");
			var threshold = {
				id:rowId,	
				eplId:eplId,
				thresholdName:$(tds[0]).val(),
				thresholdValue:$(tds[1]).val(),
				thresholdDescribe:$(tds[2]).val(),
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
						"<td value='" + data.thresholdValue + "'>" + data.thresholdValue + "</td>" +
						"<td value='" + data.thresholdDescribe + "'>" + data.thresholdDescribe + "</td>" +
						"<td style='text-align: center;width:110px;'>"+
						"<a href='${ctx}/rtc/epl/deleteThreshold?id="+data.id+"&eplId="+data.eplId+"' onclick='return confirmx('确定要删除该阀值吗？', this.href);' >"+
						"<i class='fa fa-trash'></i>删除</a></td>";
						parent.empty();
						parent.append($(ts));
					}
				});
	
			});
		}
	
		function clickRow(row,eplId) {
			var tds = $(row).find("td");
			var inputs = $(row).find("td input");
			var rowId = $(tds[0]).attr("value") ? $(tds[0]).attr("value") : '';
			var thresholdName = $(inputs[0]).val()===undefined ? $(tds[1]).attr("value") : $(inputs[0]).val();
			var thresholdValue = $(inputs[1]).val()===undefined ? $(tds[2]).attr("value") : $(inputs[1]).val();
			var thresholdDescribe = $(inputs[2]).val()===undefined ? $(tds[3]).attr("value") : $(inputs[2]).val();
			
			var tr= "<td value='" + rowId + "'>" + rowId + "</td>" +
						"<td value='" + thresholdName + "'><input value='" + thresholdName + "' maxlength='50' class='form-control required'/></td>" +
						"<td value='" + thresholdValue + "'><input value='" + thresholdValue + "'maxlength='50' class='form-control required'/></td>" +
						"<td value='" + thresholdDescribe + "'><input value='" + thresholdDescribe + "'maxlength='50' class='form-control required'/></td>" +
						"<td style='text-align: center;width:110px;'>" +
						"<a href='#' onclick='deleteRow(this,\"${ctx}/rtc/epl/deleteThreshold?id=" + rowId + "&eplId="+eplId+"\", true)' ><i class='fa fa-trash'></i>删除</a>" +
						"<a href='#' onclick='saveRow(this, \"${ctx}/rtc/epl/saveThreshold?eplId="+eplId+"\")'><i class='fa fa-edit'></i>保存</a>" +
						"</td>";
			$(row).empty();
			$(row).append($(tr));
		};
	</script>
	</body>
</html>