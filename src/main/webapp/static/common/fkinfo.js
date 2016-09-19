$(function() {
	// <!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
	// Step:3 echarts & zrender as a Global Interface by the echarts-plain.js.
	// Step:3 echarts和zrender被echarts-plain.js写入为全局接口
	//onloadurl();
	var locat = (window.location+'').split('/'); 
	if('tool'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};


	var myChart = echarts.init(document.getElementById('main'));
	var now = new Date();
	var res = [];
	var len = 20;
	while (len--) {
		var time = now.toLocaleTimeString().replace(/^\D*/, '');
		time = time.substr(time.indexOf(":") + 1);
		res.unshift(time);
		now = new Date(now - 1000);
	}
	option = {
		tooltip : {
			trigger: 'axis'
		},
		toolbox: {
			show : true,
			feature : {
				mark : {show: false},
				dataView : {show: false, readOnly: false},
				magicType: {show: false, type: ['line', 'bar']},
				restore : {show: false},
				saveAsImage : {show: false}
			}
		},
		calculable : true,
		legend: {
			data:['触发总数','已处理','未处理','处理中','忽略']
		},
		xAxis : [
			{
				type : 'category',
				data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
			}
		],
		yAxis : [
			{
				type : 'value',
				name : '数量',
				axisLabel : {
					formatter: '{value} 个'
				}
			},
			{
				type : 'value',
				name : '数量',
				axisLabel : {
					formatter: '{value} 个'
				}
			}
		],
		series : [

			{
				name:'触发总数',
				type:'bar',
				data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
			},
			{
				name:'已处理',
				type:'line',
				yAxisIndex: 1,
				data:[2.6, 5.9, 8.0, 26.4, 28.7, 50.7, 175.6, 172.2, 48.7, 12.8, 6.0, 2.3]
			},
			{
				name:'未处理',
				type:'line',
				yAxisIndex: 1,
				data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
			},
			{
				name:'处理中',
				type:'line',
				data:[3.6, 5.9, 9.0, 26.4, 28.7, 70.7, 123.6, 182.2, 48.7, 18.8, 6.0, 6.3]
			},
			{
				name:'忽略',
				type:'line',
				yAxisIndex: 1,
				data:[1.0, 2.2, 1.3, 4.5, 1.3, 10.2, 12.3, 2.4, 23.0, 12.5, 12.0, 5.2]
			}
		]
	};
	var main_one = echarts.init(document.getElementById('main_one'));
	 main_one.setOption(option);
	 //main_two.setOption(two_option);
	// one_option.series[0].data[0].name ='内存使用率';
	// one_option.series[0].pointer.color='#428bca'
	// main_three.setOption(one_option);
	//var axisData;
	//clearInterval(timeTicket);
	//var timeTicket = setInterval(function() {
	//	axisData = (new Date()).toLocaleTimeString().replace(/^\D*/, '');
	//	axisData = axisData.substr(axisData.indexOf(":") + 1);
	//	var jvm = [];
	//	var ram = [];
	//	var cpu = [];
	//	$.ajax({
	//		type : "POST",
	//		url : locat + '/a/monitor/usage',
	//		async : false,
	//		dataType : 'json',
	//		success : function(json) {
	//			$("#td_jvmUsage").html(json.jvmUsage);
	//			$("#td_serverUsage").html(json.ramUsage);
	//			$("#td_cpuUsage").html(json.cpuUsage);
	//
	//
	//			jvm.push(json.jvmUsage);
	//			ram.push(json.ramUsage);
	//			cpu.push(json.cpuUsage);
	//			// 动态数据接口 addData
	//			myChart.addData([ [ 0, // 系列索引
	//			jvm, // 新增数据
	//			false, // 新增数据是否从队列头部插入
	//			false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
	//			], [ 1, // 系列索引
	//			ram, // 新增数据
	//			false, // 新增数据是否从队列头部插入
	//			false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
	//			], [ 2, // 系列索引
	//			cpu, // 新增数据
	//			false, // 新增数据是否从队列头部插入
	//			false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
	//			axisData // 坐标轴标签
	//			] ]);
	//
	//			one_option.series[0].data[0].value =json.jvmUsage;
	//			one_option.series[0].data[0].name ='JVM使用率';
	//			 one_option.series[0].pointer.color='#FF0000'
	//			main_one.setOption(one_option, true);
	//
	//			two_option.series[0].data[0].value =json.cpuUsage;
	//			main_two.setOption(two_option, true);
	//
	//			one_option.series[0].data[0].value =json.ramUsage;
	//			 one_option.series[0].data[0].name ='内存使用率';
	//			 one_option.series[0].pointer.color='#428bca'
	//			main_three.setOption(one_option, true);
	//		}
	//	});
	//}, 300000);
	//
});