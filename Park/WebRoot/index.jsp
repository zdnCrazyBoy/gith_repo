<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="jquery-1.8.2.js"></script>
	<%--<script type="text/javascript">
	//jsp就相当于客户端，用ajax实现的向服务器请求，并返回response
	$(function(){
		$("#button").click(function(){
			alert("----");
			$.post("TestParkServlet",{},function(returnedData,status){
				alert("-------------");
				alert(returnedData)
				$("#text1").val(returnedData);
			})
		})
	})
	
	</script>
	
	--%><script type="text/javascript">
	
			$(function(){
				
				$("#calculate").click(function(){
					alert("click button");
					$.ajax({
						type:"Post",
						url:"TestParkServlet",
						dataType:"html",
						data:{},
						success:function(returnData){
							alert(returnData);
							$("#text1").val(returnData);
						}
					});
				});
			});
			
			
		$(function(){
						
						$("#fresh").click(function(){
							alert("fresh button");
							$.ajax({
								type:"Post",
								url:"Fresh_Servlet",
								dataType:"html",
								data:{},
								success:function(returnData){
									alert(returnData);
									$("#fresh_text").val(returnData);
								}
							});
						});
					});
		
		$(function(){
			
			$("#login").click(function(){
				alert("login button");
				$.ajax({
					type:"Post",
					url:"Login_Servlet",
					dataType:"html",
					data:{},
					success:function(returnData){
						alert(returnData);
						$("#login_text").val(returnData);
					}
				});
			});
		});	
		$(function(){
					
					$("#register").click(function(){
						alert("register button");
						$.ajax({
							type:"Post",
							url:"Register_Servlet",
							dataType:"html",
							data:{},
							success:function(returnData){
								alert(returnData);
								$("#register_text").val(returnData);
							}
						});
					});
				});	
					
		</script>
  </head>
  
  <body>
   	 <input type="button" value="Calculate" id="calculate">
     <input type="text" id="calculate_text"><br><br>
     
     <input type="button" value = "Fresh" id="fresh">
     <input type="text" id="fresh_text"><br><br>
     
     <input type="button" value="Register" id="register">
    
     <input type="text" id="register_text"><br><br>
     
     <input type="button" value="Login" id="login">
     <input type="text" id="login_text"><br><br>
  </body>
</html>
