<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>风控管理平台-首页</title>
    <link rel="shortcut icon" href="favicon.ico"/>
    <link href="${ctxStatic}/css/bootstrap.min.css?v=3.3.5" rel="stylesheet"/>
    <link href="${ctxStatic}/css/font-awesome.min.css?v=4.4.0" rel="stylesheet"/>
    <link href="${ctxStatic}/css/animate.min.css" rel="stylesheet"/>
    <link href="${ctxStatic}/css/style.min.css?v=4.0.0" rel="stylesheet"/>
    <script src="${ctxStatic}/js/jquery.min.js?v=2.1.4"></script>
	<script src="${ctxStatic}/websocket/sockjs-1.1.0.min.js"></script> 
	<script type="text/javascript">
	var dayData = {};
	var hourData = {};
	var minuteData = {};
	//转换触发记录状态
	function switchTriggerStatus(status){
		if(status == 1){
			return "已处理";
		}else if(status == 2){
			return "忽略";
		}else if(status == 4){
			return "处理中";
		}else{
			return "未处理";
		}
	}
	//若数据为json格式,将其装换为对象返回
	function toObject(data){
		var dataObjs = data;
		if("string" == typeof  dataObjs){
			dataObjs=$.parseJSON(dataObjs);
		}
		return dataObjs;
	}
    /**
     * websocket客户端
     */
    $(document).ready(function(){
    	var ws=null;
    	if ('WebSocket' in window) {
    		ws = new WebSocket("ws://" + window.location.host+"/fk/websocket");
    	} else if ('MozWebSocket' in window) {
    		ws = new MozWebSocket("ws://" + window.location.host+"/fk/websocket");
    	} else {
    		ws = new SockJS("http://" + window.location.host+"/fk/sockjs/websocket");
    	}

    	//连接成功建立的回调方法
    	ws.onopen = function(event){
    	};

    	//接收到消息的回调方法
    	ws.onmessage = function (event) {
    	//	console.log((new Date()).toLocaleString()+" "+event.data);
    		var dataObjs = toObject(event.data);
    		console.log(dataObjs);
    		putCurrentStatistics(dataObjs.currentStatistics);
    		if(dataObjs.unprocessed=='0'){
    			top.$("#undealTrigger").removeClass("label");
    			top.$("#undealTrigger").removeClass("label-primary");
    			top.$("#undealTriggerTotal span").html("您今日有0条未处理消息 ");
    			top.$("#undealTrigger").html("");
    			$("#newTriggers").html("");
    		}
    		else{
    			top.$("#undealTrigger").addClass("label");
    			top.$("#undealTrigger").addClass("label-primary");
    			top.$("#undealTrigger").html(dataObjs.unprocessed);
    			top.$("#undealTriggerTotal span").html("您今日有"+dataObjs.unprocessed+"条未处理消息 ");
    			top.$("#undealTriggerList").html("");
    			$("#newTriggers").html("");
    			var x;
    			for(x in dataObjs.list){
    				top.$("#undealTriggerList").append("<div onclick='switchToUndeal("+dataObjs.list[x].eplId+")'><a class='J_menuItem'  href='#'><i class='fa fa-envelope fa-fw'></i> <span>"
    						+dataObjs.list[x].eplName+" "+dataObjs.list[x].count+"条 "+"</span></a></div>");
    			}
    			var y;
    			for(y in dataObjs.triggers){
    				$("#newTriggers").append("<tr><td>"+dataObjs.triggers[y].triggerTime+"</td><td><a  href='#' title="+dataObjs.triggers[y].eplDescribe+">"+
    						dataObjs.triggers[y].eplName+"</a></td><td>"+dataObjs.triggers[y].fundid+"</td><td>"+dataObjs.triggers[y].orgname+
    						"</td><td>"+dataObjs.triggers[y].triggerState+"</td><td>"+switchTriggerStatus(dataObjs.triggers[y].status)+"</td><td>"+dataObjs.triggers[y].dealTime+"</td></tr>");
    			}
    		}
    		
    		dayData = dataObjs.day;
    		hourData = dataObjs.hour;
    		minuteData = dataObjs.minute;
       		var smalls = $("#addPre").find("small");
        	if("今日总数新增" == $(smalls[0]).text()){
        		putEcharts(dayData, "day");
        	} else if("当前小时内总数新增" == $(smalls[0]).text()) {
        		putEcharts(hourData, "hour");
        	} else if("当前分钟内总数新增" == $(smalls[0]).text()){
        		putEcharts(minuteData, "minute");
        	}
    	};
    	
    	//连接关闭的回调方法
    	ws.onclose = function (event) {
    	};
    	
        //连接发生错误的回调方法
        ws.onerror = function(){
        };
         
        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function(){
        	ws.close();
        };

	 });
	</script>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row" id="currentStatistics">
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <span class="label label-success pull-right">当日</span>
                        <h5>未处理</h5>
                    </div>
                    <div class="ibox-content">
                        <h1 class="no-margins"><a onclick="changePage(0,3);" >0</a></h1>
                        <div class="stat-percent font-bold text-success">
                                	占比：0%
                        </div>
                        <small>未处理总数</small>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <span class="label label-info pull-right">当日</span>
                        <h5>已处理</h5>
                    </div>
                    <div class="ibox-content">
                        <h1 class="no-margins"><a onclick="changePage(0,1);" >0</a></h1>
                        <div class="stat-percent font-bold text-info">
                                	占比：0%
                        </div>
                        <small>已处理总数</small>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <span class="label label-primary pull-right">当日</span>
                        <h5>忽略</h5>
                    </div>
                    <div class="ibox-content">
                        <h1 class="no-margins"><a onclick="changePage(0,2);" >0</a></h1>
                        <div class="stat-percent font-bold text-navy">
                               	 占比：0%
                        </div>
                        <small>忽略总数</small>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <span class="label label-danger pull-right">当日</span>
                        <h5>处理中</h5>
                    </div>
                    <div class="ibox-content">
                        <h1 class="no-margins"><a onclick="changePage(0,4);" >0</a></h1>
                        <div class="stat-percent font-bold text-danger">
                                	占比：0%
                        </div>
                        <small>处理中总数</small>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>事件统计</h5>
                        <div class="pull-right">
                            <div class="btn-group">
                                <a type="button" class="btn btn-xs btn-white minute" onclick="changeChart('minute')">分</a>
                                <a type="button" class="btn btn-xs btn-white hour" onclick="changeChart('hour')">时</a>
                                <a type="button" class="btn btn-xs btn-white day active" onclick="changeChart('day')">天</a>
                            </div>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-9">
                            	<div style="overflow: auto; padding: 10;">
								    <div id="chart" style="height:250px;" ></div> 
								</div>
                            </div>
                            <div class="col-sm-3" id="addPre">
                                <ul class="stat-list">
                                    <li>
                                        <h2 class="no-margins">0</h2>
                                        <small>今日总数新增</small>
                                        <div class="stat-percent">0%
                                           <i class="fa fa-level-up text-navy"></i>
                                        </div>
                                        <div class="progress progress-mini">
                                            <div style="width: 0%;" class="progress-bar"></div>
                                        </div>
                                    </li>
                                    <li>
                                        <h2 class="no-margins ">0</h2>
                                        <small>今日未处理新增</small>
                                        <div class="stat-percent">0%
                                            <i class="fa fa-level-up text-navy"></i>
                                        </div>
                                        <div class="progress progress-mini">
                                            <div style="width: 0%;" class="progress-bar"></div>
                                        </div>
                                    </li>
                                    <li>
                                        <h2 class="no-margins ">0</h2>
                                        <small>今日忽略新增</small>
                                        <div class="stat-percent">0%
                                        	 <i class="fa fa-level-up text-navy"></i>
                                        </div>
                                        <div class="progress progress-mini">
                                            <div style="width: 0%;" class="progress-bar"></div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>最新事件</h5>
                        <div class="pull-right">
                            <div class="btn-group">
                            	<a class="J_menuItem" onclick="switchToUndeal('')">
	                                <strong>查看更多 </strong>
	                                <i class="fa fa-angle-right"></i>
                             	</a>
                            </div>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-12">
                           		<table class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
								  	<thead>
									  <tr>
										<th>发生时间</th>
										<th>事件类型</th>
										<th>资金账号</th>
										<th>所属机构</th>
										<th>事件描述</th>
										<th>处理状态</th>
										<th>更新时间</th>
									  </tr>
								  	</thead>
								    <tbody id="newTriggers">
									</tbody>
							     </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="${ctxStatic}/js/bootstrap.min.js?v=3.3.5"></script>
	<script src="${ctxStatic}/echarts/esl/esl.js"></script>
	<script src="${ctxStatic}/echarts/echarts-all.js"></script>
    <script>
    var myChart1 = echarts.init(document.getElementById('chart'));
    
    //改变图形:分/时/日
    function changeChart(period){
    	$("."+period).parent().children().removeClass("active");
    	$("."+period).addClass("active");
    	//ajaxRequest(period);
    	if("minute" == period){
    		putEcharts(minuteData, period);
    	}else if("hour" == period){
    		putEcharts(hourData, period);
    	}else{
    		putEcharts(dayData, "day");
    	}
    	
    };
    
  //使图表适应窗口
    window.onresize = function () {
    	myChart1.resize(); 
    };
    /**
    *生成图形
    */
    function generateCharts(xAxisList,legendList1,serieList1,serieList2){
	    myChart1.clear(); 
    	myChart1.setOption({
    			title: {
    			    text: '事件统计',
    			    x: '40',
    			    y: 'top',
    			    show:false,
    			},
    	      tooltip : {trigger: 'axis'},
    	      legend: {
    	          y : '0',
    	          x : 'center',
    	    	  data:legendList1
    	      },
    	      calculable : true,
    	      grid:{
    	            x:50,
    	            x2:50,
    	            y:25,
    	            height:200
    	       }, 
    	      xAxis : [
    	               {
    	            	   type : 'category', 
 //   	            	   name :unit, 
    	            	   data : xAxisList,
    	            	   splitLine: {
      	                 		show: true,
      	                 	  },
    	            	   }
    	               ],
    	      yAxis: [
    	              {
    	            	  name :'总数',
    	                  type : 'value',
    	                  position: 'left',
   	                      nameTextStyle: {
   	                    	  color:"#1ab394",
   	                    	  fontFamily: 'sans-serif',
   	                    	  fontSize: 14,
   	                 	  },
   	                 	  splitLine: {
   	                 		show: false,
   	                 	  },
    	                  axisLine : {    // 轴线
    	                      show: true,
    	                      lineStyle: {
    	                    	  color: "#00caca",
    	                      },
    	                  },
    	              },
    	              {
    	            	  name :'未处理数',
    	                  type : 'value',
    	                  nameTextStyle: {
   	                    	  color:"#464f88",
   	                    	  fontFamily: 'sans-serif',
   	                    	  fontSize: 14,
   	                 	  },
   	                 	  splitLine: {
   	                 		show: false,
   	                 	  },
    	                  axisLine : {    // 轴线
    	                      show: true,
    	                      lineStyle: {
    	                          color: "#7eb4ed",
    	                      }
    	                  },
    	              },
                  ],
    	      series:[
    	              {
  	     				name:"总数",
	       				type:'bar',
	       				itemStyle:{
   	       					normal:{
	       						color:"#00caca",
   	       					},
   	       				},
	       				yAxisIndex: 0,
	       				data:serieList2,   	            	  
    	              },{
   	     				name:"未处理数",
   	       				type:'line',
   	       				itemStyle:{
	       					normal:{
       						color:"#7eb4ed",
	       					},
	       				},
   	       				yAxisIndex: 1,
   	       				data:serieList1,
    	              }
    	              ]
    	},true);
    };
    //填充事件统计下的图形和数量增长情况
    function putEcharts(data, period){
        data = toObject(data);
        putAddPre(data.triggerStatistics, period);
	 	generateCharts(data.xAxisData, data.legendData1, data.unprocesseds, data.total);
    }
    //生成当日相对于总数的各状态事件数量占比情况
    function putCurrentStatistics(triggerStatistics){
    	var triggerStatistic = triggerStatistics;
    	//console.log(triggerStatistic);
   		var h1s = $("#currentStatistics").find("h1");
   		$(h1s[0]).html("<a onclick='changePage(0,3);'>"+triggerStatistic.unprocessed+"</a>");
   		$(h1s[1]).html("<a onclick='changePage(0,1);'>"+triggerStatistic.processed+"</a>");
   		$(h1s[2]).html("<a onclick='changePage(0,2);'>"+triggerStatistic.ignored+"</a>");
   		$(h1s[3]).html("<a onclick='changePage(0,4);'>"+triggerStatistic.processing+"</a>");
   		
   		var statPercents = $("#currentStatistics").find(".stat-percent");
   		$(statPercents[0]).html("占比："+triggerStatistic.unprocessedPro);
   		$(statPercents[1]).html("占比："+triggerStatistic.processedPro);
   		$(statPercents[2]).html("占比："+triggerStatistic.ignoredPro);
   		$(statPercents[3]).html("占比："+triggerStatistic.processingPro);
    }
    
    //生成当前时间段相对于前一时间段数量变化趋势图
    function putAddPre(triggerStatistics, period){
    	var triggerStatistic = triggerStatistics;
    	//console.log(triggerStatistic);
   		var h2s = $("#addPre").find("h2");
   		//console.log($(h2s[0]).text());
   		$(h2s[0]).text(triggerStatistic.totalAdd);
   		$(h2s[1]).text(triggerStatistic.unprocessedAdd);
   		$(h2s[2]).text(triggerStatistic.ignoredAdd);
   		
   		var statPercents = $("#addPre").find(".stat-percent");
   		var totalPre = triggerStatistic.totalPre;
   		if(totalPre>=0){
   			$(statPercents[0]).html(totalPre+"%<i class='fa fa-level-up text-navy'></i>");
   		}else{
   			$(statPercents[0]).html(totalPre+"%<i class='fa fa-level-down text-navy'></i>");
   		}

   		var unprocessedPre = triggerStatistic.unprocessedPre;
   		if(unprocessedPre>=0){
   			$(statPercents[1]).html(unprocessedPre+"%<i class='fa fa-level-up text-navy'></i>");
   		}else{
   			$(statPercents[1]).html(unprocessedPre+"%<i class='fa fa-level-down text-navy'></i>");
   		}

   		var ignoredPre = triggerStatistic.ignoredPre;
   		if(ignoredPre>=0){
   			$(statPercents[2]).html(ignoredPre+"%<i class='fa fa-level-up text-navy'></i>");
   		}else{
   			$(statPercents[2]).html(ignoredPre+"%<i class='fa fa-level-down text-navy'></i>");
   		}
   		
   		var progressBars = $("#addPre").find(".progress-bar");
   		$(progressBars[0]).css("width", totalPre+"%");
   		$(progressBars[1]).css("width", unprocessedPre+"%");
   		$(progressBars[2]).css("width", ignoredPre+"%");
   		
   		var smalls = $("#addPre").find("small");
    	if(period=='day' || period==''){
	   		$(smalls[0]).text("今日总数新增");
	   		$(smalls[1]).text("今日未处理新增");
	   		$(smalls[2]).text("今日忽略新增");
    	} else if (period=='hour') {
	   		$(smalls[0]).text("当前小时内总数新增");
	   		$(smalls[1]).text("当前小时内未处理新增");
	   		$(smalls[2]).text("当前小时内忽略新增");
    	} else if(period=='minute'){
	   		$(smalls[0]).text("当前分钟内总数新增");
	   		$(smalls[1]).text("当前分钟内未处理新增");
	   		$(smalls[2]).text("当前分钟内忽略新增");
    	}
    }
    /**
     * 点击查看更多的列表跳转
     */
    function switchToUndeal(eplId){
    	var now = new Date();
    	var nowDate = now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+" ";
    	var startDate = nowDate+"00:00:00";
    	var endDate = nowDate+"23:59:59";
    	//console.log(startDate+":"+endDate);
    	top.openTab("${ctx}/trigger/record/list?status=3&eplId="+eplId+"&startDate="+startDate+"&endDate="+endDate,"事件详情", false);
    }
    function changePage(eplId,status){
    	var now = new Date();
    	var nowDate = now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+" ";
    	var startDate = nowDate+"00:00:00";
    	var endDate = nowDate+"23:59:59";
        top.openTab("${ctx}/trigger/record?startDate=" + startDate + "&endDate=" + endDate + "&status=" + status, "事件详情", false);
    };
  </script>
</body>
</html>