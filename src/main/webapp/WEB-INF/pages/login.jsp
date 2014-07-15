<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>问卷调查系统</title>
    <link href="<%=basePath%>bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="span12">
            <div class="row">
                <div class="span6" id="title">
                    <span style="font-size: 48px;">问卷调查系统</span>
                </div>
                <div class="span6" style="float: right;">
                    <div id="login-dialog">
                        <form action="login" method="post">
                            <fieldset>
                                <legend>登录</legend>
                                <label for="username">用户名：</label>
                                <input type="text" name="username" id="username" class="form-control"/>
                                <label for="password">密码：</label>
                                <input type="password" name="password" id="password" class="form-control"/>
                                <input type="submit" class="btn btn-primary" value="登录"/>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
