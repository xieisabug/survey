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
    <title>问卷调查系统</title>
    <link href="<%=basePath%>bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/questionnaire.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/minimal/_all.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="paper">
	<div style="width: 100%;text-align: right;margin-bottom: 10px;margin-right: 30px;">
		<img id="back" title="返回" style="width: 50px;height: 50px;cursor:pointer" src="<%=basePath%>images/back.png" /></div>
	<div id="question-person-info">
    	<table>
    	<tr><td class="toRight">
    	<label for="updateCname">客户名称：</label></td>
    	<td>${customer.cname }</td>
    	<td class="toRight"><label for="updateTaxCode">税号：</label></td>
    	<td>${customer.taxCode }</td></tr>
    	<tr>
        <td class="toRight"><label for="updatePerson">接受回访人：</label></td>
        <td><input type="text"  style="border-style:none" name="updatePerson"  id="updatePerson" value="${customer.person}"></td>
        <td class="toRight"><label for="updatePhone">办税人电话：</label></td>
        <td>${customer.phone}&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${customer.phone != null}"><input type='checkbox' name='finalPhone' value='${customer.phone}' /></c:if></td></tr>
        <tr><td class="toRight"><label for="updatePhone1">财务负责人电话：</label></td>
        <td>${customer.phone1}&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${customer.phone1 != null}"><input type='checkbox' name='finalPhone' value='${customer.phone1}' /></c:if></td>
        <td class="toRight"><label for="updatePhone2">法人电话：</label></td>
        <td>${customer.phone2}&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${customer.phone2 != null }"><input type='checkbox' name='finalPhone' value='${customer.phone2}' /></c:if></td>
        </tr>
        </table>
    </div>
    <div>
        <div style="height: 20px;"></div>
        <input id="customerId" name="customerId" type="hidden" value="${customer.cid}"/>
        <c:forEach items="${questions}" var="q" varStatus="s">
            <div class="question">
                <span>${q.content}</span>
                <input id="questionId${s.index}" name="questionId" type="hidden" value="${q.qid}"/>
            </div>
            <div class="answer">
                <label>
                    <input type="radio" name="answer${s.index}" value="1"/><span>非常满意</span>
                </label>
                <label>
                    <input type="radio" name="answer${s.index}" value="2"/><span>满意</span>
                </label>
                <label>
                    <input type="radio" name="answer${s.index}" value="3"/><span>基本满意</span>
                </label>
                <label>
                    <input type="radio" name="answer${s.index}" value="4"/><span>不满意</span>
                </label>
                <label>
                    <input type="radio" name="answer${s.index}" value="5"/><span>不了解</span>
                </label>
                <button onclick="feedback(${s.index})" class="btn btn-primary">意见反馈</button>
                <div id="feedback${s.index}" class="hidden feedback-input">
                    <input type="text" class="form-control" id="feedbackText${s.index}" value="无"/>
                </div>
            </div>
        </c:forEach>
    </div>
    <div id="submit">
        <select id="status">
            <option value="1">未回访</option>
            <option value="2" selected>回访成功</option>
            <option value="3">无效回访</option>
            <option value="4">需要再次回访</option>
        </select>
        <select id="reason" class="hidden"></select>
        <button onclick="submitQuestionnaire()" class="btn btn-danger ie-button">提交</button>
    </div>
</div>
</body>
<script src="<%=basePath%>js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/icheck.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/questionnaire.js" type="text/javascript"></script>
<script>
    var path = "<%=basePath%>";
</script>
</html>
