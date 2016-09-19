<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>九州证券风控管理平台</title>
		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="${ctxStatic}/images/jzlogo.ico" rel="shortcut icon">
		<link href="${ctxStatic}/bootstrap/3.3.4/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />
		<link href="${ctxStatic}/awesome/4.4/css/font-awesome.min.css" rel="stylesheet" />
		<link href="${ctxStatic}/common/jeeplus.css" type="text/css" rel="stylesheet" />
		<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${ctxStatic }/common/login/ace.css" />
		<!--[if lte IE 9]>
			<link rel="stylesheet" href="../assets/css/ace-part2.css" />
		<![endif]-->

		<!-- basic scripts -->
		<script src="${ctxStatic}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
		<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
		<script src="${ctxStatic}/jquery-validation/1.14.0/jquery.validate.min.js" type="text/javascript"></script>
		<script src="${ctxStatic}/jquery-validation/1.14.0/localization/messages_zh.min.js" type="text/javascript"></script>
		<script src="${ctxStatic}/bootstrap/3.3.4/js/bootstrap.min.js"  type="text/javascript"></script>
		<script src="${ctxStatic}/common/jeeplus.js" type="text/javascript"></script>
		<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
	</head>
	<body class="login-layout light-login">

		<div class="main-container">

			<div class="main-content">

				<div class="row">

					<div style='margin-left:6%;width:130%'>
						<img src="${ctxStatic }/common/login/images/pic.png" style=" position: absolute;width:416px;height:460px;margin:20px 0 0 100px;max-width:100%;">
						<div class="login-container">
							
							<div class="center">
								<h1>
									<br/>
									<img src="${ctxStatic }/common/login/images/logo.png" style="width:150px;margin-top:-20px;">
									<br>
								</h1>
								
								<sys:message content="${message}" hideType="0"/>
							</div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
										
											<h4 class="header white lighter bigger center">
												风控管理平台
											</h4>
											<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text"  id="username" name="username" class="form-control required"  value="${username}" placeholder="用户名" />
															<i class="ace-icon">
																<img src="${ctxStatic }/common/login/images/1.png" style="">
															</i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" id="password" name="password" class="form-control required" placeholder="密码" />
															<i class="ace-icon"><img src="${ctxStatic }/common/login/images/2.png" style=""></i>
														</span>
													</label>
													<c:if test="${isValidateCodeLogin}">
														<div class="input-group m-b text-muted validateCode">
														<label class="input-label mid" for="validateCode" style="color:white;">验证码:</label>
														<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:5px;height:28px;" buttonCssStyle="color:yellowgreen;"/>
														</div>
													</c:if>

													<div class="clearfix">

														<button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
															<%--<i class="ace-icon fa fa-key"></i>--%>
															<span>登&nbsp;&nbsp;录</span>
														</button>
													</div>

													<div class="space-4"></div>
														<div id="themeSwitch" class="dropdown">
															<%--<a class="dropdown-toggle" data-toggle="dropdown" href="#">${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}<b class="caret"></b></a>--%>
															<ul class="dropdown-menu">
															  <c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href"><font color="black">${dict.label}</font></a></li></c:forEach>
															</ul>
															<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
														</div>
												</fieldset>
											</form>
											<div class="toolbarrem clearfix">
												<div>
													<a href="#" data-target="#forgot-box" class="forgot-password-link">
													<%--<i class="ace-icon fa fa-arrow-left"></i>--%>
														<label class="inline">
															<input  type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''} class="ace" />
															<span class="lbl"> 记住密码</span>
														</label>
													</a>
												</div>
												<div class="toolbar">
													<a href="#" data-target="#forgot-box" class="forgot-password-link">
													<%--<i class="ace-icon fa fa-arrow-left"></i>--%>
													忘记密码？
													</a>
												</div>
											</div>
										</div><!-- /.widget-main -->


									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->

								<div id="forgot-box" class="forgot-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header red lighter bigger center">
												<i class="ace-icon fa fa-key"></i>
												找回密码
											</h4>

											<div class="space-6"></div>
											<p style="color:#ffffff">
												请输入您的注册手机号，您将会收到新的密码。
											</p>

											<form id="resetForm">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="tel_resetpass" name="mobile" type="tel" class="form-control  text-muted required isMobile" placeholder="请输入手机号" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
													</label>

													<div class="clearfix">
														<button id="sendPassBtn" type="button" class="width-35 pull-right btn btn-sm btn-danger">
															<i class="ace-icon fa fa-lightbulb-o"></i>
															<span class="bigger-110">发送!</span>
														</button>
													</div>
												</fieldset>
											</form>
										</div><!-- /.widget-main -->

										<div class="toolbarrem center">
											<div class="toolbar">
												<a href="#" data-target="#login-box" class="back-to-login-link">
													返回登录
													<i class="ace-icon fa fa-arrow-right"></i>
												</a>
											</div>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.forgot-box -->
							</div><!-- /.position-relative -->
							<%--<div class="center"><h4 class="blue" id="id-company-text">&copy; 九州证券</h4></div>--%>
							<%--<div class="navbar-fixed-top align-right">--%>
								<%--<br />--%>
								<%--&nbsp;--%>
								<%--<a id="btn-login-dark" href="#">Dark</a>--%>
								<%--&nbsp;--%>
								<%--<span class="blue">/</span>--%>
								<%--&nbsp;--%>
								<%--<a id="btn-login-blur" href="#">Blur</a>--%>
								<%--&nbsp;--%>
								<%--<span class="blue">/</span>--%>
								<%--&nbsp;--%>
								<%--<a id="btn-login-light" href="#">Light</a>--%>
								<%--&nbsp; &nbsp; &nbsp;--%>
							<%--</div>--%>
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

		<script>
			if (window.top !== window.self) {
				window.top.location = window.location;
			}
			// 如果在框架或在对话框中，则弹出提示并跳转到首页
			if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
				alert('未登录或登录超时。请重新登录，谢谢！');
				top.location = "${ctx}";
			}
			
			$(document).ready(function() {
				$("#loginForm").validate({
					rules: {
						validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
					},
					messages: {
						username: {required: "请填写用户名."},password: {required: "请填写密码."},
						validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
					},
					errorLabelContainer: "#messageBox",
					errorPlacement: function(error, element) {
						error.appendTo($("#loginError").parent());
					} 
				});
			$("#resetForm").validate({
				rules: {
				mobile: {remote: "${ctx}/sys/user/validateMobileExist"}
			},
				messages: {
					mobile:{remote: "此手机号未注册!", required: "手机号不能为空."}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			// 手机号码验证
			jQuery.validator.addMethod("isMobile", function(value, element) {
			    var length = value.length;
			    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
			    return (length == 11 && mobile.test(value));
			}, "请正确填写您的手机号码");

			$('#sendPassBtn').click(function () { 
				if($("#tel_resetpass").val()=="" || $("#tel_resetpass-error").text()!=""){
					top.layer.alert("请输入有效的注册手机号码！", {icon: 0});//讨厌的白色字体问题
					return;

				}
				$("#sendPassBtn").attr("disabled", true); 
				$.get("${ctx}/sys/user/resetPassword?mobile="+$("#tel_resetpass").val(),function(data){
						if(data.success == false){
							top.layer.alert(data.msg, {icon: 0});//讨厌的白色字体问题
							//alert(data.msg);
							$("#sendPassBtn").html("重新发送").removeAttr("disabled"); 
							clearInterval(countdown); 

						}

					});
				var count = 60; 
				var countdown = setInterval(CountDown, 1000); 
				function CountDown() { 
					$("#sendPassBtn").attr("disabled", true); 
					$("#sendPassBtn").html("等待 " + count + "秒!"); 
					if (count == 0) { 
						$("#sendPassBtn").html("重新发送").removeAttr("disabled"); 
						clearInterval(countdown); 
					} 
					count--; 
				}
			}) ;
		});
	</script>
		<script type="text/javascript">
			window.jQuery || document.write("<script src='/webapp/static/jquery/jquery.js'>"+"<"+"/script>");
			if('ontouchstart' in document.documentElement) document.write("<script src='/webapp/static/jquery/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
		$(document).ready(function() {
			 $(document).on('click', '.toolbar a[data-target]', function(e) {
				e.preventDefault();
				var target = $(this).data('target');
				$('.widget-box.visible').removeClass('visible');//hide others
				$(target).addClass('visible');//show target
			 });
			});
			
			//you don't need this, just used for changing background
			$(document).ready(function() {
			 $('#btn-login-dark').on('click', function(e) {
				$('body').attr('class', 'login-layout');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-light').on('click', function(e) {
				$('body').attr('class', 'login-layout light-login');
				$('#id-text2').attr('class', 'grey');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-blur').on('click', function(e) {
				$('body').attr('class', 'login-layout blur-login');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'light-blue');
				
				e.preventDefault();
			 });
			 
			});
		</script>
	</body>
</html>
