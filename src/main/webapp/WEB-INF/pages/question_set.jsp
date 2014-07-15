<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>问题设置</title>
    <link href="<%=basePath%>bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/question_set.css" rel="stylesheet" type="text/css">
</head>
<body>

<div id="set-panel">
<div style="width:100%;text-align: right;margin-top: -10px;margin-bottom: 10px;">
<button class="btn"  id="back">返回</button>
</div>

    <c:forEach items="${questions}" var="q" varStatus="status">
        <div class="input-group question" id="question_set${status.index}">
            <input type="text" id="question${status.index}" value="${q.content}" class="form-control">
            <span class="input-group-btn">
                <button class="btn btn-danger" type="button" onclick="deleteQuestion(${status.index})">删除</button>
             </span>
        </div>
    </c:forEach>
    <button class="btn btn-info" onclick="addQuestion()">添加</button>
    <button class="btn btn-primary" onclick="submitQuestion()">提交</button>
   </div> 
</body>
<script src="<%=basePath%>js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/question_set.js" type="text/javascript"></script>
<script>
    var path = "<%=basePath%>";
</script>
</html>
