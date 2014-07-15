<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">

    <title>账号管理</title>

    <link href="<%=basePath%>bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/set.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
    <style>
	.model{
		position: absolute; z-index: 1003; 
	   	width:400px; height:200px; text-align:center;
	    background-color:#FFFFFF; display: none;  
	    text-align: center;	
	}
	.mask {    
            position: absolute; top: 0px; filter: alpha(opacity=60); background-color: #777;  
            z-index: 1002; left: 0px;  
            opacity:0.5; -moz-opacity:0.5;  
       }  
	

	</style>
    <script type="text/javascript">
    var curUid = "";
    var curUser = "";
	$(document).ready(function () {
   		
   		$("#back").click(function(){
			window.location.href="customers";
		});
		
		$("#submit").click(function(e){
			var password = $("#password").val();
			if(password==null || password==""){
				alert("请输入新密码！");
				return;
			}
			
    		 $.post("<%=basePath%>changePwd", {
                password: password,
                uid: curUid
            }, function (result) {
                if(result=="success"){
                	alert("修改成功！");
                }else{
                	alert("修改失败！");
                }
                $("#cancel").click();
            });
		});
		$("#cancel").click(function(){
			$("#mask").hide();
    		$("#model").hide();
		});
    });
   	
    	
   	function showMask(){  
       	$("#mask").css("height",$(document).height());  
      	$("#mask").css("width",$(document).width());  
       	$("#mask").show();  
   	}  
    //让指定的DIV始终显示在屏幕正中间  
   function letDivCenter(divName){   
        var top = ($(window).height() - $(divName).height())/2;   
        var left = ($(window).width() - $(divName).width())/2;   
        var scrollTop = $(document).scrollTop();   
        var scrollLeft = $(document).scrollLeft();   
       $(divName).css( { position : 'absolute', 'top' : top + scrollTop, left : left + scrollLeft } ).show();  
       $("#username").val(curUser);
    }  
   	
    	
    	function change(uid,username){
    		curUid = uid;
    		curUser = username;
    		showMask();  
        	letDivCenter("#model");
    	}
    	
    	
    	
    	
	</script>
</head>
  
  <body>
    <div id="main">
		<div style="width: 100%;text-align: right;">
		<img id="back" title="返回" style="width: 50px;height: 50px;cursor:pointer" src="images/back.png" /></div>
	
	<div style="float:left;"><h3>账号管理</h3></div>
	<div>
		<table style="width:900px;" id="customerList" class="table table-bordered">
            <tr>
                <th>用户名</th>
                <th>权限</th>
                <th>操作</th>
            </tr>
			<c:forEach items="${userList}" var="user" varStatus="index">
				<tr>
        		<td>${user.username}</td>
        		<td><c:if test="${user.type==1 }">管理员</c:if><c:if test="${user.type==0 }">普通用户</c:if></td>
        		<td width="200px"><input type="button" onclick="change(${user.uid},'${user.username}')" class='btn' value='修改密码' /></td>
        		</tr>
        	</c:forEach>

        </table>
	</div>
	<div id="model" class="model">  
		<br/>
        <table class="table table-bordered" style="width:80%;">
        	<tr><td>用户名：</td><td><input type="text" id="username" value="" readonly="readonly" /></td></tr>
        	<tr><td>新密码：</td><td><input type="password" id="password" size="21" /></td></tr>
        	<tr><td colspan="2"><input class="btn btn-primary" type="button" id="submit"  value ="确定" />
        	<input type="button" id="cancel" class="btn btn-primary"    value ="取消" /></td></tr>
        </table>  
    </div> 
	
	<div id="mask" class="mask"></div>
	
	</div>
  </body>
</html>
